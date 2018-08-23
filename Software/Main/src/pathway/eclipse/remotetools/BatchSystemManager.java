package pathway.eclipse.remotetools;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ptp.core.IPTPLaunchConfigurationConstants;
import org.eclipse.ptp.core.jobs.IJobControl;
import org.eclipse.ptp.core.jobs.IJobStatus;
import org.eclipse.ptp.core.util.CoreExceptionUtils;
import org.eclipse.ptp.internal.rm.jaxb.control.core.JAXBControlConstants;
import org.eclipse.ptp.internal.rm.jaxb.control.ui.variables.LCVariableMap;
import org.eclipse.ptp.internal.rm.jaxb.core.JAXBCoreConstants;
import org.eclipse.ptp.internal.rm.jaxb.core.JAXBExtensionUtils;
import org.eclipse.ptp.rm.jaxb.core.data.AttributeType;
import org.eclipse.ptp.rm.jaxb.control.core.ILaunchController;
import org.eclipse.ptp.rm.jaxb.control.core.LaunchControllerManager;
import org.eclipse.ptp.rm.jaxb.core.IVariableMap;
import org.jdom2.Element;

import pathway.PAThWayPlugin;
import pathway.data.DataUtils;
import pathway.data.JobResults;
import pathway.data.persistence.Application;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.Tool;
import pathway.eclipse.ExecutionLog;
import pathway.workflows.workitems.Helper;




/** The batch system manager is responsible for talking to the batch scheduler on the HPC system. It is used to start remote processes, initiate builds, etc. */
@NonNullByDefault
public class BatchSystemManager {
    public static final String HPC_SCHEDULER_MPICH = "MPICH2";
    public static final String HPC_SCHEDULER_SLURM = "SLURM";
    public static final String HPC_SCHEDULER_SLURM_LRZ = "SLURM (Leibniz-Rechenzentrum)";
    public static final String HPC_SCHEDULER_LOAD_LEVELER = "LoadLeveler (SuperMUC)";
    public static final String HPC_SCHEDULER_LOAD_LEVELER_MPICH = "LoadLeveler MPICH (SuperMUC)";
    public static final String HPC_SCHEDULER_PLATFORM_LSF = "Platform LSF";


    /** Initializes a new instance. */
    public BatchSystemManager(ConnectionManager connMgr, IProgressMonitor monitor) throws IOException, CoreException {
        String path = FileLocator.resolve(PAThWayPlugin.getResource(PATHWAY_GET_ENV_SCRIPT_NAME)).getPath();
        if( path == null || path.length() == 0 )
            throw new IOException("Unable to initialize the path for the environment script.");

        String schedulerType = connMgr.getBatchSchedulerName();
        String connectionServiceId = connMgr.getConnectionService().getId();
        String connectionName = connMgr.getConnection().getName();
        System.out.println("Creating a new batch system manager of type '" + schedulerType + "' for connection '" + connectionName + "'...");
        this.connectionMgr = connMgr;
        this.envScriptPath = path;
        this.jobControl = LaunchControllerManager.getInstance().getLaunchController(connectionServiceId, connectionName, schedulerType);
        assert jobControl.isInitialized();

        URL pathToLLConfig = JAXBExtensionUtils.getConfigurationURL(schedulerType);
        System.out.println("Configuring the batch system manager... (" + pathToLLConfig + ")");
        jobControl.setRMConfigurationURL(pathToLLConfig);

        System.out.println("Starting the batch system manager...");
        jobControl.start(monitor);

        // NOTE:
        // We never stop and dispose the job controller, because it may also be used by other batch system manager instances and maybe even by some other PTF
        // components activated by the user. The launch controller manager will probably dispose of it in time.

        System.out.println("Batch system manager initialized successfully.");
    }


    /** Gets the connection manager used by this batch system manager. */
    public ConnectionManager getConnectionManager() {
        return connectionMgr;
    }


    /** Schedules a job on the batch system. */
    public IJobStatus startJob(Experiment experiment, WorkflowProcessInstance workflowInstance) throws CoreException {
        return startJob(experiment, workflowInstance, new NullProgressMonitor());
    }


    /** Schedules a job on the batch system. */
    public IJobStatus startJob(Experiment experiment, WorkflowProcessInstance workflowInstance, IProgressMonitor monitor) throws CoreException {
        final Application app = experiment.getApplication();
        if( app == null )
            throw new IllegalArgumentException("The experiment's application cannot be a null reference.");

        String executable = experiment.getExecCmd();
        if( executable == null || executable.isEmpty() )
            throw new IllegalArgumentException("The experiment's executable command cannot be null or empty.");
        executable = Helper.fillPlaceholders(executable, experiment, workflowInstance);

        String arguments = app.getStartArgs();
        if( arguments == null )
            throw new IllegalArgumentException("The experiment's application arguments cannot be a null reference.");
        arguments = Helper.fillPlaceholders(arguments, experiment, workflowInstance);

        // get the requested user environment
        Map<String, String> targetEnv = new HashMap<String, String>();
        if( experiment.getEnvironment() != null )
            targetEnv.putAll(DataUtils.EnvironmentVars2Map(experiment.getEnvironment()));
        
        

        return startJobImpl(experiment, monitor, app, executable, arguments, targetEnv, workflowInstance);
    }


    /** Requests the cancellation of a job on the batch system. */
    public void cancelJob(String jobId) {
        cancelJob(jobId, new NullProgressMonitor());
    }


    /** Requests the cancellation of a job on the batch system. */
    public void cancelJob(String jobId, IProgressMonitor monitor) {
        try {
            jobControl.control(jobId, IJobControl.TERMINATE_OPERATION, monitor);
        }
        catch( CoreException ex ) {
            ex.printStackTrace();
        }
    }


    public IJobStatus getJobStatus(String jobId) throws CoreException {
        IProgressMonitor monitor = new NullProgressMonitor();
        if( jobs.containsKey(jobId) )
            return jobControl.getJobStatus(jobId, monitor);

        throw new IllegalArgumentException("The specified job id is invalid.");
    }


    private String retrieveLog(Experiment exp, String jobId, String extension) {
        String filename = "pathway." + jobId + extension;
        String path = exp.getStartupFolder() + "/" + filename;
        String contents = FileUtils.getRemoteTextFileContents(connectionMgr, path);
        FileUtils.deleteResource(connectionMgr, path);

        return contents;
    }


    /** Gets the result of a scheduled job. */
    public JobResults getJobResultsBlocking(String jobId, IProgressMonitor monitor) throws CoreException {
        JobResults result = new JobResults();
        IJobStatus jobStatus = getJobStatus(jobId);
        System.out.println("Waiting for job to finish before getting environment file: " + jobId + ":" + jobStatus.getState());

        while( !jobStatus.getState().equals(IJobStatus.COMPLETED) && !jobStatus.getState().equals(IJobStatus.FAILED) ) {
        	
        	boolean canceled = false;
            if( !canceled && monitor.isCanceled() ) {
                cancelJob(jobId);
                canceled = true;
            }

            try {
            	
                Thread.sleep(1000);
            }
            catch( InterruptedException ex ) {
            }
      
            jobStatus = getJobStatus(jobStatus.getJobId());
        }
        
        if( jobStatus.getStateDetail().equals(IJobStatus.CANCELED) ) {
            result.status = "Canceled";
            return result;
        }
        else if( jobStatus.getState().equals(IJobStatus.COMPLETED) ) {
            final Experiment experiment = jobs.get(jobId);
            
            if( experiment == null )
                throw new IllegalArgumentException("The specified job id is invalid.");

            result.status = "Completed";
            result.stdout = retrieveLog(experiment, jobId, ".out");
            result.stderr = retrieveLog(experiment, jobId, ".err");
            result.cpu = retrieveLog(experiment, jobId, ".cpu");
            result.uname = retrieveLog(experiment, jobId, ".uname");
            result.environment = DataUtils.parseRecordedEnvStr(retrieveLog(experiment, jobId, ".env"));
        }
        
        return result;
    }


    private IJobStatus startJobImpl(Experiment experiment, IProgressMonitor monitor, Application app, String executable, String arguments,
            Map<String, String> targetEnv, WorkflowProcessInstance workflowInstance) throws CoreException {

        final String timestamp = new SimpleDateFormat("yyyMMddHHmm").format(experiment.getExpDate());
        final String experimentName = "Exp." + timestamp + "." + experiment.getMpiProcs() + "x" + experiment.getOmpThreads();

        // get the environment variables required by the chosen application - will be added to the user environment
        if( app.getReqEnvVars() != null ) {
            Map<String, String> appEnvVars = DataUtils.EnvironmentVars2Map(app.getReqEnvVars());
            for( String varName: appEnvVars.keySet() ) {
                String applEnvVarValue = appEnvVars.get(varName);
                if( applEnvVarValue.contains("{#") ) {
                    applEnvVarValue = StringUtils.replace(applEnvVarValue, "{#MPI_PROCS#}", String.valueOf(experiment.getMpiProcs()));
                    applEnvVarValue = StringUtils.replace(applEnvVarValue, "{#OMP_THREADS#}", String.valueOf(experiment.getOmpThreads()));
                }
                appEnvVars.put(varName, applEnvVarValue);
            }
            targetEnv.putAll(appEnvVars);
        }

        List<String> reqModules = new ArrayList<String>();
        if( app.getReqModules() != null )
            reqModules.addAll(DataUtils.modulesJson2List(app.getReqModules()));

        if( experiment.getStartupFolder() == null || experiment.getStartupFolder().length() <= 1 )
            if( app.getExecLocation() == null )
                experiment.setStartupFolder(connectionMgr.getCWD());
            else
                experiment.setStartupFolder(app.getExecLocation());
        experiment.setStartupFolder(Helper.fillPlaceholders(experiment.getStartupFolder(), experiment));

        // Check and configure the performance tool that will be used
        String toolName = null;
        final Tool tool = experiment.getTool();
        if( tool != null ) {
            String toolCMD = null;
            String toolArgs = null;
            String resultsURI = experiment.getResultsURI();

            toolCMD = tool.getProfileCMD();
            toolArgs = tool.getProfileArgs();
            toolCMD = StringUtils.replace(toolCMD, "{#APP_NAME#}", executable);

            // The tool command should be part of a longer string of argument and can be prefixed by other tools or env vars
            // In such a case, we should use directly the tool's arguments as a starting command and drop the tool cmd
            if( StringUtils.contains(toolArgs, "{#TOOL_CMD#}") ) {
                toolArgs = StringUtils.replace(toolArgs, "{#TOOL_CMD#}", toolCMD);
                toolCMD = null;
            }
            toolArgs = Helper.fillPlaceholders(toolArgs, experiment, workflowInstance); // fill ALL placeholders in tool arguments. for eg., MPI_PROCS, OMP_THREADS, TUNE_PARAM_NAMES

            // set the results uri to the experiment
            experiment.setResultsURI(resultsURI);

            reqModules.addAll(DataUtils.modulesJson2List(tool.getReqModules()));
            targetEnv.putAll(DataUtils.EnvironmentVars2Map(tool.getReqEnvVars()));
            
            if( toolCMD == null ) {
                toolCMD = toolArgs;
                toolArgs = "";
            }

            assert toolCMD != null;
            assert toolArgs != null;
            executable = toolCMD;
            arguments = toolArgs;
            toolName = tool.getName();
        }

        // replace all placeholders in the environment variables
        for( Entry<String, String> entry: targetEnv.entrySet() )
            entry.setValue(Helper.fillPlaceholders(entry.getValue(), experiment));
        
        
        ExecutionLog.get().writeLog("BatSysManager::startJobImpl() says, executable = " + executable + arguments);
        // create launch configuration and submit job
        ILaunchConfiguration jobLaunchConfig = createLaunchConfiguration(experimentName, executable, arguments, experiment.getMpiProcs(),
                experiment.getOmpThreads(), experiment.getStartupFolder(), targetEnv, reqModules, app.getWallclockLimit(), toolName);
        String jobId = jobControl.submitJob(jobLaunchConfig, ILaunchManager.RUN_MODE, monitor);
        if( jobId == null )
            throw CoreExceptionUtils.newException("Got no job id from the remote system.");

        // report success
        jobs.put(jobId, experiment);
        return jobControl.getJobStatus(jobId, monitor);
    }


    /**
     * Creates a launch configuration that will be used to have the job submitted by the PTP. This is what users of PTP normally have to do manually using a
     * GUI.
     */
    @SuppressWarnings("restriction")
    private ILaunchConfiguration createLaunchConfiguration(String jobName, String executable, String progArgs, Integer mpiProcs, Integer ompThreads,
            String workingFolder, Map<String, String> userEnv, List<String> reqModules, int wallclockLimit, @Nullable String toolName) {

        final String rmUniqID = jobControl.getControlId();
        ILaunchConfiguration launchConfig = new CustomLaunchConfiguration();

        Map<String, Object> launchAttribs = null;
        try {
            launchAttribs = launchConfig.getAttributes();
        }
        catch( CoreException ex ) {
            Assert.fail("Failed getting launch configuration attributes: " + ex.getMessage());
            ex.printStackTrace();
            return launchConfig;
        }

        // operate with a LCVar map to get dynamic resolution of linked variables
        LCVariableMap rmVariablesMap = new LCVariableMap();
        
       
        try {
            rmVariablesMap.initialize(jobControl.getEnvironment(), jobControl.getControlId());
        }
        catch( Throwable ex ) {
            Assert.fail("Failed initializing variables map from resource manager: " + ex.getMessage());
            ex.printStackTrace();
            return launchConfig;
        }
     
        //rmVariablesMap.restoreGlobal();
        //rmVariablesMap.clear();

        
        /*
         * Some tools require the MPIEXEC to be part of their arguments instead of a prefix to the cmd line. This requires that we replace that placeholder with
         * the system dep mpiexec and disable the automatic prefixing of the cmd line.
         * 
         * However, some other tool do not require MPIEXEC at all (like Periscope) as they will start the processes internally. In this case, we have to again
         * disable the automatic prefixing of the cmd line.
         * 
         * In all other cases when running an uninstr app, we still need the original MPIEXEC to prefix the cmd line.
         */
        String mpiExec = (String)rmVariablesMap.getValue(JAXBControlConstants.MPI_CMD);
        if( StringUtils.contains(executable, "{#MPI_EXEC#}") ) {
            executable = StringUtils.replace(executable, "{#MPI_EXEC#}", mpiExec);
            rmVariablesMap.putValue(JAXBControlConstants.MPI_CMD, "");
        }

        // number of MPI processes/OMP Threads to use for the job
        rmVariablesMap.putValue("ompNumberOfThreads", ompThreads);
        
       
        if (toolName.equalsIgnoreCase("READEX PTF")){
        	int i = 1;
            Integer intg = new Integer(i);
        	rmVariablesMap.putValue("tasks_per_node", intg); 
        	rmVariablesMap.putValue("nodes", mpiProcs+1);
        	rmVariablesMap.putValue("mpiProcesses", "");
        	rmVariablesMap.setDefault("mpiProcesses", null);
        }
        else {
        	rmVariablesMap.putValue("nodes", mpiProcs);
        	rmVariablesMap.putValue("mpiProcesses", mpiProcs);
        	rmVariablesMap.putValue("tasks_per_node", "");
        	rmVariablesMap.setDefault("tasks_per_node", null);
        }
        
        //job config        
        //rmVariablesMap.putValue("partition", "haswell");
        //rmVariablesMap.putValue("srun.ntasks", "1");
        //rmVariablesMap.putValue("memPerCPU", "500M");
        //cpus-per-task is already set by "ompNumberOfThreads"

        // executable to run
        rmVariablesMap.putValue(JAXBControlConstants.EXEC_PATH, executable);

        // cmd arguments to pass to the executable
        rmVariablesMap.putValue(JAXBControlConstants.PROG_ARGS, progArgs);
        
        // Export set to ALL
        rmVariablesMap.putValue(JAXBControlConstants.EXPORT, "ALL");
        

        // pathway-specific attributes
        rmVariablesMap.putValue("pathway.get-env.script", envScriptPath);
        rmVariablesMap.putValue("pathway.working_dir", workingFolder);
        rmVariablesMap.putValue("pathway.job.name", PATHWAY_JOB_NAME_PREFIX + jobName);
        rmVariablesMap.putValue("pathway.wallclock_limit", toTimeString(wallclockLimit));
        rmVariablesMap.putValue("pathway.wallclock_limit_minutes", wallclockLimit);
        
        rmVariablesMap.putValue("pathway.use_modules", "/projects/p_readex/modules");
        rmVariablesMap.putValue("pathway.modules", StringUtils.join(reqModules, " "));
        rmVariablesMap.putValue("pathway.environment", createEnvironmentString(userEnv)); 
        rmVariablesMap.putValue("optionExclusive", "--exclusive");
        rmVariablesMap.putValue("pathway.toolrun", (toolName != null && !toolName.isEmpty()));

        // add all additional system variables
        launchAttribs.put(IPTPLaunchConfigurationConstants.ATTR_CONFIGURATION_NAME, jobControl.getConfiguration().getName());
        launchAttribs.put(IPTPLaunchConfigurationConstants.ATTR_CONNECTION_NAME, jobControl.getConnectionName());
        launchAttribs.put(IPTPLaunchConfigurationConstants.ATTR_REMOTE_SERVICES_ID, jobControl.getRemoteServicesId());
        launchAttribs.put(IPTPLaunchConfigurationConstants.ATTR_RESOURCE_MANAGER_UNIQUENAME, rmUniqID);

        // initialize the list of valid attributes from the currently created map
        List<String> validAttributes = new ArrayList<String>(rmVariablesMap.getAttributes().keySet());

        validAttributes.add(JAXBCoreConstants.CURRENT_CONTROLLER);
        Collection<String> filteredCol = CollectionUtils.collect(validAttributes, new Transformer() {
            @Override
            public Object transform(@Nullable Object arg0) {
                if( arg0 == null )
                    throw new IllegalArgumentException("The argument cannot be a null reference.");

                return ((String)arg0).replace(rmUniqID + ".", "");
            }
        });
        validAttributes.clear();
        validAttributes.addAll(CollectionUtils.union(filteredCol, jobControl.getEnvironment().getAttributes().keySet()));

        // explicitly force the loading of scheduler variables (else they are simply ignored)... this list should include all props defined above
        rmVariablesMap.putValue(JAXBCoreConstants.CURRENT_CONTROLLER, "Default");
        rmVariablesMap.putValue(JAXBCoreConstants.VALID + "Default", StringUtils.join(validAttributes, " "));

        //reset all attributes that use the Link-value-to option to the value of the linked attribute
        //rmVariablesMap.relinkHidden("Default");
        
        Map<String, AttributeType> allRMAttrs = rmVariablesMap.getAttributes();
        for( String attrKey: allRMAttrs.keySet() )
            launchAttribs.put(attrKey, allRMAttrs.get(attrKey).getValue());

        return launchConfig;
    }


    private static String toTimeString(int minutes) {
        String h = String.format("%02d", minutes / 60);
        String m = String.format("%02d", minutes % 60);
        return h + ":" + m + ":00";
    }


    private static String createEnvironmentString(Map<String, String> environment) {
        final StringBuilder result = new StringBuilder();

        for( Entry<String, String> entry: environment.entrySet() )
            result.append("export ").append(entry.getKey()).append("=").append(entry.getValue()).append("\n");

        return result.toString();
    }


    @SuppressWarnings("unused")
    private static void printAttributes(ILaunchConfiguration jobLaunchConfig) throws CoreException {
        System.out.println();
        System.out.println();
        System.out.println();

        final Map<?, ?> attributes = jobLaunchConfig.getAttributes();
        for( Object key: attributes.keySet() ) {
            String keyString = key.toString();
            for( int i = keyString.length(); i < 80; ++i )
                keyString += ' ';
            System.out.println(keyString + attributes.get(key));
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }


    private static final String PATHWAY_JOB_NAME_PREFIX = "PAThWay_";
    private static final String PATHWAY_GET_ENV_SCRIPT_NAME = "resources/get_env.sh";

    private final Map<String, Experiment> jobs = new HashMap<String, Experiment>();
    private final ConnectionManager connectionMgr;
    private final String envScriptPath;
    private final ILaunchController jobControl;
}
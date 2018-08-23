package pathway.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.orm.PersistentException;

import pathway.data.DataUtils;
import pathway.data.ExperimentBuilder;
import pathway.data.persistence.Application;
import pathway.data.persistence.ApplicationCriteria;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.HPCSystem;
import pathway.data.persistence.HPCSystemCriteria;
import pathway.data.persistence.Tool;
import pathway.data.persistence.ToolCriteria;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.workflows.WorkflowErrorException;




@NonNullByDefault
public abstract class PrepareExperimentsList {
    /**
     * Generate Pathway experiments for a given application, performance tool and a set of HPC systems using the specified number of MPI processes and OpenMP
     * threads
     * 
     * @param application
     *            ID of the application to run/analyze
     * @param mpiProcs
     *            Number of MPI processes
     * @param ompThreads
     *            Number of OpenMP threads
     * @param hpcSystems
     *            A list of IDs of the target HPC systems to be used for the experiments
     * @param workingFolder
     *            Start-up folder on the target HPC system
     * @param perfTool
     *            ID of the performance tool (or null) to be used for the experiments
     * @param comment
     *            User's comment describing the given experiments
     * @return A list of experiments generated from the given configuration
     */
    public static List<Experiment> generateExpiments_Mult_HpcSystems(String application, int mpiProcs, int ompThreads, List<String> hpcSystems,
            @Nullable String perfTool, String comment) {

        List<Experiment> result = new ArrayList<Experiment>(hpcSystems.size());
        for( String hpcSystem: hpcSystems )
            result.addAll(generateExpiments_Single(application, String.valueOf(mpiProcs), String.valueOf(ompThreads), hpcSystem, perfTool, comment));

        return result;
    }


    /**
     * Generate Pathway experiments for a given application, performance tool and a set of HPC systems using a given range for MPI processes and OpenMP threads
     * 
     * @param application
     *            ID of the application to run/analyse
     * @param mpiProcsConfig
     *            Number of MPI processes given as a range configuration in the form: MIN:MAX:INCREMENT or 10,45,797,32 or a single value (e.g. 128)
     * @param ompThreadsConfig
     *            Number of OpenMP threads given as a range configuration in the form: MIN:MAX:INCREMENT or 10,45,797,32 or a single value (e.g. 128)
     * @param hpcSystems
     *            A list of IDs of the target HPC system to be used for the experiments
     * @param workingFolder
     *            Start-up folder on the target HPC system
     * @param perfTool
     *            ID of the performance tool (or null) to be used for the experiments
     * @param comment
     *            User's comment describing the given experiments
     * @return A list of experiments generated from the given configuration
     */
    public static List<Experiment> generateExpiments_Mult_HpcSystems(String application, String mpiProcsConfig, String ompThreadsConfig,
            List<String> hpcSystems, @Nullable String perfTool, String comment) {

        List<Experiment> result = new ArrayList<Experiment>(hpcSystems.size());
        for( String hpcSystem: hpcSystems )
            result.addAll(generateExpiments_Single(application, mpiProcsConfig, ompThreadsConfig, hpcSystem, perfTool, comment));

        return result;
    }


    public static List<Experiment> generateExpiments_Mult_EnvVars(String application, String mpiProcsConfig, String ompThreadsConfig, String hpcSystem,
            String perfTool, String comment, List<String> envVarGroups) {

        List<Experiment> result = new ArrayList<Experiment>(envVarGroups.size());
        for( String envVarGroup: envVarGroups ) {
            Map<String, String> envVarsMap = new HashMap<String, String>();
            String[] vars = envVarGroup.split(";");
            for( String varVal: vars ) {
                String[] temp = varVal.split("=");
                envVarsMap.put(temp[0], temp[1]);
            }

            List<Experiment> res = generateExpiments_Single(application, mpiProcsConfig, ompThreadsConfig, hpcSystem, perfTool, comment);
            for( Experiment experiment: res )
                experiment.setEnvironment(DataUtils.EnvironmentVars2Str(envVarsMap));

            result.addAll(res);
        }

        return result;
    }


    /**
     * Generate Pathway experiments for a given application, a set of performance tools and a single HPC system using the specified number of MPI processes and
     * OpenMP threads
     * 
     * @param application
     *            ID of the application to run/analyse
     * @param mpiProcs
     *            Number of MPI processes
     * @param ompThreads
     *            Number of OpenMP threads
     * @param hpcSystem
     *            ID of the target HPC system to be used for the experiments
     * @param workingFolder
     *            Start-up folder on the target HPC system
     * @param perfTools
     *            A list of IDs of the performance tools to be used for the experiments
     * @param comment
     *            User's comment describing the given experiments
     * @return A list of experiments generated from the given configuration
     */
    public static List<Experiment> generateExpiments_Mult_PerfTools(String application, int mpiProcs, int ompThreads, String hpcSystem, List<String> perfTools,
            String comment) {

        List<Experiment> result = new ArrayList<Experiment>(perfTools.size());
        for( String perfTool: perfTools )
            result.addAll(generateExpiments_Single(application, String.valueOf(mpiProcs), String.valueOf(ompThreads), hpcSystem, perfTool, comment));

        return result;
    }


    /**
     * Generate Pathway experiments for a given application, a set of performance tools and a single HPC system using a given range for MPI processes and OpenMP
     * threads
     * 
     * @param application
     *            ID of the application to run/analyze
     * @param mpiProcsConfig
     *            Number of MPI processes given as a range configuration in the form: MIN:MAX:INCREMENT or 10,45,797,32 or a single value (e.g. 128)
     * @param ompThreadsConfig
     *            Number of OpenMP threads given as a range configuration in the form: MIN:MAX:INCREMENT or 10,45,797,32 or a single value (e.g. 128)
     * @param hpcSystem
     *            ID of the target HPC system to be used for the experiments
     * @param workingFolder
     *            Start-up folder on the target HPC system
     * @param perfTools
     *            A list of IDs of the performance tools to be used for the experiments
     * @param comment
     *            User's comment describing the given experiments
     * @return A list of experiments generated from the given configuration
     */
    public static List<Experiment> generateExpiments_Mult_PerfTools(String application, String mpiProcsConfig, String ompThreadsConfig, String hpcSystem,
            List<String> perfTools, String comment) {

        List<Experiment> result = new ArrayList<Experiment>(perfTools.size());
        for( String perfTool: perfTools )
            result.addAll(generateExpiments_Single(application, mpiProcsConfig, ompThreadsConfig, hpcSystem, perfTool, comment));

        return result;
    }


    /**
     * Generate Pathway experiments for a set of given applications, performance tool and a single HPC system using the specified number of MPI processes and
     * OpenMP threads
     * 
     * @param applications
     *            A list of IDs of applications to run/analyze
     * @param mpiProcs
     *            Number of MPI processes
     * @param ompThreads
     *            Number of OpenMP threads
     * @param hpcSystem
     *            ID of the target HPC system to be used for the experiments
     * @param workingFolder
     *            Start-up folder on the target HPC system
     * @param perfTool
     *            ID of the performance tool (or null) to be used for the experiments
     * @param comment
     *            User's comment describing the given experiments
     * @return A list of experiments generated from the given configuration
     */
    public static List<Experiment> generateExpiments_Mult_Applications(List<String> applications, int mpiProcs, int ompThreads, String hpcSystem,
            @Nullable String perfTool, String comment) {

        List<Experiment> result = new ArrayList<Experiment>(applications.size());
        for( String application: applications )
            result.addAll(generateExpiments_Single(application, String.valueOf(mpiProcs), String.valueOf(ompThreads), hpcSystem, perfTool, comment));

        return result;
    }


    /**
     * Generate Pathway experiments for a given application, performance tool and a single HPC system using a given range for MPI processes and OpenMP threads
     * 
     * @param appName
     *            ID of the application to run/analyze
     * @param mpiProcsConfig
     *            Number of MPI processes given as a range configuration in the form: MIN:MAX:INCREMENT or 10,45,797,32 or a single value (e.g. 128)
     * @param ompThreadsConfig
     *            Number of OpenMP threads given as a range configuration in the form: MIN:MAX:INCREMENT or 10,45,797,32 or a single value (e.g. 128)
     * @param hpcSystem
     *            ID of the target HPC system to be used for the experiments
     * @param workingFolder
     *            Start-up folder on the target HPC system
     * @param perfTool
     *            ID of the performance tool (or null) to be used for the experiments
     * @param comment
     *            User's comment describing the given experiments
     * @return A list of experiments generated from the given configuration
     */
    public static List<Experiment> generateExpiments_Single(String appName, String mpiProcsConfig, String ompThreadsConfig, String hpcSystem,
            @Nullable String perfTool, String comment) {

        HPCSystem oHpcSystem = null;
        Application application = null;
        @Nullable Tool oPerfTool = null;

        try {
            // Get the HPC System config
            oHpcSystem = (HPCSystem)new HPCSystemCriteria().add(Restrictions.eq("name", hpcSystem)).uniqueResult();

            // Get the application config
            // Append the default config if only the app name is given
            if( !appName.contains("|") )
                appName += "|default";

            String[] appNameConfig = appName.split("\\|");
            application = (Application)new ApplicationCriteria().add(Restrictions.eq("name", appNameConfig[0]))
                    .add(Restrictions.eq("config", appNameConfig[1])).uniqueResult();
            if( application == null ) {
                String message = "The specified application has not been found in the database: " + appName;
                ExecutionLog.get().writeErr(message);
                UIUtils.showErrorMessage(message);
                throw new WorkflowErrorException();
            }

            // Get the performance tool
            if( perfTool != null ) {
                String[] splitToolNameVer = perfTool.split("\\|");
                ToolCriteria toolsCriteria = new ToolCriteria();
                toolsCriteria.add(Restrictions.eq("version", splitToolNameVer[1]));
                oPerfTool = (Tool)toolsCriteria.add(Restrictions.eq("name", splitToolNameVer[0])).uniqueResult();
            }
        }
        catch( PersistentException ex ) {
            throw new RuntimeException(ex);
        }

        String workingFolder = application.getExecLocation();
        int[] numMpiProcs = parseRangeConfig(mpiProcsConfig);
        int[] numThreads = parseRangeConfig(ompThreadsConfig);
        List<Experiment> experiments = new ArrayList<Experiment>(numMpiProcs.length * numThreads.length);

        for( Integer mpiProc: numMpiProcs )
            for( Integer ompThread: numThreads ) {
                ExperimentBuilder expBuilder = new ExperimentBuilder();
                expBuilder.expDateTime(DateTime.now()).hpcSystem(oHpcSystem).application(application).workingDir(workingFolder);
                expBuilder.tool(oPerfTool).expComment(comment);
                expBuilder.mpiProcs(mpiProc).ompThreads(ompThread);
                experiments.add(expBuilder.build());
            }

        return experiments;
    }


    public static List<Experiment> generateExpiments(Object appObj, String mpiProcs, String ompThreads, Object hpcSystemObj, String perfTool, String comment,
            List<String> envVarGroups) {

        if( appObj instanceof String && hpcSystemObj instanceof String && envVarGroups.size() == 0 )
            return generateExpiments_Single((String)appObj, mpiProcs, ompThreads, (String)hpcSystemObj, (String)perfTool, comment);
        else if( appObj instanceof String && hpcSystemObj instanceof String && envVarGroups.size() > 0 )
            return generateExpiments_Mult_EnvVars((String)appObj, mpiProcs, ompThreads, (String)hpcSystemObj, (String)perfTool, comment, envVarGroups);
        else
            throw new IllegalArgumentException("Invalid options specified when generating new experiments");
    }

    public static List<Experiment> generateExpimentsWithInputs_Single(String appName, String mpiProcsConfig, String ompThreadsConfig, String hpcSystem,
            @Nullable String perfTool, String comment, List<String> inputDataFileNamesList) {

        HPCSystem oHpcSystem = null;
        Application application = null;
        @Nullable Tool oPerfTool = null;

        try {
            // Get the HPC System config
            oHpcSystem = (HPCSystem)new HPCSystemCriteria().add(Restrictions.eq("name", hpcSystem)).uniqueResult();

            // Get the application config
            // Append the default config if only the app name is given
            if( !appName.contains("|") )
                appName += "|default";

            String[] appNameConfig = appName.split("\\|");
            application = (Application)new ApplicationCriteria().add(Restrictions.eq("name", appNameConfig[0]))
                    .add(Restrictions.eq("config", appNameConfig[1])).uniqueResult();
            if( application == null ) {
                String message = "The specified application has not been found in the database: " + appName;
                ExecutionLog.get().writeErr(message);
                UIUtils.showErrorMessage(message);
                throw new WorkflowErrorException();
            }

            // Get the performance tool
            if( perfTool != null ) {
                String[] splitToolNameVer = perfTool.split("\\|");
                ToolCriteria toolsCriteria = new ToolCriteria();
                toolsCriteria.add(Restrictions.eq("version", splitToolNameVer[1]));
                oPerfTool = (Tool)toolsCriteria.add(Restrictions.eq("name", splitToolNameVer[0])).uniqueResult();
            }
        }
        catch( PersistentException ex ) {
            throw new RuntimeException(ex);
        }

        String workingFolder = application.getExecLocation();
        int[] numMpiProcs = parseRangeConfig(mpiProcsConfig);
        int[] numThreads = parseRangeConfig(ompThreadsConfig);
        List<Experiment> experiments = new ArrayList<Experiment>(numMpiProcs.length * numThreads.length * inputDataFileNamesList.size());
        
        
        for( Integer mpiProc: numMpiProcs )
            for( Integer ompThread: numThreads ) 
            	for(String inputDataFileName : inputDataFileNamesList) 
            	{
            		ExperimentBuilder expBuilder = new ExperimentBuilder();
            		expBuilder.expDateTime(DateTime.now()).hpcSystem(oHpcSystem).application(application).workingDir(workingFolder);
            		expBuilder.tool(oPerfTool).expComment(comment);
            		expBuilder.mpiProcs(mpiProc).ompThreads(ompThread);
            		Experiment e = expBuilder.build();
            		e.setInputDataFileName(inputDataFileName);
            		experiments.add(e);
            	}

        return experiments;
    }

    
    public static List<Experiment> generateExpimentsWithInputs(Object appObj, String mpiProcs, String ompThreads, Object hpcSystemObj, String perfTool, String comment,
            List<String> envVarGroups, List<String> inputDataFileNamesList) {

        if( appObj instanceof String && hpcSystemObj instanceof String && envVarGroups.size() == 0 )
            return generateExpimentsWithInputs_Single((String)appObj, mpiProcs, ompThreads, (String)hpcSystemObj, (String)perfTool, comment, inputDataFileNamesList);
        else if( appObj instanceof String && hpcSystemObj instanceof String && envVarGroups.size() > 0 )
            return generateExpiments_Mult_EnvVars((String)appObj, mpiProcs, ompThreads, (String)hpcSystemObj, (String)perfTool, comment, envVarGroups);
        else
            throw new IllegalArgumentException("Invalid options specified when generating new experiments with inputs");
    }

    
    
    private PrepareExperimentsList() {
    }


    /** Gets a list of integers that from a range specified in the form # or #, #, ... or #:# or #:#:# */
    private static int[] parseRangeConfig(String rangeConfig) {
        // #:# or #:#:#
        if( rangeConfig.contains(":") ) {
            int minNum;
            int maxNum;
            int incr;

            String[] strSplit = rangeConfig.split(":");
            if( strSplit.length == 2 ) {
                minNum = Integer.parseInt(strSplit[0]);
                maxNum = Integer.parseInt(strSplit[1]);
                incr = 1;
            }
            else if( strSplit.length == 3 ) {
                minNum = Integer.parseInt(strSplit[0]);
                maxNum = Integer.parseInt(strSplit[1]);
                incr = Integer.parseInt(strSplit[2]);
            }
            else
                throw new IllegalArgumentException("Too many ':' separators in range.");

            List<Integer> configList = new ArrayList<Integer>();
            for( int i = minNum; i <= maxNum; i += incr )
                configList.add(i);

            int[] result = new int[configList.size()];
            for( int i = 0; i != result.length; ++i )
                result[i] = configList.get(i);
            return result;
        }
        // comma-separated list
        else if( rangeConfig.contains(",") ) {
            String[] split = rangeConfig.split(",");
            int[] result = new int[split.length];
            for( int i = 0; i != split.length; ++i )
                result[i] = Integer.parseInt(split[i]);

            return result;
        }
        // single numerical value
        else
            return new int[] { Integer.parseInt(rangeConfig) };
    }
}

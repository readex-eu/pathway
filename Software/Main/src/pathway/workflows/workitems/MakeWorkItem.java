package pathway.workflows.workitems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.eclipse.jdt.annotation.NonNullByDefault;
import pathway.data.DataUtils;
import pathway.data.persistence.Application;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.Tool;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.ConnectionManager;
import pathway.eclipse.remotetools.FileUtils;
import pathway.eclipse.remotetools.ProcUtils;
import pathway.eclipse.remotetools.ProcessResult;
import pathway.workflows.WorkflowErrorException;




/**
 * Runs "make" in the configured source directory, in order to build the application. The selected performance tool and environment variables are passed to the
 * Makefile.
 */
public class MakeWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        BatchSystemManager batchSysManager = (BatchSystemManager)workItem.getParameter("BatchSysManager");
        if( batchSysManager == null )
            throw new IllegalArgumentException("The batch system manager parameter has not been set.");

        Experiment experiment = (Experiment)workItem.getParameter("Experiment");
        Collection<Experiment> experiments = (Collection<Experiment>)workItem.getParameter("Experiments");
        if( experiment == null && experiments == null )
            throw new IllegalArgumentException("Both the 'Experiment' and the 'Experiments' parameters have not been set.");

        String makeArguments = (String)workItem.getParameter("MakeArguments");
        makeArguments = makeArguments == null ? "" : makeArguments;

        try {
            if( experiment != null )
                impl(batchSysManager, experiment, makeArguments);
            if( experiments != null )
                for( Experiment item: experiments )
                    impl(batchSysManager, item, makeArguments);

            return null;
        }
        catch( WorkflowErrorException ex ) {
            throw ex;
        }
        catch( Exception ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("An error occurred during instrumentation.", ex);
            throw new WorkflowErrorException();
        }
    }


    @NonNullByDefault
    private static void impl(BatchSystemManager batchSysManager, Experiment experiment, String makeArguments) throws Exception {
        final Application app = experiment.getApplication();
        final Tool perfTool = experiment.getTool();

        // check if code location is given
        String codeLocation = app.getCodeLocation();
        if( codeLocation == null || codeLocation.length() == 0 ) {
            String message = "Code location is not configured for the application.";
            ExecutionLog.get().writeErr(message);
            UIUtils.showErrorMessage(message);
            throw new WorkflowErrorException();
        }

        // check if exe location is given
        String exeLocation = app.getExecLocation();
        if( exeLocation == null || exeLocation.length() == 0 ) {
            String message = "Executable location is not configured for the application.";
            ExecutionLog.get().writeErr(message);
            UIUtils.showErrorMessage(message);
            throw new WorkflowErrorException();
        }

        // determine the name of the executable to build
        String exeName = app.getExecutable();
        exeName = Helper.fillPlaceholders(exeName, experiment);

        // run a remote make command to build the executable with the specified tool
        ConnectionManager connMngr = batchSysManager.getConnectionManager();
        String buildScript = createBuildScript(connMngr, codeLocation, Helper.fillPlaceholders(makeArguments, experiment), perfTool);
        Map<String, String> compileEnv = addEnvironmentVariables(experiment, app, perfTool);
        //ExecutionLog.get().writeLog("MakeWorkItem: " + compileEnv);
        ProcessResult makeOutput = ProcUtils.runProcess(connMngr, codeLocation, compileEnv, "sh", "-l", buildScript);
        experiment.setCompileLog(makeOutput.stdout + makeOutput.stderr);
        experiment.setExecCmd(FileUtils.combinePath(exeLocation, exeName));

        // check the result of the make
        if( makeOutput.status != 0 ) {
            ExecutionLog.get().writeErr("Application build failed!\n");
            ExecutionLog.get().writeErr("stdout:\n" + makeOutput.stdout);
            ExecutionLog.get().writeErr("stderr:\n" + makeOutput.stderr);
            ExecutionLog.get().writeErr("\n\n buildScript:\n" + buildScript);
            UIUtils.showErrorMessage("The build script has failed (return value is non-zero). See the console log for more details.");
            FileUtils.deleteResource(connMngr, buildScript);
            throw new WorkflowErrorException();
        }

        ExecutionLog.get().writeLog("Application was built successfully.");
     //   FileUtils.deleteResource(connMngr, buildScript);
    }


    @NonNullByDefault
    private static Map<String, String> addEnvironmentVariables(Experiment experiment, Application app, Tool perfTool) {
        // add the environment variables required by the instrumentation tool
        Map<String, String> compileEnv = new HashMap<String, String>();
        if( perfTool.getInstrumentCMD() != null && perfTool.getInstrumentCMD().length() > 0 ) {
            compileEnv.put("PREP", perfTool.getInstrumentCMD());

            Map<String, String> toolEnvVars = DataUtils.EnvironmentVars2Map(perfTool.getReqEnvVars());
            for( String varName: toolEnvVars.keySet() ) {
                String value = Helper.fillPlaceholders(toolEnvVars.get(varName), experiment);
                toolEnvVars.put(varName, value);
            }

            compileEnv.putAll(toolEnvVars);
        }

        // add the environment variables required by the application
        Map<String, String> appEnvVars = DataUtils.EnvironmentVars2Map(app.getReqEnvVars());
        for( String varName: appEnvVars.keySet() ) {
            String value = Helper.fillPlaceholders(appEnvVars.get(varName), experiment);
            appEnvVars.put(varName, value);
        }
        compileEnv.putAll(appEnvVars);

        // add the environment variables explicitly defined in the experiment
        Map<String, String> expEnvVars = DataUtils.EnvironmentVars2Map(experiment.getEnvironment());
        for( String varName: expEnvVars.keySet() ) {
            String value = Helper.fillPlaceholders(expEnvVars.get(varName), experiment);
            expEnvVars.put(varName, value);
        }
        compileEnv.putAll(expEnvVars);
        return compileEnv;
    }


    /** Creates the build script file on the HPC system. */
    @NonNullByDefault
    private static String createBuildScript(ConnectionManager connMngr, String codeLocation, String makeArguments, Tool tool) throws IOException {
        String script = generateBuildScript(makeArguments, tool);

        File local = File.createTempFile("pathway_build.", ".sh");
        Writer out = new OutputStreamWriter(new FileOutputStream(local));
        try {
            out.write(script);
        }
        finally {
            out.close();
        }

        String remote = FileUtils.combinePath(codeLocation, local.getName());
        FileUtils.copyResourceToRemote(connMngr, local.getAbsolutePath(), remote, true, null);
        local.delete();

        return remote;
    }


    /** Generates the build script that will execute the "make" command. */
    @NonNullByDefault
    private static String generateBuildScript(String makeArguments, Tool tool) {
        // create a standard shell script
        StringBuilder script = new StringBuilder();
        script.append("#!/bin/sh\n");
        /*
         * READEX : removed setting env vars
         */
        //script.append("set -e\n");
        
        // load modules required by the selected performance tool - the modules must already be present at build time
        /*
         * READEX : removed if block to be written in script that checks for modules.sh on Taurus
         */
        //script.append("if [ -f /etc/profile.d/10_modules.sh ]; then\n");
        //script.append("source /etc/profile.d/10_modules.sh\n");
       
        
        /*
         * READEX : the following code corresponds to adding "module use" to the script.
         */
       
        /* 
        String useModules = tool.getUseModules();
        List<String> useModulesList = useModules == null ? new ArrayList<String>() : DataUtils.modulesJson2List(useModules);
        
        for( String modulesPath: useModulesList )
           script.append("module use ").append(modulesPath).append('\n');
        */
        script.append("module use /projects/p_readex/modules").append('\n');
        String requiredModules = tool.getReqModules();
        List<String> modules = requiredModules == null ? new ArrayList<String>() : DataUtils.modulesJson2List(requiredModules);
        for( String module: modules )
            script.append("module load ").append(module).append('\n');
        //script.append("fi\n\n");

        // run make
        /*
         * READEX : Removed -e switch for make command
         */
        // script.append("make -e " + makeArguments + "\n");
        script.append("make " + makeArguments + "\n");

        return script.toString();
    }
}

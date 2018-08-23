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
import java.util.StringTokenizer;

import org.drools.runtime.process.WorkItem;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.remote.core.exception.RemoteConnectionException;
import org.h2.util.StringUtils;
import org.hibernate.criterion.Restrictions;

import pathway.data.DataUtils;
import pathway.data.persistence.ApplicationCriteria;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.Application;
import pathway.data.persistence.Tool;
import pathway.data.persistence.ToolCriteria;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.ConnectionManager;
import pathway.eclipse.remotetools.FileUtils;
import pathway.eclipse.remotetools.ProcUtils;
import pathway.eclipse.remotetools.ProcessResult;
import pathway.workflows.WorkflowErrorException;




/*
 * 1. Add the SCOREP_FILTERING_FILE environment variable to the bullxmpi tool in experiment.
 * 2. Remove existing scorep-xyz directories from previous runs in the application folder.
 * 3. Create an empty scorep.filt file in the application folder.
 * 4. Return updated experiments.
 * */
public class SetupForScorePAutoFilterWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
    	final Map<String, Object> results = new HashMap<String, Object>();
        try {	
        	BatchSystemManager batchSystemManagerObj = getParameter(workItem, "BatchSysManager");
            if( batchSystemManagerObj == null )
                throw new IllegalArgumentException("The work item's batch system manager parameter cannot be a null reference.");

        	String scorePFilterFileName = (String) workItem.getParameter("ScorePFilteringFileName");
        	String filterFileEnvVarName = "SCOREP_FILTERING_FILE";        	
        	String codeLocation = null;
            Experiment experiment = (Experiment)workItem.getParameter("Experiment");
            if( experiment == null )
                throw new IllegalArgumentException("The experiment cannot be a null reference.");

            Map<String,String> reqEnvVarForExperimentMap = new HashMap<String, String>();
        	reqEnvVarForExperimentMap.put(filterFileEnvVarName, scorePFilterFileName); //set SCOREP_FILTERING_FILE to experiment
        	
            experiment.setEnvironment(DataUtils.EnvironmentVars2Str(reqEnvVarForExperimentMap));
            
           // Tool toolObj = experiment.getTool();
           // Map<String,String> reqEnvVarsMap = DataUtils.EnvironmentVars2Map(toolObj.getReqEnvVars());
           // reqEnvVarsMap.put(filterFileEnvVarName, scorePFilterFileName);
           // toolObj.setReqEnvVars(DataUtils.EnvironmentVars2Str(reqEnvVarsMap));
           // experiment.setTool(toolObj);
            
            Application applicationObj = experiment.getApplication();
           	codeLocation = applicationObj.getCodeLocation();

            impl(batchSystemManagerObj, "rm -rf scorep-* && rm -f old_scorep.filt && echo \"\" > scorep.filt", codeLocation);
        	results.put("Experiment", experiment);
        }
        catch( Exception ex ) {
            UIUtils.showErrorMessage("Setup for scorep-autofilter failed. The return value is non-zero. See the console log for more details.");
            throw new WorkflowErrorException();
        }        	
        return results;
    }

    @NonNullByDefault
    private static void impl(BatchSystemManager batchSystemManagerObj, String command, String codeLocation)
            throws IOException, RemoteConnectionException {

        final ConnectionManager connection = batchSystemManagerObj.getConnectionManager();
        if( !connection.getConnection().isOpen() )
            connection.getConnection().open(null);

        ExecutionLog.get().writeLog("Preparing script for SetupForScorePAutoFilter...");
        
        final String scriptFile = createScriptFile(connection, command, codeLocation);
        final ArrayList<String> cmdLine = buildCmdLine(scriptFile);

        ExecutionLog.get().writeLog("Executing script for SetupForScorePAutoFilter...");
        ProcessResult output = ProcUtils.runProcess(connection, codeLocation, null, cmdLine); //execute script remotely
        //FileUtils.deleteResource(connection, scriptFile);

        if( output.status != 0 ) {
            ExecutionLog.get().writeErr("Script execution failed for SetupForScorePAutoFilter!");
            ExecutionLog.get().writeErr(output.stdout);
            ExecutionLog.get().writeErr(output.stderr);
            throw new WorkflowErrorException();
        }

        ExecutionLog.get().writeLog("Script executed successfully for SetupForScorePAutoFilter.");
        FileUtils.deleteResource(connection, scriptFile);
    }

    @NonNullByDefault
    private static ArrayList<String> buildCmdLine(String scriptFile) {
        final ArrayList<String> cmdLine = new ArrayList<String>();
        cmdLine.add("sh");
        //cmdLine.add("-l");
        cmdLine.add(scriptFile);

        return cmdLine;
    }


    @NonNullByDefault
    private static String createScriptFile(ConnectionManager connection, String command, String codeLocation) throws IOException {
    	// create local script file
        StringBuilder script = new StringBuilder();
        script.append("#!/bin/sh");
        script.append("\n");
        script.append(command);        
        script.append("\n");
 
        File localFile = File.createTempFile("pathway_build.", ".sh");
        Writer outStream = new OutputStreamWriter(new FileOutputStream(localFile));
        try {
            outStream.write(script.toString());
        }
        finally {
            outStream.close();
        }

        String remoteFile = FileUtils.combinePath(codeLocation, localFile.getName());
        FileUtils.copyResourceToRemote(connection, localFile.getAbsolutePath(), remoteFile, true, null);
        localFile.delete();

        return remoteFile;
    }
        
}
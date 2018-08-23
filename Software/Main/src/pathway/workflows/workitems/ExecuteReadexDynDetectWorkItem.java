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
import org.eclipse.core.runtime.IProgressMonitor;
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




/** Runs an arbitrary process on the HPC system. */
public class ExecuteReadexDynDetectWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        try {
        	String arg_t = (String) workItem.getParameter("Arg_t");
        	String arg_c = (String) workItem.getParameter("Arg_c");
        	String arg_v = (String) workItem.getParameter("Arg_v");
        	String arg_w = (String) workItem.getParameter("Arg_w");
        	String arg_p = (String) workItem.getParameter("Arg_p");
        	//ExecutionLog.get().writeLog("In ScorePAutoFilter: After getting workItem parameters");
        	
        	BatchSystemManager batchSystemManagerObj = getParameter(workItem, "BatchSysManager");
            if( batchSystemManagerObj == null )
                throw new IllegalArgumentException("The work item's batch system manager parameter cannot be a null reference.");

            Experiment experiment = (Experiment)workItem.getParameter("Experiment");
            String appName = (String)workItem.getParameter("Application");
            Application applicationObj = null;
            if( !appName.contains("|") ) appName += "|default";
            String[] appNameConfig = appName.split("\\|");
            applicationObj = (Application)new ApplicationCriteria().add(Restrictions.eq("name", appNameConfig[0])).add(Restrictions.eq("config", appNameConfig[1])).uniqueResult();
            if( applicationObj == null )
            {
                String message = "The specified application has not been found in the database: " + appName;
                ExecutionLog.get().writeErr(message);
                UIUtils.showErrorMessage(message);
                throw new WorkflowErrorException();
            }
            applicationObj.refresh();
        	String codeLocation = applicationObj.getCodeLocation();
        	String pathToCubexFile = applicationObj.getExecLocation().replaceAll("/+$", "").concat("/scorep-*"); //(String) workItem.getParameter("PathToCubexFile");
        	
            String perfTool = "READEX readex-dyn-detect|1.0";
            Tool perfToolObj = null;
            String[] splitToolNameVer = perfTool.split("\\|");
            ToolCriteria toolsCriteria = new ToolCriteria();
            toolsCriteria.add(Restrictions.eq("version", splitToolNameVer[1]));
            perfToolObj = (Tool)toolsCriteria.add(Restrictions.eq("name", splitToolNameVer[0])).uniqueResult();
            perfToolObj.refresh();
        	String toolCMD = perfToolObj.getProfileCMD();
        	String toolARGS = perfToolObj.getProfileArgs();
        	//add SCOREP_FILTERING_FILE=scorep.filt using perfToolObj.setEnvVars(...)
        	Map<String, String> envVarsMap = DataUtils.EnvironmentVars2Map(perfToolObj.getReqEnvVars());
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#ARG_t#}", arg_t);
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#ARG_c#}", arg_c);
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#ARG_v#}", arg_v);
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#ARG_w#}", arg_w);
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#ARG_p#}", arg_p);
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#PATH_TO_CUBEX_FILE#}", pathToCubexFile);
        	String requiredModules = perfToolObj.getReqModules();
            List<String> modules = requiredModules == null ? new ArrayList<String>() : DataUtils.modulesJson2List(requiredModules);
        	   	  
        	//ExecutionLog.get().writeLog("ReadexDynDetect: " + toolCMD + ", " + toolARGS + ", " + arg_t + ", " + arg_v + ", " + arg_w + ", " + arg_p + ", " + pathToCubexFile + ", " + codeLocation);        	
        	
            impl(batchSystemManagerObj, toolCMD, toolARGS, modules, envVarsMap, codeLocation,data.monitor);
            applicationObj.setTuningPotential("ExecuteRDD: TBD.");
            applicationObj.setInterPhaseDynamism("ExecuteRDD: TBD");
            applicationObj.setIntraPhaseDynamism("ExecuteRDD: TBD.");
            applicationObj.setDTATuningModel("TBD");
            applicationObj.setRATSwitchingDecisions("TBD");
            applicationObj.setCalibratedTuningModel("TBD");
            DataUtils.StoreApplication(applicationObj);
            
            DataUtils.StoreExperiment(experiment);
        }
        catch( Exception ex ) {
            UIUtils.showErrorMessage("Script execution failed. The return value is non-zero. See the console log for more details.");
            throw new WorkflowErrorException();
        }   
        
        return null;
    }


    @NonNullByDefault
    private static void impl(BatchSystemManager batchSystemManagerObj, String toolCMD, String toolARGS, List<String> modules, Map<String, String> envVarsMap, String codeLocation, @Nullable IProgressMonitor monitor)
            throws IOException, RemoteConnectionException {

        final ConnectionManager connection = batchSystemManagerObj.getConnectionManager();
        if( !connection.getConnection().isOpen() )
            connection.getConnection().open(null);

        ExecutionLog.get().writeLog("Preparing script for readex-dyn-detect...");
        
        final String scriptFile = createScriptFile(connection, modules, codeLocation, envVarsMap, toolCMD+" "+toolARGS);
        ExecutionLog.get().writeLog("scriptFile: " + scriptFile);
        final ArrayList<String> cmdLine = buildCmdLine(scriptFile);
        ExecutionLog.get().writeLog("cmdLine: " + cmdLine.toString());

        ExecutionLog.get().writeLog("Executing script for readex-dyn-detect...");
        ProcessResult output = ProcUtils.runProcess(connection, codeLocation, null, cmdLine);
        FileUtils.deleteResource(connection, scriptFile);

        if( output.status != 0 ) {
            ExecutionLog.get().writeErr("Script execution for readex-dyn-detect failed!");
            ExecutionLog.get().writeErr(output.stdout);
            ExecutionLog.get().writeErr(output.stderr);
            throw new WorkflowErrorException();
        }

        ExecutionLog.get().writeLog("Script executed for readex-dyn-detect successfully.");
        //FileUtils.deleteResource(connection, scriptFile);
        
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
    private static String createScriptFile(ConnectionManager connection, List<String> modules, String codeLocation, Map<String, String> envVarsMap, String command) throws IOException {
    	// create local script file
        StringBuilder script = new StringBuilder();
        script.append("#!/bin/sh");
        script.append("\n");
        script.append("module use /projects/p_readex/modules").append("\n");
        for( String module: modules )
            script.append("module load ").append(module).append("\n");
        
        for(Map.Entry<String, String> entry : envVarsMap.entrySet())
        {
        	script.append("export " + entry.getKey() + "=" + entry.getValue());
        	script.append("\n");
        }
        
        script.append("\n");
        script.append(command);
        script.append("\n");
        script.append("rm -rf scorep-* && rm -f *.filt");
        script.append("\n");

        File localFile = File.createTempFile("pathway_rdd.", ".sh");
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

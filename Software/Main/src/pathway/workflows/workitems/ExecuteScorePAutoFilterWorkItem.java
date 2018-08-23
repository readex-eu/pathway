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
 * 1. Load required modules.
 * 2. Export environment variables (PATH).
 * 3. Make a copy of scorep.filt to old_scorep.filt.
 * 4. Run scorep-autofilter.
 * 5. Remove scorep-xyz directories.
 * 6. Difference between scorep.filt and old_scorep.filt.
 * 7. Return difference result.
 *  */
public class ExecuteScorePAutoFilterWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
    	final Map<String, Object> results = new HashMap<String, Object>();
        try {
        	
        	String arg_t = (String) workItem.getParameter("Arg_t");
        	String maxFilterIterations = (String) workItem.getParameter("MaxFilterIterations");
        	
        	BatchSystemManager batchSystemManagerObj = getParameter(workItem, "BatchSysManager");
            if( batchSystemManagerObj == null )
                throw new IllegalArgumentException("The work item's batch system manager parameter cannot be a null reference.");
            
            // Get application directory from the application object.
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

            // Get toolCMD, toolARGS and required modules to be loaded from the scorep-autofilter perfTool that was selected.
            String perfTool = "READEX scorep-autofilter|1.0";
            Tool perfToolObj = null;
            String[] splitToolNameVer = perfTool.split("\\|");
            ToolCriteria toolsCriteria = new ToolCriteria();
            toolsCriteria.add(Restrictions.eq("version", splitToolNameVer[1]));
            perfToolObj = (Tool)toolsCriteria.add(Restrictions.eq("name", splitToolNameVer[0])).uniqueResult();
            perfToolObj.refresh();
        	String toolCMD = perfToolObj.getProfileCMD();
        	String toolARGS = perfToolObj.getProfileArgs();
        	
        	
        	Map<String, String> envVarsMap = DataUtils.EnvironmentVars2Map(perfToolObj.getReqEnvVars());
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#ARG_t#}", arg_t);
        	toolARGS = StringUtils.replaceAll(toolARGS, "{#PATH_TO_CUBEX_FILE#}", pathToCubexFile);
        	String requiredModules = perfToolObj.getReqModules();
            List<String> modules = requiredModules == null ? new ArrayList<String>() : DataUtils.modulesJson2List(requiredModules);
        	
            ExecutionLog.get().writeLog("ScorePAutoFilter: " + toolCMD + ", " + toolARGS + ", " + arg_t + ", " + pathToCubexFile + ", " + codeLocation);        	
            String continueFiltering = impl(batchSystemManagerObj, toolCMD, toolARGS, modules, envVarsMap, codeLocation, maxFilterIterations);
            results.put("ContinueFiltering", continueFiltering);
        }
        catch( Exception ex ) {
            UIUtils.showErrorMessage("Script execution failed. The return value is non-zero. See the console log for more details.");
            throw new WorkflowErrorException();
        }        	
        return results;
    }


    @NonNullByDefault
    private static String impl(BatchSystemManager batchSystemManagerObj, String toolCMD, String toolARGS, List<String> modules, Map<String, String> envVarsMap, String codeLocation, String maxFilterIterations)
            throws IOException, RemoteConnectionException {

        final ConnectionManager connection = batchSystemManagerObj.getConnectionManager();
        if( !connection.getConnection().isOpen() )
            connection.getConnection().open(null);

        ExecutionLog.get().writeLog("Preparing script for scorep-autofilter...");
        
        final String scriptFile = createScriptFile(connection, codeLocation, modules, envVarsMap, toolCMD+" "+toolARGS, maxFilterIterations);
        ExecutionLog.get().writeLog("scriptFile: " + scriptFile);
        final ArrayList<String> cmdLine = buildCmdLine(scriptFile);
        ExecutionLog.get().writeLog("cmdLine: " + cmdLine.toString());

        ExecutionLog.get().writeLog("Executing script for scorep-autofilter...");
        ProcessResult output = ProcUtils.runProcess(connection, codeLocation, null, cmdLine);

        ExecutionLog.get().writeLog("Script executed successfully. (continueFiltering: " + output.status + ")");
        if(output.status == 0)
        {
        	ExecutionLog.get().writeLog("scorep-autofilter: Filtering Done.");
            FileUtils.deleteResource(connection, scriptFile);
        	return "FilteringDone"; // Filtering Done.
        }
        else if(output.status == 1)
        {
        	ExecutionLog.get().writeLog("scorep-autofilter: Continue Filtering...");
            FileUtils.deleteResource(connection, scriptFile);
        	return "ContinueFiltering"; // More Filtering.
        }
        else
        {
            FileUtils.deleteResource(connection, scriptFile);
        	ExecutionLog.get().writeErr("Script execution failed!");
        	ExecutionLog.get().writeErr(output.stdout);
        	ExecutionLog.get().writeErr(output.stderr);
        	throw new WorkflowErrorException();
        }
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
    private static String createScriptFile(ConnectionManager connection, String codeLocation, List<String> modules, Map<String, String> envVarsMap, String command, String maxFilterIterations) throws IOException {
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
       	script.append("cp scorep.filt old_scorep.filt").append("\n");
        script.append(command + " 1>/dev/null").append("\n");
        script.append("rm -rf scorep-*").append("\n\n");
        script.append("commString=$(comm -1 -2 scorep.filt old_scorep.filt)").append("\n");
        script.append("if [ \"$commString\" == \"\" ]; then").append("\n");
        script.append("  cp scorep.filt old_scorep.filt").append("\n");
        script.append("  exit 1").append("\n"); //exit code = 1 because first iteration.
        script.append("else").append("\n");
        script.append("  filtFileBeginString=\"SCOREP_REGION_NAMES_BEGIN\"").append("\n");
        script.append("  filtFileEndString=\"SCOREP_REGION_NAMES_END\"").append("\n");
        script.append("  filtFileExcludeString=\"EXCLUDE\"").append("\n\n");        
        script.append("  sort scorep.filt > sorted_scorep.filt").append("\n");
        script.append("  sort old_scorep.filt > sorted_old_scorep.filt").append("\n");
        script.append("  commString=$(comm -2 -3 sorted_scorep.filt sorted_old_scorep.filt)").append("\n\n");
        script.append("  if [ \"$commString\" != \"\" ]; then").append("\n");
        script.append("    echo $filtFileBeginString > new_scorep.filt").append("\n");
        script.append("    echo $filtFileExcludeString >> new_scorep.filt").append("\n");
        script.append("    comm -2 -3 sorted_scorep.filt sorted_old_scorep.filt > comm1.txt").append("\n\n");
        script.append("    exec < \"old_scorep.filt\"").append("\n");
        script.append("    while read line; do").append("\n");
        script.append("      if [ \"$line\" != \"$filtFileEndString\" ] && [ \"$line\" != \"$filtFileBeginString\" ] && [ \"$line\" != \"$filtFileExcludeString\" ]; then").append("\n");
        script.append("        echo $line >> new_scorep.filt").append("\n");
        script.append("      fi").append("\n");
        script.append("    done").append("\n\n");
        script.append("    cat comm1.txt >> new_scorep.filt").append("\n");
        script.append("    echo $filtFileEndString >> new_scorep.filt").append("\n");
        script.append("    mv new_scorep.filt scorep.filt").append("\n");
        script.append("    rm -f sorted_scorep.filt sorted_old_scorep.filt comm1.txt").append("\n");
        script.append("    exit 1").append("\n");
        script.append("  else").append("\n");
        script.append("    mv old_scorep.filt scorep.filt").append("\n");
        script.append("    rm -f sorted_scorep.filt sorted_old_scorep.filt comm1.txt").append("\n");
        script.append("    exit 0").append("\n");
        script.append("  fi").append("\n");        
        script.append("fi").append("\n");

        File localFile = File.createTempFile("pathway_saf.", ".sh");
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

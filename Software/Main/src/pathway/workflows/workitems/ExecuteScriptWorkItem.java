package pathway.workflows.workitems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;
import org.drools.runtime.process.WorkItem;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.remote.core.exception.RemoteConnectionException;
import pathway.data.persistence.Experiment;
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
public class ExecuteScriptWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        try {
            BatchSystemManager mgr = getParameter(workItem, "BatchSysManager");
            String script = getParameter(workItem, "Script");
            if( script.isEmpty() )
                throw new IllegalArgumentException("The script cannot be empty.");

            String arguments = (String)workItem.getParameter("Arguments");
            if( arguments == null )
                arguments = "";

            Experiment experiment = null;
            String workingDir = (String)workItem.getParameter("WorkingDir");
            if( workingDir == null || workingDir.isEmpty() ) {
                experiment = (Experiment)workItem.getParameter("Experiment");
                if( experiment == null ) {
                    Collection<Experiment> experiments = (Collection<Experiment>)workItem.getParameter("Experiments");
                    if( experiments != null && !experiments.isEmpty() ) {
                        experiment = experiments.iterator().next();
                        workingDir = experiment.getApplication().getExecLocation();
                    }
                }
                else
                    workingDir = experiment.getApplication().getExecLocation();
            }

            impl(mgr, script, arguments, experiment, workingDir);
        }
        catch( Exception ex ) {
            UIUtils.showErrorMessage("Script execution failed. The return value is non-zero. See the console log for more details.");
            throw new WorkflowErrorException();
        }

        return null;
    }


    @NonNullByDefault
    private static void impl(BatchSystemManager mgr, String script, String arguments, @Nullable Experiment experiment, @Nullable String workingDir)
            throws IOException, RemoteConnectionException {

        final ConnectionManager connection = mgr.getConnectionManager();
        if( !connection.getConnection().isOpen() )
            connection.getConnection().open(null);

        ExecutionLog.get().writeLog("Preparing script...");
        if( experiment != null )
            script = Helper.fillPlaceholders(script, experiment);

        final String cwd = determineWorkingDir(connection, experiment, workingDir);
        final String scriptFile = createScriptFile(connection, cwd, script);
        final ArrayList<String> cmdLine = buildCmdLine(scriptFile, arguments);

        ExecutionLog.get().writeLog("Executing script...");
  
        ProcessResult output = ProcUtils.runProcess(connection, cwd, null, cmdLine);
        FileUtils.deleteResource(connection, scriptFile);

        if( output.status != 0 ) {
            ExecutionLog.get().writeErr("Script execution failed!");
            ExecutionLog.get().writeErr(output.stdout);
            ExecutionLog.get().writeErr(output.stderr);
            throw new WorkflowErrorException();
        }

        ExecutionLog.get().writeLog("Script executed successfully.");   
    }


    @NonNullByDefault
    private static String determineWorkingDir(ConnectionManager connection, @Nullable Experiment experiment, @Nullable String workingDir) {
        if( workingDir == null )
            return connection.getCWD();

        return experiment == null ? workingDir : Helper.fillPlaceholders(workingDir, experiment);
    }


    @NonNullByDefault
    private static ArrayList<String> buildCmdLine(String scriptFile, String arguments) {
        final ArrayList<String> cmdLine = new ArrayList<String>();
        cmdLine.add("sh");
        //cmdLine.add("-l");
        cmdLine.add(scriptFile);

        final StringTokenizer tokenizer = new StringTokenizer(arguments, " ");
        while( tokenizer.hasMoreTokens() )
            cmdLine.add(tokenizer.nextToken());

        return cmdLine;
    }


    @NonNullByDefault
    private static String createScriptFile(ConnectionManager connection, String workingDir, String script) throws IOException {
        // create local script file
        File local = File.createTempFile("pathway_temp_script.", ".sh");
        Writer out = new OutputStreamWriter(new FileOutputStream(local));
        try {
            out.write(script);
        }
        finally {
            out.close();
        }

        // move file to HPC system
        String remote = FileUtils.combinePath(workingDir, local.getName());
        FileUtils.copyResourceToRemote(connection, local.getAbsolutePath(), remote, true, null);
        local.delete();

        // return remote file path
        return remote;
    }
}

package pathway.workflows.workitems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import pathway.eclipse.remotetools.ProcUtils;
import pathway.eclipse.remotetools.ProcessResult;
import pathway.workflows.WorkflowErrorException;




/** Runs an arbitrary process on the HPC system. */
public class ExecuteCommandWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        final Map<String, Object> results = new HashMap<String, Object>();
        BatchSystemManager mgr = getParameter(workItem, "BatchSysManager");
        String command = getParameter(workItem, "Command");
        if( command.isEmpty() )
            throw new IllegalArgumentException("The command cannot be empty.");

        String workingDir = (String)workItem.getParameter("WorkingDir");
        if( workingDir == null ) {
            Experiment experiment = (Experiment)workItem.getParameter("Experiment");
            if( experiment == null ) {
                Collection<Experiment> experiments = (Collection<Experiment>)workItem.getParameter("Experiments");
                if( experiments != null && !experiments.isEmpty() )
                    workingDir = experiments.iterator().next().getApplication().getExecLocation();
            }
            else
                workingDir = experiment.getApplication().getExecLocation();
        }

        try {
            String stdout = impl(mgr, command, workingDir);
            results.put("Stdout", stdout);
        }
        catch( Exception ex ) {
            UIUtils.showErrorMessage("Command execution failed. The return value is non-zero. See the console log for more details.");
            throw new WorkflowErrorException();
        }

        return results;
    }


    @NonNullByDefault
    private static String impl(BatchSystemManager mgr, String command, @Nullable String workingDir) throws IOException, RemoteConnectionException {
        final ConnectionManager connection = mgr.getConnectionManager();
        if( !connection.getConnection().isOpen() )
            connection.getConnection().open(null);

        ExecutionLog.get().writeLog("Executing command...");
        final ArrayList<String> cmdLine = buildCmdLine(command);
        final String cwd = workingDir == null ? connection.getCWD() : workingDir;
        ProcessResult output = ProcUtils.runProcess(connection, cwd, null, cmdLine);

        if( output.status != 0 ) {
            ExecutionLog.get().writeErr("Command execution failed!");
            ExecutionLog.get().writeErr(output.stdout);
            ExecutionLog.get().writeErr(output.stderr);
            throw new WorkflowErrorException();
        }

        ExecutionLog.get().writeLog("Command executed successfully.");
        return output.stdout;
    }


    @NonNullByDefault
    private static ArrayList<String> buildCmdLine(String command) {
        final ArrayList<String> cmdLine = new ArrayList<String>();
        final StringTokenizer tokenizer = new StringTokenizer(command, " ");
        while( tokenizer.hasMoreTokens() )
            cmdLine.add(tokenizer.nextToken());

        return cmdLine;
    }
}

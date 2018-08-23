package pathway.workflows.workitems;

import java.util.HashMap;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import pathway.eclipse.WorkflowInstanceData;
import pathway.workflows.WorkflowUserAbortException;




/** Displays to dialogs in order to ask for the number of MPI processes and the number of OpenMP threads to use. */
public class ProcThreadsConfigWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        AskConfigRunnable runnable = new AskConfigRunnable();
        runnable.answerMPI = data.defaultMPI;
        runnable.answerOMP = data.defaultOMP;

        PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
        if( runnable.answerMPI == null || runnable.answerOMP == null )
            throw new WorkflowUserAbortException();

        Map<String, Object> results = new HashMap<String, Object>();
        results.put("MPIProcs", runnable.answerMPI);
        results.put("OMPThreads", runnable.answerOMP);
        return results;
    }


    private static class AskConfigRunnable implements Runnable {
        @Override
        public void run() {
            Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            if( answerMPI == null ) {
                InputDialog mpiProcsDlg = new InputDialog(
                        activeShell,
                        "MPI configuration",
                        "Please enter the number of MPI processes you want to use, given in one of the following formats:\n• a single number\n• a comma-separated list\n• min:max:incr",
                        "1", null);
                if( mpiProcsDlg.open() == Window.OK )
                    answerMPI = mpiProcsDlg.getValue();
                else
                    return;
            }

            if( answerOMP == null ) {
                InputDialog ompThreadsDlg = new InputDialog(
                        activeShell,
                        "OpenMP configuration",
                        "Please enter the number of OMP threads you want to use, given in one of the following formats:\n• a single number\n• a comma-separated list\n• min:max:incr",
                        "1", null);
                if( ompThreadsDlg.open() == Window.OK )
                    answerOMP = ompThreadsDlg.getValue();
            }
        }


        public String answerMPI;
        public String answerOMP;
    }
}

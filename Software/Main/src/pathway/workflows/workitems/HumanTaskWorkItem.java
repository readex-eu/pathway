package pathway.workflows.workitems;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import pathway.PAThWayPlugin;
import pathway.eclipse.ExecutionLog;
import pathway.workflows.WorkflowUserAbortException;




/** Represents a task that has to be executed by the human operator of the workflow. */
public class HumanTaskWorkItem implements WorkItemHandler {
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        final String name = (String)workItem.getParameter("TaskName");
        final String comment = (String)workItem.getParameter("Comment");

        ExecutionLog.get().writeLog("Starting human task: " + name);
        DialogRunnable runnable = new DialogRunnable(name, comment);
        PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
        if( runnable.answer == 1 )
            throw new WorkflowUserAbortException();

        manager.completeWorkItem(workItem.getId(), null);
    }


    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        manager.abortWorkItem(workItem.getId());
    }


    private static class DialogRunnable implements Runnable {
        public DialogRunnable(String titleMsg, String description) {
            this.titleMsg = titleMsg;
            this.description = description;
        }


        @Override
        public void run() {
            MessageDialog dialog = new MessageDialog(PAThWayPlugin.getShell(), titleMsg, null, description, MessageDialog.INFORMATION, buttons, 0);
            answer = dialog.open();
        }


        private final String titleMsg;
        private final String description;
        private int answer;

        private static final String[] buttons = new String[] { "Done!", "Abort the workflow" };
    }
}

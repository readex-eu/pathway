package pathway.workflows.workitems;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import pathway.PAThWayPlugin;
import pathway.eclipse.ExecutionLog;




/** Represents a task that reports something of interest to the user. */
public class ReportWorkItem implements WorkItemHandler {
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        final String title = (String)workItem.getParameter("Title");
        final String contents = (String)workItem.getParameter("Contents");

        DialogRunnable runnable = new DialogRunnable(title, contents);
        PlatformUI.getWorkbench().getDisplay().syncExec(runnable);

        manager.completeWorkItem(workItem.getId(), null);
    }


    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        manager.abortWorkItem(workItem.getId());
    }


    private static class DialogRunnable implements Runnable {
        public DialogRunnable(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }


        @Override
        public void run() {
            ExecutionLog.get().writeLog("\n" + title + ":\n" + contents);
            MessageDialog.openInformation(PAThWayPlugin.getShell(), title, contents);
        }


        private final String title;
        private final String contents;
    }
}

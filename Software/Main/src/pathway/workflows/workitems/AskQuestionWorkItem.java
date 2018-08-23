package pathway.workflows.workitems;

import java.util.HashMap;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import pathway.eclipse.ExecutionLog;
import pathway.workflows.WorkflowUserAbortException;




public class AskQuestionWorkItem implements WorkItemHandler {
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        final String question = (String)workItem.getParameter("Question");
        final String defValue = (String)workItem.getParameter("DefaultValue");
        final String titleMsg = (String)workItem.getParameter("Title");

        QuestionRunnable runnable = new QuestionRunnable(titleMsg, question, defValue);
        PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
        if( runnable.answer == null )
            throw new WorkflowUserAbortException();

        Map<String, Object> results = new HashMap<String, Object>();
        results.put("Answer", runnable.getAnswer());
        ExecutionLog.get().writeLog(question + " -> " + runnable.getAnswer());
        manager.completeWorkItem(workItem.getId(), results);
    }


    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        manager.abortWorkItem(workItem.getId());
    }


    private static class QuestionRunnable implements Runnable {
        public QuestionRunnable(String titleMsg, String question, String defValue) {
            this.titleMsg = titleMsg;
            this.question = question;
            this.defValue = defValue;
        }


        public String getAnswer() {
            return answer;
        }


        @Override
        public void run() {
            Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            InputDialog dialog = new InputDialog(activeShell, titleMsg, question, defValue, null);
            if( dialog.open() == Window.OK )
                answer = dialog.getValue();
        }


        private final String titleMsg;
        private final String question;
        private final String defValue;
        private String answer;
    }
}

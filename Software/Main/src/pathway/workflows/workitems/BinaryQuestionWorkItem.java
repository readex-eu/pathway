package pathway.workflows.workitems;

import java.util.HashMap;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import pathway.eclipse.ExecutionLog;




public class BinaryQuestionWorkItem implements WorkItemHandler {
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        final String question = (String)workItem.getParameter("Question");
        final String titleMsg = (String)workItem.getParameter("Title");

        QuestionRunnable runnable = new QuestionRunnable(titleMsg, question);
        PlatformUI.getWorkbench().getDisplay().syncExec(runnable);

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
        public QuestionRunnable(String titleMsg, String question) {
            this.titleMsg = titleMsg;
            this.question = question;
        }


        public boolean getAnswer() {
            return answer;
        }


        @Override
        public void run() {
            Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            answer = MessageDialog.open(MessageDialog.QUESTION, activeShell, titleMsg, question, SWT.NONE);
        }


        private final String titleMsg;
        private final String question;
        private boolean answer;
    }
}

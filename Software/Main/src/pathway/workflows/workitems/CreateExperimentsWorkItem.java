package pathway.workflows.workitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.drools.runtime.process.WorkItem;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import pathway.data.persistence.Experiment;
import pathway.eclipse.WorkflowInstanceData;
import pathway.utils.PrepareExperimentsList;
import pathway.workflows.WorkflowUserAbortException;




public class CreateExperimentsWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        Object appObj = getParameter(workItem, "Application");
        Object hpcSystemObj = getParameter(workItem, "HPCSystem");
        String perfToolObj = (String)workItem.getParameter("PerfTool");
        List<String> envVarGroups = (List<String>)workItem.getParameter("EnvVarGroups");
        String mpiProcs = getParameter(workItem, "MPIProcs");
        String ompThreads = getParameter(workItem, "OMPThreads");
        String askConfirmation = (String)workItem.getParameter("AskConfirmation");
        String description = (String)workItem.getParameter("Description");

        if( envVarGroups == null )
            envVarGroups = new ArrayList<String>();
     
        final Map<String, Object> results = new HashMap<String, Object>();
        final String configOK = confirmConfig(appObj, hpcSystemObj, perfToolObj, envVarGroups, mpiProcs, ompThreads, askConfirmation);
        if( configOK.equals(DIALOG_ANSWER_QUIT) )
            throw new WorkflowUserAbortException();
        if( configOK.equalsIgnoreCase(DIALOG_ANSWER_YES) ) {
            List<Experiment> experiments = createExperiments(appObj, hpcSystemObj, perfToolObj, envVarGroups, mpiProcs, ompThreads, description);
            for( Experiment experiment: experiments )
                storeExperimentWithStatus(experiment, "Not started");
            
            results.put("Experiments", experiments);
        }
        
        results.put("ConfigOK", configOK);
        return results;
    }


    @NonNullByDefault
    private static String confirmConfig(Object appObj, Object hpcSystemObj, Object perfToolObj, List<String> envVarGroups, String mpiProcs, String ompThreads) {
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Is the following configuration correct:\n").append("Application: ");

        if( appObj instanceof List )
            strBuilder.append(StringUtils.join((List<?>)appObj, ", "));
        else
            strBuilder.append((String)appObj);

        strBuilder.append("\n").append("HPC System: ");
        if( hpcSystemObj instanceof List )
            strBuilder.append(StringUtils.join((List<?>)hpcSystemObj, ", "));
        else
            strBuilder.append((String)hpcSystemObj);

        strBuilder.append("\n").append("Perf Tool: ");
        if( perfToolObj instanceof List )
            strBuilder.append(StringUtils.join((List<?>)perfToolObj, ", "));
        else
            strBuilder.append((String)perfToolObj);

        strBuilder.append("\n").append("MPI Procs: ").append(mpiProcs).append("\n").append("OMP Threads: ").append(ompThreads).append("\n")
                .append("Env Vars: " + ArrayUtils.toString(envVarGroups)).append("\n");

        ConfirmDialogRunnable dialog = new ConfirmDialogRunnable(strBuilder.toString());
        PlatformUI.getWorkbench().getDisplay().syncExec(dialog);
        return dialog.getAnswer();
    }


    @NonNullByDefault
    private static String confirmConfig(Object appObj, Object hpcSystemObj, Object perfToolObj, List<String> envVarGroups, String mpiProcs, String ompThreads,
            @Nullable String showDialog) {
        if( showDialog != null && showDialog.equalsIgnoreCase("false") )
            return DIALOG_ANSWER_YES;

        return confirmConfig(appObj, hpcSystemObj, perfToolObj, envVarGroups, mpiProcs, ompThreads);
    }


    @NonNullByDefault
    private static List<Experiment> createExperiments(Object appObj, Object hpcSystemObj, String perfTool, List<String> envVarGroups, String mpiProcs,
            String ompThreads, @Nullable String experimentDescription) {

        String description;
        if( experimentDescription == null || experimentDescription.isEmpty() ) {
            EnterDescriptionRunnable runnable = new EnterDescriptionRunnable();
            PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
            if( runnable.comment == null )
                throw new WorkflowUserAbortException();
            description = runnable.comment;
        }
        else
            description = experimentDescription;

        return PrepareExperimentsList.generateExpiments(appObj, mpiProcs, ompThreads, hpcSystemObj, perfTool, description, envVarGroups);
    }
    
    
    /** Displays the confirmation dialog to the user. */
    private static class ConfirmDialogRunnable implements Runnable {
        @NonNullByDefault
        public ConfirmDialogRunnable(String question) {
            this.question = question;
        }


        public String getAnswer() {
            return answer;
        }


        @Override
        public void run() {
            Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            MessageDialog dlg = new MessageDialog(activeShell, "Confirm configuration", null, question, MessageDialog.QUESTION, options, 0);

            switch( dlg.open() ) {
            case -1: // if the user closes the dialog without clicking any button.
                System.out.println("No selection");
                answer = DIALOG_ANSWER_QUIT;
                break;
            case 0:
                System.out.println("Your selection is: " + options[0]);
                answer = DIALOG_ANSWER_YES;
                break;
            case 1:
                System.out.println("Your selection is: " + options[1]);
                answer = DIALOG_ANSWER_NO;
                break;
            case 2:
                System.out.println("Your selection is: " + options[2]);
                answer = DIALOG_ANSWER_QUIT;
                break;
            }
        }


        private final String question;
        private String answer;

        private static final String[] options = { "Yes", "No", "Terminate the workflow" };
    }


    /** Asks the user to enter a comment for the experiment. */
    private static class EnterDescriptionRunnable implements Runnable {
        @Override
        public void run() {
            Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            InputDialog dialog = new InputDialog(activeShell, "Experiment Comment", "Please provide a short description of the experiment:", "", null);
            if( dialog.open() == Window.OK )
                comment = dialog.getValue();
        }


        public String comment;
    }


    private static final String DIALOG_ANSWER_YES = "YES";
    private static final String DIALOG_ANSWER_NO = "NO";
    private static final String DIALOG_ANSWER_QUIT = "QUIT";
}

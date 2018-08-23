package pathway.eclipse.handlers;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.ProcessInfo;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.javatuples.Pair;
import pathway.PAThWayPlugin;
import pathway.eclipse.ExecuteWorkflowJob;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.Utils;
import pathway.eclipse.views.ExecutionParameters;
import pathway.workflows.WorkflowErrorException;




/** Called, when the user chooses to start workflow execution. */
public class StartWorkflowHandler implements IHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // get the current process definition from the active workbench window
        Pair<ProcessInfo, String> currentProcess = null;
        try {
            currentProcess = getActiveProcessModel();
            if( currentProcess == null )
                return null;
        }
        catch( Exception ex ) {
            UIUtils.showErrorMessage("Unable to determine which process to run: ", ex);
            return null;
        }

        // switch to execution perspective
        try {
            IWorkbench workbench = PlatformUI.getWorkbench();
            workbench.showPerspective(PAThWayPlugin.PERSPECTIVE_EXECUTION, workbench.getActiveWorkbenchWindow());
            UIUtils.activateView(PAThWayPlugin.VIEW_EXECUTION_PARAMETERS);
        }
        catch( WorkbenchException ex ) {
            ex.printStackTrace();
        }

        // start the job
        try {
            StatefulKnowledgeSession ksession = createKnowledgeSession(currentProcess.getValue1());
            ExecutionLog.get().writeLog("Preparing workflow: " + currentProcess.getValue0().getProcessId());
            new ExecuteWorkflowJob(ksession, currentProcess.getValue0(), ExecutionParameters.getHPCSystem(), ExecutionParameters.getApplication(),
                    ExecutionParameters.getTool(), ExecutionParameters.getMPI(), ExecutionParameters.getOMP()).schedule();
        }
        catch( LoadWorkflowException ex ) {
            ExecutionLog.get().writeErr("Unable to start workflow execution!");
            ExecutionLog.get().writeErr(ex);
            UIUtils.showErrorMessage("Unable to start workflow execution:\n\n" + ex.getMessage());
            return null;
        }

        return null;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean isHandled() {
        return true;
    }


    @Override
    public void addHandlerListener(IHandlerListener handlerListener) {
    }


    @Override
    public void removeHandlerListener(IHandlerListener handlerListener) {
    }


    @Override
    public void dispose() {
    }


    @NonNullByDefault
    private StatefulKnowledgeSession createKnowledgeSession(String... processes) throws LoadWorkflowException {
        // create the knowledge builder and load process definitions into it
        ClassLoader classLoader = this.getClass().getClassLoader();
        KnowledgeBaseConfiguration kbaseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration(null, classLoader);
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(kbaseConfig);
        KnowledgeBuilderConfiguration kBuilderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null, classLoader);
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(kBuilderConfiguration);
        for( String p: processes )
            kbuilder.add(ResourceFactory.newFileResource(p), ResourceType.BPMN2);

        // check for errors
        if( kbuilder.hasErrors() )
            for( KnowledgeBuilderError error: kbuilder.getErrors() )
                throw new LoadWorkflowException(error.getMessage());

        // create a session with that knowledge
        knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return knowledgeBase.newStatefulKnowledgeSession();
    }


    private static @Nullable Pair<ProcessInfo, String> getActiveProcessModel() throws Exception {
        IFile active_wf_model = null;
        try {
            active_wf_model = UIUtils.getCurrentEditorContent();
        }
        catch( WorkflowErrorException ex ) {
            UIUtils.showErrorMessage("The current workflow has unsaved changes. Please save the workflow before executing it.");
            return null;
        }

        // currently no editor is opened
        if( active_wf_model == null ) {
            UIUtils.showErrorMessage("No workflow model is currently opened.\nPlease open the one you would like to start.");
            return null;
        }

        // no BPMN model opened
        if( !(active_wf_model.getFileExtension().equalsIgnoreCase("bpmn") || active_wf_model.getFileExtension().equalsIgnoreCase("bpmn2")) ) {
            UIUtils.showErrorMessage("The currently opened editor does not contain a valid workflow model.\nPlease open the one you would like to start.");
            return null;
        }

        // load the model in order to get the process id
        ProcessInfo processDef = null;
        String content = new String(Utils.getResourceContentsAsCharArray(active_wf_model));
        try {
            processDef = DroolsEclipsePlugin.getDefault().parseProcess(content, active_wf_model);
        }
        catch( Exception ex ) {
            UIUtils.showErrorMessage("The process definition cannot be parsed.", ex);
            return null;
        }

        return new Pair<ProcessInfo, String>(processDef, active_wf_model.getLocation().toOSString());
    }
}

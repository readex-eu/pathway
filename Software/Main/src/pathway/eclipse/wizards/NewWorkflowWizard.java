package pathway.eclipse.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;




/** A wizard that creates a new workflow and adds it to the project. */
public class NewWorkflowWizard extends Wizard implements INewWizard {
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle("New workflow");
    }


    @Override
    public void addPages() {
        page1 = new NewWorkflowPage1(data);
        addPage(page1);
        page2 = new NewWorkflowPage2(workbench, selection, data);
        addPage(page2);
    }


    @Override
    public boolean performFinish() {
        page1.finish();
        return page2.finish();
    }


    private final NewWorkflowData data = new NewWorkflowData();
    private IWorkbench workbench;
    private IStructuredSelection selection;
    private NewWorkflowPage1 page1;
    private NewWorkflowPage2 page2;
}

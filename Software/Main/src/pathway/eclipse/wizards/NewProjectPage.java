package pathway.eclipse.wizards;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;




public class NewProjectPage extends WizardNewProjectCreationPage {
    public NewProjectPage() {
        super("createprojectpage");
        setTitle("Create a Pathway project");
        setDescription("Enter a project name.");
    }


    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        setPageComplete(true);
    }


    public boolean finish() {
        return true;
    }
}

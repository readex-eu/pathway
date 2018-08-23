package pathway.eclipse.containers;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;




/** A wizard page that allows the user to add the Pathway class-path container to a project. */
public class PAThWayClasspathContainerPage extends WizardPage implements IClasspathContainerPage {
    public PAThWayClasspathContainerPage() {
        super("Pathway Classpath Container Wizard", "Pathway Classpath Container", null);
        setDescription("A classpath container that adds all required jBPM configuration files and extensions provided by Pathway");
        setPageComplete(true);
    }


    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setFont(parent.getFont());

        new Label(composite, SWT.NONE).setText("This adds all jBPM configurations and customizations provided by Pathway to the current project.");

        setControl(composite);
    }


    @Override
    public boolean finish() {
        return true;
    }


    @Override
    public IClasspathEntry getSelection() {
        return JavaCore.newContainerEntry(PAThWayClasspathContainer.ID);
    }


    @Override
    public void setSelection(IClasspathEntry containerEntry) {
    }
}

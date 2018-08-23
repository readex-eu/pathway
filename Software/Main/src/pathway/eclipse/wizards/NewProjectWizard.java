package pathway.eclipse.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.WorkbenchException;
import pathway.PAThWayPlugin;
import pathway.eclipse.containers.PAThWayClasspathContainer;




public class NewProjectWizard extends Wizard implements INewWizard {
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        addPage(page);
    }


    @Override
    public boolean performFinish() {
        // create the project
        IProject project = page.getProjectHandle();
        try {
            project.create(null);
            project.open(null);
            addJavaNature(project);

            IJavaProject jproject = JavaCore.create(project);
            addClasspathEntries(jproject);
        }
        catch( Exception ex ) {
            ex.printStackTrace();
            deleteProject(project);
            MessageDialog.openError(PAThWayPlugin.getShell(), "Error", "Unable to create Pathway project: " + ex.getMessage());
        }

        // switch to Pathway perspective
        try {
            workbench.showPerspective(PAThWayPlugin.PERSPECTIVE_DEVELOPMENT, workbench.getActiveWorkbenchWindow());
        }
        catch( WorkbenchException ex ) {
            ex.printStackTrace();
        }

        return true;
    }


    private static void addJavaNature(IProject project) throws CoreException {
        IProjectDescription desc = project.getDescription();
        String[] natures = desc.getNatureIds();
        String[] newNatures = new String[natures.length + 1];
        System.arraycopy(natures, 0, newNatures, 0, natures.length);
        newNatures[natures.length] = JavaCore.NATURE_ID;
        desc.setNatureIds(newNatures);
        project.setDescription(desc, null);
    }


    private static void addClasspathEntries(IJavaProject jproject) throws JavaModelException {
        IClasspathEntry jreEntry = PreferenceConstants.getDefaultJRELibrary()[0];
        IClasspathEntry pathwayEntry = JavaCore.newContainerEntry(PAThWayClasspathContainer.ID);

        IClasspathEntry[] newEntries = new IClasspathEntry[] { jreEntry, pathwayEntry };
        jproject.setRawClasspath(newEntries, null);
    }


    private static void deleteProject(IProject project) {
        try {
            project.delete(true, true, null);
        }
        catch( CoreException ex ) {
            ex.printStackTrace();
        }
    }


    private final NewProjectPage page = new NewProjectPage();
    private IWorkbench workbench;
}

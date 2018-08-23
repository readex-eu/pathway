package pathway.eclipse.wizards;

import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;




public class NewWorkflowPage2 extends WizardNewFileCreationPage {
    @NonNullByDefault
    public NewWorkflowPage2(IWorkbench workbench, IStructuredSelection selection, NewWorkflowData data) {
        super("createWorkflowPage", selection);

        this.workbench = workbench;
        this.data = data;
        setTitle("Create workflow");
        setDescription("Create a new workflow");
    }


    @Override
    protected InputStream getInitialContents() {
        String template = "resources/models/wizards/" + data.template + ".bpmn.template";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(template);
        if( stream == null )
            throw new RuntimeException("Resource not found: " + template);

        return stream;
    }


    /** Finishes this wizard page, resulting in the creation of the new workflow file. */
    public boolean finish() {
        if( !fixFilename() )
            return false;
        try {
            IFile newFile = createNewFile();
            if( newFile == null )
                return false;

            IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
            IWorkbenchPage page = dwindow.getActivePage();
            if( page != null )
                IDE.openEditor(page, newFile, true);
        }
        catch( PartInitException ex ) {
            ex.printStackTrace();
            return false;
        }
        catch( Exception ex ) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }


    private boolean fixFilename() {
        String filename = getFileName().trim();
        if( filename.length() == 0 )
            return false;

        if( !filename.endsWith(".bpmn") )
            filename += ".bpmn";

        setFileName(filename);
        return true;
    }


    private final IWorkbench workbench;
    private final NewWorkflowData data;
}

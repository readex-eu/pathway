package pathway.eclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import pathway.PAThWayPlugin;
import pathway.data.beans.ExperimentGroups.GROUP_BY;
import pathway.eclipse.views.ReadexBrowser;
import pathway.eclipse.views.ExperimentBrowser;
import pathway.workflows.WorkflowErrorException;




/** Provides UI helper functions. */
@NonNullByDefault
public class UIUtils {
    /** Shows an error message dialog. */
    public static void showErrorMessage(final String message) {
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                MessageDialog.openError(PAThWayPlugin.getShell(), "Error", message);
            }
        });
    }


    /** Shows an error message dialog. */
    public static void showErrorMessage(String message, Exception ex) {
        showErrorMessage(message + "\n\n" + ex);
    }


    /** Activates the view identified by the view id. */
    public static IViewPart activateView(String viewId) throws PartInitException {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IViewPart result = page.showView(viewId);
        assert result != null;
        return result;
    }


    /** Returns a reference to the specified view, if it is opened. */
    @Nullable
    public static IViewPart findView(String viewId) {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        return page.findView(viewId);
    }


    /** Returns a reference to the specified view. If the view is not available, it will be loaded. */
    public static IViewPart getView(String viewId) throws PartInitException {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IViewPart view = page.findView(viewId);
        return view == null ? activateView(viewId) : view;
    }


    /** Gets the contents of the currently active editor. Throws a WorkflowErrorException, when the workflow has unsaved changes. */
    @Nullable
    public static IFile getCurrentEditorContent() {
        if( PlatformUI.getWorkbench() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null ) {
            IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
            if( activeEditor == null )
                return null;

            if( activeEditor.isDirty() )
                throw new WorkflowErrorException();

            return (IFile)activeEditor.getEditorInput().getAdapter(IFile.class);
        }

        return null;
    }


    /** Opens the browsing perspective and the experiment browser. */
    public static void openBrowsingPerspective() {
        openBrowsingPerspective(null);

    }


    /** Opens the browsing perspective and the experiment browser. */
    public static void openBrowsingPerspective(@Nullable GROUP_BY grouping) {
        try {
            IWorkbench workbench = PlatformUI.getWorkbench();
            workbench.showPerspective(PAThWayPlugin.PERSPECTIVE_BROWSING, workbench.getActiveWorkbenchWindow());
            ExperimentBrowser browser = (ExperimentBrowser)activateView(PAThWayPlugin.VIEW_EXPERIMENT_BROWSER);
            browser.reloadData();

            if( grouping != null )
                browser.setGrouping(grouping);

            
        }
        catch( WorkbenchException ex ) {
            ex.printStackTrace();
            showErrorMessage("Unable to show the experiment browser.", ex);
        }
    }
    
    /** Opens the readex browsing perspective. */
    public static void openReadexBrowsingPerspective() {
        openReadexBrowsingPerspective(null);

    }
    /** Opens the readex browsing perspective. */
    public static void openReadexBrowsingPerspective(@Nullable GROUP_BY grouping) {
        try {
            IWorkbench workbench = PlatformUI.getWorkbench();
            workbench.showPerspective(PAThWayPlugin.PERSPECTIVE_READEX_BROWSING, workbench.getActiveWorkbenchWindow());
            ReadexBrowser browser = (ReadexBrowser)activateView(PAThWayPlugin.VIEW_READEX_BROWSER);
            browser.reloadData();                        
        }
        catch( WorkbenchException ex ) {
            ex.printStackTrace();
            showErrorMessage("Unable to show the experiment browser.", ex);
        }
    }
}

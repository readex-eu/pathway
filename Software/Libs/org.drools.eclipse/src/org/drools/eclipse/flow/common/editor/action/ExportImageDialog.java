/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.eclipse.flow.common.editor.action;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.ide.IDEInternalWorkbenchImages;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.misc.ResourceAndContainerGroup;

public class ExportImageDialog extends TitleAreaDialog {

    private static final String DIALOG_SETTINGS_SECTION = "SaveAsDialogSettings"; //$NON-NLS-1$

    private IFile originalFile = null;

    private String originalName = null;

    private IPath result;

    // widgets
    private ResourceAndContainerGroup resourceGroup;

    private Button okButton;

    /**
     * Image for title area
     */
    private Image dlgTitleImage = null;

    /**
     * Creates a new Save As dialog for no specific file.
     *
     * @param parentShell the parent shell
     */
    public ExportImageDialog(Shell parentShell) {
        super(parentShell);
    }

    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Export PNG image");
    }

    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);
        initializeControls();
        validatePage();
        resourceGroup.setFocus();
        setTitle("Export PNG image");
        dlgTitleImage = IDEInternalWorkbenchImages.getImageDescriptor(
                IDEInternalWorkbenchImages.IMG_DLGBAN_SAVEAS_DLG).createImage();
        setTitleImage(dlgTitleImage);
        setMessage("Export the selected process as a PNG image");
        return contents;
    }

    public boolean close() {
        if (dlgTitleImage != null) {
            dlgTitleImage.dispose();
        }
        return super.close();
    }

    protected void createButtonsForButtonBar(Composite parent) {
        okButton = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }

    protected Control createDialogArea(Composite parent) {
        // top level composite
        Composite parentComposite = (Composite) super.createDialogArea(parent);

        // create a composite with standard margins and spacing
        Composite composite = new Composite(parentComposite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setFont(parentComposite.getFont());

        Listener listener = new Listener() {
            public void handleEvent(Event event) {
                setDialogComplete(validatePage());
            }
        };

        resourceGroup = new ResourceAndContainerGroup(
                composite,
                listener,
                IDEWorkbenchMessages.SaveAsDialog_fileLabel, IDEWorkbenchMessages.SaveAsDialog_file);
        resourceGroup.setAllowExistingResources(true);

        return parentComposite;
    }

    /**
     * Returns the full path entered by the user.
     * <p>
     * Note that the file and container might not exist and would need to be created.
     * See the <code>IFile.create</code> method and the 
     * <code>ContainerGenerator</code> class.
     * </p>
     *
     * @return the path, or <code>null</code> if Cancel was pressed
     */
    public IPath getResult() {
        return result;
    }

    /**
     * Initializes the controls of this dialog.
     */
    private void initializeControls() {
        if (originalFile != null) {
            resourceGroup.setContainerFullPath(originalFile.getParent()
                    .getFullPath());
            String fileName = originalFile.getName();
            int index = fileName.lastIndexOf(".");
            if (index != -1) {
                fileName = fileName.substring(0, index);
            }
            fileName += "-image.png";
            resourceGroup.setResource(fileName);
        } else if (originalName != null) {
            resourceGroup.setResource(originalName);
        }
        setDialogComplete(validatePage());
    }

    /* (non-Javadoc)
     * Method declared on Dialog.
     */
    protected void okPressed() {
        // Get new path.
        IPath path = resourceGroup.getContainerFullPath().append(
                resourceGroup.getResource());

        //If the user does not supply a file extension and if the save 
        //as dialog was provided a default file name append the extension 
        //of the default filename to the new name
        if (path.getFileExtension() == null) {
            if (originalFile != null && originalFile.getFileExtension() != null) {
                path = path.addFileExtension(originalFile.getFileExtension());
            } else if (originalName != null) {
                int pos = originalName.lastIndexOf('.');
                if (++pos > 0 && pos < originalName.length()) {
                    path = path.addFileExtension(originalName.substring(pos));
                }
            }
        }

        // If the path already exists then confirm overwrite.
        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
        if (file.exists()) {
            String[] buttons = new String[] { IDialogConstants.YES_LABEL,
                    IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
            String question = NLS.bind(
                    IDEWorkbenchMessages.SaveAsDialog_overwriteQuestion, path
                            .toString());
            MessageDialog d = new MessageDialog(getShell(),
                    IDEWorkbenchMessages.Question,
                    null, question, MessageDialog.QUESTION, buttons, 0);
            int overwrite = d.open();
            switch (overwrite) {
            case 0: // Yes
                break;
            case 1: // No
                return;
            case 2: // Cancel
            default:
                cancelPressed();
                return;
            }
        }

        // Store path and close.
        result = path;
        close();
    }

    /**
     * Sets the completion state of this dialog and adjusts the enable state of
     * the Ok button accordingly.
     *
     * @param value <code>true</code> if this dialog is compelete, and
     *  <code>false</code> otherwise
     */
    protected void setDialogComplete(boolean value) {
        okButton.setEnabled(value);
    }

    /**
     * Sets the original file to use.
     *
     * @param originalFile the original file
     */
    public void setOriginalFile(IFile originalFile) {
        this.originalFile = originalFile;
    }

    /**
     * Set the original file name to use.
     * Used instead of <code>setOriginalFile</code>
     * when the original resource is not an IFile.
     * Must be called before <code>create</code>.
     * @param originalName default file name
     */
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * Returns whether this page's visual components all contain valid values.
     *
     * @return <code>true</code> if valid, and <code>false</code> otherwise
     */
    private boolean validatePage() {
        if (!resourceGroup.areAllValuesValid()) {
            if (!resourceGroup.getResource().equals("")) { //$NON-NLS-1$
                setErrorMessage(resourceGroup.getProblemMessage());
            } else {
                setErrorMessage(null);
            }
            return false;
        }
        
        String resourceName = resourceGroup.getResource();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        
        // Do not allow a closed project to be selected
        IPath fullPath = resourceGroup.getContainerFullPath();
        if (fullPath != null) {
            String projectName = fullPath.segment(0);
            IStatus isValidProjectName = workspace.validateName(projectName, IResource.PROJECT);
            if(isValidProjectName.isOK()) {
                IProject project = workspace.getRoot().getProject(projectName);
                if(!project.isOpen()) {
                    setErrorMessage(IDEWorkbenchMessages.SaveAsDialog_closedProjectMessage);
                    return false;
                }
            }
        }
        
        IStatus result = workspace.validateName(resourceName, IResource.FILE);
        if (!result.isOK()){
            setErrorMessage(result.getMessage());
            return false;
        }
        
        setErrorMessage(null);
        return true;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Dialog#getDialogBoundsSettings()
     * 
     * @since 3.2
     */
    protected IDialogSettings getDialogBoundsSettings() {
        IDialogSettings settings = IDEWorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection(DIALOG_SETTINGS_SECTION);
        if (section == null) {
            section = settings.addNewSection(DIALOG_SETTINGS_SECTION);
        }
        return section;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#isResizable()
     */
    protected boolean isResizable() {
        return true;
    }
}

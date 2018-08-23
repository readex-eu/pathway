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

package org.drools.eclipse;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;

public class DroolsPerspective implements IPerspectiveFactory {

    public void createInitialLayout(IPageLayout layout) {
         String editorArea = layout.getEditorArea();

        IFolderLayout folder= layout.createFolder("left", IPageLayout.LEFT, (float)0.25, editorArea); //$NON-NLS-1$
        folder.addView(JavaUI.ID_PACKAGES);
        folder.addPlaceholder(JavaUI.ID_TYPE_HIERARCHY);
        folder.addView(IPageLayout.ID_RES_NAV);

        IFolderLayout rulesfolder= layout.createFolder("leftbottom", IPageLayout.BOTTOM, (float)0.5, "left"); //$NON-NLS-1$
        rulesfolder.addView(IDroolsConstants.RULES_VIEW);

        IFolderLayout outputfolder= layout.createFolder("bottom", IPageLayout.BOTTOM, (float)0.75, editorArea); //$NON-NLS-1$
        outputfolder.addView(IPageLayout.ID_PROBLEM_VIEW);
        outputfolder.addView(IPageLayout.ID_PROP_SHEET);
        outputfolder.addPlaceholder(JavaUI.ID_JAVADOC_VIEW);
        outputfolder.addPlaceholder(JavaUI.ID_SOURCE_VIEW);
        outputfolder.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
        outputfolder.addPlaceholder(IPageLayout.ID_BOOKMARKS);
        outputfolder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);

        // *** Drools-specific extensions ***
        outputfolder.addView(IDroolsConstants.AUDIT_VIEW);
        layout.addShowViewShortcut(IDroolsConstants.AUDIT_VIEW);
        layout.addNewWizardShortcut("org.drools.eclipse.new.file");
        layout.addNewWizardShortcut("org.drools.eclipse.new.dsl");
        layout.addNewWizardShortcut("org.drools.eclipse.wizards.new.project");
        // **********************************

        layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, (float)0.75, editorArea);

        layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
        layout.addActionSet(JavaUI.ID_ACTION_SET);
        layout.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
        layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);

        // views - java
        layout.addShowViewShortcut(JavaUI.ID_PACKAGES);
        layout.addShowViewShortcut(JavaUI.ID_TYPE_HIERARCHY);
        layout.addShowViewShortcut(JavaUI.ID_SOURCE_VIEW);
        layout.addShowViewShortcut(JavaUI.ID_JAVADOC_VIEW);

        // views - debugging
        layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);

        // views - standard workbench
        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
        layout.addShowViewShortcut(IProgressConstants.PROGRESS_VIEW_ID);

        // new actions - Java project creation wizard
        layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewPackageCreationWizard"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewClassCreationWizard"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewEnumCreationWizard"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewAnnotationCreationWizard"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSourceFolderCreationWizard");     //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSnippetFileCreationWizard"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");//$NON-NLS-1$
    }

}

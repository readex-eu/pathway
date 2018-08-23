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

package org.drools.eclipse.debug;

import java.util.Iterator;
import java.util.List;
import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.ProcessInfo;
import org.drools.eclipse.flow.common.editor.ProcessExtension;
import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.drools.eclipse.flow.common.editor.core.ProcessWrapper;
import org.drools.eclipse.flow.common.editor.editpart.ElementEditPart;
import org.drools.eclipse.flow.common.editor.editpart.ProcessEditPart;
import org.drools.eclipse.flow.common.editor.editpart.ProcessEditPartFactory;
import org.drools.eclipse.flow.common.editor.editpart.figure.ElementFigure;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.jbpm.process.core.Process;




/** Pathway-specific modification of the ProcessInstanceViewer. */
public class ProcessInstanceViewer2 extends ViewPart implements ISelectionListener {
    @Override
    public void createPartControl(Composite parent) {
        this.parent = parent;
        this.parent.setLayout(new FillLayout());
        this.instanceView = new ProcessInstanceView(parent);
    }


    @Override
    public void setFocus() {
    }


    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    }


    public void drawProcessInstance(String processId, List<String> nodeIds, String projectName) {
        instanceView.update(processId, nodeIds, projectName);
    }


    private Composite parent;
    private ProcessInstanceView instanceView;


    private static IJavaProject getJavaProject(String projectName) {
        if( projectName == null )
            return null;

        projectName = projectName.trim();
        if( projectName.length() > 0 ) {
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
            if( project != null ) {
                try {
                    if( project.getNature("org.eclipse.jdt.core.javanature") != null ) {
                        IJavaProject javaProject = JavaCore.create(project);
                        if( javaProject.exists() )
                            return javaProject;
                    }
                }
                catch( CoreException ex ) {
                    DroolsEclipsePlugin.log(ex);
                }
            }
        }

        return null;
    }


    private static class ProcessInstanceView extends Composite {
        public ProcessInstanceView(Composite parent) {
            super(parent, SWT.NONE);

            this.graphicalViewer = new ScrollingGraphicalViewer();
            graphicalViewer.createControl(this);
            graphicalViewer.getControl().setBackground(new Color(Display.getCurrent(), 240, 240, 240));
            graphicalViewer.setRootEditPart(new ScalableRootEditPart());

            setLayout(new FillLayout());
        }


        public void update(String processId, List<String> nodeIds, String projectName) {
            ProcessInfo processInfo = DroolsEclipsePlugin.getDefault().getProcessInfo(processId);
            if( processInfo == null )
                throw new IllegalArgumentException("Could not find process with id " + processId);

            IJavaProject javaProject = getJavaProject(projectName);

            IExtensionRegistry reg = Platform.getExtensionRegistry();
            IConfigurationElement[] extensions = reg.getConfigurationElementsFor("org.drools.eclipse.processExtension");
            for( IConfigurationElement element: extensions ) {
                try {
                    ProcessExtension processExtension = (ProcessExtension)element.createExecutableExtension("className");
                    Process process = (Process)processInfo.getProcess();
                    if( processExtension.acceptsProcess(process.getType()) ) {
                        ProcessEditPartFactory editPartFactory = processExtension.getProcessEditPartFactory();
                        editPartFactory.setProject(javaProject);
                        graphicalViewer.setEditPartFactory(editPartFactory);
                        ProcessWrapper processWrapper = processExtension.getProcessWrapperBuilder().getProcessWrapper(process, javaProject);
                        graphicalViewer.setContents(processWrapper);
                        break;
                    }
                }
                catch( CoreException ex ) {
                    DroolsEclipsePlugin.log(ex);
                }
            }

            for( String nodeId: nodeIds )
                handleNodeInstanceSelection(nodeId);
        }


        private void handleNodeInstanceSelection(String nodeId) {
            boolean found = false;
            Iterator iterator = ((ProcessEditPart)graphicalViewer.getContents()).getChildren().iterator();

            while( iterator.hasNext() ) {
                ElementEditPart elementEditPart = (ElementEditPart)iterator.next();
                if( ((ElementWrapper)elementEditPart.getModel()).getId().equals(nodeId) ) {
                    ((ElementFigure)elementEditPart.getFigure()).setSelected(true);
                    found = true;
                    break;
                }
            }

            if( !found )
                throw new IllegalArgumentException("Could not find node with id " + nodeId);
        }


        private final GraphicalViewer graphicalViewer;
    }
}

/*
 * Copyright 2005 JBoss Inc
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

package org.drools.eclipse.flow.ruleflow.view.property.action;

import org.drools.eclipse.flow.common.view.property.BeanDialogCellEditor;
import org.drools.eclipse.flow.common.view.property.EditBeanDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jbpm.workflow.core.DroolsAction;
import org.jbpm.workflow.core.WorkflowProcess;
import org.jbpm.workflow.core.node.ActionNode;

/**
 * Cell editor for an action.
 */
public class ActionCellEditor extends BeanDialogCellEditor<DroolsAction> {

    private WorkflowProcess process;
    private ActionNode actionNode;
    
    public ActionCellEditor(Composite parent, WorkflowProcess process, ActionNode actionNode) {
        super(parent);
        this.process = process;
        this.actionNode = actionNode;
    }

    protected Object openDialogBox(Control cellEditorWindow) {
        ActionDialog dialog = (ActionDialog) createDialog(cellEditorWindow.getShell());
        dialog.setValue(actionNode.getAction());
        int result = dialog.open();
        if (result == Window.CANCEL) {
            return null;
        }
        return dialog.getValue();
    }

    protected EditBeanDialog<DroolsAction> createDialog(Shell shell) {
        ActionDialog dialog = new ActionDialog(shell, process);
        dialog.setValue(actionNode.getAction());
        return dialog;
    }
    
    protected String getLabelText(Object value) {
        if (actionNode == null || actionNode.getAction() == null || actionNode.getAction().toString() == null) {
            return "";
        }
        return actionNode.getAction().toString();
    }

}

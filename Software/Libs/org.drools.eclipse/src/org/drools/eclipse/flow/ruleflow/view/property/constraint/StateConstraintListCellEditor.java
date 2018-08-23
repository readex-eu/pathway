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

package org.drools.eclipse.flow.ruleflow.view.property.constraint;

import java.util.Map;

import org.drools.eclipse.flow.common.view.property.BeanDialogCellEditor;
import org.drools.eclipse.flow.common.view.property.EditBeanDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.jbpm.workflow.core.Constraint;
import org.jbpm.workflow.core.WorkflowProcess;
import org.jbpm.workflow.core.impl.ConnectionRef;
import org.jbpm.workflow.core.node.StateNode;

/**
 * Cell editor for state constraints.
 */
public class StateConstraintListCellEditor extends BeanDialogCellEditor<Map<ConnectionRef, Constraint>> {

    private WorkflowProcess process;
    private StateNode stateNode;
    
    public StateConstraintListCellEditor(Composite parent, WorkflowProcess process, StateNode stateNode) {
        super(parent);
        this.process = process;
        this.stateNode = stateNode;
    }

    protected EditBeanDialog<Map<ConnectionRef, Constraint>> createDialog(Shell shell) {
        return new StateConstraintListDialog(shell, process, stateNode);
    }
    
    protected String getLabelText(Object value) {
        return "";
    }
}

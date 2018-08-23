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

import org.drools.eclipse.flow.common.view.property.BeanDialogCellEditor;
import org.drools.eclipse.flow.common.view.property.EditBeanDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.jbpm.workflow.core.WorkflowProcess;
import org.jbpm.workflow.core.node.Split;

/**
 * Cell editor for constraints.
 */
public class ConstraintListCellEditor extends BeanDialogCellEditor {

    private WorkflowProcess process;
    private Split split;
    
    public ConstraintListCellEditor(Composite parent, WorkflowProcess process, Split split) {
        super(parent);
        this.process = process;
        this.split = split;
    }

    protected EditBeanDialog createDialog(Shell shell) {
        return new ConstraintListDialog(shell, process, split);
    }
    
    protected String getLabelText(Object value) {
        return "";
    }
}

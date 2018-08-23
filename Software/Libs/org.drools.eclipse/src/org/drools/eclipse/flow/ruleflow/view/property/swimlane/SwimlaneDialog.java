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

package org.drools.eclipse.flow.ruleflow.view.property.swimlane;

import org.drools.eclipse.flow.common.view.property.EditBeanDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jbpm.process.core.context.swimlane.Swimlane;

/**
 * Dialog for editing swimlanes.
 */
public class SwimlaneDialog extends EditBeanDialog<Swimlane> {
    
    private Text nameText;

    public SwimlaneDialog(Shell parentShell) {
        super(parentShell, "Edit Swimlane");
    }
    
    protected Control createDialogArea(Composite parent) {
        final Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout(gridLayout);
        
        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText("Name: ");
        nameText = new Text(composite, SWT.NONE);
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        nameText.setLayoutData(gridData);
        String name = ((Swimlane) getValue()).getName();
        nameText.setText(name == null ? "" : name);

        return composite;
    }
    
    protected Swimlane updateValue(Swimlane value) {
        value.setName(nameText.getText());
        return value;
    }
    
}

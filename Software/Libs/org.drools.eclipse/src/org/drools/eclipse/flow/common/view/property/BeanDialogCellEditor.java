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

package org.drools.eclipse.flow.common.view.property;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Cell editor for a JavaBean.
 */
public abstract class BeanDialogCellEditor<T> extends DialogCellEditor {

    public BeanDialogCellEditor(Composite parent) {
        super(parent);
    }

    @SuppressWarnings("unchecked")
    protected Object openDialogBox(Control cellEditorWindow) {
        EditBeanDialog<T> dialog = createDialog(cellEditorWindow.getShell());
        T value = (T) getValue();
        if (value != null) {
            dialog.setValue(value);
        }
        int result = dialog.open();
        if (result == Window.CANCEL) {
            return null;
        }
        return dialog.getValue();
    }
    
    protected abstract EditBeanDialog<T> createDialog(Shell shell);

    protected void updateContents(Object value) {
        getDefaultLabel().setText(getLabelText(value));
    }
    
    protected String getLabelText(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}

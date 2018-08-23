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

package org.drools.eclipse.flow.common.editor.policy;

import org.drools.eclipse.flow.common.editor.core.ElementContainer;
import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.drools.eclipse.flow.common.editor.core.command.AddElementCommand;
import org.drools.eclipse.flow.common.editor.core.command.ChangeConstraintCommand;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * Policy for performing layout of a process.
 */
public class ElementContainerLayoutEditPolicy extends XYLayoutEditPolicy {
    
    protected Command getCreateCommand(CreateRequest request) {
        AddElementCommand command = new AddElementCommand();
        command.setParent((ElementContainer) getHost().getModel());
        ElementWrapper element = (ElementWrapper) request.getNewObject();
        element.setConstraint((Rectangle) getConstraintFor(request));
        command.setChild(element);
        return command;
    }

    protected Command getDeleteDependantCommand(Request request) {
        return null;
    }

    protected Command createAddCommand(EditPart child, Object constraint) {
        // TODO this is needed to allow dragging of elements from one container to another
        return null;
    }

    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
        ChangeConstraintCommand command = new ChangeConstraintCommand();
        command.setElement((ElementWrapper) child.getModel());
        command.setConstraint((Rectangle)constraint);
        return command;
    }
}

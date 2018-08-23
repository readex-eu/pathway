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

package org.drools.eclipse.flow.common.editor.core.command;

import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

/**
 * A command for changing a constraint.
 */
public class ChangeConstraintCommand extends Command {

    private ElementWrapper element;
    private Rectangle constraint;
    private Rectangle oldConstraint;

    public void execute() {
        element.setConstraint(constraint);
    }

    public void setConstraint(Rectangle rect) {
        constraint = rect;
    }

    public void setElement(ElementWrapper element) {
        this.element = element;
        oldConstraint = element.getConstraint();
    }
    
    public void undo() {
        element.setConstraint(oldConstraint);
    }
}

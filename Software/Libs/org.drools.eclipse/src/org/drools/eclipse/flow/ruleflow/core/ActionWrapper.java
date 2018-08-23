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

package org.drools.eclipse.flow.ruleflow.core;

import org.drools.eclipse.flow.common.editor.core.ElementConnection;
import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.drools.eclipse.flow.ruleflow.view.property.action.ActionPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.jbpm.workflow.core.DroolsAction;
import org.jbpm.workflow.core.WorkflowProcess;
import org.jbpm.workflow.core.node.ActionNode;

/**
 * Wrapper for an action node.
 */
public class ActionWrapper extends AbstractNodeWrapper {

    private static final long serialVersionUID = 510l;

    private IPropertyDescriptor[] descriptors;

    public static final String ACTION = "Action";

    public ActionWrapper() {
        setNode(new ActionNode());
        getActionNode().setName("Action");
    }
    
    private void setDescriptors() {
        descriptors = new IPropertyDescriptor[AbstractNodeWrapper.DESCRIPTORS.length + 1];
        System.arraycopy(AbstractNodeWrapper.DESCRIPTORS, 0, descriptors, 0, AbstractNodeWrapper.DESCRIPTORS.length);
        descriptors[descriptors.length - 1] = 
            new ActionPropertyDescriptor(ACTION, "Action", getActionNode(), (WorkflowProcess) getParent().getProcessWrapper().getProcess());
    }
    
    public ActionNode getActionNode() {
        return (ActionNode) getNode();
    }
    
    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (descriptors == null) {
            setDescriptors();
        }
        return descriptors;
    }

    public boolean acceptsIncomingConnection(ElementConnection connection, ElementWrapper source) {
        return super.acceptsIncomingConnection(connection, source)
            && getIncomingConnections().isEmpty();
    }

    public boolean acceptsOutgoingConnection(ElementConnection connection, ElementWrapper target) {
        return super.acceptsOutgoingConnection(connection, target)
            && getOutgoingConnections().isEmpty();
    }
    
    public Object getPropertyValue(Object id) {
        if (ACTION.equals(id)) {
            Object action = getActionNode().getAction();
            return action == null ? "" : action;
        }
        return super.getPropertyValue(id);
    }

    public void resetPropertyValue(Object id) {
        if (ACTION.equals(id)) {
            getActionNode().setAction(null);
        } else {
            super.resetPropertyValue(id);
        }
    }

    public void setPropertyValue(Object id, Object value) {
        if (ACTION.equals(id)) {
            getActionNode().setAction((DroolsAction) value);
        } else {
            super.setPropertyValue(id, value);
        }
    }
}

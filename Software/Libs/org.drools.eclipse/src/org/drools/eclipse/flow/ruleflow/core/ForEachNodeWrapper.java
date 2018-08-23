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

package org.drools.eclipse.flow.ruleflow.core;

import org.drools.eclipse.flow.common.editor.core.DefaultElementWrapper;
import org.drools.eclipse.flow.common.editor.core.ElementConnection;
import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.drools.process.core.datatype.impl.type.ObjectDataType;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.jbpm.workflow.core.Node;
import org.jbpm.workflow.core.node.CompositeNode;
import org.jbpm.workflow.core.node.ForEachNode;

public class ForEachNodeWrapper extends CompositeNodeWrapper {

    public static final String VARIABLE_NAME = "variableName";
    public static final String COLLECTION_EXPRESSION = "collectionExpression";
    public static final String START_NODE = "startNodeId";
    public static final String END_NODE = "endNodeId";

    private static final long serialVersionUID = 510l;
    private static IPropertyDescriptor[] descriptors;

    static {
        descriptors = new IPropertyDescriptor[DefaultElementWrapper.DESCRIPTORS.length + 2];
        System.arraycopy(DefaultElementWrapper.DESCRIPTORS, 0, descriptors, 0, DefaultElementWrapper.DESCRIPTORS.length);
        descriptors[descriptors.length - 2] = 
            new TextPropertyDescriptor(VARIABLE_NAME, "Variable Name");
        descriptors[descriptors.length - 1] = 
            new TextPropertyDescriptor(COLLECTION_EXPRESSION, "CollectionExpression");
//        descriptors[descriptors.length - 2] = 
//            new TextPropertyDescriptor(START_NODE, "StartNodeId");
//        descriptors[descriptors.length - 1] = 
//            new TextPropertyDescriptor(END_NODE, "EndNodeId");
    }
    
    public ForEachNodeWrapper() {
        setNode(new ForEachNode());
        getForEachNode().setName("ForEach");
    }
    
    public ForEachNode getForEachNode() {
        return (ForEachNode) getNode();
    }
    
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return descriptors;
    }

    public boolean acceptsIncomingConnection(ElementConnection connection, ElementWrapper source) {
        return super.acceptsIncomingConnection(connection, source)
            && getIncomingConnections().isEmpty();
    }

    public boolean acceptsOutgoingConnection(ElementConnection connection, ElementWrapper target) {
        return target == null
            || (target.getParent() == getParent() && getOutgoingConnections().isEmpty());
//            || (target.getParent() == this && getForEachNode().getLinkedIncomingNode(Node.CONNECTION_DEFAULT_TYPE) == null);
    }
    
    public Object getPropertyValue(Object id) {
        if (VARIABLE_NAME.equals(id)) {
            String variableName = getForEachNode().getVariableName();
            return variableName == null ? "" : variableName;
        }
        if (COLLECTION_EXPRESSION.equals(id)) {
            String collectionExpression = getForEachNode().getCollectionExpression();
            return collectionExpression == null ? "" : collectionExpression;
        }
        if (START_NODE.equals(id)) {
            CompositeNode.NodeAndType link = getForEachNode().getLinkedIncomingNode(Node.CONNECTION_DEFAULT_TYPE);
            return link == null ? "" : link.getNodeId() + "";
        }
        if (END_NODE.equals(id)) {
            CompositeNode.NodeAndType link = getForEachNode().getLinkedOutgoingNode(Node.CONNECTION_DEFAULT_TYPE);
            return link == null ? "" : link.getNodeId() + "";
        }
        return super.getPropertyValue(id);
    }

    public void resetPropertyValue(Object id) {
        if (VARIABLE_NAME.equals(id)) {
            getForEachNode().setVariable(null, null);
        } else if (COLLECTION_EXPRESSION.equals(id)) {
            getForEachNode().setCollectionExpression(null);
        } else if (START_NODE.equals(id)) {
            getForEachNode().linkIncomingConnections(Node.CONNECTION_DEFAULT_TYPE, null);
        } else if (END_NODE.equals(id)) {
            getForEachNode().linkOutgoingConnections(null, Node.CONNECTION_DEFAULT_TYPE);
        } else {
            super.resetPropertyValue(id);
        }
    }

    public void setPropertyValue(Object id, Object value) {
        if (VARIABLE_NAME.equals(id)) {
            getForEachNode().setVariable((String) value, new ObjectDataType("java.lang.Object"));
        } else if (COLLECTION_EXPRESSION.equals(id)) {
            getForEachNode().setCollectionExpression((String) value);
        } else if (START_NODE.equals(id)) {
            getForEachNode().linkIncomingConnections(Node.CONNECTION_DEFAULT_TYPE, new Long((String) value), Node.CONNECTION_DEFAULT_TYPE);
        } else if (END_NODE.equals(id)) {
            getForEachNode().linkOutgoingConnections(new Long((String) value), Node.CONNECTION_DEFAULT_TYPE, Node.CONNECTION_DEFAULT_TYPE);
        } else {
            super.setPropertyValue(id, value);
        }
    }
    
}

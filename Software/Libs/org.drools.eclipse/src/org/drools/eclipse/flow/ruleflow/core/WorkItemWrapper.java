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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.drools.definition.process.NodeContainer;
import org.drools.eclipse.flow.common.editor.core.ElementConnection;
import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.drools.eclipse.flow.ruleflow.view.property.workitem.WorkItemParameterMappingPropertyDescriptor;
import org.drools.eclipse.flow.ruleflow.view.property.workitem.WorkItemResultMappingPropertyDescriptor;
import org.drools.process.core.ParameterDefinition;
import org.drools.process.core.Work;
import org.drools.process.core.WorkDefinition;
import org.drools.process.core.datatype.DataType;
import org.drools.process.core.impl.WorkImpl;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.workflow.core.Node;
import org.jbpm.workflow.core.node.WorkItemNode;

/**
 * Wrapper for a work item node.
 */
public class WorkItemWrapper extends StateBasedNodeWrapper {

    public static final String WAIT_FOR_COMPLETION = "WaitForCompletion";
    public static final String RESULT_MAPPING = "ResultMapping";
    public static final String PARAMETER_MAPPING = "ParameterMapping";
    
    private static final long serialVersionUID = 510l;

    private WorkDefinition workDefinition;

    public WorkItemWrapper() {
        setNode(new WorkItemNode());
    }
    
    public WorkItemNode getWorkItemNode() {
        return (WorkItemNode) getNode();
    }
    
    public void setNode(Node node) {
        super.setNode(node);
        if (this.workDefinition != null) {
            Work work = getWorkItemNode().getWork();
            if (work == null) {
                work = new WorkImpl();
                work.setName(workDefinition.getName());
                getWorkItemNode().setWork(work);
            }
            work.setParameterDefinitions(workDefinition.getParameters());
        }
    }
    
    public void setWorkDefinition(WorkDefinition workDefinition) {
        this.workDefinition = workDefinition;
        Work work = getWorkItemNode().getWork();
        if (work == null) {
            work = new WorkImpl();
            work.setName(workDefinition.getName());
            getWorkItemNode().setWork(work);
        }
        work.setParameterDefinitions(workDefinition.getParameters());
    }
    
    public WorkDefinition getWorkDefinition() {
        return this.workDefinition;
    }

    private boolean workParameterExists(String parameterName) {
        if (workDefinition != null) {
            return workDefinition.getParameter(parameterName) != null;
        }
        return false;
    }
    
    protected void initDescriptors() {
        super.initDescriptors();
        Set<ParameterDefinition> parameters = workDefinition.getParameters();
        IPropertyDescriptor[] oldDescriptors = descriptors;
        boolean fullProps = isFullProperties();
        descriptors = new IPropertyDescriptor[oldDescriptors.length + parameters.size() + (fullProps ? 5 : 4)];
        System.arraycopy(oldDescriptors, 0, descriptors, 0, oldDescriptors.length);
        int i = 0;
        for (ParameterDefinition def: parameters) {
            descriptors[oldDescriptors.length + (i++)] = 
                new TextPropertyDescriptor(def.getName(), def.getName());
        }
        if (fullProps) {
	        descriptors[descriptors.length - 5] = 
	            new ComboBoxPropertyDescriptor(WAIT_FOR_COMPLETION, "Wait for completion", new String[] {"true", "false"});
        }
        descriptors[descriptors.length - 4] = getOnEntryPropertyDescriptor();
        descriptors[descriptors.length - 3] = getOnExitPropertyDescriptor();
        descriptors[descriptors.length - 2] = 
            new WorkItemParameterMappingPropertyDescriptor(PARAMETER_MAPPING, "Parameter Mapping", getWorkItemNode());
        descriptors[descriptors.length - 1] = 
            new WorkItemResultMappingPropertyDescriptor(RESULT_MAPPING, "Result Mapping", getWorkItemNode());
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
        if (WAIT_FOR_COMPLETION.equals(id)) {
            return getWorkItemNode().isWaitForCompletion() ? new Integer(0) : new Integer(1);
        } else if (PARAMETER_MAPPING.equals(id)) {
            return getWorkItemNode().getInMappings();
        } else if (RESULT_MAPPING.equals(id)) {
            return getWorkItemNode().getOutMappings();
        } else if (id instanceof String) {
            String name = (String) id;
            if (workParameterExists(name)) {
                DataType type = getWorkItemNode().getWork().getParameterDefinition(name).getType();
                Object value = getWorkItemNode().getWork().getParameter(name);
                if (value == null) {
                    return "";
                }
                return type.writeValue(value);
            }
        }
        return super.getPropertyValue(id);
    }

    public void resetPropertyValue(Object id) {
        if (WAIT_FOR_COMPLETION.equals(id)) {
            getWorkItemNode().setWaitForCompletion(true);
        } else if (PARAMETER_MAPPING.equals(id)) {
            getWorkItemNode().setInMappings(new HashMap<String, String>());
        } else if (RESULT_MAPPING.equals(id)) {
            getWorkItemNode().setOutMappings(new HashMap<String, String>());
        } else if (id instanceof String && workParameterExists((String) id)) {
            getWorkItemNode().getWork().setParameter((String) id, null);
        } else {
            super.resetPropertyValue(id);
        }
    }

    @SuppressWarnings("unchecked")
    public void setPropertyValue(Object id, Object value) {
        if (WAIT_FOR_COMPLETION.equals(id)) {
            getWorkItemNode().setWaitForCompletion(((Integer) value).intValue() == 0);
        } else if (PARAMETER_MAPPING.equals(id)) {
            getWorkItemNode().setInMappings((Map<String, String>) value);
        } else if (RESULT_MAPPING.equals(id)) {
            getWorkItemNode().setOutMappings((Map<String, String>) value);
        } else if (id instanceof String && workParameterExists((String) id)) {
            DataType type = getWorkItemNode().getWork().getParameterDefinition((String) id).getType();
            getWorkItemNode().getWork().setParameter((String) id, type.readValue((String) value));
        } else {
            super.setPropertyValue(id, value);
        }
    }
}

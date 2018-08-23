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

import java.util.HashSet;
import java.util.Set;

import org.drools.eclipse.flow.common.editor.editpart.work.HumanTaskCustomEditor;
import org.drools.process.core.ParameterDefinition;
import org.drools.process.core.WorkDefinition;
import org.drools.process.core.datatype.impl.type.StringDataType;
import org.drools.process.core.impl.ParameterDefinitionImpl;
import org.drools.process.core.impl.WorkDefinitionExtensionImpl;
import org.drools.process.core.impl.WorkDefinitionImpl;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.jbpm.workflow.core.node.HumanTaskNode;

/**
 * Wrapper for a human task node.
 */
public class HumanTaskNodeWrapper extends WorkItemWrapper {

    public static final String SWIMLANE = "Swimlane";

    private static final long serialVersionUID = 510l;

    private static final WorkDefinition WORK_DEFINITION;
    
    static {
        WORK_DEFINITION = new WorkDefinitionExtensionImpl();
        ((WorkDefinitionImpl) WORK_DEFINITION).setName("Human Task");
        Set<ParameterDefinition> parameterDefinitions = new HashSet<ParameterDefinition>();
        parameterDefinitions.add(new ParameterDefinitionImpl("TaskName", new StringDataType()));
        parameterDefinitions.add(new ParameterDefinitionImpl("ActorId", new StringDataType()));
        parameterDefinitions.add(new ParameterDefinitionImpl("GroupId", new StringDataType()));
        parameterDefinitions.add(new ParameterDefinitionImpl("Priority", new StringDataType()));
        parameterDefinitions.add(new ParameterDefinitionImpl("Comment", new StringDataType()));
        parameterDefinitions.add(new ParameterDefinitionImpl("Skippable", new StringDataType()));
        parameterDefinitions.add(new ParameterDefinitionImpl("Content", new StringDataType()));
        ((WorkDefinitionExtensionImpl) WORK_DEFINITION).setParameters(parameterDefinitions);
        ((WorkDefinitionExtensionImpl) WORK_DEFINITION).setIcon("icons/human_task.gif");
        ((WorkDefinitionExtensionImpl) WORK_DEFINITION).setCustomEditor(HumanTaskCustomEditor.class.getName());
    }

    public HumanTaskNodeWrapper() {
        setNode(new HumanTaskNode());
        getNode().setName("Human Task");
        setWorkDefinition(WORK_DEFINITION);
    }
    
    protected void initDescriptors() {
        super.initDescriptors();
        IPropertyDescriptor[] parentDescriptors = descriptors;
        descriptors = new IPropertyDescriptor[parentDescriptors.length + 1];
        System.arraycopy(parentDescriptors, 0, descriptors, 0, parentDescriptors.length);
        descriptors[descriptors.length - 1] = 
            new TextPropertyDescriptor(SWIMLANE, "Swimlane");
    }
    
    public HumanTaskNode getHumanTaskNode() {
        return (HumanTaskNode) getNode();
    }
    
    public Object getPropertyValue(Object id) {
        if (SWIMLANE.equals(id)) {
            String swimlane = getHumanTaskNode().getSwimlane();
            return swimlane == null ? "" : swimlane;
        }
        return super.getPropertyValue(id);
    }

    public void resetPropertyValue(Object id) {
        if (SWIMLANE.equals(id)) {
            getHumanTaskNode().setSwimlane(null);
        } else {
            super.resetPropertyValue(id);
        }
    }

    public void setPropertyValue(Object id, Object value) {
        if (SWIMLANE.equals(id)) {
            getHumanTaskNode().setSwimlane((String) value);
        } else {
            super.setPropertyValue(id, value);
        }
    }
}

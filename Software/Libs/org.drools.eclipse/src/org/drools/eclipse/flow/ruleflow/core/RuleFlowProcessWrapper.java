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

import org.drools.definition.process.Node;
import org.drools.definition.process.Process;
import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.drools.eclipse.flow.common.editor.core.ProcessWrapper;
import org.jbpm.ruleflow.core.RuleFlowProcess;

/**
 * Wrapper for a RuleFlow process.
 */
public class RuleFlowProcessWrapper extends ProcessWrapper {

    private static final long serialVersionUID = 510l;

    public RuleFlowProcess getRuleFlowProcess() {
        return (RuleFlowProcess) getProcess();
    }
    
    protected Process createProcess() {
        return new RuleFlowProcess();
    }

    public boolean isFullProperties() {
    	// not full properties for BPMN2 process, which is set to autocomplete = true
    	return !getRuleFlowProcess().isAutoComplete();
    }
    
    protected void internalAddElement(ElementWrapper element) {
        Node node = ((NodeWrapper) element).getNode();
        long id = 0;
        for (Node n: getRuleFlowProcess().getNodes()) {
            if (n.getId() > id) {
                id = n.getId();
            }
        }
        ((org.jbpm.workflow.core.Node) node).setId(++id);
        getRuleFlowProcess().addNode(node);
    }

    protected void internalRemoveElement(ElementWrapper element) {
        getRuleFlowProcess().removeNode(((NodeWrapper) element).getNode());
    }
    
    public boolean canAddElement(ElementWrapper element) {
        if (element instanceof StartNodeWrapper && getRuleFlowProcess().getStart() != null) {
            return false;
        }
        return true;
    }
    
}

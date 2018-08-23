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
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.jbpm.workflow.core.node.Join;

/**
 * Wrapper for a join node.
 */
public class JoinWrapper extends AbstractNodeWrapper {

    public static final int CHANGE_TYPE = 5;

    private static final long serialVersionUID = 510l;
    private transient IPropertyDescriptor[] descriptors;

    public static final String TYPE = "type";
    public static final String N = "N";
    
    public JoinWrapper() {
        setNode(new Join());
        getJoin().setName("Join");
    }
    
    public Join getJoin() {
        return (Join) getNode();
    }
    
    public boolean acceptsOutgoingConnection(ElementConnection connection, ElementWrapper target) {
        return super.acceptsOutgoingConnection(connection, target)
            && getOutgoingConnections().isEmpty();
    }

    private void setDescriptors() {
        descriptors = new IPropertyDescriptor[AbstractNodeWrapper.DESCRIPTORS.length + 1];
        System.arraycopy(AbstractNodeWrapper.DESCRIPTORS, 0, descriptors, 0, AbstractNodeWrapper.DESCRIPTORS.length);
        if (isFullProperties()) {
	        descriptors[descriptors.length - 1] = 
	            new ComboBoxPropertyDescriptor(TYPE, "Type", new String[] { "", "AND", "XOR", "Discriminator", "n-of-m" });
        } else {
        	descriptors[descriptors.length - 1] = 
	            new ComboBoxPropertyDescriptor(TYPE, "Type", new String[] { "", "AND", "XOR" });
        }
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
    	if (descriptors == null) {
    		setDescriptors();
    	}
        if (getParent() != null && (getJoin().getType() == Join.TYPE_N_OF_M)) {
            IPropertyDescriptor[] result = new IPropertyDescriptor[descriptors.length + 1];
            System.arraycopy(descriptors, 0, result, 0, descriptors.length);
            result[descriptors.length] = new TextPropertyDescriptor(N, "n");
            return result;
        }
        return descriptors;
    }

    public Object getPropertyValue(Object id) {
        if (TYPE.equals(id)) {
            return new Integer(getJoin().getType());
        }
        if (N.equals(id)) {
            return getJoin().getN() == null ? "" : getJoin().getN();
        }
        return super.getPropertyValue(id);
    }

    public void resetPropertyValue(Object id) {
        if (TYPE.equals(id)) {
            getJoin().setType(Join.TYPE_UNDEFINED);
            notifyListeners(CHANGE_TYPE);
        } else if (N.equals(id)) {
            getJoin().setN(null);
        } else {
            super.resetPropertyValue(id);
        }
    }

    public void setPropertyValue(Object id, Object value) {
        if (TYPE.equals(id)) {
            getJoin().setType(((Integer) value).intValue());
            notifyListeners(CHANGE_TYPE);
        }  else if (N.equals(id)) {
            getJoin().setN((String) value);
        } else {
            super.setPropertyValue(id, value);
        }
    }
}

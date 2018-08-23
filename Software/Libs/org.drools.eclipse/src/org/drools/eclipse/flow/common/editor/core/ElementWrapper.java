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

package org.drools.eclipse.flow.common.editor.core;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Wrapper of a model element.
 */
public interface ElementWrapper {

    static final int CHANGE_ID = 0;
    static final int CHANGE_INCOMING_CONNECTIONS = 1;
    static final int CHANGE_OUTGOING_CONNECTIONS = 2;
    static final int CHANGE_CONSTRAINT = 3;
    static final int CHANGE_NAME = 4;
    
    String getId();
    String getName();
    void setName(String name);
    
    void setConstraint(Rectangle constraint);
    Rectangle getConstraint();
    
    void setParent(ElementContainer parent);
    ElementContainer getParent();
    
    List<ElementConnection> getOutgoingConnections();
    List<ElementConnection> getIncomingConnections();
    void addIncomingConnection(ElementConnection connection);
    void localAddIncomingConnection(ElementConnection connection);
    void removeIncomingConnection(ElementConnection connection);
    void addOutgoingConnection(ElementConnection connection);
    void localAddOutgoingConnection(ElementConnection connection);
    void removeOutgoingConnection(ElementConnection connection);
    boolean acceptsIncomingConnection(ElementConnection connection, ElementWrapper source);
    boolean acceptsOutgoingConnection(ElementConnection connection, ElementWrapper target);
    
    void addListener(ModelListener listener);
    void removeListener(ModelListener listener);

}

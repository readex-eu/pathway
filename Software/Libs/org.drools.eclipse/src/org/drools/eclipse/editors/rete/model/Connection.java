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

package org.drools.eclipse.editors.rete.model;

import org.drools.reteoo.BaseVertex;

/**
 * A connection between two distinct vertices.
 */
public class Connection extends ModelElement {

    private boolean    isConnected;

    private BaseVertex source;

    private BaseVertex target;

    /** 
     * Creating a connection between two distinct vertices.
     * 
     * @param source a source endpoint
     * @param target a target endpoint
     * @throws IllegalArgumentException if any of the parameters are null or source == target
     */
    public Connection(BaseVertex source,
                      BaseVertex target) {
        this.source = source;
        this.target = target;
        source.addConnection( this );
        target.addConnection( this );
        isConnected = true;
    }

    /** 
     * Disconnect this connection from the vertices it is attached to.
     */
    public void disconnect() {
        if ( isConnected ) {
            source.removeConnection( this );
            target.removeConnection( this );
            isConnected = false;
        }
    }

    /**
     * Returns the source endpoint of this connection.
     * 
     * @return BaseVertex vertex
     */
    public BaseVertex getSource() {
        return source;
    }

    /**
     * Returns the target endpoint of this connection.
     * 
     * @return BaseVertex vertex
     */
    public BaseVertex getTarget() {
        return target;
    }

    /**
     * Gets opposite of specified vertex.
     * 
     * Returning <code>null</code> if specified not does not belong into this connection.
     * 
     * @param vertex
     * @return opposite of vertex
     */
    public BaseVertex getOpposite(BaseVertex vertex) {
        // If null or not part of this connection
        if ( vertex == null || (!vertex.equals( getSource() ) && !vertex.equals( getTarget() )) ) {
            return null;
        }
        if ( vertex.equals( getSource() ) ) {
            return getTarget();
        }
        return getSource();
    }

}

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.drools.eclipse.flow.common.editor.core.ElementConnection;
import org.drools.eclipse.flow.common.editor.core.ElementWrapper;
import org.eclipse.draw2d.geometry.Point;
import org.jbpm.workflow.core.Connection;
import org.jbpm.workflow.core.Node;
import org.jbpm.workflow.core.impl.ConnectionImpl;

/**
 * Wrapper for a connection.
 */
public class ConnectionWrapper extends ElementConnection {

    private static final long serialVersionUID = 510l;

    private ConnectionImpl connection;

    public ConnectionWrapper() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void localSetConnection(Connection connection) {
        this.connection = (ConnectionImpl) connection;
    }

    public void disconnect() {
        super.disconnect();
        connection.terminate();
        connection = null;
    }

    public void connect(ElementWrapper source, ElementWrapper target) {
        connection = new ConnectionImpl(
            ((NodeWrapper) source).getNode(), Node.CONNECTION_DEFAULT_TYPE,
            ((NodeWrapper) target).getNode(), Node.CONNECTION_DEFAULT_TYPE);
        super.connect(source, target);
    }

    protected List<Point> internalGetBendpoints() {
        return (List<Point>) stringToBendpoints((String) connection.getMetaData("bendpoints"));
    }
    
    protected void internalSetBendpoints(List<Point> bendpoints) {
        connection.setMetaData("bendpoints", bendpointsToString(bendpoints));
    }
    
    private String bendpointsToString(List<Point> bendpoints) {
        if (bendpoints == null) {
            return null;
        }
        String result = "[";
        for (Iterator<Point> iterator = bendpoints.iterator(); iterator.hasNext(); ) {
            Point point = iterator.next();
            result += point.x + "," + point.y + (iterator.hasNext() ? ";" : "");
        }
        result += "]";
        return result;
    }
    
    private List<Point> stringToBendpoints(String s) {
        List<Point> result = new ArrayList<Point>();
        if (s == null) {
            return result;
        }
        s = s.substring(1, s.length() - 1);
        String[] bendpoints = s.split(";");
        for (String bendpoint: bendpoints) {
            bendpoint = bendpoint.trim();
            if (bendpoint.length() != 0) {
                String[] xy = bendpoint.split(",");
                if (xy.length != 2) {
                    throw new IllegalArgumentException(
                        "Unexpected bendpoint: " + bendpoint + " for bendpoints " + bendpoints +
                        " - nb points = " + xy.length);
                }
                try {
                    result.add(new Point(new Integer(xy[0]), new Integer(xy[1])));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Could not parse bendpoint " + bendpoint, e);
                }
            }
        }
        return result;
    }

}

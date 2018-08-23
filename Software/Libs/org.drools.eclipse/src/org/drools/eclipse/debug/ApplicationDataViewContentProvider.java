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

package org.drools.eclipse.debug;

import java.util.ArrayList;
import java.util.List;

import org.drools.eclipse.DroolsEclipsePlugin;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.debug.core.IJavaArray;
import org.eclipse.jdt.debug.core.IJavaObject;
import org.eclipse.jdt.debug.core.IJavaValue;

/**
 * The Application Data View content provider.
 */
public class ApplicationDataViewContentProvider extends DroolsDebugViewContentProvider {

    private DroolsDebugEventHandlerView view;
    
    public ApplicationDataViewContentProvider(DroolsDebugEventHandlerView view) {
        this.view = view;
    }
    
    protected String getEmptyString() {
        return "The selected working memory has no globals defined.";
    }

    public Object[] getChildren(Object obj) {
        try {
            IVariable[] variables = null;
            if (obj != null && obj instanceof IJavaObject
                    && "org.drools.reteoo.ReteooStatefulSession".equals(
                        ((IJavaObject) obj).getReferenceTypeName())) {
                variables = getApplicationDataElements((IJavaObject) obj);
            } else if (obj instanceof IVariable) {
                if (view.isShowLogicalStructure()) {
                    IValue value = getLogicalValue(((IVariable) obj).getValue(), new ArrayList());
                    variables = value.getVariables();
                }
                if (variables == null) {
                    variables = ((IVariable) obj).getValue().getVariables();
                }
            }
            if (variables == null) {
                return new Object[0];
            } else {
                cache(obj, variables);
                return variables;
            }
        } catch (DebugException e) {
            DroolsEclipsePlugin.log(e);
            return new Object[0];
        }
    }
    
    private IVariable[] getApplicationDataElements(IJavaObject stackObj) throws DebugException {
        IValue objects = DebugUtil.getValueByExpression("return ((org.drools.base.MapGlobalResolver) getGlobalResolver()).getGlobals();", stackObj);
        if (objects instanceof IJavaArray) {
            IJavaArray array = (IJavaArray) objects;
            List result = new ArrayList();
            IJavaValue[] javaVals = array.getValues();
            for ( int i = 0; i < javaVals.length; i++ ) {
                IJavaValue mapEntry = javaVals[i];
                String key = null;
                IJavaValue value = null;
                
                IVariable[] vars = mapEntry.getVariables();
                for ( int j = 0; j < vars.length; j++ ) {
                    IVariable var = vars[j];
                    if ("key".equals(var.getName())) {
                        key = var.getValue().getValueString();
                    } else if ("value".equals(var.getName())) {
                        value = (IJavaValue) var.getValue();
                    }
                }
                result.add(new VariableWrapper(key, value));
            }
            return (IVariable[]) result.toArray(new IVariable[result.size()]);
        }
        return null;
    }
}

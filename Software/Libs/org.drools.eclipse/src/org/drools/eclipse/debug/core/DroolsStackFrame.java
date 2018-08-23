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

package org.drools.eclipse.debug.core;

import java.util.ArrayList;
import java.util.List;

import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.DRLInfo.FunctionInfo;
import org.drools.eclipse.DRLInfo.RuleInfo;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.internal.debug.core.model.JDIStackFrame;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;

import com.sun.jdi.StackFrame;

public class DroolsStackFrame  extends JDIStackFrame {
    protected static final String HANDLE_SUFIX = "__Handle__";
    protected static final String DROOLS_VAR_NAME = "drools";
    private static final String CONSEQUENCE_SIGNATURE = "(Lorg/drools/spi/KnowledgeHelper";
    
    public DroolsStackFrame(JDIThread thread, StackFrame frame, int depth) {
        super(thread, frame, depth);
    }
    
    public boolean isExecutingRule() {
        try {
            if ( ("defaultConsequence".equals( getMethodName() ) || "consequence".equals( getMethodName() ) ) 
                    && getSignature().startsWith( CONSEQUENCE_SIGNATURE ) ) {
                return true;
            }
        } catch ( DebugException exc ) {
            DroolsEclipsePlugin.log( exc );
        }
        return false;
    }
    
    public RuleInfo getExecutingRuleInfo() {
        try {
            String methodName = getMethodName();
            String signature = getSignature();
            String type = getDeclaringTypeName();
            if ( ("defaultConsequence".equals( methodName ) || "consequence".equals( methodName ) )
                    && signature.startsWith( CONSEQUENCE_SIGNATURE ) ) {
                return DroolsEclipsePlugin.getDefault().getRuleInfoByClass( type );
            }

        } catch ( DebugException exc ) {
            DroolsEclipsePlugin.log( exc );
        }
        return null;
    }
    
    public FunctionInfo getExecutingFunctionInfo() {
        try {
            return DroolsEclipsePlugin.getDefault().getFunctionInfoByClass( getDeclaringTypeName() );
        } catch ( DebugException exc ) {
            DroolsEclipsePlugin.log( exc );
        }
        return null;
    }
    
    @Override
    protected JDIStackFrame bind(StackFrame frame,
                                 int depth) {
        JDIStackFrame jdiFrame =  super.bind( frame, depth );
        if ( jdiFrame != null && jdiFrame != this ) {
            // this might be a little heaver, as it's a duplicate creation
            jdiFrame = ( JDIStackFrame ) DroolsThread.createCustomFrame( (DroolsThread ) getThread(),
                                                                         depth,
                                                                         frame );
        }
        
        return jdiFrame;
    }
    
    public IVariable[] getVariables() throws DebugException {
        IVariable[] variables = super.getVariables();
        List result = new ArrayList( (variables.length - 1) / 2 );
        for ( int i = 0; i < variables.length; i++ ) {
            String name = variables[i].getName();
            if ( !(name.equals( DROOLS_VAR_NAME )) && !(name.endsWith( HANDLE_SUFIX )) ) {
                result.add( variables[i] );
            }
        }
        variables = (IVariable[]) result.toArray( new IVariable[result.size()] );
//        System.out.print( "vars" );
//        for ( IVariable var : variables ) {
//            System.out.print( var.getName()  + " " );
//        }
//        System.out.println( );
        return variables;
    }
    
    public IJavaVariable[] getLocalVariables() throws DebugException {
        IJavaVariable[] localVars = super.getLocalVariables();
//        System.out.print( "local vars" );
//        for ( IJavaVariable var : localVars ) {
//            System.out.print( var.getName() + " " );
//        }
//        System.out.println( );
        
        return localVars;
    }
    
    public int getLineNumber() throws DebugException {
        synchronized ( getThread() ) {
            RuleInfo ruleInfo = getExecutingRuleInfo();
            if ( ruleInfo != null ) {
                return ruleInfo.getConsequenceDrlLineNumber() + (super.getLineNumber() - ruleInfo.getConsequenceJavaLineNumber() - 1);
            }
            FunctionInfo functionInfo = getExecutingFunctionInfo();
            if ( functionInfo != null ) {
                return functionInfo.getDrlLineNumber() + (super.getLineNumber() - functionInfo.getJavaLineNumber());
            }
        }

        return super.getLineNumber();
    }
   
    
    public StackFrame getUnderlyingStackFrame() throws DebugException {
        return super.getUnderlyingStackFrame();
    }
    
    protected void setUnderlyingStackFrame(StackFrame frame) {
        super.setUnderlyingStackFrame( frame );
    }
    
    @Override
    public void stepOver() throws DebugException {
        super.stepOver();
    }

}

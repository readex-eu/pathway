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

package org.drools.eclipse.wizard.rule;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * This will generate DRL bits and bobs based on various templates.
 * For use by the wizards only. 
 * TODO: move this to string template (as it is being used elsewhere in drools)
 */
public class DRLGenerator {

    private static final Pattern packageDec = Pattern.compile( "\\$package\\$" );
    private static final Pattern dateDec = Pattern.compile( "\\$date\\$" );
    private static final Pattern functionsDec = Pattern.compile( "\\$functions\\$" );
    private static final Pattern expanderDec = Pattern.compile( "\\$expander\\$" );
    
    public InputStream generateRule(String packageName,
                                    InputStream template) throws IOException {
        
        String temp = readTemplate( template );
        temp = doHeader( packageName,
                         temp );
        
        return toStream( temp );
        

    }
    
    public InputStream generatePackage(String packageName, boolean functions, boolean expander, InputStream template) throws IOException {
        String temp = readTemplate( template );
        temp = doHeader(packageName, temp );
        if (functions) {
            temp = functionsDec.matcher( temp ).replaceFirst( "function myFunction( ... ) {" + System.getProperty("line.separator") + "    //function content (can have multiple functions) " + System.getProperty("line.separator") + "}" + System.getProperty("line.separator"));
        } else {
            temp = functionsDec.matcher( temp ).replaceFirst( "");
        }
        if (expander) {
            temp = expanderDec.matcher( temp ).replaceFirst( "expander customLanguage.dsl" + System.getProperty("line.separator") + "");
        } else {
            temp = expanderDec.matcher( temp ).replaceFirst( "");
        }
        return toStream( temp );

        
    }

    private ByteArrayInputStream toStream(String temp) throws UnsupportedEncodingException {
        ByteArrayInputStream stream = new ByteArrayInputStream(temp.getBytes( "UTF-8" ));
        return stream;
    }

    private String doHeader(String packageName,
                            String temp) {
        temp = packageDec.matcher( temp ).replaceFirst( "package " + packageName + System.getProperty("line.separator") );
        temp = dateDec.matcher( temp ).replaceFirst( DateFormat.getDateInstance().format( new Date()) );
        return temp;
    }

    private String readTemplate(InputStream template) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(template));
        String line = null;
        StringBuffer buf = new StringBuffer();
        while ((line = reader.readLine())  != null) {
            buf.append(line + System.getProperty("line.separator"));
        }
        String temp = buf.toString();
        return temp;
    }
    


    
    
}

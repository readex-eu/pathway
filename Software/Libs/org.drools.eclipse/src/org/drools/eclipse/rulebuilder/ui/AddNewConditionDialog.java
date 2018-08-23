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

package org.drools.eclipse.rulebuilder.ui;

import org.drools.ide.common.client.modeldriven.SuggestionCompletionEngine;
import org.drools.ide.common.client.modeldriven.brl.CompositeFactPattern;
import org.drools.ide.common.client.modeldriven.brl.DSLSentence;
import org.drools.ide.common.client.modeldriven.brl.FactPattern;
import org.drools.ide.common.client.modeldriven.brl.FreeFormLine;
import org.drools.ide.common.client.modeldriven.brl.IPattern;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * This provides a popup for new LHS condition selection. (add new if-condition)
 */
public class AddNewConditionDialog extends RuleDialog {

    private IPattern     pattern;

    private RuleModeller modeller;

    public AddNewConditionDialog(Shell parent,
                                 RuleModeller modeller) {

        super( parent,
               "Add new condition to the rule",
               "Pick the values from combos and confirm the selection." );

        this.modeller = modeller;
    }

    protected Control createDialogArea(final Composite parent) {
        Control dialog = super.createDialogArea( parent );

        Composite composite = (Composite) dialog;
        GridLayout layout = new GridLayout( 2,
                                            false );
        composite.setLayout( layout );

        addFacts( composite );

        addConditionType( composite );

        addDSLSentences( composite );

        addFreeFormLine( composite );

        return composite;
    }

    private void addFreeFormLine(Composite composite) {
        createLabel( composite,
                     "Free form action" );

        Button b = new Button( composite,
                               SWT.NONE );
        b.setText( "Add free form drl" );
        b.addSelectionListener( new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                modeller.getModel().addLhsItem( new FreeFormLine() );
                modeller.setDirty( true );
                modeller.reloadLhs();
                close();
            }
        } );
    }

    private void addFacts(Composite composite) {
        createLabel( composite,
                     "Fact" );

        String[] factTypes = getCompletion().getFactTypes();
        final Combo factsCombo = new Combo( composite,
                                            SWT.READ_ONLY );
        factsCombo.add( "Choose fact type..." );
        for ( int i = 0; i < factTypes.length; i++ ) {
            factsCombo.add( factTypes[i] );
        }
        factsCombo.select( 0 );

        factsCombo.addListener( SWT.Selection,
                                new Listener() {
                                    public void handleEvent(Event event) {
                                        if ( factsCombo.getSelectionIndex() == 0 ) {
                                            return;
                                        }
                                        modeller.getModel().addLhsItem( new FactPattern( factsCombo.getText() ) );
                                        modeller.reloadLhs();
                                        modeller.setDirty( true );
                                        close();
                                    }
                                } );
    }

    private void addConditionType(Composite composite) {
        createLabel( composite,
                     "Condition type" );

        final Combo conditionalsCombo = new Combo( composite,
                                                   SWT.READ_ONLY );
        String[] conditionalElements = getCompletion().getConditionalElements();
        conditionalsCombo.add( "Choose condition type..." );
        for ( int i = 0; i < conditionalElements.length; i++ ) {
            conditionalsCombo.add( conditionalElements[i] );
        }
        conditionalsCombo.select( 0 );

        conditionalsCombo.addListener( SWT.Selection,
                                       new Listener() {
                                           public void handleEvent(Event event) {
                                               if ( conditionalsCombo.getSelectionIndex() == 0 ) {
                                                   return;
                                               }

                                               modeller.getModel().addLhsItem( new CompositeFactPattern( conditionalsCombo.getText() ) );
                                               modeller.reloadLhs();
                                               modeller.setDirty( true );
                                               close();
                                           }
                                       } );
    }

    //
    // The list of DSL sentences
    //
    private void addDSLSentences(Composite composite) {
        if ( getCompletion().getDSLConditions().length > 0 ) {
            createLabel( composite,
                         "Condition sentences" );

            final Combo dslCombo = new Combo( composite,
                                              SWT.READ_ONLY );
            dslCombo.add( "Choose..." );

            for ( int i = 0; i < getCompletion().getDSLConditions().length; i++ ) {
                DSLSentence sen = getCompletion().getDSLConditions()[i];
                dslCombo.add( sen.toString() );
            }

            dslCombo.select( 0 );

            dslCombo.addListener( SWT.Selection,
                                  new Listener() {
                                      public void handleEvent(Event event) {
                                          if ( dslCombo.getSelectionIndex() == 0 ) {
                                              return;
                                          }

                                          DSLSentence sentence = getCompletion().getDSLConditions()[dslCombo.getSelectionIndex() - 1];
                                          // TODO Handle this kind of situations with care - add* can
                                          // throw runtime exceptions
                                          modeller.getModel().addLhsItem( sentence.copy() );

                                          modeller.reloadLhs();
                                          modeller.setDirty( true );
                                          close();
                                      }
                                  } );

        }
    }

    public IPattern getPattern() {
        return pattern;
    }

    private SuggestionCompletionEngine getCompletion() {
        return modeller.getSuggestionCompletionEngine();
    }

}

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

package org.drools.eclipse.editors.outline;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.compiler.DroolsParserException;
import org.drools.eclipse.DRLInfo;
import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.core.DroolsElement;
import org.drools.eclipse.core.DroolsModelBuilder;
import org.drools.eclipse.core.Package;
import org.drools.eclipse.core.RuleSet;
import org.drools.eclipse.core.ui.DroolsContentProvider;
import org.drools.eclipse.core.ui.DroolsGroupByRuleGroupContentProvider;
import org.drools.eclipse.core.ui.DroolsLabelProvider;
import org.drools.eclipse.core.ui.DroolsTreeSorter;
import org.drools.eclipse.core.ui.FilterActionGroup;
import org.drools.eclipse.editors.AbstractRuleEditor;
import org.drools.lang.descr.AttributeDescr;
import org.drools.lang.descr.PackageDescr;
import org.drools.lang.descr.RuleDescr;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

/**
 * Simple outline view of a DRL file. At present this is not wired in with the Parser, so it is fault
 * tolerant of incorrect syntax. 
 * Should provide navigation assistance in large rule files.
 */
public class RuleContentOutlinePage extends ContentOutlinePage {

    private AbstractRuleEditor editor;
    private RuleSet ruleSet = DroolsModelBuilder.createRuleSet();
    private Map<String, RuleDescr> rules;
    
    private boolean groupByRulegroup = false;
    private TreeViewer viewer = null;
    ///////////////////////////////////
    // Patterns that the parser uses
    // TODO: this should just reuse the existing parser to avoid inconsistencies
    //       with for example comments
    ///////////////////////////////////
    private static final Pattern RULE_PATTERN1 = Pattern.compile(
            "\\n\\s*rule\\s+\"([^\"]+)\"", Pattern.DOTALL);

    private static final Pattern RULE_PATTERN2 = Pattern.compile(
            "\\n\\s*rule\\s+([^\\s;#\"]+)", Pattern.DOTALL);

    private static final Pattern PACKAGE_PATTERN = Pattern.compile(
            "\\s*package\\s+([^\\s;#]+);?", Pattern.DOTALL);

    private static final Pattern FUNCTION_PATTERN = Pattern.compile(
            "\\n\\s*function\\s+(\\S+)\\s+(\\S+)\\(.*?\\)", Pattern.DOTALL);

    private static final Pattern TEMPLATE_PATTERN = Pattern.compile(
            "\\n\\s*template\\s+([^\\s;#\"]+)", Pattern.DOTALL);

    private static final Pattern IMPORT_PATTERN = Pattern.compile(
            "\\n\\s*import\\s+([^\\s;#]+);?", Pattern.DOTALL);

    private static final Pattern EXPANDER_PATTERN = Pattern.compile(
            "\\n\\s*expander\\s+([^\\s;#]+);?", Pattern.DOTALL);

    private static final Pattern GLOBAL_PATTERN = Pattern.compile(
            "\\n\\s*global\\s+(\\S+)\\s+([^\\s;#]+);?", Pattern.DOTALL);

    private static final Pattern QUERY_PATTERN1 = Pattern.compile(
            "\\n\\s*query\\s+\"([^\"]+)\"", Pattern.DOTALL);

    private static final Pattern QUERY_PATTERN2 = Pattern.compile(
            "\\n\\s*query\\s+([^\\s;#\"]+)", Pattern.DOTALL);

    public RuleContentOutlinePage(AbstractRuleEditor editor) {
        this.editor = editor;
    }

    DroolsContentProvider contentProvider = null;
    DroolsGroupByRuleGroupContentProvider groupByRuleGroupContentProvider = null;
    
    private void setContentProvider() {
        IPreferenceStore preferenceStore= DroolsEclipsePlugin.getDefault().getPreferenceStore();
        groupByRulegroup = preferenceStore.getBoolean("GroupByRuleGroupAction.isChecked");

        contentProvider = new DroolsContentProvider();
        groupByRuleGroupContentProvider = new DroolsGroupByRuleGroupContentProvider();

        if (groupByRulegroup) {
            viewer.setContentProvider(groupByRuleGroupContentProvider);
        } else {
            viewer.setContentProvider(contentProvider);
        }
    }
    
    public void createControl(Composite parent) {
        super.createControl(parent);

        viewer = getTreeViewer();
        setContentProvider();
        viewer.setLabelProvider(new DroolsLabelProvider());
        viewer.setSorter(new DroolsTreeSorter());
        viewer.setInput(ruleSet);
        FilterActionGroup filterActionGroup = new FilterActionGroup(
            viewer, "org.drools.eclipse.editors.outline.RuleContentOutlinePage");
        filterActionGroup.fillActionBars(getSite().getActionBars());
        update();

        // add the listener for navigation of the rule document.
        super.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                Object selectionObj = event.getSelection();
                if (selectionObj != null && selectionObj instanceof StructuredSelection) {
                    StructuredSelection sel = (StructuredSelection) selectionObj;
                    DroolsElement element = (DroolsElement) sel.getFirstElement();
                    if (element != null) {
                        editor.selectAndReveal(element.getOffset(),
                                                element.getLength());
                    }
                }
            }
        });
    }

    /**
     * Updates the outline page.
     */
    public void update() {
        TreeViewer viewer = getTreeViewer();
        if (viewer != null) {
            Control control = viewer.getControl();
            if (control != null && !control.isDisposed()) {
                initRules();
                populatePackageTreeNode();
                viewer.refresh();
                control.setRedraw(false);
                viewer.expandToLevel(2);
                control.setRedraw(true);
            }
        }
    }

    /**
     * populates the PackageTreeNode with all of its child elements
     * 
     * @param packageTreeNode the node to populate
     */
    public void populatePackageTreeNode() {
        String ruleFileContents = editor.getContent();
        populatePackageTreeNode(ruleFileContents);
    }
    
    void populatePackageTreeNode(String ruleFileContents) {
        DroolsModelBuilder.clearRuleSet(ruleSet);
        Matcher matcher = PACKAGE_PATTERN.matcher(ruleFileContents);
        String packageName = null;
        int startChar = 0;
        int endChar = 0;
        if (matcher.find()) {
            packageName = matcher.group(1);
            startChar = matcher.start(1);
            endChar = matcher.end(1);
        }
        Package pkg = DroolsModelBuilder.addPackage(ruleSet, packageName,
            startChar, endChar - startChar);

        matcher = RULE_PATTERN1.matcher(ruleFileContents);
        while (matcher.find()) {
            String ruleName = matcher.group(1);
            RuleDescr descr = (RuleDescr) rules.get(ruleName);
            if (descr != null) {
                DroolsModelBuilder.addRule(pkg, ruleName, null,
                    matcher.start(1), matcher.end(1) - matcher.start(1),
                    extractAttributes(descr));
            }
        }
        matcher = RULE_PATTERN2.matcher(ruleFileContents);
        while (matcher.find()) {
            String ruleName = matcher.group(1);
            RuleDescr descr = (RuleDescr) rules.get(ruleName);
            if (descr != null) {
                DroolsModelBuilder.addRule(pkg, ruleName, null,
                    matcher.start(1), matcher.end(1) - matcher.start(1),
                    extractAttributes(descr));
            }
         }
        matcher = FUNCTION_PATTERN.matcher(ruleFileContents);
        while (matcher.find()) {
            String functionName = matcher.group(2);
            DroolsModelBuilder.addFunction(pkg, functionName + "()", null,
                matcher.start(2), matcher.end(2) - matcher.start(2));
        }
        matcher = EXPANDER_PATTERN.matcher(ruleFileContents);
        if (matcher.find()) {
            String expanderName = matcher.group(1);
            DroolsModelBuilder.addExpander(pkg, expanderName, null,
                matcher.start(1), matcher.end(1) - matcher.start(1));
        }
        matcher = IMPORT_PATTERN.matcher(ruleFileContents);
        while (matcher.find()) {
            String importName = matcher.group(1);
            DroolsModelBuilder.addImport(pkg, importName, null,
                matcher.start(1), matcher.end(1) - matcher.start(1));
        }
        matcher = GLOBAL_PATTERN.matcher(ruleFileContents);
        while (matcher.find()) {
            String globalType = matcher.group(1);
            String globalName = matcher.group(2);
            String name = globalName + " : " + globalType;
            DroolsModelBuilder.addGlobal(pkg, name, null,
                matcher.start(2), matcher.end(2) - matcher.start(2));
        }
        matcher = QUERY_PATTERN1.matcher(ruleFileContents);
        while (matcher.find()) {
            String queryName = matcher.group(1);
            DroolsModelBuilder.addQuery(pkg, queryName, null,
                    matcher.start(1), matcher.end(1) - matcher.start(1));
        }
        matcher = QUERY_PATTERN2.matcher(ruleFileContents);
        while (matcher.find()) {
            String queryName = matcher.group(1);
            DroolsModelBuilder.addQuery(pkg, queryName, null,
                matcher.start(1), matcher.end(1) - matcher.start(1));
        }
        matcher = TEMPLATE_PATTERN.matcher(ruleFileContents);
        while (matcher.find()) {
            String templateName = matcher.group(1);
            DroolsModelBuilder.addTemplate(pkg, templateName, null,
                    matcher.start(1), matcher.end(1) - matcher.start(1));
        }
    }
    
    RuleSet getRuleSet() {
        return ruleSet;
    }
    
    private Map<String, String> extractAttributes(RuleDescr ruleDescr) {
        Map<String, String> attributes = null;
        if (ruleDescr != null) {
            attributes = new HashMap<String, String>();
            for (AttributeDescr attribute: ruleDescr.getAttributes().values()) {
                if (attribute != null && attribute.getName() != null) {
                    attributes.put(attribute.getName(), attribute.getValue());
                }
            }
        }
        return attributes;
    }

    public void initRules() {
        rules = new HashMap<String, RuleDescr>();
        try {
            DRLInfo drlInfo = DroolsEclipsePlugin.getDefault().parseResource(editor, true, false);
            if (drlInfo != null) {
                PackageDescr packageDescr = drlInfo.getPackageDescr();
                if (packageDescr != null) {
                    for (RuleDescr ruleDescr: packageDescr.getRules()) {
                        if (ruleDescr != null && ruleDescr.getName() != null) {
                            rules.put(ruleDescr.getName(), ruleDescr);
                        }
                    }
                }
            }
        } catch (DroolsParserException e) {
            DroolsEclipsePlugin.log(e);
        }
    }
    
    class GroupByRuleGroupAction extends Action {

        public GroupByRuleGroupAction() {
            super();
            setText("Group by Rule Group");
            setToolTipText("Group by Rule Group");
            setDescription("Group by agenda-group, activation-group or ruleflow-group");
            setChecked(groupByRulegroup);
        }

        @Override
        public void run() {
            setGroupByRuleGroup(!groupByRulegroup);
        }

        private void setGroupByRuleGroup(boolean groupBy) {
            groupByRulegroup = groupBy;
            setChecked(groupBy);

            IPreferenceStore preferenceStore= DroolsEclipsePlugin.getDefault().getPreferenceStore();
            preferenceStore.setValue("GroupByRuleGroupAction.isChecked", groupBy);

            setContentProvider();
            viewer.refresh(true);

        }

    }

    @Override
    public void makeContributions(IMenuManager menuManager,
            IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
        // TODO Auto-generated method stub
        GroupByRuleGroupAction groupByAction = new GroupByRuleGroupAction ();
        menuManager.add(groupByAction);
        super.makeContributions(menuManager, toolBarManager, statusLineManager);
    }
}

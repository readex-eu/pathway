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

package org.drools.eclipse.core;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IFile;

public class DroolsModelBuilder {

    public static RuleSet createRuleSet() {
        return new RuleSet();
    }

    public static Package createPackage(String packageName, int offset,
            int length) {
        Package pkg = new Package(null, packageName);
        pkg.setFile(null, offset, length);
        return pkg;
    }

    public static Package addPackage(RuleSet ruleSet, String packageName,
            int offset, int length) {
        Package pkg = new Package(ruleSet, packageName);
        pkg.setFile(null, offset, length);
        ruleSet.addPackage(pkg);
        return pkg;
    }

    public static void removePackage(Package pkg) {
        RuleSet ruleSet = pkg.getParentRuleSet();
        if (ruleSet != null) {
            ruleSet.removePackage(pkg.getPackageName());
        }
    }

    public static void clearRuleSet(RuleSet ruleSet) {
        ruleSet.clear();
    }

    public static Rule addRule(Package pkg, String ruleName, IFile file,
            int offset, int length, Map attributes) {
        Rule rule = new Rule(pkg, ruleName);
        rule.setFile(file, offset, length);
        if (attributes != null) {
            for (Iterator iterator = attributes.entrySet().iterator(); iterator
                    .hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String attributeKey = (String) entry.getKey();
                String attributeValue = (String) entry.getValue();
                RuleAttribute attribute = new RuleAttribute(rule, attributeKey,
                        attributeValue);
                attribute.setFile(file, offset, length);
                rule.addAttribute(attribute);
                // TODO Create constant for Attribute ??
                RuleGroup group = null;
                if (attributeKey.equals("agenda-group")) {
                    group = pkg.getGroup(attributeValue,
                            DroolsElement.AGENDA_GROUP);
                    if (group != null) {
                        group.addRule(rule);
                    } else {
                        group = new AgendaGroup(pkg, rule, attributeValue);
                    }
                }
                if (attributeKey.equals("ruleflow-group")) {
                    group = pkg.getGroup(attributeValue,
                            DroolsElement.RULEFLOW_GROUP);
                    if (group != null) {
                        group.addRule(rule);
                    } else {
                        group = new RuleFlowGroup(pkg, rule, attributeValue);
                    }
                }
                if (attributeKey.equals("activation-group")) {
                    group = pkg.getGroup(attributeValue,
                            DroolsElement.ACTIVATION_GROUP);
                    if (group != null) {
                        group.addRule(rule);
                    } else {
                        group = new ActivationGroup(pkg, rule, attributeValue);
                    }
                }
                if (group != null) {
                    rule.setGroup(group);
                    group.setFile(file, offset, length);
                }

            }
            if (rule.getGroup() == null) {
                pkg.getDefaultGroup().addRule(rule);
            }
        }
        pkg.addRule(rule);
        return rule;
    }

    public static void removeRule(Rule rule) {
        Package pkg = rule.getParentPackage();
        if (pkg != null) {
            pkg.removeRule(rule);
            if (pkg.getChildren().length == 0) {
                removePackage(pkg);
            }
        }
    }

    public static Function addFunction(Package pkg, String functionName,
            IFile file, int offset, int length) {
        Function function = new Function(pkg, functionName);
        function.setFile(file, offset, length);
        pkg.addFunction(function);
        return function;
    }

    public static void removeFunction(Function function) {
        Package pkg = function.getParentPackage();
        if (pkg != null) {
            pkg.removeFunction(function);
        }
    }

    public static void addExpander(Package pkg, String expanderName,
            IFile file, int offset, int length) {
        Expander expander = new Expander(pkg, expanderName);
        expander.setFile(file, offset, length);
        pkg.addExpander(expander);
    }

    public static void removeExpander(Expander expander) {
        Package pkg = expander.getParentPackage();
        if (pkg != null) {
            pkg.removeExpander(expander);
        }
    }

    public static void addImport(Package pkg, String importClass, IFile file,
            int offset, int length) {
        Import imp = new Import(pkg, importClass);
        imp.setFile(file, offset, length);
        pkg.addImport(imp);
    }

    public static void removeImport(Import imp) {
        Package pkg = imp.getParentPackage();
        if (pkg != null) {
            pkg.removeImport(imp);
        }
    }

    public static Global addGlobal(Package pkg, String globalName, IFile file,
            int offset, int length) {
        Global global = new Global(pkg, globalName);
        global.setFile(file, offset, length);
        pkg.addGlobal(global);
        return global;
    }

    public static void removeGlobal(Global global) {
        Package pkg = global.getParentPackage();
        if (pkg != null) {
            pkg.removeGlobal(global);
        }
    }

    public static Query addQuery(Package pkg, String queryName, IFile file,
            int offset, int length) {
        Query query = new Query(pkg, queryName);
        query.setFile(file, offset, length);
        pkg.addQuery(query);
        return query;
    }

    public static void removeQuery(Query query) {
        Package pkg = query.getParentPackage();
        if (pkg != null) {
            pkg.removeQuery(query);
        }
    }

    public static Template addTemplate(Package pkg, String templateName,
            IFile file, int offset, int length) {
        Template template = new Template(pkg, templateName);
        template.setFile(file, offset, length);
        pkg.addTemplate(template);
        return template;
    }

    public static void removeTemplate(Template template) {
        Package pkg = template.getParentPackage();
        if (pkg != null) {
            pkg.removeTemplate(template);
        }
    }

    public static Process addProcess(Package pkg, String processId, IFile file) {
        Process process = new Process(pkg, processId);
        process.setFile(file, -1, -1);
        pkg.addProcess(process);
        return process;
    }

    public static void removeProcess(Process process) {
        Package pkg = process.getParentPackage();
        if (pkg != null) {
            pkg.removeProcess(process);
        }
    }

    public static void removeElement(DroolsElement element) {
        switch (element.getType()) {
        case DroolsElement.RULESET:
            clearRuleSet((RuleSet) element);
            break;
        case DroolsElement.PACKAGE:
            removePackage((Package) element);
            break;
        case DroolsElement.RULE:
            removeRule((Rule) element);
            removePackageIfEmpty(((Rule) element).getParentPackage());
            break;
        case DroolsElement.QUERY:
            removeQuery((Query) element);
            removePackageIfEmpty(((Query) element).getParentPackage());
            break;
        case DroolsElement.FUNCTION:
            removeFunction((Function) element);
            removePackageIfEmpty(((Function) element).getParentPackage());
            break;
        case DroolsElement.TEMPLATE:
            removeTemplate((Template) element);
            removePackageIfEmpty(((Template) element).getParentPackage());
            break;
        case DroolsElement.EXPANDER:
            removeExpander((Expander) element);
            removePackageIfEmpty(((Expander) element).getParentPackage());
            break;
        case DroolsElement.GLOBAL:
            removeGlobal((Global) element);
            removePackageIfEmpty(((Global) element).getParentPackage());
            break;
        case DroolsElement.PROCESS:
            removeProcess((Process) element);
            removePackageIfEmpty(((Process) element).getParentPackage());
            break;
        }
    }

    private static void removePackageIfEmpty(Package pkg) {
        if (pkg.getChildren().length == 0) {
            removePackage(pkg);
        }
    }

}

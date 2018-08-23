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

package org.drools.eclipse.core.ui;

import org.drools.eclipse.core.DroolsElement;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter for the rules viewer.
 */
public class DroolsFilter extends ViewerFilter {

    public static final int FILTER_RULES = 1;
    public static final int FILTER_QUERIES = 2;
    public static final int FILTER_FUNCTIONS = 4;
    public static final int FILTER_TEMPLATES = 8;
    public static final int FILTER_GLOBALS = 16;
    public static final int FILTER_GROUPS = 32;

    private int filterProperties;

    public final void addFilter(int filter) {
        filterProperties |= filter;
    }

    public final void removeFilter(int filter) {
        filterProperties &= (-1 ^ filter);
    }

    public final boolean hasFilter(int filter) {
        return (filterProperties & filter) != 0;
    }

    public boolean isFilterProperty(Object element, Object property) {
        return false;
    }

    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof DroolsElement) {
            DroolsElement droolsElement = (DroolsElement) element;
            int type = droolsElement.getType();
            if (hasFilter(FILTER_RULES) && type == DroolsElement.RULE) {
                return false;
            }
            if (hasFilter(FILTER_QUERIES) && type == DroolsElement.QUERY) {
                return false;
            }
            if (hasFilter(FILTER_FUNCTIONS) && type == DroolsElement.FUNCTION) {
                return false;
            }
            if (hasFilter(FILTER_TEMPLATES) && type == DroolsElement.TEMPLATE) {
                return false;
            }
            if (hasFilter(FILTER_GLOBALS) && type == DroolsElement.GLOBAL) {
                return false;
            }
            if (hasFilter(FILTER_GROUPS) && ((type == DroolsElement.AGENDA_GROUP)||(type == DroolsElement.RULEFLOW_GROUP)||(type == DroolsElement.ACTIVATION_GROUP)||(type == DroolsElement.DEFAULT_RULE_GROUP))) {
                return false;
            }
        }
        return true;
    }

}

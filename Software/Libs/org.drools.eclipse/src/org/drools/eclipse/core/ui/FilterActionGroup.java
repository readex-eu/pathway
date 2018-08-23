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

import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.DroolsPluginImages;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Action Group that contributes filter buttons.
 */
public class FilterActionGroup extends ActionGroup {

    private FilterAction[] filterActions;
    private DroolsFilter filter;
    public int i;

    private StructuredViewer viewer;
    private String viewerId;

    public FilterActionGroup(StructuredViewer viewer, String viewerId) {
        this.viewer = viewer;
        this.viewerId = viewerId;
        filter = new DroolsFilter();
        filterActions = new FilterAction[] {
            addFilterForType(DroolsFilter.FILTER_RULES, "Hide Rules", DroolsPluginImages.RULE),
            addFilterForType(DroolsFilter.FILTER_QUERIES, "Hide Queries", DroolsPluginImages.QUERY),
            addFilterForType(DroolsFilter.FILTER_FUNCTIONS, "Hide Functions", DroolsPluginImages.METHOD),
            addFilterForType(DroolsFilter.FILTER_GLOBALS, "Hide Globals", DroolsPluginImages.GLOBAL),
            addFilterForType(DroolsFilter.FILTER_TEMPLATES, "Hide Templates", DroolsPluginImages.CLASS),
            addFilterForType(DroolsFilter.FILTER_GROUPS, "Hide Agenda/RuleFlow Groups", DroolsPluginImages.GROUPS),
        };
        viewer.addFilter(filter);
    }

    private FilterAction addFilterForType(int filterType, String tooltip, String imageDescriptorKey) {
        boolean filterEnabled = DroolsEclipsePlugin.getDefault()
            .getPreferenceStore().getBoolean(getPreferenceKey(filterType));
        if (filterEnabled) {
            filter.addFilter(filterType);
        }
        FilterAction hideAction = new FilterAction(this, tooltip, filterType, filterEnabled, imageDescriptorKey);
        hideAction.setToolTipText(tooltip);
        return hideAction;
    }

    private String getPreferenceKey(int filterProperty) {
        return "DroolsFilterActionGroup." + viewerId + '.' + String.valueOf(filterProperty);
    }

    public void setFilter(int filterType, boolean set) {
        setDroolsFilters(new int[] {filterType}, new boolean[] {set}, true);
    }

    private void setDroolsFilters(int[] propertyKeys, boolean[] propertyValues, boolean refresh) {
        if (propertyKeys.length == 0)
            return;
        Assert.isTrue(propertyKeys.length == propertyValues.length);

        for (int i= 0; i < propertyKeys.length; i++) {
            int filterProperty= propertyKeys[i];
            boolean set= propertyValues[i];

            IPreferenceStore store = DroolsEclipsePlugin.getDefault().getPreferenceStore();
            boolean found = false;
            for (int j= 0; j < filterActions.length; j++) {
                int currProperty= filterActions[j].getFilterType();
                if (currProperty == filterProperty) {
                    filterActions[j].setChecked(set);
                    found= true;
                    store.setValue(getPreferenceKey(filterProperty), set);
                }
            }
            if (found) {
                if (set) {
                    filter.addFilter(filterProperty);
                } else {
                    filter.removeFilter(filterProperty);
                }
            }
        }
        if (refresh) {
            viewer.getControl().setRedraw(false);
            BusyIndicator.showWhile(viewer.getControl().getDisplay(), new Runnable() {
                public void run() {
                    viewer.refresh();
                }
            });
            viewer.getControl().setRedraw(true);
        }
    }

    public boolean hasDroolsFilter(int filterType) {
        return filter.hasFilter(filterType);
    }

    public void fillActionBars(IActionBars actionBars) {
        contributeToToolBar(actionBars.getToolBarManager());
    }

    public void contributeToToolBar(IToolBarManager tbm) {
        for (int i= 0; i < filterActions.length; i++) {
            tbm.add(filterActions[i]);
        }
    }

}

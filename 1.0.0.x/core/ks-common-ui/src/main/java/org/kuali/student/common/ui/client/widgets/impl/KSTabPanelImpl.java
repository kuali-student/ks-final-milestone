/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTabPanelAbstract;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.Widget;

public class KSTabPanelImpl extends KSTabPanelAbstract {
    
    private final DecoratedTabPanel panel = new DecoratedTabPanel();
    
    public KSTabPanelImpl() {
        super();
        panel.addStyleName(KSStyles.KS_TAB_PANEL);
        TabBar tabBar = panel.getTabBar();
        tabBar.addStyleName(KSStyles.KS_TAB_PANEL_BAR_ITEM);
        initWidget(panel);
    }

    @Override
    public void addTab(Widget w, String tabText){
        panel.add(w, tabText);
    }
    
    @Override
    public void addTab(Widget w, Widget tab){
        panel.add(w, tab);
    }

    @Override
    public boolean removeTab(int index) {
        return panel.remove(index);
    }
    
    @Override
    public void removeTab(Widget widget) {
        panel.remove(widget);
    }
    
    @Override
    public void selectTab(int index) {
        panel.selectTab(index);
    }
    
    @Override
    public int getTabCount() {
        return panel.getWidgetCount();
    }
    
    @Override
    public void addStyleName(String style) {
        panel.addStyleName(style);
    }


    @Override
    public void addSelectionHandler(SelectionHandler<Integer> handler) {
        panel.addSelectionHandler(handler);
    }
    
    @Override
    public int getWidgetIndex(Widget widget) {
        return panel.getWidgetIndex(widget);
    }
}

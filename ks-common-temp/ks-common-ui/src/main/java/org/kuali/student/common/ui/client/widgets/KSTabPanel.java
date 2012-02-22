/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSTabPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
@Deprecated
public class KSTabPanel extends KSTabPanelAbstract {

    private KSTabPanelAbstract panel = GWT.create(KSTabPanelImpl.class);

    /**
     * This constructs a ...
     * 
     */
    public KSTabPanel() {
        initWidget(panel);
    }

    public void addStyleName(String style) {
        panel.addStyleName(style);
    }

    public int getTabCount() {
        return panel.getTabCount();
    }

    public void selectTab(int index) {
        panel.selectTab(index);
    }

    public void removeTab(Widget widget) {
        panel.removeTab(widget);
    }

    public boolean removeTab(int index) {
        return panel.removeTab(index);
    }

    public void addTab(Widget w, String tabText) {
        panel.addTab(w, tabText);
    }

    public void addTab(Widget w, Widget tab) {
        panel.addTab(w, tab);
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

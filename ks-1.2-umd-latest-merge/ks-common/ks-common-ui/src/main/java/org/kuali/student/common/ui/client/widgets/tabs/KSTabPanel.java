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

package org.kuali.student.common.ui.client.widgets.tabs;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.tabs.impl.KSTabPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class KSTabPanel extends KSTabPanelAbstract {
	
	public static enum TabPosition{LEFT, RIGHT};
	public static enum TabPanelStyle{FULL_PAGE, SMALL}

    private KSTabPanelAbstract panel = GWT.create(KSTabPanelImpl.class);

    public KSTabPanel(){
    	this.initWidget(panel);
    }
    
    public KSTabPanel(TabPanelStyle style){
    	panel.setTabPanelStyle(style);
    	this.initWidget(panel);
    }

	@Override
	public void addTab(String key, Widget tabWidget, Widget content,
			TabPosition position) {
		panel.addTab(key, tabWidget, content, position);
		
	}

	@Override
	public void addTab(String key, String label, Widget content,
			TabPosition position) {
		panel.addTab(key, label, content, position);
		
	}

	@Override
	public void addTab(String key, String label, Image image, Widget content,
			TabPosition position) {
		panel.addTab(key, label, image, content, position);
		
	}

	@Override
	public void addTab(String key, String label, Image image, Widget content) {
		panel.addTab(key, label, image, content);
		
	}

	@Override
	public void addTab(String key, String label, Widget content) {
		panel.addTab(key, label, content);
		
	}

	@Override
	public void addTab(String key, Widget tabWidget, Widget content) {
		panel.addTab(key, tabWidget, content);
		
	}

	@Override
	public void addTabCustomCallback(String key, Callback<String> callback) {
		panel.addTabCustomCallback(key, callback);
		
	}

	@Override
	public String getSelectedTabKey() {
		return panel.getSelectedTabKey();
	}
	
	@Override
    public Widget getSelectedTab() {
        return panel.getSelectedTab();
    }
	
	@Override
    public String getSelectedTabName() {
        return panel.getSelectedTabName();
    }

	@Override
	public void removeTab(String key) {
		panel.removeTab(key);
		
	}

	@Override
	public void removeTabCustomCallbacks(String key) {
		panel.removeTabCustomCallbacks(key);
		
	}

	@Override
	public void selectTab(String key) {
		panel.selectTab(key);
		
	}

	@Override
	public void addStyleName(String style) {
		panel.addStyleName(style);
		
	}

	@Override
	public int getTabCount() {
		return panel.getTabCount();
	}

	@Override
	public boolean hasTabKey(String key) {
		return panel.hasTabKey(key);
	}

	@Override
	public void setTabPanelStyle(TabPanelStyle style) {
		panel.setTabPanelStyle(style);
	}

}

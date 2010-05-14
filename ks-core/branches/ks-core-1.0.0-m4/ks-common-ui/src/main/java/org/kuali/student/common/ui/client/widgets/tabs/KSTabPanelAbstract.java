/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.tabs;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel.TabPosition;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSTabPanelAbstract extends Composite {
	
	public abstract void addTab(String key, Widget tabWidget, Widget content, TabPosition position);
	
	public abstract void addTab(String key, String label, Widget content, TabPosition position);
	
	public abstract void addTab(String key, String label, KSImage image, Widget content, TabPosition position);
	
	public abstract void addTab(String key, String label, KSImage image, Widget content);
	
	public abstract void addTab(String key, String label, Widget content);
	
	public abstract void addTab(String key, Widget tabWidget, Widget content);

	public abstract void selectTab(String key);
	
	public abstract void removeTab(String key);
	
    public abstract void addStyleName(String style);

    public abstract int getTabCount();
    
    public abstract void addTabCustomCallback(String key, Callback<String> callback);
    
    public abstract void removeTabCustomCallbacks(String key);
    
    public abstract String getSelectedTabKey();

    public abstract boolean hasTabKey(String key);

}

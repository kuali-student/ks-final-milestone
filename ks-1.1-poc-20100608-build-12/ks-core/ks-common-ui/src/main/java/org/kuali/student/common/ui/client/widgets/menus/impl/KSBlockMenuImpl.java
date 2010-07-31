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

package org.kuali.student.common.ui.client.widgets.menus.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class KSBlockMenuImpl extends Composite{
	private FlowPanel layout = new FlowPanel();
	private FlowPanel container = new FlowPanel();

	public KSBlockMenuImpl(){
		//layout.setStyleName("KS-Block-Menu");
		layout.setStyleName("ks-page-sub-navigation");
		container.add(layout);
		layout.add(new HTMLPanel("<div class=\"clear\">&nbsp;</div>"));
		this.initWidget(container);
	}

	public void addMenu(KSListMenuImpl menu){
		menu.setStyleName("ks-page-sub-navigation-menu");
		layout.insert(menu, layout.getWidgetCount() -1);
		//layout.add(menu);

	}

	public void setMenus(List<KSListMenuImpl> menus){
		for(KSListMenuImpl menu: menus){
			this.addMenu(menu);
		}
	}

	/**
	 * Use the top level menu items of a list of KSMenuItemData to generate separate menus in a block
	 * layout
	 * @param data
	 */
	public void setTopLevelItems(List<KSMenuItemData> data){
		for(KSMenuItemData item: data){
			List<KSMenuItemData> list = new ArrayList<KSMenuItemData>();
			list.add(item);
			KSListMenuImpl menu = new KSListMenuImpl();
			menu.setItems(list);
			this.addMenu(menu);
		}
	}
}

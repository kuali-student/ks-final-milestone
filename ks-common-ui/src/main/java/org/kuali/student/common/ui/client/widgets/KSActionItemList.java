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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSActionItemList extends Composite{
	private KSLabel title = new KSLabel();
	private KSListPanel listLayout = new KSListPanel();
	private List<ActionItem> actionItems = new ArrayList<ActionItem>();

	public static enum ListLocation{TOP, BOTTOM}

	public class ActionItem{
		private String text;
		private String shortDesc;
		private ClickHandler handler;

		//TODO actually LINK widget
		private KSLabel link = new KSLabel(text);
		private KSLabel desc = new KSLabel(shortDesc);
		private HorizontalPanel layout = new HorizontalPanel();

		public ActionItem(String text, String shortDesc,
				ClickHandler handler) {

			link.addStyleName("KS-ActionItemList-Link");
			desc.addStyleName("KS-ActionItemList-Desc");
			this.text = text;
			this.shortDesc = shortDesc;
			this.handler = handler;
		}

		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getShortDesc() {
			return shortDesc;
		}
		public void setShortDesc(String shortDesc) {
			this.shortDesc = shortDesc;
		}
		public ClickHandler getHandler() {
			return handler;
		}
		public void setHandler(ClickHandler handler) {
			this.handler = handler;
		}

		public Widget getWidget(){
			String id = HTMLPanel.createUniqueId();
		    HTMLPanel panel = new HTMLPanel("<a href='javascript:return false;' id='" + id + "'></a>");
		    panel.add(link, id);
			link.setText(text);
			link.addClickHandler(handler);
			desc.setText(shortDesc);
			layout.clear();
			layout.add(panel);
			layout.add(desc);
			return layout;
		}
	}

	public KSActionItemList(String title){
		this.title.setText(title);
		listLayout.addStyleName("KS-ActionItemList-ListPanel");
		this.title.addStyleName("KS-ActionItemList-Title");
		redrawList();
		this.initWidget(listLayout);
	}

	public KSActionItemList(String title, List<ActionItem> items){
		this.title.setText(title);
		listLayout.addStyleName("KS-ActionItemList-ListPanel");
		this.title.addStyleName("KS-ActionItemList-Title");
		actionItems = items;
		redrawList();
		this.initWidget(listLayout);
	}

	public void redrawList(){
		listLayout.clear();
		if(!title.getText().isEmpty()){
			listLayout.add(title);
		}
		for(ActionItem item: actionItems){
			listLayout.add(item.getWidget());
		}

	}

	public void setTitle(String title){
		this.title.setText(title);
	}

	public void add(ActionItem item, ListLocation location){
		if(location == ListLocation.TOP){
			actionItems.add(0, item);
		}
		else{
			actionItems.add(item);
		}
		//redrawList();
	}

	public void add(String text, String shortDesc, ClickHandler handler, ListLocation location){
		ActionItem item = new ActionItem(text, shortDesc, handler);
		if(location == ListLocation.TOP){
			actionItems.add(0, item);
		}
		else{
			actionItems.add(item);
		}
		redrawList();
	}

	public void setActionItems(List<ActionItem> items){
		actionItems = items;
		redrawList();
	}

	public void remove(ActionItem item){
		actionItems.remove(item);
		redrawList();
	}

	public void removeByText(String text){
		for(ActionItem item: actionItems){
			if(text.equals(item.getText())){
				actionItems.remove(item);
				break;
			}
		}
		redrawList();
	}



}

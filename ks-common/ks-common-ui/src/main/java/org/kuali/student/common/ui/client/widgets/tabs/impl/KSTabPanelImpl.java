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

package org.kuali.student.common.ui.client.widgets.tabs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.ClickablePanel;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanelAbstract;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel.TabPanelStyle;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel.TabPosition;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class KSTabPanelImpl extends KSTabPanelAbstract{
	
	private VerticalFlowPanel container = new VerticalFlowPanel();
	private FlowPanel tabRow = new FlowPanel();
	//private HorizontalPanel left = new HorizontalPanel();
	//private HorizontalPanel right = new HorizontalPanel();
	private KSListPanel left = new KSListPanel();
	private KSListPanel right = new KSListPanel();
	private SimplePanel content = new SimplePanel();
	private Tab selectedTab;
	private Map<String, Tab> tabMap = new HashMap<String, Tab>();
	

	
/*	protected class TabPanel extends SimplePanel implements HasAllMouseHandlers{

		@Override
		public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HandlerRegistration addMouseWheelHandler(
				MouseWheelHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		
	}*/
	
	protected class Tab extends Composite{
		private boolean selected = false;
		private Widget tab;
		private ClickablePanel tabPanel = new ClickablePanel();
		private Widget displayContent;
		private int labelIndex = -1;
		private String tabKey = "";
		private List<Callback<String>> callbacks = new ArrayList<Callback<String>>();
		
		public Tab(String key, String label, KSImage image, Widget displayContent){
			tabKey = key;
			HorizontalPanel tabContent = new HorizontalPanel();
			tabContent.add(image);
			KSLabel labelWidget = new KSLabel(label);
			tabContent.add(labelWidget);
			labelIndex = tabContent.getWidgetIndex(labelWidget);
			init(tabContent, displayContent);
			
		}
		
		public Tab(String key, String label, Widget displayContent){
			tabKey = key;
			KSLabel labelWidget = new KSLabel(label);
			init(labelWidget, displayContent);
		}
		
		public Tab(String key, Widget tab, Widget displayContent){
			tabKey = key;
			init(tab, displayContent);
		}
		
		public void addCallback(Callback<String> callback) {
			callbacks.add(callback);
		}
		
		public void emptyCallbacks(){
			callbacks = new ArrayList<Callback<String>>();
		}

		public String getTabKey() {
			return tabKey;
		}

		public void init(Widget tab, Widget displayContent){
			this.tab = tab;
			this.tabPanel.setWidget(tab);
			tabPanel.addMouseOverHandler(new MouseOverHandler(){

				@Override
				public void onMouseOver(MouseOverEvent event) {
					Tab.this.onMouseOver();
					
				}
			});
			tabPanel.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Tab.this.onSelect();
					if(!callbacks.isEmpty()){
						for(Callback<String> callback: callbacks){
							callback.exec(tabKey);
						}
					}
				}
			});
			tabPanel.addMouseOutHandler(new MouseOutHandler(){

				@Override
				public void onMouseOut(MouseOutEvent event) {
					Tab.this.onMouseOut();
					
				}
			});
			tabPanel.addStyleName("KS-TabPanel-Tab");
			this.initWidget(tabPanel);
			this.displayContent = displayContent;
		}
		
		public void addTabStyleName(String style){
			tabPanel.addStyleName(style);
		}
		
		public void onSelect(){
			KSTabPanelImpl.this.deselectCurrentTab();
			KSTabPanelImpl.this.selectedTab = Tab.this;
			selected = true;
			this.onMouseOut();
			tabPanel.addStyleName("KS-TabPanel-Tab-Selected");
			if(tab instanceof Label || tab instanceof KSLabel){
				tab.addStyleName("KS-TabPanel-Tab-Label-Selected");
			}
			else if(tab instanceof ComplexPanel){
				if(labelIndex != -1){
					Widget w = ((ComplexPanel) tab).getWidget(labelIndex);
					w.addStyleName("KS-TabPanel-Tab-Label-Selected");
				}
			}
			KSTabPanelImpl.this.content.clear();
			KSTabPanelImpl.this.content.setWidget(displayContent);
		}
		
		public void onDeselect(){
			selected = false;
			tabPanel.removeStyleName("KS-TabPanel-Tab-Selected");
			if(tab instanceof Label || tab instanceof KSLabel){
				tab.removeStyleName("KS-TabPanel-Tab-Label-Selected");
			}
			else if(tab instanceof ComplexPanel){
				if(labelIndex != -1){
					Widget w = ((ComplexPanel) tab).getWidget(labelIndex);
					w.removeStyleName("KS-TabPanel-Tab-Label-Selected");
				}
			}
		}
		
		public void onMouseOver(){
			if(!selected){
				tabPanel.addStyleName("KS-TabPanel-Tab-Hover");
				if(tab instanceof Label || tab instanceof KSLabel){
					tab.addStyleName("KS-TabPanel-Tab-Label-Hover");
				}
				else if(tab instanceof ComplexPanel){
					if(labelIndex != -1){
						Widget w = ((ComplexPanel) tab).getWidget(labelIndex);
						w.addStyleName("KS-TabPanel-Tab-Label-Hover");
					}
				}
			}
		}
		
		public void onMouseOut(){
			tabPanel.removeStyleName("KS-TabPanel-Tab-Hover");
			if(tab instanceof Label || tab instanceof KSLabel){
				tab.removeStyleName("KS-TabPanel-Tab-Label-Hover");
			}
			else if(tab instanceof ComplexPanel){
				if(labelIndex != -1){
					Widget w = ((ComplexPanel) tab).getWidget(labelIndex);
					w.removeStyleName("KS-TabPanel-Tab-Label-Hover");
				}
			}
		}
	}
	
	public KSTabPanelImpl(){
		//left.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		//right.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		//tabRow.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		tabRow.add(left);
		tabRow.add(right);
		tabRow.addStyleName("KS-TabPanel-TabRow");
		left.addStyleName("KS-TabPanel-Left-Panel");
		right.addStyleName("KS-TabPanel-Right-Panel");
		container.addStyleName("KS-TabPanel-Full");
		content.addStyleName("KS-TabPanel-Content");
		container.add(tabRow);
		container.add(content);
		this.initWidget(container);
	}
	
	public KSTabPanelImpl(TabPanelStyle style){
		//left.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		//right.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		//tabRow.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		tabRow.add(left);
		tabRow.add(right);
		tabRow.addStyleName("KS-TabPanel-TabRow");
		left.addStyleName("KS-TabPanel-Left-Panel");
		right.addStyleName("KS-TabPanel-Right-Panel");

		container.addStyleName("KS-TabPanel");
		content.addStyleName("KS-TabPanel-Content");
		container.add(tabRow);
		container.add(content);
		this.initWidget(container);
	}
	
	public void setTabPanelStyle(TabPanelStyle style){
		if(style == TabPanelStyle.FULL_PAGE){
			container.setStyleName("KS-TabPanel-Full");
		}
		else if(style == TabPanelStyle.SMALL){
			container.setStyleName("KS-TabPanel-Small");
		}
	}

	private void deselectCurrentTab() {
		if(selectedTab != null){
			selectedTab.onDeselect();
		}
	}

	@Override
	public void selectTab(String key){
		Tab tab = tabMap.get(key);
		tab.onSelect();
	}

	@Override
	public void removeTab(String key){
		Tab tab = tabMap.get(key);
		tab.removeFromParent();
	}
	
	private void positionTab(Tab tab, TabPosition position){
		if(position == TabPosition.LEFT){
			tab.addStyleName("KS-TabPanel-Tab-Left");
			left.add(tab);
		}
		else{
			tab.addStyleName("KS-TabPanel-Tab-Right");
			right.add(tab);
		}
	}

	@Override
	public void addTab(String key, Widget tabWidget, Widget content, TabPosition position){
		Tab tab = new Tab(key, tabWidget, content);
		tabMap.put(key, tab);
		positionTab(tab, position);
	}

	@Override
	public void addTab(String key, String label, Widget content, TabPosition position){
		Tab tab = new Tab(key, label, content);
		tabMap.put(key, tab);
		positionTab(tab, position);
	}

	@Override
	public void addTab(String key, String label, KSImage image, Widget content, TabPosition position){
		Tab tab = new Tab(key, label, image, content);
		tabMap.put(key, tab);
		positionTab(tab, position);
	}

	@Override
	public void addTab(String key, String label, KSImage image, Widget content){
		Tab tab = new Tab(key, label, image, content);
		tabMap.put(key, tab);
		positionTab(tab, TabPosition.LEFT);
	}

	@Override
	public void addTab(String key, String label, Widget content){
		Tab tab = new Tab(key, label, content);
		tabMap.put(key, tab);
		positionTab(tab, TabPosition.LEFT);
	}
	
	@Override
	public void addTab(String key, Widget tabWidget, Widget content){
		Tab tab = new Tab(key, tabWidget, content);
		tabMap.put(key, tab);
		positionTab(tab, TabPosition.LEFT);
	}

	@Override
	public void addStyleName(String style) {
		container.addStyleName(style);
	}

	@Override
	public void addTabCustomCallback(final String key, final Callback<String> callback) {
		Tab tab = tabMap.get(key);
		if(tab != null){
			tab.addCallback(callback);
		}
		
	}

	@Override
	public String getSelectedTabKey() {
		if(selectedTab != null){
			return selectedTab.getTabKey();
		}
		else{
			return "";
		}
		
	}

	@Override
	public int getTabCount() {
		return tabMap.size();
	}

	@Override
	public void removeTabCustomCallbacks(String key) {
		Tab tab = tabMap.get(key);
		if(tab != null){
			tab.emptyCallbacks();
		}
		
	}

	@Override
	public boolean hasTabKey(String key) {
		return tabMap.containsKey(key);
	}

	
}

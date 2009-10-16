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

import java.util.HashMap;

import org.kuali.student.common.ui.client.widgets.KSFloatPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSSidebar;
import org.kuali.student.common.ui.client.widgets.KSSidebarAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSFloatPanel.FloatLocation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSSidebarImpl extends KSSidebarAbstract{
	private final FocusPanel focusWrapper = new FocusPanel();
	private final HorizontalPanel container = new HorizontalPanel();
	private final HorizontalPanel titlePanel = new HorizontalPanel();
	private final KSLabel titleLabel = new KSLabel();
	private final VerticalPanel tabContainer = new VerticalPanel();
	private final FlowPanel contentContainer = new FlowPanel();
	private final ScrollPanel contentPanel = new ScrollPanel();
	private final KSFloatPanel fp = new KSFloatPanelImpl();
	private int trim = 0;
	private FloatLocation location = FloatLocation.FLOAT_LEFT;
	
	private HashMap<String, TabData> tabMap = new HashMap<String, TabData>();
	
	
	private final ResizeHandler resizeHandler = new ResizeHandler() {
		public void onResize(ResizeEvent event) {
			recalcContentSize();
		}
	};
	
	
	public class TabData{
		private Label tabWidget = new Label();
		private Widget contentWidget;
		private String name;
		private boolean selected = false;
		
		public TabData(Widget contentWidget, String name){
			this.contentWidget = contentWidget;
			this.name = name;
			tabWidget.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					if(selected){
						KSSidebarImpl.this.hideContent();
						setSelected(false);
					}
					else{
						KSSidebarImpl.this.showContent(TabData.this.getContentWidget(), TabData.this.name);
						for(TabData td: tabMap.values()){
							if(td.isSelected()){
								td.setSelected(false);
							}
						}
						setSelected(true);
					}	
				}

			});
			tabWidget.setTitle(name);
			setupDefaultStyle();
		}
		
		private void setupDefaultStyle(){
			if(location == FloatLocation.FLOAT_RIGHT){
				tabWidget.addStyleName(KSStyles.KS_SIDEBAR_RIGHTTAB);
			}
			else{
				tabWidget.addStyleName(KSStyles.KS_SIDEBAR_LEFTTAB);
			}
			
			tabWidget.addMouseOverHandler(new MouseOverHandler(){

				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!selected){
						if(location == FloatLocation.FLOAT_RIGHT){
							tabWidget.addStyleName(KSStyles.KS_SIDEBAR_RIGHTTAB_HOVER);
						}
						else{
							tabWidget.addStyleName(KSStyles.KS_SIDEBAR_LEFTTAB_HOVER);
						}
					}	
				}
				
			});
			tabWidget.addMouseOutHandler(new MouseOutHandler(){

				@Override
				public void onMouseOut(MouseOutEvent event) {
					if(!selected){
						if(location == FloatLocation.FLOAT_RIGHT){
							tabWidget.removeStyleName(KSStyles.KS_SIDEBAR_RIGHTTAB_HOVER);
						}
						else{
							tabWidget.removeStyleName(KSStyles.KS_SIDEBAR_LEFTTAB_HOVER);
						}
					}
				}
			});
		}
		
		public Label getTabWidget() {
			return tabWidget;
		}
		public void setTabWidget(Label tabWidget) {
			this.tabWidget = tabWidget;
		}
		public Widget getContentWidget() {
			return contentWidget;
		}
		public void setContentWidget(Widget contentWidget) {
			this.contentWidget = contentWidget;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
			if(selected){
				if(location == FloatLocation.FLOAT_RIGHT){
					tabWidget.addStyleName(KSStyles.KS_SIDEBAR_RIGHTTAB_SELECTED);
				}
				else{	
					tabWidget.addStyleName(KSStyles.KS_SIDEBAR_LEFTTAB_SELECTED);
				}
			}
			else{
				if(location == FloatLocation.FLOAT_RIGHT){
					tabWidget.setStyleName(KSStyles.KS_SIDEBAR_RIGHTTAB);
				}
				else{
					tabWidget.setStyleName(KSStyles.KS_SIDEBAR_LEFTTAB);
				}
			}
			
		}
		
	}
	
	protected void init(){//KSSidebarImpl(){
		Window.addResizeHandler(resizeHandler);
		container.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		setupDefaultStyle();
		titlePanel.add(titleLabel);
		contentContainer.add(titlePanel);
		contentContainer.add(contentPanel);
		contentContainer.setVisible(false);
		
		container.add(contentContainer);
		container.add(tabContainer);
		fp.setHeightRatio(90);
		fp.setWidget(container);
		
	}
	
	protected void init(FloatLocation location){//KSSidebarImpl(FloatLocation location){
		this.location = location;
		
		Window.addResizeHandler(resizeHandler);
		container.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		setupDefaultStyle();
		titlePanel.add(titleLabel);
		contentContainer.add(titlePanel);
		contentContainer.add(contentPanel);
		contentContainer.setVisible(false);
		
		if(location == FloatLocation.FLOAT_RIGHT)
		{			
			container.add(tabContainer);
			container.add(contentContainer);
			
			fp.setLocation(location);
		}
		else{
	
			container.add(contentContainer);
			container.add(tabContainer);
		}
		
		fp.setHeightRatio(90);
		fp.setWidget(container);
		
	}
	

	
	private void recalcContentSize() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
            	contentPanel.setVisible(false);
            	int beforeResize = contentContainer.getOffsetHeight();
                contentPanel.setHeight(contentContainer.getOffsetHeight() - titlePanel.getOffsetHeight() + "px" );
                contentPanel.setVisible(true);
                int afterResize = contentContainer.getOffsetHeight();
                if(trim == 0){
                	trim = afterResize - beforeResize;
                }
            }
        });
	}

	public void show(){
		fp.show();
		
	}
	
	public void hide(){
		fp.hide();
	}
	
	public void showContent(Widget content, String title) {
		contentContainer.setVisible(true);
		recalcContentSize();
		contentPanel.setWidget(content);
		titleLabel.setText(title);
		//show panel to recalc positoning
		fp.show();
		
	}
	
	public void hideContent() {
		contentPanel.clear();
		contentContainer.setVisible(false);
		//show panel to recalc positoning
		fp.show();
	}


	public void addTab(Widget content, String name){
		TabData theTab = new TabData(content, name);
		tabMap.put(name, theTab);
		tabContainer.add(theTab.getTabWidget());
	}
	
	public void removeTab(String name){
		TabData theTab = tabMap.get(name);
		tabContainer.remove(theTab.getTabWidget());
		tabMap.remove(name);
	}
	
	private void setupDefaultStyle(){
		fp.setStyleName(KSStyles.KS_SIDEBAR_FLOAT_PANEL);
		container.addStyleName(KSStyles.KS_SIDEBAR);
		contentPanel.addStyleName(KSStyles.KS_SIDEBAR_CONTENT_PANEL);
		contentContainer.addStyleName(KSStyles.KS_SIDEBAR_CONTENT_WRAPPER);
		titlePanel.addStyleName(KSStyles.KS_SIDEBAR_TITLE_PANEL);
		tabContainer.addStyleName(KSStyles.KS_SIDEBAR_TAB_PANEL);
		titleLabel.addStyleName(KSStyles.KS_SIDEBAR_TITLE_LABEL);
	}	
	
}

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

package org.kuali.student.common.ui.client.widgets.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSAccordionMenuImpl;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSAccordionPanelImpl extends KSAccordionPanelAbstract{ 
	private FlowPanel content = new FlowPanel();
    private List<AccordionTitleBar> titleBarList = new ArrayList<AccordionTitleBar>();
    private List<Widget> widgetList = new ArrayList<Widget>();


    public KSAccordionPanelImpl(){
    	initWidget(content);
        content.setStyleName("KS-Accordian-Panel");
    }
    @Override
    public void addGlobalTitleBarHandler(ClickHandler handler){
    	for(AccordionTitleBar atb: titleBarList){
    		atb.addClickHandler(handler);
    	}
    }
    @Override
    public FocusPanel addPanel(String title, Widget subContent) {
        AccordionTitleBar titleBar = new AccordionTitleBar(title);
        setupPanelContent(titleBar, subContent);
        return titleBar;
    }
    @Override
    public FocusPanel addPanel(Widget titleWidget, Widget subContent) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, subContent);
        return titleBar;
    }
    @Override
    public FocusPanel addPanel(Widget titleWidget) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, null);
        return titleBar;
    }
    @Override
    public FocusPanel addPanel(Widget titleWidget, ClickHandler clickHandler, Widget subContent) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, subContent);
        titleBar.addClickHandler(clickHandler);
        return titleBar;
    }
    
    @Override
    public FocusPanel addPanel(Widget titleWidget, ClickHandler clickHandler) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, null);
        titleBar.addClickHandler(clickHandler);
        return titleBar;
    }    
    private void setupPanelContent(AccordionTitleBar titleBar, Widget subContent){
    	titleBarList.add(titleBar);
    	if(subContent != null){
	        FlowPanel widgetPanel = new FlowPanel();
	        widgetPanel.addStyleName(KSStyles.KS_ACCORDION_WIDGETPANEL);
	        widgetPanel.add(subContent);
	        widgetList.add(widgetPanel);
    	}
    	else{
    		widgetList.add(null);
    	}
        content.add(titleBar);
        content.addStyleName(KSStyles.KS_ACCORDION);
        

    }
    
    private int getHeightLeft(){
        return content.getOffsetHeight();
    }
    @Override
    public void resetTitleBars(){
    	for(AccordionTitleBar atb: titleBarList){
    		atb.reset();
    	}
    }
    @Override
    public List<Widget> getWidgetList(){
    	return widgetList;
    }
    
    public void selectOption(int index){
        if(!titleBarList.get(index).isOpen())
            titleBarList.get(index).openAction();
    }
   
    public class AccordionTitleBar extends FocusPanel {
        private boolean isOpen = false;
        private HorizontalPanel contentPanel = new HorizontalPanel();
        private KSLabel titleLabel = null;
        private Widget widget = null;
    	
    	public AccordionTitleBar(String name) {
    	    KSLabel titleLabel = new KSLabel(name,false);
    		titleLabel.addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_LABEL);
    		contentPanel.add(titleLabel);
            add(contentPanel);
            setDefaultStyle();
            addEventHandlers();
        }
        
        public AccordionTitleBar(Widget w) {
            this.widget = w;
            contentPanel.add(w);
        	add(contentPanel);
        	setDefaultStyle();
        	addEventHandlers();
        }

        public Widget getTitleBarWidget() {
            return widget;
        }

        public void setTitleBarWidget(Widget widget) {
            this.widget = widget;
        }
        
        public void addImage(KSImage image){
            contentPanel.add(image);
            image.addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_IMAGE);
        }
        
        public boolean isOpen() {
			return isOpen;
		}

		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
			if(isOpen){
				addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_OPEN);
			}
			else{
				removeStyleName(KSStyles.KS_ACCORDION_TITLEBAR_OPEN);
			}
		}

		private void addEventHandlers(){
        	addClickHandler(new ClickHandler() {

    			public void onClick(ClickEvent event) {

    				openAction();
    			}
            });
        	
        	this.addKeyDownHandler(new KeyDownHandler(){

				public void onKeyDown(KeyDownEvent event) {
					if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
						openAction();
					}
					
				}
        		
        	});

        }
		
		private void recursiveReset(KSAccordionPanelImpl panel){

			List<Widget> theWidgetList = panel.getWidgetList();
			for (Widget w : theWidgetList) {
	            if(w != null){
	            	if(w instanceof FlowPanel){
	            		Widget firstWidget = ((FlowPanel) w).getWidget(0);
	            		if(firstWidget instanceof KSAccordionMenuImpl){
	            		    
	            			//Check to see if the menu is retaining history
	            			if(!((KSAccordionMenuImpl) firstWidget).isRetainingHistory()){
		            			KSAccordionPanelImpl subPanel = ((KSAccordionMenuImpl) firstWidget).getMenu();
		            			subPanel.resetTitleBars();
		            			recursiveReset(subPanel);
	            			}
	            		}
    				}
	            }
	        }
		}
		
		
		public void openAction(){
			for (Widget w : widgetList) {
	            if(w != null){
	            	content.remove(w);
	            }
	        }
			recursiveReset(KSAccordionPanelImpl.this);
	        int i = content.getWidgetIndex(this);
			if(!isOpen){

		        for(AccordionTitleBar bar: titleBarList){
		        	bar.setOpen(false);
		        }  
		        if(widgetList.get(i) != null){
			        Widget toBeAdded = widgetList.get(i);
			        content.insert(toBeAdded, i + 1);      
		        }  
		        this.setOpen(true);
        	}
        	else{
        		if(widgetList.get(i) != null){
        			this.setOpen(false);
        		}
        	}	
		}
        
        private void setDefaultStyle(){
            
        	addStyleName(KSStyles.KS_ACCORDION_TITLEBAR);
            addMouseOverHandler(new MouseOverHandler(){

    			public void onMouseOver(MouseOverEvent event) {
    				AccordionTitleBar.this.addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_HOVER);
    			}
    		});
            
            addMouseOutHandler(new MouseOutHandler(){

    			public void onMouseOut(MouseOutEvent event) {
    				AccordionTitleBar.this.removeStyleName(KSStyles.KS_ACCORDION_TITLEBAR_HOVER);
    				
    			}
    		});
            
            addFocusHandler(new FocusHandler(){

				public void onFocus(FocusEvent event) {
					AccordionTitleBar.this.addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_HOVER);
					
				}
			});
            
            addBlurHandler(new BlurHandler(){

				public void onBlur(BlurEvent event) {
					AccordionTitleBar.this.removeStyleName(KSStyles.KS_ACCORDION_TITLEBAR_HOVER);
					
				}
			});
            
        }
        
        public void reset(){
        	this.setOpen(false);
			for (Widget w : widgetList) {
	            if(w != null){
	            	content.remove(w);
	            }
	        }
        }


    }

}

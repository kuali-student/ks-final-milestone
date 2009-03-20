package org.kuali.student.common.ui.client.widgets.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSAccordionMenuImpl;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
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
    public void addPanel(String title, Widget subContent) {
        AccordionTitleBar titleBar = new AccordionTitleBar(title);
        setupPanelContent(titleBar, subContent);
    }
    @Override
    public void addPanel(Widget titleWidget, Widget subContent) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, subContent);

    }
    @Override
    public void addPanel(Widget titleWidget) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, null);
    }
    @Override
    public void addPanel(Widget titleWidget, ClickHandler clickHandler, Widget subContent) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, subContent);
        titleBar.addClickHandler(clickHandler);
        
    }
    
    @Override
    public void addPanel(Widget titleWidget, ClickHandler clickHandler) {
        AccordionTitleBar titleBar = new AccordionTitleBar(titleWidget);
        setupPanelContent(titleBar, null);
        titleBar.addClickHandler(clickHandler);
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
   
    class AccordionTitleBar extends FocusPanel {
        private boolean isOpen = false;
    	
    	AccordionTitleBar(String name) {
    		KSLabel titleLabel = new KSLabel(name,false); 
    		titleLabel.addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_LABEL);
            add(titleLabel);
            setDefaultStyle();
            addEventHandlers();
        }
        
        AccordionTitleBar(Widget widget) {
        	add(widget);
        	setDefaultStyle();
        	addEventHandlers();
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
	            		    System.out.println("GOT IN HERE");
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
		
		
		private void openAction(){
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

package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;

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

public class KSAccordionPanel extends KSAccordionPanelAbstract{ 
    
    private KSAccordionPanelAbstract accordionPanel = GWT.create(KSAccordionPanelImpl.class);

    public KSAccordionPanel() {
        initWidget(accordionPanel);
    }
    
    public void addGlobalTitleBarHandler(ClickHandler handler){
        accordionPanel.addGlobalTitleBarHandler(handler);
    }

    public void addPanel(String title, Widget subContent){
        accordionPanel.addPanel(title, subContent);
    }
    

    public void addPanel(Widget titleWidget, Widget subContent){
        accordionPanel.addPanel(titleWidget);
    }

	public void addPanel(Widget titleWidget, ClickHandler clickHandler, Widget subContent) {
    accordionPanel.addPanel(titleWidget, clickHandler, subContent);
		
	}
	
	public void addPanel(Widget titleWidget, ClickHandler clickHandler) {
	    accordionPanel.addPanel(titleWidget, clickHandler);
	}
   
    public void addPanel(Widget titleWidget){
        accordionPanel.addPanel(titleWidget);
    }
     
    public void resetTitleBars(){
        accordionPanel.resetTitleBars();
    }
    
    public List<Widget> getWidgetList(){
        return accordionPanel.getWidgetList();
    }

    public KSAccordionPanelAbstract getAccordionPanel() {
        return accordionPanel;
    }
        


}

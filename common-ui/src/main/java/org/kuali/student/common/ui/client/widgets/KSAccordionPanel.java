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

/**
 * An accordion panel is used for displaying categories of content ONE at a time.  When one category's titlebar is
 * selected, a panel with its content is shown, and if there exists any currently open category, it is closed.  
 * You may also close an open category by clicking on it's titlebar again.  This widget's behavior closely mimics
 * the gwt implementation of StackPanel, but it is more customizable.
 * 
 * @author Kuali Student Team
 *
 */
public class KSAccordionPanel extends KSAccordionPanelAbstract{ 
    
    private KSAccordionPanelAbstract accordionPanel = GWT.create(KSAccordionPanelImpl.class);

    /**
     * This constructs an Accordion panel which can be used to display different content categories
     * ONE at a time.
     * 
     */
    public KSAccordionPanel() {
        initWidget(accordionPanel);
    }
    
    /**
     * Adds a handler that will be used for ANY accordion panel title clicks.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#addGlobalTitleBarHandler(com.google.gwt.event.dom.client.ClickHandler)
     */
    public void addGlobalTitleBarHandler(ClickHandler handler){
        accordionPanel.addGlobalTitleBarHandler(handler);
    }

    /**
     * Adds a panel with the category name of title and 
     * panel content defined by the widget subContent.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#addPanel(java.lang.String, com.google.gwt.user.client.ui.Widget)
     */
    public void addPanel(String title, Widget subContent){
        accordionPanel.addPanel(title, subContent);
    }
    

    /**
     * Adds a panel with the titleWidget used as the title bar content and 
     * panel content defined by the widget subContent.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#addPanel(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.ui.Widget)
     */
    public void addPanel(Widget titleWidget, Widget subContent){
        accordionPanel.addPanel(titleWidget);
    }

	/**
     * Adds a panel with titleWidget used as the title bar content and 
     * panel content defined by the widget subContent.  In addition, this panel's title bar will
     * handle clicks using the handler passed in.
     * 
	 * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#addPanel(com.google.gwt.user.client.ui.Widget, com.google.gwt.event.dom.client.ClickHandler, com.google.gwt.user.client.ui.Widget)
	 */
	public void addPanel(Widget titleWidget, ClickHandler clickHandler, Widget subContent) {
    accordionPanel.addPanel(titleWidget, clickHandler, subContent);
		
	}
	
	/**
	 * Adds only a titlebar (no panel content) which contains the titleWidget and handles clicks using the handler
	 * passed in.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#addPanel(com.google.gwt.user.client.ui.Widget, com.google.gwt.event.dom.client.ClickHandler)
	 */
	public void addPanel(Widget titleWidget, ClickHandler clickHandler) {
	    accordionPanel.addPanel(titleWidget, clickHandler);
	}
   
    /**
     * Adds ONLY a titlebar (no panel content) which contains the titleWidget.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#addPanel(com.google.gwt.user.client.ui.Widget)
     */
    public void addPanel(Widget titleWidget){
        accordionPanel.addPanel(titleWidget);
    }
     
    /**
     * Resets all titlebars to their "closed" state.  All open panels will be closed.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#resetTitleBars()
     */
    public void resetTitleBars(){
        accordionPanel.resetTitleBars();
    }
    
    /**
     * Gets a list of all content widgets in the accordion panel.  This list can contain null
     * values.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSAccordionPanelAbstract#getWidgetList()
     */
    public List<Widget> getWidgetList(){
        return accordionPanel.getWidgetList();
    }

    /**
     * Gets this AccordionPanel's implemented object.
     * 
     * @return KSAccordionPanelAbstract object which is this panel's implementation.
     */
    public KSAccordionPanelAbstract getAccordionPanel() {
        return accordionPanel;
    }
        


}

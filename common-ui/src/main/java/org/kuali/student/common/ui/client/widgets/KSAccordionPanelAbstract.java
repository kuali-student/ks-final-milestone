package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSAccordionPanelAbstract extends Composite { 
    
    public abstract void addGlobalTitleBarHandler(ClickHandler handler);

    public abstract void addPanel(String title, Widget subContent);
    
    public abstract void addPanel(Widget titleWidget, Widget subContent);
    
    public  abstract void addPanel(Widget titleWidget);
    
    public abstract void addPanel(Widget titleWidget, ClickHandler clickHandler, Widget subContent);
    
    public abstract void addPanel(Widget titleWidget, ClickHandler clickHandler);
     
    public abstract void resetTitleBars();
    
    public abstract List<Widget> getWidgetList();
        
}

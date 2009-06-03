package org.kuali.student.common.ui.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSAccordionPanelAbstract extends Composite { 
    
    public abstract void addGlobalTitleBarHandler(ClickHandler handler);

    public abstract FocusPanel addPanel(String title, Widget subContent);
    
    public abstract FocusPanel addPanel(Widget titleWidget, Widget subContent);
    
    public  abstract FocusPanel addPanel(Widget titleWidget);
    
    public abstract FocusPanel addPanel(Widget titleWidget, ClickHandler clickHandler, Widget subContent);
    
    public abstract FocusPanel addPanel(Widget titleWidget, ClickHandler clickHandler);
     
    public abstract void resetTitleBars();
    
    public abstract List<Widget> getWidgetList();
        
}

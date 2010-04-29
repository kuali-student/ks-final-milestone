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

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

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
@Deprecated
public abstract class KSTabPanelAbstract extends Composite {

    public abstract void addStyleName(String style);

    public abstract int getTabCount();

    public abstract void selectTab(int index);

    public abstract void removeTab(Widget widget);

    public abstract boolean removeTab(int index);

    public abstract void addTab(Widget w, String tabText);

    public abstract void addTab(Widget w, Widget tab);
    
    public abstract void addSelectionHandler(SelectionHandler<Integer> handler);
    
    public abstract int getWidgetIndex(Widget widget);


}

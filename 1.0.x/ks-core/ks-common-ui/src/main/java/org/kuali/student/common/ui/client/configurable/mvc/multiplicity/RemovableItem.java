/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.Section;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This wraps a widget item
 * 
 * @author Kuali Student Team
 */
public class RemovableItem extends MultiplicityItem {
    private boolean loaded = false;

    protected FlowPanel itemPanel = new FlowPanel();
    
    public RemovableItem(){        
        initWidget(itemPanel);
    }

    public void onLoad(){
    }
    
    private Widget generateRemoveWidget() {
        ClickHandler ch = new ClickHandler() {
            public void onClick(ClickEvent event) {
                getRemoveCallback().exec(RemovableItem.this);
            }
        };

        itemPanel.addStyleName("KS-Multiplicity-Item");
        Label deleteLabel = new Label("Delete");
        deleteLabel.addStyleName("KS-Multiplicity-Link-Label");
        deleteLabel.addClickHandler(ch);
 
        return deleteLabel;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem#clear()
     */
    @Override
    public void clear() {
        loaded = false;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem#redraw()
     */
    @Override
    public void redraw() {
        Widget item = getItemWidget();
        if (!loaded){
       
            itemPanel.add(item);
            itemPanel.add(generateRemoveWidget());
            loaded = true;
        }

        if (item instanceof Section){
            ((Section)item).redraw();
        }
    }

}

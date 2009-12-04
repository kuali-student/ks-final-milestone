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
package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A multiplicity composite allows a users to add/remove/display a list of 
 * item widgets. The item widget to be used must be provided by the createItem()
 * method, which can be a MultiplicitySection, another MultiplicityComposite, 
 * or any widget that supports the HasModelDTOValue interface.  
 * 
 * @author Kuali Student Team
 *
 */
public abstract class MultiplicityComposite extends SimpleMultiplicityComposite {

    private String itemLabel = "Item ";
    public MultiplicityComposite() {
    	this(false);
    }

    public MultiplicityComposite(boolean useDeleteLabel) {
    	this.useDeleteLabel = useDeleteLabel;
	}

	/**
     * This simply decorates a list item widget to add styling and a remove button 
     *   
     * @author Kuali Student Team
     *
     */
    private class MultiplicityItemWidgetDecorator extends Composite {
        VerticalFlowPanel itemPanel;
        Widget listItem;

        private MultiplicityItemWidgetDecorator(Widget listItem){
            itemPanel = new VerticalFlowPanel();
            this.listItem = listItem;
            initWidget(itemPanel);
            if (listItem instanceof SimpleMultiplicityComposite){
                ((SimpleMultiplicityComposite) listItem).redraw();
            }
            itemCount++;
            itemPanel.addStyleName("KS-Multiplicity-Item");

            HorizontalPanel headerPanel = new HorizontalPanel();
            headerPanel.addStyleName("KS-Multiplicity-Item-Header");
            KSLabel headerLabel = new KSLabel(itemLabel + " " + itemCount);
            headerPanel.add(headerLabel);
            if (updateable) {
                headerPanel.add(generateRemoveWidget(listItem, this));
            }

            itemPanel.add(headerPanel);
            itemPanel.add(listItem);
        }

    }

    @Override
    protected Widget decorateItemWidget(Widget newItemWidget) {
        return new MultiplicityItemWidgetDecorator(newItemWidget);
	}

	public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

	protected Widget generateAddWidget() {
	    return new KSButton(addItemLabel, new ClickHandler(){
	        public void onClick(ClickEvent event) {
	            addItem();
	        }            
	    }); 
	}

}

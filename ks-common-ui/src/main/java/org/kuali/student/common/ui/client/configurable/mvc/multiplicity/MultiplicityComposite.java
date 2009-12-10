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
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.user.client.ui.Composite;
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
public abstract class MultiplicityComposite extends Composite {

    protected VerticalFlowPanel mainPanel = new VerticalFlowPanel();
    protected VerticalFlowPanel itemsPanel = new VerticalFlowPanel();
    protected boolean loaded = false;
    protected int itemCount = 0;
    protected int minEmptyItems = 0;
    protected List<MultiplicityItem> items = new ArrayList<MultiplicityItem>();
    protected List<MultiplicityItem> removed = new ArrayList<MultiplicityItem>();
    protected Set<Integer> itemKeys;
    
    public MultiplicityComposite(){
        initWidget(mainPanel);
    }
        
    protected Callback<MultiplicityItem> removeCallback = new Callback<MultiplicityItem>(){

        public void exec(MultiplicityItem itemToRemove) {
            //items.remove(itemToRemove);
            itemToRemove.setDeleted(true);
            removed.add(itemToRemove);
            itemsPanel.remove(itemToRemove);
        }
    };
        

	public MultiplicityItem addItem(){
	    itemCount++;	    
	    MultiplicityItem item = getItemDecorator();
	    Widget itemWidget = createItem();
	    if (item != null){
		    item.setItemKey(new Integer(itemCount));
		    item.setItemWidget(itemWidget);
		    item.setRemoveCallback(removeCallback);
	    } else if (itemWidget instanceof MultiplicityItem){
	    	item = (MultiplicityItem)itemWidget;
	    	item.setItemKey(new Integer(itemCount));
	    }
	    items.add(item);
	    item.redraw();
	    itemsPanel.add(item);
	    
	    return item;
	}
       
    public void onLoad() {
        if (!loaded) {            
            mainPanel.addStyleName("KS-Multiplicity-Composite");
            mainPanel.add(itemsPanel);
           
            Widget addWidget = generateAddWidget();
            if (addWidget != null){
                mainPanel.add(addWidget);
            }
            
            loaded = true;
        }
        
        if (!loaded || itemCount == 0){
            for (int i=0; i < minEmptyItems; i++){
            	addItem();
            }        	
        }
    }

    public List<MultiplicityItem> getItems() {
        return items;
    }

    public List<MultiplicityItem> getRemovedItems() {
        return removed;
    }

    public void clear(){
        itemsPanel.clear();
        items.clear();
        removed.clear();
        itemCount = 0;
    }
   
    public void redraw(){
        for (MultiplicityItem item:items){
            item.redraw();
        }
    }
    
    /**
     * This method will set the number of minimum empty rows to display on initial display of widget.   
     */
    public void setMinEmptyItems(int minCount){
    	this.minEmptyItems = minCount;
    }

    /**
     * Method used to get the item decorator for each multiplicity item 
     */
    public abstract MultiplicityItem getItemDecorator();

    
    /**
     * Method used to create a instance of the multiplicity item
     */
    public abstract Widget createItem();

    /**
     * Method used to get the add button to add additional multiplicity item
     * 
     * @return
     */
    public abstract Widget generateAddWidget();

}

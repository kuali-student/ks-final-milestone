/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.dto.Idable;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasWidgetReadyCallback;

import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Widget;

/**
 * This SelectItemWidget abstracts out the use of selecting an item from a list.
 * Use of this interface will easily allow the underlying widget to be 
 * interchangeable.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class KSSelectItemWidgetAbstract extends Composite implements HasName, HasFocusHandlers, HasBlurHandlers, HasSelectionChangeHandlers, HasWidgetReadyCallback{
	private ListItems listItems = null;
	private String name;
	private List<Callback<Widget>> widgetReadyCallbacks;
	private boolean initialized = false;
		
	public ListItems getListItems() {
		return listItems;
	}

	public <T extends Idable> void setListItems(ListItems listItems) {
		this.listItems = listItems;
	}	
	
	public abstract void redraw();

	/**
	 * Used to had a selection change handler.
	 * 
	 * @param selectionHandler
	 * @return
	 */
    public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler selectionHandler){
        return addHandler(selectionHandler,SelectionChangeEvent.getType());
    }   	
	
	protected void fireChangeEvent(boolean userInitiated){
	    SelectionChangeEvent.fire(this, userInitiated);
	}
	
	/**
	 * Select the item in list represented by the id. For multi-select list
	 * any existing selection should remain selected.
	 * 
	 * @param id
	 */	
	public abstract void selectItem(String id);
	
	/**
	 * Remove selection for item represented by id.
	 * 
	 * @param id
	 */
	public abstract void deSelectItem(String id);
	
	
	/**
	 * List of items that have been selected.
	 * 
	 * @return
	 */
	public abstract List<String> getSelectedItems();
	
	/**
	 * Id of selected item.  If multiple items are selected, this will return the
	 * first selected item.
	 * 
	 * @see com.google.gwt.user.client.ui.HasName#getName()
	 */
	public String getSelectedItem(){
	    String selectedItem = null;
	    List<String> selectedItems = getSelectedItems();
	    if (selectedItems != null && selectedItems.size() > 0){
	        selectedItem = selectedItems.get(0);
	    }
	    return selectedItem;
	}	
	
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;         
    }
    
    /** 
     * This method should be implemented if list supports setting of multiple select. 
     * 
     */
    public void setMultipleSelect(boolean isMultipleSelect){
        throw new UnsupportedOperationException();
    }
    
    /**
     *  
     * This method should if implemented if list supports multiple select
     *
     */
    public boolean isMultipleSelect(){
        return false;
    }
        
    /** 
     * This method should be implemented if list supports column sizing. 
     * 
     */
    public void setColumnSize(int col){
        throw new UnsupportedOperationException();
    }
    
    public abstract void onLoad();

    public abstract void setEnabled(boolean b);
    
    public abstract boolean isEnabled();
    
    /**
     * 
     * This method clears the current selection
     *
     */
    public abstract void clear();

    @Override
    public void addWidgetReadyCallback(Callback<Widget> callback) {
        if (widgetReadyCallbacks == null){
            widgetReadyCallbacks = new ArrayList<Callback<Widget>>();
        }
        widgetReadyCallbacks.add(callback);    
   }

    @Override
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
        if (initialized){
            //Callbacks might need to be moved after a redraw happens
            while (widgetReadyCallbacks != null && widgetReadyCallbacks.size() > 0){
                Callback<Widget> callback = widgetReadyCallbacks.remove(0);
                callback.exec(this);
            }            
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}

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

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 * KSDropDown wraps gwt Listbox in a KSSelectItemWidgetAbstract. This provides  
 * the same basic functionality as a listbox, but allows it to be interchangable
 * with other select item widget implementations.
 * 
 * @author Kuali Student Team
 *
 */
public class KSDropDown extends KSSelectItemWidgetAbstract{ 
    
    KSSelectItemWidgetAbstract dropDown = GWT.create(KSDropDownImpl.class);
    /**
     * This constructs a KSDropDown that wraps an impl
     * 
     */    
	public KSDropDown(){
	    this.initWidget(dropDown);
	}

    /**
     * Select an item whose text equals the name passed in.
     * 
     * @param value the name of the item to be selected.
     */
    @Override
    public void selectItem(String id) {
        dropDown.selectItem(id);       
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    @Override
    public void deSelectItem(String id) {       
        dropDown.deSelectItem(id);
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    @Override
    public List<String> getSelectedItems() {
        return dropDown.getSelectedItems();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItem()
     */
    @Override
    public String getSelectedItem() {
        return dropDown.getSelectedItem();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {
        dropDown.onLoad();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setListItems(org.kuali.student.common.ui.client.widgets.list.ListItems)
     */
    @Override
    public void setListItems(ListItems listItems) {
        dropDown.setListItems(listItems);
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#addSelectionChangeHandler(org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler)
     */
    public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler selectionHandler) {
        return dropDown.addSelectionChangeHandler(selectionHandler);
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getListItems()
     */
    public ListItems getListItems() {
        return dropDown.getListItems();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getName()
     */
    public String getName() {
        return dropDown.getName();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        dropDown.setName(name);
    }

    public void setEnabled(boolean b) {
        dropDown.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return dropDown.isEnabled();
    }

    public boolean isBlankFirstItem() {
    	if (dropDown instanceof KSDropDownImpl){
    		return ((KSDropDownImpl)dropDown).isBlankFirstItem();
    	} else {
    		return false;
    	}
    }

    /** 
     * Use when the first item in list should be blank. If set, it must be called before call to setListItems()
     * 
     * @param blankFirstItem
     */
    public void setBlankFirstItem(boolean blankFirstItem) {
    	if (dropDown instanceof KSDropDownImpl){
    		((KSDropDownImpl)dropDown).setBlankFirstItem(blankFirstItem);
    	}
    }

    @Override
    public void redraw() {
        dropDown.redraw();        
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return dropDown.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return dropDown.addBlurHandler(handler);
	}

    @Override
    public void addWidgetReadyCallback(Callback<Widget> callback) {
        dropDown.addWidgetReadyCallback(callback);
    }

    @Override
    public boolean isInitialized() {
        return dropDown.isInitialized();
    }

    @Override
    public void setInitialized(boolean initialized) {
        dropDown.setInitialized(initialized);
    }

    @Override
    public void clear() {
        dropDown.clear();        
    }
}

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
package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;


/**
 * KSRadiobuttonList is a list of radio buttons.
 * 
 * @author Kuali Student Team 
 *
 */
public class KSRadioButtonList extends KSSelectItemWidgetAbstract {
    private KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSRadioButtonListImpl.class);

    
	/**
	 * This constructs a new radiobutton list using the name passed in as the group/list name.
	 * 
	 * @param name the name of the radio button group
	 */
	public KSRadioButtonList(String name) {
        initWidget(selectItemWidget);
        selectItemWidget.setName(name);
	}

	/**
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
	 */
	public void deSelectItem(String id) {
	    selectItemWidget.deSelectItem(id);	
	}

	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSele ctItemWidgetAbstract#getSelectedItems()
	 */
	public List<String> getSelectedItems() {
	    return selectItemWidget.getSelectedItems();
	}

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItem()
     */
    public String getSelectedItem() {
        return selectItemWidget.getSelectedItem();
    }

	
	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
	 */
	public void selectItem(String id) {
	    selectItemWidget.selectItem(id);
	}

    /**
     * Sets the ListItems object to be used for generating this radio button list.
     * 
     * @param listItems the ListItems used for generating this radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setListItems(org.kuali.student.common.ui.client.widgets.list.ListItems)
     */
    public void setListItems(ListItems listItems) {
        selectItemWidget.setListItems(listItems);      
    }

    /**
     * Sets if this radio button list can have multiple selections or not.  (Not implemented yet????)
     * 
     * @param isMultipleSelect true if multiple can be selected, false otherwise.
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setMultipleSelect(boolean)
     */
    public void setMultipleSelect(boolean isMultipleSelect) {}

    /**
     * This overridden method is not used
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {}
   
    /**
     * Adds a SelectionChangeHandler to handle a selection change event (when the user picks another item).
     * 
     * @paran handler a SelectionChangeHandler to handle change events on the radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#addSelectionChangeHandler(org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler)
     */
    public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler) {
        return selectItemWidget.addSelectionChangeHandler(handler);
    }

    protected void fireChangeEvent() {
        selectItemWidget.fireChangeEvent();
    }

    /**
     * Gets the ListItems object used in this radio button list.
     * 
     * @return the ListItems object used in the radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getListItems()
     */
    public ListItems getListItems() {
        return selectItemWidget.getListItems();
    }

    /**
     * Gets the name of this radio button list
     * 
     * @return the name of this radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getName()
     */
    public String getName() {
        return selectItemWidget.getName();
    }

    /**
     * Sets the name of this radio button list
     * 
     * @param name the new name of the radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setName(java.lang.String)
     */
    public void setName(String name) {
        selectItemWidget.setName(name);
    }

    /**
     * Use to set number of columns to use when displaying list
     * 
     */
    public void setColumnSize(int cols){
        selectItemWidget.setColumnSize(cols);
    }

    @Override
    public void setEnabled(boolean b) {
        selectItemWidget.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return selectItemWidget.isEnabled();
    }

    @Override
    public void redraw() {
        selectItemWidget.redraw();
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return selectItemWidget.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return selectItemWidget.addBlurHandler(handler);
	}
}



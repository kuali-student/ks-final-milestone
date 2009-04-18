package org.kuali.student.common.ui.client.widgets;


import java.util.List;

import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;

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
        //FIXME: This will break replacement via deferred binding. Anyway to do this w/o adding to KSSelectItemWidgetAbstract
        return ((KSDropDownImpl)dropDown).isBlankFirstItem();
    }

    /** 
     * Use when the first item in list should be blank. If set, it must be called before call to setListItems()
     * 
     * @param blankFirstItem
     */
    public void setBlankFirstItem(boolean blankFirstItem) {
        //FIXME: This will break replacement via deferred binding. Anyway to do this w/o adding to KSSelectItemWidgetAbstract
        ((KSDropDownImpl)dropDown).setBlankFirstItem(blankFirstItem);
    }

    @Override
    public void redraw() {
        dropDown.redraw();
        
    }


}

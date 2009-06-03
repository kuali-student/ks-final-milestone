package org.kuali.student.common.ui.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.impl.KSListBoxImpl;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * KSListBox wraps gwt Listbox.  However, it adds functionality and limits the list box to only MULTI select.
 * Future implementation may associate objects to listBox items (additional features may be added, pending need).
 * This class provides some of the same functionality of ListBox, but sets KS css styles
 * for its default look and a variety of events (for improved browser compatibility and customizability).
 * 
 * @author Kuali Student Team
 *
 */
public class KSListBox extends KSSelectItemWidgetAbstract{
    
    KSSelectItemWidgetAbstract dropDown = GWT.create(KSListBoxImpl.class);
    /**
     * This constructs a KSDropDown that wraps an impl
     * 
     */    
    public KSListBox(){
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

    @Override
    public void setEnabled(boolean b) {
        dropDown.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return dropDown.isEnabled();
    }

    @Override
    public void redraw() {
        dropDown.redraw();
        
    }
    
}

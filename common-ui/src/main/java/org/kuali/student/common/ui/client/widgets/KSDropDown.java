package org.kuali.student.common.ui.client.widgets;


import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;

/**
 * KSDropDown wraps gwt Listbox.  However, it adds functionality and limits the listbox to only single select.
 * A KSDropDown can associate objects to selected values (future implementation pending).
 * This class provides some of the same functionality of ListBox, but sets KS css styles
 * for its default look and a variety of events (for improved browser compatibility and customizability).
 * 
 * @author Kuali Student Team
 *
 */
public class KSDropDown extends KSDropDownAbstract{ 
    
    KSDropDownAbstract dropDown = GWT.create(KSDropDownImpl.class);
    /**
     * This constructs a KSDropDown that wraps an impl
     * 
     */    
	public KSDropDown(){
	    dropDown.init();
	    this.initWidget(dropDown);
	}

    /**
     * Adds a ChangeHandler to this drop down.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#addChangeHandler(com.google.gwt.event.dom.client.ChangeHandler)
     */
    public void addChangeHandler(ChangeHandler handler){
        dropDown.addChangeHandler(handler);
    }

    /**
     * Add an item to this drop down with the specified text.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#addItem(java.lang.String)
     */
    public void addItem(String item){
        dropDown.addItem(item);
    }

    /**
     * Remove an item with the specified text from the drop down.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#removeItem(int)
     */
    public void removeItem(int index){
        dropDown.removeItem(index);
    }

    /**
     * Clear the drop down, removes all items from the drop down.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#clear()
     */
    public void clear(){
        dropDown.clear();
    }

    /**
     * Get the index of the item selected.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#getSelectedIndex()
     */
    public int getSelectedIndex(){
        return dropDown.getSelectedIndex();
    }

    /**
     * Get the associated object of the item if one exists, otherwise return null.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#getSelectedObject()
     */
    @Override
    public Object getSelectedObject() {
        // TODO Bsmith - THIS METHOD NEEDS JAVADOCS
        return dropDown.getSelectedObject();
    }

    /**
     * Populate the drop down with the list of strings as item names.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#populateDropDown(java.util.List)
     */
    @Override
    public void populateDropDown(List<String> stringList) {
        dropDown.populateDropDown(stringList);
        
    }

    /**
     * Populate the drop down with the HashMap keys used as item names and their associated objects as their
     * associated item objects.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#populateDropDown(java.util.HashMap)
     */
    @Override
    public void populateDropDown(HashMap<String, ?> theMap) {
        dropDown.populateDropDown(theMap);
        
    }

    /**
     * Select an item whose text equals the name passed in.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDropDownAbstract#selectItem(java.lang.String)
     */
    @Override
    public void selectItem(String value) {
        dropDown.selectItem(value);
        
    }

}

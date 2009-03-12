package org.kuali.student.common.ui.client.widgets;


import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;

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
     * @param handler the ChangeHandler which handles any changes to this drop down (new value selected, etc.)
     */
    public void addChangeHandler(ChangeHandler handler){
        dropDown.addChangeHandler(handler);
    }

    /**
     * Add an item to this drop down with the specified text.
     * 
     * @param item the name of the added item
     */
    public void addItem(String item){
        dropDown.addItem(item);
    }

    /**
     * Remove an item with the specified text from the drop down.
     * 
     * @param index the index of the item to remove
     */
    public void removeItem(int index){
        dropDown.removeItem(index);
    }

    /**
     * Clear the drop down, removes all items from the drop down.
     * 
     */
    public void clear(){
        dropDown.clear();
    }

    /**
     * Get the index of the item selected.
     * 
     * @return index value of the selected item
     */
    public int getSelectedIndex(){
        return dropDown.getSelectedIndex();
    }

    /**
     * Get the associated object of the item if one exists, otherwise return null.
     * 
     * @return Object associated with the selected item if one exists, otherwise null.
     */
    @Override
    public Object getSelectedObject() {
        return dropDown.getSelectedObject();
    }

    /**
     * Populate the drop down with the list of strings as item names.
     * 
     * @param the List of strings to populate the drop down with
     */
    @Override
    public void populateDropDown(List<String> stringList) {
        dropDown.populateDropDown(stringList);
        
    }

    /**
     * Populate the drop down with the HashMap keys used as item names and their associated objects as their
     * associated item objects.
     * 
     * @param theMap the HashMap contain string and object value pairs, representing selectable names and associated objects
     */
    @Override
    public void populateDropDown(HashMap<String, ?> theMap) {
        dropDown.populateDropDown(theMap);
        
    }

    /**
     * Select an item whose text equals the name passed in.
     * 
     * @param value the name of the item to be selected.
     */
    @Override
    public void selectItem(String value) {
        dropDown.selectItem(value);
        
    }

}

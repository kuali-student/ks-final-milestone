package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;

/**
 * This SelectItemWidget abstracts out the use of selecting an item from a list.
 * Use of this interface will easily allow the underlying widget to be 
 * interchangeable.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class KSSelectItemWidgetAbstract extends Composite implements HasName{
	private ListItems listItems = null;
	private String name;
	
	public ListItems getListItems() {
		return listItems;
	}

	public void setListItems(ListItems listItems) {
		this.listItems = listItems;
	}

	/**
	 * Used to had a selection change handler.
	 * 
	 * @param selectionHandler
	 * @return
	 */
    public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler selectionHandler){
        return addHandler(selectionHandler,SelectionChangeEvent.getType());
    }   	
	
	protected void fireChangeEvent(){
	    SelectionChangeEvent.fire(this);
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;         
    }
    
    /** 
     * This method should be implemented if list supports multiple select. 
     * 
     */
    public void setMultipleSelect(boolean isMultipleSelect){
        throw new UnsupportedOperationException();
    }
        
    /** 
     * This method should be implemented if list supports column sizing. 
     * 
     */
    public void setColumnSize(int col){
        throw new UnsupportedOperationException();
    }
    
    public abstract void onLoad();
	
}

package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;

/**
 * This SelectItemWidget abstracts out the use of selecting an item from a list.
 * Use of this interface will easily allow the underlying widget to be 
 * interchangeable.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class SelectItemWidget extends Composite{
	private ListItems listItems = null;
	private List<SelectionChangeHandler> handlers = new ArrayList<SelectionChangeHandler>();

	public ListItems getListItems() {
		return listItems;
	}

	public void setListItems(ListItems listItems) {
		this.listItems = listItems;
	}
	
	public void addSelectionChangeHandler(SelectionChangeHandler handler){
	    this.handlers.add(handler);
	}
	
	protected void fireChangeEvent(){
	    for (SelectionChangeHandler h:handlers){
	        h.onSelectionChange(this);
	    }
	}
	
	public abstract void selectItem(String id);
	public abstract void deSelectItem(String id);
	public abstract List<String> getSelectedItems();
	
}

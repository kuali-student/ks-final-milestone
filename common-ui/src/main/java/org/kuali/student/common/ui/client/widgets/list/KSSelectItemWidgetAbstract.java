package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
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
	private List<SelectionChangeHandler> handlers = new ArrayList<SelectionChangeHandler>();
	private String name;
	
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

    public abstract void init(String name);

    public abstract void onClick(ClickEvent event);

    public abstract void setMultipleSelect(boolean isMultipleSelect);

    public abstract void onLoad();
	
}

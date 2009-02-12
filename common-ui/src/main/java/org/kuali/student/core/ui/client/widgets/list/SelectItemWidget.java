package org.kuali.student.core.ui.client.widgets.list;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;

public abstract class SelectItemWidget extends Composite {
	private ListItems listItems = null;

	public ListItems getListItems() {
		return listItems;
	}

	public void setListItems(ListItems listItems) {
		this.listItems = listItems;
	}
	
	public abstract void selectItem(int index);
	public abstract void deSelectItem(int index);
	public abstract List<Integer> getSelectedItems();
	
}

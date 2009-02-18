package org.kuali.student.common.ui.client.widgets.list;

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
	
	public abstract void selectItem(String id);
	public abstract void deSelectItem(String id);
	public abstract List<String> getSelectedItems();
	
}

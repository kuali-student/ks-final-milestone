package org.kuali.student.common.ui.client.widgets.menus;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;

// TODO everything :)
public abstract class KSMenu extends Composite {
	protected List<KSMenuItemData> items;
	
	public void setItems(List<KSMenuItemData> items) {
		this.items = items;
		populateMenu();
	}
	
	public List<KSMenuItemData> getItems() {
		return this.items;
	}
	
	protected abstract void populateMenu();
}

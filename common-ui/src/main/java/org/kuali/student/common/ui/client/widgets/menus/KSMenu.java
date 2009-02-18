package org.kuali.student.common.ui.client.widgets.menus;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;

// TODO everything :)
public abstract class KSMenu extends Composite {
	protected List<MenuItem> items;
	
	public void setItems(List<MenuItem> items) {
		this.items = items;
		populateMenu();
	}
	
	public List<MenuItem> getItems() {
		return this.items;
	}
	
	protected abstract void populateMenu();
}

package org.kuali.student.common.ui.client.widgets.menus;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;


/**
 * KSMenu is the abstract class which is used to describe widgets which are menu based in ks-commons.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class KSMenu extends Composite {
	protected List<KSMenuItemData> items;
	
	/**
	 * Sets the list of KSMenuItemData to be used in this menu and populates it.
	 * 
	 * @param items list of KSMenuItemData to be used and populated into the menu
	 */
	public void setItems(List<KSMenuItemData> items) {
		this.items = items;
		populateMenu();
	}
	
	/**
	 * Gets the list of KSMenuItemData used in this menu.
	 * 
	 * @return the list of KSMenuItemData used to in this Menu.
	 */
	public List<KSMenuItemData> getItems() {
		return this.items;
	}
	
	protected abstract void populateMenu();
	
	public abstract boolean selectMenuItem(String[] hierarchy);
	
	/**
	 * Deselects the current selected menu item
	 *  
	 * @return
	 */
	public abstract void clearSelected();
}

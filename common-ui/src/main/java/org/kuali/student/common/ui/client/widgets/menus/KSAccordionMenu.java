package org.kuali.student.common.ui.client.widgets.menus;

import java.util.List;


import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSAccordionMenuImpl;


public class KSAccordionMenu extends KSAccordionMenuAbstract{ 
	//TODO make this work with KSAccordionPanel
	private KSAccordionMenuAbstract accordionMenu = new KSAccordionMenuImpl();
	
	public KSAccordionMenu(){
	    initWidget(accordionMenu);
	}
	
	@Override
	protected void populateMenu() {
	    accordionMenu.populateMenu();
	}
	
	public KSAccordionPanelImpl getMenu(){
		return accordionMenu.getMenu();
	}
	
	/**
	 * Retain the history of all sub menus when a top level menu is closed.  This must be called
	 * BEFORE the super class method setItems.
	 * @param retain true if you are retaining history, false if you are clearing it.
	 * @pre This must be called BEFORE the super class method - setItems.
	 */
	public void setRetainHistory(boolean retain){
	    accordionMenu.setRetainHistory(retain);
	}

	public boolean isRetainingHistory() {
		return accordionMenu.isRetainingHistory();
	}

    @Override
    public List<KSMenuItemData> getItems() {
        return accordionMenu.getItems();
    }

    @Override
    public void setItems(List<KSMenuItemData> items) {
        accordionMenu.setItems(items);
    }
	
}

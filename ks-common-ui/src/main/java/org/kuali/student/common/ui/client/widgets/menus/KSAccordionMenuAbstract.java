package org.kuali.student.common.ui.client.widgets.menus;

import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;

public abstract class KSAccordionMenuAbstract extends KSMenu { 
	
	public abstract KSAccordionPanelImpl getMenu();

	
	/**
	 * Retain the history of all sub menus when a top level menu is closed.  This must be called
	 * BEFORE the super class method setItems.
	 * @param retain true if you are retaining history, false if you are clearing it.
	 * @pre This must be called BEFORE the super class method - setItems.
	 */
	public abstract void setRetainHistory(boolean retain);

	public abstract boolean isRetainingHistory();
	
}

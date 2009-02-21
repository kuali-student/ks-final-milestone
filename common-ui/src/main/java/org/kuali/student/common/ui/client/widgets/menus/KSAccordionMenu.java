package org.kuali.student.common.ui.client.widgets.menus;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;

public class KSAccordionMenu extends KSMenu{
	
	private KSAccordionPanel menu = new KSAccordionPanel();
	private boolean retainHistory = false;
	private int level = 0;
	
	@Override
	protected void populateMenu() {
		this.initWidget(menu);
		Label label = null;
		for(KSMenuItemData i: items){
			
			KSLabel categoryLabel = GWT.create(KSLabel.class);
			categoryLabel.init(i.getLabel(), false);

			if(level > 0 && level <= 7){
				categoryLabel.addStyleName(KSStyles.KS_INDENT + "-" + level);
			}
			categoryLabel.addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_LABEL);
			
			if(i.getClickHandler() != null){
				label = categoryLabel.getLabel();

				label.addClickHandler(i.getClickHandler());
			}
			
			if(i.getSubItems().isEmpty()){		
				menu.addPanel(categoryLabel);
			}
			else{
				KSAccordionMenu subMenu = GWT.create(KSAccordionMenu.class);
				subMenu.setRetainHistory(retainHistory);
				subMenu.setLevel(level+1);
				subMenu.setItems(i.getSubItems());
				menu.addPanel(categoryLabel, subMenu);
			}
		}
		
	}
	
	public KSAccordionPanel getMenu(){
		return menu;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	/**
	 * Retain the history of all sub menus when a top level menu is closed.  This must be called
	 * BEFORE the super class method setItems.
	 * @param retain true if you are retaining history, false if you are clearing it.
	 * @pre This must be called BEFORE the super class method - setItems.
	 */
	public void setRetainHistory(boolean retain){
		retainHistory = retain;
	}

	public boolean isRetainingHistory() {
		return retainHistory;
	}
	
}

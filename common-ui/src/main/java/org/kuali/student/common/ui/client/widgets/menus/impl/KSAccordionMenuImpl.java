package org.kuali.student.common.ui.client.widgets.menus.impl;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenuAbstract;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

public class KSAccordionMenuImpl extends KSAccordionMenuAbstract{ 
	//TODO make this work with KSAccordionPanel
	private KSAccordionPanelImpl menu = new KSAccordionPanelImpl();
	private boolean retainHistory = false;
	
	public KSAccordionMenuImpl(){
	    this.initWidget(menu);
	}
	
	@Override
	protected void populateMenu() {
	    
		for(KSMenuItemData i: items){
			int level = calculateDepth(i);
			
			KSLabel categoryLabel = new KSLabel(i.getLabel(), false);
			
			if(level > 0 && level <= 7){
				categoryLabel.addStyleName(KSStyles.KS_INDENT + "-" + level);
			}
			categoryLabel.addStyleName(KSStyles.KS_ACCORDION_TITLEBAR_LABEL);
			
/*			if(i.getClickHandler() != null){
			    fp.addClickHandler(i.getClickHandler());
			}*/
			
			if(i.getSubItems().isEmpty()){		
				if(i.getClickHandler() != null)
				{
					menu.addPanel(categoryLabel, i.getClickHandler());
				}
				else{
					menu.addPanel(categoryLabel);
				}
			}
			else{
				KSAccordionMenuImpl subMenu = new KSAccordionMenuImpl();
				subMenu.setRetainHistory(retainHistory);
				subMenu.setItems(i.getSubItems());
				if(i.getClickHandler() != null)
				{
					menu.addPanel(categoryLabel, i.getClickHandler(), subMenu);
				}
				else{
					menu.addPanel(categoryLabel, subMenu);
				}
			}
		}
		
	}
	
	public KSAccordionPanelImpl getMenu(){
		return menu;
	}


	private int calculateDepth(KSMenuItemData item) {
	    int result = -1;
	    while (item != null) {
	        result++;
	        item = item.getParent();
	    }
	    return result;
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
    @Override
    public List<KSMenuItemData> getItems() {
        return super.getItems();
    }
    @Override
    public void setItems(List<KSMenuItemData> items) {
        super.setItems(items);
    }
	
}

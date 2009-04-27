package org.kuali.student.common.ui.client.widgets.menus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenuAbstract;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FocusPanel;

public class KSAccordionMenuImpl extends KSAccordionMenuAbstract{ 
	//TODO make this work with KSAccordionPanel
	private KSAccordionPanelImpl menu = new KSAccordionPanelImpl();
	
	private final Map<String, FocusPanel> accordionMenuItemPanels = new HashMap<String, FocusPanel>();
	private final Map<String, KSAccordionMenuImpl> subMenuMap = new HashMap<String, KSAccordionMenuImpl>();
	
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
			
			String key = i.getLabel().toLowerCase().trim();
			
			if(i.getSubItems().isEmpty()){		
				if(i.getClickHandler() != null)
				{
					accordionMenuItemPanels.put(key, menu.addPanel(categoryLabel, i.getClickHandler()));
				}
				else{
				    accordionMenuItemPanels.put(key, menu.addPanel(categoryLabel));
				}
			}
			else{
				KSAccordionMenuImpl subMenu = new KSAccordionMenuImpl();
				subMenu.setRetainHistory(retainHistory);
				subMenu.setItems(i.getSubItems());
				if(i.getClickHandler() != null)
				{
				    accordionMenuItemPanels.put(key, menu.addPanel(categoryLabel, i.getClickHandler(), subMenu));
				}
				else{
				    accordionMenuItemPanels.put(key, menu.addPanel(categoryLabel, subMenu));
				}
				subMenuMap.put(key, subMenu);
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

    @Override
    public boolean selectMenuItem(String[] hierarchy) {
        boolean selected = false;
        String current = hierarchy[0].toLowerCase().trim();
        FocusPanel itemToSelect = accordionMenuItemPanels.get(current);
        if(itemToSelect != null){
            itemToSelect.fireEvent(new ClickEvent(){});
            selected = true;
        }
        
        KSAccordionMenuImpl subMenu = subMenuMap.get(current);
        if(subMenu != null){
            String[] subHierarchy = new String[hierarchy.length - 1];
            for(int i = 0; i < subHierarchy.length; i++){
                subHierarchy[i] = hierarchy[i + 1];
            }
            selected = subMenu.selectMenuItem(subHierarchy);
        }
        
        return selected;
    }
	
}

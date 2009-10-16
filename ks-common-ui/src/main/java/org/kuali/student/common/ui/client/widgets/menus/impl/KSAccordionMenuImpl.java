/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.menus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;
import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl.AccordionTitleBar;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenuAbstract;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.MenuChangeEvent;
import org.kuali.student.common.ui.client.widgets.menus.MenuEventHandler;
import org.kuali.student.common.ui.client.widgets.menus.MenuSelectEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;

public class KSAccordionMenuImpl extends KSAccordionMenuAbstract{ 
	//TODO make this work with KSAccordionPanel
	private KSAccordionPanelImpl menu = new KSAccordionPanelImpl();
	
	private final Map<KSMenuItemData, FocusPanel> accordionMenuItemPanels = new HashMap<KSMenuItemData, FocusPanel>();
	private final Map<String, KSAccordionMenuImpl> subMenuMap = new HashMap<String, KSAccordionMenuImpl>();
	
	private boolean retainHistory = false;
	
	private MenuEventHandler menuHandler = new MenuEventHandler(){

        @Override
        public void onChange(MenuChangeEvent e) {
            KSMenuItemData i = (KSMenuItemData) e.getSource();
            AccordionTitleBar itemToChange = (AccordionTitleBar)accordionMenuItemPanels.get(i);

            KSLabel theLabel = (KSLabel) itemToChange.getTitleBarWidget();
            if(!(i.getLabel().equals(theLabel.getText()))){
                theLabel.setText(i.getLabel());
            }
            else if(i.getShownIcon() != null){
                itemToChange.addImage(i.getShownIcon());
            }
        }

        @Override
        public void onSelect(MenuSelectEvent e) {
            KSMenuItemData i = (KSMenuItemData) e.getSource();
            
            AccordionTitleBar itemToSelect = (AccordionTitleBar)accordionMenuItemPanels.get(i);
            if(i.getParent() != null){
                i.getParent().setSelected(true);
            }

            if(itemToSelect != null){
                if(!(itemToSelect.isOpen())){
                    itemToSelect.fireEvent(new ClickEvent(){});
                }  
            }
            
        }
    };
	
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
					accordionMenuItemPanels.put(i, menu.addPanel(categoryLabel, i.getClickHandler()));
				}
				else{
				    accordionMenuItemPanels.put(i, menu.addPanel(categoryLabel));
				}
			}
			else{
				KSAccordionMenuImpl subMenu = new KSAccordionMenuImpl();
				subMenu.setRetainHistory(retainHistory);
				subMenu.setItems(i.getSubItems());
				if(i.getClickHandler() != null)
				{
				    accordionMenuItemPanels.put(i, menu.addPanel(categoryLabel, i.getClickHandler(), subMenu));
				}
				else{
				    accordionMenuItemPanels.put(i, menu.addPanel(categoryLabel, subMenu));
				}
				subMenuMap.put(key, subMenu);
			}
			
			for(final KSMenuItemData d: accordionMenuItemPanels.keySet()){
			    final FocusPanel fp = accordionMenuItemPanels.get(d);
			    fp.addClickHandler(new ClickHandler(){

                    @Override
                    public void onClick(ClickEvent event) {
                        if(((AccordionTitleBar)fp).isOpen()){
                            
                            unSelect(items);
                            
                            d.unhandledSetSelected(true);
                            
                            if(d.getParent() != null){
                                KSMenuItemData parent = d.getParent();
                                while (parent != null) {
                                    parent.unhandledSetSelected(true);
                                    parent = parent.getParent();
                                }
                            }
                        }
                        else{
                            d.unhandledSetSelected(false);
                            if(retainHistory == false){
                                
                                if(!(d.getSubItems().isEmpty())){
                                    unSelect(d.getSubItems());
                                }
                            }
                        }
                        
                    }
                    
                    public void unSelect(List<KSMenuItemData> theItems){
                        for(KSMenuItemData i: theItems){
                            i.unhandledSetSelected(false);
                            if(!(i.getSubItems().isEmpty())){
                                unSelect(i.getSubItems());
                            }
                        }
                    }
                });
			}
			
	        i.addMenuEventHandler(MenuSelectEvent.TYPE, menuHandler);
	        i.addMenuEventHandler(MenuChangeEvent.TYPE, menuHandler);
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
        String currentString = hierarchy[0].toLowerCase().trim();
        KSMenuItemData current = null;
        for(KSMenuItemData i: items){
            if(i.getLabel().toLowerCase().trim().equals(currentString)){
                System.out.println("Got here");
                current = i;
                break;
            }
        }
        
        if(current != null)
        {
            if(hierarchy.length == 1){
                FocusPanel itemToSelect = accordionMenuItemPanels.get(current);
                if(itemToSelect != null){
                    selected = true;
                    current.setSelected(true);
                }
            }
            else if(hierarchy.length > 1){
                KSAccordionMenuImpl subMenu = subMenuMap.get(currentString);
                if(subMenu != null){
                    String[] subHierarchy = new String[hierarchy.length - 1];
                    for(int i = 0; i < subHierarchy.length; i++){
                        subHierarchy[i] = hierarchy[i + 1];
                    }
                    selected = subMenu.selectMenuItem(subHierarchy);
                }
            }
        }

        return selected;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.menus.KSMenu#clearSelected()
     */
    @Override
    public void clearSelected() {
        //TODO: Clear current selected item        
    }
	
}

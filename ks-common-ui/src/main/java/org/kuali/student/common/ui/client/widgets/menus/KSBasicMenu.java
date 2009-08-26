package org.kuali.student.common.ui.client.widgets.menus;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.menus.impl.KSBasicMenuImpl;

import com.google.gwt.core.client.GWT;

public class KSBasicMenu extends KSBasicMenuAbstract{
    
    private KSBasicMenuAbstract basicMenu = GWT.create(KSBasicMenuImpl.class);
    
    public KSBasicMenu(){
        initWidget(basicMenu);
    }
    
    /**
     * Populates the menu with the items set through setItems.
     * 
     * @see org.kuali.student.common.ui.client.widgets.menus.KSMenu#populateMenu()
     */
    @Override
    protected void populateMenu() {
        basicMenu.populateMenu();
    }

    /**
     * Get the list of KSMenuItemData used in this accordion menu.
     * 
     * @return the List of KSMenuItemData used in this accordion menu.
     * 
     * @see org.kuali.student.common.ui.client.widgets.menus.KSMenu#getItems()
     */
    @Override
    public List<KSMenuItemData> getItems() {
        return basicMenu.getItems();
    }

    /**
     * Sets the KSMenuItemData used in this accordion menu.
     * 
     * @param items the list of KSMenuItemData used in this accordion menu
     *
     */
    @Override
    public void setItems(List<KSMenuItemData> items) {
        basicMenu.setItems(items);
    }
    
    @Override
    public boolean isNumberAllItems() {
        return basicMenu.isNumberAllItems();
    }

    @Override
    public void setNumberAllItems(boolean numberAllItems) {
        basicMenu.setNumberAllItems(numberAllItems);
    }

    @Override
    public void setDescription(String description) {
        basicMenu.setDescription(description);
        
    }

    @Override
    public void setTitle(String title) {
        basicMenu.setTitle(title);
        
    }

    @Override
    public boolean selectMenuItem(String[] hierarchy) {
        return basicMenu.selectMenuItem(hierarchy);
    }
    
    public void clearSelected(){
        basicMenu.clearSelected();
    }
    
}

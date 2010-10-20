/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenuAbstract;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.MenuChangeEvent;
import org.kuali.student.common.ui.client.widgets.menus.MenuEventHandler;
import org.kuali.student.common.ui.client.widgets.menus.MenuSelectEvent;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSBasicMenuImpl extends KSBasicMenuAbstract{
    private VerticalPanel menuPanel = new VerticalPanel();
    private VerticalPanel menuTitlePanel = new VerticalPanel();
    private KSLabel menuTitle = new KSLabel();
    private KSLabel menuDescription = new KSLabel();
    private VerticalPanel menuContainer = new VerticalPanel();
    private List<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>();
    private boolean numberAllItems = false;
    
    private EventHandler handler = new EventHandler();
    
    private MenuEventHandler menuHandler = new MenuEventHandler(){

        @Override
        public void onChange(MenuChangeEvent e) {
            KSMenuItemData i = (KSMenuItemData) e.getSource();
            MenuItemPanel itemToChange = null;
            for(MenuItemPanel p: menuItems){
                if(i.equals(p.getItem())){
                    itemToChange = p;
                }
            }
            if(itemToChange != null){
                if(!(i.getLabel().equals(itemToChange.getItemLabel().getText()))){
                    itemToChange.getItemLabel().setText(i.getLabel());
                }
                else if(i.getShownIcon() != null){
                    itemToChange.addImage(i.getShownIcon());
                }
            } 
            
        }

        @Override
        public void onSelect(MenuSelectEvent e) {
            
            KSMenuItemData i = (KSMenuItemData) e.getSource();
            MenuItemPanel itemToSelect = null;
            for(MenuItemPanel p: menuItems){
                if(i.equals(p.getItem())){
                    itemToSelect = p;
                }
            }
            if(itemToSelect != null){
                itemToSelect.fireEvent(new ClickEvent(){});
            }
        }
    };
    
    public KSBasicMenuImpl(){
        menuTitlePanel.add(menuTitle);
        menuTitlePanel.add(menuDescription);
        menuContainer.add(menuTitlePanel);
        menuContainer.add(menuPanel);
        
        menuContainer.addStyleName(KSStyles.KS_BASIC_MENU_PARENT_CONTAINER);
        menuTitlePanel.addStyleName(KSStyles.KS_BASIC_MENU_TITLE_PANEL);
        menuPanel.addStyleName(KSStyles.KS_BASIC_MENU_PANEL);
        
        menuTitle.addStyleName(KSStyles.KS_BASIC_MENU_TITLE_LABEL);
        menuTitle.addStyleName(KSStyles.KS_INDENT + "-1");
        
        menuDescription.addStyleName(KSStyles.KS_BASIC_MENU_DESC_LABEL);
        menuDescription.addStyleName(KSStyles.KS_INDENT + "-1");
        
        this.initWidget(menuContainer);
    }
    
    public void setTitle(String title){
        menuTitle.setText(title);
        menuTitle.setWordWrap(true);
        //add style
    }
    
    public void setDescription(String description){
        menuDescription.setText(description);
        menuDescription.setWordWrap(true);
        //add style
    }
    
    private class EventHandler implements ClickHandler, MouseOverHandler, MouseOutHandler, FocusHandler, BlurHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            if(sender instanceof MenuItemPanel){
                selectMenuItemPanel((MenuItemPanel)sender);
                sender.removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_HOVER);
                ((MenuItemPanel) sender).getItemLabel().removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_HOVER);
            }
        }



        @Override
        public void onMouseOver(MouseOverEvent event) {
            Widget sender = (Widget) event.getSource();   
            if(sender instanceof MenuItemPanel){
                if(((MenuItemPanel) sender).isSelectable() && !((MenuItemPanel) sender).isSelected()){
                    sender.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_HOVER);
                    ((MenuItemPanel) sender).getItemLabel().addStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_HOVER);
                }
            } 
        }

        @Override
        public void onMouseOut(MouseOutEvent event) {
            Widget sender = (Widget) event.getSource();
            if(sender instanceof MenuItemPanel){
                if(((MenuItemPanel) sender).isSelectable()){
                    sender.removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_HOVER);
                    ((MenuItemPanel) sender).getItemLabel().removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_HOVER);
                }
            } 
        }

        @Override
        public void onFocus(FocusEvent event) {
            // no function yet
            
        }

        @Override
        public void onBlur(BlurEvent event) {
            // no function yet
            
        }
        
    }
    
    private void selectMenuItemPanel(MenuItemPanel toBeSelected) {
        if(toBeSelected.isSelectable()){
            
            clearSelected();
            
            toBeSelected.select();
            toBeSelected.getItem().unhandledSetSelected(true);
        }
        
    }
    
    private class MenuItemPanel extends FocusPanel{
        KSLabel itemLabel = new KSLabel();
        HorizontalPanel contentPanel = new HorizontalPanel();
        boolean selectable = false;
        boolean selected = false;
        KSMenuItemData item;
        int indent;
        int itemNum;
        
        public MenuItemPanel(KSMenuItemData item, int indent, int itemNum){
            this.item = item;
            this.indent = indent;
            this.itemNum = itemNum;
            
            if(numberAllItems){
                // logic for indent maybe
                itemLabel.setText(itemNum + ". " + item.getLabel());
            }
            else{
                if(indent == 1 && !(item.getSubItems().isEmpty())){
                    itemLabel.setText(itemNum + ". " + item.getLabel());
                    itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_TOPLEVEL_ITEM_LABEL);
                    this.addStyleName(KSStyles.KS_BASIC_MENU_TOPLEVEL_ITEM_PANEL);
                }
                else{
                    itemLabel.setText(item.getLabel());
                }
            }
            
            if(indent > 0 && indent <= 7){
                itemLabel.addStyleName(KSStyles.KS_INDENT + "-" + indent);
            }
            
            itemLabel.setWordWrap(true);
            this.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL);
            itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL);
            if(item.getClickHandler() != null)
            {
                this.addClickHandler(item.getClickHandler());
                itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_CLICKABLE_ITEM_LABEL);
                selectable = true;
            }
            this.addClickHandler(handler);
            this.addMouseOverHandler(handler);
            this.addMouseOutHandler(handler);
            this.addFocusHandler(handler);
            this.addBlurHandler(handler);
            contentPanel.add(itemLabel);
            this.add(contentPanel);
        }
        
        public void addImage(KSImage shownIcon) {
            shownIcon.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_IMAGE);
            contentPanel.add(shownIcon);
            
        }

        public void deSelect(){
            this.removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_SELECTED);
            itemLabel.removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_SELECTED);
            selected = false;
        }
        
        public void select(){
            this.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_SELECTED);
            itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_SELECTED);
            selected = true;
        }

        public KSLabel getItemLabel() {
            return itemLabel;
        }

        public boolean isSelectable() {
            return selectable;
        }

        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }
        
        public KSMenuItemData getItem() {
            return item;
        }

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
        
        
    }
    
    @Override
    protected void populateMenu() {
        createMenuItems(items, 1);  
    }
    
    private void createMenuItems(List<KSMenuItemData> theItems, int currentDepth){
        int itemNum = 0;
        for(KSMenuItemData i: theItems){
            itemNum++;
            addMenuItem(new MenuItemPanel(i, currentDepth, itemNum));
            if(!(i.getSubItems().isEmpty())){
                createMenuItems(i.getSubItems(), currentDepth + 1);
            }
            i.addMenuEventHandler(MenuSelectEvent.TYPE, menuHandler);
            i.addMenuEventHandler(MenuChangeEvent.TYPE, menuHandler);
        }
    }
    
    private void addMenuItem(MenuItemPanel panel){
        menuPanel.add(panel);
        menuItems.add(panel);
    }
    
    public boolean isNumberAllItems() {
        return numberAllItems;
    }

    public void setNumberAllItems(boolean numberAllItems) {
        this.numberAllItems = numberAllItems;
    }

    @Override
    public boolean selectMenuItem(String[] hierarchy) {
        List<KSMenuItemData> currentItems = items;
        KSMenuItemData itemToSelect = null;
        for(String s: hierarchy){
            s = s.trim();
            for(KSMenuItemData i: currentItems){
                if(s.equalsIgnoreCase(i.getLabel().trim())){
                    itemToSelect = i;
                    currentItems = i.getSubItems();
                    break;
                }
            }
        }
        
        if(itemToSelect != null){
        
            for(MenuItemPanel p: menuItems){
                if(itemToSelect.equals(p.getItem())){
                    p.getItem().setSelected(true);
                    return true;
                }
            }
            
        }
        
        return false;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.menus.KSMenu#clearSelected()
     */
    @Override
    public void clearSelected() {
        for(MenuItemPanel m : menuItems){
            m.deSelect();
            m.getItem().unhandledSetSelected(false);
        }
    }
    
}

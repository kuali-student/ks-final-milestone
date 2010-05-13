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

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.ClickablePanel;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenuAbstract;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.MenuChangeEvent;
import org.kuali.student.common.ui.client.widgets.menus.MenuEventHandler;
import org.kuali.student.common.ui.client.widgets.menus.MenuSelectEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSListMenuImpl extends KSBasicMenuAbstract{
	private KSListPanel menuPanel = new KSListPanel();
    private VerticalPanel menuTitlePanel = new VerticalPanel();
    private KSLabel menuTitle = new KSLabel();
    private KSLabel menuDescription = new KSLabel();
    //private VerticalPanel menuContainer = new VerticalPanel();
    private List<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>();
    private boolean numberAllItems = false;
    private List<Callback<KSMenuItemData>> globalCallbacks = new ArrayList<Callback<KSMenuItemData>>();
    private MenuImageLocation imgLoc = MenuImageLocation.RIGHT;

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

    public KSListMenuImpl(){
        menuTitlePanel.add(menuTitle);
        menuTitlePanel.add(menuDescription);
        menuTitlePanel.setVisible(false);
        menuPanel.add(menuTitlePanel);
        //menuContainer.add(menuTitlePanel);
        //menuContainer.add(menuPanel);

        //menuContainer.addStyleName(KSStyles.KS_BASIC_MENU_PARENT_CONTAINER);
        menuTitlePanel.addStyleName("KS-Basic-Menu-Title-Panel");
        menuPanel.addStyleName( "KS-Basic-Menu-Panel");

        menuTitle.addStyleName("KS-Basic-Menu-Title-Label");
        menuTitle.addStyleName(KSStyles.KS_INDENT + "-1");

        menuDescription.addStyleName("KS-Basic-Menu-Desc-Label");
        menuDescription.addStyleName(KSStyles.KS_INDENT + "-1");

        this.initWidget(menuPanel);
    }

    public void setTitle(String title){
    	menuTitlePanel.setVisible(true);
        menuTitle.setText(title);
        menuTitle.setWordWrap(true);
        //add style
    }

    public void setDescription(String description){
    	menuTitlePanel.setVisible(true);
        menuDescription.setText(description);
        menuDescription.setWordWrap(true);
        //add style
    }

    private class EventHandler implements ClickHandler, MouseOverHandler, MouseOutHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            if(sender instanceof MenuItemPanel){
                selectMenuItemPanel((MenuItemPanel)sender);
                sender.removeStyleName("KS-Basic-Menu-Item-Panel-Hover");
                ((MenuItemPanel) sender).getItemLabel().removeStyleName("KS-Basic-Menu-Item-Label-Hover");
            }
        }



        @Override
        public void onMouseOver(MouseOverEvent event) {
            Widget sender = (Widget) event.getSource();
            if(sender instanceof MenuItemPanel){
                if(((MenuItemPanel) sender).isSelectable() && !((MenuItemPanel) sender).isSelected()){
                    sender.addStyleName("KS-Basic-Menu-Item-Panel-Hover");
                    ((MenuItemPanel) sender).getItemLabel().addStyleName("KS-Basic-Menu-Item-Label-Hover");
                }
            }
        }

        @Override
        public void onMouseOut(MouseOutEvent event) {
            Widget sender = (Widget) event.getSource();
            if(sender instanceof MenuItemPanel){
                if(((MenuItemPanel) sender).isSelectable()){
                    sender.removeStyleName("KS-Basic-Menu-Item-Panel-Hover");
                    ((MenuItemPanel) sender).getItemLabel().removeStyleName("KS-Basic-Menu-Item-Label-Hover");
                }
            }
        }

    }

    private void selectMenuItemPanel(MenuItemPanel toBeSelected) {
        if(toBeSelected.isSelectable()){

            clearSelected();

            toBeSelected.select();
            toBeSelected.getItem().unhandledSetSelected(true);
        }
        for(Callback<KSMenuItemData> c: globalCallbacks){
        	c.exec(toBeSelected.getItem());
        }

    }

    private class MenuItemPanel extends ClickablePanel{
        KSLabel itemLabel = new KSLabel();
        HorizontalBlockFlowPanel contentPanel = new HorizontalBlockFlowPanel();
        boolean selectable = false;
        boolean selected = false;
        KSMenuItemData item;
        int indent;
        int itemNum;
        private String id = HTMLPanel.createUniqueId();
	    HTMLPanel anchorPanel = new HTMLPanel("<a href='javascript:return false;' id='" + id + "'></a>");

        public MenuItemPanel(KSMenuItemData item, int indent, int itemNum){
            this.item = item;
            this.indent = indent;
            this.itemNum = itemNum;

            itemLabel.setWordWrap(true);
            this.addStyleName("KS-Basic-Menu-Item-Panel");
            itemLabel.addStyleName("KS-Basic-Menu-Item-Label");
            if(item.getClickHandler() != null)
            {
                this.addClickHandler(item.getClickHandler());
                itemLabel.addStyleName("KS-Basic-Menu-Clickable-Item-Label");
                selectable = true;
            }
            this.addClickHandler(handler);
            this.addMouseOverHandler(handler);
            this.addMouseOutHandler(handler);

            contentPanel.add(itemLabel);
            if(item.getShownIcon() != null){
            	if(imgLoc == MenuImageLocation.RIGHT){
            		contentPanel.add(item.getShownIcon());
            	}
            	else{
            		contentPanel.insert(item.getShownIcon(), 0);
            	}
            }

            if(indent == 1 && !(item.getSubItems().isEmpty())){
                itemLabel.setText(item.getLabel());
                itemLabel.addStyleName("KS-Basic-Menu-Toplevel-Item-Label");
                this.addStyleName("KS-Basic-Menu-Toplevel-Item-Panel");
                this.add(contentPanel);
            }
            else{
                itemLabel.setText(item.getLabel());
                anchorPanel.add(contentPanel, id);
                this.add(anchorPanel);
            }
        }

        public void addImage(KSImage shownIcon) {
            shownIcon.addStyleName("KS-Basic-Menu-Item-Image");
            contentPanel.add(shownIcon);

        }

        public void deSelect(){
            this.removeStyleName("KS-Basic-Menu-Item-Panel-Selected");
            itemLabel.removeStyleName("KS-Basic-Menu-Item-Label-Selected");
            selected = false;
        }

        public void select(){
            this.addStyleName("KS-Basic-Menu-Item-Panel-Selected");
            itemLabel.addStyleName("KS-Basic-Menu-Item-Label-Selected");
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
    	menuPanel.clear();
    	menuItems.clear();
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

	public void addGlobalMenuItemSelectCallback(Callback<KSMenuItemData> callback){
    	globalCallbacks.add(callback);
    }

    public MenuImageLocation getImageLocation() {
		return imgLoc;
	}

	public void setImageLocation(MenuImageLocation imgLoc) {
		this.imgLoc = imgLoc;
	}

}

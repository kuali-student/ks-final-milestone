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
package org.kuali.student.common.ui.client.widgets.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * The data object used to populate interactive ui menus.
 * 
 * @author Kuali Student Team
 *
 */
public class KSMenuItemData {
	private String label;
	private ClickHandler clickHandler;
	private List<KSMenuItemData> subItems = new ArrayList<KSMenuItemData>();
	private KSMenuItemData parent = null;
	private boolean selected = false;
	private KSImage shownIcon = null;
	
	private HandlerManager manager = new HandlerManager(this);

	public KSMenuItemData(String label) {
		super();
		this.label = label;
	}
		
	public KSMenuItemData(String label, ClickHandler clickHandler) {
		super();
		this.label = label;
		this.clickHandler = clickHandler;
	}
	
	public KSMenuItemData(String label, KSImage icon, ClickHandler clickHandler) {
		super();
		this.label = label;
		this.shownIcon = icon;
		this.clickHandler = clickHandler;
	}

	/**
	 * Get the text used for this menu item
	 * 
	 * @return the "label" for this menu item
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Set the label to be used in the menu for this menu item
	 * 
	 * @param label the "label" of this menu item
	 */
	public void setLabel(String label) {
		this.label = label;
		manager.fireEvent(new MenuChangeEvent());
	}
	/**
	 * Get the ClickHandler for this menu item.
	 * 
	 * @return ClickHandler which controls what this menu item does when selected
	 */
	public ClickHandler getClickHandler() {
		return clickHandler;
	}
	/**
	 * Set the click handler for this menu item (what the menu item does when selected).
	 * 
	 * @param clickHandler a ClickHandler for this menu item.
	 */
	public void setClickHandler(ClickHandler clickHandler) {
		this.clickHandler = clickHandler;
	}
	
	/**
	 * Adds a KSMenuItemData to the list of children for this menu "category".
	 * 
	 * @param item a KSMenuItemData that is a child of this KSMenuItemData
	 */
	public void addSubItem(KSMenuItemData item) {
		subItems.add(item);
		item.setParent(this);
	}
	
	/**
	 * Gets the list of sub items (children) in this KSMenuItemData
	 * 
	 * @return the list of sub items in this KSMenuItemData
	 */
	public List<KSMenuItemData> getSubItems() {
		return Collections.unmodifiableList(subItems);
	}

    /**
     * Set the parent of this KSMenuItemData
     * 
     * @param parent the KSMenuItemData which is the parent KSMenuItemData (category)
     */
    public void setParent(KSMenuItemData parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent of this KSMenuItemData
     * 
     * @return the paren of this KSMenuItemData
     */
    public KSMenuItemData getParent() {
        return parent;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected == true){
            manager.fireEvent(new MenuSelectEvent());
        }
    }
    
    public void unhandledSetSelected(boolean selected){
        this.selected = selected;
    }

    public KSImage getShownIcon() {
        return shownIcon;
    }

    public void setShownIcon(KSImage shownIcon) {
        this.shownIcon = shownIcon;
        manager.fireEvent(new MenuChangeEvent());
    }
    
    @SuppressWarnings("unchecked")
    public HandlerRegistration addMenuEventHandler(Type type, MenuEventHandler meh){
        return manager.addHandler(type, meh);
    }
    
    
}

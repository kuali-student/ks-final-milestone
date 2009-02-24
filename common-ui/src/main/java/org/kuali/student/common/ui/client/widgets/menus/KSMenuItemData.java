package org.kuali.student.common.ui.client.widgets.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;

public class KSMenuItemData {
	private String label;
	private ClickHandler clickHandler;
	private List<KSMenuItemData> subItems = new ArrayList<KSMenuItemData>();
	private KSMenuItemData parent = null;

	public KSMenuItemData(String label) {
		super();
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public ClickHandler getClickHandler() {
		return clickHandler;
	}
	public void setClickHandler(ClickHandler clickHandler) {
		this.clickHandler = clickHandler;
	}
	
	public void addSubItem(KSMenuItemData item) {
		subItems.add(item);
		item.setParent(this);
	}
	
	public List<KSMenuItemData> getSubItems() {
		return Collections.unmodifiableList(subItems);
	}

    public void setParent(KSMenuItemData parent) {
        this.parent = parent;
    }

    public KSMenuItemData getParent() {
        return parent;
    }
}

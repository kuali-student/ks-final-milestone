package org.kuali.student.common.ui.client.widgets.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;

public class KSMenuItemData {
	private String label;
	private ClickHandler clickHandler;
	private List<KSMenuItemData> subItems = new ArrayList<KSMenuItemData>();
	

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
	}
	
	public List<KSMenuItemData> getSubItems() {
		return Collections.unmodifiableList(subItems);
	}
}

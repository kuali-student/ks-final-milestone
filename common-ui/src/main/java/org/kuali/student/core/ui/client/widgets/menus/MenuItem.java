package org.kuali.student.core.ui.client.widgets.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;

public class MenuItem {
	private String label;
	private ClickHandler clickHandler;
	private List<MenuItem> subItems = new ArrayList<MenuItem>();
	

	public MenuItem(String label) {
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
	
	public void addSubItem(MenuItem item) {
		subItems.add(item);
	}
	
	public List<MenuItem> getSubItems() {
		return Collections.unmodifiableList(subItems);
	}
}

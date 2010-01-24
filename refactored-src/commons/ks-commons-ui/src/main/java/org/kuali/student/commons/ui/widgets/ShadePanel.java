package org.kuali.student.commons.ui.widgets;

import java.util.Iterator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ShadePanel extends Panel implements HasWidgets {
	final Panel shade = new SimplePanel();
	final PopupPanel panel = new PopupPanel(false, true);
	
	public ShadePanel() {
		super();
		shade.setStyleName("KS-Shade-Panel-Shade");
		
		shade.setHeight("" + Window.getClientHeight());
		shade.setWidth("" + Window.getClientWidth());
		
		panel.setStyleName("KS-Shade-Panel-Content");
	}
	

	
	public void setHeight(String height) {
		panel.setHeight(height);
	}
	
	public void setWidget(Widget w) {
		panel.add(w);
	}
	
	public void add(Widget w) {
		this.setWidget(w);
	}

	public void setWidth(String width) {
		panel.setWidth(width);
	}
	
	public Widget getWidget() {
		Iterator<Widget> itr = panel.iterator();
		if (itr.hasNext()) {
			return itr.next();
		} else {
			return null;
		}
	}

	public Iterator<Widget> iterator() {
		return panel.iterator();
	}
	
	public boolean remove(Widget w) {
		return panel.remove(w);
	}
	
	public void clear() {
		panel.clear();
	}
	
	public void setSize(String width, String height) {
		panel.setSize(width, height);
	}

	public void hide() {
		panel.hide();
		RootPanel.get().remove(shade);
		Window.enableScrolling(true);
	}

	public void show() {
		Window.enableScrolling(false);
		RootPanel.get().add(shade,0,0);
		panel.center();
	}



	public void addPopupListener(PopupListener listener) {
		panel.addPopupListener(listener);
	}
	
	
}

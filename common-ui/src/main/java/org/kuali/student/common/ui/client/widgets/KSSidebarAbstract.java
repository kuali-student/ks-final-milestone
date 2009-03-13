package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.KSFloatPanel.FloatLocation;

import com.google.gwt.user.client.ui.Widget;



public abstract class KSSidebarAbstract{
	protected abstract void init();
	
	protected abstract void init(FloatLocation location);
	
	public abstract void show();
	
	public abstract void hide();
	
	public abstract void showContent(Widget content, String title);
	
	public abstract  void hideContent();

	public abstract void addTab(Widget content, String name);
	
	public abstract void removeTab(String name);
}

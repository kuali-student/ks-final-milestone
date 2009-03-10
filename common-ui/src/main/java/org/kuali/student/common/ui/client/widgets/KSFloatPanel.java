package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Widget;

public interface KSFloatPanel {
	public enum FloatLocation {
		FLOAT_RIGHT,
		FLOAT_LEFT
	}
	
	public int getHeightRatio();

	public void setHeightRatio(int heightRatio);

	public FloatLocation getLocation();

	public void setLocation(FloatLocation location);

	public boolean isVisible();

	public void setWidget(Widget w);

	public Widget getWidget();

	public void show();

	public void hide();
	
	public void setStyleName(String style);
	
	public void addStyleName(String style);
}

package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import com.google.gwt.user.client.ui.Widget;

public interface DocumentLayoutController extends ViewLayoutController{
	public void setContentTitle(String title);
	public void addContentWidget(Widget w);
	public void setContentInfo(String text);
}

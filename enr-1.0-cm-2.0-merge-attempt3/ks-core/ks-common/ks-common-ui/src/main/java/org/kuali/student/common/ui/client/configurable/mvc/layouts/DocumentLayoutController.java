package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface for layouts which have document headers (titles) and widgets associated with all
 * the content of a controller.
 * 
 * @author Kuali Student Team
 *
 */
public interface DocumentLayoutController extends ViewLayoutController{
	public void setContentTitle(String title);
	public void addContentWidget(Widget w);
	public void setContentInfo(String text);
}

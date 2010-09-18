package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.user.client.ui.Widget;

public interface ContentNavLayoutController extends DocumentLayoutController{
	public void removeMenuNavigation();
	public void addMenu(String title);
	public void addMenuItem(String parentMenu, final View view);
	public void addButtonForView(Enum<?> viewType, KSButton button);
	public void addCommonButton(String parentMenu, KSButton button);
	public void addSpecialMenuItem(final View view, String description);
}

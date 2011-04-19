package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;

/**
 * Interface for LayoutControllers which have navigation with menus.
 * 
 * @author Kuali Student Team
 *
 */
public interface ContentNavLayoutController extends DocumentLayoutController{
	/**
	 * Removes the navigation from the ui
	 */
	public void removeMenuNavigation();
	public void addMenu(String title);
	public void addMenuItem(String parentMenu, final View view);
	/**
	 * Adds a button specific to a particular view
	 * @param viewType
	 * @param button
	 */
	public void addButtonForView(Enum<?> viewType, KSButton button);
	/**
	 * Adds a button which will appear on every view underneath the scope of the
	 * parent menu item
	 * @param parentMenu
	 * @param button
	 */
	public void addCommonButton(String parentMenu, KSButton button);
	/**
	 * Adds a button which will appear on every view underneath the scope of the of the parent
	 * menu item EXCEPT not in the views specified in excludedViews
	 * @param parentMenu
	 * @param button
	 * @param excludedViews
	 */
	public void addCommonButton(String parentMenu, KSButton button, List<Enum<?>> excludedViews);
	/**
	 * Adds a menu item with a special style
	 * @param view
	 * @param description
	 */
	public void addSpecialMenuItem(final View view, String description);
}

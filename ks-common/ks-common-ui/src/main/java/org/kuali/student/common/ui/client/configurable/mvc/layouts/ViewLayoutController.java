package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.mvc.View;

public interface ViewLayoutController {
	
	/**
	 * Add a view to this LayoutController.  A view's "key" is defined by its viewType enumeration.
	 * @param view
	 */
	public void addView(View view);
	
	/**
	 * Shows the view which corresponds to the viewType enumeration "key"
	 * @param <V>
	 * @param viewType
	 */
	public <V extends Enum<?>> void showView(final V viewType);
	
	/**
	 * Set the view with the viewType enum passed in to the be the default view.  What the default view
	 * does varies on controller implementation, but will likely be the first view visible.
	 * @param <V>
	 * @param viewType
	 */
	public <V extends Enum<?>> void setDefaultView(V viewType);
	
	/**
	 * Creates a popup window with the view, that can be shown when needed for additional information needed
	 * from the user
	 * @param view
	 */
	public void addStartViewPopup(final View view);
}

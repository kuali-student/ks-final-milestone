package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.View;

import com.google.gwt.user.client.ui.FlowPanel;

public class BasicLayout extends LayoutController{
	private FlowPanel viewContainer = new FlowPanel();

	public BasicLayout(String controllerId) {
		super(controllerId);
		this.initWidget(viewContainer);
	}

	@Override
	protected void hideView(View view) {
		viewContainer.clear();
	}

	@Override
	protected void renderView(View view) {
		viewContainer.add(view.asWidget());
	}

	/** 
	 * This version of updateModel only updates from the currentView (since only one view is shown/accessed at a time).  
	 * Call updateModelFromView to update from a specific view under this controller's scope.
	 */
	@Override
	public void updateModel() {
		this.updateModelFromCurrentView();
	}
}

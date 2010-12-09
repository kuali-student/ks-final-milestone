package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;

import com.google.gwt.user.client.ui.FlowPanel;

/**
 *  A basic implementation of a LayoutController, no navigation, all showView calls must be invoked
 * by the app.  This implementation has a header that can be manipulated.
 * 
 * @author Kuali Student Team
 *
 */
public class BasicLayoutWithContentHeader extends LayoutController{

	protected FlowPanel viewContainer = new FlowPanel();
	protected FlowPanel layout = new FlowPanel();
	protected KSDocumentHeader header = new KSDocumentHeader(false);

	public BasicLayoutWithContentHeader(String controllerId) {
		super(controllerId);
		layout.add(header);
		layout.add(viewContainer);
		this.initWidget(layout);
		header.addStyleName("Lum-DocumentHeader-Spacing");
	}
	
	public KSDocumentHeader getHeader(){
		return header;
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

package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * A really simple controller which has a view it shows automatically, and does not log navigation events
 * (thus there will be no breadcrumbing or history).  This controller is meant to be used inline when
 * you want to have a separate model from the parent controller (and correspondingly separate validation)
 *
 * You can add and switch between additional views freely without history being logged
 */
public class InlineViewController extends BasicLayout {
	public InlineViewController(String controllerId, View defaultView) {
		super(controllerId);
		this.fireNavEvents(false);
		this.addView(defaultView);
		this.setDefaultView(defaultView.getViewEnum());
		this.showView(defaultView.getViewEnum());
	}
}

package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationPanel {
	private static final ApplicationPanel _impl = GWT.create(ApplicationPanel.class);
	private ApplicationPanel() {
		
	}
	private AbsolutePanel _get() {
		AbsolutePanel result = RootPanel.get("applicationPanel");
		if (result == null) {
			result = RootPanel.get();
		}
		return result;
	}
	public static AbsolutePanel get() {
		return _impl._get();
	}
}
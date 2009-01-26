package org.kuali.student.core.web.organization.client.view;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class OrgEntryPoint implements EntryPoint{

	public void onModuleLoad() {
		RootPanel.get().add(new Label("Hello World!"));
	}

}

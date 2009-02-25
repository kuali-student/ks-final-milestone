package org.kuali.student.core.organization.ui.client.view;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class OrgEntryPoint implements EntryPoint{

	public void onModuleLoad() {
        if(DOM.getElementById("loadingSpinner") != null)
            DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loadingSpinner"));
	    RootPanel.get().add(new OrgMain());
	}

}

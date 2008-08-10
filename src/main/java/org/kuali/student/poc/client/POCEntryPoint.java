package org.kuali.student.poc.client;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.ViewMetadataLoadCallback;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.poc.client.admin.AdminPanel;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class POCEntryPoint {
	
	public void onModuleLoad() {
		List<String> views = new ArrayList<String>(Arrays.asList(POCMain.VIEW_NAME, AdminPanel.VIEW_NAME, AdminEditPanel.VIEW_NAME));
		
		ApplicationContext.loadViewMetadata(views, new ViewMetadataLoadCallback() {
			public void onLoad(String viewName, ViewMetaData view) {
				// do nothing
			}
			public void onBulkLoadComplete() {
				DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loadingSpinner"));
				RootPanel.get().add(new POCMain());
			}
		});
	}
}

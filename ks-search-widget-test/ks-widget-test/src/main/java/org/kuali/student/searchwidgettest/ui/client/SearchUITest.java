package org.kuali.student.searchwidgettest.ui.client;

import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class SearchUITest implements EntryPoint {
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);

	@Override
	public void onModuleLoad() {
		GWT.log("HEREs", null);
		//
		KSAdvancedSearchWindow searchWindow = new KSAdvancedSearchWindow(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName",
				"org.resultColumn.orgId", "Find Organization");
		searchWindow.show();

		//RootPanel.get().add(xxx);
	}

}

package org.kuali.student.core.organization.web.client.view;

import java.util.List;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class OrgEntryPoint implements EntryPoint{

	HorizontalPanel panel;
	
	public void onModuleLoad() {
		RootPanel.get().add(new Label("Hello World!"));
		panel = new HorizontalPanel();
		RootPanel.get().add(panel);
		
		OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<OrgHierarchyInfo> result) {
				for(OrgHierarchyInfo orgHierarchyInfo:result){
					panel.add(new Label(orgHierarchyInfo.getName()));
				}
			}
			
		});
		
	}

}

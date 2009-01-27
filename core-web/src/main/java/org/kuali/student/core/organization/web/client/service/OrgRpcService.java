package org.kuali.student.core.organization.web.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface OrgRpcService extends RemoteService, OrgRemoteService {
	
	public static final String SERVICE_URI = "OrgRpcService";

	public static class Util {

		public static OrgRpcServiceAsync getInstance() {

			OrgRpcServiceAsync instance = (OrgRpcServiceAsync) GWT
					.create(OrgRpcService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}
}

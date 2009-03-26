package org.kuali.student.core.person.ui.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface PersonRpc extends RemoteService, PersonServiceClient {
	
	public static final String SERVICE_URI = "PersonRpc";

	public static class Util {

		public static PersonRpcAsync getInstance() {

			PersonRpcAsync instance = (PersonRpcAsync) GWT
					.create(PersonRpc.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}
}
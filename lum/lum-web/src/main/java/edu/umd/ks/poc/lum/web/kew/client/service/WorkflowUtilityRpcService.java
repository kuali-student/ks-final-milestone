package edu.umd.ks.poc.lum.web.kew.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface WorkflowUtilityRpcService extends RemoteService, WorkflowUtilityService {

	public static final String SERVICE_URI = "WorkflowUtilityRpcService";

	public static class Util {

		public static WorkflowUtilityRpcServiceAsync getInstance() {

		    
			WorkflowUtilityRpcServiceAsync instance = (WorkflowUtilityRpcServiceAsync) GWT
					.create(WorkflowUtilityRpcService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}

}

package edu.umd.ks.poc.lum.web.scat.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ScatRpcService extends RemoteService, ScatService {

	public static final String SERVICE_URI = "ScatRpcService";

	public static class Util {

		public static ScatRpcServiceAsync getInstance() {

		    
			ScatRpcServiceAsync instance = (ScatRpcServiceAsync) GWT
					.create(ScatRpcService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}

}

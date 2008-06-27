package org.kuali.student.commons.ui.viewmetadata.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ViewMetaDataService extends RemoteService {
	public ViewMetaData getViewMetaData(String locale, String viewName);
	public Map<String, ViewMetaData> getViewMetaDataMap(String locale, List<String> viewNames);
	
	public static final class Util {
		public static final String SERVICE_URI = "/ViewMetaDataService";
		private static ViewMetaDataServiceAsync service = null;
		public static synchronized ViewMetaDataServiceAsync getService(){
			if(service == null){
				service = (ViewMetaDataServiceAsync) GWT.create( ViewMetaDataService.class );
				ServiceDefTarget endpoint = (ServiceDefTarget) service;						
				endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);			
			}
			return service;
		}
	}
}

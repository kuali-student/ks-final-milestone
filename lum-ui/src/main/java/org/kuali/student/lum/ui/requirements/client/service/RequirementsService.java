package org.kuali.student.lum.ui.requirements.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface RequirementsService extends RemoteService, RequirementsRemoteService {
	
    public static final String SERVICE_URI = "RequirementsService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        /**
         * Creates an instance of the RequirementsServiceAsync interface using module base URL and the SERVICE_URI constant
         * 
         * @return instance of RequirementsAsync
         */
        public static RequirementsServiceAsync getInstance() {
            RequirementsServiceAsync instance = (RequirementsServiceAsync) GWT.create(RequirementsService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }       
    }
}

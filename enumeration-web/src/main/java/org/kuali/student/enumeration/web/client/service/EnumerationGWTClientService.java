package org.kuali.student.enumeration.web.client.service;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface EnumerationGWTClientService extends RemoteService{
    /**
     * URI for the service servlet.
     */
    public static final String SERVICE_URI = "/GuiService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        public static EnumerationGWTClientServiceAsync getInstance() {
            EnumerationGWTClientServiceAsync instance = (EnumerationGWTClientServiceAsync) GWT.create(EnumerationGWTClientService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    public List<String> fetchEnumertionMeta();
}
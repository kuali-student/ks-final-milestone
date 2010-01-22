package org.kuali.student.commons.ui.logging.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface LogService extends RemoteService {

	
    /**
     * URI for the log service servlet.
     */
    public static final String SERVICE_URI = "/LogService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        /**
         * Creates an instance of the LogServiceAsync interface using module base URL and the SERVICE_URI constant
         * 
         * @return instance of LogServiceAsync
         */
        public static LogServiceAsync getInstance() {

            LogServiceAsync instance = (LogServiceAsync) GWT.create(LogService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    
    
    /**
     * Sends a List<LogMessage> of messages to the server.
     * @param clientContextInfo any configuration or runtime information about the client
     * @param messages the messages to log
     */
    public Boolean sendLog(Map<String, String> clientContextInfo, String log);
}

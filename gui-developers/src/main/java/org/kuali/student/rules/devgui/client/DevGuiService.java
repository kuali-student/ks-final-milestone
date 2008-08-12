/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import org.kuali.student.rules.devgui.client.model.BusinessRule;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author zzraly
 */
public interface DevGuiService extends RemoteService {
    /**
     * URI for the log service servlet.
     */
    public static final String SERVICE_URI = "/DevGuiService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        /**
         * Creates an instance of the DevGuiServiceAsync interface using module base URL and the SERVICE_URI constant
         * 
         * @return instance of LogServiceAsync
         */
        public static DevGuiServiceAsync getInstance() {
            DevGuiServiceAsync instance = (DevGuiServiceAsync) GWT.create(DevGuiService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }

    public BusinessRule getBusinessRule(String identifier);
}

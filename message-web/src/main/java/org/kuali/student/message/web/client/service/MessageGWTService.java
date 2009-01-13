package org.kuali.student.message.web.client.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;

import org.kuali.student.message.web.client.service.MessageGWTServiceAsync;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface MessageGWTService extends RemoteService{
    /**
     * URI for the service servlet.
     */
    public static final String SERVICE_URI = "/GuiService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        public static MessageGWTServiceAsync getInstance() {
            MessageGWTServiceAsync instance = (MessageGWTServiceAsync) GWT.create(MessageGWTService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }

}
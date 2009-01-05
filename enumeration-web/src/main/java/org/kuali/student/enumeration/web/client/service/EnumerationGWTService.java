package org.kuali.student.enumeration.web.client.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMetaList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface EnumerationGWTService extends RemoteService{
    /**
     * URI for the service servlet.
     */
    public static final String SERVICE_URI = "/GuiService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        public static EnumerationGWTServiceAsync getInstance() {
            EnumerationGWTServiceAsync instance = (EnumerationGWTServiceAsync) GWT.create(EnumerationGWTService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    public EnumerationMetaList fetchEnumerationMetas();
    
    public EnumeratedValueList fetchEnumeration(String enumerationKey,String enumContextKey,String contextValue,Date   contextDate );
    
    public EnumeratedValue addEnumeratedValue(String enumerationKey,EnumeratedValue value);

    public EnumeratedValue updateEnumeratedValue(String enumerationKey,String code,EnumeratedValue value);

    public boolean removeEnumeratedValue(String enumerationKey,String code);
}
/**
 * 
 */
package org.kuali.student.rules.lumgui.client.service;

import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.lumgui.client.model.ReqComponentTypeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author zzraly
 */
public interface LumGuiService extends RemoteService {
    /**
     * URI for the log service servlet.
     */
    public static final String SERVICE_URI = "/LumGuiService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        /**
         * Creates an instance of the LumGuiServiceAsync interface using module base URL and the SERVICE_URI constant
         * 
         * @return instance of LogServiceAsync
         */
        public static LumGuiServiceAsync getInstance() {
            LumGuiServiceAsync instance = (LumGuiServiceAsync) GWT.create(LumGuiService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }       
    }
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception;
}

package org.kuali.student.commons.ui.viewmetadata.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Synchronous interface defining the ViewMetaDataService
 */
public interface ViewMetaDataService extends RemoteService {
    /**
     * Returns a Map<String, ViewMetaData> containing the ViewMetaData for the specified viewName, 
     * and internationalization messages associated with the specified locale, as well as the view metadata 
     * for any views on which the specified view depends.  
     * 
     * @param locale
     *            the locale to use when retrieving internationalization messages
     * @param viewName
     *            the view for which to retrieve metadata
     * @return Map<String, ViewMetaData> containing the ViewMetaData for the specified viewName, and internationalization for that view associated with the
     *         specified locale
     */
    public Map<String, ViewMetaData> getViewMetaData(String locale, String viewName);

    /**
     * Returns the ViewMetaData for the specified viewNames, and internationalization for those views associated with the
     * specified locale
     * 
     * @param locale
     *            the locale to use when retrieving internationalization messages
     * @param viewNames
     *            the views for which to retrieve metadata
     * @return the ViewMetaData for the specified viewNames, and internationalization for those views associated with the
     *         specified locale
     */
    public Map<String, ViewMetaData> getViewMetaDataMap(String locale, List<String> viewNames);

    /**
     * Utility class providing getInstance method for creating a ViewMetaDataServiceAsync instance
     */
    public static final class Util {
        /**
         * URI for the servlet implementing the ViewMetaData service
         */
        public static final String SERVICE_URI = "/ViewMetaDataService";
        private static ViewMetaDataServiceAsync service = null;

        /**
         * Creates an instance of ViewMetaDataServiceAsync using the module base URL and the SERVICE_URI constant
         * 
         * @return an instance of ViewMetaDataServiceAsync
         */
        public static synchronized ViewMetaDataServiceAsync getService() {
            if (service == null) {
                service = (ViewMetaDataServiceAsync) GWT.create(ViewMetaDataService.class);
                ServiceDefTarget endpoint = (ServiceDefTarget) service;
                endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            }
            return service;
        }
    }
}

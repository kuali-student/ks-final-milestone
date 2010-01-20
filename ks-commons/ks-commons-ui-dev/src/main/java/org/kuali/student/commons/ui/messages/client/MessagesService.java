package org.kuali.student.commons.ui.messages.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Synchronous service definition for Messages service used to retrieve internationalization messages.
 */
public interface MessagesService extends RemoteService {

    /**
     * URI for the Messages service servlet.
     */
    public static final String SERVICE_URI = "/MessagesService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        /**
         * Creates an instance of the MessagesServiceAsync interface using module base URL and the SERVICE_URI constant
         * 
         * @return instance of MessagesServiceAsync
         */
        public static MessagesServiceAsync getInstance() {

            MessagesServiceAsync instance = (MessagesServiceAsync) GWT.create(MessagesService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }

    /**
     * Returns the internationalization messages associated with the specified locale and group name
     * 
     * @param locale
     *            locale code
     * @param groupName
     *            the logical group name of the messages to retrieve
     * @return messages for the specified locale and group
     */
    public Messages getMessages(String locale, String groupName);

    /**
     * Returns the internationalization messages associated with the specified locale and group names
     * 
     * @param locale
     *            locale code
     * @param groupName
     *            the logical group names of the messages to retrieve
     * @return messages for the specified locale and groups
     */
    public Map<String, Messages> getMessages(String locale, List<String> groupNames);

    /**
     * Returns a list of the available internationalization locales
     * 
     * @return list of the available internationalization locales
     */
    public List<String> getLocales();

    /**
     * Returns a list of the available internationalization message groups for the specified locale
     * 
     * @param locale
     *            locale for which to return the available message groups
     * @return list of the available internationalization message groups for the specified locale
     */
    public List<String> getGroupNames(String locale);

}

package org.kuali.student.commons.ui.messages.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous service interface for the Messages service, to be used on client.
 */
public interface MessagesServiceAsync {

    /**
     * Retrieves the internationalization messages associated with the specified locale and group name
     * 
     * @param locale
     *            locale code
     * @param groupName
     *            the logical group name of the messages to retrieve
     * @param callback
     *            callback to be executed on response
     */
    public void getMessages(String locale, String groupName, AsyncCallback<Messages> callback);

    /**
     * Retrieves the internationalization messages associated with the specified locale and group names
     * 
     * @param locale
     *            locale code
     * @param groupName
     *            the logical group names of the messages to retrieve
     * @param callback
     *            callback to be executed on response
     */
    public void getMessages(String locale, List<String> groupNames, AsyncCallback<Map<String, Messages>> callback);

    /**
     * Retrieves a list of the available internationalization locales
     * 
     * @param callback
     *            callback to be executed on response
     */
    public void getLocales(AsyncCallback<List<String>> callback);

    /**
     * Retrieves a list of the available internationalization message groups for the specified locale
     * 
     * @param locale
     *            locale for which to return the available message groups
     * @param callback
     *            callback to be executed on response
     */
    public void getGroupNames(String locale, AsyncCallback<List<String>> callback);

}

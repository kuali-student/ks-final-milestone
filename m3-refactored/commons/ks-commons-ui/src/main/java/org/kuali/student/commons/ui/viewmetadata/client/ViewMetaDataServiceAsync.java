package org.kuali.student.commons.ui.viewmetadata.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ViewMetaDataServiceAsync {
    /**
     * Retrieves the ViewMetaData for the specified viewName, and internationalization for that view associated with the
     * specified locale
     * 
     * @param locale
     *            the locale to use when retrieving internationalization messages
     * @param viewName
     *            the view for which to retrieve metadata
     * @param callback
     *            the callback to invoke when response is received
     */
    public void getViewMetaData(String locale, String viewName, AsyncCallback<Map<String, ViewMetaData>> callback);

    /**
     * Retrieves the ViewMetaData for the specified viewNames, and internationalization for those views associated with the
     * specified locale
     * 
     * @param locale
     *            the locale to use when retrieving internationalization messages
     * @param viewNames
     *            the views for which to retrieve metadata
     * @param callback
     *            the callback to invoke when response is received
     */
    public void getViewMetaDataMap(String locale, List<String> viewNames, AsyncCallback<Map<String, ViewMetaData>> callback);
}

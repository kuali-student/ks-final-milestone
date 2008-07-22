package org.kuali.student.commons.ui.mvc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.logging.client.Logger;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataServiceAsync;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Global object to be used for configuration information, etc, that is shared by all controllers in a given page.
 */
public class ApplicationContext {
    // default locale of "en"
    static String locale = "en";
    static SecurityContext securityContext;
    static Map<String, ViewMetaData> views = new HashMap<String, ViewMetaData>();
    static EventDispatcher globalEventDispatcher = new EventDispatcher();
    static Map<String, String> settings = new HashMap<String, String>();

    static {
        try {
            Dictionary dict = Dictionary.getDictionary("kualiStudentSettings");
            for (String key : dict.keySet()) {
                settings.put(key, dict.get(key));
            }
            locale = settings.get("locale");
        } catch (Exception e) {
            Logger.warn("No settings dictionary defined.", e);
        }
    }

    /**
     * Returns the current locale code
     * 
     * @return the current locale code
     */
    public static String getLocale() {
        return locale;
    }

    /**
     * Sets the current locale code. Warning: Do not call this method after any localized data has been retrieved from the
     * server, or the locales will be out of sync.
     * 
     * @param locale
     *            the locale code to set
     */
    public static void setLocale(String locale) {
        ApplicationContext.locale = locale;
    }

    /**
     * Returns the security context for the page view. TODO: integrate with actual security framework once it has been
     * determined
     * 
     * @return the security context for the page view
     */
    public static SecurityContext getSecurityContext() {
        return securityContext;
    }

    /**
     * Sets the security context for the page view
     * 
     * @param securityContext
     *            the security context to set
     */
    public static void setSecurityContext(SecurityContext securityContext) {
        ApplicationContext.securityContext = securityContext;
    }

    /**
     * Returns a Map containing the currently loaded view metadata objects. Map is keyed by view name.
     * 
     * @return Map containing the currently loaded view metadata objects
     */
    public static Map<String, ViewMetaData> getViews() {
        return views;
    }

    /**
     * Loads view metadata from service. Automatically adds results to local cache.
     * 
     * @param viewName
     *            the name of the view for which to load metadata
     * @param callback
     *            callback to be used to signal load complete
     */
    public static void loadViewMetadata(final String viewName, final ViewMetadataLoadCallback callback) {
        List<String> list = new ArrayList<String>();
        list.add(viewName);
        loadViewMetadata(list, callback);
    }

    /**
     * Bulk loads view metadata from service. Automatically adds results to local cache.
     * 
     * @param viewName
     *            the name of the view for which to load metadata
     * @param callback
     *            callback to be used to signal load complete
     */
    public static void loadViewMetadata(final List<String> viewNames, final ViewMetadataLoadCallback callback) {
        ViewMetaDataServiceAsync service = ViewMetaDataService.Util.getService();
        service.getViewMetaDataMap(locale, viewNames, new AsyncCallback<Map<String, ViewMetaData>>() {

            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load view metadata", caught);
            }

            public void onSuccess(Map<String, ViewMetaData> result) {
                for (String key : result.keySet()) {
                    ViewMetaData vmd = result.get(key);
                    views.put(key, vmd);
                    callback.onLoad(key, vmd);
                }
                callback.onBulkLoadComplete();
            }

        });
    }

    /**
     * Returns reference to global event dispatcher. Global event dispatcher should only be used for events that are not view
     * specific, such as global "busy indicators" or login/logout events, etc.
     * 
     * @return
     */
    public static EventDispatcher getGlobalEventDispatcher() {
        return globalEventDispatcher;
    }
}

package org.kuali.student.commons.ui.mvc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ApplicationContext {
	static String locale;
	static SecurityContext securityContext;
	static Map<String, ViewMetaData> views = new HashMap<String, ViewMetaData>();
	static EventDispatcher globalEventDispatcher = new EventDispatcher();
	
	
	public static String getLocale() {
		return locale;
	}

	public static void setLocale(String locale) {
		ApplicationContext.locale = locale;
	}
		
	public static SecurityContext getSecurityContext() {
		return securityContext;
	}

	public static void setSecurityContext(SecurityContext securityContext) {
		ApplicationContext.securityContext = securityContext;
	}

	public static Map<String, ViewMetaData> getViews() {
		return views;
	}

	public static void setViews(Map<String, ViewMetaData> views) {
		ApplicationContext.views = views;
	}

	public static void loadViewMetadata(final String viewName, final ViewMetadataLoadCallback callback) {
		List<String> list = new ArrayList<String>();
		list.add(viewName);
		loadViewMetadata(list, callback);
	}
	
	public static void loadViewMetadata(final List<String> viewNames, final ViewMetadataLoadCallback callback) {
		ViewMetaDataServiceAsync service = ViewMetaDataService.Util.getService();
		service.getViewMetaDataMap(locale, viewNames, new AsyncCallback() {

			public void onFailure(Throwable caught) {
				throw new RuntimeException("Unable to load view metadata", caught);
			}

			public void onSuccess(Object result) {
				Map<String, ViewMetaData> m = (Map<String, ViewMetaData>) result;
				for (String key : viewNames) {
					ViewMetaData vmd = m.get(key);
					views.put(key, vmd);
					callback.onLoad(key, vmd);
				}
				callback.onBulkLoadComplete();
			}
			
		});
	}
	
	public static EventDispatcher getGlobalEventDispatcher() {
		return globalEventDispatcher;
	}
}

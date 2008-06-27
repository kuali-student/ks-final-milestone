package org.kuali.student.commons.ui.viewmetadata.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ViewMetaDataServiceAsync {
	public void getViewMetaData(String locale, String viewName, AsyncCallback callback);
	public void getViewMetaDataMap(String locale, List<String> viewNames, AsyncCallback callback);
}

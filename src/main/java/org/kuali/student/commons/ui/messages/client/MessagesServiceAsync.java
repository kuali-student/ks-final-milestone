package org.kuali.student.commons.ui.messages.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagesServiceAsync {

	public void getMessages(String locale, String groupName, AsyncCallback<Messages> callback);
	public void getMessages(String locale, List<String> groupNames, AsyncCallback<Map<String, Messages>> callback);
	
	public void getLocales(AsyncCallback<List<String>> callback);
	public void getGroupNames(String locale, AsyncCallback<List<String>> callback);

}

package org.kuali.student.poc.client.admin;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagesAdminServiceAsync {
	public void getMessages(String locale, String groupName, AsyncCallback<List<MessageModelObject>> callback);
	public void getMessageGroupTree(AsyncCallback<Map<String, List<String>>> callback);
	public void update(MessageModelObject message, AsyncCallback<Boolean> callback);
	public void update(List<MessageModelObject> messages, AsyncCallback<Boolean> callback);
}

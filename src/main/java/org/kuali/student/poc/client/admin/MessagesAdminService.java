package org.kuali.student.poc.client.admin;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface MessagesAdminService extends RemoteService {
	
	public static final String SERVICE_URI = "MessagesAdminService";

	public static class Util {

		public static MessagesAdminServiceAsync getInstance() {

			MessagesAdminServiceAsync instance = (MessagesAdminServiceAsync) GWT
					.create(MessagesAdminService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}
	
	public List<MessageModelObject> getMessages(String locale, String groupName);
	public Map<String, List<String>> getMessageGroupTree();
	public Boolean update(MessageModelObject message);
	public Boolean update(List<MessageModelObject> messages);
}

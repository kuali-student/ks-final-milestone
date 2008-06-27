package org.kuali.student.commons.ui.messages.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface MessagesService extends RemoteService {

	public static final String SERVICE_URI = "/MessagesService";

	public static class Util {

		public static MessagesServiceAsync getInstance() {

			MessagesServiceAsync instance = (MessagesServiceAsync) GWT
					.create(MessagesService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}

	public Messages getMessages(String locale, String groupName);
	public Map<String, Messages> getMessages(String locale, List<String> groupNames);
	public List<String> getLocales();
	public List<String> getGroupNames(String locale);

	
}

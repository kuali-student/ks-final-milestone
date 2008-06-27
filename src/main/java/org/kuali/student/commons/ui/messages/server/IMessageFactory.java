package org.kuali.student.commons.ui.messages.server;

import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.messages.server.impl.MessagesServiceImpl;




public interface IMessageFactory {
	public Messages getMessages(String locale, String groupName);
	public Map<String, Messages> getMessages(String locale, List<String> groupNames);
	
	// following the GWT rpc pattern here, replace with dependency injection later
	public static class Util {
		private static IMessageFactory instance = new MessagesServiceImpl();
		public static IMessageFactory getInstance() {
			return instance;
		}
	}

}

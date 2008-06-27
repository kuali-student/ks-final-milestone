package org.kuali.student.commons.ui.messages.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.messages.client.MessagesService;
import org.kuali.student.commons.ui.messages.server.impl.MessagesServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesServiceImplGWT extends RemoteServiceServlet implements MessagesService {
	private static final long serialVersionUID = 1L;
	private static MessagesService service = new MessagesServiceImpl();
	
	@Override
	public Messages getMessages(String locale, String groupName) {
		return service.getMessages(locale, groupName);
	}

	@Override
	public Map<String, Messages> getMessages(String locale, List<String> groupNames) {
		return service.getMessages(locale, groupNames);
	}

	@Override
	public List<String> getGroupNames(String locale) {
		return service.getGroupNames(locale);
	}

	@Override
	public List<String> getLocales() {
		return service.getLocales();
	}

}

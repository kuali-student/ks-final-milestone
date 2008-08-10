package org.kuali.student.poc.server.admin.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.poc.client.admin.MessageModelObject;
import org.kuali.student.poc.client.admin.MessagesAdminService;
import org.kuali.student.poc.server.admin.impl.MessagesAdminServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesAdminServiceGWT extends RemoteServiceServlet implements MessagesAdminService {
	private static final long serialVersionUID = 1L;
	private static MessagesAdminService service = new MessagesAdminServiceImpl();
	
	@Override
	public Map<String, List<String>> getMessageGroupTree() {
		return service.getMessageGroupTree();
	}

	@Override
	public List<MessageModelObject> getMessages(String locale, String groupName) {
		return service.getMessages(locale, groupName);
	}

	@Override
	public Boolean update(MessageModelObject message) {
		return service.update(message);
	}

	@Override
	public Boolean update(List<MessageModelObject> messages) {
		return service.update(messages);
	}

}

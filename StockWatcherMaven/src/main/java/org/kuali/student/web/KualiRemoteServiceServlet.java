package org.kuali.student.web;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import static org.kuali.student.web.Constants.*;

public class KualiRemoteServiceServlet extends RemoteServiceServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		getThreadLocalRequest().setAttribute(REQUEST_PAYLOAD_KEY, serializedRequest);
		super.onBeforeRequestDeserialized(serializedRequest);
	}

	@Override
	protected void onAfterResponseSerialized(String serializedResponse) {
		getThreadLocalRequest().setAttribute(RESPONSE_PAYLOAD_KEY, serializedResponse);
		super.onAfterResponseSerialized(serializedResponse);
	}

}

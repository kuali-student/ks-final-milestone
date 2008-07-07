package org.kuali.student.commons.ui.logging.server;

import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.logging.client.LogMessage;
import org.kuali.student.commons.ui.logging.client.LogService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LogServiceImpl extends RemoteServiceServlet implements LogService {
	private static final long serialVersionUID = 1L;

	private static final String DELIM = "********************************************************************************";
	@Override
	public Boolean log(Map<String, String> clientContextInfo,
			List<LogMessage> messages) {
		// TODO actually do something with the logs
		System.out.println(DELIM);
		System.out.println("Client info: ");
		for (String key : clientContextInfo.keySet()) {
			String value = clientContextInfo.get(key);
			System.out.println("\t" + key + " = " + value);
		}
		System.out.println("\n\nMessages:");
		for (LogMessage m : messages) {
			System.out.println(m.getLogLevel().toString() + "\t" + m.getMessage());
			if (m.getError() != null) {
				m.getError().printStackTrace(System.out);
			}
		}
		return true;
	}

}

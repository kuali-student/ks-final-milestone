package org.kuali.student.commons.ui.logging.server;

import java.util.Map;

import org.kuali.student.commons.ui.logging.client.LogService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LogServiceImpl extends RemoteServiceServlet implements LogService {
	private static final long serialVersionUID = 1L;

	private static final String DELIM = "********************************************************************************";
	@Override
	public Boolean sendLog(Map<String, String> clientContextInfo, String log) {
		// TODO actually do something with the logs
		System.out.println(DELIM);
		System.out.println("Client info: ");
		for (String key : clientContextInfo.keySet()) {
			String value = clientContextInfo.get(key);
			System.out.println("\t" + key + " = " + value);
		}
		System.out.println("\n\nLog:");
		System.out.println(log);
		return true;
	}

}

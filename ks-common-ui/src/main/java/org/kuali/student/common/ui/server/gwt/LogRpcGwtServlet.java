package org.kuali.student.common.ui.server.gwt;

import java.util.Map;

import org.kuali.student.common.ui.client.service.LogRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LogRpcGwtServlet extends RemoteServiceServlet implements LogRpcService {
	private static final long serialVersionUID = 1L;

	
//    final static Logger logger = LoggerFactory.getLogger(LogRpcGwtServlet.class);

	private static final String DELIM = "********************************************************************************";
	@Override
	public Boolean sendLog(Map<String, String> clientContextInfo, String log) {
	    
		// TODO Use sl4j for logging
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

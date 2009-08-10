package org.kuali.student.common.ui.server.gwt;

import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.LogRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LogRpcGwtServlet extends RemoteServiceServlet implements LogRpcService {
	private static final long serialVersionUID = 1L;

	 final Logger logger = Logger.getLogger(LogRpcGwtServlet.class);
	
//    final static Logger logger = LoggerFactory.getLogger(LogRpcGwtServlet.class);

	private static final String DELIM = "********************************************************************************";
	@Override
	public Boolean sendLog(Map<String, String> clientContextInfo, String log) {
	    
		// TODO Use sl4j for logging
	    logger.debug(DELIM);
	    logger.debug("Client info: ");
		for (String key : clientContextInfo.keySet()) {
			String value = clientContextInfo.get(key);
			logger.debug("\t" + key + " = " + value);
		}
		logger.debug("\n\nLog:");
		logger.debug(log);
		return true;
	}

}

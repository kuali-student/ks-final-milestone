/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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

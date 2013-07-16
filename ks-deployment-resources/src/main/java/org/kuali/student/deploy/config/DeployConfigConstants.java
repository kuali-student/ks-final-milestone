package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.config.ConfigRequest;

public class DeployConfigConstants {

	public static final List<ConfigRequest> INIT_SOURCE_DB = getInitSourceDbConfigRequests();

	protected static List<ConfigRequest> getInitSourceDbConfigRequests() {
		List<ConfigRequest> requests = new ArrayList<ConfigRequest>();
		requests.addAll(JdbcConfigConstants.JDBC_CONFIG);
		requests.add(new InitializeSourceDbConfigRequest());
		return requests;
	}

}

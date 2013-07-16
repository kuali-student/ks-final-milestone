package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigRequest;
import org.kuali.common.jdbc.config.SqlConfigRequest;
import org.kuali.common.util.config.ConfigRequest;

public class ConfigConstants {

	public static final List<ConfigRequest> INIT_SOURCE_DB = getInitSourceDbConfigRequests();

	protected static List<ConfigRequest> getInitSourceDbConfigRequests() {
		List<ConfigRequest> requests = new ArrayList<ConfigRequest>();
		requests.add(new SqlConfigRequest());
		requests.add(new JdbcConfigRequest());
		requests.add(new InitializeSourceDbConfigRequest());
		return requests;
	}

}

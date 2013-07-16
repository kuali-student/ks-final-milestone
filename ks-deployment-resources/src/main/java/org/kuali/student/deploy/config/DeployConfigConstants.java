package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.config.ConfigRequest;
import org.kuali.common.util.metainf.RuntimeSqlMetaInfConfig;

public class DeployConfigConstants {

	public static final List<ConfigRequest> INIT_SOURCE_DB_CONFIG = getInitSourceDbConfigRequests();

	protected static List<ConfigRequest> getInitSourceDbConfigRequests() {
		List<ConfigRequest> requests = new ArrayList<ConfigRequest>();
		// Need all the sql and jdbc related properties
		requests.addAll(JdbcConfigConstants.JDBC_CONFIG);
		// Re-use the properties from metainf:sql that created the .resources files
		requests.add(new RuntimeSqlMetaInfConfig());
		// KS specific config for connecting to Amazon RDS
		requests.add(new InitializeSourceDbConfigRequest());
		return requests;
	}

}

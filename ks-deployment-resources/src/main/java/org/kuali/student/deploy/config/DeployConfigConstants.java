package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.config.ConfigRequest;
import org.kuali.common.util.metainf.SqlMetaInfConfig;

public class DeployConfigConstants {

	public static final List<ConfigRequest> INIT_SOURCE_DB_CONFIG = getInitSourceDbConfigRequests();

	protected static List<ConfigRequest> getInitSourceDbConfigRequests() {
		List<ConfigRequest> requests = new ArrayList<ConfigRequest>();
		// Need all the sql and jdbc related properties
		requests.addAll(JdbcConfigConstants.JDBC_CONFIG);
		// This is included so we can re-use the properties that determined the names of the .resources files
		requests.add(new SqlMetaInfConfig());
		// KS specific config for connecting to Amazon RDS
		requests.add(new InitializeSourceDbConfigRequest());
		return requests;
	}

}

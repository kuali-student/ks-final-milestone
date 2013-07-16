package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigRequest;
import org.kuali.common.jdbc.config.SqlConfigRequest;
import org.kuali.common.util.config.ConfigRequest;
import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.kuali.student.deploy.config.InitializeSourceDbConfigRequest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeSourceDbPropertySourceConfig extends BuildPropertySourceConfig {

	protected static final List<ConfigRequest> CONFIG_REQUESTS = getConfigRequests();

	@Override
	protected List<String> getConfigIds() {
		return ConfigUtils.getConfigIds(CONFIG_REQUESTS);
	}

	protected static List<ConfigRequest> getConfigRequests() {
		List<ConfigRequest> requests = new ArrayList<ConfigRequest>();
		requests.add(new SqlConfigRequest());
		requests.add(new JdbcConfigRequest());
		requests.add(new InitializeSourceDbConfigRequest());
		return requests;
	}

}

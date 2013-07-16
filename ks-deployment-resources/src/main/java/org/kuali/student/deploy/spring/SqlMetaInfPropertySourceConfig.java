package org.kuali.student.deploy.spring;

import java.util.Arrays;
import java.util.List;

import org.kuali.common.util.config.ConfigRequest;
import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.kuali.common.util.metainf.SqlMetaInfConfig;
import org.kuali.student.deploy.spring.config.StudentSqlMetaInfConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlMetaInfPropertySourceConfig extends BuildPropertySourceConfig {

	protected static final List<ConfigRequest> CONFIG_REQUESTS = Arrays.asList(new SqlMetaInfConfig(), new StudentSqlMetaInfConfig());

	@Override
	protected List<String> getConfigIds() {
		List<String> configIds = ConfigUtils.getConfigIds(CONFIG_REQUESTS);
		for (String configId : configIds) {
			System.out.println("configId=" + configId);
		}
		return ConfigUtils.getConfigIds(CONFIG_REQUESTS);
	}

}

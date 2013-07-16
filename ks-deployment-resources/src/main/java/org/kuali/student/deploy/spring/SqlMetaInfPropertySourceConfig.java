package org.kuali.student.deploy.spring;

import java.util.Arrays;
import java.util.List;

import org.kuali.common.util.config.ConfigRequest;
import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.kuali.common.util.metainf.SqlMetaInfConfig;
import org.kuali.student.deploy.config.StudentSqlMetaInfConfigRequest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlMetaInfPropertySourceConfig extends BuildPropertySourceConfig {

	protected static final List<ConfigRequest> CONFIG_REQUESTS = Arrays.asList(new SqlMetaInfConfig(), new StudentSqlMetaInfConfigRequest());

	@Override
	protected List<String> getConfigIds() {
		return ConfigUtils.getConfigIds(CONFIG_REQUESTS);
	}

}

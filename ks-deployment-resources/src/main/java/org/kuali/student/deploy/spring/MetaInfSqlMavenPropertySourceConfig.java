package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.metainf.spring.SqlMetaInfPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetaInfSqlMavenPropertySourceConfig extends SqlMetaInfPropertySourceConfig {

	protected static final String KS_METAINF_SQL_CONFIG_ID = "org.kuali.student.db:ks-deployment-resources:metainf:sql";

	@Override
	protected List<String> getConfigIds() {
		List<String> configIds = new ArrayList<String>();
		configIds.add(SqlMetaInfPropertySourceConfig.UTIL_METAINF_SQL_CONFIG_ID);
		configIds.add(KS_METAINF_SQL_CONFIG_ID);
		return configIds;
	}

}

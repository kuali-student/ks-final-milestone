package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.metainf.spring.SqlMetaInfPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentSqlMetaInfPropertySourceConfig extends SqlMetaInfPropertySourceConfig {

	protected static final String KS_METAINF_SQL_CONFIG_ID = getConfigId();

	@Override
	protected List<String> getConfigIds() {
		List<String> configIds = new ArrayList<String>();
		configIds.addAll(super.getConfigIds());
		configIds.add(KS_METAINF_SQL_CONFIG_ID);
		return configIds;
	}

	protected static String getConfigId() {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.GROUP_ID);
		sb.append(":");
		sb.append(Constants.ARTIFACT_ID);
		sb.append(":");
		sb.append(SqlMetaInfPropertySourceConfig.METAINF_SQL_CONTEXT_ID);
		return sb.toString();
	}

}

package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.impex.config.KualiImpexExportConfig;
import org.kuali.common.jdbc.config.KualiJdbcConfig;
import org.kuali.common.jdbc.config.KualiSqlConfig;
import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.kuali.student.deploy.config.KSDeploymentResourcesConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DumpSourceDbPropertySourceConfig extends BuildPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		List<String> ids = new ArrayList<String>();
		ids.add(KualiSqlConfig.DEFAULT.getConfigId());
		ids.add(KualiJdbcConfig.DEFAULT.getConfigId());
		ids.add(KualiImpexExportConfig.DUMP_BUILD.getConfigId());
		ids.add(KSDeploymentResourcesConfig.SOURCE_DB_DUMP.getConfigId());
		return ids;
	}

}

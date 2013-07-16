package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.spring.RuntimePropertySourceConfig;
import org.kuali.student.deploy.config.DeployConfigConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeSourceDbPropertySourceConfig extends RuntimePropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		return ConfigUtils.getConfigIds(DeployConfigConstants.INIT_SOURCE_DB_CONFIG);
	}

	@Override
	protected String getGroupId() {
		return DeployProjectConstants.GROUP_ID;
	}

	@Override
	protected String getArtifactId() {
		return DeployProjectConstants.ARTIFACT_ID;
	}

}

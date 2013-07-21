package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.config.spring.SmartProjectPropertySourceConfig;
import org.kuali.student.deploy.config.SourceDbConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SourceDbScmPropertySourceConfig extends SmartProjectPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		return SourceDbConstants.SCM_SOURCE_DB_CONFIG_IDS;
	}

}

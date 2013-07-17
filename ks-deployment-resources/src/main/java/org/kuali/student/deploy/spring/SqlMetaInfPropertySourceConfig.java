package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.kuali.student.deploy.config.MetaInfConfigConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlMetaInfPropertySourceConfig extends BuildPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		return MetaInfConfigConstants.SQL_METAINF_CONFIG_IDS;
	}

}

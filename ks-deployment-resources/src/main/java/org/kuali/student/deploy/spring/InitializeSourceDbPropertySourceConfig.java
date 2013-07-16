package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.spring.BasicPropertySourceConfig;
import org.kuali.student.deploy.config.ConfigConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeSourceDbPropertySourceConfig extends BasicPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		return ConfigUtils.getConfigIds(ConfigConstants.INIT_SOURCE_DB);
	}

}

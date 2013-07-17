package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.kuali.student.deploy.config.DumpSourceDbConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DumpSourceDbPropertySourceConfig extends BuildPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		return DumpSourceDbConstants.DUMP_SOURCE_DB_CONFIG_IDS;
	}

}

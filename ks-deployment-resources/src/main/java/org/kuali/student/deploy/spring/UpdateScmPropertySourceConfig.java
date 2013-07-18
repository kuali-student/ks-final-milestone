package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.impex.config.KualiImpexExportConfig;
import org.kuali.common.util.config.KualiUtilConfig;
import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateScmPropertySourceConfig extends BuildPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		List<String> list = new ArrayList<String>();
		list.add(KualiUtilConfig.SCM.getConfigId());
		list.add(KualiImpexExportConfig.STAGING_BUILD.getConfigId());
		return list;
	}

}

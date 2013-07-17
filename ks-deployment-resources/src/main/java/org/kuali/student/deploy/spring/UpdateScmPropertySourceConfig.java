package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.impex.StagingConfigConstants;
import org.kuali.common.util.config.spring.BuildPropertySourceConfig;
import org.kuali.common.util.scm.ScmConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateScmPropertySourceConfig extends BuildPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		List<String> list = new ArrayList<String>();
		list.addAll(StagingConfigConstants.CONFIG_IDS);
		list.addAll(ScmConstants.CONFIG_IDS);
		return list;
	}

}

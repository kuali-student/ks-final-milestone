package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.MetaInfSqlProjectContext;
import org.kuali.common.util.ProjectContext;
import org.kuali.common.util.property.ProjectProperties;
import org.kuali.common.util.spring.ConfigUtils;
import org.kuali.common.util.spring.MavenPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetaInfSqlMavenPropertySourceConfig extends MavenPropertySourceConfig {

	@Override
	protected List<ProjectProperties> getOtherProjectProperties() {
		ProjectContext metaInf1 = new MetaInfSqlProjectContext();
		ProjectContext metaInf2 = new StudentMetaInfSqlProjectContext();
		return ConfigUtils.getProjectProperties(metaInf1, metaInf2);
	}

}

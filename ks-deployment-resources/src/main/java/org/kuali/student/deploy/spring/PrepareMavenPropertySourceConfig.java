package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.impex.MavenProjectContext;
import org.kuali.common.impex.StagingProjectContext;
import org.kuali.common.jdbc.JdbcProjectContext;
import org.kuali.common.util.ProjectContext;
import org.kuali.common.util.ScmProjectContext;
import org.kuali.common.util.property.ProjectProperties;
import org.kuali.common.util.spring.ConfigUtils;
import org.kuali.common.util.spring.MavenPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrepareMavenPropertySourceConfig extends MavenPropertySourceConfig {

	@Override
	protected List<ProjectProperties> getOtherProjectProperties() {
		// General SCM related config
		ProjectContext scm = new ScmProjectContext();
		// General JDBC related config
		ProjectContext jdbc = new JdbcProjectContext();
		ProjectContext staging = new StagingProjectContext();
		ProjectContext maven = new MavenProjectContext();
		ProjectContext prepare = new PrepareProjectContext();
		return ConfigUtils.getProjectProperties(scm, jdbc, staging, maven, prepare);
	}

}

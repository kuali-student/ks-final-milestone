package org.kuali.student.deploy.spring;

import java.util.Arrays;
import java.util.List;

import org.kuali.common.impex.DumpAndStageProjectContext;
import org.kuali.common.impex.MavenProjectContext;
import org.kuali.common.jdbc.JdbcProjectContext;
import org.kuali.common.util.MetaInfSqlProjectContext;
import org.kuali.common.util.ProjectContext;
import org.kuali.common.util.property.ProjectProperties;
import org.kuali.common.util.spring.ConfigUtils;
import org.kuali.common.util.spring.MavenPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DumpAndStageSourceDbMavenPropertySourceConfig extends MavenPropertySourceConfig {

	@Override
	protected List<ProjectProperties> getOtherProjectProperties() {
		ProjectContext jdbc = new JdbcProjectContext();
		ProjectContext dump = new DumpAndStageProjectContext();
		ProjectContext mavenImpex = new MavenProjectContext();
		ProjectContext metaInfSql = new MetaInfSqlProjectContext();
		ProjectContext dumpSourceDb = new DumpAndStageSourceDbProjectContext();
		return ConfigUtils.getProjectProperties(Arrays.asList(jdbc, dump, mavenImpex, metaInfSql, dumpSourceDb));
	}

}

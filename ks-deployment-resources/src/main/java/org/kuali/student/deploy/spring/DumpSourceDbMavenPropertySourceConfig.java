package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.impex.DumpProjectContext;
import org.kuali.common.impex.MavenProjectContext;
import org.kuali.common.jdbc.JdbcProjectContext;
import org.kuali.common.util.ProjectContext;
import org.kuali.common.util.property.ProjectProperties;
import org.kuali.common.util.spring.ConfigUtils;
import org.kuali.common.util.spring.MavenPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DumpSourceDbMavenPropertySourceConfig extends MavenPropertySourceConfig {

	@Override
	protected List<ProjectProperties> getOtherProjectProperties() {
		// General JDBC related config
		ProjectContext jdbc = new JdbcProjectContext();
		// General dump related config
		ProjectContext dump = new DumpProjectContext();
		// Config specific to dumping a database using Maven
		ProjectContext mavenDump = new MavenProjectContext();
		// Database specific config for the actual db to dump
		ProjectContext db = new DumpSourceDbProjectContext();
		return ConfigUtils.getProjectProperties(jdbc, dump, mavenDump, db);
	}

}

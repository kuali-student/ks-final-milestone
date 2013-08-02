package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.properties.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ SourceDbLocationsConfig.class, org.kuali.common.util.metainf.spring.SqlLocationsConfig.class })
public class SqlLocationsConfig {

	@Autowired
	SourceDbLocationsConfig sourceDbLocationsConfig;

	@Autowired
	org.kuali.common.util.metainf.spring.SqlLocationsConfig sqlLocationsConfig;

	@Bean
	public List<Location> metaInfSqlLocations() {
		return null;
	}

}

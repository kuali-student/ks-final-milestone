package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.metainf.spring.SqlLocationsConfig;
import org.kuali.common.util.properties.Location;
import org.kuali.common.util.properties.spring.PropertyLocationsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ SqlLocationsConfig.class, SourceDbLocationsConfig.class })
public class SqlBuildLocationsConfig implements PropertyLocationsConfig {

	@Autowired
	SourceDbLocationsConfig sourceDbLocationsConfig;

	@Autowired
	SqlLocationsConfig sqlLocationsConfig;

	@Override
	@Bean
	public List<Location> propertyLocations() {
		List<Location> locations = new ArrayList<Location>();
		locations.add(sourceDbLocationsConfig.ksSourceDbCommon());
		locations.addAll(sqlLocationsConfig.metaInfSqlLocations());
		return Collections.unmodifiableList(locations);
	}
}

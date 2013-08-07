package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.metainf.spring.SqlPropertyLocationsConfig;
import org.kuali.common.util.properties.Location;
import org.kuali.common.util.properties.spring.PropertyLocationsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ SqlPropertyLocationsConfig.class, SourceDbLocationsConfig.class })
public class SqlBuildLocationsConfig implements PropertyLocationsConfig {

	@Autowired
	SourceDbLocationsConfig sourceDbLocationsConfig;

	@Autowired
	SqlPropertyLocationsConfig sqlPropertyLocationsConfig;

	@Override
	@Bean
	public List<Location> propertyLocations() {
		List<Location> locations = new ArrayList<Location>();
		locations.add(sourceDbLocationsConfig.ksSourceDbCommon());
		locations.add(sqlPropertyLocationsConfig.metaInfSqlLocation());
		return Collections.unmodifiableList(locations);
	}
}

package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.properties.Location;
import org.kuali.common.util.properties.spring.PropertyLocationsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ SourceDbLocationsConfig.class, org.kuali.common.util.metainf.spring.SqlLocationsConfig.class })
public class SqlBuildLocationsConfig implements PropertyLocationsConfig {

	@Autowired
	SourceDbLocationsConfig sourceDbLocationsConfig;

	@Autowired
	org.kuali.common.util.metainf.spring.SqlLocationsConfig sqlLocationsConfig;

	@Override
	@Bean
	public List<Location> propertyLocations() {
		List<Location> locations = new ArrayList<Location>();
		locations.addAll(sqlLocationsConfig.metaInfSqlBuildLocations());
		locations.add(sourceDbLocationsConfig.ksSourceDbCommon());
		return Collections.unmodifiableList(locations);
	}

}

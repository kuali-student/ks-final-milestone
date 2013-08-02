package org.kuali.student.deploy.spring;

import java.util.List;
import java.util.Properties;

import org.kuali.common.util.properties.Location;
import org.kuali.common.util.properties.PropertiesService;
import org.kuali.common.util.properties.spring.ProjectPropertiesServiceConfig;
import org.kuali.common.util.spring.service.PropertySourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

@Configuration
@Import({ SqlLocationsConfig.class, ProjectPropertiesServiceConfig.class })
public class SqlPropertySourceConfig implements PropertySourceConfig {

	@Autowired
	SqlLocationsConfig sqlLocationsConfig;

	@Autowired
	ProjectPropertiesServiceConfig projectPropertiesServiceConfig;

	@Override
	@Bean
	public PropertySource<?> propertySource() {
		List<Location> locations = sqlLocationsConfig.metaInfSqlLocations();
		PropertiesService service = projectPropertiesServiceConfig.projectPropertiesService();
		Properties properties = service.getProperties(locations);
		return new PropertiesPropertySource("propertiesPropertySource", properties);
	}
}

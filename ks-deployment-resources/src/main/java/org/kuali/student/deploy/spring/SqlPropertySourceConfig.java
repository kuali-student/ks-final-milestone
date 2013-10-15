package org.kuali.student.deploy.spring;

import org.kuali.common.util.properties.PropertiesService;
import org.kuali.common.util.properties.spring.DefaultPropertiesServiceConfig;
import org.kuali.common.util.spring.PropertySourceUtils;
import org.kuali.common.util.spring.service.PropertySourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.PropertySource;

@Configuration
@Import({ SqlLocationsConfig.class, DefaultPropertiesServiceConfig.class })
public class SqlPropertySourceConfig implements PropertySourceConfig {

	@Autowired
	SqlLocationsConfig locationsConfig;

	@Autowired
	PropertiesService service;

	@Override
	@Bean
	public PropertySource<?> propertySource() {
		return PropertySourceUtils.getPropertySource(service, locationsConfig.propertyLocations());
	}
}

package org.kuali.student.deploy.spring;

import java.util.List;
import java.util.Properties;

import org.kuali.common.util.metainf.spring.MpxLocationsConfig;
import org.kuali.common.util.project.Project;
import org.kuali.common.util.properties.Location;
import org.kuali.common.util.properties.PropertiesService;
import org.kuali.common.util.properties.spring.PropertiesServiceConfig;
import org.kuali.common.util.spring.service.PropertySourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

@Configuration
@Import({ MpxLocationsConfig.class, PropertiesServiceConfig.class })
public class MpxPropertySourceConfig implements PropertySourceConfig {

	@Autowired
	MpxLocationsConfig mpxLocationsConfig;

	@Autowired
	PropertiesServiceConfig propertiesServiceConfig;

	@Autowired
	Project project;

	@Override
	@Bean
	public PropertySource<?> propertySource() {
		List<Location> locations = mpxLocationsConfig.metaInfMpxBuildLocations();
		PropertiesService service = propertiesServiceConfig.propertiesService();
		Properties properties = service.getProperties(locations, project.getProperties());
		return new PropertiesPropertySource("propertiesPropertySource", properties);
	}
}

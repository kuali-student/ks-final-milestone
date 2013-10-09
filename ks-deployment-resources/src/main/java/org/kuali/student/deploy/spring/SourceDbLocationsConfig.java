package org.kuali.student.deploy.spring;

import org.kuali.common.util.properties.Location;
import org.kuali.common.util.properties.PropertiesLocationService;
import org.kuali.common.util.properties.spring.PropertiesLocationServiceConfig;
import org.kuali.student.deploy.config.DeployProjectConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ PropertiesLocationServiceConfig.class })
public class SourceDbLocationsConfig {

	@Autowired
	PropertiesLocationService service;

	@Bean
	public Location ksSourceDbCommon() {
		return service.getLocation(DeployProjectConstants.PROJECT_ID, "db/source/common.properties");
	}
}

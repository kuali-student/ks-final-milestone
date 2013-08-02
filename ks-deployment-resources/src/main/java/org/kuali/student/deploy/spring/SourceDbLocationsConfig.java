package org.kuali.student.deploy.spring;

import org.kuali.common.util.properties.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ KSDeploymentResourcesConfig.class })
public class SourceDbLocationsConfig {

	@Autowired
	KSDeploymentResourcesConfig ksDeploymentResourcesConfig;

	@Bean
	public Location ksSourceDbCommon() {
		return getLocation("/common.properties");
	}

	protected Location getLocation(String suffix) {
		String sourceDbPrefix = "/db/source";
		return ksDeploymentResourcesConfig.getLocation(sourceDbPrefix + suffix);
	}

}

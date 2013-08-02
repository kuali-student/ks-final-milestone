package org.kuali.student.deploy.spring;

import org.kuali.common.util.properties.spring.LocationPropertySourceConfig;
import org.kuali.common.util.properties.spring.ProjectPropertiesServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ SqlBuildLocationsConfig.class, ProjectPropertiesServiceConfig.class })
public class SqlBuildPropertySourceConfig extends LocationPropertySourceConfig {

}

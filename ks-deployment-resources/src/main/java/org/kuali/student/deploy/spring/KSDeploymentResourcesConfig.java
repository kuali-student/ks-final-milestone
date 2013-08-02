package org.kuali.student.deploy.spring;

import org.kuali.common.util.project.Project;
import org.kuali.common.util.project.ProjectService;
import org.kuali.common.util.project.ProjectUtils;
import org.kuali.common.util.project.spring.ProjectServiceConfig;
import org.kuali.common.util.properties.ImmutableLocation;
import org.kuali.common.util.properties.Location;
import org.kuali.student.deploy.config.DeployProjectConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ProjectServiceConfig.class })
public class KSDeploymentResourcesConfig {

	@Autowired
	ProjectServiceConfig projectServiceConfig;

	@Bean
	public Project ksDeploymentResourcesProject() {
		ProjectService service = projectServiceConfig.projectService();
		return service.getProject(DeployProjectConstants.PROJECT_IDENTIFIER);
	}

	public Location getLocation(String suffix) {
		Project project = ksDeploymentResourcesProject();
		String encoding = ProjectUtils.getEncoding(project);
		String prefix = ProjectUtils.getClasspathPrefix(project.getGroupId(), project.getArtifactId());
		return new ImmutableLocation(prefix + suffix, encoding);
	}
}

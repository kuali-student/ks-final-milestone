package org.kuali.student.deploy.spring.config;

import org.kuali.common.util.config.ConfigRequest;
import org.kuali.student.deploy.spring.ProjectConstants;

public class DeploymentConfig extends ConfigRequest {

	public DeploymentConfig() {
		this(null);
	}

	public DeploymentConfig(String contextId) {
		super(ProjectConstants.GROUP_ID, ProjectConstants.ARTIFACT_ID, contextId);
	}

}

package org.kuali.student.deploy.spring.config;

import org.kuali.common.util.config.ConfigRequest;
import org.kuali.student.deploy.spring.ProjectConstants;

public class DeploymentConfigRequest extends ConfigRequest {

	public DeploymentConfigRequest() {
		this(null);
	}

	public DeploymentConfigRequest(String contextId) {
		super(ProjectConstants.GROUP_ID, ProjectConstants.ARTIFACT_ID, contextId);
	}

}

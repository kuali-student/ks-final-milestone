package org.kuali.student.deploy.config;

import org.kuali.student.deploy.spring.ProjectConstants;

public class DeploymentConfigRequest extends StudentConfigRequest {

	public DeploymentConfigRequest() {
		this(null);
	}

	public DeploymentConfigRequest(String contextId) {
		super(ProjectConstants.ARTIFACT_ID, contextId);
	}

}

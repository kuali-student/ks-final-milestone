package org.kuali.student.deploy.config;

import org.kuali.student.deploy.spring.DeployProjectConstants;

public class DeploymentConfigRequest extends StudentConfigRequest {

	public DeploymentConfigRequest() {
		this(null);
	}

	public DeploymentConfigRequest(String contextId) {
		super(DeployProjectConstants.ARTIFACT_ID, contextId);
	}

}

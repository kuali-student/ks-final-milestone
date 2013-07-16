package org.kuali.student.deploy.config;

import org.kuali.common.util.config.ConfigRequest;
import org.kuali.student.deploy.spring.DeployProjectConstants;

public class StudentConfigRequest extends ConfigRequest {

	public StudentConfigRequest() {
		this(null);
	}

	public StudentConfigRequest(String artifactId) {
		this(artifactId, null);
	}

	public StudentConfigRequest(String artifactId, String contextId) {
		super(DeployProjectConstants.GROUP_ID, artifactId, contextId);
	}

}

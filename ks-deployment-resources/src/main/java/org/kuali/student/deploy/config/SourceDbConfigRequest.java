package org.kuali.student.deploy.config;

import org.kuali.student.deploy.spring.ProjectConstants;

public class SourceDbConfigRequest extends StudentConfigRequest {

	public SourceDbConfigRequest() {
		this(null);
	}

	public SourceDbConfigRequest(String contextId) {
		super(ProjectConstants.SOURCE_DB_ARTIFACT_ID, contextId);
	}

}

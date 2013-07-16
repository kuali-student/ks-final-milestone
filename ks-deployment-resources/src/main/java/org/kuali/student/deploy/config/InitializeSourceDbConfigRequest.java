package org.kuali.student.deploy.config;

import org.kuali.student.deploy.spring.ProjectConstants;

public class InitializeSourceDbConfigRequest extends StudentConfigRequest {

	public static final String CONTEXT_ID = "source-db:initialize";

	public InitializeSourceDbConfigRequest() {
		this(null);
	}

	public InitializeSourceDbConfigRequest(String contextId) {
		super(ProjectConstants.SOURCE_DB_ARTIFACT_ID, contextId);
	}

}

package org.kuali.student.deploy.config;

import org.kuali.student.deploy.spring.DeployProjectConstants;

public class InitializeSourceDbConfigRequest extends StudentConfigRequest {

	public static final String ARTIFACT_ID = DeployProjectConstants.ARTIFACT_ID;
	public static final String CONTEXT_ID = "source-db:initialize";

	public InitializeSourceDbConfigRequest() {
		super(ARTIFACT_ID, CONTEXT_ID);
	}

}

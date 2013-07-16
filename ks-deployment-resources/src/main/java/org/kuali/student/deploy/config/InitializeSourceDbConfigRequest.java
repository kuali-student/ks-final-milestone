package org.kuali.student.deploy.config;


public class InitializeSourceDbConfigRequest extends StudentConfigRequest {

	public static final String CONTEXT_ID = "source-db:initialize";

	public InitializeSourceDbConfigRequest() {
		super(CONTEXT_ID);
	}

}

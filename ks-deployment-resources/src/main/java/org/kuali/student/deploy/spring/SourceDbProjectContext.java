package org.kuali.student.deploy.spring;

import org.kuali.common.util.DefaultProjectContext;

public class SourceDbProjectContext extends DefaultProjectContext {

	private static final String PROPERTIES = "classpath:org/kuali/student/ks-source-db/initialize.properties";

	public SourceDbProjectContext() {
		super(Constants.GROUP_ID, Constants.ARTIFACT_ID, PROPERTIES);
	}

}

package org.kuali.student.deploy.spring;

import java.util.Arrays;

import org.kuali.common.util.DefaultProjectContext;

public class SourceDbCommonProjectContext extends DefaultProjectContext {

	public SourceDbCommonProjectContext() {
		super(ProjectConstants.GROUP_ID, ProjectConstants.ARTIFACT_ID, Arrays.asList(ProjectConstants.KS_SOURCE_DB_COMMON));
	}

}

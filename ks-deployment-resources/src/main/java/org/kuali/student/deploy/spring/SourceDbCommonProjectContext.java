package org.kuali.student.deploy.spring;

import java.util.Arrays;

import org.kuali.common.util.DefaultProjectContext;

public class SourceDbCommonProjectContext extends DefaultProjectContext {

	public SourceDbCommonProjectContext() {
		super(DeployProjectConstants.GROUP_ID, DeployProjectConstants.ARTIFACT_ID, Arrays.asList(DeployProjectConstants.KS_SOURCE_DB_COMMON));
	}

}

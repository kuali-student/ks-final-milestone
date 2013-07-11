package org.kuali.student.deploy.spring;

import java.util.Arrays;

import org.kuali.common.util.DefaultProjectContext;

public class SourceDbCommonProjectContext extends DefaultProjectContext {

	public SourceDbCommonProjectContext() {
		super(Constants.GROUP_ID, Constants.ARTIFACT_ID, Arrays.asList(Constants.KS_SOURCE_DB_COMMON));
	}

}

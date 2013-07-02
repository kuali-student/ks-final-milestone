package org.kuali.student;

import org.kuali.common.util.DefaultProjectContext;

public class ImpexRiceDbProjectContext extends DefaultProjectContext {

	private static final String ARTIFACT_ID = "ks-impex-rice-db";
	private static final String GROUP_ID = "org.kuali.student.db";

	public ImpexRiceDbProjectContext() {
		super(GROUP_ID, ARTIFACT_ID);
	}
}

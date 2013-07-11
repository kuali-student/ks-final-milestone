package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.DefaultProjectContext;
import org.kuali.common.util.ProjectUtils;

public class DumpAndStageSourceDbProjectContext extends DefaultProjectContext {

	public DumpAndStageSourceDbProjectContext() {
		super(Constants.GROUP_ID, Constants.ARTIFACT_ID, getLocations());
	}

	protected static List<String> getLocations() {
		String prefix = ProjectUtils.getClassPathPrefix(Constants.GROUP_ID_BASE, Constants.KS_SOURCE_DB);
		List<String> list = new ArrayList<String>();
		list.add(prefix + "/common.properties");
		list.add(prefix + "/dump.properties");
		list.add(prefix + "/staging.properties");
		list.add(prefix + "/scm.properties");
		return list;
	}

}

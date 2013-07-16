package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.DefaultProjectContext;
import org.kuali.common.util.ProjectUtils;

public class UpdateScmProjectContext extends DefaultProjectContext {

	public UpdateScmProjectContext() {
		super(ProjectConstants.GROUP_ID, ProjectConstants.ARTIFACT_ID, getLocations());
	}

	protected static List<String> getLocations() {
		String prefix = ProjectUtils.getClassPathPrefix(ProjectConstants.GROUP_ID_BASE, ProjectConstants.KS_SOURCE_DB);
		List<String> list = new ArrayList<String>();
		list.add(prefix + "/common.properties");
		list.add(prefix + "/dump.properties");
		list.add(prefix + "/staging.properties");
		list.add(prefix + "/scm.properties");
		return list;
	}

}

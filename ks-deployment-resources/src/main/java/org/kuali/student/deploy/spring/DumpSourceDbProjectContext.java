package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.DefaultProjectContext;
import org.kuali.common.util.ProjectUtils;

public class DumpSourceDbProjectContext extends DefaultProjectContext {

	public DumpSourceDbProjectContext() {
		super(DeployProjectConstants.GROUP_ID, DeployProjectConstants.ARTIFACT_ID, getLocations());
	}

	protected static List<String> getLocations() {
		String prefix = ProjectUtils.getClassPathPrefix(DeployProjectConstants.GROUP_ID_BASE, DeployProjectConstants.KS_SOURCE_DB_ARTIFACT_ID);
		List<String> list = new ArrayList<String>();
		list.add(prefix + "/common.properties");
		list.add(prefix + "/dump.properties");
		return list;
	}

}

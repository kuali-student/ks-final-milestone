package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.DefaultProjectContext;
import org.kuali.common.util.ProjectUtils;

public class InitializeSourceDbProjectContext extends DefaultProjectContext {

	public InitializeSourceDbProjectContext() {
		super(Constants.GROUP_ID, Constants.ARTIFACT_ID);
	}

	@Override
	public List<String> getPropertyLocations() {
		String prefix = ProjectUtils.getClassPathPrefix(Constants.GROUP_ID_BASE, Constants.KS_SOURCE_DB);
		List<String> list = new ArrayList<String>();
		list.add(prefix + "/common.properties");
		list.add(prefix + "/initialize.properties");
		return list;
	}

}

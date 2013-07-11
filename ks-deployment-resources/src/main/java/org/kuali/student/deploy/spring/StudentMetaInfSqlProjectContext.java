package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.DefaultProjectContext;

public class StudentMetaInfSqlProjectContext extends DefaultProjectContext {

	public StudentMetaInfSqlProjectContext() {
		super(Constants.GROUP_ID, Constants.ARTIFACT_ID, getLocations());
	}

	protected static List<String> getLocations() {
		String prefix = Constants.KS_SOURCE_DB_RESOURCE_PREFIX;
		List<String> list = new ArrayList<String>();
		list.add(prefix + "/common.properties");
		return list;
	}

}

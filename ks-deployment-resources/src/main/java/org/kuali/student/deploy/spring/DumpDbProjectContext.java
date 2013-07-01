package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.DefaultProjectContext;

public class DumpDbProjectContext extends DefaultProjectContext {

	public DumpDbProjectContext() {
		super(Constants.GROUP_ID, Constants.ARTIFACT_ID);
	}

	@Override
	public List<String> getPropertyLocations() {
		List<String> list = new ArrayList<String>();
		list.add("classpath:org/kuali/student/ks-source-db/common.properties");
		list.add("classpath:org/kuali/student/ks-source-db/dump.properties");
		return list;
	}

}

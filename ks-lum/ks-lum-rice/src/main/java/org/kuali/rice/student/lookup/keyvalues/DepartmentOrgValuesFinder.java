package org.kuali.rice.student.lookup.keyvalues;

import java.util.List;

import org.kuali.rice.core.api.util.KeyValue;

public class DepartmentOrgValuesFinder extends OrgsOfTypeValuesFinder{

	public List<KeyValue> getKeyValues() {
		return findOrgs("kuali.org.Department");
	}

}

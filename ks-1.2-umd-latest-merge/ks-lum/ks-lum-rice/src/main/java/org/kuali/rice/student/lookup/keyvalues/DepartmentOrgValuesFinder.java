package org.kuali.rice.student.lookup.keyvalues;

import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;

public class DepartmentOrgValuesFinder extends OrgsOfTypeValuesFinder{

	public List<KeyLabelPair> getKeyValues() {
		return findOrgs("kuali.org.Department");
	}

}

package org.kuali.rice.student.lookup.keyvalues;

import java.util.List;

import org.kuali.rice.core.api.util.KeyValue;

public class CollegeValuesFinder extends OrgsOfTypeValuesFinder{

	@Override
	public List<KeyValue> getKeyValues() {
		return findOrgs("kuali.org.College");
	}

}

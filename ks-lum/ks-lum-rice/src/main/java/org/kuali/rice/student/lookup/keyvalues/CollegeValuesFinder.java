package org.kuali.rice.student.lookup.keyvalues;

import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;

public class CollegeValuesFinder extends OrgsOfTypeValuesFinder{

	@Override
	public List<KeyLabelPair> getKeyValues() {
		return findOrgs("kuali.org.College");
	}

}

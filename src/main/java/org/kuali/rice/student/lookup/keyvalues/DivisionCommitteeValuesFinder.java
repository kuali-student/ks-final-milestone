package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.ui.KeyLabelPair;

public class DivisionCommitteeValuesFinder extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		// TODO replace with real code
		List<KeyLabelPair> collegeCommittees = new ArrayList<KeyLabelPair>();
		String[] collegeCommitteeNames = new String[] { "Arts COC",
				"BSOS COC", "Life & Chem Sciences COC", "Engineering COC" };
		for (String committeeName : collegeCommitteeNames) {
			collegeCommittees
					.add(new KeyLabelPair(committeeName, committeeName));
		}
		return collegeCommittees;
	}

}

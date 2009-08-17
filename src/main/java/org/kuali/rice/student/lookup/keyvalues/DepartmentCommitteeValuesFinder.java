package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.ui.KeyLabelPair;

public class DepartmentCommitteeValuesFinder  extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		//TODO replace with real code
		List<KeyLabelPair> collegeCommittees = new ArrayList<KeyLabelPair>();
		String[] collegeCommitteeNames = new String[] { "English COC", "FineArts COC", "French COC",
				"Geography COC", "Linguistics COC", "Music COC", "History COC",
				"PolySci COC", "Psychology COC", "Sociology COC",
				"InterdiscBSOS COC", "Chemical COC", "Civil COC", "Mechanical COC",
				"CompSci COC", "Biology COC", "Biochemistry COC", "Botany COC",
				"Chemistry COC", "Geology COC", "GenderStudies COC" };
		for(String committeeName:collegeCommitteeNames){
			collegeCommittees.add(new KeyLabelPair(committeeName, committeeName));
		}
		return collegeCommittees;
	}
}

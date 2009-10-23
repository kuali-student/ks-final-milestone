package org.kuali.rice.student.lookup.keyvalues;

import java.util.List;

import org.kuali.rice.kns.web.ui.KeyLabelPair;

public class DivisionCocValuesFinder extends CocValuesFiinder {

	public List<KeyLabelPair> getKeyValues() {
		return findCocOrgs("kuali.org.Division");
	}

}

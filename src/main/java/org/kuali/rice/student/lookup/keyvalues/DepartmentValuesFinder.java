/**
 * 
 */
package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.ui.KeyLabelPair;

/**
 * @author delyea
 *
 */
public class DepartmentValuesFinder extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		List<KeyLabelPair> departments = new ArrayList<KeyLabelPair>();
		// TODO Auto-generated method stub
		departments.add(new KeyLabelPair("",""));
		departments.add(new KeyLabelPair("AlumniAssociation","AlumniAssociation"));
		departments.add(new KeyLabelPair("PresidentsOffice","PresidentsOffice"));
		return departments;
	}

}

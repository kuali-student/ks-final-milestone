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
public class CollegeValuesFinder extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		List<KeyLabelPair> colleges = new ArrayList<KeyLabelPair>();
		// TODO Auto-generated method stub
		colleges.add(new KeyLabelPair("",""));
		return colleges;
	}

}

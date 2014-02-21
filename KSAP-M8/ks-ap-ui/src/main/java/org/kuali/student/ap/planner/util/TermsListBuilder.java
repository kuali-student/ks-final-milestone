package org.kuali.student.ap.planner.util;

import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.core.acal.infc.Term;

/**
 * Assembles a list of published terms.
 */
public class TermsListBuilder extends KeyValuesBase {

	private static final long serialVersionUID = 4456030609113645225L;

	/**
	 * Build and returns the list of available terms.
	 * 
	 * @return A List of available terms as KeyValue items.
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new java.util.ArrayList<KeyValue>();
		for (Term term : KsapFrameworkServiceLocator.getTermHelper()
				.getPlanningTerms())
			keyValues.add(new ConcreteKeyValue(term.getId(), term.getName()));
		return keyValues;
	}

}

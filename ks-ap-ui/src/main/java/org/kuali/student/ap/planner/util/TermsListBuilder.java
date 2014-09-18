package org.kuali.student.ap.planner.util;

import org.kuali.rice.core.api.config.property.ConfigContext;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultYearTerm;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.form.AddCourseToPlanForm;
import org.kuali.student.r2.core.acal.infc.Term;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Assembles a list of published terms.
 */
public class TermsListBuilder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 4456030609113645225L;

	/**
	 * Build and returns the list of available terms.
	 * 
	 * @return A List of available terms as KeyValue items.
	 */
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyValues = new java.util.ArrayList<KeyValue>();
        List<Term> terms = KsapFrameworkServiceLocator.getTermHelper().getPlanningTerms();

        for (Term term : terms) {
            AddCourseToPlanForm form = (AddCourseToPlanForm) model;
            String plannedSuffix = "";
            if (form.getPlannedTermIds().contains(term.getId()))
                plannedSuffix = " (planned)";
			keyValues.add(new ConcreteKeyValue(term.getId(), term.getName() + plannedSuffix));
        }
		return keyValues;
	}
}

package org.kuali.student.ap.planner.util;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultYearTerm;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.ap.planner.PlannerForm;
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
        Term currentTerm = null;
        try {
            currentTerm = KsapFrameworkServiceLocator.getTermHelper().getCurrentTerm();
        } catch (IllegalArgumentException iae) {
            // No current term.  Null is fine.
        }

        List<Term> terms = getCalendarTerms(currentTerm);

        for (Term term : terms) {
            PlannerForm form = (PlannerForm) model;
            String plannedSuffix = "";
            if (form.getPlannedTermIds().contains(term.getId()))
                plannedSuffix = " (planned)";
			keyValues.add(new ConcreteKeyValue(term.getId(), term.getName() + plannedSuffix));
        }
		return keyValues;
	}

    /**
     * Gets the list of Terms to use in the Add To Plan screen using a Start Term.
     *
     * @param startTerm - Term that the calendar starts around
     * @return A full List of terms to display in the calendar.
     */
    private List<Term> getCalendarTerms(Term startTerm) {
        Date startDate = KsapHelperUtil.getCurrentDate();
        Calendar c = Calendar.getInstance();
        if (startTerm != null) {
            startDate = startTerm.getStartDate();
        }
        int futureYears = Integer.parseInt(ConfigContext.getCurrentContextConfig().getProperty( "ks.ap.planner.future.years"));
        c.add(Calendar.YEAR, futureYears);
        List<Term> calendarTerms = KsapFrameworkServiceLocator.getTermHelper().getTermsByDateRange(startDate,c.getTime());
        calendarTerms = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(calendarTerms,true);

        Term end = calendarTerms.get(calendarTerms.size()-1);

        // Gets all terms in the academic year to round off the list
        List<Term> endYear= KsapFrameworkServiceLocator.getTermHelper().getTermsInAcademicYear(new DefaultYearTerm(end.getId(),end.getTypeKey(),end.getStartDate().getYear()));

        endYear = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(endYear,true);

        for(Term t : endYear){
            if(t.getStartDate().compareTo(end.getStartDate())>0){
                calendarTerms.add(t);
            }
        }
        return calendarTerms;
    }

}

package org.kuali.student.ap.framework.context.support;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.AtpHelper;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;
import static org.kuali.rice.core.api.criteria.PredicateFactory.notEqualIgnoreCase;

/**
 * Helper methods for dealing with ATPs.
 */
public class DefaultAtpHelper implements AtpHelper {


    public final Pattern TERM_REGEX = Pattern.compile("(winter|spring|summer i|summer ii|autumn|fall)\\s+([0-9]{4})");
    public final Pattern TERM_REGEX_2 = Pattern.compile("(winter|spring|summer i|summer ii|autumn|fall) ([0-9]{4})");
    public static final String LAST_DROP_DAY = "last_drop_day";

    private static String term1 = "winter";
    private static String term2 = "spring";
    private static String term3 = "summer";
    private static String term4 = "autumn";

    private static final Logger LOG = Logger.getLogger(DefaultAtpHelper.class);

	private static ContextInfo getContext() {
		return KsapFrameworkServiceLocator.getContext().getContextInfo();
	}

	/**
	 * Constructs a term key for the current ATP using the Calendar year and month.
	 * 
	 * @return The ID of the current ATP.
	 */
	@Override
	public String getCurrentAtpId() {
        String termKey = "kuali.atp.";
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        termKey=termKey+year;

        if(month<1) termKey=termKey+"Winter";
        else if(month<5) termKey=termKey+"Spring";
        else if(month<7) termKey=termKey+"Summer1";
        else if(month<9) termKey=termKey+"Summer2";
        else if(month<12) termKey=termKey+"Fall";

        return termKey;
	}

	/**
	 * Query the Academic Calendar Service for terms that have offering's
	 * published, determine the last ATP, and return its ID.
	 * 
	 * @return The ID of the last scheduled ATP.
	 * @throws RuntimeException
	 *             if the query fails or if the return data set doesn't make
	 *             sense.
	 */
	@Override
	public String getLastScheduledAtpId() {
		List<TermInfo> scheduledTerms;
		try {
			scheduledTerms = KsapFrameworkServiceLocator
					.getAcademicCalendarService()
					.searchForTerms(
							QueryByCriteria.Builder.fromPredicates(equalIgnoreCase(
									"atpState", PlanConstants.PUBLISHED)),
							getContext());
		} catch (Throwable t) {
			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			if (t instanceof Error)
				throw (Error) t;
			throw new IllegalStateException(
					"Unexpected error in current ATP ID lookup", t);
		}
		assert scheduledTerms != null && scheduledTerms.size() > 1 : "No scheduled terms from acal service"
				+ scheduledTerms;
        if (scheduledTerms != null && scheduledTerms.size() > 1) {
            return scheduledTerms.get(scheduledTerms.size() - 1).getId();
        }
        // Need to handle not finding scheduled terms
        return "";
	}

    @Override
    public YearTerm getYearTerm(String atpId){
        return new DefaultYearTerm(atpId);
    }

    @Override
    public String getAtpId(YearTerm yearTerm){
        return yearTerm.toATP();
    }

    @Override
    public String getAtpId(String year, String term){
        return (new DefaultYearTerm(year,term)).toATP();
    }

    @Override
    public String getAtpId(int year, int term){
        return (new DefaultYearTerm(year,term)).toATP();
    }

    @Override
    public String getAtpId(int year, String term){
        return "kuali.atp."+year+term;
    }

    @Override
    public boolean validateAtp(String atpId){
        try {
            TermInfo term = KsapFrameworkServiceLocator
                    .getAcademicCalendarService().getTerm(atpId, getContext());
            if(term!=null) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean validateAtpId(String atpId){
        try{
            YearTerm term = new DefaultYearTerm(atpId);
            return validateAtp(atpId);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean isCompletedTerm(String atpId){
        YearTerm currentTerm = new DefaultYearTerm(getCurrentAtpId());
        YearTerm tempTerm = new DefaultYearTerm(atpId);

        int compare = currentTerm.compareTo(tempTerm);

        if(compare>=0)return false;
        return true;

    }



	/**
	 * Gets the ATP ID of the first ATP in the current academic year.
	 */
	@Override
	public String getFirstAtpIdOfAcademicYear(String atpId) {
        YearTerm yearTerm = getYearTerm(atpId);
        int year = yearTerm.getYear();
        int term = yearTerm.getMAX_TERM_INDEX();


        if((yearTerm.getTerm()-1)==yearTerm.getMAX_TERM_INDEX()){
            year=yearTerm.getYear()-1;
        }

        return (new DefaultYearTerm(year,term)).toATP();
	}

	/**
	 * Returns true if an ATP is considered present or greater in the context of
	 * WHAT? Otherwise, false.
     *
     * Is set to the future or current term
	 * 
	 * @param atpId
	 * @return
	 */
	@Override
	public boolean isAtpSetToPlanning(String atpId) {
        String currentTerm = this.getCurrentAtpId();
        YearTerm currentYearTerm = new DefaultYearTerm(currentTerm);

        try{
            YearTerm newYearTerm = new DefaultYearTerm(atpId);
            int compareTermsResults = currentYearTerm.compareTo(newYearTerm);
            if(compareTermsResults<0) return false;
        }catch(Exception e){
            return false;
        }

        return true;

	}

	/**
	 * Returns true if an ATP is considered present or greater in the context of
	 * WHAT? Otherwise, false.
	 * 
	 * @param atpId
	 * @return
	 */
	@Override
	public boolean isAtpCompletedTerm(String atpId) {
        String currentTerm = this.getCurrentAtpId();
        YearTerm currentYearTerm = new DefaultYearTerm(currentTerm);

        try{
            YearTerm newYearTerm = new DefaultYearTerm(atpId);
            int compareTermsResults = currentYearTerm.compareTo(newYearTerm);
            if(compareTermsResults<0) return true;
        }catch(Exception e){
            return false;
        }

        return false;
	}
    @Override
    public boolean isCourseOfferedInTerm(String atp, String course) {
        boolean isCourseOfferedInTerm = false;

        String[] splitStr = course.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        List<String> offerings = null;
        try {
            offerings = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(atp, splitStr[0].trim(), CourseSearchConstants.CONTEXT_INFO);
        } catch (Exception e) {
            LOG.error("Exception loading course offering for:" + course, e);
        }

        if (offerings != null && offerings.contains(course)) {
            isCourseOfferedInTerm = true;
        }

        return isCourseOfferedInTerm;
    }
    @Override
    public List<String> getPublishedTerms() {
        List<TermInfo> termInfos = null;
        List<String> publishedTerms = new ArrayList<String>();
        try {
            termInfos = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("atpState", "kuali.atp.state.Official"),notEqualIgnoreCase("atpType", "kuali.atp.type.HolidayCalendar"),notEqualIgnoreCase("atpType", "kuali.atp.type.AcademicCalendar")), KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (Exception e) {
            LOG.error("Web service call failed.", e);
            //  Create an empty list to Avoid NPE below allowing the data object to be fully initialized.
            termInfos = new ArrayList<TermInfo>();
        }
        for (TermInfo term : termInfos) {
            publishedTerms.add(term.getId());
        }
        return publishedTerms;
    }

	@Override
	public void addServiceError(String propertyName) {
		String[] params = {};
		GlobalVariables.getMessageMap().putWarning(propertyName,
				PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
	}

}

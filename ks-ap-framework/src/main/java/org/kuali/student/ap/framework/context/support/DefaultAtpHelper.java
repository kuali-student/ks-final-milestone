package org.kuali.student.ap.framework.context.support;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

/**
 * Helper methods for dealing with ATPs.
 */
public class DefaultAtpHelper implements AtpHelper {


    public final Pattern TERM_REGEX = Pattern.compile("(winter|spring|summer|autumn)\\s+([0-9]{4})");
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
	 * Query the Academic Calendar Service, determine the current ATP, and
	 * return the ID.
	 * 
	 * @return The ID of the current ATP.
	 */
	@Override
	public String getCurrentAtpId() {
		List<TermInfo> inProgressTerms;
		try {
			inProgressTerms = KsapFrameworkServiceLocator
					.getAcademicCalendarService()
					.searchForTerms(
							QueryByCriteria.Builder.fromPredicates(equalIgnoreCase(
									"atpState", PlanConstants.INPROGRESS)),
							getContext());
		} catch (Throwable t) {
			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			if (t instanceof Error)
				throw (Error) t;
			throw new IllegalStateException(
					"Unexpected error in current ATP ID lookup", t);
		}
		assert inProgressTerms != null && inProgressTerms.size() > 0 : "No in-progress terms from acal service"
				+ inProgressTerms;
        if (inProgressTerms != null && inProgressTerms.size() > 0)
            return inProgressTerms.iterator().next().getId();
        return "";
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

	/**
	 * Gets the ATP ID of the first ATP in the current academic year.
	 */
	@Override
	public String getFirstAtpIdOfAcademicYear(String atpId) {
		String[] termYear = atpIdToTermAndYear(atpId);

		String year = termYear[1];
		String term = termYear[0];

		// If the term is not Autumn/4 then the beginning of the academic year
		// is (year - 1) . 4
		if (term.equals("4")) {
			return atpId;
		} else {
			String y = String.valueOf(Integer.valueOf(year) - 1);
			return getAtpFromNumTermAndYear("4", y);
		}
	}

	/**
	 * Returns an String[] {term, year} given an ATP ID.
	 */
	@Override
	public String[] atpIdToTermAndYear(String atpId) {
		AtpInfo atp;
		try {
			atp = KsapFrameworkServiceLocator.getAtpService().getAtp(atpId,
					getContext());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("ATP lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("ATP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("ATP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("ATP lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("ATP lookup failure", e);
		}
		Calendar c = Calendar.getInstance();
		c.setTime(atp.getStartDate());
		String year = Integer.toString(c.get(Calendar.YEAR));
		String atptype = atp.getTypeKey();
		String term;
		if (atptype.equals("kuali.atp.type.Winter"))
			term = "1";
		else if (atptype.equals("kuali.atp.type.Spring"))
			term = "2";
		else if (atptype.equals("kuali.atp.type.Summer1"))
			term = "3";
        else if (atptype.equals("kuali.atp.type.Summer2"))
            term = "4";
		else if (atptype.equals("kuali.atp.type.Fall"))
			term = "5";
		else
			throw new IllegalArgumentException(
					"Unable to determine term from ATP " + atp);
		return new String[] { term, year };
	}

	/**
	 * Converts an ATP ID to a Term and Year ... "kuali.atp.1991.1" ->
	 * {"Autumn", "1991"}
	 * 
	 * @return A String array containing a term and year.
	 */
	@Override
	public String[] atpIdToTermNameAndYear(String atpId) {
        return atpIdToTermAndYear(atpId);
		/*AtpInfo atp;
		try {
			atp = KsapFrameworkServiceLocator.getAtpService().getAtp(atpId,
					getContext());
		} catch (Throwable t) {
			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			if (t instanceof Error)
				throw (Error) t;
			throw new IllegalStateException(
					"Unexpected error in ATP ID lookup", t);
		}
		assert atp != null : "Missing ATP for " + atpId;

        String [] arrayReFormatAtpName = null;
        String holdAtpName = atp.getName();
        String tempAtpName = holdAtpName.toLowerCase();
        if (tempAtpName.contains("summer i")) {
            arrayReFormatAtpName = tempAtpName.split(" ");
            if ((arrayReFormatAtpName != null) && (arrayReFormatAtpName.length > 2)){
                  tempAtpName = "";
                  for (int i =0; i <= arrayReFormatAtpName.length - 2; i++) {
                      tempAtpName +=    arrayReFormatAtpName[i]  ;
                  }
                atp.setName(tempAtpName + " " + arrayReFormatAtpName[arrayReFormatAtpName.length - 1]);
            }
        }

		Matcher tm = Pattern.compile("([A-Za-z]+) ([0-9]+)").matcher(
				atp.getName());
		if (!tm.matches())
			throw new IllegalArgumentException("Term name " + atp.getName()
					+ " doesn't match expected format");
		String term = tm.group(1);
		String year = tm.group(2);
        if (term.contains("summeri")) {
            arrayReFormatAtpName = null;
            if ((year != null) && (year.length() > 0)) {
                atp.setName(holdAtpName);
                arrayReFormatAtpName = atp.getName().split(year);
            }
            if (arrayReFormatAtpName != null) {
                term =  arrayReFormatAtpName[0];
            }
        }
		return new String[] { term, year };   */
	}

	@Override
	public String getAtpIdFromTermAndYear(String term, String year) {
		String ts = term == null ? "" : term.toUpperCase();
		final String termNum;
		if (ts.contains("WI"))
			termNum = "1";
		else if (ts.contains("SP"))
			termNum = "2";
		else if (ts.contains("SU"))
			termNum = "3";
		else if (ts.contains("FA") || ts.contains("AU"))
			termNum = "4";
		else
			throw new IllegalArgumentException("Invalid term " + term);
		return getAtpFromNumTermAndYear(termNum, year);
	}

	@Override
	public String getAtpFromNumTermAndYear(String term, String year) {
		int ty = Integer.parseInt(year);
		String type;
		int tmo;
		if ("1".equals(term)) {
			type = "kuali.atp.type.Winter";
			tmo = Calendar.FEBRUARY;
		} else if ("2".equals(term)) {
			type = "kuali.atp.type.Spring";
			tmo = Calendar.APRIL;
		} else if ("3".equals(term)) {
			type = "kuali.atp.type.Summer";
			tmo = Calendar.JULY;
		} else if ("4".equals(term)) {
			type = "kuali.atp.type.Fall";
			tmo = Calendar.OCTOBER;
		} else
			throw new IllegalArgumentException("Invalid term " + term);
		Calendar cal = new GregorianCalendar(ty, tmo, 1);
		List<AtpInfo> atps;
		try {
			atps = KsapFrameworkServiceLocator.getAtpService()
					.getAtpsByDateAndType(cal.getTime(), type, getContext());
		} catch (Throwable t) {
			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			if (t instanceof Error)
				throw (Error) t;
			throw new IllegalStateException(
					"Unexpected error in ATP ID lookup", t);
		}
		assert atps != null && atps.size() == 1 : "Expected exactly one term for "
				+ type + " and date " + cal.getTime();
        if (atps != null && atps.size() > 0) {
            return atps.get(0).getId();
        }
        return "";
	}

	/**
	 * Gets term name as "Spring 2012" given an ATP ID.
	 * 
	 * @return
	 */
	@Override
	public String atpIdToTermName(String atpId) {
		String[] termYear = atpIdToTermAndYear(atpId);
		return (termYear[0] + " " + termYear[1]);
	}

	/**
	 * Returns true if an ATP is considered present or greater in the context of
	 * WHAT? Otherwise, false.
	 * 
	 * @param atpId
	 * @return
	 */
	@Override
	public boolean isAtpSetToPlanning(String atpId) {
		List<TermInfo> planningTermInfo;
		TermInfo comparingTerm;
		if (atpId.isEmpty()) {
			return false;
		}
		try {
			AcademicCalendarService acal = KsapFrameworkServiceLocator
					.getAcademicCalendarService();
			planningTermInfo = acal.searchForTerms(QueryByCriteria.Builder
					.fromPredicates(equalIgnoreCase("atpState",
							PlanConstants.PLANNING)), getContext());
			comparingTerm = acal.getTerm(atpId, getContext());
		} catch (Throwable t) {
			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			if (t instanceof Error)
				throw (Error) t;
			throw new IllegalStateException(
					"Unexpected error in ATP ID lookup", t);
		}
		assert planningTermInfo != null && planningTermInfo.size() >= 1 : "Expected at least one planning term";
        if (planningTermInfo != null && planningTermInfo.size() >= 1) {
            return comparingTerm.getStartDate().after(
                    planningTermInfo.get(0).getStartDate());
        }
        return false;
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
		if (atpId.isEmpty()) {
			return false;
		}
		List<TermInfo> inProgressTermInfo;
		TermInfo comparingTerm;
		try {
			AcademicCalendarService acal = KsapFrameworkServiceLocator
					.getAcademicCalendarService();
			inProgressTermInfo = acal.searchForTerms(QueryByCriteria.Builder
					.fromPredicates(equalIgnoreCase("atpState",
							PlanConstants.INPROGRESS)), getContext());
			comparingTerm = acal.getTerm(atpId, getContext());
		} catch (Throwable t) {
			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			if (t instanceof Error)
				throw (Error) t;
			throw new IllegalStateException(
					"Unexpected error in ATP ID lookup", t);
		}
		assert inProgressTermInfo != null && inProgressTermInfo.size() >= 1 : "Expected at least one in progress term";
        if (inProgressTermInfo != null && inProgressTermInfo.size() >= 1) {
            return comparingTerm.getStartDate().before(
                    inProgressTermInfo.get(0).getStartDate());
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
            termInfos = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("query", PlanConstants.PUBLISHED)), CourseSearchConstants.CONTEXT_INFO);
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
    public String getFirstPlanTerm() {
        List<TermInfo> termInfos = null;
        String publishedTerms = null;
        try {
            termInfos = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("query", PlanConstants.PLANNING)), CourseSearchConstants.CONTEXT_INFO);
        } catch (Exception e) {
            LOG.error("Web service call failed.", e);
            //  Create an empty list to Avoid NPE below allowing the data object to be fully initialized.
            termInfos = new ArrayList<TermInfo>();
        }
        if (termInfos != null && termInfos.size() > 0) {
            publishedTerms = termInfos.get(0).getId();
        }

        return publishedTerms;
    }
    @Override
    public boolean doesAtpExist(String atpId) {
        boolean doesAtpExist = false;
        try {
            AtpInfo atpInfo = KsapFrameworkServiceLocator.getAtpService().getAtp(atpId, getContext());
            if (atpInfo != null) {
                doesAtpExist = true;
            }
        } catch (Exception e) {
            LOG.error("Atp does not Exist", e);
        }
        return doesAtpExist;
    }


    @Override
	public boolean isAtpIdFormatValid(String atpId) {
        if (atpId.isEmpty()) {
			return false;
		}
		try {
			return KsapFrameworkServiceLocator.getAcademicCalendarService()
					.getTerm(atpId, getContext()) != null;
		} catch (Throwable t) {
			return false;
		}
	}

	@Override
	public void addServiceError(String propertyName) {
		String[] params = {};
		GlobalVariables.getMessageMap().putWarning(propertyName,
				PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
	}

    /**
     * Converts Kuali ATP ids into a YearTerm object.
     *
     * eg "kuali.atp.2012.1" becomes year = 2012, term = 1
     *
     * @param atp
     * @return
     */
    @Override
    public YearTerm atpToYearTerm(String atp) {
        if (atp == null) {
            throw new NullPointerException("atp");
        }

        Matcher m = CourseSearchConstants.ATP_REGEX.matcher(atp);
        if (m.find()) {
            int year = Integer.parseInt(m.group(1));
            int term = Integer.parseInt(m.group(2));
            return new DefaultYearTerm(year, term);
        }

        throw new IllegalArgumentException(atp);
    }

    /**
     * Converts UW quarter string into a YearTerm object.
     *
     * eg "Winter 2012" becomes year = 2012, term = 1
     *
     * @param text
     * @return
     */
    @Override
    public YearTerm termToYearTerm(String text) {
        if (text == null) {
            throw new NullPointerException("text");
        }
        text = text.toLowerCase();

        Matcher m = TERM_REGEX.matcher(text);
        if (m.find()) {
            String temp = m.group(1);
            int term = CourseSearchConstants.TERM_ID_LIST.indexOf(temp) + 1;
            int year = Integer.parseInt(m.group(2));
            return new DefaultYearTerm(year, term);
        }
        throw new IllegalArgumentException(text);
    }

    /**
     * Converts UW quarter string into a YearTerm object.
     *
     * eg "Winter","2012" becomes year = 2012, term = 1
     *
     * @param
     * @return
     */
    @Override
    public YearTerm quarterYearToYearTerm(String quarter, String year) {
        if (quarter == null) {
            throw new NullPointerException("quarter");
        }
        if (year == null) {
            throw new NullPointerException("year");
        }
        quarter = quarter.toLowerCase();

        int term = CourseSearchConstants.TERM_ID_LIST.indexOf(quarter);
        if( term != -1 ) {
            term = term + 1;
            int y = Integer.parseInt(year);
            return new DefaultYearTerm(y, term);
        }
        throw new IllegalArgumentException( quarter + " " + year );
    }
    @Override
    public List<TermInfo> populateAtpIdFromCalender() {

        List<TermInfo> scheduledTerms = new ArrayList<TermInfo>();

        TermInfo termInfo = new TermInfo();
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String atp = null;
        if (month >= 1 && month <= 3) {
            atp = getAtpIdFromTermAndYear(term1, year);
        }
        if (month >= 4 && month <= 6) {
            atp = getAtpIdFromTermAndYear(term2, year);
        }
        if (month >= 7 && month <= 9) {
            atp = getAtpIdFromTermAndYear(term3, year);
        }
        if (month >= 10 && month <= 12) {
            atp = getAtpIdFromTermAndYear(term4, year);
        }
        termInfo.setId(atp);
        scheduledTerms.add(termInfo);
        return scheduledTerms;
    }



}

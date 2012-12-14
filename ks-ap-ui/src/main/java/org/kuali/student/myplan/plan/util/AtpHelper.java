package org.kuali.student.myplan.plan.util;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.plan.PlanConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

/**
 * Helper methods for dealing with ATPs.
 */
public class AtpHelper {

    private static String term1 = "winter";
    private static String term2 = "spring";
    private static String term3 = "summer";
    private static String term4 = "autumn";

    private static transient AcademicCalendarService academicCalendarService;

    public static int TERM_COUNT = 4;

    private static final Logger logger = Logger.getLogger(AtpHelper.class);

    private static AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader
                    .getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                            AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return academicCalendarService;
    }

    /**
     * Query the Academic Calendar Service, determine the current ATP, and the return the ID.
     *
     * @return The ID of the current ATP.
     * @throws RuntimeException if the query fails or if the return data set doesn't make sense.
     */
    public static String getCurrentAtpId() {
        //   The first arg here is "usageKey" which isn't used.
        List<TermInfo> inProgressTerms = new ArrayList<TermInfo>();
        try {
            inProgressTerms = getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("query", PlanConstants.INPROGRESS)), CourseSearchConstants.CONTEXT_INFO);
        } catch (Exception e) {
            logger.error("Query to Academic Calendar Service failed.", e);
            /*If SWS Fails to load up scheduled Terms then current atp Id in TermInfo is populated from the calender month and year and set to the scheduledTerms list*/
            inProgressTerms=populateAtpIdFromCalender();
        }
        //  The UW implementation of the AcademicCalendarService.getCurrentTerms() contains the "current term" logic so we can simply
        //  use the first item in the list. Although, TODO: Not sure if the order of the list is guaranteed, so maybe putting a sort here
        //  is the Right thing to do.
        TermInfo currentTerm = inProgressTerms.get(0);
        return currentTerm.getId();
    }


   /**
     * Query the Academic Calendar Service for terms that have offering's published, determine the last ATP, and return its ID.
     *
     * @return The ID of the last scheduled ATP.
     * @throws RuntimeException if the query fails or if the return data set doesn't make sense.
     */
    public static String getLastScheduledAtpId() {
        //   The first arg here is "usageKey" which isn't used.
        List<TermInfo> scheduledTerms = new ArrayList<TermInfo>();
        try {
            scheduledTerms = getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("query", PlanConstants.PUBLISHED)), CourseSearchConstants.CONTEXT_INFO);
        } catch (Exception e) {
            logger.error("Query to Academic Calendar Service failed.", e);
            /*If SWS Fails to load up scheduled Terms then current atp Id in TermInfo is populated from the calender month and year and set to the scheduledTerms list*/
            scheduledTerms=populateAtpIdFromCalender();
        }
        //  The UW implementation of the AcademicCalendarService.getCurrentTerms() contains the "current term" logic so we can simply
        //  use the first item in the list. Although, TODO: Not sure if the order of the list is guaranteed, so maybe putting a sort here
        //  is the Right thing to do.
        TermInfo currentTerm = scheduledTerms.get( scheduledTerms.size() - 1 );
        return currentTerm.getId();
    }


    public static List<TermInfo> populateAtpIdFromCalender() {

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


    /**
     * Gets the ATP ID of the first ATP in the current academic year.
     */

    public static String getFirstAtpIdOfAcademicYear(String atpId) {
        String firstAtpId = null;
        String atpSuffix = atpId.replace(PlanConstants.TERM_ID_PREFIX, "");
        String[] termYear = atpSuffix.split("\\.");
        String year = termYear[0];
        String term = termYear[1];

        //   If the term is not Autumn/4 then the beginning of the academic year is (year - 1) . 4
        if (term.equals("4")) {
            firstAtpId = atpId;
        } else {
            String y = String.valueOf(Integer.valueOf(year) - 1);
            firstAtpId = AtpHelper.getAtpFromNumTermAndYear("4", y);
        }
        return firstAtpId;
    }

    /**
     * Returns an String[] {term, year} given an ATP ID.
     */
    public static String[] atpIdToTermAndYear(String atpId) {
        String atpSuffix = atpId.replace(PlanConstants.TERM_ID_PREFIX, "");

        //  See if the ATP ID is nearly sane.
        if (!atpSuffix.matches("[0-9]{4}\\.[1-4]{1}")) {
            throw new RuntimeException(String.format("ATP ID [%s] isn't formatted correctly.", atpId));
        }

        String[] termYear = atpSuffix.split("\\.");
        String year = termYear[0];
        String term = termYear[1];
        return new String[]{term, year};
    }

    /**
     * Converts an ATP ID to a Term and Year ...
     * "kuali.uw.atp.1991.1" -> {"Autumn", "1991"}
     *
     * @return A String array containing a term and year.
     */
    public static String[] atpIdToTermNameAndYear(String atpId) {
        String[] termYear = atpIdToTermAndYear(atpId);
        String term = termYear[0];
        String year = termYear[1];

        if (term.equals("4")) {
            term = "Autumn";
        } else if (term.equals("1")) {
            term = "Winter";
        } else if (term.equals("2")) {
            term = "Spring";
        } else if (term.equals("3")) {
            term = "Summer";
        }
        return new String[]{term, year};
    }

    /*  Retuns ATP ID in format kuali.uw.atp.1991.1 for term="Winter" and year = 1991*/
    public static String getAtpIdFromTermAndYear(String term, String year) {
        int termVal = 0;
        if (term.equalsIgnoreCase(term1)) {
            termVal = 1;
        }
        if (term.equalsIgnoreCase(term2)) {
            termVal = 2;
        }
        if (term.equalsIgnoreCase(term3)) {
            termVal = 3;
        }
        if (term.equalsIgnoreCase(term4)) {
            termVal = 4;
        }
        StringBuffer newAtpId = new StringBuffer();
        newAtpId = newAtpId.append(PlanConstants.TERM_ID_PREFIX).append(year).append(".").append(termVal);
        return newAtpId.toString();
    }

    /* Returns ATP ID as kuali.uw.atp.1991.1 for term=1 and year = 1991 */
    public static String getAtpFromNumTermAndYear(String term, String year) {
        StringBuffer newAtpId = new StringBuffer();
        newAtpId = newAtpId.append(PlanConstants.TERM_ID_PREFIX).append(year).append(".").append(term);
        return newAtpId.toString();
    }

    /**
     * Gets term name as "Spring 2012" given an ATP ID.
     *
     * @return
     */
    public static String atpIdToTermName(String atpId) {
        String[] termYear = atpIdToTermNameAndYear(atpId);
        return (termYear[0] + " " + termYear[1]);
    }

    /**
     * Returns true if an ATP is considered present or greater in the context of WHAT? Otherwise, false.
     *
     * @param atpId
     * @return
     */
    public static boolean isAtpSetToPlanning(String atpId) {
        boolean isSetToPlanning = false;
        List<TermInfo> planningTermInfo = null;
        try {
            planningTermInfo = getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("query", PlanConstants.PLANNING)), CourseSearchConstants.CONTEXT_INFO);
        } catch (Exception e) {
            logger.error("Could not load planningTermInfo as service call failed", e);
            /*If SWS Fails to load up planningTermInfo  then current atp Id in TermInfo is populated from the calender month and year and set to the planningTermInfo list*/
            planningTermInfo=AtpHelper.populateAtpIdFromCalender();
        }

        String[] planningAtpYearAndTerm = atpIdToTermAndYear(planningTermInfo.get(0).getId());
        String[] comparingAtpYearAndTerm = atpIdToTermAndYear(atpId);

        /*Planning term = atpId*/
        if (planningTermInfo.get(0).getId().equalsIgnoreCase(atpId)) {
            isSetToPlanning = true;
        }

        /*atpId term having same year as planning year but atpId term is greater than planning term*/

        if (!isSetToPlanning && Integer.parseInt(comparingAtpYearAndTerm[1]) == Integer.parseInt(planningAtpYearAndTerm[1]) && Integer.parseInt(comparingAtpYearAndTerm[0]) > Integer.parseInt(planningAtpYearAndTerm[0])) {
            isSetToPlanning = true;

        }

        /*atpId term having year greater than planning year*/
        if (!isSetToPlanning && Integer.parseInt(comparingAtpYearAndTerm[1]) > Integer.parseInt(planningAtpYearAndTerm[1])) {
            isSetToPlanning = true;
        }

        return isSetToPlanning;
    }

    /**
     * Returns true if an ATP is considered present or greater in the context of WHAT? Otherwise, false.
     *
     * @param atpId
     * @return
     */
    public static boolean isAtpCompletedTerm(String atpId) {
        boolean isAtpCompletedTerm = false;

        String[] planningAtpYearAndTerm = atpIdToTermAndYear(getCurrentAtpId());
        String[] comparingAtpYearAndTerm = atpIdToTermAndYear(atpId);

        /*atpId term having same year as planning year but atpId term less than planning term*/

        if (!isAtpCompletedTerm && Integer.parseInt(comparingAtpYearAndTerm[1]) == Integer.parseInt(planningAtpYearAndTerm[1]) && Integer.parseInt(comparingAtpYearAndTerm[0]) < Integer.parseInt(planningAtpYearAndTerm[0])) {
            isAtpCompletedTerm = true;
        }

        /*atpId term having year less than planning year*/
        if (!isAtpCompletedTerm && Integer.parseInt(comparingAtpYearAndTerm[1]) < Integer.parseInt(planningAtpYearAndTerm[1])) {
            isAtpCompletedTerm = true;
        }

        return isAtpCompletedTerm;
    }

    public static boolean isAtpIdFormatValid(String atpId) {
        return atpId.matches(PlanConstants.TERM_ID_PREFIX + "[0-9]{4}\\.[1-4]{1}");
    }

    public static void addServiceError(String propertyName) {
        String[] params = {};
        GlobalVariables.getMessageMap().putWarning(propertyName, PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
    }

}

package org.kuali.student.ap.framework.context.support;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.AtpHelper;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;
import static org.kuali.rice.core.api.criteria.PredicateFactory.notEqualIgnoreCase;

/**
 * Helper methods for dealing with ATPs.
 */
public class DefaultAtpHelper implements AtpHelper {

    public static final String LAST_DROP_DAY = "last_drop_day";

    private Map<String,Integer> termMap;
    private Map<Integer,String> atpTypeMap;
    private Map<Integer,String> typeMonthDayMap;
    private String defaultAtp;

    private String currentAtpId;


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
        if(currentAtpId==null){
            Calendar c = Calendar.getInstance();

            List<AtpInfo> atpInfos = null;

            try {
                atpInfos = KsapFrameworkServiceLocator.getAtpService().getAtpsByDate(c.getTime(),KsapFrameworkServiceLocator.getContext().getContextInfo());
            } catch (Throwable t) {
                if (t instanceof RuntimeException)
                    throw (RuntimeException) t;
                if (t instanceof Error)
                    throw (Error) t;
                throw new IllegalStateException(
                        "Unexpected error in ATP lookup", t);
            }
            if(atpInfos!=null && atpInfos.size()>0){
                for(int i=0;i<atpInfos.size();i++){
                    if(!StringUtils.isEmpty(atpInfos.get(i).getCode())){
                        currentAtpId = atpInfos.get(i).getId();
                    }
                }
            }else{
                currentAtpId = getDefaultAtp();
            }
        }
        return currentAtpId;
    }

    /**
     * Query the Academic Calendar Service for terms that have offering's
     * published, determine the last ATP, and return its ID.
     *
     * @return The ID of the last scheduled ATP.
     * @throws RuntimeException if the query fails or if the return data set doesn't make
     *                          sense.
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
        return getDefaultAtp();
    }

    @Override
    public YearTerm getYearTerm(String atpId) {
        return new DefaultYearTerm(atpId);
    }

    @Override
    public String getAtpId(YearTerm yearTerm) {
        return yearTerm.toATP();
    }

    @Override
    public String getAtpId(String year, String term) {
        return (new DefaultYearTerm(year, term)).toATP();
    }

    @Override
    public String getAtpId(int year, int term) {
        return (new DefaultYearTerm(year, term)).toATP();
    }

    @Override
    public String getAtpId(int year, String term) {
        return (new DefaultYearTerm(year,term)).toATP();
    }

    @Override
    public boolean validateAtp(String atpId) {
        try {
            TermInfo term = KsapFrameworkServiceLocator
                    .getAcademicCalendarService().getTerm(atpId, getContext());
            if (term != null) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean validateAtpId(String atpId) {
        try {
            YearTerm term = new DefaultYearTerm(atpId);
            return validateAtp(atpId);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isCompletedTerm(String atpId) {
        YearTerm currentTerm = new DefaultYearTerm(getCurrentAtpId());
        YearTerm tempTerm = new DefaultYearTerm(atpId);

        int compare = currentTerm.compareTo(tempTerm);

        if (compare >= 0) return false;
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


        if ((yearTerm.getTerm() - 1) == yearTerm.getMAX_TERM_INDEX()) {
            year = yearTerm.getYear() - 1;
        }

        return (new DefaultYearTerm(year, term)).toATP();
    }

    /**
     * Returns true if an ATP is considered present or greater in the context of
     * WHAT? Otherwise, false.
     * <p/>
     * Is set to the future or current term
     *
     * @param atpId
     * @return
     */
    @Override
    public boolean isAtpSetToPlanning(String atpId) {
        String currentTerm = this.getCurrentAtpId();
        YearTerm currentYearTerm = new DefaultYearTerm(currentTerm);

        try {
            YearTerm newYearTerm = new DefaultYearTerm(atpId);
            int compareTermsResults = currentYearTerm.compareTo(newYearTerm);
            if (compareTermsResults < 0) return false;
        } catch (Exception e) {
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

        try {
            YearTerm newYearTerm = new DefaultYearTerm(atpId);
            int compareTermsResults = currentYearTerm.compareTo(newYearTerm);
            if (compareTermsResults < 0) return true;
        } catch (Exception e) {
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
            offerings = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(atp, splitStr[0].trim(), KsapFrameworkServiceLocator.getContext().getContextInfo());
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
            termInfos = KsapFrameworkServiceLocator.getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("atpState", "kuali.atp.state.Official"), notEqualIgnoreCase("atpType", "kuali.atp.type.HolidayCalendar"), notEqualIgnoreCase("atpType", "kuali.atp.type.AcademicCalendar")), KsapFrameworkServiceLocator.getContext().getContextInfo());
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

    @Override
    public void setTermMap(Map<String,Integer> termMap){
        this.termMap=termMap;
    }

    @Override
    public Map<String,Integer> getTermMap(){
        return termMap;
    }

    @Override
    public void setAtpTypeMap(Map<Integer,String> atpTypeMap){
        this.atpTypeMap=atpTypeMap;
    }

    @Override
    public Map<Integer,String> getAtpTypeMap(){
        return atpTypeMap;
    }

    @Override
    public void setTypeMonthDayMap(Map<Integer,String> typeMonthDayMap){
        this.typeMonthDayMap= typeMonthDayMap;
    }

    @Override
    public Map<Integer,String> getTypeMonthDayMap(){
        return typeMonthDayMap;
    }

    @Override
    public void setDefaultAtp(String atpId){
        this.defaultAtp=atpId;
    }

    @Override
    public String getDefaultAtp(){
        if(defaultAtp==null){
            defaultAtp="kuali.atp.2000Fall";
        }
        return defaultAtp;
    }

}

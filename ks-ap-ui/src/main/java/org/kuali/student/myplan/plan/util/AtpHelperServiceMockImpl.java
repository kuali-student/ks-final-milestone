package org.kuali.student.myplan.plan.util;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

public class AtpHelperServiceMockImpl implements AtpHelperService{

    private ContextInfo getContext() {
        return KsapFrameworkServiceLocator.getContext().getContextInfo();
    }

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
        assert inProgressTerms != null && inProgressTerms.size() > 1 : "No in-progress terms from acal service"
                + inProgressTerms;
        if( inProgressTerms != null && inProgressTerms.size() > 1)
            return inProgressTerms.iterator().next().getId();
        return "";
    }

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
        if(scheduledTerms != null && scheduledTerms.size()>1){
            return scheduledTerms.get(scheduledTerms.size() - 1).getId();
        }
        // Need to handle not finding scheduled terms
        return "";
    }

    @Override
    public String getFirstAtpIdOfAcademicYear(String atpId) {
        String[] termYear = atpIdToTermAndYear(atpId);

        String year = termYear[0];
        String term = termYear[1];

        // If the term is not Autumn/4 then the beginning of the academic year
        // is (year - 1) . 4
        if (term.equals("4")) {
            return atpId;
        } else {
            String y = String.valueOf(Integer.valueOf(year) - 1);
            return AtpHelper.getAtpFromNumTermAndYear("4", y);
        }
    }

    /**
     * Returns an String[] {term, year} given an ATP ID.
     */
    public String[] atpIdToTermAndYear(String atpId) {
        AtpInfo atp;
        if(StringUtils.isEmpty(atpId)){
            atpId="19843";
        }
        try {
            atp = KsapFrameworkServiceLocator.getAtpService().getAtp(atpId,
                    getContext());
        } catch (Throwable t) {
            if (t instanceof RuntimeException)
                throw (RuntimeException) t;
            if (t instanceof Error)
                throw (Error) t;
            throw new IllegalStateException(
                    "Unexpected error in sheduled ATP ID lookup", t);
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
        else if (atptype.equals("kuali.atp.type.Summer"))
            term = "3";
        else if (atptype.equals("kuali.atp.type.Fall"))
            term = "4";
        else
            throw new IllegalArgumentException(
                    "Unable to determine term from ATP " + atp);
        return new String[] { term, year };
    }

    @Override
    public String[] atpIdToTermNameAndYear(String atpId) {
        AtpInfo atp;
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
        Matcher tm = Pattern.compile("([A-Za-z]+) ([0-9]+)").matcher(
                atp.getName());
        if (!tm.matches())
            throw new IllegalArgumentException("Term name " + atp.getName()
                    + " doesn't match expected format");
        String term = tm.group(1);
        String year = tm.group(2);
        return new String[] { term, year };
    }

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
            tmo = Calendar.JANUARY;
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
        if(atps!= null && atps.size()>0){
            return atps.get(0).getId();
        }
        return "";
    }

    @Override
    public String atpIdToTermName(String atpId) {
        String[] termYear = atpIdToTermNameAndYear(atpId);
        return (termYear[0] + " " + termYear[1]);
    }

    @Override
    public boolean isAtpSetToPlanning(String atpId) {
        List<TermInfo> planningTermInfo;
        TermInfo comparingTerm;
        if(StringUtils.isEmpty(atpId)){
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
        if(planningTermInfo != null && planningTermInfo.size() >= 1){
            return comparingTerm.getStartDate().after(
                planningTermInfo.get(0).getStartDate());
        }
        return false;
    }

    @Override
    public boolean isAtpCompletedTerm(String atpId) {
        if(StringUtils.isEmpty(atpId)){
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
        if(inProgressTermInfo != null && inProgressTermInfo.size() >= 1){
            return comparingTerm.getStartDate().before(
                    inProgressTermInfo.get(0).getStartDate());
        }
        return false;
    }

    @Override
    public boolean isAtpIdFormatValid(String atpId) {
        if(StringUtils.isEmpty(atpId)){
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
}

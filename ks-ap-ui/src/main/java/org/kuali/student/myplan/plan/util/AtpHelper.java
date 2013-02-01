package org.kuali.student.myplan.plan.util;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

/**
 * Helper methods for dealing with ATPs.
 */
public class AtpHelper {
    private static AtpHelper atpHelper = new AtpHelper();

	private static AtpHelperService atpHelperService;


    public void setAtpHelperService(AtpHelperService helperService){
        atpHelperService = helperService;
    }
    public AtpHelperService getAtpHelperService(){
        return atpHelperService;
    }
    public AtpHelper(){

    }
    public static AtpHelper createInstance(){
        return atpHelper;
    }
	public static String getCurrentAtpId() {
        return atpHelperService.getCurrentAtpId();
	}

	public static String getLastScheduledAtpId() {
        return atpHelperService.getLastScheduledAtpId();
	}

	public static String getFirstAtpIdOfAcademicYear(String atpId) {
        return atpHelperService.getFirstAtpIdOfAcademicYear(atpId);
    }

	public static String[] atpIdToTermAndYear(String atpId) {
        return atpHelperService.atpIdToTermAndYear(atpId);
	}

	public static String[] atpIdToTermNameAndYear(String atpId) {
        return atpHelperService.atpIdToTermNameAndYear(atpId);
	}

	public static String getAtpIdFromTermAndYear(String term, String year) {
        return atpHelperService.getAtpIdFromTermAndYear(term,year);
	}

	public static String getAtpFromNumTermAndYear(String term, String year) {
        return atpHelperService.getAtpFromNumTermAndYear(term,year);
	}

	public static String atpIdToTermName(String atpId) {
        return atpHelperService.atpIdToTermName(atpId);
	}

	public static boolean isAtpSetToPlanning(String atpId) {
        return atpHelperService.isAtpSetToPlanning(atpId);
	}

	public static boolean isAtpCompletedTerm(String atpId) {
        return atpHelperService.isAtpCompletedTerm(atpId);
	}

	public static boolean isAtpIdFormatValid(String atpId) {
        return atpHelperService.isAtpIdFormatValid(atpId);
	}

	public static void addServiceError(String propertyName) {
        atpHelperService.addServiceError(propertyName);
	}

}

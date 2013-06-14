package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.CalendarSearchViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.common.util.CalendarSearchViewHelperUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CalendarSearchViewHelperServiceImpl extends KSViewHelperServiceImpl implements CalendarSearchViewHelperService {
    private final static Logger LOG = Logger.getLogger(CalendarSearchViewHelperServiceImpl.class);
    private static final long serialVersionUID = 1L;
    private transient AcademicCalendarService academicCalendarService;
    private transient AtpService atpService;

    /**
     * This method search terms based on the input name and the input year
     * @param name
     * @param year
     * @param context
     * @return
     * @throws Exception
     */
    public List<TermInfo> searchForTerms(String name, String year,ContextInfo context)throws Exception {
    	return CalendarSearchViewHelperUtil.searchForTerms(name, year, context, getAtpService(), getTypeService());
    }

    /**
     * This method search AcademicCalendars based on the input name and the input year
     * @param name
     * @param year
     * @param context
     * @return
     * @throws Exception
     */
    public List<AcademicCalendarInfo> searchForAcademicCalendars(String name, String year,ContextInfo context)throws Exception {
        return CalendarSearchViewHelperUtil.searchForAcademicCalendars(name,year,context,getAtpService());
    }

    /**
     * This method search HolidayCalendars based on the input name and the input year
     * @param name
     * @param year
     * @param context
     * @return
     * @throws Exception
     */
    public List<HolidayCalendarInfo> searchForHolidayCalendars(String name, String year,ContextInfo context)throws Exception {
        return CalendarSearchViewHelperUtil.searchForHolidayCalendars(name, year, context, getAtpService());
    }

    /**
     * This method builds URL of performRedirect for a term
     * @param term
     * @param methodToCall
     * @param readOnlyView
     * @param context
     * @return
     */

    public Properties buildTermURLParameters(TermInfo term, String methodToCall, boolean readOnlyView, ContextInfo context){

        String acalId = null;
        try {
            // check if it's a subterm
            List<TermInfo> parentTerms = getAcademicCalendarService().getContainingTerms(term.getId(), context);
            List<AcademicCalendarInfo> atps = new ArrayList<AcademicCalendarInfo>();
            if(parentTerms.isEmpty()) {
                atps = getAcademicCalendarService().getAcademicCalendarsForTerm(term.getId(), context);
            } else {
                TermInfo parentTerm = parentTerms.get(0);
                atps = getAcademicCalendarService().getAcademicCalendarsForTerm(parentTerm.getId(), context);
            }
            if (!atps.isEmpty()){
                acalId = atps.get(0).getId();
            }
        } catch (Exception e) {
           if (LOG.isDebugEnabled()){
                LOG.debug("Error calling getAcademicCalendarsForTerm - " + term.getId());

            }
            convertServiceExceptionsToUI(e);
        }

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(CalendarConstants.CALENDAR_ID,acalId);
        props.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_VIEW);
        props.put(CalendarConstants.PAGE_ID,CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        props.put(CalendarConstants.SELECT_TAB,CalendarConstants.ACAL_TERM_TAB);
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));

        if (readOnlyView){
            props.put(CalendarConstants.READ_ONLY_VIEW,""+ true);
        }

        return props;

    }

    /**
     * This method builds URL of performRedirect for an AcademicCalendar
     * @param acal
     * @param methodToCall
     * @param readOnlyView
     * @param context
     * @return
     */
    public Properties buildACalURLParameters(AcademicCalendarInfo acal, String methodToCall, boolean readOnlyView, ContextInfo context){

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(CalendarConstants.CALENDAR_ID,acal.getId());
        props.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_VIEW);
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));

        if (StringUtils.equals(methodToCall,CalendarConstants.AC_COPY_METHOD)){
           props.put(CalendarConstants.PAGE_ID,CalendarConstants.ACADEMIC_CALENDAR_COPY_PAGE);
        } else {
           props.put(CalendarConstants.PAGE_ID,CalendarConstants.ACADEMIC_CALENDAR_EDIT_PAGE);
        }

        if (readOnlyView){
            props.put(CalendarConstants.READ_ONLY_VIEW,""+ true);
        }

        return props;

    }

    /**
     * This method builds URL of performRedirect for a HolidayCalendar
     * @param hcInfo
     * @param methodToCall
     * @param readOnlyView
     * @param context
     * @return
     */
    public Properties buildHCalURLParameters(HolidayCalendarInfo hcInfo, String methodToCall, boolean readOnlyView, ContextInfo context){

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(CalendarConstants.CALENDAR_ID, hcInfo.getId());
        props.put(UifParameters.VIEW_ID, CalendarConstants.HOLIDAYCALENDAR_FLOWVIEW);
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));

        if (StringUtils.equals(methodToCall,CalendarConstants.HC_COPY_METHOD)){
           props.put(CalendarConstants.PAGE_ID,CalendarConstants.HOLIDAYCALENDAR_COPYPAGE);
        }else if (StringUtils.equals(methodToCall,CalendarConstants.HC_VIEW_METHOD) && readOnlyView){
            props.put(CalendarConstants.PAGE_ID,CalendarConstants.HOLIDAYCALENDAR_VIEWPAGE);
        } else {
           props.put(CalendarConstants.PAGE_ID,CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);
        }

        return props;

    }

    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
             academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

    protected AtpService getAtpService() {
        if(atpService == null) {
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.atpService;
    }
}

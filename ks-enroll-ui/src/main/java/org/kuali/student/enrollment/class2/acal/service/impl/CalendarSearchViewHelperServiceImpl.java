package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.acal.dto.AcalSearchResult;
import org.kuali.student.enrollment.class2.acal.service.CalendarSearchViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

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
     * This method searches for academic calendars, terms, and holiday calendars based on the input name and the input year
     * @param nameParam name being searched on
     * @param yearParam year (four digit string)
     * @param calendarType Holiday, Term or Acal
     * @param contextInfo context of the service call
     * @return list of search results
     */
    @Override
    public List<AcalSearchResult> searchForCalendars(String nameParam, String yearParam, String calendarType, ContextInfo contextInfo) throws Exception {

        List<AcalSearchResult> acalResults = new ArrayList<AcalSearchResult>();

        SearchRequestInfo searchRequest = new SearchRequestInfo("atp.search.advancedAtpSearch");
        List<String> termTypeKeys = new ArrayList<String>();
        List<String> subtermTypeKeys = new ArrayList<String>();
        if(CalendarConstants.TERM.equals(calendarType) || CalendarConstants.SUBTERM.equals(calendarType))   {
            //get all term and subterm typeKeys
            List<TypeTypeRelationInfo> typeRelations = getTypeService().getTypeTypeRelationsByOwnerAndType("kuali.atp.type.group.term", "kuali.type.type.relation.type.group", contextInfo);
            List<String> allTermTypeKeys = new ArrayList<String>(typeRelations.size());
            for(TypeTypeRelationInfo typeRelation:typeRelations){
                allTermTypeKeys.add(typeRelation.getRelatedTypeKey());
            }
            for(String termTypeKey:allTermTypeKeys){
                List<TypeTypeRelationInfo> typeTypeRelationInfos = getTypeService().getTypeTypeRelationsByRelatedTypeAndType(termTypeKey, TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, contextInfo);
                if (typeTypeRelationInfos.size()==0){
                    termTypeKeys.add(termTypeKey);
                }
                else if(typeTypeRelationInfos.size()>0){
                    subtermTypeKeys.add(termTypeKey);
                }
            }
        }

        //Add type param based on the calendar type being searched for
        if(CalendarConstants.TERM.equals(calendarType)){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpType", termTypeKeys);
        }else if(CalendarConstants.SUBTERM.equals(calendarType)) {
            searchRequest.addParam("atp.advancedAtpSearchParam.atpType", subtermTypeKeys);
        }else if(CalendarConstants.HOLIDAYCALENDER.equals(calendarType)){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpType", AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        }else if(CalendarConstants.ACADEMICCALENDER.equals(calendarType)){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpType", AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
        }

        //Add the year and name params if they are set
        if(nameParam!=null&&!nameParam.isEmpty()){
            String name = nameParam.replace('*', '%');
            searchRequest.addParam("atp.advancedAtpSearchParam.atpShortName", name);
        }
        if(yearParam!=null&&!yearParam.isEmpty()){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpYear", yearParam);
        }

        //Perform the search
        SearchResultInfo searchResults = getAtpService().search(searchRequest, contextInfo);

        //Parse the search results
        for(SearchResultRowInfo row : searchResults.getRows()){
            String id = null;
            String name = null;
            String startDate = null;
            String endDate = null;
            String stateKey = null;

            for(SearchResultCellInfo cell : row.getCells()){
                if("atp.resultColumn.atpId".equals(cell.getKey())){
                    id = cell.getValue();
                }else if("atp.resultColumn.atpShortName".equals(cell.getKey())){
                    name = cell.getValue();
                }else if("atp.resultColumn.atpStartDate".equals(cell.getKey())){
                    startDate = cell.getValue();
                }else if("atp.resultColumn.atpEndDate".equals(cell.getKey())){
                    endDate = cell.getValue();
                }else if("atp.resultColumn.atpState".equals(cell.getKey())){
                    stateKey = cell.getValue();
                }
            }

            AcalSearchResult acalResult = new AcalSearchResult();
            acalResult.setId(id);
            acalResult.setName(name);
            acalResult.setStartDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(startDate));
            acalResult.setEndDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(endDate));
            acalResult.setStateKey(stateKey);
            acalResult.setStateKeyDisplay(getStateService().getState(stateKey, contextInfo).getName());
            acalResult.setAcalSearchTypeKey(calendarType);

            acalResults.add(acalResult);
        }

        return acalResults;

    }

    /**
     * This method builds URL of performRedirect for a term
     * @param term
     * @param methodToCall
     * @param readOnlyView
     * @param context
     * @return
     */
    public Properties buildTermURLParameters(AcalSearchResult term, String methodToCall, boolean readOnlyView, ContextInfo context){

        String acalId = null;
        try {
            // check if it's a subterm
            List<TermInfo> parentTerms = getAcademicCalendarService().getContainingTerms(term.getId(), context);
            List<AcademicCalendarInfo> atps;
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
    public Properties buildACalURLParameters(AcalSearchResult acal, String methodToCall, boolean readOnlyView, ContextInfo context){

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
    public Properties buildHCalURLParameters(AcalSearchResult hcInfo, String methodToCall, boolean readOnlyView, ContextInfo context){

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

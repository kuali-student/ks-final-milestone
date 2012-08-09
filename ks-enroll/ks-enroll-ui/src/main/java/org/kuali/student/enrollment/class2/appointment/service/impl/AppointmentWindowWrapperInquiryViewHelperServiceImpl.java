package org.kuali.student.enrollment.class2.appointment.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.type.service.TypeService;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.*;


public class AppointmentWindowWrapperInquiryViewHelperServiceImpl extends InquirableImpl {

    public final static String WINDOW_WRAPPER_KEY = "id";
    private transient AppointmentService appointmentService;
    private transient AcademicCalendarService academicCalendarService;
    private transient TypeService typeService;
    private transient PopulationService populationService;
    private transient SearchService searchService;

    @Override
    public AppointmentWindowWrapper retrieveDataObject(Map<String, String> parameters) {

        AppointmentWindowWrapper appointmentWindowWrapper = new AppointmentWindowWrapper();
        AppointmentService appointmentService = getAppointmentService();
        ContextInfo context = getContextInfo();
        try{
            //need to retrieve AppointmentWindowInfo and all info related to slots and assignments to form the AppointmentWindowWrapper.
            String windowId = parameters.get(WINDOW_WRAPPER_KEY);
            if(windowId == null){
                System.out.println(">>>windowId is null");
                return null;
            }
            else {
                System.out.println(">>>windowId ="+windowId);
            }
            //populate Window Info section
            AppointmentWindowInfo appointmentWindowInfo = appointmentService.getAppointmentWindow(windowId, context);
            appointmentWindowWrapper.setAppointmentWindowInfo(appointmentWindowInfo);
            appointmentWindowWrapper.setId(appointmentWindowInfo.getId());
            KeyDateInfo period = getAcalService().getKeyDate(appointmentWindowInfo.getPeriodMilestoneId(),context);
            appointmentWindowWrapper.setPeriodName(period.getName());
            PopulationInfo populationInfo = getPopulationService().getPopulation(appointmentWindowInfo.getAssignedPopulationId(),context);
            appointmentWindowWrapper.setAssignedPopulationName(populationInfo.getName());
            TypeInfo type = getTypeService().getType(appointmentWindowInfo.getTypeKey(), context);
            appointmentWindowWrapper.setWindowTypeName(type.getName());

            //Use a search to get window detail information in one call
            SearchRequestInfo searchRequest = new SearchRequestInfo("appt.search.appointmentCountForWindowId");
            searchRequest.addParam("windowId",windowId);
            SearchResultInfo searchResult = getSearchService().search(searchRequest, null);

            //Map the search results back to the appointment window
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

            Map<String, String> searchResultMap = convertToMap(searchResult).get(0);
            Integer numberOfSlots = searchResultMap.get("numSlots")==null?null:Integer.parseInt(searchResultMap.get("numSlots"));
            Integer numberOfStudents = searchResultMap.get("numAppts")==null?null:Integer.parseInt(searchResultMap.get("numAppts"));
            double meanStudentsPerSlot = Math.ceil(numberOfStudents/(float)numberOfSlots);
            String firstSlotPopulated = searchResultMap.get("firstSlot");
            String lastSlotPopulated = searchResultMap.get("lastSlot");
            Date windowCreate = searchResultMap.get("createTime")==null?null:formatter.parse(searchResultMap.get("createTime"));

            appointmentWindowWrapper.setNumberOfSlots(numberOfSlots);
            appointmentWindowWrapper.setNumberOfStudents(numberOfStudents);
            appointmentWindowWrapper.setMeanStudentsPerSlot(new Float(meanStudentsPerSlot));
            appointmentWindowWrapper.setFirstSlotPopulated(firstSlotPopulated);
            appointmentWindowWrapper.setLastSlotPopulated(lastSlotPopulated);
            appointmentWindowWrapper.setAssignmentsCreated(windowCreate);

            return appointmentWindowWrapper;

        }catch (Exception e){

        }

        return null;
    }

    private List<Map<String,String>> convertToMap(SearchResultInfo searchResult) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for(SearchResultRowInfo row:searchResult.getRows()){
            Map<String,String> map = new HashMap<String,String>();
            for(SearchResultCellInfo cell:row.getCells()){
                map.put(cell.getKey(),cell.getValue());
            }
            list.add(map);
        }
        return list;
    }

    private String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String formattedDate = formatter.format(date);
        if (StringUtils.endsWithIgnoreCase(formattedDate, "12:00 am")){
            return StringUtils.removeEndIgnoreCase(formattedDate,"12:00 am");
        }else {
            return formattedDate;
        }
    }

    public AcademicCalendarService getAcalService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }


    public AppointmentService getAppointmentService() {
        if(appointmentService == null) {
            appointmentService = (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return appointmentService;
    }


    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationMockService")); // TODO: Fix with real service
        }
        return populationService;
    }

    protected SearchService getSearchService() {
        if(searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }



}


package org.kuali.student.enrollment.class2.appointment.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.r2.common.class1.search.ApptWindowCountsSearchImpl;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.search.util.SearchResultHelper;

import javax.xml.namespace.QName;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


public class AppointmentWindowWrapperInquiryViewHelperServiceImpl extends InquirableImpl {
    private static final long serialVersionUID = 1L;
    public final static String WINDOW_WRAPPER_KEY = "id";
    private transient AppointmentService appointmentService;
    private transient AcademicCalendarService academicCalendarService;
    private transient TypeService typeService;
    private transient PopulationService populationService;
    private transient SearchService searchService;

    @Override
    public AppointmentWindowWrapper retrieveDataObject(Map<String, String> parameters) {

        AppointmentWindowWrapper appointmentWindowWrapper = null;
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
            appointmentWindowWrapper = new AppointmentWindowWrapper();
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
            SearchRequestInfo searchRequest = new SearchRequestInfo(ApptWindowCountsSearchImpl.SEARCH_TYPE.getKey());
            searchRequest.addParam(ApptWindowCountsSearchImpl.APPT_WINDOW_ID.getKey(),windowId);
            SearchResultInfo searchResult = getSearchService().search(searchRequest, null);

            SearchResultHelper resultHelper = new SearchResultHelper(searchResult);
            Integer numberOfSlots = resultHelper.getAsInteger(0, ApptWindowCountsSearchImpl.NUM_SLOTS);
            Integer numberOfStudents = resultHelper.getAsInteger(0, ApptWindowCountsSearchImpl.NUM_APPTS);
            double meanStudentsPerSlot = Math.ceil(numberOfStudents/(float)numberOfSlots);
            String firstSlotPopulated = resultHelper.get(0, ApptWindowCountsSearchImpl.FIRST_SLOT);
            String lastSlotPopulated = resultHelper.get(0, ApptWindowCountsSearchImpl.LAST_SLOT);
            Date windowCreate = resultHelper.getAsDate(0, ApptWindowCountsSearchImpl.CREATE_TIME);
            

            appointmentWindowWrapper.setNumberOfSlots(numberOfSlots);
            appointmentWindowWrapper.setNumberOfStudents(numberOfStudents);
            appointmentWindowWrapper.setMeanStudentsPerSlot(new Float(meanStudentsPerSlot));
            appointmentWindowWrapper.setFirstSlotPopulated(firstSlotPopulated);
            appointmentWindowWrapper.setLastSlotPopulated(lastSlotPopulated);
            appointmentWindowWrapper.setAssignmentsCreated(windowCreate);

        }catch (Exception e){
             throw new RuntimeException("Unable to retireve Apppointment Window from Inquiry", e);
        }

        return appointmentWindowWrapper;
    }


    private String getFormattedDate(Date date) {

        String formattedDate = DateFormatters.MONTH_DAY_YEAR_TIME_DATE_FORMATTER.format(date);
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


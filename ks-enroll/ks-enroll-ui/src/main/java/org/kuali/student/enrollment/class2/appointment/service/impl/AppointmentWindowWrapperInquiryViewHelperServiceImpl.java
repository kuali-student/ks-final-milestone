package org.kuali.student.enrollment.class2.appointment.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;

import javax.xml.namespace.QName;
import java.util.*;


public class AppointmentWindowWrapperInquiryViewHelperServiceImpl extends InquirableImpl {

    public final static String WINDOW_WRAPPER_KEY = "id";
    private transient AppointmentService appointmentService;

    @Override
    public AppointmentWindowWrapper retrieveDataObject(Map<String, String> parameters) {

        AppointmentWindowWrapper appointmentWindowWrapper = new AppointmentWindowWrapper();
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
            AppointmentWindowInfo appointmentWindowInfo = getAppointmentService().getAppointmentWindow(windowId,getContextInfo());
            appointmentWindowWrapper.setAppointmentWindowInfo(appointmentWindowInfo);
            appointmentWindowWrapper.setId(appointmentWindowInfo.getId());
            KeyDateInfo period = getAcalService().getKeyDate(appointmentWindowInfo.getPeriodMilestoneId(),getContextInfo());
            appointmentWindowWrapper.setPeriodName(period.getName());
            PopulationInfo populationInfo = getPopulationService().getPopulation(appointmentWindowInfo.getAssignedPopulationId(),getContextInfo());
            appointmentWindowWrapper.setAssignedPopulationName(populationInfo.getName());
            TypeInfo type = getTypeService().getType(appointmentWindowInfo.getTypeKey(), getContextInfo());
            appointmentWindowWrapper.setWindowTypeName(type.getName());

            List<AppointmentSlotInfo> slots = getAppointmentService().getAppointmentSlotsByWindow(appointmentWindowInfo.getId(),getContextInfo());
            appointmentWindowWrapper.setNumberOfSlots(slots.size());



            return appointmentWindowWrapper;

        }catch (Exception e){

        }
 
        return null;
    }

    public AcademicCalendarService getAcalService() {
        return (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
    }

    public TypeService getTypeService() {
        return (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeService.class.getSimpleName()));
    }

    public AppointmentService getAppointmentService() {
        return (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
    }

    protected PopulationService getPopulationService() {
        //populationService is retrieved using global resource loader which is wired in ks-enroll-context.xml
        return (PopulationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX+"population", "PopulationService"));
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


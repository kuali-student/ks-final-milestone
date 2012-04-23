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
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AppointmentWindowWrapperInquiryViewHelperServiceImpl extends InquirableImpl {

    public final static String WINDOW_WRAPPER_KEY = "id";
    private transient AppointmentService appointmentService;

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

            //populate Assignment Info section
            if(AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY.equals(appointmentWindowInfo.getStateKey())) {
                int numberOfStudents = 0;
                List<AppointmentSlotInfo> slots = appointmentService.getAppointmentSlotsByWindow(appointmentWindowInfo.getId(),context);
                appointmentWindowWrapper.setNumberOfSlots(slots.size());

                if(!slots.isEmpty()) {
                    for(AppointmentSlotInfo slot : slots){
                        List<AppointmentInfo> appointments = appointmentService.getAppointmentsBySlot(slot.getId(),context);
                        numberOfStudents = numberOfStudents+appointments.size();
                    }
                    appointmentWindowWrapper.setNumberOfStudents(numberOfStudents);

                    float meanStudentsPerSlot = numberOfStudents/slots.size();
                    appointmentWindowWrapper.setMeanStudentsPerSlot(new Float(meanStudentsPerSlot));

                    AppointmentSlotInfo slot1 = slots.get(0);
                    appointmentWindowWrapper.setFirstSlotPopulated(getFormattedDate(slot1.getStartDate()));

                    AppointmentSlotInfo slot = slots.get(slots.size()-1);
                    appointmentWindowWrapper.setLastSlotPopulated(getFormattedDate(slot.getStartDate()));

                    List<AppointmentInfo> appointments = appointmentService.getAppointmentsBySlot(slot.getId(),context);
                    if(!appointments.isEmpty()){
                        AppointmentInfo appointment = appointments.get(appointments.size()-1);
                        appointmentWindowWrapper.setAssignmentsCreated(appointment.getMeta().getCreateTime());
                    }
                }
            }

            return appointmentWindowWrapper;

        }catch (Exception e){

        }

        return null;
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


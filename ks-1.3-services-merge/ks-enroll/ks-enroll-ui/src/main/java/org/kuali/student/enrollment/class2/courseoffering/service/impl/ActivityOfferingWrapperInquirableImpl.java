package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;

import javax.xml.namespace.QName;
import java.util.Map;

public class ActivityOfferingWrapperInquirableImpl extends InquirableImpl {

    private TypeService typeService;
    private StateService stateService;
    private AcademicCalendarService acalService;

    @Override
    public ActivityOfferingWrapper retrieveDataObject(Map<String, String> parameters) {
        try {
            ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().getActivityOffering(parameters.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID), getContextInfo());
            ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper(activityOfferingInfo);
            TypeInfo type = getTypeService().getType(activityOfferingInfo.getTypeKey(),getContextInfo());
            aoWrapper.setTypeName(type.getName());
            StateInfo state = getStateService().getState(activityOfferingInfo.getStateKey(),getContextInfo());
            aoWrapper.setStateName(state.getName());
            TermInfo term = getAcalService().getTerm(activityOfferingInfo.getTermId(),getContextInfo());
            aoWrapper.setTermName(term.getName());
            FormatOfferingInfo format = getCourseOfferingService().getFormatOffering(activityOfferingInfo.getFormatOfferingId(),getContextInfo());
            aoWrapper.setFormatOfferingName(format.getName());
            for (OfferingInstructorInfo instructor : activityOfferingInfo.getInstructors()){
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructor);
                type = getTypeService().getType(activityOfferingInfo.getTypeKey(),getContextInfo());
                instructorWrapper.setTypeName(type.getName());
                aoWrapper.getInstructors().add(instructorWrapper);
            }
            return aoWrapper;
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }

    public TypeService getTypeService(){
       if (typeService == null){
           typeService = CourseOfferingResourceLoader.loadTypeService();
       }
        return typeService;
    }

    public StateService getStateService(){
        if (stateService == null){
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    public AcademicCalendarService getAcalService() {
           if(acalService == null) {
             acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }
}

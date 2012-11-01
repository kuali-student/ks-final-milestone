package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityOfferingWrapperInquirableImpl extends InquirableImpl {
    private static final long serialVersionUID = 1L;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient AcademicCalendarService acalService;
    private transient PopulationService populationService;

    @Override
    public ActivityOfferingWrapper retrieveDataObject(Map<String, String> parameters) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().getActivityOffering(parameters.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID), contextInfo);
            ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper(activityOfferingInfo);
            TypeInfo type = getTypeService().getType(activityOfferingInfo.getTypeKey(), contextInfo);
            aoWrapper.setTypeName(type.getName());
            StateInfo state = getStateService().getState(activityOfferingInfo.getStateKey(), contextInfo);
            aoWrapper.setStateName(state.getName());
            TermInfo term = getAcalService().getTerm(activityOfferingInfo.getTermId(), contextInfo);
            aoWrapper.setTermName(term.getName());
            FormatOfferingInfo format = getCourseOfferingService().getFormatOffering(activityOfferingInfo.getFormatOfferingId(), contextInfo);
            aoWrapper.setFormatOfferingName(format.getName());
            for (OfferingInstructorInfo instructor : activityOfferingInfo.getInstructors()){
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructor);
                type = getTypeService().getType(activityOfferingInfo.getTypeKey(), contextInfo);
                instructorWrapper.setTypeName(type.getName());
                aoWrapper.getInstructors().add(instructorWrapper);
            }

            // Display the Instructor Name with the Highest % of Effort, Jira 1736
            OfferingInstructorInfo offeringInstructorInfo = ViewHelperUtil.findDisplayInstructor(activityOfferingInfo.getInstructors());
            if (null != offeringInstructorInfo) {
                aoWrapper.setInstructorNameHighestPercentEffort(offeringInstructorInfo.getPersonName());
            }

            // Get/Set SeatPools
            List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList = getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(activityOfferingInfo.getId(), contextInfo);
            List<SeatPoolWrapper> seatPoolWrapperList = new ArrayList<SeatPoolWrapper>();

            for(SeatPoolDefinitionInfo seatPoolDefinitionInfo :  seatPoolDefinitionInfoList){
                SeatPoolWrapper spWrapper = new SeatPoolWrapper();

                PopulationInfo pInfo = getPopulationService().getPopulation(seatPoolDefinitionInfo.getPopulationId(), contextInfo);
                spWrapper.setSeatPoolPopulation(pInfo);
                spWrapper.setSeatPool(seatPoolDefinitionInfo);
                spWrapper.setId(seatPoolDefinitionInfo.getId());
                seatPoolWrapperList.add(spWrapper);
            }
            aoWrapper.setSeatpools(seatPoolWrapperList);

            return aoWrapper;
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
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

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return populationService;
    }
}

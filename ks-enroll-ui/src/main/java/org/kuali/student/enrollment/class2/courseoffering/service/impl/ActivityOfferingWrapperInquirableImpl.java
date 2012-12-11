package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.helper.ActivityOfferingScheduleHelperImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActivityOfferingWrapperInquirableImpl extends InquirableImpl {
    private static final long serialVersionUID = 1L;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient AcademicCalendarService acalService;
    private transient PopulationService populationService;

    @Override
    public ActivityOfferingWrapper retrieveDataObject(Map<String, String> dataObjectKeys) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingInfo info = getCourseOfferingService().getActivityOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID), contextInfo);
            ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(info);

            //get the course offering
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(info.getCourseOfferingId(), contextInfo);

            // get the format offering
            FormatOfferingInfo formatOfferingInfo = getCourseOfferingService().getFormatOffering(info.getFormatOfferingId(), contextInfo);
            wrapper.setFormatOffering(formatOfferingInfo);

            // Added for WaitList Tanveer 06/27/2012
            wrapper.setWaitListLevelTypeKey(courseOfferingInfo.getWaitlistLevelTypeKey());
            wrapper.setWaitListTypeKey(courseOfferingInfo.getWaitlistTypeKey());
            wrapper.setHasWaitList(courseOfferingInfo.getHasWaitlist());
            if (!wrapper.getHasWaitList())
                wrapper.setWaitListText("There is no wait list for this offering.");
            if (wrapper.getWaitListLevelTypeKey().equals("Course Offering")) {
                wrapper.setWaitListText("This waitlist is managed at the Course Offering level.");
                wrapper.setToolTipText("There is one waitlist for all Activity Offerings");
            }
            if (wrapper.getWaitListLevelTypeKey().equals("Activity Offering")) {
                wrapper.setWaitListText("This waitlist is managed at the Activity Offering level.");
                wrapper.setToolTipText("Each Activity Offering has its own wait list.");
            }

            // Set the display string (e.g. 'FALL 2020 (9/26/2020 to 12/26/2020)')
            TermInfo term = getAcalService().getTerm(info.getTermId(), contextInfo);
            if (term != null) {
                wrapper.setTermName(term.getName());
            }
            wrapper.setTermDisplayString(getTermDisplayString(info.getTermId(), term));

            wrapper.setCourseOfferingCode(info.getCourseOfferingCode());
            wrapper.setCourseOfferingTitle(info.getCourseOfferingTitle());

            String sCredits = courseOfferingInfo.getCreditCnt();
            if (sCredits == null) {
                sCredits = "0";
            }
            wrapper.setCredits(sCredits);
            //wrapper.setAbbreviatedActivityCode(info.getActivityCode().toUpperCase().substring(0,3));
            wrapper.setActivityCode(info.getActivityCode());
            wrapper.setAbbreviatedCourseType(getTypeService().getType(info.getTypeKey(), contextInfo).getName().toUpperCase().substring(0, 3));

            //process instructor effort
            assembleInstructorWrapper(info.getInstructors(), wrapper);


            boolean readOnlyView = Boolean.parseBoolean(dataObjectKeys.get("readOnlyView"));
            wrapper.setReadOnlyView(readOnlyView);


            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
            wrapper.setStateName(state.getName());
            TypeInfo typeInfo = getTypeService().getType(wrapper.getAoInfo().getTypeKey(), contextInfo);
            wrapper.setTypeName(typeInfo.getName());

            // Get/Set SeatPools
            List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList = getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(info.getId(), contextInfo);

            //Sort the seatpools by priority order
            Collections.sort(seatPoolDefinitionInfoList, new Comparator<SeatPoolDefinitionInfo>() {
                @Override
                public int compare(SeatPoolDefinitionInfo sp1, SeatPoolDefinitionInfo sp2) {
                    return sp1.getProcessingPriority().compareTo(sp2.getProcessingPriority());
                }
            });

            List<SeatPoolWrapper> seatPoolWrapperList = new ArrayList<SeatPoolWrapper>();

            for (SeatPoolDefinitionInfo seatPoolDefinitionInfo : seatPoolDefinitionInfoList) {
                SeatPoolWrapper spWrapper = new SeatPoolWrapper();

                PopulationInfo pInfo = getPopulationService().getPopulation(seatPoolDefinitionInfo.getPopulationId(), contextInfo);
                spWrapper.setSeatPoolPopulation(pInfo);
                spWrapper.setSeatPool(seatPoolDefinitionInfo);
                spWrapper.setId(seatPoolDefinitionInfo.getId());
                seatPoolWrapperList.add(spWrapper);
            }
            wrapper.setSeatpools(seatPoolWrapperList);

            getScheduleHelper().loadSchedules(wrapper);

            return wrapper;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getTermDisplayString(String termId, TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        String displayString = termId; // use termId as a default.
        if (term != null) {
            String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            String termType = term.getName();
            formatter.format("%s (%s to %s)", termType, startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }

    private void assembleInstructorWrapper(List<OfferingInstructorInfo> instructors, ActivityOfferingWrapper wrapper) {
        if (instructors != null && !instructors.isEmpty()) {
            for (OfferingInstructorInfo instructor : instructors) {
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructor);
                if (instructor.getPercentageEffort() != null) {
                    instructorWrapper.setsEffort(Integer.toString(instructor.getPercentageEffort().intValue()));
                }
                wrapper.getInstructors().add(instructorWrapper);
            }
        }
    }

    protected ActivityOfferingScheduleHelperImpl getScheduleHelper(){
//        return (ActivityOfferingScheduleHelperImpl)getHelper(SCHEDULE_HELPER);
        return new ActivityOfferingScheduleHelperImpl();
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

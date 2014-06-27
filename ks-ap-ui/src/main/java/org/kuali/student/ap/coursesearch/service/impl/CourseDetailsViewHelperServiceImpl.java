package org.kuali.student.ap.coursesearch.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.GroupBase;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.widget.Disclosure;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.coursesearch.dataobject.ActivityFormatDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseTermDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.FormatOfferingInfoWrapper;
import org.kuali.student.ap.coursesearch.dataobject.PlannedRegistrationGroupDetailsWrapper;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService;
import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class CourseDetailsViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseDetailsViewHelperService {
    public ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCourseSectionDetails(UifFormBase form, String courseId) throws Exception {
        load((CourseSectionDetailsForm) form, courseId);
    }

    private void load(CourseSectionDetailsForm form, String courseId) throws Exception {
        CourseInfo courseInfo = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
        form.setCourseTitle(courseInfo.getCourseTitle());
        form.setCourseCode(courseInfo.getCode());
        List<String> termIds = KsapFrameworkServiceLocator.getCourseHelper().getScheduledTermsForCourse(courseInfo);
        form.setCourseTermDetailsWrappers(getScheduledTerms(termIds, courseId));
    }

    private List<CourseTermDetailsWrapper> getScheduledTerms(List<String> scheduledTermsList, String courseId) throws Exception {

        List<CourseTermDetailsWrapper> courseTermDetailsList = new ArrayList<CourseTermDetailsWrapper>();

        //Return only the scheduled terms
        if (scheduledTermsList != null && scheduledTermsList.size() > 0) {

            List<TermInfo> scheduledTerms;
            try {
                scheduledTerms = KsapFrameworkServiceLocator.getAcademicCalendarService().getTermsByIds(scheduledTermsList, contextInfo);
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("ATP lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("ATP lookup error", e);
            }

            //sort scheduledTermsListIds
            List<Term> terms = new ArrayList<Term>(scheduledTerms);
            List<Term> scheduledTermsListSorted = sortTerms(terms);

            List<String> courseIds = new ArrayList<String>();
            courseIds.add(courseId);
            Map<String, List<CourseOfferingDetailsWrapper>> courseOfferingsByTerm = processCourseOfferingsByTerm(courseIds, terms);

            for (Term scheduledTermId : scheduledTermsListSorted) {

                CourseTermDetailsWrapper courseTerm = new CourseTermDetailsWrapper();
                courseTerm.setTermName(scheduledTermId.getName());
                courseTerm.setTermId(scheduledTermId.getId());
                courseTerm.setCourseOfferingDetailsWrappers(courseOfferingsByTerm.get(scheduledTermId.getId()));

                courseTermDetailsList.add(courseTerm);
            }
        }
        return courseTermDetailsList;
    }

    /**
     * {@inheritDoc}
     * This implementation is sorting by the date that the Soc was released/published
     */
    @Override
    public List<Term> sortTerms(List<Term> terms) {
        return KsapFrameworkServiceLocator.getTermHelper().sortTermsBySocReleaseDate(terms, false);
    }

    /**
     * Comparator implementation so that I can sort CourseOfferingInfo objects by the course offering code
     */
    public class CourseOfferingInfoComparator implements Comparator<CourseOfferingInfo> {

        @Override
        public int compare(CourseOfferingInfo o1, CourseOfferingInfo o2) {
            return o1.getCourseOfferingCode().compareTo(o2.getCourseOfferingCode());
        }
    }

    /**
     * Comparator implementation so that I can sort ActivityOfferingInfo objects by the activity code
     */
    public class ActivityOfferingInfoComparator implements Comparator<ActivityOfferingInfo> {

        @Override
        public int compare(ActivityOfferingInfo o1, ActivityOfferingInfo o2) {
            return o1.getActivityCode().compareTo(o2.getActivityCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<CourseOfferingDetailsWrapper>> processCourseOfferingsByTerm(List<String> courseIds, List<Term> terms) throws Exception {
        List<CourseOfferingInfo> courseOfferings = KsapFrameworkServiceLocator.getCourseHelper().getCourseOfferingsForCoursesAndTerms(courseIds, terms);
        Collections.sort(courseOfferings, new CourseOfferingInfoComparator());
        Map<String, List<CourseOfferingDetailsWrapper>> map = new HashMap<String, List<CourseOfferingDetailsWrapper>>();

        for (CourseOfferingInfo offering : courseOfferings) {
            String termId = offering.getTermId();
            List<CourseOfferingDetailsWrapper> offeringsByTerm = map.get(termId);
            if (offeringsByTerm == null)
                offeringsByTerm = new ArrayList<CourseOfferingDetailsWrapper>();

            CourseOfferingDetailsWrapper courseOfferingDetailsWrapper = new CourseOfferingDetailsWrapper(offering);
            List<FormatOfferingInfo> formatOfferings = null;
            try {
                formatOfferings = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOfferingsByCourseOffering(offering.getId(), contextInfo);
                List<FormatOfferingInfoWrapper> formatOfferingWrappers = new ArrayList<FormatOfferingInfoWrapper>(formatOfferings.size());
                Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>>
                        aosByFormat = getAOData(offering.getId());

                List<PlannedRegistrationGroupDetailsWrapper> plannedActivityOfferings = new ArrayList<PlannedRegistrationGroupDetailsWrapper>();

                for (FormatOfferingInfo formatOffering : formatOfferings) {
                    FormatOfferingInfoWrapper formatOfferingInfo = new FormatOfferingInfoWrapper(formatOffering);

                    List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers = new ArrayList<ActivityFormatDetailsWrapper>();
                    Map<String, List<ActivityOfferingDetailsWrapper>> aosByTypeMap = aosByFormat.get(formatOfferingInfo.getFormatOfferingId());

                    if (aosByTypeMap != null) {
                        for (Map.Entry<String, List<ActivityOfferingDetailsWrapper>> aosByType : aosByTypeMap.entrySet()) {
                            //TypeService is cached, so this should be safe to have inside the loop here
                            TypeInfo typeInfo = KsapFrameworkServiceLocator.getTypeService().getType(aosByType.getKey(), contextInfo);
                            ActivityFormatDetailsWrapper activityFormatDetailsWrapper = new ActivityFormatDetailsWrapper(
                                    termId, offering.getCourseOfferingCode(), formatOfferingInfo.getFormatOfferingId(), typeInfo.getName(), typeInfo.getKey());
                            activityFormatDetailsWrapper.setActivityOfferingDetailsWrappers(aosByType.getValue());
                            activityFormatDetailsWrappers.add(activityFormatDetailsWrapper);

                            plannedActivityOfferings.addAll(getPlannedPlannedRegistrationGroups(aosByType.getValue()));

                        }
                    }


//                    formatOfferingInfo.setActivityOfferingDetailsWrappers(aosByFormat.get(formatOffering.getFormatId()));
                    formatOfferingInfo.setActivityFormatDetailsWrappers(activityFormatDetailsWrappers);
                    formatOfferingWrappers.add(formatOfferingInfo);

                }
                courseOfferingDetailsWrapper.setFormatOfferingInfoWrappers(formatOfferingWrappers);

//                List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers = new ArrayList<ActivityFormatDetailsWrapper>();
//
//
//                for (Map.Entry<String, List<ActivityOfferingDetailsWrapper>> entry : aosByFormat.entrySet()) {
//                    ActivityFormatDetailsWrapper activityFormatDetailsWrapper = new ActivityFormatDetailsWrapper(
//                            entry.getKey());
//                    activityFormatDetailsWrapper.setActivityOfferingDetailsWrappers(entry.getValue());
//                    activityFormatDetailsWrappers.add(activityFormatDetailsWrapper);
//                }
//                courseOfferingDetailsWrapper.setActivityFormatDetailsWrappers(activityFormatDetailsWrappers);

                courseOfferingDetailsWrapper.setPlannedActivityDetailsWrappers(plannedActivityOfferings);
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            }

            offeringsByTerm.add(courseOfferingDetailsWrapper);
            map.put(termId, offeringsByTerm);
        }

        return map;
    }

    private List<PlannedRegistrationGroupDetailsWrapper> getPlannedPlannedRegistrationGroups(
            List<ActivityOfferingDetailsWrapper> activities) throws Exception{

        List<ActivityOfferingDetailsWrapper> activityOfferings = new ArrayList<ActivityOfferingDetailsWrapper>();
        for(ActivityOfferingDetailsWrapper activityOfferingDetailsWrapper : activities){
            if(activityOfferingDetailsWrapper.isInPlan()){
                activityOfferings.add(activityOfferingDetailsWrapper);
            }
        }

        List<PlannedRegistrationGroupDetailsWrapper> plannedRegistrationGroupDetailsWrappers = new ArrayList<PlannedRegistrationGroupDetailsWrapper>();
        for(ActivityOfferingDetailsWrapper activityFormatDetailsWrapper : activityOfferings){
            boolean found = false;
            for(PlannedRegistrationGroupDetailsWrapper plannedRegistrationGroupDetailsWrapper : plannedRegistrationGroupDetailsWrappers){
                if(plannedRegistrationGroupDetailsWrapper.getRegGroupCode().equals(activityFormatDetailsWrapper.getRegGroupCode())){
                    found = true;
                    plannedRegistrationGroupDetailsWrapper.addActivities(activityFormatDetailsWrapper);
                }
            }
            if(!found){
                PlannedRegistrationGroupDetailsWrapper newPlanReg = new PlannedRegistrationGroupDetailsWrapper();
                newPlanReg.setRegGroupCode(activityFormatDetailsWrapper.getRegGroupCode());
                newPlanReg.addActivities(activityFormatDetailsWrapper);
                plannedRegistrationGroupDetailsWrappers.add(newPlanReg);
            }
        }

        return plannedRegistrationGroupDetailsWrappers;
    }

    private Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>> getAOData(String courseOfferingId) throws Exception {
        Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>> aoMapByFormatName = new HashMap<String, Map<String, List<ActivityOfferingDetailsWrapper>>>();
        List<ActivityOfferingInfo>  activityOfferings = null;

        try {
            activityOfferings = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, contextInfo);
            Collections.sort(activityOfferings, new ActivityOfferingInfoComparator());

        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        }

        for (ActivityOfferingInfo activityOffering : activityOfferings) {
            Map<String, List<ActivityOfferingDetailsWrapper>> aosByFormat = aoMapByFormatName.get(activityOffering.getFormatOfferingId());
            if (aosByFormat == null) {
                aosByFormat = new HashMap<String, List<ActivityOfferingDetailsWrapper>>();
            }
            ActivityOfferingDetailsWrapper wrapper = convertAOInfoToWrapper(activityOffering);
            String typeKey = activityOffering.getTypeKey();
            List<ActivityOfferingDetailsWrapper> aosByType = aosByFormat.get(typeKey);
            if (aosByType == null) {
                aosByType = new ArrayList<ActivityOfferingDetailsWrapper>();
            }
            aosByType.add(wrapper);
            aosByFormat.put(typeKey, aosByType);
            aoMapByFormatName.put(activityOffering.getFormatOfferingId(), aosByFormat);
        }
        return aoMapByFormatName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityOfferingDetailsWrapper convertAOInfoToWrapper(ActivityOfferingInfo aoInfo) throws Exception {
        ActivityOfferingDetailsWrapper wrapper = new ActivityOfferingDetailsWrapper(aoInfo, false, true);

        int firstValue = 0;

        FormatOfferingInfo fo = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), contextInfo);
        wrapper.setActivityFormatName(aoInfo.getName());
        if (fo.getActivityOfferingTypeKeys().size()>1) {
            wrapper.setSingleFormatOffering(false);
        }else{
            List<RegistrationGroupInfo> regGroups = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroupsByActivityOffering(aoInfo.getId(),KsapFrameworkServiceLocator.getContext().getContextInfo());
            RegistrationGroupInfo regGroup;
            try{
                regGroup = KSCollectionUtils.getRequiredZeroElement(regGroups);
                wrapper.setRegGroupCode(regGroup.getName());
                wrapper.setRegGroupId(regGroup.getId());
            }catch(OperationFailedException e){
                throw new IllegalArgumentException("Multiple Registration Groups Found for Single Format Activity Offering",e);
            }
        }

        //From Bonnie: we need to better understand firstInstructor vs.multiple instructors cases -- pull in the logic from manage CO
        OfferingInstructorInfo displayInstructor = findDisplayInstructor(aoInfo.getInstructors());

        if (displayInstructor != null) {
            wrapper.setFirstInstructorDisplayName(displayInstructor.getPersonName());
            wrapper.setInstructorName(displayInstructor.getPersonName());
        }

        //for multiple instructor display
        List<OfferingInstructorInfo> instructorInfos = aoInfo.getInstructors();
        if (instructorInfos != null) {
            for (OfferingInstructorInfo offeringInstructorInfo : instructorInfos) {
                wrapper.setInstructorDisplayNames(offeringInstructorInfo.getPersonName(), true);
            }
        }

        //This section is to display either schedule actuals assume that when an AO is offered, actuals are always available
        if (aoInfo.getScheduleIds() != null && aoInfo.getScheduleIds().size() > 0) {
            //FIXME: Use display object once we get the TBA with ScheduleComponentDisplay
            List<ScheduleInfo> scheduleInfoList = KsapFrameworkServiceLocator.getSchedulingService().getSchedulesByIds(aoInfo.getScheduleIds(), contextInfo);

            if (!scheduleInfoList.isEmpty()) {
                for (ScheduleInfo scheduleInfo : scheduleInfoList) {
                    if (!scheduleInfo.getScheduleComponents().isEmpty()) {

                        for (ScheduleComponentInfo scheduleComponentInfo : scheduleInfo.getScheduleComponents()) {

                            String roomId = scheduleComponentInfo.getRoomId();
                            // JIRA Fix : KSENROLL-8726. Added isEmpty check
                            TimeSlotInfo timeSlotInfo = KsapFrameworkServiceLocator.getSchedulingService().getTimeSlot(scheduleComponentInfo.getTimeSlotIds().isEmpty() ? StringUtils.EMPTY : scheduleComponentInfo.getTimeSlotIds().get(firstValue), contextInfo);

                            updateScheduleToAOWrapperForDisplay(wrapper, scheduleComponentInfo.getIsTBA(), roomId, timeSlotInfo);

                        }

                    }
                }
            }

        }
        wrapper.setClassUrl(aoInfo.getActivityOfferingURL());

        List<String> aoRequisites = CourseDetailsUtil.getActivityOfferingRequisites(aoInfo);


        if (aoRequisites.size()>0)
            wrapper.setRequirementsUrl("kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView" +
                    "&methodToCall=show&term_code=" +aoInfo.getTermCode()+"&course="+aoInfo.getCourseOfferingCode());

        wrapper.setInPlan(false);
        List<RegistrationGroupInfo> regGroups = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroupsByActivityOffering(wrapper.getActivityOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        String planId = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan().getId();
        if(regGroups!=null){
            for(RegistrationGroupInfo regGroup : regGroups){
                List<PlanItemInfo> items = KsapFrameworkServiceLocator.getAcademicPlanService()
                        .getPlanItemsInPlanByRefObjectIdByRefObjectType(planId, regGroup.getId(),
                                PlanConstants.REG_GROUP_TYPE,KsapFrameworkServiceLocator.getContext().getContextInfo());
                if(!items.isEmpty()){
                    wrapper.setInPlan(true);
                    wrapper.setRegGroupCode(regGroup.getName());
                    wrapper.setRegGroupId(regGroup.getId());
                }
            }
        }
        return wrapper;

    }

    private OfferingInstructorInfo findDisplayInstructor(List<OfferingInstructorInfo> instructors) {
        OfferingInstructorInfo result = null;

        if (instructors != null && !instructors.isEmpty()) {

            // Build the display name for the Instructor
            Collection<OfferingInstructorInfo> highestInstEffortInstructors = new ArrayList<OfferingInstructorInfo>();
            float highestInstEffortComparison = 0f;

            for (OfferingInstructorInfo instructor : instructors) {
                if (instructor.getPercentageEffort() != null) {
                    // if this instructor has a higher percent effort than any previous instructors,
                    // clear the list we are keeping track of and set the new comparison number to this instructor's percentage effort
                    if (instructor.getPercentageEffort() > highestInstEffortComparison) {
                        highestInstEffortInstructors.clear();
                        highestInstEffortComparison = instructor.getPercentageEffort();
                        highestInstEffortInstructors.add(instructor);
                    }
                    // if this instructor's percent effort is tied with the comparison number,
                    // add this instructor to the list of highest effort instructors
                    else if (instructor.getPercentageEffort() == highestInstEffortComparison) {
                        highestInstEffortInstructors.add(instructor);
                    }
                }
            }

            if (highestInstEffortInstructors.size() == 1) {
                result = highestInstEffortInstructors.iterator().next();
            } else {
                List<String> names = new ArrayList<String>(highestInstEffortInstructors.size());
                Map<String, OfferingInstructorInfo> nameMap = new HashMap<String, OfferingInstructorInfo>(highestInstEffortInstructors.size());
                for (OfferingInstructorInfo oiInfo : highestInstEffortInstructors) {
                    names.add(oiInfo.getPersonName());
                    nameMap.put(oiInfo.getPersonName(), oiInfo);
                }

                Collections.sort(names);
                int firstName = 0;
                result = nameMap.get(names.get(firstName));
            }
        }

        return result;
    }

    private void updateScheduleToAOWrapperForDisplay(ActivityOfferingDetailsWrapper aoWrapper, Boolean isTBA, String roomId, TimeSlotInfo timeSlot) throws Exception {
        RoomInfo roomInfo = null;
        if (StringUtils.isNotBlank(roomId)) {
            roomInfo = KsapFrameworkServiceLocator.getRoomService().getRoom(roomId, ContextUtils.createDefaultContextInfo());
        }
        updateScheduleToAOWrapperForDisplay(aoWrapper, isTBA, roomInfo, timeSlot);
    }

    private void updateScheduleToAOWrapperForDisplay(ActivityOfferingDetailsWrapper aoWrapper, Boolean isTBA, RoomInfo roomInfo, TimeSlotInfo timeSlot) throws Exception {

//        aoWrapper.setTbaDisplayName(isTBA,append);

        if (timeSlot != null) {

            TimeOfDayInfo startTime = timeSlot.getStartTime();
            TimeOfDayInfo endTime = timeSlot.getEndTime();
            List<Integer> days = timeSlot.getWeekdays();

            if ((startTime != null && startTime.getHour() != null) && (endTime != null && endTime.getHour() != null)) {
                aoWrapper.setTime(TimeOfDayHelper.makeFormattedTimeForAOSchedules(startTime) + " - " + TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime));
            }

//            if (endTime != null && endTime.getHour() != null) {
//                aoWrapper.setEndTimeDisplay(TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime), append);
//            }
//
            if (days != null && days.size() > 0) {
                aoWrapper.setDays(getDays(days));
            }
        }

        if (roomInfo != null && StringUtils.isNotBlank(roomInfo.getBuildingId())) {
            BuildingInfo buildingInfo = KsapFrameworkServiceLocator.getRoomService().getBuilding(roomInfo.getBuildingId(), ContextUtils.createDefaultContextInfo());
            aoWrapper.setLocation(buildingInfo.getBuildingCode() + " " + roomInfo.getRoomCode());
//            aoWrapper.setBuildingName(buildingInfo.getName(),append);
//            aoWrapper.setRoomName(roomInfo.getRoomCode(),append);
        }
    }

    // should go to common util?
    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if (intList == null) return sb.toString();

        for (Integer d : intList) {
            sb.append(convertIntoDays(d));
        }
        return sb.toString();
    }

    // should go to common util?
    private String convertIntoDays(int day) {
        String dayOfWeek;
        switch (day) {
            case 1:
                dayOfWeek = SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE;
                break;
            case 2:
                dayOfWeek = SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE;
                break;
            case 3:
                dayOfWeek = SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE;
                break;
            case 4:
                dayOfWeek = SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE;
                break;
            case 5:
                dayOfWeek = SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE;
                break;
            case 6:
                dayOfWeek = SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE;
                break;
            case 7:
                dayOfWeek = SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE;
                break;
            default:
                dayOfWeek = StringUtils.EMPTY;
        }
        // TODO implement TBA when service stores it.
        return dayOfWeek;
    }

    /**
     * This is the finalizeMethodToCall which builds the disclosure widgets.
     * This is really a workaround until KULRICE-9003 gets addressed, allowing me to use #parentLine in the
     * layoutManager.lineGroupPrototype.disclosure properties.
     * @param disclosure The disclosure component that we are working in
     * @param model The form backing object
     */
    public void determineDisclosureRendering(Disclosure disclosure, Object model) {
        CourseOfferingDetailsWrapper courseOfferingDetailsWrapper = (CourseOfferingDetailsWrapper)disclosure.getContext().get(UifConstants.ContextVariableNames.LINE);
        GroupBase parentGroup = (GroupBase)disclosure.getContext().get(UifConstants.ContextVariableNames.PARENT);
        GroupBase grandparent = (GroupBase)parentGroup.getContext().get(UifConstants.ContextVariableNames.PARENT);
        CourseTermDetailsWrapper courseTermDetailsWrapper = (CourseTermDetailsWrapper)grandparent.getContext().get(UifConstants.ContextVariableNames.LINE);

        int size = courseTermDetailsWrapper.getCourseOfferingDetailsWrappers().size();

//        disclosure.setRender(true);
        if (size <= 1)
            disclosure.setRender(false);

        // Set the id based off of the term id and course offering code
        disclosure.setId(courseTermDetailsWrapper.getTermId() + "_" + courseOfferingDetailsWrapper.getCourseOfferingCode());
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService
     */
    @Override
    public JsonObjectBuilder createAddSectionEvent(String courseOfferingId, ActivityOfferingDetailsWrapper activity, JsonObjectBuilder eventList){
        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        String instructor = "";
        String days = "";
        String time = "";
        String location = "";
        String classUrl = "";
        String requirementsUrl = "";

        if(activity.getInstructorName()!=null) instructor = activity.getInstructorName();
        if(activity.getDays()!=null) days = activity.getDays();
        if(activity.getTime()!=null) time = activity.getTime();
        if(activity.getLocation()!=null) location = activity.getLocation();
        if(activity.getRequirementsUrl()!=null) requirementsUrl = activity.getRequirementsUrl();
        if(activity.getClassUrl()!=null) classUrl = activity.getClassUrl();

        addEvent.add("activityOfferingId", activity.getActivityOfferingId());
        addEvent.add("activityFormatName", activity.getActivityFormatName());
        addEvent.add("activityOfferingCode", activity.getActivityOfferingCode());
        addEvent.add("regGroupCode", activity.getRegGroupCode());
        addEvent.add("instructor", instructor);
        addEvent.add("days",days);
        addEvent.add("time", time);
        addEvent.add("location", location);
        addEvent.add("currentEnrollment", activity.getCurrentEnrollment());
        addEvent.add("maxEnrollment", activity.getMaxEnrollment());
        addEvent.add("honors", activity.isHonors());
        addEvent.add("classUrl", classUrl);
        addEvent.add("requirementsUrl", requirementsUrl);
        addEvent.add("courseOfferingId", courseOfferingId);
        addEvent.add("uid", UUID.randomUUID().toString());

        eventList.add("COURSE_SECTION_ADDED", addEvent);
        return eventList;
    }
}

package org.kuali.student.ap.coursesearch.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseTermDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.FormatOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.PlannedRegGroupDetailsWrapper;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/9/14
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseDetailsViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseDetailsViewHelperService {
    public ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();

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
            List<Term> scheduledTermsListSorted = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(terms, true);

            Integer displayLimit = Integer.valueOf(ConfigContext.getCurrentContextConfig().getProperty("ks.ap.search.terms.scheduled.limit"));

            //list greater than displayLimit, truncate
            if (scheduledTermsListSorted.size() > displayLimit)
                scheduledTermsListSorted = scheduledTermsListSorted.subList(0, displayLimit);

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

    public Map<String, List<CourseOfferingDetailsWrapper>> processCourseOfferingsByTerm(List<String> courseIds, List<Term> terms) throws Exception {
        List<CourseOfferingInfo> courseOfferings = KsapFrameworkServiceLocator.getCourseHelper().getCourseOfferingsForCoursesAndTerms(courseIds, terms);
        Map<String, List<CourseOfferingDetailsWrapper>> map = new HashMap<String, List<CourseOfferingDetailsWrapper>>();

        for (CourseOfferingInfo offering : courseOfferings) {
            String termId = offering.getTermId();
            List<CourseOfferingDetailsWrapper> offeringsByTerm = map.get(termId);
            if (offeringsByTerm == null)
                offeringsByTerm = new ArrayList<CourseOfferingDetailsWrapper>();

            CourseOfferingDetailsWrapper courseOfferingDetailsWrapper = new CourseOfferingDetailsWrapper(offering);
            try {
                List<FormatOfferingInfo> formatOfferingList = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingDetailsWrapper.getCourseOfferingId(), contextInfo);
                List<FormatOfferingDetailsWrapper> formatOfferingDetailsWrappers = new ArrayList<FormatOfferingDetailsWrapper>();

                Map<String, List<ActivityOfferingDetailsWrapper>> aosByFormat = getAOData(offering.getId());

                for (FormatOfferingInfo formatOffering : formatOfferingList) {
                    FormatOfferingDetailsWrapper formatOfferingDetailsWrapper = new FormatOfferingDetailsWrapper(formatOffering);

                    formatOfferingDetailsWrapper.setActivityOfferingDetailsWrappers(aosByFormat.get(formatOffering.getId()));

                    formatOfferingDetailsWrappers.add(formatOfferingDetailsWrapper);
                }
                courseOfferingDetailsWrapper.setFormatOfferingDetailsWrappers(formatOfferingDetailsWrappers);
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

            courseOfferingDetailsWrapper.setPlannedRegGroupDetailsWrappers(
                    getPlannedRegGroupDetailsByTermAndCO(termId, offering.getCourseCode()));
            offeringsByTerm.add(courseOfferingDetailsWrapper);
            map.put(termId, offeringsByTerm);
        }

        return map;
    }

    private List<PlannedRegGroupDetailsWrapper> getPlannedRegGroupDetailsByTermAndCO(String termId, String courseCode) {

        //1. get planned reg-group items  (for student (via. context), and/or planId? ...will need to decide)
        //        List<PlanItemInfo> plannedTermItems = KsapFrameworkServiceLocator.getAcademicPlanService()
        //                .getPlanItemsInPlanByTermIdByCategory
        //                        (planId,termId,
        //                                AcademicPlanServiceConstants.ItemCategory.PLANNED,KsapFrameworkServiceLocator.getContext());
        //2. lookup AO for reg-groups (...using CourseCode to restrict)

        //Fake data for now
        List<PlannedRegGroupDetailsWrapper> regGroups = new ArrayList<PlannedRegGroupDetailsWrapper>();
        PlannedRegGroupDetailsWrapper regGroup = new PlannedRegGroupDetailsWrapper();

        regGroup.setRegGroupCode("FD1-ForDUMMIES");

        List<ActivityOfferingDetailsWrapper> activityOfferings = new ArrayList<ActivityOfferingDetailsWrapper>();

        regGroup.setActivityOfferingDetailsWrappers(activityOfferings);

        ActivityOfferingDetailsWrapper activityOffering = new ActivityOfferingDetailsWrapper();
        activityOffering.setPartOfRegGroup(true);
        activityOffering.setActivityFormatType("Lecture");
        activityOffering.setInstructorName("Neal, Jerry");
        activityOffering.setActivityOfferingCode("KRAD101Y");
        activityOffering.setDays("MF");
        activityOffering.setTime("09:00-09:50 AM");
        activityOffering.setLocation("UITS Rec Studio1");
        activityOffering.setCurrentEnrollment(1);
        activityOffering.setMaxEnrollment(17);
        activityOffering.setHonors(true);
        activityOffering.setClassUrl("http://krad.rice.kuali.org/kr-krad/kradsampleapp?viewId=ComponentLibraryHome");
        activityOffering.setRequirementsUrl("http://site.kuali.org/rice/2.4.0/reference/html/KRAD_Guide.html#d10268e992");
        activityOffering.setRegGroupCode(regGroup.getRegGroupCode());
        activityOfferings.add(activityOffering);

        activityOffering = new ActivityOfferingDetailsWrapper();
        activityOffering.setPartOfRegGroup(true);
        activityOffering.setActivityFormatType("Lab");
        activityOffering.setInstructorName("Westfall, Eric");
        activityOffering.setActivityOfferingCode("KRAD101Z");
        activityOffering.setDays("TW");
        activityOffering.setLocation("UITS Rec Studio2");
        activityOffering.setCurrentEnrollment(1);
        activityOffering.setMaxEnrollment(17);
        activityOffering.setClassUrl("http://krad.rice.kuali.org/kr-krad/kradsampleapp?viewId=ComponentLibraryHome");
        activityOffering.setRequirementsUrl("http://site.kuali.org/rice/2.4.0/reference/html/KRAD_Guide.html#d10268e992");
        activityOffering.setRegGroupCode(regGroup.getRegGroupCode());
        activityOfferings.add(activityOffering);
        regGroups.add(regGroup);

        //Adding second reg group
        regGroup = new PlannedRegGroupDetailsWrapper();
        regGroup.setRegGroupCode("FD1-ForSMARTIES");

        activityOfferings = new ArrayList<ActivityOfferingDetailsWrapper>();

        regGroup.setActivityOfferingDetailsWrappers(activityOfferings);


        activityOffering = new ActivityOfferingDetailsWrapper();
        activityOffering.setPartOfRegGroup(true);
        activityOffering.setActivityFormatType("Lecture");
        activityOffering.setInstructorName("Neal, Jerry");
        activityOffering.setActivityOfferingCode("KRAD101Y");
        activityOffering.setDays("MF");
        activityOffering.setTime("09:00-09:50 AM");
        activityOffering.setLocation("UITS Rec Studio1");
        activityOffering.setCurrentEnrollment(1);
        activityOffering.setMaxEnrollment(17);
        activityOffering.setHonors(true);
        activityOffering.setClassUrl("http://krad.rice.kuali.org/kr-krad/kradsampleapp?viewId=ComponentLibraryHome");
        activityOffering.setRequirementsUrl("http://site.kuali.org/rice/2.4.0/reference/html/KRAD_Guide.html#d10268e992");
        activityOffering.setRegGroupCode(regGroup.getRegGroupCode());
        activityOfferings.add(activityOffering);

        activityOffering = new ActivityOfferingDetailsWrapper();
        activityOffering.setPartOfRegGroup(true);
        activityOffering.setActivityFormatType("Lab");
        activityOffering.setInstructorName("Westfall, Eric");
        activityOffering.setActivityOfferingCode("KRAD101Z");
        activityOffering.setDays("TW");
        activityOffering.setLocation("UITS Rec Studio2");
        activityOffering.setCurrentEnrollment(1);
        activityOffering.setMaxEnrollment(17);
        activityOffering.setClassUrl("http://krad.rice.kuali.org/kr-krad/kradsampleapp?viewId=ComponentLibraryHome");
        activityOffering.setRequirementsUrl("http://site.kuali.org/rice/2.4.0/reference/html/KRAD_Guide.html#d10268e992");
        activityOffering.setRegGroupCode(regGroup.getRegGroupCode());
        activityOfferings.add(activityOffering);


        regGroups.add(regGroup);
        return regGroups;
    }

    private Map<String, List<ActivityOfferingDetailsWrapper>> getAOData(String courseOfferingId) throws Exception {
        Map<String, List<ActivityOfferingDetailsWrapper>> aoMapByFormatOffering = new HashMap<String, List<ActivityOfferingDetailsWrapper>>();
        List<ActivityOfferingInfo> activityOfferings = null;
        try {
            activityOfferings = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, contextInfo);

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
            String formatOfferingId = activityOffering.getFormatOfferingId();
            List<ActivityOfferingDetailsWrapper> aosByFormat = aoMapByFormatOffering.get(formatOfferingId);
            if (aosByFormat == null) {
                aosByFormat = new ArrayList<ActivityOfferingDetailsWrapper>();
            }
            ActivityOfferingDetailsWrapper wrapper = convertAOInfoToWrapper(activityOffering);
            aosByFormat.add(wrapper);
            aoMapByFormatOffering.put(formatOfferingId, aosByFormat);
        }
        return aoMapByFormatOffering;
    }

    public ActivityOfferingDetailsWrapper convertAOInfoToWrapper(ActivityOfferingInfo aoInfo) throws Exception {
        ActivityOfferingDetailsWrapper wrapper = new ActivityOfferingDetailsWrapper(aoInfo, false);

        int firstValue = 0;

        FormatOfferingInfo fo = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), contextInfo);
        wrapper.setActivityFormatType(fo.getTypeKey());

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
            aoWrapper.setLocation(buildingInfo.getName() + " " + roomInfo.getRoomCode());
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

}

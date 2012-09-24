/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by vgadiyak on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.KeyNameInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesViewHelperServiceImpl extends ViewHelperServiceImpl implements ScheduleOfClassesViewHelperService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesViewHelperServiceImpl.class);

    private CourseOfferingService coService;
    private LprService lprService;
    private SchedulingService schedulingService;
    private RoomService roomService;

    public void loadCourseOfferingsByTermAndCourseCode (String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception{

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                PredicateFactory.equalIgnoreCase("atpId", termId)));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayWrapperList().clear();
            List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
            List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
            for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                // Adding Information (icons)
                String information = "";
                if (coDisplayInfo.getIsHonorsOffering() != null && coDisplayInfo.getIsHonorsOffering()) {
                    information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                }
                if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_PERCENT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PERCENT + "\"> ";
                }
                if (!coDisplayInfo.getStudentRegistrationGradingOptions().isEmpty()) {
                    for (KeyNameInfo stuRegOption : coDisplayInfo.getStudentRegistrationGradingOptions()) {
                        if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_PASSFAIL_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_PASSFAIL + "\">";
                        } else if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_AUDIT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_AUDIT + "\">";
                        }
                    }
                }
                coDisplayWrapper.setInformation(information);

                coDisplayWrapperList.add(coDisplayWrapper);
            }
            form.setCoDisplayWrapperList(coDisplayWrapperList);
        } else {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & courseCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "courseCode", courseCode, termId);
            form.getCoDisplayWrapperList().clear();
        }
    }

    @Override
    public void loadCourseOfferingsByTermAndInstructor(String termId, String instructorId, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //this is a cross service search between LPR and LUI, so it is inefficient (no join)
        //First get all the luiIds that the instructor is teaching
        //TODO TENTATIVE_STATE_KEY should be active in the code below, but it is hardcoded as such
        List<String> luiIds = getLprService().getLuiIdsByPersonAndTypeAndState(instructorId, LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, LprServiceConstants.TENTATIVE_STATE_KEY, contextInfo);

        List<String> courseOfferingIds = null;

        if(luiIds != null && !luiIds.isEmpty()){
            //Now find all the COs with Aos that are attached to that instructor.

            // Build a query
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.in("aoid", luiIds.toArray()),
                    PredicateFactory.equalIgnoreCase("atpId", termId)));
            QueryByCriteria criteria = qbcBuilder.build();
            courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

            if(courseOfferingIds.size() > 0){
                form.getCoDisplayWrapperList().clear();
                List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
                List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
                for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                    CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                    coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                    // Adding Information (icons)
                    String information = "";
                    if (coDisplayInfo.getIsHonorsOffering() != null && coDisplayInfo.getIsHonorsOffering()) {
                        information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                    }
                    if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                        information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                    } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
                        information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_PERCENT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PERCENT + "\"> ";
                    }
                    if (!coDisplayInfo.getStudentRegistrationGradingOptions().isEmpty()) {
                        for (KeyNameInfo stuRegOption : coDisplayInfo.getStudentRegistrationGradingOptions()) {
                            if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                                information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_PASSFAIL_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_PASSFAIL + "\">";
                            } else if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                                information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_AUDIT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_AUDIT + "\">";
                            }
                        }
                    }
                    coDisplayWrapper.setInformation(information);

                    coDisplayWrapperList.add(coDisplayWrapper);
                }
                form.setCoDisplayWrapperList(coDisplayWrapperList);
            }
        }

        //If nothing was found then error
        if(courseOfferingIds == null || courseOfferingIds.isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for selected Instructor in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorId, termId);
            form.getCoDisplayWrapperList().clear();
        }
    }

    public void loadCourseOfferingsByOrganizationId(String termId, String organizationId, ScheduleOfClassesSearchForm form) throws Exception{
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("luiContentOwner", organizationId),
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayWrapperList().clear();
            List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
            List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
            for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                // Adding Information (icons)
                String information = "";
                if (coDisplayInfo.getIsHonorsOffering() != null && coDisplayInfo.getIsHonorsOffering()) {
                    information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                }
                if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_PERCENT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PERCENT + "\"> ";
                }
                if (!coDisplayInfo.getStudentRegistrationGradingOptions().isEmpty()) {
                    for (KeyNameInfo stuRegOption : coDisplayInfo.getStudentRegistrationGradingOptions()) {
                        if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_PASSFAIL_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_PASSFAIL + "\">";
                        } else if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_AUDIT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_AUDIT + "\">";
                        }
                    }
                }
                coDisplayWrapper.setInformation(information);

                coDisplayWrapperList.add(coDisplayWrapper);
            }
            form.setCoDisplayWrapperList(coDisplayWrapperList);
        }

        //If nothing was found then error
        if(courseOfferingIds == null || courseOfferingIds.isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for selected Department in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationId, termId);
            form.getCoDisplayWrapperList().clear();
        }
    }

    public void loadActivityOfferingsByCourseOfferingId(String courseOfferingId, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        Calendar calendar = new GregorianCalendar();

        List<ActivityOfferingDisplayWrapper> aoDisplayWrapperList = new ArrayList<ActivityOfferingDisplayWrapper>();
        List<ActivityOfferingDisplayInfo> aoDisplayInfoList = getCourseOfferingService().getActivityOfferingDisplaysForCourseOffering(courseOfferingId, contextInfo);

        for (ActivityOfferingDisplayInfo aoDisplayInfo : aoDisplayInfoList) {
            ActivityOfferingDisplayWrapper aoDisplayWrapper = new ActivityOfferingDisplayWrapper();
            aoDisplayWrapper.setAoDisplayInfo(aoDisplayInfo);

            // Adding Information (icons)
            String information = "";
            if (aoDisplayInfo.getIsHonorsOffering() != null && aoDisplayInfo.getIsHonorsOffering()) {
                information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY + "\"> ";
            }
            aoDisplayWrapper.setInformation(information);

            // assign the time and days
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            List<ScheduleRequestInfo> scheduleRequestInfoList = getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, aoDisplayInfo.getId(), contextInfo);
            if (scheduleRequestInfoList != null && scheduleRequestInfoList.size() > 0) {
                ScheduleRequestInfo scheduleRequestInfo = scheduleRequestInfoList.get(0);
                List<ScheduleRequestComponentInfo> componentList = scheduleRequestInfo.getScheduleRequestComponents();
                if (componentList != null && componentList.size() > 0) {
                    if(componentList.get(0).getIsTBA() != null) {
                        aoDisplayWrapper.setTbaDisplayName(componentList.get(0).getIsTBA());
                    }
                    List<String> ids = componentList.get(0).getTimeSlotIds();
                    if (ids != null && ids.size() > 0) {
                        TimeSlotInfo timeSlot = getSchedulingService().getTimeSlot(ids.get(0), contextInfo);
                        if (timeSlot != null) {
                            TimeOfDayInfo startTime = timeSlot.getStartTime();
                            TimeOfDayInfo endTime = timeSlot.getEndTime();
                            List<Integer> days = timeSlot.getWeekdays();

                            if (startTime != null) {
                                calendar.setTimeInMillis(startTime.getMilliSeconds());
                                aoDisplayWrapper.setStartTimeDisplay(format.format(calendar.getTime()));
                            }
                            if (endTime != null) {
                                calendar.setTimeInMillis(endTime.getMilliSeconds());
                                aoDisplayWrapper.setEndTimeDisplay(format.format(calendar.getTime()));
                            }
                            if (days != null && days.size() > 0) {
                                aoDisplayWrapper.setDaysDisplayName(getDays(days));
                            }
                        }
                    }

                    // assign building and room info
                    List<String> roomIds = componentList.get(0).getRoomIds();
                    if (roomIds != null && roomIds.size() > 0) {
                        if (roomIds.get(0) != null) {
                            RoomInfo roomInfo = getRoomService().getRoom(roomIds.get(0), contextInfo);
                            if (roomInfo != null) {
                                if (roomInfo.getBuildingId() != null && !roomInfo.getBuildingId().isEmpty()) {
                                    BuildingInfo buildingInfo = getRoomService().getBuilding(roomInfo.getBuildingId(), contextInfo);
                                    if (buildingInfo != null) {
                                        aoDisplayWrapper.setBuildingName(buildingInfo.getName());
                                    }
                                }
                                aoDisplayWrapper.setRoomName(roomInfo.getRoomCode());
                            }
                        }
                    }
                }
            }

            aoDisplayWrapperList.add(aoDisplayWrapper);
        }

        form.setAoDisplayWrapperList(aoDisplayWrapperList);
    }

    private CourseOfferingService getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public SchedulingService getSchedulingService() {
        if(schedulingService == null)  {
            schedulingService = CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }

    public RoomService getRoomService(){
        if (roomService == null){
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }


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

    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if(intList == null) return sb.toString();

        for(Integer d : intList) {
            sb.append(convertIntoDays(d));
        }
        return sb.toString();
    }

}
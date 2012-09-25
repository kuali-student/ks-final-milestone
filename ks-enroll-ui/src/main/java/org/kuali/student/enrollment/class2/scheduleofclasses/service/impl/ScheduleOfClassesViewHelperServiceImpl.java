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
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.KeyNameInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
    private OrganizationService organizationService;

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
                if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
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
                    if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                        information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                    } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
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

    public void loadCourseOfferingsByTermAndDepartment(String termId, String organizationId, String organizationName, ScheduleOfClassesSearchForm form) throws Exception{
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        // Search ID based on organizationName
        if (organizationId == null || organizationId.isEmpty()) {
            QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
            qBuilder.setPredicates(PredicateFactory.equal("longName", organizationName));
            QueryByCriteria query = qBuilder.build();
            OrganizationService organizationService = getOrganizationService();
            List<String> orgIDs = organizationService.searchForOrgIds(query, ContextUtils.createDefaultContextInfo());
            if (orgIDs.isEmpty()) {
                LOG.error("Error: Can't find any Department for selected Department in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationName, termId);
                form.getCoDisplayWrapperList().clear();
            } else if (orgIDs.size() > 1) {
                LOG.error("Error: There is more than one departments with the same long name in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_MULTIPLE_DEPARTMENT_IS_FOUND, organizationName);
                organizationId = null;
                form.getCoDisplayWrapperList().clear();
            } else {
                organizationId = orgIDs.get(0);
            }
        }

        if (organizationId != null) {
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
                    if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                        information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                    } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
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
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationName, termId);
                form.getCoDisplayWrapperList().clear();
            }
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

            if(!aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().isEmpty()){
                //TODO handle TBA state
                ScheduleComponentDisplay scheduleComponentDisplay = aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().get(0);
                if(scheduleComponentDisplay.getBuilding() != null){
                    aoDisplayWrapper.setBuildingName(scheduleComponentDisplay.getBuilding().getBuildingCode());
                }
                if(scheduleComponentDisplay.getRoom() != null){
                    aoDisplayWrapper.setRoomName(scheduleComponentDisplay.getRoom().getRoomCode());
                }
                if(!scheduleComponentDisplay.getTimeSlots().isEmpty()){
                    if(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime() != null){
                        aoDisplayWrapper.setStartTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime().getMilliSeconds()));
                    }
                    if(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime() != null){
                        aoDisplayWrapper.setEndTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime().getMilliSeconds()));
                    }
                    aoDisplayWrapper.setDaysDisplayName(getDays(scheduleComponentDisplay.getTimeSlots().get(0).getWeekdays()));
                }
            }

            aoDisplayWrapperList.add(aoDisplayWrapper);
        }

        form.setAoDisplayWrapperList(aoDisplayWrapperList);
    }

    private String millisToTime(long milliseconds){
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return new SimpleDateFormat("hh:mm a").format(cal.getTime());

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

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    private String convertIntoDaysDisplay(int day) {
        String dayOfWeek;
        switch (day) {
            case 1:
                dayOfWeek = SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 2:
                dayOfWeek = SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 3:
                dayOfWeek = SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 4:
                dayOfWeek = SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 5:
                dayOfWeek = SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 6:
                dayOfWeek = SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 7:
                dayOfWeek = SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            default:
                dayOfWeek = StringUtils.EMPTY;
        }
        // TODO implement TBA when service stores it.
        return dayOfWeek;
    }

    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if(intList == null){
            return sb.toString();
        }

        for(Integer d : intList) {
            sb.append(convertIntoDaysDisplay(d));
        }

        return sb.toString();
    }

}
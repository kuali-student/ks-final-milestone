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
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
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
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class performs queries for scheduling of classes
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesViewHelperServiceImpl extends ViewHelperServiceImpl implements ScheduleOfClassesViewHelperService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesViewHelperServiceImpl.class);

    private CourseOfferingService coService;
    private LprService lprService;
    private OrganizationService organizationService;

    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception{

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                PredicateFactory.equalIgnoreCase("atpId", termId)),
                PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayWrapperList().clear();
            form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(),contextInfo));
        } else {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & courseCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "courseCode", courseCode, termId);
            form.getCoDisplayWrapperList().clear();
        }
    }

    @Override
    public void loadCourseOfferingsByTermAndInstructor(String termId, String instructorId, String instructorName, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // Search ID based on organizationName
        if (instructorId == null || instructorId.isEmpty()) {
            Map<String, String> searchCriteria = new HashMap<String, String>();
            searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_NAME, instructorName);
            List<Person> instructors = getPersonService().findPeople(searchCriteria);
            if (instructors.isEmpty()) {
                LOG.error("Error: Can't find any instructor for selected instructor in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorName, termId);
                form.getCoDisplayWrapperList().clear();
            } else if (instructors.size() > 1) {
                LOG.error("Error: There is more than one instructor with the same name in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_MULTIPLE_INSTRUCTOR_IS_FOUND, instructorName);
                instructorId = null;
                form.getCoDisplayWrapperList().clear();
            } else {
                instructorId = instructors.get(0).getPrincipalId();
            }
        }

        if (instructorId != null) {
            //this is a cross service search between LPR and LUI, so it is inefficient (no join)
            //First get all the luiIds that the instructor is teaching
            //Only get active courses
            List<String> luiIds = getLprService().getLuiIdsByPersonAndTypeAndState(instructorId, LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, LprServiceConstants.ACTIVE_STATE_KEY, contextInfo);

            List<String> courseOfferingIds = null;

            if(luiIds != null && !luiIds.isEmpty()){
                //Now find all the COs with Aos that are attached to that instructor.
                // Build a query
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.in("aoid", luiIds.toArray()),
                    PredicateFactory.equalIgnoreCase("atpId", termId)),
                    PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY));
                QueryByCriteria criteria = qbcBuilder.build();
                courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

                if(courseOfferingIds.size() > 0){
                    form.getCoDisplayWrapperList().clear();
                    form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(),contextInfo));
                }
            }

            //If nothing was found then error
            if(courseOfferingIds == null || courseOfferingIds.isEmpty()) {
                LOG.error("Error: Can't find any Course Offering for selected Instructor in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorId, termId);
                form.getCoDisplayWrapperList().clear();
            }
        }
    }

    public void loadCourseOfferingsByTermAndDepartment(String termId, String organizationId, String organizationName, ScheduleOfClassesSearchForm form) throws Exception{
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        // Search ID based on organizationName
        if (organizationId == null || organizationId.isEmpty()) {
            QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
            qBuilder.setPredicates(PredicateFactory.equalIgnoreCase("longName", organizationName));
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
                form.getCoDisplayWrapperList().clear();
            }
        } else {
            qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("luiContentOwner", organizationId),
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY),
                PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)));
            QueryByCriteria criteria = qbcBuilder.build();
            List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

            if(courseOfferingIds.size() > 0){
                form.getCoDisplayWrapperList().clear();
                form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(),contextInfo));
            } else {            //If nothing was found then error
                LOG.error("Error: Can't find any Course Offering for selected Department in term: " + termId);
                GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationName, termId);
                form.getCoDisplayWrapperList().clear();
            }
        }
    }

    public void loadActivityOfferingsByCourseOfferingId(String courseOfferingId, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        List<ActivityOfferingDisplayWrapper> aoDisplayWrapperList = new ArrayList<ActivityOfferingDisplayWrapper>();
        List<ActivityOfferingDisplayInfo> aoDisplayInfoList = getCourseOfferingService().getActivityOfferingDisplaysForCourseOffering(courseOfferingId, contextInfo);

        for (ActivityOfferingDisplayInfo aoDisplayInfo : aoDisplayInfoList) {
            //Only returned offered AOS
            if(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY.equals(aoDisplayInfo.getStateKey())){
                ActivityOfferingDisplayWrapper aoDisplayWrapper = new ActivityOfferingDisplayWrapper();
                aoDisplayWrapper.setAoDisplayInfo(aoDisplayInfo);

                // Adding Information (icons)
                String information = "";
                if (aoDisplayInfo.getIsHonorsOffering() != null && aoDisplayInfo.getIsHonorsOffering()) {
                    information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY + "\"> ";
                }
                aoDisplayWrapper.setInformation(information);

                if(aoDisplayInfo.getScheduleDisplay()!=null && !aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().isEmpty()){
                    //TODO handle TBA state
                    List<? extends ScheduleComponentDisplay> scheduleComponentDisplays = aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays();
                    for (ScheduleComponentDisplay scheduleComponentDisplay : scheduleComponentDisplays) {
                        if(scheduleComponentDisplay.getBuilding() != null){
                            aoDisplayWrapper.setBuildingName(scheduleComponentDisplay.getBuilding().getBuildingCode(), true);
                        }
                        if(scheduleComponentDisplay.getRoom() != null){
                            aoDisplayWrapper.setRoomName(scheduleComponentDisplay.getRoom().getRoomCode(), true);
                        }
                        if(!scheduleComponentDisplay.getTimeSlots().isEmpty()){
                            if(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime() != null){
                                aoDisplayWrapper.setStartTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime().getMilliSeconds()), true);
                            }
                            if(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime() != null){
                                aoDisplayWrapper.setEndTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime().getMilliSeconds()), true);
                            }
                            aoDisplayWrapper.setDaysDisplayName(getDays(scheduleComponentDisplay.getTimeSlots().get(0).getWeekdays()), true);
                        }
                    }

                }

                //  Set the instructor name
                aoDisplayWrapper.setInstructorDisplayNames(aoDisplayInfo.getInstructorName(), true);

                aoDisplayWrapperList.add(aoDisplayWrapper);
            }
        }

        form.setAoDisplayWrapperList(aoDisplayWrapperList);
    }

    @Override
    public void loadCourseOfferingsByTitleAndDescription(String termId, String titleOrDescription, ScheduleOfClassesSearchForm form) throws Exception {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        // Note: the longName is not in the luiEntity so we need to use the criteriaLookupService is used.
        // it is linked back to CourseOfferingCriteriaTransform and wired in the ks-enroll-context.xml
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY),
                PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY),
                PredicateFactory.and(
                        PredicateFactory.or(
                           PredicateFactory.like("plain", "%" + titleOrDescription + "%"), // this is for the description
                           PredicateFactory.like("longName", titleOrDescription + "%")     // this is for the title
                        )
                )

        ));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayWrapperList().clear();
            form.setCoDisplayWrapperList(getCourseOfferingDisplayWrappersByIds(courseOfferingIds,getCourseOfferingService(),contextInfo));
        } else {    //If nothing was found then error
            LOG.error("Error: Can't find any Course Offering for selected Department in term: " + termId);
            GlobalVariables.getMessageMap().putError("Title & Description", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "title or description", titleOrDescription, termId);
            form.getCoDisplayWrapperList().clear();
        }

    }

    protected static List<CourseOfferingDisplayWrapper> getCourseOfferingDisplayWrappersByIds(List<String> courseOfferingIds, CourseOfferingService courseOfferingService, ContextInfo contextInfo) throws Exception{
        List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();

        if(courseOfferingIds.size() > 0){

            List<CourseOfferingDisplayInfo> coDisplayInfoList = courseOfferingService.getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);

            for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                // Adding Information (icons)
                String information = "";
                if (coDisplayInfo.getIsHonorsOffering() != null && coDisplayInfo.getIsHonorsOffering()) {
                    information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                }
                if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey() != null
                        && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY + "\"> ";
                } else if (coDisplayInfo.getGradingOption() != null && coDisplayInfo.getGradingOption().getKey() != null
                        && coDisplayInfo.getGradingOption().getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE)) {
                    information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_GRADING_PERCENT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PERCENT + "\"> ";
                }
                if (!coDisplayInfo.getStudentRegistrationGradingOptions().isEmpty()) {
                    for (KeyNameInfo stuRegOption : coDisplayInfo.getStudentRegistrationGradingOptions()) {
                        if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_PASSFAIL_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_PASSFAIL + "\">";
                        } else if (stuRegOption.getKey().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                            //FindBugs - it is fine as is
                            information = information + "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_STUREG_AUDIT_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_STUREG_AUDIT + "\">";
                        }
                    }
                }
                coDisplayWrapper.setInformation(information);

                coDisplayWrapperList.add(coDisplayWrapper);
            }

        }

        return coDisplayWrapperList;
    }


    private String millisToTime(Long milliseconds){
        if(milliseconds == null){
            return null;
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(cal.getTime());

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


    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    public PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
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
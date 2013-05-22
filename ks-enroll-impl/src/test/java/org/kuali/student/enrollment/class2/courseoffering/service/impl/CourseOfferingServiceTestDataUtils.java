/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.joda.time.DateTime;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper methods for creating the CourseOfferingService class2 objects.
 * <p/>
 * This was originally created to assist with unit testing the
 * CourseOfferingServiceMockImpl
 *
 * @author Kuali Student Team
 */
public final class CourseOfferingServiceTestDataUtils {

    /**
     * Create and initialize an ActivityOfferingCluster using some base data
     * aswell as the parameters given.
     *
     * @param formatOfferingId
     * @param activities
     * @return the initialized ActivityOfferingCluster.
     */
    public static ActivityOfferingClusterInfo createActivityOfferingCluster(String formatOfferingId, String clusterName, List<ActivityOfferingInfo> activities) {
        ActivityOfferingClusterInfo aoc = new ActivityOfferingClusterInfo();

        aoc.setFormatOfferingId(formatOfferingId);

        aoc.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        aoc.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);

        aoc.setName(clusterName);
        aoc.setDescr(new RichTextInfo(clusterName, clusterName));

        Map<String, List<ActivityOfferingInfo>> activityTypeMap = new LinkedHashMap<String, List<ActivityOfferingInfo>>();

        for (ActivityOfferingInfo ao : activities) {

            List<ActivityOfferingInfo> activityList = activityTypeMap.get(ao.getTypeKey());

            if (activityList == null) {
                activityList = new ArrayList<ActivityOfferingInfo>();
                activityTypeMap.put(ao.getTypeKey(), activityList);
            }

            activityList.add(ao);
        }

        List<ActivityOfferingSetInfo> aoSetList = new ArrayList<ActivityOfferingSetInfo>();

        for (String aoTypeKey : activityTypeMap.keySet()) {
            List<ActivityOfferingInfo> activityList = activityTypeMap.get(aoTypeKey);

            ActivityOfferingSetInfo aoSet = createActivityOfferingSet(aoTypeKey, activityList);

            aoSetList.add(aoSet);
        }

        aoc.setActivityOfferingSets(aoSetList);

        return aoc;
    }

    /**
     * Helper to build an ActivityOfferingSet from a list of the real
     * activities
     *
     * @param activityOfferingTypeKey
     * @param activities
     * @return the initialized ActivityOfferingSetInfo object.
     */
    public static ActivityOfferingSetInfo createActivityOfferingSet(String activityOfferingTypeKey, List<ActivityOfferingInfo> activities) {

        ActivityOfferingSetInfo aoSet = new ActivityOfferingSetInfo();

        aoSet.setActivityOfferingType(activityOfferingTypeKey);

        List<String> aoIdList = new ArrayList<String>();

        for (ActivityOfferingInfo activityOfferingInfo : activities) {
            aoIdList.add(activityOfferingInfo.getId());
        }

        aoSet.setActivityOfferingIds(aoIdList);

        return aoSet;
    }

    /**
     * Create and initialize an ActivityOffering using some base data aswell as
     * the parameters given.
     *
     * @param formatOfferingId
     * @param activityId
     * @param scheduleIds
     * @param activityName
     * @param instructors
     * @return
     */
    // Copied from TestCourseOfferingServiceWithMocks
    // and flushed out using the ActivityOfferingTransformer
    public static ActivityOfferingInfo createActivityOffering(String termId,
                                                              CourseOfferingInfo courseOffering, String formatOfferingId,
                                                              List<String> scheduleIds, String activityId, String activityName,
                                                              String activityCode, String activityOfferingTypeKey,
                                                              List<OfferingInstructorInfo> instructors) {

        ActivityOfferingInfo orig = new ActivityOfferingInfo();

        orig.setTypeKey(activityOfferingTypeKey);
        orig.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);

        orig.setCourseOfferingId(courseOffering.getId());
        orig.setFormatOfferingId(formatOfferingId);

        // TODO: maybe make this settable
        orig.setFormatOfferingName(formatOfferingId);

        orig.setActivityId(activityId);

        orig.setTermId(termId);

        // TODO: find out an example of what this can be coded to.
        orig.setTermCode(termId);

        orig.setActivityCode(activityCode);

        orig.setScheduleIds(scheduleIds);

        orig.setActivityOfferingURL("http://activity.com");

        orig.setDescr(new RichTextInfo(activityName, "<b>" + activityName
                + "</b>"));
        orig.setName(activityName);
        orig.setMinimumEnrollment(100);
        orig.setMaximumEnrollment(150);

        orig.setIsEvaluated(true);
        orig.setIsMaxEnrollmentEstimate(false);
        orig.setIsHonorsOffering(true);

        orig.setHasWaitlist(false);
        orig.setIsWaitlistCheckinRequired(false);

        orig.setInstructors(instructors);

        orig.setCourseOfferingCode(courseOffering.getCourseOfferingCode());
        orig.setCourseOfferingTitle(courseOffering.getCourseOfferingTitle());
        orig.setIsColocated(false);

        return orig;
    }

    public static FormatOfferingInfo createFormatOffering(
            String courseOfferingId, String canonicalFormatId, String termId,
            String formatName, String activityOfferingTypeKeys) {

        return createFormatOffering(courseOfferingId, canonicalFormatId,
                termId, formatName, new String[]{activityOfferingTypeKeys});
    }

    /**
     * Create and initialize a FormatOffering using some base data aswell as the
     * parameters given.
     *
     * @param courseOfferingId
     * @param canonicalFormatId
     * @param termId
     * @param formatName
     * @return
     */
    // Copied from TestCourseOfferingServiceWithMocks
    // fleshed out with details from FormatOfferingTransformer
    public static FormatOfferingInfo createFormatOffering(
            String courseOfferingId, String canonicalFormatId, String termId,
            String formatName, String[] activityOfferingTypeKeys) {

        FormatOfferingInfo orig = new FormatOfferingInfo();

        orig.setCourseOfferingId(courseOfferingId);
        orig.setFormatId(canonicalFormatId);
        orig.setTermId(termId);

        orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        orig.setStateKey(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0]);

        orig.setName(formatName);

        orig.setDescr(new RichTextInfo(formatName, "<b>" + formatName + "</b>"));

        orig.setGradeRosterLevelTypeKey(CourseOfferingServiceConstants.GRADE_ROSTER_LEVEL_TYPE_KEY_ATTR);
        orig.setFinalExamLevelTypeKey(CourseOfferingServiceConstants.FINAL_EXAM_LEVEL_TYPE_KEY_ATTR);

        orig.setActivityOfferingTypeKeys(Arrays
                .asList(activityOfferingTypeKeys));

        orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);

        orig.setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);

        return orig;
    }

    // copied from TestAcademicCalendarServiceWithMocks
    public static TermInfo createTerm(String termName, String plainName)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        TermInfo orig = new TermInfo();
        orig.setName(termName);
        orig.setDescr(new RichTextHelper().toRichTextInfo(plainName,
                "formatted " + plainName));
        orig.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new DateTime().plusDays(121).toDate());

        return orig;
    }

    // Copied from TestAcademicCalendarSerciceImplWithMocks
    public static AcademicCalendarInfo createAcademicCalendar(
            String calendarName, String plainName) {
        AcademicCalendarInfo orig = new AcademicCalendarInfo();
        orig.setName(calendarName);
        orig.setDescr(new RichTextHelper().toRichTextInfo(plainName,
                "formatted " + plainName));
        orig.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        DateTime start = new DateTime();
        orig.setStartDate(start.toDate());
        orig.setEndDate(start.plusMonths(4).toDate());
        orig.setAdminOrgId("testOrgId1");

        return orig;

    }

    public static OfferingInstructorInfo createInstructor(String personId,
                                                          String personName, Float percentageEffort) {

        OfferingInstructorInfo instructor = new OfferingInstructorInfo();

        instructor.setPersonId(personId);

        instructor.setPersonName(personName);

        instructor.setPercentageEffort(percentageEffort);

        instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);

        return instructor;
    }

    public static RegistrationGroupInfo createRegistrationGroup(
            String courseOfferingId, String formatOfferingId, String activityOfferingClusterId, String termId,
            List<String> activityOfferingIds, String name,
            String registrationCode, Integer maximumEnrollment) {
        return createRegistrationGroup(courseOfferingId, formatOfferingId,
                termId, activityOfferingClusterId, activityOfferingIds, name, registrationCode,
                true, true,
                maximumEnrollment, LuiServiceConstants.LUI_DRAFT_STATE_KEY);

    }

    public static RegistrationGroupInfo createRegistrationGroup(
            String courseOfferingId, String formatOfferingId, String termId,
            String activityOfferingClusterId, List<String> activityOfferingIds,
            String name, String registrationCode, boolean generated,
            Boolean honorsOffering, Integer maximumEnrollment, String registrationGroupStateKey) {

        RegistrationGroupInfo rg = new RegistrationGroupInfo();

        rg.setActivityOfferingClusterId(activityOfferingClusterId);

        rg.setActivityOfferingIds(activityOfferingIds);

        rg.setCourseOfferingId(courseOfferingId);
        rg.setDescr(new RichTextInfo(name, name));

        rg.setFormatOfferingId(formatOfferingId);
        rg.setActivityOfferingClusterId(activityOfferingClusterId);
        rg.setActivityOfferingClusterId(activityOfferingClusterId);
        rg.setIsGenerated(generated);
        rg.setName(name);
        rg.setRegistrationCode(registrationCode);

        rg.setTermId(termId);

        rg.setStateKey(registrationGroupStateKey);
        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);

        return rg;

    }


    public static String createCanonicalActivityId(String formatId, String activityTypeKey) {
        StringBuilder builder = new StringBuilder();

        builder.append(formatId);
        builder.append(":");
        builder.append(activityTypeKey);

        return builder.toString();
    }

    public static CourseOfferingInfo createCourseOffering(CourseInfo canonicalCourse, String termId, String courseOfferingStateKey) {
        return createCourseOffering(canonicalCourse, termId, courseOfferingStateKey, 1000, 100);
    }
    public static CourseOfferingInfo createCourseOffering(CourseInfo canonicalCourse, String termId, String courseOfferingStateKey, Integer maximumEnrollment, Integer minimumEnrollment) {
        CourseOfferingInfo orig = new CourseOfferingInfo();
        // this is the canonical course id
        orig.setCourseId(canonicalCourse.getId());
        orig.setTermId(termId);
        orig.setCourseCode(canonicalCourse.getCode());

        orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        orig.setStateKey(courseOfferingStateKey);

        // FIXME: assign to a constant
        orig.setWaitlistLevelTypeKey("waitlist key");
        orig.setHasWaitlist(true);
        orig.setFinalExamType(FinalExam.STANDARD.toString());
        orig.setIsEvaluated(true);
        orig.setIsFeeAtActivityOffering(false);

        orig.setFundingSource("funding source");
        orig.setIsFinancialAidEligible(true);

        // reuse the code and title from the canonical course
        orig.setCourseOfferingCode(canonicalCourse.getCode());
        orig.setCourseOfferingTitle(canonicalCourse.getCourseTitle());
        orig.setCourseNumberSuffix(canonicalCourse.getCourseNumberSuffix());

        orig.setCourseOfferingURL("http://courseoffering.com");

        List<String> campusLocations = new ArrayList<String>();

        campusLocations.add("MAIN");

        orig.setCampusLocations(campusLocations);

        orig.setDescr(new RichTextInfo(canonicalCourse.getCourseTitle(), "<b>" + canonicalCourse.getCourseTitle() + "<b>"));
        orig.setCreditOptionId(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_0);
        orig.setGradingOptionId(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        orig.getStudentRegistrationGradingOptions().add(
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        orig.getStudentRegistrationGradingOptions().add(
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE);
        
        orig.setMaximumEnrollment(maximumEnrollment);
        orig.setMinimumEnrollment(minimumEnrollment);

        return orig;

    }
    
    public static CourseOfferingInfo createCourseOffering(
            CourseInfo canonicalCourse, String termId) {
        
        return createCourseOffering(canonicalCourse, termId, 1000, 100);
    }
    
    public static CourseOfferingInfo createCourseOffering(
            CourseInfo canonicalCourse, String termId, Integer maximumEnrollment, Integer minimumEnrollment) {
        // default to the first state in the lifecycle, presently DRAFT.
       CourseOfferingInfo co = createCourseOffering(canonicalCourse, termId, LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0], maximumEnrollment, minimumEnrollment);

       return co;
       
    }

    public static SeatPoolDefinitionInfo createSeatPoolDefinition(String populationId, String name, String expirationMilestoneTypeKey, Boolean percentage, Integer seatLimit, Integer processingPriority) {

        SeatPoolDefinitionInfo spd = new SeatPoolDefinitionInfo();

        spd.setTypeKey(LuiServiceConstants.SEATPOOL_LUI_CAPACITY_TYPE_KEY);
        spd.setStateKey(LuiServiceConstants.LUI_CAPACITY_ACTIVE_STATE_KEY);

        spd.setExpirationMilestoneTypeKey(expirationMilestoneTypeKey);
        spd.setDescr(new RichTextInfo(name, name));

        spd.setIsPercentage(percentage);
        spd.setSeatLimit(seatLimit);

        spd.setPopulationId(populationId);
        spd.setName(name);

        spd.setProcessingPriority(processingPriority);

        return spd;

    }

}

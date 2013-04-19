/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.kuali.student.enrollment.class2.acal.util.MockAcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.ActivityOfferingResult;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.AutogenRegGroupServiceAdapter;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.TimeOfDay;
import org.kuali.student.r2.common.util.TimeOfDayAmPmEnum;
import org.kuali.student.r2.common.util.TimeOfDayFormattingEnum;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
/**
 * A test case for the autogen rg app layer helper.
 * 
 * @author Kuali Student Team 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-autogen-rg-test-class2-mock-context.xml"})
public class TestAutogenRegGroupServiceAdapterImpl {
    private static final Logger log = KSLog4JConfigurer
            .getLogger(TestAutogenRegGroupServiceAdapterImpl.class);

    @Resource (name="CourseOfferingService")
    protected CourseOfferingService coService;

    @Resource
    protected CourseService courseService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    @Resource(name = "LrcService")
    protected LRCService lrcService;
    
    @Resource
    protected AcademicCalendarService acalService;

    @Resource
    protected AtpService atpService;
    
    @Resource
    private AutogenRegGroupServiceAdapter serviceAdapter;

    private ContextInfo contextInfo;
    
    /**
     * 
     */
    public TestAutogenRegGroupServiceAdapterImpl() {
    }
    
    @Before
    public void beforeTest() throws Exception {
        dataLoader.beforeTest();
        
        contextInfo = new ContextInfo();
    
        contextInfo.setCurrentDate(new Date());
        
        contextInfo.setPrincipalId("test");
        contextInfo.setAuthenticatedPrincipalId("test");
    }
    
    @After
    public void afterTest() throws Exception {
        dataLoader.afterTest();
    }

    /**
     * User Story 3: I need the system to automatically create reg groups when I create an
     * AO (via add or copy) to eliminate the need to manually create them
     */
    @Test
    public void testAddActivityOffering()
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        String foId = "CO-2:LEC-ONLY";
        FormatOfferingInfo foInfo = coService.getFormatOffering(foId, contextInfo);
        List<ActivityOfferingClusterInfo> clusters =
                coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        String aocId = clusters.get(0).getId();
        List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(2, aos.size());
        ActivityOfferingInfo aoInfo = new ActivityOfferingInfo(aos.get(0));
        aoInfo.setId("CO-2:LEC-ONLY:LEC-C");
        List<RegistrationGroupInfo> rgInfos = coService.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertEquals(2, rgInfos.size());
        // App layer call
        ActivityOfferingResult aoResult =
                serviceAdapter.createActivityOffering(aoInfo, aocId, contextInfo);
        List<RegistrationGroupInfo> rgInfosByAo = coService.getRegistrationGroupsByActivityOffering(aoResult.getCreatedActivityOffering().getId(), contextInfo);
        assertEquals(1, rgInfosByAo.size());
        rgInfos = coService.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertEquals(3, rgInfos.size()); // Now three RGs
        aos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(3, aos.size());
    }

    /**
     * User Story 5
     */
    @Test
    public void testDeleteActivityOffering()
            throws PermissionDeniedException, MissingParameterException,
                   InvalidParameterException, OperationFailedException, DoesNotExistException {
        // This FO has only 2 AOs in it
        String foId = "CO-2:LEC-ONLY";
        FormatOfferingInfo foInfo = coService.getFormatOffering(foId, contextInfo);
        List<ActivityOfferingClusterInfo> clusters = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(1, clusters.size());
        String aocId = clusters.get(0).getId();
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(2, aoInfos.size());
        // Get list of AO ids
        List<String> aoIdsInCluster = new ArrayList<String>();
        aoIdsInCluster.add(aoInfos.get(0).getId());
        aoIdsInCluster.add(aoInfos.get(1).getId());
        List<RegistrationGroupInfo> rgInfos = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(2, rgInfos.size());
        for (RegistrationGroupInfo rg: rgInfos) {
            for (String aoId : rg.getActivityOfferingIds()) {
                assertTrue(aoIdsInCluster.contains(aoId));
            }
        }
        // Now delete the AO
        String aoIdFirst = aoInfos.get(0).getId();
        String aoIdSecond = aoInfos.get(1).getId();
        // App layer call
        serviceAdapter.deleteActivityOfferingCascaded(aoIdFirst, aocId, contextInfo);
        List<ActivityOfferingClusterInfo> retrieved =
                coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        // Fetch the AOs again--should only be 1
        aoInfos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(1, aoInfos.size()); // Should only have 1 AO
        // And one RG
        rgInfos = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(1, rgInfos.size());
        assertEquals(1, rgInfos.get(0).getActivityOfferingIds().size());
        assertEquals(aoIdSecond, rgInfos.get(0).getActivityOfferingIds().get(0)); // Id is one that is deleted
    }

    /**
     * This is User Story 7: As a user, I need the system to automatically delete all AOs when I delete an AOC
     * so I don’t have to delete all the AOs first
     */
    @Test
    public void testDeleteActivityOfferingCluster()
            throws PermissionDeniedException, MissingParameterException,
            InvalidParameterException, OperationFailedException, DoesNotExistException, DependentObjectsExistException {
        String foId = "CO-2:LEC-ONLY";
        FormatOfferingInfo foInfo = coService.getFormatOffering(foId, contextInfo);
        List<ActivityOfferingClusterInfo> clusters = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(1, clusters.size());
        String aocId = clusters.get(0).getId();
        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(2, activities.size());
        List<ActivityOfferingInfo> activitiesByFo = coService.getActivityOfferingsByFormatOffering(foId, contextInfo);
        assertEquals(2, activitiesByFo.size());
        // Check the RGs by AOC
        List<RegistrationGroupInfo> rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(2, rgsByAoc.size());
        List<RegistrationGroupInfo> rgsByFo = coService.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertEquals(2, rgsByFo.size());
        // App layer call
        // Now zap the AOC
        serviceAdapter.deleteActivityOfferingCluster(aocId, contextInfo);
        try {
            coService.getActivityOfferingCluster(aocId, contextInfo);
            assert(false); // Shouldn't get here
        } catch (DoesNotExistException e) {
            // Should go here
        }
        List<ActivityOfferingInfo> activitiesByFo2 = coService.getActivityOfferingsByFormatOffering(foId, contextInfo);
        assertEquals(0, activitiesByFo2.size());
        List<ActivityOfferingClusterInfo> clusters2 = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(0, clusters2.size());
    }

    /**
     * This is User Story 6: As a user, I need the system to automatically create/delete all
     * associated registration groups when I move an Activity from one AOC to another
     */
    @Test
    public void testMoveActivityOffering() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        String foId = "CO-2:LEC-ONLY";
        FormatOfferingInfo foInfo = coService.getFormatOffering(foId, contextInfo);
        List<ActivityOfferingClusterInfo> clusters = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(1, clusters.size());
        String aocId = clusters.get(0).getId();
        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(2, activities.size());
        List<ActivityOfferingInfo> activitiesByFo = coService.getActivityOfferingsByFormatOffering(foId, contextInfo);
        assertEquals(2, activitiesByFo.size());
        // Check the RGs by AOC
        List<RegistrationGroupInfo> rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(2, rgsByAoc.size());
        List<RegistrationGroupInfo> rgsByFo = coService.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertEquals(2, rgsByFo.size());
        // Pick an activity to move
        String aoIdSecond = activities.get(1).getId();
        // Store original aoId
        String aoIdFirst = activities.get(0).getId();

        // Now create a new AOC
        ActivityOfferingClusterInfo aocSecond = new ActivityOfferingClusterInfo();
        aocSecond.setFormatOfferingId(foId);
        aocSecond.setPrivateName("Second");
        aocSecond.setName("Second");
        aocSecond.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        aocSecond.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        String aocSecondTypeKey = aocSecond.getTypeKey();
        ActivityOfferingClusterInfo aocSecondCreated =
                coService.createActivityOfferingCluster(foId, aocSecondTypeKey, aocSecond, contextInfo);
        // And move the AO from original AOC to this newly created AOC
        List<BulkStatusInfo> bulkStatuses =
                serviceAdapter.moveActivityOffering(aoIdSecond, aocId, aocSecondCreated.getId(), contextInfo);
        assertEquals(1, bulkStatuses.size());
        String newRgId = bulkStatuses.get(0).getId();
        RegistrationGroupInfo newRg = coService.getRegistrationGroup(newRgId, contextInfo);
        assertEquals(1, newRg.getActivityOfferingIds().size());
        assertEquals(aoIdSecond, newRg.getActivityOfferingIds().get(0));
        // Check RGs in orig AOC
        rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(1, rgsByAoc.size());
        assertEquals(1, rgsByAoc.get(0).getActivityOfferingIds().size());
        assertEquals(aoIdFirst, rgsByAoc.get(0).getActivityOfferingIds().get(0));
        // And RGs in the new AOC
        rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocSecondCreated.getId(), contextInfo);
        assertEquals(1, rgsByAoc.size());
        assertEquals(1, rgsByAoc.get(0).getActivityOfferingIds().size());
        assertEquals(aoIdSecond, rgsByAoc.get(0).getActivityOfferingIds().get(0));
    }

   
    @Test
    public void testUserStoryThree () throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, ReadOnlyException {
        /*
         * I need the system to automatically create reg groups when I create an AO (via add or copy) to eliminate the need to manually create them
         */
        AtpInfo atp = atpService.getAtp(CourseOfferingServiceTestDataLoader.FALL_2012_TERM_ID, contextInfo);
        
        TermInfo term = new TermInfo();
        
        term.setId(atp.getId());
        term.setCode(atp.getCode());
        term.setDescr(atp.getDescr());
        term.setEndDate(atp.getEndDate());
        term.setMeta(atp.getMeta());
        term.setName(atp.getName());
        term.setStartDate(atp.getStartDate());
        term.setStateKey(atp.getStateKey());
        term.setTypeKey(atp.getTypeKey());
        
        TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails details;
        
        String courseOfferingId = dataLoader.createCourseOffering(term, details = new TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails(), contextInfo);
      
        CourseOfferingInfo co = coService.getCourseOffering(courseOfferingId, contextInfo);
        
        List<FormatOfferingInfo> formats = coService.getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);
        
        Assert.assertNotNull(formats);
        Assert.assertEquals(1, formats.size());
        
        FormatOfferingInfo fo = formats.get(0);
        
        List<ActivityOfferingClusterInfo> aocs = coService.getActivityOfferingClustersByFormatOffering(fo.getId(), contextInfo);
        
        Assert.assertEquals(1, aocs.size());
        
        ActivityOfferingClusterInfo aoc = aocs.get(0);
        
        List<OfferingInstructorInfo> instructors = new LinkedList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor;
        instructors.add(instructor = new OfferingInstructorInfo());
        
        instructor.setPercentageEffort(100.00F);
        instructor.setPersonName("Instructor");
        instructor.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        
        ActivityOfferingInfo aoInfo = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), co, fo.getId(), null, "test", "Lecture Add", "AC", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
        
        ActivityOfferingResult results = serviceAdapter.createActivityOffering(aoInfo, aoc.getId(), contextInfo);
      
        Assert.assertNotNull(results);
        
        Assert.assertTrue(results.getGeneratedRegistrationGroups().size() > 0);
    }
    @Test
    public void testUserStoryEight () throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
        
AtpInfo atp = atpService.getAtp(CourseOfferingServiceTestDataLoader.FALL_2012_TERM_ID, contextInfo);
        
        TermInfo term = new TermInfo();
        
        term.setId(atp.getId());
        term.setCode(atp.getCode());
        term.setDescr(atp.getDescr());
        term.setEndDate(atp.getEndDate());
        term.setMeta(atp.getMeta());
        term.setName(atp.getName());
        term.setStartDate(atp.getStartDate());
        term.setStateKey(atp.getStateKey());
        term.setTypeKey(atp.getTypeKey());
        
        TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails details;
        
        String courseOfferingId = dataLoader.createCourseOffering(term, details = new TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails(), contextInfo);
      
        CourseOfferingInfo co = coService.getCourseOffering(courseOfferingId, contextInfo);
        
        List<FormatOfferingInfo> formats = coService.getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);
        
        Assert.assertNotNull(formats);
        Assert.assertEquals(1, formats.size());
        
        FormatOfferingInfo fo = formats.get(0);
        
        List<ActivityOfferingClusterInfo> aocs = coService.getActivityOfferingClustersByFormatOffering(fo.getId(), contextInfo);
        
        Assert.assertEquals(1, aocs.size());
        
        ActivityOfferingClusterInfo aoc = aocs.get(0);
        
        List<OfferingInstructorInfo> instructors = new LinkedList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor;
        instructors.add(instructor = new OfferingInstructorInfo());
        
        instructor.setPercentageEffort(100.00F);
        instructor.setPersonName("Instructor");
        instructor.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        
        ActivityOfferingInfo aoInfo = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), co, fo.getId(), null, "test", "Lecture Add", "AC", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
        
        ActivityOfferingResult results = serviceAdapter.createActivityOffering(aoInfo, aoc.getId(), contextInfo);
      
        Assert.assertNotNull(results);
        
        Assert.assertTrue(results.getGeneratedRegistrationGroups().size() > 0);
        
        
        Integer aocSeatCount = serviceAdapter.getSeatCountByActivityOfferingCluster(aoc.getId(), contextInfo);
        
        Assert.assertNotNull(aocSeatCount);
        
        // this is not correct
//        Assert.assertEquals(25, aocSeatCount.intValue());

        // need to test a case where the cap is applied.
        
        // 100 seat lecture, 200 seat lecture, 50 seat lab, 75 seat lab.
        // 100, 50
        // 100, 75 100
        // 200, 50
        // 200, 75 125 = 225
        
        /*
         * max (ao.maxEnrollment) for each activity type.  200 + 75 = 275
         * 
         * sum (rg seats) = 250 
         * 
         * It seems like we need some kind of ao aware test
         * 
         * 1
         */
        
        // need to test 2 reg groups
        
        // need to test 4 reg groups
        
        // need to test 16 reg groups.
        
        
        
    }

    @Test
    public void copyCourseOfferingToTargetTerm() throws InvalidParameterException, PermissionDeniedException, DataValidationErrorException,
            AlreadyExistsException, ReadOnlyException, OperationFailedException, MissingParameterException,
            DoesNotExistException, UnsupportedActionException, DependentObjectsExistException, CircularRelationshipException, VersionMismatchException {

        // Create new Course Offering
        MockAcalTestDataLoader acalLoader = new MockAcalTestDataLoader(this.acalService);
        TermInfo term = acalLoader.loadTerm("2012SP", "Spring 2012", "2012-03-01 00:00:00.0", "2012-05-31 00:00:00.0", AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring Term 2012");
        assertEquals("2012SP", term.getCode());
        TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails details;
        String courseOfferingId = dataLoader.createCourseOffering(term, details = new TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails(), contextInfo);
        CourseOfferingInfo co = coService.getCourseOffering(courseOfferingId, contextInfo);
        assertEquals("MATH123", co.getCourseOfferingCode());

        // Create targetTerm
        TermInfo targetTerm = acalLoader.loadTerm("2012FA", "Fall 2012", "2012-09-01 00:00:00.0", "2012-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall Term 2012");
        assertEquals("2012FA", targetTerm.getCode());

        // App layer call
        CourseOfferingInfo coResult = serviceAdapter.copyCourseOfferingToTargetTerm(co, targetTerm, null, contextInfo);
        assertEquals(co.getCourseCode(), coResult.getCourseCode());
        assertEquals(targetTerm.getId(), coResult.getTermId());
        assertEquals(co.getCourseId(), coResult.getCourseId());
    }

    @Test
    public void testTimeOfDayInfoHelper() throws InvalidParameterException {
        // Standard time --------------------------------------------------------------
        // Check 8 am
        TimeOfDay tod = TimeOfDayHelper.createTimeOfDay(8, 0, TimeOfDayAmPmEnum.AM);
        String todStr = TimeOfDayHelper.formatTimeOfDay(tod);
        assertEquals("8:00 am", todStr);
        // Check 11:59 PM
        tod = TimeOfDayHelper.createTimeOfDay(11, 59, TimeOfDayAmPmEnum.PM);
        todStr = TimeOfDayHelper.formatTimeOfDay(tod);
        assertEquals("11:59 pm", todStr);
        // Military time --------------------------------------------------------------
        // Check 8 am
        tod = TimeOfDayHelper.createTimeOfDayInMilitary(8, 0);
        todStr = TimeOfDayHelper.formatTimeOfDay(tod);
        assertEquals("8:00 am", todStr);
        List<TimeOfDayFormattingEnum> options = new ArrayList<TimeOfDayFormattingEnum>();
        options.add(TimeOfDayFormattingEnum.USE_MILITARY_TIME);
        todStr = TimeOfDayHelper.formatTimeOfDay(tod, options);
        assertEquals("8:00", todStr);
        // Check 11:59 PM
        tod = TimeOfDayHelper.createTimeOfDayInMilitary(23, 59);
        todStr = TimeOfDayHelper.formatTimeOfDay(tod);
        assertEquals("11:59 pm", todStr);
        todStr = TimeOfDayHelper.formatTimeOfDay(tod, options);
        assertEquals("23:59", todStr);
        // Check for invalid times -----------------------------------------------------
        // 1 millisecond too large
        assertTrue(_testExceptionOnCreateTimeOfDay(tod.getMilliSeconds() + 1));
        // Check 1 millisecond too small
        assertTrue(_testExceptionOnCreateTimeOfDay(-1L));
        // Check creating hours too large
        assertTrue(_testExceptionOnCreateTimeOfDay(13, 0, TimeOfDayAmPmEnum.AM));
        // Check creating hours too small
        assertTrue(_testExceptionOnCreateTimeOfDay(0, 0, TimeOfDayAmPmEnum.AM));
        // Check creating minutes too large
        assertTrue(_testExceptionOnCreateTimeOfDay(1, 60, TimeOfDayAmPmEnum.AM));
        // Check creating minutes too small
        assertTrue(_testExceptionOnCreateTimeOfDay(1, -1, TimeOfDayAmPmEnum.AM));
    }

    private boolean _testExceptionOnCreateTimeOfDay(long millis) {
        TimeOfDayInfo info = new TimeOfDayInfo();
        info.setMilliSeconds(-1L);
        boolean exceptionThrown = false;
        try {
            String todStr = TimeOfDayHelper.formatTimeOfDay(info);
        } catch (InvalidParameterException e) {
            exceptionThrown = true;
        }
        return exceptionThrown;
    }
    private boolean _testExceptionOnCreateTimeOfDay(int normalHour, int minute, TimeOfDayAmPmEnum amOrPm) {
        boolean exceptionThrown = false;
        try {
            TimeOfDay tod = TimeOfDayHelper.createTimeOfDay(13, 0, TimeOfDayAmPmEnum.AM);
        } catch (InvalidParameterException e) {
            exceptionThrown = true;
        }
        return exceptionThrown;
    }
}

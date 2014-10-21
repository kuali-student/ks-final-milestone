/**
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.ListOfStringTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author ocleirig
 *         <p/>
 *         This is a set of unit test cases that runs directly against a mock
 *         implementation of the class 2 course offering service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-class2-mock-context.xml"})
public class TestCourseOfferingServiceImplWithClass2Mocks {

    @Resource(name = "CourseOfferingService")
    protected CourseOfferingService coService;

    @Resource
    protected AcademicCalendarService acalService;

    @Resource
    protected AtpService atpService;

    @Resource
    protected CourseService canonicalCourseService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    private final boolean testAwareDataLoader;

    /**
     *
     */
    public TestCourseOfferingServiceImplWithClass2Mocks() {
        this(true);
    }


    protected TestCourseOfferingServiceImplWithClass2Mocks(boolean testAwareDataLoader) {
        this.testAwareDataLoader = testAwareDataLoader;

    }


  

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setup() throws Exception {

        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);



        if (testAwareDataLoader || !dataLoader.isInitialized()) {
            dataLoader.beforeTest();
            
        }

    }


    @After
    public void tearDown() throws Exception {
        if (testAwareDataLoader)
            dataLoader.afterTest();
    }

    @Test
    public void testCreateAndGetRegistrationGroup() throws Exception {
        List<String> activityOfferingIds = new ArrayList<String>();

        activityOfferingIds.add("CO-1:LEC-ONLY:LEC-A");

        String formatOfferingId = "CO-1:LEC-ONLY";

        String activityOfferingClusterId = "fake-aoc-id";

        RegistrationGroupInfo rgLecA = CourseOfferingServiceTestDataUtils
                .createRegistrationGroup("CO-1", formatOfferingId, "2012FA",
                        activityOfferingClusterId, activityOfferingIds, "RG-1", "RG-1", true, true,
                        10, LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);

        RegistrationGroupInfo created = coService.createRegistrationGroup(
                formatOfferingId, activityOfferingClusterId,
                LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rgLecA,
                callContext);
        assertNotNull(created);

        RegistrationGroupInfo retrieved = coService.getRegistrationGroup(
                created.getId(), callContext);
        assertNotNull(retrieved);

        assertEquals(rgLecA.getFormatOfferingId(),
                retrieved.getFormatOfferingId());
        assertEquals(rgLecA.getActivityOfferingClusterId(), retrieved.getActivityOfferingClusterId());
        assertEquals(rgLecA.getName(), retrieved.getName());
        assertEquals(rgLecA.getStateKey(), retrieved.getStateKey());
        assertEquals(rgLecA.getTypeKey(), retrieved.getTypeKey());

        // test getRegistrationGroupsForCourseOffering

    }


    @Test
    public void testActivityOfferingClusters() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        // default cluster is 2x3 = 6 reg groups

        // we want to constrain to not use lec-b
        // 1x3 = 3 reg groups

        ActivityOfferingInfo activities[] = new ActivityOfferingInfo[]{
                coService.getActivityOffering("CO-1:LEC-AND-LAB:LEC-A",
                        callContext),
                coService.getActivityOffering("CO-1:LEC-AND-LAB:LAB-A",
                        callContext),
                coService.getActivityOffering("CO-1:LEC-AND-LAB:LAB-B",
                        callContext),
                coService.getActivityOffering("CO-1:LEC-AND-LAB:LAB-C",
                        callContext),};

        ActivityOfferingClusterInfo expected = CourseOfferingServiceTestDataUtils
                .createActivityOfferingCluster("CO-1:LEC-AND-LAB", "Default Cluster",
                        Arrays.asList(activities));

        expected.setId("AOC-1");
        new AttributeTester().add2ForCreate(expected.getAttributes());

        ActivityOfferingClusterInfo actual = coService.createActivityOfferingCluster("CO-1:LEC-AND-LAB", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, expected, callContext);

        validateAoc(actual, expected, activities);

        expected = actual;
        actual = coService.getActivityOfferingCluster(expected.getId(), callContext);

        validateAoc(actual, expected, activities);

        List<String> aocIds = new ArrayList<String>();
        aocIds.add(actual.getId());
        List<String> aocIdsTwo = coService.getActivityOfferingClustersIdsByFormatOffering("CO-2:LEC-ONLY", callContext);
        aocIds.addAll(aocIdsTwo);
        List<ActivityOfferingClusterInfo> aocList = coService.getActivityOfferingClustersByIds(aocIds, callContext);
        assertEquals(2, aocList.size());
        assertTrue(aocList.get(0).getId().equals(aocIds.get(0)) || aocList.get(1).getId().equals(aocIds.get(0)));
        assertTrue(aocList.get(0).getId().equals(aocIds.get(1)) || aocList.get(1).getId().equals(aocIds.get(1)));

        List<RegistrationGroupInfo> rgList = coService.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), callContext);

        assertTrue(rgList.isEmpty());

        coService.generateRegistrationGroupsForCluster(actual.getId(), callContext);

        rgList = coService.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), callContext);

        assertEquals(3, rgList.size());

        coService.generateRegistrationGroupsForCluster(actual.getId(), callContext);

        // verify count stays the same even after calling the method again.
        rgList = coService.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), callContext);

        assertEquals(3, rgList.size());
    }

    private void validateAoc(ActivityOfferingClusterInfo actual, ActivityOfferingClusterInfo expected, ActivityOfferingInfo... activities) {
        assertNotNull(actual.getId());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getFormatOfferingId(), actual.getFormatOfferingId());
        assertEquals(expected.getPrivateName(), actual.getPrivateName());
        assertEquals(expected.getName(), actual.getName());

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());

        // check that the union of activity id's matches what we declared
        assertEquals(expected.getActivityOfferingSets().size(), actual.getActivityOfferingSets().size());
        List<String> activityIds = new ArrayList<String>();
        for(ActivityOfferingInfo info : activities) {
            activityIds.add(info.getId());
        }

        new ListOfStringTester().checkExistsAnyOrder(activityIds, extractActivityOfferingIds(actual.getActivityOfferingSets()), true);
    }

    private List<String> extractActivityOfferingIds(List<ActivityOfferingSetInfo> aoList) {
        List<String> idList = new ArrayList<String>();

        for (ActivityOfferingSetInfo activityOfferingSetInfo : aoList) {

            idList.addAll(activityOfferingSetInfo.getActivityOfferingIds());

        }
        return idList;
    }


    private ActivityOfferingClusterInfo createDefaultActivityOfferingCluster(String formatOfferingId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByFormatOffering(formatOfferingId, callContext);

        ActivityOfferingClusterInfo defaultAoc = CourseOfferingServiceTestDataUtils.createActivityOfferingCluster(formatOfferingId, "Default Cluster", activities);

        defaultAoc = coService.createActivityOfferingCluster(formatOfferingId, CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, defaultAoc, callContext);

        Assert.assertNotNull(defaultAoc.getId());

        return defaultAoc;
    }

    @Test
    public void testGenerateRegistrationGroupsSimple() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException {

        String formatOfferingId = "CO-1:LEC-AND-LAB";

        createDefaultActivityOfferingCluster(formatOfferingId);

        List<BulkStatusInfo> status = coService
                .generateRegistrationGroupsForFormatOffering(
                        formatOfferingId, callContext);

        Assert.assertNotNull(status);
        Assert.assertEquals(6, status.size());

        List<RegistrationGroupInfo> rgList = coService.getRegistrationGroupsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        Assert.assertEquals(6, rgList.size());

    }

    @Test
    public void testGenerateAndDeleteRegistrationGroups() throws Exception {

        ActivityOfferingClusterInfo cluster = createDefaultActivityOfferingCluster("CO-1:LEC-AND-LAB");

        List<RegistrationGroupInfo> rgList = coService.getRegistrationGroupsForCourseOffering("CO-1", callContext);

        assertTrue(rgList.isEmpty());

        List<BulkStatusInfo> status = coService
                .generateRegistrationGroupsForFormatOffering(
                        "CO-1:LEC-AND-LAB", callContext);


        Assert.assertNotNull(status);
        Assert.assertEquals(6, status.size());

        rgList = coService.getRegistrationGroupsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        Assert.assertEquals(6, rgList.size());

        // this is harder so for now just skip

//		FormatOfferingInfo fo = coService.getFormatOffering("CO-1:LEC-AND-LAB", callContext);
//		
//		fo.getActivityOfferingTypeKeys().add(LuiServiceConstants.DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY);
//		
//		coService.updateFormatOffering(fo.getId(), fo, callContext);

        List<ActivityOfferingInfo> ao = coService.getActivityOfferingsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        Assert.assertEquals(5, ao.size());

        dataLoader.createLabActivityOfferingForCHEM123("LAB-F", callContext);

        ao = coService.getActivityOfferingsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        Assert.assertEquals(6, ao.size());

        coService.generateRegistrationGroupsForFormatOffering(
                "CO-1:LEC-AND-LAB", callContext);

        ao = coService.getActivityOfferingsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        // should stay the same size.
        Assert.assertEquals(6, ao.size());

        status = coService.deleteGeneratedRegistrationGroupsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        assertTrue("Failed to delete existing generated registration groups", !status.isEmpty());

        coService.generateRegistrationGroupsForFormatOffering(
                "CO-1:LEC-AND-LAB", callContext);

        rgList = coService.getRegistrationGroupsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        Assert.assertEquals(6, rgList.size());

        // in order to get 8 we need to assign the new AO into the default cluster

        // or just delete the existing default cluster and recreate it.

        coService.deleteRegistrationGroupsForCluster(cluster.getId(), callContext);
        coService.deleteActivityOfferingCluster(cluster.getId(), callContext);

        createDefaultActivityOfferingCluster("CO-1:LEC-AND-LAB");

        coService.generateRegistrationGroupsForFormatOffering("CO-1:LEC-AND-LAB", callContext);

        rgList = coService.getRegistrationGroupsByFormatOffering("CO-1:LEC-AND-LAB", callContext);

        Assert.assertEquals(8, rgList.size());


    }

    @Test
    public void testServiceSetup() {
        assertNotNull(coService);
    }

    /**
     * **********************************************************************
     * COURSE TESTS ***********************************************************************
     */
    @Test
    public void testGetCourseOffering() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            coService.getCourseOffering("Lui-blah", callContext);
            fail("Lui-blah should have thrown DoesNotExistException");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals("Lui-blah", dnee.getMessage());
        }

        CourseOfferingInfo co = coService.getCourseOffering("CO-1",
                callContext);
        assertNotNull(co);
        assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                co.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                co.getTypeKey());
    }

    @Test
    public void testGetCourseOfferingIdsByTerm() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> offerings = coService.getCourseOfferingIdsByTerm(
                "TermId-blah", true, callContext);

        assertTrue("Course offerings should be empty", offerings.isEmpty());

        offerings = coService.getCourseOfferingIdsByTerm("2012FA",
                true, callContext);
        assertTrue("Course offerings should be non-empty", !offerings.isEmpty());
    }

    @Test
    public void testGetCourseOfferingsByIds() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> idsList = new ArrayList<String>();
        idsList.add("test1");
        idsList.add("test2");
        idsList.add("test3");

        try {
            coService.getCourseOfferingsByIds(idsList, callContext);
            fail("idsList should have thrown DoesNotExistException");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals("test1", dnee.getMessage());
        }

        idsList.clear();

        idsList.add("CO-1");
        idsList.add("CO-2");

        List<CourseOfferingInfo> co = coService.getCourseOfferingsByIds(
                idsList, callContext);

        assertNotNull(co);
        for (CourseOfferingInfo coItem : co) {
            assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                    coItem.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                    coItem.getTypeKey());
        }
    }

    @Test
    public void testGetCourseOfferingsByCourseAndTerm()
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<CourseOfferingInfo> offerings = coService
                .getCourseOfferingsByCourseAndTerm("Lui-blah",
                        "TermId-blah", callContext);

        assertTrue("Course offerings should be empty", offerings.isEmpty());

        offerings = coService
                .getCourseOfferingsByCourseAndTerm("CLU-1", "2012FA",
                        callContext);
        assertTrue("Course offerings should be non-empty", !offerings.isEmpty());

        for (CourseOfferingInfo coItem : offerings) {
            assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                    coItem.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                    coItem.getTypeKey());
        }
    }

    @Test
    public void testCreateCourseOffering() throws Exception {

        String courseId = dataLoader.createCourse(dataLoader.getFall2012(),"MATH", "123", callContext);

        List<CourseOfferingInfo> offerings = coService
                .getCourseOfferingsByCourse(courseId, callContext);

        int expectedOfferings = offerings.size() + 1;

        List<String> optionKeys = new ArrayList<String>();
        CourseInfo canonicalCourse = this.canonicalCourseService
                .getCourse(courseId, ContextUtils.getContextInfo());
        CourseOfferingInfo coInfo = CourseOfferingServiceTestDataUtils
                .createCourseOffering(canonicalCourse, "2012FA");

        CourseOfferingInfo created = coService.createCourseOffering(courseId,
                "2012FA", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo,
                optionKeys, callContext);

        assertNotNull(created);
        assertEquals("MATH123-ID", created.getCourseId());
        assertEquals("2012FA", created.getTermId());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                created.getTypeKey());
        assertEquals("MATH123", created.getCourseOfferingCode());
        assertEquals("MATH MATH123", created.getCourseOfferingTitle());

        CourseOfferingInfo retrieved = coService.getCourseOffering(
                created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals("MATH123-ID", retrieved.getCourseId());
        assertEquals("2012FA", retrieved.getTermId());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                retrieved.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                retrieved.getTypeKey());

        assertEquals("MATH123", retrieved.getCourseOfferingCode());
        assertEquals("MATH MATH123", retrieved.getCourseOfferingTitle());

        offerings = coService.getCourseOfferingsByCourse("CLU-1", callContext);

        // we and maybe another test have added one
        assertEquals(expectedOfferings, offerings.size());
    }

    @Test
    public void testUpdateCourseOffering() throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {
        CourseOfferingInfo coi = coService.getCourseOffering("CO-2",
                callContext);
        assertNotNull(coi);

        coi.setTermId("testAtpId1");
        coi.setIsHonorsOffering(true);
        coi.setMaximumEnrollment(40);
        coi.setMinimumEnrollment(10);
        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor = new OfferingInstructorInfo();
        instructor.setPersonId("Pers-1");
        instructor.setPercentageEffort(Float.valueOf("60"));
        instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        instructors.add(instructor);
        coi.setInstructors(instructors);
        CourseOfferingInfo updated = coService.updateCourseOffering("CO-2",
                coi, callContext);
        assertNotNull(updated);

        CourseOfferingInfo retrieved = coService.getCourseOffering("CO-2",
                callContext);
        assertNotNull(retrieved);

        assertTrue(retrieved.getIsHonorsOffering());
        assertEquals(1, retrieved.getInstructors().size());
        assertEquals(coi.getMaximumEnrollment(),
                retrieved.getMaximumEnrollment());
        assertEquals(coi.getMinimumEnrollment(),
                retrieved.getMinimumEnrollment());

        retrieved.setIsHonorsOffering(false);
        List<OfferingInstructorInfo> instructors1 = new ArrayList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor1 = new OfferingInstructorInfo();
        instructor1.setPersonId("Pers-2");
        instructor1.setPercentageEffort(Float.valueOf("60"));
        instructor1
                .setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor1.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        instructors1.add(instructor1);
        OfferingInstructorInfo instructor2 = new OfferingInstructorInfo();
        instructor2.setPersonId("Pers-1");
        instructor2.setPercentageEffort(Float.valueOf("30"));
        instructor2
                .setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor2.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
        instructors1.add(instructor2);
        retrieved.setInstructors(instructors1);
        CourseOfferingInfo updated1 = coService.updateCourseOffering(
                "CO-2", retrieved, callContext);
        assertNotNull(updated1);

        CourseOfferingInfo retrieved1 = coService.getCourseOffering("CO-2",
                callContext);
        assertNotNull(retrieved1);
        assertEquals(2, retrieved1.getInstructors().size());
    }

    @Test
    public void testUpdateCourseOfferingWithDynAttrs()
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {

        CourseOfferingInfo coi = coService.getCourseOffering("CO-2",
                callContext);
        assertNotNull(coi);

        coi.setTermId("atpId1");

        // dynamic attributes
        AttributeTester attributeTester = new AttributeTester();

        List<AttributeInfo> expectedList = new ArrayList<AttributeInfo>();

        attributeTester.add2ForCreate(expectedList);

        coi.getAttributes().addAll(expectedList);

        coi.setFundingSource("state");

        CourseOfferingInfo updated = coService.updateCourseOffering(
                coi.getId(), coi, callContext);
        assertNotNull(updated);

        CourseOfferingInfo retrieved = coService.getCourseOffering(
                coi.getId(), callContext);
        assertNotNull(retrieved);

        assertEquals("state", coi.getFundingSource());

        attributeTester.check(expectedList, coi.getAttributes());

        // TODO: fix once waitlists are implemented
        // assertEquals("WaitlistLevelType1",
        // coi.getWaitlistLevelTypeKey());
    }

    @Test
    // TODO fix KSENROLL-2671, add back validation decorator and this will work again
    public void testDeleteFormatOffering() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException, DoesNotExistException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {

        try {
            coService.deleteFormatOffering("DOES NOT EXIST", callContext);
            fail("DoesNotExistException should have been thrown");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("DOES NOT EXIST", e.getMessage());
        }

        String formatOfferingId = "CO-1:LEC-ONLY";
        try {
            coService.deleteFormatOffering(formatOfferingId, callContext);
            fail("DependentObjectsExistException should have been thrown");
        } catch (DependentObjectsExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("Activity offerings still attached to FO (" + formatOfferingId + ")", e.getMessage());
        }

        StatusInfo status = coService.deleteFormatOfferingCascaded(formatOfferingId, callContext);

        assertTrue(status.getIsSuccess());

        // verify no activity offerings remain
        List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByFormatOffering(formatOfferingId, callContext);

        assertTrue(aos.isEmpty());
    }


    @Test
    public void testDeleteActivityOffering() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException, DoesNotExistException, VersionMismatchException {

        SeatPoolDefinitionInfo seatPoolDefinitionInfo = CourseOfferingServiceTestDataUtils.createSeatPoolDefinition("POP1", "Test Seat Pool", "expiration milestone", false, 12, 5);

        seatPoolDefinitionInfo = coService.createSeatPoolDefinition(seatPoolDefinitionInfo, callContext);

        String activityOfferingId = "CO-1:LEC-ONLY:LEC-A";
        coService.addSeatPoolDefinitionToActivityOffering(seatPoolDefinitionInfo.getId(), activityOfferingId, callContext);

        try {
            coService.deleteActivityOffering(activityOfferingId, callContext);
            fail("DependentObjectsExistException should have been thrown as we failed to detect associated seat pool for activity offering and abort delete");
        } catch (DependentObjectsExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("Seatpools exists for Activity id = " + activityOfferingId, e.getMessage());
        }

        // now cascade the delete

        StatusInfo status = coService.deleteActivityOfferingCascaded(activityOfferingId, callContext);

        assertTrue(status.getIsSuccess());

        // check that the activity offering and seat pool are gone.

        try {
            coService.getActivityOffering(activityOfferingId, callContext);
            fail("DoesNotExistException should have been thrown as activity should not exist after delete");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals(activityOfferingId, e.getMessage());
        }

        try {
            coService.getSeatPoolDefinitionsForActivityOffering(activityOfferingId, callContext);
            fail("DoesNotExistException should have been thrown as activity should not exist after delete");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals(activityOfferingId, e.getMessage());
        }
    }


    @Test
    public void testDeleteCourseOffering() throws Exception {

        CourseInfo canonicalCourse = canonicalCourseService.getCourse("CLU-1", ContextUtils.getContextInfo());

        // Create a CO
        CourseOfferingInfo coInfo = CourseOfferingServiceTestDataUtils
                .createCourseOffering(canonicalCourse, "2012SP");
        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo created = coService.createCourseOffering("CLU-1",
                "2012SP", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo,
                optionKeys, callContext);

        // Verify that the CO was created
        assertNotNull(created);
        assertEquals("CLU-1", created.getCourseId());
        assertEquals("2012SP", created.getTermId());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_STATE_KEYS[0],
                created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                created.getTypeKey());

        // Delete the course offering and check that the status returned was
        // a success
        StatusInfo delResult = coService.deleteCourseOffering(
                created.getId(), callContext);
        assertTrue(delResult.getIsSuccess());

        try {
            coService.deleteCourseOffering("CO-1", callContext);
            fail("DependentObjectsExistException should have been thrown as we failed to detect dependent objects");
        } catch (DependentObjectsExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("Format offering still attached to CO (CO-1)", e.getMessage());
        }
    }

    @Test
    //@Ignore //KSENROLL-3482// TODO: update cascade to deal with AOC's instead of Reg Groups.
    public void testDeleteCourseOfferingCascaded() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {

        try {
            coService.deleteCourseOffering("CO-2", callContext);
            fail("DependentObjectsExistException should have been thrown as dependent objects exist for CO-2");
        } catch (DependentObjectsExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("Format offering still attached to CO (CO-2)", e.getMessage());
        }


        coService.generateRegistrationGroupsForFormatOffering("CO-2:LEC-ONLY", callContext);

        List<RegistrationGroupInfo> rgs = coService.getRegistrationGroupsByFormatOffering("CO-2:LEC-ONLY", callContext);

        assertTrue("Registration groups should be non-empty", !rgs.isEmpty());

        StatusInfo status = coService.deleteCourseOfferingCascaded("CO-2", callContext);

        assertTrue(status.getIsSuccess());

        List<FormatOfferingInfo> formats = coService.getFormatOfferingsByCourseOffering("CO-2", callContext);

        assertTrue(formats.isEmpty());

        rgs = coService.getRegistrationGroupsForCourseOffering("CO-2", callContext);

        assertTrue(rgs.isEmpty());

    }


    @Test
    public void testCreateFormatOffering() throws Exception {
        List<CourseOfferingInfo> coList = coService
                .getCourseOfferingsByCourse("CLU-1", callContext);

        assertTrue("Course Offerings should be non-empty", !coList.isEmpty());

        CourseOfferingInfo co = coList.get(0);

        FormatOfferingInfo newFO = CourseOfferingServiceTestDataUtils
                .createFormatOffering(co.getId(), "format1",
                        co.getTermId(), "TEST FORMAT OFFERING",
                        LuiServiceConstants.ALL_ACTIVITY_TYPES);
        FormatOfferingInfo fo = coService.createFormatOffering(co.getId(),
                "format1", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
                newFO, callContext);
        assertNotNull(fo);
        assertEquals(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY,
                fo.getStateKey());
        assertEquals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
                fo.getTypeKey());
        assertEquals("TEST FORMAT OFFERING", fo.getDescr().getPlain());
    }

    @Test
    public void testGetFormatOffering() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        FormatOfferingInfo fo = coService.getFormatOffering(
                "CO-2:LEC-ONLY", callContext);
        assertNotNull(fo);
        assertEquals(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY,
                fo.getStateKey());
        assertEquals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
                fo.getTypeKey());
        assertEquals("Lecture", fo.getDescr().getPlain());
    }

    @Test
    public void testCreateAndGetActivityOffering() throws Exception {

        CourseOfferingInfo courseOffering = coService.getCourseOffering("CO-1", callContext);

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();

        instructors.add(CourseOfferingServiceTestDataUtils.createInstructor(
                "Pers-1", "Person One", 60.00F));

        String activityId = CourseOfferingServiceTestDataUtils
                .createCanonicalActivityId("CO-1:LEC-ONLY",
                        LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);

        ActivityOfferingInfo ao = CourseOfferingServiceTestDataUtils
                .createActivityOffering("2012FA", courseOffering, "CO-1:LEC-ONLY",
                        new ArrayList<String>(Collections.singletonList("SCHED-ID")), activityId, "Lecture", "A",
                        LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
                        instructors);

        ActivityOfferingInfo created = coService.createActivityOffering(
                "CO-1:LEC-ONLY", activityId,
                LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, ao,
                callContext);
        assertNotNull(created);

        ActivityOfferingInfo retrieved = coService.getActivityOffering(
                created.getId(), callContext);
        assertNotNull(retrieved);

        assertEquals(created.getActivityId(), retrieved.getActivityId());
        assertEquals(created.getTermId(), retrieved.getTermId());
        assertEquals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY,
                retrieved.getStateKey());
        assertEquals(
                LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
                retrieved.getTypeKey());
        assertEquals(1, retrieved.getInstructors().size());

        // test getActivityOfferingsByCourseOffering
        List<ActivityOfferingInfo> activities = coService
                .getActivityOfferingsByCourseOffering("CO-1", callContext);
        assertNotNull(activities);
        // 3 existing plus this new one
        // this one should probably not have been added so this test case
        // may need to be adapted
        // in the future.
        assertEquals(8, activities.size());

        boolean foundActivityId = false;
        boolean foundId = false;

        for (ActivityOfferingInfo activityOfferingInfo : activities) {

            if (activityOfferingInfo.getActivityId().equals(
                    created.getActivityId())) {
                foundActivityId = true;
            }

            if (activityOfferingInfo.getId().equals("CO-1:LEC-ONLY:LEC-B"))
                foundId = true;
        }
        assertTrue(foundActivityId);
        assertTrue(foundId);

        assertEquals(1, activities.get(0).getInstructors().size());
    }


    @Test
    public void testUpdateRegistrationGroup() throws InvalidParameterException,
            DataValidationErrorException, MissingParameterException,
            DoesNotExistException, VersionMismatchException,
            PermissionDeniedException, OperationFailedException,
            ReadOnlyException {

        List<RegistrationGroupInfo> rgList = coService.getRegistrationGroupsByFormatOffering("CO-2:LEC-ONLY", callContext);

        assertEquals(2, rgList.size());

        for (RegistrationGroupInfo regGroup : rgList) {

            // TODO: find a way to reach in and check that the reg groups are generated properly.
            if (regGroup.getActivityOfferingIds().get(0).indexOf("LEC-A") > 0) {
                assertEquals("CO-2", regGroup.getCourseOfferingId());
                assertEquals(1, regGroup.getActivityOfferingIds().size());
                assertEquals("CO-2:LEC-ONLY:LEC-A", regGroup.getActivityOfferingIds()
                        .get(0));
            } else if (regGroup.getActivityOfferingIds().get(0).indexOf("LEC-B") > 0) {
                regGroup.getActivityOfferingIds().remove(0);
                regGroup.getActivityOfferingIds().add("CO-2:LEC-ONLY:LEC-B");
                RegistrationGroupInfo updatedRegGroup = coService
                        .updateRegistrationGroup(regGroup.getId(), regGroup,
                                callContext);
                assertEquals("CO-2", regGroup.getCourseOfferingId());
                assertEquals(1, updatedRegGroup.getActivityOfferingIds().size());
                assertEquals("CO-2:LEC-ONLY:LEC-B", regGroup.getActivityOfferingIds()
                        .get(0));
            } else {
                Assert.fail("invalid reg group with id = " + regGroup.getId());
            }
        }


    }

    @Test
    public void testDeleteRegistrationGroup() throws InvalidParameterException,
            MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {

        List<RegistrationGroupInfo> rgList = coService.getRegistrationGroupsByFormatOffering("CO-2:LEC-ONLY", callContext);

        assertEquals(2, rgList.size());

        RegistrationGroupInfo rg = rgList.get(0);

        StatusInfo statusInfo = coService.deleteRegistrationGroup(
                rg.getId(), callContext);
        assertTrue(statusInfo.getIsSuccess());
        try {
            coService.getRegistrationGroup(rg.getId(), callContext);
            fail("Expected DoesNotExistException.");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals(rg.getId(), e.getMessage());
        }
    }

    @Test
    public void testGetActivityOfferingType() throws InvalidParameterException,
            MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {
        TypeInfo validType = coService.getActivityOfferingType(
                LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
                callContext);
        assertNotNull(validType);
        assertEquals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
                validType.getKey());

        try {
            coService.getActivityOfferingType(
                    "madeUpINAVLIDAoType", callContext);
            fail("Expected DoesNotExistException");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("madeUpINAVLIDAoType", e.getMessage());
        }
    }

    @Test
    public void testGetActivityOfferingTypes()
            throws InvalidParameterException, MissingParameterException,
            PermissionDeniedException, OperationFailedException {
        List<TypeInfo> validTypes = coService
                .getActivityOfferingTypes(callContext);

        assertNotNull(validTypes);
        assertTrue("Expecting at least one activity offering type",
                !validTypes.isEmpty());

        boolean found = false;
        for (TypeInfo type : validTypes) {
            found = type.getKey().equals(
                    LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
            if (found) {
                break;
            }
        }

        assertTrue("Expecting to find at least "
                + LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY
                + " type.", found);
    }


    @Test
    public void testCreateSeatPoolDefinition() throws Exception {

        SeatPoolDefinitionInfo mainPool = CourseOfferingServiceTestDataUtils.createSeatPoolDefinition("EVERYONE", "Lab 123", AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY, true, 85, 1);

        SeatPoolDefinitionInfo secondaryPool = CourseOfferingServiceTestDataUtils.createSeatPoolDefinition("EVERYONE", "Lab 123B", AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY, true, 15, 2);

        mainPool = coService.createSeatPoolDefinition(mainPool, callContext);

        secondaryPool = coService.createSeatPoolDefinition(secondaryPool, callContext);

        coService.addSeatPoolDefinitionToActivityOffering(mainPool.getId(), "CO-1:LEC-ONLY:LEC-A", callContext);
        coService.addSeatPoolDefinitionToActivityOffering(secondaryPool.getId(), "CO-1:LEC-ONLY:LEC-A", callContext);

        List<SeatPoolDefinitionInfo> spds = coService.getSeatPoolDefinitionsForActivityOffering("CO-1:LEC-ONLY:LEC-A", callContext);
        Assert.assertEquals(2, spds.size());


        coService.removeSeatPoolDefinitionFromActivityOffering(mainPool.getId(), "CO-1:LEC-ONLY:LEC-A", callContext);

        spds = coService.getSeatPoolDefinitionsForActivityOffering("CO-1:LEC-ONLY:LEC-A", callContext);

        Assert.assertEquals(1, spds.size());
    }
}

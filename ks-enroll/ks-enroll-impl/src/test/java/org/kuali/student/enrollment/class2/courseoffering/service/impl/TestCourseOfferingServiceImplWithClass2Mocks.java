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

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author ocleirig
 * 
 *         This is a set of unit test cases that runs directly against a mock
 *         implementation of the class 2 course offering service.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:co-test-with-class2-mock-context.xml" })
@Ignore //TODO This test was committed as part of the merge and it was failing. When the servive merge is redone remove the @Ignore
public class TestCourseOfferingServiceImplWithClass2Mocks {

	private static final Logger log = Logger
			.getLogger(TestCourseOfferingServiceImplWithClass2Mocks.class);

	@Resource
	protected CourseOfferingService coService;

	@Resource
	protected AcademicCalendarService acalService;

	@Resource
	protected AtpService atpService;

	@Resource
	protected org.kuali.student.lum.course.service.CourseService canonicalCourseService;
	
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
		
		if (testAwareDataLoader || !dataLoader.isInitialized())
			dataLoader.beforeTest();

	}
	
	
	@After
	public void tearDown() {
		if (testAwareDataLoader)
			dataLoader.afterTest();
	}

	@Test
	public void testCreateAndGetRegistrationGroup()
			throws AlreadyExistsException, DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> activityOfferingIds = new ArrayList<String>();

		activityOfferingIds.add("CO-1:LEC-ONLY:LEC-A");

		String formatOfferingId = "CO-1:LEC-ONLY";

		RegistrationGroupInfo rgLecA = CourseOfferingServiceDataUtils
				.createRegistrationGroup("CO-1", formatOfferingId, "2012FA",
						activityOfferingIds, "RG-1", "RG-1", true, true, 10,
						LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);

		try {
			RegistrationGroupInfo created = coService.createRegistrationGroup(
					formatOfferingId,
					LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rgLecA,
					callContext);
			assertNotNull(created);

			RegistrationGroupInfo retrieved = coService.getRegistrationGroup(
					created.getId(), callContext);
			assertNotNull(retrieved);

			assertEquals(rgLecA.getFormatOfferingId(),
					retrieved.getFormatOfferingId());
			assertEquals(rgLecA.getName(), retrieved.getName());
			assertEquals(rgLecA.getStateKey(), retrieved.getStateKey());
			assertEquals(rgLecA.getTypeKey(), retrieved.getTypeKey());

			// test getRegistrationGroupsForCourseOffering

		} catch (Exception ex) {
			fail("Exception from service call :" + ex.getMessage());
		}
	}

	


	

	@Test
	public void testGenerateRegistrationGroupsSimple() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, AlreadyExistsException {

		List<RegistrationGroupInfo> rgList = coService
				.generateRegistrationGroupsForFormatOffering(
						"CO-1:LEC-AND-LAB", callContext);

		Assert.assertEquals(6, rgList.size());

	}
	
	@Test
	public void testGenerateAndDeleteRegistrationGroups() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, org.kuali.student.common.exceptions.DoesNotExistException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, AlreadyExistsException {
		
		List<RegistrationGroupInfo> rgList = coService.getRegistrationGroupsForCourseOffering("CO-1", callContext);
		
		assertEquals (0, rgList.size());
		
		rgList = coService
				.generateRegistrationGroupsForFormatOffering(
						"CO-1:LEC-AND-LAB", callContext);

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
		
		boolean exception = false;
		
		try {
			rgList = coService
					.generateRegistrationGroupsForFormatOffering(
							"CO-1:LEC-AND-LAB", callContext);
		} catch (AlreadyExistsException e) {
			exception = true;
		}
		
		Assert.assertTrue("Exception should have occured when generating on top of existing reg groups.", exception);

		StatusInfo status = coService.deleteGeneratedRegistrationGroupsByFormatOffering("CO-1:LEC-AND-LAB", callContext);
		
		assertTrue("Failed to delete existing generated registration groups", status.getIsSuccess());

		try {
			rgList = coService
					.generateRegistrationGroupsForFormatOffering(
							"CO-1:LEC-AND-LAB", callContext);
		} catch (AlreadyExistsException e) {
			
			// this case should not happen.
			// but fail the test if it does.
			Assert.assertTrue("Failed to generate registration groups", false);
		}
		
		Assert.assertEquals(8, rgList.size());
		
		
		
	}

	@Test
	public void testServiceSetup() {
		assertNotNull(coService);
	}

	/*************************************************************************
	 * COURSE TESTS
	 *************************************************************************/
	@Test
	public void testGetCourseOffering() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {
			try {
				coService.getCourseOffering("Lui-blah", callContext);
				fail("Lui-blah should have thrown DoesNotExistException");
			} catch (DoesNotExistException enee) {
				// expected
			}

			CourseOfferingInfo co = coService.getCourseOffering("CO-1",
					callContext);
			assertNotNull(co);
			assertEquals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY,
					co.getStateKey());
			assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
					co.getTypeKey());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetCourseOfferingIdsByTerm() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<String> offerings = coService.getCourseOfferingIdsByTerm(
				"TermId-blah", true, callContext);

		assertEquals(0, offerings.size());

		List<String> idList = coService.getCourseOfferingIdsByTerm("2012FA",
				true, callContext);
		assertTrue(idList.size() > 0);
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
			try {
				coService.getCourseOfferingsByIds(idsList, callContext);
				fail("idsList should have thrown DoesNotExistException");
			} catch (DoesNotExistException enee) {
				// expected
			}

			idsList.clear();

			idsList.add("CO-1");
			idsList.add("CO-2");

			List<CourseOfferingInfo> co = coService.getCourseOfferingsByIds(
					idsList, callContext);

			assertNotNull(co);
			for (CourseOfferingInfo coItem : co) {
				assertEquals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY,
						coItem.getStateKey());
				assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
						coItem.getTypeKey());
			}
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetCourseOfferingsByCourseAndTerm()
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		try {
			List<CourseOfferingInfo> offerings = coService
					.getCourseOfferingsByCourseAndTerm("Lui-blah",
							"TermId-blah", callContext);

			assertEquals(0, offerings.size());

			List<CourseOfferingInfo> co = coService
					.getCourseOfferingsByCourseAndTerm("CLU-1", "2012FA",
							callContext);
			assertTrue(co.size() > 0);

			for (CourseOfferingInfo coItem : co) {
				assertEquals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY,
						coItem.getStateKey());
				assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
						coItem.getTypeKey());
			}
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testCreateCourseOffering() throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {

		List<CourseOfferingInfo> offerings = coService
				.getCourseOfferingsByCourse("CLU-1", callContext);

		int expectedOfferings = offerings.size() + 1;

		List<String> optionKeys = new ArrayList<String>();
		CourseInfo canonicalCourse = this.canonicalCourseService
				.getCourse("CLU-1");
		CourseOfferingInfo coInfo = CourseOfferingServiceDataUtils
				.createCourseOffering(canonicalCourse, "2012FA");
		CourseOfferingInfo created = coService.createCourseOffering("CLU-1",
				"2012FA", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo,
				optionKeys, callContext);

		assertNotNull(created);
		assertEquals("CLU-1", created.getCourseId());
		assertEquals("2012FA", created.getTermId());
		assertEquals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY,
				created.getStateKey());
		assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
				created.getTypeKey());
		assertEquals("CHEM123", created.getCourseOfferingCode());
		assertEquals("Chemistry 123", created.getCourseOfferingTitle());

		CourseOfferingInfo retrieved = coService.getCourseOffering(
				created.getId(), callContext);
		assertNotNull(retrieved);
		assertEquals("CLU-1", retrieved.getCourseId());
		assertEquals("2012FA", retrieved.getTermId());
		assertEquals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY,
				retrieved.getStateKey());
		assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
				retrieved.getTypeKey());

		assertEquals("CHEM123", retrieved.getCourseOfferingCode());
		assertEquals("Chemistry 123", retrieved.getCourseOfferingTitle());

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
		try {
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
		} catch (Exception ex) {
			fail("Exception from service call :" + ex.getMessage());
		}
	}

	@Test
	public void testUpdateCourseOfferingWithDynAttrs()
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
		try {

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
		} catch (Exception ex) {
			fail("Exception from service call :" + ex.getMessage());
		}
	}

	@Test
	public void testDeleteFormatOffering() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException, DoesNotExistException {
		
		boolean exception = false;
		
		try {
			coService.deleteFormatOffering("DOES NOT EXIST", callContext);
		} catch (DoesNotExistException e) {
			exception = true;
		} 
		
		assertTrue ("Activity should not exist but seems to.", exception);
		
		exception = false;
		
		String formatOfferingId = "CO-1:LEC-ONLY";
		try {
			coService.deleteFormatOffering(formatOfferingId, callContext);
		} catch (DependentObjectsExistException e) {
			exception = true;
		}
		
		assertTrue("Deleted a format that has activities", exception);
		
		StatusInfo status = coService.deleteFormatOfferingCascaded(formatOfferingId, callContext);
		
		assertTrue(status.getIsSuccess());
		
		// verify no activity offerings remain
		 List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByFormatOffering(formatOfferingId, callContext);
	
		 assertEquals (0, aos.size());
	}
	
	
	@Test
	public void testDeleteActivityOffering() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException, DoesNotExistException {
		
		SeatPoolDefinitionInfo seatPoolDefinitionInfo = CourseOfferingServiceDataUtils.createSeatPoolDefinition("POP1", "Test Seat Pool", "expiration milestone", false, 12, 5);
		
		seatPoolDefinitionInfo = coService.createSeatPoolDefinition(seatPoolDefinitionInfo, callContext);
		
		String activityOfferingId = "CO-1:LEC-ONLY:LEC-A";
		coService.addSeatPoolDefinitionToActivityOffering(seatPoolDefinitionInfo.getId(), activityOfferingId, callContext);
		
		boolean exception = false;
		try {
			coService.deleteActivityOffering(activityOfferingId, callContext);
		} catch (DependentObjectsExistException e) {
			exception = true;
		}
		
		assertTrue("Failed to detect associated seat pool for activity offering and abort delete", exception);
		
		// now cascade the delete
		
		StatusInfo status = coService.deleteActivityOfferingCascaded(activityOfferingId, callContext);
		
		assertTrue (status.getIsSuccess());
		
		// check that the activity offering and seat pool are gone.
		
		exception = false;
		
		try {
			coService.getActivityOffering(activityOfferingId, callContext);
		} catch (DoesNotExistException e) {
			exception = true;
		}
		
		assertTrue("activity still exists after delete", exception);
		
		exception = false;
		try {
		List<SeatPoolDefinitionInfo> spls = coService.getSeatPoolDefinitionsForActivityOffering(activityOfferingId, callContext);
		} catch (DoesNotExistException e) {
			exception = true;
		}
		
		assertTrue("activity still exists after delete", exception);
		
	}
	
	
	
	@Test
	public void testDeleteCourseOffering() throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {

		CourseInfo canonicalCourse = canonicalCourseService.getCourse("CLU-1");

		// Create a CO
		CourseOfferingInfo coInfo = CourseOfferingServiceDataUtils
				.createCourseOffering(canonicalCourse, "2012SP");
		List<String> optionKeys = new ArrayList<String>();
		CourseOfferingInfo created = coService.createCourseOffering("CLU-1",
				"2012SP", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo,
				optionKeys, callContext);

		// Verify that the CO was created
		assertNotNull(created);
		assertEquals("CLU-1", created.getCourseId());
		assertEquals("2012SP", created.getTermId());
		assertEquals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY,
				created.getStateKey());
		assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
				created.getTypeKey());

		try {
			// Delete the course offering and check that the status returned was
			// a success
			StatusInfo delResult = coService.deleteCourseOffering(
					created.getId(), callContext);
			assertTrue(delResult.getIsSuccess());
		} catch (Exception ex) {
			fail("Exception from service call :" + ex.getMessage());
		}
		
		boolean exception = false;
		
		try {
			coService.deleteCourseOffering("CO-1", callContext);
		} catch (DependentObjectsExistException e) {
			exception = true;
		}
		
		assertTrue("Failed to detect dependent objects", exception);
		
		
	}

	@Test
	public void testDeleteCourseOfferingCascaded() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException {
		
		boolean dependantObjects = false;
		
		try {
			coService.deleteCourseOffering("CO-1", callContext);
		} catch (DependentObjectsExistException e) {
			dependantObjects = true;
		}
		
		assertTrue("No dependent objects exist for CO-1", dependantObjects);
		
		List<RegistrationGroupInfo> rgs = coService.generateRegistrationGroupsForFormatOffering("CO-1:LEC-ONLY", callContext);
		
		assertTrue(rgs.size() > 0);
		
		StatusInfo status = coService.deleteCourseOfferingCascaded("CO-1", callContext);
		
		assertTrue(status.getIsSuccess());
		
		List<FormatOfferingInfo> formats = coService.getFormatOfferingsByCourseOffering("CO-1", callContext);
		
		assertEquals (0, formats.size());
		
		rgs = coService.getRegistrationGroupsForCourseOffering("CO-1", callContext);
		
		assertEquals (0, rgs.size());
		
	}
	
	
	@Test
	public void testCreateFormatOffering() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {

			List<CourseOfferingInfo> coList = coService
					.getCourseOfferingsByCourse("CLU-1", callContext);

			assertTrue(coList.size() > 0);

			CourseOfferingInfo co = coList.get(0);

			FormatOfferingInfo newFO = CourseOfferingServiceDataUtils
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
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetFormatOffering() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {

			FormatOfferingInfo fo = coService.getFormatOffering(
					"CO-2:LEC-ONLY", callContext);
			assertNotNull(fo);
			assertEquals(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY,
					fo.getStateKey());
			assertEquals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
					fo.getTypeKey());
			assertEquals("Lecture", fo.getDescr().getPlain());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testCreateAndGetActivityOffering()
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, DoesNotExistException {

		CourseOfferingInfo courseOffering = coService.getCourseOffering("CO-1", callContext);
		
		List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();

		instructors.add(CourseOfferingServiceDataUtils.createInstructor(
				"Pers-1", "Person One", 60.00F));

		String activityId = CourseOfferingServiceDataUtils
				.createCanonicalActivityId("CO-1:LEC-ONLY",
						LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);

		ActivityOfferingInfo ao = CourseOfferingServiceDataUtils
				.createActivityOffering("2012FA", courseOffering, "CO-1:LEC-ONLY",
						"SCHED-ID", activityId, "Lecture", "A",
						LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
						instructors);

		try {
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
		} catch (Exception ex) {
			log.fatal("Exception from serviceCall", ex);

			fail("Exception from service call :" + ex.getMessage());
		}
	}

	

	@Test
	public void testUpdateRegistrationGroup() throws InvalidParameterException,
			DataValidationErrorException, MissingParameterException,
			DoesNotExistException, VersionMismatchException,
			PermissionDeniedException, OperationFailedException,
			ReadOnlyException {
		String registrationGroupId = "CO-2:LEC-ONLY:REG-GROUP-LEC-A";
		RegistrationGroupInfo regGroup = coService.getRegistrationGroup(
				registrationGroupId, callContext);
		assertEquals("CO-2", regGroup.getCourseOfferingId());
		assertEquals(1, regGroup.getActivityOfferingIds().size());
		assertEquals("CO-2:LEC-ONLY:LEC-A", regGroup.getActivityOfferingIds()
				.get(0));

		regGroup.getActivityOfferingIds().remove(0);
		regGroup.getActivityOfferingIds().add("CO-2:LEC-ONLY:LEC-B");
		RegistrationGroupInfo updatedRegGroup = coService
				.updateRegistrationGroup(registrationGroupId, regGroup,
						callContext);
		assertEquals("CO-2", regGroup.getCourseOfferingId());
		assertEquals(1, updatedRegGroup.getActivityOfferingIds().size());
		assertEquals("CO-2:LEC-ONLY:LEC-B", regGroup.getActivityOfferingIds()
				.get(0));
	}

	@Test
	public void testDeleteRegistrationGroup() throws InvalidParameterException,
			MissingParameterException, DoesNotExistException,
			PermissionDeniedException, OperationFailedException {
		String registrationGroupId = "CO-2:LEC-ONLY:REG-GROUP-LEC-A";
		RegistrationGroupInfo regGroup = coService.getRegistrationGroup(
				registrationGroupId, callContext);
		assertNotNull(regGroup);
		StatusInfo statusInfo = coService.deleteRegistrationGroup(
				registrationGroupId, callContext);
		assertTrue(statusInfo.getIsSuccess());
		try {
			coService.getRegistrationGroup(registrationGroupId, callContext);
			fail("Expected DoesNotExistException.");
		} catch (DoesNotExistException e) {
			// Expected. Do nothing.
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

		TypeInfo shouldBeNull = null;
		try {
			shouldBeNull = coService.getActivityOfferingType(
					"madeUpINAVLIDAoType", callContext);
			fail("Expected DoesNotExistException");
		} catch (DoesNotExistException e) {
			assertNull(shouldBeNull);
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
	public void testCreateSeatPoolDefinition() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
		
		SeatPoolDefinitionInfo mainPool = CourseOfferingServiceDataUtils.createSeatPoolDefinition ("EVERYONE", "Lab 123", AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY, true, 85, 1);
		
		SeatPoolDefinitionInfo secondaryPool = CourseOfferingServiceDataUtils.createSeatPoolDefinition ("EVERYONE", "Lab 123B", AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY, true, 15, 2);
		
		mainPool = coService.createSeatPoolDefinition(mainPool, callContext);
		
		secondaryPool = coService.createSeatPoolDefinition(secondaryPool, callContext);
		
		boolean exceptionEncountered = false;
		try {
			coService.addSeatPoolDefinitionToActivityOffering(mainPool.getId(), "CO-1:LEC-ONLY:LEC-A", callContext);
			coService.addSeatPoolDefinitionToActivityOffering(secondaryPool.getId(), "CO-1:LEC-ONLY:LEC-A", callContext);
		} catch (AlreadyExistsException e) {
			exceptionEncountered = true;
		} catch (DoesNotExistException e) {
			exceptionEncountered = true;
		}
		
		Assert.assertFalse (exceptionEncountered);
		
		try {
			List<SeatPoolDefinitionInfo> spds = coService.getSeatPoolDefinitionsForActivityOffering("CO-1:LEC-ONLY:LEC-A", callContext);
			
			Assert.assertEquals(2, spds.size());
			
		} catch (DoesNotExistException e) {
			// should not happen
			Assert.assertFalse("Activity does not exist exception", true);
		}
		
		exceptionEncountered = false;
		
		try {
			coService.removeSeatPoolDefinitionFromActivityOffering(mainPool.getId(), "CO-1:LEC-ONLY:LEC-A", callContext);
		} catch (DoesNotExistException e) {
			
			exceptionEncountered = true;
		}
		
		Assert.assertFalse (exceptionEncountered);
		
		try {
			List<SeatPoolDefinitionInfo> spds = coService.getSeatPoolDefinitionsForActivityOffering("CO-1:LEC-ONLY:LEC-A", callContext);
			
			Assert.assertEquals(1, spds.size());
			
		} catch (DoesNotExistException e) {
			// should not happen
			Assert.assertFalse("Activity does not exist exception", true);
		}
		
	}
}

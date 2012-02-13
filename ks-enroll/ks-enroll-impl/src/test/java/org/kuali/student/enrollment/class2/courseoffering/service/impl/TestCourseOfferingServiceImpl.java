package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImpl {

    @Resource  // look up bean via variable name, then type
	private CourseOfferingService coServiceAuthDecorator;

    @Resource
    private GradingService gradingService;

    @Resource
    private LRCService lrcService;

    public static String principalId = "123";
    public ContextInfo callContext = null;

	@Before
    public void setUp() {
        callContext = new ContextInfo ();
        callContext.setPrincipalId(principalId);
    }

	@Test
    public void testServiceSetup() {
    	assertNotNull(coServiceAuthDecorator);
    }
	   
    @Test
    public void testGetCourseOffering() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        try{
            coServiceAuthDecorator.getCourseOffering("Lui-blah", callContext);
            fail("Lui-blah should have thrown DoesNotExistException");
        }
        catch (DoesNotExistException enee) {
            // expected
        }

        CourseOfferingInfo co = coServiceAuthDecorator.getCourseOffering("Lui-1", callContext);
        assertNotNull(co);
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, co.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co.getTypeKey());
        assertEquals("Lui Desc 101", co.getDescr().getPlain());
    }

    @Test
    public void testCreateCourseOfferingFromCanonical() throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	List<String> formatIdList = new ArrayList<String>();

    	CourseOfferingInfo created = coServiceAuthDecorator.createCourseOfferingFromCanonical(
                "CLU-1", "testAtpId1", formatIdList, callContext);
    	assertNotNull(created);
    	assertEquals("CLU-1", created.getCourseId());
    	assertEquals("testAtpId1", created.getTermId());
    	assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey()); 
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey()); 

        CourseOfferingInfo retrieved = coServiceAuthDecorator.getCourseOffering(created.getId(), callContext);
    	assertNotNull(retrieved);
    	assertEquals("CLU-1", retrieved.getCourseId());
    	assertEquals("testAtpId1", retrieved.getTermId());
    	assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey()); 
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, retrieved.getTypeKey()); 

        assertEquals("CHEM123", retrieved.getCourseOfferingCode());
        assertEquals("Chemistry 123", retrieved.getCourseTitle());

        // make sure a roster was created
        List<GradeRosterInfo> rosters =
                gradingService.getFinalGradeRostersForCourseOffering(retrieved.getId(), callContext);
        assertNotNull(rosters);
        assertEquals(1, rosters.size());

        // test update
        retrieved.setStateKey(LuiServiceConstants.LUI_APROVED_STATE_KEY);
        ResultValuesGroupInfo rv = new ResultValuesGroupInfo();
        rv.setStateKey("test");
        rv.setTypeKey("test");
        rv.setEffectiveDate(Calendar.getInstance().getTime());
        rv.setName("test");
        ResultValueRangeInfo rvr = new ResultValueRangeInfo();
        rv.setResultValueRange(rvr);
        lrcService.createResultValuesGroup(rv, callContext);
        retrieved.setCreditOptions(rv);
        coServiceAuthDecorator.updateCourseOffering(retrieved.getId(), retrieved, callContext);

        CourseOfferingInfo retrieved2 =
                coServiceAuthDecorator.getCourseOffering(retrieved.getId(), callContext);
        assertEquals(LuiServiceConstants.LUI_APROVED_STATE_KEY, retrieved2.getStateKey());
    }
    
    @Test
    public void testCreateAndGetActivityOffering()
            throws AlreadyExistsException, DataValidationErrorException,InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		ActivityOfferingInfo ao = new ActivityOfferingInfo();
		ao.setActivityId("CLU-1");
		ao.setTermId("testAtpId1");
		ao.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
		ao.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
		
		List<MeetingScheduleInfo> schedules = new ArrayList<MeetingScheduleInfo>();
		MeetingScheduleInfo schedule1 = new MeetingScheduleInfo();
		schedule1.setSpaceId("room 314");
		schedule1.setTimePeriods("19960415T083000");
		schedules.add(schedule1);
		MeetingScheduleInfo schedule2 = new MeetingScheduleInfo();
		schedule2.setSpaceId("room 316");
		schedule2.setTimePeriods("19960415T083000Z");
		schedules.add(schedule2);
		ao.setMeetingSchedules(schedules);
		
    	List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
    	OfferingInstructorInfo instructor = new OfferingInstructorInfo();
    	instructor.setPersonId("Pers-1");
    	instructor.setPercentageEffort(Float.valueOf("60"));
    	instructor.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
    	instructor.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
    	instructors.add(instructor);
    	ao.setInstructors(instructors);

        List<String> coIdList = Arrays.asList("Lui-1");

        ActivityOfferingInfo created =
                coServiceAuthDecorator.createActivityOffering(coIdList, ao, callContext);
        assertNotNull(created);

        ActivityOfferingInfo retrieved =
                coServiceAuthDecorator.getActivityOffering(created.getId(), callContext);
        assertNotNull(retrieved);

        assertEquals(created.getActivityId(), retrieved.getActivityId());
        assertEquals(created.getTermId(), retrieved.getTermId());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey());
        assertEquals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, retrieved.getTypeKey());
        assertEquals(2, retrieved.getMeetingSchedules().size());
        assertEquals(1, retrieved.getInstructors().size());

        // test getActivitiesForCourseOffering
        List<ActivityOfferingInfo> activities =
                coServiceAuthDecorator.getActivitiesForCourseOffering("Lui-1", callContext);
        assertNotNull(activities);
        assertEquals(1, activities.size());
        assertEquals(created.getActivityId(), activities.get(0).getActivityId());
        assertEquals(created.getId(), activities.get(0).getId());
        assertEquals(2, activities.get(0).getMeetingSchedules().size());
        assertEquals(1, activities.get(0).getInstructors().size());
    }
    
    @Test
	public void testCreateAndGetRegistrationGroup()
            throws AlreadyExistsException, DoesNotExistException,DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	String courseOfferingId = "Lui-1";
		RegistrationGroupInfo rg = new RegistrationGroupInfo();
		rg.setFormatId("CLU-1");
		rg.setName("RegGroup-1");
		rg.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
		rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
		
        RegistrationGroupInfo created =
                coServiceAuthDecorator.createRegistrationGroup(courseOfferingId, rg, callContext);
        assertNotNull(created);

        RegistrationGroupInfo retrieved =
                coServiceAuthDecorator.getRegistrationGroup(created.getId(), callContext);
        assertNotNull(retrieved);

        assertEquals(rg.getFormatId(), retrieved.getFormatId());
        assertEquals(rg.getName(), retrieved.getName());
        assertEquals(rg.getStateKey(), retrieved.getStateKey());
        assertEquals(rg.getTypeKey(), retrieved.getTypeKey());

        // test getRegGroupsForCourseOffering
        List<RegistrationGroupInfo> rgs =
                coServiceAuthDecorator.getRegGroupsForCourseOffering(courseOfferingId, callContext);
        assertNotNull(rgs);
        assertEquals(1, rgs.size());
        assertEquals(created.getFormatId(), rgs.get(0).getFormatId());
        assertEquals(created.getId(), rgs.get(0).getId());
        assertEquals(courseOfferingId, rgs.get(0).getCourseOfferingId());
	}
    
	@Test
    @Ignore
    // TODO: enable after https://jira.kuali.org/browse/KULRICE-6705 is fixed
    public void testUpdateCourseOffering()
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        CourseOfferingInfo coi = coServiceAuthDecorator.getCourseOffering("Lui-1", callContext);
        assertNotNull(coi);

        coi.setTermId("testAtpId1");
        coi.setIsHonorsOffering(true);
        coi.setMaximumEnrollment(40);
        coi.setMinimumEnrollment(10);
        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor = new OfferingInstructorInfo();
        instructor.setPersonId("Pers-1");
        instructor.setPercentageEffort(Float.valueOf("60"));
        instructor.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
        instructors.add(instructor);
        coi.setInstructors(instructors);
        CourseOfferingInfo updated =
                coServiceAuthDecorator.updateCourseOffering("Lui-1", coi, callContext);
        assertNotNull(updated);

        CourseOfferingInfo retrieved =
                coServiceAuthDecorator.getCourseOffering("Lui-1", callContext);
        assertNotNull(retrieved);

        assertTrue(retrieved.getIsHonorsOffering());
        assertEquals(1, retrieved.getInstructors().size());
        assertEquals(coi.getMaximumEnrollment(), retrieved.getMaximumEnrollment());
        assertEquals(coi.getMinimumEnrollment(), retrieved.getMinimumEnrollment());

        retrieved.setIsHonorsOffering(false);
        List<OfferingInstructorInfo> instructors1 = new ArrayList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor1 = new OfferingInstructorInfo();
        instructor1.setPersonId("Pers-2");
        instructor1.setPercentageEffort(Float.valueOf("60"));
        instructor1.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor1.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
        instructors1.add(instructor1);
        OfferingInstructorInfo instructor2 = new OfferingInstructorInfo();
        instructor2.setPersonId("Pers-1");
        instructor2.setPercentageEffort(Float.valueOf("30"));
        instructor2.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor2.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
        instructors1.add(instructor2);
        retrieved.setInstructors(instructors1);
        CourseOfferingInfo updated1 =
                coServiceAuthDecorator.updateCourseOffering("Lui-1", retrieved, callContext);
        assertNotNull(updated1);

        CourseOfferingInfo retrieved1 =
                coServiceAuthDecorator.getCourseOffering("Lui-1", callContext);
        assertNotNull(retrieved1);
        assertEquals(2, retrieved1.getInstructors().size());
    }   

    @Test
    public void testUpdateRegistrationGroup() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, VersionMismatchException, PermissionDeniedException, OperationFailedException {
        final String regGroupId = "LUI-RG-1";
        final String coId = "LUI-CO-1";
        final String aoId_1 = "LUI-ACT-1";
        final String aoId_2 = "LUI-ACT-2";
        RegistrationGroupInfo regGroup = coServiceAuthDecorator.getRegistrationGroup(regGroupId, callContext);
        assertEquals(coId, regGroup.getCourseOfferingId());
        assertEquals(1, regGroup.getActivityOfferingIds().size());
        assertEquals(aoId_1, regGroup.getActivityOfferingIds().get(0));

        regGroup.getActivityOfferingIds().remove(0);
        regGroup.getActivityOfferingIds().add(aoId_2);
        RegistrationGroupInfo updatedRegGroup = coServiceAuthDecorator.updateRegistrationGroup(regGroupId, regGroup, callContext);
        assertEquals(coId, updatedRegGroup.getCourseOfferingId());
        assertEquals(1, updatedRegGroup.getActivityOfferingIds().size());
        assertEquals(aoId_2, updatedRegGroup.getActivityOfferingIds().get(0));
    }

    @Test
    public void testDeleteRegistrationGroup() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        final String regGroupId = "LUI-RG-1";
        RegistrationGroupInfo regGroup = coServiceAuthDecorator.getRegistrationGroup(regGroupId, callContext);
        assertNotNull(regGroup);
        StatusInfo statusInfo = coServiceAuthDecorator.deleteRegistrationGroup(regGroupId, callContext);
        assertTrue(statusInfo.getIsSuccess());
        try {
            coServiceAuthDecorator.getRegistrationGroup(regGroupId, callContext);
            fail("Expected DoesNotExistException.");
        } catch (DoesNotExistException e) {
            // Expected. Do nothing.
        }
    }

}

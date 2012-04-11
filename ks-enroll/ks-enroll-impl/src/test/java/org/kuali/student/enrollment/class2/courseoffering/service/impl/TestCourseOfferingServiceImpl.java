package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.Ignore;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiTestDataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

// TODO: delete this file this once all the tests have been moved over to the new mock framework
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImpl {

    @Resource (name="coServiceAuthDecorator") 
    private CourseOfferingService courseOfferingService;
    @Resource (name="luiService") 
    private LuiService luiService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new LuiTestDataLoader(this.luiService).loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void testServiceSetup() {
        assertNotNull(courseOfferingService);
    }

    /*************************************************************************
     * COURSE TESTS
     *************************************************************************/
    @Test
    public void testGetCourseOffering() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            try {
                courseOfferingService.getCourseOffering("Lui-blah", callContext);
                fail("Lui-blah should have thrown DoesNotExistException");
            } catch (DoesNotExistException enee) {
                // expected
            }

            CourseOfferingInfo co = courseOfferingService.getCourseOffering("Lui-1", callContext);
            assertNotNull(co);
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, co.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co.getTypeKey());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetCourseOfferingIdsByTerm() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            try {
                courseOfferingService.getCourseOfferingIdsByTerm("TermId-blah", true, callContext);
                fail("TermId-blah should have thrown DoesNotExistException");
            } catch (DoesNotExistException enee) {
                // expected
            }

            List<String> idList = courseOfferingService.getCourseOfferingIdsByTerm("testAtpId1", true, callContext);
            assertNotNull(idList);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetCourseOfferingsByIds() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        /*********************************************************************
        List<String> idsList = new ArrayList<String>();
        idsList.add("test1");
        idsList.add("test2");
        idsList.add("test3");
        
        try {
        try {
        coServiceAuthDecorator.getCourseOfferingsByIds(idsList, callContext);
        fail("idsList should have thrown DoesNotExistException");
        } catch (DoesNotExistException enee) {
        // expected
        }
        
        List<CourseOfferingInfo> co = coServiceAuthDecorator.getCourseOfferingsByIds(idsList, callContext);
        
        assertNotNull(co);
        for (CourseOfferingInfo coItem : co) {
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, coItem.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coItem.getTypeKey());
        }
        } catch (Exception ex) {
        fail(ex.getMessage());
        }
         *********************************************************************/
    }

    @Test
    public void testGetCourseOfferingsByCourseAndTerm() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            try {
                courseOfferingService.getCourseOfferingsByCourseAndTerm("Lui-blah", "TermId-blah", callContext);
                fail("Lui-blah and TermId-blah should have thrown DoesNotExistException");
            } catch (DoesNotExistException enee) {
                // expected
            }

            List<CourseOfferingInfo> co = courseOfferingService.getCourseOfferingsByCourseAndTerm("Lui-1", "testAtpId1", callContext);
            assertNotNull(co);

            for (CourseOfferingInfo coItem : co) {
                assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, coItem.getStateKey());
                assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coItem.getTypeKey());
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testCreateCourseOffering() throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        String formatId = null;
        CourseOfferingInfo coInfo = new CourseOfferingInfo();
        CourseOfferingInfo created = courseOfferingService.createCourseOffering("CLU-1", "testAtpId1", formatId, coInfo, callContext);

        assertNotNull(created);
        assertEquals("CLU-1", created.getCourseId());
        assertEquals("testAtpId1", created.getTermId());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey());

        // TODO Is this necessary?
        CourseOfferingInfo retrieved = courseOfferingService.getCourseOffering(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals("CLU-1", retrieved.getCourseId());
        assertEquals("testAtpId1", retrieved.getTermId());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, retrieved.getTypeKey());

        assertEquals("CHEM123", retrieved.getCourseOfferingCode());
        assertEquals("Chemistry 123", retrieved.getCourseTitle());
    }

    @Test
    public void testUpdateCourseOffering() throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        try {
            CourseOfferingInfo coi = courseOfferingService.getCourseOffering("Lui-1", callContext);
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
                    courseOfferingService.updateCourseOffering("Lui-1", coi, callContext);
            assertNotNull(updated);

            CourseOfferingInfo retrieved =
                    courseOfferingService.getCourseOffering("Lui-1", callContext);
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
                    courseOfferingService.updateCourseOffering("Lui-1", retrieved, callContext);
            assertNotNull(updated1);

            CourseOfferingInfo retrieved1 =
                    courseOfferingService.getCourseOffering("Lui-1", callContext);
            assertNotNull(retrieved1);
            assertEquals(2, retrieved1.getInstructors().size());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testDeleteCourseOffering() throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // Create a CO
        String formatId = null;
        CourseOfferingInfo coInfo = new CourseOfferingInfo();
        CourseOfferingInfo created = courseOfferingService.createCourseOffering("CLU-1", "testAtpId1", formatId, coInfo, callContext);

        // Verify that the CO was created
        assertNotNull(created);
        assertEquals("CLU-1", created.getCourseId());
        assertEquals("testAtpId1", created.getTermId());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey());

        try {
            // Delete the course offering and check that the status returned was a success
            StatusInfo delResult = courseOfferingService.deleteCourseOffering("CLU-1", callContext);
            assertTrue(delResult.getIsSuccess());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testCreateFormatOffering() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {

            FormatOfferingInfo newFO = new FormatOfferingInfo();
            newFO.setActivityOfferingTypeKeys(Arrays.asList(LuiServiceConstants.ALL_ACTIVITY_TYPES));
            newFO.setName("TEST FORMAT OFFERING");
            newFO.setCourseOfferingId("Lui-1");
            FormatOfferingInfo fo = courseOfferingService.createFormatOffering("Lui-1", "format1", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, newFO, callContext);
            assertNotNull(fo);
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, fo.getStateKey());
            assertEquals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, fo.getTypeKey());
            assertEquals("Lui Desc 101", fo.getDescr().getPlain());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetFormatOffering() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {


            FormatOfferingInfo fo = courseOfferingService.getFormatOffering("luiFormat-1", callContext);
            assertNotNull(fo);
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, fo.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, fo.getTypeKey());
            assertEquals("Lui Desc 101", fo.getDescr().getPlain());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testCreateAndGetActivityOffering()
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        ao.setActivityId("CLU-1");
        ao.setTermId("testAtpId1");
        ao.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        ao.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
        OfferingInstructorInfo instructor = new OfferingInstructorInfo();
        instructor.setPersonId("Pers-1");
        instructor.setPercentageEffort(Float.valueOf("60"));
        instructor.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
        instructors.add(instructor);
        ao.setInstructors(instructors);

        List<String> coIds = Arrays.asList();

        try {
            ActivityOfferingInfo created =
                    courseOfferingService.createActivityOffering("FormatOfferingId", "ActivityId", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, ao, callContext);
            assertNotNull(created);

            ActivityOfferingInfo retrieved =
                    courseOfferingService.getActivityOffering(created.getId(), callContext);
            assertNotNull(retrieved);

            assertEquals(created.getActivityId(), retrieved.getActivityId());
            assertEquals(created.getTermId(), retrieved.getTermId());
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey());
            assertEquals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, retrieved.getTypeKey());
            assertEquals(1, retrieved.getInstructors().size());

            // test getActivityOfferingsByCourseOffering
            List<ActivityOfferingInfo> activities =
                    courseOfferingService.getActivityOfferingsByCourseOffering("Lui-1", callContext);
            assertNotNull(activities);
            assertEquals(1, activities.size());
            assertEquals(created.getActivityId(), activities.get(0).getActivityId());
            assertEquals(created.getId(), activities.get(0).getId());
            assertEquals(1, activities.get(0).getInstructors().size());
        } catch (Exception ex) {
            //ex.printStackTrace();
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testCreateAndGetRegistrationGroup()
            throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        String courseOfferingId = "Lui-1";
        RegistrationGroupInfo rg = new RegistrationGroupInfo();
        rg.setFormatId("CLU-1");
        rg.setName("RegGroup-1");
        rg.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);

        try {
            RegistrationGroupInfo created =
                    courseOfferingService.createRegistrationGroup(courseOfferingId, rg, callContext);
            assertNotNull(created);

            RegistrationGroupInfo retrieved =
                    courseOfferingService.getRegistrationGroup(created.getId(), callContext);
            assertNotNull(retrieved);

            assertEquals(rg.getFormatId(), retrieved.getFormatId());
            assertEquals(rg.getName(), retrieved.getName());
            assertEquals(rg.getStateKey(), retrieved.getStateKey());
            assertEquals(rg.getTypeKey(), retrieved.getTypeKey());

            // test getRegistrationGroupsForCourseOffering
            List<RegistrationGroupInfo> rgs =
                    courseOfferingService.getRegistrationGroupsForCourseOffering(courseOfferingId, callContext);
            assertNotNull(rgs);
            assertEquals(1, rgs.size());
            assertEquals(created.getFormatId(), rgs.get(0).getFormatId());
            assertEquals(created.getId(), rgs.get(0).getId());
            assertEquals(courseOfferingId, rgs.get(0).getCourseOfferingId());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testSearchForCourseOfferings() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.like("name", "*three*"));
            QueryByCriteria qbc = qbcBuilder.build();
            List<CourseOfferingInfo> coList = courseOfferingService.searchForCourseOfferings(qbc, callContext);
            assertNotNull(coList);
            assertEquals(1, coList.size());
            CourseOfferingInfo coInfo = coList.get(0);
            assertEquals("Lui-3", coInfo.getId());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testUpdateRegistrationGroup() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, VersionMismatchException, PermissionDeniedException, OperationFailedException {
        final String regGroupId = "LUI-RG-1";
        final String coId = "LUI-CO-1";
        final String aoId_1 = "LUI-ACT-1";
        final String aoId_2 = "LUI-ACT-2";
        RegistrationGroupInfo regGroup = courseOfferingService.getRegistrationGroup(regGroupId, callContext);
        assertEquals(coId, regGroup.getCourseOfferingId());
        assertEquals(1, regGroup.getActivityOfferingIds().size());
        assertEquals(aoId_1, regGroup.getActivityOfferingIds().get(0));

        regGroup.getActivityOfferingIds().remove(0);
        regGroup.getActivityOfferingIds().add(aoId_2);
        RegistrationGroupInfo updatedRegGroup = courseOfferingService.updateRegistrationGroup(regGroupId, regGroup, callContext);
        assertEquals(coId, updatedRegGroup.getCourseOfferingId());
        assertEquals(1, updatedRegGroup.getActivityOfferingIds().size());
        assertEquals(aoId_2, updatedRegGroup.getActivityOfferingIds().get(0));
    }

    @Test
    public void testDeleteRegistrationGroup() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        final String regGroupId = "LUI-RG-1";
        RegistrationGroupInfo regGroup = courseOfferingService.getRegistrationGroup(regGroupId, callContext);
        assertNotNull(regGroup);
        StatusInfo statusInfo = courseOfferingService.deleteRegistrationGroup(regGroupId, callContext);
        assertTrue(statusInfo.getIsSuccess());
        try {
            courseOfferingService.getRegistrationGroup(regGroupId, callContext);
            fail("Expected DoesNotExistException.");
        } catch (DoesNotExistException e) {
            // Expected. Do nothing.
        }
    }
}

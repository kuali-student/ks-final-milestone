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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ocleirig
 * 
 *         This is a set of unit test cases that runs directly against a mock
 *         implementation of the class 2 course offering service.
 * 
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:co-test-with-class2-mock-context.xml" })
public class TestCourseOfferingServiceMockImpl {

	private static final Logger log = Logger.getLogger(TestCourseOfferingServiceMockImpl.class);
	
	@Resource
	private CourseOfferingService coService;

	@Resource
	private AcademicCalendarService acalService;

	@Resource
	private AtpService atpService;
	
	@Resource
	private org.kuali.student.lum.course.service.CourseService canonicalCourseService;
	
	/**
	 * 
	 */
	public TestCourseOfferingServiceMockImpl() {
	}

	public static String principalId = "123";
	public ContextInfo callContext = null;


	@Before
	public void setup() throws Exception {
		
		callContext = new ContextInfo();
		callContext.setPrincipalId(principalId);
	
	}

	 @Test
	    public void testCreateAndGetRegistrationGroup()
	            throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException,
	            InvalidParameterException, MissingParameterException, OperationFailedException,
	            PermissionDeniedException {
	        String formatOfferingId = "Lui-1";
	        RegistrationGroupInfo rg = new RegistrationGroupInfo();
	        rg.setFormatOfferingId("CLU-1");
	        rg.setName("RegGroup-1");
	        rg.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
	        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);

	        try {
	            RegistrationGroupInfo created =
	                    coService.createRegistrationGroup(formatOfferingId, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rg, callContext);
	            assertNotNull(created);

	            RegistrationGroupInfo retrieved =
	                    coService.getRegistrationGroup(created.getId(), callContext);
	            assertNotNull(retrieved);

	            assertEquals(rg.getFormatOfferingId(), retrieved.getFormatOfferingId());
	            assertEquals(rg.getName(), retrieved.getName());
	            assertEquals(rg.getStateKey(), retrieved.getStateKey());
	            assertEquals(rg.getTypeKey(), retrieved.getTypeKey());

	            // test getRegistrationGroupsForCourseOffering

	        } catch (Exception ex) {
	            fail("Exception from service call :" + ex.getMessage());
	        }
	    }

	 @Test
	    public void testServiceSetup() {
	        assertNotNull(coService);
	    }

	    /*************************************************************************
	     * COURSE TESTS
	     *************************************************************************/
	    @Test
	    public void testGetCourseOffering() throws DoesNotExistException, InvalidParameterException,
	            MissingParameterException, OperationFailedException, PermissionDeniedException {
	        try {
	            try {
	                coService.getCourseOffering("Lui-blah", callContext);
	                fail("Lui-blah should have thrown DoesNotExistException");
	            } catch (DoesNotExistException enee) {
	                // expected
	            }

	            CourseOfferingInfo co = coService.getCourseOffering("CO-1", callContext);
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
	            List<String> offerings = coService.getCourseOfferingIdsByTerm("TermId-blah", true, callContext);

	            assertEquals (0, offerings.size());
	            
	            List<String> idList = coService.getCourseOfferingIdsByTerm("2012FA", true, callContext);
	            assertTrue(idList.size() > 0);
	    }

	    @Test
	    @Ignore
	    public void testGetCourseOfferingsByIds() throws DoesNotExistException, InvalidParameterException,
	            MissingParameterException, OperationFailedException, PermissionDeniedException {
	        /*********************************************************************
	        List<String> idsList = new ArrayList<String>();
	        idsList.add("test1");
	        idsList.add("test2");
	        idsList.add("test3");
	        
	        try {
	        try {
	        coServiceAuthDecorator.getCourseOfferingsByIds(idsList, contextInfo);
	        fail("idsList should have thrown DoesNotExistException");
	        } catch (DoesNotExistException enee) {
	        // expected
	        }
	        
	        List<CourseOfferingInfo> co = coServiceAuthDecorator.getCourseOfferingsByIds(idsList, contextInfo);
	        
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
	             List<CourseOfferingInfo> offerings = coService.getCourseOfferingsByCourseAndTerm("Lui-blah", "TermId-blah", callContext);

	             assertEquals(0, offerings.size());
	             
	            List<CourseOfferingInfo> co = coService.getCourseOfferingsByCourseAndTerm("CLU-1", "2012FA", callContext);
	            assertTrue(co.size() > 0);

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
	                                                  OperationFailedException, PermissionDeniedException, ReadOnlyException {
	    	
	    	List<CourseOfferingInfo> offerings = coService.getCourseOfferingsByCourse("CLU-1", callContext);
	    	
	    	int expectedOfferings = offerings.size() + 1;
	    	
	        List<String> optionKeys = new ArrayList<String>();
	        CourseOfferingInfo coInfo = CourseOfferingServiceDataUtils.createCourseOffering("CLU-1", "2012FA", "Chemistry 123", "CHEM123");
	        CourseOfferingInfo created = coService.createCourseOffering("CLU-1", "2012FA", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo, optionKeys, callContext);

	        assertNotNull(created);
	        assertEquals("CLU-1", created.getCourseId());
	        assertEquals("2012FA", created.getTermId());
	        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey());
	        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey());
	        assertEquals("CHEM123", created.getCourseOfferingCode());
	        assertEquals("Chemistry 123", created.getCourseOfferingTitle());
	        
	        
	        CourseOfferingInfo retrieved = coService.getCourseOffering(created.getId(), callContext);
	        assertNotNull(retrieved);
	        assertEquals("CLU-1", retrieved.getCourseId());
	        assertEquals("2012FA", retrieved.getTermId());
	        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey());
	        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, retrieved.getTypeKey());

	        assertEquals("CHEM123", retrieved.getCourseOfferingCode());
	        assertEquals("Chemistry 123", retrieved.getCourseOfferingTitle());
	        
	        offerings = coService.getCourseOfferingsByCourse("CLU-1", callContext);
	        
	        // we and maybe another test have added one
	        assertEquals(expectedOfferings, offerings.size());
	    }

	    @Test
	    public void testUpdateCourseOffering() throws DataValidationErrorException,
	            DoesNotExistException, InvalidParameterException, MissingParameterException,
	            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
	        try {
	            CourseOfferingInfo coi = coService.getCourseOffering("Lui-1", callContext);
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
	            CourseOfferingInfo updated =
	                    coService.updateCourseOffering("Lui-1", coi, callContext);
	            assertNotNull(updated);

	            CourseOfferingInfo retrieved =
	                    coService.getCourseOffering("Lui-1", callContext);
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
	            instructor1.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
	            instructor1.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
	            instructors1.add(instructor1);
	            OfferingInstructorInfo instructor2 = new OfferingInstructorInfo();
	            instructor2.setPersonId("Pers-1");
	            instructor2.setPercentageEffort(Float.valueOf("30"));
	            instructor2.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
	            instructor2.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
	            instructors1.add(instructor2);
	            retrieved.setInstructors(instructors1);
	            CourseOfferingInfo updated1 =
	                    coService.updateCourseOffering("Lui-1", retrieved, callContext);
	            assertNotNull(updated1);

	            CourseOfferingInfo retrieved1 =
	                    coService.getCourseOffering("Lui-1", callContext);
	            assertNotNull(retrieved1);
	            assertEquals(2, retrieved1.getInstructors().size());
	        } catch (Exception ex) {
	            fail("Exception from service call :" + ex.getMessage());
	        }
	    }

	        @Test
	    public void testUpdateCourseOfferingWithDynAttrs() throws DataValidationErrorException,
	            DoesNotExistException, InvalidParameterException, MissingParameterException,
	            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
	        try {
	        	
	        	 List<String> optionKeys = new ArrayList<String>();
	 	        CourseOfferingInfo coInfo = CourseOfferingServiceDataUtils.createCourseOffering("CLU-1", "testAtpId1", "Chemistry 123", "CHEM123");
	 	        
	 	        CourseOfferingInfo created = coService.createCourseOffering("CLU-1", "testAtpId1", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo, optionKeys, callContext);
	 	        
	            CourseOfferingInfo coi = coService.getCourseOffering(created.getId(), callContext);
	            assertNotNull(coi);

	            coi.setTermId("atpId1");

	            //dynamic attributes
	            coi.setFundingSource("state");
	            CourseOfferingInfo updated =
	                    coService.updateCourseOffering(coi.getId(), coi, callContext);
	            assertNotNull(updated);

	            CourseOfferingInfo retrieved =
	                    coService.getCourseOffering(coi.getId(), callContext);
	            assertNotNull(retrieved);

	            assertEquals("state", coi.getFundingSource());
	            // TODO: fix once waitlists are implemented
//	            assertEquals("WaitlistLevelType1", coi.getWaitlistLevelTypeKey());
	        } catch (Exception ex) {
	            fail("Exception from service call :" + ex.getMessage());
	        }
	    }
	    @Test
	    public void testDeleteCourseOffering() throws AlreadyExistsException, DoesNotExistException,
	            DataValidationErrorException, InvalidParameterException, MissingParameterException,
	                                                  OperationFailedException, PermissionDeniedException, ReadOnlyException {
	        // Create a CO
	        String formatId = null;
	        CourseOfferingInfo coInfo = new CourseOfferingInfo();
	        List<String> optionKeys = new ArrayList<String>();
	        CourseOfferingInfo created = coService.createCourseOffering("CLU-1", "testAtpId1", formatId, coInfo, optionKeys, callContext);

	        // Verify that the CO was created
	        assertNotNull(created);
	        assertEquals("CLU-1", created.getCourseId());
	        assertEquals("testAtpId1", created.getTermId());
	        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey());
	        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey());

	        try {
	            // Delete the course offering and check that the status returned was a success
	            StatusInfo delResult = coService.deleteCourseOffering("CLU-1", callContext);
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

	        	List<CourseOfferingInfo> coList = coService.getCourseOfferingsByCourse("CLU-1", callContext);
	        	
	        	assertTrue (coList.size() > 0);
	        	
	        	CourseOfferingInfo co = coList.get(0);
	        	
	            FormatOfferingInfo newFO = CourseOfferingServiceDataUtils.createFormatOffering(co.getId(), "format1", co.getTermId(), "TEST FORMAT OFFERING", LuiServiceConstants.ALL_ACTIVITY_TYPES);
	            FormatOfferingInfo fo = coService.createFormatOffering(co.getId(), "format1", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, newFO, callContext);
	            assertNotNull(fo);
	            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, fo.getStateKey());
	            assertEquals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, fo.getTypeKey());
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


	            FormatOfferingInfo fo = coService.getFormatOffering("luiFormat-1", callContext);
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
	    	
	    	  List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
	    	  
	    	  instructors.add(CourseOfferingServiceDataUtils.createInstructor("Pers-1", "Person One", 60.00F));
	    	  
	    	String formatId = "COURSE1-FORMAT1";
			String activityId = formatId + "-" + LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY;
			
			ActivityOfferingInfo ao = CourseOfferingServiceDataUtils.createActivityOffering("2012FA", "CO-1", "CO-1:FO-1", "SCHED-ID", activityId, "Lecture", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
	    	
	        try { 
	            ActivityOfferingInfo created =
	                    coService.createActivityOffering("CO-1:FO-1", activityId, LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, ao, callContext);
	            assertNotNull(created);

	            ActivityOfferingInfo retrieved =
	                    coService.getActivityOffering(created.getId(), callContext);
	            assertNotNull(retrieved);

	            assertEquals(created.getActivityId(), retrieved.getActivityId());
	            assertEquals(created.getTermId(), retrieved.getTermId());
	            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey());
	            assertEquals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, retrieved.getTypeKey());
	            assertEquals(1, retrieved.getInstructors().size());

	            // test getActivityOfferingsByCourseOffering
	            List<ActivityOfferingInfo> activities =
	                    coService.getActivityOfferingsByCourseOffering("CO-1", callContext); 
	            assertNotNull(activities);
	            // 3 existing plus this new one 
	            // this one should probably not have been added so this test case may need to be adapted
	            // in the future.
	            assertEquals(4, activities.size());
	            assertEquals(created.getActivityId(), activities.get(0).getActivityId());
	            assertEquals(created.getId(), activities.get(0).getId());
	            assertEquals(1, activities.get(0).getInstructors().size());
	        } catch (Exception ex) {
	        	log.fatal("Exception from serviceCall", ex);
	        	
	            fail("Exception from service call :" + ex.getMessage());
	        }
	    }

	   
	    @Test
	    @Ignore
	    // there is name property on CourseOffering right now so this will have to be adjusted later.
	    public void testSearchForCourseOfferings() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	        try {
	            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
	            qbcBuilder.setPredicates(PredicateFactory.like("name", "*three*"));
	            QueryByCriteria qbc = qbcBuilder.build();
	            List<CourseOfferingInfo> coList = coService.searchForCourseOfferings(qbc, callContext);
	            assertNotNull(coList);
	            assertEquals(1, coList.size());
	            CourseOfferingInfo coInfo = coList.get(0);
	            assertEquals("Lui-3", coInfo.getId());
	        } catch (Exception ex) {
	            fail("Exception from service call :" + ex.getMessage());
	        }
	    }

	    @Test
	    public void testUpdateRegistrationGroup() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, VersionMismatchException, PermissionDeniedException, OperationFailedException, ReadOnlyException {
	        final String regGroupId = "LUI-RG-1";
	        final String coId = "LUI-CO-1";
	        final String aoId_1 = "LUI-ACT-1";
	        final String aoId_2 = "LUI-ACT-2";
	        RegistrationGroupInfo regGroup = coService.getRegistrationGroup(regGroupId, callContext);
	        assertEquals(coId, regGroup.getCourseOfferingId());
	        assertEquals(1, regGroup.getActivityOfferingIds().size());
	        assertEquals(aoId_1, regGroup.getActivityOfferingIds().get(0));

	        regGroup.getActivityOfferingIds().remove(0);
	        regGroup.getActivityOfferingIds().add(aoId_2);
	        RegistrationGroupInfo updatedRegGroup = coService.updateRegistrationGroup(regGroupId, regGroup, callContext);
	        assertEquals(coId, updatedRegGroup.getCourseOfferingId());
	        assertEquals(1, updatedRegGroup.getActivityOfferingIds().size());
	        assertEquals(aoId_2, updatedRegGroup.getActivityOfferingIds().get(0));
	    }

	    @Test
	    public void testDeleteRegistrationGroup() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
	        final String regGroupId = "LUI-RG-1";
	        RegistrationGroupInfo regGroup = coService.getRegistrationGroup(regGroupId, callContext);
	        assertNotNull(regGroup);
	        StatusInfo statusInfo = coService.deleteRegistrationGroup(regGroupId, callContext);
	        assertTrue(statusInfo.getIsSuccess());
	        try {
	            coService.getRegistrationGroup(regGroupId, callContext);
	            fail("Expected DoesNotExistException.");
	        } catch (DoesNotExistException e) {
	            // Expected. Do nothing.
	        }
	    }

	    @Test
	    public void testGetActivityOfferingType() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
	        TypeInfo validType = coService.getActivityOfferingType(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, callContext);
	        assertNotNull(validType);
	        assertEquals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, validType.getKey());

	        TypeInfo shouldBeNull = null;
	        try {
	            shouldBeNull = coService.getActivityOfferingType("madeUpINAVLIDAoType", callContext);
	            fail("Expected DoesNotExistException");
	        }
	        catch (DoesNotExistException e) {
	            assertNull(shouldBeNull);
	        }
	    }

	    @Test
	    public void testGetActivityOfferingTypes() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
	        List<TypeInfo> validTypes = coService.getActivityOfferingTypes(callContext);

	        assertNotNull(validTypes);
	        assertTrue("Expecting at least one activity offering type", !validTypes.isEmpty());

	        boolean found = false;
	        for(TypeInfo type : validTypes) {
	            found = type.getKey().equals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
	            if(found) {
	                break;
	            }
	        }

	        assertTrue("Expecting to find at least " + LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY + " type.", found);
	    }

}

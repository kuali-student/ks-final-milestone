/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.ap.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceDecorator;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.MetaTester;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//import javax.ejb.EJB;
//
//import org.hibernate.ejb.event.EJB3AutoFlushEventListener;
//import org.kuali.student.ap.academicplan.service.AcademicPlanServiceImpl;
//import org.springframework.beans.factory.annotation.Autowire;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-ap-test-context.xml"})
public abstract class TestAcademicPlanServiceImplConformanceBaseCrud {

	// ====================
	// SETUP
	// ====================

    public AcademicPlanService testService;

    //Get service without the validation decorator
    // ...for some test cases to avoid courseId validation....
    // ....until we can implement a mapImpl for the CourseService
    AcademicPlanService testServiceNoValidator;

    public AcademicPlanService getAcademicPlanService() { return testService; }
	public void setAcademicPlanService(AcademicPlanService service) { testService = service; }

	public ContextInfo contextInfo = null;
	public static String principalId = "123";

    private final AcademicPlanServiceTstHelper testHelper = new AcademicPlanServiceTstHelper();

	@Before
	public void setUp() throws Exception {
        DefaultKsapContext.before("student1");
        contextInfo=KsapFrameworkServiceLocator.getContext().getContextInfo();
        testService= KsapFrameworkServiceLocator.getAcademicPlanService();
		principalId = "student1";
		contextInfo.setPrincipalId(principalId);

        testHelper.createKsapTypes();

        testServiceNoValidator=testService;
        while (testServiceNoValidator!=null && !(testServiceNoValidator instanceof AcademicPlanServiceImpl)) {
            testServiceNoValidator = ((AcademicPlanServiceDecorator)testService).getNextDecorator();
        }
    }
	
	// ====================
	// TESTING
	// ====================
	
	// ****************************************************
	//           LearningPlanInfo
	// ****************************************************
	@Test
	public void testCrudLearningPlan()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException, AlreadyExistsException {
			// -------------------------------------
			// test create
			// -------------------------------------
            LearningPlanInfo original = new LearningPlanInfo ();

            // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
            testCrudLearningPlan_setDTOFieldsForTestCreate (original);
			new AttributeTester().add2ForCreate(original.getAttributes());

            LearningPlanInfo expected = new LearningPlanInfo (original);

			// code to create actual
			LearningPlanInfo actual = testService.createLearningPlan ( expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);

			// METHOD TO TEST DTO FIELDS HERE FOR TEST update after create
            //note: we will test against orginal...just because expected could have been altered by the create method
			testCrudLearningPlan_testDTOFieldsForTestCreateUpdate (original, actual);

            //Now we have to check attributes of expected vs actual because the id's were set by create
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;  //set expected to actual...before the get call
			actual = testService.getLearningPlan ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudLearningPlan_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
            new AttributeTester().delete1Update1Add1ForUpdate(actual.getAttributes());
            testCrudLearningPlan_setDTOFieldsForTestUpdate (actual);

            //get copy to test after update
            expected = new LearningPlanInfo (actual);

			// code to save updates
			actual = testService.updateLearningPlan ( actual.getId(), actual, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudLearningPlan_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

			// Test that VersionMismatchException's are being detected
			boolean exception = false;
			try {
   			    testService.updateLearningPlan ( expected.getId(), expected, contextInfo);
			} catch (VersionMismatchException e) {
       			exception = true;
            }

			Assert.assertTrue("VersionMismatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;

			// code to get actual
			actual = testService.getLearningPlan ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudLearningPlan_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

            // create a 2nd DTO
			LearningPlanInfo alphaDTO =new LearningPlanInfo ();
            alphaDTO.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN_TEMPLATE);
            LearningPlanInfo betaDTO = new LearningPlanInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
            testCrudLearningPlan_setDTOFieldsForTestCreate (alphaDTO);
            testCrudLearningPlan_setDTOFieldsForTestCreate (betaDTO);
            new AttributeTester().add2ForCreate(alphaDTO.getAttributes());
            new AttributeTester().add2ForCreate(betaDTO.getAttributes());
			testCrudLearningPlan_setDTOFieldsForTestReadAfterUpdate (alphaDTO);
            testCrudLearningPlan_setDTOFieldsForTestReadAfterUpdate (betaDTO);
            alphaDTO = testService.createLearningPlan ( alphaDTO, contextInfo);
            betaDTO = testService.createLearningPlan ( betaDTO, contextInfo);

			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> learningPlanIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<LearningPlanInfo> records = testService.getLearningPlansByIds ( learningPlanIds, contextInfo);
			
			assertEquals(learningPlanIds.size(), records.size());
			assertEquals(0, learningPlanIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			learningPlanIds = new ArrayList<String>();
			learningPlanIds.add(alphaDTO.getId());
			learningPlanIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getLearningPlansByIds ( learningPlanIds, contextInfo);
			
			assertEquals(learningPlanIds.size(), records.size());
			for (LearningPlanInfo record : records)
			{
					if (!learningPlanIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, learningPlanIds.size());

//NOTE:  these test are commented out because we don't currently have a need
// a getLearningPlanIdsByType(...) method; we will added it if/when the need arises

			// -------------------------------------
//			// test get by type
//			// -------------------------------------
//			// code to get by specific type "typeKey01"
//			learningPlanIds = testService.getLearningPlanIdsByType ("typeKey_Updated", contextInfo);
//
//			assertEquals(1, learningPlanIds.size());
//			assertEquals(alphaDTO.getId(), learningPlanIds.get(0));
//
//			// test get by other type
//			// code to get by specific type "typeKeyBeta"
//			learningPlanIds = testService.getLearningPlanIdsByType ("typeKeyBeta", contextInfo);
//
//			assertEquals(1, learningPlanIds.size());
//			assertEquals(betaDTO.getId(), learningPlanIds.get(0));
//
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteLearningPlan ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					LearningPlanInfo record = testService.getLearningPlan ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}

	}
	
	/*
		A method to set the fields for a LearningPlan in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudLearningPlan_setDTOFieldsForTestCreate(LearningPlanInfo expected);
	
	/*
		A method to test the fields for a LearningPlan. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudLearningPlan_testDTOFieldsForTestCreateUpdate(LearningPlanInfo expected, LearningPlanInfo actual);
	
	/*
		A method to set the fields for a LearningPlan in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudLearningPlan_setDTOFieldsForTestUpdate(LearningPlanInfo expected);
	
	/*
		A method to test the fields for a LearningPlan after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudLearningPlan_testDTOFieldsForTestReadAfterUpdate(LearningPlanInfo expected, LearningPlanInfo actual);
	
	/*
		A method to set the fields for a LearningPlan in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudLearningPlan_setDTOFieldsForTestReadAfterUpdate(LearningPlanInfo expected);
	
	
	// ****************************************************
	//           PlanItemInfo
	// ****************************************************
	@Test
	public void testCrudPlanItem()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException, AlreadyExistsException {

            LearningPlanInfo learningPlan = new LearningPlanInfo();

            // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
            testCrudLearningPlan_setDTOFieldsForTestCreate(learningPlan);
            new AttributeTester().add2ForCreate(learningPlan.getAttributes());

            // code to create actual
            learningPlan = testService.createLearningPlan(learningPlan, contextInfo);


            // -------------------------------------
			// test create
			// -------------------------------------
            PlanItemInfo planItem = new PlanItemInfo ();
            planItem.setLearningPlanId(learningPlan.getId());

			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
            testCrudPlanItem_setDTOFieldsForTestCreate(planItem);
			new AttributeTester().add2ForCreate(planItem.getAttributes());
            PlanItemInfo expected = new PlanItemInfo (planItem);

            // code to create actual
			PlanItemInfo actual = testServiceNoValidator.createPlanItem ( expected, contextInfo);

			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudPlanItem_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getPlanItem ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudPlanItem_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = new PlanItemInfo (actual);

            expected.setLearningPlanId(learningPlan.getId());
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudPlanItem_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());

        	// code to update
			actual = testServiceNoValidator.updatePlanItem ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudPlanItem_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testServiceNoValidator.updatePlanItem ( expected.getId(), expected, contextInfo);
			}
			catch (VersionMismatchException e) { 
   			exception = true;			}
			
			Assert.assertTrue("VersionMismatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getPlanItem ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudPlanItem_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			PlanItemInfo alphaDTO = actual;
			
			// create a 2nd DTO
			PlanItemInfo betaDTO = new PlanItemInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudPlanItem_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testServiceNoValidator.createPlanItem ( planItem, contextInfo);

// NOTE: There is not currently a need for method:  testService.getPlanItemsByIds
//			// -------------------------------------
//			// test bulk get with no ids supplied
//			// -------------------------------------
//
//			List<String> planItemIds = new ArrayList<String>();
//			// code to get DTO by Ids
//			List<PlanItemInfo> records = testService.getPlanItemsByIds ( planItemIds, contextInfo);
//
//			assertEquals(planItemIds.size(), records.size());
//			assertEquals(0, planItemIds.size());
//
//			// -------------------------------------
//			// test bulk get
//			// -------------------------------------
//			planItemIds = new ArrayList<String>();
//			planItemIds.add(alphaDTO.getId());
//			planItemIds.add(betaDTO.getId());
//			// code to get DTO by Ids
//			records = testService.getPlanItemsByIds ( planItemIds, contextInfo);
//
//			assertEquals(planItemIds.size(), records.size());
//			for (PlanItemInfo record : records)
//			{
//					if (!planItemIds.remove(record.getId()))
//					{
//							fail(record.getId());
//					}
//			}
//			assertEquals(0, planItemIds.size());

// NOTE: There is not currently a need for method:  testService.getPlanItemIdsByType
//			// -------------------------------------
//			// test get by type
//			// -------------------------------------
//			// code to get by specific type "typeKey01"
//			planItemIds = testService.getPlanItemIdsByType ("typeKey_Updated", contextInfo);
//
//			assertEquals(1, planItemIds.size());
//			assertEquals(alphaDTO.getId(), planItemIds.get(0));
//
//			// test get by other type
//			// code to get by specific type "typeKeyBeta"
//			planItemIds = testService.getPlanItemIdsByType ("typeKeyBeta", contextInfo);
//
//			assertEquals(1, planItemIds.size());
//			assertEquals(betaDTO.getId(), planItemIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deletePlanItem ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					PlanItemInfo record = testService.getPlanItem ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a PlanItem in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudPlanItem_setDTOFieldsForTestCreate(PlanItemInfo expected);
	
	/*
		A method to test the fields for a PlanItem. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudPlanItem_testDTOFieldsForTestCreateUpdate(PlanItemInfo expected, PlanItemInfo actual);
	
	/*
		A method to set the fields for a PlanItem in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudPlanItem_setDTOFieldsForTestUpdate(PlanItemInfo expected);
	
	/*
		A method to test the fields for a PlanItem after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudPlanItem_testDTOFieldsForTestReadAfterUpdate(PlanItemInfo expected, PlanItemInfo actual);
	
	/*
		A method to set the fields for a PlanItem in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudPlanItem_setDTOFieldsForTestReadAfterUpdate(PlanItemInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getLearningPlan
			getLearningPlansByIds
			getPlanItem
			getPlanItemsByIds
			getPlanItemsInPlanByType
			getLearningPlansForStudentByType
			createLearningPlan
			createPlanItem
			updateLearningPlan
			updatePlanItem
			deleteLearningPlan
			deletePlanItem
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================

	/* Method Name: getPlanItemsInPlanByCategory */
	@Test
	public abstract void test_getPlanItemsInPlanByCategory() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

	/* Method Name: getPlanItemsInPlan */
	@Test
	public abstract void test_getPlanItemsInPlan() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

	/* Method Name: getPlanItemsInPlanByTermIdByCategory */
	@Test
	public abstract void test_getPlanItemsInPlanByAtp() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

	/* Method Name: getPlanItemsInPlanByRefObjectIdByRefObjectType */
	@Test
	public abstract void test_getPlanItemsInPlanByRefObjectIdByRefObjectType() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;
	
	/* Method Name: validateLearningPlan */
	@Test
	public abstract void test_validateLearningPlan() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;
	
	/* Method Name: validatePlanItem */
	@Test
	public abstract void test_validatePlanItem() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,AlreadyExistsException, AlreadyExistsException;
	
}



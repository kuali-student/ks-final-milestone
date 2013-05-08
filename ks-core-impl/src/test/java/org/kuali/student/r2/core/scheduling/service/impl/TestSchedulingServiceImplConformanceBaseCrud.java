/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.scheduling.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.KeyEntityTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleTransactionInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
public abstract class TestSchedulingServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource(name = "schedulingServiceImpl")
	private SchedulingService testService;
	private ContextInfo contextInfo = null;
	private static String principalId = "123";
	
	@Before
	public void setUp()
	{
		principalId = "123";
		contextInfo = new ContextInfo();
		contextInfo.setPrincipalId(principalId);
	}

    @After
    public void after() {
        if(getSchedulingService() instanceof MockService) {
            MockService service = (MockService)getSchedulingService();
            service.clear();
        }
    }

    public SchedulingService getSchedulingService() {
        return testService;
    }

    public void setSchedulingService(SchedulingService service) {
        testService = service;
    }


    public SchedulingService getTestService() {
        return testService;
    }

    public void setTestService(SchedulingService testService) {
        this.testService = testService;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    public static String getPrincipalId() {
        return principalId;
    }

    public static void setPrincipalId(String principalId) {
        TestSchedulingServiceImplConformanceBaseCrud.principalId = principalId;
    }

    // ====================
	// TESTING
	// ====================
	
	// ****************************************************
	//           ScheduleInfo
	// ****************************************************
	@Test
	public void testCrudSchedule() 
		throws DataValidationErrorException,
			DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException,
			ReadOnlyException,
			VersionMismatchException,
			DependentObjectsExistException
	{
			// -------------------------------------
			// test create
			// -------------------------------------
			ScheduleInfo expected = new ScheduleInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudSchedule_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			ScheduleInfo actual = testService.createSchedule ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudSchedule_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getSchedule ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudSchedule_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudSchedule_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateSchedule ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudSchedule_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getSchedule ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudSchedule_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			//new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			ScheduleInfo alphaDTO = actual;
			
			// create a 2nd DTO
			ScheduleInfo betaDTO = new ScheduleInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudSchedule_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createSchedule ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> scheduleIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<ScheduleInfo> records = testService.getSchedulesByIds ( scheduleIds, contextInfo);
			
			assertEquals(scheduleIds.size(), records.size());
			assertEquals(0, scheduleIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			scheduleIds = new ArrayList<String>();
			scheduleIds.add(alphaDTO.getId());
			scheduleIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getSchedulesByIds ( scheduleIds, contextInfo);
			
			assertEquals(scheduleIds.size(), records.size());
			for (ScheduleInfo record : records)
			{
					if (!scheduleIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, scheduleIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			scheduleIds = testService.getScheduleIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, scheduleIds.size());
			assertEquals(alphaDTO.getId(), scheduleIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			scheduleIds = testService.getScheduleIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, scheduleIds.size());
			assertEquals(betaDTO.getId(), scheduleIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteSchedule ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					ScheduleInfo record = testService.getSchedule ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a Schedule in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudSchedule_setDTOFieldsForTestCreate(ScheduleInfo expected);
	
	/*
		A method to test the fields for a Schedule. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudSchedule_testDTOFieldsForTestCreateUpdate(ScheduleInfo expected, ScheduleInfo actual);
	
	/*
		A method to set the fields for a Schedule in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudSchedule_setDTOFieldsForTestUpdate(ScheduleInfo expected);
	
	/*
		A method to test the fields for a Schedule after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudSchedule_testDTOFieldsForTestReadAfterUpdate(ScheduleInfo expected, ScheduleInfo actual);
	
	/*
		A method to set the fields for a Schedule in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudSchedule_setDTOFieldsForTestReadAfterUpdate(ScheduleInfo expected);
	
	
	// ****************************************************
	//           ScheduleBatchInfo
	// ****************************************************
	/*
    @Test
	public void testCrudScheduleBatch() 
		throws DataValidationErrorException,
			DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException,
			ReadOnlyException,
			VersionMismatchException,
			DependentObjectsExistException
	{
			// -------------------------------------
			// test create
			// -------------------------------------
			ScheduleBatchInfo expected = new ScheduleBatchInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleBatch_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			ScheduleBatchInfo actual = testService.createScheduleBatch ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleBatch_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getScheduleBatch ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudScheduleBatch_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudScheduleBatch_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateScheduleBatch ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleBatch_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getScheduleBatch ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleBatch_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			ScheduleBatchInfo alphaDTO = actual;
			
			// create a 2nd DTO
			ScheduleBatchInfo betaDTO = new ScheduleBatchInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudScheduleBatch_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createScheduleBatch ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> scheduleBatchIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<ScheduleBatchInfo> records = null; // TODO INSERT CODE TO GET DTO BY IDS
			
			assertEquals(scheduleBatchIds.size(), records.size());
			assertEquals(0, scheduleBatchIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			scheduleBatchIds = new ArrayList<String>();
			scheduleBatchIds.add(alphaDTO.getId());
			scheduleBatchIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = null; // TODO INSERT CODE TO GET DTO BY IDS
			
			assertEquals(scheduleBatchIds.size(), records.size());
			for (ScheduleBatchInfo record : records)
			{
					if (!scheduleBatchIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, scheduleBatchIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			scheduleBatchIds = testService.getScheduleBatchIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, scheduleBatchIds.size());
			assertEquals(alphaDTO.getId(), scheduleBatchIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			scheduleBatchIds = testService.getScheduleBatchIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, scheduleBatchIds.size());
			assertEquals(betaDTO.getId(), scheduleBatchIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteScheduleBatch ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					ScheduleBatchInfo record = testService.getScheduleBatch ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	*/

	/*
		A method to set the fields for a ScheduleBatch in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudScheduleBatch_setDTOFieldsForTestCreate(ScheduleBatchInfo expected);
	
	/*
		A method to test the fields for a ScheduleBatch. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudScheduleBatch_testDTOFieldsForTestCreateUpdate(ScheduleBatchInfo expected, ScheduleBatchInfo actual);
	
	/*
		A method to set the fields for a ScheduleBatch in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudScheduleBatch_setDTOFieldsForTestUpdate(ScheduleBatchInfo expected);
	
	/*
		A method to test the fields for a ScheduleBatch after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudScheduleBatch_testDTOFieldsForTestReadAfterUpdate(ScheduleBatchInfo expected, ScheduleBatchInfo actual);
	
	/*
		A method to set the fields for a ScheduleBatch in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudScheduleBatch_setDTOFieldsForTestReadAfterUpdate(ScheduleBatchInfo expected);

	// ****************************************************
	//           ScheduleRequestInfo
	// ****************************************************
	@Test
	public void testCrudScheduleRequest() 
		throws DataValidationErrorException,
			DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException,
			ReadOnlyException,
			VersionMismatchException,
			DependentObjectsExistException
	{
            //first load a scheduleRequestSet
            createScheduleRequestSetsForTest();

			// -------------------------------------
			// test create
			// -------------------------------------
			ScheduleRequestInfo expected = new ScheduleRequestInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleRequest_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			ScheduleRequestInfo actual = testService.createScheduleRequest ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleRequest_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getScheduleRequest ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudScheduleRequest_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getStateKey() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudScheduleRequest_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateScheduleRequest(expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleRequest_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getScheduleRequest ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleRequest_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			
			ScheduleRequestInfo alphaDTO = actual;
			
			// create a 2nd DTO
			ScheduleRequestInfo betaDTO = new ScheduleRequestInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudScheduleRequest_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createScheduleRequest ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> scheduleRequestIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<ScheduleRequestInfo> records = testService.getScheduleRequestsByIds ( scheduleRequestIds, contextInfo);
			
			assertEquals(scheduleRequestIds.size(), records.size());
			assertEquals(0, scheduleRequestIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			scheduleRequestIds = new ArrayList<String>();
			scheduleRequestIds.add(alphaDTO.getId());
			scheduleRequestIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getScheduleRequestsByIds ( scheduleRequestIds, contextInfo);
			
			assertEquals(scheduleRequestIds.size(), records.size());
			for (ScheduleRequestInfo record : records)
			{
					if (!scheduleRequestIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, scheduleRequestIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			scheduleRequestIds = testService.getScheduleRequestIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, scheduleRequestIds.size());
			assertEquals(alphaDTO.getId(), scheduleRequestIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			scheduleRequestIds = testService.getScheduleRequestIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, scheduleRequestIds.size());
			assertEquals(betaDTO.getId(), scheduleRequestIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteScheduleRequest ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					ScheduleRequestInfo record = testService.getScheduleRequest ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a ScheduleRequest in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudScheduleRequest_setDTOFieldsForTestCreate(ScheduleRequestInfo expected);
	
	/*
		A method to test the fields for a ScheduleRequest. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudScheduleRequest_testDTOFieldsForTestCreateUpdate(ScheduleRequestInfo expected, ScheduleRequestInfo actual);
	
	/*
		A method to set the fields for a ScheduleRequest in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudScheduleRequest_setDTOFieldsForTestUpdate(ScheduleRequestInfo expected);
	
	/*
		A method to test the fields for a ScheduleRequest after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudScheduleRequest_testDTOFieldsForTestReadAfterUpdate(ScheduleRequestInfo expected, ScheduleRequestInfo actual);
	
	/*
		A method to set the fields for a ScheduleRequest in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudScheduleRequest_setDTOFieldsForTestReadAfterUpdate(ScheduleRequestInfo expected);
	
	
	// ****************************************************
	//           TimeSlotInfo
	// ****************************************************
	@Test
	public void testCrudTimeSlot() 
		throws DataValidationErrorException,
			DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException,
			ReadOnlyException,
			VersionMismatchException,
			DependentObjectsExistException
	{
			// -------------------------------------
			// test create
			// -------------------------------------
			TimeSlotInfo expected = new TimeSlotInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudTimeSlot_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			TimeSlotInfo actual = testService.createTimeSlot ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudTimeSlot_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getTimeSlot ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudTimeSlot_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudTimeSlot_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateTimeSlot(expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudTimeSlot_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getTimeSlot ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			//new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudTimeSlot_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			//new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			TimeSlotInfo alphaDTO = actual;
			
			// create a 2nd DTO
			TimeSlotInfo betaDTO = new TimeSlotInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudTimeSlot_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createTimeSlot ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> timeSlotIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<TimeSlotInfo> records = testService.getTimeSlotsByIds ( timeSlotIds, contextInfo);
			
			assertEquals(timeSlotIds.size(), records.size());
			assertEquals(0, timeSlotIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			timeSlotIds = new ArrayList<String>();
			timeSlotIds.add(alphaDTO.getId());
			timeSlotIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getTimeSlotsByIds ( timeSlotIds, contextInfo);
			
			assertEquals(timeSlotIds.size(), records.size());
			for (TimeSlotInfo record : records)
			{
					if (!timeSlotIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, timeSlotIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			timeSlotIds = testService.getTimeSlotIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, timeSlotIds.size());
			assertEquals(alphaDTO.getId(), timeSlotIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			timeSlotIds = testService.getTimeSlotIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, timeSlotIds.size());
			assertEquals(betaDTO.getId(), timeSlotIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteTimeSlot ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					TimeSlotInfo record = testService.getTimeSlot ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a TimeSlot in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudTimeSlot_setDTOFieldsForTestCreate(TimeSlotInfo expected);
	
	/*
		A method to test the fields for a TimeSlot. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudTimeSlot_testDTOFieldsForTestCreateUpdate(TimeSlotInfo expected, TimeSlotInfo actual);
	
	/*
		A method to set the fields for a TimeSlot in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudTimeSlot_setDTOFieldsForTestUpdate(TimeSlotInfo expected);
	
	/*
		A method to test the fields for a TimeSlot after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudTimeSlot_testDTOFieldsForTestReadAfterUpdate(TimeSlotInfo expected, TimeSlotInfo actual);
	
	/*
		A method to set the fields for a TimeSlot in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudTimeSlot_setDTOFieldsForTestReadAfterUpdate(TimeSlotInfo expected);
	
	
	// ****************************************************
	//           ScheduleTransactionInfo
	// ****************************************************
	/* TODO
	@Test
	public void testCrudScheduleTransaction() 
		throws DataValidationErrorException,
			DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException,
			ReadOnlyException,
			VersionMismatchException,
			DependentObjectsExistException
	{
			// -------------------------------------
			// test create
			// -------------------------------------
			ScheduleTransactionInfo expected = new ScheduleTransactionInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleTransaction_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			ScheduleTransactionInfo actual = testService.createScheduleTransaction ( expected.getId(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleTransaction_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getScheduleTransaction ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudScheduleTransaction_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudScheduleTransaction_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateScheduleTransaction ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleTransaction_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getScheduleTransaction ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleTransaction_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			ScheduleTransactionInfo alphaDTO = actual;
			
			// create a 2nd DTO
			ScheduleTransactionInfo betaDTO = new ScheduleTransactionInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudScheduleTransaction_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createScheduleTransaction ( betaDTO.getId(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> scheduleTransactionIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<ScheduleTransactionInfo> records = testService.getScheduleTransactionsByIds ( scheduleTransactionIds, contextInfo);
			
			assertEquals(scheduleTransactionIds.size(), records.size());
			assertEquals(0, scheduleTransactionIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			scheduleTransactionIds = new ArrayList<String>();
			scheduleTransactionIds.add(alphaDTO.getId());
			scheduleTransactionIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getScheduleTransactionsByIds ( scheduleTransactionIds, contextInfo);
			
			assertEquals(scheduleTransactionIds.size(), records.size());
			for (ScheduleTransactionInfo record : records)
			{
					if (!scheduleTransactionIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, scheduleTransactionIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			scheduleTransactionIds = testService.getScheduleTransactionIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, scheduleTransactionIds.size());
			assertEquals(alphaDTO.getId(), scheduleTransactionIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			scheduleTransactionIds = testService.getScheduleTransactionIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, scheduleTransactionIds.size());
			assertEquals(betaDTO.getId(), scheduleTransactionIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteScheduleTransaction ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					ScheduleTransactionInfo record = testService.getScheduleTransaction ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	 */
	
	/*
		A method to set the fields for a ScheduleTransaction in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudScheduleTransaction_setDTOFieldsForTestCreate(ScheduleTransactionInfo expected);
	
	/*
		A method to test the fields for a ScheduleTransaction. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudScheduleTransaction_testDTOFieldsForTestCreateUpdate(ScheduleTransactionInfo expected, ScheduleTransactionInfo actual);
	
	/*
		A method to set the fields for a ScheduleTransaction in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudScheduleTransaction_setDTOFieldsForTestUpdate(ScheduleTransactionInfo expected);
	
	/*
		A method to test the fields for a ScheduleTransaction after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudScheduleTransaction_testDTOFieldsForTestReadAfterUpdate(ScheduleTransactionInfo expected, ScheduleTransactionInfo actual);
	
	/*
		A method to set the fields for a ScheduleTransaction in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudScheduleTransaction_setDTOFieldsForTestReadAfterUpdate(ScheduleTransactionInfo expected);
	
	
	// ****************************************************
	//           ScheduleRequestSetInfo
	// ****************************************************
	@Test
	public void testCrudScheduleRequestSet() 
		throws DataValidationErrorException,
			DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException,
			ReadOnlyException,
			VersionMismatchException,
			DependentObjectsExistException
	{
			// -------------------------------------
			// test create
			// -------------------------------------
			ScheduleRequestSetInfo expected = new ScheduleRequestSetInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleRequestSet_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			ScheduleRequestSetInfo actual = testService.createScheduleRequestSet(expected.getTypeKey(), expected.getRefObjectTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudScheduleRequestSet_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getScheduleRequestSet ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudScheduleRequestSet_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getStateKey() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudScheduleRequestSet_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateScheduleRequestSet ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleRequestSet_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getScheduleRequestSet ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudScheduleRequestSet_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			//new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			ScheduleRequestSetInfo alphaDTO = actual;
			
			// create a 2nd DTO
			ScheduleRequestSetInfo betaDTO = new ScheduleRequestSetInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudScheduleRequestSet_setDTOFieldsForTestReadAfterUpdate(betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createScheduleRequestSet (betaDTO.getTypeKey(), betaDTO.getRefObjectTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> scheduleRequestSetIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<ScheduleRequestSetInfo> records = testService.getScheduleRequestSetsByIds ( scheduleRequestSetIds, contextInfo);
			
			assertEquals(scheduleRequestSetIds.size(), records.size());
			assertEquals(0, scheduleRequestSetIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			scheduleRequestSetIds = new ArrayList<String>();
			scheduleRequestSetIds.add(alphaDTO.getId());
			scheduleRequestSetIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getScheduleRequestSetsByIds ( scheduleRequestSetIds, contextInfo);
			
			assertEquals(scheduleRequestSetIds.size(), records.size());
			for (ScheduleRequestSetInfo record : records)
			{
					if (!scheduleRequestSetIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, scheduleRequestSetIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			scheduleRequestSetIds = testService.getScheduleRequestSetIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, scheduleRequestSetIds.size());
			assertEquals(alphaDTO.getId(), scheduleRequestSetIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			scheduleRequestSetIds = testService.getScheduleRequestSetIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, scheduleRequestSetIds.size());
			assertEquals(betaDTO.getId(), scheduleRequestSetIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteScheduleRequestSet ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					ScheduleRequestSetInfo record = testService.getScheduleRequestSet ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}

    public abstract void createScheduleRequestSetsForTest() throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException;
	
	/*
		A method to set the fields for a ScheduleRequestSet in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudScheduleRequestSet_setDTOFieldsForTestCreate(ScheduleRequestSetInfo expected);
	
	/*
		A method to test the fields for a ScheduleRequestSet. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudScheduleRequestSet_testDTOFieldsForTestCreateUpdate(ScheduleRequestSetInfo expected, ScheduleRequestSetInfo actual);
	
	/*
		A method to set the fields for a ScheduleRequestSet in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudScheduleRequestSet_setDTOFieldsForTestUpdate(ScheduleRequestSetInfo expected);
	
	/*
		A method to test the fields for a ScheduleRequestSet after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudScheduleRequestSet_testDTOFieldsForTestReadAfterUpdate(ScheduleRequestSetInfo expected, ScheduleRequestSetInfo actual);
	
	/*
		A method to set the fields for a ScheduleRequestSet in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudScheduleRequestSet_setDTOFieldsForTestReadAfterUpdate(ScheduleRequestSetInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getSchedule
			getSchedulesByIds
			getScheduleIdsByType
			createSchedule
			updateSchedule
			deleteSchedule
			getScheduleBatch
			getScheduleBatchesByIds
			getScheduleBatchIdsByType
			createScheduleBatch
			updateScheduleBatch
			deleteScheduleBatch
			getScheduleRequest
			getScheduleRequestsByIds
			getScheduleRequestIdsByType
			createScheduleRequest
			updateScheduleRequest
			deleteScheduleRequest
			getTimeSlot
			getTimeSlotsByIds
			getTimeSlotIdsByType
			createTimeSlot
			updateTimeSlot
			deleteTimeSlot
			getScheduleTransaction
			getScheduleTransactionsByIds
			getScheduleTransactionIdsByType
			createScheduleTransaction
			updateScheduleTransaction
			deleteScheduleTransaction
			getScheduleDisplay
			getScheduleDisplaysByIds
			getScheduleRequestDisplay
			getScheduleRequestDisplaysByIds
			getScheduleRequestSet
			getScheduleRequestSetsByIds
			getScheduleRequestSetIdsByType
			createScheduleRequestSet
			updateScheduleRequestSet
			deleteScheduleRequestSet
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: searchForScheduleIds */
	@Test
	public abstract void test_searchForScheduleIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForSchedules */
	@Test
	public abstract void test_searchForSchedules() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateSchedule */
	@Test
	public abstract void test_validateSchedule() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleBatchIds */
	@Test
	public abstract void test_searchForScheduleBatchIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleBatches */
	@Test
	public abstract void test_searchForScheduleBatches() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateScheduleBatch */
	@Test
	public abstract void test_validateScheduleBatch() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleRequestIdsByRefObject */
	@Test
	public abstract void test_getScheduleRequestIdsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleRequestsByRefObject */
	@Test
	public abstract void test_getScheduleRequestsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleRequestsByRefObjects */
	@Test
	public abstract void test_getScheduleRequestsByRefObjects() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleRequestIds */
	@Test
	public abstract void test_searchForScheduleRequestIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleRequests */
	@Test
	public abstract void test_searchForScheduleRequests() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateScheduleRequest */
	@Test
	public abstract void test_validateScheduleRequest() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getTimeSlotsByDaysAndStartTime */
	@Test
	public abstract void test_getTimeSlotsByDaysAndStartTime() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getTimeSlotsByDaysAndStartTimeAndEndTime */
	@Test
	public abstract void test_getTimeSlotsByDaysAndStartTimeAndEndTime() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForTimeSlotIds */
	@Test
	public abstract void test_searchForTimeSlotIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForTimeSlots */
	@Test
	public abstract void test_searchForTimeSlots() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateTimeSlot */
	@Test
	public abstract void test_validateTimeSlot() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: submitScheduleBatch */
	@Test
	public abstract void test_submitScheduleBatch() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: commitSchedules */
	@Test
	public abstract void test_commitSchedules() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getValidDaysOfWeekByTimeSlotType */
	@Test
	public abstract void test_getValidDaysOfWeekByTimeSlotType() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleBatchesForScheduleTransaction */
	@Test
	public abstract void test_getScheduleBatchesForScheduleTransaction() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleTransactionIdsByRefObject */
	@Test
	public abstract void test_getScheduleTransactionIdsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleTransactionsByRefObject */
	@Test
	public abstract void test_getScheduleTransactionsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleTransactionsForScheduleBatch */
	@Test
	public abstract void test_getScheduleTransactionsForScheduleBatch() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleTransactionIds */
	@Test
	public abstract void test_searchForScheduleTransactionIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleTransactions */
	@Test
	public abstract void test_searchForScheduleTransactions() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateScheduleTransaction */
	@Test
	public abstract void test_validateScheduleTransaction() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: areTimeSlotsInConflict */
	@Test
	public abstract void test_areTimeSlotsInConflict() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleDisplays */
	@Test
	public abstract void test_searchForScheduleDisplays() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleRequestDisplays */
	@Test
	public abstract void test_searchForScheduleRequestDisplays() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleRequestSetIdsByRefObjType */
	@Test
	public abstract void test_getScheduleRequestSetIdsByRefObjType()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException;
	
	/* Method Name: searchForScheduleRequestSetIds */
	@Test
	public abstract void test_searchForScheduleRequestSetIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForScheduleRequestSets */
	@Test
	public abstract void test_searchForScheduleRequestSets() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateScheduleRequestSet */
	@Test
	public abstract void test_validateScheduleRequestSet() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getScheduleRequestSetsByRefObject */
	@Test
	public abstract void test_getScheduleRequestSetsByRefObject()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException;
	
}



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
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.room.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.PartitionInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:room-service-test-with-map-context.xml"})
public abstract class TestRoomServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource
	public RoomService testService;
	public RoomService getRoomService() { return testService; }
	public void setRoomService(RoomService service) { testService = service; }
	
	public ContextInfo contextInfo = null;
	public static String principalId = "123";
	
	@Before
	public void setUp()
	{
		principalId = "123";
		contextInfo = new ContextInfo();
		contextInfo.setPrincipalId(principalId);
	}
	
	// ====================
	// TESTING
	// ====================
	
	// ****************************************************
	//           RoomInfo
	// ****************************************************
	@Test
	public void testCrudRoom() 
		throws AlreadyExistsException,
            DataValidationErrorException,
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
			RoomInfo expected = new RoomInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudRoom_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			RoomInfo actual = testService.createRoom ( expected.getId(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudRoom_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getRoom ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudRoom_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			RoomInfo original = new RoomInfo (actual);
			expected = new RoomInfo (actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudRoom_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateRoom ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudRoom_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testService.updateRoom ( original.getId(), original, contextInfo);
			}
			catch (VersionMismatchException e) { 
   			exception = true;			}
			
			Assert.assertTrue("VersionMissmatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getRoom ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudRoom_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			RoomInfo alphaDTO = actual;
			
			// create a 2nd DTO
			RoomInfo betaDTO = new RoomInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudRoom_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createRoom ( betaDTO.getId(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> roomIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<RoomInfo> records = testService.getRoomsByIds ( roomIds, contextInfo);
			
			assertEquals(roomIds.size(), records.size());
			assertEquals(0, roomIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			roomIds = new ArrayList<String>();
			roomIds.add(alphaDTO.getId());
			roomIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getRoomsByIds ( roomIds, contextInfo);
			
			assertEquals(roomIds.size(), records.size());
			for (RoomInfo record : records)
			{
					if (!roomIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, roomIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			roomIds = testService.getRoomIdsByType ("typeKey_Updated", contextInfo);
			
			assertEquals(1, roomIds.size());
			assertEquals(alphaDTO.getId(), roomIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			roomIds = testService.getRoomIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, roomIds.size());
			assertEquals(betaDTO.getId(), roomIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteRoom ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					RoomInfo record = testService.getRoom ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a Room in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudRoom_setDTOFieldsForTestCreate(RoomInfo expected);
	
	/*
		A method to test the fields for a Room. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudRoom_testDTOFieldsForTestCreateUpdate(RoomInfo expected, RoomInfo actual);
	
	/*
		A method to set the fields for a Room in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudRoom_setDTOFieldsForTestUpdate(RoomInfo expected);
	
	/*
		A method to test the fields for a Room after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudRoom_testDTOFieldsForTestReadAfterUpdate(RoomInfo expected, RoomInfo actual);
	
	/*
		A method to set the fields for a Room in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudRoom_setDTOFieldsForTestReadAfterUpdate(RoomInfo expected);
	
	
	// ****************************************************
	//           BuildingInfo
	// ****************************************************
	@Test
	public void testCrudBuilding() 
		throws AlreadyExistsException,
            DataValidationErrorException,
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
			BuildingInfo expected = new BuildingInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudBuilding_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			BuildingInfo actual = testService.createBuilding ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudBuilding_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getBuilding ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudBuilding_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			BuildingInfo original = new BuildingInfo (actual);
			expected = new BuildingInfo (actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudBuilding_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateBuilding ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudBuilding_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testService.updateBuilding ( original.getId(), original, contextInfo);
			}
			catch (VersionMismatchException e) { 
   			exception = true;			}
			
			Assert.assertTrue("VersionMissmatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getBuilding ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudBuilding_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			BuildingInfo alphaDTO = actual;
			
			// create a 2nd DTO
			BuildingInfo betaDTO = new BuildingInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudBuilding_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createBuilding ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> buildingIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<BuildingInfo> records = testService.getBuildingsByIds ( buildingIds, contextInfo);
			
			assertEquals(buildingIds.size(), records.size());
			assertEquals(0, buildingIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			buildingIds = new ArrayList<String>();
			buildingIds.add(alphaDTO.getId());
			buildingIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getBuildingsByIds ( buildingIds, contextInfo);
			
			assertEquals(buildingIds.size(), records.size());
			for (BuildingInfo record : records)
			{
					if (!buildingIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, buildingIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			buildingIds = testService.getBuildingIdsByType ("typeKey_Updated", contextInfo);
			
			assertEquals(1, buildingIds.size());
			assertEquals(alphaDTO.getId(), buildingIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			buildingIds = testService.getBuildingIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, buildingIds.size());
			assertEquals(betaDTO.getId(), buildingIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteBuilding ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					BuildingInfo record = testService.getBuilding ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a Building in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudBuilding_setDTOFieldsForTestCreate(BuildingInfo expected);
	
	/*
		A method to test the fields for a Building. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudBuilding_testDTOFieldsForTestCreateUpdate(BuildingInfo expected, BuildingInfo actual);
	
	/*
		A method to set the fields for a Building in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudBuilding_setDTOFieldsForTestUpdate(BuildingInfo expected);
	
	/*
		A method to test the fields for a Building after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudBuilding_testDTOFieldsForTestReadAfterUpdate(BuildingInfo expected, BuildingInfo actual);
	
	/*
		A method to set the fields for a Building in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudBuilding_setDTOFieldsForTestReadAfterUpdate(BuildingInfo expected);
	
	
	// ****************************************************
	//           RoomResponsibleOrgInfo
	// ****************************************************
/*
	@Test
	public void testCrudRoomResponsibleOrg() 
		throws AlreadyExistsException,
            DataValidationErrorException,
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
			RoomResponsibleOrgInfo expected = new RoomResponsibleOrgInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudRoomResponsibleOrg_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			RoomResponsibleOrgInfo actual = testService.createRoomResponsibleOrg ( expected.getId(), expected.getId(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudRoomResponsibleOrg_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getRoomResponsibleOrg ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudRoomResponsibleOrg_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			RoomResponsibleOrgInfo original = new RoomResponsibleOrgInfo (actual);
			expected = new RoomResponsibleOrgInfo (actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudRoomResponsibleOrg_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateRoomResponsibleOrg ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudRoomResponsibleOrg_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testService.updateRoomResponsibleOrg ( original.getId(), original, contextInfo);
			}
			catch (VersionMismatchException e) { 
   			exception = true;			}
			
			Assert.assertTrue("VersionMissmatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getRoomResponsibleOrg ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudRoomResponsibleOrg_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			RoomResponsibleOrgInfo alphaDTO = actual;
			
			// create a 2nd DTO
			RoomResponsibleOrgInfo betaDTO = new RoomResponsibleOrgInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudRoomResponsibleOrg_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createRoomResponsibleOrg ( betaDTO.getId(), betaDTO.getId(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> roomResponsibleOrgIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<RoomResponsibleOrgInfo> records = testService.getRoomResponsibleOrgsByIds ( roomResponsibleOrgIds, contextInfo);
			
			assertEquals(roomResponsibleOrgIds.size(), records.size());
			assertEquals(0, roomResponsibleOrgIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			roomResponsibleOrgIds = new ArrayList<String>();
			roomResponsibleOrgIds.add(alphaDTO.getId());
			roomResponsibleOrgIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getRoomResponsibleOrgsByIds ( roomResponsibleOrgIds, contextInfo);
			
			assertEquals(roomResponsibleOrgIds.size(), records.size());
			for (RoomResponsibleOrgInfo record : records)
			{
					if (!roomResponsibleOrgIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, roomResponsibleOrgIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			roomResponsibleOrgIds = testService.getRoomResponsibleOrgIdsByType ("typeKey_Updated", contextInfo);
			
			assertEquals(1, roomResponsibleOrgIds.size());
			assertEquals(alphaDTO.getId(), roomResponsibleOrgIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			roomResponsibleOrgIds = testService.getRoomResponsibleOrgIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, roomResponsibleOrgIds.size());
			assertEquals(betaDTO.getId(), roomResponsibleOrgIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteRoomResponsibleOrg ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					RoomResponsibleOrgInfo record = testService.getRoomResponsibleOrg ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
*/
	/*
		A method to set the fields for a RoomResponsibleOrg in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudRoomResponsibleOrg_setDTOFieldsForTestCreate(RoomResponsibleOrgInfo expected);
	
	/*
		A method to test the fields for a RoomResponsibleOrg. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudRoomResponsibleOrg_testDTOFieldsForTestCreateUpdate(RoomResponsibleOrgInfo expected, RoomResponsibleOrgInfo actual);
	
	/*
		A method to set the fields for a RoomResponsibleOrg in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudRoomResponsibleOrg_setDTOFieldsForTestUpdate(RoomResponsibleOrgInfo expected);
	
	/*
		A method to test the fields for a RoomResponsibleOrg after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudRoomResponsibleOrg_testDTOFieldsForTestReadAfterUpdate(RoomResponsibleOrgInfo expected, RoomResponsibleOrgInfo actual);
	
	/*
		A method to set the fields for a RoomResponsibleOrg in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudRoomResponsibleOrg_setDTOFieldsForTestReadAfterUpdate(RoomResponsibleOrgInfo expected);
	
	
	// ****************************************************
	//           PartitionInfo
	// ****************************************************
	@Test
	public void testCrudPartition() 
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
			PartitionInfo expected = new PartitionInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudPartition_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			PartitionInfo actual = testService.createPartition ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudPartition_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getPartition ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudPartition_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			PartitionInfo original = new PartitionInfo (actual);
			expected = new PartitionInfo (actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudPartition_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updatePartition ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudPartition_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testService.updatePartition ( original.getId(), original, contextInfo);
			}
			catch (VersionMismatchException e) { 
   			exception = true;			}
			
			Assert.assertTrue("VersionMissmatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getPartition ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudPartition_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			PartitionInfo alphaDTO = actual;
			
			// create a 2nd DTO
			PartitionInfo betaDTO = new PartitionInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudPartition_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createPartition ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> partitionIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<PartitionInfo> records = testService.getPartitionsByIds ( partitionIds, contextInfo);
			
			assertEquals(partitionIds.size(), records.size());
			assertEquals(0, partitionIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			partitionIds = new ArrayList<String>();
			partitionIds.add(alphaDTO.getId());
			partitionIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getPartitionsByIds ( partitionIds, contextInfo);
			
			assertEquals(partitionIds.size(), records.size());
			for (PartitionInfo record : records)
			{
					if (!partitionIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, partitionIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			partitionIds = testService.getPartitionIdsByType ("typeKey_Updated", contextInfo);
			
			assertEquals(1, partitionIds.size());
			assertEquals(alphaDTO.getId(), partitionIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			partitionIds = testService.getPartitionIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, partitionIds.size());
			assertEquals(betaDTO.getId(), partitionIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deletePartition ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					PartitionInfo record = testService.getPartition ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a Partition in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudPartition_setDTOFieldsForTestCreate(PartitionInfo expected);
	
	/*
		A method to test the fields for a Partition. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudPartition_testDTOFieldsForTestCreateUpdate(PartitionInfo expected, PartitionInfo actual);
	
	/*
		A method to set the fields for a Partition in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudPartition_setDTOFieldsForTestUpdate(PartitionInfo expected);
	
	/*
		A method to test the fields for a Partition after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudPartition_testDTOFieldsForTestReadAfterUpdate(PartitionInfo expected, PartitionInfo actual);
	
	/*
		A method to set the fields for a Partition in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudPartition_setDTOFieldsForTestReadAfterUpdate(PartitionInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getRoom
			getRoomsByIds
			getRoomIdsByType
			createRoom
			updateRoom
			deleteRoom
			getBuilding
			getBuildingsByIds
			getBuildingIdsByType
			createBuilding
			updateBuilding
			deleteBuilding
			getRoomResponsibleOrg
			getRoomResponsibleOrgsByIds
			getRoomResponsibleOrgIdsByType
			createRoomResponsibleOrg
			updateRoomResponsibleOrg
			deleteRoomResponsibleOrg
			getPartition
			getPartitionsByIds
			getPartitionIdsByType
			createPartition
			updatePartition
			deletePartition
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getRoomIdsByBuilding */
	@Test
	public abstract void test_getRoomIdsByBuilding() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomIdsByBuildingAndFloor */
	@Test
	public abstract void test_getRoomIdsByBuildingAndFloor() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomIdsByBuildingAndRoomType */
	@Test
	public abstract void test_getRoomIdsByBuildingAndRoomType() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomsByBuildingAndRoomUsageTypes */
	@Test
	public abstract void test_getRoomsByBuildingAndRoomUsageTypes() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomIdsByBuildingAndRoomTypes */
	@Test
	public abstract void test_getRoomIdsByBuildingAndRoomTypes() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomsByBuildingAndRoomCode */
	@Test
	public abstract void test_getRoomsByBuildingAndRoomCode() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForRoomIds */
	@Test
	public abstract void test_searchForRoomIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForRooms */
	@Test
	public abstract void test_searchForRooms() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateRoom */
	@Test
	public abstract void test_validateRoom() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getBuildingIdsByCampus */
	@Test
	public abstract void test_getBuildingIdsByCampus() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getBuildingsByBuildingCode */
	@Test
	public abstract void test_getBuildingsByBuildingCode() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForBuildingIds */
	@Test
	public abstract void test_searchForBuildingIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForBuildings */
	@Test
	public abstract void test_searchForBuildings() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateBuilding */
	@Test
	public abstract void test_validateBuilding() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomResponsibleOrgIdsByRoom */
	@Test
	public abstract void test_getRoomResponsibleOrgIdsByRoom() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomResponsibleOrgIdsForBuilding */
	@Test
	public abstract void test_getRoomResponsibleOrgIdsForBuilding() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForRoomResponsibleOrgIds */
	@Test
	public abstract void test_searchForRoomResponsibleOrgIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForRoomResponsibleOrgs */
	@Test
	public abstract void test_searchForRoomResponsibleOrgs() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateRoomResponsibleOrg */
	@Test
	public abstract void test_validateRoomResponsibleOrg() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getBuildingIdsByPartition */
	@Test
	public abstract void test_getBuildingIdsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getBuildingsByPartition */
	@Test
	public abstract void test_getBuildingsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getPartitionIdsByBuilding */
	@Test
	public abstract void test_getPartitionIdsByBuilding() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getPartitionsByBuilding */
	@Test
	public abstract void test_getPartitionsByBuilding() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomIdsByPartition */
	@Test
	public abstract void test_getRoomIdsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getRoomsByPartition */
	@Test
	public abstract void test_getRoomsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getPartitionIdsByRoom */
	@Test
	public abstract void test_getPartitionIdsByRoom() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getPartitionsByRoom */
	@Test
	public abstract void test_getPartitionsByRoom() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForPartitionIds */
	@Test
	public abstract void test_searchForPartitionIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForPartitions */
	@Test
	public abstract void test_searchForPartitions() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validatePartition */
	@Test
	public abstract void test_validatePartition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: addBuildingToPartition */
	@Test
	public abstract void test_addBuildingToPartition() 
	throws 	AlreadyExistsException	,DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: removeBuildingFromPartition */
	@Test
	public abstract void test_removeBuildingFromPartition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: addRoomToPartition */
	@Test
	public abstract void test_addRoomToPartition() 
	throws 	AlreadyExistsException	,DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: removeRoomFromPartition */
	@Test
	public abstract void test_removeRoomFromPartition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
}



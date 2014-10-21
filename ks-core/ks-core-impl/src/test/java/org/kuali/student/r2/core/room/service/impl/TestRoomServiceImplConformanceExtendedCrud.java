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


import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.PartitionInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:room-service-test-with-map-context.xml"})
public class TestRoomServiceImplConformanceExtendedCrud extends TestRoomServiceImplConformanceBaseCrud
{
	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           RoomInfo
	// ****************************************************
	
	/*
		A method to set the fields for a Room in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudRoom_setDTOFieldsForTestCreate(RoomInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
		expected.setRoomCode("roomCode01");
		expected.setBuildingId("buildingId01");
		expected.setFloor("floor01");
	}
	
	/*
		A method to test the fields for a Room. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudRoom_testDTOFieldsForTestCreateUpdate(RoomInfo expected, RoomInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getRoomCode(), actual.getRoomCode());
		assertEquals (expected.getBuildingId(), actual.getBuildingId());
		assertEquals (expected.getFloor(), actual.getFloor());
	}
	
	/*
		A method to set the fields for a Room in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudRoom_setDTOFieldsForTestUpdate(RoomInfo expected) 
	{
		expected.setTypeKey("typeKey_Updated");
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
		expected.setRoomCode("roomCode_Updated");
		expected.setBuildingId("buildingId_Updated");
		expected.setFloor("floor_Updated");
	}
	
	/*
		A method to test the fields for a Room after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudRoom_testDTOFieldsForTestReadAfterUpdate(RoomInfo expected, RoomInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getRoomCode(), actual.getRoomCode());
		assertEquals (expected.getBuildingId(), actual.getBuildingId());
		assertEquals (expected.getFloor(), actual.getFloor());
	}
	
	/*
		A method to set the fields for a Room in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudRoom_setDTOFieldsForTestReadAfterUpdate(RoomInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setRoomCode("roomCode_Updated");
		expected.setBuildingId("buildingId_Updated");
		expected.setFloor("floor_Updated");
	}
	
	
	// ****************************************************
	//           BuildingInfo
	// ****************************************************
	
	/*
		A method to set the fields for a Building in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudBuilding_setDTOFieldsForTestCreate(BuildingInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
		expected.setBuildingCode("buildingCode01");
		expected.setCampusId("campusKey01");
	}
	
	/*
		A method to test the fields for a Building. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudBuilding_testDTOFieldsForTestCreateUpdate(BuildingInfo expected, BuildingInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getBuildingCode(), actual.getBuildingCode());
		assertEquals (expected.getCampusId(), actual.getCampusId());
	}
	
	/*
		A method to set the fields for a Building in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudBuilding_setDTOFieldsForTestUpdate(BuildingInfo expected) 
	{
		expected.setTypeKey("typeKey_Updated");
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
		expected.setBuildingCode("buildingCode_Updated");
		expected.setCampusId("campusKey_Updated");
	}
	
	/*
		A method to test the fields for a Building after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudBuilding_testDTOFieldsForTestReadAfterUpdate(BuildingInfo expected, BuildingInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getBuildingCode(), actual.getBuildingCode());
		assertEquals (expected.getCampusId(), actual.getCampusId());
	}
	
	/*
		A method to set the fields for a Building in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudBuilding_setDTOFieldsForTestReadAfterUpdate(BuildingInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setBuildingCode("buildingCode_Updated");
		expected.setCampusId("campusKey_Updated");
	}
	
	
	// ****************************************************
	//           RoomResponsibleOrgInfo
	// ****************************************************
	
	/*
		A method to set the fields for a RoomResponsibleOrg in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudRoomResponsibleOrg_setDTOFieldsForTestCreate(RoomResponsibleOrgInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setRoomId("roomId01");
		expected.setOrgId("orgId01");
	}
	
	/*
		A method to test the fields for a RoomResponsibleOrg. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudRoomResponsibleOrg_testDTOFieldsForTestCreateUpdate(RoomResponsibleOrgInfo expected, RoomResponsibleOrgInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getRoomId(), actual.getRoomId());
		assertEquals (expected.getOrgId(), actual.getOrgId());
	}
	
	/*
		A method to set the fields for a RoomResponsibleOrg in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudRoomResponsibleOrg_setDTOFieldsForTestUpdate(RoomResponsibleOrgInfo expected) 
	{
		expected.setTypeKey("typeKey_Updated");
		expected.setStateKey("stateKey_Updated");
		expected.setRoomId("roomId_Updated");
		expected.setOrgId("orgId_Updated");
	}
	
	/*
		A method to test the fields for a RoomResponsibleOrg after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudRoomResponsibleOrg_testDTOFieldsForTestReadAfterUpdate(RoomResponsibleOrgInfo expected, RoomResponsibleOrgInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getRoomId(), actual.getRoomId());
		assertEquals (expected.getOrgId(), actual.getOrgId());
	}
	
	/*
		A method to set the fields for a RoomResponsibleOrg in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudRoomResponsibleOrg_setDTOFieldsForTestReadAfterUpdate(RoomResponsibleOrgInfo expected) 
	{
		expected.setRoomId("roomId_Updated");
		expected.setOrgId("orgId_Updated");
	}
	
	
	// ****************************************************
	//           PartitionInfo
	// ****************************************************
	
	/*
		A method to set the fields for a Partition in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudPartition_setDTOFieldsForTestCreate(PartitionInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
	}
	
	/*
		A method to test the fields for a Partition. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudPartition_testDTOFieldsForTestCreateUpdate(PartitionInfo expected, PartitionInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a Partition in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudPartition_setDTOFieldsForTestUpdate(PartitionInfo expected) 
	{
		expected.setTypeKey("typeKey_Updated");
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
	}
	
	/*
		A method to test the fields for a Partition after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudPartition_testDTOFieldsForTestReadAfterUpdate(PartitionInfo expected, PartitionInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a Partition in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudPartition_setDTOFieldsForTestReadAfterUpdate(PartitionInfo expected) 
	{
		expected.setName("name_Updated");
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getRoomIdsByBuilding */
	@Test
	public void test_getRoomIdsByBuilding() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomIdsByBuildingAndFloor */
	@Test
	public void test_getRoomIdsByBuildingAndFloor() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomIdsByBuildingAndRoomType */
	@Test
	public void test_getRoomIdsByBuildingAndRoomType() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomsByBuildingAndRoomUsageTypes */
	@Test
	public void test_getRoomsByBuildingAndRoomUsageTypes() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomIdsByBuildingAndRoomTypes */
	@Test
	public void test_getRoomIdsByBuildingAndRoomTypes() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomsByBuildingAndRoomCode */
	@Test
	public void test_getRoomsByBuildingAndRoomCode() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForRoomIds */
	@Test
	public void test_searchForRoomIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForRooms */
	@Test
	public void test_searchForRooms() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateRoom */
	@Test
	public void test_validateRoom() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getBuildingIdsByCampus */
	@Test
	public void test_getBuildingIdsByCampus() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getBuildingsByBuildingCode */
	@Test
	public void test_getBuildingsByBuildingCode() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForBuildingIds */
	@Test
	public void test_searchForBuildingIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForBuildings */
	@Test
	public void test_searchForBuildings() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateBuilding */
	@Test
	public void test_validateBuilding() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomResponsibleOrgIdsByRoom */
	@Test
	public void test_getRoomResponsibleOrgIdsByRoom() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomResponsibleOrgIdsForBuilding */
	@Test
	public void test_getRoomResponsibleOrgIdsForBuilding() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForRoomResponsibleOrgIds */
	@Test
	public void test_searchForRoomResponsibleOrgIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForRoomResponsibleOrgs */
	@Test
	public void test_searchForRoomResponsibleOrgs() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateRoomResponsibleOrg */
	@Test
	public void test_validateRoomResponsibleOrg() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getBuildingIdsByPartition */
	@Test
	public void test_getBuildingIdsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getBuildingsByPartition */
	@Test
	public void test_getBuildingsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getPartitionIdsByBuilding */
	@Test
	public void test_getPartitionIdsByBuilding() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getPartitionsByBuilding */
	@Test
	public void test_getPartitionsByBuilding() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomIdsByPartition */
	@Test
	public void test_getRoomIdsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getRoomsByPartition */
	@Test
	public void test_getRoomsByPartition() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getPartitionIdsByRoom */
	@Test
	public void test_getPartitionIdsByRoom() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getPartitionsByRoom */
	@Test
	public void test_getPartitionsByRoom() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForPartitionIds */
	@Test
	public void test_searchForPartitionIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForPartitions */
	@Test
	public void test_searchForPartitions() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validatePartition */
	@Test
	public void test_validatePartition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: addBuildingToPartition */
	@Test
	public void test_addBuildingToPartition() 
	throws 	AlreadyExistsException	,DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: removeBuildingFromPartition */
	@Test
	public void test_removeBuildingFromPartition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: addRoomToPartition */
	@Test
	public void test_addRoomToPartition() 
	throws AlreadyExistsException,DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: removeRoomFromPartition */
	@Test
	public void test_removeRoomFromPartition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
}



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
package org.kuali.student.enrollment.class2.waitlist.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
import org.kuali.student.enrollment.waitlist.service.WaitListService;import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RelationshipInfo;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:waitList-test-with-map-context.xml"})
public abstract class TestWaitListServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource
	public WaitListService testService;
	public WaitListService getWaitListService() { return testService; }
	public void setWaitListService(WaitListService service) { testService = service; }
	
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
	//           WaitListInfo
	// ****************************************************
	@Test
	public void testCrudWaitList() 
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
			WaitListInfo expected = new WaitListInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudWaitList_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			WaitListInfo actual = testService.createWaitList ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudWaitList_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getWaitList ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudWaitList_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudWaitList_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateWaitList ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudWaitList_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getWaitList ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudWaitList_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			WaitListInfo alphaDTO = actual;
			
			// create a 2nd DTO
			WaitListInfo betaDTO = new WaitListInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudWaitList_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createWaitList ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> waitListIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<WaitListInfo> records = testService.getWaitListsByIds ( waitListIds, contextInfo);
			
			assertEquals(waitListIds.size(), records.size());
			assertEquals(0, waitListIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			waitListIds = new ArrayList<String>();
			waitListIds.add(alphaDTO.getId());
			waitListIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getWaitListsByIds ( waitListIds, contextInfo);
			
			assertEquals(waitListIds.size(), records.size());
			for (WaitListInfo record : records)
			{
					if (!waitListIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, waitListIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			waitListIds = testService.getWaitListIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, waitListIds.size());
			assertEquals(alphaDTO.getId(), waitListIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			waitListIds = testService.getWaitListIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, waitListIds.size());
			assertEquals(betaDTO.getId(), waitListIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteWaitList ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					WaitListInfo record = testService.getWaitList ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a WaitList in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudWaitList_setDTOFieldsForTestCreate(WaitListInfo expected);
	
	/*
		A method to test the fields for a WaitList. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudWaitList_testDTOFieldsForTestCreateUpdate(WaitListInfo expected, WaitListInfo actual);
	
	/*
		A method to set the fields for a WaitList in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudWaitList_setDTOFieldsForTestUpdate(WaitListInfo expected);
	
	/*
		A method to test the fields for a WaitList after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudWaitList_testDTOFieldsForTestReadAfterUpdate(WaitListInfo expected, WaitListInfo actual);
	
	/*
		A method to set the fields for a WaitList in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudWaitList_setDTOFieldsForTestReadAfterUpdate(WaitListInfo expected);
	
	
	// ****************************************************
	//           WaitListEntryInfo
	// ****************************************************
	@Test
	public void testCrudWaitListEntry() 
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
			WaitListEntryInfo expected = new WaitListEntryInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudWaitListEntry_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			WaitListEntryInfo actual = testService.createWaitListEntry (expected.getWaitListId(), expected.getStudentId(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudWaitListEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getWaitListEntry ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudWaitListEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudWaitListEntry_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateWaitListEntry ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudWaitListEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getWaitListEntry ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudWaitListEntry_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			WaitListEntryInfo alphaDTO = actual;
			
			// create a 2nd DTO
			WaitListEntryInfo betaDTO = new WaitListEntryInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudWaitListEntry_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createWaitListEntry ( betaDTO.getWaitListId(), betaDTO.getStudentId(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> waitListEntryIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<WaitListEntryInfo> records = testService.getWaitListEntriesByIds(waitListEntryIds, contextInfo);
			
			assertEquals(waitListEntryIds.size(), records.size());
			assertEquals(0, waitListEntryIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			waitListEntryIds = new ArrayList<String>();
			waitListEntryIds.add(alphaDTO.getId());
			waitListEntryIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getWaitListEntriesByIds(waitListEntryIds, contextInfo);
			
			assertEquals(waitListEntryIds.size(), records.size());
			for (WaitListEntryInfo record : records)
			{
					if (!waitListEntryIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, waitListEntryIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			waitListEntryIds = testService.getWaitListEntryIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, waitListEntryIds.size());
			assertEquals(alphaDTO.getId(), waitListEntryIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			waitListEntryIds = testService.getWaitListEntryIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, waitListEntryIds.size());
			assertEquals(betaDTO.getId(), waitListEntryIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteWaitListEntry ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					WaitListEntryInfo record = testService.getWaitListEntry ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a WaitListEntry in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudWaitListEntry_setDTOFieldsForTestCreate(WaitListEntryInfo expected);
	
	/*
		A method to test the fields for a WaitListEntry. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudWaitListEntry_testDTOFieldsForTestCreateUpdate(WaitListEntryInfo expected, WaitListEntryInfo actual);
	
	/*
		A method to set the fields for a WaitListEntry in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudWaitListEntry_setDTOFieldsForTestUpdate(WaitListEntryInfo expected);
	
	/*
		A method to test the fields for a WaitListEntry after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudWaitListEntry_testDTOFieldsForTestReadAfterUpdate(WaitListEntryInfo expected, WaitListEntryInfo actual);
	
	/*
		A method to set the fields for a WaitListEntry in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudWaitListEntry_setDTOFieldsForTestReadAfterUpdate(WaitListEntryInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getWaitList
			getWaitListsByIds
			getWaitListIdsByType
			createWaitList
			updateWaitList
			deleteWaitList
			getWaitListEntry
			getWaitListEntriesByIds
			getWaitListEntryIdsByType
			createWaitListEntry
			updateWaitListEntry
			deleteWaitListEntry
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getWaitListsByOffering */
	@Test
	public abstract void test_getWaitListsByOffering() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getWaitListsByTypeAndOffering */
	@Test
	public abstract void test_getWaitListsByTypeAndOffering() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForWaitListIds */
	@Test
	public abstract void test_searchForWaitListIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForWaitLists */
	@Test
	public abstract void test_searchForWaitLists() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateWaitList */
	@Test
	public abstract void test_validateWaitList() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: changeWaitListState */
	@Test
	public abstract void test_changeWaitListState()
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

	/* Method Name: getWaitListEntriesByStudent */
	@Test
	public abstract void test_getWaitListEntriesByStudent() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getWaitListEntriesByWaitList */
	@Test
	public abstract void test_getWaitListEntriesByWaitList() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getWaitListEntriesByWaitListAndStudent */
	@Test
	public abstract void test_getWaitListEntriesByWaitListAndStudent() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForWaitListEntryIds */
	@Test
	public abstract void test_searchForWaitListEntryIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForWaitListEntries */
	@Test
	public abstract void test_searchForWaitListEntries() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateWaitListEntry */
	@Test
	public abstract void test_validateWaitListEntry() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: changeWaitListEntryState */
	@Test
	public abstract void test_changeWaitListEntryState()
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

	/* Method Name: reorderWaitListEntries */
	@Test
	public abstract void test_reorderWaitListEntries() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: moveWaitListEntryToPosition */
	@Test
	public abstract void test_moveWaitListEntryToPosition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    private void check(TypeStateEntityInfo expected, TypeStateEntityInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
    }


    private void check(RelationshipInfo expected, RelationshipInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
    }
}



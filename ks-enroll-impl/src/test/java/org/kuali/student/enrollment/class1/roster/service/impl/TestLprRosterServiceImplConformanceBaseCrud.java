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
package org.kuali.student.enrollment.class1.roster.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.enrollment.roster.service.LprRosterService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lprRoster-test-with-map-context.xml"})
public abstract class TestLprRosterServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource
	public LprRosterService testService;
    @Resource
    protected LprRosterDataLoader dataLoader;
    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    public LprRosterService getLprRosterService() {
        return testService;
    }
	public void setLprRosterService(LprRosterService service) {
        testService = service;
    }
	

	
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
	//           LprRosterInfo
	// ****************************************************
	@Test
	public void testCrudLprRoster()
            throws Exception {

            dataLoader.beforeTest();
			// -------------------------------------
			// test create
			// -------------------------------------
			LprRosterInfo expected = new LprRosterInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudLprRoster_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			LprRosterInfo actual = testService.createLprRoster (expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudLprRoster_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getLprRoster ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudLprRoster_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getStateKey() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudLprRoster_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateLprRoster ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudLprRoster_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getLprRoster ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudLprRoster_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			LprRosterInfo alphaDTO = actual;
			
			// create a 2nd DTO
			LprRosterInfo betaDTO = new LprRosterInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudLprRoster_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createLprRoster ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> lprRosterIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<LprRosterInfo> records = testService.getLprRostersByIds ( lprRosterIds, contextInfo);
			
			assertEquals(lprRosterIds.size(), records.size());
			assertEquals(0, lprRosterIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			lprRosterIds = new ArrayList<String>();
			lprRosterIds.add(alphaDTO.getId());
			lprRosterIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getLprRostersByIds ( lprRosterIds, contextInfo);
			
			assertEquals(lprRosterIds.size(), records.size());
			for (LprRosterInfo record : records)
			{
					if (!lprRosterIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, lprRosterIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			lprRosterIds = testService.getLprRosterIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, lprRosterIds.size());
			assertEquals(alphaDTO.getId(), lprRosterIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			lprRosterIds = testService.getLprRosterIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, lprRosterIds.size());
			assertEquals(betaDTO.getId(), lprRosterIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteLprRoster ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					LprRosterInfo record = testService.getLprRoster ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a LprRoster in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudLprRoster_setDTOFieldsForTestCreate(LprRosterInfo expected);
	
	/*
		A method to test the fields for a LprRoster. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudLprRoster_testDTOFieldsForTestCreateUpdate(LprRosterInfo expected, LprRosterInfo actual);
	
	/*
		A method to set the fields for a LprRoster in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudLprRoster_setDTOFieldsForTestUpdate(LprRosterInfo expected);
	
	/*
		A method to test the fields for a LprRoster after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudLprRoster_testDTOFieldsForTestReadAfterUpdate(LprRosterInfo expected, LprRosterInfo actual);
	
	/*
		A method to set the fields for a LprRoster in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudLprRoster_setDTOFieldsForTestReadAfterUpdate(LprRosterInfo expected);
	
	
	// ****************************************************
	//           LprRosterEntryInfo
	// ****************************************************
	@Test
	public void testCrudLprRosterEntry()
            throws Exception {

            dataLoader.beforeTest();
			// -------------------------------------
			// test create
			// -------------------------------------
			LprRosterEntryInfo expected = new LprRosterEntryInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudLprRosterEntry_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			LprRosterEntryInfo actual = testService.createLprRosterEntry (expected.getLprRosterId(), expected.getLprId(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			//new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudLprRosterEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getLprRosterEntry (actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			//new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudLprRosterEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudLprRosterEntry_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateLprRosterEntry (expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			//new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudLprRosterEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getLprRosterEntry (actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			//new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudLprRosterEntry_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			LprRosterEntryInfo alphaDTO = actual;
			
			// create a 2nd DTO
			LprRosterEntryInfo betaDTO = new LprRosterEntryInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudLprRosterEntry_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createLprRosterEntry(betaDTO.getLprRosterId(), betaDTO.getLprId(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> lprRosterEntryIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<LprRosterEntryInfo> records = testService.getLprRosterEntriesByIds(lprRosterEntryIds, contextInfo);
			
			assertEquals(lprRosterEntryIds.size(), records.size());
			assertEquals(0, lprRosterEntryIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			lprRosterEntryIds = new ArrayList<String>();
			lprRosterEntryIds.add(alphaDTO.getId());
			lprRosterEntryIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getLprRosterEntriesByIds(lprRosterEntryIds, contextInfo);
			
			assertEquals(lprRosterEntryIds.size(), records.size());
			for (LprRosterEntryInfo record : records)
			{
					if (!lprRosterEntryIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, lprRosterEntryIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			lprRosterEntryIds = testService.getLprRosterEntryIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, lprRosterEntryIds.size());
			assertEquals(alphaDTO.getId(), lprRosterEntryIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			lprRosterEntryIds = testService.getLprRosterEntryIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, lprRosterEntryIds.size());
			assertEquals(betaDTO.getId(), lprRosterEntryIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteLprRosterEntry ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					LprRosterEntryInfo record = testService.getLprRosterEntry ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a LprRosterEntry in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudLprRosterEntry_setDTOFieldsForTestCreate(LprRosterEntryInfo expected);
	
	/*
		A method to test the fields for a LprRosterEntry. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudLprRosterEntry_testDTOFieldsForTestCreateUpdate(LprRosterEntryInfo expected, LprRosterEntryInfo actual);
	
	/*
		A method to set the fields for a LprRosterEntry in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudLprRosterEntry_setDTOFieldsForTestUpdate(LprRosterEntryInfo expected);
	
	/*
		A method to test the fields for a LprRosterEntry after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudLprRosterEntry_testDTOFieldsForTestReadAfterUpdate(LprRosterEntryInfo expected, LprRosterEntryInfo actual);
	
	/*
		A method to set the fields for a LprRosterEntry in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudLprRosterEntry_setDTOFieldsForTestReadAfterUpdate(LprRosterEntryInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getLprRoster
			getLprRostersByIds
			getLprRosterIdsByType
			createLprRoster
			updateLprRoster
			deleteLprRoster
			getLprRosterEntry
			getLprRosterEntriesByIds
			getLprRosterEntryIdsByType
			createLprRosterEntry
			updateLprRosterEntry
			deleteLprRosterEntry
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getLprRostersByLui */
	@Test
	public abstract void test_getLprRostersByLui() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getLprRostersByTypeAndLui */
	@Test
	public abstract void test_getLprRostersByTypeAndLui() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForLprRosterIds */
	@Test
	public abstract void test_searchForLprRosterIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForLprRosters */
	@Test
	public abstract void test_searchForLprRosters() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateLprRoster */
	@Test
	public abstract void test_validateLprRoster() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getLprRosterEntriesByLprRoster */
	@Test
	public abstract void test_getLprRosterEntriesByLprRoster() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getLprRosterEntriesByLpr */
	@Test
	public abstract void test_getLprRosterEntriesByLpr() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: getLprRosterEntriesByLprRosterAndLpr */
	@Test
	public abstract void test_getLprRosterEntriesByLprRosterAndLpr() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForLprRosterEntryIds */
	@Test
	public abstract void test_searchForLprRosterEntryIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForLprRosterEntries */
	@Test
	public abstract void test_searchForLprRosterEntries() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateLprRosterEntry */
	@Test
	public abstract void test_validateLprRosterEntry() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: moveLprRosterEntryToPosition */
	@Test
	public abstract void test_moveLprRosterEntryToPosition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: reorderLprRosterEntries */
	@Test
	public abstract void test_reorderLprRosterEntries() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
}



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
package org.kuali.student.enrollment.class2.bundledoffering.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.KeyEntityTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.enrollment.bundledoffering.dto.BundledOfferingInfo;
import org.kuali.student.enrollment.bundledoffering.service.BundledOfferingService;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@Transactional
public abstract class TestBundledOfferingServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource
	public BundledOfferingService testService;
	public BundledOfferingService getBundledOfferingService() { return testService; }
	public void setBundledOfferingService(BundledOfferingService service) { testService = service; }
	
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
	//           BundledOfferingInfo
	// ****************************************************
	@Test
	public void testCrudBundledOffering() 
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
			BundledOfferingInfo expected = new BundledOfferingInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudBundledOffering_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			BundledOfferingInfo actual = testService.createBundledOffering ( expected.getCourseBundleId(), expected.getTermId(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudBundledOffering_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getBundledOffering ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudBundledOffering_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			BundledOfferingInfo original = new BundledOfferingInfo (actual);
			expected = new BundledOfferingInfo (actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudBundledOffering_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateBundledOffering ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudBundledOffering_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			try {
       			testService.updateBundledOffering ( original.getId(), original, contextInfo);
                fail("VersionMismatchException should have been thrown");
			}
			catch (VersionMismatchException e) {
                assertNotNull(e.getMessage());
                assertEquals("1", e.getMessage());
            }

			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getBundledOffering ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudBundledOffering_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			BundledOfferingInfo alphaDTO = actual;
			
			// create a 2nd DTO
			BundledOfferingInfo betaDTO = new BundledOfferingInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudBundledOffering_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createBundledOffering (betaDTO.getCourseBundleId(), betaDTO.getTermId(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> bundledOfferingIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<BundledOfferingInfo> records = testService.getBundledOfferingsByIds ( bundledOfferingIds, contextInfo);
			
			assertEquals(bundledOfferingIds.size(), records.size());
			assertEquals(0, bundledOfferingIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			bundledOfferingIds = new ArrayList<String>();
			bundledOfferingIds.add(alphaDTO.getId());
			bundledOfferingIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getBundledOfferingsByIds ( bundledOfferingIds, contextInfo);
			
			assertEquals(bundledOfferingIds.size(), records.size());
			for (BundledOfferingInfo record : records)
			{
					if (!bundledOfferingIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, bundledOfferingIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			bundledOfferingIds = testService.getBundledOfferingIdsByType ("typeKey_Updated", contextInfo);
			
			assertEquals(1, bundledOfferingIds.size());
			assertEquals(alphaDTO.getId(), bundledOfferingIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			bundledOfferingIds = testService.getBundledOfferingIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, bundledOfferingIds.size());
			assertEquals(betaDTO.getId(), bundledOfferingIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteBundledOffering ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					BundledOfferingInfo record = testService.getBundledOffering ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
                assertNotNull(dnee.getMessage());
                assertEquals(actual.getId(), dnee.getMessage());
			}
			
	}
	
	/*
		A method to set the fields for a BundledOffering in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudBundledOffering_setDTOFieldsForTestCreate(BundledOfferingInfo expected);
	
	/*
		A method to test the fields for a BundledOffering. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudBundledOffering_testDTOFieldsForTestCreateUpdate(BundledOfferingInfo expected, BundledOfferingInfo actual);
	
	/*
		A method to set the fields for a BundledOffering in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudBundledOffering_setDTOFieldsForTestUpdate(BundledOfferingInfo expected);
	
	/*
		A method to test the fields for a BundledOffering after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudBundledOffering_testDTOFieldsForTestReadAfterUpdate(BundledOfferingInfo expected, BundledOfferingInfo actual);
	
	/*
		A method to set the fields for a BundledOffering in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudBundledOffering_setDTOFieldsForTestReadAfterUpdate(BundledOfferingInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getBundledOffering
			getBundledOfferingsByIds
			getBundledOfferingIdsByType
			createBundledOffering
			updateBundledOffering
			deleteBundledOffering
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getBundledOfferingsByCourseBundle */
	@Test
	public abstract void test_getBundledOfferingsByCourseBundle() throws Exception;
	
	/* Method Name: getBundledOfferingsByTerm */
	@Test
	public abstract void test_getBundledOfferingsByTerm() throws Exception;
	
	/* Method Name: getBundledOfferingsByCourseBundleAndTerm */
	@Test
	public abstract void test_getBundledOfferingsByCourseBundleAndTerm() throws Exception;
	
	/* Method Name: getBundledOfferingsByRegistrationGroup */
	@Test
	public abstract void test_getBundledOfferingsByRegistrationGroup() throws Exception;
	
	/* Method Name: getBundledOfferingsByTermAndCode */
	@Test
	public abstract void test_getBundledOfferingsByTermAndCode() throws Exception;
	
	/* Method Name: getBundledOfferingsByTermAndSubjectAreaOrg */
	@Test
	public abstract void test_getBundledOfferingsByTermAndSubjectAreaOrg() throws Exception;
	
	/* Method Name: searchForBundledOfferingIds */
	@Test
	public abstract void test_searchForBundledOfferingIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForBundledOfferings */
	@Test
	public abstract void test_searchForBundledOfferings() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateBundledOffering */
	@Test
	public abstract void test_validateBundledOffering() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: changeBundledOfferingState */
	@Test
	public abstract void test_changeBundledOfferingState() throws Exception;
	
}



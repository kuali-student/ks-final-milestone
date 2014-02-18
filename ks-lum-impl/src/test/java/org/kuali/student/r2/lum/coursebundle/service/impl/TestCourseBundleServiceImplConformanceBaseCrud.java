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
package org.kuali.student.r2.lum.coursebundle.service.impl;


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
import org.kuali.student.lum.coursebundle.dto.CourseBundleInfo;
import org.kuali.student.lum.coursebundle.service.CourseBundleService;
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
public abstract class TestCourseBundleServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource
	public CourseBundleService testService;
	public CourseBundleService getCourseBundleService() { return testService; }
	public void setCourseBundleService(CourseBundleService service) { testService = service; }
	
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
	//           CourseBundleInfo
	// ****************************************************
	@Test
	public void testCrudCourseBundle() 
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
			CourseBundleInfo expected = new CourseBundleInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudCourseBundle_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			CourseBundleInfo actual = testService.createCourseBundle ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudCourseBundle_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getCourseBundle ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudCourseBundle_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			CourseBundleInfo original = new CourseBundleInfo (actual);
			expected = new CourseBundleInfo (actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudCourseBundle_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateCourseBundle ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudCourseBundle_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testService.updateCourseBundle ( original.getId(), original, contextInfo);
			}
			catch (VersionMismatchException e) { 
   			exception = true;			}
			
			Assert.assertTrue("VersionMissmatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getCourseBundle ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			new IdEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudCourseBundle_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			CourseBundleInfo alphaDTO = actual;
			
			// create a 2nd DTO
			CourseBundleInfo betaDTO = new CourseBundleInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudCourseBundle_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createCourseBundle ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> courseBundleIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<CourseBundleInfo> records = testService.getCourseBundlesByIds ( courseBundleIds, contextInfo);
			
			assertEquals(courseBundleIds.size(), records.size());
			assertEquals(0, courseBundleIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			courseBundleIds = new ArrayList<String>();
			courseBundleIds.add(alphaDTO.getId());
			courseBundleIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getCourseBundlesByIds ( courseBundleIds, contextInfo);
			
			assertEquals(courseBundleIds.size(), records.size());
			for (CourseBundleInfo record : records)
			{
					if (!courseBundleIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, courseBundleIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			courseBundleIds = testService.getCourseBundleIdsByType ("typeKey_Updated", contextInfo);
			
			assertEquals(1, courseBundleIds.size());
			assertEquals(alphaDTO.getId(), courseBundleIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			courseBundleIds = testService.getCourseBundleIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, courseBundleIds.size());
			assertEquals(betaDTO.getId(), courseBundleIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteCourseBundle ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					CourseBundleInfo record = testService.getCourseBundle ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a CourseBundle in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudCourseBundle_setDTOFieldsForTestCreate(CourseBundleInfo expected);
	
	/*
		A method to test the fields for a CourseBundle. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudCourseBundle_testDTOFieldsForTestCreateUpdate(CourseBundleInfo expected, CourseBundleInfo actual);
	
	/*
		A method to set the fields for a CourseBundle in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudCourseBundle_setDTOFieldsForTestUpdate(CourseBundleInfo expected);
	
	/*
		A method to test the fields for a CourseBundle after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudCourseBundle_testDTOFieldsForTestReadAfterUpdate(CourseBundleInfo expected, CourseBundleInfo actual);
	
	/*
		A method to set the fields for a CourseBundle in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudCourseBundle_setDTOFieldsForTestReadAfterUpdate(CourseBundleInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getCourseBundle
			getCourseBundlesByIds
			getCourseBundleIdsByType
			createCourseBundle
			updateCourseBundle
			deleteCourseBundle
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: searchForCourseBundleIds */
	@Test
	public abstract void test_searchForCourseBundleIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForCourseBundles */
	@Test
	public abstract void test_searchForCourseBundles() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateCourseBundle */
	@Test
	public abstract void test_validateCourseBundle() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: changeCourseBundleState */
	@Test
	public abstract void test_changeCourseBundleState() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
}



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
package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
@ContextConfiguration(locations = {"classpath:courseWaitList-test-with-map-context.xml"})
public abstract class TestCourseWaitListServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource
	public CourseWaitListService testService;
	public CourseWaitListService getCourseWaitListService() { return testService; }
	public void setCourseWaitListService(CourseWaitListService service) { testService = service; }
	
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
	//           CourseWaitListInfo
	// ****************************************************
	@Test
	public void testCrudCourseWaitList() throws Exception
	{
			// -------------------------------------
			// test create
			// -------------------------------------
			CourseWaitListInfo expected = new CourseWaitListInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudCourseWaitList_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			CourseWaitListInfo actual = testService.createCourseWaitList ( expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudCourseWaitList_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getCourseWaitList ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudCourseWaitList_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudCourseWaitList_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateCourseWaitList ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudCourseWaitList_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getCourseWaitList ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudCourseWaitList_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			CourseWaitListInfo alphaDTO = actual;
			
			// create a 2nd DTO
			CourseWaitListInfo betaDTO = new CourseWaitListInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudCourseWaitList_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createCourseWaitList ( betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> courseWaitListIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<CourseWaitListInfo> records = testService.getCourseWaitListsByIds ( courseWaitListIds, contextInfo);
			
			assertEquals(courseWaitListIds.size(), records.size());
			assertEquals(0, courseWaitListIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			courseWaitListIds = new ArrayList<String>();
			courseWaitListIds.add(alphaDTO.getId());
			courseWaitListIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getCourseWaitListsByIds ( courseWaitListIds, contextInfo);
			
			assertEquals(courseWaitListIds.size(), records.size());
			for (CourseWaitListInfo record : records)
			{
					if (!courseWaitListIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, courseWaitListIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			courseWaitListIds = testService.getCourseWaitListIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, courseWaitListIds.size());
			assertEquals(alphaDTO.getId(), courseWaitListIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			courseWaitListIds = testService.getCourseWaitListIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, courseWaitListIds.size());
			assertEquals(betaDTO.getId(), courseWaitListIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteCourseWaitList ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					CourseWaitListInfo record = testService.getCourseWaitList ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
                assertNotNull(dnee.getMessage());
                assertEquals(actual.getId(), dnee.getMessage());
			}
			
	}
	
	/*
		A method to set the fields for a CourseWaitList in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudCourseWaitList_setDTOFieldsForTestCreate(CourseWaitListInfo expected) throws ParseException;
	
	/*
		A method to test the fields for a CourseWaitList. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudCourseWaitList_testDTOFieldsForTestCreateUpdate(CourseWaitListInfo expected, CourseWaitListInfo actual);
	
	/*
		A method to set the fields for a CourseWaitList in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudCourseWaitList_setDTOFieldsForTestUpdate(CourseWaitListInfo expected) throws ParseException;
	
	/*
		A method to test the fields for a CourseWaitList after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudCourseWaitList_testDTOFieldsForTestReadAfterUpdate(CourseWaitListInfo expected, CourseWaitListInfo actual);
	
	/*
		A method to set the fields for a CourseWaitList in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudCourseWaitList_setDTOFieldsForTestReadAfterUpdate(CourseWaitListInfo expected) throws ParseException;
	
	
	// ****************************************************
	//           CourseWaitListEntryInfo
	// ****************************************************
	@Test
	public void testCrudCourseWaitListEntry() throws Exception
	{
			// -------------------------------------
			// test create
			// -------------------------------------
			CourseWaitListEntryInfo expected = new CourseWaitListEntryInfo ();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudCourseWaitListEntry_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			CourseWaitListEntryInfo actual = testService.createCourseWaitListEntry (expected.getCourseWaitListId(), expected.getStudentId(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudCourseWaitListEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getCourseWaitListEntry ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudCourseWaitListEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			expected = actual;
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudCourseWaitListEntry_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateCourseWaitListEntry ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudCourseWaitListEntry_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getCourseWaitListEntry ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudCourseWaitListEntry_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			CourseWaitListEntryInfo alphaDTO = actual;
			
			// create a 2nd DTO
			CourseWaitListEntryInfo betaDTO = new CourseWaitListEntryInfo ();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudCourseWaitListEntry_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createCourseWaitListEntry ( betaDTO.getCourseWaitListId(), betaDTO.getStudentId(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> courseWaitListEntryIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<CourseWaitListEntryInfo> records = testService.getCourseWaitListEntriesByIds(courseWaitListEntryIds, contextInfo);
			
			assertEquals(courseWaitListEntryIds.size(), records.size());
			assertEquals(0, courseWaitListEntryIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			courseWaitListEntryIds = new ArrayList<String>();
			courseWaitListEntryIds.add(alphaDTO.getId());
			courseWaitListEntryIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getCourseWaitListEntriesByIds(courseWaitListEntryIds, contextInfo);
			
			assertEquals(courseWaitListEntryIds.size(), records.size());
			for (CourseWaitListEntryInfo record : records)
			{
					if (!courseWaitListEntryIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, courseWaitListEntryIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			courseWaitListEntryIds = testService.getCourseWaitListEntryIdsByType ("typeKey01", contextInfo);
			
			assertEquals(1, courseWaitListEntryIds.size());
			assertEquals(alphaDTO.getId(), courseWaitListEntryIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			courseWaitListEntryIds = testService.getCourseWaitListEntryIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, courseWaitListEntryIds.size());
			assertEquals(betaDTO.getId(), courseWaitListEntryIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteCourseWaitListEntry ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					CourseWaitListEntryInfo record = testService.getCourseWaitListEntry ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
                assertNotNull(dnee.getMessage());
                assertEquals(actual.getId(), dnee.getMessage());
            }
			
	}
	
	/*
		A method to set the fields for a CourseWaitListEntry in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudCourseWaitListEntry_setDTOFieldsForTestCreate(CourseWaitListEntryInfo expected) throws ParseException;
	
	/*
		A method to test the fields for a CourseWaitListEntry. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudCourseWaitListEntry_testDTOFieldsForTestCreateUpdate(CourseWaitListEntryInfo expected, CourseWaitListEntryInfo actual);
	
	/*
		A method to set the fields for a CourseWaitListEntry in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudCourseWaitListEntry_setDTOFieldsForTestUpdate(CourseWaitListEntryInfo expected) throws ParseException;
	
	/*
		A method to test the fields for a CourseWaitListEntry after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudCourseWaitListEntry_testDTOFieldsForTestReadAfterUpdate(CourseWaitListEntryInfo expected, CourseWaitListEntryInfo actual);
	
	/*
		A method to set the fields for a CourseWaitListEntry in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudCourseWaitListEntry_setDTOFieldsForTestReadAfterUpdate(CourseWaitListEntryInfo expected) throws ParseException;
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getCourseWaitList
			getCourseWaitListsByIds
			getCourseWaitListIdsByType
			createCourseWaitList
			updateCourseWaitList
			deleteCourseWaitList
			getCourseWaitListEntry
			getCourseWaitListEntriesByIds
			getCourseWaitListEntryIdsByType
			createCourseWaitListEntry
			updateCourseWaitListEntry
			deleteCourseWaitListEntry
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getCourseWaitListsByActivityOffering */
	@Test
	public abstract void test_getCourseWaitListsByActivityOffering() throws Exception;
	
	/* Method Name: getCourseWaitListsByFormatOffering */
	@Test
	public abstract void test_getCourseWaitListsByFormatOffering() throws Exception;
	
	/* Method Name: searchForCourseWaitListIds */
	@Test
	public abstract void test_searchForCourseWaitListIds()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForCourseWaitLists */
	@Test
	public abstract void test_searchForCourseWaitLists()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateCourseWaitList */
	@Test
	public abstract void test_validateCourseWaitList()
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: changeCourseWaitListState */
	@Test
	public abstract void test_changeCourseWaitListState() throws Exception;

	/* Method Name: getCourseWaitListEntriesByStudent */
	@Test
	public abstract void test_getCourseWaitListEntriesByStudent() throws Exception;
	
	/* Method Name: getCourseWaitListEntriesByCourseWaitList */
	@Test
	public abstract void test_getCourseWaitListEntriesByCourseWaitList() throws Exception;
	
	/* Method Name: getCourseWaitListEntriesByCourseWaitListAndStudent */
	@Test
	public abstract void test_getCourseWaitListEntriesByCourseWaitListAndStudent() throws Exception;
	
	/* Method Name: searchForCourseWaitListEntryIds */
	@Test
	public abstract void test_searchForCourseWaitListEntryIds()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: searchForCourseWaitListEntries */
	@Test
	public abstract void test_searchForCourseWaitListEntries()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: validateCourseWaitListEntry */
	@Test
	public abstract void test_validateCourseWaitListEntry()
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;
	
	/* Method Name: changeCourseWaitListEntryState */
	@Test
	public abstract void test_changeCourseWaitListEntryState() throws Exception;

	/* Method Name: reorderCourseWaitListEntries */
	@Test
	public abstract void test_reorderCourseWaitListEntries() throws Exception;
	
	/* Method Name: moveCourseWaitListEntryToPosition */
	@Test
	public abstract void test_moveCourseWaitListEntryToPosition() throws Exception;

    private void check(TypeStateEntityInfo expected, TypeStateEntityInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
    }


    private void check(RelationshipInfo expected, RelationshipInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
    }
}



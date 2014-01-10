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
package org.kuali.student.enrollment.class2.ges.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.AttributeTester;
import org.kuali.student.common.test.MetaTester;
import org.kuali.student.common.test.util.KeyEntityTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
public abstract class TestGesServiceImplConformanceBaseCrud {
	
	// ====================
	// SETUP
	// ====================
	
	@Resource(name = "gesServiceImpl")
	public GesService testService;
	public GesService getGesService() { return testService; }
	public void setGesService(GesService service) { testService = service; }
	
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
	//           ParameterInfo
	// ****************************************************
	@Test
	public void testCrudParameter() 
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
			ParameterInfo expected = new ParameterInfo();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudParameter_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			ParameterInfo actual = testService.createParameter ( expected.getValueTypeKey(), expected.getKey(), expected.getTypeKey(), expected, contextInfo);
			
			assertNotNull(actual.getKey());
        new KeyEntityTester().check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudParameter_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getParameter ( actual.getKey(), contextInfo);
			assertEquals(expected.getKey(), actual.getKey());
            new KeyEntityTester().check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudParameter_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			ParameterInfo original = new ParameterInfo(actual);
			expected = new ParameterInfo(actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudParameter_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateParameter ( expected.getKey(), expected, contextInfo);
			
			assertEquals(expected.getKey(), actual.getKey());
            new KeyEntityTester().check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudParameter_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testService.updateParameter ( original.getKey(), original, contextInfo);
			}
			catch (VersionMismatchException e) {
   			exception = true;			}
			
			Assert.assertTrue("VersionMissmatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getParameter ( actual.getKey(), contextInfo);
			
			assertEquals(expected.getKey(), actual.getKey());
            new KeyEntityTester().check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudParameter_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			ParameterInfo alphaDTO = actual;
			
			// create a 2nd DTO
			ParameterInfo betaDTO = new ParameterInfo();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudParameter_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createParameter (betaDTO.getValueTypeKey(), betaDTO.getKey(), betaDTO.getTypeKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> parameterIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<ParameterInfo> records = testService.getParametersByKeys ( parameterIds, contextInfo);
			
			assertEquals(parameterIds.size(), records.size());
			assertEquals(0, parameterIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			parameterIds = new ArrayList<String>();
			parameterIds.add(alphaDTO.getKey());
			parameterIds.add(betaDTO.getKey());
			// code to get DTO by Ids
			records = testService.getParametersByKeys ( parameterIds, contextInfo);
			
			assertEquals(parameterIds.size(), records.size());
			for (ParameterInfo record : records)
			{
					if (!parameterIds.remove(record.getKey()))
					{
							fail(record.getKey());
					}
			}
			assertEquals(0, parameterIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			parameterIds = testService.getParameterKeysByType ("typeKey01", contextInfo);
			
			assertEquals(1, parameterIds.size());
			assertEquals(alphaDTO.getKey(), parameterIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			parameterIds = testService.getParameterKeysByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, parameterIds.size());
			assertEquals(betaDTO.getKey(), parameterIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteParameter ( actual.getKey(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					ParameterInfo record = testService.getParameter ( actual.getKey(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}
	
	/*
		A method to set the fields for a Parameter in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudParameter_setDTOFieldsForTestCreate(ParameterInfo expected);
	
	/*
		A method to test the fields for a Parameter. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudParameter_testDTOFieldsForTestCreateUpdate(ParameterInfo expected, ParameterInfo actual);
	
	/*
		A method to set the fields for a Parameter in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudParameter_setDTOFieldsForTestUpdate(ParameterInfo expected);
	
	/*
		A method to test the fields for a Parameter after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudParameter_testDTOFieldsForTestReadAfterUpdate(ParameterInfo expected, ParameterInfo actual);
	
	/*
		A method to set the fields for a Parameter in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudParameter_setDTOFieldsForTestReadAfterUpdate(ParameterInfo expected);
	
	
	// ****************************************************
	//           ValueInfo
	// ****************************************************
	@Test
	public void testCrudValue() 
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
			ValueInfo expected = new ValueInfo();
			
			// METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
			testCrudValue_setDTOFieldsForTestCreate (expected);
			
			new AttributeTester().add2ForCreate(expected.getAttributes());
			
			// code to create actual
			ValueInfo actual = testService.createValue (expected.getTypeKey(), expected.getParameterKey(), expected, contextInfo);
			
			assertNotNull(actual.getId());
			check(expected, actual);
			
			// METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
			testCrudValue_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterCreate(actual.getMeta());
			
			// -------------------------------------
			// test read
			// -------------------------------------
			expected = actual;
			actual = testService.getValue ( actual.getId(), contextInfo);
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT CODE FOR TESTING MORE DTO FIELDS HERE
			testCrudValue_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			// -------------------------------------
			// test update
			// -------------------------------------
			ValueInfo original = new ValueInfo(actual);
			expected = new ValueInfo(actual);
			
			expected.setStateKey(expected.getState() + "_Updated");
			
			// METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
			testCrudValue_setDTOFieldsForTestUpdate (expected);
			
			new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
			// code to update
			actual = testService.updateValue ( expected.getId(), expected, contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
			testCrudValue_testDTOFieldsForTestCreateUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
			
			// Test that VersionMissmatchException's are being detected
			boolean exception = false;
			try {
   			testService.updateValue ( original.getId(), original, contextInfo);
			}
			catch (VersionMismatchException e) {
   			exception = true;			}
			
			Assert.assertTrue("VersionMissmatchException was not detected!", exception);
			
			// -------------------------------------
			// test read after update
			// -------------------------------------
			
			expected = actual;
			// code to get actual
			actual = testService.getValue ( actual.getId(), contextInfo);
			
			assertEquals(expected.getId(), actual.getId());
			check(expected, actual);
			
			// INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
			testCrudValue_testDTOFieldsForTestReadAfterUpdate (expected, actual);
			
			new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
			new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
			
			ValueInfo alphaDTO = actual;
			
			// create a 2nd DTO
			ValueInfo betaDTO = new ValueInfo();
			
			// METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
			testCrudValue_setDTOFieldsForTestReadAfterUpdate (betaDTO);
			
			betaDTO.setTypeKey("typeKeyBeta");
			betaDTO.setStateKey("stateKeyBeta");
			betaDTO = testService.createValue (betaDTO.getTypeKey(), betaDTO.getParameterKey(), betaDTO, contextInfo);
			
			// -------------------------------------
			// test bulk get with no ids supplied
			// -------------------------------------
			
			List<String> valueIds = new ArrayList<String>();
			// code to get DTO by Ids
			List<ValueInfo> records = testService.getValuesByIds ( valueIds, contextInfo);
			
			assertEquals(valueIds.size(), records.size());
			assertEquals(0, valueIds.size());
			
			// -------------------------------------
			// test bulk get
			// -------------------------------------
			valueIds = new ArrayList<String>();
			valueIds.add(alphaDTO.getId());
			valueIds.add(betaDTO.getId());
			// code to get DTO by Ids
			records = testService.getValuesByIds ( valueIds, contextInfo);
			
			assertEquals(valueIds.size(), records.size());
			for (ValueInfo record : records)
			{
					if (!valueIds.remove(record.getId()))
					{
							fail(record.getId());
					}
			}
			assertEquals(0, valueIds.size());
			
			// -------------------------------------
			// test get by type
			// -------------------------------------
			// code to get by specific type "typeKey01" 
			valueIds = testService.getValueIdsByType (GesServiceDataLoader.VALUE_TYPE_STRING, contextInfo);
			
			assertEquals(1, valueIds.size());
			assertEquals(alphaDTO.getId(), valueIds.get(0));
			
			// test get by other type
			// code to get by specific type "typeKeyBeta" 
			valueIds = testService.getValueIdsByType ("typeKeyBeta", contextInfo);
			
			assertEquals(1, valueIds.size());
			assertEquals(betaDTO.getId(), valueIds.get(0));
			
			// -------------------------------------
			// test delete
			// -------------------------------------
			
			StatusInfo status = testService.deleteValue ( actual.getId(), contextInfo);
			
			assertNotNull(status);
			assertTrue(status.getIsSuccess());
			try
			{
					ValueInfo record = testService.getValue ( actual.getId(), contextInfo);
					fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
			}
			catch (DoesNotExistException dnee)
			{
					// expected
			}
			
	}


    private void check(IdNamelessEntityInfo expected, IdNamelessEntityInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
    }
	
	/*
		A method to set the fields for a Value in a 'test create' section prior to calling the 'create' operation.
	*/
	public abstract void testCrudValue_setDTOFieldsForTestCreate(ValueInfo expected);
	
	/*
		A method to test the fields for a Value. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public abstract void testCrudValue_testDTOFieldsForTestCreateUpdate(ValueInfo expected, ValueInfo actual);
	
	/*
		A method to set the fields for a Value in a 'test update' section prior to calling the 'update' operation.
	*/
	public abstract void testCrudValue_setDTOFieldsForTestUpdate(ValueInfo expected);
	
	/*
		A method to test the fields for a Value after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public abstract void testCrudValue_testDTOFieldsForTestReadAfterUpdate(ValueInfo expected, ValueInfo actual);
	
	/*
		A method to set the fields for a Value in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public abstract void testCrudValue_setDTOFieldsForTestReadAfterUpdate(ValueInfo expected);
	
	
	// ========================================
	// SERVICE OPS TESTED IN BASE TEST CLASS
	// ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getParameter
			getParametersByIds
			getParameterIdsByType
			createParameter
			updateParameter
			deleteParameter
			getValue
			getValuesByIds
			getValueIdsByType
			createValue
			updateValue
			deleteValue
	*/
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: searchForParameterIds */
	@Test
	public abstract void test_searchForParameterIds() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: searchForParameters */
	@Test
	public abstract void test_searchForParameters() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: validateParameter */
	@Test
	public abstract void test_validateParameter() 
	throws DoesNotExistException,InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: searchForValueIds */
	@Test
	public abstract void test_searchForValueIds() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: searchForValues */
	@Test
	public abstract void test_searchForValues() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: validateValue */
	@Test
	public abstract void test_validateValue() 
	throws DoesNotExistException,InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: getValuesByParameter */
	@Test
	public abstract void test_getValuesByParameter() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: evaluateValuesByParameterAndPerson */
	@Test
	public abstract void test_evaluateValues()
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
	/* Method Name: evaluateValuesByParameterAndPersonAndAtpAndOnDate */
	@Test
	public abstract void test_evaluateValuesOnDate()
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException;
	
}



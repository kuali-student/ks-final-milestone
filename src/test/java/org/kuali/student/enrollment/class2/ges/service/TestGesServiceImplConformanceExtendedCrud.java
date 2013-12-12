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


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.ges.dto.ParameterInfo;
import org.kuali.student.r2.core.ges.dto.ValueInfo;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
public abstract class TestGesServiceImplConformanceExtendedCrud extends TestGesServiceImplConformanceBaseCrud
{

    @Resource
    private GesServiceDataLoader dataLoader;

    @After
    public void cleanup() throws Exception {
        dataLoader.afterTest();
    }

	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           ParameterInfo
	// ****************************************************
	
	/*
		A method to set the fields for a Parameter in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudParameter_setDTOFieldsForTestCreate(ParameterInfo expected)
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setKey("key01");
		expected.setValueTypeKey("valueTypeKey01");
	}
	
	/*
		A method to test the fields for a Parameter. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudParameter_testDTOFieldsForTestCreateUpdate(ParameterInfo expected, ParameterInfo actual)
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getKey(), actual.getKey());
		assertEquals (expected.getValueTypeKey(), actual.getValueTypeKey());
	}
	
	/*
		A method to set the fields for a Parameter in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudParameter_setDTOFieldsForTestUpdate(ParameterInfo expected)
	{
		expected.setStateKey("stateKey_Updated");
	}
	
	/*
		A method to test the fields for a Parameter after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudParameter_testDTOFieldsForTestReadAfterUpdate(ParameterInfo expected, ParameterInfo actual)
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getKey(), actual.getKey());
		assertEquals (expected.getValueTypeKey(), actual.getValueTypeKey());
	}
	
	/*
		A method to set the fields for a Parameter in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudParameter_setDTOFieldsForTestReadAfterUpdate(ParameterInfo expected)
	{
		expected.setKey("key_Updated");
		expected.setValueTypeKey("valueTypeKey_Updated");
	}
	
	
	// ****************************************************
	//           ValueInfo
	// ****************************************************
	
	/*
		A method to set the fields for a Value in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudValue_setDTOFieldsForTestCreate(ValueInfo expected)
	{
		expected.setTypeKey(GesServiceDataLoader.VALUE_TYPE_STRING);
		expected.setStateKey("stateKey01");
		expected.setParameterId("parameterId01");
		expected.setAtpTypeKey("atpTypeKey01");
		expected.setPopulationId("populationId01");
		expected.setRuleId("ruleId01");
        expected.setEffectiveDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("1600-06-12"));
        expected.setExpirationDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("3030-01-01"));
		expected.setStringValue("stringValue01");
	}
	
	/*
		A method to test the fields for a Value. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudValue_testDTOFieldsForTestCreateUpdate(ValueInfo expected, ValueInfo actual)
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getParameterId(), actual.getParameterId());
		assertEquals (expected.getAtpTypeKey(), actual.getAtpTypeKey());
		assertEquals (expected.getPopulationId(), actual.getPopulationId());
		assertEquals (expected.getRuleId(), actual.getRuleId());
		assertEquals (expected.getStringValue(), actual.getStringValue());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
	}
	
	/*
		A method to set the fields for a Value in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudValue_setDTOFieldsForTestUpdate(ValueInfo expected)
	{
		expected.setStateKey("stateKey_Updated");
		expected.setAtpTypeKey("atpTypeKey_Updated");
		expected.setPopulationId("populationId_Updated");
		expected.setRuleId("ruleId_Updated");
        expected.setEffectiveDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("1902-12-25"));
        expected.setExpirationDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-10-10"));
		expected.setStringValue("stringValue_Updated");
	}
	
	/*
		A method to test the fields for a Value after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudValue_testDTOFieldsForTestReadAfterUpdate(ValueInfo expected, ValueInfo actual)
	{
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getParameterId(), actual.getParameterId());
        assertEquals (expected.getAtpTypeKey(), actual.getAtpTypeKey());
        assertEquals (expected.getPopulationId(), actual.getPopulationId());
        assertEquals (expected.getRuleId(), actual.getRuleId());
        assertEquals (expected.getStringValue(), actual.getStringValue());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
	}
	
	/*
		A method to set the fields for a Value in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudValue_setDTOFieldsForTestReadAfterUpdate(ValueInfo expected)
	{
        expected.setTypeKey(GesServiceDataLoader.VALUE_TYPE_BOOLEAN);
        expected.setParameterId("parameterId02");
        expected.setStateKey("stateKey2");
        expected.setAtpTypeKey("atpTypeKey2");
        expected.setPopulationId("populationId2");
        expected.setRuleId("ruleId2");
        expected.setEffectiveDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("1902-12-26"));
        expected.setExpirationDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-10-11"));
        expected.setBooleanValue(true);
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: searchForParameterIds */
	@Test
	public void test_searchForParameterIds() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
	}
	
	/* Method Name: searchForParameters */
	@Test
	public void test_searchForParameters() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
	}
	
	/* Method Name: validateParameter */
	@Test
	public void test_validateParameter() 
	throws DoesNotExistException,InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
	}
	
	/* Method Name: searchForValueIds */
	@Test
	public void test_searchForValueIds() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
	}
	
	/* Method Name: searchForValues */
	@Test
	public void test_searchForValues() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
	}
	
	/* Method Name: validateValue */
	@Test
	public void test_validateValue() 
	throws DoesNotExistException,InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
	}
	
	/* Method Name: getValuesByParameter */
	@Test
	public void test_getValuesByParameter() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
        loadData();

        List<ValueInfo> values = testService.getValuesByParameter("1", contextInfo);
        assertEquals(3, values.size());
        assertTrue(containsValue("1", values));
        assertTrue(containsValue("2", values));
        assertTrue(containsValue("3", values));

        values = testService.getValuesByParameter("2", contextInfo);
        assertEquals(2, values.size());
        assertTrue(containsValue("4", values));
        assertTrue(containsValue("5", values));

        values = testService.getValuesByParameter("A_BAD_PARAMETER_ID", contextInfo);
        assertEquals(0, values.size());
	}
	
	/* Method Name: evaluateValuesByParameterAndPerson */
	@Test
	public void test_evaluateValuesByParameterAndPerson() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
        loadData();
	}
	
	/* Method Name: evaluateValuesByParameterAndPersonAndAtpAndOnDate */
	@Test
	public void test_evaluateValuesByParameterAndPersonAndAtpAndOnDate() 
	throws InvalidParameterException,MissingParameterException,OperationFailedException,PermissionDeniedException {
        loadData();
	}

    private void loadData() throws OperationFailedException {
        try {
            dataLoader.beforeTest();
        } catch (Exception e) {
            throw new OperationFailedException("failed to load data", e);
        }
    }

    private boolean containsValue(String id, List<ValueInfo> values) {
        for(ValueInfo info : values) {
            if(info.getId().equals(id))
                return true;
        }
        return false;
    }
}



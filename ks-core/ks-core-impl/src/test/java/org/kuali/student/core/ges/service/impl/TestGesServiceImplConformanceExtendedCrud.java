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
package org.kuali.student.core.ges.service.impl;


import net.sf.ehcache.Cache;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.infc.GesValueTypeEnum;
import org.kuali.student.core.ges.service.decorators.GesServiceCacheDecorator;
import org.kuali.student.core.population.service.impl.PopulationTestStudentEnum;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Transactional
public abstract class TestGesServiceImplConformanceExtendedCrud extends TestGesServiceImplConformanceBaseCrud
{

    @Resource(name = "gesDataLoader")
    private GesServiceDataLoader dataLoader;
    //@Resource(name = "gesServiceImpl")
    private GesServiceCacheDecorator cacheDecorator;

    public GesServiceCacheDecorator getCacheDecorator() {
        return cacheDecorator;
    }

    public void setCacheDecorator(GesServiceCacheDecorator cacheDecorator) {
        this.cacheDecorator = cacheDecorator;
    }

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
        expected.setGesValueTypeEnum(GesValueTypeEnum.STRING);
        expected.setName("Max Credit");
        expected.setDescr((RichTextHelper.buildRichTextInfo("The max credit student can take in a period of time.", "formatted descr")));
        expected.setRequireUniquePriorities(true);
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
        assertEquals(expected.getGesValueTypeEnum(),actual.getGesValueTypeEnum());
        assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(),actual.getDescr());
        assertEquals(expected.getRequireUniquePriorities(), actual.getRequireUniquePriorities());
    }

    /*
         A method to set the fields for a Parameter in a 'test update' section prior to calling the 'update' operation.
     */
    public void testCrudParameter_setDTOFieldsForTestUpdate(ParameterInfo expected)
    {
        expected.setStateKey("stateKey_Updated");
        expected.setName("My Max Credit");
        expected.setGesValueTypeEnum(GesValueTypeEnum.BOOLEAN);
        expected.setDescr((RichTextHelper.buildRichTextInfo("My max credit student can take in a period of time.", "formatted descr")));
        expected.setRequireUniquePriorities(false);
    }

    /*
         A method to test the fields for a Parameter after an update operation, followed by a read operation,
         where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public void testCrudParameter_testDTOFieldsForTestReadAfterUpdate(ParameterInfo expected, ParameterInfo actual)
    {
        assertEquals (expected.getKey(), actual.getKey());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getKey(), actual.getKey());
        assertEquals(expected.getGesValueTypeEnum(), actual.getGesValueTypeEnum());
        assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(),actual.getDescr());
        assertEquals (expected.getRequireUniquePriorities(), actual.getRequireUniquePriorities());
    }

    /*
         A method to set the fields for a Parameter in the 'test read after update' section.
         This dto is another (second) dto object being created for other tests.
     */
    public void testCrudParameter_setDTOFieldsForTestReadAfterUpdate(ParameterInfo expected)
    {
        expected.setKey("key_Updated");
        expected.setGesValueTypeEnum(GesValueTypeEnum.NUMERIC);
        expected.setName("MaxCreditLimit");
        expected.setDescr((RichTextHelper.buildRichTextInfo("Limit max credit student can take in a period of time.", "formatted descr")));
        expected.setRequireUniquePriorities(false);
    }


    // ****************************************************
    //           ValueInfo
    // ****************************************************

    /*
         A method to set the fields for a Value in a 'test create' section prior to calling the 'create' operation.
     */
    public void testCrudValue_setDTOFieldsForTestCreate(ValueInfo expected)
    {
        expected.setStateKey("stateKey01");
        expected.setParameterKey("max.credit");
        expected.setAtpTypeKey("atpTypeKey01");
        expected.setPopulationId("populationId01");
        expected.setRuleId("ruleId01");
        expected.setEffectiveDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("1600-06-12"));
        expected.setExpirationDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("3030-01-01"));
        expected.setStringValue("stringValue01");
        expected.setTypeKey("typeKey01");
        expected.setPriority(5);
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
        assertEquals (expected.getParameterKey(), actual.getParameterKey());
        assertEquals (expected.getAtpTypeKey(), actual.getAtpTypeKey());
        assertEquals (expected.getPopulationId(), actual.getPopulationId());
        assertEquals (expected.getRuleId(), actual.getRuleId());
        assertEquals (expected.getStringValue(), actual.getStringValue());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getPriority(), actual.getPriority());
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
        expected.setPriority(1);
    }

    /*
         A method to test the fields for a Value after an update operation, followed by a read operation,
         where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public void testCrudValue_testDTOFieldsForTestReadAfterUpdate(ValueInfo expected, ValueInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getParameterKey(), actual.getParameterKey());
        assertEquals (expected.getAtpTypeKey(), actual.getAtpTypeKey());
        assertEquals (expected.getPopulationId(), actual.getPopulationId());
        assertEquals (expected.getRuleId(), actual.getRuleId());
        assertEquals (expected.getStringValue(), actual.getStringValue());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getPriority(), actual.getPriority());
    }

    /*
         A method to set the fields for a Value in the 'test read after update' section.
         This dto is another (second) dto object being created for other tests.
     */
    public void testCrudValue_setDTOFieldsForTestReadAfterUpdate(ValueInfo expected)
    {
        expected.setParameterKey("min_credit");
        expected.setStateKey("stateKey2");
        expected.setTypeKey("typeKey02");
        expected.setAtpTypeKey("atpTypeKey2");
        expected.setPopulationId("populationId2");
        expected.setRuleId("ruleId2");
        expected.setEffectiveDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("1902-12-26"));
        expected.setExpirationDate(DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-10-11"));
        expected.setBooleanValue(true);
        expected.setPriority(1234);
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

        List<ValueInfo> values = testService.getValuesByParameter(dataLoader.getMaxCreditsParameter().getKey(), contextInfo);
        assertEquals(3, values.size());
        assertTrue(containsValue(20L, values));
        assertTrue(containsValue(15L, values));
        assertTrue(containsValue(10L, values));

        values = testService.getValuesByParameter(dataLoader.getMinCreditsForProgramParameter().getKey(), contextInfo);
        assertEquals(1, values.size());
        assertTrue(containsValue(122L, values));

        values = testService.getValuesByParameter("A_BAD_PARAMETER_ID", contextInfo);
        assertEquals(0, values.size());
    }

    /* Method Name: evaluateValuesByParameterAndPerson */
    @Test
    public void test_evaluateValues() throws InvalidParameterException,MissingParameterException,
            OperationFailedException,PermissionDeniedException {
        loadData();
        GesCriteriaInfo criteriaInfo = new GesCriteriaInfo();
        criteriaInfo.setPersonId(PopulationTestStudentEnum.STUDENT1.getPersonId());
        criteriaInfo.setAtpId(dataLoader.getFallAtp().getId());
        List<ValueInfo> values = testService.evaluateValues(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);
        assertEquals(2, values.size());
        assertEquals(15, (long)values.get(0).getNumericValue());
        assertEquals(20, (long)values.get(1).getNumericValue());

        criteriaInfo.setPersonId(null);
        criteriaInfo.setAtpId(null);
        values = testService.evaluateValues(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);
        assertEquals(3, values.size());
        assertEquals(15, (long)values.get(0).getNumericValue());
        assertEquals(10, (long)values.get(1).getNumericValue());
        assertEquals(20, (long)values.get(2).getNumericValue());

        criteriaInfo.setPersonId(PopulationTestStudentEnum.STUDENT8.getPersonId());
        criteriaInfo.setAtpId(dataLoader.getFallAtp().getId());
        values = testService.evaluateValues(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);
        assertEquals(1, values.size());
        assertEquals(20, (long)values.get(0).getNumericValue());

        criteriaInfo.setPersonId("SOME_RANDOM_ID_THAT_DOES_NOT_EXIST");
        criteriaInfo.setAtpId(null);
        values = testService.evaluateValues(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);
        assertEquals(0, values.size());
    }



    /* Method Name: evaluateValuesByParameterAndPersonAndAtpAndOnDate */
    @Test
    public void test_evaluateValuesOnDate() throws InvalidParameterException,MissingParameterException,
            OperationFailedException,PermissionDeniedException {
        loadData();
        GesCriteriaInfo criteriaInfo = new GesCriteriaInfo();
        criteriaInfo.setPersonId(PopulationTestStudentEnum.STUDENT1.getPersonId());
        criteriaInfo.setAtpId(dataLoader.getFallAtp().getId());
        List<ValueInfo> values = testService.evaluateValuesOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2051-01-01"), contextInfo);
        assertEquals(0, values.size());

        values = testService.evaluateValuesOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-11"), contextInfo);
        assertEquals(0, values.size());

        values = testService.evaluateValuesOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), contextInfo);
        assertEquals(2, values.size());
        assertEquals(15, (long)values.get(0).getNumericValue());
        assertEquals(20, (long)values.get(1).getNumericValue());

        values = testService.evaluateValuesOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, new Date(), contextInfo);
        assertEquals(2, values.size());
        assertEquals(15, (long)values.get(0).getNumericValue());
        assertEquals(20, (long)values.get(1).getNumericValue());

        values = testService.evaluateValuesOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), contextInfo);
        assertEquals(2, values.size());
        assertEquals(15, (long)values.get(0).getNumericValue());
        assertEquals(20, (long)values.get(1).getNumericValue());
    }
    /* Method Name: */
    @Test
    public void test_evaluateValue() throws OperationFailedException, MissingParameterException, InvalidParameterException, PermissionDeniedException, DoesNotExistException {
        loadData();
        GesCriteriaInfo criteriaInfo = new GesCriteriaInfo();
        criteriaInfo.setPersonId(PopulationTestStudentEnum.STUDENT1.getPersonId());
        criteriaInfo.setAtpId(dataLoader.getFallAtp().getId());
        ValueInfo value = testService.evaluateValue(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);

        assertEquals(15, (long) value.getNumericValue());


        criteriaInfo.setPersonId(null);
        criteriaInfo.setAtpId(null);
        value = testService.evaluateValue(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);

        assertEquals(15, (long) value.getNumericValue());


        criteriaInfo.setPersonId(PopulationTestStudentEnum.STUDENT8.getPersonId());
        criteriaInfo.setAtpId(dataLoader.getFallAtp().getId());
        value = testService.evaluateValue(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);

        assertEquals(20, (long) value.getNumericValue());

        criteriaInfo.setPersonId("SOME_RANDOM_ID_THAT_DOES_NOT_EXIST");
        criteriaInfo.setAtpId(null);
        try {
            value = testService.evaluateValue(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);
            fail("Exception should have been thrown");
        }catch (DoesNotExistException e){

        }



    }
    /* Method Name: evaluateValuesByParameterAndPersonAndAtpAndOnDate */
    @Test
    public void test_evaluateValueOnDate() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DoesNotExistException {
        loadData();
        GesCriteriaInfo criteriaInfo = new GesCriteriaInfo();
        criteriaInfo.setPersonId(PopulationTestStudentEnum.STUDENT1.getPersonId());
        criteriaInfo.setAtpId(dataLoader.getFallAtp().getId());
        try{
            ValueInfo value = testService.evaluateValueOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2051-01-01"), contextInfo);
            fail("Exception should have been thrown");
        }catch (DoesNotExistException e){

        }
        try{
            ValueInfo value = testService.evaluateValueOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-11"), contextInfo);
            fail("Exception should have been thrown");
        }catch (DoesNotExistException e){

        }


        ValueInfo value = testService.evaluateValueOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), contextInfo);
        assertEquals(15, (long)value.getNumericValue());

        value = testService.evaluateValueOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, new Date(), contextInfo);
        assertEquals(15, (long)value.getNumericValue());

        value = testService.evaluateValueOnDate(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), contextInfo);
        assertEquals(15, (long)value.getNumericValue());
    }
/*    @Test
    public void test_cachePut() throws OperationFailedException, PermissionDeniedException, MissingParameterException, InvalidParameterException, DoesNotExistException {

        loadData();
        Cache cache = cacheDecorator.getCache();


        GesCriteriaInfo criteriaInfo = new GesCriteriaInfo();
        criteriaInfo.setPersonId(PopulationTestStudentEnum.STUDENT1.getPersonId());
        criteriaInfo.setAtpId(dataLoader.getFallAtp().getId());
        ValueInfo value = testService.evaluateValue(dataLoader.getMaxCreditsParameter().getKey(), criteriaInfo, contextInfo);

        assertNotNull(cache);
        assertEquals(15, (long) value.getNumericValue());



    }*/

    private void loadData() throws OperationFailedException {
        try {
            dataLoader.beforeTest();
        } catch (Exception e) {
            throw new OperationFailedException("failed to load data", e);
        }
    }

    private boolean containsValue(Long value, List<ValueInfo> values) {
        for(ValueInfo info : values) {
            if(info.getNumericValue().equals(value))
                return true;
        }
        return false;
    }
}



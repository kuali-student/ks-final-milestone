/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author nwright
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-class2-mock-context.xml"})
public class TestRiceDataDictionaryValidatorImplAgainstAtp {
    public ContextInfo callContext = null;

    @Resource
    protected DataDictionaryValidator validator;


    public TestRiceDataDictionaryValidatorImplAgainstAtp() {
    }

    @Before
    public void setup() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId("principalId.1");
    }

    @After
    public void tearDown() throws Exception {
    }


    private Date parseDate(String str) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        return df.parse(str);
    }

    private AtpInfo getDefaultAtpInfo() throws Exception {
        AtpInfo atp = new AtpInfo();
        atp.setId("org.kuali.test.atp");
        atp.setName("test atp");
        atp.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atp.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atp.setStartDate(parseDate("2010-01-01"));
        atp.setEndDate(parseDate("2010-06-30"));
        return atp;
    }

    /**
     * Test of validate method, of class RiceValidatorImpl.
     * Because there is no constraintProcessors provided for DictionaryValidationService in rice all tests are just
     * to validate the xml file syntax. Any element setting is not validated.
     */
    @Test
    public void testValidate() throws Exception {
        // basic validation test has all required fields
        DataDictionaryValidator.ValidationType validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        AtpInfo atp = this.getDefaultAtpInfo();

        List<ValidationResultInfo> result = validator.validate(validationType, atp, callContext);
        assertTrue(result.isEmpty());

        // check that type key is required
        atp.setTypeKey(null);
        result = validator.validate(validationType, atp, callContext);
        assertEquals(1, result.size());
        assertEquals("typeKey", result.get(0).getElement());
        assertEquals(ValidationResult.ErrorLevel.ERROR, result.get(0).getLevel());
        assertEquals("error.required", result.get(0).getMessage());

        //test a config option
        validationType = DataDictionaryValidator.ValidationType.SKIP_REQUREDNESS_VALIDATIONS;
        result = validator.validate(validationType, atp, callContext);
        assertTrue(result.isEmpty());
    }
}

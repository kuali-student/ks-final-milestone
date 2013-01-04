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

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author nwright
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-class2-mock-context.xml"})
public class TestRiceDataDictionaryValidatorImplAgainstLpr {
    private static final Logger log = Logger.getLogger(TestRiceDataDictionaryValidatorImplAgainstLpr.class);
    public ContextInfo callContext = null;

    @Resource
    protected DataDictionaryValidator validator;

    public TestRiceDataDictionaryValidatorImplAgainstLpr() {
    }

    @Before
    public void setup() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId("principalId.1");
    }

    @After
    public void tearDown() throws Exception {
    }

    private Date parseDate(String str) {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(str, ex);
        }
        return date;
    }

    /**
     * Test of validate method, of class RiceValidatorImpl.
     * Because there is no constraintProcessors provided for DictionaryValidationService in rice all tests are just
     * to validate the xml file syntax. Any element setting is not validated.
     */
    @Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        DataDictionaryValidator.ValidationType validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        LprInfo lpri = new LprInfo();
        lpri.setPersonId("personId.1");
        lpri.setLuiId("luiId.1");
        lpri.setTypeKey(LprServiceConstants.REGISTRANT_TYPE_KEY);
        lpri.setStateKey(LprServiceConstants.APPLIED_STATE_KEY);
        lpri.setEffectiveDate(parseDate("2010-01-01"));

        List<ValidationResultInfo> result = validator.validate(validationType, lpri, callContext);
        assertEquals(0, result.size());

        lpri.setTypeKey(null);
        result = validator.validate(validationType, lpri, callContext);
        assertEquals(1, result.size());
        assertEquals("typeKey", result.get(0).getElement());
        assertEquals(ValidationResult.ErrorLevel.ERROR, result.get(0).getLevel());

        validationType = DataDictionaryValidator.ValidationType.SKIP_REQUREDNESS_VALIDATIONS;
        result = validator.validate(validationType, lpri, callContext);
        assertEquals(0, result.size());
    }
}

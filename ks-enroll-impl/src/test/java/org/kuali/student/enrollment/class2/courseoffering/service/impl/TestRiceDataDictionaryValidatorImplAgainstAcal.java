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
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author nwright
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-class2-mock-context.xml"})
public class TestRiceDataDictionaryValidatorImplAgainstAcal {
    private static final Logger log = Logger.getLogger(TestRiceDataDictionaryValidatorImplAgainstAcal.class);

    public ContextInfo callContext = null;

    @Resource
    protected DataDictionaryValidator validator;


    public TestRiceDataDictionaryValidatorImplAgainstAcal() {
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

    private AcademicCalendarInfo getDefaultAcademicCalendarInfo() {
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setId("org.kuali.test.acal");
        acal.setName("test acal");
        acal.setTypeKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
        acal.setStateKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_DRAFT_STATE_KEY);
        acal.setStartDate(parseDate("2010-01-01"));
        acal.setEndDate(parseDate("2010-06-30"));
        return acal;
    }

    /**
     * Test of validate method, of class RiceValidatorImpl.
     */
    @Test
    public void testValidate1() throws Exception {
        System.out.println("basic validation test has all required fields");

        callContext = new ContextInfo();
        callContext.setPrincipalId("principalId");

        DataDictionaryValidator.ValidationType validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        AcademicCalendarInfo acal = this.getDefaultAcademicCalendarInfo();

        List<ValidationResultInfo> result = validator.validate(validationType, acal, callContext);
        assertEquals(0, result.size());
    }

    /**
     * Test of validate method, of class RiceValidatorImpl.
     */
    @Test
    public void testValidate2() throws Exception {
        System.out.println("check that type key is required");

        DataDictionaryValidator.ValidationType validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        AcademicCalendarInfo acal = this.getDefaultAcademicCalendarInfo();
        acal.setTypeKey(null);

        List<ValidationResultInfo> result = validator.validate(validationType, acal, callContext);
        assertEquals(1, result.size());
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
            assertEquals("typeKey", vri.getElement());
        }

        System.out.println("check that empty string is same as null");
        acal.setTypeKey("");
        result = validator.validate(validationType, acal, callContext);
        assertEquals(1, result.size());
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
            assertEquals("typeKey", vri.getElement());
        }

        System.out.println("check that a single blank string is same as null");
        acal.setTypeKey(" ");
        result = validator.validate(validationType, acal, callContext);
        assertEquals(1, result.size());
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
            assertEquals("typeKey", vri.getElement());
        }

        System.out.println("check that a lots of blanks is same as null");
        acal.setTypeKey("         ");
        result = validator.validate(validationType, acal, callContext);
        assertEquals(1, result.size());
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
            assertEquals("typeKey", vri.getElement());
        }

        System.out.println("check that tabs and newlines count as all whitespace and is the same as null");
        acal.setTypeKey("\n\t\r");
        result = validator.validate(validationType, acal, callContext);
        assertEquals(1, result.size());
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
            assertEquals("typeKey", vri.getElement());
        }

        System.out.println("check that we can skip the requiredness checks");
        validationType = DataDictionaryValidator.ValidationType.SKIP_REQUREDNESS_VALIDATIONS;
        result = validator.validate(validationType, acal, callContext);
        assertEquals(0, result.size());
    }

}

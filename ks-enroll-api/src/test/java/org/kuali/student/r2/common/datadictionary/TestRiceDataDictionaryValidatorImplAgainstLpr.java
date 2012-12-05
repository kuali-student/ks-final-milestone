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
package org.kuali.student.r2.common.datadictionary;

import org.junit.Test;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
public class TestRiceDataDictionaryValidatorImplAgainstLpr {

    public TestRiceDataDictionaryValidatorImplAgainstLpr() {
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

    private ContextInfo getContext1() {
        return ContextInfo.getInstance("principalId.1", "en", "us");
    }
    private DataDictionaryValidator validator;

    public DataDictionaryValidator getValidator()  {
        if (validator == null) {
            ApplicationContext appContext =
                    new ClassPathXmlApplicationContext(new String[]{"classpath:testContext.xml"});
            validator = (DataDictionaryValidator) appContext.getBean("testValidator");
        }
        return validator;
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
        ContextInfo context = getContext1();

        DataDictionaryValidator intstance = this.getValidator();

        List<ValidationResultInfo> result = null;

        result = intstance.validate(validationType, lpri, context);
        assertEquals(0, result.size());

        lpri.setTypeKey(null);
        result = intstance.validate(validationType, lpri, context);
        for (ValidationResult vri : result) {
            System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(0, result.size());



        lpri.setTypeKey(null);
        validationType = DataDictionaryValidator.ValidationType.SKIP_REQUREDNESS_VALIDATIONS;
        result = intstance.validate(validationType, lpri, context);
        for (ValidationResult vri : result) {
            System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(0, result.size());

        lpri.setTypeKey(" this has \n an embedded return");
        result = intstance.validate(validationType, lpri, context);
        for (ValidationResult vri : result) {
            System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(0, result.size());
    }
}

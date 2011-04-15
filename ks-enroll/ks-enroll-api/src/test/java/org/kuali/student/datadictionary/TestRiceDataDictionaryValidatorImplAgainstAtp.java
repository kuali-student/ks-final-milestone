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
package org.kuali.student.datadictionary;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.core.config.Config;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.JAXBConfigImpl;
import org.kuali.rice.kns.util.spring.ClassPathXmlApplicationContext;
import org.kuali.student.common.infc.ValidationResult;
import org.kuali.student.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author nwright
 */
public class TestRiceDataDictionaryValidatorImplAgainstAtp {

    public TestRiceDataDictionaryValidatorImplAgainstAtp() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    protected Config getTestHarnessConfig() {
        Config config = new JAXBConfigImpl(getConfigLocations(), System.getProperties());
        try {
            config.parseConfig();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return config;
    }

    /**
     * Subclasses may override this method to customize the location(s) of the Rice configuration.
     * By default it is: classpath:META-INF/" + getModuleName().toLowerCase() + "-test-config.xml"
     * @return List of config locations to add to this tests config location.
     */
    protected List<String> getConfigLocations() {
        List<String> configLocations = new ArrayList<String>();
//        configLocations.add(getRiceMasterDefaultConfigFile());
        return configLocations;
    }


    @Before
    public void setUp() {
        Config config = getTestHarnessConfig();
        ConfigContext.init(config);
    }

    @After
    public void tearDown() {
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
        return new ContextInfo.Builder().principalId("principalId.1").localeLanguage("en").localeRegion("us").build();
    }
    private DataDictionaryValidatorInfc validator;

    public DataDictionaryValidatorInfc getValidator()  {
        if (validator == null) {
            ApplicationContext appContext =
                    new ClassPathXmlApplicationContext(new String[]{"classpath:testContext.xml"});
            this.validator = (DataDictionaryValidatorInfc) appContext.getBean("validatorToTest");
        }
        return validator;
    }

    /**
     * Test of validate method, of class RiceValidatorImpl.
     */
    @Test
    public void testValidate() throws Exception {
        System.out.println("validate ATP");
        DataDictionaryValidatorInfc.ValidationType validationType = DataDictionaryValidatorInfc.ValidationType.FULL_VALIDATION;
        AtpInfo.Builder bldr = new AtpInfo.Builder();
        bldr.setKey ("org.kuali.test.atp");
        bldr.setName("test atp");
        bldr.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        bldr.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        bldr.setStartDate(parseDate("2010-01-01"));
        bldr.setEndDate(parseDate ("2010-06-30"));
        Object info = bldr.build();
        ContextInfo context = getContext1();

        DataDictionaryValidatorInfc intstance = this.getValidator();

        List<ValidationResultInfo> result = null;

        result = intstance.validate(validationType, info, context);
        assertEquals(0, result.size());

        bldr.setTypeKey(null);
        info = bldr.build();
        result = intstance.validate(validationType, info, context);
        for (ValidationResult vri : result) {
            System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());



        bldr.setTypeKey(null);
        info = bldr.build();
        validationType = DataDictionaryValidatorInfc.ValidationType.SKIP_REQUREDNESS_VALIDATIONS;
        result = intstance.validate(validationType, info, context);
        for (ValidationResult vri : result) {
            System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(0, result.size());

        bldr.setTypeKey(" this has \n an embedded return");
        info = bldr.build();
        result = intstance.validate(validationType, info, context);
        for (ValidationResult vri : result) {
            System.out.println (vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
//        assertEquals (0, 0);
    }
}

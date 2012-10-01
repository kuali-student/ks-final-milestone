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
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author nwright
 */
@Ignore
public class TestRiceDataDictionaryValidatorImplAgainstLui {

    public TestRiceDataDictionaryValidatorImplAgainstLui() {
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
        return ContextInfo.getInstance("principalId.1", "en", "us");
    }
    private static DataDictionaryValidator validator;

    public DataDictionaryValidator getValidator() {
        if (validator == null) {
            ApplicationContext appContext =
                    new ClassPathXmlApplicationContext(new String[]{"classpath:testContext.xml"});
            this.validator = (DataDictionaryValidator) appContext.getBean("testValidator");
        }
        return validator;
    }
        private static DataDictionaryValidator validator2;
    public DataDictionaryValidator getValidator2() {
        if (validator2 == null) {
            ApplicationContext appContext =
                    new ClassPathXmlApplicationContext(new String[]{"classpath:test2Context.xml"});
            this.validator2 = (DataDictionaryValidator) appContext.getBean("testValidator");
        }
        return validator2;
    }
    private LuiInfo getDefaultLuiInfo() {
        LuiInfo lui = new LuiInfo();
        lui.setName("test lui");
        lui.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        lui.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        lui.setEffectiveDate(this.parseDate("2011-01-01"));
        return lui;
    }

    /**
     * Test of validate method, of class RiceValidatorImpl.
     */
//    @Test
    public void testValidate1() throws Exception {
        System.out.println("tests what I hoped to do -- define subobject field as required but object itself is missing so it should be checked");
        DataDictionaryValidator.ValidationType validationType = null;
        LuiInfo lui = null;
        ContextInfo context = null;

        DataDictionaryValidator intstance = this.getValidator();
        List<ValidationResultInfo> result = null;

        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        lui = this.getDefaultLuiInfo();
        context = getContext1();
        result = intstance.validate(validationType, lui, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }        
        assertEquals(0, result.size());
    }

        /**
     * Test of validate method, of class RiceValidatorImpl.
     */
    @Test
    public void testValidate2() throws Exception {
        System.out.println("test using the ComplexAttributeDefinition to hold the definition");
        DataDictionaryValidator.ValidationType validationType = null;
        LuiInfo lui = null;
        ContextInfo context = null;

        DataDictionaryValidator intstance = this.getValidator2();
        List<ValidationResultInfo> result = null;

        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        lui = this.getDefaultLuiInfo();
        context = getContext1();
        result = intstance.validate(validationType, lui, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }        
        assertEquals(0, result.size());
    }
}

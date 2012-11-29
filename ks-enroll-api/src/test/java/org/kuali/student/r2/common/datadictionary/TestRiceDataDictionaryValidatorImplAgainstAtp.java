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

import org.junit.*;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        return ContextInfo.getInstance("principalId.1", "en", "us");
    }
    private DataDictionaryValidator validator;

    public DataDictionaryValidator getValidator() {
        if (validator == null) {
            ApplicationContext appContext =
                    new ClassPathXmlApplicationContext(new String[]{"classpath:testContext.xml"});
            this.validator = (DataDictionaryValidator) appContext.getBean("validator");
        }
        return validator;
    }

    private AtpInfo getDefaultAtpInfo() {
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
     */
    @Test
    @Ignore // TODO: RICE-M9 UPGRADE
    public void testValidate() throws Exception {
        System.out.println("validate ATP");
        DataDictionaryValidator.ValidationType validationType = null;
        AtpInfo atp = null;
        ContextInfo context = null;


        DataDictionaryValidator intstance = this.getValidator();
        List<ValidationResultInfo> result = null;

        // basic validation test has all required fields
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        result = intstance.validate(validationType, atp, context);
        assertEquals(0, result.size());

        // check that type key is required
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setTypeKey(null);
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("typeKey", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.required", result.get(0).getMessage());

        // check that empty string is same as null
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setTypeKey("");
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("typeKey", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.required", result.get(0).getMessage());

        // check that a single blank string is same as null
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setTypeKey(" ");
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("typeKey", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.required", result.get(0).getMessage());

        // check that a lots of blanks is same as null
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setTypeKey("         ");
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("typeKey", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.required", result.get(0).getMessage());

        // check that tabs and newlines count as all whitespace and is the same as null
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setTypeKey("   \n\t\r      ");
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("typeKey", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.required", result.get(0).getMessage());


        // check that we can skip the requiredness checks 
        validationType = DataDictionaryValidator.ValidationType.SKIP_REQUREDNESS_VALIDATIONS;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setTypeKey(null);
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(0, result.size());

        // check that valid chars catches that the name cannot have an embedded new line
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setName("this has \n an embedded return");
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.invalidFormat", result.get(0).getMessage());

        // check that name cannot exceed 255
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setName(
                "This has is a really long name in fact it is so long that it goes on and on and on and on sometimes I think it will go on forever"
                + " but not really because the limit should be at 255 becaue that is the default in the dictionary and now I think I should stop typing");
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.outOfRange", result.get(0).getMessage());

        // check that the name does not get trimmed before comparing it to not exceed 255
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setName(
                "test atp                                                                                                                            "
                + "                                                                                                                                    ");
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.outOfRange", result.get(0).getMessage());

        // check reference to a complex sub-structure (descr)
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setDescr(new RichTextHelper().fromPlain("test atp description\n that is ok"));
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(0, result.size());

        // check reference to a complex sub-structure (descr) with bad data in it
        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        atp = this.getDefaultAtpInfo();
        context = getContext1();
        atp.setDescr(new RichTextHelper().fromPlain("test atp description \nwith an invalid character tilde ~ in it"));
        result = intstance.validate(validationType, atp, context);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        // 2 because 1 for plain and 1 for formatted
        assertEquals(2, result.size());
        assertEquals("descr.plain", result.get(0).getElement());
        assertEquals(new Integer(2), result.get(0).getLevel());
        assertEquals("error.invalidFormat", result.get(0).getMessage());
        assertEquals("descr.formatted", result.get(1).getElement());
        assertEquals(new Integer(2), result.get(1).getLevel());
        assertEquals("error.invalidFormat", result.get(1).getMessage());

        // check start date required if official
//        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
//        atp = this.getDefaultAtpInfo();
//        atp.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
//        atp.setStartDate(null);
//        context = getContext1();
//        result = intstance.validate(validationType, atp, context);
//        for (ValidationResult vri : result) {
//            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
//        }
//        assertEquals(1, result.size());
//        assertEquals("startDate", result.get(0).getElement());
//        assertEquals(new Integer(2), result.get(0).getLevel());
//        assertEquals("error.required", result.get(0).getMessage());
    }
}

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
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.rmi.runtime.NewThreadAction;

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
public class TestRiceDataDictionaryValidatorImplAgainstCourseOffering {

    private static final Logger log = Logger.getLogger(TestRiceDataDictionaryValidatorImplAgainstCourseOffering.class);

    private final boolean testAwareDataLoader;
    public ContextInfo callContext = null;

    @Resource
    protected DataDictionaryValidator validator;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;


    public TestRiceDataDictionaryValidatorImplAgainstCourseOffering() {
        this(true);
    }

    protected TestRiceDataDictionaryValidatorImplAgainstCourseOffering(boolean testAwareDataLoader) {
        this.testAwareDataLoader = testAwareDataLoader;
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
    public void setup() throws Exception {
        Config config = getTestHarnessConfig();
        ConfigContext.init(config);

        callContext = new ContextInfo();
        callContext.setPrincipalId("principalId.1");

        if (testAwareDataLoader || !dataLoader.isInitialized())
            dataLoader.beforeTest();
    }

    @After
    public void tearDown() throws Exception {
        if (testAwareDataLoader)
            dataLoader.afterTest();
    }

    private CourseOfferingInfo getDefaultCourseOfferingInfo() {
        CourseOfferingInfo co = new CourseOfferingInfo();
        return co;
    }

    /**
     * Test of validate method, of class RiceValidatorImpl.
     */
    @Test
    public void testValidate1() throws Exception {
        System.out.println("tests basic validation");
        DataDictionaryValidator.ValidationType validationType = null;
        CourseOfferingInfo co = new CourseOfferingInfo();
        co.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        co.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        co.setCourseId("fake-course-id");
        co.setTermId("fake-term-key");
        co.setGradingOptionId("fake-grading-option-id");
        co.setCreditOptionId("fake-credit-option-id");

        validationType = DataDictionaryValidator.ValidationType.FULL_VALIDATION;
        List<ValidationResultInfo> result = validator.validate(validationType, co, callContext);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(0, result.size());

        co.setGradingOptionId(null);
        co.setCreditOptionId(null);
        result = validator.validate(validationType, co, callContext);
        for (ValidationResult vri : result) {
            System.out.println(vri.getElement() + " " + vri.getLevel() + " " + vri.getMessage());
        }
        assertEquals(2, result.size());
    }

}

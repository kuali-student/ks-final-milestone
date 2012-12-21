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
package org.kuali.student.r2.common.datadictionary.util;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.resourceloader.ResourceLoader;
import org.kuali.rice.core.api.resourceloader.ServiceLocator;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.core.framework.resourceloader.BaseResourceLoader;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.kuali.rice.krad.datadictionary.validation.AttributeValueReader;
import org.kuali.rice.krad.datadictionary.validation.ErrorLevel;
import org.kuali.rice.krad.datadictionary.validation.constraint.ValidCharactersConstraint;
import org.kuali.rice.krad.datadictionary.validation.processor.ValidCharactersConstraintProcessor;
import org.kuali.rice.krad.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.krad.datadictionary.validation.result.ProcessorResult;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.KualiModuleService;
import org.kuali.rice.krad.service.impl.KualiModuleServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.namespace.QName;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestBaseValidCharsDictionary {

    @Before
    public void setUp(){
        Config config = new JAXBConfigImpl();
       config.putProperty(CoreConstants.Config.APPLICATION_ID, "test");
       ConfigContext.init(config);

       ResourceLoader resourceLoader =
               new BaseResourceLoader(
                       new QName("test", RiceConstants.DEFAULT_ROOT_RESOURCE_LOADER_NAME), new SimpleSpringResourceLoader());

       try {
           GlobalResourceLoader.stop();
           GlobalResourceLoader.addResourceLoader(resourceLoader);
           GlobalResourceLoader.start();
       } catch (Exception e) {
           throw new RuntimeException("Error initializing GRL", e);
       }
    }

    private String test(ValidCharactersConstraintProcessor vccp, ValidCharactersConstraint vcc, String value) {
        DictionaryValidationResult dvr = new DictionaryValidationResult();
        AttributeValueReader avr = new MockAttributeValueReader();
        ProcessorResult result = vccp.process(dvr, value, vcc, avr);
        for (ConstraintValidationResult cvr : result.getConstraintValidationResults()) {
            if (! cvr.getStatus().equals(ErrorLevel.OK)) {
                return "error";
            }
        }
        return null;
    }

    @Test
    public void testValidCharsConstraints() {

        System.out.println("testing base dictionary");
        String contextFile = "ks-base-dictionary-validchars.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:"
                + contextFile);
        Map<String, ValidCharactersConstraint> vccs = (Map<String, ValidCharactersConstraint>) ac.getBeansOfType(
                ValidCharactersConstraint.class);
        for (String id : vccs.keySet()) {
            ValidCharactersConstraint vcc = vccs.get(id);
            System.out.println("valid chars constraint: " + id + " "
                    + vcc.getMessageKey() + " " + vcc.getValue());
        }

        String id = null;
        ValidCharactersConstraint vc = null;
        ValidCharactersConstraintProcessor vccp = new ValidCharactersConstraintProcessor();

        id = "AlphanumericHyphenPeriod";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "A"));
        assertNull(test(vccp, vc, "."));
        assertNull(test(vccp, vc, "-"));
        assertNotNull(test(vccp, vc, " "));
        assertNotNull(test(vccp, vc, ","));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "Digits";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "1"));
        assertNotNull(test(vccp, vc, "A"));
        assertNotNull(test(vccp, vc, "."));
        assertNotNull(test(vccp, vc, "-"));
        assertNotNull(test(vccp, vc, " "));
        assertNotNull(test(vccp, vc, ","));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "Numeric";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "0"));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "20"));
        assertNull(test(vccp, vc, "-20"));
        assertNull(test(vccp, vc, "-12345620"));
        assertNull(test(vccp, vc, "20.1"));
        assertNull(test(vccp, vc, "20.0"));
        assertNull(test(vccp, vc, "20.01"));
        assertNull(test(vccp, vc, "20.00"));
        assertNull(test(vccp, vc, "120.00"));
        assertNull(test(vccp, vc, "1120.00"));
        assertNull(test(vccp, vc, "11120.00"));
        assertNull(test(vccp, vc, "111120.00"));
        assertNull(test(vccp, vc, "1111120.00"));
        assertNull(test(vccp, vc, "11111120.00"));
        assertNotNull(test(vccp, vc, "020"));
        assertNotNull(test(vccp, vc, "-020"));
        assertNotNull(test(vccp, vc, "-020.1"));
        assertNotNull(test(vccp, vc, "20.010"));
        assertNotNull(test(vccp, vc, "20.011"));
        assertNotNull(test(vccp, vc, "A"));
        assertNotNull(test(vccp, vc, "."));
        assertNotNull(test(vccp, vc, "-"));
        assertNotNull(test(vccp, vc, " "));
        assertNotNull(test(vccp, vc, ","));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "Numeric.range";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "0"));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "20"));
        assertNull(test(vccp, vc, "-20"));
        assertNull(test(vccp, vc, "-12345620"));
        assertNull(test(vccp, vc, "20.1"));
        assertNull(test(vccp, vc, "20.0"));
        assertNull(test(vccp, vc, "20.01"));
        assertNull(test(vccp, vc, "20.00"));
        assertNull(test(vccp, vc, "120.00"));
        assertNull(test(vccp, vc, "1120.00"));
        assertNull(test(vccp, vc, "11120.00"));
        assertNull(test(vccp, vc, "111120.00"));
        assertNull(test(vccp, vc, "1111120.00"));
        assertNull(test(vccp, vc, "11111120.00"));
        assertNull(test(vccp, vc, "1-2"));
        assertNull(test(vccp, vc, "1.1-2.2"));
        assertNull(test(vccp, vc, "1 - 2"));
        assertNull(test(vccp, vc, "1--2"));
        assertNull(test(vccp, vc, "1 - -2"));
        assertNull(test(vccp, vc, "-1 - -2"));
        assertNotNull(test(vccp, vc, "1 -2"));
        assertNotNull(test(vccp, vc, "1 - 2 - 3"));
        assertNotNull(test(vccp, vc, "1- 2"));
        assertNotNull(test(vccp, vc, "020"));
        assertNotNull(test(vccp, vc, "-020"));
        assertNotNull(test(vccp, vc, "-020.1"));
        assertNotNull(test(vccp, vc, "20.010"));
        assertNotNull(test(vccp, vc, "20.011"));
        assertNotNull(test(vccp, vc, "A"));
        assertNotNull(test(vccp, vc, "."));
        assertNotNull(test(vccp, vc, "-"));
        assertNotNull(test(vccp, vc, " "));
        assertNotNull(test(vccp, vc, ","));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "Positive.numeric.range";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "0"));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "20"));
        assertNotNull(test(vccp, vc, "-20"));
        assertNotNull(test(vccp, vc, "-12345620"));
        assertNull(test(vccp, vc, "20.1"));
        assertNull(test(vccp, vc, "20.0"));
        assertNull(test(vccp, vc, "20.01"));
        assertNull(test(vccp, vc, "20.00"));
        assertNull(test(vccp, vc, "120.00"));
        assertNull(test(vccp, vc, "1120.00"));
        assertNull(test(vccp, vc, "11120.00"));
        assertNull(test(vccp, vc, "111120.00"));
        assertNull(test(vccp, vc, "1111120.00"));
        assertNull(test(vccp, vc, "11111120.00"));
        assertNull(test(vccp, vc, "1-2"));
        assertNull(test(vccp, vc, "1.1-2.2"));
        assertNull(test(vccp, vc, "1 - 2"));
        assertNotNull(test(vccp, vc, "1--2"));
        assertNotNull(test(vccp, vc, "1 - -2"));
        assertNotNull(test(vccp, vc, "-1 - -2"));
        assertNotNull(test(vccp, vc, "1 -2"));
        assertNotNull(test(vccp, vc, "1 - 2 - 3"));
        assertNotNull(test(vccp, vc, "1- 2"));
        assertNotNull(test(vccp, vc, "020"));
        assertNotNull(test(vccp, vc, "-020"));
        assertNotNull(test(vccp, vc, "-020.1"));
        assertNotNull(test(vccp, vc, "20.010"));
        assertNotNull(test(vccp, vc, "20.011"));
        assertNotNull(test(vccp, vc, "A"));
        assertNotNull(test(vccp, vc, "."));
        assertNotNull(test(vccp, vc, "-"));
        assertNotNull(test(vccp, vc, " "));
        assertNotNull(test(vccp, vc, ","));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "Gpa4.0";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "0.0"));
        assertNull(test(vccp, vc, "4.0"));
        assertNull (test(vccp, vc, "4.1"));
        assertNotNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "1.0"));
        assertNull(test(vccp, vc, "0.1"));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "Positive.integer";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "22"));
        assertNull(test(vccp, vc, "33"));
        assertNotNull(test(vccp, vc, "0"));
        assertNotNull(test(vccp, vc, "1.0"));
        assertNotNull(test(vccp, vc, "-1"));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "RelationalOperator";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "="));
        assertNull(test(vccp, vc, ">"));
        assertNull(test(vccp, vc, "<"));
        assertNull(test(vccp, vc, ">="));
        assertNull(test(vccp, vc, "<="));
        assertNull(test(vccp, vc, "<>"));
        assertNotNull(test(vccp, vc, "!="));
        assertNotNull(test(vccp, vc, "=>"));
        assertNotNull(test(vccp, vc, "=<"));
        assertNotNull(test(vccp, vc, "0"));
        assertNotNull(test(vccp, vc, "1.0"));
        assertNotNull(test(vccp, vc, "-1"));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "Alpha";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "a"));
        assertNull(test(vccp, vc, "A"));
        assertNull(test(vccp, vc, "Z"));
        assertNull(test(vccp, vc, "zzzzz"));
        assertNull(test(vccp, vc,
                "abcdefghijklmnopqrstuvwxyz"));
        assertNotNull(test(vccp, vc, "1"));
        assertNotNull(test(vccp, vc, "0"));
        assertNotNull(test(vccp, vc, "X\n"));
        assertNotNull(test(vccp, vc, "X\r"));
        assertNotNull(test(vccp, vc, "X\t"));

        id = "UpperCase";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "AB"));
        assertNull(test(vccp, vc, "A"));
        assertNull(test(vccp, vc,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertNull(test(vccp, vc, "-"));
        assertNull(test(vccp, vc, "."));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "0"));
        assertNotNull(test(vccp, vc, "a"));
        assertNotNull(test(vccp, vc, "X\n"));
        assertNotNull(test(vccp, vc, "X\r"));
        assertNotNull(test(vccp, vc, "X\t"));

        id = "UpperAlpha";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "AB"));
        assertNull(test(vccp, vc, "A"));
        assertNull(test(vccp, vc, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertNotNull(test(vccp, vc, "a"));
        assertNotNull(test(vccp, vc, "1"));
        assertNotNull(test(vccp, vc, "0"));
        assertNotNull(test(vccp, vc, "X\n"));
        assertNotNull(test(vccp, vc, "X\r"));
        assertNotNull(test(vccp, vc, "X\t"));

        id = "LineText";
        //AnyCharacter constraint allows only Visible characters (i.e. anything except spaces, control characters, etc.)
        //http://www.regular-expressions.info/posixbrackets.html - [\x21-\x7E]
        //http://www.asciitable.com/
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "a"));
        assertNull(test(vccp, vc, "A"));
        assertNull(test(vccp, vc, "Z"));
        assertNull(test(vccp, vc, "Z"));
        assertNull(test(vccp, vc, "!"));
        assertNull(test(vccp, vc, "@"));
        assertNull(test(vccp, vc, "#"));
        assertNull(test(vccp, vc, "$"));
        assertNull(test(vccp, vc, "%"));
        assertNull(test(vccp, vc, "&"));
        assertNull(test(vccp, vc, "*"));
        assertNull(test(vccp, vc, "("));
        assertNull(test(vccp, vc, ")"));
        assertNull(test(vccp, vc, "_"));
        assertNull(test(vccp, vc, "+"));
        assertNull(test(vccp, vc, "-"));
        assertNull(test(vccp, vc, "="));
        assertNull(test(vccp, vc, "{"));
        assertNull(test(vccp, vc, "}"));
        assertNull(test(vccp, vc, "["));
        assertNull(test(vccp, vc, "]"));
        assertNull(test(vccp, vc, "|"));
        assertNull(test(vccp, vc, "\\"));
        assertNull(test(vccp, vc, ","));
        assertNull(test(vccp, vc, "."));
        assertNull(test(vccp, vc, "/"));
        assertNull(test(vccp, vc, "<"));
        assertNull(test(vccp, vc, ">"));
        assertNull(test(vccp, vc, "?"));
        assertNull(test(vccp, vc, "~"));
        assertNull(test(vccp, vc, "`"));
        assertNull(test(vccp, vc, "zzzzz"));
        assertNull(test(vccp, vc,"abcdefghijklmnopqrstuvwxyz"));
        assertNull(test(vccp, vc,"ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "0"));
        assertNull(test(vccp, vc, "X\n"));
        assertNull(test(vccp, vc, "X\r"));
        assertNull(test(vccp, vc, "X\t"));

        id = "MultiLineText";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "a"));
        assertNull(test(vccp, vc, "A"));
        assertNull(test(vccp, vc, "Z"));
        assertNull(test(vccp, vc, "Z"));
        assertNull(test(vccp, vc, "!"));
        assertNull(test(vccp, vc, "@"));
        assertNull(test(vccp, vc, "#"));
        assertNull(test(vccp, vc, "$"));
        assertNull(test(vccp, vc, "%"));
        assertNull(test(vccp, vc, "&"));
        assertNull(test(vccp, vc, "*"));
        assertNull(test(vccp, vc, "("));
        assertNull(test(vccp, vc, ")"));
        assertNull(test(vccp, vc, "_"));
        assertNull(test(vccp, vc, "+"));
        assertNull(test(vccp, vc, "-"));
        assertNull(test(vccp, vc, "="));
        assertNull(test(vccp, vc, "{"));
        assertNull(test(vccp, vc, "}"));
        assertNull(test(vccp, vc, "["));
        assertNull(test(vccp, vc, "]"));
        assertNull(test(vccp, vc, "|"));
        assertNull(test(vccp, vc, "\\"));
        assertNull(test(vccp, vc, ","));
        assertNull(test(vccp, vc, "."));
        assertNull(test(vccp, vc, "/"));
        assertNull(test(vccp, vc, "<"));
        assertNull(test(vccp, vc, ">"));
        assertNull(test(vccp, vc, "?"));
        assertNull(test(vccp, vc, "~"));
        assertNull(test(vccp, vc, "`"));
        assertNull(test(vccp, vc, "zzzzz"));
        assertNull(test(vccp, vc,"abcdefghijklmnopqrstuvwxyz"));
        assertNull(test(vccp, vc,"ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "0"));
        assertNull(test(vccp, vc, "X\n"));
        assertNull(test(vccp, vc, "X\r"));
        assertNull(test(vccp, vc, "X\t"));

        // If you get this error -- don't just change the number
        // also add a unit test for new valid chars definition that you must have added into the base dictionary!
        assertEquals(14, vccs.size());
    }
}

/**
 * Copied from ks-core-test.. As this test class is in ks-enroll-api and it doesnt make sense to make ks-enroll-api depends on
 * ks-core-test, just copied this class from there to make it simple. Also, in ks-enroll-api, this is the only class
 * uses the resource loader.
 */
class SimpleSpringResourceLoader implements ApplicationContextAware, ServiceLocator {

    private static ConfigurationService configurationService = new ConfigurationService() {
        @Override public String getPropertyValueAsString(String key) { return "{0} message"; }
        @Override public boolean getPropertyValueAsBoolean(String key) { return false; }
        @Override public Map<String, String> getAllProperties() { return null; }
    };

    private static KualiModuleService kualiModuleService = new KualiModuleServiceImpl();

    private ApplicationContext applicationContext;

    public Object getService(QName qname) {
        if (qname == null || StringUtils.isEmpty(qname.toString())) {
            throw new IllegalArgumentException("The service name must be non-null.");
        }

        String localServiceName = qname.toString();

        if (KRADServiceLocator.KUALI_CONFIGURATION_SERVICE.equals(localServiceName)) {
            return configurationService;
        } else if (KRADServiceLocatorWeb.KUALI_MODULE_SERVICE.equals(localServiceName)) {
            return kualiModuleService;
        } else {
            return applicationContext.getBean(localServiceName);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public String getContents(String indent, boolean servicePerLine) {
        return null;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public boolean isStarted() {
        return false;
    }
}


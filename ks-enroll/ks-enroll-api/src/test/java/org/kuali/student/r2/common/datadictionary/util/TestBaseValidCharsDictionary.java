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

import java.util.Map;
import org.junit.Test;
import org.kuali.rice.kns.datadictionary.validation.AttributeValueReader;
import org.kuali.rice.kns.datadictionary.validation.ErrorLevel;
import org.kuali.rice.kns.datadictionary.validation.constraint.ValidCharactersConstraint;
import org.kuali.rice.kns.datadictionary.validation.processor.ValidCharactersConstraintProcessor;
import org.kuali.rice.kns.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.kns.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.kns.datadictionary.validation.result.ProcessorResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestBaseValidCharsDictionary {

    private String test(ValidCharactersConstraintProcessor vccp, ValidCharactersConstraint vcc, String value) {
        DictionaryValidationResult dvr = new DictionaryValidationResult();
        AttributeValueReader avr = new MockAttributeValueReader();
        ProcessorResult result = vccp.process(dvr, value, vcc, avr);
//        System.out.println("Number of results=" + result.getConstraintValidationResults().size());
        for (ConstraintValidationResult cvr : result.getConstraintValidationResults()) {
//            System.out.println("cvr.status =" + cvr.getStatus());
            if (! cvr.getStatus().equals(ErrorLevel.OK)) {
//                System.out.println("cvr.errorKey =" + cvr.getErrorKey());
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
                    + vcc.getLabelKey() + " " + vcc.getValue());
        }

        String id = null;
        ValidCharactersConstraint vc = null;
        ValidCharactersConstraintProcessor vccp = new ValidCharactersConstraintProcessor();

        id = "alphanumericHyphenPeriod";
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

        id = "digits";
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

        id = "numeric";
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

        id = "numeric.range";
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

        id = "positive.numeric.range";
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

        id = "gpa4.0";
        vc = vccs.get(id);
        assertNotNull(vc);
        assertNull(test(vccp, vc, "0.0"));
        assertNull(test(vccp, vc, "4.0"));
        assertNull(test(vccp, vc, "1.0"));
        // TODO: fix reg ex so it allows 4.0 but excludes 4.1, 4.2 etc...
//  assertNotNull (v.processValidCharConstraint ("test", vc, null, "4.1"));
        assertNotNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "1.0"));
        assertNotNull(test(vccp, vc, "\n"));
        assertNotNull(test(vccp, vc, "\r"));
        assertNotNull(test(vccp, vc, "\t"));

        id = "positive.integer";
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

        id = "relationalOperator";
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

        id = "alpha";
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

        id = "upperCase";
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

        id = "upperAlpha";
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

        id = "lineText";
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
        //TODO: maybe allow these special characters
        assertNotNull(test(vccp, vc, "^"));
        assertNull(test(vccp, vc, "&"));
        assertNotNull(test(vccp, vc, "*"));
        assertNull(test(vccp, vc, "("));
        assertNull(test(vccp, vc, ")"));
        assertNull(test(vccp, vc, "_"));
        assertNull(test(vccp, vc, "+"));
        assertNull(test(vccp, vc, "-"));
        assertNull(test(vccp, vc, "="));
        assertNotNull(test(vccp, vc, "{"));
        assertNotNull(test(vccp, vc, "}"));
        assertNull(test(vccp, vc, "["));
        assertNull(test(vccp, vc, "]"));
        assertNotNull(test(vccp, vc, "|"));
        assertNull(test(vccp, vc, "\\"));
        assertNull(test(vccp, vc, ","));
        assertNull(test(vccp, vc, "."));
        assertNull(test(vccp, vc, "/"));
        assertNull(test(vccp, vc, "<"));
        assertNull(test(vccp, vc, ">"));
        assertNull(test(vccp, vc, "?"));
        assertNotNull(test(vccp, vc, "~"));
        assertNotNull(test(vccp, vc, "`"));
        assertNull(test(vccp, vc, "zzzzz"));
        assertNull(test(vccp, vc,"abcdefghijklmnopqrstuvwxyz"));
        assertNull(test(vccp, vc,"ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "0"));
        assertNotNull(test(vccp, vc, "X\n"));
        assertNotNull(test(vccp, vc, "X\r"));
        assertNull(test(vccp, vc, "X\t"));

        id = "multiLineText";
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
        //TODO: maybe allow these special characters
        assertNotNull(test(vccp, vc, "^"));
        assertNull(test(vccp, vc, "&"));
        assertNotNull(test(vccp, vc, "*"));
        assertNull(test(vccp, vc, "("));
        assertNull(test(vccp, vc, ")"));
        assertNull(test(vccp, vc, "_"));
        assertNull(test(vccp, vc, "+"));
        assertNull(test(vccp, vc, "-"));
        assertNull(test(vccp, vc, "="));
        assertNotNull(test(vccp, vc, "{"));
        assertNotNull(test(vccp, vc, "}"));
        assertNull(test(vccp, vc, "["));
        assertNull(test(vccp, vc, "]"));
        assertNotNull(test(vccp, vc, "|"));
        assertNull(test(vccp, vc, "\\"));
        assertNull(test(vccp, vc, ","));
        assertNull(test(vccp, vc, "."));
        assertNull(test(vccp, vc, "/"));
        assertNull(test(vccp, vc, "<"));
        assertNull(test(vccp, vc, ">"));
        assertNull(test(vccp, vc, "?"));
        assertNotNull(test(vccp, vc, "~"));
        assertNotNull(test(vccp, vc, "`"));

        assertNull(test(vccp, vc, "zzzzz"));
        assertNull(test(vccp, vc,
                "abcdefghijklmnopqrstuvwxyz"));
        assertNull(test(vccp, vc,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertNull(test(vccp, vc, "1"));
        assertNull(test(vccp, vc, "0"));
        assertNull(test(vccp, vc, "X\n"));
        assertNull(test(vccp, vc, "X\r"));
        assertNull(test(vccp, vc, "X\t"));

        // If you get this error -- don't just change the number
        // also add a unit test for new valid chars definition that you must have added into the base dictionary!
        assertEquals(13, vccs.size());
    }
}

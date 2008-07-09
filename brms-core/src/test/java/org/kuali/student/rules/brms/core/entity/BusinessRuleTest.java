/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.brms.core.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.common.entity.BusinessRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a <code>BusinessRuleTest</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:brms-core-test-context.xml"})
public class BusinessRuleTest extends AbstractTransactionalDaoTest {

    public static final String funcBusRule1 = "1";
    public static final String funcBusRule2 = "2";
    public static final String funcBusRule3 = "3";
    public static final String funcBusRule4 = "4";

    @Autowired
    FunctionalBusinessRuleManagementService brmsService;

    @Test
    public void testCreateRuleFunctionString() throws Exception {
        assertEquals("A OR B", retrieveFunctionString(funcBusRule1));
        assertEquals("A", retrieveFunctionString(funcBusRule2));
        assertEquals("A OR (B AND C)", retrieveFunctionString(funcBusRule3));
        assertEquals("(A OR B) AND C", retrieveFunctionString(funcBusRule4));
    }

    @Test
    public void testCreateAdjustedRuleFunctionString() throws Exception {
        assertEquals("A + B", retrieveAdjustedFunctionString(funcBusRule1));
        assertEquals("A", retrieveAdjustedFunctionString(funcBusRule2));
        assertEquals("A + (B * C)", retrieveAdjustedFunctionString(funcBusRule3));
        assertEquals("(A + B) * C", retrieveAdjustedFunctionString(funcBusRule4));
    }

    public String retrieveFunctionString(String ruleID) throws Exception {

        BusinessRule rule = null;

        try {
            rule = brmsService.getBusinessRuleUsingRuleId(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database: " + dae.getMessage());
            return null;
        }

        return rule.createRuleFunctionString();
    }

    private String retrieveAdjustedFunctionString(String ruleID) throws Exception {

        BusinessRule rule = null;

        try {
            rule = brmsService.getBusinessRuleUsingRuleId(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database: " + dae.getMessage());
            return null;
        }

        return rule.createAdjustedRuleFunctionString();
    }
}

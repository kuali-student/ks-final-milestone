/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSetType;
import org.kuali.student.rules.internal.common.entity.BusinessRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a <code>FunctionalBusinessRuleManagementService</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:brms-core-test-context.xml"})
public class FunctionalBusinessRuleManagementServiceTest extends AbstractTransactionalDaoTest {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    FunctionalBusinessRuleManagementService brmsService;

    @Test
    public void testRetrieveBusinessRules() throws Exception {
        List<BusinessRuleSetType> businessRuleTypes = new ArrayList<BusinessRuleSetType>();
        businessRuleTypes.add(new BusinessRuleSetType("course.co.req", "course.co.req"));
        List<BusinessRule> businessRules = brmsService.retrieveBusinessRules(businessRuleTypes, "CPR 201");

        assertEquals(1, businessRules.size());

        for (BusinessRule businessRule : businessRules) {
            assertTrue(businessRule.getBusinessRuleType().equals("course.co.req"));
            assertEquals("CPR 201", businessRule.getAnchor());
        }

        businessRuleTypes = new ArrayList<BusinessRuleSetType>();
        businessRuleTypes.add(new BusinessRuleSetType("course.co.req", "course.co.req"));
        businessRules = brmsService.retrieveBusinessRules(businessRuleTypes, "CPR 301");

        assertEquals(1, businessRules.size());

        for (BusinessRule businessRule : businessRules) {
            assertTrue(businessRule.getBusinessRuleType().equals("course.co.req"));
            assertEquals("CPR 301", businessRule.getAnchor());
        }

        businessRuleTypes = new ArrayList<BusinessRuleSetType>();
        businessRuleTypes.add(new BusinessRuleSetType("course.co.req", "course.co.req"));
        businessRules = brmsService.retrieveBusinessRules(businessRuleTypes, "EMS 1001");

        assertEquals(1, businessRules.size());

        for (BusinessRule businessRule : businessRules) {
            assertTrue(businessRule.getBusinessRuleType().equals("course.co.req"));
            assertEquals("EMS 1001", businessRule.getAnchor());
        }

        businessRuleTypes = new ArrayList<BusinessRuleSetType>();
        businessRuleTypes.add(new BusinessRuleSetType("course.co.req", "course.co.req"));
        businessRules = brmsService.retrieveBusinessRules(businessRuleTypes, "LPN 1001");

        assertEquals(1, businessRules.size());

        for (BusinessRule businessRule : businessRules) {
            assertTrue(businessRule.getBusinessRuleType().equals("course.co.req"));
            assertEquals("LPN 1001", businessRule.getAnchor());
        }
    }
}

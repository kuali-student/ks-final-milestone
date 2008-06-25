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
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.brms.agenda.entity.BusinessRuleType;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is a <code>FunctionalBusinessRuleManagementService</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:brms-core-test-context.xml"})
public class FunctionalBusinessRuleManagementServiceTest extends AbstractTransactionalDaoTest {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    FunctionalBusinessRuleManagementService brmsService;

    @Test
    public void testRetrieveFunctionalBusinessRules() throws Exception {
        List<BusinessRuleType> businessRuleTypes = new ArrayList<BusinessRuleType>();
        businessRuleTypes.add(new BusinessRuleType("course-co-req", "course-co-req"));
        List<FunctionalBusinessRule> functionalBusinessRules = brmsService
                .retrieveFunctionalBusinessRules("Student Enrolls in Course", businessRuleTypes, "course", "CPR 201");

        assertEquals(1, functionalBusinessRules.size());

        for (FunctionalBusinessRule businessRule : functionalBusinessRules) {
            assertEquals(businessRule.getAgendaType(), "Student Enrolls in Course");
            assertTrue(businessRule.getBusinessRuleType().equals("course-co-req"));
            assertEquals(businessRule.getAnchorType(), "course");
            assertEquals("CPR 201", businessRule.getAnchor());
        }

        businessRuleTypes = new ArrayList<BusinessRuleType>();
        businessRuleTypes.add(new BusinessRuleType("course-co-req", "course-co-req"));
        functionalBusinessRules = brmsService.retrieveFunctionalBusinessRules("Student Enrolls in Course",
                                                                              businessRuleTypes, "course", "CPR 301");

        assertEquals(1, functionalBusinessRules.size());

        for (FunctionalBusinessRule businessRule : functionalBusinessRules) {
            assertEquals(businessRule.getAgendaType(), "Student Enrolls in Course");
            assertTrue(businessRule.getBusinessRuleType().equals("course-co-req"));
            assertEquals(businessRule.getAnchorType(), "course");
            assertEquals("CPR 301", businessRule.getAnchor());
        }

        businessRuleTypes = new ArrayList<BusinessRuleType>();
        businessRuleTypes.add(new BusinessRuleType("course-co-req", "course-co-req"));
        functionalBusinessRules = brmsService.retrieveFunctionalBusinessRules("Student Enrolls in Course",
                                                                              businessRuleTypes, "course", "EMS 1001");

        assertEquals(1, functionalBusinessRules.size());

        for (FunctionalBusinessRule businessRule : functionalBusinessRules) {
            assertEquals(businessRule.getAgendaType(), "Student Enrolls in Course");
            assertTrue(businessRule.getBusinessRuleType().equals("course-co-req"));
            assertEquals(businessRule.getAnchorType(), "course");
            assertEquals("EMS 1001", businessRule.getAnchor());
        }

        businessRuleTypes = new ArrayList<BusinessRuleType>();
        businessRuleTypes.add(new BusinessRuleType("course-co-req", "course-co-req"));
        functionalBusinessRules = brmsService.retrieveFunctionalBusinessRules("Student Enrolls in Course",
                                                                              businessRuleTypes, "course", "LPN 1001");

        assertEquals(1, functionalBusinessRules.size());

        for (FunctionalBusinessRule businessRule : functionalBusinessRules) {
            assertEquals(businessRule.getAgendaType(), "Student Enrolls in Course");
            assertTrue(businessRule.getBusinessRuleType().equals("course-co-req"));
            assertEquals(businessRule.getAnchorType(), "course");
            assertEquals("LPN 1001", businessRule.getAnchor());
        }
    }
}

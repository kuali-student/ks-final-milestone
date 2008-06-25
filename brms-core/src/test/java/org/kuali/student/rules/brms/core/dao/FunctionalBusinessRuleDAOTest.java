/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.entity.RuleMetaData;

/**
 * This is a <code>FunctionalBusinessRuleDAOImpl</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
public class FunctionalBusinessRuleDAOTest extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.rules.brms.core.dao.impl.FunctionalBusinessRuleDAOImpl", testDataFile = "classpath:test-beans.xml")
    public FunctionalBusinessRuleDAO functionalBusinessRuleDAO;

    public static final String funcBusRule1 = "1";

    @Test
    public void testCreateRule() {
        RuleMetaData metaData = new RuleMetaData("Tom", new Date(), "", new Date(), new Date(), new Date(), "v1.1",
                "active");

        FunctionalBusinessRule rule = new FunctionalBusinessRule("PR CHEM 200",
                "enrollment prerequisites for Chemistry 200", "Success Message", "Failure Message", "2", null,
                metaData, "Student Enrolls in Course", "course-co-req", "course", "PR CHEM 200");

        functionalBusinessRuleDAO.createBusinessRule(rule);

        FunctionalBusinessRule newRule = em.find(FunctionalBusinessRule.class, rule.getId());
        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getIdentifier(), rule.getIdentifier());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
        assertEquals(newRule.getSuccessMessage(), rule.getSuccessMessage());
    }

    @Test
    public void testUpdateRule() {
        FunctionalBusinessRule rule = functionalBusinessRuleDAO.lookupBusinessRuleUsingRuleId(funcBusRule1);

        rule.setName("New Rule Name");

        functionalBusinessRuleDAO.updateBusinessRule(rule);

        FunctionalBusinessRule updatedRule = em.find(FunctionalBusinessRule.class, rule.getId());
        assertEquals(updatedRule.getName(), rule.getName());
    }

    @Test
    public void testDeleteRule() {
        FunctionalBusinessRule rule = functionalBusinessRuleDAO.lookupBusinessRuleUsingRuleId(funcBusRule1);
        assertTrue(functionalBusinessRuleDAO.deleteBusinessRule(rule));

        boolean noResult = false;
        try {
            functionalBusinessRuleDAO.lookupBusinessRuleUsingRuleId(funcBusRule1);
        } catch (NoResultException noresex) {
            noResult = true;
        }

        assertTrue(noResult);
    }

    @Test
    public void testLookupBusinessRuleUsingRuleId() {
        FunctionalBusinessRule rule = functionalBusinessRuleDAO.lookupBusinessRuleUsingRuleId("3");
        assertEquals("course-co-req", rule.getBusinessRuleType());
        assertEquals("Advanced CPR", rule.getName());
        assertEquals(7, rule.getElements().size());
    }

    @Test
    public void testLookupCompiledRuleID() {
        FunctionalBusinessRule rule = new FunctionalBusinessRule("PR CHEM 2000",
                "enrollment co-requisites for Chemistry 2000", "Success Message1", "Failure Message1", "5", null, null,
                "Student Enrolls in Course", "course-co-req", "course", "PR CHEM 1001");
        functionalBusinessRuleDAO.createBusinessRule(rule);

        Collection<FunctionalBusinessRule> newRules = functionalBusinessRuleDAO
                .lookupCompiledIDs("Student Enrolls in Course", "course-co-req", "course", "PR CHEM 1001");

        assertEquals(newRules.size(), 1);

        for (FunctionalBusinessRule newRule : newRules) {
            assertEquals(rule.getAgendaType(), newRule.getAgendaType());
            assertEquals(rule.getAnchor(), newRule.getAnchor());
            assertEquals(rule.getAnchorType(), newRule.getAnchorType());
            assertEquals(rule.getBusinessRuleType(), newRule.getBusinessRuleType());
        }
    }
}

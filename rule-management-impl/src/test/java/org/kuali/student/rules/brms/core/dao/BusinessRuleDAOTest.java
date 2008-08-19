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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.internal.common.entity.BusinessRule;
import org.kuali.student.rules.internal.common.entity.BusinessRuleInfo;
import org.kuali.student.rules.internal.common.entity.BusinessRuleInfo.Status;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a <code>BusinessRuleDAOImpl</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:brms-core-test-context.xml"})
public class BusinessRuleDAOTest extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.rules.brms.core.dao.impl.BusinessRuleDAOImpl")
    public BusinessRuleDAO businessRuleDAO;

    public static final String busRule1 = "1";

    @Test
    public void testCreateRule() {
        BusinessRuleInfo businessRuleInfo = new BusinessRuleInfo("Tom", new Date(), "", new Date(), new Date(), new Date(), "v1.1", Status.PUBLISHED);

        BusinessRule rule = new BusinessRule("PR CHEM 200", "enrollment prerequisites for Chemistry 200", "Success Message", "Failure Message", "2", null, businessRuleInfo, "course.co.req", "PR CHEM 200");

        businessRuleDAO.createBusinessRule(rule);

        BusinessRule newRule = em.find(BusinessRule.class, rule.getId());
        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getIdentifier(), rule.getIdentifier());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
        assertEquals(newRule.getSuccessMessage(), rule.getSuccessMessage());
    }

    @Test
    public void testUpdateRule() {
        BusinessRule rule = businessRuleDAO.lookupBusinessRuleUsingIdentifier(busRule1);

        rule.setName("New Rule Name");

        businessRuleDAO.updateBusinessRule(rule);

        BusinessRule updatedRule = em.find(BusinessRule.class, rule.getId());
        assertEquals(updatedRule.getName(), rule.getName());
    }

    @Test
    public void testDeleteRule() {
        BusinessRule rule = businessRuleDAO.lookupBusinessRuleUsingIdentifier(busRule1);
        assertTrue(businessRuleDAO.deleteBusinessRule(rule));

        boolean noResult = false;
        try {
            businessRuleDAO.lookupBusinessRuleUsingIdentifier(busRule1);
        } catch (EmptyResultDataAccessException noresex) {
            noResult = true;
        }

        assertTrue(noResult);
    }

    @Test
    public void testLookupBusinessRuleUsingIdentifier() {
        BusinessRule rule = businessRuleDAO.lookupBusinessRuleUsingIdentifier("3");
        assertEquals("course.co.req", rule.getBusinessRuleType());
        assertEquals("EMS Certificate Program", rule.getName());
        assertEquals(7, rule.getElements().size());
    }

    @Test
    public void testLookupCompiledRuleID() {
        BusinessRule rule = new BusinessRule("PR CHEM 2000", "enrollment co-requisites for Chemistry 2000", "Success Message1", "Failure Message1", "5", null, null, "course.co.req", "PR CHEM 1001");
        businessRuleDAO.createBusinessRule(rule);

        Collection<BusinessRule> newRules = businessRuleDAO.lookupCompiledIDs("course.co.req", "PR CHEM 1001");

        assertEquals(newRules.size(), 1);

        for (BusinessRule newRule : newRules) {
            assertEquals(rule.getAnchor(), newRule.getAnchor());
            assertEquals(rule.getBusinessRuleType(), newRule.getBusinessRuleType());
        }
    }
}

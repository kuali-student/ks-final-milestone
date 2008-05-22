/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.BRMSCore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.BRMSCore.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.BRMSCore.entity.BusinessRuleEvaluation;
import org.kuali.student.rules.BRMSCore.entity.ComparisonOperatorType;
import org.kuali.student.rules.BRMSCore.entity.FunctionalBusinessRule;
import org.kuali.student.rules.BRMSCore.entity.LeftHandSide;
import org.kuali.student.rules.BRMSCore.entity.Operator;
import org.kuali.student.rules.BRMSCore.entity.RightHandSide;
import org.kuali.student.rules.BRMSCore.entity.RuleElement;
import org.kuali.student.rules.BRMSCore.entity.RuleElementType;
import org.kuali.student.rules.BRMSCore.entity.RuleMetaData;
import org.kuali.student.rules.BRMSCore.entity.RuleProposition;

/**
 * This is a <code>FunctionalBusinessRuleDAOImpl</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@PersistenceFileLocation("classpath:META-INF/persistence.xml")
public class TestFunctionalBusinessRuleDAO extends AbstractTransactionalDaoTest {

    @Dao("org.kuali.student.rules.BRMSCore.dao.impl.FunctionalBusinessRuleDAOImpl")
    public FunctionalBusinessRuleDAO functionalBusinessRuleDAO;

    // @PersistenceContext(unitName = "BRMS")
    // private EntityManager em;

    public static final String FACT_CONTAINER = "AcademicRecord";

    private long ruleId;

    @Before
    public void onSetUpInTransaction() throws Exception {
        int ordinalPosition = 1;
        RuleElement ruleElement = null;
        RuleProposition ruleProp = null;
        LeftHandSide leftSide = null;
        RightHandSide rightSide = null;
        Operator operator = null;
        ArrayList<String> criteria = null;
        ArrayList<String> facts = null;

        // setup business rule meta data (for now common to all rules)
        RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1", "active");

        // we keep this entity empty for now
        BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();

        /********************************************************************************************************************
         * insert "(1 of CPR 101) OR ( 1 of FA 001)"
         *******************************************************************************************************************/

        // create basic rule structure
        FunctionalBusinessRule busRule = new FunctionalBusinessRule("Intermediate CPR", "enrollment co-requisites for Intermediate CPR 201", "Success Message", "Failure Message", "1", "51", metaData, businessRuleEvaluation);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 101
        facts = new ArrayList<String>();
        facts.add("CPR 101");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("1");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001
        facts = new ArrayList<String>();
        facts.add("FA 001");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("1");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        functionalBusinessRuleDAO.createBusinessRule(busRule);
        em.persist(busRule);

        ruleId = busRule.getId();
    }

    @Test
    public void testCreateRule() {
        RuleMetaData metaData = new RuleMetaData("Tom", new Date(), "", new Date(), new Date(), new Date(), "v1.1", "active");

        BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();

        FunctionalBusinessRule rule = new FunctionalBusinessRule("PR CHEM 200", "enrollment prerequisites for Chemistry 200", "Success Message", "Failure Message", "2", null, metaData, businessRuleEvaluation);

        functionalBusinessRuleDAO.createBusinessRule(rule);

        FunctionalBusinessRule newRule = em.find(FunctionalBusinessRule.class, rule.getId());
        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getRuleIdentifier(), rule.getRuleIdentifier());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
        assertEquals(newRule.getSuccessMessage(), rule.getSuccessMessage());
    }

    @Test
    public void testUpdateRule() {
        FunctionalBusinessRule rule = functionalBusinessRuleDAO.lookupBusinessRule(ruleId);

        rule.setName("New Rule Name");

        functionalBusinessRuleDAO.updateBusinessRule(rule);

        FunctionalBusinessRule updatedRule = em.find(FunctionalBusinessRule.class, rule.getId());
        assertEquals(updatedRule.getName(), rule.getName());
    }

    @Test
    public void testDeleteRule() {
        FunctionalBusinessRule rule = functionalBusinessRuleDAO.lookupBusinessRule(ruleId);
        assertTrue(functionalBusinessRuleDAO.deleteBusinessRule(rule));
        assertNull(functionalBusinessRuleDAO.lookupBusinessRule(ruleId));
    }

    @Test
    public void testLookupBusinessRule() {
        RuleMetaData metaData = new RuleMetaData("James", new Date(), "", new Date(), new Date(), new Date(), "v1.1", "active");

        BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();

        FunctionalBusinessRule rule = new FunctionalBusinessRule("PR CHEM 300", "enrollment prerequisites for Chemistry 300", "Success Message", "Failure Message", "3", null, metaData, businessRuleEvaluation);

        functionalBusinessRuleDAO.createBusinessRule(rule);

        FunctionalBusinessRule newRule = functionalBusinessRuleDAO.lookupBusinessRule(rule.getId());
        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getRuleIdentifier(), rule.getRuleIdentifier());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
        assertEquals(newRule.getSuccessMessage(), rule.getSuccessMessage());
    }

    @Test
    public void testLookupBusinessRuleID() {
        RuleMetaData metaData = new RuleMetaData("Eric1", new Date(), "", new Date(), new Date(), new Date(), "v1.1", "active");
        System.out.println("Test1");
        BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();
        FunctionalBusinessRule rule = new FunctionalBusinessRule("PR CHEM 1001", "enrollment prerequisites for Chemistry 1001", "Success Message1", "Failure Message1", "4", null, metaData, businessRuleEvaluation);
        functionalBusinessRuleDAO.createBusinessRule(rule);
        FunctionalBusinessRule newRule = functionalBusinessRuleDAO.lookupBusinessRuleID(rule.getRuleIdentifier());
        assertEquals(newRule.getId(), rule.getId());
        assertEquals(newRule.getRuleIdentifier(), rule.getRuleIdentifier());
        assertEquals(newRule.getName(), rule.getName());
        assertEquals(newRule.getDescription(), rule.getDescription());
        assertEquals(newRule.getSuccessMessage(), rule.getSuccessMessage());
    }
}

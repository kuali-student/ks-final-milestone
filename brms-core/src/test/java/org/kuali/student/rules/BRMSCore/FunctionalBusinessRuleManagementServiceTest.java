/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.BRMSCore;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.rules.BRMSCore.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.BRMSCore.entity.BusinessRuleEvaluation;
import org.kuali.student.rules.BRMSCore.entity.ComparisonOperatorType;
import org.kuali.student.rules.BRMSCore.entity.ComputationAssistant;
import org.kuali.student.rules.BRMSCore.entity.FunctionalBusinessRule;
import org.kuali.student.rules.BRMSCore.entity.LeftHandSide;
import org.kuali.student.rules.BRMSCore.entity.Operator;
import org.kuali.student.rules.BRMSCore.entity.RightHandSide;
import org.kuali.student.rules.BRMSCore.entity.RuleElement;
import org.kuali.student.rules.BRMSCore.entity.RuleElementType;
import org.kuali.student.rules.BRMSCore.entity.RuleMetaData;
import org.kuali.student.rules.BRMSCore.entity.RuleProposition;
import org.kuali.student.rules.BRMSCore.entity.ValueType;
import org.kuali.student.rules.BRMSCore.entity.YieldValueFunctionType;
import org.kuali.student.rules.BRMSCore.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.brms.parser.GenerateRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a <code>FunctionalBusinessRuleManagementService</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager")
public class FunctionalBusinessRuleManagementServiceTest extends AbstractJpaTests {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    @Autowired
    FunctionalBusinessRuleManagementService brmsService;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testCreateRuleFunctionString() throws Exception {
        assertEquals("(A OR B)", retrieveFunctionString("1"));
        assertEquals("A", retrieveFunctionString("2"));
        assertEquals("(A) OR (B AND C)", retrieveFunctionString("3"));
        assertEquals("(A OR B) AND C", retrieveFunctionString("4"));
    }

    @Test
    public void testCreateAdjustedRuleFunctionString() throws Exception {
        assertEquals("(A + B)", retrieveAdjustedFunctionString("1"));
        assertEquals("A", retrieveAdjustedFunctionString("2"));
        assertEquals("(A) + (B * C)", retrieveAdjustedFunctionString("3"));
        assertEquals("(A + B) * C", retrieveAdjustedFunctionString("4"));
    }

    @Test
    public void testBuildRuleSet() throws Exception {
        FunctionalBusinessRule rule1 = businessRuleDAO.lookupBusinessRuleID("1");
        GenerateRuleSet ruleSet1 = brmsService.buildRuleSet(rule1);
        assertEquals("org.kuali.student.rules.enrollment1", ruleSet1.getRuleSet().getName());
    }

    public String retrieveFunctionString(String ruleID) throws Exception {

        FunctionalBusinessRule rule = null;

        try {
            rule = brmsService.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database:" + dae.getStackTrace());
            return null;
        }

        return brmsService.createRuleFunctionString(rule);
    }

    private String retrieveAdjustedFunctionString(String ruleID) throws Exception {

        FunctionalBusinessRule rule = null;

        try {
            rule = brmsService.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database.");
            return null;
        }

        return brmsService.createAdjustedRuleFunctionString(rule);
    }

    /*
     * public void onTearDownAfterTransaction() throws Exception { super.onTearDownInTransaction(); setDirty(); }
     */
    @Override
    @Before
    public void onSetUpInTransaction() throws Exception {

        deleteRules();

        int ordinalPosition = 1;
        RuleElement ruleElement = null;
        RuleProposition ruleProp = null;
        LeftHandSide leftSide = null;
        RightHandSide rightSide = null;
        Operator operator = null;
        ComputationAssistant compAssistant = null;

        // setup business rule meta data (for now common to all rules)
        RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1", "active");

        // we keep this entity empty for now
        BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();

        /********************************************************************************************************************
         * insert "(1 of CPR 101) OR ( 1 of FA 001)"
         *******************************************************************************************************************/

        // create basic rule structure
        FunctionalBusinessRule busRule = new FunctionalBusinessRule("Intermediate CPR", "enrollment co-requisites for Intermediate CPR 201", "Rule 1 Success Message", "Rule 1 Failure Message", "1", null, metaData, businessRuleEvaluation);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 101
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "1");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "FA 001", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "1");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // businessRuleDAO.createBusinessRule(busRule);
        em.persist(busRule);

        /********************************************************************************************************************
         * insert "2 of CPR 101 and CPR 201"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("Advanced CPR", "enrollment co-requisites for Advanced CPR 301", "Rule 2 Success Message", "Rule 2 Failure Message", "2", null, metaData, businessRuleEvaluation);

        // 2 of CPR 101 and CPR 201
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101, CPR 201", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "2");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // businessRuleDAO.createBusinessRule(busRule);
        em.persist(busRule);

        /********************************************************************************************************************
         * insert "(12 credits from CPR 101, CPR 105, CPR 201, CPR 301) OR (1 of CPR 4005 and 1 of FA 001, WS 001)"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("EMS Certificate Program", "enrollment co-requisites for Certificate Program EMS 1001", "Rule 3 Success Message", "Rule 3 Failure Message", "3", null, metaData, businessRuleEvaluation);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 12 credits from CPR 101, CPR 105, CPR 201, CPR 301
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101, CPR 105, CPR 201, CPR 301", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCredits", "java.lang.Integer", "12");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite credits", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 4005
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 4005", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "1");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // AND
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001, WS 001
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "FA 001, WS 001", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "2");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // businessRuleDAO.createBusinessRule(busRule);
        em.persist(busRule);

        /********************************************************************************************************************
         * insert "(12 credits from CPR 101, CPR 105, CPR 201, CPR 301 OR 1 of CPR 4005) and 1 of FA 001, WS 001"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("LPN Certificate Program", "enrollment co-requisites for Certificate Program LPN 1001", "Rule 4 Success Message", "Rule 4 Failure Message", "4", null, metaData, businessRuleEvaluation);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 12 credits from CPR 101, CPR 105, CPR 201, CPR 301
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101, CPR 105, CPR 201, CPR 301", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCredits", "java.lang.Integer", "12");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite credits", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 4005
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 4005", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "1");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // AND
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001, WS 001
        compAssistant = new ComputationAssistant(YieldValueFunctionType.INTERSECTION_TYPE);
        leftSide = new LeftHandSide("Student.LearningResults", "FA 001, WS 001", FACT_CONTAINER, compAssistant, ValueType.NUMBER_TYPE);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "2");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // businessRuleDAO.createBusinessRule(busRule);
        em.persist(busRule);
    }

    @After
    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownInTransaction();
        setDirty();
        deleteRules();
    }

    public void deleteRules() {
        try {
            FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID("1");
            businessRuleDAO.deleteBusinessRule(rule);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.toString());
        }

        try {
            FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID("2");
            businessRuleDAO.deleteBusinessRule(rule);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.toString());
        }

        try {
            FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID("3");
            businessRuleDAO.deleteBusinessRule(rule);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.toString());
        }

        try {
            FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID("4");
            businessRuleDAO.deleteBusinessRule(rule);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.toString());
        }

        em.flush();
    }
}

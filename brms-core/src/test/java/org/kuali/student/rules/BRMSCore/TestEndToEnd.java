package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.kuali.student.rules.BRMSCore.service.FunctionalBusinessRuleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager")
public class TestEndToEnd extends AbstractJpaTests {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    @Autowired
    FunctionalBusinessRuleManagementService metadata;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testBRMS() throws Exception {

        FunctionalBusinessRule rule = null;

        // 1. retrieve business rule
        String ruleID = "1";

        try {
            rule = metadata.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database.");
            return;
        }

        if (rule == null) {
            System.out.println("Rule " + ruleID + " not found");
        } else {
            System.out.println("Found rule: " + rule.getName());
        }

        // FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRule(id);
        System.out.println(System.getProperty("line.separator"));

        // 2. get function string for a given business rule
        String functionString = metadata.createRuleFunctionString(rule);
        System.out.println("Function String: " + functionString);
        System.out.println(System.getProperty("line.separator"));

        // 3. generate Drool rule WHEN part based on business rule propositions
        // String droolWhenPart = metadata.mapMetaRuleToDroolRule(rule);
        // if (droolWhenPart != null) {
        // System.out.println("Drools WHEN:" + droolWhenPart);
        // System.out.println(System.getProperty("line.separator"));
        // } else {
        // System.out.println("Could not map Meta Data into Drool rules.");
        // }

        ruleID = "2";

        try {
            rule = metadata.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database.");
            return;
        }

        if (rule == null) {
            System.out.println("Rule " + ruleID + " not found");
        } else {
            System.out.println("Found rule: " + rule.getName());
        }

        functionString = metadata.createRuleFunctionString(rule);
        System.out.println("Function String: " + functionString);
        System.out.println(System.getProperty("line.separator"));

        ruleID = "3";

        try {
            rule = metadata.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database.");
            return;
        }

        if (rule == null) {
            System.out.println("Rule " + ruleID + " not found");
        } else {
            System.out.println("Found rule: " + rule.getName());
        }

        functionString = metadata.createRuleFunctionString(rule);
        System.out.println("Function String: " + functionString);
        System.out.println(System.getProperty("line.separator"));

        ruleID = "4";

        try {
            rule = metadata.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database.");
            return;
        }

        if (rule == null) {
            System.out.println("Rule " + ruleID + " not found");
        } else {
            System.out.println("Found rule: " + rule.getName());
        }

        functionString = metadata.createRuleFunctionString(rule);
        System.out.println("Function String: " + functionString);
        System.out.println(System.getProperty("line.separator"));
    }

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
        FunctionalBusinessRule busRule = new FunctionalBusinessRule("Intermediate CPR", "enrollment co-requisites for Intermediate CPR 201", "1", metaData, businessRuleEvaluation);

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
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", leftSide, operator, rightSide);
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
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        businessRuleDAO.createBusinessRule(busRule);

        /********************************************************************************************************************
         * insert "2 of CPR 101 and CPR 201"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("Advanced CPR", "enrollment co-requisites for Advanced CPR 301", "2", metaData, businessRuleEvaluation);

        // 2 of CPR 101 and CPR 201
        facts = new ArrayList<String>();
        facts.add("CPR 101");
        facts.add("CPR 201");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        businessRuleDAO.createBusinessRule(busRule);

        /********************************************************************************************************************
         * insert "(12 credits from CPR 101, CPR 105, CPR 201, CPR 301) OR (1 of CPR 4005 and 1 of FA 001, WS 001)"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("EMS Certificate Program", "enrollment co-requisites for Certificate Program EMS 1001", "3", metaData, businessRuleEvaluation);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 12 credits from CPR 101, CPR 105, CPR 201, CPR 301
        facts = new ArrayList<String>();
        facts.add("CPR 101");
        facts.add("CPR 105");
        facts.add("CPR 201");
        facts.add("CPR 301");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUCredits", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("12");
        rightSide = new RightHandSide("requiredCredits", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite credits", leftSide, operator, rightSide);
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
        facts = new ArrayList<String>();
        facts.add("CPR 4005");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("1");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // AND
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001, WS 001
        facts = new ArrayList<String>();
        facts.add("FA 001");
        facts.add("WS 001");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        businessRuleDAO.createBusinessRule(busRule);

        /********************************************************************************************************************
         * insert "(12 credits from CPR 101, CPR 105, CPR 201, CPR 301 OR 1 of CPR 4005) and 1 of FA 001, WS 001"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("LPN Certificate Program", "enrollment co-requisites for Certificate Program LPN 1001", "4", metaData, businessRuleEvaluation);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 12 credits from CPR 101, CPR 105, CPR 201, CPR 301
        facts = new ArrayList<String>();
        facts.add("CPR 101");
        facts.add("CPR 105");
        facts.add("CPR 201");
        facts.add("CPR 301");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUCredits", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("12");
        rightSide = new RightHandSide("requiredCredits", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite credits", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 4005
        facts = new ArrayList<String>();
        facts.add("CPR 4005");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("1");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", leftSide, operator, rightSide);
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
        facts = new ArrayList<String>();
        facts.add("FA 001");
        facts.add("WS 001");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        businessRuleDAO.createBusinessRule(busRule);
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

    @Override
    // @After
    public void onTearDownAfterTransaction() throws Exception {

        deleteRules();
        super.onTearDownInTransaction();
        setDirty();
    }

    /**
     * @return the businessRuleDAO
     */
    public final FunctionalBusinessRuleDAO getBusinessRuleDAO() {
        return businessRuleDAO;
    }

    /**
     * @param businessRuleDAO
     *            the businessRuleDAO to set
     */
    public final void setBusinessRuleDAO(FunctionalBusinessRuleDAO businessRuleDAO) {
        this.businessRuleDAO = businessRuleDAO;
    }

    /**
     * @return the em
     */
    public final EntityManager getEm() {
        return em;
    }

    /**
     * @param em
     *            the em to set
     */
    public final void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the metadata
     */
    public final FunctionalBusinessRuleManagementService getMetadata() {
        return metadata;
    }

    /**
     * @param metadata
     *            the metadata to set
     */
    public final void setMetadata(FunctionalBusinessRuleManagementService metadata) {
        this.metadata = metadata;
    }

}

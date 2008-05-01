package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;
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
    public void testPOCRules() throws Exception {
        assertEquals("(A OR B)", retrieveFunctionString("1"));
        assertEquals("A", retrieveFunctionString("2"));
        assertEquals("(A) OR (B AND C)", retrieveFunctionString("3"));
        assertEquals("(A OR B) AND C", retrieveFunctionString("4"));
    }

    @Test
    public void testAdjustedPOCRules() throws Exception {
        assertEquals("(A + B)", retrieveAdjustedFunctionString("1"));
        assertEquals("A", retrieveAdjustedFunctionString("2"));
        assertEquals("(A) + (B * C)", retrieveAdjustedFunctionString("3"));
        assertEquals("(A + B) * C", retrieveAdjustedFunctionString("4"));
    }

    private String retrieveFunctionString(String ruleID) throws Exception {

        FunctionalBusinessRule rule = null;

        try {
            rule = metadata.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database.");
            return null;
        }

        return metadata.createRuleFunctionString(rule);
    }

    private String retrieveAdjustedFunctionString(String ruleID) throws Exception {

        FunctionalBusinessRule rule = null;

        try {
            rule = metadata.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.out.println("Could not load rule " + ruleID + " from database.");
            return null;
        }

        return metadata.createAdjustedRuleFunctionString(rule);
    }

    @After
    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownInTransaction();
        setDirty();
        deleteRules();
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:application-context.xml"};
    }

    /*
     * public void onTearDownAfterTransaction() throws Exception { super.onTearDownInTransaction(); setDirty(); }
     */
    @Override
    @Before
    public void onSetUpInTransaction() throws Exception {

        // deleteRules();

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
        FunctionalBusinessRule busRule = new FunctionalBusinessRule("Intermediate CPR", "enrollment co-requisites for Intermediate CPR 201", "Success Message", "Failure Message", "1", metaData, businessRuleEvaluation);

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

        // businessRuleDAO.createBusinessRule(busRule);
        em.persist(busRule);

        /********************************************************************************************************************
         * insert "2 of CPR 101 and CPR 201"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("Advanced CPR", "enrollment co-requisites for Advanced CPR 301", "Success Message", "Failure Message", "2", metaData, businessRuleEvaluation);

        // 2 of CPR 101 and CPR 201
        facts = new ArrayList<String>();
        facts.add("CPR 101");
        facts.add("CPR 201");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
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
        busRule = new FunctionalBusinessRule("EMS Certificate Program", "enrollment co-requisites for Certificate Program EMS 1001", "Success Message", "Failure Message", "3", metaData, businessRuleEvaluation);

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
        facts = new ArrayList<String>();
        facts.add("CPR 4005");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("1");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
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
        busRule = new FunctionalBusinessRule("LPN Certificate Program", "enrollment co-requisites for Certificate Program LPN 1001", "Success Message", "Failure Message", "4", metaData, businessRuleEvaluation);

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
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite credits", "prop error message", leftSide, operator, rightSide);
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
        facts = new ArrayList<String>();
        facts.add("FA 001");
        facts.add("WS 001");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses", "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // businessRuleDAO.createBusinessRule(busRule);
        em.persist(busRule);
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

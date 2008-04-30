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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager")
public class TestBRMSMetaData extends AbstractJpaTests {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    @PersistenceContext
    private EntityManager em;

    private long ruleId; // holds ID of a test rule created in onSetUpInTransaction()

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.test.AbstractTransactionalSpringContextTests#onTearDownInTransaction()
     */
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownInTransaction();
        setDirty();
    }

    @Override
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

        // setup business rule meta data and main rule entity
        RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1", "active");

        // we keep this entity empty for now
        BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();

        // create basic rule structure
        FunctionalBusinessRule busRule = new FunctionalBusinessRule("PR CHEM 200", "enrollment prerequisites for Chemistry 200", "PR 40244", metaData, businessRuleEvaluation);

        // =======================================
        // ( Completed any 2 of (Math 101, 102, 103) OR Completed any 3 of (Chem 101, 102, 103) )
        // =======================================
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // Completed any 2 of (Math 101, 102, 103)
        facts = new ArrayList<String>();
        facts.add("MATH101, MATH102, MATH103");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatchesYYY", facts);
        operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("Math Prerequisite", "enumeration of required Math courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // Completed any 3 of (Chem 101, 102, 103)
        facts = new ArrayList<String>();
        facts.add("CHEM101, CHEM102, CHEM103");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", criteria);
        operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("3");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("Chemistry Prerequisite", "enumeration of required Chem courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // =======================================
        // AND Accumulated 10 creds in Basic Core
        // =======================================
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        facts = new ArrayList<String>();
        facts.add("ENG100, PSYC102, MATH103");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCoreUnits", facts);
        operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCoreUnits", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("Required Core Units", "Number of core units", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // =======================================
        // AND ( obtained instructor approval OR has Senior class standing )
        // =======================================
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // obtained instructor approval
        facts = new ArrayList<String>();
        facts.add("");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "isApprovedByInstructor", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("true");
        rightSide = new RightHandSide("instructorApproval", "java.lang.Boolean", criteria);
        ruleProp = new RuleProposition("Instructor Approval", "approval granted", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // has Senior class standing
        facts = new ArrayList<String>();
        facts.add("");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "hasSeniorStanding", facts);
        operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("true");
        rightSide = new RightHandSide("seniorStanding", "java.lang.Boolean", criteria);
        ruleProp = new RuleProposition("Senior Class Standing", "Senior standing achieved", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        businessRuleDAO.createBusinessRule(busRule);

        ruleId = busRule.getId();
    }

    @Test
    public void testCreateRule() {
        RuleMetaData metaData = new RuleMetaData("Tom", new Date(), "", new Date(), new Date(), new Date(), "v1.1", "active");

        BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();

        FunctionalBusinessRule rule = new FunctionalBusinessRule("PR CHEM 200", "enrollment prerequisites for Chemistry 200", "PR 40244", metaData, businessRuleEvaluation);

        businessRuleDAO.createBusinessRule(rule);

        FunctionalBusinessRule newRule = em.find(FunctionalBusinessRule.class, rule.getId());
        assertEquals(newRule.getId(), rule.getId());
    }

    @Test
    public void testUpdateRule() {
        FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRule(ruleId);
        // FunctionalBusinessRule ruleSet = functionalBusinessRuleDAO.lookupBusinessRuleID("PR 40244");

        rule.setName("New Rule Name");

        businessRuleDAO.updateBusinessRule(rule);

        FunctionalBusinessRule updatedRule = em.find(FunctionalBusinessRule.class, rule.getId());
        assertEquals(updatedRule.getName(), rule.getName());
    }

    @Test
    public void testDeleteRule() {
        FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRule(ruleId);
        assertTrue(businessRuleDAO.deleteBusinessRule(rule));
        assertNull(businessRuleDAO.lookupBusinessRule(ruleId));
    }

    @Override
    protected boolean shouldUseShadowLoader() {
        return false;
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
}

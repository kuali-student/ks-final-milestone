package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TestEndToEnd {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    public static void main(String[] args) throws Exception {

        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-context.xml");

        // add a shutdown hook for the above context...
        ctx.registerShutdownHook();

        BRMSMetaData metadata = (BRMSMetaData) ctx.getBean("BRMSMetaData");
        // TestEndToEnd testEndToEnd = (TestEndToEnd) ctx.getBean("TestEndToEnd");

        // 1. load one business rule MetaData into database
        try {
            // testEndToEnd.insertBusinessRuleMetadata();
        } catch (Exception e) {
            System.out.println("Cannot insert duplicate record:" + e.getMessage());
            return;
        }

        // 2. retrieve business rule
        String ruleID = "PR 40244";
        FunctionalBusinessRule rule = metadata.getFunctionalBusinessRule(ruleID);

        if (rule == null) {
            System.out.println("Rule " + ruleID + " not found");
        } else {
            System.out.println("Found rule: " + rule.getName());
        }

        // FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRule(id);
        System.out.println(System.getProperty("line.separator"));

        // 3. get function string for a given business rule
        String functionString = metadata.getRuleFunctionString(rule);
        System.out.println("Function String: " + functionString);
        System.out.println(System.getProperty("line.separator"));

        // 4. generate Drool rule WHEN part based on business rule propositions
        String droolWhenPart = metadata.mapMetaRuleToDroolRule(rule);
        if (droolWhenPart != null) {
            System.out.println("Drools WHEN:" + droolWhenPart);
            System.out.println(System.getProperty("line.separator"));
        } else {
            System.out.println("Could not map Meta Data into Drool rules.");
        }

    }

    public void insertBusinessRuleMetadata() throws Exception {

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
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // Completed any 2 of (Math 101, 102, 103)
        facts = new ArrayList<String>();
        facts.add("MATH101, MATH102, MATH103");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatchesXXX", facts);
        operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("2");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("Math Prerequisite", "enumeration of required Math courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // Completed any 3 of (Chem 101, 102, 103)
        facts = new ArrayList<String>();
        facts.add("CHEM101, CHEM102, CHEM103");
        leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", facts);
        operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
        criteria = new ArrayList<String>();
        criteria.add("3");
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);
        ruleProp = new RuleProposition("Chemistry Prerequisite", "enumeration of required Chem courses", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // =======================================
        // AND Accumulated 10 creds in Basic Core
        // =======================================
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
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
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // =======================================
        // AND ( obtained instructor approval OR has Senior class standing )
        // =======================================
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
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
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
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
        ruleElement.setFunctionalBusinessRule(busRule);
        busRule.addRuleElement(ruleElement);

        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        ruleElement.setFunctionalBusinessRule(busRule);
        // ruleElementDAO.createRuleElement(ruleElement);
        busRule.addRuleElement(ruleElement);

        this.businessRuleDAO.createBusinessRule(busRule);
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

}

/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.validate;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.brms.repository.RuleEngineRepository;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.brms.core.entity.ComparisonOperator;
import org.kuali.student.rules.brms.core.entity.ComputationAssistant;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.entity.LeftHandSide;
import org.kuali.student.rules.brms.core.entity.Operator;
import org.kuali.student.rules.brms.core.entity.RightHandSide;
import org.kuali.student.rules.brms.core.entity.RuleElement;
import org.kuali.student.rules.brms.core.entity.RuleElementType;
import org.kuali.student.rules.brms.core.entity.RuleMetaData;
import org.kuali.student.rules.brms.core.entity.RuleProposition;
import org.kuali.student.rules.brms.core.entity.ValueType;
import org.kuali.student.rules.brms.core.entity.YieldValueFunction;
import org.kuali.student.rules.brms.drools.translator.GenerateRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * BRMS utility functions used to populate database and repository with demo data.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public class UtilBRMSDatabase {

    public static final String FACT_CONTAINER = "AcademicRecord";

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RuleEngineRepository droolsRepository;

    /**
     * Inserts 4 demo business rules into BRMS database.
     */
    public final void populateDatabase() throws Exception {

        deleteRules();

        int ordinalPosition = 1;
        RuleElement ruleElement = null;
        RuleProposition ruleProp = null;
        LeftHandSide leftSide = null;
        RightHandSide rightSide = null;
        Operator operator = null;
        ComputationAssistant compAssistant = null;

        // setup business rule meta data (for now common to all rules)
        RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1",
                "active");

        /********************************************************************************************************************
         * insert "(1 of CPR 101) OR ( 2 of FA 001, FA 002)"
         *******************************************************************************************************************/

        // create basic rule structure
        FunctionalBusinessRule busRule = new FunctionalBusinessRule("Intermediate CPR",
                "enrollment co-requisites for Intermediate CPR 201", "Rule 1 Success Message",
                "Rule 1 Failure Message", "1", null, metaData, "Student Enrolls in Course", "course-co-req", "course",
                "CPR 201");

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 101
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101", FACT_CONTAINER, compAssistant,
                ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "1");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "FA 001, FA 002", FACT_CONTAINER, compAssistant,
                ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "2");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        em.persist(busRule);

        /********************************************************************************************************************
         * insert "2 of CPR 101 and CPR 201"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("Advanced CPR", "enrollment co-requisites for Advanced CPR 301",
                "Rule 2 Success Message", "Rule 2 Failure Message", "2", null, metaData, "Student Enrolls in Course",
                "course-co-req", "course", "CPR 301");

        // 2 of CPR 101 and CPR 201
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101, CPR 201", FACT_CONTAINER, compAssistant,
                ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "2");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        em.persist(busRule);

        /********************************************************************************************************************
         * insert "(12 credits from CPR 101, CPR 105, CPR 201, CPR 301) OR (1 of CPR 4005 and 1 of FA 001, WS 001)"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("EMS Certificate Program",
                "enrollment co-requisites for Certificate Program EMS 1001", "Rule 3 Success Message",
                "Rule 3 Failure Message", "3", null, metaData, "Student Enrolls in Course", "course-co-req", "course",
                "EMS 1001");

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 12 credits from CPR 101, CPR 105, CPR 201, CPR 301
        compAssistant = new ComputationAssistant(YieldValueFunction.SUM);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101, CPR 105, CPR 201, CPR 301", FACT_CONTAINER,
                compAssistant, ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCredits", "java.lang.Integer", "12");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite credits",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 4005
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 4005", FACT_CONTAINER, compAssistant,
                ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "1");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // AND
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001, WS 001
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "FA 001, WS 001", FACT_CONTAINER, compAssistant,
                ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "2");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
         busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        em.persist(busRule);

        /********************************************************************************************************************
         * insert "(12 credits from CPR 101, CPR 105, CPR 201, CPR 301 OR 1 of CPR 4005) and 1 of FA 001, WS 001"
         *******************************************************************************************************************/

        // create basic rule structure
        busRule = new FunctionalBusinessRule("LPN Certificate Program",
                "enrollment co-requisites for Certificate Program LPN 1001", "Rule 4 Success Message",
                "Rule 4 Failure Message", "4", null, metaData, "Student Enrolls in Course", "course-co-req", "course",
                "LPN 1001");

        // left bracket '('
        ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 12 credits from CPR 101, CPR 105, CPR 201, CPR 301
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 101, CPR 105, CPR 201, CPR 301", FACT_CONTAINER,
                compAssistant, ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCredits", "java.lang.Integer", "12");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite credits",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // OR
        ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 1 of CPR 4005
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "CPR 4005", FACT_CONTAINER, compAssistant,
                ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "1");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        // right bracket ')'
        ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // AND
        ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
        busRule.addRuleElement(ruleElement);

        // 1 of FA 001, WS 001
        compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        leftSide = new LeftHandSide("Student.LearningResults", "FA 001, WS 001", FACT_CONTAINER, compAssistant,
                ValueType.NUMBER);
        operator = new Operator(ComparisonOperator.EQUAL_TO);
        rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", "2");
        ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        busRule.addRuleElement(ruleElement);

        em.persist(busRule);
        em.flush();
    }

    /**
     * Compiles 4 demo business rules and inserts them into Drools repository.
     */
    public final void compileDroolsRule() throws Exception {

        GenerateRuleSet grs = GenerateRuleSet.getInstance();

        FunctionalBusinessRule rule1 = businessRuleDAO.lookupBusinessRuleID("1");
        RuleSet rs1 = grs.parse(rule1);
        System.out.println("Rule set1:\n" + rs1.getContent());
        String rulesetUuid1 = droolsRepository.createRuleSet(rs1);
        rule1.setCompiledID(rulesetUuid1);
        droolsRepository.loadRuleSet(rulesetUuid1);
        em.merge(rule1);

        
        FunctionalBusinessRule rule2 = businessRuleDAO.lookupBusinessRuleID("2");
        RuleSet rs2 = grs.parse(rule2);
        System.out.println("Rule set2:\n" + rs2.getContent());
        String rulesetUuid2 = droolsRepository.createRuleSet(rs2);
        rule2.setCompiledID(rulesetUuid2);
        droolsRepository.loadRuleSet(rulesetUuid2);
        em.merge(rule2);
        

        FunctionalBusinessRule rule3 = businessRuleDAO.lookupBusinessRuleID("3");
        RuleSet rs3 = grs.parse(rule3);
        System.out.println("Rule set3:\n" + rs3.getContent());
        String rulesetUuid3 = droolsRepository.createRuleSet(rs3);
        rule3.setCompiledID(rulesetUuid3);
        droolsRepository.loadRuleSet(rulesetUuid3);
        em.merge(rule3);

        FunctionalBusinessRule rule4 = businessRuleDAO.lookupBusinessRuleID("4");
        RuleSet rs4 = grs.parse(rule4);
        System.out.println("Rule set4:\n" + rs4.getContent());
        String rulesetUuid4 = droolsRepository.createRuleSet(rs4);
        rule4.setCompiledID(rulesetUuid4);
        droolsRepository.loadRuleSet(rulesetUuid4);
        em.merge(rule4);        
    }

    /**
     * Deletes 4 demo business rules from BRMS database.
     */
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
        System.out.println("Setting EM");
        this.em = em;
    }

    /**
     * @return the droolsRepository
     */
    public RuleEngineRepository getDroolsRepository() {
        return droolsRepository;
    }

    /**
     * @param droolsRepository
     *            the droolsRepository to set
     */
    public void setDroolsRepository(RuleEngineRepository droolsRepository) {
        System.out.println("Setting DRepo");
        this.droolsRepository = droolsRepository;
    }
}

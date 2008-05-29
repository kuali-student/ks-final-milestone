/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.BRMSCore.util;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.brms.repository.RuleEngineRepository;
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

    @Autowired
    private FunctionalBusinessRuleManagementService metadata;

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
        em.flush();
    }

    /**
     * Compiles 4 demo business rules and inserts them into Drools repository.
     */
    public final void compileDroolsRule() throws Exception {

        FunctionalBusinessRule rule1 = businessRuleDAO.lookupBusinessRuleID("1");
        GenerateRuleSet grs1 = metadata.buildRuleSet(rule1);
        String rulesetUuid1 = droolsRepository.createRuleSet(grs1.getRuleSet());
        rule1.setCompiledRuleID(rulesetUuid1);
        droolsRepository.loadRuleSet(rulesetUuid1);
        em.merge(rule1);

        System.out.println("Rule set1:\n" + grs1.getRuleSet().getContent());

        FunctionalBusinessRule rule2 = businessRuleDAO.lookupBusinessRuleID("2");
        GenerateRuleSet grs2 = metadata.buildRuleSet(rule2);
        String rulesetUuid2 = droolsRepository.createRuleSet(grs2.getRuleSet());
        rule2.setCompiledRuleID(rulesetUuid2);
        droolsRepository.loadRuleSet(rulesetUuid2);
        em.merge(rule2);

        System.out.println("Rule set2:\n" + grs2.getRuleSet().getContent());

        FunctionalBusinessRule rule3 = businessRuleDAO.lookupBusinessRuleID("3");
        GenerateRuleSet grs3 = metadata.buildRuleSet(rule3);
        String rulesetUuid3 = droolsRepository.createRuleSet(grs3.getRuleSet());
        rule3.setCompiledRuleID(rulesetUuid3);
        droolsRepository.loadRuleSet(rulesetUuid3);
        em.merge(rule3);

        System.out.println("Rule set3:\n" + grs3.getRuleSet().getContent());

        FunctionalBusinessRule rule4 = businessRuleDAO.lookupBusinessRuleID("4");
        GenerateRuleSet grs4 = metadata.buildRuleSet(rule4);
        String rulesetUuid4 = droolsRepository.createRuleSet(grs4.getRuleSet());
        rule4.setCompiledRuleID(rulesetUuid4);
        droolsRepository.loadRuleSet(rulesetUuid4);
        em.merge(rule4);

        System.out.println("Rule set4:\n" + grs4.getRuleSet().getContent());

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

    /**
     * @return the metadata
     */
    public FunctionalBusinessRuleManagementService getMetadata() {
        return metadata;
    }

    /**
     * @param metadata
     *            the metadata to set
     */
    public void setMetadata(FunctionalBusinessRuleManagementService metadata) {
        this.metadata = metadata;
    }

}

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.brms.translators.drools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.rules.brms.repository.rule.RuleSet;
import org.kuali.student.rules.internal.common.entity.BusinessRule;
import org.kuali.student.rules.internal.common.entity.BusinessRuleContainer;
import org.kuali.student.rules.internal.common.entity.BusinessRuleInfo;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.LeftHandSide;
import org.kuali.student.rules.internal.common.entity.Operator;
import org.kuali.student.rules.internal.common.entity.RightHandSide;
import org.kuali.student.rules.internal.common.entity.RuleElement;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.RuleProposition;
import org.kuali.student.rules.internal.common.entity.YieldValueFunction;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.entity.BusinessRuleInfo.Status;
import org.kuali.student.rules.internal.common.facts.CourseEnrollmentRequest;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.util.FactContainer;

public class GenerateRuleSetTest {

    private final GenerateRuleSet generateRuleSet = GenerateRuleSet.getInstance();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private BusinessRule getBusinessRule() throws Exception {
        int ordinalPosition = 0;

        BusinessRuleInfo ruleInfo = new BusinessRuleInfo("Tom Smith", new Date(), "", null, new Date(), new Date(),
                "1.1", Status.PUBLISHED);
        // create basic rule structure
        BusinessRule businessRule = new BusinessRule("CPR 201",
                "enrollment co-requisites for Intermediate CPR 201", "Rule 1 Success Message",
                "Rule 1 Failure Message", "1", null, ruleInfo, "course_co_req", "CPR 201");

        // 1 of CPR 101
        RuleProposition ruleProp = getRuleProposition("CPR101");
        RuleElement ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", ruleProp);
        businessRule.addRuleElement(ruleElement);

        return businessRule;
    }

    private RuleProposition getRuleProposition(String criteria) {
        // E.g. 1 of CPR 101
        YieldValueFunction yieldValueFunction = new YieldValueFunction("1", YieldValueFunctionType.INTERSECTION);
        LeftHandSide leftSide = new LeftHandSide(criteria, yieldValueFunction);
        Operator operator = new Operator(ComparisonOperator.EQUAL_TO);
        RightHandSide rightSide = new RightHandSide("1");
        RuleProposition ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);

        return ruleProp;
    }

    private static RuleBase getRuleBase(String source) throws Exception {
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl(new StringReader(source));

        Package pkg = builder.getPackage();

        // Add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(pkg);
        return ruleBase;
    }

    private CourseEnrollmentRequest getCourseEnrollmentRequest(String anchorId, String luiIds) {
        CourseEnrollmentRequest request = new CourseEnrollmentRequest(anchorId);
        Set<String> luiIdSet = new HashSet<String>(Arrays.asList(luiIds.split(",")));
        request.setLuiIds(luiIdSet);
        return request;
    }

    // private void executeRule( String source, CourseEnrollmentRequest request, PropositionContainer prop ) throws Exception
    // {
    private void executeRule(String source, FactContainer facts) throws Exception {
        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase(source).newStatefulSession();
        // workingMemory.insert( request );
        // workingMemory.insert( prop );
        workingMemory.insert(facts);
        workingMemory.fireAllRules();
    }

    @Test
    public void testParseBusinessRule() throws Exception {
        // Generate Drools rule set source code
        BusinessRuleContainer container = new BusinessRuleContainer("course.co.req", "Cource Co-Requisites");
        BusinessRule businessRule = getBusinessRule();
        container.addBusinessRule(businessRule);

        RuleSet ruleSet = this.generateRuleSet.parse(container);
        assertNotNull(ruleSet);

        // Rule set source code
        String source = ruleSet.getContent();

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(businessRule.getName(), "CPR101,MATH102");
        FactContainer facts = new FactContainer(businessRule.getName(), request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_OneProposition() throws Exception {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR 101
        propositionMap.put("A", getRuleProposition("CPR101"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                                      "A", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_TwoProposition_AandB() throws Exception {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR 101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of CPR 101
        propositionMap.put("B", getRuleProposition("MATH102"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                                      "A*B", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_TwoProposition_AorB() throws Exception {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR 101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of CPR 101
        propositionMap.put("B", getRuleProposition("MATH102"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                                      "A+B", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();
        
        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_ThreeProposition_AandBorC() throws Exception {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                                      "(A*B)+C", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    /**
     * TODO: Drools Bug - Test case will fail.
     */
    @Test
    @Ignore
    public void testParseRuleSet_ThreeProposition_AorBandC() throws Exception {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                                      "(A+B)*C", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AorBandCorD() throws Exception {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));
        // 1 of CHEM102
        propositionMap.put("D", getRuleProposition("CHEM102"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                                      "(A+B)*(C+D)", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101,CHEM102");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AandBorCandD() throws Exception {
        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));
        // 1 of CHEM102
        propositionMap.put("D", getRuleProposition("CHEM102"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                                      "(A*B)+(C*D)", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101,CHEM102");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }
}

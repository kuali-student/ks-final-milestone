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
package org.kuali.student.rules.brms.drools.translator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import org.kuali.student.rules.brms.translators.drools.GenerateRuleSet;
import org.kuali.student.rules.common.entity.ComparisonOperator;
import org.kuali.student.rules.common.entity.ComputationAssistant;
import org.kuali.student.rules.common.entity.FunctionalBusinessRule;
import org.kuali.student.rules.common.entity.FunctionalBusinessRuleContainer;
import org.kuali.student.rules.common.entity.LeftHandSide;
import org.kuali.student.rules.common.entity.Operator;
import org.kuali.student.rules.common.entity.RightHandSide;
import org.kuali.student.rules.common.entity.RuleElement;
import org.kuali.student.rules.common.entity.RuleElementType;
import org.kuali.student.rules.common.entity.RuleMetaData;
import org.kuali.student.rules.common.entity.RuleProposition;
import org.kuali.student.rules.common.entity.ValueType;
import org.kuali.student.rules.common.entity.YieldValueFunction;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.statement.PropositionContainer;
import org.kuali.student.rules.util.FactContainer;


public class GenerateRuleSetTest {

    private final String FACT_CONTAINER = "AcademicRecord";
    private GenerateRuleSet generateRuleSet = GenerateRuleSet.getInstance();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    private FunctionalBusinessRule getFunctionalBusinessRule() throws Exception {
        int ordinalPosition = 1;

        RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1",
        "active");
        // create basic rule structure
        FunctionalBusinessRule businessRule = new FunctionalBusinessRule("Intermediate_CPR",
                "enrollment co-requisites for Intermediate CPR 201", "Rule 1 Success Message",
                "Rule 1 Failure Message", "1", null, metaData, "Student Enrolls in Course", "course_co_req", "course",
                "CPR 201");

        // 1 of CPR 101
        RuleProposition ruleProp = getRuleProposition("CPR101");
        RuleElement ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        businessRule.addRuleElement(ruleElement);
        
        return businessRule;
    }
    
    private RuleProposition getRuleProposition(String criteria) {
        // E.g. 1 of CPR 101
        ComputationAssistant compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        LeftHandSide leftSide = new LeftHandSide(criteria, FACT_CONTAINER, compAssistant, ValueType.NUMBER);
        Operator operator = new Operator(ComparisonOperator.EQUAL_TO);
        RightHandSide rightSide = new RightHandSide("java.lang.Integer", "1");
        RuleProposition ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        
        return ruleProp;
    }

    private static RuleBase getRuleBase(String source) throws Exception {
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl( new StringReader( source ) );

        Package pkg = builder.getPackage();
        
        // Add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage( pkg );
        return ruleBase;
    }
    
    private CourseEnrollmentRequest getCourseEnrollmentRequest(String luiIds) {
        CourseEnrollmentRequest request = new CourseEnrollmentRequest();
        Set<String> luiIdSet = new HashSet<String>(Arrays.asList(luiIds.split(",")));
        request.setLuiIds( luiIdSet );
        return request;
    }

    //private void executeRule( String source, CourseEnrollmentRequest request, PropositionContainer prop ) throws Exception {
    private void executeRule( String source, FactContainer facts ) throws Exception {
        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase( source ).newStatefulSession();
        //workingMemory.insert( request );
        //workingMemory.insert( prop );
        workingMemory.insert( facts );
        workingMemory.fireAllRules();
    }
    
    @Test
    public void testParseFunctionalBusinessRule() throws Exception {
        // Generate Drools rule set source code
        FunctionalBusinessRuleContainer container = new FunctionalBusinessRuleContainer("course.co.req", "Cource Co-Requisites");
        FunctionalBusinessRule businessRule = getFunctionalBusinessRule();
        container.addFunctionalBusinessRule(businessRule);

        RuleSet ruleSet = this.generateRuleSet.parse(container);
        assertNotNull( ruleSet );

        // Collection of Propositions
        PropositionContainer prop = new PropositionContainer();

        // Rule set source code
        String source = ruleSet.getContent();
        System.out.println( "\n\n"+source + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102");
        FactContainer facts = new FactContainer(businessRule.getName(), request, prop);

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_OneProposition() throws Exception {
        PropositionContainer prop = new PropositionContainer();

        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR 101
        propositionMap.put("A", getRuleProposition("CPR101"));
        
        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName", "A", propositionMap);
        System.out.println( "\n\n"+ruleSet.getContent() + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102");
        FactContainer facts = new FactContainer( "TestRuleName", request, prop );

        executeRule( ruleSet.getContent(), facts );
        assertTrue( prop.getRuleResult() );
    }

    @Test
    public void testParseRuleSet_TwoProposition_AandB() throws Exception {
        PropositionContainer prop = new PropositionContainer();

        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR 101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of CPR 101
        propositionMap.put("B", getRuleProposition("MATH102"));
        
        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName", "A*B", propositionMap);
        //System.out.println( "\n\n"+ruleSet.getContent() + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer( "TestRuleName", request, prop );

        executeRule( ruleSet.getContent(), facts );
        assertTrue( prop.getRuleResult() );
    }

    @Test
    public void testParseRuleSet_TwoProposition_AorB() throws Exception {
        PropositionContainer prop = new PropositionContainer();

        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR 101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of CPR 101
        propositionMap.put("B", getRuleProposition("MATH102"));
        
        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName", "A+B", propositionMap);
        //System.out.println( "\n\n"+ruleSet.getContent() + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer( "TestRuleName", request, prop );

        executeRule( ruleSet.getContent(), facts );
        assertTrue( prop.getRuleResult() );
    }

    @Test
    public void testParseRuleSet_ThreeProposition_AandBorC() throws Exception {
        PropositionContainer prop = new PropositionContainer();

        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));
        
        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName", "(A*B)+C", propositionMap);
        //System.out.println( "\n\n"+ruleSet.getContent() + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer( "TestRuleName", request, prop );

        executeRule( ruleSet.getContent(), facts );
        assertTrue(prop.getRuleResult());
    }

    /**
     * TODO: Drools Bug - Test case will fail.
     */
    @Test
    @Ignore
    public void testParseRuleSet_ThreeProposition_AorBandC() throws Exception {
        PropositionContainer prop = new PropositionContainer();

        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));
        
        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName", "(A+B)*C", propositionMap);
        //System.out.println( "\n\n"+ruleSet.getContent() + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102,CHEM101");
        FactContainer facts = new FactContainer( "TestRuleName", request, prop );

        executeRule( ruleSet.getContent(), facts );
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AorBandCorD() throws Exception {
        PropositionContainer prop = new PropositionContainer();

        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));
        // 1 of CHEM102
        propositionMap.put("D", getRuleProposition("CHEM102"));
        
        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName", "(A+B)*(C+D)", propositionMap);
        //System.out.println( "\n\n"+ruleSet.getContent() + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102,CHEM101,CHEM102");
        FactContainer facts = new FactContainer( "TestRuleName", request, prop );

        executeRule( ruleSet.getContent(), facts );
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AandBorCandD() throws Exception {
        PropositionContainer prop = new PropositionContainer();

        Map<String, RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
        // 1 of CPR101
        propositionMap.put("A", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("B", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("C", getRuleProposition("CHEM101"));
        // 1 of CHEM102
        propositionMap.put("D", getRuleProposition("CHEM102"));

        RuleSet ruleSet = GenerateRuleSet.getInstance().createRuleSet("TestPackageName", "A package", "TestRuleName", "(A*B)+(C*D)", propositionMap);
        System.out.println( "\n\n"+ruleSet.getContent() + "\n\n");

        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest("CPR101,MATH102,CHEM101,CHEM102");
        FactContainer facts = new FactContainer( "TestRuleName", request, prop );

        executeRule( ruleSet.getContent(), facts );
        assertTrue(prop.getRuleResult());
    }
}

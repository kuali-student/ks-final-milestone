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
package org.kuali.student.rules.translators.drools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
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
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.facts.CourseEnrollmentRequest;
import org.kuali.student.rules.internal.common.statement.Proposition;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.drools.GenerateRuleSet;
import org.kuali.student.rules.util.FactContainer;

public class GenerateRuleSetTest {

    private final GenerateRuleSet generateRuleSet = GenerateRuleSet.getInstance();
    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private BusinessRuleInfoDTO getBusinessRule() throws Exception {
        BusinessRuleInfoDTO businessRule = dtoFactory.createBusinessRuleInfoDTO(
        		"XXXXX - CPR 101 - XXXXX", 
        		"1", 
        		"Rule 1 Success Message", 
        		"Rule 1 Failure Message", 
        		"kuali.businessrule.typekey.course.corequisites", 
        		getRuleElementList("CPR101"), 
        		"kuali.anchor.typekey.course", 
        		"CPR101");
        return businessRule;
    }
    
    private List<RuleElementDTO> getRuleElementList(String criteria) {
    	List<RuleElementDTO> list = new ArrayList<RuleElementDTO>();
        RuleElementDTO re1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(criteria));
        list.add(re1);
        return list;
    }

    private RulePropositionDTO getRuleProposition(String criteria) {
        // E.g. 1 of CPR 101
    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, "INTERSECTION");
    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO("1");
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		"co-requisites", java.lang.Integer.class.getName(), 
        		ComparisonOperator.EQUAL_TO.toString(), leftSide, rightSide);
        
        FactStructureDTO factStructure = new FactStructureDTO();
        factStructure.setDataType(java.util.Set.class.getName());
        factStructure.setFactStructureId("kuali.student.criteria.intersection.id");
        factStructure.setAnchorFlag(false);
        Map<String,String> definitionVariableMap = new HashMap<String,String>();
        definitionVariableMap.put("kuali.student.criteria.key", criteria);
        factStructure.setDefinitionVariableList(definitionVariableMap);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(factStructure);
        yieldValueFunction.setFactStructureList(factStructureList);
        
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
        BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        BusinessRuleInfoDTO businessRule = getBusinessRule();
        container.getBusinessRules().add(businessRule);

        RuleSet ruleSet = this.generateRuleSet.parse(container);
        assertNotNull(ruleSet);

        // Rule set source code
        String source = ruleSet.getContent();
//System.out.println("\n\n"+source+"\n\n");
        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(businessRule.getAnchorValue(), "CPR101,MATH102");
        FactContainer facts = new FactContainer(businessRule.getAnchorValue(), request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_OneProposition() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101"));

        RuleSet ruleSet = this.generateRuleSet.createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                             //"A", propositionMap);
															 "P1", propositionMap);
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
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101"));
        // 1 of CPR 101
        propositionMap.put("P2", getRuleProposition("MATH102"));

        RuleSet ruleSet = this.generateRuleSet.createRuleSet("TestPackageName", "A package", "TestRuleName",
											                 //"A*B", propositionMap);
                                                             "P1*P2", propositionMap);
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
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101"));
        // 1 of CPR 101
        propositionMap.put("P2", getRuleProposition("MATH102"));

        RuleSet ruleSet = this.generateRuleSet.createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                             "P1+P2", propositionMap);
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
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101"));

        RuleSet ruleSet = this.generateRuleSet.createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                             "(P1*P2)+P3", propositionMap);
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
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101"));

        RuleSet ruleSet = this.generateRuleSet.createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                             "(P1+P2)*P3", propositionMap);
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
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101"));
        // 1 of CHEM102
        propositionMap.put("P4", getRuleProposition("CHEM102"));

        RuleSet ruleSet = this.generateRuleSet.createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                             "(P1+P2)*(P3+P4)", propositionMap);
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
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102"));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101"));
        // 1 of CHEM102
        propositionMap.put("P4", getRuleProposition("CHEM102"));

        RuleSet ruleSet = this.generateRuleSet.createRuleSet("TestPackageName", "A package", "TestRuleName",
                                                             "(P1*P2)+(P3*P4)", propositionMap);
        // Get facts
        String id = "TestRuleName";
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101,CHEM102");
        FactContainer facts = new FactContainer(id, request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    private RuleElementDTO getRule1() {
    	// Rule 1
    	YieldValueFunctionDTO yieldValueFunction = new YieldValueFunctionDTO();
        yieldValueFunction.setYieldValueFunctionType("INTERSECTION");
        
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        leftHandSide.setYieldValueFunction(yieldValueFunction);
       
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("1");
        
        RulePropositionDTO ruleProposition = new RulePropositionDTO();
        ruleProposition.setName("Must have 1 of CPR 101");
        ruleProposition.setDescription("Course intersection");
        ruleProposition.setLeftHandSide(leftHandSide);
        ruleProposition.setRightHandSide(rightHandSide);
        ruleProposition.setComparisonDataType(java.lang.Integer.class.getName());
        ruleProposition.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

    	RuleElementDTO re = new RuleElementDTO();
        re.setName("Course intersection - 1 of CPR 101");
        re.setDescription("Must have 1 of CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private RuleElementDTO getRule2() {
    	// Rule 1
    	YieldValueFunctionDTO yieldValueFunction = new YieldValueFunctionDTO();
        yieldValueFunction.setYieldValueFunctionType("SUM");
        
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        leftHandSide.setYieldValueFunction(yieldValueFunction);
       
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("10.0");
        
        RulePropositionDTO ruleProposition = new RulePropositionDTO();
        ruleProposition.setName("Course credit summation");
        ruleProposition.setDescription("Course credit summation");
        ruleProposition.setLeftHandSide(leftHandSide);
        ruleProposition.setRightHandSide(rightHandSide);
        ruleProposition.setComparisonDataType(java.lang.Double.class.getName());
        ruleProposition.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

    	RuleElementDTO re = new RuleElementDTO();
        re.setName("Sum of credits");
        re.setDescription("Pre req check for CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setOperation("AND");
        
        return re;
    }
    
    @Ignore
    @Test
    public void testComplexRule() throws Exception {
        List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        ruleElementList.add(getRule1());
        ruleElementList.add(getAndOperator());
        ruleElementList.add(getRule2());

        BusinessRuleInfoDTO bri = new BusinessRuleInfoDTO();
    	bri.setName("Rule-1");
    	bri.setDescription("Some business rule");
    	bri.setSuccessMessage("Success message");
    	bri.setFailureMessage("Failure message");
    	bri.setBusinessRuleId("1");
    	bri.setBusinessRuleTypeKey("kuali.student.businessrule.typekey.course.corequisites");
    	bri.setAnchorTypeKey("kuali.student.lui.course.id");
    	bri.setAnchorValue("CPR101");
    	bri.setRuleElementList(ruleElementList);

        
    	// Facts - 1 of CPR101
        FactStructureDTO fs1 = new FactStructureDTO();
        fs1.setDataType(java.util.Set.class.getName());
        fs1.setFactStructureId("kuali.student.criteria.intersection.id");
        fs1.setAnchorFlag(false);
        Map<String,String> definitionVariableMap = new HashMap<String,String>();
        // 1 of CPR101
        definitionVariableMap.put("kuali.student.criteria.key", "CPR101");
        fs1.setDefinitionVariableList(definitionVariableMap);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(fs1);

        
        // Facts - Sum of credit > 10.0
        FactStructureDTO fs2 = new FactStructureDTO();
        fs2.setDataType(java.lang.Integer.class.getName());
        fs2.setFactStructureId("kuali.student.criteria.summation.id");
        fs2.setAnchorFlag(false);
        Map<String,String> executionVariableMap = new HashMap<String,String>();
        executionVariableMap.put("kuali.student.criteria.person.id", null);
        fs2.setExecutionVariableList(executionVariableMap);
        
        factStructureList.add(fs2);

    
        // Parse
        BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(bri);

        RuleSet ruleSet = this.generateRuleSet.parse(container);
        assertNotNull(ruleSet);

        // Rule set source code
        String source = ruleSet.getContent();
System.out.println("\n\n"+source+"\n\n");
        // Get facts
        CourseEnrollmentRequest request = getCourseEnrollmentRequest(bri.getAnchorValue(), "CPR101,MATH102");
        FactContainer facts = new FactContainer(bri.getAnchorValue(), request);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

}

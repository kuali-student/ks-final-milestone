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

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;
import org.kuali.student.rules.translators.util.Constants;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;

public class RuleSetTranslatorPropositionTest {

    private final RuleSetTranslatorDroolsImpl generateRuleSet = new RuleSetTranslatorDroolsImpl();
    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private RulePropositionDTO getRuleProposition(String criteria, String factKey) {
    	return getRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"1", 
    			ComparisonOperator.EQUAL_TO.toString(), 
    			criteria, 
    			factKey);
    }
    
    private RulePropositionDTO getRuleProposition(
    		String yieldValueFunctionType,
    		String expectedValue,
    		String comparisonOperator,
    		String criteria, 
    		String factKey) {
    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, yieldValueFunctionType);
    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		"co-requisites", java.lang.Integer.class.getName(), 
        		comparisonOperator, leftSide, rightSide);
        
        FactStructureDTO factStructure = new FactStructureDTO();
        factStructure.setDataType(java.util.Set.class.getName());
        factStructure.setFactStructureId(Constants.FACT_STRUCTURE_ID);
        factStructure.setAnchorFlag(false);

        Map<String,String> definitionVariableMap = new HashMap<String,String>();
        definitionVariableMap.put(Constants.DEF_CRITERIA_KEY, criteria);
        factStructure.setDefinitionVariableList(definitionVariableMap);
        
        Map<String,String> executionVariableMap = new HashMap<String,String>();
        executionVariableMap.put(Constants.EXE_FACT_KEY, factKey);
        factStructure.setExecutionVariableList(executionVariableMap);

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

    private void executeRule(String source, FactContainer fact) throws Exception {
        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase(source).newStatefulSession();
        workingMemory.insert(new CurrentDateTime());
    	workingMemory.insert(fact);
        workingMemory.fireAllRules();
    }

    private Set<String> createSet(String list) {
        Set<String> set = new HashSet<String>();
        for( String s : list.split(",") ) {
        	set.add(s.trim());
        }
        return set;
    }
    
    private List<BigDecimal> createList(String list) {
    	List<BigDecimal> set = new ArrayList<BigDecimal>();
        for( String s : list.split(",") ) {
        	set.add(new BigDecimal(s.trim()));
        }
        return set;
    }
    
    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
    }

    private RuleSet createRuleSet(String anchor, String packageName, String description, 
			 String ruleName, String functionString,
			 Map<String, RulePropositionDTO> propositionMap) {
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	RuleSet ruleSet = this.generateRuleSet.createRuleSet(anchor, 
        		"TestPackageName", "A package", "TestRuleName", "P1", propositionMap,
        		effectiveStartTime, effectiveEndTime);

    	return ruleSet;
    }
    
    @Test
    public void testParseRuleSet_OneProposition() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101","courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "P1", propositionMap);
        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_TwoProposition_AandB() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101","courseKey"));
        // 1 of CPR 101
        propositionMap.put("P2", getRuleProposition("MATH102","courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "P1*P2", propositionMap);
        // Get facts
        //CourseEnrollmentRequest request = getCourseEnrollmentRequest(id, "CPR101,MATH102,CHEM101");
        //FactContainer facts = new FactContainer(id, request);
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_TwoProposition_AorB() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR 101
        propositionMap.put("P1", getRuleProposition("CPR101","courseKey"));
        // 1 of CPR 101
        propositionMap.put("P2", getRuleProposition("MATH102","courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId,  "TestPackageName", 
        		"A package", "TestRuleName", "P1+P2", propositionMap);
        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();
        
        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_ThreeProposition_AandBorC() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101","courseKey"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102","courseKey"));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101","courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "(P1*P2)+P3", propositionMap);
        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

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
        propositionMap.put("P1", getRuleProposition("CPR101","courseKey"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102","courseKey"));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101","courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "(P1+P2)*P3", propositionMap);
        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AorBandCorD() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition("CPR101","courseKey"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition("MATH102","courseKey"));
        // 1 of CHEM101
        propositionMap.put("P3", getRuleProposition("CHEM101","courseKey"));
        // 1 of CHEM102
        propositionMap.put("P4", getRuleProposition("CHEM999","courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", "A package", "TestRuleName", "(P1+P2)*(P3+P4)", propositionMap);
        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM101,CHEM102");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts =  new FactContainer(anchorId, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AandBorC_IntersectionSumAverage() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition(
        		YieldValueFunctionType.INTERSECTION.toString(),
        		"1",
        		ComparisonOperator.EQUAL_TO.toString(),
        		"CPR101",
        		"intersection.courseKey"));
        // Sum of credits = 12.0
        propositionMap.put("P2", getRuleProposition(
        		YieldValueFunctionType.SUM.toString(),
        		"12.0",
        		ComparisonOperator.EQUAL_TO.toString(),
        		null,
        		"sum.courseKey"));
        // Average >= 75.0
        propositionMap.put("P3", getRuleProposition(
        		YieldValueFunctionType.AVERAGE.toString(),
        		"75.0",
        		ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
        		null,
        		"average.courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "(P1*P2)+P3", propositionMap);
        Map<String,Object> factMap = new HashMap<String,Object>();
        
        // Get facts for intersection
        Set<String> courseSetIntersection = createSet("CPR101,MATH102,CHEM101,CHEM102");
        factMap.put("intersection.courseKey", courseSetIntersection);

        // Get facts for sum
        List<BigDecimal> courseSetSum = createList("3.0,6.0,3.0");
        factMap.put("sum.courseKey", courseSetSum);

        // Get facts for average
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        factMap.put("average.courseKey", courseSet);

        FactContainer facts =  new FactContainer(anchorId, factMap);
        
        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseRuleSet_FourProposition_AandBandCandDandEandF_IntersectionSumAverage() throws Exception {
        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        // 1 of CPR101
        propositionMap.put("P1", getRuleProposition(
        		YieldValueFunctionType.INTERSECTION.toString(),
        		"1",
        		ComparisonOperator.EQUAL_TO.toString(),
        		"CPR101",
        		"intersection.courseKey"));
        // 1 of MATH102
        propositionMap.put("P2", getRuleProposition(
        		YieldValueFunctionType.INTERSECTION.toString(),
        		"1",
        		ComparisonOperator.EQUAL_TO.toString(),
        		"MATH102",
        		"intersection.courseKey"));

        // Sum of credits = 12.0
        propositionMap.put("P3", getRuleProposition(
        		YieldValueFunctionType.SUM.toString(),
        		"12.0",
        		ComparisonOperator.EQUAL_TO.toString(),
        		null,
        		"sum.courseKey"));
        // Sum of credits >= 12.0
        propositionMap.put("P4", getRuleProposition(
        		YieldValueFunctionType.SUM.toString(),
        		"9.0",
        		ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
        		null,
        		"sum.courseKey"));

        // Average > 75.0
        propositionMap.put("P5", getRuleProposition(
        		YieldValueFunctionType.AVERAGE.toString(),
        		"75.0",
        		ComparisonOperator.GREATER_THAN.toString(),
        		null,
        		"average.courseKey"));
        // Average >= 80.0
        propositionMap.put("P6", getRuleProposition(
        		YieldValueFunctionType.AVERAGE.toString(),
        		"80.0",
        		ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
        		null,
        		"average.courseKey"));

        String anchorId = "TestRuleName";
        RuleSet ruleSet = createRuleSet(anchorId, "TestPackageName", 
        		"A package", "TestRuleName", "P1*P2*P3*P4*P5*P6", propositionMap);
        Map<String,Object> factMap = new HashMap<String,Object>();
        
        // Get facts for intersection
        Set<String> courseSetIntersection = createSet("CPR101,MATH102,CHEM101,CHEM102");
        factMap.put("intersection.courseKey", courseSetIntersection);

        // Get facts for sum
        List<BigDecimal> courseSetSum = createList("3.0,6.0,3.0");
        factMap.put("sum.courseKey", courseSetSum);

        // Get facts for average
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        factMap.put("average.courseKey", courseSet);

        FactContainer facts =  new FactContainer(anchorId, factMap);
        
        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        executeRule(ruleSet.getContent(), facts);
        assertTrue(prop.getRuleResult());
    }

}

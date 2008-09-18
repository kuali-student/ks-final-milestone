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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
import org.kuali.student.rules.repository.exceptions.GenerateRuleSetException;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.drools.GenerateRuleSet;
import org.kuali.student.rules.translators.util.Constants;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;

public class GenerateRuleSetTest {

    private final GenerateRuleSet generateRuleSet = GenerateRuleSet.getInstance();
    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private BusinessRuleInfoDTO getBusinessRule(String yieldValueFunctionType, 
    		String criteria, String comparisonOperator, String expectedValue, 
    		String factKey, String anchorValue) throws Exception {
        BusinessRuleInfoDTO businessRule = dtoFactory.createBusinessRuleInfoDTO(
        		"CPR 101", 
        		"1", 
        		"Rule 1 Success Message", 
        		"Rule 1 Failure Message", 
        		"kuali.businessrule.typekey.course.corequisites", 
        		getRuleElementList(yieldValueFunctionType, criteria, comparisonOperator, expectedValue, factKey), 
        		"kuali.anchor.typekey.course", 
        		anchorValue);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	businessRule.setEffectiveStartTime(effectiveStartTime);
    	businessRule.setEffectiveEndTime(effectiveEndTime);

        return businessRule;
    }
    
    private List<RuleElementDTO> getRuleElementList(String yieldValueFunctionType, 
    		String criteria, String comparisonOperator, String expectedValue, String factKey) {
    	List<RuleElementDTO> list = new ArrayList<RuleElementDTO>();
        RuleElementDTO re1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(yieldValueFunctionType, criteria, comparisonOperator, expectedValue, factKey));
        list.add(re1);
        return list;
    }

    private RulePropositionDTO getRuleProposition(String yieldValueFunctionType, 
    		String criteria, String comparisonOperator, String expectedValue, String factKey) {
        // E.g. 1 of CPR 101
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

    private void executeRule(String source, FactContainer facts) throws Exception {
        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase(source).newStatefulSession();
        workingMemory.insert(new CurrentDateTime());
        workingMemory.insert(facts);
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
    
    private BusinessRuleInfoDTO getBusinessRule(String yieldValueFunctionType,
    		String criteria, String comparisonOperator, 
    		String expectedValue, String anchorValue) throws Exception {
        // Generate Drools rule set source code
        BusinessRuleInfoDTO businessRule = getBusinessRule(
        		yieldValueFunctionType, 
        		criteria, 
        		comparisonOperator,
        		expectedValue, 
        		"courseKey", 
        		anchorValue);
        return businessRule;
    }

    private String getRuleSourceCode(String yieldValueFunctionType,
    		String criteria, String comparisonOperator, 
    		String expectedValue, String anchorValue) throws Exception {
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
        		yieldValueFunctionType, 
        		criteria, 
        		comparisonOperator,
        		expectedValue, 
        		anchorValue);
    	BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet = this.generateRuleSet.parse(container);
        // Rule set source code
        return ruleSet.getContent();
    }
    
    @Test
    public void testParseBusinessRule_Intersection() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			anchorValue);

        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Intersection_NullMap() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			anchorValue);

        // Get facts - null fact
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", null);
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertFalse(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Intersection_EmptyMap() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			anchorValue);

        // Get facts - empty fact
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", new HashSet<String>());
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertFalse(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Sum() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.SUM.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"12.0",
    			anchorValue);

        // Get facts
        List<BigDecimal> courseSet = createList("3.0,6.0,3.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts = new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Sum_NullMap() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.SUM.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"12.0",
    			anchorValue);

        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put("courseKey", null);
        FactContainer facts = new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertFalse(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Sum_EmptyMap() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.SUM.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"12.0",
    			anchorValue);

        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put("courseKey", new ArrayList<BigDecimal>());
        FactContainer facts = new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertFalse(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Average() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);

        // Get facts
//        Set<SumFact> courseSet = new HashSet<SumFact>(2);
//        courseSet.add( new SumFact("CPR101", new BigDecimal(85.0d)));
//        courseSet.add(new SumFact("MATH102", new BigDecimal(75.0d)));

        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts = new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Average_NullMap() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);

        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put("courseKey", null);
        FactContainer facts = new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        try {
        	executeRule(source, facts);
            fail("Executing rule should have throw an IllegalArgumentException since courseSet=null");
        } catch(Exception e) {
        	assertTrue(true);
        }
    }

    @Test
    public void testParseBusinessRule_Average_EmptyMap() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);

        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put("courseKey", new ArrayList<BigDecimal>());
        FactContainer facts = new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        try {
        	executeRule(source, facts);
            fail("Executing rule should have throw an IllegalArgumentException since courseSet is empty");
        } catch(Exception e) {
        	assertTrue(true);
        }
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
    }

    @Test
    public void testParseBusinessRule_Average_EffectiveExpiryDate() throws Exception {
    	String anchorValue = "CPR101";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);
    	
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	businessRule.setEffectiveStartTime(effectiveStartTime);
    	businessRule.setEffectiveEndTime(effectiveEndTime);
    	
    	BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet = this.generateRuleSet.parse(container);
        // Rule set source code
        String source = ruleSet.getContent();

        // Get facts
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer facts = new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Ignore
    @Test
    public void testParseBusinessRule_Average_NoDefinitionKeys() throws Exception {
    	String anchorValue = "CPR101";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);
    	
    	businessRule.getRuleElementList().get(0).getRuleProposition().
    		getLeftHandSide().getYieldValueFunction().
    		getFactStructureList().get(0).getDefinitionVariableList().clear();
    	
    	BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet;
		try {
			ruleSet = this.generateRuleSet.parse(container);
			fail("Should have thrown a GenerateRuleSetException since rule contains no valid definition keys");
		} catch (GenerateRuleSetException e) {
			assertTrue(true);
		}
    }

    @Ignore
    @Test
    public void testParseBusinessRule_Average_InvalidDefinitionKeys() throws Exception {
    	String anchorValue = "CPR101";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);
    	
    	// Remove definition key
    	businessRule.getRuleElementList().get(0).getRuleProposition().
    		getLeftHandSide().getYieldValueFunction().
    		getFactStructureList().get(0).getDefinitionVariableList().remove(Constants.DEF_CRITERIA_KEY);
    	// Add invalid definition key
    	businessRule.getRuleElementList().get(0).getRuleProposition().
			getLeftHandSide().getYieldValueFunction().
			getFactStructureList().get(0).getDefinitionVariableList().put("xxx","xyz");
    	
    	BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
		try {
			this.generateRuleSet.parse(container);
			fail("Should have thrown a GenerateRuleSetException since rule contains no valid definition keys");
		} catch (GenerateRuleSetException e) {
			assertTrue(true);
		}
    }

    @Ignore
    @Test
    public void testParseBusinessRule_Average_InvalidExecutionKeys() throws Exception {
    	String anchorValue = "CPR101";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);

    	// Add invalid execution key
    	businessRule.getRuleElementList().get(0).getRuleProposition().
			getLeftHandSide().getYieldValueFunction().
			getFactStructureList().get(0).getExecutionVariableList().put("xxx","xyz");
    	
    	BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
		try {
			this.generateRuleSet.parse(container);
			fail("Should have thrown a GenerateRuleSetException since rule contains no valid execution keys");
		} catch (GenerateRuleSetException e) {
			assertTrue(true);
		}
    }

    @Ignore
    @Test
    public void testParseBusinessRule_Average_NoExecutionKeys() throws Exception {
    	String anchorValue = "CPR101";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue);
    	
    	businessRule.getRuleElementList().get(0).getRuleProposition().
    		getLeftHandSide().getYieldValueFunction().
    		getFactStructureList().get(0).getExecutionVariableList().clear();
    	
    	BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet;
		try {
			ruleSet = this.generateRuleSet.parse(container);
			fail("Should have thrown a GenerateRuleSetException since rule contains no valid definition keys");
		} catch (GenerateRuleSetException e) {
			assertTrue(true);
		}
    }

    private BusinessRuleInfoDTO createBusinessRule(List<RuleElementDTO> ruleElementList) {
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
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	bri.setEffectiveStartTime(effectiveStartTime);
    	bri.setEffectiveEndTime(effectiveEndTime);
    	return bri;
    }

    private RuleElementDTO createRule1(YieldValueFunctionDTO yieldValueFunction) {
    	// Rule 1
    	//YieldValueFunctionDTO yieldValueFunction = new YieldValueFunctionDTO();
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
        re.setName("course.intersection.1.of.cpr101");
        re.setDescription("Must have 1 of CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private RuleElementDTO createRule2(YieldValueFunctionDTO yieldValueFunction) {
    	// Rule 1
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
        re.setName("course.credits.sum");
        re.setDescription("Pre req check for CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private void createFact1(YieldValueFunctionDTO yieldValueFunction1) {
    	// Facts - Rule 1 - 1 of CPR101
        FactStructureDTO fs1 = new FactStructureDTO();
        fs1.setDataType(java.util.Set.class.getName());
        fs1.setFactStructureId(Constants.FACT_STRUCTURE_ID);
        fs1.setAnchorFlag(false);

        Map<String,String> definitionVariableMap1 = new HashMap<String,String>();
        definitionVariableMap1.put(Constants.DEF_CRITERIA_KEY, "CPR101");
        fs1.setDefinitionVariableList(definitionVariableMap1);

        Map<String,String> executionVariableMap1 = new HashMap<String,String>();
        executionVariableMap1.put(Constants.EXE_FACT_KEY, "intersection.courseSet");
        fs1.setExecutionVariableList(executionVariableMap1);
        
        List<FactStructureDTO> factStructureList1 = new ArrayList<FactStructureDTO>();
        factStructureList1.add(fs1);
        yieldValueFunction1.setFactStructureList(factStructureList1);
    }
    
    private void createFact2(YieldValueFunctionDTO yieldValueFunction2) {
        // Facts - Rule 2 - Sum of credit > 10.0
        FactStructureDTO fs2 = new FactStructureDTO();
        fs2.setDataType(java.math.BigDecimal.class.getName());
        fs2.setFactStructureId(Constants.FACT_STRUCTURE_ID);
        fs2.setAnchorFlag(false);

        // Not need for summation or averages
        //Map<String,String> definitionVariableMap2 = new HashMap<String,String>();
        //definitionVariableMap2.put(Constants.DEF_CRITERIA_KEY, null);
        //fs2.setDefinitionVariableList(definitionVariableMap2);

        Map<String,String> executionVariableMap2 = new HashMap<String,String>();
        executionVariableMap2.put(Constants.EXE_FACT_KEY, "summation.courseSet");
        fs2.setExecutionVariableList(executionVariableMap2);
        
        List<FactStructureDTO> factStructureList2 = new ArrayList<FactStructureDTO>();
        factStructureList2.add(fs2);
        yieldValueFunction2.setFactStructureList(factStructureList2);
    }
    
    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setOperation("AND");
        
        return re;
    }
    
    private Map<String,Object> getFacts() {
		Map<String,Object> factMap = new HashMap<String,Object>();

		Set<String> courseSet1 = createSet("CPR101,MATH102,CHEM100");
		factMap.put("intersection.courseSet", courseSet1);

        List<BigDecimal> courseSet2 = createList("3.0,6.0,3.0");
        factMap.put("summation.courseSet", courseSet2);
        return factMap;
    }
    
    @Test
    public void testComplexRule() throws Exception {
    	// Yield value functions
    	YieldValueFunctionDTO yieldValueFunction1 = new YieldValueFunctionDTO();
    	YieldValueFunctionDTO yieldValueFunction2 = new YieldValueFunctionDTO();

        // Rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        ruleElementList.add(createRule1(yieldValueFunction1));
        ruleElementList.add(getAndOperator());
        ruleElementList.add(createRule2(yieldValueFunction2));

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);

    	// Facts - Rule 1 - 1 of CPR101
    	createFact1(yieldValueFunction1);

        // Facts - Rule 2 - Sum of credit > 10.0
    	createFact2(yieldValueFunction2);

        // Parse and generate functional business rule into Drools rules
        BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(bri);
        RuleSet ruleSet = this.generateRuleSet.parse(container);
        assertNotNull(ruleSet);

        // Rule set source code
        String source = ruleSet.getContent();

		// Get facts
		Map<String,Object> factMap = getFacts();
		FactContainer facts =  new FactContainer(bri.getAnchorValue(), factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

}

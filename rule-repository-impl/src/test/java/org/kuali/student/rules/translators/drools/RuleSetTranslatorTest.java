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
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;

public class RuleSetTranslatorTest {
    private final RuleSetTranslatorDroolsImpl generateRuleSet = new RuleSetTranslatorDroolsImpl();
    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();
    
    private final static String PROPOSITION_NAME = "co-requisites";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private BusinessRuleInfoDTO getBusinessRule(String yieldValueFunctionType, 
    		String criteria, String comparisonOperator, String expectedValue, 
    		String anchorValue, String factKey,
    		String comparisonDataType) throws Exception {
        BusinessRuleInfoDTO businessRule = dtoFactory.createBusinessRuleInfoDTO(
        		"CPR101", 
        		"1", 
        		"Rule 1 Success Message", 
        		"Rule 1 Failure Message", 
        		"kuali.businessrule.typekey.course.corequisites", 
        		getRuleElementList(yieldValueFunctionType, criteria, 
        				comparisonOperator, expectedValue, factKey, comparisonDataType), 
        		"kuali.anchor.typekey.course", 
        		anchorValue);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	businessRule.setEffectiveStartTime(effectiveStartTime);
    	businessRule.setEffectiveEndTime(effectiveEndTime);

        return businessRule;
    }

    private List<RuleElementDTO> getRuleElementList(String yieldValueFunctionType, 
    		String criteria, String comparisonOperator, String expectedValue, String factKey,
    		String comparisonDataType) {
    	List<RuleElementDTO> list = new ArrayList<RuleElementDTO>();
        RuleElementDTO re1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(yieldValueFunctionType, 
        				criteria, comparisonOperator, expectedValue, factKey, comparisonDataType));
        list.add(re1);
        return list;
    }

    private RulePropositionDTO getRuleProposition(String yieldValueFunctionType, 
    		String criteria, String comparisonOperator, String expectedValue, String factKey,
    		String comparisonDataType) {
        // E.g. 1 of CPR 101
    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, yieldValueFunctionType);
    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		PROPOSITION_NAME, java.lang.Integer.class.getName(), 
        		comparisonOperator, leftSide, rightSide);

        ruleProp.setComparisonDataType(comparisonDataType);
        
        FactStructureDTO factStructure = new FactStructureDTO();
        factStructure.setDataType(java.util.Set.class.getName());
        factStructure.setFactStructureId(factKey);
        factStructure.setAnchorFlag(false);

        Map<String,String> definitionVariableMap = new HashMap<String,String>();
        definitionVariableMap.put("some identifier - assume only one key", criteria);
        factStructure.setDefinitionVariableList(definitionVariableMap);

        Map<String,String> executionVariableMap = new HashMap<String,String>();
        //executionVariableMap.put(Constants.EXE_FACT_KEY, factKey);
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
    
    private BusinessRuleInfoDTO getBusinessRuleInfo(String yieldValueFunctionType,
    		String criteria, String comparisonOperator, 
    		String expectedValue, String anchorValue, String factId,
    		String comparisonDataType) throws Exception {
        // Generate Drools rule set source code
        BusinessRuleInfoDTO businessRule = getBusinessRule(
        		yieldValueFunctionType, 
        		criteria, 
        		comparisonOperator,
        		expectedValue, 
        		anchorValue, 
        		factId,
        		comparisonDataType);
        return businessRule;
    }

    private String getRuleSourceCode(String yieldValueFunctionType,
    		String criteria, String comparisonOperator, 
    		String expectedValue, String anchorValue, String factId,
    		String comparisonDataType) throws Exception {
    	BusinessRuleInfoDTO businessRule = getBusinessRuleInfo(
        		yieldValueFunctionType, 
        		criteria, 
        		comparisonOperator,
        		expectedValue, 
        		anchorValue,
        		factId,
        		comparisonDataType);
    	//BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        //container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet = this.generateRuleSet.translate(businessRule);
        // Rule set source code
        return ruleSet.getContent();
    }
    
    @Test
    public void testParseBusinessRule_MinTrue() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.MIN.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"75.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        //List<String> courseSet = Arrays.asList(new String[] {"70.0", "80.0", "90.0"});
        //Map<String,List<String>> factMap = new HashMap<String,List<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_MinFalse() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.MIN.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"85.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        //List<String> courseSet = Arrays.asList(new String[] {"70.0", "80.0", "90.0"});
        //Map<String,List<String>> factMap = new HashMap<String,List<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertFalse(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_MaxTrue() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.MAX.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"85.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        //List<String> courseSet = Arrays.asList(new String[] {"70.0", "80.0", "90.0"});
        //Map<String,List<String>> factMap = new HashMap<String,List<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_MaxFalse() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.MAX.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"75.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        //List<String> courseSet = Arrays.asList(new String[] {"70.0", "80.0", "90.0"});
        //Map<String,List<String>> factMap = new HashMap<String,List<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertFalse(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Intersection() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			anchorValue,
    			factId1,
    			String.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }

    @Test
    public void testParseBusinessRule_Subset() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.SUBSET.toString(), 
    			"CPR101",
    			null,
    			null,
    			anchorValue,
    			factId1,
    			String.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM100");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, courseSet);
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			anchorValue,
    			factId1,
    			String.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts - null fact
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, null);
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			anchorValue,
    			factId1,
    			String.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts - empty fact
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put(factId1, new HashSet<String>());
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.SUM.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"12.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        List<BigDecimal> courseSet = createList("3.0,6.0,3.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put(factId1, courseSet);
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.SUM.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"12.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put(factId1, null);
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.SUM.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"12.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put(factId1, new ArrayList<BigDecimal>());
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        // Get facts
//        Set<SumFact> courseSet = new HashSet<SumFact>(2);
//        courseSet.add( new SumFact("CPR101", new BigDecimal(85.0d)));
//        courseSet.add(new SumFact("MATH102", new BigDecimal(75.0d)));

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put(factId1, courseSet);
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put(factId1, null);
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
    	String factId1 = "fact1";
    	String source = getRuleSourceCode(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());

        factId1 = FactUtil.getFactKey(PROPOSITION_NAME, factId1, 0);
        // Get facts
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factMap.put(factId1, new ArrayList<BigDecimal>());
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
    	String factId1 = "fact1";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());
    	
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	businessRule.setEffectiveStartTime(effectiveStartTime);
    	businessRule.setEffectiveEndTime(effectiveEndTime);
    	
    	//BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        //container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet = this.generateRuleSet.translate(businessRule);
        // Rule set source code
        String source = ruleSet.getContent();

        // Get facts
        List<BigDecimal> courseSet = createList("85.0,75.0,80.0");
        Map<String,List<BigDecimal>> factMap = new HashMap<String,List<BigDecimal>>(1);
        factId1 = FactUtil.getFactKey(businessRule.getRuleElementList().get(0).getRuleProposition().getName(), factId1, 0);
        factMap.put(factId1, courseSet);
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
    	String factId1 = "fact1";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());
    	
    	businessRule.getRuleElementList().get(0).getRuleProposition().
    		getLeftHandSide().getYieldValueFunction().
    		getFactStructureList().get(0).getDefinitionVariableList().clear();
    	
    	//BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        //container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet = null;
		try {
			ruleSet = this.generateRuleSet.translate(businessRule);
			fail("Should have thrown a GenerateRuleSetException since rule contains no valid definition keys");
		} catch (RuleSetTranslatorException e) {
			assertTrue(true);
		}
    }

    @Ignore
    @Test
    public void testParseBusinessRule_Average_InvalidDefinitionKeys() throws Exception {
    	String anchorValue = "CPR101";
    	String factId1 = "fact1";
    	BusinessRuleInfoDTO businessRule = getBusinessRule(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			"CPR101",
    			ComparisonOperator.EQUAL_TO.toString(),
    			"80.0",
    			anchorValue,
    			factId1,
    			BigDecimal.class.getName());
    	
    	// Remove definition key
    	businessRule.getRuleElementList().get(0).getRuleProposition().
    		getLeftHandSide().getYieldValueFunction().
    		getFactStructureList().get(0).getDefinitionVariableList().remove("some identifier - assume only one key");
    	// Add invalid definition key
    	businessRule.getRuleElementList().get(0).getRuleProposition().
			getLeftHandSide().getYieldValueFunction().
			getFactStructureList().get(0).getDefinitionVariableList().put("xxx","xyz");
    	
    	//BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        //container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
		try {
			this.generateRuleSet.translate(businessRule);
			fail("Should have thrown a GenerateRuleSetException since rule contains no valid definition keys");
		} catch (RuleSetTranslatorException e) {
			assertTrue(true);
		}
    }

    private BusinessRuleInfoDTO createBusinessRule(List<RuleElementDTO> ruleElementList) {
        BusinessRuleInfoDTO bri = new BusinessRuleInfoDTO();
    	bri.setName("MyBusinessRule");
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
        yieldValueFunction.setYieldValueFunctionType("INTERSECTION");
        
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        leftHandSide.setYieldValueFunction(yieldValueFunction);
       
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("1");
        
        RulePropositionDTO ruleProposition = new RulePropositionDTO();
        ruleProposition.setName("1ofCPR101");
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
        ruleProposition.setName("CourseCreditSummation");
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
    
    private void createFact1(YieldValueFunctionDTO yieldValueFunction1, String factId1) {
    	// Facts - Rule 1 - 1 of CPR101
        FactStructureDTO fs1 = new FactStructureDTO();
        fs1.setDataType(java.util.Set.class.getName());
        fs1.setFactStructureId(factId1);
        fs1.setAnchorFlag(false);

        Map<String,String> definitionVariableMap1 = new HashMap<String,String>();
        definitionVariableMap1.put("some identifier - assume only one key", "CPR101");
        fs1.setDefinitionVariableList(definitionVariableMap1);

        Map<String,String> executionVariableMap1 = new HashMap<String,String>();
        //executionVariableMap1.put(Constants.EXE_FACT_KEY, "intersection.courseSet");
        fs1.setExecutionVariableList(executionVariableMap1);
        
        List<FactStructureDTO> factStructureList1 = new ArrayList<FactStructureDTO>();
        factStructureList1.add(fs1);
        yieldValueFunction1.setFactStructureList(factStructureList1);
    }
    
    private void createFact2(YieldValueFunctionDTO yieldValueFunction2, String factId1) {
        // Facts - Rule 2 - Sum of credit > 10.0
        FactStructureDTO fs2 = new FactStructureDTO();
        fs2.setDataType(java.math.BigDecimal.class.getName());
        fs2.setFactStructureId(factId1);
        fs2.setAnchorFlag(false);

        // Not need for summation or averages
        //Map<String,String> definitionVariableMap2 = new HashMap<String,String>();
        //definitionVariableMap2.put(Constants.DEF_CRITERIA_KEY, null);
        //fs2.setDefinitionVariableList(definitionVariableMap2);

        //Map<String,String> executionVariableMap2 = new HashMap<String,String>();
        //executionVariableMap2.put(Constants.EXE_FACT_KEY, "summation.courseSet");
        //fs2.setExecutionVariableList(executionVariableMap2);
        
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
    
    private Map<String,Object> getFacts(String factId1, String factId2) {
		Map<String,Object> factMap = new HashMap<String,Object>();

		Set<String> courseSet1 = createSet("CPR101,MATH102,CHEM100");
		factMap.put(factId1, courseSet1);

        List<BigDecimal> courseSet2 = createList("3.0,6.0,3.0");
        factMap.put(factId2, courseSet2);
        return factMap;
    }
    
    @Test
    public void testComplexRule() throws Exception {
    	// Yield value functions
    	YieldValueFunctionDTO yieldValueFunction1 = new YieldValueFunctionDTO();
    	YieldValueFunctionDTO yieldValueFunction2 = new YieldValueFunctionDTO();

        // Rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
    	RuleElementDTO element1 = createRule1(yieldValueFunction1);
    	RuleElementDTO element2 = createRule2(yieldValueFunction2);
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);

    	// Facts - Rule 1 - 1 of CPR101
    	//String factId1 = factId1 + "." + 1;
        String factId1 = "fact1";
    	createFact1(yieldValueFunction1, factId1);

        // Facts - Rule 2 - Sum of credit > 10.0
    	//String factId2 = factId1 + "." + 2;
        String factId2 = "fact2";
    	createFact2(yieldValueFunction2, factId2);

        // Parse and generate functional business rule into Drools rules
        //BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        //container.getBusinessRules().add(bri);
        RuleSet ruleSet = this.generateRuleSet.translate(bri);
        assertNotNull(ruleSet);

        // Rule set source code
        String source = ruleSet.getContent();

		// Get facts
        factId1 = FactUtil.getFactKey(element1.getRuleProposition().getName(), factId1, 0);
        factId2 = FactUtil.getFactKey(element2.getRuleProposition().getName(), factId2, 0);
		Map<String,Object> factMap = getFacts(factId1, factId2);
		FactContainer facts =  new FactContainer(bri.getAnchorValue(), factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }
    
    @Test
    public void testRule_NoRuleElements() throws Exception {
        // Rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);

        // Parse and generate functional business rule into Drools rules
        try{
	        RuleSet ruleSet = this.generateRuleSet.translate(bri);
	        fail("Should have thrown a RuleSetTranslatorException since rule element list is empty");
        } catch(RuleSetTranslatorException e) {
        	assertTrue(true);
        }
    }
}

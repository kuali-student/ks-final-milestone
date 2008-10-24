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
import java.util.Arrays;
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
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO.FactParamDefTime;
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

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
    }

    private BusinessRuleInfoDTO getBusinessRule(
    		String yieldValueFunctionType, 
    		Object criteria, 
    		String criteriaId,
    		String comparisonOperator, 
    		String expectedValue, 
    		String anchorValue, 
    		String comparisonOperatorType,
    		String factKey) throws Exception {
    		//String comparisonDataType) throws Exception {
        BusinessRuleInfoDTO businessRule = dtoFactory.createBusinessRuleInfoDTO(
        		"CPR101", 
        		"1", 
        		"Rule 1 Success Message", 
        		"Rule 1 Failure Message", 
        		"kuali.businessrule.typekey.course.corequisites", 
        		getRuleElementList(
        				yieldValueFunctionType, 
        				criteria, 
        				criteriaId,
        				comparisonOperator, 
        				expectedValue, 
        				comparisonOperatorType,
        				factKey),
        				//comparisonDataType), 
        		"kuali.anchor.typekey.course", 
        		anchorValue);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	businessRule.setEffectiveStartTime(effectiveStartTime);
    	businessRule.setEffectiveEndTime(effectiveEndTime);

        return businessRule;
    }

    private List<RuleElementDTO> getRuleElementList(String yieldValueFunctionType, 
    		Object criteria, 
    		String criteriaId,
    		String comparisonOperator, 
    		String expectedValue, 
    		String comparisonOperatorType,
    		String factKey) {
    		//String comparisonDataType) {
    	List<RuleElementDTO> list = new ArrayList<RuleElementDTO>();
        RuleElementDTO re1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
        				yieldValueFunctionType, 
        				criteria, 
        				criteriaId,
        				comparisonOperator, 
        				expectedValue, 
        				comparisonOperatorType,
        				factKey));
        				//comparisonDataType));
        list.add(re1);
        return list;
    }

    private RulePropositionDTO getRuleProposition(
    		String yieldValueFunctionType, 
    		Object criteria, 
    		String criteriaId,
    		String comparisonOperator, 
    		String expectedValue,
    		String comparisonOperatorType,
    		String factKey) {

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, yieldValueFunctionType);
    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		PROPOSITION_NAME, comparisonOperatorType, 
        		comparisonOperator, leftSide, rightSide);
        
        FactStructureDTO factStructure = new FactStructureDTO();
        //factStructure.setDataType(java.util.Set.class.getName());
        //factStructure.setFactStructureId("some id");
        factStructure.setAnchorFlag(false);

        // DEFINITION
        Map<String, FactParamDTO> factParamMap = new HashMap<String, FactParamDTO>();
        FactParamDTO param1 = new FactParamDTO();
        param1.setDataType("");
        param1.setDefTime(FactParamDefTime.DEFINITION);
        param1.setKey(criteriaId);
        factParamMap.put(criteriaId, param1);
        
        FactCriteriaTypeInfoDTO criteriaTypeInfo = new FactCriteriaTypeInfoDTO();
        criteriaTypeInfo.setFactParamMap(factParamMap);
        factStructure.setCriteriaTypeInfo(criteriaTypeInfo);
        
        Map<String,Object> paramValueMap = new HashMap<String,Object>();
        paramValueMap.put(criteriaId, criteria);
        factStructure.setParamValueMap(paramValueMap);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(factStructure);
        yieldValueFunction.setFactStructureList(factStructureList);

        // EXECUTION
        factStructure.setFactStructureId("some.id");
        factStructure.setAnchorFlag(false);

        FactParamDTO param2 = new FactParamDTO();
        param2.setDataType("");
        param2.setDefTime(FactParamDefTime.EXECUTION);
        param2.setKey(factKey);
        
        criteriaTypeInfo.getFactParamMap().put(factKey, param2);

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

    /*private Set<String> createSet(String list) {
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
    }*/
    
    private BusinessRuleInfoDTO getBusinessRuleInfo(String yieldValueFunctionType,
    		Object criteria, 
    		String criteriaId, 
    		String comparisonOperator, 
    		String expectedValue, 
    		String anchorValue, 
    		String comparisonOperatorType,
    		String factKey) throws Exception {
    		//String comparisonDataType) throws Exception {
        // Generate Drools rule set source code
        BusinessRuleInfoDTO businessRule = getBusinessRule(
        		yieldValueFunctionType, 
        		criteria,
        		criteriaId,
        		comparisonOperator,
        		expectedValue, 
        		anchorValue, 
        		comparisonOperatorType,
        		factKey);
        		//comparisonDataType);
        return businessRule;
    }

    private String createRuleSourceCode(
    		String yieldValueFunctionType,
    		Object criteria, 
    		String criteriaId, 
    		String comparisonOperator, 
    		String expectedValue, 
    		String anchorValue, 
    		String comparisonOperatorType,
    		String factKey) throws Exception {
    		//String comparisonDataType) throws Exception {
    	BusinessRuleInfoDTO businessRule = getBusinessRuleInfo(
        		yieldValueFunctionType, 
        		criteria, 
        		criteriaId,
        		comparisonOperator,
        		expectedValue, 
        		anchorValue,
        		comparisonOperatorType,
        		factKey);
        		//comparisonDataType);
    	//BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        //container.getBusinessRules().add(businessRule);
        // Parse and generate rule set
        RuleSet ruleSet = this.generateRuleSet.translate(businessRule);
        // Rule set source code
        return ruleSet.getContent();
    }

    private FactResultDTO createFactResult(String values) {
    	List<String> list = Arrays.asList(values.split(","));
    	FactResultDTO factResult = new FactResultDTO();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for(String item : list) {
        	Map<String, Object> row = new HashMap<String, Object>();
	        row.put("column1", item);
	        resultList.add(row);
        }

        factResult.setResultList(resultList);
        return factResult;
    }

/*    
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
*/
    @Test
    public void testParseBusinessRule_Intersection() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
    	String factKey = "FactKey1";
    	String criteriaKey = "CriteriaKey1";
        // DEFINITION and EXECUTION: Create rule definition and map execution keys
    	Set<String> criteria = new HashSet<String>(Arrays.asList("CPR101"));
    	String source = createRuleSourceCode(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			criteria,
    			criteriaKey,
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			anchorValue,
    			Integer.class.getName(),
    			factKey);

        // EXECUTION: Create facts
        FactResultDTO factResult = createFactResult("CPR101,MATH101,CHEM101");
        FactResultDTO factResultCriteria = createFactResult("CPR101");

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);
    	
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
    	String factKey = "FactKey1";
    	String criteriaKey = "CriteriaKey1";
        // DEFINITION and EXECUTION: Create rule definition and map execution keys
    	Set<String> criteria = new HashSet<String>(Arrays.asList("CPR101"));
    	String source = createRuleSourceCode(
    			YieldValueFunctionType.SUBSET.toString(), 
    			criteria,
    			criteriaKey,
    			null, // Comparison operator needed for subset
    			null, // no expected valued needed for subset
    			anchorValue,
    			Integer.class.getName(),
    			factKey);

        // EXECUTION: Create facts
        FactResultDTO factResult = createFactResult("CPR101,MATH101,CHEM101");
        FactResultDTO factResultCriteria = createFactResult("CPR101");

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);
    	
        FactContainer facts =  new FactContainer(anchorValue, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        executeRule(source, facts);
        assertTrue(prop.getRuleResult());
    }
/*
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
    }*/

    /*@Test
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
*/
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

    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setOperation("AND");
        
        return re;
    }
    
    @Test
    public void testComplexRule() throws Exception {
    	String factKey1 = "FactKey1";
    	String criteriaKey1 = "CriteriaKey1";
    	String factKey2 = "FactKey2";
    	String criteriaKey2 = "CriteriaKey2";
        // DEFINITION and EXECUTION: Create rule definition and map execution keys
    	Set<String> criteria = new HashSet<String>(Arrays.asList("CPR101"));

        // Rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			YieldValueFunctionType.SUBSET.toString(), 
            			criteria,
            			criteriaKey1,
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			factKey1));
        RuleElementDTO element2 = dtoFactory.createRuleElementDTO(
        		"element-2", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			YieldValueFunctionType.INTERSECTION.toString(), 
            			criteria,
            			criteriaKey2,
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			factKey2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);
        bri.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = this.generateRuleSet.translate(bri);
        assertNotNull(ruleSet);
        // Rule set source code
        String source = ruleSet.getContent();
System.out.println(source);

        // EXECUTION: Create facts
        FactResultDTO factResult1 = createFactResult("CPR101,MATH101,CHEM101");
        FactResultDTO factResultCriteria1 = createFactResult("CPR101");
        FactResultDTO factResult2 = createFactResult("CPR102,MATH102,CHEM102");
        FactResultDTO factResultCriteria2 = createFactResult("MATH102");

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey1, factResultCriteria1);
        factMap.put(factKey1, factResult1);
        factMap.put(criteriaKey2, factResultCriteria2);
        factMap.put(factKey2, factResult2);

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

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

import static org.junit.Assert.assertEquals;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.StatelessSession;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.RuleSetValidator;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;
import org.kuali.student.rules.translators.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;

public class RuleSetTranslatorTest {
    private static RuleSetTranslatorDroolsImpl ruleSetTranslator = null;
    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();
    
    private final static String PROPOSITION_NAME = "co-requisites";

    @BeforeClass
    public static void setUpOnce() throws Exception {
    	ruleSetTranslator = new RuleSetTranslatorDroolsImpl();
    	RuleSetValidator validator = new RuleSetValidatorDroolsImpl();
    	ruleSetTranslator.setRuleSetValidator(validator);
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
    }

    private RulePropositionDTO getRuleProposition(
    		String comparisonOperator, 
    		String expectedValue,
    		String comparisonOperatorType,
    		YieldValueFunctionDTO yieldValueFunction) {

    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		PROPOSITION_NAME, comparisonOperatorType, 
        		comparisonOperator, leftSide, rightSide);
        
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

    private Map<String, FactContainer> executeStatefulSessionRule(String source, List<FactContainer> facts) throws Exception {
        // Execute Drools rule set source code
    	StatefulSession workingMemory = getRuleBase(source).newStatefulSession();
        workingMemory.insert(new CurrentDateTime());
        for(FactContainer fact : facts) {
        	workingMemory.insert(fact);
        }
        workingMemory.fireAllRules();
        Map<String, FactContainer> map = new HashMap<String, FactContainer>();
        for(Iterator<?> it = workingMemory.iterateObjects(); it.hasNext(); ) {
        	Object obj = it.next();
        	if(obj instanceof FactContainer) {
        		FactContainer fc = (FactContainer) obj;
        		map.put(fc.getId(), fc);
        	}
        }
        workingMemory.dispose();
        return map;
    }

    private Map<String, FactContainer> executeStatelessSessionRule(String source, List<FactContainer> facts) throws Exception {
        // Execute Drools rule set source code
    	StatelessSession session = getRuleBase(source).newStatelessSession();
    	List<Object> newFacts = new ArrayList<Object>(facts.size()+1);
    	newFacts.add(new CurrentDateTime());
    	for(FactContainer fc : facts){ 
    		newFacts.add(fc);
    	}
    	Iterator<?> it = session.executeWithResults(newFacts).iterateObjects();
    	
        Map<String, FactContainer> map = new HashMap<String, FactContainer>();
        while(it.hasNext()) {
        	Object obj = it.next();
        	if(obj instanceof FactContainer) {
        		FactContainer fc = (FactContainer) obj;
        		map.put(fc.getId(), fc);
        	}
        }
        return map;
    }

    private FactResultDTO createFactResult(String[] values) {
		FactResultDTO factResult = new FactResultDTO();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for (String item : values) {
			Map<String, String> row = new HashMap<String, String>();
			row.put("column1", item);
			resultList.add(row);
		}

		factResult.setResultList(resultList);
		return factResult;
	}
    
    private FactResultTypeInfoDTO createColumnMetaData(String dataType) {
    	Map<String, FactResultColumnInfoDTO> columnsInfoMap = new HashMap<String, FactResultColumnInfoDTO>();
    	FactResultColumnInfoDTO columnInfo = new FactResultColumnInfoDTO();
    	columnInfo.setKey("column1");
    	columnInfo.setDataType(dataType);
    	columnsInfoMap.put(columnInfo.getKey(), columnInfo);
    	FactResultTypeInfoDTO typeInfo = new FactResultTypeInfoDTO();
    	typeInfo.setResultColumnsMap(columnsInfoMap);
    	return typeInfo;
    }

    private FactStructureDTO createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureDTO factStructure1 = new FactStructureDTO();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfoDTO criteriaTypeInfo1 = new FactCriteriaTypeInfoDTO();
	    criteriaTypeInfo1.setName(criteriaTypeName);
	    factStructure1.setCriteriaTypeInfo(criteriaTypeInfo1);
	    return factStructure1;
    }

    private FactResultDTO createFactResult(String fact, Class<?> dataType) {
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(dataType.getName());
        FactResultDTO factResult = createFactResult(new String[] {fact});
        factResult.setFactResultTypeInfo(columnMetadata);
        return factResult;
    }
    
    private List<FactContainer> createFactList(String trueFact, int loops, BusinessRuleInfoDTO businessRule, String factKey, String anchor, Class<?> dataType) {
    	List<FactContainer> factList = new ArrayList<FactContainer>(loops);
        for(int i=0; i<loops; i++) {
        	// True fact
        	FactResultDTO factResult = createFactResult(trueFact, dataType);
            if (i % 2 != 0) {
            	// False fact
            	factResult = createFactResult(""+trueFact.hashCode(), dataType);
            }
            
            Map<String, Object> factMap = new HashMap<String, Object>();
            factMap.put(factKey, factResult);

            Map<String, RulePropositionDTO> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
            FactContainer fact = new FactContainer("id-"+i, anchor, propositionMap, factMap);
            
            factList.add(fact);
        }
        return factList;
    }
    
    private void assertFactContainerList(Map<String, FactContainer> exeMap, int loops) {
        for(int i=0; i<loops; i++) {
        	String key = "id-"+i;
        	FactContainer fc = exeMap.get(key);
            if (i % 2 == 0) {
            	assertTrue("Failing proposition: id=" + key, fc.getPropositionContainer().getRuleResult());
            } else {
            	assertFalse(fc.getPropositionContainer().getRuleResult());
            }
        }
    }
    
	@Test
	public void testTranslateBusinessRule_Comparable_100FactContainers_StatelessSession() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"cluID-1234",
            			String.class.getName(),
            			yieldValueFunction1));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String factKey1 = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
        int loops = 20;
        List<FactContainer> factList = createFactList("cluID-1234", loops, businessRule, factKey1, anchorValue, String.class);

        Map<String, FactContainer> exeMap = executeStatelessSessionRule(ruleSet.getContent(), factList);
        
        assertFactContainerList(exeMap, loops);
	}

	@Test
	public void testTranslateBusinessRule_Comparable_100FactContainers_StatefulSession() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"100.1234567890",
            			BigDecimal.class.getName(),
            			yieldValueFunction1));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String factKey1 = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
        int loops = 20;
        List<FactContainer> factList = createFactList("100.1234567890", loops, businessRule, factKey1, anchorValue, BigDecimal.class);

        Map<String, FactContainer> exeMap = executeStatefulSessionRule(ruleSet.getContent(), factList);
        
        assertFactContainerList(exeMap, loops);
	}

	@Test
	public void testTranslateBusinessRule_Comparable() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
    	YieldValueFunctionDTO yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.comparable.fact");
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure2));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"cluID-1234",
            			String.class.getName(),
            			yieldValueFunction1));
        RuleElementDTO element2 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String factKey1 = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata1 = createColumnMetaData(String.class.getName());
        FactResultDTO factResult1 = createFactResult(new String[] {"cluID-1234"});
        factResult1.setFactResultTypeInfo(columnMetadata1);

    	String factKey2 = FactUtil.createFactKey(factStructure2);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata2 = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult2 = createFactResult(new String[] {"75.0"});
        factResult2.setFactResultTypeInfo(columnMetadata2);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey1, factResult1);
        factMap.put(factKey2, factResult2);

    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts = new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_Comparable_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
    	YieldValueFunctionDTO yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("cluID-1234");
		factStructure1.setStaticValueDataType(String.class.getName());
		
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.comparable.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("75.0");
		factStructure2.setStaticValueDataType(BigDecimal.class.getName());
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure2));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"cluID-1234",
            			String.class.getName(),
            			yieldValueFunction1));
        RuleElementDTO element2 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts = new FactContainer(""+System.nanoTime(), anchorValue, propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MinTrue() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MIN.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MinFalse() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MIN.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"85.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertFalse(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MinTrue_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MIN.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("85.0, 75.0, 80.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MaxTrue() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MAX.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"85.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MaxTrue_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MAX.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("85.0, 75.0, 80.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"85.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MaxFalse() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MAX.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertFalse(facts.getPropositionContainer().getRuleResult());
	}
    
    @Test
    public void testTranslateBusinessRule_Subset() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String criteriaKey = FactUtil.createCriteriaKey(factStructure1);
    	String factKey = FactUtil.createFactKey(factStructure2);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(String.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResult.setFactResultTypeInfo(columnMetadata);
        FactResultDTO factResultCriteria = createFactResult(new String[] {"CPR101"});
        factResultCriteria.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Subset_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("CPR101");
		factStructure1.setStaticValueDataType(String.class.getName());
		// EXECUTION: Fact
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("CPR101,MATH101,CHEM101");
		factStructure2.setStaticValueDataType(String.class.getName());
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

	@Test
    public void testTranslateBusinessRule_Intersection() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String criteriaKey = FactUtil.createCriteriaKey(factStructure1);
    	String factKey = FactUtil.createFactKey(factStructure2);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(String.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResult.setFactResultTypeInfo(columnMetadata);
        FactResultDTO factResultCriteria = createFactResult(new String[] {"CPR101"});
        factResultCriteria.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

	@Test
    public void testTranslateBusinessRule_Intersection_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("CPR101");
		factStructure1.setStaticValueDataType(String.class.getName());
		// EXECUTION: Fact
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("CPR101,MATH101,CHEM101");
		factStructure2.setStaticValueDataType(String.class.getName());
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Sum() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUM.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"12.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"3.0", "6.0", "3.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Sum_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUM.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("3.0, 6.0, 3.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"12.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Average() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"80.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Average_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("85.0,75.0,80.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"80.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Average_EffectiveExpiryDate() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"80.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");
    	// Set effective and expiry dates
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2001, 1, 1, 12, 00);
    	businessRule.setEffectiveStartTime(effectiveStartTime);
    	businessRule.setEffectiveEndTime(effectiveEndTime);

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultDTO factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertFalse(facts.getPropositionContainer().getRuleResult());
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

    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setOperation("AND");
        
        return re;
    }
    
    @Test
    public void testTranslateBusinessRule_SubsetIntersection() throws Exception {
    	YieldValueFunctionDTO yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
    	YieldValueFunctionDTO yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
		FactStructureDTO factStructure3 = createFactStructure("subset.id.3", "course.subset.criteria");
		FactStructureDTO factStructure4 = createFactStructure("subset.id.4", "course.subset.fact");
		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

		List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction1));
        RuleElementDTO element2 = dtoFactory.createRuleElementDTO(
        		"element-2", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
        assertNotNull(ruleSet);
        
    	String criteriaKey1 = FactUtil.createCriteriaKey(factStructure1);
    	String factKey1 = FactUtil.createFactKey(factStructure2);

    	String criteriaKey2 = FactUtil.createCriteriaKey(factStructure3);
    	String factKey2 = FactUtil.createFactKey(factStructure4);

        // EXECUTION: Create facts
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(String.class.getName());

        FactResultDTO factResult1 = createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResult1.setFactResultTypeInfo(columnMetadata);

        FactResultDTO factResultCriteria1 = createFactResult(new String[] {"CPR101"});
        factResultCriteria1.setFactResultTypeInfo(columnMetadata);

        FactResultDTO factResult2 = createFactResult(new String[] {"CPR102","MATH102","CHEM102"});
        factResult2.setFactResultTypeInfo(columnMetadata);

        FactResultDTO factResultCriteria2 = createFactResult(new String[] {"MATH102"});
        factResultCriteria2.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey1, factResultCriteria1);
        factMap.put(factKey1, factResult1);
        factMap.put(criteriaKey2, factResultCriteria2);
        factMap.put(factKey2, factResult2);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), businessRule.getAnchorValue(), propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_SubsetIntersection_StaticFact() throws Exception {
    	YieldValueFunctionDTO yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
    	YieldValueFunctionDTO yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("CPR101");
		factStructure1.setStaticValueDataType(String.class.getName());
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("CPR101,MATH101,CHEM101");
		factStructure2.setStaticValueDataType(String.class.getName());
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
		FactStructureDTO factStructure3 = createFactStructure("subset.id.3", "course.subset.criteria");
		factStructure3.setStaticFact(true);
		factStructure3.setStaticValue("MATH102");
		factStructure3.setStaticValueDataType(String.class.getName());
		FactStructureDTO factStructure4 = createFactStructure("subset.id.4", "course.subset.fact");
		factStructure4.setStaticFact(true);
		factStructure4.setStaticValue("CPR102,MATH102,CHEM102");
		factStructure4.setStaticValueDataType(String.class.getName());
		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

		List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction1));
        RuleElementDTO element2 = dtoFactory.createRuleElementDTO(
        		"element-2", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchorValue("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
        assertNotNull(ruleSet);
        
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), businessRule.getAnchorValue(), propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testRule_NullRuleElements() throws Exception {
        // Rule elements
    	List<RuleElementDTO> ruleElementList = null;

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);

        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        assertNotNull(ruleSet);
    }

    @Test
    public void testRule_EmptyRuleElements() throws Exception {
        // Rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        assertNotNull(ruleSet);
    }

    @Test
    public void testRule_InvalidRuleName_InvalidCharacters1() throws Exception {
        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(null);
        // Create invalid name
        bri.setBusinessRuleId("11223344-1122-1122-1112-200000000001");
        bri.setBusinessRuleTypeKey("org_kuali-student.pre req#1&2");
        bri.setName("TestName `~!@#$%^&*()-+={[}]|\\:;\"'<,>.?/ \b\t\n\f\r \' \"");

        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        String name = RuleSetTranslatorDroolsImpl.getRuleSetName(bri);
        assertNotNull(ruleSet);
        assertEquals(name, ruleSet.getName());
    }

    @Test
    public void testRule_InvalidRuleName_InvalidCharacters2() throws Exception {
        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(null);
        // Create invalid name
        bri.setBusinessRuleId("11223344-1122-1122-1112-200000000001");
        bri.setBusinessRuleTypeKey("org_kuali-student.pre req#1&2");
        bri.setName("ABC123456 `~!@#$%^&*()-+= XYZ");

        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        String name = RuleSetTranslatorDroolsImpl.getRuleSetName(bri);
        assertNotNull(ruleSet);
        assertEquals(name, ruleSet.getName());
    }

    @Test
    public void testRule_InvalidPackageAndRuleName_InvalidNumber() throws Exception {
        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(null);
        // Create invalid name
        bri.setBusinessRuleId("11223344-1122-1122-1112-200000000001");
        bri.setBusinessRuleTypeKey("123 kuali-student.pre req#1&2");
        bri.setName("123456 `~!@#$%^&*()-+= XYZ");

		try {
        	ruleSetTranslator.translate(bri);
        fail("Translator should have thrown an invalid rule exception");
        } catch(RuleSetTranslatorException e) {
        	assertNotNull(e.getMessage());
        }
    }
}

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
package org.kuali.student.brms.translators.drools;

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
import org.kuali.student.brms.factfinder.dto.FactCriteriaTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactResultColumnInfo;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.brms.internal.common.utils.FactUtil;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;
import org.kuali.student.brms.translators.RuleSetValidator;
import org.kuali.student.brms.translators.drools.RuleSetTranslatorDroolsImpl;
import org.kuali.student.brms.translators.drools.RuleSetValidatorDroolsImpl;
import org.kuali.student.brms.translators.exceptions.RuleSetTranslatorException;
import org.kuali.student.brms.util.CurrentDateTime;
import org.kuali.student.brms.util.FactContainer;

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

    private RulePropositionInfo getRuleProposition(
    		String comparisonOperator, 
    		String expectedValue,
    		String comparisonOperatorType,
    		YieldValueFunctionInfo yieldValueFunction) {

    	LeftHandSideInfo leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideInfo rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionInfo ruleProp = dtoFactory.createRulePropositionInfo(
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

    private FactResultInfo createFactResult(String[] values) {
		FactResultInfo factResult = new FactResultInfo();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for (String item : values) {
			Map<String, String> row = new HashMap<String, String>();
			row.put("column1", item);
			resultList.add(row);
		}

		factResult.setResultList(resultList);
		return factResult;
	}
    
    private FactResultTypeInfo createColumnMetaData(String dataType) {
    	Map<String, FactResultColumnInfo> columnsInfoMap = new HashMap<String, FactResultColumnInfo>();
    	FactResultColumnInfo columnInfo = new FactResultColumnInfo();
    	columnInfo.setKey("column1");
    	columnInfo.setDataType(dataType);
    	columnsInfoMap.put(columnInfo.getKey(), columnInfo);
    	FactResultTypeInfo typeInfo = new FactResultTypeInfo();
    	typeInfo.setResultColumnsMap(columnsInfoMap);
    	return typeInfo;
    }

    private FactStructureInfo createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureInfo factStructure1 = new FactStructureInfo();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfo criteriaTypeInfo1 = new FactCriteriaTypeInfo();
	    criteriaTypeInfo1.setName(criteriaTypeName);
	    factStructure1.setCriteriaTypeInfo(criteriaTypeInfo1);
	    return factStructure1;
    }

    private FactResultInfo createFactResult(String fact, Class<?> dataType) {
    	FactResultTypeInfo columnMetadata = createColumnMetaData(dataType.getName());
        FactResultInfo factResult = createFactResult(new String[] {fact});
        factResult.setFactResultTypeInfo(columnMetadata);
        return factResult;
    }
    
    private List<FactContainer> createFactList(String trueFact, int loops, BusinessRuleInfo businessRule, String factKey, String anchor, Class<?> dataType) {
    	List<FactContainer> factList = new ArrayList<FactContainer>(loops);
        for(int i=0; i<loops; i++) {
        	// True fact
        	FactResultInfo factResult = createFactResult(trueFact, dataType);
            if (i % 2 != 0) {
            	// False fact
            	factResult = createFactResult(""+trueFact.hashCode(), dataType);
            }
            
            Map<String, Object> factMap = new HashMap<String, Object>();
            factMap.put(factKey, factResult);

            Map<String, RulePropositionInfo> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
            FactContainer fact = new FactContainer("id-"+i, anchor, "KUALI_COURSE", propositionMap, factMap);
            
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
    
    private BusinessRuleInfo createBusinessRule(List<RuleElementInfo> ruleElementList) {
        BusinessRuleInfo bri = new BusinessRuleInfo();
    	bri.setName("MyBusinessRule");
    	bri.setDesc("Some business rule");
    	bri.setSuccessMessage("Success message");
    	bri.setFailureMessage("Failure message");
    	bri.setId("1");
    	bri.setType("kuali.student.businessrule.typekey.course.corequisites");
    	bri.setAnchorTypeKey("KUALI_COURSE");
    	bri.setAnchor("CPR101");
    	bri.setBusinessRuleElementList(ruleElementList);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	bri.setEffectiveDate(effectiveStartTime);
    	bri.setExpirationDate(effectiveEndTime);
    	return bri;
    }

    private RuleElementInfo getAndOperator() {
    	RuleElementInfo re = new RuleElementInfo();
        re.setName("And");
        re.setDesc("And");
        re.setBusinessRuleElemnetTypeKey("AND");
        
        return re;
    }
    
	@Test
	public void testTranslateBusinessRule_Comparable_100FactContainers_StatelessSession() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"cluID-1234",
            			String.class.getName(),
            			yieldValueFunction1));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String factKey1 = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
        int loops = 5;
        List<FactContainer> factList = createFactList("cluID-1234", loops, businessRule, factKey1, anchorValue, String.class);

        Map<String, FactContainer> exeMap = executeStatelessSessionRule(ruleSet.getContent(), factList);
        
        assertFactContainerList(exeMap, loops);
	}

	@Test
	public void testTranslateBusinessRule_Comparable_100FactContainers_StatefulSession() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"100.1234567890",
            			BigDecimal.class.getName(),
            			yieldValueFunction1));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String factKey1 = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
        int loops = 5;
        List<FactContainer> factList = createFactList("100.1234567890", loops, businessRule, factKey1, anchorValue, BigDecimal.class);

        Map<String, FactContainer> exeMap = executeStatefulSessionRule(ruleSet.getContent(), factList);
        
        assertFactContainerList(exeMap, loops);
	}

	@Test
	public void testTranslateBusinessRule_Comparable() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
    	YieldValueFunctionInfo yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "column1");

        FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);

        FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.comparable.fact");
        factStructure2.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure2));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"cluID-1234",
            			String.class.getName(),
            			yieldValueFunction1));
        RuleElementInfo element2 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String factKey1 = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata1 = createColumnMetaData(String.class.getName());
        FactResultInfo factResult1 = createFactResult(new String[] {"cluID-1234"});
        factResult1.setFactResultTypeInfo(columnMetadata1);

    	String factKey2 = FactUtil.createFactKey(factStructure2);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata2 = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult2 = createFactResult(new String[] {"75.0"});
        factResult2.setFactResultTypeInfo(columnMetadata2);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey1, factResult1);
        factMap.put(factKey2, factResult2);

    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts = new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_Comparable_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
    	YieldValueFunctionInfo yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SIMPLECOMPARABLE.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.comparable.fact");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("cluID-1234");
		factStructure1.setStaticValueDataType(String.class.getName());
		
		FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.comparable.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("75.0");
		factStructure2.setStaticValueDataType(BigDecimal.class.getName());
		
		// EXECUTION: Fact
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1));
		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure2));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"cluID-1234",
            			String.class.getName(),
            			yieldValueFunction1));
        RuleElementInfo element2 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts = new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MinTrue() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MIN.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MIN_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MinFalse() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MIN.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"85.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertFalse(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MinTrue_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MIN.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("85.0, 75.0, 80.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MaxTrue() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MAX.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"85.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MaxTrue_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MAX.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("85.0, 75.0, 80.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"85.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
	}

	@Test
	public void testTranslateBusinessRule_MaxFalse() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.MAX.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_MAX_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"75.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertFalse(facts.getPropositionContainer().getRuleResult());
	}
    
    @Test
    public void testTranslateBusinessRule_Subset() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
        factStructure2.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String criteriaKey = FactUtil.createCriteriaKey(factStructure1);
    	String factKey = FactUtil.createFactKey(factStructure2);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(String.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResult.setFactResultTypeInfo(columnMetadata);
        FactResultInfo factResultCriteria = createFactResult(new String[] {"CPR101"});
        factResultCriteria.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Subset_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("CPR101");
		factStructure1.setStaticValueDataType(String.class.getName());
		// EXECUTION: Fact
		FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("CPR101,MATH101,CHEM101");
		factStructure2.setStaticValueDataType(String.class.getName());
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

	@Test
    public void testTranslateBusinessRule_Intersection() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());

		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "column1");
        factStructure1.setResultColumnKeyTranslations(resultColumnKeyMap);
		
		// EXECUTION: Fact
		FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
        factStructure2.setResultColumnKeyTranslations(resultColumnKeyMap);

        yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);

    	String criteriaKey = FactUtil.createCriteriaKey(factStructure1);
    	String factKey = FactUtil.createFactKey(factStructure2);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(String.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResult.setFactResultTypeInfo(columnMetadata);
        FactResultInfo factResultCriteria = createFactResult(new String[] {"CPR101"});
        factResultCriteria.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey, factResultCriteria);
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

	@Test
    public void testTranslateBusinessRule_Intersection_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("CPR101");
		factStructure1.setStaticValueDataType(String.class.getName());
		// EXECUTION: Fact
		FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("CPR101,MATH101,CHEM101");
		factStructure2.setStaticValueDataType(String.class.getName());
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Sum() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUM.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> resultColumnKey = new HashMap<String, String>();
		resultColumnKey.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(resultColumnKey);

		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"12.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"3.0", "6.0", "3.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Sum_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUM.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("3.0, 6.0, 3.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"12.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Average() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> resultColumnKey = new HashMap<String, String>();
		resultColumnKey.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(resultColumnKey);

		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"80.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Average_StaticFact() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("85.0,75.0,80.0");
		factStructure1.setStaticValueDataType(BigDecimal.class.getName());
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"80.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_Average_EffectiveExpiryDate() throws Exception {
        // Generate Drools rule set source code
    	String anchorValue = "CPR101";
        // DEFINITION: Create rule definition 

    	YieldValueFunctionInfo yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		// EXECUTION: Fact
		yieldValueFunction.setFactStructureList(Arrays.asList(factStructure1));
		
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"80.0",
            			BigDecimal.class.getName(),
            			yieldValueFunction));
    	ruleElementList.add(element1);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");
    	// Set effective and expiry dates
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2001, 1, 1, 12, 00);
    	businessRule.setEffectiveDate(effectiveStartTime);
    	businessRule.setExpirationDate(effectiveEndTime);

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
    	
    	String factKey = FactUtil.createFactKey(factStructure1);

        // EXECUTION: Create fact results (data)
    	FactResultTypeInfo columnMetadata = createColumnMetaData(BigDecimal.class.getName());
        FactResultInfo factResult = createFactResult(new String[] {"85.0", "75.0", "80.0"});
        factResult.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);

    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), anchorValue, "KUALI_COURSE", propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertFalse(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_SubsetIntersection() throws Exception {
    	YieldValueFunctionInfo yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
    	YieldValueFunctionInfo yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		Map<String,String> subsetResultColumnKey = new HashMap<String, String>();
		subsetResultColumnKey.put(MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY, "column1");

		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setResultColumnKeyTranslations(subsetResultColumnKey);
		FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setResultColumnKeyTranslations(subsetResultColumnKey);
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
		Map<String,String> intersectionResultColumnKey = new HashMap<String, String>();
		intersectionResultColumnKey.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "column1");

		FactStructureInfo factStructure3 = createFactStructure("subset.id.3", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(intersectionResultColumnKey);
		FactStructureInfo factStructure4 = createFactStructure("subset.id.4", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(intersectionResultColumnKey);

		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

		List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction1));
        RuleElementInfo element2 = dtoFactory.createRuleElementDTO(
        		"element-2", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
        assertNotNull(ruleSet);
        
    	String criteriaKey1 = FactUtil.createCriteriaKey(factStructure1);
    	String factKey1 = FactUtil.createFactKey(factStructure2);

    	String criteriaKey2 = FactUtil.createCriteriaKey(factStructure3);
    	String factKey2 = FactUtil.createFactKey(factStructure4);

        // EXECUTION: Create facts
    	FactResultTypeInfo columnMetadata = createColumnMetaData(String.class.getName());

        FactResultInfo factResult1 = createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResult1.setFactResultTypeInfo(columnMetadata);

        FactResultInfo factResultCriteria1 = createFactResult(new String[] {"CPR101"});
        factResultCriteria1.setFactResultTypeInfo(columnMetadata);

        FactResultInfo factResult2 = createFactResult(new String[] {"CPR102","MATH102","CHEM102"});
        factResult2.setFactResultTypeInfo(columnMetadata);

        FactResultInfo factResultCriteria2 = createFactResult(new String[] {"MATH102"});
        factResultCriteria2.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey1, factResultCriteria1);
        factMap.put(factKey1, factResult1);
        factMap.put(criteriaKey2, factResultCriteria2);
        factMap.put(factKey2, factResult2);

        //Map<String, YieldValueFunctionInfo> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), businessRule.getAnchor(), businessRule.getAnchorTypeKey(), propMap, factMap);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testTranslateBusinessRule_SubsetIntersection_StaticFact() throws Exception {
    	YieldValueFunctionInfo yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
    	YieldValueFunctionInfo yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setStaticFact(true);
		factStructure1.setStaticValue("CPR101");
		factStructure1.setStaticValueDataType(String.class.getName());
		FactStructureInfo factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setStaticFact(true);
		factStructure2.setStaticValue("CPR101,MATH101,CHEM101");
		factStructure2.setStaticValueDataType(String.class.getName());
		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
		FactStructureInfo factStructure3 = createFactStructure("subset.id.3", "course.subset.criteria");
		factStructure3.setStaticFact(true);
		factStructure3.setStaticValue("MATH102");
		factStructure3.setStaticValueDataType(String.class.getName());
		FactStructureInfo factStructure4 = createFactStructure("subset.id.4", "course.subset.fact");
		factStructure4.setStaticFact(true);
		factStructure4.setStaticValue("CPR102,MATH102,CHEM102");
		factStructure4.setStaticValueDataType(String.class.getName());
		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

		List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();
        RuleElementInfo element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction1));
        RuleElementInfo element2 = dtoFactory.createRuleElementDTO(
        		"element-2", "PROPOSITION", 
        		Integer.valueOf(1), getRuleProposition(
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfo businessRule = createBusinessRule(ruleElementList);
        businessRule.setAnchor("CPR101");

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(businessRule);
        assertNotNull(ruleSet);
        
    	Map<String, RulePropositionInfo> propMap = BusinessRuleUtil.getRulePropositions(businessRule);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), businessRule.getAnchor(), businessRule.getAnchorTypeKey(), propMap, null);

        // Execute rule
        executeRule(ruleSet.getContent(), facts);
        assertTrue(facts.getPropositionContainer().getRuleResult());
    }

    @Test
    public void testRule_NullRuleElements() throws Exception {
        // Rule elements
    	List<RuleElementInfo> ruleElementList = null;

        // Create functional business rule
        BusinessRuleInfo bri = createBusinessRule(ruleElementList);

        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        assertNotNull(ruleSet);
    }

    @Test
    public void testRule_EmptyRuleElements() throws Exception {
        // Rule elements
    	List<RuleElementInfo> ruleElementList = new ArrayList<RuleElementInfo>();

        // Create functional business rule
        BusinessRuleInfo bri = createBusinessRule(ruleElementList);

        // Parse and generate functional business rule into Drools rules
        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        assertNotNull(ruleSet);
    }

    @Test
    public void testRule_InvalidRuleName_InvalidCharacters1() throws Exception {
        // Create functional business rule
        BusinessRuleInfo bri = createBusinessRule(null);
        // Create invalid name
        bri.setId("11223344-1122-1122-1112-200000000001");
        bri.setType("org_kuali-student.pre req#1&2");
        bri.setName("TestName `~!@#$%^&*()-+={[}]|\\:;\"'<,>.?/ \b\t\n\f\r \' \"");

        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        String name = RuleSetTranslatorDroolsImpl.getRuleSetName(bri);
        assertNotNull(ruleSet);
        assertEquals(name, ruleSet.getName());
    }

    @Test
    public void testRule_InvalidRuleName_InvalidCharacters2() throws Exception {
        // Create functional business rule
        BusinessRuleInfo bri = createBusinessRule(null);
        // Create invalid name
        bri.setId("11223344-1122-1122-1112-200000000001");
        bri.setType("org_kuali-student.pre req#1&2");
        bri.setName("ABC123456 `~!@#$%^&*()-+= XYZ");

        RuleSet ruleSet = ruleSetTranslator.translate(bri);
        String name = RuleSetTranslatorDroolsImpl.getRuleSetName(bri);
        assertNotNull(ruleSet);
        assertEquals(name, ruleSet.getName());
    }

    @Test
    public void testRule_InvalidPackageAndRuleName_InvalidNumber() throws Exception {
        // Create functional business rule
        BusinessRuleInfo bri = createBusinessRule(null);
        // Create invalid name
        bri.setId("11223344-1122-1122-1112-200000000001");
        bri.setType("123 kuali-student.pre req#1&2");
        bri.setName("123456 `~!@#$%^&*()-+= XYZ");

		try {
        	ruleSetTranslator.translate(bri);
        fail("Translator should have thrown an invalid rule exception");
        } catch(RuleSetTranslatorException e) {
        	assertNotNull(e.getMessage());
        }
    }
}

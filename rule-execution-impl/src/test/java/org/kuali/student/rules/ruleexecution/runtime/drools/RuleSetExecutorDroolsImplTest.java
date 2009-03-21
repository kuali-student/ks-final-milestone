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
package org.kuali.student.rules.ruleexecution.runtime.drools;

import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsExecutionStatistics;
import org.kuali.student.rules.ruleexecution.runtime.drools.util.DroolsTestUtil;
import org.kuali.student.rules.ruleexecution.runtime.drools.util.DroolsUtil;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.ruleexecution.util.RuleManagementDtoFactory;

public class RuleSetExecutorDroolsImplTest {

	private DroolsUtil droolsUtil = new DroolsUtil();

	/** Rule set executor interface */
	private static RuleSetExecutor executor = new RuleSetExecutorDroolsImpl();
	private static SimpleExecutor simpleExecutor = new SimpleExecutorDroolsImpl();
	private static DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();

    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
//        ReportBuilder reportBuilder = new RuleReportBuilder(simpleExecutor);
//        ((RuleSetExecutorDroolsImpl)executor).setReportBuilder(reportBuilder);
        ((RuleSetExecutorDroolsImpl)executor).setEnableExecutionLogging(true);
    	((RuleSetExecutorDroolsImpl)executor).setRuleBaseCache(ruleBase);
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    	executor = null;
    	ruleBase = null;
    }

    @Before
    public void setUp() throws Exception {
    	executor.clearRuleSetCache();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    private FactStructureDTO createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureDTO factStructure1 = new FactStructureDTO();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfoDTO criteriaTypeInfo1 = new FactCriteriaTypeInfoDTO();
	    criteriaTypeInfo1.setName(criteriaTypeName);
	    factStructure1.setCriteriaTypeInfo(criteriaTypeInfo1);
	    return factStructure1;
    }

    private RulePropositionDTO createRuleProposition(
    		String yieldValueFunctionType, 
    		String comparisonOperator, 
    		String expectedValue,
    		String comparisonOperatorType,
    		YieldValueFunctionDTO yieldValueFunction) {
		LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		"co-requisites", comparisonOperatorType, 
        		comparisonOperator, leftSide, rightSide);
        return ruleProp;
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	return cal.getTime();
    }

    private BusinessRuleInfoDTO generateNewBusinessRuleInfo(
    		String businessRuleId, 
    		String ruleName, 
    		String anchor) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Kamal");

        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setId(businessRuleId);
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);
        brInfoDTO.setType("PreReqTypeKey");

        //brInfoDTO.setEffectiveStartTime(new Date());
        //brInfoDTO.setEffectiveEndTime(new Date());
        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
        brInfoDTO.setEffectiveDate(effectiveStartTime);
        brInfoDTO.setExpirationDate(effectiveEndTime);

        return brInfoDTO;
    }

	@Test
    public void testClearRuleSetCache() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfoDTO brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetDTO ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		executor.clearRuleSetCache();
		Assert.assertFalse(executor.containsRuleSet(brInfoAverage));
	}
	
	@Test
    public void testAddRuleSet() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfoDTO brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetDTO ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		Assert.assertTrue(executor.containsRuleSet(brInfoAverage));
	}
	
	@Test
    public void testAddRuleSet_SameAnchorValue() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfoDTO brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetDTO ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);

        // Business rule 2 - Intersection proposition
        BusinessRuleInfoDTO brInfoIntersection = generateNewBusinessRuleInfo("2", "Business Rule - Intersection", "CPR101");

		RuleSetDTO ruleSetIntersection = DroolsTestUtil.getIntersectionPropositionRuleSet();
    	byte[] bytes2 = DroolsTestUtil.createKnowledgePackage(ruleSetIntersection);
    	ruleSetIntersection.setCompiledRuleSet(bytes2);
    	
		try {
			executor.addRuleSet(brInfoIntersection, ruleSetIntersection);
			Assert.fail("Rule base already contains a business rule (id="+
					brInfoIntersection.getId() +
    				") with anchor value '" +brInfoIntersection.getAnchorValue() + "'");
		} catch(RuleSetExecutionException e) {
			Assert.assertTrue(e.getMessage().startsWith("Rule base already contains a business rule"));
		}
	}
	
	@Test
    public void testRemoveRuleSet() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfoDTO brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetDTO ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		executor.removeRuleSet(brInfoAverage, ruleSetAverage);
		Assert.assertFalse(executor.containsRuleSet(brInfoAverage));
	}
	
	@Test
    public void testContainsRuleSet() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfoDTO brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetDTO ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		Assert.assertTrue(executor.containsRuleSet(brInfoAverage));
	}
	
	/*@Test
    public void testExecuteAgenda() throws Exception {
		// Create functional business rule
		YieldValueFunctionDTO yvfAverage = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
    	YieldValueFunctionDTO yvfIntersection = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> averageResultColumnKey = new HashMap<String, String>();
		averageResultColumnKey.put(YVFAverageProposition.AVERAGE_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(averageResultColumnKey);
		
		yvfAverage.setFactStructureList(Arrays.asList(factStructure1));
		
		Map<String,String> intersectionResultColumnKey = new HashMap<String, String>();
		intersectionResultColumnKey.put(YVFIntersectionProposition.INTERSECTION_COLUMN_KEY, "column1");
		
		FactStructureDTO factStructure3 = createFactStructure("subset.id.2", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(intersectionResultColumnKey);
		FactStructureDTO factStructure4 = createFactStructure("subset.id.3", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(intersectionResultColumnKey);

		yvfIntersection.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

    	String factKeyAverage = FactUtil.createFactKey(factStructure1);

    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(factStructure3);
    	String factKeyIntersection = FactUtil.createFactKey(factStructure4);
		
		RulePropositionDTO propositionAverage = createRuleProposition(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfAverage);

		RulePropositionDTO propositionIntersection = createRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfIntersection);
		
		//Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        RuleElementDTO elementAverage = new RuleElementDTO();
        elementAverage.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        elementAverage.setBusinessRuleProposition(propositionAverage);

        RuleElementDTO elementIntersection = new RuleElementDTO();
        elementIntersection.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        elementIntersection.setBusinessRuleProposition(propositionIntersection);

        List<RuleElementDTO> elementList1 = new ArrayList<RuleElementDTO>();
        elementList1.add(elementAverage);
        List<RuleElementDTO> elementList2 = new ArrayList<RuleElementDTO>();
        elementList2.add(elementIntersection);

        // Business rule 1 - Average proposition
        BusinessRuleInfoDTO brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR201");
        brInfoAverage.setBusinessRuleElementList(elementList1);

        // Business rule 2 - Intersection proposition
        BusinessRuleInfoDTO brInfoIntersection = generateNewBusinessRuleInfo("2", "Business Rule - Intersection", "CPR301");
        brInfoIntersection.setBusinessRuleElementList(elementList2);

		// Agenda
        RuntimeAgendaDTO agenda = new RuntimeAgendaDTO();
        List<BusinessRuleInfoDTO> briList = new ArrayList<BusinessRuleInfoDTO>();
        briList.add(brInfoAverage);
        briList.add(brInfoIntersection);
        agenda.setBusinessRules(briList);

        // EXECUTION: Create facts
    	FactResultTypeInfoDTO columnMetaData1 = DroolsTestUtil.createColumnMetaData(BigDecimal.class.getName());
    	FactResultDTO factResultAverage = DroolsTestUtil.createFactResult(new String[] {"85.0","75.0","80.0"});
    	factResultAverage.setFactResultTypeInfo(columnMetaData1);
    	
    	FactResultTypeInfoDTO columnMetaData2 = DroolsTestUtil.createColumnMetaData(String.class.getName());
        FactResultDTO factResultIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResultIntersection.setFactResultTypeInfo(columnMetaData2);

    	FactResultDTO factResultCriteriaIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101"});
    	factResultCriteriaIntersection.setFactResultTypeInfo(columnMetaData2);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKeyAverage, factResultAverage);
        factMap.put(criteriaKeyIntersection, factResultCriteriaIntersection);
        factMap.put(factKeyIntersection, factResultIntersection);

    	// Create rule set
		RuleSetDTO ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createPackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		RuleSetDTO ruleSetIntersection = DroolsTestUtil.getIntersectionPropositionRuleSet();
    	byte[] bytes2 = DroolsTestUtil.createPackage(ruleSetIntersection);
    	ruleSetIntersection.setCompiledRuleSet(bytes2);
    	
    	executor.addRuleSet(brInfoAverage, ruleSetAverage);
    	executor.addRuleSet(brInfoIntersection, ruleSetIntersection);

    	// Execute ruleset and fact
        AgendaExecutionResult executionResult = executor.execute(agenda, factMap);
        Assert.assertNotNull(executionResult);
        ExecutionResult result1 = executionResult.getExecutionResultList().get(0);
        Assert.assertEquals("1", result1.getId());
        Assert.assertNotNull(result1.getResults());
        Assert.assertTrue(result1.getExecutionResult());
        Assert.assertTrue(result1.getReport().isSuccessful());
        Assert.assertEquals(1, result1.getReport().getPropositionReports().size());
        Assert.assertTrue(result1.getReport().getPropositionReports().get(0).isSuccessful());

        ExecutionResult result2 = executionResult.getExecutionResultList().get(1);
        Assert.assertEquals("2", result2.getId());
        Assert.assertNotNull(result2.getResults());
        Assert.assertTrue(result2.getExecutionResult());
        Assert.assertTrue(result2.getReport().isSuccessful());
        Assert.assertEquals(1, result2.getReport().getPropositionReports().size());
        Assert.assertTrue(result2.getReport().getPropositionReports().get(0).isSuccessful());
	}*/

	@Test
    public void testExecuteAverageIntersectionProposition_DroolsRuleSet() throws Exception {
        // Rule set source code
    	RuleSetDTO ruleSet = DroolsTestUtil.getAverageIntersectionPropositionRuleSet();

    	byte[] bytes = DroolsTestUtil.createKnowledgePackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

    	YieldValueFunctionDTO yvfAverage = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
    	YieldValueFunctionDTO yvfIntersection = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> averageResultColumnKey = new HashMap<String, String>();
		averageResultColumnKey.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(averageResultColumnKey);

		yvfAverage.setFactStructureList(Arrays.asList(factStructure1));
		
		Map<String,String> subsetResultColumnKey = new HashMap<String, String>();
		subsetResultColumnKey.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "column1");

		FactStructureDTO factStructure3 = createFactStructure("subset.id.2", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(subsetResultColumnKey);
		FactStructureDTO factStructure4 = createFactStructure("subset.id.3", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(subsetResultColumnKey);
		yvfIntersection.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

    	String factKeyAverage = FactUtil.createFactKey(factStructure1);

    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(factStructure3);
    	String factKeyIntersection = FactUtil.createFactKey(factStructure4);
		
		RulePropositionDTO propositionAverage = createRuleProposition(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfAverage);
		RulePropositionDTO propositionIntersection = createRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfIntersection);
		//Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        RuleElementDTO element1 = new RuleElementDTO();
        element1.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element1.setBusinessRuleProposition(propositionAverage);
        RuleElementDTO element2 = new RuleElementDTO();
        element2.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element2.setBusinessRuleProposition(propositionIntersection);

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(element1);
        elementList.add(element2);

        BusinessRuleInfoDTO brInfo = generateNewBusinessRuleInfo("1", "Business Rule", "CPR101");
        brInfo.setBusinessRuleElementList(elementList);
        
        // EXECUTION: Create facts
    	FactResultTypeInfoDTO columnMetaData1 = DroolsTestUtil.createColumnMetaData(BigDecimal.class.getName());
    	FactResultDTO factResultAverage = DroolsTestUtil.createFactResult(new String[] {"85.0","75.0","80.0"});
    	factResultAverage.setFactResultTypeInfo(columnMetaData1);
    	
    	FactResultTypeInfoDTO columnMetaData2 = DroolsTestUtil.createColumnMetaData(String.class.getName());
        FactResultDTO factResultIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResultIntersection.setFactResultTypeInfo(columnMetaData2);

    	FactResultDTO factResultCriteriaIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101"});
    	factResultCriteriaIntersection.setFactResultTypeInfo(columnMetaData2);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKeyAverage, factResultAverage);
        factMap.put(criteriaKeyIntersection, factResultCriteriaIntersection);
        factMap.put(factKeyIntersection, factResultIntersection);

        // Execute ruleset and fact
		executor.addRuleSet(brInfo, ruleSet);
        ExecutionResult result = executor.execute(brInfo, factMap);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getResults());
        Assert.assertNotNull(result.getExecutionLog());
//        Assert.assertTrue(result.getReport().isSuccessful());
//        Assert.assertEquals(2, result.getReport().getPropositionReports().size());
//        Assert.assertTrue(result.getReport().getPropositionReports().get(0).isSuccessful());
//        Assert.assertTrue(result.getReport().getPropositionReports().get(1).isSuccessful());
	}

	@Test
    public void testExecuteAverageIntersectionProposition_DroolsRuleSet_MultipleExecutions() throws Exception {
        // Rule set source code
    	RuleSetDTO ruleSet = DroolsTestUtil.getAverageIntersectionPropositionRuleSet();

    	byte[] bytes = DroolsTestUtil.createKnowledgePackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

    	YieldValueFunctionDTO yvfAverage = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
    	YieldValueFunctionDTO yvfIntersection = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> averageResultColumnKey = new HashMap<String, String>();
		averageResultColumnKey.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(averageResultColumnKey);

		yvfAverage.setFactStructureList(Arrays.asList(factStructure1));
		
		Map<String,String> intersectionResultColumnKey = new HashMap<String, String>();
		intersectionResultColumnKey.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "column1");

		FactStructureDTO factStructure3 = createFactStructure("subset.id.2", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(intersectionResultColumnKey);
		FactStructureDTO factStructure4 = createFactStructure("subset.id.3", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(intersectionResultColumnKey);
		yvfIntersection.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

    	String factKeyAverage = FactUtil.createFactKey(factStructure1);

    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(factStructure3);
    	String factKeyIntersection = FactUtil.createFactKey(factStructure4);
		
		RulePropositionDTO propositionAverage = createRuleProposition(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfAverage);
		RulePropositionDTO propositionIntersection = createRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfIntersection);
		//Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        RuleElementDTO element1 = new RuleElementDTO();
        element1.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element1.setBusinessRuleProposition(propositionAverage);
        RuleElementDTO element2 = new RuleElementDTO();
        element2.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element2.setBusinessRuleProposition(propositionIntersection);

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(element1);
        elementList.add(element2);

        BusinessRuleInfoDTO brInfo = generateNewBusinessRuleInfo("1", "Business Rule", "CPR101");
        brInfo.setBusinessRuleElementList(elementList);
        
        // EXECUTION: Create facts
    	FactResultTypeInfoDTO columnMetaData1 = DroolsTestUtil.createColumnMetaData(BigDecimal.class.getName());
    	FactResultDTO factResultAverage = DroolsTestUtil.createFactResult(new String[] {"85.0","75.0","80.0"});
    	factResultAverage.setFactResultTypeInfo(columnMetaData1);
    	
    	FactResultTypeInfoDTO columnMetaData2 = DroolsTestUtil.createColumnMetaData(String.class.getName());
        FactResultDTO factResultIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResultIntersection.setFactResultTypeInfo(columnMetaData2);

    	FactResultDTO factResultCriteriaIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101"});
    	factResultCriteriaIntersection.setFactResultTypeInfo(columnMetaData2);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKeyAverage, factResultAverage);
        factMap.put(criteriaKeyIntersection, factResultCriteriaIntersection);
        factMap.put(factKeyIntersection, factResultIntersection);

    	// Enable rule execution statistics
        ((RuleSetExecutorDroolsImpl)executor).setEnableStatLogging(true);
        DroolsExecutionStatistics droolsStats = ((RuleSetExecutorDroolsImpl)executor).getStatistics();

    	// Execute ruleset and fact
		executor.addRuleSet(brInfo, ruleSet);
        for(int i=0; i<10; i++) {
        	ExecutionResult result = executor.execute(brInfo, factMap);
	        Assert.assertNotNull(result);
	        Assert.assertNotNull(result.getResults());
	        Assert.assertNotNull(result.getExecutionLog());
//	        Assert.assertTrue(result.getReport().isSuccessful());
        }

	    System.out.println(droolsUtil.getStatisticsSummary(droolsStats));
	}

}

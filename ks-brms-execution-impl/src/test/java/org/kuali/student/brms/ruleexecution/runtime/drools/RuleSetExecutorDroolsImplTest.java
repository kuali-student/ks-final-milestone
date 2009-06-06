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
package org.kuali.student.brms.ruleexecution.runtime.drools;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactCriteriaTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.internal.common.entity.AnchorTypeKey;
import org.kuali.student.brms.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.brms.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.brms.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.utils.FactUtil;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.brms.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.brms.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.drools.logging.DroolsExecutionStatistics;
import org.kuali.student.brms.ruleexecution.runtime.drools.util.DroolsTestUtil;
import org.kuali.student.brms.ruleexecution.runtime.drools.util.DroolsUtil;
import org.kuali.student.brms.ruleexecution.util.RuleManagementDtoFactory;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;
import org.kuali.student.core.dto.MetaInfo;

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
    
    private FactStructureInfo createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureInfo factStructure1 = new FactStructureInfo();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfo criteriaTypeInfo1 = new FactCriteriaTypeInfo();
	    criteriaTypeInfo1.setName(criteriaTypeName);
	    factStructure1.setCriteriaTypeInfo(criteriaTypeInfo1);
	    return factStructure1;
    }

    private RulePropositionInfo createRuleProposition(
    		String yieldValueFunctionType, 
    		String comparisonOperator, 
    		String expectedValue,
    		String comparisonOperatorType,
    		YieldValueFunctionInfo yieldValueFunction) {
		LeftHandSideInfo leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideInfo rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionInfo ruleProp = dtoFactory.createRulePropositionDTO(
        		"co-requisites", comparisonOperatorType, 
        		comparisonOperator, leftSide, rightSide);
        return ruleProp;
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	return cal.getTime();
    }

    private BusinessRuleInfo generateNewBusinessRuleInfo(
    		String businessRuleId, 
    		String ruleName, 
    		String anchor) {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateId("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateId("Kamal");

        BusinessRuleInfo brInfoDTO = new BusinessRuleInfo();
        brInfoDTO.setId(businessRuleId);
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchor(anchor);
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
        BusinessRuleInfo brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetInfo ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		executor.clearRuleSetCache();
		Assert.assertFalse(executor.containsRuleSet(brInfoAverage));
	}
	
	@Test
    public void testAddRuleSet() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfo brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetInfo ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		Assert.assertTrue(executor.containsRuleSet(brInfoAverage));
	}
	
	@Test
    public void testAddRuleSet_SameAnchorValue() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfo brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetInfo ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);

        // Business rule 2 - Intersection proposition
        BusinessRuleInfo brInfoIntersection = generateNewBusinessRuleInfo("2", "Business Rule - Intersection", "CPR101");

		RuleSetInfo ruleSetIntersection = DroolsTestUtil.getIntersectionPropositionRuleSet();
    	byte[] bytes2 = DroolsTestUtil.createKnowledgePackage(ruleSetIntersection);
    	ruleSetIntersection.setCompiledRuleSet(bytes2);
    	
		try {
			executor.addRuleSet(brInfoIntersection, ruleSetIntersection);
			Assert.fail("Rule base already contains a business rule (id="+
					brInfoIntersection.getId() +
    				") with anchor value '" +brInfoIntersection.getAnchor() + "'");
		} catch(RuleSetExecutionException e) {
			Assert.assertTrue(e.getMessage().startsWith("Rule base already contains a business rule"));
		}
	}
	
	@Test
    public void testRemoveRuleSet() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfo brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetInfo ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		executor.removeRuleSet(brInfoAverage, ruleSetAverage);
		Assert.assertFalse(executor.containsRuleSet(brInfoAverage));
	}
	
	@Test
    public void testContainsRuleSet() throws Exception {
        // Business rule 1 - Average proposition
        BusinessRuleInfo brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR101");

        // Create rule set
		RuleSetInfo ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createKnowledgePackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		executor.addRuleSet(brInfoAverage, ruleSetAverage);
		Assert.assertTrue(executor.containsRuleSet(brInfoAverage));
	}
	
	/*@Test
    public void testExecuteAgenda() throws Exception {
		// Create functional business rule
		YieldValueFunctionInfo yvfAverage = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
    	YieldValueFunctionInfo yvfIntersection = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> averageResultColumnKey = new HashMap<String, String>();
		averageResultColumnKey.put(YVFAverageProposition.AVERAGE_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(averageResultColumnKey);
		
		yvfAverage.setFactStructureList(Arrays.asList(factStructure1));
		
		Map<String,String> intersectionResultColumnKey = new HashMap<String, String>();
		intersectionResultColumnKey.put(YVFIntersectionProposition.INTERSECTION_COLUMN_KEY, "column1");
		
		FactStructureInfo factStructure3 = createFactStructure("subset.id.2", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(intersectionResultColumnKey);
		FactStructureInfo factStructure4 = createFactStructure("subset.id.3", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(intersectionResultColumnKey);

		yvfIntersection.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

    	String factKeyAverage = FactUtil.createFactKey(factStructure1);

    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(factStructure3);
    	String factKeyIntersection = FactUtil.createFactKey(factStructure4);
		
		RulePropositionInfo propositionAverage = createRuleProposition(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfAverage);

		RulePropositionInfo propositionIntersection = createRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfIntersection);
		
		//Map<String, RulePropositionInfo> propositionMap = new HashMap<String, RulePropositionInfo>();
        RuleElementInfo elementAverage = new RuleElementInfo();
        elementAverage.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        elementAverage.setBusinessRuleProposition(propositionAverage);

        RuleElementInfo elementIntersection = new RuleElementInfo();
        elementIntersection.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        elementIntersection.setBusinessRuleProposition(propositionIntersection);

        List<RuleElementInfo> elementList1 = new ArrayList<RuleElementInfo>();
        elementList1.add(elementAverage);
        List<RuleElementInfo> elementList2 = new ArrayList<RuleElementInfo>();
        elementList2.add(elementIntersection);

        // Business rule 1 - Average proposition
        BusinessRuleInfo brInfoAverage = generateNewBusinessRuleInfo("1", "Business Rule - Average", "CPR201");
        brInfoAverage.setBusinessRuleElementList(elementList1);

        // Business rule 2 - Intersection proposition
        BusinessRuleInfo brInfoIntersection = generateNewBusinessRuleInfo("2", "Business Rule - Intersection", "CPR301");
        brInfoIntersection.setBusinessRuleElementList(elementList2);

		// Agenda
        RuntimeAgendaInfo agenda = new RuntimeAgendaInfo();
        List<BusinessRuleInfo> briList = new ArrayList<BusinessRuleInfo>();
        briList.add(brInfoAverage);
        briList.add(brInfoIntersection);
        agenda.setBusinessRules(briList);

        // EXECUTION: Create facts
    	FactResultTypeInfo columnMetaData1 = DroolsTestUtil.createColumnMetaData(BigDecimal.class.getName());
    	FactResultInfo factResultAverage = DroolsTestUtil.createFactResult(new String[] {"85.0","75.0","80.0"});
    	factResultAverage.setFactResultTypeInfo(columnMetaData1);
    	
    	FactResultTypeInfo columnMetaData2 = DroolsTestUtil.createColumnMetaData(String.class.getName());
        FactResultInfo factResultIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResultIntersection.setFactResultTypeInfo(columnMetaData2);

    	FactResultInfo factResultCriteriaIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101"});
    	factResultCriteriaIntersection.setFactResultTypeInfo(columnMetaData2);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKeyAverage, factResultAverage);
        factMap.put(criteriaKeyIntersection, factResultCriteriaIntersection);
        factMap.put(factKeyIntersection, factResultIntersection);

    	// Create rule set
		RuleSetInfo ruleSetAverage = DroolsTestUtil.getAveragePropositionRuleSet();
    	byte[] bytes1 = DroolsTestUtil.createPackage(ruleSetAverage);
    	ruleSetAverage.setCompiledRuleSet(bytes1);

		RuleSetInfo ruleSetIntersection = DroolsTestUtil.getIntersectionPropositionRuleSet();
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
    	RuleSetInfo ruleSet = DroolsTestUtil.getAverageIntersectionPropositionRuleSet();

    	byte[] bytes = DroolsTestUtil.createKnowledgePackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

    	YieldValueFunctionInfo yvfAverage = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
    	YieldValueFunctionInfo yvfIntersection = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> averageResultColumnKey = new HashMap<String, String>();
		averageResultColumnKey.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(averageResultColumnKey);

		yvfAverage.setFactStructureList(Arrays.asList(factStructure1));
		
		Map<String,String> subsetResultColumnKey = new HashMap<String, String>();
		subsetResultColumnKey.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "column1");

		FactStructureInfo factStructure3 = createFactStructure("subset.id.2", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(subsetResultColumnKey);
		FactStructureInfo factStructure4 = createFactStructure("subset.id.3", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(subsetResultColumnKey);
		yvfIntersection.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

    	String factKeyAverage = FactUtil.createFactKey(factStructure1);

    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(factStructure3);
    	String factKeyIntersection = FactUtil.createFactKey(factStructure4);
		
		RulePropositionInfo propositionAverage = createRuleProposition(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfAverage);
		RulePropositionInfo propositionIntersection = createRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfIntersection);
		//Map<String, RulePropositionInfo> propositionMap = new HashMap<String, RulePropositionInfo>();
        RuleElementInfo element1 = new RuleElementInfo();
        element1.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element1.setBusinessRuleProposition(propositionAverage);
        RuleElementInfo element2 = new RuleElementInfo();
        element2.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element2.setBusinessRuleProposition(propositionIntersection);

        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(element1);
        elementList.add(element2);

        BusinessRuleInfo brInfo = generateNewBusinessRuleInfo("1", "Business Rule", "CPR101");
        brInfo.setBusinessRuleElementList(elementList);
        
        // EXECUTION: Create facts
    	FactResultTypeInfo columnMetaData1 = DroolsTestUtil.createColumnMetaData(BigDecimal.class.getName());
    	FactResultInfo factResultAverage = DroolsTestUtil.createFactResult(new String[] {"85.0","75.0","80.0"});
    	factResultAverage.setFactResultTypeInfo(columnMetaData1);
    	
    	FactResultTypeInfo columnMetaData2 = DroolsTestUtil.createColumnMetaData(String.class.getName());
        FactResultInfo factResultIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResultIntersection.setFactResultTypeInfo(columnMetaData2);

    	FactResultInfo factResultCriteriaIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101"});
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
    	RuleSetInfo ruleSet = DroolsTestUtil.getAverageIntersectionPropositionRuleSet();

    	byte[] bytes = DroolsTestUtil.createKnowledgePackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

    	YieldValueFunctionInfo yvfAverage = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.AVERAGE.toString());
    	YieldValueFunctionInfo yvfIntersection = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureInfo factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");

		Map<String,String> averageResultColumnKey = new HashMap<String, String>();
		averageResultColumnKey.put(MessageContextConstants.PROPOSITION_AVERAGE_COLUMN_KEY, "column1");
		factStructure1.setResultColumnKeyTranslations(averageResultColumnKey);

		yvfAverage.setFactStructureList(Arrays.asList(factStructure1));
		
		Map<String,String> intersectionResultColumnKey = new HashMap<String, String>();
		intersectionResultColumnKey.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "column1");

		FactStructureInfo factStructure3 = createFactStructure("subset.id.2", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(intersectionResultColumnKey);
		FactStructureInfo factStructure4 = createFactStructure("subset.id.3", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(intersectionResultColumnKey);
		yvfIntersection.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

    	String factKeyAverage = FactUtil.createFactKey(factStructure1);

    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(factStructure3);
    	String factKeyIntersection = FactUtil.createFactKey(factStructure4);
		
		RulePropositionInfo propositionAverage = createRuleProposition(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfAverage);
		RulePropositionInfo propositionIntersection = createRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfIntersection);
		//Map<String, RulePropositionInfo> propositionMap = new HashMap<String, RulePropositionInfo>();
        RuleElementInfo element1 = new RuleElementInfo();
        element1.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element1.setBusinessRuleProposition(propositionAverage);
        RuleElementInfo element2 = new RuleElementInfo();
        element2.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        element2.setBusinessRuleProposition(propositionIntersection);

        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(element1);
        elementList.add(element2);

        BusinessRuleInfo brInfo = generateNewBusinessRuleInfo("1", "Business Rule", "CPR101");
        brInfo.setBusinessRuleElementList(elementList);
        
        // EXECUTION: Create facts
    	FactResultTypeInfo columnMetaData1 = DroolsTestUtil.createColumnMetaData(BigDecimal.class.getName());
    	FactResultInfo factResultAverage = DroolsTestUtil.createFactResult(new String[] {"85.0","75.0","80.0"});
    	factResultAverage.setFactResultTypeInfo(columnMetaData1);
    	
    	FactResultTypeInfo columnMetaData2 = DroolsTestUtil.createColumnMetaData(String.class.getName());
        FactResultInfo factResultIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResultIntersection.setFactResultTypeInfo(columnMetaData2);

    	FactResultInfo factResultCriteriaIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101"});
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

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
package org.kuali.student.rules.integration;

import org.junit.Assert;

import java.util.ArrayList;
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
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.utils.ServiceFactory;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;

public class ServiceTestClient {

    private static String ruleManagementServiceURL = "http://localhost:8080/brms-ws-0.0.1-SNAPSHOT/services/RuleManagementService";
    private static String ruleManagementNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement";
    private static String ruleManagementServiceName = "RuleManagementService";
    private static String ruleManagementServiceInterface = RuleManagementService.class.getName();

    private static String ruleRepositoryServiceURL = "http://localhost:8080/brms-ws-0.0.1-SNAPSHOT/services/RuleRepositoryService";
    private static String ruleRepositoryNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository";
    private static String ruleRepositoryServiceName = "RuleRepositoryService";
    private static String ruleRepositoryServiceInterface = RuleRepositoryService.class.getName();

    private static String ruleExecutionServiceURL = "http://localhost:8080/brms-ws-0.0.1-SNAPSHOT/services/RuleExecutionService";
    private static String ruleExecutionNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution";
    private static String ruleExecutionServiceName = "RuleExecutionService";
    private static String ruleExecutionServiceInterface = RuleExecutionService.class.getName();

    private static String factServiceURL = "http://localhost:8080/brms-ws-0.0.1-SNAPSHOT/services/FactFinderService";
    private static String factNamespace = "http://student.kuali.org/wsdl/brms/FactFinder";
    private static String factServiceName = "FactFinderService";
    private static String factServiceInterface = FactFinderService.class.getName();

    private static RuleManagementService ruleManagementService;
    private static RuleRepositoryService ruleRepositoryService;
    private static RuleExecutionService ruleExecutionService;
    private static FactFinderService factFinderService;
    
	@BeforeClass
    public static void setUpOnce() throws Exception {
    	ruleManagementService = (RuleManagementService) ServiceFactory.getPort(
    			ruleManagementServiceURL + "?wsdl", 
    			ruleManagementNamespace, 
    			ruleManagementServiceName, 
    			ruleManagementServiceInterface, 
    			ruleManagementServiceURL);

    	ruleRepositoryService = (RuleRepositoryService) ServiceFactory.getPort(
    			ruleRepositoryServiceURL + "?wsdl", 
    			ruleRepositoryNamespace, 
    			ruleRepositoryServiceName, 
    			ruleRepositoryServiceInterface, 
    			ruleRepositoryServiceURL);

    	ruleExecutionService = (RuleExecutionService) ServiceFactory.getPort(
    			ruleExecutionServiceURL + "?wsdl", 
    			ruleExecutionNamespace, 
    			ruleExecutionServiceName, 
    			ruleExecutionServiceInterface, 
    			ruleExecutionServiceURL);

    	factFinderService = (FactFinderService) ServiceFactory.getPort(
    			factServiceURL + "?wsdl", 
    			factNamespace, 
    			factServiceName, 
    			factServiceInterface, 
    			factServiceURL);
	}

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	return cal.getTime();
    }

    /*
     * Builds the fact structure to be used as a criteria
     */
    private FactStructureDTO buildFactStructureForRuleCriteria(boolean staticFact) {
        FactStructureDTO fs = new FactStructureDTO();
        fs.setFactStructureId("1");
        fs.setFactTypeKey("fact.clusetId");
        fs.setAnchorFlag(false);
        fs.setStaticFact(staticFact);
        if (staticFact) {
	        fs.setStaticValue("CPR101");
	        fs.setStaticValueDataType(String.class.getName());
        } else {
		    Map<String, String> paramMap = new HashMap<String, String>();
		    paramMap.put("factParam.clusetId", "PSYC 200");
		    fs.setParamValueMap(paramMap);
        }        

        return fs;
    }

    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureDTO buildFactStructureForIntersection(boolean staticFact) {
        FactStructureDTO fs = new FactStructureDTO();
        fs.setFactStructureId("2");
        fs.setFactTypeKey("fact.completed_course_list");
        fs.setAnchorFlag(false);
        fs.setStaticFact(staticFact);
        if (staticFact) {
        	fs.setStaticValue("CPR101, CPR201, CPR301");
            fs.setStaticValueDataType(String.class.getName());
        } else {
		    Map<String, String> paramMap = new HashMap<String, String>();
		    paramMap.put("factParam.studentId", "student1");
		    fs.setParamValueMap(paramMap);
        }        
        return fs;                
    }

    private BusinessRuleInfoDTO generateNewBusinessRuleInfo(String ruleName, String anchor) {
    	return generateNewBusinessRuleInfo(ruleName, anchor, true);
    }

    private BusinessRuleInfoDTO generateNewBusinessRuleInfo(String ruleName, String anchor, boolean staticFact) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
     
        FactStructureDTO fs1 = buildFactStructureForRuleCriteria(staticFact);
        FactStructureDTO fs2 = buildFactStructureForIntersection(staticFact);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(fs1);
        factStructureList.add(fs2);
        
        YieldValueFunctionDTO yieldValueFunctionDTO = new YieldValueFunctionDTO();
        yieldValueFunctionDTO.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION.toString());
        yieldValueFunctionDTO.setFactStructureList(factStructureList);

        LeftHandSideDTO leftHandSideDTO = new LeftHandSideDTO();
        leftHandSideDTO.setYieldValueFunction(yieldValueFunctionDTO);

        RightHandSideDTO rightHandSideDTO = new RightHandSideDTO();
        rightHandSideDTO.setExpectedValue("12");

        RulePropositionDTO rulePropositionDTO = new RulePropositionDTO();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDescription("Credit Intersection Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataType(Double.class.getName());
        rulePropositionDTO.setComparisonOperatorType(ComparisonOperator.LESS_THAN.toString());

        RuleElementDTO reDTO = new RuleElementDTO();
        reDTO.setName("Pre-req 1");
        reDTO.setDescription("Pre req check for Math 101");
        reDTO.setOperation(RuleElementType.PROPOSITION.toString());
        reDTO.setRuleProposition(rulePropositionDTO);

        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDescription("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

        //brInfoDTO.setEffectiveStartTime(new Date());
        //brInfoDTO.setEffectiveEndTime(new Date());
        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
        brInfoDTO.setEffectiveStartTime(effectiveStartTime);
        brInfoDTO.setEffectiveEndTime(effectiveEndTime);

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO);

        brInfoDTO.setRuleElementList(elementList);
        
        return brInfoDTO;
    }
    
    private BusinessRuleInfoDTO generateNewEmptyBusinessRuleInfo(String businessRuleId, String ruleName, String anchor) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
     
        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setBusinessRuleId(businessRuleId);
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDescription("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

		Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
		Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
		brInfoDTO.setEffectiveStartTime(effectiveStartTime);
		brInfoDTO.setEffectiveEndTime(effectiveEndTime);
        
        return brInfoDTO;
    }
    
    @Test
    public void testFindBusinessRuleTypesFromTestBeans() throws Exception {
    	System.out.println("*****  testLoadBusinessRulesFromTestBeans  *****");

		List<String> businessRuleIdList1 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
		System.out.println("Business Rule ID1: "+businessRuleIdList1);
		List<String> businessRuleIdList2 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");
		System.out.println("Business Rule ID2: "+businessRuleIdList2);
    }
    
    @Test
    public void testCreateBusinessRule() throws Exception {
    	System.out.println("*****  testCreateBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
    		Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    		
    		System.out.println("Business Rule ID:   "+businessRule2.getBusinessRuleId());
	        System.out.println("Business Rule Name: "+businessRule2.getName());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateEmptyBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testCreateEmptyBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewEmptyBusinessRuleInfo("1000", "CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1);
	        System.out.println("Business Rule ID:   "+businessRuleId);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule Name: "+businessRule2.getName());
	        Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateAndFetchBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testCreateAndFetchBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule = generateNewBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule);
	        System.out.println("businessRuleId:         "+businessRuleId);
	        
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:       "+businessRule.getBusinessRuleId());
	        System.out.println("Business Rule Name:     "+businessRule.getName());
	        System.out.println("Business Compiled ID:   "+businessRule.getCompiledId());
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        System.out.println("RuleSet Name:           "+ruleSet.getName());
	        System.out.println("RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("RuleSet Source:\n"+ruleSet.getContent());
	        Assert.assertTrue(true);
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testUpdateBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testUpdateBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule = generateNewBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");
    	
    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule);
	        System.out.println("businessRuleId:         "+businessRuleId);
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        StatusDTO status = ruleManagementService.updateBusinessRule(businessRuleId, businessRule);
	        System.out.println("status:                 "+status);
	
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:       "+businessRule.getBusinessRuleId());
	        System.out.println("Business Rule Name:     "+businessRule.getName());
	        System.out.println("Business Compiled ID:   "+businessRule.getCompiledId());
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        System.out.println("RuleSet Name:           "+ruleSet.getName());
	        System.out.println("RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("RuleSet Source:\n"+ruleSet.getContent());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateBusinessRuleAndExecute() throws Exception {
    	System.out.println("\n\n*****  testCreateBusinessRuleAndExecute  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:        "+businessRule2.getBusinessRuleId());
	        System.out.println("Business Rule Name:      "+businessRule2.getName());

	        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);
	        System.out.println("Execution result:        "+executionResult.getExecutionResult());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
	        System.out.println("Execution log:\n"+executionResult.getExecutionLog());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testFindFactTypes() throws Exception {
    	System.out.println("\n\n*****  testFindFactTypes  *****");
    	List<FactTypeInfoDTO> factTypes = factFinderService.findFactTypes();
    	for(FactTypeInfoDTO factTypeInfo : factTypes) {
    		System.out.println("Fact type name:        "+factTypeInfo.getName());
    		System.out.println("Fact type description: "+factTypeInfo.getDescription());
    		System.out.println("Fact type key:         "+factTypeInfo.getFactTypeKey());
    	}
    }

    @Test
    public void testFetchDynamicFact() throws Exception {
    	System.out.println("\n\n*****  testFetchDynamicFact  *****");
        String factTypeKey = "fact.completed_course_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        paramMap.put("factParam.clusetId", "PSYC 200,PSYC 201,PSYC 202");
        
        FactStructureDTO factStructureDTO = new FactStructureDTO();
        factStructureDTO.setFactTypeKey(factTypeKey);
        factStructureDTO.setParamValueMap(paramMap);
        
        FactResultDTO result = factFinderService.fetchFact(factTypeKey, factStructureDTO);
        
        Assert.assertEquals(result.getFactResultTypeInfo().getKey(), "result.completedCourseInfo");
        Assert.assertEquals(1, result.getFactResultTypeInfo().getResultColumnsMap().size());
        
        Assert.assertEquals(3, result.getResultList().size());
        Assert.assertEquals("PSYC 200", result.getResultList().get(0).get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 201", result.getResultList().get(1).get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 202", result.getResultList().get(2).get("resultColumn.cluId"));        
    }
    
    @Test
    public void testCreateBusinessRuleAndExecute_DynamicFact() throws Exception {
    	System.out.println("\n\n*****  testCreateBusinessRuleAndExecute_DynamicFact  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100", false);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        //paramMap.put("factParam.clusetId", "CPR 101, MATH 101, MATH 102");

        FactStructureDTO factStructure1 = new FactStructureDTO();
        factStructure1.setFactStructureId("xxx");
        factStructure1.setStaticFact(false);
        factStructure1.setFactTypeKey("fact.completed_course_list");
        factStructure1.setParamValueMap(paramMap);
    	
    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:        "+businessRule2.getBusinessRuleId());
	        System.out.println("Business Rule Name :     "+businessRule2.getName());

	        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, factStructure1);
	        System.out.println("Execution result:        "+executionResult.getExecutionResult());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
	        System.out.println("Execution log:\n"+executionResult.getExecutionLog());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
}

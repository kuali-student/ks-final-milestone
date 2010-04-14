/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.ruleexecution.service;

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
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.brms.factfinder.service.FactFinderService;
import org.kuali.student.brms.internal.common.entity.AnchorTypeKey;
import org.kuali.student.brms.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.brms.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.brms.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.utils.ServiceFactory;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.repository.service.RuleRepositoryService;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ParameterMapInfo;
import org.kuali.student.brms.ruleexecution.dto.PropositionReportInfo;
import org.kuali.student.brms.ruleexecution.service.RuleExecutionService;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
//import org.kuali.student.brms.rulemanagement.dto.MetaInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;
import org.kuali.student.brms.rulemanagement.service.RuleManagementService;
import org.kuali.student.core.dto.MetaInfo;

public class ServiceTestClient {

    private final static String HOST = "http://localhost:8080/ks-brms";
	private static String ruleManagementServiceURL = HOST+"/services/RuleManagementService";
    private static String ruleManagementNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement";
    private static String ruleManagementServiceName = "RuleManagementService";
    private static String ruleManagementServiceInterface = RuleManagementService.class.getName();

    private static String ruleRepositoryServiceURL = HOST+"/services/RuleRepositoryService";
    private static String ruleRepositoryNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository";
    private static String ruleRepositoryServiceName = "RuleRepositoryService";
    private static String ruleRepositoryServiceInterface = RuleRepositoryService.class.getName();

    private static String ruleExecutionServiceURL = HOST+"/services/RuleExecutionService";
    private static String ruleExecutionNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution";
    private static String ruleExecutionServiceName = "RuleExecutionService";
    private static String ruleExecutionServiceInterface = RuleExecutionService.class.getName();

    private static String factServiceURL = HOST+"/services/FactFinderService";
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

    private boolean containsResult(List<Map<String,String>> set, String column, String value) {
    	for(Map<String,String> map : set) {
    		if (map.get(column).equals(value)) {
    			return true;
    		}
    	}
    	return false;
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	return cal.getTime();
    }

    /*
     * Builds the fact structure to be used as a criteria
     */
    private FactStructureInfo buildFactStructureForRuleCriteria(boolean staticFact) {
        FactStructureInfo fs = new FactStructureInfo();
        fs.setFactStructureId("1");
        fs.setFactTypeKey("fact.clusetId");
        fs.setAnchorFlag(false);

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "resultColumn.cluId");
        fs.setResultColumnKeyTranslations(resultColumnKeyMap);
        
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
    private FactStructureInfo buildFactStructureForIntersection(boolean staticFact) {
        FactStructureInfo fs = new FactStructureInfo();
        fs.setFactStructureId("2");
        fs.setFactTypeKey("fact.completed_course_list");
        fs.setAnchorFlag(false);

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "resultColumn.cluId");
        fs.setResultColumnKeyTranslations(resultColumnKeyMap);
        
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

    /*private BusinessRuleInfo generateNewBusinessRuleInfo(String ruleName, String anchor) {
    	return generateNewBusinessRuleInfo(ruleName, anchor, true);
    }

    private BusinessRuleInfo generateNewBusinessRuleInfo(String ruleName, String anchor, boolean staticFact) {
//        MetaInfoDTO metaInfo = new MetaInfoDTO();
//        metaInfo.setCreateTime(new Date());
//        metaInfo.setCreateID("Zdenek");
//        metaInfo.setUpdateTime(new Date());
//        metaInfo.setUpdateID("Len");
     
        FactStructureInfo fs1 = buildFactStructureForRuleCriteria(staticFact);
        FactStructureInfo fs2 = buildFactStructureForIntersection(staticFact);
        
        List<FactStructureInfo> factStructureList = new ArrayList<FactStructureInfo>();
        factStructureList.add(fs1);
        factStructureList.add(fs2);
        
        YieldValueFunctionInfo yieldValueFunctionDTO = new YieldValueFunctionInfo();
        yieldValueFunctionDTO.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION.toString());
        yieldValueFunctionDTO.setFactStructureList(factStructureList);

        LeftHandSideInfo leftHandSideDTO = new LeftHandSideInfo();
        leftHandSideDTO.setYieldValueFunction(yieldValueFunctionDTO);

        RightHandSideInfo rightHandSideDTO = new RightHandSideInfo();
        rightHandSideDTO.setExpectedValue("12");

        RulePropositionInfo rulePropositionDTO = new RulePropositionInfo();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDesc("Credit Intersection Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataTypeKey(Double.class.getName());
        rulePropositionDTO.setComparisonOperatorTypeKey(ComparisonOperator.LESS_THAN.toString());

        RuleElementInfo reDTO = new RuleElementInfo();
        reDTO.setName("Pre-req 1");
        reDTO.setDesc("Pre req check for Math 101");
        reDTO.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        reDTO.setBusinessRuleProposition(rulePropositionDTO);

        BusinessRuleInfo brInfoDTO = new BusinessRuleInfo();
        //brInfoDTO.setId("123");
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchor(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        //brInfoDTO.setMetaInfo(metaInfo);

        //brInfoDTO.setEffectiveStartTime(new Date());
        //brInfoDTO.setEffectiveEndTime(new Date());
        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
        brInfoDTO.setEffectiveDate(effectiveStartTime);
        brInfoDTO.setExpirationDate(effectiveEndTime);

        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(reDTO);

        brInfoDTO.setBusinessRuleElementList(elementList);
        
        return brInfoDTO;
    }*/
    private BusinessRuleInfo createIntersectionBusinessRuleInfo(String ruleName, String anchor) {
    	return createIntersectionBusinessRule(ruleName, anchor, true);
    }

    private BusinessRuleInfo createIntersectionBusinessRule(String ruleName, String anchor, boolean staticFact) {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateId("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateId("Len");
     
        FactStructureInfo fs1 = buildFactStructureForRuleCriteria(staticFact);
        FactStructureInfo fs2 = buildFactStructureForIntersection(staticFact);
        
        List<FactStructureInfo> factStructureList = new ArrayList<FactStructureInfo>();
        factStructureList.add(fs1);
        factStructureList.add(fs2);
        
        YieldValueFunctionInfo yieldValueFunctionInfo = new YieldValueFunctionInfo();
        yieldValueFunctionInfo.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION.toString());
        yieldValueFunctionInfo.setFactStructureList(factStructureList);

        LeftHandSideInfo leftHandSideInfo = new LeftHandSideInfo();
        leftHandSideInfo.setYieldValueFunction(yieldValueFunctionInfo);

        RightHandSideInfo rightHandSideInfo = new RightHandSideInfo();
        rightHandSideInfo.setExpectedValue("1");

        RulePropositionInfo rulePropositionInfo = new RulePropositionInfo();
        rulePropositionInfo.setName("Credit Check");
        rulePropositionInfo.setDesc("Credit Intersection Change");
        rulePropositionInfo.setLeftHandSide(leftHandSideInfo);
        rulePropositionInfo.setRightHandSide(rightHandSideInfo);
        rulePropositionInfo.setComparisonDataTypeKey(Integer.class.getName());
        rulePropositionInfo.setComparisonOperatorTypeKey(ComparisonOperator.EQUAL_TO.toString());

        RuleElementInfo reInfo = new RuleElementInfo();
        reInfo.setName("Pre-req 1");
        reInfo.setDesc("Pre req check for Math 101");
        reInfo.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        reInfo.setBusinessRuleProposition(rulePropositionInfo);

        BusinessRuleInfo brInfo = new BusinessRuleInfo();
        brInfo.setName(ruleName);
        brInfo.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfo.setSuccessMessage("Test success message");
        brInfo.setFailureMessage("Test failure message");
        brInfo.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfo.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfo.setAnchor(anchor);
        brInfo.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfo.setMetaInfo(metaInfo);

        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
        brInfo.setEffectiveDate(effectiveStartTime);
        brInfo.setExpirationDate(effectiveEndTime);

        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(reInfo);

        brInfo.setBusinessRuleElementList(elementList);
        
        return brInfo;
    }
    
    private BusinessRuleInfo generateNewEmptyBusinessRuleInfo(String businessRuleId, String ruleName, String anchor) {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateId("");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateId("");
     
        BusinessRuleInfo brInfoDTO = new BusinessRuleInfo();
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchor(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

		Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
		Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
		brInfoDTO.setEffectiveDate(effectiveStartTime);
		brInfoDTO.setExpirationDate(effectiveEndTime);
        
        return brInfoDTO;
    }

    
    
    @Test
    public void testFactFinderService() throws Exception {
    	List<FactTypeInfo> list = factFinderService.getFactTypes();
    	Assert.assertNotNull(list);
    	Assert.assertTrue(list.size() > 0);
    }
    
    @Test
    public void testRuleRepositoryService() throws Exception {
    	List<String> list = ruleRepositoryService.getStates();
    	Assert.assertNotNull(list);
    	Assert.assertTrue(list.size() > 0);
    }
    
    @Test
    public void testRuleManagementService() throws Exception {
    	List<String> list = ruleManagementService.getBusinessRuleTypes();
    	Assert.assertNotNull(list);
    	Assert.assertTrue(list.size() > 0);
    }
    
    
    @Test
    public void testFindBusinessRuleTypesFromTestBeans() throws Exception {
    	System.out.println("*****  testFindBusinessRuleTypesFromTestBeans  *****");

		List<String> businessRuleIdList1 = ruleManagementService.getBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
		Assert.assertNotNull(businessRuleIdList1);
		System.out.println("Business Rule ID1: "+businessRuleIdList1);
		List<String> businessRuleIdList2 = ruleManagementService.getBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");
		Assert.assertNotNull(businessRuleIdList2);
		System.out.println("Business Rule ID2: "+businessRuleIdList2);
    }
    
    @Ignore
    @Test
    public void testFindBusinessRuleTypesFromTestBeansAndExecute_StaticFact() throws Exception {
    	System.out.println("*****  testFindBusinessRuleTypesFromTestBeansAndExecute_StaticFact  *****");

		List<String> businessRuleIdList1 = ruleManagementService.getBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
		List<String> businessRuleIdList2 = ruleManagementService.getBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");

		businessRuleIdList1.addAll(businessRuleIdList2);
		System.out.println("Business Rule ID1: "+businessRuleIdList1);
		
        for(String businessRuleId : businessRuleIdList1) {
    		// Ignore since it has dynamic facts
    		if (businessRuleId.equals("11223344-1122-1122-1112-100000000032") ||
    		    businessRuleId.equals("11223344-1122-1122-1112-100000000041")) {
        			continue;
        		}
    		
    		System.out.println("Executing Business Rule ID: "+businessRuleId);
    		
    		// Update business rule to translate/compile and create a new rule set in the rule repository
    		BusinessRuleInfo businessRuleInfo = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
//    		RuleSetDTO ruleSet = ruleRepositoryService.getRuleSet(businessRuleInfo.getCompiledId());
//    		if (ruleSet != null) {
	    		ruleManagementService.updateBusinessRule(businessRuleId, businessRuleInfo);
//    		}
    		
    		ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);
    		Assert.assertNotNull(executionResult);
    		System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
        }
    }
    
    @Test
    public void testCreateBusinessRule() throws Exception {
    	System.out.println("*****  testCreateBusinessRule  *****");
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfo businessRule2 = ruleManagementService.getBusinessRuleInfo(businessRuleId);
    		Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    		
    		System.out.println("Business Rule ID:   "+businessRule2.getId());
	        System.out.println("Business Rule Name: "+businessRule2.getName());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateEmptyBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testCreateEmptyBusinessRule  *****");
    	BusinessRuleInfo businessRule1 = generateNewEmptyBusinessRuleInfo("1000", "CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
	        System.out.println("Business Rule ID:   "+businessRuleId);
    		BusinessRuleInfo businessRule2 = ruleManagementService.getBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule Name: "+businessRule2.getName());
	        Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateAndFetchBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testCreateAndFetchBusinessRule  *****");
    	BusinessRuleInfo businessRule = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
	        System.out.println("businessRuleId:         "+businessRuleId);
	        
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:       "+businessRule.getId());
	        System.out.println("Business Rule Name:     "+businessRule.getName());
	        System.out.println("Business Compiled ID:   "+businessRule.getCompiledId());
	        
	        RuleSetInfo ruleSet = ruleRepositoryService.getRuleSet(businessRule.getCompiledId());
	        Assert.assertNotNull(ruleSet);
	        System.out.println("RuleSet Name:           "+ruleSet.getName());
	        System.out.println("RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("RuleSet Source:\n"+ruleSet.getContent());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testUpdateBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testUpdateBusinessRule  *****");
    	BusinessRuleInfo businessRule = createIntersectionBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");
    	
    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
	        System.out.println("businessRuleId:         "+businessRuleId);
	        businessRule = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
	        String status = ruleManagementService.updateBusinessRule(businessRuleId, businessRule).getState();
	        Assert.assertNotNull(status);
	        System.out.println("status:                 "+status);
	
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
	        Assert.assertNotNull(businessRule);
	        System.out.println("Business Rule ID:       "+businessRule.getId());
	        System.out.println("Business Rule Name:     "+businessRule.getName());
	        System.out.println("Business Compiled ID:   "+businessRule.getCompiledId());
	        
	        RuleSetInfo ruleSet = ruleRepositoryService.getRuleSet(businessRule.getCompiledId());
	        Assert.assertNotNull(ruleSet);
	        System.out.println("RuleSet Name:           "+ruleSet.getName());
	        System.out.println("RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("RuleSet Source:\n"+ruleSet.getContent());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateBusinessRuleAndExecute_StaticFact() throws Exception {
    	System.out.println("\n\n*****  testCreateBusinessRuleAndExecute  *****");
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfo businessRule = ruleManagementService.getBusinessRuleInfo(businessRuleId);
	        Assert.assertNotNull(businessRule);
	        System.out.println("Business Rule ID:        "+businessRule.getId());
	        System.out.println("Business Rule Name:      "+businessRule.getName());

	        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);
	        Assert.assertNotNull(executionResult);
	        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
	        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
	        Assert.assertTrue(executionResult.getReport().isSuccessful());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testFindFactTypes() throws Exception {
    	System.out.println("\n\n*****  testFindFactTypes  *****");
    	List<FactTypeInfo> factTypes = factFinderService.getFactTypes();
        Assert.assertNotNull(factTypes);
    	for(FactTypeInfo factTypeInfo : factTypes) {
    		System.out.println("Fact type name:        "+factTypeInfo.getName());
    		System.out.println("Fact type description: "+factTypeInfo.getDescr());
//    		System.out.println("Fact type key:         "+factTypeInfo.getFactTypeKey());
    	}
    }

    @Test
    public void testFetchDynamicFact() throws Exception {
    	System.out.println("\n\n*****  testFetchDynamicFact  *****");
        String factTypeKey = "fact.completed_course_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        
        FactStructureInfo FactStructureInfo = new FactStructureInfo();
        FactStructureInfo.setFactTypeKey(factTypeKey);
        FactStructureInfo.setParamValueMap(paramMap);
        
        FactResultInfo result = factFinderService.getFact(factTypeKey, FactStructureInfo);
        Assert.assertNotNull(result);
        
        Assert.assertEquals(result.getFactResultTypeInfo().getId(), "result.completedCourseInfo");
        Assert.assertEquals(2, result.getFactResultTypeInfo().getResultColumnsMap().size());
        
        Assert.assertEquals(4, result.getResultList().size());
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 200"));
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 201"));
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 202"));
    }

    @Test
    public void testCreateAndExecuteBusinessRule_DynamicFact() throws Exception {
    	System.out.println("\n\n*****  testCreateAndExecuteBusinessRule_DynamicFact  *****");
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRule("CHEM200PRE_REQ", "CHEM200", false);

        ParameterMapInfo paramMap = new ParameterMapInfo();
        paramMap.addParameter("factParam.studentId", "student1");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
            Assert.assertNotNull(businessRuleId);
    		BusinessRuleInfo businessRule2 = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:        "+businessRule2.getId());
	        System.out.println("Business Rule Name :     "+businessRule2.getName());

	        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);
            Assert.assertNotNull(executionResult);
	        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
	        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
	        Assert.assertTrue(executionResult.getReport().isSuccessful());

	        // Test proposition reports
	        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
	        
	        PropositionReportInfo prDTO = executionResult.getReport().getPropositionReports().get(0);
	        
	        Assert.assertEquals("P1", prDTO.getPropositionName());
	        Assert.assertTrue(prDTO.isSuccessful());
	        
	        // Test criteria facts
	        Map<String,String> criteriaRowMap = prDTO.getCriteriaResult().getResultList().get(0);
			Assert.assertEquals(1, criteriaRowMap.size());
	        Assert.assertEquals("PSYC 200", criteriaRowMap.get("resultColumn.cluId"));

	        // Test facts
	        Map<String,String> factRowMap1 = prDTO.getFactResult().getResultList().get(0);
	        Map<String,String> factRowMap2 = prDTO.getFactResult().getResultList().get(1);
	        Map<String,String> factRowMap3 = prDTO.getFactResult().getResultList().get(2);
	        Assert.assertEquals(2, factRowMap1.size());
	        Assert.assertEquals(2, factRowMap2.size());
	        Assert.assertEquals(2, factRowMap3.size());
	        Assert.assertEquals("PSYC 200", factRowMap1.get("resultColumn.cluId"));
	        Assert.assertEquals("PSYC 201", factRowMap2.get("resultColumn.cluId"));
	        Assert.assertEquals("PSYC 202", factRowMap3.get("resultColumn.cluId"));

			FactResultInfo propositionResult1 = prDTO.getPropositionResult();
	        Assert.assertEquals(1, propositionResult1.getResultList().size());
			Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateAndExecuteBusinessRuleTest_StaticFact() throws Exception {
    	System.out.println("\n\n*****  testCreateAndExecuteBusinessRuleTest_StaticFact  *****");
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ_TEST", "CHEM100");
    	businessRule1.setId("xxx");

        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, null);
        Assert.assertNotNull(executionResult);

        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
        System.out.println("Execution error message: "+executionResult.getErrorMessage());
        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
        Assert.assertTrue(executionResult.getReport().isSuccessful());

        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());

        PropositionReportInfo prDTO = executionResult.getReport().getPropositionReports().get(0);
        
        Assert.assertEquals("P1", prDTO.getPropositionName());
        Assert.assertTrue(prDTO.isSuccessful());
        
        // Test criteria facts
        Map<String,String> criteriaRowMap = prDTO.getCriteriaResult().getResultList().get(0);
        Assert.assertEquals(1, criteriaRowMap.size());
        Assert.assertEquals("CPR101", criteriaRowMap.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));

        // Test facts
        Map<String,String> factRowMap1 = prDTO.getFactResult().getResultList().get(0);
        Map<String,String> factRowMap2 = prDTO.getFactResult().getResultList().get(1);
        Map<String,String> factRowMap3 = prDTO.getFactResult().getResultList().get(2);
        Assert.assertEquals(1, factRowMap1.size());
        Assert.assertEquals(1, factRowMap2.size());
        Assert.assertEquals(1, factRowMap3.size());
        Assert.assertEquals("CPR101", factRowMap1.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));
        Assert.assertEquals("CPR201", factRowMap2.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));
        Assert.assertEquals("CPR301", factRowMap3.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));

		FactResultInfo propositionResult1 = prDTO.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
    }

    @Test
    public void testCreateAndExecuteBusinessRuleTest_DynamicFact() throws Exception {
    	System.out.println("\n\n*****  testCreateBusinessRuleAndExecute_DynamicFact  *****");
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRule("CHEM100PRE_REQ", "CHEM100", false);
    	businessRule1.setId("xxx");

        ParameterMapInfo paramMap = new ParameterMapInfo();
        paramMap.addParameter("factParam.studentId", "student1");
    	
        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, paramMap);
        Assert.assertNotNull(executionResult);

        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
        System.out.println("Execution error message: "+executionResult.getErrorMessage());
        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
        Assert.assertTrue(executionResult.getReport().isSuccessful());

        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
        
        PropositionReportInfo prDTO = executionResult.getReport().getPropositionReports().get(0);
        
        Assert.assertEquals("P1", prDTO.getPropositionName());
        Assert.assertTrue(prDTO.isSuccessful());
        
        // Test criteria facts
        Map<String,String> criteriaRowMap = prDTO.getCriteriaResult().getResultList().get(0);
		Assert.assertEquals(1, criteriaRowMap.size());
        Assert.assertEquals("PSYC 200", criteriaRowMap.get("resultColumn.cluId"));

        // Test facts
        Map<String,String> factRowMap1 = prDTO.getFactResult().getResultList().get(0);
        Map<String,String> factRowMap2 = prDTO.getFactResult().getResultList().get(1);
        Map<String,String> factRowMap3 = prDTO.getFactResult().getResultList().get(2);
        Assert.assertEquals(2, factRowMap1.size());
        Assert.assertEquals(2, factRowMap2.size());
        Assert.assertEquals(2, factRowMap3.size());
        Assert.assertEquals("PSYC 200", factRowMap1.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 201", factRowMap2.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 202", factRowMap3.get("resultColumn.cluId"));

		FactResultInfo propositionResult1 = prDTO.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    }
}

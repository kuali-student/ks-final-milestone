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

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.junit.Ignore;
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
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.utils.ServiceFactory;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.AgendaExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.PropositionReportDTO;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.kuali.student.poc.common.test.spring.AbstractIntegrationServiceTest;
import org.kuali.student.poc.common.test.spring.IntegrationServer;
import org.kuali.student.poc.common.test.spring.SystemProperties;
import org.kuali.student.poc.common.test.spring.Property;

// The following system properties are only needed if you want to specify a 
// server port (port 9000) other than 8080 as defined in 
// brms-ws/src/main/resources/application.properties, 
// application-ruleexecution.properties and application-rulerepository.properties
@IntegrationServer(port=9000, webappPath="../../../brms-ws/target/brms-ws-0.1.0-SNAPSHOT", contextPath="/brms")
@SystemProperties(properties={
	@Property(key="ks.servicelocation.FactFinderService", value="http://localhost:9000/brms/services/FactFinderService"),
	@Property(key="ks.servicelocation.RuleManagementService", value="http://localhost:9000/brms/services/RuleManagementService"),
	@Property(key="ks.servicelocation.RuleExecutionService", value="http://localhost:9000/brms/services/RuleExecutionService"),
	@Property(key="ks.servicelocation.RuleRepositoryService", value="http://localhost:9000/brms/services/RuleRepositoryService"),

	@Property(key="jpa.factfinder.hibernate.hbm2ddl.auto", value="create-drop"),
	@Property(key="jpa.rulemanagement.hibernate.hbm2ddl.auto", value="create-drop")
})
public class IntegrationTest extends AbstractIntegrationServiceTest {
	private final static String HOST = "http://localhost:9000/brms";

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
    private FactStructureDTO buildFactStructureForRuleCriteria(boolean staticFact) {
        FactStructureDTO fs = new FactStructureDTO();
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
    private FactStructureDTO buildFactStructureForIntersection(boolean staticFact) {
        FactStructureDTO fs = new FactStructureDTO();
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

    private BusinessRuleInfoDTO createIntersectionBusinessRuleInfo(String ruleName, String anchor) {
    	return createIntersectionBusinessRule(ruleName, anchor, true);
    }

    private BusinessRuleInfoDTO createIntersectionBusinessRule(String ruleName, String anchor, boolean staticFact) {
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
        rightHandSideDTO.setExpectedValue("1");

        RulePropositionDTO rulePropositionDTO = new RulePropositionDTO();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDescription("Credit Intersection Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataTypeKey(Integer.class.getName());
        rulePropositionDTO.setComparisonOperatorTypeKey(ComparisonOperator.EQUAL_TO.toString());

        RuleElementDTO reDTO = new RuleElementDTO();
        reDTO.setName("Pre-req 1");
        reDTO.setDescription("Pre req check for Math 101");
        reDTO.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        reDTO.setBusinessRuleProposition(rulePropositionDTO);

        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
        brInfoDTO.setEffectiveDate(effectiveStartTime);
        brInfoDTO.setExpirationDate(effectiveEndTime);

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO);

        brInfoDTO.setBusinessRuleElementList(elementList);
        
        return brInfoDTO;
    }
    
    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureDTO buildFactStructureForSum() {
        FactStructureDTO fs = new FactStructureDTO();
        fs.setFactStructureId("2");
        fs.setFactTypeKey("fact.earned_credit_list");
        fs.setAnchorFlag(false);
        fs.setStaticFact(false);

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY, "resultColumn.credit");
        fs.setResultColumnKeyTranslations(resultColumnKeyMap);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.clusetId", "PSYC 200,PSYC 201,PSYC 202");
        paramMap.put("factParam.excludeCluSet", "PSYC 200");
	    fs.setParamValueMap(paramMap);
        return fs;                
    }

    private BusinessRuleInfoDTO createSumBusinessRule(String ruleName, String anchor) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
     
        FactStructureDTO fs1 = buildFactStructureForSum();
        FactStructureDTO fs2 = buildFactStructureForRuleCriteria(false);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(fs1);
        factStructureList.add(fs2);
        
        YieldValueFunctionDTO yieldValueFunctionDTO = new YieldValueFunctionDTO();
        yieldValueFunctionDTO.setYieldValueFunctionType(YieldValueFunctionType.SUM.toString());
        yieldValueFunctionDTO.setFactStructureList(factStructureList);

        LeftHandSideDTO leftHandSideDTO = new LeftHandSideDTO();
        leftHandSideDTO.setYieldValueFunction(yieldValueFunctionDTO);

        RightHandSideDTO rightHandSideDTO = new RightHandSideDTO();
        rightHandSideDTO.setExpectedValue("6.0");

        RulePropositionDTO rulePropositionDTO = new RulePropositionDTO();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDescription("Credit Sum Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataTypeKey(BigDecimal.class.getName());
        rulePropositionDTO.setComparisonOperatorTypeKey(ComparisonOperator.EQUAL_TO.toString());

        RuleElementDTO reDTO = new RuleElementDTO();
        reDTO.setName("Pre-req 1");
        reDTO.setDescription("Pre req check for Math 101");
        reDTO.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        reDTO.setBusinessRuleProposition(rulePropositionDTO);

        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
        brInfoDTO.setEffectiveDate(effectiveStartTime);
        brInfoDTO.setExpirationDate(effectiveEndTime);

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO);

        brInfoDTO.setBusinessRuleElementList(elementList);
        
        return brInfoDTO;
    }
    
    private BusinessRuleInfoDTO generateNewEmptyBusinessRuleInfo(String businessRuleId, String ruleName, String anchor) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
     
        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

		Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
		Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
		brInfoDTO.setEffectiveDate(effectiveStartTime);
		brInfoDTO.setExpirationDate(effectiveEndTime);
        
        return brInfoDTO;
    }

    @Test
    public void testFindBusinessRuleTypesFromTestBeans() throws Exception {
		List<String> businessRuleIdList1 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
		Assert.assertNotNull(businessRuleIdList1);
		List<String> businessRuleIdList2 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");
		Assert.assertNotNull(businessRuleIdList2);
    }
    
    @Ignore
    @Test
    public void testFindBusinessRuleTypesFromTestBeansAndExecute_StaticFact() throws Exception {
		List<String> businessRuleIdList1 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
		List<String> businessRuleIdList2 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");

		businessRuleIdList1.addAll(businessRuleIdList2);
		
        for(String businessRuleId : businessRuleIdList1) {
    		// Ignore since it has dynamic facts
    		if (businessRuleId.equals("11223344-1122-1122-1112-100000000032") ||
    		    businessRuleId.equals("11223344-1122-1122-1112-100000000041")) {
    			continue;
    		}
    		
    		// Update business rule to translate/compile and create a new rule set in the rule repository
    		BusinessRuleInfoDTO businessRuleInfo = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
    		ruleManagementService.updateBusinessRule(businessRuleId, businessRuleInfo);
    		
    		ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);
    		Assert.assertNotNull(executionResult);
        }
    }
    
    @Test
    public void testCreateBusinessRule() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = createIntersectionBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
    		Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateEmptyBusinessRule() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = generateNewEmptyBusinessRuleInfo("1000", "CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
	        Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateAndFetchBusinessRule() throws Exception {
    	BusinessRuleInfoDTO businessRule = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
	        
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        Assert.assertNotNull(ruleSet);
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testUpdateBusinessRule() throws Exception {
    	BusinessRuleInfoDTO businessRule = createIntersectionBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");
    	
    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        String status = ruleManagementService.updateBusinessRule(businessRuleId, businessRule).getState();
	        Assert.assertNotNull(status);
	
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        Assert.assertNotNull(businessRule);
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        Assert.assertNotNull(ruleSet);
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateBusinessRuleAndExecute_StaticFact() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfoDTO businessRule = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
	        Assert.assertNotNull(businessRule);

	        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);

	        Assert.assertNotNull(executionResult);
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testFindFactTypes() throws Exception {
    	List<FactTypeInfoDTO> factTypes = factFinderService.findFactTypes();
        Assert.assertNotNull(factTypes);
    }

    @Test
    public void testFetchDynamicFact() throws Exception {
        String factTypeKey = "fact.completed_course_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        
        FactStructureDTO factStructureDTO = new FactStructureDTO();
        factStructureDTO.setFactTypeKey(factTypeKey);
        factStructureDTO.setParamValueMap(paramMap);
        
        FactResultDTO result = factFinderService.fetchFact(factTypeKey, factStructureDTO);
        Assert.assertNotNull(result);
        
        Assert.assertEquals(result.getFactResultTypeInfo().getKey(), "result.completedCourseInfo");
        Assert.assertEquals(2, result.getFactResultTypeInfo().getResultColumnsMap().size());
        
        Assert.assertEquals(4, result.getResultList().size());
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 200"));
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 201"));
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 202"));
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 218"));
    }

    @Test
    public void testCreateAndExecuteBusinessRule_DynamicFact() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = createIntersectionBusinessRule("CHEM200PRE_REQ", "CHEM200", false);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
            Assert.assertNotNull(businessRuleId);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);

	        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);
            Assert.assertNotNull(executionResult);
            Assert.assertNotNull(executionResult.getExecutionLog());
	        Assert.assertTrue(executionResult.getReport().isSuccessful());
	        // Test proposition reports
	        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
	        
	        PropositionReportDTO prDTO = executionResult.getReport().getPropositionReports().get(0);
	        
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

			FactResultDTO propositionResult1 = prDTO.getPropositionResult();
	        Assert.assertEquals(1, propositionResult1.getResultList().size());
			Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateAndExecuteBusinessRuleTest_StaticFact() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ_TEST", "CHEM100");
    	businessRule1.setId("xxx");

        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, null);
        Assert.assertNotNull(executionResult);
        Assert.assertTrue(executionResult.getReport().isSuccessful());
        
        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());

        PropositionReportDTO prDTO = executionResult.getReport().getPropositionReports().get(0);
        
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

		FactResultDTO propositionResult1 = prDTO.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
    }

    @Test
    public void testCreateAndExecuteBusinessRuleTest_DynamicFact() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = createIntersectionBusinessRule("CHEM100PRE_REQ", "CHEM100", false);
    	businessRule1.setId("xxx");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");

        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, paramMap);
        Assert.assertNotNull(executionResult);
        Assert.assertTrue(executionResult.getReport().isSuccessful());

        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
        
        PropositionReportDTO prDTO = executionResult.getReport().getPropositionReports().get(0);
        
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

		FactResultDTO propositionResult1 = prDTO.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    }

    @Test
    public void testCreateAndExecuteComplexBusinessRule_DynamicFact() throws Exception {
    	BusinessRuleInfoDTO businessRule = createSumBusinessRule("MATH100PRE_REQ", "MATH100");

        Map<String, String> paramMap = new HashMap<String, String>();
	    paramMap.put("factParam.studentId", "student1");

    	String businessRuleId = null;
	    try {
		    businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
			BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	
	        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);
	        Assert.assertNotNull(executionResult);
	        Assert.assertTrue(executionResult.getReport().isSuccessful());
	        
	        Assert.assertTrue(getProposition(executionResult.getReport().getPropositionReports(), "P1").isSuccessful());
	        Assert.assertEquals("SUM", getProposition(executionResult.getReport().getPropositionReports(), "P1").getPropositionType());
	
	        PropositionReportDTO prP1 = getProposition(executionResult.getReport().getPropositionReports(), "P1");
	        
			FactResultDTO propositionResult1 = prP1.getPropositionResult();
	        Assert.assertEquals(1, propositionResult1.getResultList().size());
			Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.credit", "6.0"));
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

	private PropositionReportDTO getProposition(List<PropositionReportDTO> list, String name) {
		for(PropositionReportDTO report : list) {
			if(report.getPropositionName().equals(name)) {
				return report;
			}
		}
		return null;
	}

	/*@Test
    public void testCreateAndExecuteComplexBusinessRuleWithNoReport_DynamicFact2() throws Exception {
    	String businessRuleId = "11223344-1122-1122-1112-100000000032";

        Map<String, String> paramMap = new HashMap<String, String>();
	    paramMap.put("factParam.studentId", "student1");

    	BusinessRuleInfoDTO businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
    	ruleManagementService.updateBusinessRule(businessRuleId, businessRule);
    	businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);

    	Boolean executionResult1 = ruleExecutionService.executeBusinessRuleWithNoReport(businessRuleId, paramMap);

        Assert.assertNotNull(executionResult1);
        Assert.assertTrue(executionResult1);
	}*/

	@Test
    public void testCreateAndExecuteComplexBusinessRule_DynamicFact3() throws Exception {
    	String businessRuleId = "11223344-1122-1122-1112-100000000032";

        Map<String, String> paramMap = new HashMap<String, String>();
	    paramMap.put("factParam.studentId", "student1");

    	BusinessRuleInfoDTO businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
    	ruleManagementService.updateBusinessRule(businessRuleId, businessRule);
    	businessRule = ruleManagementService.updateBusinessRuleState(businessRuleId, BusinessRuleStatus.ACTIVE.toString());
    	businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);

        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);

        Assert.assertNotNull(executionResult);
        Assert.assertTrue(executionResult.isExecutionSuccessful());
        Assert.assertTrue(executionResult.getReport().isSuccessful());

        // Test proposition reports
        Assert.assertEquals(3, executionResult.getReport().getPropositionReports().size());

        Assert.assertTrue(getProposition(executionResult.getReport().getPropositionReports(), "P1").isSuccessful());
        Assert.assertTrue(getProposition(executionResult.getReport().getPropositionReports(), "P2").isSuccessful());
        Assert.assertTrue(getProposition(executionResult.getReport().getPropositionReports(), "P3").isSuccessful());

        Assert.assertEquals("INTERSECTION", getProposition(executionResult.getReport().getPropositionReports(), "P1").getPropositionType());
        Assert.assertEquals("INTERSECTION", getProposition(executionResult.getReport().getPropositionReports(), "P2").getPropositionType());
        Assert.assertEquals("SUM", getProposition(executionResult.getReport().getPropositionReports(), "P3").getPropositionType());

        PropositionReportDTO prP1 = getProposition(executionResult.getReport().getPropositionReports(), "P1");
        PropositionReportDTO prP2 = getProposition(executionResult.getReport().getPropositionReports(), "P2");
        PropositionReportDTO prP3 = getProposition(executionResult.getReport().getPropositionReports(), "P3");

        Assert.assertEquals("P1", prP1.getPropositionName());
        Assert.assertTrue(prP1.isSuccessful());

        // Test criteria facts
        Map<String,String> criteriaRowMap = prP1.getCriteriaResult().getResultList().get(0);
		Assert.assertEquals(1, criteriaRowMap.size());
        Assert.assertEquals("PSYC 200", criteriaRowMap.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));

        // Test facts - Assume rows are ordered
        Map<String,String> factRowMap1 = prP1.getFactResult().getResultList().get(0);
        Map<String,String> factRowMap2 = prP1.getFactResult().getResultList().get(1);
        Map<String,String> factRowMap3 = prP1.getFactResult().getResultList().get(2);
        Map<String,String> factRowMap4 = prP1.getFactResult().getResultList().get(3);
        Assert.assertEquals(2, factRowMap1.size());
        Assert.assertEquals(2, factRowMap2.size());
        Assert.assertEquals(2, factRowMap3.size());
        Assert.assertEquals(2, factRowMap4.size());
        Assert.assertEquals("PSYC 200", factRowMap1.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 201", factRowMap2.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 202", factRowMap3.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 218", factRowMap4.get("resultColumn.cluId"));

		FactResultDTO propositionResult1 = prP1.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));

		FactResultDTO propositionResult2 = prP2.getPropositionResult();
        Assert.assertEquals(2, propositionResult2.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult2.getResultList(), "resultColumn.cluId", "PSYC 201"));
		Assert.assertTrue(containsResult(propositionResult2.getResultList(), "resultColumn.cluId", "PSYC 202"));

		FactResultDTO propositionResult3 = prP3.getPropositionResult();
        Assert.assertEquals(1, propositionResult3.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult3.getResultList(), "resultColumn.credit", "13.0"));
    }
/*
	@Test
	public void testCreateAndExecuteAgenda_OneBusinessRule() throws Exception {
    	// Update business rule to create a compiled/executable rule
    	String businessRuleId = "11223344-1122-1122-1112-100000000011";
    	BusinessRuleInfoDTO businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
    	if (!businessRule.getState().equals(BusinessRuleStatus.ACTIVE.toString())) {
//	    	if(ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId()) == null) {
	    		businessRule = ruleManagementService.updateBusinessRule(businessRuleId, businessRule);
//	    	}
	    	businessRule = ruleManagementService.updateBusinessRuleState(businessRuleId, BusinessRuleStatus.ACTIVE.toString());
    	}

//        Map<String, String> agendaDeterminationMap = new HashMap<String, String>();
//        agendaDeterminationMap.put("agendaDeterminationInfo.luiType", "course");
//        agendaDeterminationMap.put("agendaDeterminationInfo.luiPersonRelationType", "kuali.student");
//        agendaDeterminationMap.put("agendaDeterminationInfo.relationState", "enrolled");
//        
//        AgendaDeterminationInfoDTO adiDTO = new AgendaDeterminationInfoDTO();
//        adiDTO.setAgendaInfoDeterminationKeyList(agendaDeterminationMap);
//		
//        AgendaInfoDTO aiDTO = ruleManagementService.fetchAgendaInfo(AgendaType.KUALI_VALIDATE_LUI_PERSON_RELATION.toString(),  adiDTO);
        
        List<BusinessRuleAnchorInfoDTO> businessRuleAnchorInfoList = new ArrayList<BusinessRuleAnchorInfoDTO>();
        BusinessRuleAnchorInfoDTO anchorInfo = new BusinessRuleAnchorInfoDTO();
        anchorInfo.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        anchorInfo.setAnchorValue("MATH 101");
        anchorInfo.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        businessRuleAnchorInfoList.add(anchorInfo);
        
        Map<String, String> exectionParamMap = new HashMap<String, String>();
        exectionParamMap.put("factParam.studentId", "student1");

		AgendaExecutionResultDTO agendaExecutionResult = ruleExecutionService.executeAgenda(businessRuleAnchorInfoList, exectionParamMap);

        Assert.assertTrue(agendaExecutionResult.isExecutionSuccessful());
        Assert.assertFalse(agendaExecutionResult.isAgendaReportSuccessful());
        Assert.assertNotNull(agendaExecutionResult);
        Assert.assertEquals(1, agendaExecutionResult.getExecutionResultList().size());

        Assert.assertEquals("Credit check failed. Sum is short by 3.0", agendaExecutionResult.getFailureMessageSummary());
        Assert.assertNull(agendaExecutionResult.getSuccessMessageSummary());

        // This agenda only has one rule
        ExecutionResultDTO ruleResult = agendaExecutionResult.getExecutionResultList().get(0);

        Assert.assertEquals("Credit check failed. Sum is short by 3.0", ruleResult.getReport().getFailureMessage());
		Assert.assertEquals(2, ruleResult.getReport().getPropositionReports().size());
		
		// First proposition
		PropositionReportDTO propositionReport1 = ruleResult.getReport().getPropositionReports().get(0);
		
		Assert.assertTrue(propositionReport1.isSuccessful());
		Assert.assertEquals(YieldValueFunctionType.INTERSECTION.toString(), propositionReport1.getPropositionType());
		Assert.assertEquals("Pre-requisite course requirement met: [MATH 100]", propositionReport1.getMessage());
		
		// Second proposition
		PropositionReportDTO propositionReport2 = ruleResult.getReport().getPropositionReports().get(1);

		Assert.assertFalse(propositionReport2.isSuccessful());
		Assert.assertEquals(YieldValueFunctionType.SUM.toString(), propositionReport2.getPropositionType());
		Assert.assertEquals("Credit check failed. Sum is short by 3.0", propositionReport2.getMessage());
	}

	@Test
	public void testCreateAndExecuteAgenda_TwoBusinessRule() throws Exception {
    	// Update business rule to change state to ACTIVE and compile an executable rule
    	String businessRuleId1 = "11223344-1122-1122-1112-100000000011";
    	BusinessRuleInfoDTO businessRule1 = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId1);
    	if (!businessRule1.getState().equals(BusinessRuleStatus.ACTIVE.toString())) {
//	    	if(ruleRepositoryService.fetchRuleSet(businessRule1.getCompiledId()) == null) {
	    		businessRule1 = ruleManagementService.updateBusinessRule(businessRuleId1, businessRule1);
//	    	}
	    	businessRule1 = ruleManagementService.updateBusinessRuleState(businessRuleId1, BusinessRuleStatus.ACTIVE.toString());
    	}
    	// Update business rule to change state to ACTIVE and compile an executable rule
    	String businessRuleId2 = "11223344-1122-1122-1112-100000000032";
    	BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId2);
    	if (!businessRule2.getState().equals(BusinessRuleStatus.ACTIVE.toString())) {
	    	if(ruleRepositoryService.fetchRuleSet(businessRule2.getCompiledId()) == null) {
	    		businessRule2 = ruleManagementService.updateBusinessRule(businessRuleId2, businessRule2);
	    	}
	    	businessRule2 = ruleManagementService.updateBusinessRuleState(businessRuleId2, BusinessRuleStatus.ACTIVE.toString());
    	}

        List<BusinessRuleAnchorInfoDTO> businessRuleAnchorInfoList = new ArrayList<BusinessRuleAnchorInfoDTO>();
        
        BusinessRuleAnchorInfoDTO anchorInfo1 = new BusinessRuleAnchorInfoDTO();
        anchorInfo1.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        anchorInfo1.setAnchorValue("MATH 101");
        anchorInfo1.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        businessRuleAnchorInfoList.add(anchorInfo1);

        BusinessRuleAnchorInfoDTO anchorInfo2 = new BusinessRuleAnchorInfoDTO();
        anchorInfo2.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        anchorInfo2.setAnchorValue("PSYC 300");
        anchorInfo2.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        businessRuleAnchorInfoList.add(anchorInfo2);
        
        Map<String, String> exectionParamMap = new HashMap<String, String>();
        exectionParamMap.put("factParam.studentId", "student1");

		AgendaExecutionResultDTO agendaExecutionResult = ruleExecutionService.executeAgenda(businessRuleAnchorInfoList, exectionParamMap);

        Assert.assertTrue(agendaExecutionResult.isExecutionSuccessful());
        Assert.assertFalse(agendaExecutionResult.isAgendaReportSuccessful());
        Assert.assertNotNull(agendaExecutionResult);
        // This agenda has 2 business rules
        Assert.assertEquals(2, agendaExecutionResult.getExecutionResultList().size());

        Assert.assertNotNull(agendaExecutionResult.getSuccessMessageSummary());
        Assert.assertNotNull(agendaExecutionResult.getFailureMessageSummary());
        Assert.assertEquals("Credit check failed. Sum is short by 3.0", agendaExecutionResult.getFailureMessageSummary());
        Assert.assertEquals("Intersection constraint fulfilled OR Intersection constraint fulfilled OR Sum constraint fulfilled", agendaExecutionResult.getSuccessMessageSummary());

        // Rule 1
        ExecutionResultDTO ruleResult1 = agendaExecutionResult.getExecutionResultList().get(0);
        Assert.assertTrue(ruleResult1.isExecutionSuccessful());
        Assert.assertFalse(ruleResult1.getReport().isSuccessful());

        // Rule 2
        ExecutionResultDTO ruleResult2 = agendaExecutionResult.getExecutionResultList().get(1);
        Assert.assertTrue(ruleResult2.isExecutionSuccessful());
        Assert.assertTrue(ruleResult2.getReport().isSuccessful());
	}*/
}

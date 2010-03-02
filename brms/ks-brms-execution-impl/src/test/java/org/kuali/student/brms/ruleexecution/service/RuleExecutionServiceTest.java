package org.kuali.student.brms.ruleexecution.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.kuali.student.brms.repository.service.RuleRepositoryService;
import org.kuali.student.brms.ruleexecution.dto.AgendaExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResultInfo;
import org.kuali.student.brms.ruleexecution.dto.ParameterMapInfo;
import org.kuali.student.brms.ruleexecution.dto.PropositionReportInfo;
import org.kuali.student.brms.rulemanagement.RuleManagementServiceAssembler;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;
import org.kuali.student.brms.rulemanagement.entity.BusinessRule;
import org.kuali.student.brms.rulemanagement.service.RuleManagementService;
import org.kuali.student.common.ws.beans.JaxWsClientFactory;
import org.kuali.student.common.ws.beans.JaxWsClientFactoryBean;
import org.kuali.student.core.dto.MetaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:ws/jetty-servlet-context.xml"})
public class RuleExecutionServiceTest {
	// Jetty server location
	private final static String HOST_URL = "http://localhost:9090"; 

	@Autowired
	private ApplicationContext applicationContext;

	private static String businessRuleId1 = "11223344-1122-1122-1112-100000000001";
	private static String businessRuleId2 = "11223344-1122-1122-1112-100000000011";
	private static BusinessRuleInfo businessRuleInfo1;
	private static BusinessRuleInfo businessRuleInfo2;
		
    private static RuleManagementService ruleManagementService;
    private static RuleRepositoryService ruleRepositoryService;
    private static RuleExecutionService ruleExecutionService;
    private static FactFinderService factFinderService;

    private static boolean setup = false;
    
    public RuleExecutionServiceTest() {
    }
    
    @Before
    public void setUp() throws Exception {
    	if(!setup) {
    		createClients();
    		setupRuleManagement();
    		setup = true;
    	}
    }
    
    private void createClients() throws Exception {
		factFinderService = (FactFinderService) createClient(
				FactFinderService.class, 
				"http://student.kuali.org/wsdl/brms/FactFinder", "FactFinderService",
				HOST_URL + "/services/factFinderService?wsdl", 
				HOST_URL + "/services/factFinderService");

		ruleRepositoryService = (RuleRepositoryService) createClient(
				RuleRepositoryService.class, 
				"http://student.kuali.org/wsdl/brms/RuleRepository", "RuleRepositoryService",
				HOST_URL + "/services/ruleRepositoryService?wsdl", 
				HOST_URL + "/services/ruleRepositoryService");

		ruleManagementService = (RuleManagementService) createClient(
				RuleManagementService.class, 
				"http://student.kuali.org/wsdl/brms/RuleManagement", "RuleManagementService",
				HOST_URL + "/services/ruleManagementService?wsdl", 
				HOST_URL + "/services/ruleManagementService");

		ruleExecutionService = (RuleExecutionService) createClient(
				RuleExecutionService.class, 
				"http://student.kuali.org/wsdl/brms/RuleExecution", "RuleExecutionService",
				HOST_URL + "/services/ruleExecutionService?wsdl", 
				HOST_URL + "/services/ruleExecutionService");
    }
    
    private Object createClient(Class<?> sei, 
    		String namespaceURI, String localPart, 
    		String wsdlLocation, String address) throws Exception {
		JaxWsClientFactory clientFactory = new JaxWsClientFactoryBean();
		clientFactory.setServiceEndpointInterface(sei);
		clientFactory.setServiceName(new QName(namespaceURI, localPart));
		clientFactory.setWsdlLocation(wsdlLocation);
		clientFactory.setAddress(address);
		
		return clientFactory.getObject();
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
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
        brInfo.setEffectiveDate(effectiveStartTime);
        brInfo.setExpirationDate(effectiveEndTime);

        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(reInfo);

        brInfo.setBusinessRuleElementList(elementList);
        
        return brInfo;
    }
    
    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureInfo buildFactStructureForSum() {
        FactStructureInfo fs = new FactStructureInfo();
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

    private BusinessRuleInfo createSumBusinessRule(String ruleName, String anchor) {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateId("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateId("Len");
     
        FactStructureInfo fs1 = buildFactStructureForSum();
        FactStructureInfo fs2 = buildFactStructureForRuleCriteria(false);
        
        List<FactStructureInfo> factStructureList = new ArrayList<FactStructureInfo>();
        factStructureList.add(fs1);
        factStructureList.add(fs2);
        
        YieldValueFunctionInfo yieldValueFunctionInfo = new YieldValueFunctionInfo();
        yieldValueFunctionInfo.setYieldValueFunctionType(YieldValueFunctionType.SUM.toString());
        yieldValueFunctionInfo.setFactStructureList(factStructureList);

        LeftHandSideInfo leftHandSideInfo = new LeftHandSideInfo();
        leftHandSideInfo.setYieldValueFunction(yieldValueFunctionInfo);

        RightHandSideInfo rightHandSideInfo = new RightHandSideInfo();
        rightHandSideInfo.setExpectedValue("6.0");

        RulePropositionInfo rulePropositionInfo = new RulePropositionInfo();
        rulePropositionInfo.setName("Credit Check");
        rulePropositionInfo.setDesc("Credit Sum Change");
        rulePropositionInfo.setLeftHandSide(leftHandSideInfo);
        rulePropositionInfo.setRightHandSide(rightHandSideInfo);
        rulePropositionInfo.setComparisonDataTypeKey(BigDecimal.class.getName());
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
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
        brInfo.setEffectiveDate(effectiveStartTime);
        brInfo.setExpirationDate(effectiveEndTime);

        List<RuleElementInfo> elementList = new ArrayList<RuleElementInfo>();
        elementList.add(reInfo);

        brInfo.setBusinessRuleElementList(elementList);
        
        return brInfo;
    }

    private PropositionReportInfo getProposition(List<PropositionReportInfo> list, String name) {
		for(PropositionReportInfo report : list) {
			if(report.getPropositionName().equals(name)) {
				return report;
			}
		}
		return null;
	}

    private boolean containsResult(List<Map<String,String>> set, String column, String value) {
    	for(Map<String,String> map : set) {
    		if (map.get(column).equals(value)) {
    			return true;
    		}
    	}
    	return false;
    }

	private void setupRuleManagement() throws Exception {
		BusinessRule businessRule1 = (BusinessRule) applicationContext.getBean("funcBusRule1");
		BusinessRule businessRule2 = (BusinessRule) applicationContext.getBean("funcBusRule2");

		businessRuleInfo1 = RuleManagementServiceAssembler.toBusinessRuleInfo(businessRule1);
		businessRuleInfo2 = RuleManagementServiceAssembler.toBusinessRuleInfo(businessRule2);

		// Generate the Drools rule source code (DRL) and save it to the rule repository
		ruleManagementService.updateBusinessRule(businessRuleId1, businessRuleInfo1);
		ruleManagementService.updateBusinessRule(businessRuleId2, businessRuleInfo2);
		
		ruleManagementService.updateBusinessRuleState(businessRuleId1, BusinessRuleStatus.ACTIVE.toString());
		ruleManagementService.updateBusinessRuleState(businessRuleId2, BusinessRuleStatus.ACTIVE.toString());
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
    public void testExecuteBusinessRule_StaticFact() throws Exception {
    	// data-beans.xml - bean id=funcBusRule1, BusinessRule id=11223344-1122-1122-1112-100000000001
    	ExecutionResultInfo result = ruleExecutionService.executeBusinessRule(businessRuleId1, null);
        Assert.assertNotNull(result);

        Assert.assertTrue(result.isExecutionSuccessful());
        Assert.assertNotNull(result.getExecutionLog());
        Assert.assertNull(result.getErrorMessage());
        Assert.assertNotNull(result.getReport());
        Assert.assertTrue(result.getReport().isSuccessful());
        Assert.assertNotNull(result.getReport().getSuccessMessage());
        Assert.assertNull(result.getReport().getFailureMessage());
    }

    @Test
    public void testExecuteBusinessRuleTest_StaticFact() throws Exception {
    	// data-beans.xml - bean id=funcBusRule2, BusinessRule id=11223344-1122-1122-1112-100000000011
    	ExecutionResultInfo result = ruleExecutionService.executeBusinessRuleTest(businessRuleInfo2, null);
        Assert.assertNotNull(result);

        Assert.assertTrue(result.isExecutionSuccessful());
        Assert.assertNotNull(result.getExecutionLog());
        Assert.assertNull(result.getErrorMessage());
        Assert.assertNotNull(result.getReport());
        Assert.assertFalse(result.getReport().isSuccessful());
        Assert.assertNull(result.getReport().getSuccessMessage());
        Assert.assertNotNull(result.getReport().getFailureMessage());
    }

    @Test
    public void testCreateBusinessRuleAndExecute_StaticFact() throws Exception {
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfo businessRule = ruleManagementService.getBusinessRuleInfo(businessRuleId);
	        Assert.assertNotNull(businessRule);

	        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);

	        Assert.assertNotNull(executionResult);
	        Assert.assertTrue(executionResult.isExecutionSuccessful());
	        Assert.assertTrue(executionResult.getReport().isSuccessful());
	        Assert.assertEquals("Intersection constraint fulfilled", executionResult.getReport().getSuccessMessage());
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateAndExecuteBusinessRule_DynamicFact() throws Exception {
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRule("CHEM200PRE_REQ", "CHEM200", false);

        ParameterMapInfo paramMap = new ParameterMapInfo();
        paramMap.addParameter("factParam.studentId", "student1");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
            Assert.assertNotNull(businessRuleId);
    		BusinessRuleInfo businessRule2 = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);

	        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);

	        Assert.assertNotNull(executionResult);
            Assert.assertNotNull(executionResult.getExecutionLog());
	        Assert.assertTrue(executionResult.getReport().isSuccessful());
	        // Test proposition reports
	        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
	        
	        PropositionReportInfo prInfo = executionResult.getReport().getPropositionReports().get(0);
	        
	        Assert.assertEquals("P1", prInfo.getPropositionName());
	        Assert.assertTrue(prInfo.isSuccessful());
	        
	        // Test criteria facts
	        Map<String,String> criteriaRowMap = prInfo.getCriteriaResult().getResultList().get(0);
			Assert.assertEquals(1, criteriaRowMap.size());
	        Assert.assertEquals("PSYC 200", criteriaRowMap.get("resultColumn.cluId"));

	        // Test facts
	        Map<String,String> factRowMap1 = prInfo.getFactResult().getResultList().get(0);
	        Map<String,String> factRowMap2 = prInfo.getFactResult().getResultList().get(1);
	        Map<String,String> factRowMap3 = prInfo.getFactResult().getResultList().get(2);
	        Assert.assertEquals(2, factRowMap1.size());
	        Assert.assertEquals(2, factRowMap2.size());
	        Assert.assertEquals(2, factRowMap3.size());
	        Assert.assertEquals("PSYC 200", factRowMap1.get("resultColumn.cluId"));
	        Assert.assertEquals("PSYC 201", factRowMap2.get("resultColumn.cluId"));
	        Assert.assertEquals("PSYC 202", factRowMap3.get("resultColumn.cluId"));

			FactResultInfo propositionResult1 = prInfo.getPropositionResult();
	        Assert.assertEquals(1, propositionResult1.getResultList().size());
			Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateAndExecuteBusinessRuleTest_StaticFact() throws Exception {
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRuleInfo("CHEM100PRE_REQ_TEST", "CHEM100");
    	businessRule1.setId("xxx");

        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, null);
        Assert.assertNotNull(executionResult);
        Assert.assertTrue(executionResult.getReport().isSuccessful());
        
        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());

        PropositionReportInfo prInfo = executionResult.getReport().getPropositionReports().get(0);
        
        Assert.assertEquals("P1", prInfo.getPropositionName());
        Assert.assertTrue(prInfo.isSuccessful());
        
        // Test criteria facts
        Map<String,String> criteriaRowMap = prInfo.getCriteriaResult().getResultList().get(0);
        Assert.assertEquals(1, criteriaRowMap.size());
        Assert.assertEquals("CPR101", criteriaRowMap.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));

        // Test facts
        Map<String,String> factRowMap1 = prInfo.getFactResult().getResultList().get(0);
        Map<String,String> factRowMap2 = prInfo.getFactResult().getResultList().get(1);
        Map<String,String> factRowMap3 = prInfo.getFactResult().getResultList().get(2);
        Assert.assertEquals(1, factRowMap1.size());
        Assert.assertEquals(1, factRowMap2.size());
        Assert.assertEquals(1, factRowMap3.size());
        Assert.assertEquals("CPR101", factRowMap1.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));
        Assert.assertEquals("CPR201", factRowMap2.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));
        Assert.assertEquals("CPR301", factRowMap3.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));

		FactResultInfo propositionResult1 = prInfo.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
    }

    @Test
    public void testCreateAndExecuteBusinessRuleTest_DynamicFact() throws Exception {
    	BusinessRuleInfo businessRule1 = createIntersectionBusinessRule("CHEM100PRE_REQ", "CHEM100", false);
    	businessRule1.setId("xxx");

        ParameterMapInfo paramMap = new ParameterMapInfo();
        paramMap.addParameter("factParam.studentId", "student1");

        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, paramMap);
        Assert.assertNotNull(executionResult);
        Assert.assertTrue(executionResult.getReport().isSuccessful());

        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
        
        PropositionReportInfo prInfo = executionResult.getReport().getPropositionReports().get(0);
        
        Assert.assertEquals("P1", prInfo.getPropositionName());
        Assert.assertTrue(prInfo.isSuccessful());
        
        // Test criteria facts
        Map<String,String> criteriaRowMap = prInfo.getCriteriaResult().getResultList().get(0);
		Assert.assertEquals(1, criteriaRowMap.size());
        Assert.assertEquals("PSYC 200", criteriaRowMap.get("resultColumn.cluId"));

        // Test facts
        Map<String,String> factRowMap1 = prInfo.getFactResult().getResultList().get(0);
        Map<String,String> factRowMap2 = prInfo.getFactResult().getResultList().get(1);
        Map<String,String> factRowMap3 = prInfo.getFactResult().getResultList().get(2);
        Assert.assertEquals(2, factRowMap1.size());
        Assert.assertEquals(2, factRowMap2.size());
        Assert.assertEquals(2, factRowMap3.size());
        Assert.assertEquals("PSYC 200", factRowMap1.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 201", factRowMap2.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 202", factRowMap3.get("resultColumn.cluId"));

		FactResultInfo propositionResult1 = prInfo.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    }

    @Test
    public void testCreateAndExecuteComplexBusinessRule_DynamicFact() throws Exception {
    	BusinessRuleInfo businessRule = createSumBusinessRule("MATH100PRE_REQ", "MATH100");

        ParameterMapInfo paramMap = new ParameterMapInfo();
	    paramMap.addParameter("factParam.studentId", "student1");

    	String businessRuleId = null;
	    try {
		    businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
			BusinessRuleInfo businessRule2 = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
	
	        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);
	        Assert.assertNotNull(executionResult);
	        Assert.assertTrue(executionResult.getReport().isSuccessful());
	        
	        Assert.assertTrue(getProposition(executionResult.getReport().getPropositionReports(), "P1").isSuccessful());
	        Assert.assertEquals("SUM", getProposition(executionResult.getReport().getPropositionReports(), "P1").getPropositionType());
	
	        PropositionReportInfo prP1 = getProposition(executionResult.getReport().getPropositionReports(), "P1");
	        
			FactResultInfo propositionResult1 = prP1.getPropositionResult();
	        Assert.assertEquals(1, propositionResult1.getResultList().size());
			Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.credit", "6.0"));
    	} finally {
    		ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateAndExecuteComplexBusinessRule_DynamicFact3() throws Exception {
    	String businessRuleId = "11223344-1122-1122-1112-100000000032";

        ParameterMapInfo paramMap = new ParameterMapInfo();
	    paramMap.addParameter("factParam.studentId", "student1");

    	BusinessRuleInfo businessRule = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
    	ruleManagementService.updateBusinessRule(businessRuleId, businessRule);
    	businessRule = ruleManagementService.updateBusinessRuleState(businessRuleId, BusinessRuleStatus.ACTIVE.toString());
    	businessRule = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);

        ExecutionResultInfo executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);

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

        PropositionReportInfo prP1 = getProposition(executionResult.getReport().getPropositionReports(), "P1");
        PropositionReportInfo prP2 = getProposition(executionResult.getReport().getPropositionReports(), "P2");
        PropositionReportInfo prP3 = getProposition(executionResult.getReport().getPropositionReports(), "P3");

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

		FactResultInfo propositionResult1 = prP1.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));

		FactResultInfo propositionResult2 = prP2.getPropositionResult();
        Assert.assertEquals(2, propositionResult2.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult2.getResultList(), "resultColumn.cluId", "PSYC 201"));
		Assert.assertTrue(containsResult(propositionResult2.getResultList(), "resultColumn.cluId", "PSYC 202"));

		FactResultInfo propositionResult3 = prP3.getPropositionResult();
        Assert.assertEquals(1, propositionResult3.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult3.getResultList(), "resultColumn.credit", "13.0"));
    }

	@Test
	public void testCreateAndExecuteAgenda_OneBusinessRule() throws Exception {
    	// Update business rule to create a compiled/executable rule
    	String businessRuleId = "11223344-1122-1122-1112-100000000011";
    	BusinessRuleInfo businessRule = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId);
    	if (!businessRule.getState().equals(BusinessRuleStatus.ACTIVE.toString())) {
//	    	if(ruleRepositoryService.getRuleSet(businessRule.getCompiledId()) == null) {
	    		businessRule = ruleManagementService.updateBusinessRule(businessRuleId, businessRule);
//	    	}
	    	businessRule = ruleManagementService.updateBusinessRuleState(businessRuleId, BusinessRuleStatus.ACTIVE.toString());
    	}

//        Map<String, String> agendaDeterminationMap = new HashMap<String, String>();
//        agendaDeterminationMap.put("agendaDeterminationInfo.luiType", "course");
//        agendaDeterminationMap.put("agendaDeterminationInfo.luiPersonRelationType", "kuali.student");
//        agendaDeterminationMap.put("agendaDeterminationInfo.relationState", "enrolled");
//        
//        AgendaDeterminationInfo adiInfo = new AgendaDeterminationInfo();
//        adiInfo.setAgendaInfoDeterminationKeyList(agendaDeterminationMap);
//		
//        AgendaInfo aiInfo = ruleManagementService.getAgendaInfo(AgendaType.KUALI_VALIDATE_LUI_PERSON_RELATION.toString(),  adiInfo);
        
        List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList = new ArrayList<BusinessRuleAnchorInfo>();
        BusinessRuleAnchorInfo anchorInfo = new BusinessRuleAnchorInfo();
        anchorInfo.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        anchorInfo.setAnchorValue("MATH 101");
        anchorInfo.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        businessRuleAnchorInfoList.add(anchorInfo);
        
        ParameterMapInfo exectionParamMap = new ParameterMapInfo();
        exectionParamMap.addParameter("factParam.studentId", "student1");

		AgendaExecutionResultInfo agendaExecutionResult = ruleExecutionService.executeAgenda(businessRuleAnchorInfoList, exectionParamMap);

        Assert.assertTrue(agendaExecutionResult.isExecutionSuccessful());
        Assert.assertFalse(agendaExecutionResult.isAgendaReportSuccessful());
        Assert.assertNotNull(agendaExecutionResult);
        Assert.assertEquals(1, agendaExecutionResult.getExecutionResultList().size());

        Assert.assertEquals("Credit check failed. Sum is short by 3.0", agendaExecutionResult.getFailureMessageSummary());
        Assert.assertNull(agendaExecutionResult.getSuccessMessageSummary());

        // This agenda only has one rule
        ExecutionResultInfo ruleResult = agendaExecutionResult.getExecutionResultList().get(0);

        Assert.assertEquals("Credit check failed. Sum is short by 3.0", ruleResult.getReport().getFailureMessage());
		Assert.assertEquals(2, ruleResult.getReport().getPropositionReports().size());
		
		// First proposition
		PropositionReportInfo propositionReport1 = ruleResult.getReport().getPropositionReports().get(0);
		
		Assert.assertTrue(propositionReport1.isSuccessful());
		Assert.assertEquals(YieldValueFunctionType.INTERSECTION.toString(), propositionReport1.getPropositionType());
		Assert.assertEquals("Pre-requisite course requirement met: [MATH 100]", propositionReport1.getMessage());
		
		// Second proposition
		PropositionReportInfo propositionReport2 = ruleResult.getReport().getPropositionReports().get(1);

		Assert.assertFalse(propositionReport2.isSuccessful());
		Assert.assertEquals(YieldValueFunctionType.SUM.toString(), propositionReport2.getPropositionType());
		Assert.assertEquals("Credit check failed. Sum is short by 3.0", propositionReport2.getMessage());
	}

	@Test
	public void testCreateAndExecuteAgenda_TwoBusinessRule() throws Exception {
    	// Update business rule to change state to ACTIVE and compile an executable rule
    	String businessRuleId1 = "11223344-1122-1122-1112-100000000011";
    	BusinessRuleInfo businessRule1 = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId1);
    	if (!businessRule1.getState().equals(BusinessRuleStatus.ACTIVE.toString())) {
//	    	if(ruleRepositoryService.getRuleSet(businessRule1.getCompiledId()) == null) {
	    		businessRule1 = ruleManagementService.updateBusinessRule(businessRuleId1, businessRule1);
//	    	}
	    	businessRule1 = ruleManagementService.updateBusinessRuleState(businessRuleId1, BusinessRuleStatus.ACTIVE.toString());
    	}
    	// Update business rule to change state to ACTIVE and compile an executable rule
    	String businessRuleId2 = "11223344-1122-1122-1112-100000000032";
    	BusinessRuleInfo businessRule2 = ruleManagementService.getDetailedBusinessRuleInfo(businessRuleId2);
    	if (!businessRule2.getState().equals(BusinessRuleStatus.ACTIVE.toString())) {
	    	if(ruleRepositoryService.getRuleSet(businessRule2.getCompiledId()) == null) {
	    		businessRule2 = ruleManagementService.updateBusinessRule(businessRuleId2, businessRule2);
	    	}
	    	businessRule2 = ruleManagementService.updateBusinessRuleState(businessRuleId2, BusinessRuleStatus.ACTIVE.toString());
    	}

        List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList = new ArrayList<BusinessRuleAnchorInfo>();
        
        BusinessRuleAnchorInfo anchorInfo1 = new BusinessRuleAnchorInfo();
        anchorInfo1.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        anchorInfo1.setAnchorValue("MATH 101");
        anchorInfo1.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        businessRuleAnchorInfoList.add(anchorInfo1);

        BusinessRuleAnchorInfo anchorInfo2 = new BusinessRuleAnchorInfo();
        anchorInfo2.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        anchorInfo2.setAnchorValue("PSYC 300");
        anchorInfo2.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        businessRuleAnchorInfoList.add(anchorInfo2);
        
        ParameterMapInfo exectionParamMap = new ParameterMapInfo();
        exectionParamMap.addParameter("factParam.studentId", "student1");

		AgendaExecutionResultInfo agendaExecutionResult = ruleExecutionService.executeAgenda(businessRuleAnchorInfoList, exectionParamMap);

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
        ExecutionResultInfo ruleResult1 = agendaExecutionResult.getExecutionResultList().get(0);
        Assert.assertTrue(ruleResult1.isExecutionSuccessful());
        Assert.assertFalse(ruleResult1.getReport().isSuccessful());

        // Rule 2
        ExecutionResultInfo ruleResult2 = agendaExecutionResult.getExecutionResultList().get(1);
        Assert.assertTrue(ruleResult2.isExecutionSuccessful());
        Assert.assertTrue(ruleResult2.getReport().isSuccessful());
	}
}

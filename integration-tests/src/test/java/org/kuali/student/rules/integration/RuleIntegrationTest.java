package org.kuali.student.rules.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleAdapter;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RuleIntegrationTest {

	private static RuleRepositoryService ruleRepositoryService;
	private static RuleManagementService ruleManagementService;
	private static RuleExecutionService ruleExecutionService;

	private static ApplicationContext testBeanContext;
	
	private BusinessRuleInfoDTO businessRuleInfo1;
	private BusinessRuleInfoDTO businessRuleInfo2;
	
	@BeforeClass
    public static void setUpOnce() throws Exception {
		System.setProperty("ks.test.persistenceLocation", "classpath:META-INF/rulemanagement-persistence.xml");
		System.setProperty("ks.test.daoImplClasses", "org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl|classpath:test-beans.xml");
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/rulemanagement-mock-service-context.xml");
		
		ruleRepositoryService = (RuleRepositoryService) applicationContext.getBean("repository");
		ruleManagementService = (RuleManagementService) applicationContext.getBean("ruleManagement");
		ruleExecutionService = (RuleExecutionService) applicationContext.getBean("ruleExecution");

		testBeanContext = new ClassPathXmlApplicationContext("/test-beans.xml");
	}

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
		BusinessRule businessRule1 = (BusinessRule) testBeanContext.getBean("funcBusRule1");
		BusinessRule businessRule2 = (BusinessRule) testBeanContext.getBean("funcBusRule2");

		this.businessRuleInfo1 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule1);
		this.businessRuleInfo2 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule2);
		
		// Generate the Drools rule source code (DRL) and save it to the rule repository
		//ruleManagementService.updateBusinessRule(businessRuleInfo1.getBusinessRuleId(), businessRuleInfo1);
		//ruleManagementService.updateBusinessRule(businessRuleInfo2.getBusinessRuleId(), businessRuleInfo2);
    }

    @After
    public void tearDown() throws Exception {
    	this.businessRuleInfo1 = null;
    	this.businessRuleInfo2 = null;
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	return cal.getTime();
    }

    /*
     * Builds the fact structure to be used as a criteria
     */
    private FactStructureDTO buildFactStructureForRuleCriteria() {
        FactStructureDTO fs = new FactStructureDTO();

        fs.setFactStructureId("1");
        fs.setFactTypeKey("fact.cpr_prereq_criteria");
        fs.setAnchorFlag(false);
        fs.setStaticFact(true);
        fs.setStaticValue("CPR101");
        fs.setStaticValueDataType(String.class.getName());

        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs.setParamValueMap(paramVariableMap);

        return fs;
    }
    
    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureDTO buildFactStructureForIntersection() {
        FactStructureDTO fs = new FactStructureDTO();
        
        fs.setFactStructureId("2");
        fs.setFactTypeKey("fact.cpr_prereq_clulist1");
        fs.setAnchorFlag(false);
        fs.setStaticFact(true);
        fs.setStaticValue("CPR101, CPR201, CPR301");
        fs.setStaticValueDataType(String.class.getName());
        
        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs.setParamValueMap(paramVariableMap);
        
        return fs;                
    }

    private BusinessRuleInfoDTO generateNewBusinessRuleInfo(String businessRuleId, String ruleName, String anchor) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
     
        FactStructureDTO fs1 = buildFactStructureForRuleCriteria();
        FactStructureDTO fs2 = buildFactStructureForIntersection();
        
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
        brInfoDTO.setBusinessRuleId(businessRuleId);
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
    
    /*@Test
    public void testRuleIntegration_ExecuteBusinessRuleWithStaticFact() throws Exception {
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo("100", "CHEM100PRE_REQ", "CHEM100");
        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);
        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertTrue(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getSuccessMessage());
        assertNull(result.getReport().getFailureMessage());
        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());
    }

    @Test
    public void testRuleIntegration_ExecuteBusinessRuleWithStaticFact_BadEffectiveDates() throws Exception {
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo("200", "CHEM200PRE_REQ", "CHEM200");
        brInfoDTO.setEffectiveStartTime(new Date());
        brInfoDTO.setEffectiveEndTime(new Date());

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);
        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getErrorMessage());
        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());
    }

    @Test
    public void testRuleIntegration_ExecuteBusinessRuleSnapshotWithStaticFact() throws Exception {
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo("300", "CHEM300PRE_REQ", "CHEM300");
        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        //Create snapshot - Active business rule will create a snapshot
        brInfoDTO.setStatus(BusinessRuleStatus.ACTIVE.toString());
        ruleManagementService.updateBusinessRule(businessRuleId, brInfoDTO);
        
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);
        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertTrue(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getSuccessMessage());
        assertNull(result.getReport().getFailureMessage());
        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());
    }*/

    @Test
    public void testExecuteBusinessRuleWithStaticFact_Rule1() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo1;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "1000"
    	brInfoDTO.setBusinessRuleId("1000");
        brInfoDTO.setName("CHEM100PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM100");
    	
        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);

        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertTrue(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getSuccessMessage());
        assertNull(result.getReport().getFailureMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
    }

    @Test
    public void testExecuteBusinessRuleWithStaticFact_Rule1_BadEffectiveDates() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo1;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "2000"
    	brInfoDTO.setBusinessRuleId("2000");
        brInfoDTO.setName("CHEM200PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM200");

    	brInfoDTO.setEffectiveStartTime(new Date());
        brInfoDTO.setEffectiveEndTime(new Date());

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getErrorMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
    }

    @Test
    public void testExecuteBusinessRuleSnapshotWithStaticFact_Rule1() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo1;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "3000"
    	brInfoDTO.setBusinessRuleId("3000");
        brInfoDTO.setName("CHEM300PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM300");

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        //Create snapshot - Active business rule will create a snapshot
        brInfoDTO.setStatus(BusinessRuleStatus.ACTIVE.toString());
        ruleManagementService.updateBusinessRule(businessRuleId, brInfoDTO);
        
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertTrue(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getSuccessMessage());
        assertNull(result.getReport().getFailureMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
    }

    @Test
    public void testExecuteBusinessRuleWithStaticFact_Rule2() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo2;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "1000"
    	brInfoDTO.setBusinessRuleId("4000");
        brInfoDTO.setName("CHEM400PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM400");
    	
        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);

        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getFailureMessage());
        assertNull(result.getReport().getSuccessMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
    }

    @Test
    public void testExecuteBusinessRuleSnapshotWithStaticFact_Rule2() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo2;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "3000"
    	brInfoDTO.setBusinessRuleId("5000");
        brInfoDTO.setName("CHEM500PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM500");

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        //Create snapshot - Active business rule will create a snapshot
        brInfoDTO.setStatus(BusinessRuleStatus.ACTIVE.toString());
        ruleManagementService.updateBusinessRule(businessRuleId, brInfoDTO);
        
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getFailureMessage());
        assertNull(result.getReport().getSuccessMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
    }

}

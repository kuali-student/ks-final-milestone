package org.kuali.student.rules.integration;

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
import org.kuali.student.rules.internal.common.utils.ServiceFactory;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
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

    private static RuleManagementService ruleManagementService;
    private static RuleRepositoryService ruleRepositoryService;
    
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
    
    @Test
    public void testCreateBusinessRule() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("1000", "CHEM200PRE_REQ", "CHEM100");

    	try {
    		String businessRuleId = ruleManagementService.createBusinessRule(businessRule1);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
	        System.out.println("********** Business Rule ID            : "+businessRule2.getBusinessRuleId());
	        System.out.println("********** Business Rule Name          : "+businessRule2.getName());
    	} finally {
    		ruleManagementService.deleteBusinessRule("1000");
    	}
    }
    
    @Test
    public void testCreateAndFetchBusinessRule() throws Exception {
    	BusinessRuleInfoDTO businessRule = generateNewBusinessRuleInfo("1000", "CHEM100PRE_REQ", "CHEM100");

    	try {
    		String businessRuleId = ruleManagementService.createBusinessRule(businessRule);
	        System.out.println("********** businessRuleId              : "+businessRuleId);
	        
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("********** Business Rule ID            : "+businessRule.getBusinessRuleId());
	        System.out.println("********** Business Rule Name          : "+businessRule.getName());
	        System.out.println("********** Business Compiled ID        : "+businessRule.getCompiledId());
	        System.out.println("********** Business Compiled Version No: "+businessRule.getCompiledVersionNumber());
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        System.out.println("********** RuleSet Name:           "+ruleSet.getName());
	        System.out.println("********** RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("********** RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("********** RuleSet Source:\n"+ruleSet.getContent());
    	} finally {
    		ruleManagementService.deleteBusinessRule("1000");
    	}
    }

    @Test
    public void testUpdateBusinessRule() throws Exception {
    	BusinessRuleInfoDTO businessRule = generateNewBusinessRuleInfo("2000", "CHEM200PRE_REQ", "CHEM100");
    	
    	try {
    		String businessRuleId = ruleManagementService.createBusinessRule(businessRule);
	        System.out.println("********** businessRuleId              : "+businessRuleId);
	        StatusDTO status = ruleManagementService.updateBusinessRule(businessRuleId, businessRule);
	        System.out.println("********** status                      : "+status);
	
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("********** Business Rule ID            : "+businessRule.getBusinessRuleId());
	        System.out.println("********** Business Rule Name          : "+businessRule.getName());
	        System.out.println("********** Business Compiled ID        : "+businessRule.getCompiledId());
	        System.out.println("********** Business Compiled Version No: "+businessRule.getCompiledVersionNumber());
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        System.out.println("********** RuleSet Name:           "+ruleSet.getName());
	        System.out.println("********** RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("********** RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("********** RuleSet Source:\n"+ruleSet.getContent());
    	} finally {
    		ruleManagementService.deleteBusinessRule("2000");
    	}
    }
}

package org.kuali.student.rules.rulemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.AgendaType;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;

@Daos({@Dao(value = "org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl", testDataFile = "classpath:test-beans.xml")})
@PersistenceFileLocation("classpath:META-INF/rulemanagement-persistence.xml")
public class TestRulesManagementServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.rules.rulemanagement.service.impl.RuleManagementServiceImpl", port = "8181")
    public RuleManagementService client;
    
    public static final String ruleId_1 = "11223344-1122-1122-1112-100000000001";
    public static final String ruleId_2 = "11223344-1122-1122-1112-100000000011";    
    private String ruleId_3 = "11223344-1122-1122-1112-100000000032";
    
    @Test
    public void testFetchDetailedBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleInfoDTO brInfoDTO = client.fetchDetailedBusinessRuleInfo(ruleId_1);
        assertEquals(ruleId_1,  brInfoDTO.getBusinessRuleId());
        assertEquals("CPR 201", brInfoDTO.getAnchorValue());
        assertEquals(3, brInfoDTO.getRuleElementList().size());
                
        RulePropositionDTO ruleProp = brInfoDTO.getRuleElementList().get(0).getRuleProposition();
        
        List<FactStructureDTO> factStructureList = ruleProp.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(2, factStructureList.size());    
        
        FactStructureDTO fs1 = factStructureList.get(0);
        assertEquals("CPR 101", fs1.getStaticValue());

        FactStructureDTO fs2 = factStructureList.get(1);
        assertEquals("CPR 101, MATH 101, MATH 102", fs2.getStaticValue());
    }    

    @Test
    public void testFetchBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleInfoDTO brInfoDTO = client.fetchBusinessRuleInfo(ruleId_1);
        assertEquals(ruleId_1,  brInfoDTO.getBusinessRuleId());
        assertEquals("CPR 201", brInfoDTO.getAnchorValue());
        assertEquals(0, brInfoDTO.getRuleElementList().size());
    }    

    @Test
    public void testFindBusinessRuleIdByType()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<String> ruleIdList = client.findBusinessRuleIdsByBusinessRuleType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
    
        assertEquals(2, ruleIdList.size());
        assertEquals(ruleId_2, ruleIdList.get(0));
        assertEquals(ruleId_3, ruleIdList.get(1));
    }
    
    @Test
    public void testInvalidFetchBusinessRule()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        try {
            // Business rule with ruleId 0 does not exists
            BusinessRuleInfoDTO brInfo = client.fetchBusinessRuleInfo("0");
        } catch (DoesNotExistException ex) {
            // Right behavior
            return;
        }        
       
        assertTrue(false);
    }

    @Test
    public void testFindAgendaTypes() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> agendaTypes = client.findAgendaTypes();
        assertTrue(agendaTypes.size() == 2);
        assertTrue(agendaTypes.contains(AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSE.toString()));
        assertTrue(agendaTypes.contains(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE.toString()));
    }
    
    @Test
    public void testFindAnchorTypes()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<String> anchorTypes = client.findAnchorTypes();

        assertTrue(anchorTypes.size() == 1);
        assertTrue(anchorTypes.contains(AnchorTypeKey.KUALI_COURSE.toString()));
    }
    
    @Test
    public void testFindAnchorsByAnchorType()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        List<String> anchors = client.findAnchorsByAnchorType(AnchorTypeKey.KUALI_COURSE.toString());
        
        assertTrue(anchors.size() == 3);
        assertTrue(anchors.contains("CPR 201"));
        assertTrue(anchors.contains("MATH 101"));
        assertTrue(anchors.contains("PSYC 300"));
    }

    @Test
    public void testFetchBusinessRuleType()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleTypeDTO ruleType = client.fetchBusinessRuleType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString(), AnchorTypeKey.KUALI_COURSE.toString());
        
        assertEquals(BusinessRuleTypeKey.KUALI_PRE_REQ.toString(), ruleType.getBussinessRuleTypeKey());
        assertEquals(AnchorTypeKey.KUALI_COURSE.toString(),ruleType.getAnchorTypeKey());
    }
    
    @Test
    public void testFetchFactTypeKey()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleTypeDTO ruleType = client.fetchBusinessRuleType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString(), AnchorTypeKey.KUALI_COURSE.toString());
        
        assertEquals(2, ruleType.getFactTypeKeyList().size());
        assertEquals("fact.earned_credit_list", ruleType.getFactTypeKeyList().get(0));
        assertEquals("fact.completed_course_list", ruleType.getFactTypeKeyList().get(1));
    }
    
    
    @Test
    public void testCreateBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        // Create Rule Test
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo();
        String ruleId = client.createBusinessRule(brInfoDTO);
        
        BusinessRuleInfoDTO newBrInfoDTO = client.fetchBusinessRuleInfo(ruleId);
        assertEquals(ruleId, newBrInfoDTO.getBusinessRuleId());
    }
    
    @Test
    public void testUpdateBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {        

        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Update Rule");
        String ruleId = client.createBusinessRule(brInfoDTO);
        
        BusinessRuleInfoDTO updateBrInfoDTO = generateUpdatedBusinessRule( generateNewBusinessRuleInfo() );
        brInfoDTO.setName("Update Rule");
        updateBrInfoDTO.setBusinessRuleId(ruleId);
        
        client.updateBusinessRule(ruleId, updateBrInfoDTO);
        
        BusinessRuleInfoDTO newBrInfoDTO1 = client.fetchDetailedBusinessRuleInfo(ruleId);        
        assertTrue(newBrInfoDTO1.getRuleElementList().size() == 3);
        assertEquals(newBrInfoDTO1.getStatus(), "DRAFT_IN_PROGRESS");
    }
    
    @Test
    public void testActivateBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("activate rule test");
        String ruleId = client.createBusinessRule(brInfoDTO);

        brInfoDTO.setStatus("ACTIVE");      
        brInfoDTO.setBusinessRuleId(ruleId);
        client.updateBusinessRule(ruleId, brInfoDTO);
                
        BusinessRuleInfoDTO newBrInfoDTO1 = client.fetchDetailedBusinessRuleInfo(ruleId);        
        assertEquals(newBrInfoDTO1.getStatus(), "ACTIVE");
        assertEquals(newBrInfoDTO1.getCompiledId() + "_SNAPSHOT", newBrInfoDTO1.getRepositorySnapshotName() );
                
        // Check if the exception is caught when trying to change ACTIVE rule
        brInfoDTO.setStatus("DRAFT_IN_PROGRESS");        
        try {
            client.updateBusinessRule(ruleId, brInfoDTO);
            fail("Updating business rule from 'ACTIVE' to 'DRAFT_IN_PROGRESS' should have failed");
        } catch (InvalidParameterException ex) {
            // This is an expected exception
        	assertTrue(true);
        }
    }
    
    @Test
    public void testRetireBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("retire rule test");
        String ruleId = client.createBusinessRule(brInfoDTO);
        
        brInfoDTO.setStatus("ACTIVE");
        brInfoDTO.setBusinessRuleId(ruleId);        
        client.updateBusinessRule(ruleId, brInfoDTO);

        brInfoDTO.setStatus("RETIRED");
        client.updateBusinessRule(ruleId, brInfoDTO);
        
        BusinessRuleInfoDTO newBrInfoDTO1 = client.fetchDetailedBusinessRuleInfo(ruleId);        
        assertEquals(newBrInfoDTO1.getStatus(), "RETIRED");                
    }    
    
    
    @Test
    public void testFetchBusinessRuleEnglish() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        String englishValue = client.fetchBusinessRuleEnglish("1");
        assertEquals("Rule1", englishValue);
    }

    @Test
    public void testFindBusinessRuleType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> brTypeKeys = client.findBusinessRuleTypes();
        
        assertTrue(brTypeKeys.size() == 2);
        assertTrue(brTypeKeys.contains(BusinessRuleTypeKey.KUALI_CO_REQ.toString()));        
    }
    
    @Test
    public void testFindBusinessRuleTypeByAgenda() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> brTypeKeys = client.findBusinessRuleTypesByAgendaType("KUALI_STUDENT_STUDENT_DROPS_COURSE");
        
        assertTrue(brTypeKeys.size() == 1);
        assertTrue(brTypeKeys.contains(BusinessRuleTypeKey.KUALI_PRE_REQ.toString()));
    }
    
    @Test
    public void testDeleteBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException {
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Delete Rule");
        String ruleId = client.createBusinessRule(brInfoDTO);
                
        BusinessRuleInfoDTO newBrInfoDTO = client.fetchBusinessRuleInfo(ruleId);
        assertEquals(ruleId, newBrInfoDTO.getBusinessRuleId());        
        
        client.deleteBusinessRule(ruleId);

        try {
            BusinessRuleInfoDTO newBrInfoDTO1 = client.fetchDetailedBusinessRuleInfo(ruleId_1);
        } catch (DoesNotExistException ex) {
            assertTrue(true);
        }                        
    }
    
    @Test
    public void testFetchBusinessRuleByAnchor()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException {
        BusinessRuleAnchorDTO anchorDTO = new BusinessRuleAnchorDTO();
        anchorDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_CO_REQ.toString());
        anchorDTO.setAnchorValue("CPR 201");
        anchorDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        
        List<BusinessRuleInfoDTO> rules = client.fetchBusinessRuleInfoByAnchor(anchorDTO);
                
        assertEquals(1, rules.size());
        
        BusinessRuleInfoDTO ruleInfo = rules.get(0);
        
        assertEquals(ruleId_1, ruleInfo.getBusinessRuleId());        
    }

    
    /**
     * 
     * This method tests business rule with complex rule element and fact structure created through test-beans
     * 
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DependentObjectsExistException
     * @throws PermissionDeniedException
     * @throws AlreadyExistsException
     */
    @Test
    public void testComplextBusinessRuleCreation()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException {
        BusinessRuleInfoDTO brInfo = client.fetchDetailedBusinessRuleInfo(ruleId_3);
        
        assertEquals(ruleId_3, brInfo.getBusinessRuleId());
        assertEquals("PSYC 300", brInfo.getAnchorValue());
        
        
        List<RuleElementDTO> ruleElements = brInfo.getRuleElementList();
        assertEquals(11, ruleElements.size());
        
        
        RulePropositionDTO proposition1 = ruleElements.get(1).getRuleProposition();
        RulePropositionDTO proposition2 = ruleElements.get(5).getRuleProposition();
        RulePropositionDTO proposition3 = ruleElements.get(9).getRuleProposition();
        
        RuleElementDTO ruleElementL = ruleElements.get(0);
        assertEquals(ruleElementL.getOperation(), "(");
                
        assertEquals("INTERSECTION", proposition1.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
        assertEquals("INTERSECTION", proposition2.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
        assertEquals("SUM", proposition3.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
        
        List<FactStructureDTO> prop1facts = proposition1.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(2, prop1facts.size());
        FactStructureDTO fact11 = prop1facts.get(0);
        assertEquals(true, fact11.isStaticFact());
        assertEquals("fact.psyc300_prereq_criteria1", fact11.getFactTypeKey());
        
        
        List<FactStructureDTO> prop2facts = proposition2.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(2, prop2facts.size());
        
        
        List<FactStructureDTO> prop3facts = proposition3.getLeftHandSide().getYieldValueFunction().getFactStructureList();
        assertEquals(1, prop3facts.size());

        FactStructureDTO fact31 = prop3facts.get(0);
        Map<String, String> paramMap = fact31.getParamValueMap();
        
        assertTrue(paramMap.containsKey("factParam.studentId"));
        assertTrue(paramMap.containsKey("factParam.clusetId"));
        assertTrue(paramMap.containsKey("factParam.excludeCluSet"));
        
        assertEquals("PSYC 205, PSYC 263",paramMap.get("factParam.excludeCluSet"));
        
    }
    
    @Test
    public void testEmptyRuleCreating()   throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException {

        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo();
        brInfoDTO.setName("Empty rule");
        // Empty the rule elements
        brInfoDTO.setRuleElementList(new ArrayList<RuleElementDTO>());

        String ruleId = "";
        try {
            ruleId = client.createBusinessRule(brInfoDTO);
        } catch (Exception e) {
            assertTrue(false);
        }
        
        BusinessRuleInfoDTO newBrInfoDTO = client.fetchBusinessRuleInfo(ruleId);
        assertEquals(0, newBrInfoDTO.getRuleElementList().size());
        
        assertTrue(true);
    }
    
    private BusinessRuleInfoDTO generateNewBusinessRuleInfo() {
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
        brInfoDTO.setName("CHEM100PRE_REQ");
        brInfoDTO.setDescription("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue("CHEM 100");
        brInfoDTO.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);
        brInfoDTO.setEffectiveStartTime(new Date());
        brInfoDTO.setEffectiveEndTime(new Date());

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO);

        brInfoDTO.setRuleElementList(elementList);
        
        return brInfoDTO;
    }

    
    private BusinessRuleInfoDTO generateUpdatedBusinessRule(BusinessRuleInfoDTO brInfoDTO) {
        
        brInfoDTO.setStatus("DRAFT_IN_PROGRESS");
                
        // Rule element - I
        YieldValueFunctionDTO yieldValueFunction1 = new YieldValueFunctionDTO();
        yieldValueFunction1.setYieldValueFunctionType("INTERSECTION");
        
        FactStructureDTO fs1 = buildFactStructureForRuleCriteria();
        FactStructureDTO fs2 = buildFactStructureForIntersection();

        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs1.setParamValueMap(paramVariableMap);
        
        List<FactStructureDTO> factStructureList1 = new ArrayList<FactStructureDTO>();
        factStructureList1.add(fs1);
        factStructureList1.add(fs2);
        yieldValueFunction1.setFactStructureList(factStructureList1);
        
        LeftHandSideDTO leftHandSide1 = new LeftHandSideDTO();
        leftHandSide1.setYieldValueFunction(yieldValueFunction1);
       
        RightHandSideDTO rightHandSide1 = new RightHandSideDTO();
        rightHandSide1.setExpectedValue("1");
        
        RulePropositionDTO ruleProposition1 = new RulePropositionDTO();
        ruleProposition1.setName("Must-have-1-of-CPR101");
        ruleProposition1.setDescription("Course intersection");
        ruleProposition1.setLeftHandSide(leftHandSide1);
        ruleProposition1.setRightHandSide(rightHandSide1);
        ruleProposition1.setComparisonDataType(String.class.getName());
        ruleProposition1.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

        RuleElementDTO re1 = new RuleElementDTO();
        re1.setName("course.intersection.1.of.cpr101");
        re1.setDescription("Must have 1 of CPR 101");
        re1.setOperation("PROPOSITION");
        re1.setRuleProposition(ruleProposition1);
        
        // Rule Element - II        
        RuleElementDTO re2 = new RuleElementDTO();
        re2.setName("And");
        re2.setDescription("And");
        re2.setOperation("AND");
                
        // Rule Element - III
        YieldValueFunctionDTO yieldValueFunction2 = new YieldValueFunctionDTO();
        yieldValueFunction2.setYieldValueFunctionType("SUM");
        
        FactStructureDTO fs3 = buildFactStructureForSum();

        // Not need for summation or averages
        Map<String,String> paramVariableMap2 = new HashMap<String,String>();
        fs3.setParamValueMap(paramVariableMap2);
        
        List<FactStructureDTO> factStructureList2 = new ArrayList<FactStructureDTO>();
        factStructureList2.add(fs3);
        yieldValueFunction2.setFactStructureList(factStructureList2);
        
        LeftHandSideDTO leftHandSide2 = new LeftHandSideDTO();
        leftHandSide2.setYieldValueFunction(yieldValueFunction2);
       
        RightHandSideDTO rightHandSide2 = new RightHandSideDTO();
        rightHandSide2.setExpectedValue("10.0");
        
        RulePropositionDTO ruleProposition2 = new RulePropositionDTO();
        ruleProposition2.setName("Course-credit-summation");
        ruleProposition2.setDescription("Course credit summation");
        ruleProposition2.setLeftHandSide(leftHandSide2);
        ruleProposition2.setRightHandSide(rightHandSide2);
        ruleProposition2.setComparisonDataType(BigDecimal.class.getName());
        ruleProposition2.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

        RuleElementDTO re3 = new RuleElementDTO();
        re3.setName("course.credits.sum");
        re3.setDescription("Pre req check for CPR 101");
        re3.setOperation("PROPOSITION");
        re3.setRuleProposition(ruleProposition2);
        
        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(re1);
        elementList.add(re2);
        elementList.add(re3);

        brInfoDTO.setRuleElementList(elementList);
        
        return brInfoDTO;
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
        
        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs.setParamValueMap(paramVariableMap);
        
        return fs;                
    }

    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureDTO buildFactStructureForSum() {
        FactStructureDTO fs = new FactStructureDTO();
        
        fs.setFactStructureId("3");
        fs.setFactTypeKey("fact.cpr_prereq_gpalist");
        fs.setAnchorFlag(false);
        fs.setStaticFact(true);
        fs.setStaticValue("4.0, 2.5, 3.0");
        
        Map<String,String> paramVariableMap = new HashMap<String,String>();
        fs.setParamValueMap(paramVariableMap);
        
        return fs;                
    }

}

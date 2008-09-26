package org.kuali.student.rules.rulemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.internal.common.entity.AgendaType;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
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

    private BusinessRuleInfoDTO brInfoDTO = null;

    // @Test
    // public void testFetchBusinessRuleInfo() throws OperationFailedException, DoesNotExistException,
    // InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
    // BusinessRuleInfoDTO brInfoDTO = client.fetchBusinessRuleInfo("1");
    //	    
    // assertEquals(brInfoDTO.getBusinessRuleId(), "1");
    // }
    //	

    @Before
    public void setup() {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");

        YieldValueFunctionDTO yieldValueFunctionDTO = new YieldValueFunctionDTO();
        yieldValueFunctionDTO.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION.toString());
        yieldValueFunctionDTO.setFactStructureList(new ArrayList<FactStructureDTO>());

        LeftHandSideDTO leftHandSideDTO = new LeftHandSideDTO();
        leftHandSideDTO.setYieldValueFunction(yieldValueFunctionDTO);

        RightHandSideDTO rightHandSideDTO = new RightHandSideDTO();
        rightHandSideDTO.setExpectedValue("12.0");

        RulePropositionDTO rulePropositionDTO = new RulePropositionDTO();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDescription("Credit Intersection Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataType("kuali.number");
        rulePropositionDTO.setComparisonOperatorType(ComparisonOperator.LESS_THAN.toString());

        RuleElementDTO reDTO = new RuleElementDTO();
        reDTO.setName("Pre-req 1");
        reDTO.setDescription("Pre req check for Math 101");
        reDTO.setOperation(RuleElementType.PROPOSITION.toString());
        reDTO.setRuleProposition(rulePropositionDTO);

        brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setBusinessRuleId("100");
        brInfoDTO.setName("CHEM 100 course prerequisites");
        brInfoDTO.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue("CHEM 100");
        brInfoDTO.setStatus("PENDING");
        brInfoDTO.setMetaInfo(metaInfo);
        brInfoDTO.setEffectiveStartTime(new Date());
        brInfoDTO.setEffectiveEndTime(new Date());

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO);

        brInfoDTO.setRuleElementList(elementList);
    }

    @Test
    public void testCreateBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        client.createBusinessRule(brInfoDTO);
        BusinessRuleInfoDTO newBrInfoDTO = client.fetchBusinessRuleInfo("100");
        assertEquals(brInfoDTO.getBusinessRuleId(), newBrInfoDTO.getBusinessRuleId());
    }

    @Test
    public void testUpdateBusinessRule()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        
        brInfoDTO.setStatus("ACTIVE");
        brInfoDTO.setBusinessRuleId("1");
        
        YieldValueFunctionDTO yieldValueFunctionDTO = new YieldValueFunctionDTO();
        yieldValueFunctionDTO.setYieldValueFunctionType(YieldValueFunctionType.SUM.toString());
        yieldValueFunctionDTO.setFactStructureList(new ArrayList<FactStructureDTO>());

        LeftHandSideDTO leftHandSideDTO = new LeftHandSideDTO();
        leftHandSideDTO.setYieldValueFunction(yieldValueFunctionDTO);

        RightHandSideDTO rightHandSideDTO = new RightHandSideDTO();
        rightHandSideDTO.setExpectedValue("15.0");

        RulePropositionDTO rulePropositionDTO = new RulePropositionDTO();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDescription("Credit Intersection Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataType("kuali.number");
        rulePropositionDTO.setComparisonOperatorType(ComparisonOperator.EQUAL_TO.toString());
        
        RuleElementDTO reDTO1 = new RuleElementDTO();
        reDTO1.setName("P3");
        reDTO1.setDescription("Pre req check for Math 101");
        reDTO1.setOperation(RuleElementType.PROPOSITION.toString());
        reDTO1.setRuleProposition(rulePropositionDTO);
        
        YieldValueFunctionDTO yieldValueFunctionDTO2 = new YieldValueFunctionDTO();
        yieldValueFunctionDTO2.setYieldValueFunctionType(YieldValueFunctionType.SUBSET.toString());
        yieldValueFunctionDTO2.setFactStructureList(new ArrayList<FactStructureDTO>());

        LeftHandSideDTO leftHandSideDTO2 = new LeftHandSideDTO();
        leftHandSideDTO2.setYieldValueFunction(yieldValueFunctionDTO2);

        RightHandSideDTO rightHandSideDTO2 = new RightHandSideDTO();
        rightHandSideDTO2.setExpectedValue("10.0");

        RulePropositionDTO rulePropositionDTO2 = new RulePropositionDTO();
        rulePropositionDTO2.setName("Credit Check");
        rulePropositionDTO2.setDescription("Credit Intersection Change");
        rulePropositionDTO2.setLeftHandSide(leftHandSideDTO2);
        rulePropositionDTO2.setRightHandSide(rightHandSideDTO2);
        rulePropositionDTO2.setComparisonDataType("kuali.number");
        rulePropositionDTO2.setComparisonOperatorType(ComparisonOperator.EQUAL_TO.toString());

        RuleElementDTO reDTO2 = new RuleElementDTO();
        reDTO2.setName("P4");
        reDTO2.setDescription("Pre req check for Math 101");
        reDTO2.setOperation(RuleElementType.PROPOSITION.toString());
        reDTO2.setRuleProposition(rulePropositionDTO2);
                
        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO1);
        elementList.add(reDTO2);    

        brInfoDTO.setRuleElementList(elementList);
        
        client.updateBusinessRule("1", brInfoDTO);
        
        BusinessRuleInfoDTO newBrInfoDTO = client.fetchDetailedBusinessRuleInfo("1");        
        assertTrue(newBrInfoDTO.getRuleElementList().size() == 2);
        assertEquals(newBrInfoDTO.getStatus(), "ACTIVE");
    }
    
    @Test
    public void testFetchBusinessRuleEnglish() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        String englishValue = client.fetchBusinessRuleEnglish("1");
        assertEquals("Rule1", englishValue);
    }

    @Test
    public void testFindAgendaTypes() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> agendaTypes = client.findAgendaTypes();
        assertTrue(agendaTypes.size() == 2);
        assertTrue(agendaTypes.contains(AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSRE.toString()));
        assertTrue(agendaTypes.contains(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE.toString()));
    }

    @Test
    public void testFindBusinessRuleType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> brTypeKeys = client.findBusinessRuleTypesByAgendaType("ANY_KEY");
        
        assertTrue(brTypeKeys.size() == 2);
        assertTrue(brTypeKeys.contains(BusinessRuleTypeKey.KUALI_CO_REQ.toString()));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        List<String> brTypeKeys1 = client.findBusinessRuleTypesByAgendaType("ANY_KEY");

        assertTrue(brTypeKeys1.size() == 2);
        assertTrue(brTypeKeys1.contains(BusinessRuleTypeKey.KUALI_CO_REQ.toString()));

    }
}

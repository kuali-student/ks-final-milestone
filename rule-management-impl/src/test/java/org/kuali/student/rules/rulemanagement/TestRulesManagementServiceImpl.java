package org.kuali.student.rules.rulemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.kuali.student.rules.translators.util.Constants;

@Daos({@Dao(value = "org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl", testDataFile = "classpath:test-beans.xml")})
@PersistenceFileLocation("classpath:META-INF/rulemanagement-persistence.xml")
public class TestRulesManagementServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.rules.rulemanagement.service.impl.RuleManagementServiceImpl", port = "8181")
    public RuleManagementService client;
    
        
    @Test
    public void testCreateAndUpdateBusinessRule() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, AlreadyExistsException, PermissionDeniedException {
        // Create Rule Test
        BusinessRuleInfoDTO brInfoDTO = generateNewBusinessRuleInfo();
        client.createBusinessRule(brInfoDTO);
        BusinessRuleInfoDTO newBrInfoDTO = client.fetchBusinessRuleInfo("100");
        assertEquals(brInfoDTO.getBusinessRuleId(), newBrInfoDTO.getBusinessRuleId());
        
        brInfoDTO = generateUpdatedBusinessRule(brInfoDTO);
        client.updateBusinessRule("100", brInfoDTO);
        
        BusinessRuleInfoDTO newBrInfoDTO1 = client.fetchDetailedBusinessRuleInfo("100");        
        assertTrue(newBrInfoDTO1.getRuleElementList().size() == 3);
        assertEquals(newBrInfoDTO1.getStatus(), "ACTIVE");
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
    
//    @Test
    public void testDeleteBusinessRuleType()  throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, PermissionDeniedException {
        client.deleteBusinessRule("1");
        
        try {
            BusinessRuleInfoDTO ruleInfoDTO = client.fetchDetailedBusinessRuleInfo("1");
        } catch (Exception e) {
            String s = "temp";
        }
        
    }

    private BusinessRuleInfoDTO generateNewBusinessRuleInfo() {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
        
        FactStructureDTO fs1 = new FactStructureDTO();
        fs1.setDataType(java.util.Set.class.getName());
        fs1.setFactStructureId("f1");
        fs1.setAnchorFlag(false);

        Map<String,String> definitionVariableMap1 = new HashMap<String,String>();
        definitionVariableMap1.put(Constants.DEF_CRITERIA_KEY, "CPR101");
        fs1.setDefinitionVariableList(definitionVariableMap1);

        Map<String,String> executionVariableMap1 = new HashMap<String,String>();
        //executionVariableMap1.put(Constants.EXE_FACT_KEY, "intersection.courseSet");
        fs1.setExecutionVariableList(executionVariableMap1);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(fs1);
        
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
        brInfoDTO.setBusinessRuleId("100");
        brInfoDTO.setName("CHEM100PRE_REQ");
        brInfoDTO.setDescription("Prerequsite courses required in order to enroll in CHEM 100");
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
        
        return brInfoDTO;
    }

    
    private BusinessRuleInfoDTO generateUpdatedBusinessRule(BusinessRuleInfoDTO brInfoDTO) {
        
        brInfoDTO.setStatus("ACTIVE");
        brInfoDTO.setBusinessRuleId("1");
                
        // Rule element - I
        YieldValueFunctionDTO yieldValueFunction1 = new YieldValueFunctionDTO();
        yieldValueFunction1.setYieldValueFunctionType("INTERSECTION");
        
        FactStructureDTO fs1 = new FactStructureDTO();
        fs1.setDataType(java.util.Set.class.getName());
        fs1.setFactStructureId("f1");
        fs1.setAnchorFlag(false);

        Map<String,String> definitionVariableMap1 = new HashMap<String,String>();
        definitionVariableMap1.put(Constants.DEF_CRITERIA_KEY, "CPR101");
        fs1.setDefinitionVariableList(definitionVariableMap1);

        Map<String,String> executionVariableMap1 = new HashMap<String,String>();
        //executionVariableMap1.put(Constants.EXE_FACT_KEY, "intersection.courseSet");
        fs1.setExecutionVariableList(executionVariableMap1);
        
        List<FactStructureDTO> factStructureList1 = new ArrayList<FactStructureDTO>();
        factStructureList1.add(fs1);
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
        
        FactStructureDTO fs2 = new FactStructureDTO();
        fs2.setDataType(java.math.BigDecimal.class.getName());
        fs2.setFactStructureId("f2");
        fs2.setAnchorFlag(false);

        // Not need for summation or averages
        Map<String,String> definitionVariableMap2 = new HashMap<String,String>();
        definitionVariableMap2.put(Constants.DEF_CRITERIA_KEY, null);
        fs2.setDefinitionVariableList(definitionVariableMap2);

        Map<String,String> executionVariableMap2 = new HashMap<String,String>();
        //executionVariableMap2.put(Constants.EXE_FACT_KEY, "summation.courseSet");
        fs2.setExecutionVariableList(executionVariableMap2);
        
        List<FactStructureDTO> factStructureList2 = new ArrayList<FactStructureDTO>();
        factStructureList2.add(fs2);
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

}

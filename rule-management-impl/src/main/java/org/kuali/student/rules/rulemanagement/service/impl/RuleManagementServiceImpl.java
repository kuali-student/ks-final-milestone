package org.kuali.student.rules.rulemanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDeterminationStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;

@WebService(endpointInterface = "org.kuali.student.rules.rulemanagement.service.RuleManagementService", 
        serviceName = "RuleManagementService", 
        portName = "RuleManagementService", 
        targetNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
public class RuleManagementServiceImpl implements RuleManagementService {

    RuleManagementDAO rulesManagementDao;
    
    @Override
    public String createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusDTO deleteBusinessRule(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        StatusDTO status = new StatusDTO();
        status.setSuccess( rulesManagementDao.deleteBusinessRule(businessRuleId) );        
        return status;
    }

    @Override
    public AgendaInfoDTO fetchAgendaInfo(String agendaTypeKey, AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AgendaInfoDeterminationStructureDTO fetchAgendaInfoDeterminationStructure(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String fetchBusinessRuleEnglish(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return "Rule" + businessRuleId;
    }

    @Override
    public BusinessRuleInfoDTO fetchBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date() );
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
        
       
        YieldValueFunctionDTO yieldValueFunctionDTO = new YieldValueFunctionDTO();
        yieldValueFunctionDTO.setYieldValueFunctionType("INTERSECTION(..)");
        
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
        rulePropositionDTO.setComparisonOperatorType("LESS_THAN");
               
        RuleElementDTO reDTO = new RuleElementDTO();
        reDTO.setName("Pre-req 1");
        reDTO.setDescription("Pre req check for Math 101");
        reDTO.setOperation("PROPOSITION");
        reDTO.setRuleProposition(rulePropositionDTO);

        
        
        
        
        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setBusinessRuleId("1");
        brInfoDTO.setName("CHEM 100 course prerequisites");
        brInfoDTO.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        brInfoDTO.setAnchorTypeKey("kuali.lui.course.id");
        brInfoDTO.setAnchorValue("CHEM 100");
        brInfoDTO.setStatus("ACTIVE");
        brInfoDTO.setMetaInfo(metaInfo);
        brInfoDTO.setEffectiveStartTime(new Date());
        brInfoDTO.setEffectiveEndTime(new Date());
        
        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO);
        
        brInfoDTO.setRuleElementList(elementList);
        
        
        return brInfoDTO;
    }

    @Override
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoList(List<BusinessRuleAnchorDTO> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // Remove hard coding
        return null;
    }

    @Override
    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findAgendaTypes() throws OperationFailedException {
        // TODO: Remove hard coding
        List<String> agendaTypes = new ArrayList<String>();
        agendaTypes.add("kuali.studentDropsCourse");
        agendaTypes.add("kuali.studentEnrollsInCourse");
        return agendaTypes;
    }

    @Override
    public List<String> findAnchorTypes() throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findAnchorsByAnchorType(String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO: Remove hard coding
        List<String> anchors = new ArrayList<String>();
        anchors.add("CHEM 100");
        anchors.add("CHEM 200");
        return anchors;    
    }

    @Override
    public List<String> findBusinessRuleIdsByBusinessRuleType(String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findBusinessRuleTypes() throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO: Remove hard coding
        List<String> brTypes = new ArrayList<String>();
        brTypes.add("kuali.coursePrerequisite");
        brTypes.add("kuali.coursePrerequisite");
        return brTypes;    
    }

    @Override
    public StatusDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * @return the rulesManagementDao
     */
    public RuleManagementDAO getRulesManagementDao() {
        return rulesManagementDao;
    }

    /**
     * @param rulesManagementDao the rulesManagementDao to set
     */
    public void setRulesManagementDao(RuleManagementDAO rulesManagementDao) {
        this.rulesManagementDao = rulesManagementDao;
    }
    
    

}

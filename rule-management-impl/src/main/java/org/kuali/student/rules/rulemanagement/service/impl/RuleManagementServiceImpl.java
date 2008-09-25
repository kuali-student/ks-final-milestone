package org.kuali.student.rules.rulemanagement.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.internal.common.entity.AgendaType;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDeterminationStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.entity.AgendaInfo;
import org.kuali.student.rules.rulemanagement.entity.AgendaInfoDeterminationStructure;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleAdapter;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.rules.rulemanagement.entity.RuleElement;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.rules.rulemanagement.service.RuleManagementService", serviceName = "RuleManagementService", portName = "RuleManagementService", targetNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@Transactional
public class RuleManagementServiceImpl implements RuleManagementService {

    RuleManagementDAO ruleManagementDAO;

    @Override
    public String createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        BusinessRule rule = BusinessRuleAdapter.getBusinessRuleEntity(businessRuleInfo);

        // Lookup Business Rule Type
        BusinessRuleType brType = ruleManagementDAO.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleInfo.getBusinessRuleTypeKey()), AnchorTypeKey.valueOf(businessRuleInfo.getAnchorTypeKey()));

        if (null == brType) {
            throw new InvalidParameterException("Could not lookup business rule type!");
        }
        rule.setBusinessRuleType(brType);

        ruleManagementDAO.createBusinessRule(rule);
        return rule.getId();
    }

    @Override
    public StatusDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        BusinessRule orgRule = ruleManagementDAO.lookupBusinessRuleUsingRuleId(businessRuleId);

        BusinessRule rule = BusinessRuleAdapter.getBusinessRuleEntity(businessRuleInfo);

        // Lookup Business Rule Type
        BusinessRuleType brType = ruleManagementDAO.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleInfo.getBusinessRuleTypeKey()), AnchorTypeKey.valueOf(businessRuleInfo.getAnchorTypeKey()));

        if (null == brType) {
            throw new InvalidParameterException("Could not lookup business rule type!");
        }
        rule.setBusinessRuleType(brType);

        // Remove the existing rule elements from the detached object
        for (RuleElement element : orgRule.getRuleElements()) {
            ruleManagementDAO.deleteRuleElement(element);
        }
        orgRule.setRuleElements(null);

        orgRule = BusinessRuleAdapter.copyBusinessRule(rule, orgRule);
        // rule.setId(orgRule.getId());
        ruleManagementDAO.updateBusinessRule(orgRule);

        StatusDTO status = new StatusDTO();
        status.setSuccess(true);

        return status;
    }

    @Override
    public StatusDTO deleteBusinessRule(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        StatusDTO status = new StatusDTO();
        status.setSuccess(ruleManagementDAO.deleteBusinessRule(businessRuleId));
        return status;
    }

    @Override
    public AgendaInfoDTO fetchAgendaInfo(String agendaTypeKey, AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AgendaInfoDeterminationStructureDTO fetchAgendaInfoDeterminationStructure(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<AgendaInfo> agendaInfoList = ruleManagementDAO.lookupAgendaInfosByType(AgendaType.valueOf(agendaTypeKey));

        // Use the first agenda in the list as all of them will have the same agendadeterminiationstructure keys
        AgendaInfoDeterminationStructureDTO aidDTO = null;
        if (agendaInfoList.size() > 0) {
            AgendaInfo agendaInfo = agendaInfoList.get(0);

            aidDTO = new AgendaInfoDeterminationStructureDTO();
            Map<String, String> structureMap = new HashMap<String, String>();

            for (AgendaInfoDeterminationStructure aidStruct : agendaInfo.getAgendaInfoDeterminationStructureList()) {
                structureMap.put(aidStruct.getStructureKey(), aidStruct.getValue());
            }

            aidDTO.setAgendaInfoDeterminationKeyList(structureMap);
        }

        if (null == aidDTO) {
            throw new DoesNotExistException("No agendaInfo exists for the given type:" + agendaTypeKey);
        }

        return aidDTO;
    }

    @Override
    public String fetchBusinessRuleEnglish(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return "Rule" + businessRuleId;
    }

    @Override
    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRule rule = ruleManagementDAO.lookupBusinessRuleUsingRuleId(businessRuleId);
        BusinessRuleInfoDTO brInfoDTO = BusinessRuleAdapter.getBusinessRuleInfoDTO(rule);
        return brInfoDTO;
    }

    @Override
    public BusinessRuleInfoDTO fetchBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRuleInfoDTO brInfoDTO = fetchDetailedBusinessRuleInfo(businessRuleId);
        brInfoDTO.setRuleElementList(null);

        return brInfoDTO;
    }

    @Override
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoList(List<BusinessRuleAnchorDTO> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<BusinessRuleInfoDTO> ruleInfoDTOList = new ArrayList<BusinessRuleInfoDTO>();

        for (BusinessRuleAnchorDTO anchorDTO : businessRuleAnchorInfoList) {
            List<BusinessRuleInfoDTO> tempInfoList = fetchBusinessRuleInfoDTO(anchorDTO);
            ruleInfoDTOList.addAll(tempInfoList);
        }

        return ruleInfoDTOList;
    }

    @Override
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoDTO(BusinessRuleAnchorDTO ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<BusinessRule> ruleList = ruleManagementDAO.lookupBusinessRuleUsingAnchor(BusinessRuleTypeKey.valueOf(ruleAnchor.getBusinessRuleTypeKey()), ruleAnchor.getAnchorValue());

        List<BusinessRuleInfoDTO> ruleInfoDTOList = new ArrayList<BusinessRuleInfoDTO>();
        for (BusinessRule rule : ruleList) {
            BusinessRuleInfoDTO ruleInfoDTO = BusinessRuleAdapter.getBusinessRuleInfoDTO(rule);
            ruleInfoDTOList.add(ruleInfoDTO);
        }

        return ruleInfoDTOList;
    }

    @Override
    public List<String> findAgendaTypes() throws OperationFailedException {
        List<String> agendaTypeStList = new ArrayList<String>();
        agendaTypeStList.add(AgendaType.KUALI_STUDENT_ENROLLS_IN_COURSRE.toString());
        agendaTypeStList.add(AgendaType.KUALI_STUDENT_STUDENT_DROPS_COURSE.toString());
        return agendaTypeStList;
    }

    @Override
    public List<String> findAnchorTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<AnchorTypeKey> anchorTypeKeyList = ruleManagementDAO.lookupUniqueAnchorTypeKeys();

        for (AnchorTypeKey key : anchorTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    @Override
    public List<String> findAnchorsByAnchorType(String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> anchorTypes = ruleManagementDAO.lookupAnchorByAnchorType(AnchorTypeKey.valueOf(anchorTypeKey));
        return anchorTypes;
    }

    @Override
    public List<String> findBusinessRuleIdsByBusinessRuleType(String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return ruleManagementDAO.lookupBusinessRuleIdUsingRuleTypeKey(BusinessRuleTypeKey.valueOf(businessRuleTypeKey));
    }

    @Override
    public List<String> findBusinessRuleTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<BusinessRuleTypeKey> brTypeKeyList = ruleManagementDAO.lookupUniqueBusinessRuleTypeKeys();

        for (BusinessRuleTypeKey key : brTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    @Override
    public BusinessRuleTypeDTO fetchBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRuleType ruleType = ruleManagementDAO.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleTypeKey), AnchorTypeKey.valueOf(anchorTypeKey));
        return BusinessRuleAdapter.getBusinessRuleTypeDTO(ruleType);
    }

    @Override
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO: Remove hard coding. Currently all unique business rule types are returned
        return findBusinessRuleTypes();
    }

    /**
     * @return the ruleManagementDAO
     */
    public RuleManagementDAO getRuleManagementDao() {
        return ruleManagementDAO;
    }

    /**
     * @param ruleManagementDAO
     *            the ruleManagementDAO to set
     */
    public void setRuleManagementDao(RuleManagementDAO ruleManagementDao) {
        this.ruleManagementDAO = ruleManagementDao;
    }
}

package org.kuali.student.rules.rulemanagement.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.internal.common.entity.AgendaType;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
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
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.rules.rulemanagement.service.RuleManagementService", serviceName = "RuleManagementService", portName = "RuleManagementService", targetNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@Transactional
public class RuleManagementServiceImpl implements RuleManagementService {

    private static final String RULE_SNAPSHOT_SUFFIX = "_SNAPSHOT";
    
    private RuleManagementDAO ruleManagementDao;

    private RuleRepositoryService repository;

    @Override
    public String createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        BusinessRule rule = BusinessRuleAdapter.getBusinessRuleEntity(businessRuleInfo);
        rule.setRuleId(null);
        
        BusinessRuleType brType = null;
        // Lookup Business Rule Type
        try {
            brType = ruleManagementDao.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleInfo.getBusinessRuleTypeKey()), AnchorTypeKey.valueOf(businessRuleInfo.getAnchorTypeKey()));
        } catch (NoResultException nre) {
            throw new InvalidParameterException("Could not lookup business rule type!");
        } catch (NonUniqueResultException nure) {
            throw new InvalidParameterException("Multiple business rule types found!");
        }

        rule.setBusinessRuleType(brType);

        ruleManagementDao.createBusinessRule(rule);        
        businessRuleInfo.setBusinessRuleId(rule.getRuleId());

        // Compile the business rule
        RuleSetDTO rsDTO = repository.generateRuleSetForBusinessRule(businessRuleInfo);
        rule.setCompiledId(rsDTO.getUUID());

        ruleManagementDao.updateBusinessRule(rule);
                        
        return rule.getRuleId().toString();
    }

    @Override
    // Update, Publish & Un-publish based on state of the rule
    public StatusDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        BusinessRule orgRule = null;
        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingRuleId(businessRuleId);
        } catch (NoResultException nre1) {
            throw new DoesNotExistException("No rule exists with rule Id:" + businessRuleId);
        }
        
        // Check if the rule has already been activated or retired
        BusinessRuleStatus orgStatus = BusinessRuleStatus.valueOf(orgRule.getMetaData().getStatus());
        BusinessRuleStatus newStatus = BusinessRuleStatus.valueOf(businessRuleInfo.getStatus());

        if( BusinessRuleStatus.RETIRED == orgStatus) {            
            throw new InvalidParameterException("Cannot update RETIRED rules");
        }
                                
        if( BusinessRuleStatus.RETIRED == newStatus && BusinessRuleStatus.ACTIVE != orgStatus) {            
            throw new InvalidParameterException("Cannot RETIRE non ACTIVE rules");
        }
        
        if( BusinessRuleStatus.ACTIVE == newStatus && BusinessRuleStatus.DRAFT_IN_PROGRESS != orgStatus ) {            
            throw new InvalidParameterException("Cannot ACTIVATE already ACTIVE or RETIRED rules");
        }
        
        if( BusinessRuleStatus.DRAFT_IN_PROGRESS == newStatus && BusinessRuleStatus.DRAFT_IN_PROGRESS != orgStatus ) {            
            throw new InvalidParameterException("Cannot change status to DRAFT_IN_PROGRESS from ACTIVE or RETIRED rules");
        }
        
                
        // Overwrite the incoming values for non updateable attributes
        businessRuleInfo.setName(orgRule.getName());
        businessRuleInfo.setCompiledId(orgRule.getCompiledId());
        
        BusinessRule rule = BusinessRuleAdapter.getBusinessRuleEntity(businessRuleInfo);

        BusinessRuleType brType = null;
        // Lookup Business Rule Type
        try {
            brType = ruleManagementDao.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleInfo.getBusinessRuleTypeKey()), AnchorTypeKey.valueOf(businessRuleInfo.getAnchorTypeKey()));
        } catch (NoResultException nre) {            
            throw new InvalidParameterException("Could not lookup business rule type!");
        } catch (NonUniqueResultException nure) {
            new InvalidParameterException("Multiple business rule types found!");
        }

        rule.setBusinessRuleType(brType);
        
        // Compile the business rule

        RuleSetDTO rsDTO = null;
        if(BusinessRuleStatus.DRAFT_IN_PROGRESS == newStatus) {
            rsDTO = repository.generateRuleSetForBusinessRule(businessRuleInfo);
            orgRule.setCompiledId(rsDTO.getUUID());
        } else if(BusinessRuleStatus.ACTIVE == newStatus) {
            rsDTO = repository.generateRuleSetForBusinessRule(businessRuleInfo);
            String snapshotName = orgRule.getCompiledId()+RULE_SNAPSHOT_SUFFIX;
            rule.setRepositorySnapshotName(snapshotName);
            rsDTO = repository.createRuleSetSnapshot(orgRule.getCompiledId(), snapshotName, "Activating rule " + orgRule.getName());
        } else if(BusinessRuleStatus.RETIRED == newStatus) {
            repository.removeRuleSetSnapshot(orgRule.getCompiledId(), orgRule.getRepositorySnapshotName());
        }
                
        // Remove the existing rule elements from the detached object
        for (RuleElement element : orgRule.getRuleElements()) {
            ruleManagementDao.deleteRuleElement(element);
        } 

        orgRule.setRuleElements(null);
        orgRule = BusinessRuleAdapter.copyBusinessRule(rule, orgRule);
                
        ruleManagementDao.updateBusinessRule(orgRule);

        StatusDTO status = new StatusDTO();
        status.setSuccess(true);

        return status;
    }

    @Override
    public StatusDTO deleteBusinessRule(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        StatusDTO status = new StatusDTO();
        status.setSuccess(false);

        BusinessRule orgRule = null;

        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingRuleId(businessRuleId);
        } catch (NoResultException nre) {
            throw new DoesNotExistException("No rule exists with Id: " + businessRuleId);
        }

        try {
            repository.removeRuleSet(orgRule.getCompiledId());

            status.setSuccess(ruleManagementDao.deleteBusinessRule(businessRuleId));
            status.setSuccess(true);
        } catch (Exception e) {
            throw new OperationFailedException("Operation failed:" + e.getLocalizedMessage());
        }
        return status;
    }

    @Override
    public AgendaInfoDTO fetchAgendaInfo(String agendaTypeKey, AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AgendaInfoDeterminationStructureDTO fetchAgendaInfoDeterminationStructure(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<AgendaInfo> agendaInfoList = ruleManagementDao.lookupAgendaInfosByType(AgendaType.valueOf(agendaTypeKey));

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
        BusinessRule rule = null;
        try {
            rule = ruleManagementDao.lookupBusinessRuleUsingRuleId(businessRuleId);            
            rule.getRuleElements().size();
            
            for(RuleElement element: rule.getRuleElements()) {
                if(RuleElementType.PROPOSITION == element.getOperation()) {
                    element.getRuleProposition().getLeftHandSide().getYieldValueFunction().getFacts().size();
                }
            }
            
        } catch (NoResultException nre) {
            throw new DoesNotExistException("No rule exists with Id: " + businessRuleId);
        }

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
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoByAnchorList(List<BusinessRuleAnchorDTO> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<BusinessRuleInfoDTO> ruleInfoDTOList = new ArrayList<BusinessRuleInfoDTO>();

        for (BusinessRuleAnchorDTO anchorDTO : businessRuleAnchorInfoList) {
            List<BusinessRuleInfoDTO> tempInfoList = fetchBusinessRuleInfoByAnchor(anchorDTO);
            ruleInfoDTOList.addAll(tempInfoList);
        }

        return ruleInfoDTOList;
    }

    @Override
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoByAnchor(BusinessRuleAnchorDTO ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<BusinessRule> ruleList = ruleManagementDao.lookupBusinessRuleUsingAnchor(BusinessRuleTypeKey.valueOf(ruleAnchor.getBusinessRuleTypeKey()), ruleAnchor.getAnchorValue());

        List<BusinessRuleInfoDTO> ruleInfoDTOList = new ArrayList<BusinessRuleInfoDTO>();
        for (BusinessRule rule : ruleList) {
            BusinessRuleInfoDTO ruleInfoDTO = BusinessRuleAdapter.getBusinessRuleInfoDTO(rule);
            ruleInfoDTOList.add(ruleInfoDTO);
        }

        return ruleInfoDTOList;
    }

    @Override
    public List<String> findAgendaTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<AgendaType> agendaTypeKeyList = ruleManagementDao.lookupUniqueAgendaTypes();

        for (AgendaType key : agendaTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    @Override
    public List<String> findAnchorTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<AnchorTypeKey> anchorTypeKeyList = ruleManagementDao.lookupUniqueAnchorTypeKeys();

        for (AnchorTypeKey key : anchorTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    @Override
    public List<String> findAnchorsByAnchorType(String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> anchorTypes = ruleManagementDao.lookupAnchorByAnchorType(AnchorTypeKey.valueOf(anchorTypeKey));
        return anchorTypes;
    }

    @Override
    public List<String> findBusinessRuleIdsByBusinessRuleType(String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return ruleManagementDao.lookupBusinessRuleIdUsingRuleTypeKey(BusinessRuleTypeKey.valueOf(businessRuleTypeKey));
    }

    @Override
    public List<String> findBusinessRuleTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<BusinessRuleTypeKey> brTypeKeyList = ruleManagementDao.lookupUniqueBusinessRuleTypeKeys();

        for (BusinessRuleTypeKey key : brTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    @Override
    public BusinessRuleTypeDTO fetchBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRuleType ruleType = ruleManagementDao.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleTypeKey), AnchorTypeKey.valueOf(anchorTypeKey));
        return BusinessRuleAdapter.getBusinessRuleTypeDTO(ruleType);
    }

    @Override
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<BusinessRuleTypeKey> brTypeKeyList = ruleManagementDao.lookupBusinessRuleTypeByAgenda(AgendaType.valueOf(agendaTypeKey));

        for (BusinessRuleTypeKey key : brTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    /**
     * @return the ruleManagementDao
     */
    public RuleManagementDAO getRuleManagementDao() {
        return ruleManagementDao;
    }

    /**
     * @param ruleManagementDao
     *            the ruleManagementDao to set
     */
    public void setRuleManagementDao(RuleManagementDAO ruleManagementDao) {
        this.ruleManagementDao = ruleManagementDao;
    }

    /**
     * @return the repository
     */
    public RuleRepositoryService getRepository() {
        return repository;
    }

    /**
     * @param repository
     *            the repository to set
     */
    public void setRepository(RuleRepositoryService repository) {
        this.repository = repository;
    }
}

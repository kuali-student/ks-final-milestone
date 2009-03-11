package org.kuali.student.rules.rulemanagement.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.rules.internal.common.entity.AgendaType;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.AgendaDeterminationInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.entity.AgendaInfo;
import org.kuali.student.rules.rulemanagement.entity.AgendaDeterminationInfo;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleAdapter;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.rules.rulemanagement.entity.RuleElement;
import org.kuali.student.rules.rulemanagement.entity.RuleMetaData;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@WebService(endpointInterface = "org.kuali.student.rules.rulemanagement.service.RuleManagementService", serviceName = "RuleManagementService", portName = "RuleManagementService", targetNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@Transactional
public class RuleManagementServiceImpl implements RuleManagementService {

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);

    private static final String RULE_SNAPSHOT_SUFFIX = "_SNAPSHOT";

    private RuleManagementDAO ruleManagementDao;

    private RuleRepositoryService repository;

    private static final int DATE_COMPARE_BUFFER = 5;
    
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
    public List<String> findBusinessRuleTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<BusinessRuleTypeKey> brTypeKeyList = ruleManagementDao.lookupUniqueBusinessRuleTypeKeys();

        for (BusinessRuleTypeKey key : brTypeKeyList) {
            result.add(key.toString());
        }

        return result;
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

    @Override
    public List<String> findAnchorTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<AnchorTypeKey> anchorTypeKeyList = ruleManagementDao.lookupUniqueAnchorTypeKeys();

        for (AnchorTypeKey key : anchorTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    // Fetch Operations ******************************************************************

    @Override
    public AgendaDeterminationInfoDTO fetchAgendaInfoDeterminationStructure(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<AgendaInfo> agendaInfoList = ruleManagementDao.lookupAgendaInfosByType(AgendaType.valueOf(agendaTypeKey));

        // Use the first agenda in the list as all of them will have the same agendadeterminiationstructure keys
        AgendaDeterminationInfoDTO aidDTO = null;
        if (agendaInfoList.size() > 0) {
            AgendaInfo agendaInfo = agendaInfoList.get(0);

            aidDTO = new AgendaDeterminationInfoDTO();
            Map<String, String> structureMap = new HashMap<String, String>();

            for (AgendaDeterminationInfo aidStruct : agendaInfo.getAgendaDeterminationInfoList()) {
                structureMap.put(aidStruct.getStructureKey(), "");
            }

            aidDTO.setAgendaInfoDeterminationKeyList(structureMap);
        }

        if (null == aidDTO) {
            throw new DoesNotExistException("No agendaInfo exists for the given type:" + agendaTypeKey);
        }

        return aidDTO;
    }

    @Override
    public AgendaInfoDTO fetchAgendaInfo(String agendaTypeKey, AgendaDeterminationInfoDTO agendaDeterminationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<AgendaInfo> agendaInfoList = ruleManagementDao.lookupAgendaInfosByType(AgendaType.valueOf(agendaTypeKey));

        AgendaInfo resultAgenda = null;

        if (agendaInfoList.size() > 0) {

            Map<String, String> inputAgendaDeterminationMap = agendaDeterminationInfo.getAgendaDeterminationKeyList();
            Set<String> keyset = inputAgendaDeterminationMap.keySet();

            for (AgendaInfo agenda : agendaInfoList) {
                Map<String, String> agendaInfoMap = agenda.getAgendaDeterminationInfoMap();

                // Check if all elements in the input key match up
                boolean matchFound = true;
                for (String key : keyset) {
                    String value = agendaInfoMap.get(key);
                    if (null == value || !value.equals(inputAgendaDeterminationMap.get(key))) {
                        matchFound = false;
                        break;
                    }
                }

                if (matchFound && null == resultAgenda) {
                    resultAgenda = agenda;
                } else if (matchFound && null != resultAgenda) {
                    throw new OperationFailedException("Multiple agenda's satisfy the determiniation info criteria!");
                }
            }
        } else {
            throw new DoesNotExistException("No agendaInfo exists for the given type:" + agendaTypeKey);
        }

        return BusinessRuleAdapter.getAgendaInfoDTO(resultAgenda);
    }

    @Override
    public BusinessRuleTypeInfoDTO fetchBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRuleType ruleType = ruleManagementDao.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleTypeKey), AnchorTypeKey.valueOf(anchorTypeKey));
        return BusinessRuleAdapter.getBusinessRuleTypeDTO(ruleType);
    }

    @Override
    public List<BusinessRuleInfoDTO> fetchBusinessRuleByAnchorList(List<BusinessRuleAnchorInfoDTO> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<BusinessRuleInfoDTO> ruleInfoDTOList = new ArrayList<BusinessRuleInfoDTO>();

        for (BusinessRuleAnchorInfoDTO anchorDTO : businessRuleAnchorInfoList) {
            List<BusinessRuleInfoDTO> tempInfoList = fetchBusinessRuleByAnchor(anchorDTO);
            ruleInfoDTOList.addAll(tempInfoList);
        }

        return ruleInfoDTOList;
    }

    @Override
    public List<BusinessRuleInfoDTO> fetchBusinessRuleByAnchor(BusinessRuleAnchorInfoDTO ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<BusinessRule> ruleList = ruleManagementDao.lookupCurrentActiveBusinessRuleUsingAnchor(BusinessRuleTypeKey.valueOf(ruleAnchor.getBusinessRuleTypeKey()), ruleAnchor.getAnchorValue());

        List<BusinessRuleInfoDTO> ruleInfoDTOList = new ArrayList<BusinessRuleInfoDTO>();
        for (BusinessRule rule : ruleList) {
            BusinessRuleInfoDTO ruleInfoDTO = BusinessRuleAdapter.getBusinessRuleInfoDTO(rule);
            ruleInfoDTOList.add(ruleInfoDTO);
        }

        return ruleInfoDTOList;
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
    public BusinessRuleInfoDTO fetchBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRuleInfoDTO brInfoDTO = fetchDetailedBusinessRuleInfo(businessRuleId);
        brInfoDTO.setBusinessRuleElementList(null);

        return brInfoDTO;
    }

    @Override
    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRule rule = null;
        try {
            rule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleId);
            rule.getRuleElements().size();

            for (RuleElement element : rule.getRuleElements()) {
                if (RuleElementType.PROPOSITION == element.getBusinessRuleElemnetTypeKey()) {
                    element.getRuleProposition().getLeftHandSide().getYieldValueFunction().getFacts().size();
                }
            }

        } catch (NoResultException nre) {
            logger.error(nre.getMessage(), nre);
            throw new DoesNotExistException("No rule exists with Id: " + businessRuleId);
        }

        BusinessRuleInfoDTO brInfoDTO = BusinessRuleAdapter.getBusinessRuleInfoDTO(rule);
        return brInfoDTO;
    }

    @Override
    public String fetchBusinessRuleEnglish(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return "Rule" + businessRuleId;
    }

    // Maintenance ----------------------------------------------------

    @Override
    public BusinessRuleInfoDTO createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // RuleId should not be already be set
        if (StringUtils.hasText(businessRuleInfo.getId())) {
            throw new InvalidParameterException("Create business rule cannot have preset value for rule Id!");
        }

        // Check to see if the state is draft in progress
        BusinessRuleStatus newStatus = BusinessRuleStatus.valueOf(businessRuleInfo.getState());
        if (BusinessRuleStatus.DRAFT_IN_PROGRESS != newStatus) {
            throw new InvalidParameterException("Cannot create a ACIVE/RETIRED rule!");
        }

        // Convert the DTO to Entity
        BusinessRule rule = BusinessRuleAdapter.getBusinessRuleEntity(businessRuleInfo);

        // Lookup Business Rule Type
        BusinessRuleType brType = null;
        try {
            brType = ruleManagementDao.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleInfo.getType()), AnchorTypeKey.valueOf(businessRuleInfo.getAnchorTypeKey()));
        } catch (NoResultException nre) {
            logger.error(nre.getMessage(), nre);
            throw new InvalidParameterException("Could not lookup business rule type!");
        } catch (NonUniqueResultException nure) {
            logger.error(nure.getMessage(), nure);
            throw new InvalidParameterException("Multiple business rule types found!");
        }
        rule.setBusinessRuleType(brType);

        RuleMetaData metaData = rule.getMetaData();
        metaData.setCreateDate(new Date());
        metaData.setUpdateDate(new Date());
        rule.setMetaData(metaData);

        // Create the business rule
        ruleManagementDao.createBusinessRule(rule);
        businessRuleInfo.setId(rule.getId());

        // If this is the first rule in the series
        if (!StringUtils.hasText(businessRuleInfo.getOriginalRuleId())) {
            rule.setOriginalRuleId(rule.getId());
            businessRuleInfo.setOriginalRuleId(rule.getOriginalRuleId());
        }

        // Compile the business rule
        RuleSetDTO rsDTO = repository.generateRuleSetForBusinessRule(businessRuleInfo);
        rule.setCompiledId(rsDTO.getUUID());

        // Update the rule with the compiledId
        ruleManagementDao.updateBusinessRule(rule);

        // Re build the business rule Info
        businessRuleInfo = BusinessRuleAdapter.getBusinessRuleInfoDTO(rule);

        if (logger.isDebugEnabled()) {
            logger.info("Rule Created..");
            logger.info(rule.toString());
        }

        return businessRuleInfo;
    }

    @Override
    public BusinessRuleInfoDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        BusinessRule orgRule = null;
        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleId);
        } catch (NoResultException nre1) {
            logger.error(nre1.getMessage(), nre1);
            throw new DoesNotExistException("No rule exists with rule Id:" + businessRuleId);
        }

        // Check if the rule has already been retired
        BusinessRuleStatus orgState = orgRule.getState();
        BusinessRuleStatus newState = BusinessRuleStatus.valueOf(businessRuleInfo.getState());

        // Check to see if state is not being modified
        if (orgState != newState) {
            throw new ReadOnlyException("State cannot be updated through this method!");
        }

        // Check if read only attributes are being updated
        if (orgRule.getCompiledId() != null && !orgRule.getCompiledId().equals(businessRuleInfo.getCompiledId())) {
            throw new ReadOnlyException("Cannot updated rule's compiled Id!");
        }

        if (orgRule.getBusinessRuleType().getBusinessRuleTypeKey() != BusinessRuleTypeKey.valueOf(businessRuleInfo.getType())) {
            throw new ReadOnlyException("Cannot update rule's business rule type key!");
        }

        BusinessRule rule = null;
        switch (orgState) {
            case DRAFT_IN_PROGRESS:
                rule = BusinessRuleAdapter.getBusinessRuleEntity(businessRuleInfo);

                // Remove the existing rule elements from the detached object
                for (RuleElement element : orgRule.getRuleElements()) {
                    ruleManagementDao.deleteRuleElement(element);
                }
                orgRule.setRuleElements(null);
                orgRule = BusinessRuleAdapter.copyBusinessRule(rule, orgRule);

                break;
            case ACTIVE:
                /**
                 * Only allow effective date manipulation for ACTIVE rules
                 * RULE: If the date change is happening in future and if the date to be changed is in future then allow 
                 * the change. For safety add DATE_COMPARE_BUFFER minutes to current date
                 */
                Calendar now = new GregorianCalendar();
                now.add(Calendar.MINUTE, DATE_COMPARE_BUFFER);
                Date nowPlus5Dt = now.getTime();
                
                if(orgRule.getMetaData().getEffectiveDate().after(nowPlus5Dt) && businessRuleInfo.getEffectiveDate().after(nowPlus5Dt)) {
                    orgRule.getMetaData().setEffectiveDate(businessRuleInfo.getEffectiveDate());                    
                }
                
                if(orgRule.getMetaData().getExpirationDate().after(nowPlus5Dt) && businessRuleInfo.getExpirationDate().after(nowPlus5Dt)) {
                    orgRule.getMetaData().setExpirationDate(businessRuleInfo.getExpirationDate());                    
                }
                
                break;
            case RETIRED:
                throw new InvalidParameterException("Cannot update RETIRED rules!");
            default:
                logger.error("Unknow state (" + orgState + ") encountered while updating rule");
                throw new OperationFailedException("Unknown state of the peristed rule!");
        }

        BusinessRuleInfoDTO  ruleInfoDTO = BusinessRuleAdapter.getBusinessRuleInfoDTO(orgRule);
        RuleSetDTO rsDTO = repository.generateRuleSetForBusinessRule(ruleInfoDTO);
        orgRule.setCompiledId(rsDTO.getUUID());
        ruleInfoDTO.setCompiledId(rsDTO.getUUID());

        RuleMetaData metaData = orgRule.getMetaData();
        metaData.setUpdateDate(new Date());
        orgRule.setMetaData(metaData);
        
        ruleManagementDao.updateBusinessRule(orgRule);

        // Re build the business rule Info
        businessRuleInfo = BusinessRuleAdapter.getBusinessRuleInfoDTO(orgRule);

        if (logger.isDebugEnabled()) {
            logger.info("Rule Updated..");
            logger.info(orgRule.toString());
        }
        
        return ruleInfoDTO;
    }

    @Override
    public BusinessRuleInfoDTO updateBusinessRuleState(String businessRuleId, String brState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        BusinessRule orgRule = null;
        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleId);
        } catch (NoResultException nre1) {
            logger.error(nre1.getMessage(), nre1);
            throw new DoesNotExistException("No rule exists with rule Id:" + businessRuleId);
        }

        BusinessRuleStatus orgState = orgRule.getState();
        BusinessRuleStatus newState = BusinessRuleStatus.valueOf(brState);
        
        if (BusinessRuleStatus.RETIRED == orgState) {
            throw new InvalidParameterException("Cannot change state for RETIRED rules!");
        }
        
        if(BusinessRuleStatus.RETIRED == newState && BusinessRuleStatus.ACTIVE != orgState) {
            throw new InvalidParameterException("Cannot RETIRE non ACTIVE rules");
        }
        
        if(BusinessRuleStatus.ACTIVE == newState && BusinessRuleStatus.DRAFT_IN_PROGRESS != orgState) {
            throw new InvalidParameterException("Cannot ACTIVATE non DRAFT_IN_PROGRESS rules!");
        }

        if(BusinessRuleStatus.DRAFT_IN_PROGRESS == newState) {
            throw new InvalidParameterException("Cannot change state to DRAFT_IN_PROGRESS!");
        }
        
        if(orgState == newState) {
            throw new InvalidParameterException("Only state changes can be performed through this method!");
        }
              
        // Check if there is only one active version for the rules effective time period
        if (BusinessRuleStatus.ACTIVE == newState) {
            if (checkForActiveDateOvelapps(orgRule)) {
                throw new InvalidParameterException("Cannot have multiple rule versions Active simultaneously");
            }
        }

        orgRule.setState(newState);
        
        // Compile the business rule
        String snapshotName = buildSnapshotName(orgRule.getId());
        if (BusinessRuleStatus.ACTIVE == newState) {
            repository.createRuleSetSnapshot(orgRule.getCompiledId(), snapshotName, "Activating rule " + orgRule.getName());
            orgRule.setRepositorySnapshotName(snapshotName);
        } else if (BusinessRuleStatus.RETIRED == newState) {
            repository.removeRuleSetSnapshot(orgRule.getCompiledId(), orgRule.getRepositorySnapshotName());
        }

        RuleMetaData metaData = orgRule.getMetaData();
        metaData.setUpdateDate(new Date());
        orgRule.setMetaData(metaData);
        
        ruleManagementDao.updateBusinessRule(orgRule);

        // Re build the business rule Info
        return BusinessRuleAdapter.getBusinessRuleInfoDTO(orgRule);
    }

    @Override
    public StatusDTO deleteBusinessRule(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        StatusDTO status = new StatusDTO();
        status.setSuccess(false);

        BusinessRule orgRule = null;

        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleId);
        } catch (NoResultException nre) {
            throw new DoesNotExistException("No rule exists with Id: " + businessRuleId);
        }

        if (BusinessRuleStatus.ACTIVE == orgRule.getState() || BusinessRuleStatus.RETIRED == orgRule.getState()) {
            throw new OperationFailedException("Cannot delete active or retired rules!");
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

    @Override
    public BusinessRuleInfoDTO createNewVersion(BusinessRuleInfoDTO businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {

        // Check if the rule exists
        BusinessRule orgRule = null;
        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleInfo.getId());
        } catch (NoResultException nre1) {
            throw new DoesNotExistException("No rule exists with rule Id:" + businessRuleInfo.getId());
        }

        // Check if the rule has already been activated or retired
        BusinessRuleStatus orgStatus = orgRule.getState();

        // Check if the incoming rule is either active or retired
        if (BusinessRuleStatus.RETIRED != orgStatus && BusinessRuleStatus.ACTIVE != orgStatus) {
            throw new InvalidParameterException("New version can be created for only RETIRED/ACTIVE rules");
        }

        BusinessRuleInfoDTO nextVersion = null;
        synchronized (this) {
            // Copy the original rule to rule info and then back to remove jpa markers
            BusinessRuleInfoDTO rule1 = BusinessRuleAdapter.getBusinessRuleInfoDTO(orgRule);
            rule1.setId("");
            rule1.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
            
            // Remove the id from rule elements
            for(RuleElementDTO element: rule1.getBusinessRuleElementList()) {
                element.setId("");
            }
            
            // Persist rule2. Watch out for unique key constraint violation
            try {
                nextVersion = createBusinessRule(rule1);
            } catch (AlreadyExistsException e) {
                logger.error(e.getMessage(), e);
                // Should not happen as we have reset the business rule Id
                throw new OperationFailedException("JPA returning AlreadyExistsException while trying to persist newer version of the rule!");
            }
        }

        return nextVersion;
    }

    private String buildSnapshotName(String ruleId) {
        return ruleId + RULE_SNAPSHOT_SUFFIX;
    }

    private boolean checkForActiveDateOvelapps(BusinessRule newRule) {
        boolean result = false;
        Date stDate = newRule.getMetaData().getEffectiveDate();
        Date enDate = newRule.getMetaData().getExpirationDate();

        List<BusinessRule> allVersions = ruleManagementDao.lookupAllVersions(newRule.getOriginalRuleId());
        for (BusinessRule rule : allVersions) {
            if (BusinessRuleStatus.ACTIVE == rule.getState()) {
                // Check start date
                if (rule.getMetaData().getEffectiveDate().compareTo(stDate) >= 0 && rule.getMetaData().getEffectiveDate().compareTo(enDate) <= 0) {
                    result = true;
                    break;
                }
                // Check end date
                if (rule.getMetaData().getExpirationDate().compareTo(enDate) <= 0 && rule.getMetaData().getExpirationDate().compareTo(stDate) >= 0) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}

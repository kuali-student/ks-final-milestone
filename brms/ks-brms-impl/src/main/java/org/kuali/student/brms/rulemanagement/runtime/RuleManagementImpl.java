/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.rulemanagement.runtime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.kuali.student.brms.internal.common.entity.AgendaType;
import org.kuali.student.brms.internal.common.entity.AnchorTypeKey;
import org.kuali.student.brms.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.brms.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.repository.runtime.RuleRepository;
import org.kuali.student.brms.rulemanagement.RuleManagementServiceAssembler;
import org.kuali.student.brms.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfo;
import org.kuali.student.brms.rulemanagement.dto.AgendaInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.entity.Agenda;
import org.kuali.student.brms.rulemanagement.entity.AgendaDetermination;
import org.kuali.student.brms.rulemanagement.entity.BusinessRule;
import org.kuali.student.brms.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.brms.rulemanagement.entity.RuleElement;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional
public class RuleManagementImpl implements RuleManagement {

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleManagementImpl.class);

    private static final String RULE_SNAPSHOT_SUFFIX = "_SNAPSHOT";

    private RuleManagementDAO ruleManagementDao;

    private RuleRepository ruleRepository;

    private static final int DATE_COMPARE_BUFFER = 5;
    
    /**
     * @param ruleManagementDao
     *            the ruleManagementDao to set
     */
    public void setRuleManagementDao(RuleManagementDAO ruleManagementDao) {
        this.ruleManagementDao = ruleManagementDao;
    }

    /**
     * @param repository
     *            the repository to set
     */
    public void setRuleRepository(RuleRepository repository) {
        this.ruleRepository = repository;
    }

    @Override
    public List<String> getAgendaTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<AgendaType> agendaTypeKeyList = ruleManagementDao.lookupUniqueAgendaTypes();

        for (AgendaType key : agendaTypeKeyList) {
            result.add(key.toString());
        }
        return result;
    }

    @Override
    public List<String> getBusinessRuleTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<BusinessRuleTypeKey> brTypeKeyList = ruleManagementDao.lookupUniqueBusinessRuleTypeKeys();

        for (BusinessRuleTypeKey key : brTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    @Override
    public List<String> getBusinessRuleTypesByAgendaType(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<BusinessRuleTypeKey> brTypeKeyList = ruleManagementDao.lookupBusinessRuleTypeByAgenda(AgendaType.valueOf(agendaTypeKey));

        for (BusinessRuleTypeKey key : brTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    @Override
    public List<String> getAnchorTypes() throws OperationFailedException {
        List<String> result = new ArrayList<String>();
        List<AnchorTypeKey> anchorTypeKeyList = ruleManagementDao.lookupUniqueAnchorTypeKeys();

        for (AnchorTypeKey key : anchorTypeKeyList) {
            result.add(key.toString());
        }

        return result;
    }

    // Fetch Operations ******************************************************************

    @Override
    public AgendaDeterminationInfo getAgendaInfoDeterminationStructure(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<Agenda> agendaList = ruleManagementDao.lookupAgendasByType(AgendaType.valueOf(agendaTypeKey));

        // Use the first agenda in the list as all of them will have the same agendadeterminiationstructure keys
        AgendaDeterminationInfo aidDTO = null;
        if (agendaList.size() > 0) {
            Agenda agenda = agendaList.get(0);

            aidDTO = new AgendaDeterminationInfo();
            Map<String, String> structureMap = new HashMap<String, String>();

            for (AgendaDetermination aidStruct : agenda.getAgendaDeterminationList()) {
                structureMap.put(aidStruct.getStructureKey(), "");
            }

            aidDTO.setAgendaInfoDeterminationKeyList(structureMap);
        }

        if (null == aidDTO) {
            throw new DoesNotExistException("AgendaInfo does not exist for agenda type key: " + agendaTypeKey);
        }

        return aidDTO;
    }

    @Override
    public AgendaInfo getAgendaInfo(String agendaTypeKey, AgendaDeterminationInfo agendaDeterminationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<Agenda> agendaInfoList = ruleManagementDao.lookupAgendasByType(AgendaType.valueOf(agendaTypeKey));

        Agenda resultAgenda = null;

        if (agendaInfoList.size() > 0) {

            Map<String, String> inputAgendaDeterminationMap = agendaDeterminationInfo.getAgendaDeterminationKeyList();
            Set<String> keyset = inputAgendaDeterminationMap.keySet();

            for (Agenda agenda : agendaInfoList) {
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
            throw new DoesNotExistException("AgendaInfo dos not exists for agenda type key: " + agendaTypeKey);
        }

        return RuleManagementServiceAssembler.toAgendaInfo(resultAgenda);
    }

    @Override
    public BusinessRuleTypeInfo getBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRuleType ruleType = ruleManagementDao.lookupRuleTypeByKeyAndAnchorType(BusinessRuleTypeKey.valueOf(businessRuleTypeKey), AnchorTypeKey.valueOf(anchorTypeKey));
        return RuleManagementServiceAssembler.toBusinessRuleTypeInfo(ruleType);
    }

    @Override
    public List<BusinessRuleInfo> getBusinessRuleByAnchorList(List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<BusinessRuleInfo> ruleInfoDTOList = new ArrayList<BusinessRuleInfo>();

        for (BusinessRuleAnchorInfo anchorDTO : businessRuleAnchorInfoList) {
            List<BusinessRuleInfo> tempInfoList = getBusinessRuleByAnchor(anchorDTO);
            ruleInfoDTOList.addAll(tempInfoList);
        }

        return ruleInfoDTOList;
    }

    @Override
    public List<BusinessRuleInfo> getBusinessRuleByAnchor(BusinessRuleAnchorInfo ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<BusinessRule> ruleList = ruleManagementDao.lookupCurrentActiveBusinessRuleUsingAnchor(BusinessRuleTypeKey.valueOf(ruleAnchor.getBusinessRuleTypeKey()), ruleAnchor.getAnchorValue());

        List<BusinessRuleInfo> ruleInfoDTOList = new ArrayList<BusinessRuleInfo>();
        for (BusinessRule rule : ruleList) {
            BusinessRuleInfo ruleInfoDTO = RuleManagementServiceAssembler.toBusinessRuleInfo(rule);
            ruleInfoDTOList.add(ruleInfoDTO);
        }

        return ruleInfoDTOList;
    }

    @Override
    public List<String> getAnchorsByAnchorType(String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> anchorTypes = ruleManagementDao.lookupAnchorByAnchorType(AnchorTypeKey.valueOf(anchorTypeKey));
        return anchorTypes;
    }

    @Override
    public List<String> getBusinessRuleIdsByBusinessRuleType(String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return ruleManagementDao.lookupBusinessRuleIdUsingRuleTypeKey(BusinessRuleTypeKey.valueOf(businessRuleTypeKey));
    }

    @Override
    public BusinessRuleInfo getBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        BusinessRuleInfo brInfoDTO = getDetailedBusinessRuleInfo(businessRuleId);
        brInfoDTO.setBusinessRuleElementList(null);

        return brInfoDTO;
    }

    @Override
    public BusinessRuleInfo getDetailedBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
            throw new DoesNotExistException("Rule does not exist for id: " + businessRuleId);
        }

        BusinessRuleInfo brInfoDTO = RuleManagementServiceAssembler.toBusinessRuleInfo(rule);
        return brInfoDTO;
    }

    @Override
    public String getBusinessRuleEnglish(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return "Rule" + businessRuleId;
    }

    // Maintenance ----------------------------------------------------

    @Override
    public BusinessRuleInfo createBusinessRule(BusinessRuleInfo businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

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
        BusinessRule rule = RuleManagementServiceAssembler.toBusinessRuleRelation(businessRuleInfo, ruleManagementDao);

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

        // Create the business rule
        ruleManagementDao.createBusinessRule(rule);
        businessRuleInfo.setId(rule.getId());

        // If this is the first rule in the series
        if (!StringUtils.hasText(businessRuleInfo.getOriginalRuleId())) {
            rule.setOriginalRuleId(rule.getId());
            businessRuleInfo.setOriginalRuleId(rule.getOriginalRuleId());
        }

        // Compile the business rule
        RuleSetInfo rsDTO = ruleRepository.generateRuleSetForBusinessRule(businessRuleInfo);
        rule.setCompiledId(rsDTO.getUUID());

        // Update the rule with the compiledId
        ruleManagementDao.updateBusinessRule(rule);

        // Re build the business rule Info
        businessRuleInfo = RuleManagementServiceAssembler.toBusinessRuleInfo(rule);

        if (logger.isDebugEnabled()) {
            logger.info("Rule Created..");
            logger.info(rule.toString());
        }
        return businessRuleInfo;
    }

    @Override
    public BusinessRuleInfo updateBusinessRule(String businessRuleId, BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        BusinessRule orgRule = null;
        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleId);
        } catch (NoResultException nre1) {
            logger.error(nre1.getMessage(), nre1);
            throw new DoesNotExistException("Rule does not exist for rule Id:" + businessRuleId);
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
                rule = RuleManagementServiceAssembler.toBusinessRuleRelation(businessRuleInfo, ruleManagementDao);

                // Remove the existing rule elements from the detached object
                for (RuleElement element : orgRule.getRuleElements()) {
                    ruleManagementDao.deleteRuleElement(element);
                }
                orgRule.setRuleElements(null);
                orgRule = RuleManagementServiceAssembler.copyBusinessRule(rule, orgRule);

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
                
                if(orgRule.getEffectiveDate().after(nowPlus5Dt) && businessRuleInfo.getEffectiveDate().after(nowPlus5Dt)) {
                    orgRule.setEffectiveDate(businessRuleInfo.getEffectiveDate());                    
                }
                
                if(orgRule.getExpirationDate().after(nowPlus5Dt) && businessRuleInfo.getExpirationDate().after(nowPlus5Dt)) {
                    orgRule.setExpirationDate(businessRuleInfo.getExpirationDate());                    
                }
                
                break;
            case RETIRED:
                throw new InvalidParameterException("Cannot update RETIRED rules!");
            default:
                logger.error("Unknow state (" + orgState + ") encountered while updating rule");
                throw new OperationFailedException("Unknown state of the peristed rule!");
        }

        BusinessRuleInfo  ruleInfoDTO = RuleManagementServiceAssembler.toBusinessRuleInfo(orgRule);
        RuleSetInfo rsDTO = ruleRepository.generateRuleSetForBusinessRule(ruleInfoDTO);
        orgRule.setCompiledId(rsDTO.getUUID());
        ruleInfoDTO.setCompiledId(rsDTO.getUUID());
        
        ruleManagementDao.updateBusinessRule(orgRule);

        // Re build the business rule Info
        businessRuleInfo = RuleManagementServiceAssembler.toBusinessRuleInfo(orgRule);

        if (logger.isDebugEnabled()) {
            logger.info("Rule Updated..");
            logger.info(orgRule.toString());
        }
        
        return ruleInfoDTO;
    }

    @Override
    public BusinessRuleInfo updateBusinessRuleState(String businessRuleId, String brState) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        BusinessRule orgRule = null;
        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleId);
        } catch (NoResultException nre1) {
            logger.error(nre1.getMessage(), nre1);
            throw new DoesNotExistException("Rule does not exist for rule Id:" + businessRuleId);
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
            ruleRepository.createRuleSetSnapshot(orgRule.getCompiledId(), snapshotName, "Activating rule " + orgRule.getName());
            orgRule.setRepositorySnapshotName(snapshotName);
        } else if (BusinessRuleStatus.RETIRED == newState) {
            ruleRepository.removeRuleSetSnapshot(orgRule.getCompiledId(), orgRule.getRepositorySnapshotName());
        }
        
        ruleManagementDao.updateBusinessRule(orgRule);

        // Re build the business rule Info
        return RuleManagementServiceAssembler.toBusinessRuleInfo(orgRule);
    }

    @Override
    public StatusInfo deleteBusinessRule(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(false);

        BusinessRule orgRule = null;

        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleId);
        } catch (NoResultException nre) {
            throw new DoesNotExistException("Rule does not exist for Id: " + businessRuleId);
        }

        if (BusinessRuleStatus.ACTIVE == orgRule.getState() || BusinessRuleStatus.RETIRED == orgRule.getState()) {
            throw new OperationFailedException("Cannot delete active or retired rules!");
        }

        try {
            ruleRepository.removeRuleSet(orgRule.getCompiledId());

            status.setSuccess(ruleManagementDao.deleteBusinessRule(businessRuleId));
            status.setSuccess(true);
        } catch (Exception e) {
            throw new OperationFailedException("Operation failed:" + e.getLocalizedMessage());
        }
        return status;
    }

    @Override
    public BusinessRuleInfo createNewVersion(BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, OperationFailedException, PermissionDeniedException {

        // Check if the rule exists
        BusinessRule orgRule = null;
        try {
            orgRule = ruleManagementDao.lookupBusinessRuleUsingId(businessRuleInfo.getId());
        } catch (NoResultException nre1) {
            throw new DoesNotExistException("Rule does not exist for rule Id:" + businessRuleInfo.getId());
        }

        // Check if the rule has already been activated or retired
        BusinessRuleStatus orgStatus = orgRule.getState();

        // Check if the incoming rule is either active or retired
        if (BusinessRuleStatus.RETIRED != orgStatus && BusinessRuleStatus.ACTIVE != orgStatus) {
            throw new InvalidParameterException("New version can be created for only RETIRED/ACTIVE rules");
        }

        BusinessRuleInfo nextVersion = null;
        synchronized (this) {
            // Copy the original rule to rule info and then back to remove jpa markers
            BusinessRuleInfo rule1 = RuleManagementServiceAssembler.toBusinessRuleInfo(orgRule);
            rule1.setId("");
            rule1.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
            
            // Remove the id from rule elements
            for(RuleElementInfo element: rule1.getBusinessRuleElementList()) {
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
        Date stDate = newRule.getEffectiveDate();
        Date enDate = newRule.getExpirationDate();

        List<BusinessRule> allVersions = ruleManagementDao.lookupAllVersions(newRule.getOriginalRuleId());
        for (BusinessRule rule : allVersions) {
            if (BusinessRuleStatus.ACTIVE == rule.getState()) {
                // Check start date
                if (rule.getEffectiveDate().compareTo(stDate) >= 0 && rule.getEffectiveDate().compareTo(enDate) <= 0) {
                    result = true;
                    break;
                }
                // Check end date
                if (rule.getExpirationDate().compareTo(enDate) <= 0 && rule.getExpirationDate().compareTo(stDate) >= 0) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}

package org.kuali.student.r2.core.population.service;

import java.util.ArrayList;
import java.util.Date;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulationServiceMockImpl implements PopulationService {

    private Map<String, PopulationInfo> populations = new HashMap<String, PopulationInfo>();
    private Map<String, PopulationRuleInfo> populationRules = new HashMap<String, PopulationRuleInfo>();
    private Map<String, String> populationToRule = new HashMap<String, String>();

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    @Override
    public StatusInfo applyPopulationRuleToPopulation(String populationRuleId, String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        this.populationToRule.put(populationKey, populationRuleId);
        return newStatus();

    }

    @Override
    public PopulationInfo createPopulation(PopulationInfo populationInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (populations.containsKey(populationInfo.getKey())) {
            throw new AlreadyExistsException(populationInfo.getKey());
        }
        PopulationInfo copy = new PopulationInfo(populationInfo);
        copy.setMeta(newMeta(contextInfo));
        populations.put(copy.getKey(), copy);
        return new PopulationInfo(copy);
    }

    @Override
    public PopulationRuleInfo createPopulationRule(PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        PopulationRuleInfo copy = new PopulationRuleInfo(populationRuleInfo);
        copy.setId(populationRules.size() + "");
        copy.setMeta(newMeta(contextInfo));
        populationRules.put(copy.getId(), copy);
        return new PopulationRuleInfo(copy);
    }

    @Override
    public StatusInfo deletePopulation(String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.populations.remove(populationKey) == null) {
            throw new DoesNotExistException(populationKey);
        }
        return newStatus();
    }

    @Override
    public StatusInfo deletePopulationRule(String populationRuleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.populationRules.remove(populationRuleId) == null) {
            throw new DoesNotExistException(populationRuleId);
        }
        return newStatus();
    }

    @Override
    public List<String> getMembers(String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationRuleInfo rule = this.getPopulationRuleForPopulation(populationKey, contextInfo);
        return new ArrayList(rule.getPersonIds());
    }

    @Override
    public PopulationInfo getPopulation(String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationInfo info = this.populations.get(populationKey);
        if (info == null) {
            throw new DoesNotExistException(populationKey);
        }
        return new PopulationInfo(info);
    }

    @Override
    public List<String> getPopulationKeysByType(String populationTypeId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PopulationRuleInfo getPopulationRule(String populationRuleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationRuleInfo info = this.populationRules.get(populationRuleId);
        if (info == null) {
            throw new DoesNotExistException(populationRuleId);
        }
        return new PopulationRuleInfo(info);
    }

    @Override
    public PopulationRuleInfo getPopulationRuleForPopulation(String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String ruleId = this.populationToRule.get(populationKey);
        if (ruleId == null) {
            throw new DoesNotExistException (populationKey);
        }
        PopulationRuleInfo rule = this.getPopulationRule(ruleId, contextInfo);
        return rule;
    }

    @Override
    public List<String> getPopulationRuleIdsByType(String populationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PopulationRuleInfo> getPopulationRulesByIds(List<String> populationRuleIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PopulationInfo> getPopulationsByIds(List<String> populationKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PopulationInfo> getPopulationsForPopulationRule(String populationRuleId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean isMember(String personId, String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (populationKey.equals(PopulationServiceConstants.EVERYONE_POPULATION_KEY)) {
            return true;
        }
        PopulationRuleInfo rule = this.getPopulationRuleForPopulation(populationKey, contextInfo);
        if (rule.getPersonIds().contains(personId)) {
            return true;
        }
        // TODO: implement other logic
        return false;
    }

    @Override
    public StatusInfo removePopulationRuleFromPopulation(String populationRuleId, String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        this.populationToRule.remove(populationKey);
        return newStatus ();
    }

    @Override
    public List<String> searchForPopulationKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForPopulationRuleIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PopulationRuleInfo> searchForPopulationRules(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PopulationInfo> searchForPopulations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PopulationInfo updatePopulation(String populationKey, PopulationInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        PopulationInfo copy = new PopulationInfo(populationInfo);
        PopulationInfo old = this.getPopulation(populationKey, contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.populations.put(populationInfo.getKey(), copy);
        return new PopulationInfo(copy);
    }

    @Override
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        PopulationRuleInfo copy = new PopulationRuleInfo(populationRuleInfo);
        PopulationRuleInfo old = this.getPopulationRule(populationRuleId, contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.populationRules.put(populationRuleInfo.getId(), copy);
        return new PopulationRuleInfo(copy);
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(String validationTypeId, PopulationInfo populationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(String validationTypeKey, PopulationRuleInfo populationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

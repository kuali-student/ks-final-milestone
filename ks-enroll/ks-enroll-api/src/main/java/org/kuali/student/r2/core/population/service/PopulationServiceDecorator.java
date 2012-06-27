/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.population.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author nwright
 */
public class PopulationServiceDecorator implements PopulationService {
    private PopulationService nextDecorator;

    public PopulationService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(PopulationService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }


    @Override
    public List<ValidationResultInfo> validatePopulationRule(String validationTypeKey, PopulationRuleInfo populationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validatePopulationRule(validationTypeKey, populationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(String validationTypeId, PopulationInfo populationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validatePopulation(validationTypeId, populationInfo, contextInfo);
    }

    @Override
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updatePopulationRule(populationRuleId, populationInfo, contextInfo);
    }

    @Override
    public PopulationInfo updatePopulation(String populationKey, PopulationInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updatePopulation(populationKey, populationInfo, contextInfo);
    }

    @Override
    public List<PopulationInfo> searchForPopulations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForPopulations(criteria, contextInfo);
    }

    @Override
    public List<PopulationRuleInfo> searchForPopulationRules(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForPopulationRules(criteria, contextInfo);
    }

    @Override
    public List<String> searchForPopulationRuleIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForPopulationRuleIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForPopulationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForPopulationIds(criteria, contextInfo);
    }

    @Override
    public StatusInfo removePopulationRuleFromPopulation(String populationRuleId, String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removePopulationRuleFromPopulation(populationRuleId, populationKey, contextInfo);
    }

    @Override
    public Boolean isMemberAsOfDate(String personId, String populationKey, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().isMemberAsOfDate(personId, populationKey, date, contextInfo);
    }

    @Override
    public List<PopulationInfo> getPopulationsForPopulationRule(String populationRuleId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulationsForPopulationRule(populationRuleId, contextInfo);
    }

    @Override
    public List<PopulationInfo> getPopulationsByIds(List<String> populationKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulationsByIds(populationKeys, contextInfo);
    }

    @Override
    public List<PopulationRuleInfo> getPopulationRulesByIds(List<String> populationRuleIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulationRulesByIds(populationRuleIds, contextInfo);
    }

    @Override
    public List<String> getPopulationRuleIdsByType(String populationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulationRuleIdsByType(populationTypeKey, contextInfo);
    }

    @Override
    public PopulationRuleInfo getPopulationRuleForPopulation(String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulationRuleForPopulation(populationKey, contextInfo);
    }

    @Override
    public PopulationRuleInfo getPopulationRule(String populationRuleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulationRule(populationRuleId, contextInfo);
    }

    @Override
    public List<String> getPopulationIdsByType(String populationTypeId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulationIdsByType(populationTypeId, contextInfo);
    }

    @Override
    public PopulationInfo getPopulation(String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPopulation(populationKey, contextInfo);
    }

    @Override
    public List<String> getMembersAsOfDate(String populationKey, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMembersAsOfDate(populationKey, date, contextInfo);
    }

    @Override
    public StatusInfo deletePopulationRule(String populationRuleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deletePopulationRule(populationRuleId, contextInfo);
    }

    @Override
    public StatusInfo deletePopulation(String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deletePopulation(populationKey, contextInfo);
    }

    @Override
    public PopulationRuleInfo createPopulationRule(PopulationRuleInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createPopulationRule(populationInfo, contextInfo);
    }

    @Override
    public PopulationInfo createPopulation(PopulationInfo populationInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createPopulation(populationInfo, contextInfo);
    }

    @Override
    public StatusInfo applyPopulationRuleToPopulation(String populationRuleId, String populationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().applyPopulationRuleToPopulation(populationRuleId, populationKey, contextInfo);
    }
    
}

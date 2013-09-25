/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 7/16/12
 */
package org.kuali.student.r2.core.population.service.impl;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.population.dao.PopulationDao;
import org.kuali.student.r2.core.population.dao.PopulationRuleDao;
import org.kuali.student.r2.core.population.model.PopulationEntity;
import org.kuali.student.r2.core.population.model.PopulationRuleEntity;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of Population Service
 *
 * @author Kuali Student Team
 */
public class PopulationServiceImpl implements PopulationService {
    // populationDao and populationRuleDao injected by Spring context files
    private PopulationDao populationDao;
    private PopulationRuleDao populationRuleDao;
    private CriteriaLookupService criteriaLookupService;

    // ============================= Population start =============================
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public PopulationInfo createPopulation(PopulationInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        PopulationEntity popEntity = new PopulationEntity(populationInfo);
        popEntity.setCreateId(contextInfo.getPrincipalId());
        popEntity.setCreateTime(contextInfo.getCurrentDate());
        popEntity.setUpdateId(contextInfo.getPrincipalId());
        popEntity.setUpdateTime(contextInfo.getCurrentDate());
        populationDao.persist(popEntity);
        
        populationDao.getEm().flush();
        return popEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public PopulationInfo updatePopulation( String populationId, PopulationInfo populationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        PopulationEntity popEntity = populationDao.find(populationId);
        if (null != popEntity) {
            popEntity.fromDTO(populationInfo);
            popEntity.setUpdateId(contextInfo.getPrincipalId());
            popEntity.setUpdateTime(contextInfo.getCurrentDate());
            populationDao.merge(popEntity);
            populationDao.getEm().flush();
            return popEntity.toDto();
        } else {
            throw new DoesNotExistException(populationId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deletePopulation( String populationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        PopulationEntity popEntity = populationDao.find(populationId);
        if (null != popEntity) {
            populationDao.remove(popEntity);
        } else {
            throw new DoesNotExistException(populationId);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public PopulationInfo getPopulation(String populationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        PopulationEntity popEntity = populationDao.find(populationId);
        if (null == popEntity) {
            throw new DoesNotExistException(populationId);
        }
        return popEntity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PopulationInfo> getPopulationsByIds(List<String> populationIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getPopulationsByIds");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getPopulationIdsByType(String populationTypeId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getPopulationIdsByType");
    }

    @Override
    @Transactional(readOnly = true)
    public List<PopulationInfo> getPopulationsForPopulationRule(String populationRuleId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // For now, do it the simple-minded way
        List<PopulationEntity> popEntityList = populationDao.getPopulationsForPopulationRule(populationRuleId);
        List<PopulationInfo> popInfos = new ArrayList<PopulationInfo>();
        for (PopulationEntity entity: popEntityList) {
            PopulationInfo info = entity.toDto();
            popInfos.add(info);
        }
        return popInfos;
    }
    // ============================= PopulationRule end =============================

    // ============================= PopulationRule start =============================
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public PopulationRuleInfo createPopulationRule(PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        PopulationRuleEntity popRuleEntity = new PopulationRuleEntity(populationRuleInfo);
        popRuleEntity.setCreateId(contextInfo.getPrincipalId());
        popRuleEntity.setCreateTime(contextInfo.getCurrentDate());
        popRuleEntity.setUpdateId(contextInfo.getPrincipalId());
        popRuleEntity.setUpdateTime(contextInfo.getCurrentDate());
        // Have to explicitly fetch PopulationInfo and convert it to PopulationEntity for child populations
        Set<PopulationEntity> childPops = new HashSet<PopulationEntity>();
        if (populationRuleInfo.getChildPopulationIds() != null) {
            for (String id: populationRuleInfo.getChildPopulationIds()) {
                PopulationEntity entity = populationDao.find(id);
                childPops.add(entity);
            }
        }
        popRuleEntity.setChildPopulations(childPops);
        populationRuleDao.persist(popRuleEntity);
        
        populationRuleDao.getEm().flush();
        return popRuleEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        PopulationRuleEntity popRuleEntity = populationRuleDao.find(populationRuleId);
        if (null != popRuleEntity) {
            popRuleEntity.fromDTO(populationRuleInfo);
            popRuleEntity.setUpdateId(contextInfo.getPrincipalId());
            popRuleEntity.setUpdateTime(contextInfo.getCurrentDate());
            // Have to explicitly fetch PopulationInfo and convert it to PopulationEntity for child populations
            Set<PopulationEntity> childPops = new HashSet<PopulationEntity>();
            if (populationRuleInfo.getChildPopulationIds() != null) {
                for (String id: populationRuleInfo.getChildPopulationIds()) {
                    PopulationEntity entity = populationDao.find(id);
                    childPops.add(entity);
                }
            }
            popRuleEntity.setChildPopulations(childPops);
            populationRuleDao.merge(popRuleEntity);
            populationRuleDao.getEm().flush();
            return popRuleEntity.toDto();
        } else {
            throw new DoesNotExistException(populationRuleId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deletePopulationRule(String populationRuleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        // TODO: Find all populations that use this population rule and set its populationRuleId to null
        PopulationRuleEntity popRuleEntity = populationRuleDao.find(populationRuleId);
        if (null != popRuleEntity) {
            populationRuleDao.remove(popRuleEntity);
        } else {
            throw new DoesNotExistException(populationRuleId);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public PopulationRuleInfo getPopulationRule(String populationRuleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationRuleEntity popRuleEntity = populationRuleDao.find(populationRuleId);
        if (null == popRuleEntity) {
            throw new DoesNotExistException(populationRuleId);
        }
        return popRuleEntity.toDto();
    }

    @Override
    public List<PopulationRuleInfo> getPopulationRulesByIds(List<String> populationRuleIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getPopulationRulesByIds");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getPopulationRuleIdsByType(String populationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return populationRuleDao.getPopulationRuleIdsByType(populationTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public PopulationRuleInfo getPopulationRuleForPopulation(String populationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationEntity popEnt = populationDao.find(populationId);
        String popRuleId = popEnt.getPopulationRuleId();
        if (popRuleId == null) {
            throw new DoesNotExistException("Missing population rule ID for: " + populationId);
        }
        PopulationRuleEntity popRuleEntity = populationRuleDao.find(popRuleId);
        PopulationRuleInfo ruleInfo = popRuleEntity.toDto();
        return ruleInfo;
    }
    // ============================= PopulationRule end =============================

    @Override
    public Boolean isMemberAsOfDate( String personId,  String populationId,  Date date,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("isMemberAsOfDate");
    }

    @Override
    public List<String> getMembersAsOfDate( String populationId,  Date date,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getMembersAsOfDate");
    }

    @Override
    public List<String> searchForPopulationIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("searchForPopulationIds");
    }

    @Override
    public List<PopulationInfo> searchForPopulations(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

            GenericQueryResults<PopulationEntity> results = criteriaLookupService.lookup(PopulationEntity.class, criteria);
            List<PopulationInfo> populations = new ArrayList<PopulationInfo>(results.getResults().size());
            for (PopulationEntity pe : results.getResults()) {
                try {
                    PopulationInfo pi = this.getPopulation(pe.getId(), contextInfo);
                    populations.add(pi);
                } catch (DoesNotExistException ex) {
                    throw new OperationFailedException(pe.getId(), ex);
                }
            }
            return populations;
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(String validationTypeKey, PopulationInfo populationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //throw new UnsupportedOperationException("validatePopulation");
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<String> searchForPopulationRuleIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("searchForPopulationRuleIds");
    }

    @Override
    public List<PopulationRuleInfo> searchForPopulationRules(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        GenericQueryResults<PopulationRuleEntity> results = criteriaLookupService.lookup(PopulationRuleEntity.class, criteria);
        List<PopulationRuleInfo> populationRules = new ArrayList<PopulationRuleInfo>(results.getResults().size());
        for (PopulationRuleEntity pre : results.getResults()) {
            try {
                PopulationRuleInfo pri = this.getPopulationRule(pre.getId(), contextInfo);
                populationRules.add(pri);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException(pre.getId(), ex);
            }
        }
        return populationRules;
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(String validationTypeKey, PopulationRuleInfo populationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //throw new UnsupportedOperationException("validatePopulationRule");
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo applyPopulationRuleToPopulation(String populationRuleId, String populationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationEntity popEntity = populationDao.find(populationId);
        // Strictly not needed, but is a good check to make sure the populationRule is valid (exception thrown if not valid)
        PopulationRuleEntity popRuleEntity = populationRuleDao.find(populationRuleId);
        popEntity.setPopulationRuleId(populationRuleId);
        populationDao.merge(popEntity);
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo removePopulationRuleFromPopulation(String populationRuleId, String populationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PopulationEntity popEntity = populationDao.find(populationId);
        String popRuleId = popEntity.getPopulationRuleId();
        if (!popRuleId.equals(populationRuleId)) {
            throw new InvalidParameterException("Passed population rule ID, " + populationRuleId + ", does not match population's pop rule ID: " + popRuleId);
        }
        popEntity.setPopulationRuleId(null); // Presumably, setting to null does the trick.
        populationDao.merge(popEntity);
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    @Override
    public PopulationCategoryInfo getPopulationCategory(String populationCategoryId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getPopulationCategory");
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesByIds(List<String> populationCategoryIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getPopulationCategoriesByIds");
    }

    @Override
    public List<String> getPopulationCategoryIdsByType(String populationTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getPopulationCategoryIdsByType");
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesForPopulation( String populationId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getPopulationCategoriesForPopulation");
    }

    @Override
    public List<String> searchForPopulationCategoryIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("searchForPopulationCategoryIds");
    }

    @Override
    public List<PopulationCategoryInfo> searchForPopulationCategories(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("searchForPopulationCategories");
    }

    @Override
    public List<ValidationResultInfo> validatePopulationCategory(String validationTypeKey,  String populationCategoryTypeKey,PopulationCategoryInfo populationCategoryInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("validatePopulationCategory");
    }

    @Override
    public PopulationCategoryInfo createPopulationCategory(String populationCategoryTypeKey, PopulationCategoryInfo populationCategoryInfo,  ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("createPopulationCategory");
    }

    @Override
    public PopulationCategoryInfo updatePopulationCategory(String populationCategoryId, PopulationCategoryInfo populationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("updatePopulationCategory");
    }

    @Override
    public StatusInfo deletePopulationCategory(String populationCategoryId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("deletePopulationCategory");
    }

    @Override
    public StatusInfo addPopulationToPopulationCategory( String populationId, String populationCategoryId,  ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("addPopulationToPopulationCategory");
    }

    @Override
    public StatusInfo removePopulationFromPopulationCategory( String populationId, String populationCategoryId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("removePopulationFromPopulationCategory");
    }

    // Setters/getters
    public PopulationDao getPopulationDao() {
        return populationDao;
    }

    public void setPopulationDao(PopulationDao populationDao) {
        this.populationDao = populationDao;
    }

    public PopulationRuleDao getPopulationRuleDao() {
        return populationRuleDao;
    }

    public void setPopulationRuleDao(PopulationRuleDao populationRuleDao) {
        this.populationRuleDao = populationRuleDao;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }
}

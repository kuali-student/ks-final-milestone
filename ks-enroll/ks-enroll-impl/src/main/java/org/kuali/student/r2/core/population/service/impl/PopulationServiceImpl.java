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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
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
import org.kuali.student.r2.core.fee.model.EnrollmentFeeEntity;
import org.kuali.student.r2.core.population.dao.PopulationDao;
import org.kuali.student.r2.core.population.dao.PopulationRuleDao;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.model.PopulationEntity;
import org.kuali.student.r2.core.population.model.PopulationRuleEntity;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationServiceImpl implements PopulationService {
    @Resource
    private PopulationDao populationDao;
    @Resource
    private PopulationRuleDao populationRuleDao;

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
        return popEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public PopulationInfo updatePopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        PopulationEntity popEntity = populationDao.find(populationId);
        if (null != popEntity) {
            popEntity.fromDTO(populationInfo);
            popEntity.setUpdateId(contextInfo.getPrincipalId());
            popEntity.setUpdateTime(contextInfo.getCurrentDate());
            populationDao.merge(popEntity);
            return popEntity.toDto();
        } else {
            throw new DoesNotExistException(populationId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deletePopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<PopulationInfo> getPopulationsByIds(@WebParam(name = "populationIds") List<String> populationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getPopulationIdsByType(@WebParam(name = "populationTypeId") String populationTypeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PopulationInfo> getPopulationsForPopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        populationRuleDao.persist(popRuleEntity);
        return popRuleEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        PopulationRuleEntity popRuleEntity = populationRuleDao.find(populationRuleId);
        if (null != popRuleEntity) {
            popRuleEntity.fromDTO(populationInfo);
            popRuleEntity.setUpdateId(contextInfo.getPrincipalId());
            popRuleEntity.setUpdateTime(contextInfo.getCurrentDate());
            populationRuleDao.merge(popRuleEntity);
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
    public List<PopulationRuleInfo> getPopulationRulesByIds(@WebParam(name = "populationRuleIds") List<String> populationRuleIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getPopulationRuleIdsByType(@WebParam(name = "populationTypeKey") String populationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PopulationRuleInfo getPopulationRuleForPopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    // ============================= PopulationRule end =============================

    @Override
    public Boolean isMemberAsOfDate(@WebParam(name = "personId") String personId, @WebParam(name = "populationId") String populationId, @WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getMembersAsOfDate(@WebParam(name = "populationId") String populationId, @WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForPopulationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PopulationInfo> searchForPopulations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForPopulationRuleIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PopulationRuleInfo> searchForPopulationRules(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "populationRuleInfo") PopulationRuleInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }



    @Override
    public StatusInfo applyPopulationRuleToPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo removePopulationRuleFromPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PopulationCategoryInfo getPopulationCategory(@WebParam(name = "populationCategoryId") String populationCategoryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesByIds(@WebParam(name = "populationCategoryIds") List<String> populationCategoryIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getPopulationCategoryIdsByType(@WebParam(name = "populationTypeKey") String populationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesForPopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForPopulationCategoryIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PopulationCategoryInfo> searchForPopulationCategories(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validatePopulationCategory(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "populationCategoryTypeKey") String populationCategoryTypeKey, @WebParam(name = "populationCategoryInfo") PopulationCategoryInfo populationCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PopulationCategoryInfo createPopulationCategory(@WebParam(name = "populationCategoryTypeKey") String populationCategoryTypeKey, @WebParam(name = "populationCategoryInfo") PopulationCategoryInfo populationCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PopulationCategoryInfo updatePopulationCategory(@WebParam(name = "populationCategoryId") String populationCategoryId, @WebParam(name = "populationInfo") PopulationCategoryInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deletePopulationCategory(@WebParam(name = "populationCategoryId") String populationCategoryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo addPopulationToPopulationCategory(@WebParam(name = "populationId") String populationId, @WebParam(name = "populationCategoryId") String populationCategoryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo removePopulationFromPopulationCategory(@WebParam(name = "populationId") String populationId, @WebParam(name = "populationCategoryId") String populationCategoryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

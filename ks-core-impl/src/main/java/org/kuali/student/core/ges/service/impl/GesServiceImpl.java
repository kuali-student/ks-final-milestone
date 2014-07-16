/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.core.ges.service.impl;


import org.kuali.student.core.ges.service.dao.ParameterDao;
import org.kuali.student.core.ges.service.dao.ValueDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.rice.core.api.criteria.CriteriaLookupService;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.ges.service.model.ParameterEntity;
import org.kuali.student.core.ges.service.model.ValueEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.springframework.transaction.annotation.Transactional;


public class GesServiceImpl implements GesService
{
	// dao variables 

	private ParameterDao parameterDao;
    private ValueDao valueDao;
    // Criteria Lookup for this object
    private CriteriaLookupService parameterCriteriaLookupService;
    // Criteria Lookup for this object
    private CriteriaLookupService valueCriteriaLookupService;

	public void setParameterDao(ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	public ParameterDao getParameterDao() {
		return this.parameterDao;
	}

	public void setParameterCriteriaLookupService(CriteriaLookupService parameterCriteriaLookupService) {
		this.parameterCriteriaLookupService = parameterCriteriaLookupService;
	}

	public CriteriaLookupService getParameterCriteriaLookupService() {
		return this.parameterCriteriaLookupService;
	}

	public void setValueDao(ValueDao valueDao) {
		this.valueDao = valueDao;
	}

	public ValueDao getValueDao() {
		return this.valueDao;
	}

	public void setValueCriteriaLookupService(CriteriaLookupService valueCriteriaLookupService) {
		this.valueCriteriaLookupService = valueCriteriaLookupService;
	}

	public CriteriaLookupService getValueCriteriaLookupService() {
		return this.valueCriteriaLookupService;
	}

	@Override
	@Transactional(readOnly = true)
	public ParameterInfo getParameter(String parameterKey, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		ParameterEntity entity = parameterDao.find(parameterKey);
		if (entity == null) {
			throw new DoesNotExistException(parameterKey);
		}
		return entity.toDto();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ParameterInfo> getParametersByKeys(List<String> parameterKeys, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
        List<ParameterEntity> entities = parameterDao.findByIds(parameterKeys);
		List<ParameterInfo> list = new ArrayList<ParameterInfo> (entities.size());
		for (ParameterEntity entity : entities) {
			if (entity == null) {
				throw new DoesNotExistException();
			}
			list.add(entity.toDto());
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getParameterKeysByType(String parameterTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		return parameterDao.getIdsByType(parameterTypeKey);

	}

	@Override
	@Transactional(readOnly = true)
	public List<String> searchForParameterKeys(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// SEARCH_FOR_IDS
		List<String> results = new ArrayList<String>();
		GenericQueryResults<ParameterEntity> entities
		    = parameterCriteriaLookupService.lookup(ParameterEntity.class, criteria);
		if (null != entities && !entities.getResults().isEmpty()) {
			for (ParameterEntity entity : entities.getResults()) {
				results.add(entity.getId());
			}
		}
		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ParameterInfo> searchForParameters(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// SEARCH_FOR_INFOS
		List<ParameterInfo> results = new ArrayList<ParameterInfo>();
		GenericQueryResults<ParameterEntity> entities
		    = parameterCriteriaLookupService.lookup(ParameterEntity.class, criteria);
		if (null != entities && !entities.getResults().isEmpty()) {
			for (ParameterEntity entity : entities.getResults()) {
				results.add(entity.toDto());
			}
		}
		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ValidationResultInfo> validateParameter(String validationTypeKey, String parameterTypeKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}

	@Override
	@Transactional(readOnly = false,noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
	public ParameterInfo createParameter(String parameterKey, String parameterTypeKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		parameterInfo.setTypeKey(parameterTypeKey);
		ParameterEntity entity = new ParameterEntity(parameterInfo);
		entity.setEntityCreated(contextInfo);
		parameterDao.persist(entity);
		parameterDao.getEm().flush();
		return entity.toDto();
	}

	@Override
	@Transactional(readOnly = false,noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
	public ParameterInfo updateParameter(String parameterKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!parameterKey.equals(parameterInfo.getKey())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		ParameterEntity entity = parameterDao.find(parameterKey);
		if (entity == null) {
			throw new DoesNotExistException(parameterKey);
		}
		entity.fromDto(parameterInfo);
		entity.setEntityUpdated(contextInfo);

        entity = parameterDao.merge(entity);
        valueDao.getEm().flush();
		return entity.toDto();
	}

	@Override
	@Transactional(readOnly = false,noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
	public StatusInfo deleteParameter(String parameterKey, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		ParameterEntity entity = parameterDao.find(parameterKey);
		if (entity == null) {
			throw new DoesNotExistException(parameterKey);
		}
		parameterDao.remove(entity);
		return new StatusInfo();
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class})
	public ValueInfo getValue(String valueId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		ValueEntity entity = valueDao.find(valueId);
		if (entity == null) {
			throw new DoesNotExistException(valueId);
		}
		return entity.toDto();
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class})
	public List<ValueInfo> getValuesByIds(List<String> valueIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<ValueEntity> entities = valueDao.findByIds(valueIds);
		List<ValueInfo> list = new ArrayList<ValueInfo> (entities.size());
		for (ValueEntity entity : entities) {
			if (entity == null) {
				throw new DoesNotExistException();
			}
			list.add(entity.toDto());
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getValueIdsByType(String valueTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		return valueDao.getIdsByType(valueTypeKey);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> searchForValueIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// SEARCH_FOR_IDS
		List<String> results = new ArrayList<String>();
		GenericQueryResults<ValueEntity> entities
		    = valueCriteriaLookupService.lookup(ValueEntity.class, criteria);
		if (null != entities && !entities.getResults().isEmpty()) {
			for (ValueEntity entity : entities.getResults()) {
				results.add(entity.getId());
			}
		}
		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ValueInfo> searchForValues(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// SEARCH_FOR_INFOS
		List<ValueInfo> results = new ArrayList<ValueInfo>();
		GenericQueryResults<ValueEntity> entities
		    = valueCriteriaLookupService.lookup(ValueEntity.class, criteria);
		if (null != entities && !entities.getResults().isEmpty()) {
			for (ValueEntity entity : entities.getResults()) {
				results.add(entity.toDto());
			}
		}
		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ValidationResultInfo> validateValue(String validationTypeKey, String valueTypeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = {Throwable.class})
	public ValueInfo createValue(String valueTypeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		valueInfo.setTypeKey (valueTypeKey);
		ValueEntity entity = new ValueEntity(valueInfo);
		entity.setEntityCreated(contextInfo);
		valueDao.persist(entity);
		valueDao.getEm().flush();
		return entity.toDto();
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = {Throwable.class})
	public ValueInfo updateValue(String valueId, ValueInfo valueInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!valueId.equals (valueInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		ValueEntity entity = valueDao.find(valueId);
		if (entity == null) {
			throw new DoesNotExistException(valueId);
		}
		entity.fromDto(valueInfo);
		entity.setEntityUpdated(contextInfo);
		entity = valueDao.merge(entity);
        valueDao.getEm().flush();
        return entity.toDto();
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = {Throwable.class})
	public StatusInfo deleteValue(String valueId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		ValueEntity entity = valueDao.find(valueId);
		if (entity == null) {
			throw new DoesNotExistException(valueId);
		}
		valueDao.remove(entity);
		return new StatusInfo();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ValueInfo> getValuesByParameter(String parameterKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<ValueEntity> entities = valueDao.getByParameter(parameterKey);
		List<ValueInfo> list = new ArrayList<ValueInfo> (entities.size());
		for (ValueEntity entity: entities) {
		    list.add (entity.toDto ());
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ValueInfo> evaluateValues(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("invalid configuration");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ValueInfo> evaluateValuesOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("invalid configuration");
	}

	@Override
	@Transactional(readOnly = true)
	public ValueInfo evaluateValue(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,DoesNotExistException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("invalid configuration");
	}

	@Override
	@Transactional(readOnly = true)
	public ValueInfo evaluateValueOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,DoesNotExistException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("invalid configuration");
	}
}


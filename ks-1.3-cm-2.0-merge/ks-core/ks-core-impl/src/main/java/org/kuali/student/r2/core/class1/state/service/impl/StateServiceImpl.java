package org.kuali.student.r2.core.class1.state.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.core.class1.state.dao.LifecycleDao;
import org.kuali.student.r2.core.class1.state.dao.StateDao;
import org.kuali.student.r2.core.class1.state.dao.StateLifecycleRelationDao;
import org.kuali.student.r2.common.class1.state.model.LifecycleEntity;
import org.kuali.student.r2.common.class1.state.model.StateEntity;
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
import org.kuali.student.r2.common.state.dto.LifecycleInfo;
import org.kuali.student.r2.common.state.dto.StateInfo;
import org.kuali.student.r2.common.state.service.StateService;

import org.springframework.transaction.annotation.Transactional;

@WebService(name = "StateService", serviceName = "StateService", portName = "StateService", targetNamespace = "http://student.kuali.org/wsdl/state")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class StateServiceImpl implements StateService{

	private StateDao stateDao;
	private LifecycleDao lifecycleDao;
    private CriteriaLookupService criteriaLookupService;
	
	public StateDao getStateDao() {
		return stateDao;
	}
	public void setStateDao(StateDao stateDao) {
		this.stateDao = stateDao;
	}

	public LifecycleDao getLifecycleDao() {
        return lifecycleDao;
    }

    public void setLifecycleDao(LifecycleDao lifecycleDao) {
        this.lifecycleDao = lifecycleDao;
    }
    
    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }	

    @Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            LifecycleEntity sp = lifecycleDao.getProcessByKey(lifecycleKey);
            if (null == sp) {
                throw new DoesNotExistException(lifecycleKey);
            }
            return sp.toDto();
        } catch (NoResultException ex) {
            throw new DoesNotExistException(lifecycleKey);
        }
    }

    @Override
    public StateInfo getState(String stateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            StateEntity state = stateDao.getState(stateKey);
            if (null == state) {
                throw new DoesNotExistException(stateKey);
            }
            return state.toDto();
        } catch (NoResultException ex) {
            throw new DoesNotExistException(stateKey);
        }
    }

    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(List<String> lifecycleKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LifecycleEntity> lifecycles = lifecycleDao.findByIds(lifecycleKeys);
        List<LifecycleInfo> result = new ArrayList<LifecycleInfo>(lifecycles.size());
        for (int i = 0; i < lifecycles.size(); i++) {
            LifecycleEntity entity = lifecycles.get(i);
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null,
                // then one of the keys in the list was not found
                throw new DoesNotExistException(lifecycleKeys.get(i));
            }
            result.add(entity.toDto());
        }
        return result;
    }
    
    @Override
    public List<String> getLifecycleKeysByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LifecycleEntity> lifecycles = lifecycleDao.getLifecyclesByRefObjectUri(refObjectUri);
        List<String> result = new ArrayList<String>();
        for (LifecycleEntity entity : lifecycles) {
            if (entity != null) {
                result.add(entity.getId());
            }
        }
        return result;
    }
    
    @Override
    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> lifecycleKeys = new ArrayList<String>();
        GenericQueryResults<LifecycleEntity> results = criteriaLookupService.lookup(LifecycleEntity.class, criteria);
        if (null != results && results.getResults().size() > 0) {
            for (LifecycleEntity lifecycle : results.getResults()) {
                lifecycleKeys.add(lifecycle.getId());
            }
        }
        return lifecycleKeys;
    }
    
    @Override
    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LifecycleInfo> lifecycleInfos = new ArrayList<LifecycleInfo>();
        GenericQueryResults<LifecycleEntity> results = criteriaLookupService.lookup(LifecycleEntity.class, criteria);
        if (null != results && results.getResults().size() > 0) {
            for (LifecycleEntity lifecycle : results.getResults()) {
                lifecycleInfos.add(lifecycle.toDto());
            }
        }
        return lifecycleInfos;
    }

    @Override
    public List<ValidationResultInfo> validateLifecycle(String validationTypeKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public LifecycleInfo createLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        LifecycleEntity entity = lifecycleDao.find(lifecycleKey);
        if (entity != null) {
            throw new AlreadyExistsException(lifecycleKey);
        }
        if (!lifecycleKey.equals(lifecycleInfo.getKey())) {
            throw new InvalidParameterException(lifecycleKey + " does not match the key in the info object " + lifecycleInfo.getKey());
        }

        entity = new LifecycleEntity(lifecycleInfo);
        entity.setId(lifecycleKey);

        entity.setEntityCreated(contextInfo);

        lifecycleDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public LifecycleInfo updateLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        LifecycleEntity entity = lifecycleDao.find(lifecycleKey);
        if (entity == null) {
            throw new DoesNotExistException(lifecycleKey);
        }
        entity.fromDto(lifecycleInfo);

        entity.setEntityUpdated(contextInfo);

        lifecycleDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteLifecycle(String lifecycleKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LifecycleEntity lifecycle = lifecycleDao.find(lifecycleKey);
        if (lifecycle == null) {
            throw new DoesNotExistException(lifecycleKey);
        }
        lifecycleDao.remove(lifecycle);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<StateInfo> getStatesByKeys(List<String> stateKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateEntity> list = stateDao.findByIds(stateKeys);
        List<StateInfo> result = new ArrayList<StateInfo>(list.size());
        for (int i = 0; i < list.size(); i++) {
            StateEntity entity = list.get(i);
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null,
                // then one of the keys in the list was not found
                throw new DoesNotExistException(stateKeys.get(i));
            }
            result.add(entity.toDto());
        }
        return result;
    }

    @Override
    public List<StateInfo> getStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check that the key is valid
        this.getLifecycle(lifecycleKey, contextInfo);
        List<StateEntity> entities = null;
        entities = stateDao.getStatesByLifecycle(lifecycleKey);
        List<StateInfo> infos = new ArrayList<StateInfo>();
        for (StateEntity state : entities) {
            infos.add(state.toDto());
        }
        return infos;
    }

    @Override
    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> stateKeys = new ArrayList<String>();
        GenericQueryResults<StateEntity> results = criteriaLookupService.lookup(StateEntity.class, criteria);
        if (null != results && results.getResults().size() > 0) {
            for (StateEntity state : results.getResults()) {
                stateKeys.add(state.getId());
            }
        }
        return stateKeys;
    }

    @Override
    public List<StateInfo> searchForStates(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateInfo> stateInfos = new ArrayList<StateInfo>();
        GenericQueryResults<StateEntity> results = criteriaLookupService.lookup(StateEntity.class, criteria);
        if (null != results && results.getResults().size() > 0) {
            for (StateEntity state : results.getResults()) {
                stateInfos.add(state.toDto());
            }
        }
        return stateInfos;
    }

    @Override
    public List<ValidationResultInfo> validateState(String validationTypeKey, String lifecycleKey, StateInfo stateInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public StateInfo createState(String lifecycleKey, String stateKey, StateInfo stateInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        StateEntity entity = stateDao.find(stateKey);
        if (entity != null) {
            throw new AlreadyExistsException(stateKey);
        }
        if (!lifecycleKey.equals(stateInfo.getLifecycleKey())) {
            throw new InvalidParameterException(lifecycleKey + " life cycle key does not match the key in the info object " + stateInfo.getLifecycleKey());
        }
        if (!stateKey.equals(stateInfo.getKey())) {
            throw new InvalidParameterException(stateKey + " state key does not match the key in the info object " + stateInfo.getKey());
        }
        entity = new StateEntity(stateInfo);
        entity.setId(stateKey);
        entity.setLifecycleKey(lifecycleKey);

        entity.setEntityCreated(contextInfo);

        stateDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public StateInfo updateState(String stateKey, StateInfo stateInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, 
            InvalidParameterException, MissingParameterException, OperationFailedException, 
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        StateEntity entity = stateDao.find(stateKey);
        if (entity == null) {
            throw new DoesNotExistException(stateKey);
        }
        entity.fromDto(stateInfo);

        entity.setEntityUpdated(contextInfo);

        stateDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteState(String stateKey, ContextInfo contextInfo) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
            OperationFailedException, PermissionDeniedException {
        StateEntity entity = stateDao.find(stateKey);
        if (entity == null) {
            throw new DoesNotExistException(stateKey);
        }
        stateDao.remove(entity);
        StatusInfo deleteStatus = new StatusInfo();
        deleteStatus.setSuccess(true);
        return deleteStatus;
    }

}

package org.kuali.student.r2.core.class1.state.service.impl;

import org.apache.commons.lang.StringUtils;
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
import org.kuali.student.r2.core.class1.state.dao.LifecycleDao;
import org.kuali.student.r2.core.class1.state.dao.StateChangeDao;
import org.kuali.student.r2.core.class1.state.dao.StateConstraintDao;
import org.kuali.student.r2.core.class1.state.dao.StateDao;
import org.kuali.student.r2.core.class1.state.dao.StatePropagationDao;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.class1.state.model.LifecycleEntity;
import org.kuali.student.r2.core.class1.state.model.StateChangeEntity;
import org.kuali.student.r2.core.class1.state.model.StateConstraintEntity;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.core.class1.state.model.StatePropagationEntity;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebService(name = "StateService", serviceName = "StateService", portName = "StateService", targetNamespace = "http://student.kuali.org/wsdl/state")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class StateServiceImpl implements StateService {

    private StateDao stateDao;
    private LifecycleDao lifecycleDao;
    private StateChangeDao stateChangeDao;
    private StateConstraintDao stateConstraintDao;
    private StatePropagationDao statePropagationDao;
    private CriteriaLookupService lifecycleCriteriaLookupService;
    private CriteriaLookupService stateCriteriaLookupService;

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

    public StateChangeDao getStateChangeDao() {
        return stateChangeDao;
    }

    public void setStateChangeDao(StateChangeDao stateChangeDao) {
        this.stateChangeDao = stateChangeDao;
    }

    public StateConstraintDao getStateConstraintDao() {
        return stateConstraintDao;
    }

    public void setStateConstraintDao(StateConstraintDao stateConstraintDao) {
        this.stateConstraintDao = stateConstraintDao;
    }

    public StatePropagationDao getStatePropagationDao() {
        return statePropagationDao;
    }

    public void setStatePropagationDao(StatePropagationDao statePropagationDao) {
        this.statePropagationDao = statePropagationDao;
    }

    public CriteriaLookupService getLifecycleCriteriaLookupService() {
        return lifecycleCriteriaLookupService;
    }

    public void setLifecycleCriteriaLookupService(CriteriaLookupService lifecycleCriteriaLookupService) {
        this.lifecycleCriteriaLookupService = lifecycleCriteriaLookupService;
    }

    public CriteriaLookupService getStateCriteriaLookupService() {
        return stateCriteriaLookupService;
    }

    public void setStateCriteriaLookupService(CriteriaLookupService stateCriteriaLookupService) {
        this.stateCriteriaLookupService = stateCriteriaLookupService;
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
        GenericQueryResults<LifecycleEntity> results = lifecycleCriteriaLookupService.lookup(LifecycleEntity.class, criteria);
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
        GenericQueryResults<LifecycleEntity> results = lifecycleCriteriaLookupService.lookup(LifecycleEntity.class, criteria);
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
        
        lifecycleDao.getEm().flush();
        
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
        
        lifecycleDao.getEm().flush();
        
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
    public List<String> getInitialStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if( StringUtils.isEmpty(lifecycleKey) ) throw new MissingParameterException( "lifecycle key must be supplied" );
        lifecycleKey = lifecycleKey.trim();

        List<String> result = new ArrayList<String>();
        try {
            List<String> initialStateKeys = stateDao.getInitialStateKeys( lifecycleKey );
            if( initialStateKeys != null ) {
                result.addAll( initialStateKeys );
            }
        } catch ( Exception e ) {
            throw new OperationFailedException( "unable to get initial-state-keys for lifecycle(" + lifecycleKey + ")", e );
        }

        return result;
    }

    @Override
    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> stateKeys = new ArrayList<String>();
        GenericQueryResults<StateEntity> results = stateCriteriaLookupService.lookup(StateEntity.class, criteria);
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
        GenericQueryResults<StateEntity> results = stateCriteriaLookupService.lookup(StateEntity.class, criteria);
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
        
        stateDao.getEm().flush();
        
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
        
        stateDao.getEm().flush();
        
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

    @Override
    public StatusInfo addInitialStateToLifecycle(String initialStateKey, String lifecycleKey, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo removeInitialStateFromLifecycle(String initialStateKey, String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StateChangeInfo getStateChange( String stateChangeId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            StateChangeEntity stateChangeEntity = stateChangeDao.find(stateChangeId);
            if (null == stateChangeEntity) {
                throw new DoesNotExistException(stateChangeId);
            }
            return stateChangeEntity.toDto();
        } catch (NoResultException ex) {
            throw new DoesNotExistException(stateChangeId);
        }

    }

    @Override
    public List<StateChangeInfo> getStateChangesByIds( List<String> stateChangeIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStateChangeIdsByType( String stateChangeTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromState( String fromStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateChangeInfo> getStateChangesByToState( String toStateKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromStateAndToState(String fromStateKey,  String toStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateChangeEntity> stateChangeEntities = stateChangeDao.getStateChangesByFromStateAndToState(fromStateKey, toStateKey);
        List<StateChangeInfo> stateChangeInfos = new ArrayList<StateChangeInfo>();

        for(StateChangeEntity scentity : stateChangeEntities){
            stateChangeInfos.add(scentity.toDto());
        }

        return stateChangeInfos;
    }

    @Override
    public List<String> searchForStateChangeIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateChangeInfo> searchForStateChanges( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateStateChange(String validationTypeKey, String toStateKey,  String fromStateKey, String stateChangeTypeKey, StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional
    public StateChangeInfo createStateChange(String toStateKey, String fromStateKey, String stateChangeTypeKey, StateChangeInfo stateChangeInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        StateEntity toStateEntity = stateDao.find(toStateKey);
        if (toStateEntity == null) {
            throw new DoesNotExistException(toStateKey);
        }

        StateEntity fromStateEntity = stateDao.find(fromStateKey);
        if (fromStateEntity == null) {
            throw new DoesNotExistException(fromStateKey);
        }

        List<StateChangeEntity> stateChangeEntities = stateChangeDao.getStateChangesByFromStateAndToState(fromStateKey, toStateKey);
        if (stateChangeEntities != null && !stateChangeEntities.isEmpty()) {
            throw new OperationFailedException(fromStateKey + "to" + toStateKey + "Already Exists");
        }

        if (!stateChangeTypeKey.equals(stateChangeInfo.getTypeKey())) {
            throw new InvalidParameterException(stateChangeTypeKey + " stateChangeType key does not match the key in the info object " + stateChangeInfo.getTypeKey());
        }

        StateChangeEntity entity = new StateChangeEntity(stateChangeInfo);
        entity.setToStateKey(toStateKey);
        entity.setFromStateKey(fromStateKey);
        entity.setTypeKey(stateChangeTypeKey);

        entity.setEntityCreated(contextInfo);

        stateChangeDao.persist(entity);
        
        stateChangeDao.getEm().flush();
        
        return entity.toDto();
    }

    @Override
    public StateChangeInfo updateStateChange(String stateChangeId,StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        StateChangeEntity entity = stateChangeDao.find(stateChangeId);

        if (entity == null) {
            throw new DoesNotExistException(stateChangeId);
        }

        entity.fromDTO(stateChangeInfo);
        entity.setEntityUpdated(contextInfo);

        stateChangeDao.merge(entity);
        
        stateChangeDao.getEm().flush();
        
        return entity.toDto();
    }

    @Override
    public StatusInfo deleteStateChange( String stateChangeId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StateChangeEntity entity = stateChangeDao.find(stateChangeId);

        if (entity == null) {
            throw new DoesNotExistException(stateChangeId);
        }

        stateChangeDao.remove(entity);
        StatusInfo deleteStatus = new StatusInfo();
        deleteStatus.setSuccess(true);

        return deleteStatus;
    }

    @Override
    public StateConstraintInfo getStateConstraint( String stateConstraintId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            StateConstraintEntity stateConstraintEntity = stateConstraintDao.find(stateConstraintId);
            if (null == stateConstraintEntity) {
                throw new DoesNotExistException(stateConstraintId);
            }
            return stateConstraintEntity.toDto();
        } catch (NoResultException ex) {
            throw new DoesNotExistException(stateConstraintId);
        }
    }

    @Override
    public List<StateConstraintInfo> getStateConstraintsByIds( List<String> stateConstraintIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateConstraintEntity> entities = stateConstraintDao.findByIds(stateConstraintIds);
        List<StateConstraintInfo> infos = new ArrayList<StateConstraintInfo>(entities.size());
        for(StateConstraintEntity entity : entities){
            infos.add(entity.toDto());
        }
        return infos;
    }

    @Override
    public List<String> getStateConstraintIdsByType(String stateConstraintTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> stateChangeIds = stateConstraintDao.getStateConstraintIdsByType(stateConstraintTypeKey);

        return stateChangeIds;
    }

    @Override
    public List<String> searchForStateConstraintIds( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateConstraintInfo> searchForStateConstraints( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateStateConstraint(String validationTypeKey, String stateConstraintTypeKey, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional
    public StateConstraintInfo createStateConstraint(String stateConstraintTypeKey,
                                                     StateConstraintInfo stateConstraintInfo,
                                                     ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        StateConstraintEntity entity = new StateConstraintEntity(stateConstraintInfo);

        entity.setEntityCreated(contextInfo);

        stateConstraintDao.persist(entity);
        
        stateConstraintDao.getEm().flush();
        
        return entity.toDto();
    }

    @Override
    @Transactional
    public StateConstraintInfo updateStateConstraint(String stateConstraintId,
                                                     StateConstraintInfo stateConstraintInfo,
                                                     ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        StateConstraintEntity entity = stateConstraintDao.find(stateConstraintId);
        if (entity == null) {
            throw new DoesNotExistException(stateConstraintId);
        }
        entity.fromDto(stateConstraintInfo);

        entity.setEntityUpdated(contextInfo);

        stateConstraintDao.merge(entity);
        
        stateConstraintDao.getEm().flush();
        
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteStateConstraint(String stateConstraintId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StateConstraintEntity entity = stateConstraintDao.find(stateConstraintId);
        if (entity == null) {
            throw new DoesNotExistException(stateConstraintId);
        }
        stateConstraintDao.remove(entity);
        StatusInfo deleteStatus = new StatusInfo();
        deleteStatus.setSuccess(true);
        return deleteStatus;
    }

    @Override
    public StatePropagationInfo getStatePropagation(String statePropagationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatePropagationEntity entity = statePropagationDao.find(statePropagationId);
        if (entity == null){
            throw new DoesNotExistException("Entity for " + statePropagationId + " doesnt exists");
        }
        return entity.toDto();
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByIds(List<String> statePropagationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<StatePropagationEntity> entities = statePropagationDao.findByIds(statePropagationIds);

        if (!entities.isEmpty()){
            List<StatePropagationInfo> infos = new ArrayList<StatePropagationInfo>(entities.size());
            for(StatePropagationEntity entity : entities){
                 infos.add(entity.toDto());
            }
            return infos;
        } else {
            return new ArrayList();
        }
    }

    @Override
    public List<String> getStatePropagationIdsByType(String statePropagationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return statePropagationDao.getStatePropagationIdsByType(statePropagationTypeKey);
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByTargetState(String targetStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<StatePropagationEntity> entities = statePropagationDao.getStatePropagationsByTargetState(targetStateKey);

        if (!entities.isEmpty()){
            List<StatePropagationInfo> infos = new ArrayList<StatePropagationInfo>(entities.size());
            for(StatePropagationEntity entity : entities){
                 infos.add(entity.toDto());
            }
            return infos;
        } else {
            return new ArrayList();
        }
    }

    @Override
    public List<String> searchForStatePropagationIds( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StatePropagationInfo> searchForStatePropagations( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateStatePropagation(String validationTypeKey,  String targetStateChangeId, String statePropagationTypeKey,  StatePropagationInfo statePropagationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional
    public StatePropagationInfo createStatePropagation(String targetStateChangeId,  String statePropagationTypeKey, StatePropagationInfo statePropagationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        if (!StringUtils.equals(statePropagationTypeKey,statePropagationInfo.getTypeKey())) {
            throw new InvalidParameterException(statePropagationTypeKey + " key does not match the key in the info object " + statePropagationInfo.getTypeKey());
        }

        StatePropagationEntity entity = new StatePropagationEntity(statePropagationInfo);
        entity.setEntityCreated(contextInfo);

        statePropagationDao.persist(entity);
        
        statePropagationDao.getEm().flush();
        
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatePropagationInfo updateStatePropagation(String statePropagationId,  StatePropagationInfo statePropagationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        StatePropagationEntity entity = statePropagationDao.find(statePropagationId);

        if (entity == null) {
            throw new DoesNotExistException(statePropagationId);
        }

        entity.fromDto(statePropagationInfo);
        entity.setEntityUpdated(contextInfo);

        statePropagationDao.merge(entity);
        
        statePropagationDao.getEm().flush();
        
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteStatePropagation(String statePropagationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatePropagationEntity entity = statePropagationDao.find(statePropagationId);

        if (entity == null) {
            throw new DoesNotExistException(statePropagationId);
        }

        statePropagationDao.remove(entity);
        StatusInfo deleteStatus = new StatusInfo();
        deleteStatus.setSuccess(true);

        return deleteStatus;
    }
}

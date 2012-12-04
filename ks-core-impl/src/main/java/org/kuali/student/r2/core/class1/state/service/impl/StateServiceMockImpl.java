/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.state.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

import javax.jws.WebParam;
import java.util.*;


/**
 *  This is a mock implementation of the State Service using in-core
 *  hashmaps as persistence.
 */

public class StateServiceMockImpl 
    implements StateService, MockService {

    private final Map<String, LifecycleInfo> lifecycles = new HashMap<String, LifecycleInfo>();
    private final Map<String, StateInfo> states = new HashMap<String, StateInfo>();
    private final Map<String, Collection<String>> lifecycleStates = new HashMap<String, Collection<String>>();

    
    @Override
	public void clear() {
    	this.lifecycles.clear();
    	this.lifecycleStates.clear();
    	this.states.clear();
		
	}

	@Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LifecycleInfo lifecycle = this.lifecycles.get(lifecycleKey);
        if (lifecycle == null ) {
            throw new DoesNotExistException(lifecycleKey);
        }
     
        return lifecycle;
    }

    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(List<String> lifecycleKeys, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LifecycleInfo> ret = new ArrayList<LifecycleInfo>();
        for (String key : lifecycleKeys) {
            ret.add(getLifecycle(key, contextInfo));
        }

        return ret;
    }

    @Override
    public List<String> getLifecycleKeysByRefObjectUri(String refObjectUri, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<String> ret = new ArrayList<String>();
        for (LifecycleInfo lifecycle : this.lifecycles.values()) {
            if (refObjectUri.equals(lifecycle.getRefObjectUri())) {
                ret.add(lifecycle.getKey());
            }
        }

        return ret;
    }

    @Override
    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<String>(this.lifecycles.keySet()); // TODO: look at the criteria
    }

    @Override
    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<LifecycleInfo>(this.lifecycles.values()); // TODO: look at the criteria
    }

    @Override
    public List<ValidationResultInfo> validateLifecycle(String validationTypeKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        return new ArrayList<ValidationResultInfo>(); // TODO check for valid info
    }

    @Override
    public LifecycleInfo createLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) 
        throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        if (this.lifecycles.get(lifecycleKey) != null) {
            throw new AlreadyExistsException(lifecycleKey + " already exists");
        }

        // TODO call validate 

        this.lifecycles.put(lifecycleKey, lifecycleInfo);
        this.lifecycleStates.put(lifecycleKey, new HashSet<String>());

        return lifecycleInfo;
    }

    @Override
    public LifecycleInfo updateLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) 
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        if (this.lifecycles.get(lifecycleKey) != null) {
            throw new DoesNotExistException(lifecycleKey + " does not exist");
        }

        // TODO call validate 

        this.lifecycles.put(lifecycleKey, lifecycleInfo);
        return lifecycleInfo;
    }

    @Override
    public StatusInfo deleteLifecycle(String lifecycleKey, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        if (this.lifecycles.get(lifecycleKey) != null) {
            throw new DoesNotExistException(lifecycleKey + " does not exist");
        }

        this.lifecycles.remove(lifecycleKey);
        this.lifecycleStates.remove(lifecycleKey);

        return new StatusInfo();
    }


    @Override
    public StateInfo getState(String stateKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StateInfo state = this.states.get(stateKey);
        if (state == null ) {
            throw new DoesNotExistException(stateKey);
        }

        return state;
    }

    @Override
    public List<StateInfo> getStatesByKeys(List<String> stateKeys, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<StateInfo> ret = new ArrayList<StateInfo>();
        for (String key : stateKeys) {
            ret.add(getState(key, contextInfo));
        }

        return ret;
    }

    public List<StateInfo> getStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Collection<String> stateKeys = this.lifecycleStates.get(lifecycleKey);
        if (states == null) {
            throw new DoesNotExistException(lifecycleKey + " not found");
        }

        return getStatesByKeys(new ArrayList<String>(stateKeys), contextInfo);
    }

    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<String>(this.states.keySet()); // TODO: look at criteria
    }

    public List<StateInfo> searchForStates(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<StateInfo>(this.states.values()); // TODO: look at criteria
    }

    @Override
    public List<ValidationResultInfo> validateState(String validationStateKey, String lifecycleKey, StateInfo stateInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (this.lifecycles.get(lifecycleKey) == null) {
            throw new DoesNotExistException(lifecycleKey + " not found");
        }

        return new ArrayList<ValidationResultInfo>(); // TODO check for valid info
    }

    @Override
    public StateInfo createState(String lifecycleKey, String stateKey, StateInfo stateInfo, ContextInfo contextInfo) 
        throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (this.states.get(stateKey) != null) {
            throw new AlreadyExistsException(stateKey + " already exists");
        }

        if (this.lifecycles.get(lifecycleKey) == null) {
            throw new DoesNotExistException(lifecycleKey + " not found");
        }

        if (stateInfo.getKey() == null) {
            stateInfo.setKey(stateKey);
        } else if (!stateKey.equals(stateInfo.getKey())) {
            throw new DataValidationErrorException("attempt to set a state key");
        }

        if (stateInfo.getLifecycleKey() == null) {
            stateInfo.setLifecycleKey(lifecycleKey);
        } else if (!lifecycleKey.equals(stateInfo.getLifecycleKey())) {
            throw new DataValidationErrorException("attempt to set a lifecycle in state");
        }
            
        // TODO call validate 

        this.states.put(stateKey, stateInfo);
        this.lifecycleStates.get(lifecycleKey).add(stateKey);

        return stateInfo;
    }

    @Override
    public StateInfo updateState(String stateKey, StateInfo stateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        StateInfo oldState = this.states.get(stateKey);
        if (oldState == null) {
            throw new DoesNotExistException(stateKey + " does not exist");
        }

        if (stateInfo.getKey() == null) {
            stateInfo.setKey(oldState.getKey());
        } else if (!oldState.getKey().equals(stateInfo.getKey())) {
            throw new DataValidationErrorException("attempt to set the state key");
        }

        if (stateInfo.getLifecycleKey() == null) {
            stateInfo.setLifecycleKey(oldState.getLifecycleKey());
        } else if (!oldState.getLifecycleKey().equals(stateInfo.getLifecycleKey())) {
            throw new DataValidationErrorException("attempt to set a lifecycle in state");
        }

        this.states.put(oldState.getKey(), stateInfo);
        return stateInfo;
    }

    @Override
    public StatusInfo deleteState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StateInfo state = this.states.get(stateKey);
        if (state == null) {
            throw new DoesNotExistException( stateKey + " does not exist");
        }

        this.states.remove(stateKey);
        this.lifecycleStates.get(state.getLifecycleKey()).remove(stateKey);

        return new StatusInfo();
    }

    @Override
    public StateChangeInfo getStateChange(String stateChangeId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByIds(List<String> stateChangeIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStateChangeIdsByType( String stateChangeTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromState( String fromStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByToState(String toStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromStateAndToState( String fromStateKey, String toStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateChangeInfo> stateChangeInfos = new ArrayList<StateChangeInfo>();
        stateChangeInfos.add(new StateChangeInfo());
        return stateChangeInfos;
    }

    @Override
    public List<String> searchForStateChangeIds( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> searchForStateChanges( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStateChange( String validationTypeKey, String toStateKey,  String fromStateKey,  String stateChangeTypeKey,  StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateChangeInfo createStateChange(String toStateKey,  String fromStateKey,  String stateChangeTypeKey,  StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateChangeInfo updateStateChange(String stateChangeId,  StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStateChange(String stateChangeId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo getStateConstraint(String stateConstraintId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateConstraintInfo> getStateConstraintsByIds(List<String> stateConstraintIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<StateConstraintInfo>();
    }

    @Override
    public List<String> getStateConstraintIdsByType(String stateConstraintTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStateConstraintIds( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateConstraintInfo> searchForStateConstraints( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStateConstraint( String validationTypeKey,  String stateConstraintTypeKey, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo createStateConstraint( String stateConstraintTypeKey,  StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo updateStateConstraint(String stateConstraintId,  StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStateConstraint(String stateConstraintId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo getStatePropagation(String statePropagationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByIds(List<String> statePropagationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<StatePropagationInfo>();
    }

    @Override
    public List<String> getStatePropagationIdsByType( String statePropagationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByTargetState( String targetStateId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStatePropagationIds( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> searchForStatePropagations( QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStatePropagation( String validationTypeKey, String targetStateChangeId,  String statePropagationTypeKey, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo createStatePropagation(String targetStateChangeId,  String statePropagationTypeKey, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo updateStatePropagation(String statePropagationId, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStatePropagation(String statePropagationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }
}

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

package org.kuali.student.r2.core.class1.state.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
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
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 *  This is a mock implementation of the State Service using in-core
 *  hashmaps as persistence.
 */

public class StateServiceMockImpl 
    implements StateService, MockService {

    private final Map<String, LifecycleInfo> lifecycleMap = new HashMap<String, LifecycleInfo>();
    private final Map<String, Collection<String>> lifeCycleStatesMap = new HashMap<String, Collection<String>>();
    private final Map<String, Collection<String>> initialStatesMap = new HashMap<String, Collection<String>>();
    private final Map<String, StateInfo> stateMap = new HashMap<String, StateInfo>();
    private Map<String, StateChangeInfo> stateChangeMap = new LinkedHashMap<String, StateChangeInfo>();
    private Map<String, StateConstraintInfo> stateConstraintMap = new LinkedHashMap<String, StateConstraintInfo>();
    private Map<String, StatePropagationInfo> statePropagationMap = new LinkedHashMap<String, StatePropagationInfo>();

    
    @Override
	public void clear() {
    	this.lifecycleMap.clear();
    	this.lifeCycleStatesMap.clear();
        this.initialStatesMap.clear();
    	this.stateMap.clear();
        this.stateChangeMap.clear();
        this.stateConstraintMap.clear();
        this.statePropagationMap.clear();
	}

	@Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LifecycleInfo lifecycle = this.lifecycleMap.get(lifecycleKey);
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
        for (LifecycleInfo lifecycle : this.lifecycleMap.values()) {
            if (refObjectUri.equals(lifecycle.getRefObjectUri())) {
                ret.add(lifecycle.getKey());
            }
        }

        return ret;
    }

    @Override
    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<String>(this.lifecycleMap.keySet()); // TODO: look at the criteria
    }

    @Override
    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<LifecycleInfo>(this.lifecycleMap.values()); // TODO: look at the criteria
    }

    @Override
    public List<ValidationResultInfo> validateLifecycle(String validationTypeKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        return new ArrayList<ValidationResultInfo>(); // TODO check for valid info
    }

    @Override
    public LifecycleInfo createLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) 
        throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        // create
        LifecycleInfo copy = new LifecycleInfo(lifecycleInfo);
        if (copy.getKey() == null) {
            copy.setKey(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        lifecycleMap.put(copy.getKey(), copy);
        this.lifeCycleStatesMap.put(lifecycleKey, new HashSet<String>());
        this.initialStatesMap.put(lifecycleKey, new HashSet<String>());

        return new LifecycleInfo(copy);
    }

    @Override
    public LifecycleInfo updateLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) 
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        // update
        if (!lifecycleKey.equals (lifecycleInfo.getKey())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        LifecycleInfo copy = new LifecycleInfo(lifecycleInfo);
        LifecycleInfo old = this.getLifecycle(lifecycleInfo.getKey(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.lifecycleMap .put(lifecycleInfo.getKey(), copy);
        return new LifecycleInfo(copy);

    }

    @Override
    public StatusInfo deleteLifecycle(String lifecycleKey, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        if (this.lifecycleMap.get(lifecycleKey) == null) {
            throw new DoesNotExistException(lifecycleKey + " does not exist");
        }

        this.lifecycleMap.remove(lifecycleKey);
        this.lifeCycleStatesMap.remove(lifecycleKey);
        this.initialStatesMap.remove(lifecycleKey);

        return newStatus();
    }


    @Override
    public StateInfo getState(String stateKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StateInfo state = this.stateMap.get(stateKey);
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

        Collection<String> stateKeys = this.lifeCycleStatesMap.get(lifecycleKey);
        if (stateMap == null) {
            throw new DoesNotExistException(lifecycleKey + " not found");
        }

        return getStatesByKeys(new ArrayList<String>(stateKeys), contextInfo);
    }

    @Override
    public List<String> getInitialStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {

        Collection<String> initialStateKeys = this.initialStatesMap.get(lifecycleKey);

        ArrayList<String> initialStates = new ArrayList<String>();

        if (initialStateKeys != null) {
            initialStates.addAll(initialStateKeys);
        }

        return initialStates;
    }

    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<String>(this.stateMap.keySet()); // TODO: look at criteria
    }

    public List<StateInfo> searchForStates(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<StateInfo>(this.stateMap.values()); // TODO: look at criteria
    }

    @Override
    public List<ValidationResultInfo> validateState(String validationStateKey, String lifecycleKey, StateInfo stateInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (this.lifecycleMap.get(lifecycleKey) == null) {
            throw new DoesNotExistException(lifecycleKey + " not found");
        }

        return new ArrayList<ValidationResultInfo>(); // TODO check for valid info
    }

    @Override
    public StateInfo createState(String lifecycleKey, String stateKey, StateInfo stateInfo, ContextInfo contextInfo) 
        throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (this.stateMap.get(stateKey) != null) {
            throw new AlreadyExistsException(stateKey + " already exists");
        }

        if (this.lifecycleMap.get(lifecycleKey) == null) {
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
        stateInfo.setMeta(newMeta(contextInfo));
        this.stateMap.put(stateKey, stateInfo);
        this.lifeCycleStatesMap.get(lifecycleKey).add(stateKey);

        if( stateInfo.getIsInitialState() != null && stateInfo.getIsInitialState() ) this.addInitialStateToLifecycle( stateKey, lifecycleKey, contextInfo );

        return stateInfo;
    }


    @Override
    public StateInfo updateState(String stateKey, StateInfo stateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        StateInfo oldState = this.stateMap.get(stateKey);
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

        StateInfo copy = new StateInfo(stateInfo);

        if (!oldState.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(oldState.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(oldState.getMeta(), contextInfo));
        this.stateMap.put(stateInfo.getKey(), copy);
        return new StateInfo(copy);
    }

    @Override
    public StatusInfo deleteState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StateInfo state = this.stateMap.get(stateKey);
        if (state == null) {
            throw new DoesNotExistException( stateKey + " does not exist");
        }

        this.stateMap.remove(stateKey);
        this.lifeCycleStatesMap.get(state.getLifecycleKey()).remove(stateKey);

        return new StatusInfo();
    }

    @Override
    public StatusInfo addInitialStateToLifecycle(@WebParam(name = "initialStateKey") String initialStateKey, @WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StateInfo stateKey = this.stateMap.get(initialStateKey);
        if (stateKey == null) {
            throw new DoesNotExistException(initialStateKey + " does not exist");
        }

        this.initialStatesMap.get(lifecycleKey).add(initialStateKey);

        return new StatusInfo();
    }

    @Override
    public StatusInfo removeInitialStateFromLifecycle(@WebParam(name = "initialStateKey") String initialStateKey, @WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StateInfo stateKey = this.stateMap.get(initialStateKey);
        if (stateKey == null) {
            throw new DoesNotExistException(initialStateKey + " does not exist");
        }

        this.initialStatesMap.remove(lifecycleKey);

        return new StatusInfo();
    }

    @Override
    public StateChangeInfo getStateChange(String stateChangeId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.stateChangeMap.containsKey(stateChangeId)) {
            throw new DoesNotExistException(stateChangeId);
        }
        return new StateChangeInfo(this.stateChangeMap.get (stateChangeId));
    }

    @Override
    public List<StateChangeInfo> getStateChangesByIds(List<String> stateChangeIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<StateChangeInfo> list = new ArrayList<StateChangeInfo> ();
        for (String id: stateChangeIds) {
            list.add (this.getStateChange(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStateChangeIdsByType(String stateChangeTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (StateChangeInfo info: stateChangeMap.values ()) {
            if (stateChangeTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromState(String fromStateKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<StateChangeInfo> list = new ArrayList<StateChangeInfo> ();
        for (StateChangeInfo info: stateChangeMap.values ()) {
            if (fromStateKey.equals(info.getFromStateKey())) {
                list.add (info);
            }
        }
        return list;
    }

    @Override
    public List<StateChangeInfo> getStateChangesByToState(String toStateKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<StateChangeInfo> list = new ArrayList<StateChangeInfo> ();
        for (StateChangeInfo info: stateChangeMap.values ()) {
            if (toStateKey.equals(info.getToStateKey())) {
                list.add (info);
            }
        }
        return list;
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromStateAndToState(String fromStateKey, String toStateKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<StateChangeInfo> list = new ArrayList<StateChangeInfo> ();
        for (StateChangeInfo info: stateChangeMap.values ()) {
            if (fromStateKey.equals(info.getFromStateKey())) {
                if (toStateKey.equals(info.getToStateKey())) {
                    list.add (info);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForStateChangeIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForStateChangeIds has not been implemented");
    }

    @Override
    public List<StateChangeInfo> searchForStateChanges(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForStateChanges has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStateChange(String validationTypeKey, String toStateKey, String fromStateKey, String stateChangeTypeKey, StateChangeInfo stateChangeInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public StateChangeInfo createStateChange(String toStateKey, String fromStateKey, String stateChangeTypeKey, StateChangeInfo stateChangeInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!stateChangeTypeKey.equals (stateChangeInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StateChangeInfo copy = new StateChangeInfo(stateChangeInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        stateChangeMap.put(copy.getId(), copy);
        return new StateChangeInfo(copy);
    }

    @Override
    public StateChangeInfo updateStateChange(String stateChangeId, StateChangeInfo stateChangeInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!stateChangeId.equals (stateChangeInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        StateChangeInfo copy = new StateChangeInfo(stateChangeInfo);
        StateChangeInfo old = this.getStateChange(stateChangeInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.stateChangeMap .put(stateChangeInfo.getId(), copy);
        return new StateChangeInfo(copy);
    }

    @Override
    public StatusInfo deleteStateChange(String stateChangeId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.stateChangeMap.remove(stateChangeId) == null) {
            throw new DoesNotExistException(stateChangeId);
        }
        return newStatus();
    }

    @Override
    public StateConstraintInfo getStateConstraint(String stateConstraintId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.stateConstraintMap.containsKey(stateConstraintId)) {
            throw new DoesNotExistException(stateConstraintId);
        }
        return new StateConstraintInfo(this.stateConstraintMap.get (stateConstraintId));
    }

    @Override
    public List<StateConstraintInfo> getStateConstraintsByIds(List<String> stateConstraintIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<StateConstraintInfo> list = new ArrayList<StateConstraintInfo> ();
        for (String id: stateConstraintIds) {
            list.add (this.getStateConstraint(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStateConstraintIdsByType(String stateConstraintTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (StateConstraintInfo info: stateConstraintMap.values ()) {
            if (stateConstraintTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForStateConstraintIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForStateConstraintIds has not been implemented");
    }

    @Override
    public List<StateConstraintInfo> searchForStateConstraints(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForStateConstraints has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStateConstraint(String validationTypeKey, String stateConstraintTypeKey, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public StateConstraintInfo createStateConstraint(String stateConstraintTypeKey, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!stateConstraintTypeKey.equals (stateConstraintInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        StateConstraintInfo copy = new StateConstraintInfo(stateConstraintInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        stateConstraintMap.put(copy.getId(), copy);
        return new StateConstraintInfo(copy);
    }

    @Override
    public StateConstraintInfo updateStateConstraint(String stateConstraintId, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!stateConstraintId.equals (stateConstraintInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        StateConstraintInfo copy = new StateConstraintInfo(stateConstraintInfo);
        StateConstraintInfo old = this.getStateConstraint(stateConstraintInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.stateConstraintMap .put(stateConstraintInfo.getId(), copy);
        return new StateConstraintInfo(copy);
    }

    @Override
    public StatusInfo deleteStateConstraint(String stateConstraintId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.stateConstraintMap.remove(stateConstraintId) == null) {
            throw new DoesNotExistException(stateConstraintId);
        }
        return newStatus();
    }

    @Override
    public StatePropagationInfo getStatePropagation(String statePropagationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.statePropagationMap.containsKey(statePropagationId)) {
            throw new DoesNotExistException(statePropagationId);
        }
        return new StatePropagationInfo(this.statePropagationMap.get (statePropagationId));
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByIds(List<String> statePropagationIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<StatePropagationInfo> list = new ArrayList<StatePropagationInfo> ();
        for (String id: statePropagationIds) {
            list.add (this.getStatePropagation(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStatePropagationIdsByType(String statePropagationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (StatePropagationInfo info: statePropagationMap.values ()) {
            if (statePropagationTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByTargetState(String targetStateId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<StatePropagationInfo> list = new ArrayList<StatePropagationInfo> ();
        for (StatePropagationInfo info: statePropagationMap.values ()) {
            if (targetStateId.equals(info.getTargetStateChangeId())) {
                list.add (info);
            }
        }
        return list;
    }

    @Override
    public List<String> searchForStatePropagationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForStatePropagationIds has not been implemented");
    }

    @Override
    public List<StatePropagationInfo> searchForStatePropagations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForStatePropagations has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStatePropagation(String validationTypeKey, String targetStateChangeId, String statePropagationTypeKey, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public StatePropagationInfo createStatePropagation(String targetStateChangeId, String statePropagationTypeKey, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!statePropagationTypeKey.equals (statePropagationInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StatePropagationInfo copy = new StatePropagationInfo(statePropagationInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        statePropagationMap.put(copy.getId(), copy);
        return new StatePropagationInfo(copy);
    }

    @Override
    public StatePropagationInfo updateStatePropagation(String statePropagationId, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!statePropagationId.equals (statePropagationInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        StatePropagationInfo copy = new StatePropagationInfo(statePropagationInfo);
        StatePropagationInfo old = this.getStatePropagation(statePropagationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.statePropagationMap .put(statePropagationInfo.getId(), copy);
        return new StatePropagationInfo(copy);
    }

    @Override
    public StatusInfo deleteStatePropagation(String statePropagationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.statePropagationMap.remove(statePropagationId) == null) {
            throw new DoesNotExistException(statePropagationId);
        }
        return newStatus();
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
    
}

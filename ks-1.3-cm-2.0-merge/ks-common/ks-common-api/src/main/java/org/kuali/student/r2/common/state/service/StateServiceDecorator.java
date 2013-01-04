/*
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
package org.kuali.student.r2.common.state.service;

import java.util.List;

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

import org.kuali.student.r2.common.state.dto.LifecycleInfo;
import org.kuali.student.r2.common.state.dto.StateInfo;

/**
 *
 * @author nwright
 */
public class StateServiceDecorator implements StateService {
    
    private StateService nextDecorator;

    public StateService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(StateService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLifecycle(lifecycleKey, contextInfo);
    }
   
    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(List<String> lifecycleKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLifecyclesByKeys(lifecycleKeys, contextInfo);
    }

    @Override
    public List<String> getLifecycleKeysByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLifecycleKeysByRefObjectUri(refObjectUri, contextInfo);
    }
    
    @Override
    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLifecycleKeys(criteria, contextInfo);
    }

    @Override
    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLifecycles(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLifecycle(String validationTypeKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLifecycle(validationTypeKey, lifecycleInfo, contextInfo);
    }

    @Override
    public LifecycleInfo createLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLifecycle(lifecycleKey, lifecycleInfo, contextInfo);
    }

    @Override
    public LifecycleInfo updateLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateLifecycle(lifecycleKey, lifecycleInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public StateInfo getState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getState(stateKey, contextInfo);
    }

    @Override
    public List<StateInfo> getStatesByKeys(List<String> stateKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getStatesByKeys(stateKeys, contextInfo);
    }

    @Override
    public List<StateInfo> getStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getStatesByLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForStateKeys(criteria, contextInfo);
    }

    @Override
    public List<StateInfo> searchForStates(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForStates(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateState(String validationTypeKey, String lifecycleKey, StateInfo stateInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateState(validationTypeKey, lifecycleKey, stateInfo, contextInfo);
    }

    @Override
    public StateInfo createState(String lifecycleKey, String stateKey, StateInfo stateInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createState(lifecycleKey, stateKey, stateInfo, contextInfo);
    }

    @Override
    public StateInfo updateState(String stateKey, StateInfo stateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateState(stateKey, stateInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteState(stateKey, contextInfo);
    }
}
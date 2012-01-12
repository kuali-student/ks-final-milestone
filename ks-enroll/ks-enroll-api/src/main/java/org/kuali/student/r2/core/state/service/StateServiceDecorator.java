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
package org.kuali.student.r2.core.state.service;

import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;

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
    public List<StateInfo> getStatesForLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getStatesForLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public StateInfo getState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getState(stateKey, contextInfo);
    }

    @Override
    public StateInfo getNextHappyState(String lifecycleKey, String currentStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getNextHappyState(lifecycleKey, currentStateKey, contextInfo);
    }

    @Override
    public List<String> getLifecyclesByObjectType(String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getLifecyclesByObjectType(refObjectUri, contextInfo);
    }

    @Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public List<StateInfo> getInitialValidStates(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getInitialValidStates(lifecycleKey, contextInfo);
    }
    
    
    
}

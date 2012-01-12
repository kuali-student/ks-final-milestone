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

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.StateServiceConstants;

/**
 * Provides a state management and state flow information.
 *
 * @version 1.0 (Dev)
 *
 * @author kamal
 */
@WebService(name = "StateService", targetNamespace = StateServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StateService {

      
    /**
     * Get Lifecycle Information by Key.
     *
     * @param lifecycleKey the lifecycle key
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the requested Lifecycle
     * @throws DoesNotExistException lifecycleKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LifecycleInfo getLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
   
    /**
     * This method retrieves the list of lifecycle keys associated
     * with a type of object.  TODO: consider changing the name of
     * this method to getLifecycleByRefObjectUri.
     * 
     * @param refObjectUri unique name for an object that states are
     *        attached
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return  a list of lifecycle keys
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectUri or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLifecyclesByObjectType(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns information about a state.
     * 
     * @param stateKey Key of the state
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the requested State
     * @throws DoesNotExistException stateKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException stateKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getState(@WebParam(name = "stateKey") String stateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of States that belong to a
     * lifecycle. For e.g Clu states for clu proposal lifecycle
     * 
     * @param lifecycleKey Key identifying the lifecycle
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the list of StateInfo objects associated with the lifecycle
     * @throws DoesNotExistException lifecycleKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getStatesForLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of StateInfo objects that are valid
     * initial states for a given lifecycle.
     *
     * Often there will be just a single initial valid state.
     *
     * @param lifecycleKey Lifecycle key 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the list of states are valid for the given lifecycle
     * @throws DoesNotExistException lifecycleKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getInitialValidStates(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * This method retrieves the next happy state in a lifecycle given
     * the current state.
     * 
     * @param lifecycleKey Lifecycle key 
     * @param currentStateKey Current state key 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the next happy state in the lifecycle 
     * @throws DoesNotExistException lifecycleKey or currentStateKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey, currentStateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getNextHappyState(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

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
import org.kuali.student.r2.common.util.constants.StateServiceConstants;

/**
 * Provides a read-only view of states and state flow information. 
 * 
 * This service needs to be implemented by any KS service that is going to handle states
 *
 * @version 1.0 (Dev)
 *
 * @author kamal
 */
@WebService(name = "StateService", targetNamespace = StateServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StateService {

      
    /**
     * Get Lifecycle Information by Key
     * @param lifecycleKey the lifecycle key
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return Information about State Lifecycle
     * @throws DoesNotExistException lifecycleKey not found
     * @throws InvalidParameterException invalid lifecycleKey
     * @throws MissingParameterException no lifecycle defined for that lifecycle Key
     * @throws OperationFailedException unable to complete request
     */
    public LifecycleInfo getLifecycleByKey(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
   
    /**
     * This method retrieves the list of lifecycle keys associated with a type of object.
     * TODO: consider changing the name of this method to getLifecycleByRefObjectUri
     * 
     * @param refObjectUri unique name for an object that states are attached
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return List of lifecycle keys
     * @throws DoesNotExistException typeKey not found
     * @throws InvalidParameterException invalid typeKey
     * @throws MissingParameterException missing typeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getLifecycleByObjectType(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     *  
     * This method returns information about a state for a given lifecycle. State keys can be reused and state key along with lifecycle key uniquely identifies the state instance within a lifecycle. 
     * 
     * @param lifecycleKey Key identifying the lifecycle
     * @param stateKey Key of the state
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return Information about the state
     * @throws DoesNotExistException  lifecycleKey, stateKey not found
     * @throws InvalidParameterException invalid lifecycleKey, stateKey
     * @throws MissingParameterException missing lifecycleKey, stateKey
     * @throws OperationFailedException unable to complete request
     */
    public StateInfo getState(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of States that belong to a lifecycle. For e.g Clu states for clu proposal lifecycle
     * 
     * @param lifecycleKey Key identifying the lifecycle
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return List of StateInfo objects associated with the lifecycle
     * @throws DoesNotExistException lifecycleKey not found
     * @throws InvalidParameterException invalid lifecycleKey
     * @throws MissingParameterException missing lifecycleKey
     * @throws OperationFailedException unable to complete request
     */
    public List<StateInfo> getStatesByLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of StateInfo objects that are valid initial states for a given lifecycle.
     *
     * Often there will be just a single initial valid state.
     *
     * ? if more than one does the order matter? i.e. the 1st one returned should be the default but others still allowed?
     * 
     * @param lifecycleKey Lifecycle key 
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return list of states are valid for the given lifecycle
     * @throws DoesNotExistException lifecycleKey not found
     * @throws InvalidParameterException invalid lifecycleKey
     * @throws MissingParameterException missing lifecycleKey
     * @throws OperationFailedException unable to complete request
     */
    public List<StateInfo> getInitialValidStates(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    
    /**
     * 
     * This method retrieves the next happy state in a lifecycle given the current state.
     * 
     * @param lifecycleKey Lifecycle key 
     * @param currentStateKey Current state key 
     * @param context  Context information containing the principalId and locale information about the caller of service operation
     * @return Next happy state in the lifecycle 
     * @throws DoesNotExistException lifecycleKey or currentStateKey not found
     * @throws InvalidParameterException invalid lifecycleKey or currentStateKey
     * @throws MissingParameterException missing lifecycleKey or currentStateKey
     * @throws OperationFailedException unable to complete request
     */
    public StateInfo getNextHappyState(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}

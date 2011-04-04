/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StateInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;

/**
 * Provides a read-only view of states and state flow information. 
 * 
 * This service needs to be implemented by any KS service that is going to handle states
 * 
 * @author kamal
 */
@WebService(name = "StateService", targetNamespace = "http://student.kuali.org/wsdl/state")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StateService {

   
    /**
     * 
     * This method retrieves the list of process keys associated with a type of object.
     * 
     * @param typeKey Type key
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return List of process keys
     * @throws DoesNotExistException typeKey not found
     * @throws InvalidParameterException invalid typeKey
     * @throws MissingParameterException missing typeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getProcessKeys(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     *  
     * This method returns information about a state for a given process. State keys can be reused and state key along with process key uniquely identifies the state instance within a process. 
     * 
     * @param processKey Key identifying the process
     * @param stateKey Key of the state
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return Information about the state
     * @throws DoesNotExistException  processKey, stateKey not found
     * @throws InvalidParameterException invalid processKey, stateKey
     * @throws MissingParameterException missing processKey, stateKey
     * @throws OperationFailedException unable to complete request
     */
    public StateInfo getState(@WebParam(name = "processKey") String processKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of States that belong to a process. For e.g Clu states for clu proposal process
     * 
     * @param processKey Key identifying the process
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return List of StateInfo objects associated with the process
     * @throws DoesNotExistException processKey not found
     * @throws InvalidParameterException invalid processKey
     * @throws MissingParameterException missing processKey
     * @throws OperationFailedException unable to complete request
     */
    public List<StateInfo> getStatesByProcess(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of StateInfo objects that are valid initial states for a given process.
     *
     * Often there will be just a single initial valid state.
     *
     * ? if more than one does the order matter? i.e. the 1st one returned should be the default but others still allowed?
     * 
     * @param processKey Process key 
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return list of states are valid for the given process
     * @throws DoesNotExistException processKey not found
     * @throws InvalidParameterException invalid processKey
     * @throws MissingParameterException missing processKey
     * @throws OperationFailedException unable to complete request
     */
    public List<StateInfo> getInitialValidStates(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    
    /**
     * 
     * This method retrieves the next happy state in a process given the current state.
     * 
     * @param processKey Process key 
     * @param currentStateKey Current state key 
     * @param context  Context information containing the principalId and locale information about the caller of service operation
     * @return Next happy state in the process 
     * @throws DoesNotExistException processKey or currentStateKey not found
     * @throws InvalidParameterException invalid processKey or currentStateKey
     * @throws MissingParameterException missing processKey or currentStateKey
     * @throws OperationFailedException unable to complete request
     */
    public StateInfo getNextHappyState(@WebParam(name = "processKey") String processKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}

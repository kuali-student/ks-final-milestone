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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
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

import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
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
      
    //
    // Lookup Methods for Lifecycle Key Entity Pattern.
    //

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
     * Retrieves a list of Lifecycles from a list of Lifecycle
     * keys. The returned list may be in any order and if duplicate
     * keys are supplied, a unique set may or may not be returned.
     * 
     * @param lifecycleKeys a list of Lifecycle keys
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Lifecycles
     * @throws DoesNotExistException a lifecycleId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKeys, a key in
     *         lifecycleKeys, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LifecycleInfo> getLifecyclesByKeys(@WebParam(name = "lifecycleKeys") List<String> lifecycleKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Lifecycle keys by Lifecycle Type.
     * 
     * @param lifecycleTypeKey an identifier for the Lifecycle
     *        type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Lifecycle keys matching lifecycleTypeKey
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException lifecycleTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLifecycleKeysByType(@WebParam(name = "lifecycleTypeKey") String lifecycleTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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

    //
    // Search methods for Lifecycle Key Entity Pattern.
    //

    /**
     * Searches for Lifecycle keys that meet the given search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Lifecycle identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForLifecycleKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Lifecycles that meet the given search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Lifecycles matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LifecycleInfo> searchForLifecycles(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // CRUD methods for Lifecycle Key Entity Pattern.
    //

    /**
     * Validates a Lifecycle. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this Lifecycle. If an
     * identifier is present for the Lifecycle (and/or one of its contained
     * sub-objects) and a record is found for that identifier, the
     * validation checks if the Lifecycle can be shifted to the new
     * values. If a an identifier is not present or a record does not
     * exist, the validation checks if the Lifecycle with the given data can
     * be created.
     * 
     * @param validationTypeKey the identifier for the validation Type
     * @param lifecycleTypeKey the identifier for the Lifecycle Type to be validated
     * @param lifecycleInfo the identifier for the Lifecycle to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException validationTypeKey or lifecycleTypeKey
     *         is not found
     * @throws InvalidParameterException lifecycleInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, lifecycleTypeKey
     *         lifecycleInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLifecycle(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "lifecycleTypeKey") String lifecycleTypeKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Lifecycle. The Lifecycle Type and Meta
     * information may not be set in the supplied data object.
     * 
     * @param lifecycleKey a unique for the new Lifecycle
     * @param lifecycleInfo the data with which to create the Lifecycle
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new Lifecycle
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lifecycleTypeKey does not exist or is
     *         not supported
     * @throws InvalidParameterException lifecycleInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException lifecycleKey,
     *         lifecycleTypeKey, lifecycleInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public LifecycleInfo createLifecycle(@WebParam(name = "lifecycleKey") String lifeycleKey, @WebParam(name = "lifecycleTypeKey") String lifecycleTypeKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Lifecycle. The Lifecycle Key, Type, and
     * Meta information may not be changed.
     * 
     * @param lifecycleKey the identifier for the Lifecycle to be updated
     * @param lifecycleInfo the new data for the Lifecycle
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated Lifecycle
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lifecycleKey is not found
     * @throws InvalidParameterException lifecycleInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException lifecycleKey, lifecycleInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public LifecycleInfo updateLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Lifecycle.
     * 
     * @param lifecycleKey the identifier for the Lifecycle to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException lifecycleKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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

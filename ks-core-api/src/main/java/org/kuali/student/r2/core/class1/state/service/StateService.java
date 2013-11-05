/*
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.r2.core.class1.state.service;

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
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.constants.StateServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;


/**
 * Provides a state management and state flow information.
 *
 * @version 2.0
 * @author kamal
 * @Author Sri komandur@uw.edu
 */

@WebService(name = "StateService", serviceName = "StateService", portName = "StateService", targetNamespace = StateServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StateService {

    //
    // Lookup Methods for Lifecycle Key Entity Pattern.
    //

    /**
     * Get Lifecycle Information by Key.
     *
     * @param lifecycleKey the lifecycle key
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return the requested Lifecycle
     * @throws DoesNotExistException     lifecycleKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LifecycleInfo getLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Lifecycles from a list of Lifecycle keys. The
     * returned list may be in any order and if duplicate keys are supplied, a
     * unique set may or may not be returned.
     *
     * @param lifecycleKeys a list of Lifecycle keys
     * @param contextInfo   information containing the principalId and locale
     *                      information about the caller of service operation
     * @return a list of Lifecycles
     * @throws DoesNotExistException     a lifecycleKey in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKeys, a key in lifecycleKeys,
     *                                   or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LifecycleInfo> getLifecyclesByKeys(@WebParam(name = "lifecycleKeys") List<String> lifecycleKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method retrieves the list of Lifecycle keys associated with a type
     * of object.
     *
     * @param refObjectUri unique name for an object that states are attached
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return a list of lifecycle keys
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectUri or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLifecycleKeysByRefObjectUri(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Search methods for Lifecycle Key Entity Pattern.
    //

    /**
     * Searches for Lifecycle keys that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Lifecycle identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForLifecycleKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Lifecycles that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Lifecycles matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LifecycleInfo> searchForLifecycles(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // CRUD methods for Lifecycle Key Entity Pattern.
    //

    /**
     * Validates a Lifecycle. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained sub-objects or expanded to perform all tests related
     * to this Lifecycle. If an identifier is present for the Lifecycle (and/or
     * one of its contained sub-objects) and a record is found for that
     * identifier, the validation checks if the Lifecycle can be shifted to the
     * new values. If a an identifier is not present or a record does not exist,
     * the validation checks if the Lifecycle with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param lifecycleInfo     the identifier for the Lifecycle to be
     *                          validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey is not found
     * @throws InvalidParameterException lifecycleInfo or contextInfo is not
     *                                   valid
     * @throws MissingParameterException validationTypeKey, lifecycleInfo, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLifecycle(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Lifecycle. The Lifecycle Type and Meta information may not
     * be set in the supplied data object.
     *
     * @param lifecycleKey  a unique for the new Lifecycle
     * @param lifecycleInfo the data with which to create the Lifecycle
     * @param contextInfo   information containing the principalId and locale
     *                      information about the caller of service operation
     * @return the new Lifecycle
     * @throws AlreadyExistsException       lifecycleKey already exists
     * @throws DataValidationErrorException supplied data is invalid
     * @throws InvalidParameterException    lifecycleInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    lifecycleKey, lifecycleInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public LifecycleInfo createLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Lifecycle. The Lifecycle Key, Type, and Meta
     * information may not be changed.
     *
     * @param lifecycleKey  the identifier for the Lifecycle to be updated
     * @param lifecycleInfo the new data for the Lifecycle
     * @param contextInfo   information containing the principalId and locale
     *                      information about the caller of service operation
     * @return the updated Lifecycle
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        lifecycleKey is not found
     * @throws InvalidParameterException    lifecycleInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    lifecycleKey, lifecycleInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public LifecycleInfo updateLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Lifecycle.
     *
     * @param lifecycleKey the identifier for the Lifecycle to be deleted
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     lifecycleKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Lookup Methods for States. States are not full entities because
    // they do not have Types and States.
    //

    /**
     * This method returns information about a state.
     *
     * @param stateKey    Key of the state
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the requested State
     * @throws DoesNotExistException     stateKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException stateKey or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getState(@WebParam(name = "stateKey") String stateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of States from a list of State keys. The returned list
     * may be in any order and if duplicate keys are supplied, a unique set may
     * or may not be returned.
     *
     * @param stateKeys   a list of State keys
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of States
     * @throws DoesNotExistException     a stateId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException stateKeys, a key in stateKeys, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getStatesByKeys(@WebParam(name = "stateKeys") List<String> stateKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of States that belong to a lifecycle. For e.g
     * Clu states for clu proposal lifecycle.
     *
     * @param lifecycleKey Key identifying the lifecycle
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return the list of StateInfo objects associated with the lifecycle
     * @throws DoesNotExistException     lifecycleKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getStatesByLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of initial valid States that belong to a lifecycle. For e.g
     * 'kuali.lui.activity.offering.state.draft' is the initial state of Activity Offering
     *
     * @param lifecycleKey Key identifying the lifecycle
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return the list of state keys identifying initial states of the lifecycle
     * @throws DoesNotExistException     lifecycleKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lifecycleKey is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getInitialStatesByLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Search methods for State Key Pattern.
    //

    /**
     * Searches for State keys that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of State identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStateKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for States that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of States matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> searchForStates(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // CRUD methods for Dependent Key Pattern. States do not have
    // Types or States so they are not complete entities.
    //

    /**
     * Validates a State. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained sub-objects or expanded to perform all tests related
     * to this State. If an identifier is present for the State (and/or one of
     * its contained sub-objects) and a record is found for that identifier, the
     * validation checks if the State can be shifted to the new values. If a an
     * identifier is not present or a record does not exist, the validation
     * checks if the State with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param lifecycleKey      the identifier for the Lifecycle to which the
     *                          State belongs
     * @param stateInfo         the identifier for the State to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey or lifecycleKey is
     *                                   not found
     * @throws InvalidParameterException stateInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, lifecycleKey
     *                                   stateInfo, or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateState(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "stateInfo") StateInfo stateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new State. The State key and Meta information may not be set in
     * the supplied data object.
     *
     * @param lifecycleKey the identifier for the Lifecycle to which the State
     *                     belongs
     * @param stateKey     a unique identifier for the new State
     * @param stateInfo    the data with which to create the State
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return the new State
     * @throws AlreadyExistsException       stateKey already exists
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        lifecycleKey is not found
     * @throws InvalidParameterException    stateInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    lifecycleKey, stateKey, stateInfo,
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public StateInfo createState(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "stateInfo") StateInfo stateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing State. The State key and Meta information may not be
     * changed.
     *
     * @param stateKey    the identifier for the State to be updated
     * @param stateInfo   the new data for the State
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the updated State
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        stateKey is not found
     * @throws InvalidParameterException    stateInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    stateKey, stateInfo, or contextInfo
     *                                      is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public StateInfo updateState(@WebParam(name = "stateKey") String stateKey, @WebParam(name = "stateInfo") StateInfo stateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing State.
     *
     * @param stateKey    the identifier for the State to be deleted
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     stateKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException stateKey or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteState(@WebParam(name = "stateKey") String stateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Add an initial state to a lifecycle
     *
     * @param initialStateKey       unique key of the state
     * @param lifecycleKey         unique key of the lifecycle to which the
     *                             initial state is added
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status              status of the operation (success, failed)
     * @throws AlreadyExistsException    initialStateKey already related to
     *                                   activityOfferingId
     * @throws DoesNotExistException     initialStateKey or lifecycleKey
     *                                   not found
     * @throws InvalidParameterException invalid initialStateKey,
     *                                   lifecycleKey, or contextInfo
     * @throws MissingParameterException missing initialStateKey,
     *                                   lifecycleKey, or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addInitialStateToLifecycle(@WebParam(name = "initialStateKey") String initialStateKey,
                                                              @WebParam(name = "lifecycleKey") String lifecycleKey,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Remove an initial state from a lifecycle.
     *
     * @param initialStateKey       unique key of the state
     * @param lifecycleKey         unique key of the lifecycle to which the
     *                             initial state is added
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status
     * @throws DoesNotExistException     initialStateKey or lifecycleKey
     *                                   not found
     * @throws InvalidParameterException invalid initialStateKey,
     *                                   lifecycleKey, or contextInfo
     * @throws MissingParameterException missing initialStateKey,
     *                                   lifecycleKey, or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeInitialStateFromLifecycle(@WebParam(name = "initialStateKey") String initialStateKey,
                                                                   @WebParam(name = "lifecycleKey") String lifecycleKey,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the StateChange
     *
     * @param stateChangeId a unique Id of a StateChange
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return an StateChange
     * @throws DoesNotExistException     stateChangeId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateChangeId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateChangeInfo getStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of StateChanges corresponding to the given list of
     * StateChange Ids.
     *
     * @param stateChangeIds list of StateChanges to be retrieved
     * @param contextInfo    Context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return a list of StateChanges
     * @throws DoesNotExistException     a stateChangeId in list not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateChangeId or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateChangeInfo> getStateChangesByIds(@WebParam(name = "stateChangeIds") List<String> stateChangeIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of StateChange Ids by StateChange Type.
     *
     * @param stateChangeTypeKey an identifier for an StateChange Type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of StateChange identifiers matching stateChangeTypeKey or
     *         an empty list if none found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateChangeTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getStateChangeIdsByType(@WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all StateChanges from the given state
     *
     * @param fromStateKey the identifier for the "from" state
     * @param contextInfo  Context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return a list of StateChanges from the given state or an empty list if
     *         none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException fromStateKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateChangeInfo> getStateChangesByFromState(@WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all StateChanges leading to the given state
     *
     * @param toStateKey  the identifier for the target state
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of StateChanges to the target state or an empty list if
     *         none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException toStateKey or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateChangeInfo> getStateChangesByToState(@WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all StateChanges from the source state to the target state
     *
     * @param fromStateKey the identifier for the "from" state
     * @param toStateKey   the identifier for the target state
     * @param contextInfo  Context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return a list of StateChanges from the source to the target states or an
     *         empty list if none found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException fromStateKey, toStateKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateChangeInfo> getStateChangesByFromStateAndToState(@WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for StateChanges based on the criteria and returns a list of
     * StateChange identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of StateChange Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStateChangeIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for StateChanges based on the criteria and returns a list of
     * StateChanges which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of StateChange information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateChangeInfo> searchForStateChanges(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an StateChange. Depending on the value of validationType, this
     * validation could be limited to tests on just the current StateChange and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this StateChange. If an identifier is present for the
     * StateChange (and/or one of its contained sub-objects) and a record is
     * found for that identifier, the validation checks if the StateChange can
     * be updated to the new values. If an identifier is not present or a record
     * does not exist, the validation checks if the object with the given data
     * can be created.
     *
     * @param validationTypeKey  the identifier for the validation Type
     * @param toStateKey         "to" state
     * @param fromStateKey       "from" state
     * @param stateChangeTypeKey stateChange type
     * @param stateChangeInfo    detailed information about the stateChange
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey, toStateKey,
     *                                   fromStateKey or stateChangeTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid stateChangeInfo or contextInfo
     * @throws MissingParameterException validationTypeKey, toStateKey,
     *                                   fromStateKey, stateChangeTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateStateChange(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a StateChange
     *
     * @param toStateKey         "to" state
     * @param fromStateKey       "from" state
     * @param stateChangeTypeKey stateChange type
     * @param stateChangeInfo    detailed information about the stateChange
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return detailed information about the stateChange
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        fromStateKey or stateChangeTypeKey
     *                                      does not exist
     * @throws InvalidParameterException    invalid stateChangeInfo or
     *                                      contextInfo
     * @throws MissingParameterException    toStateKey, fromStateKey,
     *                                      stateChangeTypeKey or contextInfo is
     *                                      missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public StateChangeInfo createStateChange(@WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a relationship between a person and their stateChange.
     *
     * @param stateChangeId   identifier of the stateChange relationship to be
     *                        updated
     * @param stateChangeInfo information about the object stateChangeInfo to be
     *                        updated
     * @param contextInfo     context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return updated stateChange relationship information
     * @throws DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        stateChangeId not found
     * @throws InvalidParameterException    invalid stateChangeInfo or
     *                                      contextInfo
     * @throws MissingParameterException    stateChangeId, stateChangeInfo or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public StateChangeInfo updateStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Removes stateChange relationship between source and target states
     *
     * @param stateChangeId object StateChange relationship identifier
     * @param contextInfo   context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     stateChangeId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateChangeId or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteStateChange(@WebParam(name = "stateChangeId") String stateChangeId,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the StateConstraint
     *
     * @param stateConstraintId a unique Id of a StateConstraint
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return an StateConstraint
     * @throws DoesNotExistException     stateConstraintId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateConstraintId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateConstraintInfo getStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of StateConstraints corresponding to the given list of
     * StateConstraint Ids.
     *
     * @param stateConstraintIds list of constraints to be retrieved
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of StateConstraints
     * @throws DoesNotExistException     an stateConstraintId in list not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateConstraintId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateConstraintInfo> getStateConstraintsByIds(@WebParam(name = "stateConstraintIds") List<String> stateConstraintIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of StateConstraint Ids by StateConstraint Type.
     *
     * @param stateConstraintTypeKey an identifier for an StateConstraint Type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of StateConstraint identifiers matching stateConstraintTypeKey or
     *         an empty list if none found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateConstraintTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getStateConstraintIdsByType(@WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for StateConstraints based on the criteria and returns a list of
     * StateConstraint identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of StateConstraint Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStateConstraintIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for StateConstraints based on the criteria and returns a list of
     * StateConstraints which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of StateConstraint information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateConstraintInfo> searchForStateConstraints(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate an StateConstraint
     *
     * @param validationTypeKey      the identifier for the validation Type
     * @param stateConstraintTypeKey the type of the StateConstraint
     * @param stateConstraintInfo    detailed information about the
     *                               StateConstraint
     * @param contextInfo            context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey or
     *                                   stateConstraintTypeKey does not exist
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException validationTypeKey,
     *                                   stateConstraintTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateStateConstraint(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a StateConstraint
     *
     * @param stateConstraintTypeKey the identifier of the constraint type
     * @param stateConstraintInfo    detailed information about the
     *                               StateConstraint
     * @param contextInfo            context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return created StateConstraint
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        stateConstraintTypeKey
     *                                      does not exist
     * @throws InvalidParameterException    invalid contextInfo
     * @throws MissingParameterException    stateConstraintTypeKey
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @impl Some examples of constraint types are: 'precondition',
     * 'propagation'
     */
    public StateConstraintInfo createStateConstraint(@WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;


    /**
     * Updates a StateConstraint
     *
     * @param stateConstraintId   identifier of the stateConstraint to be
     *                            updated
     * @param stateConstraintInfo information about the object stateConstraint
     *                            to be updated
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return updated StateConstraint information
     * @throws DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        stateConstraintId not found
     * @throws InvalidParameterException    invalid stateConstraintInfo or
     *                                      contextInfo
     * @throws MissingParameterException    stateConstraintId, stateConstraintInfo
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public StateConstraintInfo updateStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Delete a StateConstraint
     *
     * @param stateConstraintId the identifier for the StateConstraint
     * @param contextInfo       context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     stateConstraintId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException stateConstraintId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a StatePropagation
     *
     * @param statePropagationId a unique Id of an StatePropagation
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a StatePropagation
     * @throws DoesNotExistException     statePropagationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException statePropagationId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatePropagationInfo getStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of StatePropagations corresponding to the given list of
     * StatePropagation Ids.
     *
     * @param statePropagationIds list of propagations to be retrieved
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return a list of StatePropagations
     * @throws DoesNotExistException     an statePropagationId in list not
     *                                   found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException statePropagationId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StatePropagationInfo> getStatePropagationsByIds(@WebParam(name = "statePropagationIds") List<String> statePropagationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of StatePropagation Ids by StatePropagation Type.
     *
     * @param statePropagationTypeKey an identifier for an StatePropagation Type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of StatePropagation identifiers matching statePropagationTypeKey or
     *         an empty list if none found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException statePropagationTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getStatePropagationIdsByType(@WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all StatePropagations to the given target state
     *
     * @param targetStateId the identifier for the target state
     * @param contextInfo    Context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return StatePropagations to the target state or an empty list if none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException targetStateId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StatePropagationInfo> getStatePropagationsByTargetState(@WebParam(name = "targetStateId") String targetStateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for StatePropagations based on the criteria and returns a list
     * of StatePropagation identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of StatePropagation Ids
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStatePropagationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for StatePropagations based on the criteria and returns a list
     * of StatePropagations which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of StatePropagation information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StatePropagationInfo> searchForStatePropagations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate a StatePropagation
     *
     * @param validationTypeKey       the identifier for the validation Type
     * @param targetStateChangeId     the identifier of the target StateChange
     *                                for this propagation
     * @param statePropagationTypeKey the type of the StatePropagation
     * @param statePropagationInfo    detailed information about the
     *                                StatePropagation
     * @param contextInfo             context information containing the
     *                                principalId and locale information about
     *                                the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey, targetStateChangeId
     *                                   or statePropagationTypeKey does not
     *                                   exist
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException validationTypeKey, targetStateChangeId,
     *                                   statePropagationTypeKey, statePropagationInfo
     *                                   or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateStatePropagation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "targetStateChangeId") String targetStateChangeId, @WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a StatePropagation
     *
     * @param targetStateChangeId     the identifier of the target StateChange
     *                                for this propagation
     * @param statePropagationTypeKey the type of the StatePropagation
     * @param statePropagationInfo    detailed information about the
     *                                StatePropagation
     * @param contextInfo             context information containing the
     *                                principalId and locale information about
     *                                the caller of service operation
     * @return created StatePropagation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        targetStateChangeId or statePropagationTypeKey
     *                                      does not exist
     * @throws InvalidParameterException    invalid contextInfo
     * @throws MissingParameterException    targetStateChangeId, statePropagationTypeKey
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public StatePropagationInfo createStatePropagation(@WebParam(name = "targetStateChangeId") String targetStateChangeId, @WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a StatePropagation
     *
     * @param statePropagationId   identifier of the StatePropagation to be updated
     * @param statePropagationInfo information about the object StatePropagation
     *                             to be updated
     * @param contextInfo          context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return updated state propagation  information
     * @throws DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        statePropagationId not found
     * @throws InvalidParameterException    invalid statePropagationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    statePropagationId, statePropagationInfo
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public StatePropagationInfo updateStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Delete a StatePropagation
     *
     * @param statePropagationId the identifier for the StatePropagation
     * @param contextInfo        context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     statePropagationId or statePropagationId
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException statePropagationId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}

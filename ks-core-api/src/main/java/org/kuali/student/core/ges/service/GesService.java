/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.ges.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.core.ges.dto.ParameterGroupInfo;
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
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * This service supports the management of settings and default values.
 * These values are distinguished using a parameter and other optional
 * qualifiers (ATP typekey, population, organization, and program).
 *
 * @author Kuali Student Services
 */

@WebService(name = "GesService", serviceName = "GesService", portName = "GesService", targetNamespace = GesServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface GesService {

    //////////////////////////
    // Parameter
    //////////////////////////


    /**
     * Retrieves a single Parameter by Parameter Key.
     *
     * @param parameterKey the identifier for the parameter to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return the Parameter requested
     * @throws DoesNotExistException     parameterKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ParameterInfo getParameter(@WebParam(name = "parameterKey") String parameterKey,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Parameters from a list of
     * Parameter Keys. The returned list may be in any order and
     * if duplicates Keys are supplied, a unique set may or may not be
     * returned.
     *
     * @param parameterKeys a list of Parameter identifiers
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of the service operation
     * @return a list of Parameters
     * @throws DoesNotExistException     a parameterKey in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException parameterKeys, a Key in
     *                                   parameterKeys, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ParameterInfo> getParametersByKeys(@WebParam(name = "parameterKeys") List<String> parameterKeys,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Parameter Keys by Parameter Type.
     *
     * @param parameterTypeKey an identifier for a
     *                         Parameter Type
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of the service operation
     * @return a list of Parameter identifiers matching
     *         parameterTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getParameterKeysByType(@WebParam(name = "parameterTypeKey") String parameterTypeKey,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Parameter Keys by Parameter Group Key
     *
     * @param parameterGroupKey a Parameter Group Key
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of the service operation
     * @return a list of Parameter identifiers that are associated with a parameter group
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getParameterKeysForParameterGroup(@WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Searches for Parameters based on the criteria and returns
     * a list of Parameter keys which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Parameter Keys matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForParameterKeys(@WebParam(name = "criteria") QueryByCriteria criteria,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Parameters based on the criteria and returns
     * a list of Parameters which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Parameters matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ParameterInfo> searchForParameters(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Parameter. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current Parameter and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * Parameter. If an identifier is present for the
     * Parameter (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the Parameter can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param parameterTypeKey  the identifier for the parameter Type
     * @param parameterInfo     the Parameter information to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey, valueTypeKey, or
     *                                   parameterTypeKey is not found
     * @throws InvalidParameterException parameterInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, valueTypeKey, parameterTypeKey,
     *                                   parameterInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateParameter(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                        @WebParam(name = "parameterTypeKey") String parameterTypeKey,
                                                        @WebParam(name = "parameterInfo") ParameterInfo parameterInfo,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Parameter. The Parameter Key, Value Type, Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param parameterTypeKey the identifier for the Type of
     *                         the new Parameter
     * @param parameterKey     the key of the parameter - for example, 'max-credits'
     * @param parameterInfo    the data with which to create the
     *                         Parameter
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return the new Parameter
     * @throws DoesNotExistException     valueTypeKey or parameterTypeKey does not exist or is not
     *                                   supported
     * @throws InvalidParameterException parameterInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException valueTypeKey, parameterTypeKey, parameterKey, parameterInfo or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws DataValidationErrorException supplied data is invalid
     */
    public ParameterInfo createParameter(@WebParam(name = "parameterKey") String parameterKey,
                                         @WebParam(name = "parameterTypeKey") String parameterTypeKey,
                                         @WebParam(name = "parameterInfo") ParameterInfo parameterInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Parameter. The Parameter Key,
     * Type, and Meta information may not be changed.
     *
     * @param parameterKey   the key for the Parameter
     *                      to be updated
     * @param parameterInfo the new data for the Parameter
     * @param contextInfo   information containing the principalId and
     *                      locale information about the caller of service operation
     * @return the updated Parameter
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        parameterKey not found
     * @throws InvalidParameterException    parameterInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    parameterKey,
     *                                      parameterInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public ParameterInfo updateParameter(@WebParam(name = "parameterKey") String parameterKey,
                                         @WebParam(name = "parameterInfo") ParameterInfo parameterInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;


    /**
     * Deletes an existing Parameter.
     *
     * @param parameterKey the key for the Parameter
     *                    to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     parameterKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteParameter(@WebParam(name = "parameterKey") String parameterKey,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    //////////////////////////
    // Value
    //////////////////////////

    /**
     * Retrieves a single Value by Value Id.
     *
     * @param valueId     the identifier for the value to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return the Value requested
     * @throws DoesNotExistException     valueId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException valueId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ValueInfo getValue(@WebParam(name = "valueId") String valueId,
                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Values from a list of
     * Value Ids. The returned list may be in any order and
     * if duplicates Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param valueIds    a list of Value identifiers
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Values
     * @throws DoesNotExistException     a valueId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException valueIds, an Id in
     *                                   valueIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> getValuesByIds(@WebParam(name = "valueIds") List<String> valueIds,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Value Ids by Value Type.
     *
     * @param valueTypeKey an identifier for a
     *                     Value Type
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of the service operation
     * @return a list of Value identifiers matching
     *         valueTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException valueTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getValueIdsByType(@WebParam(name = "valueTypeKey") String valueTypeKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Values based on the criteria and returns
     * a list of Value identifiers which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Value Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForValueIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Values based on the criteria and returns
     * a list of Values which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Values matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> searchForValues(@WebParam(name = "criteria") QueryByCriteria criteria,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Value. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current Value and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * Value. If an identifier is present for the
     * Value (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the Value can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param valueTypeKey      the identifier for the value Type
     * @param parameterKey      the key of the parameter that this value is attached to.
     * @param valueInfo         the Value information to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey, parameterKey, or valueTypeKey is not found
     * @throws InvalidParameterException valueInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, parameterKey, valueTypeKey,
     *                                   valueInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateValue(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                    @WebParam(name = "valueTypeKey") String valueTypeKey,
                                                    @WebParam(name = "parameterKey") String parameterKey,
                                                    @WebParam(name = "valueInfo") ValueInfo valueInfo,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Value. The Value Id, Value Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param valueTypeKey the identifier for the Type of
     *                     the new Value
     * @param parameterKey  the key of the parameter that this value is attached to.
     * @param valueInfo    the data with which to create the
     *                     Value
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the new Value
     * @throws DoesNotExistException     valueTypeKey or parameterKey does not exist or is not
     *                                   supported
     * @throws InvalidParameterException valueInfo
     *                                   contextInfo is not valid
     * @throws MissingParameterException valueTypeKey, parameterKey, valueInfo or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws DataValidationErrorException supplied data is invalid
     */
    public ValueInfo createValue(@WebParam(name = "valueTypeKey") String valueTypeKey,
                                 @WebParam(name = "parameterKey") String parameterKey,
                                 @WebParam(name = "valueInfo") ValueInfo valueInfo,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Value. The Value Id,
     * Type, and Meta information may not be changed.
     *
     * @param valueId     the identifier for the Value
     *                    to be updated
     * @param valueInfo   the new data for the Value
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the updated Value
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        valueId not found
     * @throws InvalidParameterException    valueInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    valueId, valueInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public ValueInfo updateValue(@WebParam(name = "valueId") String valueId,
                                 @WebParam(name = "valueInfo") ValueInfo valueInfo,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;


    /**
     * Deletes an existing Value.
     *
     * @param valueId     the identifier for the Value
     *                    to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     valueId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException valueId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteValue(@WebParam(name = "valueId") String valueId,
                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a list of values associated with the given particular parameter
     *
     * @param parameterKey the key for the parameter associated with the values that will be returned.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return A list of values associated with the given parameter
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> getValuesByParameter(@WebParam(name = "parameterKey") String parameterKey,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of values associated with the given particular parameter keys.
     *
     * @param parameterKeys the list of keys for the parameters associated with the values that will be returned.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return A list of values associated with the given parameter keys
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> getValuesByParameters(@WebParam(name = "parameterKeys") List<String> parameterKeys,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException, DoesNotExistException;


    /**
     * Retrieves a list of values associated with the given particular parameter keys and retricted by the criteria.
     *
     * @param parameterKeys the list of keys for the parameters associated with the values that will be returned.
     * @param criteria    the criteria that restricts the values returned by this method.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return A list of values associated with the given parameter keys
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> getValuesByParameters(@WebParam(name = "parameterKeys") List<String> parameterKeys,
                                                 @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException, DoesNotExistException;

    /**
     * Retrieves a list of values associated with a particular parameter
     * that are applicable based on the evaluation of the given criteria.
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     *
     * The relevant values must also have rules that are either null or evaluate to true.
     *
     * @param parameterKey the key for the parameter associated with the values that will be returned.
     * @param criteria    the criteria that restricts the values returned by this method.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return  A List of applicable values, sorted. The key word is applicable. The values returned will be based
     *          primarily on priority. If there are values with duplicate priorities, the sort order may or
     *          may not be specified. For some evaluations, such as course based criteria, the atp id and the
     *          atp type will be looked at to return the values in the most applicable order. If two values are
     *          of equal 'applicability', they will be returned in an unspecified order.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKey, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValues(@WebParam(name = "parameterKey") String parameterKey,
                                          @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of values associated with a particular parameter
     * that are applicable based on the evaluation of the given criteria, and date.
     *
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     *
     * The relevant values must also have rules that are either null or evaluate to true.
     *
     * The date parameter is used as the date for the evaluation.
     *
     * @param parameterKey the key for the parameter associated with the values that will be returned.
     * @param criteria    the criteria that restricts the values returned by this method.
     * @param onDate the date that will be used for the evaluation.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return  A List of applicable values, sorted. The key word is applicable. The values returned will be based
     *          primarily on priority. If there are values with duplicate priorities, the sort order may or
     *          may not be specified. For some evaluations, such as course based criteria, the atp id and the
     *          atp type will be looked at to return the values in the most applicable order. If two values are
     *          of equal 'applicability', they will be returned in an unspecified order.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKey, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValuesOnDate(@WebParam(name = "parameterKey") String parameterKey,
                                                @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                                @WebParam(name = "onDate") Date onDate,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a value with the highest priority associated with a particular parameter
     * that is applicable based on the evaluation of the given criteria.
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     *
     * The relevant value must also have rules that are either null or evaluate to true.
     *
     * @param parameterKey the key for the parameter associated with the values that will be returned.
     * @param criteria    the criteria that restricts the values returned by this method.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The best matching valueInfo.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws DoesNotExistException     value does not exist.
     * @throws MissingParameterException parameterKey, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ValueInfo evaluateValue(@WebParam(name = "parameterKey") String parameterKey,
                                   @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            DoesNotExistException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a value with the highest priority associated with a particular parameter
     * that is applicable based on the evaluation of the given criteria, and date.
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     *
     * The relevant value must also have rules that are either null or evaluate to true.
     *
     * The date parameter is used as the date for the evaluation.
     *
     * @param parameterKey the key for the parameter associated with the values that will be returned.
     * @param criteria    the criteria that restricts the values returned by this method.
     * @param onDate the date that will be used for the evaluation.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The best matching valueInfo.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws DoesNotExistException     value does not exist.
     * @throws MissingParameterException parameterKey, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ValueInfo evaluateValueOnDate(@WebParam(name = "parameterKey") String parameterKey,
                                         @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                         @WebParam(name = "onDate") Date onDate,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            DoesNotExistException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of values associated with the list of particular parameters
     * that are applicable based on the evaluation of the given criteria.
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     * The relevant values must also have rules that are either null or evaluate to true.
     *
     * @param parameterKeys the keys for the parameters associated with the values that will be returned.
     * @param criteria    the criteria that restricts the values returned by this method.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return  A List of applicable values, sorted. The key word is applicable. The values returned will be based
     *          primarily on priority. If there are values with duplicate priorities, the sort order may or
     *          may not be specified. For some evaluations, such as course based criteria, the atp id and the
     *          atp type will be looked at to return the values in the most applicable order. If two values are
     *          of equal 'applicability', they will be returned in an unspecified order.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKeys, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValuesForParameters (@WebParam(name = "parameterKeys") List<String> parameterKeys,
                                                        @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of values associated with the list of particular parameters
     * that are applicable based on the evaluation of the given criteria, and date.
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     * The relevant values must also have rules that are either null or evaluate to true.
     * The date parameter is used as the date for the evaluation.
     *
     * @param parameterKeys the keys for the parameters associated with the values that will be returned.
     * @param criteria    the criteria that restricts the values returned by this method.
     * @param onDate the date that will be used for the evaluation.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return  A List of applicable values, sorted. The key word is applicable. The values returned will be based
     *          primarily on priority. If there are values with duplicate priorities, the sort order may or
     *          may not be specified. For some evaluations, such as course based criteria, the atp id and the
     *          atp type will be looked at to return the values in the most applicable order. If two values are
     *          of equal 'applicability', they will be returned in an unspecified order.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterKeys, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValuesForParametersOnDate (@WebParam(name = "parameterKeys") List<String> parameterKeys,
                                                              @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                                              @WebParam(name = "onDate") Date onDate,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of values associated with the list of particular parameters in
     * the given parameter group that are applicable based on the evaluation of the given criteria.
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     * The relevant values must also have rules that are either null or evaluate to true.
     *
     * @param parameterGroupKey the keys for the ParameterGroup that consists of the Parameters associated with
     *                          the values that will be returned.
     * @param criteria          the criteria that restricts the values returned by this method.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return  A List of applicable values, sorted. The key word is applicable. The values returned will be based
     *          primarily on priority. If there are values with duplicate priorities, the sort order may or
     *          may not be specified. For some evaluations, such as course based criteria, the atp id and the
     *          atp type will be looked at to return the values in the most applicable order. If two values are
     *          of equal 'applicability', they will be returned in an unspecified order.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterGroupKey, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValuesForParameterGroup (@WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                            @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DoesNotExistException;


    /**
     * Retrieves a list of values associated with the list of particular parameters in
     * the given parameter group that are applicable based on the evaluation of the given criteria, and date.
     * Empty or null fields within the criteria are treated as a wild card and will not restrict values that are returned.
     * Empty or null attributes on the value are treated as a wild card and will not restrict values that are returned.
     * The relevant values must also have rules that are either null or evaluate to true.
     *
     * @param parameterGroupKey the keys for the ParameterGroup that consists of the Parameters associated with
     *                          the values that will be returned.
     * @param criteria          the criteria that restricts the values returned by this method.
     * @param onDate the date that will be used for the evaluation.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return  A List of applicable values, sorted. The key word is applicable. The values returned will be based
     *          primarily on priority. If there are values with duplicate priorities, the sort order may or
     *          may not be specified. For some evaluations, such as course based criteria, the atp id and the
     *          atp type will be looked at to return the values in the most applicable order. If two values are
     *          of equal 'applicability', they will be returned in an unspecified order.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterGroupKey, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValuesForParameterGroupOnDate (@WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                                  @WebParam(name = "criteria") GesCriteriaInfo criteria,
                                                                  @WebParam(name = "onDate") Date onDate,
                                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    //////////////////////////
    // ParameterGroup
    //////////////////////////

    /**
     * Retrieves a ParameterGroup.
     *
     * @param parameterGroupKey a unique Key of a parameterGroup
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a ParameterGroup
     * @throws DoesNotExistException parameterGroupKey not found
     * @throws InvalidParameterException invalid parameterGroupKey or
     * contextInfo
     * @throws MissingParameterException missing parameterGroupKey or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ParameterGroupInfo getParameterGroup(@WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ParameterGroups corresponding to the given list of
     * ParameterGroup Keys.
     *
     * @param parameterGroupKeys list of ParameterGroups to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of ParameterGroup Keys of the given type
     * @throws DoesNotExistException an parameterGroupKey in list not found
     * @throws InvalidParameterException invalid parameterGroupKey or
     * contextInfo
     * @throws MissingParameterException missing parameterGroupKey or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ParameterGroupInfo> getParameterGroupsByKeys(@WebParam(name = "parameterGroupKeys") List<String> parameterGroupKeys,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ParameterGroup Keys of the specified type.
     *
     * @param parameterGroupTypeKey a ParameterGroup type to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of ParameterGroup Keys
     * @throws InvalidParameterException invalid parameterGroupTypeKey or contextInfo
     * @throws MissingParameterException missing parameterGroupTypeKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getParameterGroupKeysByType(@WebParam(name = "parameterGroupTypeKey") String parameterGroupTypeKey,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ParameterGroups based on the criteria and returns
     * a list of ParameterGroup keys which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of ParameterGroup Keys matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForParameterGroupKeys(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ParameterGroups based on the criteria and returns
     * a list of ParameterGroups which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of ParameterGroups matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ParameterGroupInfo> searchForParameterGroups(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a ParameterGroup. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current ParameterGroup and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * ParameterGroup. If an identifier is present for the
     * ParameterGroup (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the ParameterGroup can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey         the identifier for the validation Type
     * @param valueTypeKey              the identifier for the value Type
     * @param parameterGroupInfo        the ParameterGroup information to be validated
     * @param contextInfo               information containing the principalId and
     *                                  locale information about the caller of service operation
     * @return                          a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException    validationTypeKey, parameterKey, or valueTypeKey is not found
     * @throws InvalidParameterException parameterGroupInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, parameterKey, valueTypeKey,
     *                                   parameterGroupInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateParameterGroup (@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                              @WebParam(name = "valueTypeKey") String valueTypeKey,
                                                              @WebParam(name = "parameterGroupInfo") ParameterGroupInfo parameterGroupInfo,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new ParameterGroup. The ParameterGroup Key, ParameterGroup Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param parameterGroupTypeKey the identifier for the Type of the new ParameterGroup
     * @param parameterGroupInfo    the data with which to create the ParameterGroup
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the new ParameterGroup
     * @throws DoesNotExistException     parameterGroupTypeKey does not exist or is not supported
     * @throws InvalidParameterException parameterGroupInfo or contextInfo is not valid
     * @throws MissingParameterException parameterGroupTypeKey, parameterGroupInfo or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws DataValidationErrorException supplied data is invalid
     */
    public ParameterGroupInfo createParameterGroup(@WebParam(name = "parameterGroupTypeKey") String parameterGroupTypeKey,
                                                   @WebParam(name = "parameterGroupInfo") ParameterGroupInfo parameterGroupInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing ParameterGroup. The ParameterGroup Key,
     * Type, and Meta information may not be changed.
     *
     * @param parameterGroupKey    the identifier for the ParameterGroup to be updated
     * @param parameterGroupInfo   the new data for the ParameterGroup
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the updated ParameterGroup
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        parameterGroupKey not found
     * @throws InvalidParameterException    parameterGroupInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    parameterGroupKey, parameterGroupInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public ParameterGroupInfo updateParameterGroup(@WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                   @WebParam(name = "parameterGroupInfo") ParameterGroupInfo parameterGroupInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing ParameterGroup.
     *
     * @param parameterGroupKey     the identifier for the ParameterGroup to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     parameterGroupKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterGroupKey or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteParameterGroup (@WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ParameterGroups in which the given Parameter belongs.
     *
     * @param parameterKey a Parameter key
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of ParameterGroups
     * @throws InvalidParameterException invalid parameterKey or contextInfo
     * @throws MissingParameterException missing parameterKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ParameterGroupInfo> getParameterGroupsForParameter(@WebParam(name = "parameterKey") String parameterKey,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Parameters which belong to the given ParameterGroup.
     *
     * @param parameterGroupKey a ParameterGroup Key
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of Parameters
     * @throws InvalidParameterException invalid parameterGroupKey or contextInfo
     * @throws MissingParameterException missing parameterGroupKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ParameterInfo> getParametersForParameterGroup(@WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Adds a Parameter to a ParameterGroup.
     *
     * @param parameterKey a unique identifier for a Parameter
     * @param parameterGroupKey a unique key for a ParameterGroup
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service operation
     * @return status
     * @throws AlreadyExistsException parameterKey already related to parameterGroupKey
     * @throws DoesNotExistException parameterKey or parameterGroupKey not found
     * @throws InvalidParameterException invalid parameterKey, parameterGroupKey, or contextInfo
     * @throws MissingParameterException missing parameterKey, parameterGroupKey, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addParameterToParameterGroup(@WebParam(name = "parameterKey") String parameterKey,
                                                   @WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Removes a Parameter from a ParameterGroup.
     *
     * @param parameterKey a unique identifier for a Parameter
     * @param parameterGroupKey a unique identifier for a ParameterGroup
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status
     * @throws DoesNotExistException parameterKey or parameterGroupKey not found or unrelated
     * @throws InvalidParameterException invalid parameterKey, parameterGroupKey, or contextInfo
     * @throws MissingParameterException missing parameterKey, parameterGroupKey, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeParameterFromParameterGroup(@WebParam(name = "parameterKey") String parameterKey,
                                                        @WebParam(name = "parameterGroupKey") String parameterGroupKey,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}

/**
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

package org.kuali.student.core.fee.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.core.fee.dto.CatalogFeeInfo;
import org.kuali.student.core.fee.dto.FeeInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * This service supports the management of fees. The managed entities
 * are:
 *
 * <dl>
 * <dt>CatalogFee</dt> <dd>This is an "entry" in the fee catalog. The
 *                     fee catalog represents a catalog of
 *                     "canonical-like" fees. Approved catalog entries
 *                     may be used to create a specific fee to an
 *                     external object.</dd>
 *
 * <dt>Fee</dt> <dd>The actual fee associated with an external object. The
 *              Fee is constrained by its related entry in the fee
 *              catalog.</dd>
 * </dl>
 *
 * @version 0.0.8
 * @author Kuali Student Services
 */

@WebService(name = "FeeService", serviceName = "FeeService", portName = "FeeService", targetNamespace = FeeServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface FeeService {

    /** 
     * Retrieves a single CatalogFee by CatalogFee Id.
     *
     * @param catalogFeeId the identifier for the fee to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the CatalogFee requested
     * @throws DoesNotExistException catalogFeeId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogFeeId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CatalogFeeInfo getCatalogFee(@WebParam(name = "catalogFeeId") String catalogFeeId, 
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogFees from a list of CatalogFee
     * Ids. The returned list may be in any order and if duplicates
     * Ids are supplied, a unique set may or may not be returned.
     *
     * @param catalogFeeIds a list of CatalogFee identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogFees
     * @throws DoesNotExistException a catalogFeeId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException catalogFeeIds, an Id in
     *         catalogFeeIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogFeeInfo> getCatalogFeesByIds(@WebParam(name = "catalogFeeIds") List<String> catalogFeeIds, 
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogFee Ids by CatalogFee Type.
     *
     * @param catalogFeeTypeKey an identifier for a CatalogFee Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogFee identifiers matching
     *         catalogFeeTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogFeeTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCatalogFeeIdsByType(@WebParam(name = "catalogFeeTypeKey") String catalogFeeTypeKey, 
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogFees by code.
     *
     * @param code a CatalogFee code
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogFees
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException code or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogFeeInfo> getCatalogFeesByCode(@WebParam(name = "code") String code, 
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CatalogFees based on the criteria and returns a
     * list of CatalogFee identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CatalogFee Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCatalogFeeIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CatalogFees based on the criteria and returns a
     * list of CatalogFees which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CatalogFees matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogFeeInfo> searchForCatalogFees(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a CatalogFee. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current CatalogFee and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * CatalogFee. If an identifier is present for the CatalogFee
     * (and/or one of its contained sub-objects) and a record is found
     * for that identifier, the validation checks if the CatalogFee
     * can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if
     * the object with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param catalogFeeTypeKey the identifier for the fee Type
     * @param catalogFeeInfo the CatalogFee information to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or
     *         catalogFeeTypeKey is not found
     * @throws InvalidParameterException catalogFeeInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException validationTypeKey,
     *         catalogFeeTypeKey, catalogFeeInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateCatalogFee(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                         @WebParam(name = "catalogFeeTypeKey") String catalogFeeTypeKey, 
                                                         @WebParam(name = "catalogFeeInfo") CatalogFeeInfo catalogFeeInfo, 
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new CatalogFee. The CatalogFee Id, Type, and Meta
     * information may nogt be set in the supplied data.
     *
     * @param catalogFeeTypeKey the identifier for the Type of the new
     *        CatalogFee
     * @param catalogFeeInfo the data with which to create the CatalogFee
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new CatalogFee 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogFeeTypeKey does not
     *         exist is not supported
     * @throws InvalidParameterException catalogFeeInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException catalogFeeTypeKey,
     *         catalogFeeInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public CatalogFeeInfo createCatalogFee(@WebParam(name = "catalogFeeTypeKey") String catalogFeeTypeKey, 
                                           @WebParam(name = "catalogFeeInfo") CatalogFeeInfo catalogFeeInfo, 
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing CatalogFee. The CatalogFee Id, Type, and
     * Meta information may not be changed.
     *
     * @param catalogFeeId the identifier for the CatalogFee to be
     *        updated
     * @param catalogFeeInfo the new data for the CatalogFee
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated CatalogFee
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogFeeId not found
     * @throws InvalidParameterException catalogFeeInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException catalogFeeId, catalogFeeInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public CatalogFeeInfo updateCatalogFee(@WebParam(name = "catalogFeeId") String catalogFeeId, 
                                           @WebParam(name = "catalogFeeInfo") CatalogFeeInfo catalogFeeInfo, 
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
     * Changes the state of a CatalogFee.
     *
     * @param catalogFeeId the Id of the CatalogFee
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException catalogFeeId not found or stateKey
     *         not found in CatalogFee Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogFeeId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeCatalogFeeState(@WebParam(name = "catalogFeeId") String catalogFeeId, 
                                            @WebParam(name = "stateKey") String stateKey,
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing CatalogFee.
     *
     * @param catalogFeeId the identifier for the CatalogFee to be
     *        deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DependentObjectsExistException Fees are attached to this
     *         FeeCatalog
     * @throws DoesNotExistException catalogFeeId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogFeeId or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteCatalogFee(@WebParam(name = "catalogFeeId") String catalogFeeId, 
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DependentObjectsExistException,
               DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a single Fee by Fee Id.
     *
     * @param feeId the identifier for the fee to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Fee requested
     * @throws DoesNotExistException feeId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException feeId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public FeeInfo getFee(@WebParam(name = "feeId") String feeId, 
                          @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of Fees from a list of Fee Ids. The returned
     * list may be in any order and if duplicates Ids are supplied, a
     * unique set may or may not be returned.
     *
     * @param feeIds a list of Fee identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Fees
     * @throws DoesNotExistException a feeId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException feeIds, an Id in feeIds, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<FeeInfo> getFeesByIds(@WebParam(name = "feeIds") List<String> feeIds, 
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of Fee Ids by Fee Type.
     *
     * @param feeTypeKey an identifier for a Fee Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Fee identifiers matching feeTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException feeTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getFeeIdsByType(@WebParam(name = "feeTypeKey") String feeTypeKey, 
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of Fees belonging to a CatalogFee.
     *
     * @param catalogFeeId the identifier for a fee catalog entry
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Fees for the fee catalog entry or an empty
     *         list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogFeeId or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<FeeInfo> getFeesByCatalogFee(@WebParam(name = "catalogFeeId") String catalogFeeId, 
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of Fees by the reference object.
     *
     * @param refObjectURI the URI identifying the namespace of the
     *        reference Id
     * @param refObjectId the Id of the reference
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Fees for the reference object or an empty
     *         list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectURI, refObjectId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<FeeInfo> getFeesByReference(@WebParam(name = "refObjectURI") String refObjectURI, 
                                            @WebParam(name = "refObjectId") String refObjectId, 
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for Fees based on the criteria and returns a list of
     * Fee identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Fee Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForFeeIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for Fees based on the criteria and returns a list of
     * Fees which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Fees matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<FeeInfo> searchForFees(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a Fee. Depending on the value of validationType, this
     * validation could be limited to tests on just the current Fee
     * and its directly contained sub-objects or expanded to perform
     * all tests related to this Fee. If an identifier is present for
     * the Fee (and/or one of its contained sub-objects) and a record
     * is found for that identifier, the validation checks if the Fee
     * can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if
     * the object with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param catalogFeeId the identifier for the fee catalog entry
     * @param feeTypeKey the identifier for the fee Type
     * @param feeInfo the Fee information to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey, catalogFeeId,
     *         or feeTypeKey is not found
     * @throws InvalidParameterException feeInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException validationTypeKey,
     *         catalogFeeId, feeTypeKey, feeInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateFee(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                  @WebParam(name = "catalogFeeId") String catalogFeeId, 
                                                  @WebParam(name = "feeTypeKey") String feeTypeKey, 
                                                  @WebParam(name = "feeInfo") FeeInfo feeInfo, 
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new Fee. The Fee Id, Type, and Meta information may
     * nogt be set in the supplied data.
     *
     * @param catalogFeeId the identifier for the fee catalog entry
     * @param feeTypeKey the identifier for the Type of the new Fee
     * @param feeInfo the data with which to create the Fee
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new Fee 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogFeeId or feeTypeKey does
     *         not exist or is not supported
     * @throws InvalidParameterException feeInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException catalogFeeId, feeTypeKey,
     *         feeInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public FeeInfo createFee(@WebParam(name = "catalogFeeId") String catalogFeeId, 
                             @WebParam(name = "feeTypeKey") String feeTypeKey, 
                             @WebParam(name = "feeInfo") FeeInfo feeInfo, 
                             @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing Fee. The Fee Id, Type, and Meta information
     * may not be changed.
     *
     * @param feeId the identifier for the Fee to be updated
     * @param feeInfo the new data for the Fee
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated Fee
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException feeId not found
     * @throws InvalidParameterException feeInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException feeId, feeInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public FeeInfo updateFee(@WebParam(name = "feeId") String feeId, 
                             @WebParam(name = "feeInfo") FeeInfo feeInfo, 
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
     * Changes the state of a Fee.
     *
     * @param feeId the Id of the Fee
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException feeId not found or stateKey
     *         not found in Fee Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException feeId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeFeeState(@WebParam(name = "feeId") String feeId, 
                                     @WebParam(name = "stateKey") String stateKey,
                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing Fee.
     *
     * @param feeId the identifier for the Fee to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException feeId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException feeId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteFee(@WebParam(name = "feeId") String feeId, 
                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;
}

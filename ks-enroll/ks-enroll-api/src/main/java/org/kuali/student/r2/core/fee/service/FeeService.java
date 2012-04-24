/**
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.r2.core.fee.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.fee.dto.FeeInfo;

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

/**
 * Fee Service Description and Assumptions.
 *
 * This service supports the management of people sets.
 *
 * Version: 1.0 (Dev)
 *
 * @Author tom
 * @Since Mon Nov 21 14:22:34 EDT 2011
 */

@WebService(name = "FeeService", serviceName = "FeeService", portName = "FeeService", targetNamespace = "fee")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface FeeService {

    /** 
     * Retrieves a Fee.
     *
     * @param feeKey a unique Id of a Fee
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a Fee
     * @throws DoesNotExistException feeId not found
     * @throws InvalidParameterException invalid feeId or contextInfo
     * @throws MissingParameterException missing feeId or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public FeeInfo getFee(@WebParam(name = "feeId") String feeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Fees corresponding to the given list
     * of Fee Ids.
     *
     * @param feeIds list of Feess to be retrieved
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of Fee Ids of the given type
     * @throws DoesNotExistException an feeId in list not found
     * @throws InvalidParameterException invalid feeId or contextInfo
     * @throws MissingParameterException missing feeId or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<FeeInfo> getFeesByIds(@WebParam(name = "feeIds") List<String> feeIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Fee Ids of the specified type.
     *
     * @param feeTypeId a Fee type to be retrieved
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of Fee Ids
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing feeTypeId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getFeeIdsByType(@WebParam(name = "feeTypeId") String feeTypeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Fees by the reference object.
     *
     * @param refObjectTypeKey the type of the reference
     * @param refObjectId the Id of the reference
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Fee Ids
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing refObjectTypeKey,
     *         refObjectId, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getFeesByReference(@WebParam(name = "refObjectTypeKey") String refObjectTypeKey, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Fees based on the criteria and returns a list
     * of Fee identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return list of Fee Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForFeeIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Fees based on the criteria and returns a list of
     * Fees which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return list of Fees
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<FeeInfo> searchForFees(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Fee. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the Fee and a record is found for that
     * identifier, the validation checks if the Fee can be shifted
     * to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as
     * such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the
     * standard validation as the caller provides the identifier in
     * the create statement instead of the server assigning an
     * identifier.
     *
     * @param validationTypeId the identifier of the extent of validation
     * @param feeInfo the Fee information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeId not found
     * @throws InvalidParameterException invalid validationTypeId,
     *         feeInfo, or contextInfo
     * @throws MissingParameterException missing validationTypeId,
     *         feeInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateFee(@WebParam(name = "validationTypeId") String validationTypeId, @WebParam(name = "feeInfo") FeeInfo feeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new Fee.
     *
     * @param feeInfo the details of Fee to be created
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return the Fee just created
     * @throws DataValidationErrorException one or more values invalid
     *         for this operation
     * @throws InvalidParameterException invalid feeInfo or
     *         contextInfo
     * @throws MissingParameterException missing feeInfo or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public FeeInfo createFee(@WebParam(name = "feeInfo") FeeInfo feeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing Fee.
     *
     * @param feeId the Id of Fee to be updated
     * @param feeInfo the details of updates to Fee being updated
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return the details of Fee just updated
     * @throws DataValidationErrorException One or more values invalid 
     *         for this operation
     * @throws DoesNotExistException feeId not found
     * @throws InvalidParameterException invalid feeId,
     *         feeInfo, or contextInfo
     * @throws MissingParameterException missing feeId,
     *         feeInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException The action was attempted on an out 
     *         of date version.
     */
    public FeeInfo updateFee(@WebParam(name = "feeId") String feeId, @WebParam(name = "feeInfo") FeeInfo feeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing Fee.
     *
     * @param feeId the Id of the Fee to be deleted
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException feeId not found
     * @throws InvalidParameterException invalid feeId or contextInfo
     * @throws MissingParameterException missing feeId or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteFee(@WebParam(name = "feeId") String feeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

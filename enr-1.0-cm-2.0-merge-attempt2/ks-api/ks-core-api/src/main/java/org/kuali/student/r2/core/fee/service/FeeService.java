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

package org.kuali.student.r2.core.fee.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;

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

import org.kuali.student.r2.core.constants.FeeServiceConstants;

/**
 * This service supports the management of fees.
 *
 * @author tom
 */

@WebService(name = "FeeService", serviceName = "FeeService", portName = "FeeService", targetNamespace = FeeServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface FeeService {

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
    public EnrollmentFeeInfo getFee(@WebParam(name = "feeId") String feeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<EnrollmentFeeInfo> getFeesByIds(@WebParam(name = "feeIds") List<String> feeIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<String> getFeeIdsByType(@WebParam(name = "feeTypeKey") String feeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Fees by the reference object.
     *
     * @param refObjectURI the URI identifying the namespace of the
     *        reference Id
     * @param refObjectId the Id of the reference
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Fees for the reference object or an empty list
     *         is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing refObjectURI,
     *         refObjectId, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<EnrollmentFeeInfo> getFeesByReference(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Fees based on the criteria and returns a list
     * of Fee identifiers which match the search criteria.
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
    public List<String> searchForFeeIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<EnrollmentFeeInfo> searchForFees(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param feeTypeKey the identifier for the fee Type
     * @param feeInfo the Fee information to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or feeTypeKey
     *         is not found
     * @throws InvalidParameterException feeInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException validationTypeKey,
     *         feeTypeKey, feeInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateFee(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "feeTypeKey") String feeTypeKey, @WebParam(name = "feeInfo") EnrollmentFeeInfo feeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new Fee. The Fee Id, Type, and Meta information
     * may nogt be set in the supplied data.
     *
     * @param feeTypeKey the identifier for the Type of the new Fee
     * @param feeInfo the data with which to create the Fee
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new Fee 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws InvalidParameterException feeInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException feeTypeKey, feeInfo or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public EnrollmentFeeInfo createFee(@WebParam(name = "feeTypeKey") String feeTypeKey, @WebParam(name = "feeInfo") EnrollmentFeeInfo feeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

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
    public EnrollmentFeeInfo updateFee(@WebParam(name = "feeId") String feeId, @WebParam(name = "feeInfo") EnrollmentFeeInfo feeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing Fee.
     *
     * @param feeId the identifier for the Fee to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException feeId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException feeId or contextInfo is missing
     *         or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteFee(@WebParam(name = "feeId") String feeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

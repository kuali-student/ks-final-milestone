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

package org.kuali.student.r2.core.enumerationmanagement.service;

import org.kuali.student.r1.common.search.service.SearchService;
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
import org.kuali.student.r2.core.constants.EnumerationManagementServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * Enumeration Management service supports the management of code tables for
 * other services. It is only accessed by authorized callers configuring some
 * piece of the system.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

@WebService(name = "EnumerationManagementService", targetNamespace = EnumerationManagementServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EnumerationManagementService extends SearchService {

    /**
     * Retrieves the list of meta information for the enumerations supported by
     * this service.
     *
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of enumeration meta information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<EnumerationInfo> getEnumerations(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves meta information for a particular Enumeration. The meta
     * information should describe constraints on the various fields comprising
     * the enumeration as well as the allowed contexts.
     *
     * @param enumerationKey identifier for the Enumeration
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Meta information about an enumeration
     * @throws DoesNotExistException     enumerationKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing enumerationKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public EnumerationInfo getEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of values for a particular enumeration with a certain
     * context for a particular date. The values returned should be those where
     * the supplied date is between the effective and expiration dates. Certain
     * enumerations may not support this functionality.
     *
     * @param enumerationKey identifier for the Enumeration
     * @param contextTypeKey identifier for the enumeration context type
     * @param contextValue   value for the enumeration context
     * @param contextDate    date and time to get the enumeration for
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return List of Codes and Values
     * @throws DoesNotExistException     enumerationKey not found
     * @throws InvalidParameterException invalid contextValue, contextDate or
     *                                   contextInfo
     * @throws MissingParameterException missing enumerationKey, contextTypeKey,
     *                                   contextValue, contextDate or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<EnumeratedValueInfo> getEnumeratedValues(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "contextTypeKey") String contextTypeKey, @WebParam(name = "contextValue") String contextValue, @WebParam(name = "contextDate") Date contextDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Validates an EnumerationValue. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the Process and a
     * record is found for that identifier, the validation checks if the Process
     * can be shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     *
     * @param validationTypeKey   the identifier of the extent of validation
     * @param enumerationKey      identifier for the Enumeration
     * @param code                code identifying the value to be validated
     * @param enumeratedValueInfo the Room information to be tested
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException     validationTypeKey, enumerationKey or
     *                                   code not found
     * @throws InvalidParameterException invalid enumeratedValueInfo or
     *                                   contextInfo
     * @throws MissingParameterException missing validationTypeKey, enumerationKey,
     *                                   code, enumeratedValueInfo or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateEnumeratedValue(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Updates a value in a particular Enumeration. The pattern in this
     * signature is different from most updates in that it is unlikely for
     * multiple individuals or processes to be altering the same construct at
     * the same time.
     *
     * @param enumerationKey      identifier for the Enumeration
     * @param code                code identifying the value to be updated
     * @param enumeratedValueInfo updated information on the value
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return updated enumerated value
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        enumerationKey, code not found
     * @throws InvalidParameterException    invalid enumeratedValueInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing enumerationKey, code,
     *                                      enumeratedValueInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public EnumeratedValueInfo updateEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Removes a value from a particular Enumeration. This particular operation
     * should be used sparingly, as removal of a value may lead to dangling
     * references. It is suggested that standard procedure should be to update
     * the expiration date for the value so that it is seen as expired.
     *
     * @param enumerationKey Identifier for the Enumeration
     * @param code           code identifying the value to be removed
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Status of the operation (success, failed)
     * @throws DoesNotExistException     enumerationKey, code not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing enumerationKey, code or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a value to a particular Enumeration.
     *
     * @param enumerationKey      Identifier for the Enumeration
     * @param code                code identifying the value to be added
     * @param enumeratedValueInfo Value to be added
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return Newly created enumerated value
     * @throws AlreadyExistsException       combination of enumerationKey, code
     *                                      already exists
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        enumerationKey not found
     * @throws InvalidParameterException    invalid enumeratedValueInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing enumerationKey, enumeratedValueInfo
     *                                      or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read only
     */
    public EnumeratedValueInfo addEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

}


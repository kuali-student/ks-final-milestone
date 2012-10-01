/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r2.core.exemption.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.dto.ExemptionRequestInfo;


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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.ExemptionServiceConstants;

/**
 * Version: DRAFT - NOT READY FOR RELEASE.
 *
 * The Exemption service stores information to permit a person to be exempted
 * from the enforcement of a restriction, deadline, or statement.
 *
 * The service begins with creating an ExemptionRequest for a person. On
 * approval of an ExemptionRequest, one or more Exemptions are created. There
 * are several types of Exemptions. Exemption types govern what data is stored
 * in the Exemption structure. Current types include:
 *
 * Date Exemption: overrides a Milestone in ATP with a Date Milestone Exemption:
 * overrides a Milestone in ATP with another Milestone
 *
 * The Exemption stores the fact that an exemption has been granted. The
 * interpretation and enforcment of the exemption is performed by the caller of
 * this service. The Exemption service provides the information to override a
 * specific check that occurs somewhere in the system based on one of the above
 * Exemption types.
 *
 * @author tom
 * @since Tue Jun 21 14:22:34 EDT 2011
 */
@WebService(name = "ExemptionService", targetNamespace = ExemptionServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ExemptionService {

    ////
    //// CRUD Request
    //// 
    /**
     * Retrieves the details of a single ExemptionRequest by a exemption Id.
     *
     * @param exemptionRequestId Unique Id of the ExemptionRequest to be
     * retrieved
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the details of the ExemptionRequest requested
     * @throws DoesNotExistException exemptionRequestId not found
     * @throws InvalidParameterException invalid exemptionRequestId
     * @throws MissingParameterException missing exemptionRequestId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionRequestInfo getExemptionRequest(@WebParam(name = "exemptionRequestId") String exemptionRequestId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list ExemptionRequests corresponding to a list of exemption
     * request Ids.
     *
     * @param exemptionRequestIds list of unique Ids of the ExemptionRequests to
     * be retrieved
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of ExemptionRequests
     * @throws DoesNotExistException an exemptionRequestId in list not found
     * @throws InvalidParameterException invalid exemptionRequestId in list
     * @throws MissingParameterException missing exemptionRequestIds
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionRequestInfo> getExemptionRequestsByIds(@WebParam(name = "exemptionRequestIds") List<String> exemptionRequestIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list Exemption Request Ids by Type.
     *
     * @param exemptionRequestTypeKey a unique key for an exemption requestType
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws DoesNotExistException an exemptionId in list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getExemptionRequestIdsByType(@WebParam(name = "exemptionRequestTypeKey") String exemptionRequestTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates an ExemptionRequest. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained subobjects or expanded to perform all tests
     * related to this object. If an identifier is present for the exemption
     * request and a record is found for that identifier, the validation checks
     * if the exemption request can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create statement
     * instead of the server assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param exemptionRequestInfo the exemption request information to be
     * tested.
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     * exemptionRequestInfo
     * @throws MissingParameterException missing validationTypeKey,
     * exemptionRequestInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateExemptionRequest(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Creates a new Exemption Request.
     *
     * @param personId id of the person for whom the exemption is requested
     * @param exemptionRequestTypeKey type of request
     * @param exemptionRequestInfo Details of the Exemption Request to be
     * created
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the details of the Exemption Requst just created
     * @throws AlreadyExistsException the Exemption Request being created
     * already exists
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionRequestInfo createExemptionRequest(@WebParam(name = "personId") String personId,
            @WebParam(name = "exemptionRequestTypeKey") String exemptionRequestTypeKey,
            @WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Updates an existing Exemption Request.
     *
     * @param exemptionRequestId Id of Exemption Request to be updated
     * @param exemptionRequestInfo Details of updates to the Exemption Request
     * being updated
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the details of Exemption Request just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException the Exemption Request does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public ExemptionRequestInfo updateExemptionRequest(@WebParam(name = "exemptionRequestId") String exemptionRequestId,
            @WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException;

    /**
     * Deletes an existing Exemption Request record.
     *
     * @param exemptionRequestId the Id of the Exemption Request to be deleted
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Exemption Request does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteExemptionRequest(@WebParam(name = "exemptionRequestId") String exemptionRequestId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////
    //// request getters
    ////
    /**
     * Gets a list of all exemption requests for a person.
     *
     * @param personId a unique Id of the Person
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemption requests
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionRequestInfo> getRequestsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of all exemption requests by the requester.
     *
     * @param requesterId a unique Id of the Person
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemption requests
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionRequestInfo> getRequestsByRequester(
            @WebParam(name = "requesterId") String requesterId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////
    ////  standard crud methods for exemption
    //// 
    /**
     * Retrieves the details of a single Exemption by a exemption Id.
     *
     * @param exemptionId Unique Id of the Exemption to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the details of the Exemption requested
     * @throws DoesNotExistException exemptionId not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionInfo getExemption(@WebParam(name = "exemptionId") String exemptionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list Exemptions corresponding to a list of exemption Ids.
     *
     * @param exemptionIds list of unique Ids of the Exemption to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws DoesNotExistException an exemptionId in list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsByIds(@WebParam(name = "exemptionIds") List<String> exemptionIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list Exemption Ids by Type.
     *
     * @param exemptionTypeKey a unique key for an exemption Type
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws DoesNotExistException an exemptionId in list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getExemptionIdsByType(@WebParam(name = "exemptionTypeKey") String exemptionTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates an Exemption. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained subobjects or expanded to perform all tests related to
     * this object. If an identifier is present for the exemption and a record
     * is found for that identifier, the validation checks if the exemption can
     * be shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param exemptionInfo the exemption information to be tested.
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     * exemptionInfo
     * @throws MissingParameterException missing validationTypeKey,
     * exemptionInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateExemption(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "exemptionInfo") ExemptionInfo exemptionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Creates a new Exemption. Exemptions can only be created off of an
     * exemption request.
     *
     * @param exemptionRequestId an Id for an exemption request
     * @param exemptionTypeKey type key identifying the type of the exemption
     * @param exemptionInfo Details of the Exemption to be created
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the details of the Exemption just created
     * @throws AlreadyExistsException the Exemption being created already exists
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionInfo createExemption(@WebParam(name = "exemptionRequestId") String exemptionRequestId,
            @WebParam(name = "exemptionTypeKey") String exemptionTypeKey,
            @WebParam(name = "exemptionInfo") ExemptionInfo exemptionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Updates an existing Exemption.
     *
     * @param exemptionId Id of Exemption to be updated
     * @param exemptionInfo Details of updates to the Exemption being updated
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the details of Exemption just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException the Exemption does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public ExemptionInfo updateExemption(@WebParam(name = "exemptionId") String exemptionId,
            @WebParam(name = "exemptionInfo") ExemptionInfo exemptionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException;

    /**
     * Indicate that the given Exemption has been used successfully in a
     * transaction. This method increments the Exemption use count.
     *
     * @param exemptionId the Id for the Exemption
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return the status
     * @throws DoesNotExistException no valid exemption exists
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addUseToExemption(@WebParam(name = "exeptionId") String exemptionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Deletes an existing Exemption record.
     *
     * @param exemptionId the Id of the Exemption to be deleted
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Exemption does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteExemption(@WebParam(name = "exemptionId") String exemptionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////
    //// exemption get By methods 
    ////
    /**
     * Gets a list of all exemptions for a Person.
     *
     * @param personId a unique Id of the Person
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets a list of all exemptions tied to the exemption request.
     *
     * @param requestId a unique Id of an exemption request
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsForRequest(@WebParam(name = "requestId") String requestId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets a list of all active effective exemptions for a Person.
     * 
     * An effective exemption is one with an active state, the as of date falls 
     * within the effective date range, and the use count is less than the use limit.
     *
     * @param personId a unique Id of the Person
     * @param asOfDate the date for to check if the exemption is valid
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getActiveExemptionsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "asOfDate") Date asOfDate,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets a list of all exemptions by Type for a Person.
     *
     * @param typeKey an exemption Type
     * @param personId a unique Id of the Person
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsByTypeForPerson(@WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets a list of all effective exemptions by Type for a Person. An
     * effective exemption is one with an active state, the current date falls
     * within the effective date range, and the use count is less than the use
     * limit.
     *
     * @param typeKey an exemption Type
     * @param personId a unique Id of the Person
     * @param asOfDate the date for to check if the exemption is valid
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getActiveExemptionsByTypeForPerson(@WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "asOfDate") Date asOfDate,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets a list of all effective exemptions by Type, Process and check for a
     * Person. An effective exemption is one with an active state, the current
     * date falls within the effective date range, and the use count is less
     * than the use limit.
     *
     * @param typeKey an exemption Type
     * @param processKey the id of process 
     * @param checkId the key of a check 
     * @param personId a unique Id of the Person
     * @param asOfDate the date for to check if the exemption is valid
     * @param contextInfo Context information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getActiveExemptionsByTypeProcessAndCheckForPerson(@WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "processKey") String processKey,
            @WebParam(name = "checkId") String checkId,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "asOfDate") Date asOfDate,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}

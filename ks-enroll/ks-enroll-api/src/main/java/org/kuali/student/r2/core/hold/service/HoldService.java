/**
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

package org.kuali.student.r2.core.hold.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;

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
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;

/**
 * Version: DRAFT - NOT READY FOR RELEASE. 
 *
 * The Hold Service defines a service to manage holdss on a Person by
 * Person basis. Holds are a way to put a "stop" on an action
 * implemented by another service, such as registration for a course.
 * Holds are checked in the Process Service.
 *
 * The Hold is a relation between a Person and an Issue. An inactive
 * Hold is one that has been released or cancelled. The state of the
 * Hold needs to be checked to determine if a restriction exists.
 *
 * @author tom
 * @since Sun May 1 14:22:34 EDT 2011
 */

@WebService(name = "HoldService", targetNamespace = HoldServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface HoldService {

    /** 
     * Retrieves the a single Hold by a hold Id.
     *
     * @param holdId a unique identifier of the Hold to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Hold requested
     * @throws DoesNotExistException holdId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holdId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public HoldInfo getHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Holds from to a list of hold Ids. The returned
     * list may be in any order and of duplicate Ids are supplied, a
     * unique set may or may not be returned.
     *
     * @param holdIds list of Hold identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Holds
     * @throws DoesNotExistException a holdId in list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holdIds, a holdId in holdIds,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HoldInfo> getHoldsByIds(@WebParam(name = "holdIds") List<String> holdIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Hold Ids by Hold Type.
     *
     * @param holdTypeKey an identifier for a Hold Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Hold identifiers
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holdTypeKey or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getHoldIdsByType(@WebParam(name = "holdTypeKey") String holdTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all Holds related to the given Issue.
     *
     * @param issueId a unique identifier for the Issue 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Holds to the given Issue
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException issueId or contetInfo is missing
     *         or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HoldInfo> getHoldsByIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all Holds to the given Person.
     *
     * @param personId a unique identifier for the Person 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Holds to the given Person
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId of contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HoldInfo> getHoldsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of active Holds, that pertain to the given
     * Person. An active Hold is any open Hold that has had not been
     * released or cancelled.
     *
     * @param personId an Id of a Person 
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getActiveHoldsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of all Holds by Issue for a Person.
     *
     * @param issueId an Issue
     * @param personId Id of a person
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByIssueAndPerson(@WebParam(name = "issueId") String issueId, @WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of active Holds, both warning and blocking, by
     * Issue for a Person. An active Hold is any open Hold that has
     * had not been released or cancelled.
     *
     * @param issueId an Issue
     * @param personId Id of a person
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getActiveHoldsByIssueAndPerson(@WebParam(name = "issueId") String issueId, @WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Holds based on the criteria and returns a list
     * of Hold identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of Hold Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForHoldIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Holds based on the criteria and returns a list of
     * Holds which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> searchForHolds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Hold. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the hold and a record is found for that identifier,
     * the validation checks if the hold can be shifted to the new
     * values. If a record cannot be found for the identifier, it is
     * assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create
     * statement instead of the server assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param holdInfo the hold information to be tested.
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, holdInfo
     * @throws MissingParameterException missing validationTypeKey, holdInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHold(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "holdInfo") HoldInfo holdInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Hold.
     *
     * @param personId identifying the person for whom the hold is to be applied
     * @param issueId identifying the exact issue involved
     * @param holdTypeKey identifying the type of the hold
     * @param holdInfo Details of the Hold to be created
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Hold just created
     * @throws AlreadyExistsException the Hold being created already exists
     * @throws DataValidationErrorException One or more values invalid for 
     *         this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws PermissionDeniedException authorization failure
     */
    public HoldInfo createHold( 
            @WebParam(name = "personId") String personId,  
            @WebParam(name = "issueId") String issueId, 
            @WebParam(name = "holdTypeKey") String holdTypeKey,
            @WebParam(name = "holdInfo") HoldInfo holdInfo, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws AlreadyExistsException, DataValidationErrorException, 
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing Hold.
     *
     * @param holdId Id of Hold to be updated
     * @param holdInfo Details of updates to the Hold
     *        being updated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Hold just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException The action was attempted on an
     *         out of date version.
     */
    public HoldInfo updateHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "holdInfo") HoldInfo holdInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Releases a Hold. A release preserves the record and marks the
     * State as released and sets the released date. A Hold should be
     * released instead of deleted when the record needs to be
     * preserved.
     *
     * @param holdId a hold
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the modified HoldInfo
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldInfo releaseHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes an existing Hold record.
     *
     * @param holdId the Id of the Hold to be deleted
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of a single Issue by an issue Id.
     *
     * @param issueId Unique Id of the Issue to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Issue requested
     * @throws DoesNotExistException issueId not found
     * @throws InvalidParameterException invalid issueId
     * @throws MissingParameterException missing issueId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public IssueInfo getIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Issues corresponding to a list of issue Ids.
     *
     * @param issueIds list of unique Ids of the
     *        Issue to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Issues
     * @throws DoesNotExistException a issueId in list not found
     * @throws InvalidParameterException invalid issueId in list
     * @throws MissingParameterException missing issueIds
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<IssueInfo> getIssuesByIds(@WebParam(name = "issueIds") List<String> issueIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Issues by Type.
     *
     * @param issueTypeKey a Type of the Issue to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Issues of the given Type
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getIssueIdsByType(@WebParam(name = "issueTypeKey") String issueTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Issues that pertain to the given
     * organization.
     *
     * @param organizationId a unique Id of an organoization
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Issues 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<IssueInfo> getIssuesByOrg(@WebParam(name = "organizationId") String organizationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Issues based on the criteria and returns a list
     * of Issue identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of Issue Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForIssueIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Issues based on the criteria and returns a list of
     * Issues which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of IssueIds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<IssueInfo> searchForIssues(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an issue. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the issue and a record is found for that
     * identifier, the validation checks if the issue can be
     * shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as
     * such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the
     * standard validation as the caller provides the identifier in
     * the create statement instead of the server assigning an
     * identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param issueInfo the issue information to be tested.
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, issueInfo
     * @throws MissingParameterException missing validationTypeKey, issueInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateIssue(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "issueInfo") IssueInfo issueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Issue.
     *
     * @param issueTypeKey indicates the type of issue
     * @param issueInfo Details of the Issue to be created
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Issue just created
     * @throws AlreadyExistsException the Issue being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public IssueInfo createIssue(
            @WebParam(name = "issueTypeKey") String issueTypeKey,
            @WebParam(name = "issueInfo") IssueInfo issueInfo, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws AlreadyExistsException, DataValidationErrorException, 
            InvalidParameterException, MissingParameterException, 
            OperationFailedException, 
            PermissionDeniedException, ReadOnlyException;;

    /** 
     * Updates an existing Issue.
     *
     * @param issueId Id of the Issue to be updated
     * @param issueInfo Details of updates to the Issue
     *        being updated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Issue just updated
     * @throws DataValidationErrorException One or more values invalid
     *         for this operation
     * @throws DoesNotExistException the Issue does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException The action was attempted on an
     *         out of date version.
     */
    public IssueInfo updateIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "issueInfo") IssueInfo issueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing Issue.
     *
     * @param issueId the Id of the Issue to
     *        be deleted
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Issue does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

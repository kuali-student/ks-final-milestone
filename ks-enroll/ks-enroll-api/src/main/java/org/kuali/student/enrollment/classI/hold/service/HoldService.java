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

package org.kuali.student.enrollment.classI.hold.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enrollment.classI.hold.dto.HoldInfo;
import org.kuali.student.enrollment.classI.hold.dto.IssueInfo;
import org.kuali.student.enrollment.classI.hold.dto.RestrictionInfo;

import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;

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
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;

/**
 * Version: DRAFT - NOT READY FOR RELEASE.
 *
 * The Hold Service defines a service to manage Restrictions on a
 * Person by Person basis. Restrictions are a way to put a "stop" on
 * an action implemented by another service, such as registration for
 * a course.
 *
 * A Restriction is made up of Issues. A Restriction for course
 * registration may be based on payment of tuition and current
 * immunization. An Issue can be defined by the Bursar for non-payment
 * of tuition and by Medical for lack of immunization. Both these
 * Issues can be mapped to a Restriction.
 *
 * The Hold is a relation between a Person and an Issue. A person is
 * "restricted" if any active Hold exists for any Issue related to a
 * Restriction. An inactive Hold is one that has been released or
 * cancelled. The state of the Hold needs to be checked to determine
 * if a restriction exists. 
 *
 * Holds can be designated as a warning Hold. A warning Hold is a
 * non-blocking Hold where a warning message should be displayed to a
 * Person instead of blocking the action. A Person may not be
 * restricted but have active warning Holds.
 *
 * @Author tom
 * @Since Sun May 1 14:22:34 EDT 2011
 */

@WebService(name = "HoldService", targetNamespace = HoldServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface HoldService extends DataDictionaryService, StateService, TypeService {

    /** 
     * Tests if the given person has a Restriction. A person is
     * restricted if any active non-warning blocking hold exists for
     * the Person on any Issue related to the given Restriction. 
     *
     * @param restrictionKey a unique key of the Restriction
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return true if a restriction exists, false otherwise
     * @throws DoesNotExistException holdId not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Boolean isPersonRestricted(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of Person Ids that are restricted. A list of people
     * with any active non-warning blocking hold exists on any Issue
     * related to the given Restriction.
     *
     * @param restrictionKey a unique key of the Restriction
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws DoesNotExistException holdId not found
     * @throws InvalidParameterException invalid holdId
     * @throws MissingParameterException missing holdId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRestrictedPersons(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of holds for a Person and Restriction. The returned
     * list includes all Holds for the Person for any Issue related to
     * the given Restriction.
     *
     * @param restrictionKey a unique key of the Restriction
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws DoesNotExistException holdId not found
     * @throws InvalidParameterException invalid holdId
     * @throws MissingParameterException missing holdId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByRestrictionForPerson(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of holds for a Person and Restriction. The returned
     * list includes only active Holds, both warning and blocking, for
     * the Person for any Issue related to the given Restriction. An
     * active Hold is any open Hold that has had not been released or
     * cancelled.
     *
     * @param restrictionKey a unique key of the Restriction
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws DoesNotExistException holdId not found
     * @throws InvalidParameterException invalid holdId
     * @throws MissingParameterException missing holdId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getActiveHoldsByRestrictionForPerson(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of a single Hold by a hold Id.
     *
     * @param holdId Unique Id of the Hold to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Hold requested
     * @throws DoesNotExistException holdId not found
     * @throws InvalidParameterException invalid holdId
     * @throws MissingParameterException missing holdId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldInfo getHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Holds corresponding to a list of
     * hold Ids.
     *
     * @param holdIdList list of unique Ids of the
     *        Hold to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws DoesNotExistException an  holdId in list not found
     * @throws InvalidParameterException invalid holdId in list
     * @throws MissingParameterException missing holdIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByIdList(@WebParam(name = "holdIdList") List<String> holdIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Holds by Issue.
     *
     * @param issueId an Id of an Issue 
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of all Holds that pertain to the given Person.
     *
     * @param personId an Id of a Person 
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of active Holds, both warning and blocking,
     * that pertain to the given Person. An active Hold is any open
     * Hold that has had not been released or cancelled.
     *
     * @param personId an Id of a Person 
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getActiveHoldsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of all Holds by Issue for a Person.
     *
     * @param issueId an Issue
     * @param personId Id of a person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByIssueForPerson(@WebParam(name = "issueId") String issueId, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of active Holds, both warning and blocking, by
     * Issue for a Person. An active Hold is any open Hold that has
     * had not been released or cancelled.
     *
     * @param issueId an Issue
     * @param personId Id of a person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     public List<HoldInfo> getActiveHoldsByIssueForPerson(@WebParam(name = "issueId") String issueId, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param validationType Identifier of the extent of validation
     * @param holdInfo the hold information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, holdInfo
     * @throws MissingParameterException missing validationTypeKey, holdInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHold(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "holdInfo") HoldInfo holdInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Hold.
     *
     * @param holdInfo Details of the Hold to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Hold just created
     * @throws AlreadyExistsException the Hold being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldInfo createHold(@WebParam(name = "holdInfo") HoldInfo holdInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Hold.
     *
     * @param holdId Id of Hold to be updated
     * @param holdInfo Details of updates to the Hold
     *        being updated
     * @param context Context information containing the principalId
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
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public HoldInfo updateHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "holdInfo") HoldInfo holdInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Releases a Hold. A release preserves the record and marks the
     * State as released and sets the released date. A Hold should be
     * released instead of deleted when the record needs to be
     * preserved.
     *
     * @param holdId a hold
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the modified HoldInfo
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     public HoldInfo releaseHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes an existing Hold record.
     *
     * @param holdId the Id of the Hold to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of a single Issue by an issue Id.
     *
     * @param issueId Unique Id of the Issue to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Issue requested
     * @throws DoesNotExistException issueId not found
     * @throws InvalidParameterException invalid issueId
     * @throws MissingParameterException missing issueId
     * @Throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public IssueInfo getIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Issues corresponding to a list of issue Ids.
     *
     * @param issueIdList list of unique Ids of the
     *        Issue to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Issues
     * @throws DoesNotExistException a issueId in list not found
     * @throws InvalidParameterException invalid issueId in list
     * @throws MissingParameterException missing issueIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<IssueInfo> getIssuesByIdList(@WebParam(name = "issueIdList") List<String> issueIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Issues by Type.
     *
     * @param issueTypeKey a Type of the Issue to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Issues of the given Type
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getIssueIdsByType(@WebParam(name = "issueTypeKey") String issueTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Issues that pertain to the given
     * organization.
     *
     * @param organizationId a unique Id of an organoization
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Issues 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<IssueInfo> getIssuesByOrg(@WebParam(name = "organizationId") String organizationId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Issues that are related to the given
     * Restriction.
     *
     * @param restrictionKey a unique key for a Restriction
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Issues 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<IssueInfo> getIssuesByRestriction(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Adds an Issue to a Restriction.
     *
     * @param restrictionKey a unique key for a Restriction
     * @param issueId a unique Id of an Issue to be added
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return StatusInfo
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addIssueToRestriction(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "issueId") String issueId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Removes an Issue to a Restriction.
     *
     * @param restrictionKey a unique key for a Restriction
     * @param issueId a unique Id of an Issue to be removed
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return StatusInfo
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeIssueFromRestriction(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "issueId") String issueId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param validationType Identifier of the extent of validation
     * @param issueInfo the issue information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, issueInfo
     * @throws MissingParameterException missing validationTypeKey, issueInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateIssue(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "issueInfo") IssueInfo issueInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Issue.
     *
     * @param issueInfo Details of the Issue to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Issue just created
     * @throws AlreadyExistsException the Issue being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public IssueInfo createIssue(@WebParam(name = "issueInfo") IssueInfo issueInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Issue.
     *
     * @param issueId Id of the Issue to be updated
     * @param issueInfo Details of updates to the Issue
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Issue just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Issue does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public IssueInfo updateIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "issueInfo") IssueInfo issueInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Issue.
     *
     * @param issueId the Id of the Issue to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Issue does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteIssue(@WebParam(name = "issueId") String issueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of a single Restriction by a restriction key.
     *
     * @param restrictionKey Unique Key of the Restriction to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Restriction requested
     * @throws DoesNotExistException restrictionKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @Throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RestrictionInfo getRestriction(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Restrictions corresponding to a list of
     * restriction keys.
     *
     * @param restrictionKeyList list of unique keys of the
     *        Restriction to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Restrictions
     * @throws DoesNotExistException a restrictionKey in list not found
     * @throws InvalidParameterException invalid restrictionKey in list
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RestrictionInfo> getRestrictionsByKeyList(@WebParam(name = "restrictionKeyList") List<String> restrictionKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Restrictions by Type.
     *
     * @param restrictionTypeKey a Type of the Restriction to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Restrictions of the given Type
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRestrictionKeysByType(@WebParam(name = "restrictionTypeKey") String restrictionTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a restriction. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the restriction and a record is found for that
     * identifier, the validation checks if the restriction can be
     * shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as
     * such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the
     * standard validation as the caller provides the identifier in
     * the create statement instead of the server assigning an
     * identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param restrictionInfo the restriction information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, restrictionInfo
     * @throws MissingParameterException missing validationTypeKey, restrictionInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateRestriction(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "restrictionInfo") RestrictionInfo restrictionInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Restriction.
     *
     * @param restrictionKey a unique key for the restriction
     * @param restrictionInfo Details of the Restriction to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Restriction just created
     * @throws AlreadyExistsException the Restriction being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RestrictionInfo createRestriction(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "restrictionInfo") RestrictionInfo restrictionInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Restriction.
     *
     * @param restrictionKey the key of the Restriction to be updated
     * @param restrictionInfo Details of updates to the Restriction
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Restriction just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Restriction does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public RestrictionInfo updateRestriction(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "restrictionInfo") RestrictionInfo restrictionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Restriction.
     *
     * @param restrictionKey the key of the Restriction to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Restriction does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRestriction(@WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

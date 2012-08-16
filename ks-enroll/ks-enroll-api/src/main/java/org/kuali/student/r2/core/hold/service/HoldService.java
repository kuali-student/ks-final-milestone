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
package org.kuali.student.r2.core.hold.service;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

/**
 * The Hold Service defines a service to manage holds on a Person by Person
 * basis. Holds are a way to put a "stop" on an action implemented by another
 * service, such as registration for a course. Holds are checked in the Process
 * Service.
 *
 * The Student Hold is a relation between a Person and an Issue. An inactive
 * Hold is one that has been released or canceled. The state of the Hold needs
 * to be checked to determine if the restriction still exists.
 *
 * @author tom
 * @since Sun May 1 14:22:34 EDT 2011
 */
@WebService(name = "HoldService", targetNamespace = HoldServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface HoldService {

    /**
     * Retrieves the a single applied hold by the applied hold Id.
     *
     * @param appliedHoldId a unique identifier of the Hold to be retrieved
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return the Hold requested
     * @throws DoesNotExistException appliedHoldId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException appliedHoldId or contextInfo is missing
     * or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AppliedHoldInfo getAppliedHold(@WebParam(name = "appliedHoldId") String appliedHoldId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list applied holds from to a list of applied hold Ids. The
     * returned list may be in any order and of duplicate Ids are supplied, a
     * unique set may or may not be returned.
     *
     * @param appliedHoldIds list of Hold identifiers
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Holds
     * @throws DoesNotExistException a appliedHoldId in list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException appliedHoldIds, a appliedHoldId in
     * appliedHoldIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AppliedHoldInfo> getAppliedHoldsByIds(@WebParam(name = "appliedHoldIds") List<String> appliedHoldIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list Student Hold Ids by Student Hold Type.
     *
     * @param appliedHoldTypeKey an identifier for a Hold Type
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Hold identifiers
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException appliedHoldTypeKey or contextInfo is
     * missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAppliedHoldIdsByType(@WebParam(name = "appliedHoldTypeKey") String appliedHoldTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves all Student Holds related to the given hold Issue.
     *
     * @param holdIssueId a unique identifier for the Issue
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return the Hold ids to the given Issue
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holdIssueId or contetInfo is missing or
     * null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAppliedHoldIdsByIssue(
            @WebParam(name = "holdIssueId") String holdIssueId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves all applied holds to the given Person.
     *
     * @param personId a unique identifier for the Person
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return the Holds to the given Person
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId of contextInfo is missing or
     * null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AppliedHoldInfo> getAppliedHoldsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of active applied holds, that pertain to the given
     * Person. An active Hold is any open Hold that has had not been released or
     * canceled.
     *
     * @param personId an Id of a Person
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AppliedHoldInfo> getActiveAppliedHoldsByPerson(
            @WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of all Holds by Issue for a Person.
     *
     * @param holdIssueId an Issue
     * @param personId Id of a person
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AppliedHoldInfo> getAppliedHoldsByIssueAndPerson(@WebParam(name = "holdIssueId") String holdIssueId,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of active applied holds, both warning and blocking, by
     * hold Issue for a Person. An active Hold is any open Hold that has had not
     * been released or canceled.
     *
     * @param holdIssueId an Issue
     * @param personId Id of a person
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AppliedHoldInfo> getActiveAppliedHoldsByIssueAndPerson(
            @WebParam(name = "holdIssueId") String holdIssueId,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for applied holds based on the criteria and returns a list of Hold
     * identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Hold Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForAppliedHoldIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for applied holds based on the criteria and returns a list of Holds
     * which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AppliedHoldInfo> searchForAppliedHolds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a applied hold. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained subobjects or expanded to perform all tests related to
     * this object. If an identifier is present for the hold and a record is
     * found for that identifier, the validation checks if the hold can be
     * shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param appliedHoldInfo the hold information to be tested.
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     * appliedHoldInfo
     * @throws MissingParameterException missing validationTypeKey,
     * appliedHoldInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateAppliedHold(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "appliedHoldInfo") AppliedHoldInfo appliedHoldInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Creates a new applied hold.
     *
     * @param personId identifying the person for whom the hold is to be applied
     * @param holdIssueId identifying the exact issue involved
     * @param appliedHoldTypeKey identifying the type of the hold
     * @param appliedHoldInfo Details of the Hold to be created
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of the Hold just created
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read only
     * @throws PermissionDeniedException authorization failure
     */
    public AppliedHoldInfo createAppliedHold(
            @WebParam(name = "personId") String personId,
            @WebParam(name = "holdIssueId") String holdIssueId,
            @WebParam(name = "appliedHoldTypeKey") String appliedHoldTypeKey,
            @WebParam(name = "appliedHoldInfo") AppliedHoldInfo appliedHoldInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing applied hold.
     *
     * @param appliedHoldId Id of Hold to be updated
     * @param appliedHoldInfo Details of updates to the Hold being updated
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of Hold just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read only
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public AppliedHoldInfo updateAppliedHold(@WebParam(name = "appliedHoldId") String appliedHoldId,
            @WebParam(name = "appliedHoldInfo") AppliedHoldInfo appliedHoldInfo,
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
     * Releases a applied hold. A release preserves the record and marks the State as
     * released and sets the released date. A Hold should be released instead of
     * deleted when the record needs to be preserved.
     *
     * @param appliedHoldId a hold
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the modified HoldInfo
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AppliedHoldInfo releaseAppliedHold(@WebParam(name = "appliedHoldId") String appliedHoldId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Deletes an existing applied hold record.
     *
     * @param appliedHoldId the Id of the Hold to be deleted
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteAppliedHold(@WebParam(name = "appliedHoldId") String appliedHoldId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the details of a single hold Issue by it's hold issue Id.
     *
     * @param holdIssueId Unique Id of the Issue to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of the Issue requested
     * @throws DoesNotExistException holdIssueId not found
     * @throws InvalidParameterException invalid holdIssueId
     * @throws MissingParameterException missing holdIssueId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldIssueInfo getHoldIssue(@WebParam(name = "holdIssueId") String holdIssueId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list hold Issues corresponding to a list of issue Ids.
     *
     * @param holdIssueIds list of unique Ids of the Issue to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Issues
     * @throws DoesNotExistException a holdIssueId in list not found
     * @throws InvalidParameterException invalid holdIssueId in list
     * @throws MissingParameterException missing holdIssueIds
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldIssueInfo> getHoldIssuesByIds(@WebParam(name = "holdIssueIds") List<String> holdIssueIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of hold Issues by Type.
     *
     * @param holdIssueTypeKey a Type of the Issue to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Issues of the given Type
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getHoldIssueIdsByType(@WebParam(name = "holdIssueTypeKey") String holdIssueTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of hold Issues that pertain to the given organization.
     *
     * @param organizationId a unique Id of an organization
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Issues
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldIssueInfo> getHoldIssuesByOrg(@WebParam(name = "organizationId") String organizationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Issues based on the criteria and returns a list of Issue
     * identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Issue Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForHoldIssueIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Issues based on the criteria and returns a list of Issues
     * which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of IssueIds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldIssueInfo> searchForHoldIssues(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates an issue. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained subobjects or expanded to perform all tests related to
     * this object. If an identifier is present for the issue and a record is
     * found for that identifier, the validation checks if the issue can be
     * shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param holdIssueInfo the issue information to be tested.
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     * holdIssueInfo
     * @throws MissingParameterException missing validationTypeKey,
     * holdIssueInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHoldIssue(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "holdIssueInfo") HoldIssueInfo holdIssueInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Creates a new hold Issue.
     *
     * @param holdIssueTypeKey indicates the type of issue
     * @param holdIssueInfo Details of the Issue to be created
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of the Issue just created
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read only
     */
    public HoldIssueInfo createHoldIssue(
            @WebParam(name = "holdIssueTypeKey") String holdIssueTypeKey,
            @WebParam(name = "holdIssueInfo") HoldIssueInfo holdIssueInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing hold Issue.
     *
     * @param holdIssueId Id of the Issue to be updated
     * @param holdIssueInfo Details of updates to the Issue being updated
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of the Issue just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException the Issue does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read only
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public HoldIssueInfo updateHoldIssue(@WebParam(name = "holdIssueId") String holdIssueId,
            @WebParam(name = "holdIssueInfo") HoldIssueInfo holdIssueInfo,
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
     * Deletes an existing hold Issue.
     *
     * @param holdIssueId the Id of the Issue to be deleted
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Issue does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DependentObjectsExistException if a hold exists for this issue
     */
    public StatusInfo deleteHoldIssue(@WebParam(name = "holdIssueId") String holdIssueId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DependentObjectsExistException;
}

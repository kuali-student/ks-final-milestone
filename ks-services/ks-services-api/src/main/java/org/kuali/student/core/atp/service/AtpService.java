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

package org.kuali.student.core.atp.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.service.TypeService;
import org.kuali.student.common.service.StateService;

import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.datadictionary.service.DataDictionaryService;


/**
 * Academic Time Period Service Description and Assumptions.
 *
 * This service supports the management of Academic Time Periods and
 * their associated Milestones. The intent is to provide a flexible
 * but structured way to define the various time frames that are used
 * throughout the definition, offering and scheduling of Learning
 * Units. This is a catalogue service with basic operations.
 *
 * Version: 1.0 (Dev)
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */

@WebService(name = "AtpService", targetNamespace = "http://student.kuali.org/wsdl/atp")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface AtpService extends DataDictionaryService, TypeService, StateService {

    /** 
     * Retrieves the details of a single Academic Time Period by atpKey.
     *
     * @param atpKey Unique key of the Academic Time Period to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the Academic Time Period requested
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException invalid atpKey
     * @throws MissingParameterException missing atpKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AtpInfo getAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods that the supplied
     * date falls within inclusive of the start end end date of the
     * ATP.
     *
     * @param searchDate Timestamp to be matched
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods that contain the supplied searchDate
     * @throws InvalidParameterException invalid searchDate
     * @throws MissingParameterException invalid searchDate
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByDate(@WebParam(name = "searchDate") Date searchDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods that are totally
     * contained within the supplied dates. The entire Atp falls
     * within the supplied dates inclusive of the dates.
     *
     * @param startDate Earliest Timestamp
     * @param endDate Latest Timestamp
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods that contain the supplied searchDate
     * @throws InvalidParameterException invalid searchDate
     * @throws MissingParameterException invalid searchDate
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods corresponding to the
     * given list of ATP keys.
     *
     * @param atpKeyList list of ATPs to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Period keys of the given type
     * @throws DoesNotExistException an atpKey in list not found
     * @throws InvalidParameterException invalid atpKey
     * @throws MissingParameterException missing atpKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByKeyList(@WebParam(name = "atpKeyList") List<String> atpKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods of the specified type.
     *
     * @param atpTypeKey ATP type to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return  a list of Academic Time Period keys 
     * @throws InvalidParameterException invalid atpTypeKey
     * @throws MissingParameterException invalid atpTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAtpKeysByType(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of the specified milestone.
     *
     * @param milestoneKey Unique id of the milestone to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of requested milestone
     * @throws DoesNotExistException milestoneKey not found
     * @throws InvalidParameterException invalid milestoneKey
     * @throws MissingParameterException invalid milestoneKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MilestoneInfo getMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestones corresponding to the given list
     * of Milestone keys.
     *
     * @param milestoneKeyList list of Milestones to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the list of milestone keys 
     * @throws DoesNotExistException a milestoneKey in list not found
     * @throws InvalidParameterException invalid milestoneKey
     * @throws MissingParameterException invalid milestibeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getMilestonesByKeyList(@WebParam(name = "milestoneKeyList") List<String> milestoneKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestones of the specified type.
     *
     * @param milstoneTypeKey Milestone type to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return  a list of Milestone keys 
     * @throws InvalidParameterException invalid milestoneTypeKey
     * @throws MissingParameterException invalid milestoneTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getMilestoneKeysByType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of milestones for a specified Academic Time
     * Period.
     *
     * @param atpKey Unique key of the Academic Time Period to be retieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestones for this Academic Time Period
     * @throws InvalidParameterException invalid atpKey
     * @throws MissingParameterException missing atpKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of milestones that fall within a specified
     * set of dates inclusive of the dates.
     *
     * @param startDate Start Date for date span
     * @param endDate End Date for date span
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestones that fall within this set of dates
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameter(s) missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of milestones of a specified type that fall
     * within a specified set of dates inclusive of the dates.
     *
     * @param milestoneTypeKey Milestone type to be retrieved
     * @param startDate Start Date for date range
     * @param endDate End Date for date range
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestones of this milestone type within this set of dates
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByDatesAndType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an academic time period. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the academic time period and a record
     * is found for that identifier, the validation checks if the
     * academic time period can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param atpInfo The academic time period information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, atpInfo
     * @throws MissingParameterException missing validationTypeKey, atpInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateAtp(@WebParam(name = "validationType") String validationType, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Academic Time Period.
     *
     * @param atpKey Key of ATP to be created
     * @param atpInfo Details of ATP to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of ATP just created
     * @throws AlreadyExistsException ATP being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AtpInfo createAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Academic Time Period.
     *
     * @param atpKey Key of ATP to be updated
     * @param atpInfo Details of updates to ATP being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of ATP just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException ATP being updated does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public AtpInfo updateAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Academic Time Period.
     *
     * @param atpKey the key of the ATP to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException ATP being deleted does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a milestone. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the milestone and a record is found
     * for that identifier, the validation checks if the milestone can
     * be shifted to the new values. If a record cannot be found for
     * the identifier, it is assumed that the record does not exist
     * and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the
     * validationType to the current object. This is a slightly
     * different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the
     * server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param milestoneInfo The milestone information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, milestoneInfo
     * @throws MissingParameterException missing validationTypeKey, milestoneInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateMilestone(@WebParam(name = "validationType") String validationType, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a new milestone.
     *
     * @param milestoneKey the Id of milestone to be added
     * @param milestoneInfo Details of milestone to be added
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the newly created milestone
     * @throws AlreadyExistsException Milestone already exists
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MilestoneInfo createMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing milestone.
     *
     * @param milestoneKey the Id of milestone to be updated
     * @param milestoneInfo Details of milestone to be updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the updated milestone
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException Milestone being updated does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of 
     *         date version.date
     */
    public MilestoneInfo updateMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing milestone from all ATPs.
     *
     * @param milestoneKey the Id of milestone to be removed
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException Milestone being removed does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves an ATP Milestone Relationship.
     *
     * @param atpMilestoneRelationId Unique id of the atp milestone relation 
     *        to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of requested Atp milestone relation
     * @throws DoesNotExistException atpMilestoneRelationId not found
     * @throws InvalidParameterException invalid atpMilestonerelationId
     * @throws MissingParameterException invalid atpMilestoneRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AtpMilestoneRelationInfo getAtpMilestoneRelation(@WebParam(name = "atpMilestoneRelationId") String atpMilestoneRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all ATP Milestone Relationships by ATP.
     *
     * @param atpKey Unique key of an ATP
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Atp milestone relationships
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException invalid atpKey
     * @throws MissingParameterException missing atpKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all ATP Milestone Relationships by Milestone.
     *
     * @param milestoneKey Unique key of a Milestone
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Atp milestone relationships
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException invalid milestoneKey
     * @throws MissingParameterException missing milestoneKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an ATP/Milestone relationship. Depending on the value
     * of validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the relationship and a record is
     * found for that identifier, the validation checks if the
     * relationship can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param atpMilestoneRelationshipInfo The ATP Milestone Relationship
     *        to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, atpInfo
     * @throws MissingParameterException missing validationTypeKey, atpInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateAtpMilestoneRelation(@WebParam(name = "validationType") String validationType, @WebParam(name = "atpMilestoneRelationInfo") AtpMilestoneRelationInfo atpMilestoneRelationInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Adds a Milestone to an ATP by creating a relationship.
     *
     * @param atpMilestoneRleationship the relationship
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status
     * @throws AlreadyExistsException Milestone being added already exists
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AtpMilestoneRelationInfo createAtpMilestoneRelation(@WebParam(name = "atpMilestoneRelationInfo") AtpMilestoneRelationInfo atpMilestoneRelationInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an ATP Mielstone Relationship.
     *
     * @param atpMilestoneRelationId the Id of the ATP Milestone Relation to be 
     *        updated
     * @param atpMilestoneRelationInfo the ATP Milstone relation to be updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException Milestone does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of 
     *         date version.date
     */
    public AtpMilestoneRelationInfo updateAtpMilestoneRelation(@WebParam(name = "atpMilestonRelationId") String atpMilestoneRelationId, @WebParam(name = "atpMilestoneRelationInfo") AtpMilestoneRelationInfo atpMilestoneRelationInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Removes an existing milestone from an ATP by deleting the relationship.
     *
     * @param atpMilestoneRelationshipId the Id of ATP Milestone Relatiosnhip
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException Milestone being removed does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteAtpMilestoneRelation(@WebParam(name = "atpMilestonRelationId") String atpMilestoneRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

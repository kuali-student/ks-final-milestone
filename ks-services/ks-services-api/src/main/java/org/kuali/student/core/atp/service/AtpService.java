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
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.AtpSeasonalTypeInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.core.atp.dto.MilestoneTypeInfo;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.datadictionary.service.DataDictionaryService;

/**
 * Academic Time Period Service Description and Assumptions
 *
 * This service supports the management of Academic Time Periods and
 * their associated Milestones. The intent is to provide a flexible
 * but structured way to define the various time frames that are used
 * throughout the definition, offering and scheduling of Learning
 * Units. This is a catalogue service with basic operations.
 *
 * Version: 1.0 (Dev)
 *
 */
@WebService(name = "AtpService", targetNamespace = "http://student.kuali.org/wsdl/atp")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AtpService extends DataDictionaryService {

    /** 
     * Retrieves the list of academic time period types known by this service.
     *
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of academic time period types
     * @throws OperationFailedException unable to complete request
     */
    public List<AtpTypeInfo> getAtpTypes(@WebParam(name = "context") ContextInfo context) throws OperationFailedException;

    /** 
     * Retrieves information about a particular academic time period type.
     *
     * @param atpTypeKey academic time period type identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return academic time period type information
     * @throws DoesNotExistException specified atp type not found
     * @throws InvalidParameterException invalid atpTypeKey
     * @throws MissingParameterException atpTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public AtpTypeInfo getAtpType(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of Academic Time Period Seasonal Types known
     * by this service.
     *
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of academic time period seasonal types
     * @throws OperationFailedException unable to complete request
     */
    public List<AtpSeasonalTypeInfo> getAtpSeasonalTypes(@WebParam(name = "context") ContextInfo context) throws OperationFailedException;

    /** 
     * Retrieves information about a particular academic time period
     * seasonal type.
     *
     * @param atpSeasonalTypeKey academic time period seasonal type identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return academic time period seasonal type information
     * @throws DoesNotExistException specified atp seasonal type not found
     * @throws InvalidParameterException invalid atpSeasonalTypeKey
     * @throws MissingParameterException atpSeasonalTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public AtpSeasonalTypeInfo getAtpSeasonalType(@WebParam(name = "atpSeasonalTypeKey") String atpSeasonalTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of Academic Time Period Duration Types known
     * by this service.
     *
     * @return List of academic time period duration types
     * @throws OperationFailedException unable to complete request
     */
    public List<AtpDurationTypeInfo> getAtpDurationTypes(@WebParam(name = "context") ContextInfo context) throws OperationFailedException;

    /** 
     * Retrieves information about a particular academic time period
     * duration type.
     *
     * @param atpDurationTypeKey academic time period duration type identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return academic time period duration type information
     * @throws DoesNotExistException specified atp duration type not found
     * @throws InvalidParameterException invalid atpDurationTypeKey
     * @throws MissingParameterException atpDurationTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public AtpDurationTypeInfo getAtpDurationType(@WebParam(name = "atpDurationTypeKey") String atpDurationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of milestone types known by this service.
     *
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestone types
     * @throws OperationFailedException unable to complete request
     */
    public List<MilestoneTypeInfo> getMilestoneTypes(@WebParam(name = "context") ContextInfo context) throws OperationFailedException;

    /** 
     * Retrieves information about a particular milestone type.
     *
     * @param milestoneTypeKey milestone type identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return milestone type information
     * @throws DoesNotExistException specified milestone type not found
     * @throws InvalidParameterException invalid milestoneTypeKey
     * @throws MissingParameterException milestoneTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public MilestoneTypeInfo getMilestoneType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of milestone types that are defined for a
     * particular Atp Type.
     *
     * @param atpTypeKey atpTypeKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestone types
     * @throws DoesNotExistException specified atpTypeKey not found
     * @throws InvalidParameterException invalid atpTypeKey
     * @throws MissingParameterException atpTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<MilestoneTypeInfo> getMilestoneTypesForAtpType(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
     * Retrieves the details of a single Academic Time Period by atpKey.
     *
     * @param atpKey Unique key of the Academic Time Period to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the Academic Time Period requested
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException invalid atpKey
     * @throws MissingParameterException invalid atpKey
     * @throws OperationFailedException unable to complete request
     */
    public AtpInfo getAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of Academic Time Periods that the supplied
     * date falls within.
     *
     * @param searchDate Timestamp to be matched
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods that contain the supplied searchDate
     * @throws InvalidParameterException invalid searchDate
     * @throws MissingParameterException invalid searchDate
     * @throws OperationFailedException unable to complete request
     */
    public List<AtpInfo> getAtpsByDate(@WebParam(name = "searchDate") Date searchDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of Academic Time Periods that are totally
     * contained within the supplied dates. The entire Atp falls
     * within the supplied dates.
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
     */
    public List<AtpInfo> getAtpsByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of Academic Time Periods of the specified type.
     *
     * @param atpTypeKey ATP type to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods that contain the supplied date
     * @throws InvalidParameterException invalid atpTypeKey
     * @throws MissingParameterException invalid atpTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<AtpInfo> getAtpsByAtpType(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

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
     */
    public MilestoneInfo getMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
     * @throws MissingParameterException invalid atpKey
     * @throws OperationFailedException unable to complete request
     */
    public List<MilestoneInfo> getMilestonesByAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of milestones that fall within a specified
     * set of dates.
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
     */
    public List<MilestoneInfo> getMilestonesByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of milestones of a specified type that fall
     * within a specified set of dates.
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
     */
    public List<MilestoneInfo> getMilestonesByDatesAndType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Academic Time Period.
     *
     * @param atpTypeKey Type of ATP to be created
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
    public AtpInfo createAtp(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param atpKey Key of ATP to be deleted
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
     * Create a new milestone to an existing Academic Time Period.
     *
     * @param atpKey Key of ATP to be updated
     * @param milestoneKey Id of milestone to be added
     * @param milestoneInfo Details of milestone to be added
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the newly created milestone
     * @throws AlreadyExistsException Milestone being added already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MilestoneInfo createMilestone(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Add an existing milestone to an existing Academic Time Period.
     *
     * @param atpKey Key of ATP to be updated
     * @param milestoneKey Id of milestone to be added
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
    public StatusInfo addMilestone(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing milestone.
     *
     * @param milestoneKey ID of milestone to be updated
     * @param milestoneInfo Details of milestone to be updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the updated milestone
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Milestone being updated does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public MilestoneInfo updateMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Removes an existing milestone from an ATP.
     *
     * @param atpKey Id of ATP
     * @param milestoneKey Id of milestone to be removed
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Status of the operation (success, failed)
     * @throws DoesNotExistException Milestone being removed does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeMilestone(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes an existing milestone from all ATPs.
     *
     * @param milestoneKey Id of milestone to be removed
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Status of the operation (success, failed)
     * @throws DoesNotExistException Milestone being removed does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

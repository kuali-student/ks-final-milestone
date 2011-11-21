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

package org.kuali.student.r2.core.atp.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

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

@WebService(name = "AtpService", serviceName = "AtpService", portName = "AtpService", targetNamespace = "http://student.kuali.org/wsdl/atp")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface AtpServiceAlt extends DataDictionaryService, TypeService, StateService {

    /** 
     * Retrieves the details of a single Academic Time Period by atpKey.
     *
     * @param atpKey Unique key of the Academic Time Period to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the Academic Time Period requested
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException invalid atpKey or contextInfo
     * @throws MissingParameterException missing atpKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AtpInfo getAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods corresponding to the
     * given list of ATP keys.
     *
     * @param atpKeys list of ATPs to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Period keys of the given type
     * @throws DoesNotExistException an atpKey in list not found
     * @throws InvalidParameterException invalid atpKey or contextInfo
     * @throws MissingParameterException missing atpKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByKeys(@WebParam(name = "atpKeys") List<String> atpKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods of the specified type.
     *
     * @param atpTypeKey ATP type to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return  a list of Academic Time Period keys 
     * @throws InvalidParameterException invalid atpTypeKey or contextInfo
     * @throws MissingParameterException missing atpTypeKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAtpKeysByType(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods that the supplied
     * date falls within inclusive of the start end end date of the
     * ATP.
     *
     * @param date Timestamp to be matched
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods that contain the supplied 
     *         date
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing date or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByDate(@WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods that the supplied
     * date falls within inclusive the start and end date of the
     * ATP and whose type matches the specified type key.
     *
     * @param date Timestamp to be matched
     * @param atpTypeKey typeKey to be matched
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods
     * @throws InvalidParameterException invalid atpTypeKey, or
     *         contextInfo
     * @throws MissingParameterException missing date, atpTypeKey, or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByDateAndType(@WebParam(name = "date") Date date, @WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods that are totally
     * contained within the supplied dates. The entire Atp falls
     * within the supplied dates inclusive of the dates.
     *
     * @param startDate start date of range
     * @param endDate end date of range
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Academic Time Periods 
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing startDate, endDate,
     *         or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods that are totally
     * contained within the supplied dates. The entire Atp falls
     * within the supplied dates inclusive of the dates and whose
     * typeKey matches the specified type.
     *
     * @param startDate start date of range
     * @param endDate end date of range
     * @param atpTypeKey a key for an ATP type
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Academic Time Periods 
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing startDate, endDate, 
     *         atpTypeKey, or conetxtInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByDatesAndType(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods whose start dates
     * fall within the supplied date range, inclusive of the start and
     * end dates on the range.
     *
     * @param dateRangeStart start date of range
     * @param dateRangeEnd end date of range
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing datRangeStart,
     *         dateRangeEnd, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByStartDateRange(@WebParam(name = "dateRangeStart") Date dateRangeStart, @WebParam(name = "dateRangeEnd") Date dateRangeEnd, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods whose start dates
     * fall within the supplied date range, inclusive of the start and
     * end dates on the range and whose type matches the specified
     * type.
     *
     * @param dateRangeStart start date of range
     * @param dateRangeEnd end date of range
     * @param atpTypeKey typeKey to be matched
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of Academic Time Periods
     * @throws InvalidParameterException invalid atpTypeKey or contextInfo
     * @throws MissingParameterException missing dateRangeStart,
     *         dateRangeEnd, atpTypeKey, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByStartDateRangeAndType(@WebParam(name = "dateRangeStart") Date dateRangeStart, @WebParam(name = "dateRangeEnd") Date dateRangeEnd, @WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;    

    /**
     * Searches for Atps based on the criteria and returns a list
     * of Atp identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of Atp keys
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing croteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForAtpKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Atps based on the criteria and returns a list of
     * Atps which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of Atps
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing croteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> searchForAtps(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     *         atpInfo, or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         atpInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateAtp(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new Academic Time Period.
     *
     * @param atpKey Key of ATP to be created
     * @param atpInfo Details of ATP to be created
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of ATP just created
     * @throws AlreadyExistsException ATP being created already exists
     * @throws DataValidationErrorException one or more values invalid
     *         for this operation
     * @throws InvalidParameterException invalid atpKey, atpInfo, or
     *         contextInfo
     * @throws MissingParameterException missing atpKey, atpInfo, or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public AtpInfo createAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing Academic Time Period.
     *
     * @param atpKey Key of ATP to be updated
     * @param atpInfo Details of updates to ATP being updated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of ATP just updated
     * @throws DataValidationErrorException One or more values invalid 
     *         for this operation
     * @throws DoesNotExistException ATP being updated does not exist
     * @throws InvalidParameterException invalid atpKey, atpInfo, or
     *         contextInfo
     * @throws MissingParameterException missing atpKey, atpInfo, or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException The action was attempted on an out 
     *         of date version.
     */
    public AtpInfo updateAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing Academic Time Period.
     *
     * @param atpKey the key of the ATP to be deleted
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException ATP being deleted does not exist
     * @throws InvalidParameterException invalid atpKey or contextInfo
     * @throws MissingParameterException missing atpKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves an ATP Relationship.
     *
     * @param atpAtpRelationId a unique id of the atp atp relation 
     *        to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of requested Atp atp relation
     * @throws DoesNotExistException atpAtpRelationId not found
     * @throws InvalidParameterException invalid atpAtprelationId or
     *         contextInfo
     * @throws MissingParameterException missing atpAtpRelationId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AtpAtpRelationInfo getAtpAtpRelation(@WebParam(name = "atpAtpRelationId") String atpAtpRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of AtpAtpRelations corresponding to the given list
     * of identifiers.
     *
     * @param atpAtpRelationIds list of AtpAtpRelations to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the list of AtpAtpRelations
     * @throws DoesNotExistException an atpAtpRelationId in list not found
     * @throws InvalidParameterException invalid atpAtprelationId or
     *         contextInfo
     * @throws MissingParameterException missing atpAtpRelationId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(@WebParam(name = "atpAtpRelationIds") List<String> atpAtpRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of AtpAtpRelation Ids of the specified type.
     *
     * @param atpAtpRelationTypeKey Atp atp relation type to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return  a list of AtpAtpRelation identifiers
     * @throws InvalidParameterException invalid atpRelationTypeKey or
     *         contextInfo
     * @throws MissingParameterException missing atpRelationTypeKey or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAtpAtpRelationIdsByType(@WebParam(name = "atpAtpRelationTypeKey") String atpAtpRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all ATP Relationships by ATP. Any relationship to the
     * given ATP is retrieved independent of which side of the
     * relationship the ATP resides.
     *
     * @param atpKey  a unique key of an ATP
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Atp atp relationships
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException invalid atpKey or contextInfo
     * @throws MissingParameterException missing atpKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves ATP relationships by both Atp and the AtpAtpRelation
     * Type.
     * 
     * @param atpKey a unique key for an ATP
     * @param relationTypeKey a unique key for an ATP ATP relation
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of ATP-ATP relations
     * @throws DoesNotExistException atpKey does not exist
     * @throws InvalidParameterException invalid atpKey,
     *         atpRelationTypeKey, or contextInfo
     * @throws MissingParameterException invalid atpKey,
     *         atpRelationTypeKey, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure occurred
     */
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpRelationTypeKey") String atpRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException; 

    /**
     * Searches for AtpAtpRelations based on the criteria and returns
     * a list of AtpAtpRelation identifiers which match the search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of AtpAtpRelation Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForAtpAtpRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AtpAtpRelations based on the criteria and returns
     * a list of AtpAtpRelations which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of AtpAtpRelations
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an ATP/ATP relationship. Depending on the value
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
     * @param atpKey a peer of the relationship
     * @param atpPeerKey a peer of the relationship
     * @param atpAtpRelationInfo The ATP Relationship
     *        to be tested.
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey, atpKey, or
     *         atpPeerKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     *         atpKey, atpPeerKey, atpAtpRelationInfo, or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         atpKey, atpPeerKey, atpAtpRelationInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateAtpAtpRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpPeerKey") String atpPeerKey, @WebParam(name = "atpAtpRelationInfo") AtpAtpRelationInfo atpAtpRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates an ATP relationship.
     *
     * @param atpKey a peer of the relationship
     * @param atpPeerKey a peer of the relationship
     * @param atpAtpRelationInfo the relationship to be created
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the atp atp relation that was created
     * @throws AlreadyExistsException atp relation added already exists
     * @throws DataValidationErrorException if the relation fails
     *         validation checks
     * @throws DoesNotExistException atpKey or atpPeerKey not found
     * @throws InvalidParameterException invalid atpKey, atpPeerKey,
     *         atpAtpRelationInfo, or contextInfo
     * @throws MissingParameterException missing atpKey, atpPeerKey,
     *         atpAtpRelationInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public AtpAtpRelationInfo createAtpAtpRelation(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpPeerKey") String atpPeerKey, @WebParam(name = "atpAtpRelationInfo") AtpAtpRelationInfo atpAtpRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an ATP Milestone Relationship.
     *
     * @param atpAtpRelationId the Id of the ATP Relation to be 
     *        updated
     * @param atpAtpRelationInfo the ATP relation to be updated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status
     * @throws DataValidationErrorException One or more values invalid 
     *         for this operation
     * @throws DoesNotExistException atpRelationId does not exist
     * @throws InvalidParameterException invalid atpAtpRelationId,
     *         atpAtpRelationInfo or contextInfo
     * @throws MissingParameterException missing atpAtpRelationId,
     *         atpAtpRelationInfo or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException The action was attempted on an
     *         out of date version
     */
    public AtpAtpRelationInfo updateAtpAtpRelation(@WebParam(name = "atpAtpRelationId") String atpAtpRelationId, @WebParam(name = "atpAtpRelationInfo") AtpAtpRelationInfo atpAtpRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Removes an existing ATP relationship.
     *
     * @param atpAtpRelationId the Id of relatiosnhip
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException atp relation being removed does
     *         not exist
     * @throws InvalidParameterException invalid atpAtpRelationId or
     *         contextInfo
     * @throws MissingParameterException missing atpAtpRelationId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteAtpAtpRelation(@WebParam(name = "atpAtpRelationId") String atpAtpRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of the specified milestone.
     *
     * @param milestoneId Unique id of the milestone to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a Milestone
     * @throws DoesNotExistException milestoneId not found
     * @throws InvalidParameterException invalid milestoneId or
     *         contextInfo
     * @throws MissingParameterException missing milestoneId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MilestoneInfo getMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestones corresponding to the given list
     * of Milestone Ids.
     *
     * @param milestoneIds list of Milestones to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the list of milestone ids 
     * @throws DoesNotExistException a milestoneId in list not found
     * @throws InvalidParameterException invalid milestoneId or
     *         contextInfo
     * @throws MissingParameterException missing milestoneId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByIds(@WebParam(name = "milestoneIds") List<String> milestoneIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestone Ids of the specified type.
     *
     * @param milestoneTypeKey Milestone type to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return  a list of Milestone keys 
     * @throws InvalidParameterException invalid milestoneTypeKey or
     *         contextInfo
     * @throws MissingParameterException missing milestoneTypeKey or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getMilestoneIdsByType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of milestones that fall within a specified
     * set of dates inclusive of the dates.
     *
     * @param startDate Start Date for date span
     * @param endDate End Date for date span
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestones that fall within this set of dates
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing startDate, endDate,
     *         or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of milestones for a specified Academic Time
     * Period.
     *
     * @param atpKey Unique key of the Academic Time Period to be retieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestones for this Academic Time Period
     * @throws InvalidParameterException invalid atpKey or contextInfo
     * @throws MissingParameterException missing atpKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of milestones mapped to an ATP that fall
     * within a specified set of dates inclusive of the dates.
     *
     * @param atpKey a key for an ATP
     * @param startDate Start Date for date range
     * @param endDate End Date for date range
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestones of this milestone type within this set 
     *         of dates
     * @throws DoesNotExistException atp not found
     * @throws InvalidParameterException invalid atpKey or contextInfo
     * @throws MissingParameterException missing atpKey, startDate,
     *         endDate, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByDatesForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of milestones of a specified type that 
     * are mapped to a given Atp.
     *
     * @param atpKey a key for an ATP
     * @param milestoneTypeKey Milestone type to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of milestones of this milestone type within this set 
     *         of dates
     * @throws DoesNotExistException atp not found
     * @throws InvalidParameterException invalid atpKey, milsetoneTypeKey,
     *         or contextInfo
     * @throws MissingParameterException missing atpKey, milsetoneTypeKey,
     *         or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByTypeForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Milestones Ids based on the criteria and returns a
     * list of Milestone identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of Milestone Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForMilestoneIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Milestones based on the criteria and returns a list of
     * Milestones which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of Milestones
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> searchForMilestones(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     *         milestoneInfo, or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         milestoneInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateMilestone(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Create a new milestone.
     *
     * @param milestoneInfo Details of milestone to be added
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the newly created milestone
     * @throws AlreadyExistsException Milestone already exists
     * @throws DataValidationErrorException One or more values invalid 
     *         for this operation.
     * @throws InvalidParameterException invalid milestoneInfo or
     *         contextInfo
     * @throws MissingParameterException missing milestoneInfo or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public MilestoneInfo createMilestone(@WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing milestone.
     *
     * @param milestoneId the Id of milestone to be updated
     * @param milestoneInfo Details of milestone to be updated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Details of the updated milestone
     * @throws DataValidationErrorException One or more values invalid 
     *         for this operation
     * @throws DoesNotExistException Milestone being updated does not exist
     * @throws InvalidParameterException invalid milestoneId,
     *         milestoneInfo, or contextInfo
     * @throws MissingParameterException missing milestoneId,
     *         milestoneInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException The action was attempted on an out of 
     *         date version
     */
    public MilestoneInfo updateMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing milestone from all ATPs.
     *
     * @param milestoneId the Id of milestone to be removed
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException Milestone being removed does not exist
     * @throws InvalidParameterException invalid milestoneId or contextInfo
     * @throws MissingParameterException missing milestoneId or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Adds a Milestone to an ATP.
     *
     * @param milestoneId an Id for a Milestone
     * @param atpKey a key for an ATP
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status
     * @throws AlreadyExistsException milestoneId already related to
     *         atpKey
     * @throws DoesNotExistException milestoneId or atpKey not found
     * @throws InvalidParameterException invalid milestoneId, atpKey,
     *         or contextInfo
     * @throws MissingParameterException missing milestoneId, atpKey,
     *         or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addMilestoneToAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Removes a Milestone from an ATP.
     *
     * @param milestoneId an Id for a Milestone
     * @param atpKey a key for an ATP
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status
     * @throws DoesNotExistException milestoneId or atpKey not found
     *         or not related
     * @throws InvalidParameterException invalid milestoneId, atpKey,
     *         or contextInfo
     * @throws MissingParameterException missing milestoneId, atpKey,
     *         or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeMilestoneFromAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

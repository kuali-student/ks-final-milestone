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

public interface AtpServiceAlt 
    extends DataDictionaryService, TypeService, StateService {

    /** 
     * Retrieves a single Academic Time Period by ATP key.
     *
     * @param atpKey a unique identifier of the Academic Time Period
     *        to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Academic Time Period requested
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException contextInfo not valid
     * @throws MissingParameterException atpKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AtpInfo getAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods from a list of ATP
     * keys. The returned list may be in any order and if duplicate
     * keys are supplied, a unique set may or may not be returned.
     *
     * @param atpKeys a list of ATP keys
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of ATPs
     * @throws DoesNotExistException an atpKey in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpKeys, a key in atpKeys, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByKeys(@WebParam(name = "atpKeys") List<String> atpKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods by ATP Type.
     *
     * @param atpTypeKey an identifier for the ATP type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Period keys matching atpTypeKey
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException atpTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAtpKeysByType(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods where the supplied
     * date falls within the start and end date of the ATP inclusive
     * of the date.
     *
     * @param date a date
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods that contain the supplied 
     *         date
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException date or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AtpInfo> getAtpsByDate(@WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods where the supplied
     * date falls within the start and end date of the ATP, inclusive
     * of the date, and whose type matches the specified ATP type.
     *
     * @param date a date
     * @param atpTypeKey an identifier for an ATP Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods of the ATP Type that
     *         contain the supplied date
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException date, atpTypeKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByDateAndType(@WebParam(name = "date") Date date, @WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods that are totally
     * contained within the supplied dates. The entire ATP falls
     * within the supplied dates inclusive of the dates.
     *
     * @param startDate start date of range
     * @param endDate end date of range
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods within the supplied dates
     * @throws InvalidParameterException contextInfo is not valid or
     *         the startDate is greater than the endDate
     * @throws MissingParameterException startDate, endDate, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods that are totally
     * contained within the supplied dates and whose type matches the
     * supplied ATP type. The entire ATP falls within the supplied
     * dates inclusive of the dates.
     *
     * @param startDate start date of range
     * @param endDate end date of range
     * @param atpTypeKey an identifier for an ATP Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods of the supplied ATP
     *         Type within the supplied dates
     * @throws InvalidParameterException contextInfo is not valid or
     *         startDate is greater than endDate
     * @throws MissingParameterException startDate, endDate,
     *         atpTypeKey, or conetxtInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByDatesAndType(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Time Periods whose start dates
     * fall within the supplied date range, inclusive of the start and
     * end dates on the supplied range.
     *
     * @param dateRangeStart start date of range
     * @param dateRangeEnd end date of range
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods with start dates within
     *         the supplied dates
     * @throws InvalidParameterException contextInfo is not valid or
     *         startDate is greater than endDate
     * @throws MissingParameterException datRangeStart, dateRangeEnd,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByStartDateRange(@WebParam(name = "dateRangeStart") Date dateRangeStart, @WebParam(name = "dateRangeEnd") Date dateRangeEnd, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Academic Time Periods whose start dates
     * fall within the supplied date range, inclusive of the start and
     * end dates on the range and whose type matches the specified
     * ATP Type.
     *
     * @param dateRangeStart start date of range
     * @param dateRangeEnd end date of range
     * @param atpTypeKey an identifier for an ATP Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods with start dates within
     *         the supplied dates and of the given ATP Type
     * @throws InvalidParameterException contextInfo is not valid or
     *         dateRangeStart is greater than dateRangeEnd
     * @throws MissingParameterException dateRangeStart, dateRangeEnd,
     *         atpTypeKey, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByStartDateRangeAndType(@WebParam(name = "dateRangeStart") Date dateRangeStart, @WebParam(name = "dateRangeEnd") Date dateRangeEnd, @WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;    

    /**
     * Searches for Academic Time Periods that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of ATP keys matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAtpKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Academic Time Periods that meet the given search
     * criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of ATPs matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> searchForAtps(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an Academic Time Period. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this ATP. If an
     * identifier is present for the ATP (and/or one of its contained
     * sub-objects) and a record is found for that identifier, the
     * validation checks if the ATP can be shifted to the new
     * values. If a an identifier is not present or a record does not
     * exist, the validation checks if the ATP with the given data can
     * be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param atpTypeKey the identifier for the ATP Type to be validated
     * @param atpInfo the identifier for the ATP to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or atpTypeKey
     *         is not found
     * @throws InvalidParameterException atpInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException validationTypeKey, atpTypeKey
     *         atpInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateAtp(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new Academic Time Period. The ATP Type and Meta
     * information may not be set in the supplied data object.
     *
     * @param atpKey a unique key for the new ATP
     * @param atpTypeKey the identifier for the Type of ATP to be created
     * @param atpInfo the data with which to create the ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new ATP
     * @throws AlreadyExistsException atpKey already exists
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException atpTypeKey does not exist or is
     *         not supported
     * @throws InvalidParameterException atpInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException atpKey, atpTypeKey, atpInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public AtpInfo createAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing Academic Time Period. The ATP key, Type,
     * and Meta information may not be changed.
     *
     * @param atpKey the identifier for the ATP to be updated
     * @param atpInfo the new data for the ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated ATP
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException atpKey is not found
     * @throws InvalidParameterException atpInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException atpKey, atpInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public AtpInfo updateAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing Academic Time Period.
     *
     * @param atpKey the identifier for the ATP to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException atpKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves an ATP Relationship.
     *
     * @param atpAtpRelationId a unique id of the atp atp relation 
     *        to be retrieved
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of AtpAtpRelation identifiers
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
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
     * Retrieves a single Milestone by a Milestone Id.
     *
     * @param milestoneId a unique iddentifier for the milestone to be
     *        retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Milestone requested
     * @throws DoesNotExistException milestoneId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public MilestoneInfo getMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestones from a list of Milestone
     * Ids. The returned list may be in any order and if duplicate Ids
     * are supplied, a unique set may or may not be returned.
     *
     * @param milestoneIds a list of Milestone identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones
     * @throws DoesNotExistException a milestoneId in the list was not
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneIds, an Id in the
     *         milestoneIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesByIds(@WebParam(name = "milestoneIds") List<String> milestoneIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestone Ids by Milestone Type.
     *
     * @param milestoneTypeKey an identifier for a Milestone Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestone Ids matching milestoneTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getMilestoneIdsByType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Milestones that fall within a specified
     * set of dates inclusive of the dates.
     *
     * @param startDate start of date range
     * @param endDate end of date range
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones that fall within this set of dates
     * @throws InvalidParameterException contextInfo is not valid or
     *         startDate is greater than endDate
     * @throws MissingParameterException startDate, endDate, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Milestones for a specified Academic Time
     * Period.
     *
     * @param atpKey an identifier for an Academic Time Period
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones for the ATP or an empty list if
     *         none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpKey or contextInfo is 
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestones for a specified Academic Time
     * Period that fall within a specified set of dates inclusive of
     * the dates.
     *
     * @param atpKey an identifier for an ATP
     * @param startDate start of date range
     * @param endDate end of date range
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones for the ATP in the specified date
     *         range or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid or
     *         startDate is greater than end date
     * @throws MissingParameterException atpKey, startDate, endDate,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesByDatesForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestones of a specified Milestone Type for
     * an Acdemic Time Period.
     *
     * @param atpKey an identifier for an ATP
     * @param milestoneTypeKey an identifier for a Milestone Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones of the Milestone Type for the ATP
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpKey, milsetoneTypeKey,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesByTypeForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Milestone Ids that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Milestone Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo
     *         is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForMilestoneIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Milestones that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Milestones matching the criteria
     * @throws InvalidParameterException criteria or contextInfo
     *         is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> searchForMilestones(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Milestone. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current Milestone and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * object. If an identifier is present for the Milestone (and/or
     * one of its contained sub-obejcts) and a record is found for
     * that identifier, the validation checks if the Milestone can be
     * shifted to the new values. If an identifier is not present or a
     * record does not exist, the validation checks if the object with
     * the given data can be created.  
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param milestoneTypeKey the identifier for the Milestone Type
     *        to be validated
     * @param milestoneInfo the milestone to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or
     *         milestoneTypeKey is not found
     * @throws InvalidParameterException milestoneInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException validationTypeKey,
     *         milestoneTypeKey, milestoneInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateMilestone(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Create a new Milestone. The Milestone Id, Type, and Meta
     * information may not be set in the supplied data object.
     *
     * @param milestoneTypeKey the identifier for the Type of
     *        Milestone to be created
     * @param milestoneInfo the data with which to create the Milestone
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new Milestone
     * @throws DataValidationErrorException supplied data is invalid
     * @throws InvalidParameterException milestoneInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException milestoneTypeKey,
     *         milestoneInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public MilestoneInfo createMilestone(@WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing Milestone. The Milestone Id, Type, and Meta
     * information may not be changed.
     *
     * @param milestoneId the identifier for the Milestone to be updated
     * @param milestoneInfo the new data for the Milestone
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated Milestone
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException milestoneId is not found
     * @throws InvalidParameterException milestoneInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException milestoneId, milestoneInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public MilestoneInfo updateMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing Milestone.
     *
     * @param milestoneId the identifier for the Milestone to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the delete operation. This must always be true.
     * @throws DoesNotExistException milestoneId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Adds a Milestone to an ATP.
     *
     * @param milestoneId an identifier for a Milestone
     * @param atpKey an identifier for an ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the mapping operation. This must always be true.
     * @throws AlreadyExistsException milestoneId is already related to
     *         atpKey
     * @throws DoesNotExistException milestoneId or atpKey is not found
     * @throws InvalidParameterException contextInfo is not valud
     * @throws MissingParameterException milestoneId, atpKey,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo addMilestoneToAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Removes a Milestone from an ATP.
     *
     * @param milestoneId an identifier for a Milestone
     * @param atpKey an identifier for an ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the unmapping operation. This must always be true.
     * @throws DoesNotExistException milestoneId or atpKey is not found
     *         or milestoneId is not related to atpKey
     * @throws InvalidParameterException contextInfo is not valud
     * @throws MissingParameterException milestoneId, atpKey,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo removeMilestoneFromAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

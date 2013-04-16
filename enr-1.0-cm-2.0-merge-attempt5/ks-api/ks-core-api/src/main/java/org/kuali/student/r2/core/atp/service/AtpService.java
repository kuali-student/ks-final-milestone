/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.atp.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
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
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.search.service.SearchService;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * Academic Time Period Service Description and Assumptions. This
 * service supports the management of Academic Time Periods and their
 * associated Milestones. The intent is to provide a flexible but
 * structured way to define the various time frames that are used
 * throughout the definition, offering and scheduling of Learning
 * Units.
 *
 *
 * @Version 1.0 (Dev)
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */

@WebService(name = "AtpService", serviceName = "AtpService", portName = "AtpService", targetNamespace = "http://student.kuali.org/wsdl/atp")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AtpService extends SearchService {

    //
    // Lookup Methods for ATP Id Entity Pattern.
    //

    /**
     * Retrieves a single Academic Time Period by ATP id.
     *
     * @param atpId the identifier of the Academic Time Period to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Academic Time Period requested
     * @throws DoesNotExistException atpId is not found
     * @throws InvalidParameterException contextInfo not valid
     * @throws MissingParameterException atpId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AtpInfo getAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Academic Time Periods from a list of ATP
     * Ids. The returned list may be in any order and if duplicate Ids
     * are supplied, a unique set may or may not be returned.
     *
     * @param atpIds a list of ATP Ids
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of ATPs
     * @throws DoesNotExistException an atpId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpIds, an id in atpIds, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByIds(@WebParam(name = "atpIds") List<String> atpIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Academic Time Periods by ATP Type.
     *
     * @param atpTypeKey an identifier for the ATP type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Period Ids matching atpTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException atpTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAtpIdsByType(@WebParam(name = "atpTypeKey") String atpTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of ATPss by Code. Typically, an ATP Code is unique.
     *
     * @param code an ATP Code
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of ATPs with the given ATP Code
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException code or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public List<AtpInfo> getAtpsByCode(@WebParam(name = "code") String code, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Special ATP lookup methods by date.
    //

    /**
     * Retrieves a list of Academic Time Periods where the supplied
     * date falls within the start and end date of the ATP inclusive
     * of the date.
     *
     * @param date a date
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods that contain the supplied date
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException date or contextInfo is missing or null
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
     * @throws InvalidParameterException contextInfo is not valid or the
     *         startDate is greater than the endDate
     * @throws MissingParameterException startDate, endDate, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getAtpsByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Academic Time Periods that are totally
     * contained within the supplied dates and whose type matches the
     * supplied ATP type.  The entire ATP falls within the supplied
     * dates inclusive of the dates.
     *
     * @param startDate start date of range
     * @param endDate end date of range
     * @param atpTypeKey an identifier for an ATP Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Academic Time Periods of the supplied ATP
     *         Type within the supplied dates
     * @throws InvalidParameterException contextInfo is not valid or startDate
     *         is greater than endDate
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
     * end dates on the range and whose type matches the specified ATP
     * Type.
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
     * Retrieves a list of ATPs for a specified Milestone
     *
     * @param milestoneId an identifier for a Milestone
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of ATPs for the Milestone or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> getATPsForMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Search methods for ATP id Entity Pattern.
    //

    /**
     * Searches for Academic Time Period Ids that meet the given
     * search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of ATP identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAtpIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Academic Time Periods that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of ATPs matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpInfo> searchForAtps(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // CRUD methods for ATP Id Entity Pattern.
    //

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
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException validationTypeKey or atpTypeKey
     *         is not found
     * @throws InvalidParameterException atpInfo or contextInfo is not valid
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
     * @param atpTypeKey the type of the atp 
     * @param atpInfo the data with which to create the ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new ATP
     * @throws DataValidationErrorException supplied data is invalid
     * @throws InvalidParameterException atpInfo or contextInfo is not valid
     * @throws MissingParameterException atpId, atpTypeKey, atpInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public AtpInfo createAtp(@WebParam (name = "atpTypeKey") String atpTypeKey,
                             @WebParam(name = "atpInfo") AtpInfo atpInfo,
                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Academic Time Period. The ATP id, Type, and
     * Meta information may not be changed.
     *
     * @param atpId the identifier for the ATP to be updated
     * @param atpInfo the new data for the ATP
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated ATP
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException atpId is not found
     * @throws InvalidParameterException atpInfo or contextInfo is not valid
     * @throws MissingParameterException atpId, atpInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public AtpInfo updateAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "atpInfo") AtpInfo atpInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Academic Time Period.
     *
     * @param atpId the identifier for the ATP to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException atpId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Lookup methods for AtpAtpRelation Symmetrical Relationship Pattern.
    //

    /**
     * Retrieves a single AtpAtpRelation by AtpAtpRelation Id.
     *
     * @param atpAtpRelationId the identifier for the AtpAtpRelation
     *        to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the AtpAtpRelation requested
     * @throws DoesNotExistException atpAtpRelationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpAtpRelationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AtpAtpRelationInfo getAtpAtpRelation(@WebParam(name = "atpAtpRelationId") String atpAtpRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AtpAtpRelations from a list of
     * AtpAtpRelation Ids.  The returned list may be in any order and
     * if duplicate Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param atpAtpRelationIds a list of AtpAtpRelation identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of AtpAtpRelations
     * @throws DoesNotExistException an atpAtpRelationId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpAtpRelationIds, an
     *         atpAtpRelationId in the atpAtpRelationIds, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(@WebParam(name = "atpAtpRelationIds") List<String> atpAtpRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AtpAtpRelation Ids by AtpAtpRelation Type.
     *
     * @param atpAtpRelationTypeKey an identifier for an AtpAtpRelation Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of AtpAtpRelation identifiers matching
     *         atpAtpRelationTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpAtpRelationTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAtpAtpRelationIdsByType(@WebParam(name = "atpAtpRelationTypeKey") String atpAtpRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all AtpAtpRelations to the given ATP independent of
     * which side of the relationship the ATP resides.
     *
     * @param atpId the identifier for the ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of AtpAtprelations to the given ATP or an empty list if
     *         none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all AtpAtpRelations between the given ATPs.
     *
     * @param atpId the identifier for the ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of AtpAtpRelations between the given ATPs or an empty list
     *         if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpId, atpPeerId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(@WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AtpAtpRelations of the specified
     * AtpAtpRelationType for an ATP. (these parameters are
     * backwards).
     *
     * @param atpId the identifier for an ATP
     * @param atpAtpRelationTypeKey the identifier for an AtpAtpRelationType
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of AtpAtpRelations of the specified AtpAtpRelationType for
     *         the given ATP or an empty list if none found
     * @throws InvalidParameterException contextInfo is notvalid
     * @throws MissingParameterException atpId, atpAtpRelationTypeKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "atpAtpRelationTypeKey") String atpAtpRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Search methods for AtpAtpRelation Symmetrical Relationship Pattern.
    //

    /**
     * Searches for AtpAtpRelations that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of AtpAtpRelation identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAtpAtpRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AtpAtpRelations that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of AtpAtpRelations matching the criteria
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // CRUD methods for AtpAtpRelation Symmetrical Relationship Pattern.
    //

    /**
     * Validates an AtpAtpRelation. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current AtpAtpRelation and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * AtpAtpRelation. If an identifier is present for the
     * AtpAtpRelation (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the AtpAtpRelation can be shifted to the new values. If an
     * identifier is not present or a record cannot be found for the
     * identifier, the validation checks if the object with the given
     * data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param atpId the identifier for an ATP
     * @param atpPeerId a the identifier for the ATP peer
     * @param atpAtpRelationTypeKey the identifier for the AtpAtpRelation Type
     * @param atpAtpRelationInfo the AtpAtpRelation to be validated
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException validationTypeKey, atpId,
     *         atpPeerId, or atpAtpRelationTypeKey is not found
     * @throws InvalidParameterException atpAtpRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, atpId,
     *         atpPeerId, atpAtpRelationTypeKey, atpAtpRelationInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateAtpAtpRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "atpId") String atpId, @WebParam(name = "atpPeerId") String atpPeerId, @WebParam(name = "atpAtprelationTypeKey") String atpAtpRelationTypeKey, @WebParam(name = "atpAtpRelationInfo") AtpAtpRelationInfo atpAtpRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new AtpAtpRelation. The AtpAtpRelation Id, Type, ATP
     * Ids, and Meta information may not be set in the supplied data.
     *
     * @param atpId a peer of the relationship
     * @param relatedAtpId a peer of the relationship
     * @param atpAtpRelationTypeKey type of relationship between the two
     * @param atpAtpRelationInfo the relationship to be created
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the new AtpAtpRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException atpId, atpPeerId, or
     *         atpAtpRelationTypeKey is not found
     * @throws InvalidParameterException atpAtpRelationInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException atpId, atpPeerId,
     *         atpAtpRelationTypeKey, atpAtpRelationInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public AtpAtpRelationInfo createAtpAtpRelation(@WebParam(name = "atpId") String atpId,
                                                   @WebParam(name = "relatedAtpId") String relatedAtpId,
                                                   @WebParam(name = "atpAtpRelationTypeKey") String atpAtpRelationTypeKey,
                                                   @WebParam(name = "atpAtpRelationInfo") AtpAtpRelationInfo atpAtpRelationInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an ATP Milestone Relationship. The AtpAtpRelation Id,
     * Type, ATP Ids, and Meta information may not be changed.
     *
     * @param atpAtpRelationId the identifier for the AtpAtpRelation updated
     * @param atpAtpRelationInfo the new data for the AtpAtpRelation
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated AtpAtpRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException atpAtpRelationId is not found
     * @throws InvalidParameterException atpAtpRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException atpAtpRelationId,
     *         atpAtpRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or the action
     *         was attempted on an out of date version
     */
    public AtpAtpRelationInfo updateAtpAtpRelation(@WebParam(name = "atpAtpRelationId") String atpAtpRelationId, @WebParam(name = "atpAtpRelationInfo") AtpAtpRelationInfo atpAtpRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing AtpAtpRelation.
     *
     * @param atpAtpRelationId the identifier for the AtpAtpRelation
     *        to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the delete operation. This must always be true.
     * @throws DoesNotExistException atpAtprelationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpAtpRelationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteAtpAtpRelation(@WebParam(name = "atpAtpRelationId") String atpAtpRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Lookup methods for Milestone Id Entity Pattern.
    //

    /**
     * Retrieves a single Milestone by a Milestone Id.
     *
     * @param milestoneId the identifier for the Milestone to be retrieved
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
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of Milestones
     * @throws DoesNotExistException a milestoneId in the list was not found
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
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of Milestone identifiers matching milestoneTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getMilestoneIdsByType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Milestones that fall within a specified set
     * of dates inclusive of the dates.
     *
     * If the milestone is a date range then it should be selected if it overlaps 
     * any part of the specified start and end dates.
     *
     * Should follow these rules for storing and querying
     * https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
     *
     * @param startDate start of date range
     * @param endDate end of date range
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of Milestones that fall within this set of dates
     * @throws InvalidParameterException contextInfo is not valid or startDate
     *         is greater than endDate
     * @throws MissingParameterException startDate, endDate, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesByDates(@WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Milestones for a specified Academic Time
     * Period.
     *
     * @param atpId an identifier for an Academic Time Period
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones for the ATP or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesForAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Milestones for a specified Academic Time
     * Period that fall within a specified set of dates inclusive of
     * the dates.
     *
     * If the milestone is a date range then it should be selected if it overlaps 
     * any part of the specified start and end dates.
     *
     * Should follow these rules for storing and querying
     * https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
     *
     * @param atpId an identifier for an ATP
     * @param startDate start of date range
     * @param endDate end of date range
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of Milestones for the ATP in the specified date range or
     *         an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid or
     *         startDate is greater than end date
     * @throws MissingParameterException atpId, startDate, endDate, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesByDatesForAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Milestones of a specified Milestone Type
     * for an Acdemic Time Period.
     *
     * @param atpId an identifier for an ATP
     * @param milestoneTypeKey an identifier for a Milestone Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones of the Milestone Type for the ATP or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException atpId, milsetoneTypeKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> getMilestonesByTypeForAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of Milestones impacted by a change to a given
     * Milestone. A milestone can be a "relative" milsetone where its
     * dates are calculated from another milestone. The calculation
     * itself is a rule attched to the Milestone Type. 
     *
     * This method queries to see what other Milestones use the given
     * Milestone as an anchor.
     *
     * @param milestoneId an identifier for a Milestone
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestones impacted by the given Milestone
     * @throws DoesNotExistException milestoneId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public List<MilestoneInfo> getImpactedMilestones(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // Search methods for Milestone Id Entity Pattern.
    //

    /**
     * Searches for Milestones that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Milestone identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
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
     * @return a list of Milestones matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MilestoneInfo> searchForMilestones(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //
    // CRUD methods for Milestone Id Entity Pattern.
    //

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
     * @param milestoneInfo the milestone to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
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
     * @param milestoneTypeKey identifies the type of this milestone
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
    public MilestoneInfo createMilestone(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey,
                                         @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

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
     * Calculates the dates in the Milestone based on a rule attached
     * to the Milestone Type. If there is no rule available for the
     * Type of the given Milestone, then no changes to the Milestone
     * occur.
     *
     * @param milestoneId an identifier for a Milestone
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Milestone with the calculated dates
     * @throws DoesNotExistException milestoneId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException milestoneId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public MilestoneInfo calculateMilestone(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a Milestone to an ATP.
     *
     * @param milestoneId an identifier for a Milestone
     * @param atpId an identifier for an ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the mapping operation. This must always be true.
     * @throws AlreadyExistsException milestoneId is already related to atpId
     * @throws DoesNotExistException milestoneId or atpId is not found
     * @throws InvalidParameterException contextInfo is not valud
     * @throws MissingParameterException milestoneId, atpId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo addMilestoneToAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes a Milestone from an ATP.
     *
     * @param milestoneId an identifier for a Milestone
     * @param atpId an identifier for an ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the unmapping operation. This must always be true.
     * @throws DoesNotExistException milestoneId or atpId is not found
     *         or milestoneId is not related to atpId
     * @throws InvalidParameterException contextInfo is not valud
     * @throws MissingParameterException milestoneId, atpId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo removeMilestoneFromAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
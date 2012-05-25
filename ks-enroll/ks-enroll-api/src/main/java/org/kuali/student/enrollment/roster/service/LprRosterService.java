/*
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.roster.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.util.constants.LprRosterServiceConstants;

/**
 * The LprRoster service maintains ordered collections of Lprs for
 * various applications such as waitlists and grading sheets.
 */

@WebService(name = "LprRosterService", serviceName = "LprRosterService", portName = "LprRosterService", targetNamespace = LprRosterServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface LprRosterService {

    /**
     * Retrieves a single LprRoster by an LprRoster Id.
     *
     * @param lprRosterId the identifier for the LprRoster to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the LprRoster requested
     * @throws DoesNotExistException lprRosterId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LprRosterInfo getLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of LprRosters from a list of LprRoster Ids. The
     * returned list may be in any order and if duplicate Ids are
     * supplied, a unique set may or may not ber returned.
     *
     * @param lprRosterIds a list of LprRoster identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosters
     * @throws DoesNotExistException a lprRosterId in the list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterIds, an Id in
     *         lprRosterIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterInfo> getLprRostersByIds(@WebParam(name = "lprRosterIds") List<String> lprRosterIds,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of LprRosterIds by LprRoster Type.
     *
     * @param lprRosterTypeKey an identifier for an LprRoster Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosters identifiers matching
     *         lprRosterTypeKey or an empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterTypeKey
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLprRosterIdsByType(@WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of LprRosters associated with a given Lui. 
     *
     * @param luiId an identifier for a LUI
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of LprRosters associated with the given Lui or
     *         an empty list if none found 
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException luiId
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterInfo> getLprRostersByLui(@WebParam(name = "luiId") String luiId,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of LprRosters associated with a given LprRoster
     * Type and Lui.
     *
     * @param lprRosterTypeKey an identifier for an LprRoster Type
     * @param luiId an identifier for a Lui
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of LprRosters of the given LprRoster Type and
     *         associated with the given Lui or an empty list if none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterTypeKey, luiId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterInfo> getLprRostersByTypeAndLui(@WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
                                                         @WebParam(name = "luiId") String luiId,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for LprRosters that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRoster identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForLprRosterIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for LprRosters that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosters matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterInfo> searchForLprRosters(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Validates an LprRoster. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current LprRoster and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * LprRoster. If an identifier is present for the LprRoster
     * (and/or one of its contained sub-objects) and a record is found
     * for that identifier, the validation checks if the LprRoster can
     * be updated to the new values. If an identifier is not present
     * or a record does not exist, the validation checks if the
     * LprRoster with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param lprRosterTypeKey the identifier for the LprRoster Type
     *        to be validated
     * @param lprRosterInfo the LprRoster to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or
     *         lprRosterTypeKey is not found
     * @throws InvalidParameterException lprRosterInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException validationTypeKey,
     *         lprRosterTypeKey, lprRosterInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLprRoster(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                        @WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
                                                        @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Creates a new LprRoster. The LprRoster Id, Type, and Meta
     * information may not be set in the supplied data object.
     *
     * @param lprRosterTypeKey the identifier for the Type of
     *        LprRoster to be created
     * @param lprRosterInfo the data with which to create the
     *        LprRoster
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the new LprRoster
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprRosterTypeKey does not exist
     *         or is not supported
     * @throws InvalidParameterException lprRosterInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException lprRosterTypeKey,
     *         lprRosterInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public LprRosterInfo createLprRoster(@WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
                                         @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException,
               DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException,
               ReadOnlyException;

    /**
     * Updates an existing LprRoster. The LprRoster Id, Type, and Meta
     * information may not be changed.
     *
     * @param lprRosterId the identifier for the LprRoster to be
     *        updated
     * @param lprRosterInfo the new data for the LprRoster
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the updated LprRoster
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprRosterId is not found
     * @throws InvalidParameterException lprRosterInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException lprRosterId, lprRosterInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public LprRosterInfo updateLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
                                         @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
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
     * Deletes an existing LprRoster.
     *
     * @param lprRosterId the identifier for the LprRoster to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException lprRosterId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieves a single LprRosterEntry by an LprRosterEntry Id.
     *
     * @param lprRosterEntryId the identifier for the LprRosterEntry to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the LprRosterEntry requested
     * @throws DoesNotExistException lprRosterEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LprRosterEntryInfo getLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of LprRosterEntriess from a list of
     * LprRosterEntry Ids. The returned list may be in any order and
     * if duplicate Ids are supplied, a unique set may or may not ber
     * returned.
     *
     * @param lprRosterEntryIds a list of LprRosterEntry identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosterEntries
     * @throws DoesNotExistException a lprRosterEntryId in the list
     *         was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterEntryIds, an Id in
     *         lprRosterIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterEntryInfo> getLprRosterEntriesByIds(@WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of LprRosterENtryIds by LprRosterEntry Type.
     *
     * @param lprRosterEntryTypeKey an identifier for an
     *        LprRosterEntry Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosterEntries identifiers matching
     *         lprRosterEntryTypeKey or an empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterEntryTypeKey
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLprRosterEntryIdsByType(@WebParam(name = "lprRosterEntryTypeKey") String lprRosterEntryTypeKey,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * This method returns all the LprRosterEntries for an LprRoster.
     *
     * @param lprRosterId an identifier for an LprRoster
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosterEntries identifiers for the given
     *         LprRoster or an empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterEntryInfo> getLprRosterEntriesByLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException,
               PermissionDeniedException;

    /**
     * This method returns all the LprRosterEntries for an LPR.
     *
     * @param lprId an identifier for an Lpr
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosterEntries identifiers for the given
     *         Lpr or an empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterEntryInfo> getLprRosterEntriesByLpr(@WebParam(name = "lprId") String lprId,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException,
               PermissionDeniedException;

    /**
     * This method returns all the LprRosterEntries to the given
     * LprRoster and Lpr.
     *
     * @param lprRosterId an identifier for an LprRoster
     * @param lprId an identifier for an Lpr
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosterEntries identifiers for the given
     *         LprRoster and Lpr or an empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterId, lprId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterEntryInfo> getLprRosterEntriesByLprRosterAndLpr(@WebParam(name = "lprRosterId") String lprRosterId,
                                                                          @WebParam(name = "lprId") String lprId,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException,
               PermissionDeniedException;

    /**
     * Searches for LprRosterEntries that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosterEntry identifiers matching the
     *         criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForLprRosterEntryIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for LprRosterEntris that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprRosterEntries matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprRosterEntryInfo> searchForLprRosterEntries(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Validates an LprRosterEntry. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current LprRosterEntry and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * LprRosterEntry. If an identifier is present for the
     * LprRosterEntry (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the LprRosterEntry can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the LprRosterEntry with the given data can
     * be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param lprRosterId the LprRoster of the LprRosterEntry
     * @param lprId the Lpr of the LprRosterEntry
     * @param lprRosterEntryTypeKey the identifier for the
     *        LprRosterEntry Type to be validated
     * @param lprRosterEntryInfo the LprRosterEntry to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey, lprRosterId,
     *         or lprId, or lprRosterEntryTypeKey is not found
     * @throws InvalidParameterException lprRosterEntryInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException validationTypeKey,
     *         lprRosterId, lprId, lprRosterTypeKey, lprRosterInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLprRosterEntry(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                        @WebParam(name = "lprRosterId") String lprRosterId,
                                                        @WebParam(name = "lprId") String lprId,
                                                        @WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
                                                        @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Creates a new LprRosterEntry. The LprRosterEntry Id,
     * LprRosterId, Lpr Id, Type, and Meta information may not be set
     * in the supplied data object.
     *
     * @param lprRosterId the LprRoster of the LprRosterEntry
     * @param lprId the Lpr of the LprRosterEntry
     * @param lprRosterEntryTypeKey the identifier for the Type of
     *        LprRosterEntry to be created
     * @param lprRosterInfo the data with which to create the
     *        LprRoster
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the new LprRosterEntry
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprRosterId, lprId, or
     *         lprRosterEntryTypeKey does not exist or is not
     *         supported
     * @throws InvalidParameterException lprRosterEntryInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException lprRosterId, lprId,
     *         lprRosterEntryTypeKey, lprRosterInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public LprRosterEntryInfo createLprRosterEntry(@WebParam(name = "lprRosterId") String lprRosterId,
                                                   @WebParam(name = "lprId") String lprId,
                                                   @WebParam(name = "lprRosterEntryTypeKey") String lprRosterEntryTypeKey,
                                                   @WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException,
               DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException,
               ReadOnlyException;
 
    /**
     * Updates an existing LprRosterEntry. The LprRosterEntry Id,
     * Type, and Meta information may not be changed.
     *
     * @param lprRosterEntryId the identifier for the LprRosterEntry to
     *        be updated
     * @param lprRosterEntryInfo the new data for the LprRosterEntry
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the updated LprRosterEntry
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprRosterEntryId is not found
     * @throws InvalidParameterException lprRosterEntryInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException lprRosterEntryId,
     *         lprRosterEntryInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public LprRosterEntryInfo updateLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
                                                   @WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo,
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
     * Deletes an existing LprRosterEntry.
     *
     * @param lprRosterEntryId the identifier for the LprRosterEntry
     *        to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException lprRosterEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Inserts an existing roster entry at a particular position on
     * the roster.
     * 
     * If another roster entry already exists at that particular
     * position within the roster then this method "bumps down" the
     * rest of the roster entries until there is an open position.
     * 
     * @param lprRosterEntryId
     * @param position the absolute position in the LprRoster
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @throws DoesNotExistException lprRosterEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo moveLprRosterEntryToPosition(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
                                                   @WebParam(name = "position") Integer position,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Reorders all the LprRosterEntries setting their position to
     * match the order within the specified list of LprRosterEntry
     * Ids.
     * 
     * This is a bulk method to reset the positions all of the entries
     * in the LprRoster.
     * 
     * Any entries in the LprRoster that arenot specified in the
     * supplied list are ordered by their existing position and placed
     * at the end of the LprRosterEntries in the specified list.
     *
     * @param lprRosterIds the LprRoster to reorder.  All supplied
     *        LprRosterEntryIds must belong to the given LprRoster.
     * @param lprRosterEntryIds an ordered list of LprRosterEntries
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @throws DoesNotExistException an lprRosterEntryId in the list
     *         is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprRosterEntryIds, an Id in
     *         lprRosterEntryIds, or conetxtInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo reorderLprRosterEntries(@WebParam(name = "lprRosterIds") String lprRosterId,
                                              @WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds,
                                              @WebParam(name = "context") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;
}
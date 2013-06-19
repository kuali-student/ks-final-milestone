/**
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.waitlist.service;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.WaitListServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Wait lists provide a mechanism to queue and prioritize students with respect to an Offering (Activity Offering, Format Offering...).
 * For example, students who are unable register for a course due to availability or student based course restrictions.
 */
@WebService(name = "WaitListService", targetNamespace = WaitListServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface WaitListService {

    /**
     * Retrieves a single WaitList by WaitList Id.
     *
     * @param waitListId  the identifier for the WaitList to be
     *                    retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the WaitList requested
     * @throws DoesNotExistException     waitListId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public WaitListInfo getWaitList(@WebParam(name = "waitListId") String waitListId,
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of WaitLists from a list of WaitList
     * Ids. The returned list may be in any order and if duplicate Ids
     * are supplied, a unique set may or may not be returned.
     *
     * @param waitListIds a list of WaitList Ids
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of WaitList
     * @throws DoesNotExistException     an waitListId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListIds, an id in
     *                                   waitListIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListInfo> getWaitListsByIds(@WebParam(name = "waitListIds") List<String> waitListIds,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of WaitList Ids by WaitList type.
     *
     * @param waitListTypeKey an identifier for the WaitList
     *                        type
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return a list of WaitList Ids matching waitListTypeKey
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException waitListTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getWaitListIdsByType(@WebParam(name = "waitListTypeKey") String waitListTypeKey,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves all WaitLists attached to the given offering Id.
     *
     * @param offeringId the identifier for the attached offering
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of WaitLists associated to the given offering Id or an empty list if
     *         none are found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListInfo> getWaitListsByOffering(@WebParam(name = "offeringId") String offeringId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the WaitLists associated with the given WaitList type and offering Id.
     *
     * @param waitListTypeKey an identifier for the WaitList type
     * @param offeringId       the identifier for the attached offering
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return The WaitLists associated with the given WaitListTypeKey and offeringId
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListTypeKey, offeringId, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListInfo> getWaitListsByTypeAndOffering(@WebParam(name = "waitListTypeKey") String waitListTypeKey,
                                                       @WebParam(name = "offeringId") String offeringId,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for WaitList Ids that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of WaitList identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForWaitListIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for WaitList that meet the given search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of WaitLists matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListInfo> searchForWaitLists(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a WaitList. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this
     * WaitList. If an identifier is present for the WaitList
     * (and/or one of its contained sub-objects) and a record is found
     * for that identifier, the validation checks if the WaitList
     * can be shifted to the new values. If a an identifier is not
     * present or a record does not exist, the validation checks if
     * the WaitList with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param waitListTypeKey   the identifier for the WaitList
     *                          Type to be validated
     * @param waitListInfo      the WaitList to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey or
     *                                   waitListTypeKey is not found
     * @throws InvalidParameterException waitListInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *                                   waitListTypeKey, waitListInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateWaitList(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                       @WebParam(name = "waitListTypeKey") String waitListTypeKey,
                                                       @WebParam(name = "waitListInfo") WaitListInfo waitListInfo,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new WaitList. The WaitList type key and Meta information may not be set
     * in the supplied data object.
     *
     * @param waitListTypeKey a unique identifier for the Type of
     *                        the new WaitList
     * @param waitListInfo    the data with which to create the WaitList
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the new WaitList
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        waitListTypeKey does not exist or is
     *                                      not supported
     * @throws InvalidParameterException    waitListInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    waitListTypeKey,
     *                                      waitListInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public WaitListInfo createWaitList(@WebParam(name = "waitListTypeKey") String waitListTypeKey,
                                       @WebParam(name = "waitListInfo") WaitListInfo waitListInfo,
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing WaitList. The WaitList id, Type,
     * and Meta information may not be changed.
     *
     * @param waitListId   the identifier for the WaitList to be
     *                     updated
     * @param waitListInfo the new data for the WaitList
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the updated WaitList
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        waitListId is not found
     * @throws InvalidParameterException    waitListInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    waitListId,
     *                                      waitListInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public WaitListInfo updateWaitList(@WebParam(name = "waitListId") String waitListId,
                                       @WebParam(name = "waitListInfo") WaitListInfo waitListInfo,
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
     * Deletes an existing WaitList.
     *
     * @param waitListId  the identifier for the WaitList to be
     *                    deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     waitListId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListId or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteWaitList(@WebParam(name = "waitListId") String waitListId,
                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a single WaitListEntry by WaitListEntry Id.
     *
     * @param waitListEntryId the identifier for the WaitListEntry to be
     *                        retrieved
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the WaitListEntry requested
     * @throws DoesNotExistException     waitListEntryId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListEntryId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public WaitListEntryInfo getWaitListEntry(@WebParam(name = "waitListEntryId") String waitListEntryId,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of WaitListEntries from a list of WaitListEntry
     * Ids. The returned list may be in any order and if duplicate Ids
     * are supplied, a unique set may or may not be returned.
     *
     * @param waitListEntryIds a list of WaitListEntry Ids
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return a list of WaitListEntries
     * @throws DoesNotExistException     an waitListEntryId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListEntryIds, an id in
     *                                   waitListEntryIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListEntryInfo> getWaitListEntriesByIds(@WebParam(name = "waitListEntryIds") List<String> waitListEntryIds,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of WaitListEntry Ids by WaitListEntry Type.
     *
     * @param waitListEntryTypeKey an identifier for the WaitListEntry
     *                             type
     * @param contextInfo          information containing the principalId and
     *                             locale information about the caller of service operation
     * @return a list of WaitListEntry Ids matching waitListEntryTypeKey
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException waitListEntryTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getWaitListEntryIdsByType(@WebParam(name = "waitListEntryTypeKey") String waitListEntryTypeKey,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the WaitListEntries associated with the given Student Id.
     *
     * @param studentId   the identifier for the Student
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The WaitListEntry associated with the given Student Id
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListEntryInfo> getWaitListEntriesByStudent(@WebParam(name = "studentId") String studentId,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the WaitListEntries associated with the given WaitList Id.
     *
     * @param waitListId  the identifier for the WaitList
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The WaitListEntry associated with the given WaitList Id
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListEntryInfo> getWaitListEntriesByWaitList(@WebParam(name = "waitListId") String waitListId,
                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the WaitListEntries associated with the given WaitList Id and Student Id.
     *
     * @param waitListId  the identifier for the WaitList
     * @param studentId   the identifier for the Student
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The WaitListEntry associated with the given WaitList Id and Student Id
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListEntryInfo> getWaitListEntriesByWaitListAndStudent(@WebParam(name = "waitListId") String waitListId,
                                                                          @WebParam(name = "studentId") String studentId,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Searches for WaitListEntry Ids that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of WaitListEntry identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForWaitListEntryIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for WaitListEntries that meet the given search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of WaitListEntries matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<WaitListEntryInfo> searchForWaitListEntries(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a WaitListEntry. Depending on the value of validationType,
     * this validation could be limited to tests on just the current Relationship
     * and its directly contained sub-objects or expanded to perform all tests
     * related to this Relationship. If an identifier is present for the
     * Relationship (and/or one of its contained sub-objects) and a record is
     * found for that identifier, the validation checks if the Relationship
     * can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if the
     * object with the given data can be created.
     *
     * @param validationTypeKey    the identifier for the validation Type
     * @param waitListId the identifier for the WaitList that this entry is attached to.
     * @param studentId the identifier for the student that this entry is attached to.
     * @param waitListEntryTypeKey the identifier for the WaitListEntry
     *                             Type to be validated
     * @param waitListEntryInfo    the WaitListEntry to be validated
     * @param contextInfo          information containing the principalId and
     *                             locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey, waitListId, studentId, or
     *                                   waitListEntryTypeKey is not found
     * @throws InvalidParameterException waitListEntryInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *                                   waitListEntryTypeKey, waitListEntryInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateWaitListEntry(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                            @WebParam(name = "waitListId") String waitListId,
                                                            @WebParam(name = "studentId") String studentId,
                                                            @WebParam(name = "waitListEntryTypeKey") String waitListEntryTypeKey,
                                                            @WebParam(name = "waitListEntryInfo") WaitListEntryInfo waitListEntryInfo,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new WaitListEntry. The position of any other entries that are affected by this
     * addition are adjusted. For example, inserting a student at the third
     * position of a list with more than three entries would cause all
     * existing entries at position three and greater to have their positions
     * increased by one.
     * <p/>
     * The WaitList Id, student Id, WaitListEntry type key, and Meta information may not be set in the supplied data object.
     *
     * @param waitListId The WaitList that this entry belongs to
     * @param studentId The student that will be on the WaitList.
     * @param waitListEntryTypeKey a unique identifier for the Type of
     *                             the new WaitListEntry
     * @param waitListEntryInfo    the data with which to create the WaitListEntry
     * @param contextInfo          information containing the principalId and
     *                             locale information about the caller of service operation
     * @return the new WaitListEntry
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        waitListEntryTypeKey does not exist or is
     *                                      not supported
     * @throws InvalidParameterException    waitListEntryInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    waitListEntryTypeKey,
     *                                      waitListEntryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public WaitListEntryInfo createWaitListEntry(@WebParam(name = "waitListId") String waitListId,
                                                 @WebParam(name = "studentId") String studentId,
                                                 @WebParam(name = "waitListEntryTypeKey") String waitListEntryTypeKey,
                                                 @WebParam(name = "waitListEntryInfo") WaitListEntryInfo waitListEntryInfo,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing WaitListEntry. The WaitListEntry id, Type, WaitList id,
     * and Meta information may not be changed.
     *
     * To update the position please use the operation reorderWaitListEntries
     *
     * @param waitListEntryId   the identifier for the WaitListEntry to be
     *                          updated
     * @param waitListEntryInfo the new data for the WaitListEntry
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return the updated WaitListEntry
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        waitListEntryId is not found
     * @throws InvalidParameterException    waitListEntryInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    waitListEntryId,
     *                                      waitListEntryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public WaitListEntryInfo updateWaitListEntry(@WebParam(name = "waitListEntryId") String waitListEntryId,
                                                 @WebParam(name = "waitListEntryInfo") WaitListEntryInfo waitListEntryInfo,
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
     * Deletes an existing WaitListEntry.
     *
     * @param waitListEntryId the identifier for the WaitListEntry to be
     *                        deleted
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     waitListEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListEntryId or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteWaitListEntry(@WebParam(name = "waitListEntryId") String waitListEntryId,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Reorders the WaitListEntries contained in a WaitList.
     * Essentially this just adjusts the position of the entries relative to each other.
     * Causes the entries not in the list to the have a higher position (lower priority) than all of the entries given.
     *
     * It is important to note that this reordering is stable for any entries not given - entries not given will
     * have the same position relative to each other.
     *
     * For example, given a wait list with five entries (1, 2, 3, 4, 5) a call
     * to reorderWaitListEntries with (3, 2) would reorder the wait list as (3, 2, 1, 4, 5).
     *
     * @param waitListId the Id of the wait list that all of the wait list entries belong to.
     * @param waitListEntryIds a list of WaitListEntry Ids
     * @param contextInfo information containing the principalId and
     * locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException waitListId is not found or one of the waitListEntryIds given is not found
     * @throws InvalidParameterException One or more of the given waitListEntryIds is not on the given waitList
     * or the contextInfo is not valid
     * @throws MissingParameterException Missing waitlistIds, waitListEntryIds, or contextInfo
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo reorderWaitListEntries(@WebParam(name = "waitListId") String waitListId,
                                             @WebParam(name = "waitListEntryIds") List<String> waitListEntryIds,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Inserts an existing WaitListEntry at a particular position on
     * the WaitList.
     *
     * If another entry already exists at that particular
     * position within the WaitList then this method "bumps down" the
     * rest of the entries until there is an open position.
     *
     * @param waitListEntryId the id for the WaitListEntry to be moved.
     * @param position the absolute position in the WaitList
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @throws DoesNotExistException waitListEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException waitListEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo moveWaitListEntryToPosition(@WebParam(name = "waitListEntryId") String waitListEntryId,
                                                   @WebParam(name = "position") Integer position,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}

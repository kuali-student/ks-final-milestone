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
package org.kuali.student.enrollment.coursewaitlist.service;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.WaitListPositionInfo;
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
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Course wait lists provide a mechanism to queue and prioritize students with respect to an activity offering and format offering
 * For example, students who are unable register for a course due to availability or student based course restrictions.
 */
@WebService(name = "CourseWaitListService", targetNamespace = CourseWaitListServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseWaitListService {

    /**
     * Retrieves a single CourseWaitList by CourseWaitList Id.
     *
     * @param courseWaitListId  the identifier for the CourseWaitList to be
     *                    retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the CourseWaitList requested
     * @throws DoesNotExistException     courseWaitListId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CourseWaitListInfo getCourseWaitList(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseWaitLists from a list of CourseWaitList
     * Ids. The returned list may be in any order and if duplicate Ids
     * are supplied, a unique set may or may not be returned.
     *
     * @param courseWaitListIds a list of CourseWaitList Ids
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of CourseWaitList
     * @throws DoesNotExistException     a courseWaitListId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListIds, an id in
     *                                   courseWaitListIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListInfo> getCourseWaitListsByIds(@WebParam(name = "courseWaitListIds") List<String> courseWaitListIds,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseWaitList Ids by CourseWaitList type.
     *
     * @param courseWaitListTypeKey an identifier for the CourseWaitList
     *                        type
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return a list of CourseWaitList Ids matching courseWaitListTypeKey
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException courseWaitListTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseWaitListIdsByType(@WebParam(name = "courseWaitListTypeKey") String courseWaitListTypeKey,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves all CourseWaitLists attached to the given activity offering Id.
     *
     * @param activityOfferingId the identifier for the attached activity offering
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of CourseWaitLists associated to the given activityOfferingId or an empty list if
     *         none are found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListInfo> getCourseWaitListsByActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves all CourseWaitLists directly attached to the given format offering Id.
     *
     * @param formatOfferingId the identifier for the attached format offering
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of CourseWaitLists associated to the given formatOfferingId or an empty list if
     *         none are found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListInfo> getCourseWaitListsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId,
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for CourseWaitList Ids that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of CourseWaitList identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCourseWaitListIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for CourseWaitList that meet the given search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of CourseWaitLists matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListInfo> searchForCourseWaitLists(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a CourseWaitList. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this
     * CourseWaitList. If an identifier is present for the CourseWaitList
     * (and/or one of its contained sub-objects) and a record is found
     * for that identifier, the validation checks if the CourseWaitList
     * can be shifted to the new values. If a an identifier is not
     * present or a record does not exist, the validation checks if
     * the CourseWaitList with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param courseWaitListTypeKey   the identifier for the CourseWaitList
     *                          Type to be validated
     * @param courseWaitListInfo      the CourseWaitList to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey or
     *                                   courseWaitListTypeKey is not found
     * @throws InvalidParameterException courseWaitListInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *                                   courseWaitListTypeKey, courseWaitListInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateCourseWaitList(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                       @WebParam(name = "courseWaitListTypeKey") String courseWaitListTypeKey,
                                                       @WebParam(name = "courseWaitListInfo") CourseWaitListInfo courseWaitListInfo,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new CourseWaitList. The CourseWaitList type key and Meta information may not be set
     * in the supplied data object.
     *
     * @param courseWaitListTypeKey a unique identifier for the Type of
     *                        the new CourseWaitList
     * @param courseWaitListInfo    the data with which to create the CourseWaitList
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the new CourseWaitList
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        courseWaitListTypeKey does not exist or is
     *                                      not supported
     * @throws InvalidParameterException    courseWaitListInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    courseWaitListTypeKey,
     *                                      courseWaitListInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public CourseWaitListInfo createCourseWaitList(@WebParam(name = "courseWaitListTypeKey") String courseWaitListTypeKey,
                                       @WebParam(name = "courseWaitListInfo") CourseWaitListInfo courseWaitListInfo,
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing CourseWaitList. The CourseWaitList id, Type,
     * and Meta information may not be changed.
     *
     * @param courseWaitListId   the identifier for the CourseWaitList to be
     *                     updated
     * @param courseWaitListInfo the new data for the CourseWaitList
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the updated CourseWaitList
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        courseWaitListId is not found
     * @throws InvalidParameterException    courseWaitListInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    courseWaitListId,
     *                                      courseWaitListInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public CourseWaitListInfo updateCourseWaitList(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                       @WebParam(name = "courseWaitListInfo") CourseWaitListInfo courseWaitListInfo,
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
     * Changes the state of a CourseWaitList.
     *
     * @param courseWaitListId the Id of the CourseWaitList
     * @param stateKey the identifier for the new State
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the change state operation. This must always be
     *         true.
     * @throws DoesNotExistException courseWaitListId not found or stateKey
     *         not found in CourseWaitList Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeCourseWaitListState(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                          @WebParam(name = "stateKey") String stateKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Deletes an existing CourseWaitList.
     *
     * @param courseWaitListId  the identifier for the CourseWaitList to be
     *                    deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     courseWaitListId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteCourseWaitList(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a single CourseWaitListEntry by CourseWaitListEntry Id.
     *
     * @param courseWaitListEntryId the identifier for the CourseWaitListEntry to be
     *                        retrieved
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the CourseWaitListEntry requested
     * @throws DoesNotExistException     courseWaitListEntryId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListEntryId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CourseWaitListEntryInfo getCourseWaitListEntry(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseWaitListEntries from a list of CourseWaitListEntry
     * Ids. The returned list may be in any order and if duplicate Ids
     * are supplied, a unique set may or may not be returned.
     *
     * @param courseWaitListEntryIds a list of CourseWaitListEntry Ids
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return a list of CourseWaitListEntries
     * @throws DoesNotExistException     an courseWaitListEntryId in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListEntryIds, an id in
     *                                   courseWaitListEntryIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByIds(@WebParam(name = "courseWaitListEntryIds") List<String> courseWaitListEntryIds,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseWaitListEntry Ids by CourseWaitListEntry Type.
     *
     * @param courseWaitListEntryTypeKey an identifier for the CourseWaitListEntry
     *                             type
     * @param contextInfo          information containing the principalId and
     *                             locale information about the caller of service operation
     * @return a list of CourseWaitListEntry Ids matching courseWaitListEntryTypeKey
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException courseWaitListEntryTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseWaitListEntryIdsByType(@WebParam(name = "courseWaitListEntryTypeKey") String courseWaitListEntryTypeKey,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the CourseWaitListEntries associated with the given Student Id.
     *
     * @param studentId   the identifier for the Student
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The CourseWaitListEntry associated with the given Student Id
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByStudent(@WebParam(name = "studentId") String studentId,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the CourseWaitListEntries associated with the given CourseWaitList Id.
     * The returned list will be ordered by CourseWaitListEntryInfo.position starting with position 1.
     *
     * @param courseWaitListId  the identifier for the CourseWaitList
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The CourseWaitListEntry associated with the given CourseWaitList Id
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitList(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the CourseWaitListEntries associated with the given CourseWaitList Id and Student Id.
     *
     * @param courseWaitListId  the identifier for the CourseWaitList
     * @param studentId   the identifier for the Student
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return The CourseWaitListEntry associated with the given CourseWaitList Id and Student Id
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitListAndStudent(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                                          @WebParam(name = "studentId") String studentId,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Searches for CourseWaitListEntry Ids that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of CourseWaitListEntry identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCourseWaitListEntryIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for CourseWaitListEntries that meet the given search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of CourseWaitListEntries matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListEntryInfo> searchForCourseWaitListEntries(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a CourseWaitListEntry. Depending on the value of validationType,
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
     * @param courseWaitListId the identifier for the CourseWaitList that this entry is attached to.
     * @param studentId the identifier for the student that this entry is attached to.
     * @param courseWaitListEntryTypeKey the identifier for the CourseWaitListEntry
     *                             Type to be validated
     * @param courseWaitListEntryInfo    the CourseWaitListEntry to be validated
     * @param contextInfo          information containing the principalId and
     *                             locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey, courseWaitListId, studentId, or
     *                                   courseWaitListEntryTypeKey is not found
     * @throws InvalidParameterException courseWaitListEntryInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *                                   courseWaitListEntryTypeKey, courseWaitListEntryInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateCourseWaitListEntry(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                            @WebParam(name = "courseWaitListId") String courseWaitListId,
                                                            @WebParam(name = "studentId") String studentId,
                                                            @WebParam(name = "courseWaitListEntryTypeKey") String courseWaitListEntryTypeKey,
                                                            @WebParam(name = "courseWaitListEntryInfo") CourseWaitListEntryInfo courseWaitListEntryInfo,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new CourseWaitListEntry. The position of any other entries that are affected by this
     * addition are adjusted. For example, inserting a student at the third
     * position of a list with more than three entries would cause all
     * existing entries at position three and greater to have their positions
     * increased by one.
     * <p/>
     * The CourseWaitList Id, student Id, CourseWaitListEntry type key, and Meta information may not be set in the supplied data object.
     *
     * @param courseWaitListId The CourseWaitList that this entry belongs to
     * @param studentId The student that will be on the CourseWaitList.
     * @param courseWaitListEntryTypeKey a unique identifier for the Type of
     *                             the new CourseWaitListEntry
     * @param courseWaitListEntryInfo    the data with which to create the CourseWaitListEntry
     * @param contextInfo          information containing the principalId and
     *                             locale information about the caller of service operation
     * @return the new CourseWaitListEntry
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        courseWaitListEntryTypeKey does not exist or is
     *                                      not supported
     * @throws InvalidParameterException    courseWaitListEntryInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    courseWaitListEntryTypeKey,
     *                                      courseWaitListEntryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public CourseWaitListEntryInfo createCourseWaitListEntry(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                 @WebParam(name = "studentId") String studentId,
                                                 @WebParam(name = "courseWaitListEntryTypeKey") String courseWaitListEntryTypeKey,
                                                 @WebParam(name = "courseWaitListEntryInfo") CourseWaitListEntryInfo courseWaitListEntryInfo,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing CourseWaitListEntry. The CourseWaitListEntry id, Type, CourseWaitList id,
     * and Meta information may not be changed.
     *
     * To update the position please use the operation reorderCourseWaitListEntries
     *
     * @param courseWaitListEntryId   the identifier for the CourseWaitListEntry to be
     *                          updated
     * @param courseWaitListEntryInfo the new data for the CourseWaitListEntry
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return the updated CourseWaitListEntry
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        courseWaitListEntryId is not found
     * @throws InvalidParameterException    courseWaitListEntryInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    courseWaitListEntryId,
     *                                      courseWaitListEntryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public CourseWaitListEntryInfo updateCourseWaitListEntry(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                 @WebParam(name = "courseWaitListEntryInfo") CourseWaitListEntryInfo courseWaitListEntryInfo,
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
     * Changes the state of a CourseWaitListEntry.
     *
     * @param courseWaitListEntryId the Id of the CourseWaitListEntry
     * @param stateKey the identifier for the new State
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the change state operation. This must always be
     *         true.
     * @throws DoesNotExistException courseWaitListEntryId not found or stateKey
     *         not found in CourseWaitListEntry Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListEntryId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeCourseWaitListEntryState(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                               @WebParam(name = "stateKey") String stateKey,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Deletes an existing CourseWaitListEntry.
     *
     * @param courseWaitListEntryId the identifier for the CourseWaitListEntry to be
     *                        deleted
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     courseWaitListEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListEntryId or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteCourseWaitListEntry(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Reorders the CourseWaitListEntries contained in a CourseWaitList.
     * Essentially this just adjusts the position of the entries relative to each other.
     * Causes the entries not in the list to the have a higher position (lower priority) than all of the entries given.
     *
     * It is important to note that this reordering is stable for any entries not given - entries not given will
     * have the same position relative to each other.
     *
     * For example, given a course wait list with five entries (1, 2, 3, 4, 5) a call
     * to reorderCourseWaitListEntries with (3, 2) would reorder the course wait list as (3, 2, 1, 4, 5).
     *
     * @param courseWaitListId the Id of the course wait list that all of the course wait list entries belong to.
     * @param courseWaitListEntryIds a list of CourseWaitListEntry Ids
     * @param contextInfo information containing the principalId and
     * locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException courseWaitListId is not found or one of the courseWaitListEntryIds given is not found
     * @throws InvalidParameterException One or more of the given courseWaitListEntryIds is not on the given courseWaitList
     * or the contextInfo is not valid
     * @throws MissingParameterException Missing waitlistIds, courseWaitListEntryIds, or contextInfo
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo reorderCourseWaitListEntries(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                             @WebParam(name = "courseWaitListEntryIds") List<String> courseWaitListEntryIds,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Inserts an existing CourseWaitListEntry at a particular position on
     * the CourseWaitList.
     *
     * If another entry already exists at that particular
     * position within the CourseWaitList then this method "bumps down" the
     * rest of the entries until there is an open position.
     *
     * @param courseWaitListEntryId the id for the CourseWaitListEntry to be moved.
     * @param position the absolute position in the CourseWaitList
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @throws DoesNotExistException courseWaitListEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo moveCourseWaitListEntryToPosition(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                  @WebParam(name = "position") Integer position,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the position in the CourseWaitList (matching given wait list id) for
     * a student with given id.
     *
     * @param studentId         the identifier for the Student
     * @param courseWaitListId  the identifier for the CourseWaitList the student is in
     * @param activityOfferingId the identifier for the activity offering the waitlist is for
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @throws DoesNotExistException     courseWaitListId, studentId or activityOfferingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId, studentId, activityOfferingId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public WaitListPositionInfo getWaitListPositionForStudent (@WebParam(name = "studentId") String studentId,
                                                               @WebParam(name = "courseWaitListId") String courseWaitListId,
                                                               @WebParam(name = "activityOfferingId") String activityOfferingId,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the top waitlist entries in the waitlist with the given id.
     *
     * @param courseWaitListId  the identifier for the CourseWaitList whose top entries are to be obtained
     * @param activityOfferingId the identifier for the activity offering the waitlist is for
     * @param count             the number of entries to get from the top
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @throws DoesNotExistException     courseWaitListId is not found or activityOfferingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListId, count, activityOfferingId or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseWaitListEntryInfo> getTopCourseWaitListEntries (@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                                      @WebParam(name = "activityOfferingId") String activityOfferingId,
                                                                      @WebParam(name = "count") Integer count,
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}

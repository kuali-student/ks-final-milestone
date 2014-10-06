/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 8/19/2014
 */
package org.kuali.student.enrollment.coursewaitlist2.service;

import org.kuali.student.enrollment.coursewaitlist2.dto.ActivityOfferingWaitListConfigInfo;
import org.kuali.student.enrollment.coursewaitlist2.dto.AltActivityWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist2.dto.AltCourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist2.dto.WaitlistInfo;
import org.kuali.student.enrollment.coursewaitlist2.infc.ActivityOfferingWaitListConfig;
import org.kuali.student.enrollment.coursewaitlist2.infc.WaitListConfig;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;
import java.util.List;
import java.util.Map;

/**
 * An alternative to the CourseWaitListService
 *
 * @author Kuali Student Team
 */
public interface AltCourseWaitListService {

    /**
     * Retrieves a single CourseRegistration by a CourseRegistration Id.
     *
     * @param courseWaitListEntryId the identifier for the
     *        course waitlist entry to be retrieved (the master LPR id)
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the CourseWaitListEntryInfo requested
     * @throws DoesNotExistException courseWaitListEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseWaitListEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    AltCourseWaitListEntryInfo getCourseWaitListEntry(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single ActivityWaitListEntry by an
     * ActivityWaitListEntry Id.
     *
     * @param activityWaitListEntryId the identifier for the
     *        ActivityRegistration to be retrieved (i.e., the LPR id)
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the ActivityRegistration requested
     * @throws DoesNotExistException activityWaitListEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException activityWaitListEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    AltActivityWaitListEntryInfo getActivityWaitListEntry(@WebParam(name = "activityWaitListEntryId") String activityWaitListEntryId,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the global values used to govern the behavior of waitlists in the system.
     *
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the ActivityRegistration requested
     * @throws DoesNotExistException activityWaitListEntryId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException activityWaitListEntryId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    WaitListConfig getGlobalWaitListConfig(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves ActivityOfferingWaitListConfig by id
     * @param activityOfferingWaitListConfigId The id of the config
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the ActivityOfferingWaitListConfig requested
     */
    ActivityOfferingWaitListConfigInfo getActivityOfferingWaitListConfig(@WebParam(name = "activityWaitListEntryId") String activityOfferingWaitListConfigId,
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves list of ActivityOfferingWaitListConfig by activity offering id
     * @param activityOfferingId An activity offering id
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the ActivityOfferingWaitListConfig requested
     */
    List<ActivityOfferingWaitListConfigInfo>
    getActivityOfferingWaitListConfigsByActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves list of ActivityOfferingWaitListConfig by activity offering id
     * @param formatOfferingId A format offering id
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the ActivityOfferingWaitListConfig requested
     */
    List<ActivityOfferingWaitListConfigInfo>
    getActivityOfferingWaitListConfigsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new CourseWaitList. The CourseWaitList type key and Meta information may not be set
     * in the supplied data object.
     *
     * @param activityOfferingWaitListConfigTypeKey a unique identifier for the Type of
     *                        the new ActivityOfferingWaitListConfigTypeKey
     * @param activityOfferingWaitListConfigInfo    the data with which to create the ActivityOfferingWaitListConfig
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the new CourseWaitList
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        courseWaitListTypeKey does not exist or is
     *                                      not supported
     * @throws InvalidParameterException    courseWaitListInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    courseWaitListTypeKey,
     *                                      courseWaitListInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information designated as read only
     */
    public ActivityOfferingWaitListConfigInfo
    createActivityOfferingWaitListConfig(@WebParam(name = "activityOfferingWaitListConfigTypeKey") String activityOfferingWaitListConfigTypeKey,
                                         @WebParam(name = "activityOfferingWaitListConfigInfo") ActivityOfferingWaitListConfigInfo activityOfferingWaitListConfigInfo,
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
     * @param activityOfferingWaitListConfigId the identifier for the CourseWaitList to be
     *                     updated
     * @param activityOfferingWaitListConfigInfo the new data for the CourseWaitList
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
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    ActivityOfferingWaitListConfig
    updateActivityOfferingWaitListConfig(@WebParam(name = "courseWaitListId") String activityOfferingWaitListConfigId,
                                         @WebParam(name = "activityOfferingWaitListConfigInfo") ActivityOfferingWaitListConfigInfo activityOfferingWaitListConfigInfo,
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
     * Deletes an existing ActivityOfferingWaitListConfig.
     *
     * @param activityOfferingWaitListConfigId  the identifier for the CourseWaitList to be
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
    StatusInfo
    deleteActivityOfferingWaitListConfig(@WebParam(name = "activityOfferingWaitListConfigId") String activityOfferingWaitListConfigId,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    public List<WaitlistInfo> getPeopleToProcessFromWaitlist(List<String> aoIds,
                                                             Map<String, Integer> aoid2openSeatsMap,
                                                             ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException;
}

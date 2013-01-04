/*
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseregistration.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.dto.CreditLoadInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

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

import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;

/**
 * The Course Registration Service is a Class II service supporting
 * the process of registering a student in course(s) for a term. The
 * service provides operations for creating and validating
 * registration requests, registering for a course, and dropping a
 * course. 
 *
 * This service supports the concept of registration cart in the
 * application and all of the transactional requests for registration
 * are made through this service. As part of negotiating the student's
 * registration, operations are provided to manage related exceptions
 * and holds related to registration.
 * 
 * @author Kuali Student Team (sambit)
 */

@WebService(name = "CourseRegistrationService", targetNamespace = CourseRegistrationServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseRegistrationService  {

    // CourseRegistration methods

    /**
     * Retrieves a single CourseRegistration by an CourseRegistration Id.
     *
     * @param courseRegistrationId the identifier for the
     *        CourseRegistration to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the CourseRegistration requested
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CourseRegistrationInfo getCourseRegistration(@WebParam(name = "courseRegistrationId") String courseRegistrationId,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of CourseRegistrations from a list of
     * CourseRegistration Ids. The returned list may be in any order
     * and if duplicate Ids are supplied, a unique set may or may not
     * ber returned.
     *
     * @param courseRegistrationIds a list of CourseRegistration identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of CourseRegistrations
     * @throws DoesNotExistException a courseRegistrationId in the
     *         list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationIds, an Id
     *         in courseRegistrationIds, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByIds(@WebParam(name = "courseRegistrationIds") List<String> courseRegistrationIds,
                                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of CourseRegistrationIds by CourseRegistration
     * Type.
     *
     * @param courseRegistrationTypeKey an identifier for an
     *        CourseRegistration Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of CourseRegistrations identifiers matching
     *         courseRegistrationTypeKey or an empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationTypeKey
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseRegistrationIdsByType(@WebParam(name = "courseRegistrationTypeKey") String courseRegistrationTypeKey,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of CourseRegistrations for a given Student. 
     *
     * @param studentId an identifier for a Student
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of CourseRegistrations associated with the given
     *         Student or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(@WebParam(name = "studentId") String studentId,
                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of CourseRegistrations for a given CourseOffering.
     *
     * @param courseOfferingId an identifier for a CourseOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of CourseRegistrations associated with the given
     *         CourseOffering or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of CourseRegistrations for a given Student and
     * CourseOffering.
     *
     * @param studentId an identifier for a Student
     * @param courseOfferingId an identifier for a CourseOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of CourseRegistrations associated with the given
     *         Student and CourseOffering or an empty list if none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId, courseOfferingId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndCourseOffering(@WebParam(name = "studentId") String studentId,
                                                                                         @WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of CourseRegistrations for a given Student and
     * Term.
     *
     * @param studentId an identifier for a Student
     * @param termId an identifier for a Term
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of CourseRegistrations associated with the given
     *         Student and CourseOffering or an empty list if none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId, termId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(@WebParam(name = "studentId") String studentId,
                                                                               @WebParam(name = "termId") String termId,
                                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CourseRegistrations that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of CourseRegistration identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCourseRegistrationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CourseRegistrations that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of CourseRegistrations matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseRegistrationInfo> searchForCourseRegistrations(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    // ActivityRegistration methods

    /**
     * Retrieves a single ActivityRegistration by an
     * ActivityRegistration Id.
     *
     * @param activityRegistrationId the identifier for the
     *        ActivityRegistration to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the ActivityRegistration requested
     * @throws DoesNotExistException activityRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException activityRegistrationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ActivityRegistrationInfo getActivityRegistration(@WebParam(name = "activityRegistrationId") String activityRegistrationId,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of ActivityRegistrations from a list of
     * ActivityRegistration Ids. The returned list may be in any order
     * and if duplicate Ids are supplied, a unique set may or may not
     * ber returned.
     *
     * @param activityRegistrationIds a list of ActivityRegistration
     *        identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of ActivityRegistrations
     * @throws DoesNotExistException a activityRegistrationId in the
     *         list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException activityRegistrationIds, an Id in
     *         activityRegistrationIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsByIds(@WebParam(name = "activityRegistrationIds") List<String> activityRegistrationIds,
                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of ActivityRegistrationIds by
     * ActivityRegistration Type.
     *
     * @param activityRegistrationTypeKey an identifier for an
     *        ActivityRegistration Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of ActivityRegistrations identifiers matching
     *         activityRegistrationTypeKey or an empty list of none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException activityRegistrationTypeKey
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getActivityRegistrationIdsByType(@WebParam(name = "activityRegistrationTypeKey") String activityRegistrationTypeKey,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of ActivityRegistrations for a CourseRegistration.
     *
     * @param courseRegistrationId an identifier for a CourseRegistration
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of ActivityRegistrations associated with the given
     *         CourseRegistration or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegistration(@WebParam(name = "courseRegistrationId") String courseRegistrationId,
                                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of ActivityRegistrations for a given Student.
     *
     * @param studentId an identifier for a Student
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of ActivityRegistrations associated with the given
     *         Student or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudent(@WebParam(name = "studentId") String studentId,
                                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of ActivityRegistrations for a given
     * ActivityOffering.
     *
     * @param courseOfferingId an identifier for a ActivityOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of ActivityRegistrations associated with the given
     *         ActivityOffering or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsByActivityOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of ActivityRegistrations for a given Student and
     * ActivityOffering.
     *
     * @param studentId an identifier for a Student
     * @param courseOfferingId an identifier for a ActivityOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of ActivityRegistrations associated with the given
     *         Student and ActivityOffering or an empty list if none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId, courseOfferingId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndActivityOffering(@WebParam(name = "studentId") String studentId,
                                                                                               @WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of ActivityRegistrations for a given Student and
     * Term.
     *
     * @param studentId an identifier for a Student
     * @param termId an identifier for a Term
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of ActivityRegistrations associated with the given
     *         Student and ActivityOffering or an empty list if none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId, termId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndTerm(@WebParam(name = "studentId") String studentId,
                                                                                   @WebParam(name = "termId") String termId,
                                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for ActivityRegistrations that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of ActivityRegistration identifiers matching the
     *         criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForActivityRegistrationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for ActivityRegistrations that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of ActivityRegistrations matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    // RegistrationRequest methods

    /**
     * Retrieves a single RegistrationRequest by an
     * RegistrationRequest Id.
     *
     * @param registrationRequestId the identifier for the
     *        RegistrationRequest to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the RegistrationRequest requested
     * @throws DoesNotExistException registrationRequestId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException registrationRequestId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public RegistrationRequestInfo getRegistrationRequest(@WebParam(name = "registrationRequestId") String registrationRequestId,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of RegistrationRequests from a list of
     * RegistrationRequest Ids. The returned list may be in any order
     * and if duplicate Ids are supplied, a unique set may or may not
     * ber returned.
     *
     * @param registrationRequestIds a list of RegistrationRequest
     *        identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of RegistrationRequests
     * @throws DoesNotExistException a registrationRequestId in the
     *         list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException registrationRequestIds, an Id in
     *         registrationRequestIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RegistrationRequestInfo> getRegistrationRequestsByIds(@WebParam(name = "registrationRequestIds") List<String> registrationRequestIds,
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of RegistrationRequestIds by
     * RegistrationRequest Type.
     *
     * @param registrationRequestTypeKey an identifier for an
     *        RegistrationRequest Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of RegistrationRequests identifiers matching
     *         registrationRequestTypeKey or an empty list of none
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException registrationRequestTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getRegistrationRequestIdsByType(@WebParam(name = "registrationRequestTypeKey") String registrationRequestTypeKey,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of RegistrationRequests by requesting person Id. 
     *
     * @param personId an identifier for a Person
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of RegistrationRequests associated with the given Lui or
     *         an empty list if none found 
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RegistrationRequestInfo> getRegistrationRequestsByRequestor(@WebParam(name = "pesonId") String personId,
                                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of unsubmitted RegistrationRequests by requesting
     * person Id and Term.
     *
     * @param personId an identifier for a Person
     * @param termId an identifier for a Term
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of RegistrationRequests associated with the given Lui or
     *         an empty list if none found 
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId, termId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */

    public List<RegistrationRequestInfo> getUnsubmittedRegistrationRequestsByRequestorAndTerm(@WebParam(name = "requestorId") String requestorId, 
                                                                                              @WebParam(name = "termId") String termId,
                                                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for RegistrationRequests that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of RegistrationRequest identifiers matching the
     *         criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForRegistrationRequestIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for RegistrationRequests that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of RegistrationRequests matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RegistrationRequestInfo> searchForRegistrationRequests(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Validates an RegistrationRequest. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current RegistrationRequest and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * RegistrationRequest. If an identifier is present for the
     * RegistrationRequest (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation
     * checks if the RegistrationRequest can be updated to the new
     * values. If an identifier is not present or a record does not
     * exist, the validation checks if the RegistrationRequest with
     * the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param registrationRequestTypeKey the identifier for the
     *        RegistrationRequest Type to be validated
     * @param registrationRequestInfo the RegistrationRequest to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or
     *         registrationRequestTypeKey is not found
     * @throws InvalidParameterException registrationRequestInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *         registrationRequestTypeKey, registrationRequestInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateRegistrationRequest(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                  @WebParam(name = "registrationRequestTypeKey") String registrationRequestTypeKey,
                                                                  @WebParam(name = "registrationRequestInfo") RegistrationRequestInfo registrationRequestInfo,
                                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Creates a new RegistrationRequest. The RegistrationRequest Id,
     * Type, and Meta information may not be set in the supplied data
     * object.
     *
     * @param registrationRequestTypeKey the identifier for the Type
     *        of RegistrationRequest to be created
     * @param registrationRequestInfo the data with which to create
     *        the RegistrationRequest
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the new RegistrationRequest
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException registrationRequestTypeKey does
     *         not exist or is not supported
     * @throws InvalidParameterException registrationRequestInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException registrationRequestTypeKey,
     *         registrationRequestInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public RegistrationRequestInfo createRegistrationRequest(@WebParam(name = "registrationRequestTypeKey") String registrationRequestTypeKey,
                                                             @WebParam(name = "registrationRequestInfo") RegistrationRequestInfo registrationRequestInfo,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException,
               DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException,
               ReadOnlyException;

    /**
     * A utiligy to create a new RegistrationRequest from an existing
     * RegistrationRequest. Once a RegistrationRequest is submitted,
     * it cannot be reused. If the registration fails, this method can
     * be used to copy the contents of the failed request into a new
     * request.
     *
     * @param registrationRequestId a RegistrationRequest from which to create 
     *        the new one
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the new RegistrationRequest
     * @throws DoesNotExistException registrationRequestId does
     *         not exist
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException registrationRequestId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public RegistrationRequestInfo createRegistrationRequestFromExisting(@WebParam(name = "registrationRequestId") String registrationRequestId, 
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,               
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Updates an existing RegistrationRequest. The
     * RegistrationRequest Id, Type, and Meta information may not be
     * changed.
     *
     * @param registrationRequestId the identifier for the
     *        RegistrationRequest to be updated
     * @param registrationRequestInfo the new data for the RegistrationRequest
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the updated RegistrationRequest
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException registrationRequestId is not found
     * @throws InvalidParameterException registrationRequestInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException registrationRequestId,
     *         registrationRequestInfo, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public RegistrationRequestInfo updateRegistrationRequest(@WebParam(name = "registrationRequestId") String registrationRequestId,
                                                             @WebParam(name = "registrationRequestInfo") RegistrationRequestInfo registrationRequestInfo,
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
     * Deletes an existing RegistrationRequest.
     *
     * @param registrationRequestId the identifier for the
     *        RegistrationRequest to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the status of the delete operation. This must always be
     *         true.q
     * @throws DoesNotExistException registrationRequestId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException registrationRequestId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRegistrationRequest(@WebParam(name = "registrationRequestId") String registrationRequestId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Verifies a persisted RegistrationRequest for
     * submission. validateRegistrationRequest() validates the data
     * for persistence of the request itself. This method is intended
     * as a final validation prior to submission and may perform
     * additional checks, such as eligibility, course pre-requisites,
     * and calculating credit load limits.
     *
     * @param registrationRequestId an identifier for a
     *        RegistrationRequest
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of ValidationResults
     * @throws DoesNotExistException registrationRequestId
     *         is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException, registrationRequestId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> verifyRegistrationRequestForSubmission(@WebParam(name = "registrationRequestId") String registrationRequestId, 
                                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Submits a RegsitrationRequest. This method is transactional and
     * for multiple items, each RegistrationRequestItem must succeed
     * or the entireregistration transaction fails.
     *
     * @param registrationRequestId an identifier for a RegistrationRequest
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a RegistrationResponse
     * @throws AlreadyExistsException When the reg request is already submitted
     * @throws DoesNotExistException registrationRequestId
     *         is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException, registrationRequestId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public RegistrationResponseInfo submitRegistrationRequest(@WebParam(name = "registrationRequestId") String registrationRequestId, 
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws AlreadyExistsException,
               DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets the RegistrationRequestItems that resulted in or impacted
     * the given CourseRegistration.
     * 
     * @param courseRegistrationId an identifier for a CourseRegistration
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of RegistrationRequests associated with the given
     *         CourseRegistration or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsForCourseRegistration(@WebParam(name = "courseRegistrationId") String courseRegistrationId, 
                                                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets list of RegistrationRequestItems resulting in or impacting
     * a Student's registration in a CourseOffering.
     * 
     * @param courseOfferingId an identifier for a CourseOffering
     * @param studentId an identifier for a Student
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return list of RegistrationRequests associated with the given
     *         CourseOffering or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingId, personId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsByCourseOfferingAndStudent(@WebParam(name = "courseOfferingId") String courseOfferingId, 
                                                                                                   @WebParam(name = "studentId") String studentId,
                                                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Checks if a student is eligible to enter the registration
     * process. 
     *
     * @param studentId Identifier of the student
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of errors, warnings, or informational messages
     * @throws DoesNotExistException studentId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
                                                                                                   /// yes, no, messages
    public List<ValidationResultInfo> checkStudentEligibility(@WebParam(name = "studentId") String studentId, 
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Checks the eligibility of a student to register in a particular
     * term. 
     *
     * @param studentId an identifier of a Student
     * @param termId an identifier of a Term
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of errors, warnings or informational messages
     * @throws DoesNotExistException studentId or termId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId, termId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(@WebParam(name = "studentId") String studentId, 
                                                                     @WebParam(name = "termId") String termId,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular course
     * offering.
     *
     * @param studentId an identifier of a Student
     * @param courseOfferingId an identifier of a CourseOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of errors, warnings or informational messages
     * @throws DoesNotExistException studentId or courseOfferingId is
     *         not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId, courseOfferingId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(@WebParam(name = "studentId") String studentId, 
                                                                              @WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular
     * registration group. 
     *
     * @param studentId an identifier of a Student
     * @param registrationGroupId an identifier of a RegistrationGroup
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of errors, warnings or informational messages
     * @throws DoesNotExistException studentId or registrationGroupId
     *         is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId,
     *         registrationGroupId, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForRegistrationGroup(@WebParam(name = "studentId") String studentId, 
                                                                                 @WebParam(name = "registrationGroupId") String registrationGroupId,
                                                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets the Registration Groups for a CourseOffering for which the
     * given student is eligible to register.
     * 
     * @param studentId an identifier of a Student
     * @param courseOfferingId an identifier of a CourseOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of RegistrationGroups
     * @throws DoesNotExistException studentId or courseOfferingId is
     *         not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException studentId, courseOfferingId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RegistrationGroupInfo> getEligibleRegistrationGroupsForStudentInCourseOffering(@WebParam(name = "studentId") String studentId, 
                                                                                               @WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Calculate the credit load for a given student in a given
     * RegistrationRequest.
     * 
     * @param registrationRequestId an identifier of a RegistrationRequest
     * @param studentId an identifier of a Student
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the credit load
     * @throws DoesNotExistException registrationRequestId is not
     *         found or studentId not in RegistrationRequest
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException registrationRequestId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CreditLoadInfo calculateCreditLoadForStudentRegistrationRequest(@WebParam(name = "registrationRequestId") String registrationRequestId, 
                                                                           @WebParam(name = "studentId") String studentId, 
                                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieves the available seat count for a particular course offering. It sums
     * up the available seats for individual registration groups under the same
     * course offering.
     * 
     * @param courseOfferingId
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified CourseOffering.
     * @throws InvalidParameterException Invalid courseOfferingId in the input
     * @throws MissingParameterException Missing courseOfferingId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
                                               //    public Integer getAvailableSeatsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,         MissingParameterException, OperationFailedException, PermissionDeniedException;


                                                      
    /**
     * Get available seat count for the registration group.
     * 
     * @param regGroupId Identifier of the registration group
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified Registration Group
     * @throws InvalidParameterException Invalid regGroupId in the input
     * @throws MissingParameterException Missing regGroupId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
                                                      //    public Integer getAvailableSeatsForRegistrationGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the number of seats available for a particular student in a
     * registration group.
     * <p>
     * Implementation notes : Seats available for a student taking seat pool (if
     * any) into consideration.
     * 
     * @param studentId Identifier of the student
     * @param regGroupId Identifier of the registration group
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified student in a specified RegistrationGroup.
     * @throws InvalidParameterException Invalid studentId or regGroupId in the
     *             input
     * @throws MissingParameterException Missing studentId or regGroupId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
                                                      //    public Integer getAvailableSeatsForStudentInRegistrationGroup(@WebParam(name = "studentId") String studentId, @WebParam(name = "regGroupId") String regGroupId,            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Returns the available seat count in a particular seat pool. This is an admin
     * support function to check the seat pool usage.
     * 
     * @param seatPoolId Identifier of the seatPool
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified seat pool.
     * @throws InvalidParameterException Invalid seatPool in the input
     * @throws MissingParameterException Missing parameter seatPoolId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
                                                      //    public Integer getAvailableSeatsInSeatPool(@WebParam(name = "seatPoolId") String seatPoolId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,            MissingParameterException, OperationFailedException, PermissionDeniedException;                                               
}

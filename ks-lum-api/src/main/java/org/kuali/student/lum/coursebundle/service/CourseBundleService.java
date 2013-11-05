/**
 * Copyright 2012 The Kuali Foundation 
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

package org.kuali.student.lum.coursebundle.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.lum.coursebundle.dto.CourseBundleInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;


/**
 * This service supports the management of Course Bundles.
 *
 * @version 0.0.8
 * @author Kuali Student Services
 */

@WebService(name = "CourseBundleService", serviceName = "CourseBundleService", portName = "CourseBundleService", targetNamespace = CourseBundleServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface CourseBundleService {


    /** 
     * Retrieves a single CourseBundle by CourseBundle Id.
     *
     * @param courseBundleId the identifier for the course bundle to
     *        be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the CourseBundle requested
     * @throws DoesNotExistException courseBundleId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseBundleId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CourseBundleInfo getCourseBundle(@WebParam(name = "courseBundleId") String courseBundleId, 
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CourseBundles from a list of CourseBundle
     * Ids. The returned list may be in any order and if duplicates
     * Ids are supplied, a unique set may or may not be returned.
     *
     * @param courseBundleIds a list of CourseBundle identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CourseBundles
     * @throws DoesNotExistException a courseBundleId in the list not
     *         found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException courseBundleIds, an Id in
     *         courseBundleIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseBundleInfo> getCourseBundlesByIds(@WebParam(name = "courseBundleIds") List<String> courseBundleIds, 
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CourseBundle Ids by CourseBundle Type.
     *
     * @param courseBundleTypeKey an identifier for a
     *        CourseBundle Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CourseBundle identifiers matching
     *         courseBundleTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseBundleTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseBundleIdsByType(@WebParam(name = "courseBundleTypeKey") String courseBundleTypeKey, 
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CourseBundles based on the criteria and returns
     * a list of CourseBundle identifiers which match the search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CourseBundle Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCourseBundleIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CourseBundles based on the criteria and returns
     * a list of CourseBundles which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CourseBundles matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseBundleInfo> searchForCourseBundles(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a CourseBundle. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current CourseBundle and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * CourseBundle. If an identifier is present for the CourseBundle
     * (and/or one of its contained sub-objects) and a record is found
     * for that identifier, the validation checks if the CourseBundle
     * can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if
     * the object with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param courseBundleTypeKey the identifier for the bundled
     *        offering Type
     * @param courseBundleInfo the CourseBundle information to
     *        be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey,
     *         courseBundleTypeKey is not found
     * @throws InvalidParameterException courseBundleInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *         courseBundleTypeKey, courseBundleInfo, or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateCourseBundle(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                              @WebParam(name = "courseBundleTypeKey") String courseBundleTypeKey, 
                                                              @WebParam(name = "courseBundleInfo") CourseBundleInfo courseBundleInfo, 
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new CourseBundle from a canonical CourseBundle for a
     * Term. The CourseBundle Id, Type, and Meta information may not
     * be set in the supplied data.
     *
     * @param courseBundleTypeKey the identifier for the Type of
     *        the new CourseBundle
     * @param courseBundleInfo the data with which to create the
     *        CourseBundle
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new CourseBundle 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException courseBundleTypeKey does not
     *         exist or is not supported
     * @throws InvalidParameterException courseBundleInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException courseBundleTypeKey,
     *         courseBundleInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public CourseBundleInfo createCourseBundle(@WebParam(name = "courseBundleTypeKey") String courseBundleTypeKey, 
                                               @WebParam(name = "courseBundleInfo") CourseBundleInfo courseBundleInfo, 
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing CourseBundle. The CourseBundle Id, Type,
     * and Meta information may not be changed.
     *
     * @param courseBundleId the identifier for the CourseBundle to be
     *        updated
     * @param courseBundleInfo the new data for the CourseBundle
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated CourseBundle
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException courseBundleId not found
     * @throws InvalidParameterException courseBundleInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException courseBundleId,
     *         courseBundleInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public CourseBundleInfo updateCourseBundle(@WebParam(name = "courseBundleId") String courseBundleId, 
                                               @WebParam(name = "courseBundleInfo") CourseBundleInfo courseBundleInfo, 
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
     * Changes the state of a CourseBundle.
     *
     * @param courseBundleId the Id of the CourseBundle
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException courseBundleId not found or
     *         stateKey not found in CourseBundle Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseBundleId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeCourseBundleState(@WebParam(name = "courseBundleId") String courseBundleId, 
                                              @WebParam(name = "stateKey") String stateKey,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing CourseBundle.
     *
     * @param courseBundleId the identifier for the CourseBundle to be
     *        deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException courseBundleId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseBundleId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteCourseBundle(@WebParam(name = "courseBundleId") String courseBundleId, 
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;
}

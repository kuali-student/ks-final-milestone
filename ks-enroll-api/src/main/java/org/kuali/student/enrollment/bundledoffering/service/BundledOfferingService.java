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

package org.kuali.student.enrollment.bundledoffering.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.bundledoffering.dto.BundledOfferingInfo;

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
 * This service supports the management of Bundled Offerings. A
 * Bundled Offering is a set of Registration Groups for which a
 * student is registered together. A Bundled Offering is an offering
 * of a canonical CourseBundle.
 *
 * @version 0.0.8
 * @author Kuali Student Services
 */

@WebService(name = "BundledOfferingService", serviceName = "BundledOfferingService", portName = "BundledOfferingService", targetNamespace = BundledOfferingServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface BundledOfferingService {


    /** 
     * Retrieves a single BundledOffering by BundledOffering Id.
     *
     * @param bundledOfferingId the identifier for the bundled
     *        offering to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the BundledOffering requested
     * @throws DoesNotExistException bundledOfferingId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException bundledOfferingId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public BundledOfferingInfo getBundledOffering(@WebParam(name = "bundledOfferingId") String bundledOfferingId, 
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of BundledOfferings from a list of
     * BundledOffering Ids. The returned list may be in any order and
     * if duplicates Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param bundledOfferingIds a list of BundledOffering identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOfferings
     * @throws DoesNotExistException a bundledOfferingId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException bundledOfferingIds, an Id in
     *         bundledOfferingIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> getBundledOfferingsByIds(@WebParam(name = "bundledOfferingIds") List<String> bundledOfferingIds, 
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of BundledOffering Ids by BundledOffering Type.
     *
     * @param bundledOfferingTypeKey an identifier for a
     *        BundledOffering Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOffering identifiers matching
     *         bundledOfferingTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException bundledOfferingTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getBundledOfferingIdsByType(@WebParam(name = "bundledOfferingTypeKey") String bundledOfferingTypeKey, 
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of BundledOfferings belonging to a
     * CourseBundle.
     *
     * @param courseBundleId the identifier for a canonical
     *        CourseBundle
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOfferings for a CourseBundle or an
     *         empty list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseBundleId or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> getBundledOfferingsByCourseBundle(@WebParam(name = "courseBundleId") String courseBundleId, 
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of BundledOfferings by Term.
     *
     * @param termId the Id of a Term
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOfferings for the Term or an empty
     *         list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> getBundledOfferingsByTerm(@WebParam(name = "termId") String termId, 
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /** 
     * Retrieves a list of BundledOfferings by CourseBundle and Term.
     *
     * @param courseBundleId the identifier for a canonical
     *        CourseBundle
     * @param termId the Id of a Term
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOfferings for the Term or an empty
     *         list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseBundleId, termId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> getBundledOfferingsByCourseBundleAndTerm(@WebParam(name = "courseBundleId") String courseBundleId, 
@WebParam(name = "termId") String termId, 
                                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of BundledOfferings containing a Registration
     * Group.
     *
     * @param regsitartionGroupId the Id of a registration Group
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOfferings fwith the registration
     *         Groupor an empty list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException regsitrationGroupId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> getBundledOfferingsByRegistrationGroup(@WebParam(name = "regsitrationGroupId") String registrationGroupId, 
                                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of BundledOfferings by Term and code.
     *
     * @param termId a Term Id
     * @param code a BundledOffering code
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOfferings for the given code empty
     *         list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId, code or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> getBundledOfferingsByTermAndCode(@WebParam(name = "termId") String termId, 
                                                                      @WebParam(name = "code") String code, 
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of BundledOfferings by Term and subject area.
     *
     * @param term a Term Id
     * @param subjectAreaOrgIdId a subject area Org Id
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of BundledOfferings for the given term and
     *         subject area or an empty list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId, subjectAreaOrgId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> getBundledOfferingsByTermAndSubjectAreaOrg(@WebParam(name = "termId") String termId, 
                                                                                @WebParam(name = "subjectAreaOrgId") String subjectAreaOrgId, 
                                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for BundledOfferings based on the criteria and returns
     * a list of BundledOffering identifiers which match the search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of BundledOffering Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForBundledOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for BundledOfferings based on the criteria and returns
     * a list of BundledOfferings which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of BundledOfferings matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<BundledOfferingInfo> searchForBundledOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a BundledOffering. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current BundledOffering and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * BundledOffering. If an identifier is present for the
     * BundledOffering (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the BundledOffering can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param courseBundleId the identifier for the CourseBundle
     * @param termIdId the identifier for the Term
     * @param bundledOfferingTypeKey the identifier for the bundled
     *        offering Type
     * @param bundledOfferingInfo the BundledOffering information to
     *        be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey,
     *         courseBundleId, termId, or bundledOfferingTypeKey is
     *         not found
     * @throws InvalidParameterException bundledOfferingInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *         courseBundleId, termId, bundledOfferingTypeKey,
     *         bundledOfferingInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateBundledOffering(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                              @WebParam(name = "courseBundleId") String courseBundleId, 
                                                              @WebParam(name = "termId") String termId, 
                                                              @WebParam(name = "bundledOfferingTypeKey") String bundledOfferingTypeKey, 
                                                              @WebParam(name = "bundledOfferingInfo") BundledOfferingInfo bundledOfferingInfo, 
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new BundledOffering from a canonical CourseBundle for
     * a Term. The BundledOffering Id, Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param courseBundleId the identifier for the canonical
     *        CourseBundle
     * @param termId the identifier for the Term
     * @param bundledOfferingTypeKey the identifier for the Type of
     *        the new BundledOffering
     * @param bundledOfferingInfo the data with which to create the
     *        BundledOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new BundledOffering 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException courseBundleId, termId, or
     *         bundledOfferingTypeKey does not exist or is not
     *         supported
     * @throws InvalidParameterException bundledOfferingInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException courseBundleId, termId,
     *         bundledOfferingTypeKey, bundledOfferingInfo or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public BundledOfferingInfo createBundledOffering(@WebParam(name = "courseBundleId") String courseBundleId, 
@WebParam(name = "termId") String termId, 
                                                     @WebParam(name = "bundledOfferingTypeKey") String bundledOfferingTypeKey, 
                                                     @WebParam(name = "bundledOfferingInfo") BundledOfferingInfo bundledOfferingInfo, 
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing BundledOffering. The BundledOffering Id,
     * Type, and Meta information may not be changed.
     *
     * @param bundledOfferingId the identifier for the BundledOffering
     *        to be updated
     * @param bundledOfferingInfo the new data for the BundledOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated BundledOffering
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException bundledOfferingId not found
     * @throws InvalidParameterException bundledOfferingInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException bundledOfferingId,
     *         bundledOfferingInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public BundledOfferingInfo updateBundledOffering(@WebParam(name = "bundledOfferingId") String bundledOfferingId, 
                                                     @WebParam(name = "bundledOfferingInfo") BundledOfferingInfo bundledOfferingInfo, 
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
     * Changes the state of a BundledOffering.
     *
     * @param bundledOfferingId the Id of the BundledOffering
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException bundledOfferingId not found or stateKey
     *         not found in BundledOffering Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException bundledOfferingId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeBundledOfferingState(@WebParam(name = "bundledOfferingId") String bundledOfferingId, 
                                                 @WebParam(name = "stateKey") String stateKey,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing BundledOffering.
     *
     * @param bundledOfferingId the identifier for the BundledOffering
     *        to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException bundledOfferingId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException bundledOfferingId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteBundledOffering(@WebParam(name = "bundledOfferingId") String bundledOfferingId, 
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;
}

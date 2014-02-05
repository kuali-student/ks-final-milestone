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

package org.kuali.student.core.appliedrate.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.core.appliedrate.dto.CatalogRateCanonicalRelationInfo;
import org.kuali.student.core.appliedrate.dto.RateOfferingRelationInfo;

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
 * This service applies rates to courses and offerings. This service
 * is a little odd in that it is separated from the basic Rate Service
 * and refers directly to canonicals and offerings. We should revisit
 * the packaging issues of these mega service packages.
 *
 * @version 0.0.8
 * @author Kuali Student Services
 */

@WebService(name = "AppliedRateService", targetNamespace = AppliedRateServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface AppliedRateService {

    /** 
     * Retrieves a single CatalogRateCanonicalRelation by
     * CatalogRateCanonicalRelation Id.
     *
     * @param catalogRateCanonicalRelationId the identifier for the
     *        rate to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the CatalogRateCanonicalRelation requested
     * @throws DoesNotExistException catalogRateCanonicalRelationId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException
     *         catalogRateCanonicalRelationId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CatalogRateCanonicalRelationInfo getCatalogRateCanonicalRelation(@WebParam(name = "catalogRateCanonicalRelationId") String catalogRateCanonicalRelationId, 
                                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRateCanonicalRelations from a list
     * of CatalogRateCanonicalRelation Ids. The returned list may be
     * in any order and if duplicates Ids are supplied, a unique set
     * may or may not be returned.
     *
     * @param catalogRateCanonicalRelationIds a list of
     *        CatalogRateCanonicalRelation identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRateCanonicalRelations
     * @throws DoesNotExistException a catalogRateCanonicalRelationId
     *         in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException
     *         catalogRateCanonicalRelationIds, an Id in
     *         catalogRateCanonicalRelationIds, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateCanonicalRelationInfo> getCatalogRateCanonicalRelationsByIds(@WebParam(name = "catalogRateCanonicalRelationIds") List<String> catalogRateCanonicalRelationIds, 
                                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRateCanonicalRelation Ids by
     * CatalogRateCanonicalRelation Type.
     *
     * @param catalogRateCanonicalRelationTypeKey an identifier for a
     *        CatalogRateCanonicalRelation Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRateCanonicalRelation identifiers
     *         matching catalogRateCanonicalRelationTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException
     *         catalogRateCanonicalRelationTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCatalogRateCanonicalRelationIdsByType(@WebParam(name = "catalogRateCanonicalRelationTypeKey") String catalogRateCanonicalRelationTypeKey, 
                                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRateCanonicalRelations for a
     * CatalogRate.
     *
     * @param catalogRateId the Id of the CatalogRate
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRateCanonicalRelations
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException catalogRateId or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateCanonicalRelationInfo> getCatalogRateCanonicalRelationsByCatalogRate(@WebParam(name = "catalogId") String catalogRateId, 
                                                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRateCanonicalRelations for a
     * Course.
     *
     * @param courseId the Id of the Course
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRateCanonicalRelations
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException courseId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateCanonicalRelationInfo> getCatalogRateCanonicalRelationsByCourse(@WebParam(name = "courseId") String courseId, 
                                                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRateCanonicalRelations for a
     * CatalogRate and Course.
     *
     * @param catalogRateId the Id of the CatalogRate
     * @param courseId the Id of the Course
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRateCanonicalRelations
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException catalogRateId, courseId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateCanonicalRelationInfo> getCatalogRateCanonicalRelationsByCatalogRateAndCourse(@WebParam(name = "catalogRateId") String catalogRateId, 
                                                                                                         @WebParam(name = "courseId") String courseId, 
                                                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CatalogRateCanonicalRelations based on the
     * criteria and returns a list of CatalogRateCanonicalRelation
     * identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CatalogRateCanonicalRelation Ids matching the
     *         criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCatalogRateCanonicalRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CatalogRateCanonicalRelations based on the
     * criteria and returns a list of CatalogRateCanonicalRelations
     * which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CatalogRateCanonicalRelations matching the
     *         criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateCanonicalRelationInfo> searchForCatalogRateCanonicalRelations(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a CatalogRateCanonicalRelation. Depending on the
     * value of validationType, this validation could be limited to
     * tests on just the current CatalogRateCanonicalRelation and its
     * directly contained sub-objects or expanded to perform all tests
     * related to this CatalogRateCanonicalRelation. If an identifier
     * is present for the CatalogRateCanonicalRelation (and/or one of
     * its contained sub-objects) and a record is found for that
     * identifier, the validation checks if the
     * CatalogRateCanonicalRelation can be updated to the new
     * values. If an identifier is not present or a record does not
     * exist, the validation checks if the object with the given data
     * can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param catalogRateId the Id of the CatalogRate
     * @param courseId the Id of the Course
     * @param catalogRateCanonicalRelationTypeKey the identifier for
     *        the rate Type
     * @param catalogRateCanonicalRelationInfo the
     *        CatalogRateCanonicalRelation information to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey, catalogRateId,
     *         courseId, or catalogRateCanonicalRelationTypeKey is not
     *         found
     * @throws InvalidParameterException
     *         catalogRateCanonicalRelationInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException validationTypeKey,
     *         catalogRateId, courseId,
     *         catalogRateCanonicalRelationTypeKey,
     *         catalogRateCanonicalRelationInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateCatalogRateCanonicalRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                                           @WebParam(name = "catalogRateId") String catalogRateId, 
                                                                           @WebParam(name = "courseId") String courseId, 
                                                                           @WebParam(name = "catalogRateCanonicalRelationTypeKey") String catalogRateCanonicalRelationTypeKey, 
                                                                           @WebParam(name = "catalogRateCanonicalRelationInfo") CatalogRateCanonicalRelationInfo catalogRateCanonicalRelationInfo, 
                                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new CatalogRateCanonicalRelation. The
     * CatalogRateCanonicalRelation Id, Type, and Meta information may
     * nogt be set in the supplied data.
     *
     * @param catalogRateId the Id of the CatalogRate
     * @param courseId the Id of the Course
     * @param catalogRateCanonicalRelationTypeKey the identifier for
     *        the Type of the new CatalogRateCanonicalRelation
     * @param catalogRateCanonicalRelationInfo the data with which to
     *        create the CatalogRateCanonicalRelation
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new CatalogRateCanonicalRelation 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogRateId, courseId,
     *         catalogRateCanonicalRelationTypeKey does not exist is
     *         not supported
     * @throws InvalidParameterException
     *         catalogRateCanonicalRelationInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException catalogRateId, courseId,
     *         catalogRateCanonicalRelationTypeKey,
     *         catalogRateCanonicalRelationInfo or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public CatalogRateCanonicalRelationInfo createCatalogRateCanonicalRelation(@WebParam(name = "catalogRateId") String catalogRateId, 
                                                                               @WebParam(name = "courseId") String courseId, 
                                                                               @WebParam(name = "catalogRateCanonicalRelationTypeKey") String catalogRateCanonicalRelationTypeKey, 
                                                                               @WebParam(name = "catalogRateCanonicalRelationInfo") CatalogRateCanonicalRelationInfo catalogRateCanonicalRelationInfo, 
                                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing CatalogRateCanonicalRelation. The
     * CatalogRateCanonicalRelation Id, Type, and Meta information may
     * not be changed.
     *
     * @param catalogRateCanonicalRelationId the identifier for the
     *        CatalogRateCanonicalRelation to be updated
     * @param catalogRateCanonicalRelationInfo the new data for the
     *        CatalogRateCanonicalRelation
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated CatalogRateCanonicalRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogRateCanonicalRelationId not found
     * @throws InvalidParameterException
     *         catalogRateCanonicalRelationInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException
     *         catalogRateCanonicalRelationId,
     *         catalogRateCanonicalRelationInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public CatalogRateCanonicalRelationInfo updateCatalogRateCanonicalRelation(@WebParam(name = "catalogRateCanonicalRelationId") String catalogRateCanonicalRelationId, 
                                                                               @WebParam(name = "catalogRateCanonicalRelationInfo") CatalogRateCanonicalRelationInfo catalogRateCanonicalRelationInfo, 
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
     * Changes the state of a CatalogRateCanonicalRelation.
     *
     * @param catalogRateCanonicalRelationId the Id of the
     *        CatalogRateCanonicalRelation
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be
     *         true.
     * @throws DoesNotExistException catalogRateCanonicalRelationId
     *         not found or stateKey not found in
     *         CatalogRateCanonicalRelation Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException
     *         catalogRateCanonicalRelationId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeCatalogRateCanonicalRelationState(@WebParam(name = "catalogRateCanonicalRelationId") String catalogRateCanonicalRelationId, 
                                                              @WebParam(name = "stateKey") String stateKey,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing CatalogRateCanonicalRelation.
     *
     * @param catalogRateCanonicalRelationId the identifier for the
     *        CatalogRateCanonicalRelation to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DependentObjectsExistException Rates are attached to
     *         this RateCatalog
     * @throws DoesNotExistException catalogRateCanonicalRelationId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException
     *         catalogRateCanonicalRelationId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteCatalogRateCanonicalRelation(@WebParam(name = "catalogRateCanonicalRelationId") String catalogRateCanonicalRelationId, 
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DependentObjectsExistException,
               DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /** 
     * Retrieves a single RateOfferingRelation by
     * RateOfferingRelation Id.
     *
     * @param rateOfferingRelationId the identifier for the
     *        rate to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the RateOfferingRelation requested
     * @throws DoesNotExistException rateOfferingRelationId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException
     *         rateOfferingRelationId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public RateOfferingRelationInfo getRateOfferingRelation(@WebParam(name = "rateOfferingRelationId") String rateOfferingRelationId, 
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of RateOfferingRelations from a list of
     * RateOfferingRelation Ids. The returned list may be in any order
     * and if duplicates Ids are supplied, a unique set may or may not
     * be returned.
     *
     * @param rateOfferingRelationIds a list of
     *        RateOfferingRelation identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of RateOfferingRelations
     * @throws DoesNotExistException a rateOfferingRelationId
     *         in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException
     *         rateOfferingRelationIds, an Id in
     *         rateOfferingRelationIds, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateOfferingRelationInfo> getRateOfferingRelationsByIds(@WebParam(name = "rateOfferingRelationIds") List<String> rateOfferingRelationIds, 
                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of RateOfferingRelation Ids by
     * RateOfferingRelation Type.
     *
     * @param rateOfferingRelationTypeKey an identifier for a
     *        RateOfferingRelation Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of RateOfferingRelation identifiers
     *         matching rateOfferingRelationTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException
     *         rateOfferingRelationTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getRateOfferingRelationIdsByType(@WebParam(name = "rateOfferingRelationTypeKey") String rateOfferingRelationTypeKey, 
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of RateOfferingRelations for a Rate.
     *
     * @param rateId the Id of the Rate
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of RateOfferingRelations
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException rateId or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateOfferingRelationInfo> getRateOfferingRelationsByRate(@WebParam(name = "catalogId") String rateId, 
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of RateOfferingRelations for a FormatOffering.
     *
     * @param formatOfferingId the Id of the FormatOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of RateOfferingRelations
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException formatOfferingId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateOfferingRelationInfo> getRateOfferingRelationsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, 
                                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of RateOfferingRelations for a Rate and
     * FormatOffering.
     *
     * @param rateId the Id of the Rate
     * @param formatOfferingId the Id of the FormatOffering
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of RateOfferingRelations
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException rateId, formatOfferingId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateOfferingRelationInfo> getRateOfferingRelationsByRateAndFormatOffering(@WebParam(name = "rateId") String rateId, 
                                                                                          @WebParam(name = "formatOfferingId") String formatOfferingId, 
                                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for RateOfferingRelations based on the
     * criteria and returns a list of RateOfferingRelation
     * identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of RateOfferingRelation Ids matching the
     *         criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForRateOfferingRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for RateOfferingRelations based on the
     * criteria and returns a list of RateOfferingRelations
     * which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of RateOfferingRelations matching the
     *         criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateOfferingRelationInfo> searchForRateOfferingRelations(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a RateOfferingRelation. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current RateOfferingRelation and its directly
     * contained sub-objects or expanded to perform all tests related
     * to this RateOfferingRelation. If an identifier is present for
     * the RateOfferingRelation (and/or one of its contained
     * sub-objects) and a record is found for that identifier, the
     * validation checks if the RateOfferingRelation can be updated to
     * the new values. If an identifier is not present or a record
     * does not exist, the validation checks if the object with the
     * given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param rateId the Id of the Rate
     * @param formatOfferingId the Id of the FormatOffering
     * @param rateOfferingRelationTypeKey the identifier for the rate
     *        Type
     * @param rateOfferingRelationInfo the
     *        RateOfferingRelation information to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey, rateId,
     *         formatOfferingId, or rateOfferingRelationTypeKey is not
     *         found
     * @throws InvalidParameterException rateOfferingRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, rateId,
     *         formatOfferingId, rateOfferingRelationTypeKey,
     *         RateOfferingRelationInfo, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateRateOfferingRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                                   @WebParam(name = "rateId") String rateId, 
                                                                   @WebParam(name = "formatOfferingId") String formatOfferingId, 
                                                                   @WebParam(name = "rateOfferingRelationTypeKey") String rateOfferingRelationTypeKey, 
                                                                   @WebParam(name = "rateOfferingRelationInfo") RateOfferingRelationInfo rateOfferingRelationInfo, 
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new RateOfferingRelation. The RateOfferingRelation
     * Id, Type, and Meta information may nogt be set in the supplied
     * data.
     *
     * @param rateId the Id of the Rate
     * @param formatOfferingId the Id of the FormatOffering
     * @param rateOfferingRelationTypeKey the identifier for the Type
     *        of the new RateOfferingRelation
     * @param rateOfferingRelationInfo the data with which to create
     *        the RateOfferingRelation
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new RateOfferingRelation 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException rateId, formatOfferingId, or
     *         rateOfferingRelationTypeKey does not exist is not
     *         supported
     * @throws InvalidParameterException rateOfferingRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException rateId, formatOfferingId,
     *         rateOfferingRelationTypeKey, rateOfferingRelationInfo
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public RateOfferingRelationInfo createRateOfferingRelation(@WebParam(name = "rateId") String rateId, 
                                                               @WebParam(name = "formatOfferingId") String formatOfferingId, 
                                                               @WebParam(name = "rateOfferingRelationTypeKey") String rateOfferingRelationTypeKey, 
                                                               @WebParam(name = "rateOfferingRelationInfo") RateOfferingRelationInfo rateOfferingRelationInfo, 
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing RateOfferingRelation. The
     * RateOfferingRelation Id, Type, and Meta information may not be
     * changed.
     *
     * @param rateOfferingRelationId the identifier for the
     *        RateOfferingRelation to be updated
     * @param rateOfferingRelationInfo the new data for the
     *        RateOfferingRelation
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated RateOfferingRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException rateOfferingRelationId not found
     * @throws InvalidParameterException rateOfferingRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException rateOfferingRelationId,
     *         rateOfferingRelationInfo, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public RateOfferingRelationInfo updateRateOfferingRelation(@WebParam(name = "rateOfferingRelationId") String rateOfferingRelationId, 
                                                               @WebParam(name = "rateOfferingRelationInfo") RateOfferingRelationInfo rateOfferingRelationInfo, 
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
     * Changes the state of a RateOfferingRelation.
     *
     * @param rateOfferingRelationId the Id of the
     *        RateOfferingRelation
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be
     *         true.
     * @throws DoesNotExistException rateOfferingRelationId
     *         not found or stateKey not found in
     *         RateOfferingRelation Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException
     *         rateOfferingRelationId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeRateOfferingRelationState(@WebParam(name = "rateOfferingRelationId") String rateOfferingRelationId, 
                                                      @WebParam(name = "stateKey") String stateKey,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing RateOfferingRelation.
     *
     * @param rateOfferingRelationId the identifier for the
     *        RateOfferingRelation to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DependentObjectsExistException Rates are attached to
     *         this RateCatalog
     * @throws DoesNotExistException rateOfferingRelationId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException rateOfferingRelationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteRateOfferingRelation(@WebParam(name = "rateOfferingRelationId") String rateOfferingRelationId, 
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DependentObjectsExistException,
               DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;
}

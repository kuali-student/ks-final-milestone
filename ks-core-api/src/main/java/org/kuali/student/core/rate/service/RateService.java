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

package org.kuali.student.core.rate.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.core.rate.dto.CatalogRateInfo;
import org.kuali.student.core.rate.dto.RateInfo;

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
 * This service supports the management of rates. The managed entities
 * are:
 *
 * <dl>
 * <dt>CatalogRate</dt> <dd>This is an "entry" in the rate catalog. The
 *                      rate catalog represents a catalog of
 *                      "canonical-like" rates. Approved catalog entries
 *                      may be used to create a specific rate to an
 *                      external object.</dd>
 *
 * <dt>Rate</dt> <dd>The actual rate associated with an external object. The
 *               Rate is constrained by its related entry in the rate
 *               catalog.</dd>
 * </dl>
 *
 * @version 0.0.8
 * @author Kuali Student Services
 */

@WebService(name = "RateService", targetNamespace = RateServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface RateService {

    /** 
     * Retrieves a single CatalogRate by CatalogRate Id.
     *
     * @param catalogRateId the identifier for the rate to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the CatalogRate requested
     * @throws DoesNotExistException catalogRateId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogRateId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CatalogRateInfo getCatalogRate(@WebParam(name = "catalogRateId") String catalogRateId, 
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRates from a list of CatalogRate
     * Ids. The returned list may be in any order and if duplicates
     * Ids are supplied, a unique set may or may not be returned.
     *
     * @param catalogRateIds a list of CatalogRate identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRates
     * @throws DoesNotExistException a catalogRateId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException catalogRateIds, an Id in
     *         catalogRateIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateInfo> getCatalogRatesByIds(@WebParam(name = "catalogRateIds") List<String> catalogRateIds, 
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRate Ids by CatalogRate Type.
     *
     * @param catalogRateTypeKey an identifier for a CatalogRate Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRate identifiers matching
     *         catalogRateTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogRateTypeKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCatalogRateIdsByType(@WebParam(name = "catalogRateTypeKey") String catalogRateTypeKey, 
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of CatalogRates by code.
     *
     * @param code a CatalogRate code
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of CatalogRates
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException code or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateInfo> getCatalogRatesByCode(@WebParam(name = "code") String code, 
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CatalogRates based on the criteria and returns a
     * list of CatalogRate identifiers which match the search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CatalogRate Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCatalogRateIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for CatalogRates based on the criteria and returns a
     * list of CatalogRates which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of CatalogRates matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CatalogRateInfo> searchForCatalogRates(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a CatalogRate. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current CatalogRate and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * CatalogRate. If an identifier is present for the CatalogRate
     * (and/or one of its contained sub-objects) and a record is found
     * for that identifier, the validation checks if the CatalogRate
     * can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if
     * the object with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param catalogRateTypeKey the identifier for the rate Type
     * @param catalogRateInfo the CatalogRate information to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or
     *         catalogRateTypeKey is not found
     * @throws InvalidParameterException catalogRateInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException validationTypeKey,
     *         catalogRateTypeKey, catalogRateInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateCatalogRate(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                          @WebParam(name = "catalogRateTypeKey") String catalogRateTypeKey, 
                                                          @WebParam(name = "catalogRateInfo") CatalogRateInfo catalogRateInfo, 
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new CatalogRate. The CatalogRate Id, Type, and Meta
     * information may nogt be set in the supplied data.
     *
     * @param catalogRateTypeKey the identifier for the Type of the new
     *        CatalogRate
     * @param catalogRateInfo the data with which to create the CatalogRate
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new CatalogRate 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogRateTypeKey does not
     *         exist is not supported
     * @throws InvalidParameterException catalogRateInfo or contextInfo
     *         is not valid
     * @throws MissingParameterException catalogRateTypeKey,
     *         catalogRateInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public CatalogRateInfo createCatalogRate(@WebParam(name = "catalogRateTypeKey") String catalogRateTypeKey, 
                                             @WebParam(name = "catalogRateInfo") CatalogRateInfo catalogRateInfo, 
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing CatalogRate. The CatalogRate Id, Type, and
     * Meta information may not be changed.
     *
     * @param catalogRateId the identifier for the CatalogRate to be
     *        updated
     * @param catalogRateInfo the new data for the CatalogRate
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated CatalogRate
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogRateId not found
     * @throws InvalidParameterException catalogRateInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException catalogRateId,
     *         catalogRateInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public CatalogRateInfo updateCatalogRate(@WebParam(name = "catalogRateId") String catalogRateId, 
                                             @WebParam(name = "catalogRateInfo") CatalogRateInfo catalogRateInfo, 
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
     * Changes the state of a CatalogRate.
     *
     * @param catalogRateId the Id of the CatalogRate
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be
     *         true.
     * @throws DoesNotExistException catalogRateId not found or
     *         stateKey not found in CatalogRate Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogRateId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeCatalogRateState(@WebParam(name = "catalogRateId") String catalogRateId, 
                                             @WebParam(name = "stateKey") String stateKey,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing CatalogRate.
     *
     * @param catalogRateId the identifier for the CatalogRate to be
     *        deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DependentObjectsExistException Rates are attached to
     *         this RateCatalog
     * @throws DoesNotExistException catalogRateId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogRateId or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteCatalogRate(@WebParam(name = "catalogRateId") String catalogRateId, 
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DependentObjectsExistException,
               DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a single Rate by Rate Id.
     *
     * @param rateId the identifier for the rate to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the Rate requested
     * @throws DoesNotExistException rateId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException rateId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public RateInfo getRate(@WebParam(name = "rateId") String rateId, 
                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of Rates from a list of Rate Ids. The returned
     * list may be in any order and if duplicates Ids are supplied, a
     * unique set may or may not be returned.
     *
     * @param rateIds a list of Rate identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Rates
     * @throws DoesNotExistException a rateId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException rateIds, an Id in rateIds, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateInfo> getRatesByIds(@WebParam(name = "rateIds") List<String> rateIds, 
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of Rate Ids by Rate Type.
     *
     * @param rateTypeKey an identifier for a Rate Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Rate identifiers matching rateTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException rateTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getRateIdsByType(@WebParam(name = "rateTypeKey") String rateTypeKey, 
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Retrieves a list of Rates belonging to a CatalogRate.
     *
     * @param catalogRateId the identifier for a rate catalog entry
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Rates for the rate catalog entry or an empty
     *         list is none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException catalogRateId or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateInfo> getRatesByCatalogRate(@WebParam(name = "catalogRateId") String catalogRateId, 
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for Rates based on the criteria and returns a list of
     * Rate identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Rate Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForRateIds(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for Rates based on the criteria and returns a list of
     * Rates which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of Rates matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RateInfo> searchForRates(@WebParam(name = "criteria") QueryByCriteria criteria, 
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Validates a Rate. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * Rate and its directly contained sub-objects or expanded to
     * perform all tests related to this Rate. If an identifier is
     * present for the Rate (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation
     * checks if the Rate can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param catalogRateId the identifier for the rate catalog entry
     * @param rateTypeKey the identifier for the rate Type
     * @param rateInfo the Rate information to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey, catalogRateId,
     *         or rateTypeKey is not found
     * @throws InvalidParameterException rateInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException validationTypeKey,
     *         catalogRateId, rateTypeKey, rateInfo, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateRate(@WebParam(name = "validationTypeKey") String validationTypeKey, 
                                                   @WebParam(name = "catalogRateId") String catalogRateId, 
                                                   @WebParam(name = "rateTypeKey") String rateTypeKey, 
                                                   @WebParam(name = "rateInfo") RateInfo rateInfo, 
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Creates a new Rate. The Rate Id, Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param catalogRateId the identifier for the rate catalog entry
     * @param rateTypeKey the identifier for the Type of the new Rate
     * @param rateInfo the data with which to create the Rate
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new Rate 
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException catalogRateId or rateTypeKey does
     *         not exist or is not supported
     * @throws InvalidParameterException rateInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException catalogRateId, rateTypeKey,
     *         rateInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public RateInfo createRate(@WebParam(name = "catalogRateId") String catalogRateId, 
                               @WebParam(name = "rateTypeKey") String rateTypeKey, 
                               @WebParam(name = "rateInfo") RateInfo rateInfo, 
                               @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException;

    /** 
     * Updates an existing Rate. The Rate Id, Type, and Meta
     * information may not be changed.
     *
     * @param rateId the identifier for the Rate to be updated
     * @param rateInfo the new data for the Rate
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the updated Rate
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException rateId not found
     * @throws InvalidParameterException rateInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException rateId, rateInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at changing information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or
     *         the action was attempted on an out of date version
     */
    public RateInfo updateRate(@WebParam(name = "rateId") String rateId, 
                               @WebParam(name = "rateInfo") RateInfo rateInfo, 
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
     * Changes the state of a Rate.
     *
     * @param rateId the Id of the Rate
     * @param stateKey the identifier for the new State 
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException rateId not found or stateKey
     *         not found in Rate Lifecycle
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException rateId, stateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeRateState(@WebParam(name = "rateId") String rateId, 
                                      @WebParam(name = "stateKey") String stateKey,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /** 
     * Deletes an existing Rate.
     *
     * @param rateId the identifier for the Rate to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException rateId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException rateId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteRate(@WebParam(name = "rateId") String rateId, 
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;
}

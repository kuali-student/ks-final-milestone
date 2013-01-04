/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.lum.lo.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.search.service.SearchService;
import org.kuali.student.r1.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.r1.lum.lo.dto.LoLoRelationTypeInfo;
import org.kuali.student.r1.lum.lo.dto.LoTypeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.lum.lo.dto.LoRepositoryInfo;

/**
 * The Learning Objective Service allows for the creation and management of Learning Objectives.
 */
@WebService(name = "LearningObjectiveService", targetNamespace = LearningObjectiveServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LearningObjectiveService extends  SearchService // ,DictionaryService
{

    /**
     * Retrieves a single LoRepository by an LoRepository Key.
     *
     * @param loRepositoryKey   the identifier for the LoRepository to be retrieved
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return the LoRepository requested
     * @throws DoesNotExistException        loRepositoryKey is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loRepositoryKey or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public LoRepositoryInfo getLoRepository (@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoRepositories from a list of LoRepository Keys. The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param loRepositoryKeys  a list of LoRepository Keys
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoRepositories
     * @throws DoesNotExistException        an loRepositoryKey in the list was not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loRepositoryKeys, a key in the loRepositoryKeys, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoRepositoryInfo> getLoRepositoriesByKeys (@WebParam(name = "loRepositoryKeys") List<String> loRepositoryKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoRepositories.
     *
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoRepositories or an empty list if none found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoRepositoryInfo> getLoRepositories(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoRepository keys by LoRepository Type.
     *
     * @param loRepositoryTypeKey   a unique key for an LoRepository Type
     * @param contextInfo           information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoRepository keys matching loRepositoryTypeKey or an empty list if none found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loRepositoryTypeKey or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> getLoRepositoryKeysByType (@WebParam(name = "loRepositoryTypeKey") String loRepositoryTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for LoRepositories that meet the given search criteria.
     *
     * @param criteria      the search criteria
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoRepository keys matching the criteria
     * @throws InvalidParameterException    criteria or contextInfo is not valid
     * @throws MissingParameterException    criteria or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> searchForLoRepositoryKeys (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for LoRepositories that meet the given search criteria.
     *
     * @param criteria      the search criteria
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoRepositories matching the criteria
     * @throws InvalidParameterException    criteria or context is not valid
     * @throws MissingParameterException    criteria or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoRepositoryInfo> searchForLoRepositories (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an LoRepository. Depending on the value of validationType, this validation could be limited to tests on just the current LoRepository and its directly contained sub-objects or expanded to perform all tests related to this LoRepository. If an identifier is present for the LoRepository (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the LoRepository can be updated to the new values. If an identifier is not present or a record does not exist, the validation checks if the LoRepository with the given data can be created.
     *
     * @param validationTypeKey     the identifier for the validation Type
     * @param loRepositoryTypeKey   the identifier for the LoRepository Type
     * @param loRepositoryInfo      the LoRepository to be validated
     * @param contextInfo           information containing the principalId and locale information about the caller of the service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException        validationTypeKey or loRepositoryTypeKey is not found
     * @throws InvalidParameterException    loRepositoryInfo or contextInfo is not valid
     * @throws MissingParameterException    validationTypeKey, loRepositoryTypeKey, loRepositoryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLoRepository (@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "loRepositoryTypeKey") String loRepositoryTypeKey, @WebParam(name = "loRepositoryInfo") LoRepositoryInfo loRepositoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new LoRepository. The LoRepository Type, and Meta information may not be set in the supplied data object.
     *
     * @param loRepositoryKey       a unique key for the new LoRepository
     * @param loRepositoryTypeKey   the identifier for the Type of LoRepository to be created
     * @param loRepositoryInfo      the data with which to create the LoRepository
     * @param contextInfo           information containing the principalId and locale information about the caller of the service operation
     * @return the new LoRepository
     * @throws AlreadyExistsException       loRepositoryKey already exists
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loRepositoryTypeKey does not exist or is not supported
     * @throws InvalidParameterException    loRepositoryInfo or contextInfo is not valid
     * @throws MissingParameterException    loRepositoryTypeKey, loRepositoryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public LoRepositoryInfo createLoRepository (@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "loRepositoryTypeKey") String loRepositoryTypeKey, @WebParam(name = "loRepositoryInfo") LoRepositoryInfo loRepositoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing LoRepository. The LoRepository Key, Type, and Meta information may not be changed.
     *
     * @param loRepositoryKey   the identifier for the LoRepository to be updated
     * @param loRepositoryInfo  the new data for the LoRepository
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return the updated LoRepository
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loRepositoryKey is not found
     * @throws InvalidParameterException    loRepositoryInfo or contextInfo is not valid
     * @throws MissingParameterException    loRepositoryKey, loRepositoryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public LoRepositoryInfo updateLoRepository (@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "loRepositoryInfo") LoRepositoryInfo loRepositoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing LoRepository.
     *
     * @param loRepositoryKey   the identifier for the LoRepository to be deleted
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException        loRepositoryKey is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loRepositoryKey or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public StatusInfo deleteLoRepository (@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single Lo by an Lo Id.
     *
     * @param loId          the identifier for the Lo to be retrieved
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the Lo requested
     * @throws DoesNotExistException        loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public LoInfo getLo (@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Los from a list of Lo Ids. The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param loIds         a list of Lo identifiers
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of Los
     * @throws DoesNotExistException        an loId in the list was not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loIds, an Id in the loIds, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoInfo> getLosByIds (@WebParam(name = "loIds") List<String> loIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Lo Ids by Lo Type.
     *
     * @param loTypeKey     an identifier for an Lo Type
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of Lo identifiers matching loTypeKey or an empty list if none found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loTypeKey or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> getLoIdsByType (@WebParam(name = "loTypeKey") String loTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Los by LoRepository.
     *
     * @param loRepositoryKey   a key for the LoRepository
     * @param loTypeKey   the identifier for the Lo Type
     * @param loStateKey   the identifier for the Lo State
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return a list of Los for the LoRepository
     * @throws DoesNotExistException        loRepositoryId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loRepositoryId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoInfo> getLosByLoRepository(@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name="loTypeKey")String loTypeKey, @WebParam(name="loStateKey")String loStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Los by LoCategory.
     *
     * @param loCategoryId  an identifier for the LoCategory
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of Los for the LoCategory
     * @throws DoesNotExistException        loCategoryId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoInfo> getLosByLoCategory(@WebParam(name = "loCategoryId") String loCategoryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Los by related Lo.
     *
     * @param relatedLoId       an identifier for the related Lo
     * @param loLoRelationTypeKey   the identifier for the LoLoRelation Type
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return a list of Los for the related Lo
     * @throws DoesNotExistException        relatedLoId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    relatedLoId, loLoRelationTypeKey, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoInfo> getLosByRelatedLoId(@WebParam(name = "relatedLoId") String relatedLoId, @WebParam(name = "loLoRelationTypeKey") String loLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of related Los by Lo.
     *
     * @param loId                  an identifier for the Lo
     * @param loLoRelationTypeKey   the identifier for the LoLoRelation Type
     * @param contextInfo           information containing the principalId and locale information about the caller of the service operation
     * @return a list of related Los for the Lo
     * @throws DoesNotExistException        loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoInfo> getRelatedLosByLoId(@WebParam(name = "loId") String loId, @WebParam(name = "loLoRelationTypeKey") String loLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Los that meet the given search criteria.
     *
     * @param criteria      the search criteria
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of Lo identifiers matching the criteria
     * @throws InvalidParameterException    criteria or contextInfo is not valid
     * @throws MissingParameterException    criteria or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> searchForLoIds (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Los that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of Los matching the criteria
     * @throws InvalidParameterException    criteria or context is not valid
     * @throws MissingParameterException    criteria or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoInfo> searchForLos (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an Lo. Depending on the value of validationType, this validation could be limited to tests on just the current Lo and its directly contained sub-objects or expanded to perform all tests related to this Lo. If an identifier is present for the Lo (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the Lo can be updated to the new values. If an identifier is not present or a record does not exist, the validation checks if the Lo with the given data can be created.
     *
     * @param validationTypeKey the identifier for the Lo Type to be validated
     * @param loInfo            the Lo to be validated
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException        validationTypeKey or loTypeKey is not found
     * @throws InvalidParameterException    loInfo or contextInfo is not valid
     * @throws MissingParameterException    validationTypeKey, loTypeKey, loInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLo (@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "loInfo") LoInfo loInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Lo. The Lo Id, Type, and Meta information may not be set in the supplied data object.
     *
     * @param loTypeKey     the identifier for the Type of Lo to be created
     * @param string 
     * @param loInfo        the data with which to create the Lo
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the new Lo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loTypeKey does not exist or is not supported
     * @throws InvalidParameterException    loInfo or contextInfo is not valid
     * @throws MissingParameterException    loTypeKey, loInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public LoInfo createLo( @WebParam(name = "repositoryId")String repositoryId, @WebParam(name = "loType")String loType, 
            @WebParam(name = "loInfo")LoInfo loInfo, @WebParam(name = "contextInfo")ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Lo. The Lo Id, Type, and Meta information may not be changed.
     *
     * @param loId          the identifier for the Lo to be updated
     * @param loInfo        the new data for the Lo
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the updated Lo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loId is not found
     * @throws InvalidParameterException    loInfo or contextInfo is not valid
     * @throws MissingParameterException    loId, loInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public LoInfo updateLo (@WebParam(name = "loId") String loId, @WebParam(name = "loInfo") LoInfo loInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Lo.
     *
     * @param loId          the identifier for the Lo to be deleted
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException        loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws DependentObjectsExistException 
     */
    public StatusInfo deleteLo (@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;


    /**
     * Retrieves a single LoCategory by an LoCategory Id.
     *
     * @param loCategoryId  the identifier for the LoCategory to be retrieved
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the LoCategory requested
     * @throws DoesNotExistException        loCategoryId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public LoCategoryInfo getLoCategory (@WebParam(name = "loCategoryId") String loCategoryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoCategories from a list of LoCategory Ids. The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param loCategoryIds a list of LoCategory identifiers
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoCategories
     * @throws DoesNotExistException        an loCategoryId in the list was not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryIds, an Id in the loCategoryIds, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoCategoryInfo> getLoCategoriesByIds (@WebParam(name = "loCategoryIds") List<String> loCategoryIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Retrieves a list of LoCategories by their associated repository key
     * 
     * @param loRepositoryKey a list of LoCategory identifiers
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoCategories
     * @throws DoesNotExistException        an loCategoryId in the list was not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryIds, an Id in the loCategoryIds, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoCategoryInfo> getLoCategoriesByLoRepository (@WebParam(name = "repositoryKey") String loRepositoryKey, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Retrieves a list of LoCategory Ids by LoCategory Type.
     *
     * @param loCategoryTypeKey an identifier for an LoCategory Type
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoCategory identifiers matching loCategoryTypeKey or an empty list if none found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryTypeKey or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> getLoCategoryIdsByType (@WebParam(name = "loCategoryTypeKey") String loCategoryTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoCategories by Lo.
     *
     * @param loId          an identifier for the Lo
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoCategories for the Lo
     * @throws DoesNotExistException        loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoCategoryInfo> getLoCategoriesByLo(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for LoCategories that meet the given search criteria.
     *
     * @param criteria      the search criteria
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoCategory identifiers matching the criteria
     * @throws InvalidParameterException    criteria or contextInfo is not valid
     * @throws MissingParameterException    criteria or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> searchForLoCategoryIds (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for LoCategories that meet the given search criteria.
     *
     * @param criteria      the search criteria
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoCategories matching the criteria
     * @throws InvalidParameterException    criteria or context is not valid
     * @throws MissingParameterException    criteria or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoCategoryInfo> searchForLoCategories (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an LoCategory. Depending on the value of validationType, this validation could be limited to tests on just the current LoCategory and its directly contained sub-objects or expanded to perform all tests related to this LoCategory. If an identifier is present for the LoCategory (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the LoCategory can be updated to the new values. If an identifier is not present or a record does not exist, the validation checks if the LoCategory with the given data can be created.
     *
     * @param validationTypeKey the identifier for the LoCategory Type to be validated
     * @param loCategoryInfo    the LoCategory to be validated
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException     validationTypeKey or loCategoryTypeKey is not found
     * @throws InvalidParameterException loCategoryInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, loCategoryTypeKey, loCategoryInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLoCategory (@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "loCategoryInfo") LoCategoryInfo loCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new LoCategory. The LoCategory Id, Type, and Meta information may not be set in the supplied data object.
     *
     * @param loCategoryTypeKey   the identifier for the Type of LoCategory to be created
     * @param loCategoryInfo      the data with which to create the LoCategory
     * @param contextInfo         information containing the principalId and locale information about the caller of the service operation
     * @return the new LoCategory
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loCategoryTypeKey does not exist or is not supported
     * @throws InvalidParameterException    loCategoryInfo or contextInfo is not valid
     * @throws MissingParameterException    loCategoryTypeKey, loCategoryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public LoCategoryInfo createLoCategory (@WebParam(name = "loCategoryTypeKey") String loCategoryTypeKey, @WebParam(name = "loCategoryInfo") LoCategoryInfo loCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing LoCategory. The LoCategory Id, Type, and Meta information may not be changed.
     *
     * @param loCategoryId   the identifier for the LoCategory to be updated
     * @param loCategoryInfo the new data for the LoCategory
     * @param contextInfo    information containing the principalId and locale information about the caller of the service operation
     * @return the updated LoCategory
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loCategoryId is not found
     * @throws InvalidParameterException    loCategoryInfo or contextInfo is not valid
     * @throws MissingParameterException    loCategoryId, loCategoryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public LoCategoryInfo updateLoCategory (@WebParam(name = "loCategoryId") String loCategoryId, @WebParam(name = "loCategoryInfo") LoCategoryInfo loCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing LoCategory.
     *
     * @param loCategoryId  the identifier for the LoCategory to be deleted
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException        loCategoryId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws DependentObjectsExistException 
     */
    public StatusInfo deleteLoCategory (@WebParam(name = "loCategoryId") String loCategoryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;

    /**
     * Deletes existing LoCategories of a Lo.
     *
     * @param loId          the identifier for the Lo for which LoCategories are to be deleted
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException        loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public StatusInfo deleteLoCategoryByLo(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a LoCategory to a Lo
     *
     * @param loCategoryId  the identifier for the LoCategory
     * @param loId          the identifier for the Lo
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the status of the delete operation. This must always be true.
     * @throws AlreadyExistsException       LoCategory already exists for Lo
     * @throws DoesNotExistException        loCategoryId or loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryId, loId, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws UnsupportedActionException   loCategoryId and loId are not in the same repository
     */
    public StatusInfo addLoCategoryToLo(@WebParam(name = "loCategoryId") String loCategoryId, @WebParam(name = "loId")String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

   /**
     * Removes a LoCategory from a Lo
     *
     * @param loCategoryId  the identifier for the LoCategory
     * @param loId          the identifier for the Lo
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return the status of the delete operation. This must always be true.
     * @throws AlreadyExistsException       LoCategory already exists for Lo
     * @throws DoesNotExistException        loCategoryId or loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loCategoryId, loId, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws UnsupportedActionException   loCategoryId and loId are not in the same repository
     */
    public StatusInfo removeLoCategoryFromLo(@WebParam(name = "loCategoryId") String loCategoryId, @WebParam(name = "loId")String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /**
     * Retrieves a single LoLoRelation by an LoLoRelation Id.
     *
     * @param loLoRelationId    the identifier for the LoLoRelation to be retrieved
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return the LoLoRelation requested
     * @throws DoesNotExistException        loLoRelationId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loLoRelationId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public LoLoRelationInfo getLoLoRelation (@WebParam(name = "loLoRelationId") String loLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoLoRelations from a list of LoLoRelation Ids. The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param loLoRelationIds   a list of LoLoRelation identifiers
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoLoRelations
     *
     * @throws DoesNotExistException        an loLoRelationId in the list was not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loLoRelationIds, an Id in the loLoRelationIds, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoLoRelationInfo> getLoLoRelationsByIds (@WebParam(name = "loLoRelationIds") List<String> loLoRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoLoRelation Ids by LoLoRelation Type.
     *
     * @param loLoRelationTypeKey   an identifier for an LoLoRelation Type
     * @param contextInfo           information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoLoRelation identifiers matching loLoRelationTypeKey or an empty list if none found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loLoRelationTypeKey or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> getLoLoRelationIdsByType (@WebParam(name = "loLoRelationTypeKey") String loLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LoLoRelations by Lo.
     *
     * @param loId      an identifier for the Lo
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoLoRelations for the Lo
     * @throws DoesNotExistException        loId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoLoRelationInfo> getLoLoRelationsByLoId(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for LoLoRelations that meet the given search criteria.
     *
     * @param criteria      the search criteria
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoLoRelation identifiers matching the criteria
     * @throws InvalidParameterException    criteria or contextInfo is not valid
     * @throws MissingParameterException    criteria or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<String> searchForLoLoRelationIds (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for LoLoRelations that meet the given search criteria.
     *
     * @param criteria      the search criteria
     * @param contextInfo   information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoLoRelations matching the criteria
     * @throws InvalidParameterException    criteria or context is not valid
     * @throws MissingParameterException    criteria or context is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<LoLoRelationInfo> searchForLoLoRelations (@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an LoLoRelation. Depending on the value of validationType, this validation could be limited to tests on just the current LoLoRelation and its directly contained sub-objects or expanded to perform all tests related to this LoLoRelation. If an identifier is present for the LoLoRelation (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the LoLoRelation can be updated to the new values. If an identifier is not present or a record does not exist, the validation checks if the LoLoRelation with the given data can be created.
     *
     * @param validationTypeKey     the identifier for the LoLoRelation Type to be validated
     * @param loLoRelationInfo      the LoLoRelation to be validated
     * @param contextInfo           information containing the principalId and locale information about the caller of the service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException        validationTypeKey or loLoRelationTypeKey is not found
     * @throws InvalidParameterException    loLoRelationInfo or contextInfo is not valid
     * @throws MissingParameterException    validationTypeKey, loLoRelationTypeKey, loLoRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLoLoRelation (@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "loLoRelationInfo") LoLoRelationInfo loLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new LoLoRelation. The LoLoRelation Id, Type, and Meta information may not be set in the supplied data object.
     *
     * @param loLoRelationTypeKey   the identifier for the Type of LoLoRelation to be created
     * @param type 
     * @param relatedLoId 
     * @param loLoRelationInfo      the data with which to create the LoLoRelation
     * @param contextInfo           information containing the principalId and locale information about the caller of the service operation
     * @return the new LoLoRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loLoRelationTypeKey does not exist or is not supported
     * @throws InvalidParameterException    loLoRelationInfo or contextInfo is not valid
     * @throws MissingParameterException    loLoRelationTypeKey, loLoRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information designated as read only
     */
    public LoLoRelationInfo createLoLoRelation(@WebParam(name = "loLoRelationTypeKey")String loLoRelationTypeKey,
            @WebParam(name = "loLoRelationInfo")LoLoRelationInfo loLoRelationInfo, @WebParam(name = "contextInfo")ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing LoLoRelation. The LoLoRelation Id, Type, and Meta information may not be changed.
     *
     * @param loLoRelationId    the identifier for the LoLoRelation to be updated
     * @param loLoRelationInfo  the new data for the LoLoRelation
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return the updated LoLoRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        loLoRelationId is not found
     * @throws InvalidParameterException    loLoRelationInfo or contextInfo is not valid
     * @throws MissingParameterException    loLoRelationId, loLoRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the action was attempted on an out of date version
     */
    public LoLoRelationInfo updateLoLoRelation (@WebParam(name = "loLoRelationId") String loLoRelationId, @WebParam(name = "loLoRelationInfo") LoLoRelationInfo loLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing LoLoRelation.
     *
     * @param loLoRelationId    the identifier for the LoLoRelation to be deleted
     * @param contextInfo       information containing the principalId and locale information about the caller of the service operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException        loLoRelationId is not found
     * @throws InvalidParameterException    contextInfo is not valid
     * @throws MissingParameterException    loLoRelationId or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     */
    public StatusInfo deleteLoLoRelation (@WebParam(name = "loLoRelationId") String loLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @Deprecated
    /**
     * 
     * This method ...
     * 
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    public List<LoTypeInfo> getLoTypes(@WebParam(name = "contextInfo")ContextInfo contextInfo)
			throws OperationFailedException;
    
    @Deprecated
    /**
     * 
     * This method ...
     * 
     * @param loTypeKey
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
	public LoTypeInfo getLoType(@WebParam(name = "loTypeKey")String loTypeKey,@WebParam(name = "contextInfo") ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;
    
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param contextInfo
	 * @return
	 * @throws OperationFailedException
	 */
	public List<LoLoRelationTypeInfo> getLoLoRelationTypes(@WebParam(name = "contextInfo")ContextInfo contextInfo)
			throws OperationFailedException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param loLoRelationTypeKey
	 * @param contextInfo
	 * @return
	 * @throws OperationFailedException
	 * @throws MissingParameterException
	 * @throws DoesNotExistException
	 */
	public LoLoRelationTypeInfo getLoLoRelationType(@WebParam(name = "loLoRelationTypeKey")String loLoRelationTypeKey,
			@WebParam(name = "contextInfo")ContextInfo contextInfo) throws OperationFailedException,
			MissingParameterException, DoesNotExistException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param loTypeKey
	 * @param relatedLoTypeKey
	 * @param contextInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	public List<String> getAllowedLoLoRelationTypesForLoType(@WebParam(name = "loTypeKey")String loTypeKey,
			@WebParam(name = "relatedLoTypeKey")String relatedLoTypeKey,@WebParam(name = "contextInfo") ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param loIds
	 * @param contextInfo
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	public List<LoInfo> getLoByIdList(List<String> loIds, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param loRepositoryKey
	 * @param contextInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	public List<LoCategoryInfo> getLoCategories(@WebParam(name = "loRepositoryKey")String loRepositoryKey,
			@WebParam(name = "contextInfo")ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param loId
	 * @param contextInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	public List<LoCategoryInfo> getLoCategoriesForLo(@WebParam(name = "loId")String loId,
			@WebParam(name = "contextInfo")ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param loCategoryTypeKey
	 * @param contextInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	public LoCategoryTypeInfo getLoCategoryType(@WebParam(name = "loCategoryTypeKey")String loCategoryTypeKey,
			@WebParam(name = "contextInfo")ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @param loRepositoryKey
	 * @param loTypeKey
	 * @param loStateKey
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	public List<LoInfo> getLosByRepository(@WebParam(name = "loRepositoryKey")String loRepositoryKey, @WebParam(name = "loTypeKey")String loTypeKey,
			@WebParam(name = "loStateKey")String loStateKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;
	
	@Deprecated
	/**
	 * 
	 * This method ...
	 * 
	 * @return
	 * @throws OperationFailedException
	 */
	public List<LoCategoryTypeInfo> getLoCategoryTypes()
			throws OperationFailedException;

}
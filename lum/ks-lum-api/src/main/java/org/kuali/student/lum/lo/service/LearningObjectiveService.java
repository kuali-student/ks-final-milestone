/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lo.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationTypeInfo;
import org.kuali.student.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;

/**
 *
 * @Author KSContractMojo
 * @Author jimt
 * @Since Tue Dec 08 10:00:55 PST 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Learning+Objective+Service+v1.0-rc3">LearningObjectiveService</>
 *
 */
@WebService(name = "LearningObjectiveService", targetNamespace = "http://student.kuali.org/wsdl/lo") // TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LearningObjectiveService extends DictionaryService, SearchService { 
    /** 
     * Retrieves the list of learning objective repositories known by this service.
     * @return list of learning objective repository information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoRepositoryInfo> getLoRepositories() throws OperationFailedException;

    /** 
     * Retrieves information about a particular learning objective repository.
     * @param loRepositoryKey learning objective repository identifier
     * @return information about a learning objective repository
     * @throws DoesNotExistException specified learning objective repository not found
     * @throws InvalidParameterException invalid loRepositoryKey
     * @throws MissingParameterException loRepositoryKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LoRepositoryInfo getLoRepository(@WebParam(name="loRepositoryKey")String loRepositoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning objective category types known by this service.
     * @return list of learning objective category type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoCategoryTypeInfo> getLoCategoryTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular learning objective category type.
     * @param loCategoryTypeKey learning objective category type identifier
     * @return information about a learning objective category type
     * @throws DoesNotExistException specified learning objective category type not found
     * @throws InvalidParameterException invalid loCategoryTypeKey
     * @throws MissingParameterException loCategoryTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LoCategoryTypeInfo getLoCategoryType(@WebParam(name="loCategoryTypeKey")String loCategoryTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning objective types known by this service.
     * @return list of learning objective type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoTypeInfo> getLoTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular learning objective type.
     * @param loTypeKey learning objective type identifier
     * @return information about a learning objective type
     * @throws DoesNotExistException specified learning objective type not found
     * @throws InvalidParameterException invalid loTypeKey
     * @throws MissingParameterException loTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LoTypeInfo getLoType(@WebParam(name="loTypeKey")String loTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the complete list of LO to LO relation types
     * @return list of LO to LO relation type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoLoRelationTypeInfo> getLoLoRelationTypes() throws OperationFailedException;

    /** 
     * Retrieves the LO to LO relation type
     * @param loLoRelationTypeKey Key of the LO to LO Relation Type
     * @return LO to LO relation type information
     * @throws OperationFailedException unable to complete request
     * @throws MissingParameterException 
     * @throws DoesNotExistException 
	 */
    public LoLoRelationTypeInfo getLoLoRelationType(@WebParam(name="loLoRelationTypeKey")String loLoRelationTypeKey) throws OperationFailedException, MissingParameterException, DoesNotExistException;

    /** 
     * Retrieves the list of allowed relation types between the two specified LO Types
     * @param loTypeKey Key of the first LO Type
     * @param relatedLoTypeKey Key of the second LO Type
     * @return list of LO to LO relation types
     * @throws DoesNotExistException loTypeKey, relatedLoTypeKey not found
     * @throws InvalidParameterException invalid loTypeKey, relatedLoTypeKey
     * @throws MissingParameterException missing loTypeKey, relatedLoTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLoLoRelationTypesForLoType(@WebParam(name="loTypeKey")String loTypeKey, @WebParam(name="relatedLoTypeKey")String relatedLoTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about all the learning objective categories in a given learning objective repository.
     * @param loRepositoryKey loRepository identifier
     * @return list of learning objective category information
     * @throws DoesNotExistException loRepositoryKey not found
     * @throws InvalidParameterException invalid loRepositoryKey
     * @throws MissingParameterException missing loRepositoryKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoCategoryInfo> getLoCategories(@WebParam(name="loRepositoryKey")String loRepositoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about an learning objective category.
     * @param loCategoryId loCategory identifier
     * @return LoCategory
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId
     * @throws MissingParameterException missing loCategoryId
     * @throws OperationFailedException unable to complete request
	 */
    public LoCategoryInfo getLoCategory(@WebParam(name="loCategoryId")String loCategoryId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information on a single learning objective.
     * @param loId learning objective identifier
     * @return information about a single learning objective
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public LoInfo getLo(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information on multiple learning objectives.
     * @param loId list of learning objective identifiers
     * @return list of learning objective information
     * @throws InvalidParameterException invalid loIdList
     * @throws MissingParameterException missing loIdList
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLoByIdList(@WebParam(name="loId")List<String> loId) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves learning objectives from a given repository of a given type and state.
     * @param loRepositoryKey repository identifier
     * @param loTypeKey learning objective type identifier
     * @param loStateKey learning objective state identifier
     * @return list of learning objectives
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more missing parameters missing
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLosByRepository(@WebParam(name="loRepositoryKey")String loRepositoryKey, @WebParam(name="loTypeKey")String loTypeKey, @WebParam(name="loStateKey")String loStateKey) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of learning objective categories for a specific learning objective.
     * @param loId learning objective identifier
     * @return list of learning objective category information
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoCategoryInfo> getLoCategoriesForLo(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of learning objectives that have a specific learning objective category.
     * @param loCategoryId learning objective category identifier
     * @return list of learning objective information
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId
     * @throws MissingParameterException missing loCategoryId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLosByLoCategory(@WebParam(name="loCategoryId")String loCategoryId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LO information for the LOs related to a specified LO Id with a certain LU to LU relation type (getRelatedLosByLoId from the other direction)
     * @param relatedLoId identifier of the LO
     * @param loLoRelationType the LO to LO relation type
     * @return list of LO information
     * @throws DoesNotExistException relatedLoId, loLoRelationType not found
     * @throws InvalidParameterException invalid relatedLoId, loLoRelationType
     * @throws MissingParameterException missing relatedLoId, loLoRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLosByRelatedLoId(@WebParam(name="relatedLoId")String relatedLoId, @WebParam(name="loLoRelationType")String loLoRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related LO information for the specified LO Id and LU to LU relation type (getLosByRelatedLoId from the other direction)
     * @param loId identifier of the LO
     * @param loLoRelationType the LO to LO relation type
     * @return list of LO information
     * @throws DoesNotExistException loId, loLoRelationType not found
     * @throws InvalidParameterException invalid loId, loLoRelationType
     * @throws MissingParameterException missing loId, loLoRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getRelatedLosByLoId(@WebParam(name="loId")String loId, @WebParam(name="loLoRelationType")String loLoRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the relationship information between LOs for a particular relationship identifier
     * @param loLoRelationId identifier of the LO to LO relationship
     * @return information on the relation between two LOs
     * @throws DoesNotExistException loLoRelationId not found
     * @throws InvalidParameterException invalid loLoRelationId
     * @throws MissingParameterException missing loLoRelationId
     * @throws OperationFailedException unable to complete request
	 */
    public LoLoRelationInfo getLoLoRelation(@WebParam(name="loLoRelationId")String loLoRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the relationship information between LOs for a particular LO.
     * @param loId identifier of the LO
     * @return all relations (both directions) from an LO
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoLoRelationInfo> getLoLoRelationsByLoId(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a learning objective category. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning objective category (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning objective category can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param loCategoryInfo learning objective category information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, loCategoryInfo
     * @throws MissingParameterException missing validationTypeKey, loCategoryInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateLoCategory(@WebParam(name="validationType")String validationType, @WebParam(name="loCategoryInfo")LoCategoryInfo loCategoryInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a learning objective category in a particular learning objective repository.
     * @param loRepositoryKey identifier of the learning objective repository
     * @param loCategoryTypeKey identifier of the learning objective category type
     * @param loCategoryInfo information to create the learning objective category
     * @return information on the created learning objective category
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loRepositoryKey, loCategoryTypeKey not found
     * @throws InvalidParameterException invalid loRepositoryKey, loCategoryTypeKey, loCategoryInfo
     * @throws MissingParameterException missing loRepositoryKey, loCategoryTypeKey, loCategoryInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LoCategoryInfo createLoCategory(@WebParam(name="loRepositoryKey")String loRepositoryKey, @WebParam(name="loCategoryTypeKey")String loCategoryTypeKey, @WebParam(name="loCategoryInfo")LoCategoryInfo loCategoryInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a learning objective category in a particular learning objective repository.
     * @param loCategoryId the learning objective category identifier
     * @param loCategoryInfo information to create the learning objective category
     * @return information on the uppdated learning objective category
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId, loCategoryInfo
     * @throws MissingParameterException missing loCategoryId, loCategoryInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version.
	 */
    public LoCategoryInfo updateLoCategory(@WebParam(name="loCategoryId")String loCategoryId, @WebParam(name="loCategoryInfo")LoCategoryInfo loCategoryInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a learning objective category.
     * @param loCategoryId learning objective category identifier
     * @return status of the operation (success/failure)
     * @throws DependentObjectsExistException learning objective category is still attached to one or more learning objectives
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId
     * @throws MissingParameterException missing loCategoryId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLoCategory(@WebParam(name="loCategoryId")String loCategoryId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a learning objective. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning objective (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning objective can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param loInfo learning objective information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, loInfo
     * @throws MissingParameterException missing validationTypeKey, loInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateLo(@WebParam(name="validationType")String validationType, @WebParam(name="loInfo")LoInfo loInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a learning objective.
     * @param loRepositoryKey identifier of the learning objective repository
     * @param loType type for the learning objective
     * @param loInfo information to create the learning objective
     * @return information on the created learning objective
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loRepositoryKey, loType not found
     * @throws InvalidParameterException invalid loRepositoryKey, loType, loInfo
     * @throws MissingParameterException missing loRepositoryKey, loType, loInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LoInfo createLo(@WebParam(name="loRepositoryKey")String loRepositoryKey, @WebParam(name="loType")String loType, @WebParam(name="loInfo")LoInfo loInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Update a learning objective.
     * @param loId identifier of the learning objective to update
     * @param loInfo updated information on the learning objective
     * @return information on the updated learning objective
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId, loInfo
     * @throws MissingParameterException missing loId, loInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version.
	 */
    public LoInfo updateLo(@WebParam(name="loId")String loId, @WebParam(name="loInfo")LoInfo loInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Delete a learning objective.
     * @param loId identifier of the learning objective to delete
     * @return status of the operation (success or failure)
     * @throws DependentObjectsExistException removing the learning objective will orphan one or more child learning objectives
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLo(@WebParam(name="loId")String loId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Add a existing learning objective category to an existing learning objective in the same repository.
     * @param loCategoryId identifier of the learning objective category to add
     * @param loId identifier of the learning objective
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException loCategoryId is already set as a category of the LoId
     * @throws DoesNotExistException loId, loCategoryId not found
     * @throws InvalidParameterException invalid loId, loCategoryId
     * @throws MissingParameterException missing loId, loCategoryId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException loId, loCategoryId are not in the same repository
	 */
    public StatusInfo addLoCategoryToLo(@WebParam(name="loCategoryId")String loCategoryId, @WebParam(name="loId")String loId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove a existing learning objective category from an existing learning objective in the same repository.
     * @param loCategoryId identifier of the learning objective category to remove
     * @param loId identifier of the learning objective
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException loId, loCategoryId not found
     * @throws InvalidParameterException invalid loId, loCategoryId
     * @throws MissingParameterException missing loId, loCategoryId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException loId, loCategoryId are not in the same repository
	 */
    public StatusInfo removeLoCategoryFromLo(@WebParam(name="loCategoryId")String loCategoryId, @WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Validates a loLoRelation. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the loLoRelation (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the relationship can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param loLoRelationInfo loLoRelation information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, loLoRelationInfo
     * @throws MissingParameterException missing validationTypeKey, loLoRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateLoLoRelation(@WebParam(name="validationType")String validationType, @WebParam(name="loLoRelationInfo")LoLoRelationInfo loLoRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a directional relationship between two LOs
     * @param loId identifier of the first LO in the relationship - The From or Parent of the relation
     * @param relatedLoId identifier of the second LO in the relationship to be related to - the To or Child of the Relation
     * @param loLoRelationType the type of the LO to LO relationship
     * @param loLoRelationInfo information about the relationship between the two LOs
     * @return the created LO to LO relation information
     * @throws AlreadyExistsException relationship already exists
     * @throws CircularRelationshipException Relation would create a loop (with ancestor Lo)
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loId, relatedLoId, luLuRelationType not found
     * @throws InvalidParameterException invalid loId, relatedLoId, luluRelationType, loLoRelationInfo
     * @throws MissingParameterException missing loId, relatedLoId, luluRelationType, loLoRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LoLoRelationInfo createLoLoRelation(@WebParam(name="loId")String loId, @WebParam(name="relatedLoId")String relatedLoId, @WebParam(name="loLoRelationType")String loLoRelationType, @WebParam(name="loLoRelationInfo")LoLoRelationInfo loLoRelationInfo) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a relationship between two LOs
     * @param loLoRelationId identifier of the LO to LO relation to be updated
     * @param loLoRelationInfo changed information about the LO to LO relationship
     * @return the updated LO to LO relation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loLoRelationId not found
     * @throws InvalidParameterException invalid loLoRelationId, loLoRelationInfo
     * @throws MissingParameterException missing loLoRelationId, loLoRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public LoLoRelationInfo updateLoLoRelation(@WebParam(name="loLoRelationId")String loLoRelationId, @WebParam(name="loLoRelationInfo")LoLoRelationInfo loLoRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a relationship between two LOs
     * @param loLoRelationId identifier of LO to LO relationship to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException loLoRelationId not found
     * @throws InvalidParameterException invalid loLoRelationId
     * @throws MissingParameterException missing loLoRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DependentObjectsExistException 
	 */
    public StatusInfo deleteLoLoRelation(@WebParam(name="loLoRelationId")String loLoRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;

}
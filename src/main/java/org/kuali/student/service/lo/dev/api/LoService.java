/*
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.service.lo.dev.api;


import java.util.List;


public interface LoService
{
	
	/**
	* Retrieves the list of learning objective repositories known by this service.
	* 
	* @return list of learning objective repository information
	*/
	public List<LoRepositoryInfo> getLoRepositories()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular learning objective repository.
	* 
	* @param loRepositoryKey - loRepositoryKey - learning objective repository 
	* identifier
	* @return information about a learning objective repository
	*/
	public LoRepositoryInfo getLoRepository(String loRepositoryKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of learning objective category types known by this service.
	* 
	* @return list of learning objective category type information
	*/
	public List<LoCategoryTypeInfo> getLoCategoryTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular learning objective category type.
	* 
	* @param loCategoryTypeKey - loCategoryTypeKey - learning objective category type 
	* identifier
	* @return information about a learning objective category type
	*/
	public LoCategoryTypeInfo getLoCategoryType(String loCategoryTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of learning objective types known by this service.
	* 
	* @return list of learning objective type information
	*/
	public List<LoTypeInfo> getLoTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular learning objective type.
	* 
	* @param loTypeKey - loTypeKey - learning objective type identifier
	* @return information about a learning objective type
	*/
	public LoTypeInfo getLoType(String loTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the complete list of LO to LO relation types
	* 
	* @return list of LO to LO relation type information
	*/
	public List<LoLoRelationTypeInfo> getLoLoRelationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves the LO to LO relation type
	* 
	* @param loLoRelationTypeKey - loLoRelationTypeKey - Key of the LO to LO Relation 
	* Type
	* @return LO to LO relation type information
	*/
	public LoLoRelationTypeInfo getLoLoRelationType(String loLoRelationTypeKey)
		throws OperationFailedException
	;
	
	/**
	* Retrieves the list of allowed relation types between the two specified LO Types
	* 
	* @param loTypeKey - loTypeKey - Key of the first LO Type
	* @param relatedLoTypeKey - loTypeKey - Key of the second LO Type
	* @return list of LO to LO relation types
	*/
	public List<String> getAllowedLoLoRelationTypesForLoType(String loTypeKey, String relatedLoTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves information about all the learning objective categories in a given 
	* learning objective repository.
	* 
	* @param loRepositoryKey - loRepositoryKey - loRepository identifier
	* @return list of learning objective category information
	*/
	public List<LoCategoryInfo> getLoCategories(String loRepositoryKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves information about an learning objective category.
	* 
	* @param loCategoryId - loCategoryId - loCategory identifier
	* @return LoCategory
	*/
	public LoCategoryInfo getLoCategory(String loCategoryId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves information on a single learning objective.
	* 
	* @param loId - loId - learning objective identifier
	* @return information about a single learning objective
	*/
	public LoInfo getLo(String loId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves information on multiple learning objectives.
	* 
	* @param loId - loIdList - list of learning objective identifiers
	* @return list of learning objective information
	*/
	public List<LoInfo> getLoByIdList(List<String> loId)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves learning objectives from a given repository of a given type and state.
	* 
	* @param loRepositoryKey - loRepositoryKey - repository identifier
	* @param loTypeKey - loTypeKey - learning objective type identifier
	* @param loStateKey - string - learning objective state identifier
	* @return list of learning objectives
	*/
	public List<LoInfo> getLosByRepository(String loRepositoryKey, String loTypeKey, String loStateKey)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of learning objective categories for a specific learning 
	* objective.
	* 
	* @param loId - loId - learning objective identifier
	* @return list of learning objective category information
	*/
	public List<LoCategoryInfo> getLoCategoriesForLo(String loId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of learning objectives that have a specific learning objective 
	* category.
	* 
	* @param loCategoryId - loCategoryId - learning objective category identifier
	* @return list of learning objective information
	*/
	public List<LoInfo> getLosByLoCategory(String loCategoryId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param relatedLoId - loId - identifier of the LO
	* @param loLoRelationType - loLoRelationTypeKey - the LO to LO relation type
	* @return list of LO information
	*/
	public List<LoInfo> getLosByRelatedLoId(String relatedLoId, String loLoRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param loId - loId - identifier of the LO
	* @param loLoRelationType - loLoRelationTypeKey - the LO to LO relation type
	* @return list of LO information
	*/
	public List<LoInfo> getRelatedLosByLoId(String loId, String loLoRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the relationship information between LOs for a particular relationship 
	* identifier
	* 
	* @param loLoRelationId - loLoRelationId - identifier of the LO to LO relationship
	* @return information on the relation between two LOs
	*/
	public LoLoRelationInfo getLoLoRelation(String loLoRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the relationship information between LOs for a particular LO.
	* 
	* @param loId - loId - identifier of the LO
	* @return all relations (both directions) from an LO
	*/
	public List<LoLoRelationInfo> getLoLoRelationsByLoId(String loId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates a learning objective category. Depending on the value of 
	* validationType, this validation could be limited to tests on just the current 
	* object and its directly contained sub-objects or expanded to perform all tests 
	* related to this object. If an identifier is present for the learning objective 
	* category (and/or one of its contained sub-objects) and a record is found for 
	* that identifier, the validation checks if the learning objective category can be 
	* shifted to the new values. If an identifier is not present or a record cannot be 
	* found for the identifier, it is assumed that the record does not exist and as 
	* such, the checks performed will be much shallower, typically mimicking those 
	* performed by setting the validationType to the current object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param loCategoryInfo - loCategoryInfo - learning objective category information 
	* to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateLoCategory(String validationType, LoCategoryInfo loCategoryInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Create a learning objective category in a particular learning objective 
	* repository.
	* 
	* @param loRepositoryKey - loRepositoryKey - identifier of the learning objective 
	* repository
	* @param loCategoryTypeKey - loCategoryTypeKey - identifier of the learning 
	* objective category type
	* @param loCategoryInfo - loCategoryInfo - information to create the learning 
	* objective category
	* @return information on the created learning objective category
	*/
	public LoCategoryInfo createLoCategory(String loRepositoryKey, String loCategoryTypeKey, LoCategoryInfo loCategoryInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates a learning objective category in a particular learning objective 
	* repository.
	* 
	* @param loCategoryId - loCategoryId - the learning objective category identifier
	* @param loCategoryInfo - loCategoryInfo - information to create the learning 
	* objective category
	* @return information on the uppdated learning objective category
	*/
	public LoCategoryInfo updateLoCategory(String loCategoryId, LoCategoryInfo loCategoryInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes a learning objective category.
	* 
	* @param loCategoryId - loCategoryId - learning objective category identifier
	* @return status of the operation (success/failure)
	*/
	public StatusInfo deleteLoCategory(String loCategoryId)
		throws DependentObjectsExistException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Validates a learning objective. Depending on the value of validationType, this 
	* validation could be limited to tests on just the current object and its directly 
	* contained sub-objects or expanded to perform all tests related to this object. 
	* If an identifier is present for the learning objective (and/or one of its 
	* contained sub-objects) and a record is found for that identifier, the validation 
	* checks if the learning objective can be shifted to the new values. If an 
	* identifier is not present or a record cannot be found for the identifier, it is 
	* assumed that the record does not exist and as such, the checks performed will be 
	* much shallower, typically mimicking those performed by setting the 
	* validationType to the current object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param loInfo - loInfo - learning objective information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateLo(String validationType, LoInfo loInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Create a learning objective.
	* 
	* @param loRepositoryKey - loRepositoryKey - identifier of the learning objective 
	* repository
	* @param loType - loTypeKey - type for the learning objective
	* @param loInfo - loInfo - information to create the learning objective
	* @return information on the created learning objective
	*/
	public LoInfo createLo(String loRepositoryKey, String loType, LoInfo loInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Update a learning objective.
	* 
	* @param loId - loId - identifier of the learning objective to update
	* @param loInfo - loInfo - updated information on the learning objective
	* @return information on the updated learning objective
	*/
	public LoInfo updateLo(String loId, LoInfo loInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Delete a learning objective.
	* 
	* @param loId - loId - identifier of the learning objective to delete
	* @return status of the operation (success or failure)
	*/
	public StatusInfo deleteLo(String loId)
		throws DependentObjectsExistException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Add a existing learning objective category to an existing learning objective in 
	* the same repository.
	* 
	* @param loCategoryId - loCategoryId - identifier of the learning objective 
	* category to add
	* @param loId - loId - identifier of the learning objective
	* @return status of the operation (success or failure)
	*/
	public StatusInfo addLoCategoryToLo(String loCategoryId, String loId)
		throws AlreadyExistsException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,UnsupportedActionException
	;
	
	/**
	* Remove a existing learning objective category from an existing learning 
	* objective in the same repository.
	* 
	* @param loCategoryId - loCategoryId - identifier of the learning objective 
	* category to remove
	* @param loId - loId - identifier of the learning objective
	* @return status of the operation (success or failure)
	*/
	public StatusInfo removeLoCategoryFromLo(String loCategoryId, String loId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,UnsupportedActionException
	;
	
	/**
	* Validates a loLoRelation. Depending on the value of validationType, this 
	* validation could be limited to tests on just the current object and its directly 
	* contained sub-objects or expanded to perform all tests related to this object. 
	* If an identifier is present for the loLoRelation (and/or one of its contained 
	* sub-objects) and a record is found for that identifier, the validation checks if 
	* the relationship can be shifted to the new values. If an identifier is not 
	* present or a record cannot be found for the identifier, it is assumed that the 
	* record does not exist and as such, the checks performed will be much shallower, 
	* typically mimicking those performed by setting the validationType to the current 
	* object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param loLoRelationInfo - loLoRelationInfo - loLoRelation information to be 
	* tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateLoLoRelation(String validationType, LoLoRelationInfo loLoRelationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Create a directional relationship between two LOs
	* 
	* @param loId - loId - identifier of the first LO in the relationship - The From 
	* or Parent of the relation
	* @param relatedLoId - loId - identifier of the second LO in the relationship to 
	* be related to - the To or Child of the Relation
	* @param loLoRelationType - loLoRelationTypeKey - the type of the LO to LO 
	* relationship
	* @param loLoRelationInfo - loLoRelationInfo - information about the relationship 
	* between the two LOs
	* @return the created LO to LO relation information
	*/
	public LoLoRelationInfo createLoLoRelation(String loId, String relatedLoId, String loLoRelationType, LoLoRelationInfo loLoRelationInfo)
		throws AlreadyExistsException
		      ,CircularRelationshipException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates a relationship between two LOs
	* 
	* @param loLoRelationId - loLoRelationId - identifier of the LO to LO relation to 
	* be updated
	* @param loLoRelationInfo - loLoRelationInfo - changed information about the LO to 
	* LO relationship
	* @return the updated LO to LO relation information
	*/
	public LoLoRelationInfo updateLoLoRelation(String loLoRelationId, LoLoRelationInfo loLoRelationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes a relationship between two LOs
	* 
	* @param loLoRelationId - loLoRelationId - identifier of LO to LO relationship to 
	* delete
	* @return status of the operation (success or failure)
	*/
	public StatusInfo deleteLoLoRelation(String loLoRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
}


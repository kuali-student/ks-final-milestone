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
package org.kuali.student.service.organization.dev.api;


import java.util.List;


public interface OrganizationService
{
	
	/**
	* Retrieves the list of organization hierarchies known by this service.
	* 
	* @return list of organization hierarchies
	*/
	public List<OrgHierarchyInfo> getOrgHierarchies()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a specific organization hierarchy.
	* 
	* @param orgHierarchyKey - orgHierarchyKey - organization hierarchy identifier
	* @return information about the specified organization hierarchy
	*/
	public OrgHierarchyInfo getOrgHierarchy(String orgHierarchyKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of types of organizations known by this service.
	* 
	* @return List of organization types
	*/
	public List<OrgTypeInfo> getOrgTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about the specified type of organization.
	* 
	* @param orgTypeKey - orgTypeKey - organization type identifier
	* @return organization type
	*/
	public OrgTypeInfo getOrgType(String orgTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of all types of relationships between organizations known to 
	* the service.
	* 
	* @return list of organization to organization relationship types
	*/
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about the specified type of relationship between 
	* organizations.
	* 
	* @param orgOrgRelationTypeKey - orgOrgRelationTypeKey - organization organization 
	* relationship type identifier
	* @return organization organization relationship type information
	*/
	public OrgOrgRelationTypeInfo getOrgOrgRelationType(String orgOrgRelationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the types of relationships between organizations that are allowed for 
	* a particular type of organization.
	* 
	* @param orgTypeKey - orgTypeKey - organization type identifier
	* @return list of relationship types between organizations for the specified 
	* organization type
	*/
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgType(String orgTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the types of relationships between organizations that are allowed for 
	* a particular organization hierarchy.
	* 
	* @param orgHierarchyKey - orgHierarchyKey - organization hierarchy identifier
	* @return list of relationship types between organizations
	*/
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgHierarchy(String orgHierarchyKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves all types of relationships between an organization and a person known 
	* by this service.
	* 
	* @return list of all organization person relationship types
	*/
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a type of relationship between an organization and a 
	* person.
	* 
	* @param orgPersonRelationTypeKey - orgPersonRelationTypeKey - organization person 
	* relationship type identifier
	* @return organization person relationship type information
	*/
	public OrgPersonRelationTypeInfo getOrgPersonRelationType(String orgPersonRelationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the types of relationships between an organization and a person that 
	* are allowed for a particular organization type.
	* 
	* @param orgTypeKey - orgTypeKey - organization type identifier
	* @return list of organization person relationship types that are valid for the 
	* supplied organization type (may have nothing)
	*/
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(String orgTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates an organization. Depending on the value of validationType, this 
	* validation could be limited to tests on just the current object and its directly 
	* contained sub-objects or expanded to perform all tests related to this object. 
	* If an identifier is present for the organization (and/or one of its contained 
	* sub-objects) and a record is found for that identifier, the validation checks if 
	* the organization can be shifted to the new values. If an identifier is not 
	* present or a record cannot be found for the identifier, it is assumed that the 
	* record does not exist and as such, the checks performed will be much shallower, 
	* typically mimicking those performed by setting the validationType to the current 
	* object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param orgInfo - orgInfo - organization information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateOrg(String validationType, OrgInfo orgInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates an organization to organization relationship. Depending on the value 
	* of validationType, this validation could be limited to tests on just the current 
	* object and its directly contained sub-objects or expanded to perform all tests 
	* related to this object. If an identifier is present for the organization 
	* organization relationship (and/or one of its contained sub-objects) and a record 
	* is found for that identifier, the validation checks if the organization 
	* organization relationship can be shifted to the new values. If an identifier is 
	* not present or a record cannot be found for the identifier, it is assumed that 
	* the record does not exist and as such, the checks performed will be much 
	* shallower, typically mimicking those performed by setting the validationType to 
	* the current object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param orgOrgRelationInfo - orgOrgRelationInfo - organization organization 
	* relationship information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateOrgOrgRelation(String validationType, OrgOrgRelationInfo orgOrgRelationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates a organization to person relationship. Depending on the value of 
	* validationType, this validation could be limited to tests on just the current 
	* object and its directly contained sub-objects or expanded to perform all tests 
	* related to this object. If an identifier is present for the organization person 
	* relationship (and/or one of its contained sub-objects) and a record is found for 
	* that identifier, the validation checks if the organization person relationship 
	* can be shifted to the new values. If an identifier is not present or a record 
	* cannot be found for the identifier, it is assumed that the record does not exist 
	* and as such, the checks performed will be much shallower, typically mimicking 
	* those performed by setting the validationType to the current object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param orgPersonRelationInfo - orgPersonRelationInfo - organization person 
	* relationship information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateOrgPersonRelation(String validationType, OrgPersonRelationInfo orgPersonRelationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates an organization position restriction. Depending on the value of 
	* validationType, this validation could be limited to tests on just the current 
	* object and its directly contained sub-objects or expanded to perform all tests 
	* related to this object. If an identifier is present for the position restriction 
	* (and/or one of its contained sub-objects) and a record is found for that 
	* identifier, the validation checks if the position restriction can be shifted to 
	* the new values. If an identifier is not present or a record cannot be found for 
	* the identifier, it is assumed that the record does not exist and as such, the 
	* checks performed will be much shallower, typically mimicking those performed by 
	* setting the validationType to the current object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param orgPositionRestrictionInfo - orgPositionRestrictionInfo - organization 
	* position restriction information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateOrgPositionRestriction(String validationType, OrgPositionRestrictionInfo orgPositionRestrictionInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves an existing organization by its identifier.
	* 
	* @param orgId - orgId - identifier for org to be retrieved
	* @return details of the organization for this id
	*/
	public OrgInfo getOrganization(String orgId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves a list of existing organizations from a list of identifiers.
	* 
	* @param orgIdList - orgIdList - identifiers for orgs to be retrieved
	* @return details of the organizations for these ids
	*/
	public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves an existing organization to organization relationship by the 
	* relationship identifier
	* 
	* @param orgOrgRelationId - orgOrgRelationId - identifier for org to org relation 
	* to be retrieved
	* @return details of the orgOrgRelation for this id
	*/
	public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves a list of existing organization to organization relationships from a 
	* list of identifiers
	* 
	* @param orgOrgRelationIdList - orgOrgRelationIdList - identifiers for org org 
	* relations to be retrieved
	* @return details of the organizations for these ids
	*/
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByIdList(List<String> orgOrgRelationIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves organization to organization relationships for a particular 
	* organization
	* 
	* @param orgId - orgId - organization identifier for which organization 
	* organization relationships are to be found
	* @return List of org to org relations found for the supplied organization
	*/
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* .
	* 
	* @param relatedOrgId - orgId - organization identifier for which the reverse 
	* organization organization relationships are to be found
	* @return List of organization organization relationships found for the supplied 
	* organization
	*/
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(String relatedOrgId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Tests if a org has a current relationship with a specified organization.
	* 
	* @param orgId - orgId - identifier of the organization
	* @param comparisonOrgId - orgId - identifier of the organization to be compared 
	* to
	* @param orgOrgRelationTypeKey - orgOrgRelationTypeKey - type of relationship 
	* between the organizations
	* @return true if a relationship exists
	*/
	public Boolean hasOrgOrgRelation(String orgId, String comparisonOrgId, String orgOrgRelationTypeKey)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Tests if a specified organization is a descendant of the other specified 
	* organization in the given organization hierarchy.
	* 
	* @param orgId - orgId - identifier of the "ancestor" organization
	* @param descendantOrgId - orgId - identifier of the organization to be checked if 
	* it is a descendant
	* @param orgHierarchy - orgHierarchyKey - identifier of the organization hierarchy 
	* to be checked against
	* @return True if the organization is a descendant of the other organization in 
	* that hierarchy
	*/
	public Boolean isDescendant(String orgId, String descendantOrgId, String orgHierarchy)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the list of identifiers for the "descendant" organizations of the 
	* specified organization in the given organization hierarchy. Information about 
	* the distance from the original organization is not passed in this call, so this 
	* can be seen as a flattened and de-duplicated representation.
	* 
	* @param orgId - orgId - identifier of the "ancestor" organization
	* @param orgHierarchy - orgHierarchyKey - identifier of the organization hierarchy 
	* to be checked against
	* @return list of identifiers for the "descendant" organizations for the specified 
	* organization
	*/
	public List<String> getAllDescendants(String orgId, String orgHierarchy)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the list of identifiers for the "ancestor" organizations of the 
	* specified organization in the given organization hierarchy. Information about 
	* the distance from the original organization is not passed in this call, so this 
	* can be seen as a flattened and de-duplicated representation.
	* 
	* @param orgId - orgId - identifier of the "descendant" organization
	* @param orgHierarchy - orgHierarchyKey - identifier of the organization hierarchy 
	* to be checked against
	* @return list of identifiers for the "ancestor" organizations of the specified 
	* organization
	*/
	public List<String> getAncestors(String orgId, String orgHierarchy)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves an existing org to person relation by the relation ID
	* 
	* @param orgPersonRelationId - orgPersonRelationId - identifier for org to person 
	* relation to be retrieved
	* @return details of the orgPersonRelation for this id
	*/
	public OrgPersonRelationInfo getOrgPersonRelation(String orgPersonRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves a list of existing organization to person relations from a list of org 
	* person relation IDs
	* 
	* @param orgPersonRelationIdList - orgPersonRelationIdList - identifiers for org 
	* person relations to be retrieved
	* @return details of the relations for these ids
	*/
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByIdList(List<String> orgPersonRelationIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the list of identifiers for people that have the specified type of 
	* relationship to this organization
	* 
	* @param orgId - orgId - identifier of the organization that members are being 
	* found for
	* @param orgPersonRelationTypeKey - orgPersonRelationTypeKey - type of 
	* organization person relationship that is being looked for
	* @return list of person identifiers that match supplied criteria
	*/
	public List<String> getPersonIdsForOrgByRelationType(String orgId, String orgPersonRelationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the org to person relations for a particular organization
	* 
	* @param orgId - orgId - identifier for the organization for which organization 
	* person relationships are to be found
	* @return list of organization person relationships found for the supplied 
	* organization
	*/
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves all OrgPersonRelations for a particular Person and Organization
	* 
	* @param personId - personId - person to use to look for relationships
	* @param orgId - orgId - organization to use to look for relationships
	* @return list of organization person relationships that exist for the supplied 
	* person and organization
	*/
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(String personId, String orgId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves all organization person relationships for a person.
	* 
	* @param personId - personId - person to get all relationships for
	* @return List of organization person relationships that exist for this person
	*/
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByPerson(String personId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves all organization person relationships for an organization.
	* 
	* @param orgId - orgId - organization to get all relationships for
	* @return list of organization person relationships that exist for this 
	* organization
	*/
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByOrg(String orgId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Tests if a person has a current relationship with a specified organization
	* 
	* @param orgId - orgId - identifier of the organization
	* @param personId - personId - identifier of the person
	* @param orgPersonRelationTypeKey - orgPersonRelationTypeKey - type of 
	* relationship between the person and organization
	* @return true if a relationship exists
	*/
	public Boolean hasOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves a list of organization-specific restrictions on relationships with 
	* people for a particular organization.
	* 
	* @param orgId - orgId - identifier of the organization
	* @return list of the organization-specific position restriction information
	*/
	public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,PermissionDeniedException
		      ,OperationFailedException
	;
	
	/**
	* Creates a new organization
	* 
	* @param orgTypeKey - orgTypeKey - Unique identifier for an organization type.
	* @param orgInfo - orgInfo - information about the organization to be created
	* @return newly created organization information
	*/
	public OrgInfo createOrganization(String orgTypeKey, OrgInfo orgInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing organization
	* 
	* @param orgId - orgId - identifier for org to be updated
	* @param orgInfo - orgInfo - information about the organization to be updated
	* @return updated organization information
	*/
	public OrgInfo updateOrganization(String orgId, OrgInfo orgInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes an existing organization.
	* 
	* @param orgId - orgId - identifier for org to be deleted
	* @return status of the operation (success or failure)
	*/
	public StatusInfo deleteOrganization(String orgId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Creates an organization to organization relationship between two organizations 
	* of a particular type.
	* 
	* @param orgId - orgId - identifier of the organization
	* @param relatedOrgId - orgId - identifier of the "child" organization
	* @param orgOrgRelationTypeKey - orgOrgRelationTypeKey - identifier of the type of 
	* relationship between the organizations
	* @param orgOrgRelationInfo - orgOrgRelationInfo - organization to organization 
	* relationship information to be created
	* @return information about the new organization to organization relationship
	*/
	public OrgOrgRelationInfo createOrgOrgRelation(String orgId, String relatedOrgId, String orgOrgRelationTypeKey, OrgOrgRelationInfo orgOrgRelationInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,PermissionDeniedException
		      ,OperationFailedException
	;
	
	/**
	* Updates an organization to organization relationship.
	* 
	* @param orgOrgRelationId - orgOrgRelationId - organization to organization 
	* relationship identifier
	* @param orgOrgRelationInfo - orgOrgRelationInfo - detail information of the 
	* organization to organization relationship
	* @return updated organization to organization relationship information
	*/
	public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId, OrgOrgRelationInfo orgOrgRelationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Removes an organization to organization relationship.
	* 
	* @param orgOrgRelationId - orgOrgRelationId - organization to organization 
	* relationship identifier
	* @return status of the operation
	*/
	public StatusInfo removeOrgOrgRelation(String orgOrgRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Creates an organization to person relationship between an organization and a 
	* person with a particular type.
	* 
	* @param orgId - orgId - organization
	* @param personId - personId - person
	* @param orgPersonRelationTypeKey - orgPersonRelationTypeKey - organization person 
	* relationship type
	* @param orgPersonRelationInfo - orgPersonRelationInfo - organization person 
	* relationship information
	* @return detail information of the new organization to person relationship
	*/
	public OrgPersonRelationInfo createOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,PermissionDeniedException
		      ,OperationFailedException
	;
	
	/**
	* Updates an organization to person relationship.
	* 
	* @param orgPersonRelationId - orgPersonRelationId - organization person 
	* relationship identifier
	* @param orgPersonRelationInfo - orgPersonRelationInfo - information about the 
	* organization to person relationship to be updated
	* @return updated organization to person relationship information
	*/
	public OrgPersonRelationInfo updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Removes an organization to person relationship.
	* 
	* @param orgPersonRelationId - orgPersonRelationId - organization person 
	* relationship identifier
	* @return status of the operation
	*/
	public StatusInfo removeOrgPersonRelation(String orgPersonRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Adds a description of the organization-specific usage of an organization person 
	* relationship type. This information typically coincides with constraints, such 
	* as how many relationships of a given type may be active at a particular time, 
	* etc.
	* 
	* @param orgId - orgId - organization
	* @param orgPersonRelationTypeKey - orgPersonRelationTypeKey - organization person 
	* relationship type
	* @param orgPositionRestrictionInfo - orgPositionRestrictionInfo - organization 
	* position restriction information
	* @return information about the newly created organization position restriction
	*/
	public OrgPositionRestrictionInfo addPositionRestrictionToOrg(String orgId, String orgPersonRelationTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates a description of the organization-specific usage of an organization 
	* person relationship type.
	* 
	* @param orgId - orgId - organization
	* @param orgPersonRelationTypeKey - orgPersonRelationTypeKey - organization person 
	* relationship type
	* @param orgPositionRestrictionInfo - orgPositionRestrictionInfo - organization 
	* position restriction information
	* @return information about the updated organization position restriction
	*/
	public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(String orgId, String orgPersonRelationTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Removes a description of the organization-specific usage of an organization 
	* person relationship type.
	* 
	* @param orgId - orgId - organization
	* @param orgPersonRelationTypeKey - orgPersonRelationTypeKey - organization person 
	* relationship type
	* @return status
	*/
	public StatusInfo removePositionRestrictionFromOrg(String orgId, String orgPersonRelationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
}


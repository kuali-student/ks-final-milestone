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
package org.kuali.student.service.proposal.dev.api;


import java.util.List;


public interface ProposalService
{
	
	/**
	* Retrieves the list of proposal types known by this service
	* 
	* @return list of proposal types
	*/
	public List<ProposalTypeInfo> getProposalTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular proposal type
	* 
	* @param proposalTypeKey - proposalTypeKey - proposal type identifier
	* @return proposal type information
	*/
	public ProposalTypeInfo getProposalType(String proposalTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of types which can be referenced by a proposal.
	* 
	* @return the list of types which can be referenced by a proposal
	*/
	public List<ReferenceTypeInfo> getReferenceTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves the list of proposal types that are defined for a particular Reference 
	* Type
	* 
	* @param referenceTypeKey - referenceTypeKey - referenceTypeKey
	* @return List of proposal types
	*/
	public List<ProposalTypeInfo> getProposalTypesForReferenceType(String referenceTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of all Proposal document relationship types
	* 
	* @return list of Proposal document relationship types
	*/
	public List<ProposalDocRelationTypeInfo> getProposalDocRelationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information for a specified LU document relationship type
	* 
	* @param proposalDocRelationTypeKey - proposalDocRelationTypeKey - Proposal 
	* document relationship type key
	* @return Proposal document relationship type information
	*/
	public ProposalDocRelationTypeInfo getProposalDocRelationType(String proposalDocRelationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of allowed Proposal document relation types for a given 
	* Proposal Type
	* 
	* @param proposalTypeKey - proposalTypeKey - Key of the first Proposal Type
	* @return list of Proposal Doc relation types
	*/
	public List<String> getAllowedProposalDocRelationTypesForProposalType(String proposalTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates a proposal. Depending on the value of validationType, this validation 
	* could be limited to tests on just the current object and its directly contained 
	* subobjects or expanded to perform all tests related to this object. If an 
	* identifier is present for the proposal and a record is found for that 
	* identifier, the validation checks if the proposal can be shifted to the new 
	* values. If a record cannot be found for the identifier, it is assumed that the 
	* record does not exist and as such, the checks performed will be much shallower, 
	* typically mimicking those performed by setting the validationType to the current 
	* object. This is a slightly different pattern from the standard validation as the 
	* caller provides the identifier in the create statement instead of the server 
	* assigning an identifier.
	* 
	* @param validationType - validationTypeKey - Identifier of the extent of 
	* validation
	* @param proposalInfo - proposalInfo - The proposal information to be tested.
	* @return Results from performing the validation
	*/
	public List<ValidationResultInfo> validateProposal(String validationType, ProposalInfo proposalInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates a proposalDocRelation. Depending on the value of validationType, this 
	* validation could be limited to tests on just the current object and its directly 
	* contained sub-objects or expanded to perform all tests related to this object. 
	* If an identifier is present for the object (and/or one of its contained sub-
	* objects) and a record is found for that identifier, the validation checks if the 
	* object can be shifted to the new values. If an identifier is not present or a 
	* record cannot be found for the identifier, it is assumed that the record does 
	* not exist and as such, the checks performed will be much shallower, typically 
	* mimicking those performed by setting the validationType to the current object.
	* 
	* @param validationType - validationTypeKey - identifier of the extent of 
	* validation
	* @param proposalDocRelationInfo - proposalDocRelationInfo - proposalDocRelation 
	* information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateProposalDocRelation(String validationType, ProposalDocRelationInfo proposalDocRelationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the details of a single Proposal by proposalId
	* 
	* @param proposalId - proposalId - Unique id of the Proposal to be retrieved
	* @return Details of the Proposal requested
	*/
	public ProposalInfo getProposal(String proposalId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Proposals for the supplied list of proposalIds
	* 
	* @param proposalIdList - proposalIdList - list of proposal identifiers
	* @return List of proposals that match the supplied proposalId list
	*/
	public List<ProposalInfo> getProposalsByIdList(List<String> proposalIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Proposals for the supplied Proposal Type
	* 
	* @param proposalTypeKey - proposalTypeKey - key of the proposal type
	* @return List of proposal information
	*/
	public List<ProposalInfo> getProposalsByProposalType(String proposalTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Proposals for the specified Reference Type and Reference 
	* Id
	* 
	* @param referenceTypeKey - referenceTypeKey - Key of the Reference Type
	* @param referenceId - referenceId - Identifier of the reference
	* @return list of Proposal information
	*/
	public List<ProposalInfo> getProposalsByReference(String referenceTypeKey, String referenceId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Proposals for the specified proposal type and state
	* 
	* @param proposalState - string - Proposal State
	* @param proposalTypeKey - proposalTypeKey - Proposal Type.
	* @return list of Proposal information
	*/
	public List<ProposalInfo> getProposalsByState(String proposalState, String proposalTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a getProposalDocRelation by its identifier
	* 
	* @param proposalDocRelationId - proposalDocRelationId - proposalDocRelation 
	* identifier
	* @return proposalDocRelation information
	*/
	public ProposalDocRelationInfo getProposalDocRelation(String proposalDocRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of proposalDocRelations of a particular type
	* 
	* @param proposalDocRelationTypeKey - proposalDocRelationTypeKey - 
	* proposalDocRelationType identifier
	* @return list of proposalDocRelation of a type
	*/
	public List<ProposalDocRelationInfo> getProposalDocRelationsByType(String proposalDocRelationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of proposalDocRelations from a list of ids
	* 
	* @param proposalDocRelationIdList - proposalDocRelationIdList - list of 
	* proposalDocRelation identifiers
	* @return list of proposalDocRelation that matches the id list
	*/
	public List<ProposalDocRelationInfo> getProposalDocRelationsByIdList(List<String> proposalDocRelationIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of ProposalDocRelations of a particular Proposal
	* 
	* @param proposalId - proposalId - Proposal identifier
	* @return list of proposalDocRelations of a proposal
	*/
	public List<ProposalDocRelationInfo> getProposalDocRelationsByProposal(String proposalId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of ProposalDocRelations by a document identifier.
	* 
	* @param documentId - documentId - document identifier
	* @return list of proposalDocRelations of a document
	*/
	public List<ProposalDocRelationInfo> getProposalDocRelationsByDocument(String documentId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Creates a new Proposal
	* 
	* @param proposalTypeKey - proposalTypeKey - identifier of the Proposal Type for 
	* the Proposal being created
	* @param proposalInfo - proposalInfo - information about the Proposal being 
	* created
	* @return the created Proposal information
	*/
	public ProposalInfo createProposal(String proposalTypeKey, ProposalInfo proposalInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing CLU
	* 
	* @param proposalId - proposalId - identifier for the Proposal to be updated
	* @param proposalInfo - proposalInfo - updated information about the Proposal
	* @return the updated Proposal information
	*/
	public ProposalInfo updateProposal(String proposalId, ProposalInfo proposalInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes an existing Proposal
	* 
	* @param proposalId - proposalId - identifier for the Proposal to be deleted
	* @return status of the operation
	*/
	public StatusInfo deleteProposal(String proposalId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,DependentObjectsExistException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Creates a createProposalDocRelation for a proposal.
	* 
	* @param proposalDocRelationType - proposalDocRelationTypeKey - identifier of the 
	* type of proposalDocRelation
	* @param documentId - documentId - document identifier
	* @param proposalId - proposalId - the identifier of the proposal to relate to
	* @param proposalDocRelationInfo - proposalDocRelationInfo - information about the 
	* proposalDocRelation
	* @return information about the newly created proposalDocRelation
	*/
	public ProposalDocRelationInfo createProposalDocRelation(String proposalDocRelationType, String documentId, String proposalId, ProposalDocRelationInfo proposalDocRelationInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates a ProposalDocRelation
	* 
	* @param proposalDocRelationId - proposalDocRelationId - identifier of the 
	* proposalDocRelation to be updated
	* @param proposalDocRelationInfo - proposalDocRelationInfo - information about the 
	* proposalDocRelation to be updated
	* @return the updated proposalDocRelation information
	*/
	public ProposalDocRelationInfo updateProposalDocRelation(String proposalDocRelationId, ProposalDocRelationInfo proposalDocRelationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* removes a ProposalDocRelation from a proposal
	* 
	* @param proposalDocRelationId - proposalDocRelationId - identifier of the 
	* ProposalDocRelation to delete
	* @return status of the operation (success or failure)
	*/
	public StatusInfo deleteProposalDocRelation(String proposalDocRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
}


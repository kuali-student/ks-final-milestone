/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.proposal.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.kuali.student.core.dictionary.service.old.DictionaryService;
import org.kuali.student.core.dto.ReferenceTypeInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.dto.ProposalDocRelationTypeInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

/**
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Thu May 28 10:25:06 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Proposal+Service">ProposalService</>
 *
 */
@WebService(name = "ProposalService", targetNamespace = "http://student.kuali.org/wsdl/proposal") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@XmlSeeAlso({org.kuali.student.core.dto.ReferenceTypeInfo.class})
public interface ProposalService extends DictionaryService, SearchService{
    /**
     * Retrieves the list of proposal types known by this service
     * @return list of proposal types
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalTypeInfo> getProposalTypes() throws OperationFailedException;

    /**
     * Retrieves information about a particular proposal type
     * @param proposalTypeKey proposal type identifier
     * @return proposal type information
     * @throws DoesNotExistException specified proposal type not found
     * @throws InvalidParameterException invalid proposalTypeKey
     * @throws MissingParameterException proposalTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public ProposalTypeInfo getProposalType(@WebParam(name="proposalTypeKey")String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of types which can be referenced by a proposal.
     * @return the list of types which can be referenced by a proposal
     * @throws OperationFailedException unable to complete request
	 */
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException;

    /**
     * Retrieves the list of proposal types that are defined for a particular Reference Type
     * @param referenceTypeKey referenceTypeKey
     * @return List of proposal types
     * @throws DoesNotExistException specified referenceTypeKey not found
     * @throws InvalidParameterException invalid referenceTypeKey
     * @throws MissingParameterException referenceTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalTypeInfo> getProposalTypesForReferenceType(@WebParam(name="referenceTypeKey")String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of all Proposal document relationship types
     * @return list of Proposal document relationship types
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalDocRelationTypeInfo> getProposalDocRelationTypes() throws OperationFailedException;

    /**
     * Retrieves information for a specified LU document relationship type
     * @param proposalDocRelationTypeKey Proposal document relationship type key
     * @return Proposal document relationship type information
     * @throws DoesNotExistException proposalDocRelationTypeKey not found
     * @throws InvalidParameterException invalid proposalDocRelationTypeKey
     * @throws MissingParameterException missing proposalDocRelationTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ProposalDocRelationTypeInfo getProposalDocRelationType(@WebParam(name="proposalDocRelationTypeKey")String proposalDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of allowed Proposal document relation types for a given Proposal Type
     * @param proposalTypeKey Key of the first Proposal Type
     * @return list of Proposal Doc relation types
     * @throws DoesNotExistException luTypeKey not found
     * @throws InvalidParameterException invalid proposalTypeKey
     * @throws MissingParameterException missing proposalTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedProposalDocRelationTypesForProposalType(@WebParam(name="proposalTypeKey")String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates a proposal. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained subobjects or expanded to perform all tests related to this object. If an identifier is present for the proposal and a record is found for that identifier, the validation checks if the proposal can be shifted to the new values. If a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object. This is a slightly different pattern from the standard validation as the caller provides the identifier in the create statement instead of the server assigning an identifier.
     * @param validationType Identifier of the extent of validation
     * @param proposalInfo The proposal information to be tested.
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, proposalInfo
     * @throws MissingParameterException missing validationTypeKey, proposalInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateProposal(@WebParam(name="validationType")String validationType, @WebParam(name="proposalInfo")ProposalInfo proposalInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates a proposalDocRelation. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the object (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the object can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param proposalDocRelationInfo proposalDocRelation information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, proposalDocRelationInfo
     * @throws MissingParameterException missing validationTypeKey, proposalDocRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateProposalDocRelation(@WebParam(name="validationType")String validationType, @WebParam(name="proposalDocRelationInfo")ProposalDocRelationInfo proposalDocRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the details of a single Proposal by proposalId
     * @param proposalId Unique id of the Proposal to be retrieved
     * @return Details of the Proposal requested
     * @throws DoesNotExistException proposalId not found
     * @throws InvalidParameterException invalid proposalId
     * @throws MissingParameterException invalid proposalId
     * @throws OperationFailedException unable to complete request
	 */
    public ProposalInfo getProposal(@WebParam(name="proposalId")String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of Proposals for the supplied list of proposalIds
     * @param proposalIdList list of proposal identifiers
     * @return List of proposals that match the supplied proposalId list
     * @throws DoesNotExistException One or more proposalIds not found
     * @throws InvalidParameterException One or more invalid proposalId
     * @throws MissingParameterException missing proposalIdList
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalInfo> getProposalsByIdList(@WebParam(name="proposalIdList")List<String> proposalIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of Proposals for the supplied Proposal Type
     * @param proposalTypeKey key of the proposal type
     * @return List of proposal information
     * @throws DoesNotExistException proposalTypeKey not found
     * @throws InvalidParameterException invalid proposalTypeKey
     * @throws MissingParameterException missing proposalTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalInfo> getProposalsByProposalType(@WebParam(name="proposalTypeKey")String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of Proposals for the specified Reference Type and Reference Id
     * @param referenceTypeKey Key of the Reference Type
     * @param referenceId Identifier of the reference
     * @return list of Proposal information
     * @throws DoesNotExistException referenceTypeKey or referenceId not found
     * @throws InvalidParameterException invalid referenceTypeKey or referenceId
     * @throws MissingParameterException missing referenceTypeKey or referenceId
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalInfo> getProposalsByReference(@WebParam(name="referenceTypeKey")String referenceTypeKey, @WebParam(name="referenceId")String referenceId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of Proposals for the specified proposal type and state
     * @param proposalState Proposal State
     * @param proposalTypeKey Proposal Type.
     * @return list of Proposal information
     * @throws DoesNotExistException proposalTypeKey not found
     * @throws InvalidParameterException invalid proposalState or proposalTypeKey
     * @throws MissingParameterException missing proposalState or proposalTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalInfo> getProposalsByState(@WebParam(name="proposalState")String proposalState, @WebParam(name="proposalTypeKey")String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a getProposalDocRelation by its identifier
     * @param proposalDocRelationId proposalDocRelation identifier
     * @return proposalDocRelation information
     * @throws DoesNotExistException luDocRelationId not found
     * @throws InvalidParameterException invalid proposalDocRelationId
     * @throws MissingParameterException proposalDocRelationId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public ProposalDocRelationInfo getProposalDocRelation(@WebParam(name="proposalDocRelationId")String proposalDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of proposalDocRelations of a particular type
     * @param proposalDocRelationTypeKey proposalDocRelationType identifier
     * @return list of proposalDocRelation of a type
     * @throws DoesNotExistException proposalDocRelationTypeKey not found
     * @throws InvalidParameterException invalid proposalDocRelationTypeKey
     * @throws MissingParameterException proposalDocRelationTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalDocRelationInfo> getProposalDocRelationsByType(@WebParam(name="proposalDocRelationTypeKey")String proposalDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of proposalDocRelations from a list of ids
     * @param proposalDocRelationIdList list of proposalDocRelation identifiers
     * @return list of proposalDocRelation that matches the id list
     * @throws DoesNotExistException luDocRelationId not found
     * @throws InvalidParameterException invalid proposalDocRelationInfoList
     * @throws MissingParameterException proposalDocRelationInfoList not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalDocRelationInfo> getProposalDocRelationsByIdList(@WebParam(name="proposalDocRelationIdList")List<String> proposalDocRelationIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of ProposalDocRelations of a particular Proposal
     * @param proposalId Proposal identifier
     * @return list of proposalDocRelations of a proposal
     * @throws DoesNotExistException proposalId not found
     * @throws InvalidParameterException invalid proposalId
     * @throws MissingParameterException proposalId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalDocRelationInfo> getProposalDocRelationsByProposal(@WebParam(name="proposalId")String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of ProposalDocRelations by a document identifier.
     * @param documentId document identifier
     * @return list of proposalDocRelations of a document
     * @throws DoesNotExistException documentId not found
     * @throws InvalidParameterException invalid documentId
     * @throws MissingParameterException documentId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProposalDocRelationInfo> getProposalDocRelationsByDocument(@WebParam(name="documentId")String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a new Proposal
     * @param proposalTypeKey identifier of the Proposal Type for the Proposal being created
     * @param proposalInfo information about the Proposal being created
     * @return the created Proposal information
     * @throws AlreadyExistsException Proposal already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException proposalTypeKey not found
     * @throws InvalidParameterException invalid proposalTypeKey, proposalInfo
     * @throws MissingParameterException missing proposalTypeKey, proposalInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ProposalInfo createProposal(@WebParam(name="proposalTypeKey")String proposalTypeKey, @WebParam(name="proposalInfo")ProposalInfo proposalInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing CLU
     * @param proposalId identifier for the Proposal to be updated
     * @param proposalInfo updated information about the Proposal
     * @return the updated Proposal information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException proposalId not found
     * @throws InvalidParameterException invalid proposalId, proposalInfo
     * @throws MissingParameterException missing proposalId, proposalInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public ProposalInfo updateProposal(@WebParam(name="proposalId")String proposalId, @WebParam(name="proposalInfo")ProposalInfo proposalInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing Proposal
     * @param proposalId identifier for the Proposal to be deleted
     * @return status of the operation
     * @throws DoesNotExistException proposalId not found
     * @throws InvalidParameterException invalid proposalId
     * @throws MissingParameterException missing proposalId
     * @throws DependentObjectsExistException delete would leave orphaned objects or violate integrity constraints
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteProposal(@WebParam(name="proposalId")String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a createProposalDocRelation for a proposal.
     * @param proposalDocRelationType identifier of the type of proposalDocRelation
     * @param documentId document identifier
     * @param proposalId the identifier of the proposal to relate to
     * @param proposalDocRelationInfo information about the proposalDocRelation
     * @return information about the newly created proposalDocRelation
     * @throws AlreadyExistsException proposalDocRelation already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException proposalId or proposalDocRelationTypeKey or documentId not found
     * @throws InvalidParameterException invalid proposalId or proposalDocRelationTypeKey or documentId or proposalDocRelation
     * @throws MissingParameterException missing proposalId or proposalDocRelationTypeKey or documentId or proposalDocRelation
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ProposalDocRelationInfo createProposalDocRelation(@WebParam(name="proposalDocRelationType")String proposalDocRelationType, @WebParam(name="documentId")String documentId, @WebParam(name="proposalId")String proposalId, @WebParam(name="proposalDocRelationInfo")ProposalDocRelationInfo proposalDocRelationInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a ProposalDocRelation
     * @param proposalDocRelationId identifier of the proposalDocRelation to be updated
     * @param proposalDocRelationInfo information about the proposalDocRelation to be updated
     * @return the updated proposalDocRelation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException proposalDocRelationId not found
     * @throws InvalidParameterException invalid proposalDocRelationId, proposalDocRelationInfo
     * @throws MissingParameterException missing proposalDocRelationId, proposalDocRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public ProposalDocRelationInfo updateProposalDocRelation(@WebParam(name="proposalDocRelationId")String proposalDocRelationId, @WebParam(name="proposalDocRelationInfo")ProposalDocRelationInfo proposalDocRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * removes a ProposalDocRelation from a proposal
     * @param proposalDocRelationId identifier of the ProposalDocRelation to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException proposalDocRelationId not found
     * @throws InvalidParameterException invalid proposalDocRelationId
     * @throws MissingParameterException missing proposalDocRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteProposalDocRelation(@WebParam(name="proposalDocRelationId")String proposalDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}

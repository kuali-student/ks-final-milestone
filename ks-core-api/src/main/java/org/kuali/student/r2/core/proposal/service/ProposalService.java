/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.proposal.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

/**
 * This is a description of what this class does - sambit don't forget to fill
 * this in.
 * 
 * @author Sambit (sambitpa@kuali.org)
 */
@WebService(name = "ProposalService", targetNamespace = "http://student.kuali.org/wsdl/proposal")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@XmlSeeAlso({org.kuali.student.r1.common.dto.ReferenceTypeInfo.class})
public interface ProposalService extends DictionaryService, SearchService {

    /**
     * Retrieves the details of a single Proposal by proposalId
     * 
     * @param proposalId Id of the proposal
     * @param contextInfo
     * @return
     * @throws DoesNotExistException Proposal doesnt not exist
     * @throws InvalidParameterException Invalid proposal id in the parameter
     * @throws MissingParameterException Missing proposal id
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProposalInfo getProposal(@WebParam(name = "proposalId") String proposalId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Proposals for the supplied list of proposalIds
     * 
     * @param proposalIds list of proposal identifiers
     * @return List of proposals that match the supplied proposalId list
     * @throws DoesNotExistException One or more proposalIds not found
     * @throws InvalidParameterException One or more invalid proposalId
     * @throws MissingParameterException missing proposalIds
     * @throws OperationFailedException unable to complete request
     */
    public List<ProposalInfo> getProposalsByIds(@WebParam(name = "proposalIds") List<String> proposalIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Proposals for the supplied Proposal Type
     * 
     * @param proposalTypeKey key of the proposal type
     * @return List of proposal information
     * @throws DoesNotExistException proposalTypeKey not found
     * @throws InvalidParameterException invalid proposalTypeKey
     * @throws MissingParameterException missing proposalTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<ProposalInfo> getProposalsByProposalType(@WebParam(name = "proposalTypeKey") String proposalTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Proposals for the specified Reference Type and
     * Reference Id
     * 
     * @param referenceTypeKey Key of the Reference Type
     * @param referenceId Identifier of the reference
     * @return list of Proposal information
     * @throws DoesNotExistException referenceTypeKey or referenceId not found
     * @throws InvalidParameterException invalid referenceTypeKey or referenceId
     * @throws MissingParameterException missing referenceTypeKey or referenceId
     * @throws OperationFailedException unable to complete request
     */
    public List<ProposalInfo> getProposalsByReference(@WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "referenceId") String referenceId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the list of Proposals for the specified proposal type and state
     * 
     * @param proposalState Proposal State
     * @param proposalTypeKey Proposal Type.
     * @return list of Proposal information
     * @throws DoesNotExistException proposalTypeKey not found
     * @throws InvalidParameterException invalid proposalState or
     *             proposalTypeKey
     * @throws MissingParameterException missing proposalState or
     *             proposalTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<ProposalInfo> getProposalsByState(@WebParam(name = "proposalState") String proposalState, @WebParam(name = "proposalTypeKey") String proposalTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * @param workflowId Workflow id
     * @return Proposal Information
     * @throws DoesNotExistException workflowId not found
     * @throws InvalidParameterException invalid workflowId
     * @throws MissingParameterException missing workflowId
     * @throws OperationFailedException unable to complete request
     */
    public ProposalInfo getProposalByWorkflowId(@WebParam(name = "workflowId") String workflowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a proposal. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained subobjects or expanded to perform all tests related to
     * this object. If an identifier is present for the proposal and a record is
     * found for that identifier, the validation checks if the proposal can be
     * shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     * 
     * @param validationTypeKey Identifier of the extent of validation
     * @param proposalInfo The proposal information to be tested.
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, proposalInfo
     * @throws MissingParameterException missing validationTypeKey, proposalInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateProposal(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "proposalInfo") ProposalInfo proposalInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a new Proposal
     * 
     * @param proposalTypeKey identifier of the Proposal Type for the Proposal
     *            being created
     * @param proposalInfo information about the Proposal being created
     * @return the created Proposal information
     * @throws AlreadyExistsException Proposal already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException proposalTypeKey not found
     * @throws InvalidParameterException invalid proposalTypeKey, proposalInfo
     * @throws MissingParameterException missing proposalTypeKey, proposalInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProposalInfo createProposal(@WebParam(name = "proposalTypeKey") String proposalTypeKey, @WebParam(name = "proposalInfo") ProposalInfo proposalInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing CLU
     * 
     * @param proposalId identifier for the Proposal to be updated
     * @param proposalInfo updated information about the Proposal
     * @return the updated Proposal information
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException proposalId not found
     * @throws InvalidParameterException invalid proposalId, proposalInfo
     * @throws MissingParameterException missing proposalId, proposalInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version.
     */
    public ProposalInfo updateProposal(@WebParam(name = "proposalId") String proposalId, @WebParam(name = "proposalInfo") ProposalInfo proposalInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing Proposal
     * 
     * @param proposalId identifier for the Proposal to be deleted
     * @return status of the operation
     * @throws DoesNotExistException proposalId not found
     * @throws InvalidParameterException invalid proposalId
     * @throws MissingParameterException missing proposalId
     * @throws DependentObjectsExistException delete would leave orphaned
     *             objects or violate integrity constraints
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteProposal(@WebParam(name = "proposalId") String proposalId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for proposal Ids that meet the given search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of proposal identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForProposalIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for proposals that meet the given search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of proposal identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ProposalInfo> searchForProposals(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}

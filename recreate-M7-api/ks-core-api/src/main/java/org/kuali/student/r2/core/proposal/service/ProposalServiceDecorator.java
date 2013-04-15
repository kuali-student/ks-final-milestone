/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.proposal.service;

import java.util.List;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

/**
 *
 * @author nwright
 */
public class ProposalServiceDecorator implements ProposalService {
    private ProposalService nextDecorator;

    public ProposalService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(ProposalService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }
    
    

    @Override
    public ProposalInfo getProposal(String proposalId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getProposal(proposalId, contextInfo);
    }

    @Override
    public List<ProposalInfo> getProposalsByIds(List<String> proposalIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getProposalsByIds(proposalIds, contextInfo);
    }

    @Override
    public List<ProposalInfo> getProposalsByProposalType(String proposalTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getProposalsByProposalType(proposalTypeKey, contextInfo);
    }

    @Override
    public List<ProposalInfo> getProposalsByReference(String referenceTypeKey, String referenceId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getProposalsByReference(referenceTypeKey, referenceId, contextInfo);
    }

    @Override
    public List<ProposalInfo> getProposalsByState(String proposalState, String proposalTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getProposalsByState(proposalState, proposalTypeKey, contextInfo);
    }

    @Override
    public ProposalInfo getProposalByWorkflowId(String workflowId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getProposalByWorkflowId(workflowId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateProposal(String validationTypeKey, ProposalInfo proposalInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator ().validateProposal(validationTypeKey, proposalInfo, contextInfo);
    }

    @Override
    public ProposalInfo createProposal(String proposalTypeKey, ProposalInfo proposalInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator ().createProposal(proposalTypeKey, proposalInfo, contextInfo);
    }

    @Override
    public ProposalInfo updateProposal(String proposalId, ProposalInfo proposalInfo, ContextInfo contextInfo) throws ReadOnlyException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator ().updateProposal(proposalId, proposalInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteProposal(String proposalId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().deleteProposal(proposalId, contextInfo);
    }

    @Override
    public List<String> searchForProposalIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().searchForProposalIds(criteria, contextInfo);
    }

    @Override
    public List<ProposalInfo> searchForProposals(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().searchForProposals(criteria, contextInfo);
    }

    @Override
    public List<String> getObjectTypes() {
        return getNextDecorator ().getObjectTypes();
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return getNextDecorator ().getObjectStructure(objectTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator ().getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator ().getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, 
            InvalidParameterException,OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().search(searchRequestInfo, contextInfo);
    }
    
}

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

package org.kuali.student.core.proposal.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
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
import org.kuali.student.core.proposal.dao.ProposalDao;
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.dto.ProposalDocRelationTypeInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalDocRelation;
import org.kuali.student.core.proposal.entity.ProposalDocRelationType;
import org.kuali.student.core.proposal.entity.ProposalReferenceType;
import org.kuali.student.core.proposal.entity.ProposalType;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the Proposal Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Proposal+Service">ProposalService</>
 */
@WebService(endpointInterface = "org.kuali.student.core.proposal.service.ProposalService", serviceName = "ProposalService", portName = "ProposalService", targetNamespace = "http://student.kuali.org/wsdl/proposal")
@Transactional(rollbackFor={Throwable.class})
public class ProposalServiceImpl implements ProposalService {
    private ProposalDao proposalDao;

    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    
    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#createProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo)
     */
    @Override
    public ProposalInfo createProposal(String proposalTypeKey, ProposalInfo proposalInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        if (proposalInfo.getProposerPerson() != null && !proposalInfo.getProposerPerson().isEmpty() && proposalInfo.getProposerOrg() != null && !proposalInfo.getProposerOrg().isEmpty()) {
            throw new InvalidParameterException("Not allowed to have both Person and Organization propsers");
        }
       try {
           Proposal proposal = ProposalAssembler.toProposal(proposalTypeKey, proposalInfo, proposalDao);
           proposalDao.create(proposal);

           return ProposalAssembler.toProposalInfo(proposal);
       } catch (VersionMismatchException e) {
           throw new InvalidParameterException(e.getMessage());
       }

    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#createProposalDocRelation(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.core.proposal.dto.ProposalDocRelationInfo)
     */
    @Override
    public ProposalDocRelationInfo createProposalDocRelation(String proposalDocRelationType, String documentId, String proposalId, ProposalDocRelationInfo proposalDocRelationInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalDocRelationType, "proposalDocRelationType");
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(proposalId, "proposalId");
        checkForMissingParameter(proposalDocRelationInfo, "proposalDocRelationInfo");

        try {
            ProposalDocRelation proposalDocRelation = ProposalAssembler.toProposalDocRelation(proposalDocRelationType, documentId, proposalId, proposalDocRelationInfo, proposalDao);
            proposalDao.create(proposalDocRelation);
            return ProposalAssembler.toProposalDocRelationInfo(proposalDocRelation);
        } catch (VersionMismatchException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#deleteProposal(java.lang.String)
     */
    @Override
    public StatusInfo deleteProposal(String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalId, "proposalId");

        StatusInfo status = new StatusInfo();
        try {
            proposalDao.delete(Proposal.class, proposalId);
        } catch (DoesNotExistException e) {
            status.setSuccess(false);
        }

        return status;
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#deleteProposalDocRelation(java.lang.String)
     */
    @Override
    public StatusInfo deleteProposalDocRelation(String proposalDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalDocRelationId, "proposalDocRelationId");

        StatusInfo status = new StatusInfo();
        try {
            proposalDao.delete(ProposalDocRelation.class, proposalDocRelationId);
        } catch (DoesNotExistException e) {
            status.setSuccess(false);
        }

        return status;
    }

    /**
    * @see org.kuali.student.core.proposal.service.ProposalService#getAllowedProposalDocRelationTypesForProposalType(java.lang.String)
     */
    @Override
    public List<String> getAllowedProposalDocRelationTypesForProposalType(String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");
        List<ProposalDocRelationType> proposalDocRelationType = proposalDao.getAllowedProposalDocRelationTypesForProposalType(proposalTypeKey);
        return ProposalAssembler.toProposalDocRelationTypeKeyList(proposalDocRelationType);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposal(java.lang.String)
     */
    @Override
    public ProposalInfo getProposal(String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalId, "proposalId");
        Proposal entity = proposalDao.fetch(Proposal.class, proposalId);
        return ProposalAssembler.toProposalInfo(entity);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelation(java.lang.String)
     */
    @Override
    public ProposalDocRelationInfo getProposalDocRelation(String proposalDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationId, "proposalDocRelationId");
        ProposalDocRelation docRelation = proposalDao.fetch(ProposalDocRelation.class, proposalDocRelationId);
        return ProposalAssembler.toProposalDocRelationInfo(docRelation);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationType(java.lang.String)
     */
    @Override
    public ProposalDocRelationTypeInfo getProposalDocRelationType(String proposalDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationTypeKey, "proposalDocRelationTypeKey");

        ProposalDocRelationType type = proposalDao.fetch(ProposalDocRelationType.class, proposalDocRelationTypeKey);
        return ProposalAssembler.toProposalDocRelationTypeInfo(type);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationTypes()
     */
    @Override
    public List<ProposalDocRelationTypeInfo> getProposalDocRelationTypes() throws OperationFailedException {
        List<ProposalDocRelationType> types = proposalDao.find(ProposalDocRelationType.class);
        return ProposalAssembler.toProposalDocRelationTypeInfos(types);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByDocument(java.lang.String)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(documentId, "documentId");

        List<ProposalDocRelation> proposalDocRelations = proposalDao.getProposalDocRelationsByDocument(documentId);
        return ProposalAssembler.toProposalDocRelationInfos(proposalDocRelations);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByIdList(java.util.List)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByIdList(List<String> proposalDocRelationIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationIdList, "proposalDocRelationIdList");
        checkForEmptyList(proposalDocRelationIdList, "proposalDocRelationIdList");
        List<ProposalDocRelation> proposalDocRelations = proposalDao.getProposalDocRelationsByIdList(proposalDocRelationIdList);
        return ProposalAssembler.toProposalDocRelationInfos(proposalDocRelations);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByProposal(java.lang.String)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByProposal(String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalId, "proposalId");
        List<ProposalDocRelation> proposalDocRelations = proposalDao.getProposalDocRelationsByProposal(proposalId);
        return ProposalAssembler.toProposalDocRelationInfos(proposalDocRelations);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByType(java.lang.String)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByType(String proposalDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationTypeKey, "proposalDocRelationTypeKey");
        List<ProposalDocRelation> proposalDocRelations = proposalDao.getProposalDocRelationsByType(proposalDocRelationTypeKey);
        return ProposalAssembler.toProposalDocRelationInfos(proposalDocRelations);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalType(java.lang.String)
     */
    @Override
    public ProposalTypeInfo getProposalType(String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        ProposalType proposalType = proposalDao.fetch(ProposalType.class, proposalTypeKey);
        return ProposalAssembler.toProposalTypeInfo(proposalType);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalTypes()
     */
    @Override
    public List<ProposalTypeInfo> getProposalTypes() throws OperationFailedException {
        List<ProposalType> proposalTypes = proposalDao.find(ProposalType.class);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalTypesForReferenceType(java.lang.String)
     */
    @Override
    public List<ProposalTypeInfo> getProposalTypesForReferenceType(String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(referenceTypeKey, "referenceTypeKey");

        List<ProposalType> proposalTypes = proposalDao.getProposalTypesForReferenceType(referenceTypeKey);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByIdList(java.util.List)
     */
    @Override
    public List<ProposalInfo> getProposalsByIdList(List<String> proposalIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalIdList, "proposalIdList");
        checkForEmptyList(proposalIdList, "proposalIdList");

        List<Proposal> proposals = proposalDao.getProposalsByIdList(proposalIdList);
        if (proposals.size() != proposalIdList.size()) {
            throw new DoesNotExistException();
        }
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByProposalType(java.lang.String)
     */
    @Override
    public List<ProposalInfo> getProposalsByProposalType(String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        List<Proposal> proposals = proposalDao.getProposalsByProposalType(proposalTypeKey);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByReference(java.lang.String, java.lang.String)
     */
    @Override
    public List<ProposalInfo> getProposalsByReference(String referenceTypeKey, String referenceId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(referenceTypeKey, "referenceTypeKey");
        checkForMissingParameter(referenceId, "referenceId");

        List<Proposal> proposals = proposalDao.getProposalsByReference(referenceTypeKey, referenceId);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByState(java.lang.String, java.lang.String)
     */
    @Override
    public List<ProposalInfo> getProposalsByState(String proposalState, String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalState, "proposalState");
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        List<Proposal> proposals = proposalDao.getProposalsByState(proposalState, proposalTypeKey);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getReferenceTypes()
     */
    @Override
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException {
        List<ProposalReferenceType> referenceTypes = proposalDao.find(ProposalReferenceType.class);
        return ProposalAssembler.toReferenceTypeInfos(referenceTypes);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#updateProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo)
     */
    @Override
    public ProposalInfo updateProposal(String proposalId, ProposalInfo proposalInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(proposalId, "proposalId");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        proposalInfo.setId(proposalId);
        if (proposalInfo.getProposerPerson() != null && !proposalInfo.getProposerPerson().isEmpty() && proposalInfo.getProposerOrg() != null && !proposalInfo.getProposerOrg().isEmpty()) {
            throw new InvalidParameterException("Not allowed to have both Person and Organization propsers");
        }

        Proposal proposal = ProposalAssembler.toProposal(proposalInfo.getType(), proposalInfo, proposalDao);
        Proposal updated = proposalDao.update(proposal);

        return ProposalAssembler.toProposalInfo(updated);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#updateProposalDocRelation(java.lang.String, org.kuali.student.core.proposal.dto.ProposalDocRelationInfo)
     */
    @Override
    public ProposalDocRelationInfo updateProposalDocRelation(String proposalDocRelationId, ProposalDocRelationInfo proposalDocRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(proposalDocRelationId, "proposalDocRelationId");
        checkForMissingParameter(proposalDocRelationInfo, "proposalDocRelationInfo");

        proposalDocRelationInfo.setId(proposalDocRelationId);
        ProposalDocRelation proposalDocRelation = ProposalAssembler.toProposalDocRelation(proposalDocRelationInfo.getType(), proposalDocRelationInfo.getDocumentId(), proposalDocRelationInfo.getProposalId(), proposalDocRelationInfo, proposalDao);
        proposalDao.update(proposalDocRelation);
        return ProposalAssembler.toProposalDocRelationInfo(proposalDocRelation);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#validateProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo)
     */
    @Override
    public List<ValidationResultInfo> validateProposal(String validationType, ProposalInfo proposalInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(proposalInfo, "proposalInfo");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#validateProposalDocRelation(java.lang.String, org.kuali.student.core.proposal.dto.ProposalDocRelationInfo)
     */
    @Override
    public List<ValidationResultInfo> validateProposalDocRelation(String validationType, ProposalDocRelationInfo proposalDocRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(proposalDocRelationInfo, "proposalDocRelationInfo");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * Check for missing parameter and thow localized exception if missing
     *
     * @param param
     * @param parameter name
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    /**
     * Check for an empty list
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForEmptyList(Object param, String paramName)
            throws MissingParameterException {
        if (param != null && param instanceof List<?> && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }

    public ProposalDao getProposalDao() {
        return proposalDao;
    }

    public void setProposalDao(ProposalDao dao) {
        this.proposalDao = dao;
    }

    /**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#getObjectStructure(java.lang.String)
     */
    @Override
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    /**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#getObjectTypes()
     */
    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    /**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#validateObject(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean validateObject(String objectTypeKey, String stateKey, String info) {
        return dictionaryServiceDelegate.validateObject(objectTypeKey, stateKey, info);
    }

    /**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#validateStructureData(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean validateStructureData(String objectTypeKey, String stateKey, String info) {
        return dictionaryServiceDelegate.validateStructureData(objectTypeKey, stateKey, info);
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaType(java.lang.String)
     */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaTypes()
     */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchResultType(java.lang.String)
     */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchResultTypes()
     */
    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchType(java.lang.String)
     */
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);    
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypes()
     */
    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
    }

    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByResult(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypesByResult(searchResultTypeKey);
    }

	@Override
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
		return searchManager.search(searchRequest, proposalDao);
	}
}

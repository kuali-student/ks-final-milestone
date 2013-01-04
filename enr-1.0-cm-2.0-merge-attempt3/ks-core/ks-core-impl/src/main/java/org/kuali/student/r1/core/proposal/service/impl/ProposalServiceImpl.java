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

package org.kuali.student.r1.core.proposal.service.impl;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.dto.ReferenceTypeInfo;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r1.core.proposal.dao.ProposalDao;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r1.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.r1.core.proposal.entity.Proposal;
import org.kuali.student.r1.core.proposal.entity.ProposalReference;
import org.kuali.student.r1.core.proposal.entity.ProposalReferenceType;
import org.kuali.student.r1.core.proposal.entity.ProposalType;
import org.kuali.student.r1.core.proposal.service.ProposalService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.List;

/**
 * Implementation of the Proposal Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Proposal+Service">ProposalService</>
 */
@Deprecated
@WebService(endpointInterface = "org.kuali.student.r1.core.proposal.service.ProposalService", serviceName = "ProposalService", portName = "ProposalService", targetNamespace = "http://student.kuali.org/wsdl/proposal")
public class ProposalServiceImpl implements ProposalService {
    private ProposalDao proposalDao;

    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;
    
    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    /**
     * @see org.kuali.student.r1.core.proposal.service.ProposalService#createProposal(java.lang.String, org.kuali.student.r1.core.proposal.dto.ProposalInfo)
     */
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public ProposalInfo createProposal(String proposalTypeKey, ProposalInfo proposalInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateProposal("OBJECT", proposalInfo);
        if (null != validationResults && validationResults.size() > 0) {        	
        	throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        
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
     * @see org.kuali.student.core.proposal.service.ProposalService#deleteProposal(java.lang.String)
     */
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposal(java.lang.String)
     */
    @Override
    @Transactional(readOnly=true)
    public ProposalInfo getProposal(String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalId, "proposalId");
        Proposal entity = proposalDao.fetch(Proposal.class, proposalId);
        return ProposalAssembler.toProposalInfo(entity);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalType(java.lang.String)
     */
    @Override
    @Transactional(readOnly=true)
    public ProposalTypeInfo getProposalType(String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        ProposalType proposalType = proposalDao.fetch(ProposalType.class, proposalTypeKey);
        return ProposalAssembler.toProposalTypeInfo(proposalType);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalTypes()
     */
    @Override
    @Transactional(readOnly=true)
    public List<ProposalTypeInfo> getProposalTypes() throws OperationFailedException {
        List<ProposalType> proposalTypes = proposalDao.find(ProposalType.class);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalTypesForReferenceType(java.lang.String)
     */
    @Override
    @Transactional(readOnly=true)
    public List<ProposalTypeInfo> getProposalTypesForReferenceType(String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(referenceTypeKey, "referenceTypeKey");

        List<ProposalType> proposalTypes = proposalDao.getProposalTypesForReferenceType(referenceTypeKey);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByIdList(java.util.List)
     */
    @Override
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<ProposalInfo> getProposalsByProposalType(String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        List<Proposal> proposals = proposalDao.getProposalsByProposalType(proposalTypeKey);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByReference(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<ProposalInfo> getProposalsByState(String proposalState, String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalState, "proposalState");
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        List<Proposal> proposals = proposalDao.getProposalsByState(proposalState, proposalTypeKey);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalByWorkflowId()
     */
	@Override
    @Transactional(readOnly=true)
	public ProposalInfo getProposalByWorkflowId(String workflowId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(workflowId, "workflowId");
		
        Proposal entity = proposalDao.getProposalByWorkflowId(workflowId);
        return ProposalAssembler.toProposalInfo(entity);
	}

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getReferenceTypes()
     */
    @Override
    @Transactional(readOnly=true)
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException {
        List<ProposalReferenceType> referenceTypes = proposalDao.find(ProposalReferenceType.class);
        return ProposalAssembler.toReferenceTypeInfos(referenceTypes);
    }

    /**
     * @see org.kuali.student.r1.core.proposal.service.ProposalService#updateProposal(java.lang.String, org.kuali.student.r1.core.proposal.dto.ProposalInfo)
     */
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public ProposalInfo updateProposal(String proposalId, ProposalInfo proposalInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(proposalId, "proposalId");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateProposal("OBJECT", proposalInfo);
        if (null != validationResults && validationResults.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        
        proposalInfo.setId(proposalId);
        if (proposalInfo.getProposerPerson() != null && !proposalInfo.getProposerPerson().isEmpty() && proposalInfo.getProposerOrg() != null && !proposalInfo.getProposerOrg().isEmpty()) {
            throw new InvalidParameterException("Not allowed to have both Person and Organization propsers");
        }

        Proposal proposal = ProposalAssembler.toProposal(proposalInfo.getType(), proposalInfo, proposalDao);
        Proposal updated = proposalDao.update(proposal);

        return ProposalAssembler.toProposalInfo(updated);
    }

    /**
     * @see org.kuali.student.r1.core.proposal.service.ProposalService#validateProposal(java.lang.String, org.kuali.student.r1.core.proposal.dto.ProposalInfo)
     */
    @Override
    public List<ValidationResultInfo> validateProposal(String validationType, ProposalInfo proposalInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(ProposalInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(proposalInfo, objStructure, null);
        return validationResults;         
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

    /**
     * 
     * This method ...
     * 
     * @return
     */
    @Deprecated
    public ProposalDao getProposalDao() {
        return proposalDao;
    }

    /**
     * 
     * This method ...
     * 
     * @param dao
     */
    @Deprecated
    public void setProposalDao(ProposalDao dao) {
        this.proposalDao = dao;
    }

    /**
     * @see org.kuali.student.common.dictionary.service.old.DictionaryService#getObjectStructure(java.lang.String)
     */
    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    /**
     * @see org.kuali.student.common.dictionary.service.old.DictionaryService#getObjectTypes()
     */
    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    /**
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchCriteriaTypes(ContextInfo)
     */
    @Override
    public List<TypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchCriteriaTypes(contextInfo);
    }

    /**
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchResultTypes(ContextInfo)
     */
    @Override
    public List<TypeInfo> getSearchResultTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchResultTypes(contextInfo);
    }

    /**
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchType(java.lang.String, ContextInfo)
     */
    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey, contextInfo);
    }

    /**
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchTypes(ContextInfo)
     */
    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchTypes(contextInfo);
    }

    /**
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchTypesByCriteria(java.lang.String,ContextInfo)
     */
    @Override
    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

    /**
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchTypesByResult(java.lang.String,ContextInfo)
     */
    @Override
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

	@Override
	public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
		if("proposal.search.proposalsForReferenceIds".equals(searchRequest.getSearchKey())){
			return doSearchProposalsForReferenceIds(searchRequest);
		}else{
			return searchManager.search(searchRequest, contextInfo);
		}
	}

    private SearchResultInfo doSearchProposalsForReferenceIds(
			SearchRequestInfo searchRequest) {

    	List<String> referenceIds = null;
    	for(SearchParamInfo param: searchRequest.getParams()){
    		if("proposal.queryParam.proposalOptionalReferenceIds".equals(param.getKey())){
    			referenceIds = param.getValues();
    		}
    	}
    	List<Proposal> proposals = proposalDao.getProposalsByRefernceIds(referenceIds);
    	SearchResultInfo result = new SearchResultInfo();
    	for(Proposal proposal:proposals){
    		for(ProposalReference reference:proposal.getProposalReference()){
	    		SearchResultRowInfo row = new SearchResultRowInfo();
	    		row.addCell("proposal.resultColumn.proposalId", proposal.getId());
	    		row.addCell("proposal.resultColumn.proposalOptionalName", proposal.getName());
	    		row.addCell("proposal.resultColumn.proposalOptionalReferenceId", reference.getObjectReferenceId());
	    		result.getRows().add(row);
    		}
    	}
		return result;
	}

	/**
     * @return the validatorFactory
     */
    @Deprecated
    public ValidatorFactory getValidatorFactory()  {
        return validatorFactory;
    }

    /**
     * @param validatorFactory the validatorFactory to set
     */
    @Deprecated
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    /**
     * @return the searchManager
     */
    @Deprecated
    public SearchManager getSearchManager()  {
        return searchManager;
    }

    /**
     * @return the dictionaryServiceDelegate
     */
    @Deprecated
    public DictionaryService getDictionaryServiceDelegate()  {
        return dictionaryServiceDelegate;
    }

	
}

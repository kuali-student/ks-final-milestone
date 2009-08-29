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
package org.kuali.student.core.proposal.service.impl;

import java.util.List;

import javax.jws.WebService;

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
import org.kuali.student.core.proposal.dto.ReferenceTypeInfo;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalDocRelation;
import org.kuali.student.core.proposal.entity.ProposalType;
import org.kuali.student.core.proposal.entity.ReferenceType;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
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

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#createProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo)
     */
    @Override
    public ProposalInfo createProposal(String proposalTypeKey, ProposalInfo proposalInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");
        checkForMissingParameter(proposalInfo, "proposalInfo");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#createProposalDocRelation(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.core.proposal.dto.ProposalDocRelationInfo)
     */
    @Override
    public ProposalDocRelationInfo createProposalDocRelation(String proposalDocRelationType, String documentId, String proposalId, ProposalDocRelationInfo proposalDocRelationInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalDocRelationType, "proposalDocRelationType");
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(proposalId, "proposalId");
        checkForMissingParameter(proposalDocRelationInfo, "proposalDocRelationInfo");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#deleteProposal(java.lang.String)
     */
    @Override
    public StatusInfo deleteProposal(String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalId, "proposalId");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#deleteProposalDocRelation(java.lang.String)
     */
    @Override
    public StatusInfo deleteProposalDocRelation(String proposalDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalDocRelationId, "proposalDocRelationId");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getAllowedProposalDocRelationTypesForProposalType(java.lang.String)
     */
    @Override
    public List<String> getAllowedProposalDocRelationTypesForProposalType(String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposal(java.lang.String)
     */
    @Override
    public ProposalInfo getProposal(String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalId, "proposalId");
        Proposal entity = proposalDao.fetch(Proposal.class, proposalId);
        return ProposalAssembler.toProposalInfo(entity);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelation(java.lang.String)
     */
    @Override
    public ProposalDocRelationInfo getProposalDocRelation(String proposalDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationId, "proposalDocRelationId");
        ProposalDocRelation docRelation = proposalDao.fetch(ProposalDocRelation.class, proposalDocRelationId);
        return ProposalAssembler.toProposalDocRelationInfo(docRelation);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationType(java.lang.String)
     */
    @Override
    public ProposalDocRelationTypeInfo getProposalDocRelationType(String proposalDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationTypeKey, "proposalDocRelationTypeKey");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationTypes()
     */
    @Override
    public List<ProposalDocRelationTypeInfo> getProposalDocRelationTypes() throws OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByDocument(java.lang.String)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(documentId, "documentId");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByIdList(java.util.List)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByIdList(List<String> proposalDocRelationIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationIdList, "proposalDocRelationIdList");
        checkForEmptyList(proposalDocRelationIdList, "proposalDocRelationIdList");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByProposal(java.lang.String)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByProposal(String proposalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalId, "proposalId");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalDocRelationsByType(java.lang.String)
     */
    @Override
    public List<ProposalDocRelationInfo> getProposalDocRelationsByType(String proposalDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalDocRelationTypeKey, "proposalDocRelationTypeKey");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
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
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalTypes()
     */
    @Override
    public List<ProposalTypeInfo> getProposalTypes() throws OperationFailedException {
        List<ProposalType> proposalTypes = proposalDao.find(ProposalType.class);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalTypesForReferenceType(java.lang.String)
     */
    @Override
    public List<ProposalTypeInfo> getProposalTypesForReferenceType(String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(referenceTypeKey, "referenceTypeKey");

        List<ProposalType> proposalTypes = proposalDao.getProposalTypesForReferenceType(referenceTypeKey);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     * This overridden method ...
     *
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
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByProposalType(java.lang.String)
     */
    @Override
    public List<ProposalInfo> getProposalsByProposalType(String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        List<Proposal> proposals = proposalDao.getProposalsByProposalType(proposalTypeKey);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * This overridden method ...
     *
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
     * This overridden method ...
     *
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
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#getReferenceTypes()
     */
    @Override
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException {
        List<ReferenceType> referenceTypes = proposalDao.find(ReferenceType.class);
        return ProposalAssembler.toReferenceTypeInfos(referenceTypes);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#updateProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo)
     */
    @Override
    public ProposalInfo updateProposal(String proposalId, ProposalInfo proposalInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(proposalId, "proposalId");
        checkForMissingParameter(proposalInfo, "proposalInfo");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#updateProposalDocRelation(java.lang.String, org.kuali.student.core.proposal.dto.ProposalDocRelationInfo)
     */
    @Override
    public ProposalDocRelationInfo updateProposalDocRelation(String proposalDocRelationId, ProposalDocRelationInfo proposalDocRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(proposalDocRelationId, "proposalDocRelationId");
        checkForMissingParameter(proposalDocRelationInfo, "proposalDocRelationInfo");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#validateProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo)
     */
    @Override
    public List<ValidationResultContainer> validateProposal(String validationType, ProposalInfo proposalInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(proposalInfo, "proposalInfo");
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.proposal.service.ProposalService#validateProposalDocRelation(java.lang.String, org.kuali.student.core.proposal.dto.ProposalDocRelationInfo)
     */
    @Override
    public List<ValidationResultContainer> validateProposalDocRelation(String validationType, ProposalDocRelationInfo proposalDocRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
}

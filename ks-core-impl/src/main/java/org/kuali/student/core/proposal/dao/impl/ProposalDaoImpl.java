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

package org.kuali.student.core.proposal.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.proposal.dao.ProposalDao;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalDocRelation;
import org.kuali.student.core.proposal.entity.ProposalDocRelationType;
import org.kuali.student.core.proposal.entity.ProposalOrg;
import org.kuali.student.core.proposal.entity.ProposalPerson;
import org.kuali.student.core.proposal.entity.ProposalReference;
import org.kuali.student.core.proposal.entity.ProposalType;

/**
 * Database access for the Proposal Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ProposalDaoImpl extends AbstractSearchableCrudDaoImpl implements ProposalDao {
    @PersistenceContext(unitName = "Proposal")
    @Override
    public void setEm(EntityManager em) {
        super.setEm(em);
    }

    @Override
    public List<Proposal> getProposalsByIdList(List<String> idList) {
        Query query = em.createNamedQuery("Proposal.getProposalsByIdList");
        query.setParameter("idList", idList);
        @SuppressWarnings("unchecked")
        List<Proposal> proposals = query.getResultList();
        return proposals;
    }

    @Override
    public List<Proposal> getProposalsByProposalType(String proposalTypeId) throws DoesNotExistException{
        Query query = em.createNamedQuery("Proposal.getProposalsByProposalType");
        query.setParameter("proposalTypeId", proposalTypeId);
        @SuppressWarnings("unchecked")
        List<Proposal> proposals = query.getResultList();
        return proposals;
    }

    @Override
    public List<Proposal> getProposalsByReference(String referenceTypeId, String referenceId) throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getProposalsByReference");
        query.setParameter("referenceId", referenceId);
        query.setParameter("referenceTypeId", referenceTypeId);
        @SuppressWarnings("unchecked")
        List<Proposal> proposals = query.getResultList();
        return proposals;
    }

    @Override
    public List<Proposal> getProposalsByState(String proposalState, String proposalTypeId) throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getProposalsByState");
        query.setParameter("proposalState", proposalState);
        query.setParameter("proposalTypeId", proposalTypeId);
        @SuppressWarnings("unchecked")
        List<Proposal> proposals = query.getResultList();
        return proposals;
    }

    @Override
    public List<ProposalType> getProposalTypesForReferenceType(String referenceTypeId) throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getProposalTypesForReferenceType");
        query.setParameter("referenceTypeId", referenceTypeId);
        @SuppressWarnings("unchecked")
        List<ProposalType> proposalTypes = query.getResultList();
        return proposalTypes;
    }

    @Override
    public List<ProposalDocRelation> getProposalDocRelationsByDocument(String documentId) throws DoesNotExistException {
        Query query = em.createNamedQuery("ProposalDocRelation.getByDocId");
        query.setParameter("documentId", documentId);
        @SuppressWarnings("unchecked")
        List<ProposalDocRelation> proposalDocRelations = query.getResultList();
        return proposalDocRelations;
    }

    @Override
    public List<ProposalDocRelation> getProposalDocRelationsByIdList(List<String> idList) throws DoesNotExistException {
        Query query = em.createNamedQuery("ProposalDocRelation.getProposalDocRelationsByIdList");
        query.setParameter("idList", idList);
        @SuppressWarnings("unchecked")
        List<ProposalDocRelation> proposalDocRelations = query.getResultList();
        return proposalDocRelations;
    }

    @Override
    public List<ProposalDocRelation> getProposalDocRelationsByProposal(String proposalId) throws DoesNotExistException {
        Query query = em.createNamedQuery("ProposalDocRelation.getProposalDocRelationsByProposal");
        query.setParameter("proposalId", proposalId);
        @SuppressWarnings("unchecked")
        List<ProposalDocRelation> proposalDocRelations = query.getResultList();
        return proposalDocRelations;
    }

    @Override
    public List<ProposalDocRelation> getProposalDocRelationsByType(String proposalDocRelationTypeKey) throws DoesNotExistException {
        Query query = em.createNamedQuery("ProposalDocRelation.getProposalDocRelationsByType");
        query.setParameter("proposalDocRelationTypeKey", proposalDocRelationTypeKey);
        @SuppressWarnings("unchecked")
        List<ProposalDocRelation> proposalDocRelations = query.getResultList();
        return proposalDocRelations;
    }

    @Override
    public ProposalPerson getProposalPerson(String proposerId) {
        Query query = em.createNamedQuery("ProposalPerson.getProposalPerson");
        query.setParameter("proposerId", proposerId);
        ProposalPerson proposalPerson = (ProposalPerson)query.getSingleResult();
        return proposalPerson;
    }

    @Override
    public ProposalOrg getProposalOrg(String orgId) throws NoResultException {
        Query query = em.createNamedQuery("ProposalOrg.getProposalOrg");
        query.setParameter("orgId", orgId);
        ProposalOrg proposalPerson = (ProposalOrg)query.getSingleResult();
        return proposalPerson;
    }

    @Override
    public ProposalReference getObjectReference(String objectReferenceId, String objectReferenceType) throws NoResultException {
        Query query = em.createNamedQuery("ProposalReference.getObjectReference");
        query.setParameter("objectReferenceType", objectReferenceType);
        query.setParameter("objectReferenceId", objectReferenceId);
        ProposalReference objectReference = (ProposalReference)query.getSingleResult();
        return objectReference;
    }

    @Override
    public List<ProposalDocRelationType> getAllowedProposalDocRelationTypesForProposalType(String proposalTypeKey) throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getDocRelationTypesForProposalType");
        query.setParameter("proposalTypeKey", proposalTypeKey);
        @SuppressWarnings("unchecked")
        List<ProposalDocRelationType> proposalDocRelationTypeList = query.getResultList();
        return proposalDocRelationTypeList;
    }
}

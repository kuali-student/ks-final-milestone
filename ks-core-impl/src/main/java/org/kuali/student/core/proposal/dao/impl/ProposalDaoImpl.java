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
package org.kuali.student.core.proposal.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.proposal.dao.ProposalDao;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalType;

/**
 * Database access for the Proposal Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ProposalDaoImpl extends AbstractCrudDaoImpl implements ProposalDao {
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
        if (proposals == null || proposals.isEmpty()) {
            throw new DoesNotExistException(proposalTypeId);
        }
        return proposals;
    }

    @Override
    public List<Proposal> getProposalsByReference(String referenceTypeId, String referenceId) throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getProposalsByReference");
        query.setParameter("referenceId", referenceId);
        query.setParameter("referenceTypeId", referenceTypeId);
        @SuppressWarnings("unchecked")
        List<Proposal> proposals = query.getResultList();
        if (proposals == null || proposals.isEmpty()) {
            throw new DoesNotExistException(referenceId +":" + referenceTypeId);
        }
        return proposals;
    }

    @Override
    public List<Proposal> getProposalsByState(String proposalState, String proposalTypeId) throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getProposalsByState");
        query.setParameter("proposalState", proposalState);
        query.setParameter("proposalTypeId", proposalTypeId);
        @SuppressWarnings("unchecked")
        List<Proposal> proposals = query.getResultList();
        if (proposals == null || proposals.isEmpty()) {
            throw new DoesNotExistException(proposalState +":" + proposalTypeId);
        }
        return proposals;
    }

    @Override
    public List<ProposalType> getProposalTypesForReferenceType(String referenceTypeId) throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getProposalTypesForReferenceType");
        query.setParameter("referenceTypeId", referenceTypeId);
        @SuppressWarnings("unchecked")
        List<ProposalType> proposalTypes = query.getResultList();
        if (proposalTypes == null || proposalTypes.isEmpty()) {
            throw new DoesNotExistException(referenceTypeId);
        }
        return proposalTypes;

    }
}

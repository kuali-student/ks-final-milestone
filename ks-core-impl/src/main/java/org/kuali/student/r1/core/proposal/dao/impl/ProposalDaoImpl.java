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

package org.kuali.student.r1.core.proposal.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.r1.common.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r1.core.proposal.dao.ProposalDao;
import org.kuali.student.r1.core.proposal.entity.Proposal;
import org.kuali.student.r1.core.proposal.entity.ProposalOrg;
import org.kuali.student.r1.core.proposal.entity.ProposalPerson;
import org.kuali.student.r1.core.proposal.entity.ProposalReference;
import org.kuali.student.r1.core.proposal.entity.ProposalType;

/**
 * Database access for the Proposal Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
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
    public List<Proposal> getProposalsByProposalType(String proposalTypeId) {
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
	public Proposal getProposalByWorkflowId(String workflowId)
			throws DoesNotExistException {
        Query query = em.createNamedQuery("Proposal.getProposalByWorkflowId");
        query.setParameter("workflowId", workflowId);

        Proposal proposal = (Proposal)query.getSingleResult();
		return proposal;
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
	public List<Proposal> getProposalsByRefernceIds(List<String> referenceIds) {
		Query query = em.createNamedQuery("Proposal.getProposalsByRefernceIds");
		query.setParameter("referenceIds", referenceIds);
		List<Proposal> proposals = query.getResultList();
		return proposals;
	}

}

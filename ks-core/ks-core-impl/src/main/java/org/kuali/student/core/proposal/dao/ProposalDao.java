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

package org.kuali.student.core.proposal.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.kuali.student.common.dao.CrudDao;
import org.kuali.student.common.dao.SearchableDao;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalOrg;
import org.kuali.student.core.proposal.entity.ProposalPerson;
import org.kuali.student.core.proposal.entity.ProposalReference;
import org.kuali.student.core.proposal.entity.ProposalType;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public interface ProposalDao extends CrudDao, SearchableDao {
    public List<Proposal> getProposalsByIdList(List<String> idList);
    public List<Proposal> getProposalsByProposalType(String proposalTypeId) throws DoesNotExistException;
    public List<Proposal> getProposalsByReference(String referenceTypeId, String referenceId) throws DoesNotExistException;
    public List<Proposal> getProposalsByState(String proposalState, String proposalTypeId) throws DoesNotExistException;
    public Proposal getProposalByWorkflowId(String workflowId) throws DoesNotExistException;
    public List<ProposalType> getProposalTypesForReferenceType(String referenceTypeId) throws DoesNotExistException;
    public ProposalPerson getProposalPerson(String proposerId);
    public ProposalOrg getProposalOrg(String orgId);
    public ProposalReference getObjectReference(String objectReferenceId, String objectReferenceType);
    public EntityManager getEm();
}

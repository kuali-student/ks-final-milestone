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

package org.kuali.student.core.proposal.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.kuali.student.core.entity.BaseEntity;

/**
 * Join table between Proposal and what it references
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KSPR_PROPOSAL_REFERENCE")
@NamedQueries( {
    @NamedQuery(name = "ProposalReference.getObjectReference", query = "SELECT o FROM ProposalReference o WHERE o.objectReferenceId = :objectReferenceId AND o.type.id = :objectReferenceType")
})
@AttributeOverride(name="id", column=@Column(name="REFERENCE_ID"))
public class ProposalReference extends BaseEntity{

    @ManyToMany(mappedBy="proposalReference",fetch=FetchType.EAGER)
    private List<Proposal> proposals;

    @Column(name = "OBJECT_REFERENCE_ID")
    private String objectReferenceId;

    @ManyToOne(optional=true)
    @JoinColumn(name = "TYPE")
    private ProposalReferenceType type;

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public String getObjectReferenceId() {
        return objectReferenceId;
    }

    public void setObjectReferenceId(String objectReferenceId) {
        this.objectReferenceId = objectReferenceId;
    }

    public ProposalReferenceType getType() {
        return type;
    }

    public void setType(ProposalReferenceType type) {
        this.type = type;
    }
}

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

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

/**
 * Proposal
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KSPR_PROPOSAL")
@NamedQueries( {
    @NamedQuery(name = "Proposal.getProposalsByIdList", query = "SELECT p FROM Proposal p WHERE p.id IN (:idList)"),
    @NamedQuery(name = "Proposal.getProposalsByProposalType", query = "SELECT DISTINCT p FROM Proposal p WHERE p.type.id = :proposalTypeId"),
    @NamedQuery(name = "Proposal.getProposalsByReference", query = "SELECT r.proposals FROM ProposalReference r WHERE r.objectReferenceId = :referenceId AND r.type.id = :referenceTypeId"),
    @NamedQuery(name = "Proposal.getProposalsByState", query = "SELECT DISTINCT p FROM Proposal p WHERE p.state = :proposalState AND p.type.id = :proposalTypeId"),
    @NamedQuery(name = "Proposal.getProposalTypesForReferenceType", query = "SELECT DISTINCT p.type FROM ProposalReference r JOIN r.proposals p WHERE r.type.id = :referenceTypeId")
})
public class Proposal extends MetaEntity implements AttributeOwner<ProposalAttribute> {
    @Id
    @Column(name = "PROPOSAL_ID")
    private String id;

    @Column(name="WORKFLOW_ID")
    private String workflowId;

	@Column(name="NAME")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proposal")
    private List<ProposalPerson> proposerPerson;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proposal")
    private List<ProposalOrg> proposerOrg;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="KSPR_PROPOSAL_JN_REFERENCE",
            joinColumns=
            @JoinColumn(name="PROPOSAL_ID", referencedColumnName="PROPOSAL_ID"),
      inverseJoinColumns=
            @JoinColumn(name="REFERENCE_ID", referencedColumnName="REFERENCE_ID")
    )
    private List<ProposalReference> proposalReference;

    @Column(name = "RATIONALE")
    private String rationale;

    @Column(name = "DETAIL_DESC")
    private String detailDesc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ProposalAttribute> attributes;

    @ManyToOne(optional=true)
    @JoinColumn(name = "TYPE")
    private ProposalType type;

    @Column(name = "STATE")
    private String state;

    @Override
    protected void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProposalPerson> getProposerPerson() {
        return proposerPerson;
    }

    public void setProposerPerson(List<ProposalPerson> proposerPerson) {
        this.proposerPerson = proposerPerson;
    }

    public List<ProposalOrg> getProposerOrg() {
        return proposerOrg;
    }

    public void setProposerOrg(List<ProposalOrg> proposerOrg) {
        this.proposerOrg = proposerOrg;
    }


    public List<ProposalReference> getProposalReference() {
        return proposalReference;
    }

    public void setProposalReference(List<ProposalReference> proposalReference) {
        this.proposalReference = proposalReference;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ProposalType getType() {
        return type;
    }

    public void setType(ProposalType type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public List<ProposalAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<ProposalAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
}

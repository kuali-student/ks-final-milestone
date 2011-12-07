/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.proposal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.kuali.student.r2.core.proposal.infc.Proposal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProposalInfo extends IdEntityInfo implements Proposal, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> proposerPerson;

    @XmlElement
    private List<String> proposerOrg;

    @XmlElement
    private String proposalReferenceType;

    @XmlElement
    private List<String> proposalReference;

    @XmlElement
    private String rationale;

    @XmlElement
    private String detailDesc;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private String workflowId;

    /**
     * List of person identifiers. Structure should contain a proposerPerson OR
     * a proposerOrg.
     */
    @Override
    public List<String> getProposerPerson() {
        if (proposerPerson == null) {
            proposerPerson = new ArrayList<String>(0);
        }
        return proposerPerson;
    }

    public void setProposerPerson(List<String> proposerPerson) {
        this.proposerPerson = proposerPerson;
    }

    /**
     * List of organization identifiers. Structure should contain a
     * proposerPerson OR a proposerOrg
     */
    @Override
    public List<String> getProposerOrg() {
        if (proposerOrg == null) {
            proposerOrg = new ArrayList<String>(0);
        }
        return proposerOrg;
    }

    public void setProposerOrg(List<String> proposerOrg) {
        this.proposerOrg = proposerOrg;
    }

    /**
     * Unique identifier for a reference type.
     */
    @Override
    public String getProposalReferenceType() {
        return proposalReferenceType;
    }

    public void setProposalReferenceType(String proposalReferenceType) {
        this.proposalReferenceType = proposalReferenceType;
    }

    /**
     * List of reference identifiers.
     */
    @Override
    public List<String> getProposalReference() {
        if (proposalReference == null) {
            proposalReference = new ArrayList<String>(0);
        }
        return proposalReference;
    }

    public void setProposalReference(List<String> proposalReference) {
        this.proposalReference = proposalReference;
    }

    /**
     * Brief explanation of the reason for the proposal
     */
    @Override
    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    /**
     * Detailed description of the proposed changes.
     */
    @Override
    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    /**
     * Date and time that this proposal became effective. This is a similar
     * concept to the effective date on enumerated values. When an expiration
     * date has been specified, this field must be less than or equal to the
     * expiration date.
     */
    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this proposal expires. This is a similar concept to
     * the expiration date on enumerated values. If specified, this should be
     * greater than or equal to the effective date. If this field is not
     * specified, then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     */
    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * The workflow document associated with this proposal.
     * 
     * @return
     */
    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

}

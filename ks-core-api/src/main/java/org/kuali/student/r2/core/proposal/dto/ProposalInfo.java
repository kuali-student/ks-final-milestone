/*
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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;

import org.kuali.student.r2.core.proposal.infc.Proposal;
//import org.w3c.dom.Element;

@XmlType(name = "ProposalInfo", propOrder = {"id", 
        "typeKey", 
        "stateKey",
        "name", 
        "descr",
        "proposerPerson", 
        "proposerOrg", 
        "proposalReferenceType",
        "proposalReference", 
        "rationale", 
        "effectiveDate",
        "expirationDate",
        "workflowId",
        "meta", 
        "attributes", "_futureElements" }) 
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
    private RichTextInfo rationale;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private String workflowId;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public ProposalInfo() {

    }

    public ProposalInfo(Proposal input) {
        super(input);
        if (input != null) {
            if (input.getProposerPerson() != null) {
                this.proposerPerson = new ArrayList<String>(input.getProposerPerson());
            }
            if (input.getProposerOrg() != null) {
                this.proposerOrg = new ArrayList<String>(input.getProposerOrg());
            }
            this.proposalReferenceType = input.getProposalReferenceType();
            if (input.getProposalReference() != null) {
                this.proposalReference = new ArrayList<String>(input.getProposalReference());
            }
            if (input.getRationale() != null) {
                this.rationale = new RichTextInfo(input.getRationale());
            }
            if (input.getEffectiveDate() != null) {
                this.effectiveDate = new Date(input.getEffectiveDate().getTime());
            }
            if (input.getExpirationDate() != null) {
                this.expirationDate = new Date(input.getExpirationDate().getTime());
            }
            this.workflowId = input.getWorkflowId();
        }

    }

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

    @Override
    public String getProposalReferenceType() {
        return proposalReferenceType;
    }

    public void setProposalReferenceType(String proposalReferenceType) {
        this.proposalReferenceType = proposalReferenceType;
    }

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

    @Override
    public RichTextInfo getRationale() {
        return rationale;
    }

    public void setRationale(RichTextInfo rationale) {
        this.rationale = rationale;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

}

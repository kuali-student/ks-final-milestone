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
package org.kuali.student.core.proposal.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;

/**
 * Join table between Proposal and Document
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KSCO_PROPOSAL_DOCREL")
public class ProposalDocRelation extends MetaEntity implements AttributeOwner<ProposalDocRelationAttribute> {
    @Id
    @Column(name = "DOCREL_ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "PROPOSAL_ID")
    private Proposal proposal;

    @Column(name = "DOCUMENT_ID")
    private String documentId;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private RichText desc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ProposalDocRelationAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private ProposalDocRelationType type;

    @Column(name = "STATE")
    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RichText getDesc() {
        return desc;
    }

    public void setDesc(RichText desc) {
        this.desc = desc;
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

    public ProposalDocRelationType getType() {
        return type;
    }

    public void setType(ProposalDocRelationType type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public List<ProposalDocRelationAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<ProposalDocRelationAttribute> attributes) {
        this.attributes = attributes;
    }

}

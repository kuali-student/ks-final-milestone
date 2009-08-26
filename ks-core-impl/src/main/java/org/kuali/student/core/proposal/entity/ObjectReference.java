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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Join table between Proposal and what it references
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KSDO_PROPOSAL_REFERENCE")
public class ObjectReference {
    @Id
    @Column(name = "REFERENCE_ID")
    private String id;

    @ManyToMany(mappedBy="proposalReference",fetch=FetchType.EAGER)
    private List<Proposal> proposals;

    @Column(name = "OBJECT_REFERENCE_ID")
    private String objectReferenceId;

    @ManyToOne(optional=true)
    @JoinColumn(name = "TYPE")
    private ReferenceType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public ReferenceType getType() {
        return type;
    }

    public void setType(ReferenceType type) {
        this.type = type;
    }
}

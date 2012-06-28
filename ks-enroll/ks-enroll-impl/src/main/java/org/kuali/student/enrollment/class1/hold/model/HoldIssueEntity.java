/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.hold.model;

import java.util.ArrayList;
import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.infc.Issue;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * This is a description of what this class does - andy don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Entity
@Table(name = "KSEN_HOLD_ISSUE")
@NamedQueries({
    @NamedQuery(name = "HoldIssueEntity.getIdsByType",
    query = "select id from HoldIssueEntity where holdIssueType = :type"),
    @NamedQuery(name = "HoldIssueEntity.getByOrganization",
    query = "select ISSUE from HoldIssueEntity ISSUE where ISSUE.organizationId = :organizationId")
})
public class HoldIssueEntity extends MetaEntity implements AttributeOwner<HoldIssueAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORG_ID")
    private String organizationId;

    @Column(name = "HOLD_ISSUE_TYPE", nullable = false)
    private String holdIssueType;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<HoldIssueAttributeEntity> attributes;

    @Column(name = "HOLD_ISSUE_STATE", nullable = false)
    private String holdIssueState;

    public HoldIssueEntity() {
    }

    public HoldIssueEntity(Issue dto, EntityManager em) {
        super(dto);
        this.setId(dto.getId());
        this.setHoldIssueType(dto.getTypeKey());
        this.fromDto(dto, em);
    }

    public void fromDto(Issue dto, EntityManager em) {
        setName(dto.getName());
        setHoldIssueState(dto.getStateKey());
        setOrganizationId(dto.getOrganizationId());
        if (dto.getDescr() != null) {
            this.setDescrFormatted(dto.getDescr().getFormatted());
            this.setDescrPlain(dto.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        
        // dynamic attributes
        if (this.getAttributes() == null) {
            this.setAttributes(new HashSet<HoldIssueAttributeEntity>());
        }
        Set<String> idSet = new HashSet<String>(dto.getAttributes().size());
        for (Attribute attr : dto.getAttributes()) {
            if (attr.getId() != null) {
                idSet.add(attr.getId());
            }
        }
        for (HoldIssueAttributeEntity attEntity : new ArrayList<HoldIssueAttributeEntity> (this.getAttributes())) {
            if (!idSet.contains(attEntity.getId())) {
                em.remove(attEntity);
                this.getAttributes().remove(attEntity);
            }
        }
        for (Attribute att : dto.getAttributes()) {
            HoldIssueAttributeEntity attEntity = new HoldIssueAttributeEntity(att);
            attEntity.setOwner(this);
            this.getAttributes().add(attEntity);
        }
    }

    @Override
    public void setAttributes(Set<HoldIssueAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<HoldIssueAttributeEntity> getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getHoldIssueType() {
        return holdIssueType;
    }

    public void setHoldIssueType(String issueType) {
        this.holdIssueType = issueType;
    }

    public String getHoldIssueState() {
        return holdIssueState;
    }

    public void setHoldIssueState(String issueState) {
        this.holdIssueState = issueState;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String plain) {
        this.descrPlain = plain;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String formatted) {
        this.descrFormatted = formatted;
    }

    public IssueInfo toDto() {
        IssueInfo info = new IssueInfo();
        info.setId(getId());
        info.setName(getName());
        info.setTypeKey(getHoldIssueType());
        info.setStateKey(getHoldIssueState());
        info.setOrganizationId(getOrganizationId());
        if (descrPlain != null) {
            info.setDescr(new RichTextInfo(descrPlain, descrFormatted));
        }
        info.setMeta(super.toDTO());
        for (HoldIssueAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }
        return info;
    }
}

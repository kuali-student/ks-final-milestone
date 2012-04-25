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

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.infc.Issue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a description of what this class does - andy don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Entity
@Table(name = "KSEN_HOLD_ISSUE")
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<HoldIssueAttributeEntity> attributes;

    @Column(name = "HOLD_ISSUE_STATE", nullable = false)
    private String holdIssueState;

    public HoldIssueEntity() {
    }

    public HoldIssueEntity(Issue issue) {
        super(issue);
        this.setId(issue.getId());
        this.setHoldIssueType(issue.getTypeKey());
        this.fromDto(issue);
    }

    public void fromDto(Issue issue) {
        setName(issue.getName());
        setOrganizationId(issue.getOrganizationId());
        setHoldIssueState(issue.getStateKey());
        setHoldIssueType(issue.getTypeKey());
        if (issue.getDescr() != null) {
            setDescrFormatted(issue.getDescr().getFormatted());
            setDescrPlain(issue.getDescr().getPlain());
        }
        this.setAttributes(new ArrayList<HoldIssueAttributeEntity>());
        for (Attribute att : issue.getAttributes()) {
            HoldIssueAttributeEntity attEntity = new HoldIssueAttributeEntity(att);
            this.getAttributes().add(attEntity);
        }
    }

    @Override
    public void setAttributes(List<HoldIssueAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<HoldIssueAttributeEntity> getAttributes() {
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

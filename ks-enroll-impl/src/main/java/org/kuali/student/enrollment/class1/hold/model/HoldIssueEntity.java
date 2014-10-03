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

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.infc.HoldIssue;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class HoldIssueEntity
        extends MetaEntity
        implements AttributeOwner<HoldIssueAttributeEntity> {

    @Column(name = "NAME")
    private String name;
    @Column(name = "ORG_ID")
    private String organizationId;
    @Column(name = "HOLD_ISSUE_TYPE", nullable = false)
    private String holdIssueType;
    @Column(name = "HOLD_ISSUE_STATE", nullable = false)
    private String holdIssueState;

    @Column(name = "HOLD_CD")
    private String holdCode;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FIRST_APPLIED_DT")
    private Date firstAppliedDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_APPLIED_DT")
    private Date lastAppliedDate;

    @Column(name = "HOLD_ISSUE_TERM_BASED_IND", nullable = false)
    private Integer isHoldIssueTermBased;
    @Column(name = "FIRST_APP_TERM_ID")
    private String firstApplicationTermId;
    @Column(name = "LAST_APP_TERM_ID")
    private String lastApplicationTermId;

    @Column(name = "MAINT_HIST_OF_APP_OF_HOLD_IND", nullable = false)
    private Integer maintainHistoryOfApplicationOfHold;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<HoldIssueAttributeEntity> attributes = new HashSet<HoldIssueAttributeEntity>();

    public HoldIssueEntity() {
    }

    public HoldIssueEntity(HoldIssue dto) {
        super(dto);
        this.setId(dto.getId());
        this.setHoldIssueType(dto.getTypeKey());
        this.fromDto(dto);
    }

    public void fromDto(HoldIssue dto) {
        super.fromDTO(dto);
        
        setName(dto.getName());
        setHoldIssueState(dto.getStateKey());
        setOrganizationId(dto.getOrganizationId());
        setHoldCode(dto.getHoldCode());
        if (dto.getDescr() != null) {
            this.setDescrFormatted(dto.getDescr().getFormatted());
            this.setDescrPlain(dto.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }

        setFirstAppliedDate(dto.getFirstAppliedDate());
        setLastAppliedDate(dto.getLastAppliedDate());
        if((dto.getIsHoldIssueTermBased()!=null) && (dto.getIsHoldIssueTermBased())) {
            setIsHoldIssueTermBased(1);
        } else {
            setIsHoldIssueTermBased(0);
        }
        setFirstApplicationTermId(dto.getFirstApplicationTermId());
        setLastApplicationTermId(dto.getLastApplicationTermId());
        if((dto.getMaintainHistoryOfApplicationOfHold()!=null) && (dto.getMaintainHistoryOfApplicationOfHold())) {
            setMaintainHistoryOfApplicationOfHold(1);
        } else {
            setMaintainHistoryOfApplicationOfHold(0);
        }

        // dynamic attributes
        this.getAttributes().clear();
        for (Attribute att : dto.getAttributes()) {
            HoldIssueAttributeEntity attEntity = new HoldIssueAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    @Override
    public void setAttributes(Set<HoldIssueAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
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

    public String getHoldCode() {
        return holdCode;
    }

    public void setHoldCode(String holdCode) {
        this.holdCode = holdCode;
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

    public Date getFirstAppliedDate() {
        return firstAppliedDate;
    }

    public void setFirstAppliedDate(Date firstAppliedDate) {
        this.firstAppliedDate = firstAppliedDate;
    }

    public Date getLastAppliedDate() {
        return lastAppliedDate;
    }

    public void setLastAppliedDate(Date lastAppliedDate) {
        this.lastAppliedDate = lastAppliedDate;
    }

    public Integer getIsHoldIssueTermBased() {
        return isHoldIssueTermBased;
    }

    public void setIsHoldIssueTermBased(Integer isHoldIssueTermBased) {
        this.isHoldIssueTermBased = isHoldIssueTermBased;
    }

    public String getFirstApplicationTermId() {
        return firstApplicationTermId;
    }

    public void setFirstApplicationTermId(String firstApplicationTermId) {
        this.firstApplicationTermId = firstApplicationTermId;
    }

    public String getLastApplicationTermId() {
        return lastApplicationTermId;
    }

    public void setLastApplicationTermId(String lastApplicationTermId) {
        this.lastApplicationTermId = lastApplicationTermId;
    }

    public Integer getMaintainHistoryOfApplicationOfHold() {
        return maintainHistoryOfApplicationOfHold;
    }

    public void setMaintainHistoryOfApplicationOfHold(Integer maintainHistoryOfApplicationOfHold) {
        this.maintainHistoryOfApplicationOfHold = maintainHistoryOfApplicationOfHold;
    }

    public HoldIssueInfo toDto() {
        HoldIssueInfo info = new HoldIssueInfo();
        info.setId(getId());
        info.setName(getName());
        info.setTypeKey(getHoldIssueType());
        info.setStateKey(getHoldIssueState());
        info.setOrganizationId(getOrganizationId());
        info.setHoldCode(getHoldCode());
        if (descrPlain != null) {
            info.setDescr(new RichTextInfo(descrPlain, descrFormatted));
        }

        info.setFirstAppliedDate(getFirstAppliedDate());
        info.setLastAppliedDate(getLastAppliedDate());
        info.setIsHoldIssueTermBased(getIsHoldIssueTermBased() == 1 ? true : false);
        info.setFirstApplicationTermId(getFirstApplicationTermId());
        info.setLastApplicationTermId(getLastApplicationTermId());

        info.setMaintainHistoryOfApplicationOfHold(getMaintainHistoryOfApplicationOfHold() == 1 ? true : false);
        info.setMeta(super.toDTO());
        for (HoldIssueAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }
        return info;
    }
}

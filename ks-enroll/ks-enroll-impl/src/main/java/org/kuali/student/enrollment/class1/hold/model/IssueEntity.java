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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.infc.Issue;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.model.AttributeEntity;
import org.kuali.student.r2.common.model.StateEntity;

/**
 * This is a description of what this class does - andy don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */

@Entity
@Table(name = "KSEN_ISSUE")
public class IssueEntity extends MetaEntity implements AttributeOwner<AttributeEntity> {

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "ORG_ID")
    private String organizationId;
    
    /*
     * TODO Use common type entity
    @ManyToOne
    @JoinColumn(name = "ISSUE_TYPE_ID")
    private TypeEntity<AttributeEntity> issueType;
    */
    @Column(name = "TYPE_ID")
    private String typeId;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "RT_DESCR_ID")
    private HoldRichTextEntity descr;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttributeEntity> attributes;
    
    @ManyToOne
    @JoinColumn(name = "STATE_ID")
    private StateEntity issueState;
    
    public IssueEntity() {
    }

    public IssueEntity(Issue issue) {
        super(issue);
        setName(issue.getName());
        setOrganizationId(issue.getOrganizationId());
        setTypeId(issue.getTypeKey());
        setIssueState(new StateEntity());
        issueState.setId(issue.getStateKey());
        
        setDescr(new HoldRichTextEntity(issue.getDescr()));
    }
    
    @Override
    public void setAttributes(List<AttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<AttributeEntity> getAttributes() {
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public StateEntity getIssueState() {
        return issueState;
    }

    public void setIssueState(StateEntity issueState) {
        this.issueState = issueState;
    }

    public HoldRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(HoldRichTextEntity descr) {
        this.descr = descr;
    }
    
    public IssueInfo toDto() {
        IssueInfo info = new IssueInfo();
        
        info.setId(getId());
        info.setName(getName());
        info.setTypeKey(getTypeId());
        info.setStateKey(getIssueState().getId());
        info.setOrganizationId(getOrganizationId());
        info.setMeta(super.toDTO());
        
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);
        
        info.setDescr(getDescr().toDto());
        
        return info;
    }
    
}

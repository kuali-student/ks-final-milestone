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
package org.kuali.student.r2.core.class1.atp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.infc.AtpMilestoneRelation;

@Entity
@Table(name = "KSEN_ATPMSTONE_RELTN")
public class AtpMilestoneRelationEntity extends MetaEntity implements AttributeOwner<AtpMilestoneRelationAttributeEntity> {

    @ManyToOne
    @JoinColumn(name="ATP_ID")
    private AtpEntity atp;
    
    @ManyToOne
    @JoinColumn(name="MSTONE_ID")
    private MilestoneEntity milestone;
    
    @ManyToOne
    @JoinColumn(name="AM_RELTN_TYPE_ID")
    private AtpMilestoneRelationTypeEntity atpMilestoneRelationType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "ATP_STATE_ID")
    private AtpStateEntity atpState;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AtpMilestoneRelationAttributeEntity> attributes;
    
    public AtpMilestoneRelationEntity() {
    }
    
    public AtpMilestoneRelationEntity(AtpMilestoneRelation relation) {
        this.setId(relation.getId());
        this.setEffectiveDate(relation.getEffectiveDate());
        this.setExpirationDate(relation.getExpirationDate());
        
        if(relation.getAttributes() != null) {
            this.setAttributes(new ArrayList<AtpMilestoneRelationAttributeEntity>(relation.getAttributes().size()));
            for(Attribute att : relation.getAttributes()) {
                this.getAttributes().add(new AtpMilestoneRelationAttributeEntity(att));
            }
        }
    }
    
    public AtpEntity getAtp() {
        return atp;
    }

    public void setAtp(AtpEntity atp) {
        this.atp = atp;
    }

    public MilestoneEntity getMilestone() {
        return milestone;
    }

    public void setMilestone(MilestoneEntity milestone) {
        this.milestone = milestone;
    }

    public AtpMilestoneRelationTypeEntity getAtpMilestoneRelationType() {
        return atpMilestoneRelationType;
    }

    public void setAtpMilestoneRelationType(AtpMilestoneRelationTypeEntity atpMilestoneRelationType) {
        this.atpMilestoneRelationType = atpMilestoneRelationType;
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

    public AtpStateEntity getAtpState() {
        return atpState;
    }

    public void setAtpState(AtpStateEntity atpState) {
        this.atpState = atpState;
    }
    
    @Override
    public void setAttributes(List<AtpMilestoneRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<AtpMilestoneRelationAttributeEntity> getAttributes() {
        return attributes;
    }
    
    public AtpMilestoneRelationInfo toDto() {
        AtpMilestoneRelationInfo dto = new AtpMilestoneRelationInfo();
        
        dto.setId(getId());
        dto.setAtpKey(getAtp().getId());
        dto.setMilestoneKey(getMilestone().getId());
        dto.setEffectiveDate(getEffectiveDate());
        dto.setExpirationDate(getExpirationDate());
        dto.setStateKey(getAtpState().getId());
        dto.setTypeKey(getAtpMilestoneRelationType().getId());
        dto.setMeta(super.toDTO());
        
        if(getAttributes() != null) {
            List<AttributeInfo> atts = new ArrayList<AttributeInfo>(getAttributes().size());
            for (AtpMilestoneRelationAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }
            
            dto.setAttributes(atts);
        }
        
        
        return dto;
    }

}

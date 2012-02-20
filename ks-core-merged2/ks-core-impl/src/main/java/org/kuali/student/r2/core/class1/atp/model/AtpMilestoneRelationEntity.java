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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.atp.infc.AtpMilestoneRelation;

@Entity
@Table(name = "KSEN_ATPMSTONE_RELTN")
public class AtpMilestoneRelationEntity extends MetaEntity implements
        AttributeOwner<AtpMilestoneRelationAttributeEntity> {

    @ManyToOne
    @JoinColumn(name = "ATP_ID")
    private AtpEntity atp;

    @ManyToOne
    @JoinColumn(name = "MSTONE_ID")
    private MilestoneEntity milestone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<AtpMilestoneRelationAttributeEntity> attributes;

    public AtpMilestoneRelationEntity() {}

    public AtpMilestoneRelationEntity(AtpMilestoneRelation relation) {
        super(relation);
        this.setId(relation.getId());
        if (relation.getAttributes() != null) {
            this.setAttributes(new ArrayList<AtpMilestoneRelationAttributeEntity>(relation.getAttributes().size()));
            for (Attribute att : relation.getAttributes()) {
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

    @Override
    public void setAttributes(List<AtpMilestoneRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<AtpMilestoneRelationAttributeEntity> getAttributes() {
        return attributes;
    }

}

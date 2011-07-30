package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATPMSTONE_RELTN_ATTR")
public class AtpMilestoneRelationAttributeEntity extends BaseAttributeEntity<AtpMilestoneRelationEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private AtpMilestoneRelationEntity owner;

    public AtpMilestoneRelationAttributeEntity() {
    }
    
    public AtpMilestoneRelationAttributeEntity(Attribute attribute) {
        super(attribute);
    }
    
    public AtpMilestoneRelationAttributeEntity(String name, String value) {
        super(name, value);
    }

    @Override
    public void setOwner(AtpMilestoneRelationEntity owner) {
        this.owner = owner;
    }

    @Override
    public AtpMilestoneRelationEntity getOwner() {
        return owner;
    }
}

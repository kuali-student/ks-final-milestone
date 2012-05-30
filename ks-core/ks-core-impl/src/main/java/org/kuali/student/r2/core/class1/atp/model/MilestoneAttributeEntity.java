package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_MSTONE_ATTR")
public class MilestoneAttributeEntity extends BaseAttributeEntityNew<MilestoneEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private MilestoneEntity owner;

    public MilestoneAttributeEntity() {}

    public MilestoneAttributeEntity(String key, String value) {
        super(key, value);
    }

    public MilestoneAttributeEntity(Attribute att, MilestoneEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(MilestoneEntity owner) {
        this.owner = owner;

    }

    @Override
    public MilestoneEntity getOwner() {
        return owner;
    }
}

package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATP_ATTR")
public class AtpAttributeEntity extends BaseAttributeEntityNew<AtpEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private AtpEntity owner;

    public AtpAttributeEntity() {}

    public AtpAttributeEntity(String key, String value) {
        super(key, value);
    }

    public AtpAttributeEntity(Attribute att, AtpEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(AtpEntity owner) {
        this.owner = owner;

    }

    @Override
    public AtpEntity getOwner() {
        return owner;
    }
}

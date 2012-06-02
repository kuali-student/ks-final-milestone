package org.kuali.student.r2.core.class1.process.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_CHECK_ATTR")
public class CheckAttributeEntity extends BaseAttributeEntityNew<CheckEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private CheckEntity owner;

    public CheckAttributeEntity() {}

    public CheckAttributeEntity(String key, String value) {
        super(key, value);
    }

    public CheckAttributeEntity(Attribute att, CheckEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(CheckEntity owner) {
        this.owner = owner;
    }

    @Override
    public CheckEntity getOwner() {
        return owner;
    }
}

package org.kuali.student.r2.core.class1.process.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_CHECK_TYPE_ATTR")
public class CheckTypeAttributeEntity extends BaseAttributeEntity<CheckTypeEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private CheckTypeEntity owner;

    public CheckTypeAttributeEntity() {
    }

    public CheckTypeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public CheckTypeAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(CheckTypeEntity owner) {
        this.owner = owner;
    }

    @Override
    public CheckTypeEntity getOwner() {
        return owner;
    }
}
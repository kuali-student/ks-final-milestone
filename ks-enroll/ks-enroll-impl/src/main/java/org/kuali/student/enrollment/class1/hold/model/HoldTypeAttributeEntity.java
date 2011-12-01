package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_HOLD_TYPE_ATTR")
public class HoldTypeAttributeEntity extends BaseAttributeEntity<HoldTypeEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private HoldTypeEntity owner;

    public HoldTypeAttributeEntity () {
    }
    
    public HoldTypeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public HoldTypeAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(HoldTypeEntity owner) {
        this.owner = owner;
    }

    @Override
    public HoldTypeEntity getOwner() {
        return owner;
    }
}

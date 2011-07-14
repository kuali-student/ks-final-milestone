package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_HOLD_ATTR")
public class HoldAttributeEntity extends BaseAttributeEntity<HoldEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private HoldEntity owner;

    public HoldAttributeEntity () {
    }
    
    public HoldAttributeEntity(String key, String value) {
        super(key, value);
    }

    public HoldAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(HoldEntity owner) {
        this.owner = owner;
    }

    @Override
    public HoldEntity getOwner() {
        return owner;
    }
}

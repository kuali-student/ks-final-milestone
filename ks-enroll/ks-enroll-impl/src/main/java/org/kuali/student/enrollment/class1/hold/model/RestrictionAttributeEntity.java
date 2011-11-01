package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_RESTRICTION_ATTR")
public class RestrictionAttributeEntity extends BaseAttributeEntity<RestrictionEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private RestrictionEntity owner;

    public RestrictionAttributeEntity () {
    }
    
    public RestrictionAttributeEntity(String key, String value) {
        super(key, value);
    }

    public RestrictionAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(RestrictionEntity owner) {
        this.owner = owner;
    }

    @Override
    public RestrictionEntity getOwner() {
        return owner;
    }
}

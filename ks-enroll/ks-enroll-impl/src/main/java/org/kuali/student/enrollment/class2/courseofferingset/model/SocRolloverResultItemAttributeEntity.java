package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_SOC_ROR_ITEM_ATTR")
public class SocRolloverResultItemAttributeEntity extends BaseAttributeEntity<SocRolloverResultItemEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private SocRolloverResultItemEntity owner;

    public SocRolloverResultItemAttributeEntity() {}

    public SocRolloverResultItemAttributeEntity(String key, String value) {
        super(key, value);
    }

    public SocRolloverResultItemAttributeEntity(Attribute att, SocRolloverResultItemEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(SocRolloverResultItemEntity owner) {
        this.owner = owner;

    }

    @Override
    public SocRolloverResultItemEntity getOwner() {
        return owner;
    }
}

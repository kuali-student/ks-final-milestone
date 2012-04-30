package org.kuali.student.enrollment.class2.courseofferingset.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_SOC_ROR_ATTR")
public class SocRolloverResultAttributeEntity extends BaseAttributeEntityNew<SocRolloverResultEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private SocRolloverResultEntity owner;

    public SocRolloverResultAttributeEntity() {}

    public SocRolloverResultAttributeEntity(String key, String value) {
        super(key, value);
    }

    public SocRolloverResultAttributeEntity(Attribute att, SocRolloverResultEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(SocRolloverResultEntity owner) {
        this.owner = owner;

    }

    @Override
    public SocRolloverResultEntity getOwner() {
        return owner;
    }
}

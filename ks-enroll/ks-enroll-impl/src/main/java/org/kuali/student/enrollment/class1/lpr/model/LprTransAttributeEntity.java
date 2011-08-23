package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

public class LprTransAttributeEntity extends BaseAttributeEntity<LprTransactionEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LprTransactionEntity owner;

    public LprTransAttributeEntity() {}

    public LprTransAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LprTransactionEntity owner) {
        this.owner = owner;

    }

    @Override
    public LprTransactionEntity getOwner() {
        return owner;
    }

}

package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_TRANS_ITEM_ATTR")
public class LprTransItemAttributeEntity extends BaseAttributeEntity<LprTransactionItemEntity> {
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LprTransactionItemEntity owner;

    public LprTransItemAttributeEntity(Attribute att) {
        super(att);

    }

    @Override
    public LprTransactionItemEntity getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(LprTransactionItemEntity owner) {
        this.owner = owner;

    }

}

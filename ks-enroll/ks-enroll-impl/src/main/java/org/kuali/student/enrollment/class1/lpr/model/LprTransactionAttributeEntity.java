package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;

@Entity
@Table(name = "KSLP_LPR_TRANS_ATTR")
public class LprTransactionAttributeEntity extends BaseAttributeEntity<LprTransactionItemEntity> {
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LprTransactionItemEntity owner;

    @Override
    public LprTransactionItemEntity getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(LprTransactionItemEntity owner) {
        this.owner = owner;

    }

}

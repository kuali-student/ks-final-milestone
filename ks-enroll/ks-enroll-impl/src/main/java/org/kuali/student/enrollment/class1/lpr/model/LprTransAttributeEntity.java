package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_TRANS_ATTR")
public class LprTransAttributeEntity extends BaseAttributeEntity<LprTransactionEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LprTransactionEntity owner;

    public LprTransAttributeEntity() {}

    public LprTransAttributeEntity(Attribute att) {
        super(att);
    }

    public LprTransAttributeEntity(String key, String value) {
        super(key, value);
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

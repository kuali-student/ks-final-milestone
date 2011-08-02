package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;

@Entity
@Table(name = "KSLP_LPR_TRANS_ATTR")
public class LprTransactionAttributeEntity extends BaseAttributeEntity<LprTransactionEntity> {
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LprTransactionEntity owner;
 

    @Override
    public LprTransactionEntity getOwner() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public void setOwner(LprTransactionEntity owner) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        
    }

}

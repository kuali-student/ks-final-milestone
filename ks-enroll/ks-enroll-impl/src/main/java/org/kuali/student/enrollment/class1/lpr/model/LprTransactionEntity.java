package org.kuali.student.enrollment.class1.lpr.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSLP_LPR_TRANS")
public class LprTransactionEntity  extends MetaEntity implements AttributeOwner<LprTransactionAttributeEntity>  {

    @Override
    public void setAttributes(List<LprTransactionAttributeEntity> attributes) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public List<LprTransactionAttributeEntity> getAttributes() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}

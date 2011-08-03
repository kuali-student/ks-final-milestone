package org.kuali.student.enrollment.class1.lpr.model;

import java.util.List;

import javax.persistence.Column;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

public class LprTransactionEntity  extends MetaEntity implements AttributeOwner<LprTransactionAttributeEntity>  {

    @Column(name = "REQ_PERSON_ID")
    private String requestingPersonId;

    @Column(name = "REQ_PERSON_ID")
    private List<LprTransactionItemEntity> lprTransactionItems;

    
    @Override
    public void setAttributes(List<LprTransactionAttributeEntity> attributes) {
        
    }

    @Override
    public List<LprTransactionAttributeEntity> getAttributes() {
        return null;
    }

}

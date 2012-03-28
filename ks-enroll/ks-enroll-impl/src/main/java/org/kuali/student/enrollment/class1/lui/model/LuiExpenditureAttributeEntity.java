package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_EXPEND_ATTR")
public class LuiExpenditureAttributeEntity extends BaseAttributeEntity<LuiExpenditureEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LuiExpenditureEntity owner;

    public LuiExpenditureAttributeEntity () {
    }
    
    public LuiExpenditureAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuiExpenditureAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LuiExpenditureEntity owner) {
        this.owner = owner;
    }

    @Override
    public LuiExpenditureEntity getOwner() {
        return owner;
    }
}
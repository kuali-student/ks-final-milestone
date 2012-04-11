package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_REVENUE_ATTR")
public class LuiRevenueAttributeEntity extends BaseAttributeEntity<LuiRevenueEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LuiRevenueEntity owner;

    public LuiRevenueAttributeEntity () {
    }
    
    public LuiRevenueAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuiRevenueAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LuiRevenueEntity owner) {
        this.owner = owner;
    }

    @Override
    public LuiRevenueEntity getOwner() {
        return owner;
    }
}

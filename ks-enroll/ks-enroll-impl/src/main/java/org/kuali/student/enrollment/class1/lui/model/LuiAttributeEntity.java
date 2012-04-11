package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_ATTR")
public class LuiAttributeEntity extends BaseAttributeEntityNew<LuiEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private LuiEntity owner;

    public LuiAttributeEntity () {
    }
    
    public LuiAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuiAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LuiEntity owner) {
        this.owner = owner;
    }

    @Override
    public LuiEntity getOwner() {
        return owner;
    }
}

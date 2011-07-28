package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_TYPE_ATTR")
public class LuiTypeAttributeEntity extends BaseAttributeEntity<LuiTypeEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LuiTypeEntity owner;

    public LuiTypeAttributeEntity () {
    }
    
    public LuiTypeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuiTypeAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LuiTypeEntity owner) {
        this.owner = owner;
    }

    @Override
    public LuiTypeEntity getOwner() {
        return owner;
    }
}


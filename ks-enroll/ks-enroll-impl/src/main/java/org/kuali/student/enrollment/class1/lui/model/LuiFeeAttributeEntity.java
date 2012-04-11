package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_FEE_ATTR")
public class LuiFeeAttributeEntity extends BaseAttributeEntity<LuiFeeEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LuiFeeEntity owner;

    public LuiFeeAttributeEntity () {
    }
    
    public LuiFeeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuiFeeAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LuiFeeEntity owner) {
        this.owner = owner;
    }

    @Override
    public LuiFeeEntity getOwner() {
        return owner;
    }
}
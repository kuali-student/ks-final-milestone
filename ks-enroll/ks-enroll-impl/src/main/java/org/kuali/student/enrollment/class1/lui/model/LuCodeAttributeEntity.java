package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;


@Entity
@Table(name = "KSEN_LUI_LUCD_ATTR")
public class LuCodeAttributeEntity extends BaseAttributeEntity<LuCodeEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LuCodeEntity owner;

    public LuCodeAttributeEntity () {
    }
    
    public LuCodeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuCodeAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LuCodeEntity owner) {
        this.owner = owner;
    }

    @Override
    public LuCodeEntity getOwner() {
        return owner;
    }

}

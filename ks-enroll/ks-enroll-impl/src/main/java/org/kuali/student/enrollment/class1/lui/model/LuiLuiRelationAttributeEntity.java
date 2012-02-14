package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUILUI_RELTN_ATTR")
public class LuiLuiRelationAttributeEntity extends BaseAttributeEntity<LuiLuiRelationEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LuiLuiRelationEntity owner;

    public LuiLuiRelationAttributeEntity(){}
    
    public LuiLuiRelationAttributeEntity(Attribute att) {
        super(att);
    }
    
    public LuiLuiRelationAttributeEntity(String key, String value) {
        super(key, value);
    }

    @Override
    public void setOwner(LuiLuiRelationEntity owner) {
        this.owner = owner;
        
    }

    @Override
    public LuiLuiRelationEntity getOwner() {
        return owner;
    }
}

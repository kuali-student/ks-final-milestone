package org.kuali.student.r2.core.class1.type.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_TYPE_ATTR")
public class TypeAttributeEntity extends BaseAttributeEntityNew<TypeEntity> {

   
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private TypeEntity owner;

    public TypeAttributeEntity() {
    }

    public TypeAttributeEntity(String key, String value) {
        super(key, value);
    }
    
    public TypeAttributeEntity(Attribute att, TypeEntity owner) {
        super(att);
        setOwner(owner);
    }
    
    @Override
    public void setOwner(TypeEntity owner) {
        this.owner = owner;
    }

    @Override
    public TypeEntity getOwner() {
        return null;
    }

}

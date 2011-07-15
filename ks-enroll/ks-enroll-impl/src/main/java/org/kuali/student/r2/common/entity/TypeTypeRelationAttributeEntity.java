package org.kuali.student.r2.common.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATPATP_RELTN_ATTR")
public class TypeTypeRelationAttributeEntity extends BaseAttributeEntity<TypeTypeRelationEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private TypeTypeRelationEntity owner;

    public TypeTypeRelationAttributeEntity(){}
    
    public TypeTypeRelationAttributeEntity(Attribute att) {
        super(att);
    }
    
    public TypeTypeRelationAttributeEntity(String key, String value) {
        super(key, value);
    }

    @Override
    public void setOwner(TypeTypeRelationEntity owner) {
        this.owner = owner;
    }

    @Override
    public TypeTypeRelationEntity getOwner() {
        return owner;
    }
}

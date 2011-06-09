package org.kuali.student.r2.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATPATP_RELTN_ATTR")
public class TypeTypeRelationAttributeEntity extends BaseAttributeEntity{
    public TypeTypeRelationAttributeEntity(){}
    
    public TypeTypeRelationAttributeEntity(Attribute att) {
        super(att);
    }
    
    public TypeTypeRelationAttributeEntity(String key, String value) {
        super(key, value);
    }
}

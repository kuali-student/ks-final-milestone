package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATPATP_RELTN_ATTR")
public class AtpAtpRelationAttributeEntity extends BaseAttributeEntity{
    public AtpAtpRelationAttributeEntity(){}
    
    public AtpAtpRelationAttributeEntity(Attribute att) {
        super(att);
    }
    
    public AtpAtpRelationAttributeEntity(String key, String value) {
        super(key, value);
    }
}

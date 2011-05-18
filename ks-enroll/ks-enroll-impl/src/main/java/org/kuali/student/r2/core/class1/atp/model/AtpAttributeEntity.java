package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATP_ATTR")
public class AtpAttributeEntity extends BaseAttributeEntity {
    public AtpAttributeEntity () {
    }
    
    public AtpAttributeEntity(String key, String value) {
        super(key, value);
    }

    public AtpAttributeEntity(Attribute att) {
        super(att);
    }
}

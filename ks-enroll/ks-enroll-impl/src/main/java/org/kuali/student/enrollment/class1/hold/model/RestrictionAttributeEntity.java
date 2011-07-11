package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_RESTRICTION_ATTR")
public class RestrictionAttributeEntity extends BaseAttributeEntity<RestrictionEntity> {
    public RestrictionAttributeEntity () {
    }
    
    public RestrictionAttributeEntity(String key, String value) {
        super(key, value);
    }

    public RestrictionAttributeEntity(Attribute att) {
        super(att);
    }
}

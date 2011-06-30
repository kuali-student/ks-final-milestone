package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_ATTR")
public class LuiAttributeEntity extends BaseAttributeEntity{
    public LuiAttributeEntity () {
    }
    
    public LuiAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuiAttributeEntity(Attribute att) {
        super(att);
    }
}

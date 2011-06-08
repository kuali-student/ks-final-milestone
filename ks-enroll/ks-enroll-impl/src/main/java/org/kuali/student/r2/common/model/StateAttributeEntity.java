package org.kuali.student.r2.common.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_COMM_ATTR")
public class StateAttributeEntity extends BaseAttributeEntity{
	public StateAttributeEntity(){}
	
	public StateAttributeEntity(String key, String value) {
        super(key, value);
    }
	
	public StateAttributeEntity(Attribute att) {
        super(att);
    }
}

package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_FEE_ATTR")
public class LuiFeeAttributeEntity extends BaseAttributeEntity<LuiFeeEntity> {

	public LuiFeeAttributeEntity() {
		super();
	}

	public LuiFeeAttributeEntity(Attribute att, LuiFeeEntity owner) {
		super(att, owner);
	}
    
   
}
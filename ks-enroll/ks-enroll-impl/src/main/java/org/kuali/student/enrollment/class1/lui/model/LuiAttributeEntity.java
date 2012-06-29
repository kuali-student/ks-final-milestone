package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LUI_ATTR")
public class LuiAttributeEntity extends BaseAttributeEntity<LuiEntity> {

	public LuiAttributeEntity() {
		super();
	}

	public LuiAttributeEntity(Attribute att, LuiEntity owner) {
		super(att, owner);
	}
    
    
}

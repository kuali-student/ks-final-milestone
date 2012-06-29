package org.kuali.student.enrollment.class1.hold.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_HOLD_ATTR")
public class HoldAttributeEntity extends BaseAttributeEntity<HoldEntity> {

	public HoldAttributeEntity() {
		super();
	}

	public HoldAttributeEntity(Attribute att, HoldEntity owner) {
		super(att, owner);
	}
    
   
}

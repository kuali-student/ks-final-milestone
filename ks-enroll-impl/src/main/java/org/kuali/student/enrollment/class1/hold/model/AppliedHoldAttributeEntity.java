package org.kuali.student.enrollment.class1.hold.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_HOLD_ATTR")
public class AppliedHoldAttributeEntity extends BaseAttributeEntity<AppliedHoldEntity> {

	public AppliedHoldAttributeEntity() {
		super();
	}

	public AppliedHoldAttributeEntity(Attribute att, AppliedHoldEntity owner) {
		super(att, owner);
	}
    
   
}

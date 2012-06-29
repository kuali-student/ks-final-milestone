package org.kuali.student.r2.core.class1.atp.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_MSTONE_ATTR")
public class MilestoneAttributeEntity extends BaseAttributeEntity<MilestoneEntity> {

	public MilestoneAttributeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MilestoneAttributeEntity(Attribute att, MilestoneEntity owner) {
		super(att, owner);
		// TODO Auto-generated constructor stub
	}

   
}

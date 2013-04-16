package org.kuali.student.r2.core.process.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_PROCESS_INSTRN_ATTR")
public class InstructionAttributeEntity extends BaseAttributeEntity<InstructionEntity> {

	public InstructionAttributeEntity() {
		super();
	}

	public InstructionAttributeEntity(Attribute att, InstructionEntity owner) {
		super(att, owner);
	}

    
}

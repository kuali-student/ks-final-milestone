package org.kuali.student.r2.core.process.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_PROCESS_ATTR")
public class ProcessAttributeEntity extends BaseAttributeEntity<ProcessEntity> {

	public ProcessAttributeEntity() {
		super();
	}

	public ProcessAttributeEntity(Attribute att, ProcessEntity owner) {
		super(att, owner);
	}


}

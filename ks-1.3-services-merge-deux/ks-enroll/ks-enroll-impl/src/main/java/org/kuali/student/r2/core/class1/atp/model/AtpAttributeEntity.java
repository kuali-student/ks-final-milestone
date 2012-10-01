package org.kuali.student.r2.core.class1.atp.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_ATP_ATTR")
public class AtpAttributeEntity extends BaseAttributeEntity<AtpEntity> {

	public AtpAttributeEntity() {
		super();
	}

	public AtpAttributeEntity(Attribute att, AtpEntity owner) {
		super(att, owner);
	}

    
}

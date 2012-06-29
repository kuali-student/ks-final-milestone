package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_STATE_LIFECYCLE_ATTR")
public class LifecycleAttributeEntity extends BaseAttributeEntity<LifecycleEntity> {

	public LifecycleAttributeEntity() {
		super();
	}

	public LifecycleAttributeEntity(Attribute att, LifecycleEntity owner) {
		super(att, owner);
	}
    
    
}

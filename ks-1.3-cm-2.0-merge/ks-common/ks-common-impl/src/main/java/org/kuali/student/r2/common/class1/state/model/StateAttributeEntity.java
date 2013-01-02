package org.kuali.student.r2.common.class1.state.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_STATE_ATTR")
public class StateAttributeEntity extends BaseAttributeEntity<StateEntity> {
    
	public StateAttributeEntity() {
		super();
    }
    
    public StateAttributeEntity(Attribute att, StateEntity owner) {
		super(att, owner);
    }


}

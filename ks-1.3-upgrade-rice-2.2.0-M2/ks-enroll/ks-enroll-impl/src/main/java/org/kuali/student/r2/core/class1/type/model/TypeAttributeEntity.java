package org.kuali.student.r2.core.class1.type.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_TYPE_ATTR")
public class TypeAttributeEntity extends BaseAttributeEntity<TypeEntity> {

	public TypeAttributeEntity() {
		super();
	}

	public TypeAttributeEntity(Attribute att, TypeEntity owner) {
		super(att, owner);
	}

   
   
}

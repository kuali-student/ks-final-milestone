package org.kuali.student.r2.core.class1.type.model;


import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_ATPATP_RELTN_ATTR")
public class TypeTypeRelationAttributeEntity extends BaseAttributeEntity<TypeTypeRelationEntity> {

	public TypeTypeRelationAttributeEntity() {
		super();
	}

	public TypeTypeRelationAttributeEntity(Attribute att,
			TypeTypeRelationEntity owner) {
		super(att, owner);
	}
    
   
}

package org.kuali.student.r2.core.class1.atp.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_ATPATP_RELTN_ATTR")
public class AtpAtpRelationAttributeEntity extends BaseAttributeEntity<AtpAtpRelationEntity> {

	public AtpAtpRelationAttributeEntity() {
		super();
	}

	public AtpAtpRelationAttributeEntity(Attribute att,
			AtpAtpRelationEntity owner) {
		super(att, owner);
	}

    
}

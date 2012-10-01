package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;


@Entity
@Table(name = "KSEN_LUI_LU_CD_ATTR")
public class LuCodeAttributeEntity extends BaseAttributeEntity<LuCodeEntity> {

	public LuCodeAttributeEntity() {
		super();
	}

	public LuCodeAttributeEntity(Attribute att, LuCodeEntity owner) {
		super(att, owner);
	}
    
  
}

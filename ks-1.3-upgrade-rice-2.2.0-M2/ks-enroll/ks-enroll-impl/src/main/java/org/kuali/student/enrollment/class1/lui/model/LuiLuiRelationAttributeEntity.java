package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LUILUI_RELTN_ATTR")
public class LuiLuiRelationAttributeEntity extends BaseAttributeEntity<LuiLuiRelationEntity> {

	public LuiLuiRelationAttributeEntity() {
		super();
	}

	public LuiLuiRelationAttributeEntity(Attribute att,
			LuiLuiRelationEntity owner) {
		super(att, owner);
	}
    
   
}

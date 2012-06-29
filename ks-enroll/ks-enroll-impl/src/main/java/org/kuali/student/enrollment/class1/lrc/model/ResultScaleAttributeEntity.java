package org.kuali.student.enrollment.class1.lrc.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRC_RES_SCALE_ATTR")
public class ResultScaleAttributeEntity extends BaseAttributeEntity<ResultScaleEntity> {

	public ResultScaleAttributeEntity() {
		super();
	}

	public ResultScaleAttributeEntity(Attribute att, ResultScaleEntity owner) {
		super(att, owner);
	}

   
}

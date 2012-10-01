package org.kuali.student.enrollment.class1.lrc.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRC_RESULT_VALUE_ATTR")
public class ResultValueAttributeEntity  extends BaseAttributeEntity<ResultValueEntity> {

	public ResultValueAttributeEntity() {
		super();
	}

	public ResultValueAttributeEntity(Attribute att, ResultValueEntity owner) {
		super(att, owner);
	}

   
}

package org.kuali.student.enrollment.class1.roster.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.class1.roster.model.LprRosterEntity;
import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_ROSTER_ATTR")
public class LprRosterAttributeEntity extends BaseAttributeEntity<LprRosterEntity> {

	public LprRosterAttributeEntity() {
		super();
	}

	public LprRosterAttributeEntity(Attribute att, LprRosterEntity owner) {
		super(att, owner);
	}

}

package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_HOLD_ISSUE_ATTR")
public class HoldIssueAttributeEntity extends BaseAttributeEntity<HoldIssueEntity> {

	public HoldIssueAttributeEntity() {
		super();
	}

	public HoldIssueAttributeEntity(Attribute att, HoldIssueEntity owner) {
		super(att, owner);
	}
    
    
}

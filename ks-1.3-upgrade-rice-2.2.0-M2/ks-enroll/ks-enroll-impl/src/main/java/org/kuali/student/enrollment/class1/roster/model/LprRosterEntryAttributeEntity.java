package org.kuali.student.enrollment.class1.roster.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LPR_ROSTER_ENTRY_ATTR")
public class LprRosterEntryAttributeEntity extends BaseAttributeEntity<LprRosterEntryEntity> {

	public LprRosterEntryAttributeEntity() {
		super();
	}

	public LprRosterEntryAttributeEntity(Attribute att,
			LprRosterEntryEntity owner) {
		super(att, owner);
	}

   
}

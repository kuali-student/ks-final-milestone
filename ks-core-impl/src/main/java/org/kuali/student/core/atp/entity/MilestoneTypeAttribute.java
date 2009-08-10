package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_MLSTN_TYPE_ATTR")
public class MilestoneTypeAttribute extends Attribute<MilestoneType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private MilestoneType owner;

	@Override
	public MilestoneType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(MilestoneType owner) {
		this.owner = owner;
	}
}

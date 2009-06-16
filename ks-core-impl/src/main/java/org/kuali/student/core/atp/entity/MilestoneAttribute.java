package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_MLSTN_ATTR")
public class MilestoneAttribute extends Attribute<Milestone> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Milestone owner;

	@Override
	public Milestone getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Milestone owner) {
		this.owner = owner;
	}
}

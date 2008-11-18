package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;

@Entity
public class MilestoneAttribute extends Attribute{
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Milestone owner;

	public Milestone getOwner() {
		return owner;
	}

	public void setOwner(Milestone owner) {
		this.owner = owner;
	}
}

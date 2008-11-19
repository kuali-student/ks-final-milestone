package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;

@Entity
public class AtpAttribute extends Attribute<Atp, AtpAttributeDef> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Atp owner;

	@Override
	public Atp getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Atp owner) {
		this.owner = owner;
	}
}

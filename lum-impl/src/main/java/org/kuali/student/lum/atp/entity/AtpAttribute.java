package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;

@Entity
public class AtpAttribute extends Attribute {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Atp owner;

	public Atp getOwner() {
		return owner;
	}

	public void setOwner(Atp owner) {
		this.owner = owner;
	}
}

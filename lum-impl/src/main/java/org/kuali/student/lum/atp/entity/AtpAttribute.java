package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.AttributeEntity;

@Entity
public class AtpAttribute extends AttributeEntity {
	@ManyToOne
	private Atp owner;

	public Atp getOwner() {
		return owner;
	}

	public void setOwner(Atp owner) {
		this.owner = owner;
	}
}

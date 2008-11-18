package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;
@Entity
public class AtpTypeAttribute extends Attribute {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private AtpType owner;

	public AtpType getOwner() {
		return owner;
	}

	public void setOwner(AtpType owner) {
		this.owner = owner;
	}
}

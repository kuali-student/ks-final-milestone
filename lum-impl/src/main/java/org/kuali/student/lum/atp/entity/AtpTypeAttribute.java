package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;
import org.kuali.student.core.entity.TypeAttributeDef;
@Entity
public class AtpTypeAttribute extends Attribute<AtpType, TypeAttributeDef> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private AtpType owner;

	@Override
	public AtpType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(AtpType owner) {
		this.owner = owner;
	}
}

package org.kuali.student.core.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TypeAttribute extends Attribute<Type, AttributeDef> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Type owner;

	@Override
	public Type getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Type owner) {
		this.owner = owner;
	}
}

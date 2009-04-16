package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LR_TYPE_ATTR")
public class LrTypeAttribute extends Attribute<LrType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LrType owner;

	@Override
	public LrType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LrType owner) {
		this.owner = owner;
	}
}

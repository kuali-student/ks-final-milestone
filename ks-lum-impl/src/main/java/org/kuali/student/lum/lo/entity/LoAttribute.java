package org.kuali.student.lum.lo.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LO_ATTR")
public class LoAttribute extends Attribute<Lo> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Lo owner;

	@Override
	public Lo getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Lo owner) {
		this.owner = owner;
	}
}

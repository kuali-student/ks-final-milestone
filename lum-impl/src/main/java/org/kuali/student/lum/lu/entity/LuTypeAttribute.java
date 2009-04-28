package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LU_TYPE_ATTR")
public class LuTypeAttribute extends Attribute<LuType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuType owner;

	@Override
	public LuType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuType owner) {
		this.owner = owner;
	}

}

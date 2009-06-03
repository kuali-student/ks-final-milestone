package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LU_CODE_ATTR")
public class LuCodeAttribute extends Attribute<LuCode> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuCode owner;

	@Override
	public LuCode getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuCode owner) {
		this.owner = owner;
	}
}

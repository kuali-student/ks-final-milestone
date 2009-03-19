package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LULU_RELTN_TYPE_ATTR")
public class LuLuRelationTypeAttribute extends Attribute<LuLuRelationType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuLuRelationType owner;

	@Override
	public LuLuRelationType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuLuRelationType owner) {
		this.owner = owner;
	}

}

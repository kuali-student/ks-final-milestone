package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LUILUI_RELTN_ATTR")
public class LuiLuiRelationAttribute extends Attribute<LuiLuiRelation> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuiLuiRelation owner;

	@Override
	public LuiLuiRelation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuiLuiRelation owner) {
		this.owner = owner;
	}

}

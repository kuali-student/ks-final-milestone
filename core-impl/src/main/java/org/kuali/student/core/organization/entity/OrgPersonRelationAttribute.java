package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;
@Entity
public class OrgPersonRelationAttribute  extends Attribute<OrgPersonRelation, OrgPersonRelationAttributeDef>{

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgPersonRelation owner;

	@Override
	public OrgPersonRelation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgPersonRelation owner) {
		this.owner = owner;
	}

}

package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;
@Entity
public class OrgPositionRestrictionAttribute  extends Attribute<OrgPositionRestriction, OrgPositionRestrictionAttributeDef>{

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgPositionRestriction owner;

	@Override
	public OrgPositionRestriction getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgPositionRestriction owner) {
		this.owner = owner;
	}

}

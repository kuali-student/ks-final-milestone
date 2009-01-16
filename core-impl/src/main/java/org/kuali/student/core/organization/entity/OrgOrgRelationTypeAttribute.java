package org.kuali.student.core.organization.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;
import org.kuali.student.core.entity.TypeAttributeDef;

public class OrgOrgRelationTypeAttribute extends Attribute<OrgOrgRelationType, TypeAttributeDef>{

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgOrgRelationType owner;

	@Override
	public OrgOrgRelationType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgOrgRelationType owner) {
		this.owner = owner;
	}

}

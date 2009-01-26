package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;
@Entity
@Table(name="KS_ORG_POS_RESTR_ATTR_T")
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

package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name="KS_ORG_ORG_RELATION_ATTR_T")
public class OrgOrgRelationAttribute extends Attribute<OrgOrgRelation, OrgOrgRelationAttributeDef>{

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgOrgRelation owner;
	
	@Override
	public OrgOrgRelation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgOrgRelation owner) {
		this.owner=owner;
	}

}

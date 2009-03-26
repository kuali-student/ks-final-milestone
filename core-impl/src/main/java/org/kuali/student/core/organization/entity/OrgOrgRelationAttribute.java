package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSOR_ORG_ORG_RELTN_ATTR")
public class OrgOrgRelationAttribute extends Attribute<OrgOrgRelation> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgOrgRelation owner;

	@Override
	public OrgOrgRelation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgOrgRelation owner) {
		this.owner = owner;
	}
}

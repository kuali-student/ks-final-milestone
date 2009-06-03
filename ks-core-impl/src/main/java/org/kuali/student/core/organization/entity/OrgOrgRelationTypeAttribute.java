package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSOR_ORG_ORG_RELTN_TYPE_ATTR")
public class OrgOrgRelationTypeAttribute extends Attribute<OrgOrgRelationType> {
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

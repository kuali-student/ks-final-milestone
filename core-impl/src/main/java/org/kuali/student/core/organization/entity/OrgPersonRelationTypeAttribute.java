package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSOR_ORG_PERS_RELTN_TYPE_ATTR")
public class OrgPersonRelationTypeAttribute extends Attribute<OrgPersonRelationType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgPersonRelationType owner;

	@Override
	public OrgPersonRelationType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgPersonRelationType owner) {
		this.owner = owner;
	}
}

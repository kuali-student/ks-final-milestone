package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSOR_ORG_TYPE_ATTR")
public class OrgTypeAttribute extends Attribute<OrgType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgType owner;

	@Override
	public OrgType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgType owner) {
		this.owner = owner;
	}
}

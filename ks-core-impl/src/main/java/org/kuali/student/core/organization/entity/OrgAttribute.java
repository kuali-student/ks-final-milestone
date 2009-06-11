package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSOR_ORG_ATTR")
public class OrgAttribute extends Attribute<Org> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Org owner;

	@Override
	public Org getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Org owner) {
		this.owner = owner;
	}
}

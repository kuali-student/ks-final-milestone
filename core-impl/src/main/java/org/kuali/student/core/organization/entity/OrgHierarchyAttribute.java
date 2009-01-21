package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;

@Entity
public class OrgHierarchyAttribute extends Attribute<OrgHierarchy, OrgHierarchyAttributeDef>{

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgHierarchy owner;
	
	@Override
	public OrgHierarchy getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgHierarchy owner) {
		this.owner=owner;
	}
}

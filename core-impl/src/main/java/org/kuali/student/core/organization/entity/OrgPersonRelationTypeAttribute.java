package org.kuali.student.core.organization.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;
import org.kuali.student.core.entity.TypeAttributeDef;

@Entity
@Table(name="KS_ORG_PERS_REL_TYPE_ATTR_T")
@AttributeOverride(name="id", column=@Column(name="OPRTA_KEY"))
public class OrgPersonRelationTypeAttribute extends Attribute<OrgPersonRelationType, TypeAttributeDef>{

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

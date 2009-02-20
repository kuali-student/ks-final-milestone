package org.kuali.student.core.organization.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KS_ORG_PERS_REL_TYPE_ATTR_T")
@AttributeOverride(name = "id", column = @Column(name = "OPRTA_KEY"))
public class OrgPersonRelationTypeAttribute extends
		Attribute<OrgPersonRelationType> {

}

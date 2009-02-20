package org.kuali.student.core.organization.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KS_ORG_TYPE_T")
public class OrgType extends Type<OrgTypeAttribute> {
	@ManyToMany
	@JoinTable(name = "KS_ORG_TYPE_ORG_PERS_RL_TYPE_T", joinColumns = @JoinColumn(name = "ORG_TYPE_KEY", referencedColumnName = "TYPE_KEY"), inverseJoinColumns = @JoinColumn(name = "ORG_PERSON_RELATION_TYPE_KEY", referencedColumnName = "OPRT_KEY"))
	private List<OrgPersonRelationType> orgPersonRelationTypes;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
	private List<OrgTypeAttribute> attributes;

	public void setOrgPersonRelationTypes(
			List<OrgPersonRelationType> orgPersonRelationTypes) {
		this.orgPersonRelationTypes = orgPersonRelationTypes;
	}

	public List<OrgPersonRelationType> getOrgPersonRelationTypes() {
		return orgPersonRelationTypes;
	}

	public List<OrgTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<OrgTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<OrgTypeAttribute> attributes) {
		this.attributes = attributes;
	}

}

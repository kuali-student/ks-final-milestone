package org.kuali.student.core.organization.entity;

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
@Table(name = "KSOR_ORG_TYPE")
public class OrgType extends Type<OrgTypeAttribute> {
	@ManyToMany
	@JoinTable(name = "KSOR_ORG_TYPE_JN_ORG_PERRL_TYP", 
	        joinColumns = @JoinColumn(name = "ORG_TYPE_ID", referencedColumnName = "TYPE_KEY"), 
	        inverseJoinColumns = @JoinColumn(name = "ORG_PERS_RELTN_TYPE_ID", referencedColumnName = "TYPE_KEY"))
	private List<OrgPersonRelationType> orgPersonRelationTypes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgTypeAttribute> attributes;
	
	@ManyToMany(mappedBy="organizationTypes")
	private List<OrgHierarchy> orgHierarchies;

	public void setOrgPersonRelationTypes(
			List<OrgPersonRelationType> orgPersonRelationTypes) {
		this.orgPersonRelationTypes = orgPersonRelationTypes;
	}

	public List<OrgPersonRelationType> getOrgPersonRelationTypes() {
		return orgPersonRelationTypes;
	}

	public List<OrgTypeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<OrgTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public List<OrgHierarchy> getOrgHierarchies() {
		return orgHierarchies;
	}

	public void setOrgHierarchies(List<OrgHierarchy> orgHierarchies) {
		this.orgHierarchies = orgHierarchies;
	}

}

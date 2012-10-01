/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.organization.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.Type;;

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

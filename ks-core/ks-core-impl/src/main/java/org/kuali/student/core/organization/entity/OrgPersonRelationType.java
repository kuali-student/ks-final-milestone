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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSOR_ORG_PERS_RELTN_TYPE")
@NamedQueries( {
		@NamedQuery(name = "OrgPersonRelationType.getOrgPersonRelationTypesForOrgType", query = "SELECT oprt FROM OrgPersonRelationType oprt JOIN oprt.organizationTypes ot WHERE ot.id = :orgTypeKey"),
		@NamedQuery(name = "OrgPersonRelationType.getPersonIdsForOrgByRelationType", query = "SELECT DISTINCT opr.personId FROM OrgPersonRelation opr JOIN opr.org o JOIN opr.orgPersonRelationType t " +
				                                                                            "WHERE o.id = :orgId AND " +
				                                                                            "t.id = :orgPersonRelationTypeKey AND" +
				                                                                            "(opr.expirationDate IS NULL OR opr.expirationDate>=CURRENT_TIMESTAMP)"),
		@NamedQuery(name = "OrgPersonRelationType.hasOrgPersonRelation", query = "SELECT COUNT(oprt) FROM OrgPersonRelationType oprt JOIN oprt.orgPersonRelations relations JOIN oprt.organizations orgs WHERE relations.personId = :personId AND orgs.id = :orgId AND oprt.id = :orgPersonRelationTypeKey") })
public class OrgPersonRelationType extends Type<OrgPersonRelationTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgPersonRelationTypeAttribute> attributes;

	@ManyToMany(mappedBy = "orgPersonRelationTypes")
	private List<Org> organizations;

	@ManyToMany(mappedBy = "orgPersonRelationTypes")
	private List<OrgType> organizationTypes;

	@OneToMany(mappedBy = "orgPersonRelationType")
	private List<OrgPersonRelation> orgPersonRelations;

	@Override
	public List<OrgPersonRelationTypeAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgPersonRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}

/*	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

	public List<Org> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Org> organizations) {
		this.organizations = organizations;
	}

	public void setOrgPersonRelations(List<OrgPersonRelation> orgPersonRelations) {
		this.orgPersonRelations = orgPersonRelations;
	}

	public List<OrgPersonRelation> getOrgPersonRelations() {
		return orgPersonRelations;
	}

	public void setOrganizationTypes(List<OrgType> organizationTypes) {
		this.organizationTypes = organizationTypes;
	}

	public List<OrgType> getOrganizationTypes() {
		return organizationTypes;
	}
}

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

package org.kuali.student.r1.core.organization.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;;

@Entity
@Table(name = "KSOR_ORG_ORG_RELTN_TYPE")
@NamedQueries( {
		@NamedQuery(name = "OrgOrgRelationType.getOrgOrgRelationTypesForOrgHierarchy", query = "SELECT oort FROM OrgOrgRelationType oort WHERE oort.orgHierarchy.id = :orgHierarchy"),
		@NamedQuery(name = "OrgOrgRelationType.getOrgOrgRelationTypesForOrgType", query = "SELECT DISTINCT oort FROM OrgOrgRelation oor JOIN oor.org org JOIN oor.type oort WHERE org.type.id = :orgTypeKey") })
public class OrgOrgRelationType extends Type<OrgOrgRelationTypeAttribute> {

	@Column(name = "REV_NAME")
	private String revName;

	@Column(name = "REV_DESCR")
	private String revDesc;

	@ManyToOne
	@JoinColumn(name = "ORG_HIRCHY")
	private OrgHierarchy orgHierarchy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgOrgRelationTypeAttribute> attributes;

	@Override
	public List<OrgOrgRelationTypeAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgOrgRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getRevName() {
		return revName;
	}

	public void setRevName(String revName) {
		this.revName = revName;
	}

	public String getRevDesc() {
		return revDesc;
	}

	public void setRevDesc(String revDesc) {
		this.revDesc = revDesc;
	}

	public OrgHierarchy getOrgHierarchy() {
		return orgHierarchy;
	}

	public void setOrgHierarchy(OrgHierarchy orgHierarchy) {
		this.orgHierarchy = orgHierarchy;
	}
}

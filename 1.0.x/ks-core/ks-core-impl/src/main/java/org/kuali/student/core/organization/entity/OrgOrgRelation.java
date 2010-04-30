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

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSOR_ORG_ORG_RELTN")
@NamedQueries( {
		@NamedQuery(name = "OrgOrgRelation.getAllDescendants", query = "SELECT oor.relatedOrg.id FROM OrgOrgRelation oor "
				+ " WHERE oor.org.id = :orgId "
				+ "   AND oor.type.orgHierarchy.id = :orgHierarchy"
				+ "   AND oor.org.effectiveDate<=CURRENT_TIMESTAMP  "
				+ "   AND (oor.org.expirationDate IS NULL OR oor.org.expirationDate>=CURRENT_TIMESTAMP)"),
		@NamedQuery(name = "OrgOrgRelation.getAncestors", query = "SELECT oor.org.id FROM OrgOrgRelation oor "
				+ " WHERE oor.relatedOrg.id = :orgId "
                + "   AND oor.type.orgHierarchy.id = :orgHierarchy"
                + "   AND oor.relatedOrg.effectiveDate<=CURRENT_TIMESTAMP  "
                + "   AND (oor.relatedOrg.expirationDate IS NULL OR oor.relatedOrg.expirationDate>=CURRENT_TIMESTAMP)"),
		@NamedQuery(name = "OrgOrgRelation.getOrgOrgRelationsByIdList", query = "SELECT oor FROM OrgOrgRelation oor WHERE oor.id IN (:idList)"),
		@NamedQuery(name = "OrgOrgRelation.OrgOrgRelation", query = "SELECT oor FROM OrgOrgRelation oor WHERE oor.org.id = :orgId"),
		@NamedQuery(name = "OrgOrgRelation.getOrgOrgRelationsByRelatedOrg", query = "SELECT oor FROM OrgOrgRelation oor WHERE oor.relatedOrg.id = :relatedOrgId"),
		@NamedQuery(name = "OrgOrgRelation.getOrgTreeInfo", query = "SELECT NEW org.kuali.student.core.organization.dto.OrgTreeInfo(oor.relatedOrg.id, oor.org.id, oor.relatedOrg.longName) "
				+ "   FROM OrgOrgRelation oor "
				+ "  WHERE oor.org.id = :orgId "
				+ "    AND oor.type.orgHierarchy.id = :orgHierarchyId " 
                + "   AND oor.relatedOrg.effectiveDate<=CURRENT_TIMESTAMP  "
                + "   AND (oor.relatedOrg.expirationDate IS NULL OR oor.relatedOrg.expirationDate>=CURRENT_TIMESTAMP)"),
		@NamedQuery(name = "OrgOrgRelation.hasOrgOrgRelation", query = "SELECT COUNT(oor) "
				+ "  FROM OrgOrgRelation oor "
				+ " WHERE oor.org.id = :orgId "
				+ "   AND oor.relatedOrg.id = :comparisonOrgId "
				+ "   AND oor.type.id = :orgOrgRelationTypeKey") })
public class OrgOrgRelation extends MetaEntity implements
		AttributeOwner<OrgOrgRelationAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "ORG")
	private Org org;

	@ManyToOne
	@JoinColumn(name = "RELATED_ORG")
	private Org relatedOrg;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgOrgRelationAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "TYPE")
	private OrgOrgRelationType type;

	@Column(name = "ST")
	private String state;

	/**
	 * AutoGenerate the Id
	 */
	@Override
	public void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Org getRelatedOrg() {
		return relatedOrg;
	}

	public void setRelatedOrg(Org relatedOrg) {
		this.relatedOrg = relatedOrg;
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

	@Override
	public List<OrgOrgRelationAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgOrgRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public OrgOrgRelationType getType() {
		return type;
	}

	public void setType(OrgOrgRelationType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

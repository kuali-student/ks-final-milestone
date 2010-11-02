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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSOR_ORG_PERS_RELTN", uniqueConstraints={@UniqueConstraint(columnNames={"ORG_PERS_RELTN_TYPE","ORG","PERS_ID"})})
@NamedQueries( {
		@NamedQuery(name = "OrgPersonRelation.getAllOrgPersonRelationsByOrg", query = "SELECT distinct opr FROM OrgPersonRelation opr WHERE opr.org.id = :orgId"),
		@NamedQuery(name = "OrgPersonRelation.getAllOrgPersonRelationsByPerson", query = "SELECT distinct opr FROM OrgPersonRelation opr WHERE opr.personId = :personId"),
		@NamedQuery(name = "OrgPersonRelation.getOrgPersonRelationsByIdList", query = "SELECT  opr FROM OrgPersonRelation opr WHERE opr.id IN (:idList)"),
		@NamedQuery(name = "OrgPersonRelation.getOrgPersonRelationsByPerson", query = "SELECT  opr FROM OrgPersonRelation opr WHERE opr.personId = :personId AND opr.org.id = :orgId"),
		@NamedQuery(name = "OrgPersonRelation.getOrgMembershipCount", query = "SELECT COUNT(opr) FROM OrgPersonRelation opr WHERE opr.org.id= :orgId")})
public class OrgPersonRelation extends MetaEntity implements
		AttributeOwner<OrgPersonRelationAttribute> {

	@ManyToOne
	@JoinColumn(name = "ORG")
	private Org org;

	// Foreign Key from external Service
	@Column(name = "PERS_ID")
	private String personId;

	@ManyToOne
	@JoinColumn(name = "ORG_PERS_RELTN_TYPE")
	private OrgPersonRelationType orgPersonRelationType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgPersonRelationAttribute> attributes;

	@Column(name = "ST")
	private String state;

	@Override
	public List<OrgPersonRelationAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgPersonRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
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

	public OrgPersonRelationType getType() {
		return orgPersonRelationType;
	}

	public void setType(OrgPersonRelationType type) {
		this.orgPersonRelationType = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

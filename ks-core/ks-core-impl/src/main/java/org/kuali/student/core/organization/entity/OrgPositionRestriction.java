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
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.KSEntityConstants;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.TimeAmount;

@Entity
@Table(name = "KSOR_ORG_POS_RESTR", 
        uniqueConstraints={@UniqueConstraint(columnNames={"ORG","PERS_RELTN_TYPE"})})
@NamedQueries( {
		@NamedQuery(name = "OrgPositionRestriction.findOrgPositionRestrictions", query = "SELECT opr FROM OrgPositionRestriction opr WHERE opr.org.id = :orgId"),
		@NamedQuery(name = "OrgPositionRestriction.validatePositionRestriction", query = "SELECT COUNT(opr) "
				+ "   FROM OrgPositionRestriction opr "
				+ "  WHERE opr.org.id = :orgId "
				+ "    AND opr.personRelationType.id = :orgPersonRelationTypeKey"),
		@NamedQuery(name = "OrgPositionRestriction.getPositionRestrictionByOrgAndPersonRelationTypeKey", query = "SELECT opr FROM OrgPositionRestriction opr JOIN opr.personRelationType oprt WHERE opr.org.id = :orgId AND oprt.id = :orgPersonRelationTypeKey") })
public class OrgPositionRestriction extends MetaEntity implements
		AttributeOwner<OrgPositionRestrictionAttribute> {

	@ManyToOne
	@JoinColumn(name = "ORG")
	private Org org;

	@ManyToOne
	@JoinColumn(name = "PERS_RELTN_TYPE")
	private OrgPersonRelationType personRelationType;

	@Column(name = "DESCR", length = KSEntityConstants.LONG_TEXT_LENGTH)
	private String descr;

	@Column(name = "TTL")
	private String title;

	@Embedded
	private TimeAmount stdDuration;

	@Column(name = "MIN_NUM_RELTN")
	private Integer minNumRelations;

	@Column(name = "MAX_NUM_RELTN")
	private String maxNumRelations;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgPositionRestrictionAttribute> attributes;

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TimeAmount getStdDuration() {
		return stdDuration;
	}

	public void setStdDuration(TimeAmount stdDuration) {
		this.stdDuration = stdDuration;
	}

	public Integer getMinNumRelations() {
		return minNumRelations;
	}

	public void setMinNumRelations(Integer minNumRelations) {
		this.minNumRelations = minNumRelations;
	}

	public String getMaxNumRelations() {
		return maxNumRelations;
	}

	public void setMaxNumRelations(String maxNumRelations) {
		this.maxNumRelations = maxNumRelations;
	}

	@Override
	public List<OrgPositionRestrictionAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgPositionRestrictionAttribute> attributes) {
		this.attributes = attributes;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public OrgPersonRelationType getPersonRelationType() {
		return personRelationType;
	}

	public void setPersonRelationType(OrgPersonRelationType personRelationType) {
		this.personRelationType = personRelationType;
	}

}

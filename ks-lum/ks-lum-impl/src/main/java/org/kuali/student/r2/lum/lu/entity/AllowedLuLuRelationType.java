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

package org.kuali.student.r2.lum.lu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r1.common.entity.MetaEntity;

@Entity
@Table(name = "KSLU_LU_LU_ALOW_RELTN_TYPE")
@NamedQueries( { @NamedQuery(name = "AllowedLuLuRelationType.getAllowedTypesByLuTypes", query = "SELECT relType.relationType.id FROM AllowedLuLuRelationType relType WHERE luType.id = :luTypeId and relatedLuType.id = :relatedLuTypeId") })
public class AllowedLuLuRelationType extends MetaEntity {

	@ManyToOne
	@JoinColumn(name = "LU_LU_RELTN_TYPE_ID")
	private LuLuRelationType relationType;
	
	@ManyToOne
	@JoinColumn(name = "LU_TYPE_ID")
	private LuType luType;
	
	@ManyToOne
	@JoinColumn(name = "LU_REL_TYPE_ID")
	private LuType relatedLuType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	public LuLuRelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(LuLuRelationType relationType) {
		this.relationType = relationType;
	}

	public LuType getLuType() {
		return luType;
	}

	public void setLuType(LuType luType) {
		this.luType = luType;
	}

	public LuType getRelatedLuType() {
		return relatedLuType;
	}

	public void setRelatedLuType(LuType relatedLuType) {
		this.relatedLuType = relatedLuType;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
}

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

package org.kuali.student.lum.lo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLO_LO_ALLOWED_RELTN_TYPE")
public class AllowedLoLoRelationType extends MetaEntity {

	@Column(name = "LOLO_RELTN_TYPE_ID")
	private String relationTypeId;
	
	@Column(name = "LO_TYPE_ID")
	private String loTypeId;
	
	@Column(name = "LO_REL_TYPE_ID")
	private String relatedLoTypeId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	public String getRelationTypeId() {
		return relationTypeId;
	}

	public void setRelationTypeId(String relationTypeId) {
		this.relationTypeId = relationTypeId;
	}

	public String getLoTypeId() {
		return loTypeId;
	}

	public void setLoTypeId(String loTypeId) {
		this.loTypeId = loTypeId;
	}

	public String getRelatedLoTypeId() {
		return relatedLoTypeId;
	}

	public void setRelatedLoTypeId(String relatedLoTypeId) {
		this.relatedLoTypeId = relatedLoTypeId;
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

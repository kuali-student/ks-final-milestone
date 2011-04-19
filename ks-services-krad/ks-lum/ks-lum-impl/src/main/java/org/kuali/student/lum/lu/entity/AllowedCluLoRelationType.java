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

/*
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.entity;

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

import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_CLU_LO_ALOW_RELTN_TYPE")
@NamedQueries( { @NamedQuery(name = "AllowedCluLoRealtionType.getAllowedTypesByLuType", query = "SELECT relType.cluLoRelationType.id FROM AllowedCluLoRelationType relType WHERE luType.id = :luTypeId") })
public class AllowedCluLoRelationType extends MetaEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "CLULO_RELTN_TYPE_ID")
	private CluLoRelationType cluLoRelationType;

	@ManyToOne
	@JoinColumn(name = "LU_TYPE_ID")
	private LuType luType;

	@Column(name = "LO_TYPE_ID")
	private String loTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	public CluLoRelationType getCluLoRelationType() {
		return cluLoRelationType;
	}

	public void setCluLoRelationType(CluLoRelationType cluLoRelationType) {
		this.cluLoRelationType = cluLoRelationType;
	}

	public LuType getLuType() {
		return luType;
	}

	public void setLuType(LuType luType) {
		this.luType = luType;
	}

	public String getLoTypeId() {
		return loTypeId;
	}

	public void setLoTypeId(String loTypeId) {
		this.loTypeId = loTypeId;
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
	
}
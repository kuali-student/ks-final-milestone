/*
 * Copyright 2009 The Kuali Foundation
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_RSLT_COMP_USG_ALOW_TYPE")
@NamedQueries( { @NamedQuery(name = "AllowedResultComponentUsageType.getAllowedComponentsByUsageType", query = "SELECT atype.resultComponentId FROM AllowedResultComponentUsageType atype WHERE resultUsageType.id = :resultUsageType") })
public class AllowedResultComponentUsageType extends MetaEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "RSLT_USG_TYPE_ID")
	private ResultUsageType resultUsageType;

	@Column(name = "RSLT_COMP_ID")
	private String resultComponentId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@Id
	@Column(name = "ID")
	private String id;

    @Override
    protected void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }
	
	public String getResultComponentId() {
		return resultComponentId;
	}

	public void setResultComponentId(String resultComponentId) {
		this.resultComponentId = resultComponentId;
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

	public ResultUsageType getResultUsageType() {
		return resultUsageType;
	}

	public void setResultUsageType(ResultUsageType resultUsageType) {
		this.resultUsageType = resultUsageType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
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

package org.kuali.student.lum.lu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

/**
 * This is a description of what this class does - hjohnson don't forget to fill
 * this in.
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 * 
 */

@Entity
@Table(name = "KSLU_CLU_ACCRED")
public class CluAccreditation extends MetaEntity implements
		AttributeOwner<CluAccreditationAttribute> {
	
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "ORG_ID")
	private String orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluAccreditationAttribute> attributes;

	@PrePersist
	public void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	public List<CluAccreditationAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CluAccreditationAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	}
}

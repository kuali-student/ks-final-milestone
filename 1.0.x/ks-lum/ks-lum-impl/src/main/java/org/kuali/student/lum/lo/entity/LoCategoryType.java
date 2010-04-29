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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

/**
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSLU_LO_CATEGORY_TYPE")
public class LoCategoryType implements AttributeOwner<LoCategoryTypeAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCR")
	private String descr;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LoCategoryTypeAttribute> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
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

	/* (non-Javadoc)
	 * @see org.kuali.student.core.entity.AttributeOwner#getAttributes()
	 */
	@Override
	public List<LoCategoryTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LoCategoryTypeAttribute>(0);
		}
		return attributes;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.core.entity.AttributeOwner#setAttributes(java.util.List)
	 */
	@Override
	public void setAttributes(List<LoCategoryTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}

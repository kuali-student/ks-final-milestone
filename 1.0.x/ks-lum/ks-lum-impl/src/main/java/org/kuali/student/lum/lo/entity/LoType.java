/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_LO_TYPE")
public class LoType implements AttributeOwner<LoTypeAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCR")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LoTypeAttribute> attributes;

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
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

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

//	/**
//	 * @param desc the desc to set
//	 */
//	public void setDesc(RichText desc) {
//		this.desc = desc;
//	}
//
//	/**
//	 * @return the desc
//	 */
//	public RichText getDesc() {
//		return desc;
//	}

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

	/* (non-Javadoc)
	 * @see org.kuali.student.core.entity.AttributeOwner#getAttributes()
	 */
	@Override
	public List<LoTypeAttribute> getAttributes() {
		if (null == attributes) {
			attributes = new ArrayList<LoTypeAttribute>(0);
		}
		return attributes;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.core.entity.AttributeOwner#setAttributes(java.util.List)
	 */
	@Override
	public void setAttributes(List<LoTypeAttribute> attributes) {
		this.attributes = attributes;
	}


}

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

package org.kuali.student.lum.lo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
import org.kuali.student.core.entity.RichText;

/**
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSLU_LO")
@NamedQueries( {
	@NamedQuery(name = "Lo.findLosByIdList", query = "SELECT l FROM Lo l WHERE l.id IN (:idList)"),
	@NamedQuery(name = "Lo.getLoCategoriesForLo", query = "SELECT c FROM LoCategory c, Lo l WHERE c.loHierarchy = l.loHierarchy AND l.id = :loId"),
	@NamedQuery(name = "Lo.getLosByLoCategory", query = "SELECT l FROM Lo l, LoCategory c WHERE c.loHierarchy = l.loHierarchy AND c.id = :loCategoryId")
})
public class Lo extends MetaEntity implements AttributeOwner<LoAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private
	String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private RichText desc;
	
	@ManyToOne
	@JoinColumn(name = "LOHIRCHY_ID")
	private LoHierarchy loHierarchy;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "KSLU_LO_LO_HIRCHY_RELTN",
				joinColumns = { @JoinColumn(name = "PARENT_LO_ID")},
				inverseJoinColumns = { @JoinColumn(name = "CHILD_LO_ID")})
	private List<Lo> childLos;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LoAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "LOTYPE_ID")
	private LoType loType;

	@Column(name = "ST")
	private String state;
	
	@Override
	protected void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

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

	public RichText getDesc() {
		return desc;
	}

	public void setDesc(RichText desc) {
		this.desc = desc;
	}

	/**
	 * @param loHierarchy the loHierarchy to set
	 */
	public void setLoHierarchy(LoHierarchy loHierarchy) {
		this.loHierarchy = loHierarchy;
	}

	/**
	 * @return the loHierarchy
	 */
	public LoHierarchy getLoHierarchy() {
		return loHierarchy;
	}

	/**
	 * @param childLos the childLos to set
	 */
	public void setChildLos(List<Lo> childLos) {
		this.childLos = childLos;
	}

	/**
	 * @return the childLos
	 */
	public List<Lo> getChildLos() {
		return childLos;
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
	public List<LoAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LoAttribute>(0);
		}
		return attributes;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.core.entity.AttributeOwner#setAttributes(java.util.List)
	 */
	@Override
	public void setAttributes(List<LoAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @param loType the loType to set
	 */
	public void setLoType(LoType loType) {
		this.loType = loType;
	}

	/**
	 * @return the loType
	 */
	public LoType getLoType() {
		return loType;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
}

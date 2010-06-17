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

/**
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSLU_LO")
@NamedQueries( {
	@NamedQuery(name = "Lo.getAllowedLoLoRelationTypes", query = "SELECT relType.relationTypeId FROM AllowedLoLoRelationType relType WHERE relType.loTypeId = :loTypeKey AND relType.relatedLoTypeId = :relatedLoTypeKey"),	
	@NamedQuery(name = "Lo.getRelatedLosByLoId", query = "SELECT rel.relatedLo FROM LoLoRelation rel WHERE rel.lo.id = :loId AND rel.loLoRelationType.id = :loLoRelationTypeId"),
	@NamedQuery(name = "Lo.getLosByRelatedLoId", query = "SELECT rel.lo FROM LoLoRelation rel WHERE rel.relatedLo.id = :relatedLoId AND rel.loLoRelationType.id = :loLoRelationTypeId"),
	@NamedQuery(name = "Lo.getLoCategories", query = "SELECT c FROM LoCategory c WHERE c.loRepository.id = :repositoryId"),
	@NamedQuery(name = "Lo.findLosByIdList", query = "SELECT l FROM Lo l WHERE l.id IN (:idList)"),
	@NamedQuery(name = "Lo.getLoCategoriesForLo", query = "SELECT c FROM LoCategory c, IN (c.los) lo WHERE lo.id = :loId"),
	@NamedQuery(name = "Lo.getLosByLoCategory", query = "SELECT l FROM Lo l, IN (l.categories) category WHERE category.id = :loCategoryId"),
	@NamedQuery(name = "Lo.getLosByRepository", query = "SELECT l FROM Lo l WHERE l.loRepository.id = :loRepositoryId"),
	@NamedQuery(name = "Lo.getLoLoRelationsByLoId", query = "SELECT llRel FROM LoLoRelation llRel WHERE llRel.lo.id = :loId OR llRel.relatedLo.id = :loId")
})
public class Lo extends MetaEntity implements AttributeOwner<LoAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private
	String name;
	
	@ManyToOne(cascade = CascadeType.ALL) // { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } ) // CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private LoRichText descr;
	
	@ManyToOne
	@JoinColumn(name = "LO_REPO_ID")
	private LoRepository loRepository;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "KSLU_LOLO_RELTN",
				joinColumns = { @JoinColumn(name = "LO_ID")},
				inverseJoinColumns = { @JoinColumn(name = "RELATED_LO_ID")})
	private List<Lo> relatedLos;

	@ManyToMany
	@JoinTable(name = "KSLU_LO_JN_LOCATEGORY",
				joinColumns = { @JoinColumn(name = "LO_ID")},
				inverseJoinColumns = { @JoinColumn(name = "LOCATEGORY_ID")})
	private List<LoCategory> categories;

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

	public LoRichText getDescr() {
		return descr;
	}

	public void setDescr(LoRichText descr) {
		this.descr = descr;
	}

	/**
	 * @param loHierarchy the loHierarchy to set
	 */
	public void setLoRepository(LoRepository loRepository) {
		this.loRepository = loRepository;
	}

	/**
	 * @return the loHierarchy
	 */
	public LoRepository getLoRepository() {
		return loRepository;
	}

	/**
	 * @param relatedLos the relatedLos to set
	 */
	public void setRelatedLos(List<Lo> relatedLos) {
		this.relatedLos = relatedLos;
	}

	/**
	 * @return the relatedLos
	 */
	public List<Lo> getRelatedLos() {
		if (null == relatedLos) {
			relatedLos = new ArrayList<Lo>(0);
		}
		return relatedLos;
	}

	public void setCategories(List<LoCategory> categories) {
		this.categories = categories;
	}

	public List<LoCategory> getCategories() {
		if (null == categories) {
			categories = new ArrayList<LoCategory>(0);
		}
		return categories;
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

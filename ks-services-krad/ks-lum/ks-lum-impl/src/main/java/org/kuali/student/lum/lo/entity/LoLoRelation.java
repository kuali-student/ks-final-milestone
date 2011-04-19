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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLO_LO_RELTN")
public class LoLoRelation extends MetaEntity implements AttributeOwner<LoLoRelationAttribute> {
	
	@ManyToOne
	@JoinColumn(name="LO_ID")
	private Lo lo;
	
	@ManyToOne
	@JoinColumn(name="RELATED_LO_ID")
	private Lo relatedLo;
	
	@ManyToOne
	@JoinColumn(name="LO_LO_RELATION_TYPE_ID")
	private LoLoRelationType loLoRelationType;
	
	@Column(name = "ST")
	private String state;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LoLoRelationAttribute> attributes;

	public void setLo(Lo lo) {
		this.lo = lo;
	}

	public Lo getLo() {
		return lo;
	}

	public void setRelatedLo(Lo relatedLo) {
		this.relatedLo = relatedLo;
	}

	public Lo getRelatedLo() {
		return relatedLo;
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

	@Override
	public List<LoLoRelationAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<LoLoRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setLoLoRelationType(LoLoRelationType loLoRelationType) {
		this.loLoRelationType = loLoRelationType;
	}

	public LoLoRelationType getLoLoRelationType() {
		return loLoRelationType;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}

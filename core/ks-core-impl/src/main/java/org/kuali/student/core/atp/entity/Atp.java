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
package org.kuali.student.core.atp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;

@Entity
@Table(name = "KSAP_ATP")
@NamedQueries( { 
	@NamedQuery(name = "Atp.findAtpsByAtpType", query = "SELECT atp FROM Atp atp WHERE atp.type.id = :atpTypeId"),
	@NamedQuery(name = "Atp.findAtpsByDate", query = "SELECT atp FROM Atp atp WHERE atp.effectiveDate <= :searchDate AND atp.expirationDate > :searchDate"),
	@NamedQuery(name = "Atp.findAtpsByDates", query = "SELECT atp FROM Atp atp WHERE atp.effectiveDate >= :startDate AND atp.expirationDate <= :endDate")
})
public class Atp extends MetaEntity implements AttributeOwner<AtpAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private RichText descr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<AtpAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "TYPE")
	private AtpType type;

	@Column(name = "STATE")
	private String state;

	@OneToMany(mappedBy = "atp", cascade = CascadeType.REMOVE)
	private List<DateRange> dateRanges;

	@OneToMany(mappedBy = "atp", cascade = CascadeType.REMOVE)
	private List<Milestone> milestones;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RichText getDescr() {
		return descr;
	}

	public void setDescr(RichText descr) {
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

	public List<AtpAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AtpAttribute> attributes) {
		this.attributes = attributes;
	}

	public AtpType getType() {
		return type;
	}

	public void setType(AtpType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<DateRange> getDateRanges() {
		return dateRanges;
	}

	public void setDateRanges(List<DateRange> dateRanges) {
		this.dateRanges = dateRanges;
	}

	public List<Milestone> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<Milestone> milestones) {
		this.milestones = milestones;
	}

	// public Meta getMeta() {
	// return meta;
	// }
	//
	// public void setMeta(Meta meta) {
	// this.meta = meta;
	// }

}

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

package org.kuali.student.core.atp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "KSAP_ATP")
@NamedQueries( { 
	@NamedQuery(name = "Atp.findAtpsByAtpType", query = "SELECT atp FROM Atp atp WHERE atp.type.id = :atpTypeId"),
	@NamedQuery(name = "Atp.findAtpsByDate", query = "SELECT atp FROM Atp atp WHERE atp.startDate <= :searchDate AND atp.endDate > :searchDate"),
	@NamedQuery(name = "Atp.findAtpsByDates", query = "SELECT atp FROM Atp atp WHERE atp.startDate >= :startDate AND atp.endDate <= :endDate")
})
public class Atp extends MetaEntity implements AttributeOwner<AtpAttribute> {


	@Column(name = "NAME")
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private AtpRichText descr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DT")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DT")
	private Date endDate;

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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AtpRichText getDescr() {
		return descr;
	}

	public void setDescr(AtpRichText descr) {
		this.descr = descr;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

}

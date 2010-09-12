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

@Entity
@Table(name = "KSAP_DT_RANGE")
@NamedQueries({
	@NamedQuery(name="DateRange.findDateRangesByAtp", query="SELECT dateRange FROM DateRange dateRange WHERE dateRange.atp.id = :atpId"),
	@NamedQuery(name="DateRange.findDateRangesByDate", query="SELECT dateRange FROM DateRange dateRange WHERE dateRange.startDate <= :searchDate AND dateRange.endDate >= :searchDate")
})
public class DateRange extends MetaEntity implements AttributeOwner<DateRangeAttribute> {
	
	@Id
	@Column(name = "DATERANGE_KEY")
	private String id;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private AtpRichText descr;

	@ManyToOne
	@JoinColumn(name = "ATP_ID")
	private Atp atp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DT")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_DT")
	private Date endDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<DateRangeAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "DT_RANGE_TYPE_ID")
	private DateRangeType type;

	@Column(name="STATE")
	private String state;

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

	public Atp getAtp() {
		return atp;
	}

	public void setAtp(Atp atp) {
		this.atp = atp;
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

	public List<DateRangeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<DateRangeAttribute> attributes) {
		this.attributes = attributes;
	}

	public DateRangeType getType() {
		return type;
	}

	public void setType(DateRangeType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

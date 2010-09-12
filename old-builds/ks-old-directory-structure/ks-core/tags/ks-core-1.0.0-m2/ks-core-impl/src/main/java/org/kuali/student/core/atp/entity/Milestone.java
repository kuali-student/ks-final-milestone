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
@Table(name = "KSAP_MLSTN")
@NamedQueries( { 
	@NamedQuery(name = "Milestone.findMilestonesByAtp", query = "SELECT milestone FROM Milestone milestone WHERE milestone.atp.id = :atpId"),
	@NamedQuery(name = "Milestone.findMilestonesByDates", query = "SELECT milestone FROM Milestone milestone WHERE milestone.milestoneDate >= :startDate AND milestone.milestoneDate <= :endDate"),
	@NamedQuery(name = "Milestone.findMilestonesByDatesAndType", query = "SELECT milestone FROM Milestone milestone WHERE milestone.type.id = :milestoneTypeId AND milestone.milestoneDate >= :startDate AND milestone.milestoneDate <= :endDate")
})
public class Milestone extends MetaEntity implements
		AttributeOwner<MilestoneAttribute> {
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String name;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private RichText desc;

	@ManyToOne
	@JoinColumn(name = "ATP_ID")
	private Atp atp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MLSTN_DT")
	private Date milestoneDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<MilestoneAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "MLSTN_TYPE_ID")
	private MilestoneType type;
	
	@Column(name="STATE")
	private String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Atp getAtp() {
		return atp;
	}

	public void setAtp(Atp atp) {
		this.atp = atp;
	}

	public Date getMilestoneDate() {
		return milestoneDate;
	}

	public void setMilestoneDate(Date milestoneDate) {
		this.milestoneDate = milestoneDate;
	}

	public List<MilestoneAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MilestoneAttribute> attributes) {
		this.attributes = attributes;
	}

	public MilestoneType getType() {
		return type;
	}

	public void setType(MilestoneType type) {
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

	public void setDesc(RichText desc) {
		this.desc = desc;
	}

	public RichText getDesc() {
		return desc;
	}

}

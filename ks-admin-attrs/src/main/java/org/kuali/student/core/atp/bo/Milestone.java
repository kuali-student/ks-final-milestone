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

package org.kuali.student.core.atp.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.rice.kns.bo.KualiTypeCodeBase;

@Entity
@Table(name = "KSAP_MLSTN")
@NamedQueries({
		@NamedQuery(name = "Milestone.findMilestonesByAtp", query = "SELECT milestone FROM Milestone milestone WHERE milestone.atp.id = :atpId"),
		@NamedQuery(name = "Milestone.findMilestonesByDates", query = "SELECT milestone FROM Milestone milestone WHERE milestone.milestoneDate >= :startDate AND milestone.milestoneDate <= :endDate"),
		@NamedQuery(name = "Milestone.findMilestonesByDatesAndType", query = "SELECT milestone FROM Milestone milestone WHERE milestone.type.id = :milestoneTypeId AND milestone.milestoneDate >= :startDate AND milestone.milestoneDate <= :endDate") })
public class Milestone extends KualiTypeCodeBase {
	private static final long serialVersionUID = 423410094436352921L;

	@Column(name = "ATP_ID")
	private String atpId;

	@ManyToOne
	@JoinColumn(name = "ATP_ID")
	private Atp atp;
	
	@ManyToOne
	@JoinColumn(name = "TYPE")
	private MilestoneType type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MLSTN_DT")
	private Date milestoneDate;

	public Milestone() {
		super();
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

	public String getAtpId() {
		return atpId;
	}

	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}

	public MilestoneType getType() {
		return type;
	}

	public void setType(MilestoneType type) {
		this.type = type;
	}

}

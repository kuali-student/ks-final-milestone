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

import org.kuali.student.core.bo.KsTypeStateBusinessObjectBase;

@Entity
@Table(name = "KSAP_DT_RANGE")
@NamedQueries({
	@NamedQuery(name="DateRange.findDateRangesByAtp", query="SELECT dateRange FROM DateRange dateRange WHERE dateRange.atp.id = :atpId"),
	@NamedQuery(name="DateRange.findDateRangesByDate", query="SELECT dateRange FROM DateRange dateRange WHERE dateRange.startDate <= :searchDate AND dateRange.endDate >= :searchDate")
})
public class DateRange extends KsTypeStateBusinessObjectBase {
	private static final long serialVersionUID = -9071561165465644092L;

	@Column(name = "ATP_ID")
	private String atpId;
	
	@ManyToOne
	@JoinColumn(name = "ATP_ID")
	private Atp atp;
	
	@ManyToOne
	@JoinColumn(name = "TYPE")
	private DateRangeType type;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DT")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_DT")
	private Date endDate;
	
	private String descriptionId;
    
    private AtpRichText description;
	
	public DateRange() {
		super();
	}
	
	public Atp getAtp() {
		return atp;
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

	public String getAtpId() {
		return atpId;
	}

	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}

	public void setAtp(Atp atp) {
		this.atp = atp;
	}

	public DateRangeType getType() {
		return type;
	}

	public void setType(DateRangeType type) {
		this.type = type;
	}

    public String getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(String descriptionId) {
        this.descriptionId = descriptionId;
    }

    public AtpRichText getDescription() {
        return description;
    }

    public void setDescription(AtpRichText description) {
        this.description = description;
    }
	
}

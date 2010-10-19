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

import java.util.ArrayList;
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

import org.kuali.student.core.bo.TypeStateBusinessObjectBase;

@Entity
@Table(name = "KSAP_ATP")
@NamedQueries( { 
	@NamedQuery(name = "Atp.findAtpsByAtpType", query = "SELECT atp FROM Atp atp WHERE atp.type.id = :atpTypeId"),
	@NamedQuery(name = "Atp.findAtpsByDate", query = "SELECT atp FROM Atp atp WHERE atp.effectiveDate <= :searchDate AND atp.expirationDate > :searchDate"),
	@NamedQuery(name = "Atp.findAtpsByDates", query = "SELECT atp FROM Atp atp WHERE atp.effectiveDate >= :startDate AND atp.expirationDate <= :endDate")
})
public class Atp extends TypeStateBusinessObjectBase {
	private static final long serialVersionUID = -4021959685737332345L;
	
	@ManyToOne
	@JoinColumn(name = "TYPE")
	private AtpType type;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="START_DT")
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="END_DT")
    private Date endDate;
    
    private String descriptionId;
    
    private AtpRichText description;
    
	@OneToMany(mappedBy = "atp", cascade = CascadeType.REMOVE)
	private List<DateRange> dateRanges;

	@OneToMany(mappedBy = "atp", cascade = CascadeType.REMOVE)
	private List<Milestone> milestones;
	
	public Atp() {
		super();
		dateRanges = new ArrayList<DateRange>();
		milestones = new ArrayList<Milestone>();
		
	}


    public AtpType getType() {
		return type;
	}

	public void setType(AtpType type) {
		this.type = type;
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

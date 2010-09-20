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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.rice.kns.bo.AttributedBusinessObject;
import org.kuali.rice.kns.bo.BusinessObjectExtensionAttribute;
import org.kuali.rice.kns.bo.KualiTypeCodeInactivatableFromToBase;

@Entity
@Table(name = "KSAP_ATP")
@NamedQueries( { 
	@NamedQuery(name = "Atp.findAtpsByAtpType", query = "SELECT atp FROM Atp atp WHERE atp.type.id = :atpTypeId"),
	@NamedQuery(name = "Atp.findAtpsByDate", query = "SELECT atp FROM Atp atp WHERE atp.effectiveDate <= :searchDate AND atp.expirationDate > :searchDate"),
	@NamedQuery(name = "Atp.findAtpsByDates", query = "SELECT atp FROM Atp atp WHERE atp.effectiveDate >= :startDate AND atp.expirationDate <= :endDate")
})
public class Atp extends KualiTypeCodeInactivatableFromToBase implements AttributedBusinessObject {
	private static final long serialVersionUID = -4021959685737332345L;

	@OneToMany(mappedBy = "atp", cascade = CascadeType.REMOVE)
	private List<DateRange> dateRanges;

	@OneToMany(mappedBy = "atp", cascade = CascadeType.REMOVE)
	private List<Milestone> milestones;
	
	private List<BusinessObjectExtensionAttribute> attributes;
	
	public Atp() {
		super();
		dateRanges = new ArrayList<DateRange>();
		milestones = new ArrayList<Milestone>();
		attributes = new ArrayList<BusinessObjectExtensionAttribute>();
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
	
	public List<BusinessObjectExtensionAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<BusinessObjectExtensionAttribute> attributes) {
		this.attributes = attributes;
	}

}

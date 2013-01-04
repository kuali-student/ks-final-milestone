/*
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
package org.kuali.student.r2.core.exemption.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.exemption.infc.DateOverride;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateOverrideInfo", propOrder = { "milestoneId", "effectiveStartDate", 
                 "effectiveEndDate"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code })

public class DateOverrideInfo implements DateOverride, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String milestoneId;

	@XmlElement
	private Date effectiveStartDate;

	@XmlElement
	private Date effectiveEndDate;

//  TODO KSCM-372: Non-GWT translatable code
//	@XmlAnyElement
//	private List<Element> _futureElements;

	public DateOverrideInfo() {
		super();
		milestoneId = null;
		effectiveStartDate = null;
		effectiveEndDate = null;
//	    TODO KSCM-372: Non-GWT translatable code
//		_futureElements = null;
	}

	/**
	 * Constructs a new DateOverrideInfo from another DateOverride.
	 * 
	 * @param exemption the DateOverride to copy
	 */
	public DateOverrideInfo(DateOverride dateOverride) {
		super();
		if (null != dateOverride) {
		    this.milestoneId = dateOverride.getMilestoneId();
		    this.effectiveStartDate = dateOverride.getEffectiveStartDate();
		    this.effectiveEndDate = dateOverride.getEffectiveEndDate();
		}

//	    TODO KSCM-372: Non-GWT translatable code
//		_futureElements = null;
	}

	@Override
	public String getMilestoneId() {
	    return milestoneId;
	}

	public void setMilestoneId(String milestoneId) {
	    this.milestoneId = milestoneId;
	}

	@Override
	public Date getEffectiveStartDate() {
	    return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
	    this.effectiveStartDate = effectiveStartDate;
	}

	@Override
	public Date getEffectiveEndDate() {
	    return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
	    this.effectiveEndDate = effectiveEndDate;
	}
}

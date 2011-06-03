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

package org.kuali.student.enrollment.acal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.AcademicCalendar;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.KeyEntity;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcademicCalendarInfo", propOrder = { "key", "typeKey",
		"stateKey", "name", "descr", "startDate", "endDate",
		"campusCalendarKeys", "credentialProgramTypeKey", "meta",
		"attributes", "_futureElements" })
public class AcademicCalendarInfo extends KeyEntityInfo implements
		AcademicCalendar, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private List<String> campusCalendarKeys;

	@XmlElement
	private String credentialProgramTypeKey;

	@XmlElement
	private Date startDate; 

	@XmlElement
	private Date endDate;

	public AcademicCalendarInfo() {
		campusCalendarKeys = null;
		credentialProgramTypeKey = null;
		setStartDate(null);
		setEndDate(null);
	}

	/**
	 * Constructs a new AcademicCalendarInfo from another AcademicCalendar.
	 * 
	 * @param acal
	 *            the Academic Calendar to copy
	 */
	public AcademicCalendarInfo(AcademicCalendar acal) {

		super(acal);

		if (null != acal) {
			this.campusCalendarKeys = null != acal.getCampusCalendarKeys() ? new ArrayList<String>(
					acal.getCampusCalendarKeys()) : null;
			this.credentialProgramTypeKey = acal.getCredentialProgramTypeKey();
			this.setStartDate(new Date( acal.getStartDate().getTime()));
			this.setEndDate(new Date( acal.getEndDate().getTime()));
		}
	}

	@Override
	public List<String> getCampusCalendarKeys() {
		return campusCalendarKeys;
	}

	public void setCampusCalendarKeys(List<String> campusCalendarKeys) {
		this.campusCalendarKeys = campusCalendarKeys;
	}

	@Override
	public String getCredentialProgramTypeKey() {
		return credentialProgramTypeKey;
	}

	public void setCredentialProgramTypeKey(String credentialProgramTypeKey) {
		this.credentialProgramTypeKey = credentialProgramTypeKey;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}

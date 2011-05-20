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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.Holiday;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HolidayInfo", propOrder = { "key", "typeKey", "stateKey",
		"name", "descr", "isInstructionalDay", "isAllDay", "isDateRange",
		"startDate", "endDate", "metaInfo", "attributes", "_futureElements" })
public class HolidayInfo extends KeyDateInfo implements Holiday, Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private Boolean isInstructionalDay;

	public HolidayInfo() {
		super();
		isInstructionalDay = Boolean.valueOf(false);
	}

	/**
	 * Constructs a new HolidayInfo from another Holiday.
	 * 
	 * @param holiday
	 *            the Holiday to copy
	 */
	public HolidayInfo(Holiday holiday) {
		super(holiday);
		if (null != holiday) {
			this.isInstructionalDay = new Boolean(
					holiday.getIsInstructionalDay());
		}
	}

	@Override
	public Boolean getIsInstructionalDay() {
		return isInstructionalDay;
	}

	public void setInstructionalDay(Boolean instructionalDay) {
		this.isInstructionalDay = instructionalDay;
	}
}

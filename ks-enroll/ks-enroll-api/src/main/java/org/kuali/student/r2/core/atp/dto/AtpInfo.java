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
package org.kuali.student.r2.core.atp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.core.atp.infc.Atp;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtpInfo", propOrder = { "key", "typeKey", "stateKey", "name",
		"descr", "startDate", "endDate", "meta", "attributes",
		"_futureElements" })
public class AtpInfo extends KeyEntityInfo implements Atp, Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private Date startDate;
	@XmlElement
	private Date endDate;
	@XmlAnyElement
	private List<Element> _futureElements;

	public static AtpInfo newInstance() {
		return new AtpInfo();
	}

	public static AtpInfo getInstance(Atp atp) {
		return new AtpInfo(atp);
	}

	public AtpInfo() {
		startDate = null;
		endDate = null;
		_futureElements = null;
	}

	/**
	 * Constructs a new AtpInfo from another Atp.
	 * 
	 * @param atp
	 *            the ATP to copy
	 */
	public AtpInfo(Atp atp) {
		super(atp);
		if (null != atp) {
			this.startDate = new Date(atp.getStartDate().getTime());
			this.endDate = new Date(atp.getEndDate().getTime());
			_futureElements = null;
		}
	}

	@Override
	public Date getStartDate() {
		return startDate != null ? new Date(startDate.getTime()) : null;
	}

	@Override
	public void setStartDate(Date startDate) {
		if (startDate != null)
			this.startDate = new Date(startDate.getTime());
	}

	@Override
	public Date getEndDate() {
		return endDate != null ? new Date(endDate.getTime()) : null;
	}

	@Override
	public void setEndDate(Date endDate) {
		if (endDate != null)
			this.endDate = new Date(endDate.getTime());
	}
}

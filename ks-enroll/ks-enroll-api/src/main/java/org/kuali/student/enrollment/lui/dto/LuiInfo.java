/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lui.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiInfo", propOrder = { "id", "typeKey", "stateKey", "name",
		"descr", "luiCode", "cluId", "atpKey", "instructors", 
		"studySubjectArea", "maximumEnrollment", "minimumEnrollment",
		"effectiveDate", "expirationDate", "meta", "attributes", "_futureElements" })
public class LuiInfo extends IdEntityInfo implements Serializable, Lui {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String luiCode;

	@XmlElement
	private String cluId;

	@XmlElement
	private String atpKey;

    @XmlElement
    private String studySubjectArea;
	
    @XmlElement
    private Integer maximumEnrollment;
    
    @XmlElement
    private Integer minimumEnrollment;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlElement
	private List<LuiInstructorInfo> instructors;
	
	@XmlAnyElement
	private List<Element> _futureElements;

	public LuiInfo() {
		super();
		luiCode = null;
		cluId = null;
		atpKey = null;
		studySubjectArea = null;
		maximumEnrollment = null;
		minimumEnrollment = null;
		instructors = new ArrayList<LuiInstructorInfo>();
		effectiveDate = null;
		expirationDate = null;
		_futureElements = null;
	}

	public LuiInfo(Lui lui) {
		super(lui);
		
		if(null == lui) return;
		
		this.luiCode = lui.getLuiCode();
		this.cluId = lui.getCluId();
		this.atpKey = lui.getAtpKey();
		this.studySubjectArea = lui.getStudySubjectArea();
		this.maximumEnrollment = (null != lui.getMaximumEnrollment()) ? new Integer(lui.getMaximumEnrollment()) : null;
		this.maximumEnrollment = (null != lui.getMinimumEnrollment()) ? new Integer(lui.getMinimumEnrollment()) : null;
		this.instructors = (null != lui.getInstructors()) ? 
		        new ArrayList<LuiInstructorInfo>(lui.getInstructors()) : null; 
		this.effectiveDate = null != lui.getEffectiveDate() ? new Date(lui
				.getEffectiveDate().getTime()) : null;
		this.expirationDate = null != lui.getExpirationDate() ? new Date(lui
				.getExpirationDate().getTime()) : null;
		this._futureElements = null;
	}

	@Override
	public String getLuiCode() {
		return luiCode;
	}

	public void setLuiCode(String luiCode) {
		this.luiCode = luiCode;
	}

	@Override
	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	@Override
	public String getAtpKey() {
		return atpKey;
	}

	public void setAtpKey(String atpKey) {
		this.atpKey = atpKey;
	}

	@Override
    public String getStudySubjectArea() {
        return studySubjectArea;
    }

	@Override
    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    @Override
    public Integer getMinimumEnrollment() {
        return minimumEnrollment;
    }

    public void setStudySubjectArea(String studySubjectArea) {
        this.studySubjectArea = studySubjectArea;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }

    @Override
	public List<LuiInstructorInfo> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<LuiInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    @Override
	public Date getEffectiveDate() {
		return effectiveDate != null ? new Date(effectiveDate.getTime()) : null;
	}

	public void setEffectiveDate(Date effectiveDate) {
		if (effectiveDate != null)
			this.effectiveDate = new Date(effectiveDate.getTime());
	}

	@Override
	public Date getExpirationDate() {
		return expirationDate != null ? new Date(expirationDate.getTime()) : null;
	}

	public void setExpirationDate(Date expirationDate) {
		if (expirationDate != null)
			this.expirationDate = new Date(expirationDate.getTime());
	}
}
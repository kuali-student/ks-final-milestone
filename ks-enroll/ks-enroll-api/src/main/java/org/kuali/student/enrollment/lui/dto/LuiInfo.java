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
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.lum.lu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.lu.dto.FeeInfo;
import org.kuali.student.r2.lum.lu.dto.RevenueInfo;
import org.kuali.student.r2.lum.lu.infc.Fee;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiInfo", propOrder = { "id", "typeKey", "stateKey", "name",
		"descr", "luiCode", "cluId", "atpKey", "instructors", 
		"studySubjectArea", "maximumEnrollment", "minimumEnrollment",
		"effectiveDate", "expirationDate", "fees", "gradingOptions",
		"studyTitle", "unitsContentOwner", "unitsDeployment", "expenditure",
		"revenues", "hasWaitlist", "isWaitlistCheckinRequired", "waitlistCheckinFrequency",
		"waitlistMaximum", "waitlistTypeKey", "meta", "attributes", "_futureElements" })
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
	
	// nina begin refactored from CourseOffering, RegistrationGroup, ActivityOffering
	@XmlElement
	private List<FeeInfo> fees;
	
	@XmlElement
	private List<String> gradingOptions;
	
	@XmlElement
	private String studyTitle;
	
	@XmlElement
	private List<String> unitsContentOwner;
	
	@XmlElement
	private List<String> unitsDeployment;

	@XmlElement
	private ExpenditureInfo expenditure;

	@XmlElement
	private List<RevenueInfo> revenues;
	
	@XmlElement
	private Boolean hasWaitlist;

	@XmlElement
	private Boolean isWaitlistCheckinRequired;

	@XmlElement
	private TimeAmountInfo waitlistCheckinFrequency;

	@XmlElement
	private Integer waitlistMaximum;

	@XmlElement
	private String waitlistTypeKey;
	// nina end refactored from CourseOffering, RegistrationGroup, ActivityOffering
	
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
		fees = new ArrayList<FeeInfo>();
		gradingOptions = new ArrayList<String>();
		studyTitle = null;
		unitsContentOwner  = new ArrayList<String>();
		unitsDeployment = new ArrayList<String>();
		expenditure = null;
		hasWaitlist = new Boolean(false);
		isWaitlistCheckinRequired = new Boolean(false);
		waitlistCheckinFrequency = null;
	    waitlistMaximum = null;
	    waitlistTypeKey = null;
	    revenues = new ArrayList<RevenueInfo>();
	}

	public LuiInfo(Lui lui) {
		super(lui);
		
		if(null == lui) return;
		
		this.luiCode = lui.getLuiCode();
		this.cluId = lui.getCluId();
		this.atpKey = lui.getAtpKey();
		this.studySubjectArea = lui.getStudySubjectArea();
		this.maximumEnrollment = (null != lui.getMaximumEnrollment()) ? new Integer(lui.getMaximumEnrollment()) : null;
		this.minimumEnrollment = (null != lui.getMinimumEnrollment()) ? new Integer(lui.getMinimumEnrollment()) : null;
		this.instructors = (null != lui.getInstructors()) ? 
		        new ArrayList<LuiInstructorInfo>(lui.getInstructors()) : null; 
		this.effectiveDate = null != lui.getEffectiveDate() ? new Date(lui.getEffectiveDate().getTime()) : null;
		this.expirationDate = null != lui.getExpirationDate() ? new Date(lui.getExpirationDate().getTime()) : null;
		this._futureElements = null;
		this.fees = (null != lui.getFees()) ? 
		        new ArrayList<FeeInfo>(lui.getFees()) : null;
		this.gradingOptions = new ArrayList<String>(lui.getGradingOptions());
		this.studyTitle = lui.getStudyTitle();
		this.unitsContentOwner  = new ArrayList<String>(lui.getUnitsContentOwner());
		this.unitsDeployment = new ArrayList<String>(lui.getUnitsDeployment());
		this.expenditure = new ExpenditureInfo(lui.getExpenditure());
		this.hasWaitlist = (null != lui.getHasWaitlist()) ? new Boolean(lui.getHasWaitlist()) : null;
		this.isWaitlistCheckinRequired = (null != lui.getIsWaitlistCheckinRequired()) ? new Boolean(lui.getIsWaitlistCheckinRequired()) : null;
		this.waitlistCheckinFrequency = new TimeAmountInfo(lui.getWaitlistCheckinFrequency());
		this.waitlistMaximum = lui.getWaitlistMaximum();
		this.waitlistTypeKey = lui.getWaitlistTypeKey();
		this.revenues = (null != lui.getRevenues()) ? 
		        new ArrayList<RevenueInfo>(lui.getRevenues()) : null;
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

	@Override
	public List<FeeInfo> getFees() {
		return fees;
	}

	@Override
	public List<String> getGradingOptions() {
		return gradingOptions;
	}

	@Override
	public String getStudyTitle() {
		return studyTitle;
	}

	@Override
	public List<String> getUnitsContentOwner() {
		return unitsContentOwner;
	}

	@Override
	public List<String> getUnitsDeployment() {
		return unitsDeployment;
	}

	@Override
	public ExpenditureInfo getExpenditure() {
		return expenditure;
	}

	@Override
	public List<RevenueInfo> getRevenues() {
		return revenues;
	}

	public void setFees(List<FeeInfo> fees) {
		this.fees = fees;
	}

	public void setGradingOptions(List<String> gradingOptions) {
		this.gradingOptions = gradingOptions;
	}

	public void setStudyTitle(String studyTitle) {
		this.studyTitle = studyTitle;
	}

	public void setUnitsContentOwner(List<String> unitsContentOwner) {
		this.unitsContentOwner = unitsContentOwner;
	}

	public void setUnitsDeployment(List<String> unitsDeployment) {
		this.unitsDeployment = unitsDeployment;
	}

	public void setExpenditure(ExpenditureInfo expenditure) {
		this.expenditure = expenditure;
	}

	public void setRevenues(List<RevenueInfo> revenues) {
		this.revenues = revenues;
	}

	@Override
	public Boolean getHasWaitlist() {
		return hasWaitlist;
	}

	@Override
	public Boolean getIsWaitlistCheckinRequired() {
		return isWaitlistCheckinRequired;
	}

	@Override
	public TimeAmountInfo getWaitlistCheckinFrequency() {
		return waitlistCheckinFrequency;
	}

	@Override
	public Integer getWaitlistMaximum() {
		return waitlistMaximum;
	}

	@Override
	public String getWaitlistTypeKey() {
		return waitlistTypeKey;
	}

	public void setHasWaitlist(Boolean hasWaitlist) {
		this.hasWaitlist = hasWaitlist;
	}

	public void setIsWaitlistCheckinRequired(Boolean isWaitlistCheckinRequired) {
		this.isWaitlistCheckinRequired = isWaitlistCheckinRequired;
	}

	public void setWaitlistCheckinFrequency(TimeAmountInfo waitlistCheckinFrequency) {
		this.waitlistCheckinFrequency = waitlistCheckinFrequency;
	}

	public void setWaitlistMaximum(Integer waitlistMaximum) {
		this.waitlistMaximum = waitlistMaximum;
	}

	public void setWaitlistTypeKey(String waitlistTypeKey) {
		this.waitlistTypeKey = waitlistTypeKey;
	}
}
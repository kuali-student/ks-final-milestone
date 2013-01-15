/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.infc.CourseOfferingCrossListing;
import org.kuali.student.enrollment.courseoffering.infc.OfferingInstructor;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

/**
 * @author Kuali Student Team (Kamal)
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingInfo", propOrder = {
        "id", "typeKey", "stateKey", "descr", "courseId",
        "termId", "courseCode", "courseOfferingCode", "courseNumberSuffix", 
        "courseOfferingTitle", "creditCnt", "isHonorsOffering", 
        "instructors", "subjectArea", "unitsDeploymentOrgIds",
        "unitsContentOwnerOrgIds",  "maximumEnrollment", 
        "minimumEnrollment",
        "crossListings", "gradingOptionId", "gradingOptionName",
        "studentRegistrationGradingOptions", "creditOptionName", 
        "creditOptionId", "waitlistLevelTypeKey", "waitlistMaximum", 
        "hasWaitlist", "waitlistTypeKey","campusLocations", 
        "isEvaluated", "fundingSource", "isFeeAtActivityOffering", 
        "courseNumberInternalSuffix",
        "isFinancialAidEligible", "courseOfferingURL", "finalExamType",
        "meta", "attributes", "_futureElements"})

public class CourseOfferingInfo 
    extends IdNamelessEntityInfo 
    implements CourseOffering, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseId;

    @XmlElement
    private String termId;

    @XmlElement
    private String courseOfferingTitle;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private String courseCode;

    @XmlElement
    private String courseOfferingCode;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private List<CourseOfferingCrossListingInfo> crossListings;

    @XmlElement
    private Boolean isHonorsOffering;

    @XmlElement
    private List<String> campusLocations;

    @XmlElement
    private List<OfferingInstructorInfo> instructors;

    @XmlElement
    private List<String> unitsDeploymentOrgIds;

    @XmlElement
    private List<String> unitsContentOwnerOrgIds;

    @XmlElement
    private String gradingOptionId;

    @XmlElement
    private String gradingOptionName;

    @XmlElement
    private String creditCnt;

    @XmlElement
    private List<String> studentRegistrationGradingOptions;

    @XmlElement
    private String creditOptionName;

    @XmlElement
    private String creditOptionId;

    @XmlElement
    private Boolean hasWaitlist;

    @XmlElement
    private String waitlistTypeKey;

    @XmlElement
    private String waitlistLevelTypeKey;
    
    @XmlElement
    private Integer waitlistMaximum;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Integer minimumEnrollment;

    @XmlElement
    private String finalExamType;

    @XmlElement
    private String fundingSource;

    @XmlElement
    private Boolean isFinancialAidEligible;

    @XmlElement
    private Boolean isEvaluated;

    @XmlElement
    private Boolean isFeeAtActivityOffering;

    @XmlElement
    private String courseOfferingURL;

    @XmlAnyElement
    private List<Element> _futureElements;

    @XmlElement
    private String courseNumberInternalSuffix;

    /**
     * Constructs a new CourseOfferingInfo.
     */
    public CourseOfferingInfo() {
        this.finalExamType = FinalExam.NONE.toString();
    }

    /**
     * Constructs a new CourseOfferingInfo from another
     * CourseOffering.
     *
     * @param offering the course offering to copy
     */
    public CourseOfferingInfo(CourseOffering offering) {

        super(offering);

        if (offering == null) {
            return;
        }

        this.courseId = offering.getCourseId();
        this.termId = offering.getTermId();
        

        this.courseOfferingTitle = offering.getCourseOfferingTitle();
        this.courseCode = offering.getCourseCode();
        this.courseOfferingCode = offering.getCourseOfferingCode();
        this.courseOfferingCode = offering.getCourseOfferingCode();
        this.courseNumberSuffix = offering.getCourseNumberSuffix();
        this.courseNumberInternalSuffix = offering.getCourseNumberInternalSuffix();
        this.subjectArea = offering.getSubjectArea();
        this.crossListings = new ArrayList<CourseOfferingCrossListingInfo>();
        for (CourseOfferingCrossListing crossListing : offering.getCrossListings()) {
            this.crossListings.add(new CourseOfferingCrossListingInfo(crossListing));
        }

        this.isHonorsOffering = offering.getIsHonorsOffering();

        this.instructors = new ArrayList<OfferingInstructorInfo>();
        for (OfferingInstructor instructor : offering.getInstructors()) {
            this.instructors.add(new OfferingInstructorInfo(instructor));
        }

        this.unitsDeploymentOrgIds = offering.getUnitsDeploymentOrgIds();
        this.unitsContentOwnerOrgIds = offering.getUnitsContentOwnerOrgIds();

        this.gradingOptionId =  offering.getGradingOptionId();
        this.creditCnt = offering.getCreditCnt();
        this.gradingOptionName = offering.getGradingOptionName();
        this.studentRegistrationGradingOptions = (null != offering.getStudentRegistrationGradingOptions()) ? new ArrayList<String>(offering.getStudentRegistrationGradingOptions()) : null;
        this.creditOptionName = offering.getCreditOptionName();
        this.creditOptionId = offering.getCreditOptionId();

        this.campusLocations = (null != offering.getCampusLocations()) ? new ArrayList<String>(offering.getCampusLocations()) : null;

        this.waitlistTypeKey = offering.getWaitlistTypeKey();
        this.waitlistMaximum = offering.getWaitlistMaximum();
        this.maximumEnrollment = offering.getMaximumEnrollment();
        this.minimumEnrollment = offering.getMinimumEnrollment();

        this.hasWaitlist = (null != offering.getHasWaitlist()) ? new Boolean(offering.getHasWaitlist()) : null;

        this.waitlistLevelTypeKey = offering.getWaitlistLevelTypeKey();

        this.isEvaluated = offering.getIsEvaluated();
        this.fundingSource = offering.getFundingSource();

        this.isFinancialAidEligible = offering.getIsFinancialAidEligible();
        if (offering.getDescr() != null) {
            this.descr = new RichTextInfo(offering.getDescr());
        }

        this.isFeeAtActivityOffering = offering.getIsFeeAtActivityOffering();
        this.finalExamType = offering.getFinalExamType();
        this.courseOfferingURL = offering.getCourseOfferingURL();
    }

    @Override
    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String getTermId() {
        return this.termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public String getCourseOfferingTitle() {
        return this.courseOfferingTitle;
    }

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String getCourseOfferingCode() {
        return this.courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    @Override
    public String getCourseNumberSuffix() {
        return this.courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    @Override
    public String getCourseNumberInternalSuffix() {
        return courseNumberInternalSuffix;
    }

    public void setCourseNumberInternalSuffix(String courseNumberInternalSuffix) {
        this.courseNumberInternalSuffix = courseNumberInternalSuffix;
    }

    @Override
    public String getSubjectArea() {
        return this.subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    @Override
    public List<CourseOfferingCrossListingInfo> getCrossListings() {
        if (crossListings == null) {
            crossListings = new ArrayList<CourseOfferingCrossListingInfo>();
        }

        return crossListings;
    }

    public void setCrossListings(List<CourseOfferingCrossListingInfo> crossListings) {
        this.crossListings = crossListings;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return this.isHonorsOffering;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    @Override
    public List<String> getCampusLocations() {
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    @Override
    public List<OfferingInstructorInfo> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<OfferingInstructorInfo> ();
        }
        return instructors;
    }

    public void setInstructors(List<OfferingInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    @Override
    public List<String> getUnitsDeploymentOrgIds() {
        if (null == this.unitsDeploymentOrgIds) {
            this.unitsDeploymentOrgIds = new ArrayList<String>();
        }

        return this.unitsDeploymentOrgIds;
    }

    public void setUnitsDeploymentOrgIds(List<String> unitsDeploymentOrgIds) {
        this.unitsDeploymentOrgIds = unitsDeploymentOrgIds;
    }

    @Override
    public List<String> getUnitsContentOwnerOrgIds() {
        if (null == this.unitsContentOwnerOrgIds) {
            this.unitsContentOwnerOrgIds = new ArrayList<String>();
        }

        return this.unitsContentOwnerOrgIds;
    }

    public void setUnitsContentOwnerOrgIds(List<String> unitsContentOwnerOrgIds) {
        this.unitsContentOwnerOrgIds = unitsContentOwnerOrgIds;
    }

    @Override
    public String getGradingOptionId() {
        return this.gradingOptionId;
    }

    public void setGradingOptionId(String gradingOptionId) {
        this.gradingOptionId = gradingOptionId;
    }

    @Override
    public List<String> getStudentRegistrationGradingOptions() {
        if (studentRegistrationGradingOptions == null) {
            studentRegistrationGradingOptions = new ArrayList<String>();
        }

        return this.studentRegistrationGradingOptions;
    }

    public void setStudentRegistrationGradingOptions(List<String> gradingOptionIds) {
        this.studentRegistrationGradingOptions = gradingOptionIds;
    }

    @Override
    public String getCreditOptionName() {
        return this.creditOptionName;
    }

    public void setCreditOptionName(String creditOptionName) {
        this.creditOptionName = creditOptionName;
    }

    @Override
    public String getCreditOptionId() {
        return this.creditOptionId;
    }

    public void setCreditOptionId(String creditOptionId) {
        this.creditOptionId = creditOptionId;
    }

    @Override
    public Boolean getHasWaitlist() {
        return this.hasWaitlist;
    }

    public void setHasWaitlist(Boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }

    @Override
    public String getWaitlistTypeKey() {
        return this.waitlistTypeKey;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    @Override
    public String getWaitlistLevelTypeKey() {
        return this.waitlistLevelTypeKey;
    }

    public void setWaitlistLevelTypeKey(String waitlistLevelTypeKey) {
        this.waitlistLevelTypeKey = waitlistLevelTypeKey;
    }

    @Override
    public Integer getWaitlistMaximum() {
        return waitlistMaximum;
    }

    public void setWaitlistMaximum(Integer waitlistMaximum) {
        this.waitlistMaximum = waitlistMaximum;
    }

    @Override
    public Integer getMaximumEnrollment() {
        return this.maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    @Override
    public Integer getMinimumEnrollment() {
        return this.minimumEnrollment;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }

    @Override
    public String getFundingSource() {
        return this.fundingSource;
    }

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    @Override
    public Boolean getIsFinancialAidEligible() {
        return this.isFinancialAidEligible;
    }

    public void setIsFinancialAidEligible(Boolean isFinancialAidEligible) {
        this.isFinancialAidEligible = isFinancialAidEligible;
    }

    @Override
    public Boolean getIsEvaluated() {
        return isEvaluated;
    }

   
    public void setIsEvaluated(Boolean isEvaluated) {
		this.isEvaluated = isEvaluated;
	}

	@Override
    public Boolean getIsFeeAtActivityOffering() {
        return isFeeAtActivityOffering;
    }

   
    
     public void setIsFeeAtActivityOffering(Boolean isFeeAtActivityOffering) {
		this.isFeeAtActivityOffering = isFeeAtActivityOffering;
	}

	@Override
    public String getCourseOfferingURL() {
        return courseOfferingURL;
    }

    public void setCourseOfferingURL(String courseOfferingURL) {
        this.courseOfferingURL = courseOfferingURL;
    }

	public void setFinalExamType(String value) {
		this.finalExamType = value;
	}

	@Override
	public String getFinalExamType() {
		return this.finalExamType;
	}

    @Override
    public String getGradingOptionName() {
        return gradingOptionName;
    }

    public void setGradingOption(String gradingOptionName) {
       this.gradingOptionName = gradingOptionName;
    }

    @Override
    public String getCreditCnt() {
        return creditCnt;
    }

    public void setCreditCnt(String creditCnt) {
        this.creditCnt = creditCnt;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CourseOfferingInfo [courseId=");
        builder.append(courseId);
        builder.append(", termId=");
        builder.append(termId);
        builder.append(", courseOfferingCode=");
        builder.append(courseOfferingCode);
        builder.append("]");
        return builder.toString();
    }
}

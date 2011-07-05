/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.courseoffering.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.lui.dto.LuiInstructorInfo;
import org.kuali.student.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultComponent;
import org.w3c.dom.Element;

/**
 *
 * @author Kuali Student Team (Kamal)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingInfo", propOrder = {"termKey", "activityOfferingIds", "registrationGroupIds",
        "courseCode", "courseNumberSuffix", "courseTitle", "courseId", "formatIds", "isHonorsOffering", "instructors", 
        "subjectArea", "unitsDeployment", "unitsContentOwner", "finalExamStatus", "maximumEnrollment", "minimumEnrollment", 
        "jointOfferingIds", "creditOptions", "gradingOptions", "gradeRosterLevel",
        "hasWaitlist", "waitlistTypeKey", "waitlistMaximum", "isWaitlistCheckinRequired", "waitlistCheckinFrequency",
        "fundingSource", "fees", "revenues", "expenditure", "isFinancialAidEligible", "registrationOrderTypeKey",
        "id", "typeKey", "stateKey", "name", "descr", "meta", "attributes", "_futureElements"})
public class CourseOfferingInfo extends IdEntityInfo implements CourseOffering {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> activityOfferingIds;
    
    @XmlElement
    private List<String> registrationGroupIds;
    
    @XmlElement
    private String courseId;

    @XmlElement
    private List<String> formatIds;

    @XmlElement
    private String subjectArea;
    
    @XmlElement
    private Boolean isHonorsOffering;

    @XmlElement
    private List<LuiInstructorInfo> instructors;
    
    @XmlElement
    private List<String> unitsDeployment;
    
    @XmlElement
    private List<String> unitsContentOwner;
    
    @XmlElement
    private Boolean finalExamStatus;
    
    @XmlElement
    private String waitlistTypeKey;
    
    @XmlElement
    private Integer waitlistMaximum;
    
    @XmlElement
    private String termKey;
    
    @XmlElement
    private String courseCode;
    
    @XmlElement
    private String courseNumberSuffix;
    
    @XmlElement
    private String courseTitle;
    
    @XmlElement
    private Integer maximumEnrollment;
    
    @XmlElement
    private Integer minimumEnrollment;
    
    @XmlElement
    private List<String> jointOfferingIds;
    
    @XmlElement
    private ResultComponent creditOptions;
    
    @XmlElement
    private List<String> gradingOptions;
    
    @XmlElement
    private String gradeRosterLevel;
    
    @XmlElement
    private Boolean hasWaitlist;
    
    @XmlElement
    private Boolean isWaitlistCheckinRequired;
    
    @XmlElement
    private TimeAmount waitlistCheckinFrequency;
    
    @XmlElement
    private String fundingSource;
    
    @XmlElement
    private List<CourseFeeInfo> fees;
    
    @XmlElement
    private List<CourseRevenueInfo> revenues;
    
    @XmlElement
    private CourseExpenditureInfo expenditure;
    
    @XmlElement
    private Boolean isFinancialAidEligible;
    
    @XmlElement
    private String registrationOrderTypeKey;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    
    public CourseOfferingInfo() {

        this.activityOfferingIds = new ArrayList<String>();
        this.registrationGroupIds = new ArrayList<String>();
        this.courseId = null;
        this.formatIds = new ArrayList<String>();
        this.subjectArea = null;
        this.isHonorsOffering = new Boolean(false); 
        this.instructors = new ArrayList<LuiInstructorInfo>();
        this.unitsContentOwner = null;
        this.finalExamStatus = new Boolean(false);
        this.waitlistTypeKey = null;
        this.waitlistMaximum = null;
        this.termKey = null;
        this.courseCode = null;
        this.courseNumberSuffix = null;
        this.courseTitle = null;
        this.maximumEnrollment = null;
        this.minimumEnrollment = null;
        this.jointOfferingIds = new ArrayList<String>();
        this.creditOptions = null;
        this.gradingOptions = new ArrayList<String>();
        this.gradeRosterLevel = null;
        this.hasWaitlist = new Boolean(false);
        this.isWaitlistCheckinRequired = new Boolean(false);
        this.waitlistCheckinFrequency = null;
        this.fundingSource = null;
        this.fees = new ArrayList<CourseFeeInfo>();
        this.revenues = new ArrayList<CourseRevenueInfo>();
        this.expenditure = null;
        this.isFinancialAidEligible = new Boolean(false);
        this.registrationOrderTypeKey = null;
        this._futureElements = null;                
    }
    

    
    public CourseOfferingInfo(CourseOffering course) {

        super(course);
        
        if(null == course) return;
        
        this.activityOfferingIds = (null != course.getActivityOfferingIds()) ? new ArrayList<String>(course.getActivityOfferingIds()) : null ;
        this.registrationGroupIds = (null != course.getRegistrationGroupIds()) ?  new ArrayList<String>(course.getRegistrationGroupIds()) : null;
        this.courseId = course.getCourseId();
        this.formatIds = (null != course.getFormatIds()) ? new ArrayList<String>(course.getFormatIds()) : null;
        this.subjectArea = course.getSubjectArea();
        this.isHonorsOffering = (null != course.getIsHonorsOffering()) ? new Boolean(course.getIsHonorsOffering()) : null; 
        this.instructors = (null != course.getInstructors()) ? new ArrayList<LuiInstructorInfo>(((List<LuiInstructorInfo>)course.getInstructors())) : null;
        this.unitsContentOwner = course.getUnitsContentOwner();
        this.finalExamStatus = (null != course.getFinalExamStatus()) ? new Boolean(course.getFinalExamStatus()) : null;
        this.waitlistTypeKey = course.getWaitlistTypeKey();
        this.waitlistMaximum = (null != course.getWaitlistMaximum()) ? new Integer(course.getWaitlistMaximum()) : null;
        this.termKey = course.getTermKey();
        this.courseCode = course.getCourseCode();
        this.courseNumberSuffix = course.getCourseNumberSuffix();
        this.courseTitle = course.getCourseTitle();
        this.maximumEnrollment = (null != course.getMaximumEnrollment()) ? new Integer(course.getMaximumEnrollment()) : null;
        this.minimumEnrollment = (null != course.getMinimumEnrollment()) ? new Integer(course.getMinimumEnrollment()) : null;
        this.jointOfferingIds = (null != course.getJointOfferingIds()) ? new ArrayList<String>(course.getJointOfferingIds()) : null;
        this.creditOptions = (null != course.getCreditOptions()) ? new ResultComponentInfo(course.getCreditOptions()) : null;
        this.gradingOptions = (null != course.getGradingOptions()) ? new ArrayList<String>(course.getGradingOptions()) : null;
        this.gradeRosterLevel = course.getGradeRosterLevel();
        this.hasWaitlist = (null != course.getHasWaitlist()) ? new Boolean(course.getHasWaitlist()) : null;
        this.isWaitlistCheckinRequired = (null != course.getIsWaitlistCheckinRequired()) ? new Boolean(course.getIsWaitlistCheckinRequired()) : null;
        this.waitlistCheckinFrequency = (null != course.getWaitlistCheckinFrequency()) ? new TimeAmountInfo(course.getWaitlistCheckinFrequency()) : null;
        this.fundingSource = course.getFundingSource();
        this.fees = (null != course.getFees()) ? new ArrayList<CourseFeeInfo>((List<CourseFeeInfo>)course.getFees()) : null;
        // TODO: Change this to r2 revenue with null check
        this.revenues = course.getRevenues();
        // TODO: Change this to r2 expenditure with null;
        this.expenditure = course.getExpenditure();
        this.isFinancialAidEligible = (null != course.getIsFinancialAidEligible()) ? new Boolean(course.getIsFinancialAidEligible()) : null;
        this.registrationOrderTypeKey = course.getRegistrationOrderTypeKey();
    }

    
    
    @Override
    public List<String> getActivityOfferingIds() {
        if(null == this.activityOfferingIds) {
            this.activityOfferingIds = new ArrayList<String>();
        }
        return activityOfferingIds;
    }

    @Override
    public List<String> getRegistrationGroupIds() {
        if(null == this.registrationGroupIds) {
            this.registrationGroupIds = new ArrayList<String>();
        }
        return registrationGroupIds;
    }

    @Override
    public String getCourseId() {
        return this.courseId;
    }

    @Override
    public List<String> getFormatIds() {
        return formatIds;
    }

    @Override
    public String getSubjectArea() {
        return this.subjectArea;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return this.isHonorsOffering;
    }

    @Override
    public List<String> getUnitsDeployment() {
        if(null == this.unitsDeployment) {
            this.unitsDeployment = new ArrayList<String>();
        }
        
        return this.unitsDeployment;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        if(null == this.unitsContentOwner) {
            this.unitsContentOwner = new ArrayList<String>();
        }
        
        return this.unitsContentOwner;    }

    @Override
    public Boolean getFinalExamStatus() {
        return this.finalExamStatus;
    }

    @Override
    public String getWaitlistTypeKey() {
        return this.waitlistTypeKey;
    }

    @Override
    public Integer getWaitlistMaximum() {
        return this.waitlistMaximum;
    }

    @Override
    public String getTermKey() {
        return this.termKey;
    }

    @Override
    public String getCourseCode() {
        return this.courseCode;
    }

    @Override
    public String getCourseNumberSuffix() {
        return this.courseNumberSuffix;
    }

    @Override
    public String getCourseTitle() {
        return this.courseTitle;
    }

    @Override
    public Integer getMaximumEnrollment() {
        return this.maximumEnrollment;
    }

    @Override
    public Integer getMinimumEnrollment() {
        return this.minimumEnrollment;
    }

    @Override
    public List<String> getJointOfferingIds() {
        if(null == this.jointOfferingIds) {
            this.jointOfferingIds = new ArrayList<String>();
        }
        
        return this.jointOfferingIds;
    }

    @Override
    public ResultComponent getCreditOptions() {
        return this.creditOptions;
    }

    @Override
    public String getGradeRosterLevel() {
        return this.gradeRosterLevel;
    }

    @Override
    public Boolean getHasWaitlist() {
        return this.hasWaitlist;
    }

    @Override
    public Boolean getIsWaitlistCheckinRequired() {
        return this.isWaitlistCheckinRequired;
    }
    

    @Override
    public TimeAmount getWaitlistCheckinFrequency() {
        return this.waitlistCheckinFrequency;
    }
    
    @Override
    public String getFundingSource() {
        return this.fundingSource;
    }

    @Override
    public List<CourseFeeInfo> getFees() {
        if(null == this.fees) {
            this.fees = new ArrayList<CourseFeeInfo>();
        }
        return this.fees;
    }

    @Override
    public List<CourseRevenueInfo> getRevenues() {
        if(null == this.revenues) {
            this.revenues = new ArrayList<CourseRevenueInfo>();
        }
        return this.revenues;
    }

    @Override
    public CourseExpenditureInfo getExpenditure() {
        return this.expenditure;        
    }

    @Override
    public Boolean getIsFinancialAidEligible() {
        return this.isFinancialAidEligible;
    }

    @Override
    public String getRegistrationOrderTypeKey() {
        return this.registrationOrderTypeKey;
    }
   
    @Override
    public List<String> getGradingOptions() {
        return this.gradingOptions;
    }
    
    @Override
    public List<LuiInstructorInfo> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<LuiInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    public void setRegistrationGroupIds(List<String> registrationGroupIds) {
        this.registrationGroupIds = registrationGroupIds;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setFormatIds(List<String> formatIds) {
        this.formatIds = formatIds;
    }
    
    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    public void setFinalExamStatus(Boolean finalExamStatus) {
        this.finalExamStatus = finalExamStatus;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    public void setWaitlistMaximum(Integer waitlistMaximum) {
        this.waitlistMaximum = waitlistMaximum;
    }

    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }

    public void setJointOfferingIds(List<String> jointOfferingIds) {
        this.jointOfferingIds = jointOfferingIds;
    }

    public void setCreditOptions(ResultComponent creditOptions) {
        this.creditOptions = creditOptions;
    }

    public void setGradingOptions(List<String> gradingOptions) {
        this.gradingOptions = gradingOptions;
    }

    public void setGradeRosterLevel(String gradeRosterLevel) {
        this.gradeRosterLevel = gradeRosterLevel;
    }

    public void setHasWaitlist(Boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }

    public void setIsWaitlistCheckinRequired(Boolean isWaitlistCheckinRequired) {
        this.isWaitlistCheckinRequired = isWaitlistCheckinRequired;
    }

    public void setWaitlistCheckinFrequency(TimeAmount waitlistCheckinFrequency) {
        this.waitlistCheckinFrequency = waitlistCheckinFrequency;
    }

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    public void setFees(List<CourseFeeInfo> fees) {
        this.fees = fees;
    }

    public void setRevenues(List<CourseRevenueInfo> revenues) {
        this.revenues = revenues;
    }

    public void setExpenditure(CourseExpenditureInfo expenditure) {
        this.expenditure = expenditure;
    }

    public void setIsFinancialAidEligible(Boolean isFinancialAidEligible) {
        this.isFinancialAidEligible = isFinancialAidEligible;
    }

    public void setRegistrationOrderTypeKey(String registrationOrderTypeKey) {
        this.registrationOrderTypeKey = registrationOrderTypeKey;
    }
        
}

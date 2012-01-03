/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
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
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.lu.dto.FeeInfo;
import org.kuali.student.r2.lum.lu.dto.RevenueInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "courseId",
        "formatIds", "termId", "courseOfferingCode", "courseNumberSuffix", "courseTitle", "isHonorsOffering",
        "instructors", "subjectArea", "unitsDeployment", "unitsContentOwner", "hasFinalExam", "maximumEnrollment",
        "minimumEnrollment", "jointOfferingIds", "creditOptions", "gradingOptionKeys", "gradeRosterLevelTypeKey",
        "hasWaitlist", "waitlistTypeKey", "waitlistMaximum", "isWaitlistCheckinRequired", "waitlistCheckinFrequency",
        "fundingSource", "fees", "revenues", "expenditure", "isFinancialAidEligible", "registrationOrderTypeKey",
        "meta", "attributes", "_futureElements"})
public class CourseOfferingInfo extends IdEntityInfo implements CourseOffering {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseId;

    @XmlElement
    private List<String> formatIds;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private Boolean isHonorsOffering;

    @XmlElement
    private List<OfferingInstructorInfo> instructors;

    @XmlElement
    private List<String> unitsDeployment;

    @XmlElement
    private List<String> unitsContentOwner;

    @XmlElement
    private Boolean hasFinalExam;

    @XmlElement
    private String waitlistTypeKey;

    @XmlElement
    private Integer waitlistMaximum;

    @XmlElement
    private String termId;

    @XmlElement
    private String courseOfferingCode;

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
    private ResultValuesGroupInfo creditOptions;

    @XmlElement
    private List<String> gradingOptionKeys;

    @XmlElement
    private String gradeRosterLevelTypeKey;

    @XmlElement
    private Boolean hasWaitlist;

    @XmlElement
    private Boolean isWaitlistCheckinRequired;

    @XmlElement
    private TimeAmountInfo waitlistCheckinFrequency;

    @XmlElement
    private String fundingSource;

    @XmlElement
    private List<FeeInfo> fees;

    @XmlElement
    private List<RevenueInfo> revenues;

    @XmlElement
    private ExpenditureInfo expenditure;

    @XmlElement
    private Boolean isFinancialAidEligible;

    @XmlElement
    private String registrationOrderTypeKey;

    @XmlAnyElement
    private List<Element> _futureElements;


    public CourseOfferingInfo() {
    }

    public CourseOfferingInfo(CourseOffering course) {

        super(course);

        if (null == course)
            return;

        this.courseId = course.getCourseId();
        this.formatIds = (null != course.getFormatIds()) ? new ArrayList<String>(course.getFormatIds()) : null;
        this.subjectArea = course.getSubjectArea();
        this.isHonorsOffering = course.getIsHonorsOffering();
        this.instructors = (null != course.getInstructors()) ? new ArrayList<OfferingInstructorInfo>(
                ((List<OfferingInstructorInfo>) course.getInstructors())) : new ArrayList<OfferingInstructorInfo>();
        this.unitsContentOwner = course.getUnitsContentOwner();
        this.hasFinalExam = course.getHasFinalExam();
        this.waitlistTypeKey = course.getWaitlistTypeKey();
        this.waitlistMaximum = course.getWaitlistMaximum();
        this.termId = course.getTermId();
        this.courseOfferingCode = course.getCourseOfferingCode();
        this.courseNumberSuffix = course.getCourseNumberSuffix();
        this.courseTitle = course.getCourseTitle();
        this.maximumEnrollment = course.getMaximumEnrollment();
        this.minimumEnrollment = course.getMinimumEnrollment();
        this.jointOfferingIds = (null != course.getJointOfferingIds()) ? new ArrayList<String>(
                course.getJointOfferingIds()) : null;
        this.creditOptions = (null != course.getCreditOptions()) ? new ResultValuesGroupInfo(course.getCreditOptions())
                : null;
        this.gradingOptionKeys = (null != course.getGradingOptionKeys()) ? new ArrayList<String>(
                course.getGradingOptionKeys()) : null;
        this.gradeRosterLevelTypeKey = course.getGradeRosterLevelTypeKey();
        this.hasWaitlist = (null != course.getHasWaitlist()) ? new Boolean(course.getHasWaitlist()) : null;
        this.isWaitlistCheckinRequired = course.getIsWaitlistCheckinRequired();
        this.waitlistCheckinFrequency = (null != course.getWaitlistCheckinFrequency()) ? new TimeAmountInfo(
                course.getWaitlistCheckinFrequency()) : null;
        this.fundingSource = course.getFundingSource();
        this.fees = (null != course.getFees()) ? new ArrayList<FeeInfo>((List<FeeInfo>) course.getFees()) : null;
        // TODO: Change this to r2 revenue with null check
        this.revenues = course.getRevenues();
        // TODO: Change this to r2 expenditure with null;
        this.expenditure = course.getExpenditure();
        this.isFinancialAidEligible = course.getIsFinancialAidEligible();
        this.registrationOrderTypeKey = course.getRegistrationOrderTypeKey();
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
        if (null == this.unitsDeployment) {
            this.unitsDeployment = new ArrayList<String>();
        }

        return this.unitsDeployment;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        if (null == this.unitsContentOwner) {
            this.unitsContentOwner = new ArrayList<String>();
        }

        return this.unitsContentOwner;
    }

    @Override
    public Boolean getHasFinalExam() {
        return this.hasFinalExam;
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
    public String getTermId() {
        return this.termId;
    }

    @Override
    public String getCourseOfferingCode() {
        return this.courseOfferingCode;
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
        if (null == this.jointOfferingIds) {
            this.jointOfferingIds = new ArrayList<String>();
        }

        return this.jointOfferingIds;
    }

    @Override
    public ResultValuesGroupInfo getCreditOptions() {
        return this.creditOptions;
    }

    @Override
    public String getGradeRosterLevelTypeKey() {
        return this.gradeRosterLevelTypeKey;
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
    public TimeAmountInfo getWaitlistCheckinFrequency() {
        return this.waitlistCheckinFrequency;
    }

    @Override
    public String getFundingSource() {
        return this.fundingSource;
    }

    @Override
    public List<FeeInfo> getFees() {
        if (null == this.fees) {
            this.fees = new ArrayList<FeeInfo>();
        }
        return this.fees;
    }

    @Override
    public List<RevenueInfo> getRevenues() {
        if (null == this.revenues) {
            this.revenues = new ArrayList<RevenueInfo>();
        }
        return this.revenues;
    }

    @Override
    public ExpenditureInfo getExpenditure() {
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
    public List<String> getGradingOptionKeys() {
        return this.gradingOptionKeys;
    }

    @Override
    public List<OfferingInstructorInfo> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<OfferingInstructorInfo> instructors) {
        this.instructors = instructors;
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

    public void setHasFinalExam(Boolean hasFinalExam) {
        this.hasFinalExam = hasFinalExam;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    public void setWaitlistMaximum(Integer waitlistMaximum) {
        this.waitlistMaximum = waitlistMaximum;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
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

    public void setCreditOptions(ResultValuesGroupInfo creditOptions) {
        this.creditOptions = creditOptions;
    }

    public void setGradingOptionKeys(List<String> gradingOptionKeys) {
        this.gradingOptionKeys = gradingOptionKeys;
    }

    public void setGradeRosterLevelTypeKey(String gradeRosterLevelTypeKey) {
        this.gradeRosterLevelTypeKey = gradeRosterLevelTypeKey;
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

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    public void setFees(List<FeeInfo> fees) {
        this.fees = fees;
    }

    public void setRevenues(List<RevenueInfo> revenues) {
        this.revenues = revenues;
    }

    public void setExpenditure(ExpenditureInfo expenditure) {
        this.expenditure = expenditure;
    }

    public void setIsFinancialAidEligible(Boolean isFinancialAidEligible) {
        this.isFinancialAidEligible = isFinancialAidEligible;
    }

    public void setRegistrationOrderTypeKey(String registrationOrderTypeKey) {
        this.registrationOrderTypeKey = registrationOrderTypeKey;
    }
}

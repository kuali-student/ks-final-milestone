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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.infc.OfferingInstructor;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingInfo", propOrder = {"id", "typeKey", "stateKey", "descr", "courseId",
        "termId", "courseOfferingCode", "courseNumberSuffix", "courseOfferingTitle", "isHonorsOffering",
        "instructors", "subjectArea", "unitsDeploymentOrgIds", "unitsContentOwnerOrgIds",  "maximumEnrollment",
        "minimumEnrollment", "jointOfferingIds", "creditOptionId", "gradingOptionId",
        "studentRegistrationOptionIds", "waitlistLevelTypeKey", "hasWaitlist", "waitlistTypeKey",
        "campusLocations", "finalExamType", "isEvaluated", "fundingSource", "isFeeAtActivityOffering",
        "isFinancialAidEligible", "courseOfferingURL", "meta", "attributes", "_futureElements"})

public class CourseOfferingInfo extends IdNamelessEntityInfo  implements CourseOffering {

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
    private String courseOfferingCode;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    private String subjectArea;

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
    private String creditOptionId;

    @XmlElement
    private String gradingOptionId;

    @XmlElement
    private List<String> studentRegistrationOptionIds;

    @XmlElement
    private String waitlistTypeKey;

    @XmlElement
    private Boolean hasWaitlist;

    @XmlElement
    private String waitlistLevelTypeKey;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Integer minimumEnrollment;


    @XmlElement
    private String finalExamType;

    @XmlElement
    private List<String> jointOfferingIds;

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
        this.courseOfferingCode = offering.getCourseOfferingCode();
        this.courseNumberSuffix = offering.getCourseNumberSuffix();
        this.subjectArea = offering.getSubjectArea();
        this.isHonorsOffering = offering.getIsHonorsOffering();

        this.instructors = new ArrayList<OfferingInstructorInfo>();
        for (OfferingInstructor instructor : offering.getInstructors()) {
            this.instructors.add(new OfferingInstructorInfo(instructor));
        }

        this.unitsDeploymentOrgIds = offering.getUnitsDeploymentOrgIds();
        this.unitsContentOwnerOrgIds = offering.getUnitsContentOwnerOrgIds();
        this.creditOptionId = offering.getCreditOptionId();
        this.gradingOptionId = offering.getGradingOptionId();
        this.studentRegistrationOptionIds = (null != offering.getStudentRegistrationOptionIds()) ? new ArrayList<String>(
                offering.getStudentRegistrationOptionIds()) : null;


        this.campusLocations = (null != offering.getCampusLocations()) ? new ArrayList<String>(offering.getCampusLocations()) : null;

        this.waitlistTypeKey = offering.getWaitlistTypeKey();
        this.maximumEnrollment = offering.getMaximumEnrollment();
        this.minimumEnrollment = offering.getMinimumEnrollment();

        this.jointOfferingIds = (null != offering.getJointOfferingIds()) ? new ArrayList<String>(
                offering.getJointOfferingIds()) : null;


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
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getCourseOfferingCode() {
        return this.courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    @Override
    public String getSubjectArea() {
        return this.subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    @Override
    public String getCourseNumberSuffix() {
        return this.courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    @Override
    public String getCourseOfferingTitle() {
        return this.courseOfferingTitle;
    }

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return this.isHonorsOffering;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
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
    public List<String> getJointOfferingIds() {
        if (null == jointOfferingIds) {
            jointOfferingIds = new ArrayList<String>();
        }

        return jointOfferingIds;
    }

    public void setJointOfferingIds(List<String> jointOfferingIds) {
        this.jointOfferingIds = jointOfferingIds;
    }

    @Override
    public String getGradingOptionId() {
        return this.gradingOptionId;
    }

    public void setGradingOptionId(String gradingOptionId) {
        this.gradingOptionId = gradingOptionId;
    }


    public List<String> getStudentRegistrationOptionIds() {
        if(studentRegistrationOptionIds == null){
            studentRegistrationOptionIds = new ArrayList<String>();
        }
        return studentRegistrationOptionIds;
    }

    public void setStudentRegistrationOptionIds(List<String> studentRegistrationOptionIds) {
        this.studentRegistrationOptionIds = studentRegistrationOptionIds;
    }

    @Override
    public String getCreditOptionId() {
        return creditOptionId;
    }

    public void setCreditOptionId(String creditOptionId) {
        this.creditOptionId = creditOptionId;
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
        if (null == unitsDeploymentOrgIds) {
            unitsDeploymentOrgIds = new ArrayList<String>();
        }
        return unitsDeploymentOrgIds;
    }

    public void setUnitsDeploymentOrgIds(List<String> unitsDeploymentOrgIds) {
        this.unitsDeploymentOrgIds = unitsDeploymentOrgIds;
    }

    @Deprecated
    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeploymentOrgIds = unitsDeployment;
    }

    @Override
    public List<String> getUnitsContentOwnerOrgIds() {
        if (null == unitsContentOwnerOrgIds) {
            unitsContentOwnerOrgIds = new ArrayList<String>();
        }

        return unitsContentOwnerOrgIds;
    }
    
    public void setUnitsContentOwnerOrgIds(List<String> unitsContentOwnerOrgIds) {
        this.unitsContentOwnerOrgIds = unitsContentOwnerOrgIds;
    }

    @Deprecated
    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwnerOrgIds = unitsContentOwner;
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
    public Boolean getFinancialAidEligible() {
        return isFinancialAidEligible;
    }

    public void setFinancialAidEligible(Boolean financialAidEligible) {
        isFinancialAidEligible = financialAidEligible;
    }

    @Override
    public List<String> getCampusLocations() {
        if (null == campusLocations) {
            campusLocations = new ArrayList<String>();
        }
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    @Override
    public Boolean getHonorsOffering() {
        return isHonorsOffering;
    }

    public void setHonorsOffering(Boolean honorsOffering) {
        isHonorsOffering = honorsOffering;
    }

    @Override
    public String getFinalExamType() {
          return this.finalExamType;
    }

    public void setFinalExamType(String finalExamType) {
        this.finalExamType = finalExamType;
    }

    @Override
    public Boolean getIsFeeAtActivityOffering() {
        return isFeeAtActivityOffering;
    }

    public void setFeeAtActivityOffering(Boolean feeAtActivityOffering) {
        isFeeAtActivityOffering = feeAtActivityOffering;
    }

    @Override
    public Boolean getIsEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(Boolean evaluated) {
        isEvaluated = evaluated;
    }

    @Override
    public String getCourseOfferingURL() {
        return courseOfferingURL;
    }

    public void setCourseOfferingURL(String courseOfferingURL) {
        this.courseOfferingURL = courseOfferingURL;
    }
}

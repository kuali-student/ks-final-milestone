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
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingInfo", propOrder = {"id", "typeKey", "stateKey", "descr", "courseId",
        "termId", "courseOfferingCode", "courseNumberSuffix", "courseOfferingTitle", "isHonorsOffering",
        "instructors", "subjectArea", "unitsDeploymentOrgIds", "unitsContentOwnerOrgIds",  "maximumEnrollment",
        "minimumEnrollment", "jointOfferingIds", "creditOptionIds", "gradingOptionIds", "waitlistLevelTypeKey",
        "hasWaitlist", "waitlistTypeKey","campusLocations", "hasFinalExam", "isEvaluated",
        "fundingSource", "isFeeAtActivityOffering", "isFinancialAidEligible",
        "meta", "attributes", "_futureElements"})

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
    private List<String> creditOptionIds;

    @XmlElement
    private List<String> gradingOptionIds;

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
    private Boolean hasFinalExam;

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

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new CourseOfferingInfo.
     */
    public CourseOfferingInfo() {
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
        this.creditOptionIds =offering.getCreditOptionIds();
        this.gradingOptionIds = (null != offering.getGradingOptionIds()) ? new ArrayList<String>(
                offering.getGradingOptionIds()) : null;
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

        this.hasFinalExam = offering.getHasFinalExam();
    }

    @Override
    public String getCourseId() {
        return this.courseId;
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
    public List<String> getUnitsDeploymentOrgIds() {
        if (null == this.unitsDeploymentOrgIds) {
            this.unitsDeploymentOrgIds = new ArrayList<String>();
        }

        return this.unitsDeploymentOrgIds;
    }

    @Override
    public List<String> getUnitsContentOwnerOrgIds() {
        if (null == this.unitsContentOwnerOrgIds) {
            this.unitsContentOwnerOrgIds = new ArrayList<String>();
        }

        return this.unitsContentOwnerOrgIds;
    }


    @Override
    public String getWaitlistTypeKey() {
        return this.waitlistTypeKey;
    }

    @Override
    public String getWaitlistLevelTypeKey() {
        return this.waitlistLevelTypeKey;
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
    public String getCourseOfferingTitle() {
        return this.courseOfferingTitle;
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
    public List<String> getCreditOptionIds() {
        return this.creditOptionIds;
    }



    @Override
    public Boolean getHasWaitlist() {
        return this.hasWaitlist;
    }


    @Override
    public String getFundingSource() {
        return this.fundingSource;
    }




    @Override
    public Boolean getIsFinancialAidEligible() {
        return this.isFinancialAidEligible;
    }

    @Override
    public List<String> getGradingOptionIds() {
        return this.gradingOptionIds;
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

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeploymentOrgIds = unitsDeployment;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwnerOrgIds = unitsContentOwner;
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

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
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

    public void setCreditOptionIds(List<String> creditOptionIds) {
        this.creditOptionIds = creditOptionIds;
    }

    public void setGradingOptionIds(List<String> gradingOptionIds) {
        this.gradingOptionIds = gradingOptionIds;
    }


    public void setHasWaitlist(Boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }


    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }



    public void setIsFinancialAidEligible(Boolean isFinancialAidEligible) {
        this.isFinancialAidEligible = isFinancialAidEligible;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }
    @Override
    public Boolean getHonorsOffering() {
        return isHonorsOffering;
    }

    public void setHonorsOffering(Boolean honorsOffering) {
        isHonorsOffering = honorsOffering;
    }

    @Override
    public List<String> getCampusLocations() {
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    @Override
    public Boolean getFinancialAidEligible() {
        return isFinancialAidEligible;
    }

    public void setFinancialAidEligible(Boolean financialAidEligible) {
        isFinancialAidEligible = financialAidEligible;
    }

    @Override
    public Boolean getHasFinalExam(){
          return this.hasFinalExam;
    }

    public void setUnitsDeploymentOrgIds(List<String> unitsDeploymentOrgIds) {
        this.unitsDeploymentOrgIds = unitsDeploymentOrgIds;
    }

    public void setUnitsContentOwnerOrgIds(List<String> unitsContentOwnerOrgIds) {
        this.unitsContentOwnerOrgIds = unitsContentOwnerOrgIds;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    public void setWaitlistLevelTypeKey(String waitlistLevelTypeKey) {
        this.waitlistLevelTypeKey = waitlistLevelTypeKey;
    }

    public void setHasFinalExam(Boolean hasFinalExam) {
        this.hasFinalExam = hasFinalExam;
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
}

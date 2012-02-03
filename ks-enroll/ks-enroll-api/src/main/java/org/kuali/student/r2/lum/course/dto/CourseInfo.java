/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.course.infc.CourseCrossListing;
import org.kuali.student.r2.lum.course.infc.CourseFee;
import org.kuali.student.r2.lum.course.infc.CourseJoint;
import org.kuali.student.r2.lum.course.infc.CourseRevenue;
import org.kuali.student.r2.lum.course.infc.Format;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.infc.CluInstructor;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - sambit don't forget to fill
 * this in.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlType(name = "CourseInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name",
    "descr",
    "code",
    "courseNumberSuffix",
    "level",
    "courseTitle",
    "transcriptTitle",
    "formats",
    "termsOffered",
    "duration",
    "joints",
    "crossListings",
    "variations",
    "subjectArea",
    "campusLocations",
    "outOfClassHours",
    "primaryInstructor",
    "instructors",
    "unitsDeployment",
    "feeJustification",
    "unitsContentOwner",
    "fees",
    "revenues",
    "expenditure",
    "courseSpecificLOs",
    "gradingOptions",
    "creditOptions",
    "specialTopicsCourse",
    "pilotCourse",
    "startTerm",
    "endTerm",
    "effectiveDate",
    "expirationDate",
    "versionInfo",
    "meta",
    "attributes",
    "versionInfo",
    "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseInfo extends IdEntityInfo implements Course, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String code;
    @XmlElement
    private String courseNumberSuffix;
    @XmlElement
    private String level;
    @XmlElement
    private String courseTitle;
    @XmlElement
    private String transcriptTitle;
    @XmlElement
    private List<FormatInfo> formats;
    @XmlElement
    private List<String> termsOffered;
    @XmlElement
    private TimeAmountInfo duration;
    @XmlElement
    private List<CourseJointInfo> joints;
    @XmlElement
    private List<CourseCrossListingInfo> crossListings;
    @XmlElement
    private List<CourseVariationInfo> variations;
    @XmlElement
    private String subjectArea;
    @XmlElement
    private List<String> campusLocations;
    @XmlElement
    private AmountInfo outOfClassHours;
    @XmlElement
    private CluInstructorInfo primaryInstructor;
    @XmlElement
    private List<CluInstructorInfo> instructors;
    @XmlElement
    private List<String> unitsDeployment;
    @XmlElement
    private RichTextInfo feeJustification;
    @XmlElement
    private List<String> unitsContentOwner;
    private List<CourseFeeInfo> fees;
    @XmlElement
    private List<CourseRevenueInfo> revenues;
    @XmlElement
    private CourseExpenditureInfo expenditure;
    @XmlElement
    private List<LoDisplayInfo> courseSpecificLOs;
    @XmlElement
    private List<String> gradingOptions;
    @XmlElement
    private List<String> creditOptions;
    @XmlElement
    private boolean specialTopicsCourse;
    @XmlElement
    private boolean pilotCourse;
    @XmlElement
    private String startTerm;
    @XmlElement
    private String endTerm;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlElement
    private VersionInfo versionInfo;
    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseInfo(Course courseInfo) {
        super(courseInfo);
        if (courseInfo != null) {
            this.code = courseInfo.getCode();

            this.courseNumberSuffix = courseInfo.getCourseNumberSuffix();

            this.level = courseInfo.getLevel();

            this.courseTitle = courseInfo.getCourseTitle();

            this.transcriptTitle = courseInfo.getTranscriptTitle();

            List<FormatInfo> formatList = new ArrayList<FormatInfo>();

            for (Format format : courseInfo.getFormats()) {

                formatList.add(new FormatInfo(format));
            }

            this.termsOffered = new ArrayList<String>(courseInfo.getTermsOffered());

            this.duration = new TimeAmountInfo(courseInfo.getDuration());

            List<CourseJointInfo> courseJointList = new ArrayList<CourseJointInfo>();

            for (CourseJoint courseJoint : courseInfo.getJoints()) {

                courseJointList.add(new CourseJointInfo(courseJoint));
            }

            this.joints = courseJointList;

            this.crossListings = new ArrayList<CourseCrossListingInfo>();

            List<CourseCrossListingInfo> courseCrossListingList = new ArrayList<CourseCrossListingInfo>();

            for (CourseCrossListing courseCrossListing : courseInfo.getCrossListings()) {

                courseCrossListingList.add(new CourseCrossListingInfo(courseCrossListing));
            }

            this.variations = new ArrayList<CourseVariationInfo>();

            this.subjectArea = courseInfo.getSubjectArea();
            this.campusLocations = new ArrayList<String>(courseInfo.getCampusLocations());

            this.outOfClassHours = new AmountInfo(courseInfo.getOutOfClassHours());

            this.primaryInstructor = new CluInstructorInfo(courseInfo.getPrimaryInstructor());

            List<CluInstructorInfo> instructorList = new ArrayList<CluInstructorInfo>();

            for (CluInstructor cluInstructor : courseInfo.getInstructors()) {

                instructorList.add(new CluInstructorInfo(cluInstructor));
            }

            this.instructors = instructorList;

            this.unitsDeployment = new ArrayList<String>(courseInfo.getUnitsDeployment());

            this.feeJustification = new RichTextInfo(courseInfo.getFeeJustification());

            this.unitsContentOwner = new ArrayList<String>(courseInfo.getUnitsContentOwner());

            List<CourseFeeInfo> courseFeeList = new ArrayList<CourseFeeInfo>();
            for (CourseFee courseFee : courseInfo.getFees()) {

                courseFeeList.add(new CourseFeeInfo(courseFee));
            }

            this.fees = courseFeeList;

            List<CourseRevenueInfo> courseRevList = new ArrayList<CourseRevenueInfo>();
            for (CourseRevenue courseRevenue : courseInfo.getRevenues()) {

                courseRevList.add(new CourseRevenueInfo(courseRevenue));
            }

            this.revenues = courseRevList;

            this.expenditure = new CourseExpenditureInfo(courseInfo.getExpenditure());

            List<LoDisplayInfo> courseLos = new ArrayList<LoDisplayInfo>();

            for (LoDisplay courseRevenue : courseInfo.getCourseSpecificLOs()) {

                courseLos.add(new LoDisplayInfo(courseRevenue));
            }

            this.courseSpecificLOs = courseLos;

            this.gradingOptions = new ArrayList<String>(courseInfo.getGradingOptions());

            this.creditOptions = new ArrayList<String>(courseInfo.getCreditOptions());

            this.specialTopicsCourse = courseInfo.isSpecialTopicsCourse();

            this.pilotCourse = courseInfo.isPilotCourse();
            this.startTerm = courseInfo.getStartTerm();

            this.endTerm = courseInfo.getEndTerm();

            this.effectiveDate = new Date(courseInfo.getEffectiveDate().getTime());

            this.expirationDate = new Date(courseInfo.getExpirationDate().getTime());
            this.transcriptTitle = courseInfo.getTranscriptTitle();
            this.code = courseInfo.getCode();

            this.unitsContentOwner = courseInfo.getUnitsContentOwner() != null
                    ? new ArrayList<String>(courseInfo.getUnitsContentOwner())
                    : new ArrayList<String>();

            this.versionInfo = new VersionInfo(courseInfo.getVersionInfo());

        }
    }

   
    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    /**
     * A code that indicates what level 100, 200 or upper division, lower
     * division etc
     * 
     * @return
     */
    @Override
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }

    @Override
    public List<FormatInfo> getFormats() {
        if (formats == null) {
            formats = new ArrayList<FormatInfo>(0);
        }
        return formats;
    }

    public void setFormats(List<FormatInfo> formats) {
        this.formats = formats;
    }

    @Override
    public List<String> getTermsOffered() {
        if (termsOffered == null) {
            termsOffered = new ArrayList<String>(0);
        }
        return termsOffered;
    }

    public void setTermsOffered(List<String> termsOffered) {
        this.termsOffered = termsOffered;
    }

    @Override
    public TimeAmountInfo getDuration() {
        return duration;
    }

    public void setDuration(TimeAmountInfo duration) {
        this.duration = duration;
    }

    @Override
    public List<CourseJointInfo> getJoints() {
        if (joints == null) {
            joints = new ArrayList<CourseJointInfo>(0);
        }
        return joints;
    }

    public void setJoints(List<CourseJointInfo> joints) {
        this.joints = joints;
    }

    @Override
    public List<CourseCrossListingInfo> getCrossListings() {
        if (crossListings == null) {
            crossListings = new ArrayList<CourseCrossListingInfo>(0);
        }
        return crossListings;
    }

    public void setCrossListings(List<CourseCrossListingInfo> crossListings) {
        this.crossListings = crossListings;
    }

    @Override
    public List<CourseVariationInfo> getVariations() {
        if (variations == null) {
            variations = new ArrayList<CourseVariationInfo>(0);
        }
        return variations;
    }

    public void setVariations(List<CourseVariationInfo> variations) {
        this.variations = variations;
    }

    @Override
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    @Override
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>(0);
        }
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    @Override
    public AmountInfo getOutOfClassHours() {
        return outOfClassHours;
    }

    public void setOutOfClassHours(AmountInfo outOfClassHours) {
        this.outOfClassHours = outOfClassHours;
    }

    @Override
    public CluInstructorInfo getPrimaryInstructor() {
        return primaryInstructor;
    }

    public void setPrimaryInstructor(CluInstructorInfo primaryInstructor) {
        this.primaryInstructor = primaryInstructor;
    }

    @Override
    public List<CluInstructorInfo> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<CluInstructorInfo>(0);
        }
        return instructors;
    }

    public void setInstructors(List<CluInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    @Override
    public List<String> getUnitsDeployment() {
        if (unitsDeployment == null) {
            unitsDeployment = new ArrayList<String>(0);
        }
        return unitsDeployment;
    }

    @Override
    public RichTextInfo getFeeJustification() {
        return feeJustification;

    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    public void setFeeJustification(RichTextInfo feeJustification) {
        this.feeJustification = feeJustification;

    }

    @Override
    public List<String> getUnitsContentOwner() {
        if (unitsContentOwner == null) {
            unitsContentOwner = new ArrayList<String>(0);
        }
        return unitsContentOwner;
    }

    @Override
    public List<CourseFeeInfo> getFees() {
        if (fees == null) {
            fees = new ArrayList<CourseFeeInfo>(0);
        }
        return fees;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    public void setFees(List<CourseFeeInfo> fees) {
        this.fees = fees;
    }

    @Override
    public List<CourseRevenueInfo> getRevenues() {
        if (revenues == null) {
            revenues = new ArrayList<CourseRevenueInfo>(0);
        }
        return revenues;
    }

    public void setRevenues(List<CourseRevenueInfo> revenues) {
        this.revenues = revenues;
    }

    @Override
    public CourseExpenditureInfo getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(CourseExpenditureInfo expenditure) {
        this.expenditure = expenditure;
    }

    @Override
    public List<LoDisplayInfo> getCourseSpecificLOs() {
        if (courseSpecificLOs == null) {
            courseSpecificLOs = new ArrayList<LoDisplayInfo>(0);
        }
        return courseSpecificLOs;
    }

    public void setCourseSpecificLOs(List<LoDisplayInfo> courseSpecificLOs) {
        this.courseSpecificLOs = courseSpecificLOs;
    }

    @Override
    public List<String> getGradingOptions() {
        if (gradingOptions == null) {
            gradingOptions = new ArrayList<String>(0);
        }
        return gradingOptions;
    }

    public void setGradingOptions(List<String> gradingOptions) {
        this.gradingOptions = gradingOptions;
    }

    @Override
    public List<String> getCreditOptions() {
        if (creditOptions == null) {
            creditOptions = new ArrayList<String>(0);
        }
        return creditOptions;
    }

    public void setCreditOptions(List<String> creditOptions) {
        this.creditOptions = creditOptions;
    }

    @Override
    public Boolean isSpecialTopicsCourse() {
        return specialTopicsCourse;
    }

    public void setSpecialTopicsCourse(boolean specialTopicsCourse) {
        this.specialTopicsCourse = specialTopicsCourse;
    }

    @Override
    public Boolean isPilotCourse() {
        return pilotCourse;
    }

    public void setPilotCourse(boolean pilotCourse) {
        this.pilotCourse = pilotCourse;
    }

    @Override
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    @Override
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }
}

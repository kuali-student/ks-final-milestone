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
import org.kuali.student.r2.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.lu.infc.CluInstructor;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - sambit don't forget to fill
 * this in.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */

@XmlType(name = "CourseInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "code", "courseNumberSuffix", "level", "courseTitle", "transcriptTitle", "formats", "termsOffered",
        "duration", "joints", "crossListings", "variations", "subjectArea", "campusLocations", "outOfClassHours", "primaryInstructor", "instructors", "unitsDeployment", "feeJustification",
        "unitsContentOwner", "fees", "revenues", "expenditure", "courseSpecificLOs", "gradingOptionIds", "creditOptionKeys", "specialTopicsCourse", "pilotCourse", "startTerm", "endTerm",
        "effectiveDate", "expirationDate", "versionInfo", "meta", "attributes", "versionInfo", "_futureElements"})
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
    private List<String> gradingOptionIds;

    @XmlElement
    private List<String> creditOptionKeys;

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

            this.gradingOptionIds = new ArrayList<String>(courseInfo.getGradingOptionIds());

            this.creditOptionKeys = new ArrayList<String>(courseInfo.getCreditOptionKeys());

            this.specialTopicsCourse = courseInfo.isSpecialTopicsCourse();

            this.pilotCourse = courseInfo.isPilotCourse();
            this.startTerm = courseInfo.getStartTerm();

            this.endTerm = courseInfo.getEndTerm();

            this.effectiveDate = new Date(courseInfo.getEffectiveDate().getTime());

            this.expirationDate = new Date(courseInfo.getExpirationDate().getTime());
            this.transcriptTitle = courseInfo.getTranscriptTitle();
            this.code = courseInfo.getCode();

            this.unitsContentOwner = courseInfo.getUnitsContentOwner() != null ? new ArrayList<String>(courseInfo.getUnitsContentOwner()) : new ArrayList<String>();
            
            this.versionInfo =  new VersionInfo(courseInfo.getVersionInfo());

        }
    }

    /**
     * The composite string that is used to officially reference or publish the
     * CLU. Note it may have an internal structure that each Institution may
     * want to enforce. This structure may be composed from the other parts of
     * the structure such as Level amp; Division, but may include items such as
     * cluType.
     */
    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number. Ex. at MIT we might map Division to subject
     * area(Ex:6) but overall we need to say the code is 6.120. This field would
     * represent the 120 part.
     */
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

    /**
     * Abbreviated name of the Course
     */
    @Override
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Information related to the official identification of the credit course,
     * typically in human readable form. Used to officially reference or
     * publish.
     */
    @Override
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }

    /**
     * 
     */
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

    /**
     * Terms in which this Course is typically offered.
     */
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

    /**
     * The standard duration of the Course.
     */
    @Override
    public TimeAmountInfo getDuration() {
        return duration;
    }

    public void setDuration(TimeAmountInfo duration) {
        this.duration = duration;
    }

    /**
     * 
     */
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

    /**
     * 
     */
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

    /**
     * 
     */
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

    /**
     * The Study Subject Area is used to identify the area of study associated
     * with the course. It may be a general study area (e.g. Chemistry) or very
     * specific (e.g. Naval Architecture).
     */
    @Override
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    /**
     * Places where this course might be offered
     */
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

    /**
     * The expected level of out of class time commitment between the student
     * and the course.
     */
    @Override
    public AmountInfo getOutOfClassHours() {
        return outOfClassHours;
    }

    public void setOutOfClassHours(AmountInfo outOfClassHours) {
        this.outOfClassHours = outOfClassHours;
    }

    /**
     * Primary potential instructor for the clu. This is primarily for use in
     * advertising the course and may not be the actual instructor.
     */
    @Override
    public CluInstructorInfo getPrimaryInstructor() {
        return primaryInstructor;
    }

    public void setPrimaryInstructor(CluInstructorInfo primaryInstructor) {
        this.primaryInstructor = primaryInstructor;
    }

    /**
     * Instructors associated with this course.
     */
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

    /**
     * Narrative description of overall course fee justification.
     */
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

    /**
     * Fees information associated with this Course.
     */
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

    /**
     * Revenue information associated with this Course.
     */

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

    /**
     * Expenditure information associated with this Course.
     */
    @Override
    public CourseExpenditureInfo getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(CourseExpenditureInfo expenditure) {
        this.expenditure = expenditure;
    }

    /**
     * Learning Objectives associated with this Course.
     */
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

    /**
     * Grading opitons available for the course
     */
    @Override
    public List<String> getGradingOptionIds() {
        if (gradingOptionIds == null) {
            gradingOptionIds = new ArrayList<String>(0);
        }
        return gradingOptionIds;
    }

    public void setGradingOptions(List<String> gradingOptions) {
        this.gradingOptionIds = gradingOptions;
    }

    /**
     * Credit outcomes from taking the course
     */
    @Override
    public List<String> getCreditOptionKeys() {
        if (creditOptionKeys == null) {
            creditOptionKeys = new ArrayList<String>(0);
        }
        return creditOptionKeys;
    }

    public void setCreditOptions(List<String> creditOptions) {
        this.creditOptionKeys = creditOptions;
    }

    /**
     * Flag to indicate the course as a special topics course
     */
    @Override
    public boolean isSpecialTopicsCourse() {
        return specialTopicsCourse;
    }

    public void setSpecialTopicsCourse(boolean specialTopicsCourse) {
        this.specialTopicsCourse = specialTopicsCourse;
    }

    /**
     * Flag to indicate a one-time or pilot course, which is likely to have
     * expedited approval process
     */
    @Override
    public boolean isPilotCourse() {
        return pilotCourse;
    }

    public void setPilotCourse(boolean pilotCourse) {
        this.pilotCourse = pilotCourse;
    }

    /**
     * The first academic time period that this Course would be effective.
     */
    @Override
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    /**
     * The last academic time period that this Course would be effective.
     */
    @Override
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    /**
     * Date and time the Course became effective. This is a similar concept to
     * the effective date on enumerated values. When an expiration date has been
     * specified, this field must be less than or equal to the expiration date.
     */
    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this Course expires. This is a similar concept to the
     * expiration date on enumerated values. If specified, this should be
     * greater than or equal to the effective date. If this field is not
     * specified, then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     */
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

    public void setGradingOptionIds(List<String> gradingOptionIds) {
        this.gradingOptionIds = gradingOptionIds;
    }

    public void setCreditOptionKeys(List<String> creditOptionKeys) {
        this.creditOptionKeys = creditOptionKeys;
    }

}

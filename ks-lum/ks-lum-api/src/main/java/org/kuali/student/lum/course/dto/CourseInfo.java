/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;

/**
 * Detailed information about a single course.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue May 18 11:30:53 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/courseInfo+Structure">CourseInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    private String courseTitle;

    @XmlElement
    private String transcriptTitle;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private List<FormatInfo> formats;

    @XmlElement
    private List<String> termsOffered;

    @XmlElement
    private String firstExpectedOffering;

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
    private List<String> gradingOptions;

    @XmlElement
    private List<String> creditOptions;
    
    @XmlElement
    private String department;

    @XmlElement
    private List<String> academicSubjectOrgs;

    @XmlElement
    private CluInstructorInfo primaryInstructor;

    @XmlElement
    private List<CluInstructorInfo> instructors;
    
    @XmlElement
    private CluFeeInfo feeInfo;

    @XmlElement
    private List<LoDisplayInfo> courseSpecificLOs;

    @XmlElement
    private boolean isSpecialTopicsCourse;

    @XmlElement
    private boolean isPilotCourse;
    
    @XmlElement
    private String startTerm;

    @XmlElement
    private String endTerm;
    
    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * The composite string that is used to officially reference or publish the CLU. Note it may have an internal structure that each Institution may want to enforce. This structure may be composed from the other parts of the structure such as Level amp; Division, but may include items such as cluType.
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * The "extra" portion of the code, which usually corresponds with the most detailed part of the number. Ex. at MIT we might map Division to subject area(Ex:6) but overall we need to say the code is 6.120. This field would represent the 120 part.
     */
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    /**
     * Abbreviated name of the Course
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Information related to the official identification of the credit course, typically in human readable form. Used to officially reference or publish.
     */
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }

    /**
     * Narrative description of the Course.
     */
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    /**
     * 
     */
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
     * The expected first academic time period that this course would be effective.
     */
    public String getFirstExpectedOffering() {
        return firstExpectedOffering;
    }

    public void setFirstExpectedOffering(String firstExpectedOffering) {
        this.firstExpectedOffering = firstExpectedOffering;
    }

    /**
     * The standard duration of the Course.
     */
    public TimeAmountInfo getDuration() {
        return duration;
    }

    public void setDuration(TimeAmountInfo duration) {
        this.duration = duration;
    }

    /**
     * 
     */
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
     * The Study Subject Area is used to identify the area of study associated with the course. It may be a general study area (e.g. Chemistry) or very specific (e.g. Naval Architecture).
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    /**
     * Places where this course might be offered
     */
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>(0);
        }
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }        
    
    public AmountInfo getOutOfClassHours() {
        return outOfClassHours;
    }

    public void setOutOfClassHours(AmountInfo outOfClassHours) {
        this.outOfClassHours = outOfClassHours;
    }

    /**
     * @return the gradingOptions
     */
    public List<String> getGradingOptions() {
        return gradingOptions;
    }

    /**
     * @param gradingOptions the gradingOptions to set
     */
    public void setGradingOptions(List<String> gradingOptions) {
        this.gradingOptions = gradingOptions;
    }

    /**
     * @return the creditOptions
     */
    public List<String> getCreditOptions() {
        return creditOptions;
    }

    /**
     * @param creditOptions the creditOptions to set
     */
    public void setCreditOptions(List<String> creditOptions) {
        this.creditOptions = creditOptions;
    }

    /**
     * The primary organization (typically, an academic department) with administrative oversight over the Course.
     */
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * The organizations that represents the Subject area of the course.
     */
    public List<String> getAcademicSubjectOrgs() {
        if (academicSubjectOrgs == null) {
            academicSubjectOrgs = new ArrayList<String>(0);
        }
        return academicSubjectOrgs;
    }

    public void setAcademicSubjectOrgs(List<String> academicSubjectOrgs) {
        this.academicSubjectOrgs = academicSubjectOrgs;
    }

    /**
     * Primary potential instructor for the clu. This is primarily for use in advertising the course and may not be the actual instructor.
     */
    public CluInstructorInfo getPrimaryInstructor() {
        return primaryInstructor;
    }

    public void setPrimaryInstructor(CluInstructorInfo primaryInstructor) {
        this.primaryInstructor = primaryInstructor;
    }
    
    public List<CluInstructorInfo> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<CluInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    /**
     * Fee information associated with this Course.
     */
    public CluFeeInfo getFeeInfo() {
        return feeInfo;
    }

    public void setFeeInfo(CluFeeInfo feeInfo) {
        this.feeInfo = feeInfo;
    }

    /**
     * Learning Objectives associated with this Course.
     */
    public List<LoDisplayInfo> getCourseSpecificLOs() {
        if (courseSpecificLOs == null) {
            courseSpecificLOs = new ArrayList<LoDisplayInfo>(0);
        }
        return courseSpecificLOs;
    }

    public void setCourseSpecificLOs(List<LoDisplayInfo> courseSpecificLOs) {
        this.courseSpecificLOs = courseSpecificLOs;
    }
    
    public boolean isSpecialTopicsCourse() {
        return isSpecialTopicsCourse;
    }

    public void setSpecialTopicsCourse(boolean isSpecialTopicsCourse) {
        this.isSpecialTopicsCourse = isSpecialTopicsCourse;
    }
        
    public boolean isPilotCourse() {
        return isPilotCourse;
    }

    public void setPilotCourse(boolean isPilotCourse) {
        this.isPilotCourse = isPilotCourse;
    }

    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    /**
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the course. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value. This field may not be updated through updating this structure and must instead be updated through a dedicated operation.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a Course. This is optional, due to the identifier being set at the time of creation. Once the Course has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
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
import org.kuali.student.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;

/**
 * Detailed information about a single course.
 * For specific usage, check the specific service(s) implementation(s)
 *
 * @Author KSContractMojo
 * @Author Daniel Epstein
 * @Since Mon Jul 26 14:12:05 EDT 2010
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
    private List<ResultComponentInfo> creditOptions;

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
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;
    
    @XmlElement
    private VersionInfo versionInfo;
    
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

    /**
     * The expected level of out of class time commitment between the student and the course.
     */
    public AmountInfo getOutOfClassHours() {
        return outOfClassHours;
    }

    public void setOutOfClassHours(AmountInfo outOfClassHours) {
        this.outOfClassHours = outOfClassHours;
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

    /**
     * Instructors associated with this course.
     */
    public List<CluInstructorInfo> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<CluInstructorInfo>(0);
        }
        return instructors;
    }

    public void setInstructors(List<CluInstructorInfo> instructors) {
        this.instructors = instructors;
    }


    public List<String> getUnitsDeployment() {
    	if(unitsDeployment == null){
    		unitsDeployment = new ArrayList<String>(0);
    	}
        return unitsDeployment;
    }
    
    /**
     * Narrative description of overall course fee justification.
     */
    public RichTextInfo getFeeJustification() {
       return feeJustification;

    }


    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }
    
    public void setFeeJustification(RichTextInfo feeJustification) {
        this.feeJustification = feeJustification;

    }

    public List<String> getUnitsContentOwner() {
    	if(unitsContentOwner == null){
    		unitsContentOwner = new ArrayList<String>(0);
    	}
    	return unitsContentOwner;
    }
    
    /**
     * Fees information associated with this Course.
     */
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
    public CourseExpenditureInfo getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(CourseExpenditureInfo expenditure) {
        this.expenditure = expenditure;
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

    /**
     * Grading opitons available for the course
     */
    public List<String> getGradingOptions() {
        if (gradingOptions == null) {
            gradingOptions = new ArrayList<String>(0);
        }
        return gradingOptions;
    }

    public void setGradingOptions(List<String> gradingOptions) {
        this.gradingOptions = gradingOptions;
    }

    /**
     * Credit outcomes from taking the course
     */
    public List<ResultComponentInfo> getCreditOptions() {
        if (creditOptions == null) {
            creditOptions = new ArrayList<ResultComponentInfo>(0);
        }
        return creditOptions;
    }

    public void setCreditOptions(List<ResultComponentInfo> creditOptions) {
        this.creditOptions = creditOptions;
    }

    /**
     * Flag to indicate the course as a special topics course
     */
    public boolean isSpecialTopicsCourse() {
        return specialTopicsCourse;
    }

    public void setSpecialTopicsCourse(boolean specialTopicsCourse) {
        this.specialTopicsCourse = specialTopicsCourse;
    }

    /**
     * Flag to indicate a one-time or pilot course, which is likely to have expedited approval process
     */
    public boolean isPilotCourse() {
        return pilotCourse;
    }

    public void setPilotCourse(boolean pilotCourse) {
        this.pilotCourse = pilotCourse;
    }

    /**
     * The first academic time period that this Course would be effective.
     */
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    /**
     * The last academic time period that this Course would be effective.
     */
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    /**
     * Date and time the Course became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this Course expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
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

    public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}    
}
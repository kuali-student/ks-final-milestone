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

import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.OfferingInstructor;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "formatOfferingId", "formatOfferingName",
                "activityId", "termId", "termCode", "activityCode", 
                "scheduleIds", "schedulingStateKey",
                "isHonorsOffering", "gradingOptionKeys", "instructors",
                "weeklyInclassContactHours", "weeklyOutofclassContactHours", 
                "weeklyTotalContactHours",  "isEvaluated",
                "maximumEnrollment", "minimumEnrollment","isMaxEnrollmentEstimate",
                "activityOfferingURL",
                "courseOfferingId", "courseOfferingTitle", 
                "courseOfferingCode", "isColocated",
                "isApprovedForNonStandardTimeSlots",
                "meta", "attributes", "_futureElements"})

public class ActivityOfferingInfo
    extends IdEntityInfo
    implements ActivityOffering, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private String formatOfferingName;
    
    @XmlElement
    private String activityId;
        
    @XmlElement
    private String termId;

    @XmlElement
    private String termCode;

    @XmlElement
    private String activityCode;   

    @XmlElement
    private List<String> scheduleIds;
    
    @XmlElement
    private String schedulingStateKey;

    @XmlElement
    private Boolean isHonorsOffering;

    @XmlElement
    private List<String> gradingOptionKeys;

    @XmlElement
    private List<OfferingInstructorInfo> instructors;

    @XmlElement
    private String weeklyInclassContactHours;
    
    @XmlElement
    private String weeklyOutofclassContactHours;

    @XmlElement
    private String weeklyTotalContactHours;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Boolean isMaxEnrollmentEstimate;

    @XmlElement
    private Integer minimumEnrollment;

    @XmlElement
    private Boolean isEvaluated;

    @XmlElement
    private String activityOfferingURL;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private String courseOfferingTitle;

    @XmlElement
    private String courseOfferingCode;

    @XmlAnyElement
    private List<Element> _futureElements;

    @XmlElement
    private Boolean isColocated;

    @XmlElement
    private Boolean isApprovedForNonStandardTimeSlots = Boolean.FALSE;

    /**
     * Constructs a new ActivityOfferingInfo.
     */
    public ActivityOfferingInfo() {
    }

    /**
     * Constructs a new ActivityOfferingInfo from another
     * ActivityOffering.
     *
     * @param offering the activity offering to copy
     */
    public ActivityOfferingInfo(ActivityOffering offering) {
        super(offering); 
        
        if (offering == null) {
            return;
        }

        this.formatOfferingId = offering.getFormatOfferingId();
        this.formatOfferingName = offering.getFormatOfferingName();
        
        this.courseOfferingId = offering.getCourseOfferingId();
        this.courseOfferingCode = offering.getCourseOfferingCode();
        this.courseOfferingTitle = offering.getCourseOfferingTitle();
        
        this.activityId = offering.getActivityId();
        this.termId = offering.getTermId();
        if (offering.getScheduleIds() != null) {
            this.scheduleIds = new ArrayList<String>(offering.getScheduleIds());
        }
        this.schedulingStateKey = offering.getSchedulingStateKey();
        this.activityCode = offering.getActivityCode();

        this.isHonorsOffering = offering.getIsHonorsOffering();
        this.instructors = new ArrayList<OfferingInstructorInfo>();

        if (offering.getGradingOptionKeys() != null) {
            this.gradingOptionKeys = new ArrayList<String>(offering.getGradingOptionKeys());
        }

        for (OfferingInstructor instructor : offering.getInstructors()) {
            this.instructors.add(new OfferingInstructorInfo(instructor));
        }

        this.weeklyInclassContactHours = offering.getWeeklyInclassContactHours();
        this.weeklyOutofclassContactHours = offering.getWeeklyOutofclassContactHours();
        this.weeklyTotalContactHours = offering.getWeeklyTotalContactHours();
        this.maximumEnrollment = offering.getMaximumEnrollment();

        this.minimumEnrollment = offering.getMinimumEnrollment();        

        this.isMaxEnrollmentEstimate = offering.getIsMaxEnrollmentEstimate();

        this.isEvaluated = offering.getIsEvaluated();
        this.activityOfferingURL = offering.getActivityOfferingURL();

        this.isColocated = offering.getIsColocated();

        this.isApprovedForNonStandardTimeSlots = offering.getIsApprovedForNonStandardTimeSlots();
    }

    @Override
    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    @Override
    public String getFormatOfferingName() {
        return formatOfferingName;
    }

    public void setFormatOfferingName(String formatOfferingName) {
        this.formatOfferingName = formatOfferingName;
    }

    @Override
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }


    @Override
    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    @Override
    public List<String> getScheduleIds() {
        if (this.scheduleIds == null) {
            this.scheduleIds =  new ArrayList<String>();
        }
        return this.scheduleIds;
    }

    public void setScheduleIds(List<String> scheduleIds) {
        this.scheduleIds = scheduleIds;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return isHonorsOffering;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    @Override
    public List<String> getGradingOptionKeys() {
        if (gradingOptionKeys == null) {
            gradingOptionKeys = new ArrayList<String>();
        }

        return gradingOptionKeys;
    }

    public void setGradingOptionKeys(List<String> gradingOptionKeys) {
        this.gradingOptionKeys = gradingOptionKeys;
    }

    @Override
    public List<OfferingInstructorInfo> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<OfferingInstructorInfo>();
        }

        return instructors;
    }

    public void setInstructors(List<OfferingInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    @Override
    public String getWeeklyInclassContactHours() {
        return weeklyInclassContactHours;
    }

    public void setWeeklyInclassContactHours(String weeklyInclassContactHours) {
        this.weeklyInclassContactHours = weeklyInclassContactHours;
    }

    @Override
    public String getWeeklyOutofclassContactHours() {
        return weeklyOutofclassContactHours;
    }

    public void setWeeklyOutofclassContactHours(String weeklyOutofclassContactHours) {
        this.weeklyOutofclassContactHours = weeklyOutofclassContactHours;
    }

    @Override
    public String getWeeklyTotalContactHours() {
        return weeklyTotalContactHours;
    }

    public void setWeeklyTotalContactHours(String weeklyTotalContactHours) {
        this.weeklyTotalContactHours = weeklyTotalContactHours;
    }

    @Override
    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    @Override
    public Integer getMinimumEnrollment() {
        return minimumEnrollment;
    }

    @Override
    public Boolean getIsEvaluated() {
        return this.isEvaluated;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }

    public void setHonorsOffering(Boolean honorsOffering) {
        isHonorsOffering = honorsOffering;
    }

    public void setIsEvaluated(Boolean isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    @Override
    public Boolean getIsMaxEnrollmentEstimate() {
       return isMaxEnrollmentEstimate;
    }

    public void setIsMaxEnrollmentEstimate(Boolean isMaxEnrollmentEstimate) {
         this.isMaxEnrollmentEstimate  = isMaxEnrollmentEstimate;
    }

    @Override
    public String getActivityOfferingURL() {
        return activityOfferingURL;
    }

    public void setActivityOfferingURL(String activityOfferingURL) {
        this.activityOfferingURL = activityOfferingURL;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }


    @Override
    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    @Override
    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    public void setSchedulingStateKey(String schedulingStateKey) {
		this.schedulingStateKey = schedulingStateKey;
	}

	@Override
	public String getSchedulingStateKey() {
		return schedulingStateKey;
	}

    @Override
    public Boolean getIsColocated() {
        return isColocated;
    }

    public void setIsColocated(Boolean isColocated) {
        this.isColocated = isColocated;
    }

    public void setIsApprovedForNonStandardTimeSlots( Boolean isApprovedForNonStandardTimeSlots ) {
        this.isApprovedForNonStandardTimeSlots = isApprovedForNonStandardTimeSlots;
    }

    @Override
    public Boolean getIsApprovedForNonStandardTimeSlots() {
        return this.isApprovedForNonStandardTimeSlots;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ActivityOfferingInfo [id=");
        builder.append(getId());
        builder.append (", formatOfferingId=");
        builder.append(formatOfferingId);
        builder.append(", formatOfferingName=");
        builder.append(formatOfferingName);
        builder.append(", courseOfferingId=");
        builder.append(courseOfferingId);
        builder.append(", activityId=");
        builder.append(activityId);
        builder.append(", termId=");
        builder.append(termId);
        builder.append(", scheduleIds=");
        builder.append(scheduleIds.toString());
        builder.append(", schedulingStateKey=");
        builder.append(schedulingStateKey);
        builder.append("]");
        return builder.toString();
    }
}

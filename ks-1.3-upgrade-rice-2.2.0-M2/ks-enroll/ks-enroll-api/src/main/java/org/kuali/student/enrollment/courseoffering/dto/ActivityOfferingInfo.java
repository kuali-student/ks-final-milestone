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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.OfferingInstructor;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "formatOfferingId", "formatOfferingName",
                "activityId", "termId", "termCode", "activityCode", "scheduleId",
                "isHonorsOffering", "gradingOptionKeys", "instructors",
                "weeklyInclassContactHours", "weeklyOutofclassContactHours", 
                "weeklyTotalContactHours",  "isEvaluated",
                "maximumEnrollment", "minimumEnrollment","isMaxEnrollmentEstimate",
                "finalExamStartTime", "finalExamEndTime", 
                "finalExamSpaceCode", "activityOfferingURL", 
                "courseOfferingId", "courseOfferingTitle", 
                "courseOfferingCode", "hasWaitlist", "waitlistTypeKey",
                "waitlistMaximum", "isWaitlistCheckinRequired", "waitlistCheckinFrequency",
                "meta", "attributes", "_futureElements"})

public class ActivityOfferingInfo
    extends IdEntityInfo
    implements ActivityOffering {

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
    private String scheduleId;

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
    private Date finalExamStartTime;
    
    @XmlElement
    private Date finalExamEndTime;

    @XmlElement
    private String finalExamSpaceCode;

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

    @XmlElement
    private Boolean hasWaitlist;

    @XmlElement
    private String waitlistTypeKey;

    @XmlElement
    private Integer waitlistMaximum;

    @XmlElement
    private Boolean isWaitlistCheckinRequired;

    @XmlElement
    private TimeAmountInfo waitlistCheckinFrequency;

    @XmlAnyElement
    private List<Element> _futureElements;


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
        this.scheduleId = offering.getScheduleId();
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

        if (offering.getFinalExamStartTime() != null) {
            this.finalExamStartTime = new Date(offering.getFinalExamStartTime().getTime());
        }

        if (offering.getFinalExamEndTime() != null) {
            this.finalExamEndTime = new Date(offering.getFinalExamEndTime().getTime());
        }

        this.finalExamSpaceCode = offering.getFinalExamSpaceCode();
        this.isEvaluated = offering.getIsEvaluated();
        this.activityOfferingURL = offering.getActivityOfferingURL();

        this.hasWaitlist = offering.getHasWaitlist();
        this.isWaitlistCheckinRequired = offering.getIsWaitlistCheckinRequired();
        this.waitlistCheckinFrequency = new TimeAmountInfo(offering.getWaitlistCheckinFrequency());
        this.waitlistMaximum = offering.getWaitlistMaximum();
        this.waitlistTypeKey = offering.getWaitlistTypeKey();
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
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
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

    @Override
    public Date getFinalExamStartTime() {
        return finalExamStartTime;
    }

    public void setFinalExamStartTime(Date finalExamStartTime) {
        this.finalExamStartTime = finalExamStartTime;
    }

    @Override
    public Date getFinalExamEndTime() {
        return finalExamEndTime;
    }

    public void setFinalExamEndTime(Date finalExamEndTime) {
        this.finalExamEndTime = finalExamEndTime;
    }

    @Override
    public String getFinalExamSpaceCode() {
        return finalExamSpaceCode;
    }

    public void setFinalExamSpaceCode(String finalExamSpaceCode) {
        this.finalExamSpaceCode = finalExamSpaceCode;
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

    @Override
    public Boolean getHasWaitlist() {
        return hasWaitlist;
    }

    public void setHasWaitlist(Boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }

    @Override
    public String getWaitlistTypeKey() {
        return waitlistTypeKey;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    @Override
    public Integer getWaitlistMaximum() {
        return waitlistMaximum;
    }

    public void setWaitlistMaximum(Integer waitlistMaximum) {
        this.waitlistMaximum = waitlistMaximum;
    }

    @Override
    public Boolean getIsWaitlistCheckinRequired() {
        return isWaitlistCheckinRequired;
    }

    public void setIsWaitlistCheckinRequired(Boolean isWaitlistCheckinRequired) {
        this.isWaitlistCheckinRequired = isWaitlistCheckinRequired;
    }

    @Override
    public TimeAmountInfo getWaitlistCheckinFrequency() {
        return waitlistCheckinFrequency;
    }

    public void setWaitlistCheckinFrequency(TimeAmountInfo waitlistCheckinFrequency) {
        this.waitlistCheckinFrequency = waitlistCheckinFrequency;
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
        builder.append(", scheduleId=");
        builder.append(scheduleId);
        builder.append("]");
        return builder.toString();
    }
}

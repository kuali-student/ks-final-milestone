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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.OfferingInstructor;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - Kamal don't for to fill this in. 
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingInfo", propOrder = {"activityId",
"activityCode", "termId", "scheduleId", "isHonorsOffering", "gradingOptionKeys", "instructors",
        "finalExamStartTime", "finalExamEndTime", "finalExamSpaceCode", "meetingSchedules", "weeklyInclassContactHours",
        "weeklyOutofclassContactHours", "weeklyTotalContactHours", "maximumEnrollment", "minimumEnrollment", 
    "id", "typeKey", "stateKey", "name", "descr", "meta", "attributes", "_futureElements"})
public class ActivityOfferingInfo extends IdEntityInfo implements ActivityOffering {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String activityId;
        
    @XmlElement
    private String activityCode;   
    
    @XmlElement
    private String termId;

    @XmlElement
    private String scheduleId;

    @XmlElement
    private Boolean isHonorsOffering;
    
    @XmlElement
    private List<OfferingInstructorInfo> instructors;
    
    @XmlElement
    private List<String> gradingOptionKeys;
       
    @XmlElement
    private Date finalExamStartTime;
    
    @XmlElement
    private Date finalExamEndTime;
    
    @XmlElement
    private String finalExamSpaceCode;

    @XmlElement
    private String weeklyInclassContactHours;
    
    @XmlElement
    private String weeklyOutofclassContactHours;

    @XmlElement
    private String weeklyTotalContactHours;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Integer minimumEnrollment;
        
    @XmlAnyElement
    private List<Element> _futureElements;
    
    public ActivityOfferingInfo() {
    }

    public ActivityOfferingInfo(ActivityOffering activity) {
        super(activity); 
        
        if(null == activity) return;      

        this.activityCode = activity.getActivityCode();
        this.activityId = activity.getActivityId();
        this.scheduleId = activity.getScheduleId();
        this.finalExamSpaceCode = activity.getFinalExamSpaceCode();
        this.finalExamEndTime = (null != activity.getFinalExamEndTime()) ? new Date(activity.getFinalExamEndTime().getTime()) : null;
        this.finalExamStartTime = (null != activity.getFinalExamStartTime()) ? new Date(activity.getFinalExamStartTime().getTime()) : null;
        this.gradingOptionKeys = (null != activity.getGradingOptionKeys()) ? new ArrayList<String>(activity.getGradingOptionKeys()) : null;
        this.isHonorsOffering = (null != activity.getIsHonorsOffering()) ? new Boolean(activity.getIsHonorsOffering()) : null;
        this.maximumEnrollment = activity.getMaximumEnrollment();
        this.minimumEnrollment = activity.getMinimumEnrollment();        
        this.termId = activity.getTermId();
        this.weeklyInclassContactHours = activity.getWeeklyInclassContactHours();
        this.weeklyOutofclassContactHours = activity.getWeeklyOutofclassContactHours();
        this.weeklyTotalContactHours = activity.getWeeklyTotalContactHours();
        
        this.instructors = new ArrayList<OfferingInstructorInfo>();
        if(null != activity.getInstructors()) {
            for(OfferingInstructor i : activity.getInstructors()) {
                this.instructors.add(new OfferingInstructorInfo(i));                
            }
        }
        
        this._futureElements = null;        
    }
    
    @Override
    public String getActivityId() {
        return activityId;
    }

    @Override
    public String getActivityCode() {
        return activityCode;
    }

    @Override
    public String getTermId() {
        return termId;
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

    @Override
    public List<String> getGradingOptionKeys() {
        return gradingOptionKeys;
    }

    @Override
    public Date getFinalExamStartTime() {
        return finalExamStartTime;
    }

    @Override
    public Date getFinalExamEndTime() {
        return finalExamEndTime;
    }

    @Override
    public String getFinalExamSpaceCode() {
        return finalExamSpaceCode;
    }

    @Override
    public String getWeeklyInclassContactHours() {
        return weeklyInclassContactHours;
    }

    @Override
    public String getWeeklyOutofclassContactHours() {
        return weeklyOutofclassContactHours;
    }

    @Override
    public String getWeeklyTotalContactHours() {
        return weeklyTotalContactHours;
    }

    @Override
    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    @Override
    public Integer getMinimumEnrollment() {
        return minimumEnrollment;
    }

    @Override
    public List<OfferingInstructorInfo> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<OfferingInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public void setGradingOptionKeys(List<String> gradingOptionKeys) {
        this.gradingOptionKeys = gradingOptionKeys;
    }

    public void setFinalExamStartTime(Date finalExamStartTime) {
        this.finalExamStartTime = finalExamStartTime;
    }

    public void setFinalExamEndTime(Date finalExamEndTime) {
        this.finalExamEndTime = finalExamEndTime;
    }

    public void setFinalExamSpaceCode(String finalExamSpaceCode) {
        this.finalExamSpaceCode = finalExamSpaceCode;
    }

    public void setWeeklyInclassContactHours(String weeklyInclassContactHours) {
        this.weeklyInclassContactHours = weeklyInclassContactHours;
    }

    public void setWeeklyOutofclassContactHours(String weeklyOutofclassContactHours) {
        this.weeklyOutofclassContactHours = weeklyOutofclassContactHours;
    }

    public void setWeeklyTotalContactHours(String weeklyTotalContactHours) {
        this.weeklyTotalContactHours = weeklyTotalContactHours;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }
}

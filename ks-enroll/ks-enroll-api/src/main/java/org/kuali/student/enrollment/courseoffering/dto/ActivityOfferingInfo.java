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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.lui.dto.LuiInstructorInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - Kamal don't for to fill this in. 
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingInfo", propOrder = {"courseOfferingIds", "registrationGroupIds", "activityId",
        "activityCode", "termKey", "isHonorsOffering", "gradingOptions",
        "finalExamStartTime", "finalExamEndTime", "finalExamBuilding", "finalExamRoom", "weeklyInclassContactHours",
        "weeklyOutofclassContactHours", "weeklyTotalContactHours", "maximumEnrollment", "minimumEnrollment", 
        "id", "typeKey", "stateKey", "name", "descr", "meta", "attributes", "_futureElements"})
public class ActivityOfferingInfo extends IdEntityInfo implements ActivityOffering {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> courseOfferingIds;
    
    @XmlElement
    private List<String> registrationGroupIds;
    
    @XmlElement
    private String activityId;
        
    @XmlElement
    private String activityCode;   
    
    @XmlElement
    private String termKey;

    @XmlElement
    private Boolean isHonorsOffering;
    
    @XmlElement
    private List<LuiInstructorInfo> instructors;
    
    @XmlElement
    private List<String> gradingOptions;
       
    @XmlElement
    private Date finalExamStartTime;
    
    @XmlElement
    private Date finalExamEndTime;
    
    @XmlElement
    private String finalExamBuilding;
    
    @XmlElement
    private String finalExamRoom;

    @XmlElement
    private Float weeklyInclassContactHours;
    
    @XmlElement
    private Float weeklyOutofclassContactHours;

    @XmlElement
    private Float weeklyTotalContactHours;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Integer minimumEnrollment;
        
    @XmlAnyElement
    private List<Element> _futureElements;
    
    public ActivityOfferingInfo() {
        this.registrationGroupIds = new ArrayList<String>();
        this.courseOfferingIds = new ArrayList<String>();
        this.activityCode = null;
        this.activityId = null;
        this.finalExamBuilding = null;
        this.finalExamEndTime = null;
        this.finalExamRoom = null;
        this.finalExamStartTime = null;
        this.gradingOptions = new ArrayList<String>();
        this.isHonorsOffering = new Boolean(false);
        this.maximumEnrollment = null;
        this.minimumEnrollment = null;
        this.registrationGroupIds = new ArrayList<String>();
        this.termKey = null;
        this.weeklyInclassContactHours = null;
        this.weeklyOutofclassContactHours = null;
        this.weeklyTotalContactHours = null;
        this._futureElements = null;
    }

    public ActivityOfferingInfo(ActivityOffering activity) {
        super(activity); 
        
        if(null == activity) return;      
        
        this.registrationGroupIds = (null != activity.getRegistrationGroupIds()) ? new ArrayList<String>(activity.getRegistrationGroupIds()) : null;
        this.courseOfferingIds = (null != activity.getCourseOfferingIds()) ? new ArrayList<String>(activity.getCourseOfferingIds()) : null;
        this.activityCode = activity.getActivityCode();
        this.activityId = activity.getActivityId();
        this.finalExamBuilding = activity.getFinalExamBuilding();
        this.finalExamEndTime = (null != activity.getFinalExamEndTime()) ? new Date(activity.getFinalExamEndTime().getTime()) : null;
        this.finalExamRoom = activity.getFinalExamRoom();
        this.finalExamStartTime = (null != activity.getFinalExamStartTime()) ? new Date(activity.getFinalExamStartTime().getTime()) : null;
        this.gradingOptions = (null != activity.getGradingOptions()) ? new ArrayList<String>(activity.getGradingOptions()) : null;
        this.isHonorsOffering = (null != activity.getIsHonorsOffering()) ? new Boolean(activity.getIsHonorsOffering()) : null;
        this.maximumEnrollment = activity.getMaximumEnrollment();
        this.minimumEnrollment = activity.getMinimumEnrollment();
        this.registrationGroupIds = (null != activity.getRegistrationGroupIds()) ? new ArrayList<String>(activity.getRegistrationGroupIds()) : null;
        this.termKey = activity.getTermKey();
        this.weeklyInclassContactHours = (null != activity.getWeeklyInclassContactHours()) ? new Float(activity.getWeeklyInclassContactHours()) : null;
        this.weeklyOutofclassContactHours = (null != activity.getWeeklyOutofclassContactHours()) ? new Float(activity.getWeeklyOutofclassContactHours()) : null;
        this.weeklyTotalContactHours = (null != activity.getWeeklyTotalContactHours()) ? new Float(activity.getWeeklyTotalContactHours()) : null;
        this._futureElements = null;        
    }
    
    @Override
    public List<String> getCourseOfferingIds() {
        return courseOfferingIds;
    }

    @Override
    public List<String> getRegistrationGroupIds() {
        return registrationGroupIds;
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
    public String getTermKey() {
        return termKey;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return isHonorsOffering;
    }

    @Override
    public List<String> getGradingOptions() {
        return gradingOptions;
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
    public String getFinalExamBuilding() {
        return finalExamBuilding;
    }

    @Override
    public String getFinalExamRoom() {
        return finalExamRoom;
    }

    @Override
    public Float getWeeklyInclassContactHours() {
        return weeklyInclassContactHours;
    }

    @Override
    public Float getWeeklyOutofclassContactHours() {
        return weeklyOutofclassContactHours;
    }

    @Override
    public Float getWeeklyTotalContactHours() {
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
    public List<LuiInstructorInfo> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<LuiInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    public void setCourseOfferingIds(List<String> courseOfferingIds) {
        this.courseOfferingIds = courseOfferingIds;
    }

    public void setRegistrationGroupIds(List<String> registrationGroupIds) {
        this.registrationGroupIds = registrationGroupIds;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public void setGradingOptions(List<String> gradingOptions) {
        this.gradingOptions = gradingOptions;
    }

    public void setFinalExamStartTime(Date finalExamStartTime) {
        this.finalExamStartTime = finalExamStartTime;
    }

    public void setFinalExamEndTime(Date finalExamEndTime) {
        this.finalExamEndTime = finalExamEndTime;
    }

    public void setFinalExamBuilding(String finalExamBuilding) {
        this.finalExamBuilding = finalExamBuilding;
    }

    public void setFinalExamRoom(String finalExamRoom) {
        this.finalExamRoom = finalExamRoom;
    }

    public void setWeeklyInclassContactHours(Float weeklyInclassContactHours) {
        this.weeklyInclassContactHours = weeklyInclassContactHours;
    }

    public void setWeeklyOutofclassContactHours(Float weeklyOutofclassContactHours) {
        this.weeklyOutofclassContactHours = weeklyOutofclassContactHours;
    }

    public void setWeeklyTotalContactHours(Float weeklyTotalContactHours) {
        this.weeklyTotalContactHours = weeklyTotalContactHours;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }
        
}

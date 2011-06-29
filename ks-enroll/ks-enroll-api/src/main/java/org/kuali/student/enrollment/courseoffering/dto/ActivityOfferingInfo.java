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

import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultComponent;

/**
 * This is a description of what this class does - Kamal don't for to fill this in. 
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
public class ActivityOfferingInfo extends IdEntityInfo implements ActivityOffering {

    private static final long serialVersionUID = 1L;

    private List<String> courseOfferingIds;
    
    private List<String> registrationGroupIds;
    
    private String activityId;
        
    private String activityCode;   
    
    private String termKey;

    private Boolean isHonorsOffering;
    
    private ResultComponent gradingOptions;
       
    private Date finalExamStartTime;
    
    private Date finalExamEndTime;
    
    private String finalExamBuilding;
    
    private String finalExamRoom;

    private Float weeklyInclassContactHours;
    
    private Float weeklyOutofclassContactHours;

    private Float weeklyTotalContactHours;

    private Integer maximumEnrollment;

    private Integer minimumEnrollment;
        
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
    public ResultComponent getGradingOptions() {
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

    public void setGradingOptions(ResultComponent gradingOptions) {
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

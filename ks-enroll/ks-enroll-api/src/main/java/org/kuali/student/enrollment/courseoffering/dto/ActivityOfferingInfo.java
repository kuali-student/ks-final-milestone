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
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.kuali.student.r2.common.infc.MeetingSchedule;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - Kamal don't for to fill this in. 
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingInfo", propOrder = {"activityId",
        "activityCode", "termKey", "isHonorsOffering", "gradingOptionKeys", "instructors",
        "finalExamStartTime", "finalExamEndTime", "finalExamSpaceCode", "meetingSchedules", "weeklyInclassContactHours",
        "weeklyOutofclassContactHours", "weeklyTotalContactHours", "maximumEnrollment", "minimumEnrollment", 
        "id", "typeKey", "stateKey", "descr", "meta", "attributes", "_futureElements"})
public class ActivityOfferingInfo extends TypeStateEntityInfo implements ActivityOffering {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private RichTextInfo descr;
            
    @XmlElement
    private String activityId;
        
    @XmlElement
    private String activityCode;   
    
    @XmlElement
    private String termKey;

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
    private List<MeetingScheduleInfo> meetingSchedules;

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
        this.id = null;
        this.descr = null;
        this.activityCode = null;
        this.activityId = null;
        this.finalExamSpaceCode = null;
        this.finalExamEndTime = null;
        this.meetingSchedules = new ArrayList<MeetingScheduleInfo>();
        this.instructors = new ArrayList<OfferingInstructorInfo>();
        this.finalExamStartTime = null;
        this.gradingOptionKeys = new ArrayList<String>();
        this.isHonorsOffering = new Boolean(false);
        this.maximumEnrollment = null;
        this.minimumEnrollment = null;
        this.termKey = null;
        this.weeklyInclassContactHours = null;
        this.weeklyOutofclassContactHours = null;
        this.weeklyTotalContactHours = null;
        this._futureElements = null;
    }

    public ActivityOfferingInfo(ActivityOffering activity) {
        super(activity); 
        
        if(null == activity) return;      

        this.id = activity.getId();
        this.descr = (null != activity.getDescr()) ? new RichTextInfo(activity.getDescr()) : null;        
        this.activityCode = activity.getActivityCode();
        this.activityId = activity.getActivityId();
        this.finalExamSpaceCode = activity.getFinalExamSpaceCode();
        this.finalExamEndTime = (null != activity.getFinalExamEndTime()) ? new Date(activity.getFinalExamEndTime().getTime()) : null;
        this.finalExamStartTime = (null != activity.getFinalExamStartTime()) ? new Date(activity.getFinalExamStartTime().getTime()) : null;
        this.gradingOptionKeys = (null != activity.getGradingOptionKeys()) ? new ArrayList<String>(activity.getGradingOptionKeys()) : null;
        this.isHonorsOffering = (null != activity.getIsHonorsOffering()) ? new Boolean(activity.getIsHonorsOffering()) : null;
        this.maximumEnrollment = activity.getMaximumEnrollment();
        this.minimumEnrollment = activity.getMinimumEnrollment();        
        this.termKey = activity.getTermKey();
        this.weeklyInclassContactHours = (null != activity.getWeeklyInclassContactHours()) ? new Float(activity.getWeeklyInclassContactHours()) : null;
        this.weeklyOutofclassContactHours = (null != activity.getWeeklyOutofclassContactHours()) ? new Float(activity.getWeeklyOutofclassContactHours()) : null;
        this.weeklyTotalContactHours = (null != activity.getWeeklyTotalContactHours()) ? new Float(activity.getWeeklyTotalContactHours()) : null;
        
        this.meetingSchedules = new ArrayList<MeetingScheduleInfo>();        
        if(null != activity.getMeetingSchedules()) {
            for(MeetingSchedule m : activity.getMeetingSchedules()) {
                this.meetingSchedules.add(new MeetingScheduleInfo(m));
            }
        }
        
        this.instructors = new ArrayList<OfferingInstructorInfo>();
        if(null != activity.getInstructors()) {
            for(OfferingInstructor i : activity.getInstructors()) {
                this.instructors.add(new OfferingInstructorInfo(i));                
            }
        }
        
        this._futureElements = null;        
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public RichTextInfo getDescr() {
        return descr;
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
    public List<MeetingScheduleInfo> getMeetingSchedules() {
        return this.meetingSchedules;
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
    public List<OfferingInstructorInfo> getInstructors() {
        return instructors;
    }


    public void setId(String id) {
        this.id = id;
    }
    
    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
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

    public void setTermKey(String termKey) {
        this.termKey = termKey;
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

    public void setMeetingSchedules(List<MeetingScheduleInfo> meetingSchedules) {
        this.meetingSchedules = meetingSchedules;
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

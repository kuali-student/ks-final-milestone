/*
 * Copyright 2012 The Kuali Foundation
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

import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingAdminDisplay;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingAdminDisplayInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "typeName", "stateName", "courseOfferingTitle",
                "courseOfferingCode", "formatOfferingId", "formatOfferingName",
                "activityOfferingCode", "instructorId",  "instructorName", 
                "scheduleWeekdays", "scheduleTime",
                "meta", "attributes", "_futureElements"})

public class ActivityOfferingAdminDisplayInfo 
    extends IdEntityInfo 
    implements ActivityOfferingAdminDisplay {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String typeName;
    
    @XmlElement
    private String stateName;
        
    @XmlElement
    private String courseOfferingTitle;

    @XmlElement
    private String courseOfferingCode;   
    
    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private String formatOfferingName;
    
    @XmlElement
    private String activityOfferingCode;   

    @XmlElement
    private String instructorId;   

    @XmlElement
    private String instructorName;   

    @XmlElement
    private String scheduleWeekdays;

    @XmlElement
    private String scheduleTime;   

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new ActivityOfferingAdminDisplayInfo.
     */
    public ActivityOfferingAdminDisplayInfo() {
    }

    /**
     * Constructs a new ActivityOfferingAdminDisplayInfo from another
     * ActivityOfferingAdminDisplay.
     *
     * @param offeringAdmin the activity offering admin display to copy
     */
    public ActivityOfferingAdminDisplayInfo(ActivityOfferingAdminDisplay offeringAdmin) {
        super(offeringAdmin); 
        
        if (offeringAdmin == null) {
            return;
        }

        this.typeName = offeringAdmin.getTypeName();
        this.stateName = offeringAdmin.getStateName();
        this.courseOfferingTitle = offeringAdmin.getCourseOfferingTitle();
        this.courseOfferingCode = offeringAdmin.getCourseOfferingCode();
        this.formatOfferingId = offeringAdmin.getFormatOfferingId();
        this.formatOfferingName = offeringAdmin.getFormatOfferingName();
        this.activityOfferingCode = offeringAdmin.getActivityOfferingCode();
        this.instructorId = offeringAdmin.getInstructorId();
        this.instructorName = offeringAdmin.getInstructorName();
        this.scheduleWeekdays = offeringAdmin.getScheduleWeekdays();
        this.scheduleTime = offeringAdmin.getScheduleTime();
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    @Override
    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
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
    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = activityOfferingCode;
    }

    @Override
    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    @Override
    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    @Override
    public String getScheduleWeekdays() {
        return scheduleWeekdays;
    }

    public void setScheduleWeekdays(String scheduleWeekdays) {
        this.scheduleWeekdays = scheduleWeekdays;
    }

    @Override
    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
}

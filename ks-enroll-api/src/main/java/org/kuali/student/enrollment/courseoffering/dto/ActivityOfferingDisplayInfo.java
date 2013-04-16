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

import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingDisplay;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingDisplayInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "typeName", "stateName", "courseOfferingTitle",
                "courseOfferingCode", "formatOfferingId", "formatOfferingName",
                "activityOfferingCode", "instructorId",  "instructorName",
                "isHonorsOffering", "maximumEnrollment", "scheduleDisplay",
                "meta", "attributes", "_futureElements"})

public class ActivityOfferingDisplayInfo
    extends IdEntityInfo 
    implements ActivityOfferingDisplay {

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
    private Boolean isHonorsOffering;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private ScheduleDisplayInfo scheduleDisplay;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new ActivityOfferingDisplayInfo.
     */
    public ActivityOfferingDisplayInfo() {
    }

    /**
     * Constructs a new ActivityOfferingDisplayInfo from another
     * ActivityOfferingDisplay.
     *
     * @param offeringDisplay the activity offering admin display to copy
     */
    public ActivityOfferingDisplayInfo(ActivityOfferingDisplay offeringDisplay) {
        super(offeringDisplay);
        
        if (offeringDisplay == null) {
            return;
        }

        this.typeName = offeringDisplay.getTypeName();
        this.stateName = offeringDisplay.getStateName();
        this.courseOfferingTitle = offeringDisplay.getCourseOfferingTitle();
        this.courseOfferingCode = offeringDisplay.getCourseOfferingCode();
        this.formatOfferingId = offeringDisplay.getFormatOfferingId();
        this.formatOfferingName = offeringDisplay.getFormatOfferingName();
        this.activityOfferingCode = offeringDisplay.getActivityOfferingCode();
        this.instructorId = offeringDisplay.getInstructorId();
        this.instructorName = offeringDisplay.getInstructorName();
        this.isHonorsOffering = offeringDisplay.getIsHonorsOffering();
        this.maximumEnrollment = offeringDisplay.getMaximumEnrollment();
        this.scheduleDisplay = (null != offeringDisplay.getScheduleDisplay()) ? new ScheduleDisplayInfo(offeringDisplay.getScheduleDisplay()) : null;
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
    public Boolean getIsHonorsOffering() {
        return this.isHonorsOffering;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    @Override
    public ScheduleDisplayInfo getScheduleDisplay() {
        return this.scheduleDisplay;
    }

    public void setScheduleDisplay(ScheduleDisplayInfo scheduleDisplay) {
        this.scheduleDisplay = scheduleDisplay;
    }
}

/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.courseoffering.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationGroupInfo", propOrder = {"activityOfferingIds", "courseOfferingId", 
        "registrationCode", "formatId", "isHonorsOffering", "maximumEnrollment", "minimumEnrollment", 
        "hasWaitlist", "waitlistTypeKey", "waitlistMaximum", "isWaitlistCheckinRequired", "waitlistCheckinFrequency",
        "id", "typeKey", "stateKey", "name", "descr", "meta", "attributes", "_futureElements"})
public class RegistrationGroupInfo extends IdEntityInfo implements RegistrationGroup {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> activityOfferingIds;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Integer minimumEnrollment;

    @XmlElement
    private String formatId;

    @XmlElement
    private String registrationCode;

    @XmlElement
    private Boolean isHonorsOffering;

    @XmlElement
    private Boolean hasWaitlist;

    @XmlElement
    private String waitlistTypeKey;

    @XmlElement
    private Integer waitlistMaximum;

    @XmlElement
    private Boolean isWaitlistCheckinRequired;

    @XmlElement
    private TimeAmount waitlistCheckinFrequency;

    @XmlAnyElement
    private List<Element> _futureElements;
    
    @Override
    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    @Override    
    public String getCourseOfferingId() {
        return courseOfferingId;
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
    public String getFormatId() {
        return formatId;
    }

    @Override
    public String getRegistrationCode() {
        return registrationCode;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return isHonorsOffering;
    }

    @Override
    public Boolean getHasWaitlist() {
        return hasWaitlist;
    }

    @Override
    public String getWaitlistTypeKey() {
        return waitlistTypeKey;
    }

    @Override
    public Integer getWaitlistMaximum() {
        return waitlistMaximum;
    }

    @Override
    public Boolean getIsWaitlistCheckinRequired() {
        return isWaitlistCheckinRequired;
    }

    @Override
    public TimeAmount getWaitlistCheckinFrequency() {
        return waitlistCheckinFrequency;
    }

    
    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public void setHasWaitlist(Boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    public void setWaitlistMaximum(Integer waitlistMaximum) {
        this.waitlistMaximum = waitlistMaximum;
    }

    public void setIsWaitlistCheckinRequired(Boolean isWaitlistCheckinRequired) {
        this.isWaitlistCheckinRequired = isWaitlistCheckinRequired;
    }

    public void setWaitlistCheckinFrequency(TimeAmount waitlistCheckinFrequency) {
        this.waitlistCheckinFrequency = waitlistCheckinFrequency;
    }
}

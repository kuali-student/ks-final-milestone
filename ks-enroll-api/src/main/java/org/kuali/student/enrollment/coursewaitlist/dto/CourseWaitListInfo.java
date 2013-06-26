/**
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.coursewaitlist.dto;


import org.kuali.student.enrollment.coursewaitlist.infc.CourseWaitList;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitListInfo", propOrder = {
        "id", "typeKey", "stateKey","activityOfferingIds",
        "formatOfferingIds", "courseWaitListProcessingTypeKey", "maxSize",
        "checkInRequired", "checkInFrequency", "allowHoldListEntries", "effectiveDate", "expirationDate",
        "meta", "attributes", "_futureElements" })
public class CourseWaitListInfo extends IdNamelessEntityInfo implements CourseWaitList, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> activityOfferingIds;
    @XmlElement
    private List<String> formatOfferingIds;
    @XmlElement
    private String courseWaitListProcessingTypeKey;
    @XmlElement
    private Integer maxSize;
    @XmlElement
    private Boolean checkInRequired;
    @XmlElement
    private TimeAmountInfo checkInFrequency;
    @XmlElement
    private Boolean allowHoldListEntries;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;


    public CourseWaitListInfo() {
    }

    public CourseWaitListInfo(CourseWaitList waitList) {
        super(waitList);

        if(waitList != null) {
            if(waitList.getActivityOfferingIds() != null) {
                setActivityOfferingIds(new ArrayList<String>(waitList.getActivityOfferingIds()));
            }

            if(waitList.getFormatOfferingIds() != null) {
                setFormatOfferingIds(new ArrayList<String>(waitList.getFormatOfferingIds()));
            }

            setCourseWaitListProcessingTypeKey(waitList.getCourseWaitListProcessingTypeKey());
            setMaxSize(waitList.getMaxSize());
            setCheckInRequired(waitList.getCheckInRequired());
            if(waitList.getCheckInFrequency() != null) {
                setCheckInFrequency(new TimeAmountInfo(waitList.getCheckInFrequency()));
            }
            setAllowHoldListEntries(waitList.getAllowHoldListEntries());
            if(waitList.getEffectiveDate() != null) {
                setEffectiveDate(new Date(waitList.getEffectiveDate().getTime()));
            }
            if(waitList.getExpirationDate() != null) {
                setExpirationDate(new Date(waitList.getExpirationDate().getTime()));
            }
        }
    }

    @Override
    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    @Override
    public List<String> getFormatOfferingIds() {
        return formatOfferingIds;
    }

    public void setFormatOfferingIds(List<String> formatOfferingIds) {
        this.formatOfferingIds = formatOfferingIds;
    }

    @Override
    public String getCourseWaitListProcessingTypeKey() {
        return courseWaitListProcessingTypeKey;
    }

    public void setCourseWaitListProcessingTypeKey(String courseWaitListProcessingTypeKey) {
        this.courseWaitListProcessingTypeKey = courseWaitListProcessingTypeKey;
    }

    @Override
    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    @Override
    public TimeAmountInfo getCheckInFrequency() {
        return checkInFrequency;
    }

    public void setCheckInFrequency(TimeAmountInfo checkInFrequency) {
        this.checkInFrequency = checkInFrequency;
    }

    @Override
    public Boolean getAllowHoldListEntries() {
        return allowHoldListEntries;
    }

    public void setAllowHoldListEntries(Boolean allowHoldListEntries) {
        this.allowHoldListEntries = allowHoldListEntries;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.lum.lu.dto;
 
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.entity.TimeAmount;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluCredit implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String repeatCount;

    @XmlElement
    private TimeAmount repeatTime;

    @XmlElement
    private String repeatUnits;

    @XmlElement
    private Integer minTotalUnits;

    @XmlElement
    private Integer maxTotalUnits;

    @XmlElement
    private Integer instructorUnits;

    @XmlElement
    private TimeAmount minTimeToComplete;

    @XmlElement
    private TimeAmount maxTimeToComplete;

    @XmlElement
    private TimeAmount maxAllowableInactivity;

    @XmlElement
    private TimeAmount maxTimeResultsRecognized;

    public String getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    public TimeAmount getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(TimeAmount repeatTime) {
        this.repeatTime = repeatTime;
    }

    public String getRepeatUnits() {
        return repeatUnits;
    }

    public void setRepeatUnits(String repeatUnits) {
        this.repeatUnits = repeatUnits;
    }

    public Integer getMinTotalUnits() {
        return minTotalUnits;
    }

    public void setMinTotalUnits(Integer minTotalUnits) {
        this.minTotalUnits = minTotalUnits;
    }

    public Integer getMaxTotalUnits() {
        return maxTotalUnits;
    }

    public void setMaxTotalUnits(Integer maxTotalUnits) {
        this.maxTotalUnits = maxTotalUnits;
    }

    public Integer getInstructorUnits() {
        return instructorUnits;
    }

    public void setInstructorUnits(Integer instructorUnits) {
        this.instructorUnits = instructorUnits;
    }

    public TimeAmount getMinTimeToComplete() {
        return minTimeToComplete;
    }

    public void setMinTimeToComplete(TimeAmount minTimeToComplete) {
        this.minTimeToComplete = minTimeToComplete;
    }

    public TimeAmount getMaxTimeToComplete() {
        return maxTimeToComplete;
    }

    public void setMaxTimeToComplete(TimeAmount maxTimeToComplete) {
        this.maxTimeToComplete = maxTimeToComplete;
    }

    public TimeAmount getMaxAllowableInactivity() {
        return maxAllowableInactivity;
    }

    public void setMaxAllowableInactivity(TimeAmount maxAllowableInactivity) {
        this.maxAllowableInactivity = maxAllowableInactivity;
    }

    public TimeAmount getMaxTimeResultsRecognized() {
        return maxTimeResultsRecognized;
    }

    public void setMaxTimeResultsRecognized(TimeAmount maxTimeResultsRecognized) {
        this.maxTimeResultsRecognized = maxTimeResultsRecognized;
    }
}
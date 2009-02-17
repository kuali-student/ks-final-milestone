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

import org.kuali.student.core.atp.dto.TimeAmountInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluCredit implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String repeatCount;

    @XmlElement
    private TimeAmountInfo repeatTime;

    @XmlElement
    private String repeatUnits;

    @XmlElement
    private Integer minTotalUnits;

    @XmlElement
    private Integer maxTotalUnits;

    @XmlElement
    private Integer instructorUnits;

    @XmlElement
    private TimeAmountInfo minTimeToComplete;

    @XmlElement
    private TimeAmountInfo maxTimeToComplete;

    @XmlElement
    private TimeAmountInfo maxAllowableInactivity;

    @XmlElement
    private TimeAmountInfo maxTimeResultsRecognized;

    public String getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    public TimeAmountInfo getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(TimeAmountInfo repeatTime) {
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

    public TimeAmountInfo getMinTimeToComplete() {
        return minTimeToComplete;
    }

    public void setMinTimeToComplete(TimeAmountInfo minTimeToComplete) {
        this.minTimeToComplete = minTimeToComplete;
    }

    public TimeAmountInfo getMaxTimeToComplete() {
        return maxTimeToComplete;
    }

    public void setMaxTimeToComplete(TimeAmountInfo maxTimeToComplete) {
        this.maxTimeToComplete = maxTimeToComplete;
    }

    public TimeAmountInfo getMaxAllowableInactivity() {
        return maxAllowableInactivity;
    }

    public void setMaxAllowableInactivity(TimeAmountInfo maxAllowableInactivity) {
        this.maxAllowableInactivity = maxAllowableInactivity;
    }

    public TimeAmountInfo getMaxTimeResultsRecognized() {
        return maxTimeResultsRecognized;
    }

    public void setMaxTimeResultsRecognized(TimeAmountInfo maxTimeResultsRecognized) {
        this.maxTimeResultsRecognized = maxTimeResultsRecognized;
    }
}
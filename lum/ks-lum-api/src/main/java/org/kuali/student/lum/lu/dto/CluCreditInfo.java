/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.dto;
 
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.TimeAmountInfo;

/**
 *Detailed information about credit for a CLU, including common data-driven constraints around repetition.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class CluCreditInfo implements Serializable {

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

    /**
     * The number of times a student may repeat the course for credit. The values of this field are restricted to integer values and the string "unbounded".
     */
    public String getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    /**
     * The amount of time after which a student may repeat the course (in ATP)
     */
    public TimeAmountInfo getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(TimeAmountInfo repeatTime) {
        this.repeatTime = repeatTime;
    }

    /**
     * The total number of units for which the student may repeat this course.
     */
    public String getRepeatUnits() {
        return repeatUnits;
    }

    public void setRepeatUnits(String repeatUnits) {
        this.repeatUnits = repeatUnits;
    }

    /**
     * The minimum total number of credits or units earned by the Student. This field will be used for calculations. If specified, should be less than or equal to the maxTotalUnits.
     */
    public Integer getMinTotalUnits() {
        return minTotalUnits;
    }

    public void setMinTotalUnits(Integer minTotalUnits) {
        this.minTotalUnits = minTotalUnits;
    }

    /**
     * The maximum total number of credits or units earned by the Student. This field will be used for calculations. If specified, should be greater than or equal to the minTotalUnits.
     */
    public Integer getMaxTotalUnits() {
        return maxTotalUnits;
    }

    public void setMaxTotalUnits(Integer maxTotalUnits) {
        this.maxTotalUnits = maxTotalUnits;
    }

    /**
     * The total credit hours for use in evaluating the Instructor, meant to cover the concept of Contact Hours
     */
    public Integer getInstructorUnits() {
        return instructorUnits;
    }

    public void setInstructorUnits(Integer instructorUnits) {
        this.instructorUnits = instructorUnits;
    }

    /**
     * Minimum amount of time required to complete the CLU. If specified, should be less than or equal to maxTimeToComplete.
     */
    public TimeAmountInfo getMinTimeToComplete() {
        return minTimeToComplete;
    }

    public void setMinTimeToComplete(TimeAmountInfo minTimeToComplete) {
        this.minTimeToComplete = minTimeToComplete;
    }

    /**
     * The default maximum amount of time allowed to complete the CLU. If specified, should be greater than or equal to the minTimeToComplete.
     */
    public TimeAmountInfo getMaxTimeToComplete() {
        return maxTimeToComplete;
    }

    public void setMaxTimeToComplete(TimeAmountInfo maxTimeToComplete) {
        this.maxTimeToComplete = maxTimeToComplete;
    }

    /**
     * The Maximum Allowable Number of Time Periods of Inactivity (i.e. Stale or Resting time).
     */
    public TimeAmountInfo getMaxAllowableInactivity() {
        return maxAllowableInactivity;
    }

    public void setMaxAllowableInactivity(TimeAmountInfo maxAllowableInactivity) {
        this.maxAllowableInactivity = maxAllowableInactivity;
    }

    /**
     * After this period expires, the results will no longer be recognized and the student will have to retake the CLU. This may apply to certain types of exams.
     */
    public TimeAmountInfo getMaxTimeResultsRecognized() {
        return maxTimeResultsRecognized;
    }

    public void setMaxTimeResultsRecognized(TimeAmountInfo maxTimeResultsRecognized) {
        this.maxTimeResultsRecognized = maxTimeResultsRecognized;
    }
}

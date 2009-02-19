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
package org.kuali.student.lum.lu.entity;
 
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.student.core.entity.TimeAmount;

@Entity
@Table(name = "KS_CLU_CREDIT_T")
public class CluCredit {

    @Id
    @Column(name = "ID")
    private String id;
    
    @Column(name = "REPEAT_COUNT")
    private String repeatCount;

    @Embedded
    @Column(name = "REPEAT_TIME")
    private TimeAmount repeatTime;

    @Column(name = "REPEAT_UNITS")
    private String repeatUnits;

    @Column(name = "MIN_TOTAL_UNITS")
    private Integer minTotalUnits;

    @Column(name = "MAX_TOTAL_UNITS")
    private Integer maxTotalUnits;

    @Column(name = "INSTRUCTOR_UNITS")
    private Integer instructorUnits;

    @Embedded
    @Column(name = "MIN_TIME_TO_COMPLETE")
    private TimeAmount minTimeToComplete;

    @Embedded
    @Column(name = "MAX_TIME_TO_COMPLETE")
    private TimeAmount maxTimeToComplete;

    @Embedded
    @Column(name = "MAX_ALLOWABLE_INACTIVITY")
    private TimeAmount maxAllowableInactivity;

    @Embedded
    @Column(name = "MAX_TIME_RESULTS_RECOGNIZED")
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
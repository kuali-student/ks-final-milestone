/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lu.entity;
 
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.common.entity.BaseEntity;
import org.kuali.student.common.entity.TimeAmount;

@Entity
@Table(name = "KSLU_CLU_CR")
public class CluCredit extends BaseEntity{
    
    @Column(name = "REPEAT_CNT")
    private String repeatCount;

    @Column(name = "REPEAT_UNIT")
    private String repeatUnits;

    @Column(name = "MIN_TOT_UNIT")
    private Integer minTotalUnits;

    @Column(name = "MAX_TOT_UNIT")
    private Integer maxTotalUnits;

    @Column(name = "INSTR_UNIT")
    private Integer instructorUnits;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="atpDurationTypeKey", column=@Column(name="REPEAT_TM_ATP")),
        @AttributeOverride(name="timeQuantity", column=@Column(name="REPEAT_TM_TMQ"))}
    )
    private TimeAmount repeatTime;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="atpDurationTypeKey", column=@Column(name="MIN_TM_TO_COMP_ATP")),
        @AttributeOverride(name="timeQuantity", column=@Column(name="MIN_TM_TO_COMP_TMQ"))}
    )
    private TimeAmount minTimeToComplete;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="atpDurationTypeKey", column=@Column(name="MAX_TM_TO_COMP_ATP")),
        @AttributeOverride(name="timeQuantity", column=@Column(name="MAX_TM_TO_COMP_TMQ"))}
    )
    private TimeAmount maxTimeToComplete;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="atpDurationTypeKey", column=@Column(name="MAX_ALOW_INACV_ATP")),
        @AttributeOverride(name="timeQuantity", column=@Column(name="MAX_ALOW_INACV_TMQ"))}
    )
    private TimeAmount maxAllowableInactivity;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="atpDurationTypeKey", column=@Column(name="MAX_TM_RSLT_RCGZ_ATP")),
        @AttributeOverride(name="timeQuantity", column=@Column(name="MAX_TM_RSLT_RCGZ_TMQ"))}
    )
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

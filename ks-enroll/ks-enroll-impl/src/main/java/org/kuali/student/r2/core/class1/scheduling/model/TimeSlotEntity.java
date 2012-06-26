/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.class1.scheduling.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for Time Slot data in the Scheduling Service
 * @author andrewlubbers
 *
 */

@Entity
@Table(name = "KSEN_SCHED_TMSLOT")
public class TimeSlotEntity extends MetaEntity {

    @Column(name = "TM_SLOT_TYPE")
    private String timeSlotType;

    @Column(name = "TM_SLOT_STATE")
    private String timeSlotState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String descrPlain;

    @Column(name = "WEEKDAYS")
    private String weekdays;

    @Column(name = "START_TIME_MS")
    private Long startTimeMillis;

    @Column(name = "END_TIME_MS")
    private Long endTimeMillis;

    public String getTimeSlotType() {
        return timeSlotType;
    }

    public void setTimeSlotType(String timeSlotType) {
        this.timeSlotType = timeSlotType;
    }

    public String getTimeSlotState() {
        return timeSlotState;
    }

    public void setTimeSlotState(String timeSlotState) {
        this.timeSlotState = timeSlotState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public Long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(Long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public Long getEndTimeMillis() {
        return endTimeMillis;
    }

    public void setEndTimeMillis(Long endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }
}

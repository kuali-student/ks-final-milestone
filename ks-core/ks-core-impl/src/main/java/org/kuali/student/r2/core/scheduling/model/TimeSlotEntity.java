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

package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for Time Slot data in the Scheduling Service
 * @author andrewlubbers
 *
 */

@Entity
@Table(name = "KSEN_SCHED_TMSLOT")
@NamedQueries({
        @NamedQuery(name = "TimeSlotEntity.GetByTimeSlotType", query = "select timeSlot from TimeSlotEntity timeSlot where timeSlot.timeSlotType = :timeSlotType"),
        @NamedQuery(name = "TimeSlotEntity.GetByTimeSlotTypeDaysAndStartTime", query = "select timeSlot from TimeSlotEntity timeSlot where timeSlot.timeSlotType = :timeSlotType and timeSlot.weekdays = :weekdays and timeSlot.startTimeMillis = :startTimeMillis"),
        @NamedQuery(name = "TimeSlotEntity.GetByTimeSlotTypeDaysStartTimeAndEndTime", query = "select timeSlot from TimeSlotEntity timeSlot where timeSlot.timeSlotType = :timeSlotType and timeSlot.weekdays = :weekdays and timeSlot.startTimeMillis = :startTimeMillis and timeSlot.endTimeMillis = :endTimeMillis"),
        @NamedQuery(name = "TimeSlotEntity.GetTimeSlotNames", query = "select name from TimeSlotEntity timeSlot")})


public class TimeSlotEntity extends MetaEntity implements AttributeOwner<TimeSlotAttributeEntity> {

    @Column(name = "TM_SLOT_TYPE")
    private String timeSlotType;

    @Column(name = "TM_SLOT_STATE")
    private String timeSlotState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "WEEKDAYS")
    private String weekdays;

    @Column(name = "START_TIME_MS")
    private Long startTimeMillis;

    @Column(name = "END_TIME_MS")
    private Long endTimeMillis;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval=true)
    private Set<TimeSlotAttributeEntity> attributes = new HashSet<TimeSlotAttributeEntity>();

    public TimeSlotEntity() {
        super();
    }

    public TimeSlotEntity(TimeSlot timeSlot) {
        super(timeSlot);
        setId(timeSlot.getId());
        setTimeSlotType(timeSlot.getTypeKey());
        fromDto(timeSlot);
    }

    public void fromDto(TimeSlot timeSlot) {
        super.fromDTO(timeSlot);

        setTimeSlotState(timeSlot.getStateKey());
        setTimeSlotType(timeSlot.getTypeKey());
        setName(timeSlot.getName());
        setWeekdays(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlot.getWeekdays()));
        if(timeSlot.getStartTime() != null) {
            setStartTimeMillis(TimeOfDayHelper.getMillis(timeSlot.getStartTime()));
        }
        if(timeSlot.getEndTime() != null) {
            setEndTimeMillis(TimeOfDayHelper.getMillis(timeSlot.getEndTime()));
        }

        if (timeSlot.getDescr() != null) {
            setDescrFormatted(timeSlot.getDescr().getFormatted());
            this.setDescrPlain(timeSlot.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }

        if(this.getAttributes() == null) {
            this.setAttributes(new HashSet<TimeSlotAttributeEntity>());
        }
        else {
            this.getAttributes().clear();
        }

        if(timeSlot.getAttributes() != null && !timeSlot.getAttributes().isEmpty()) {
            for (Attribute att : timeSlot.getAttributes()) {
                this.getAttributes().add(new TimeSlotAttributeEntity(att, this));
            }
        }
    }

    public TimeSlotInfo toDto() {
        TimeSlotInfo info = new TimeSlotInfo();



        info.setId(getId());
        info.setTypeKey(getTimeSlotType());
        info.setStateKey(getTimeSlotState());
        info.setName(getName());
        info.setWeekdays(SchedulingServiceUtil.weekdaysString2WeekdaysList(getWeekdays()));

        info.setStartTime(TimeOfDayHelper.setMillis(getStartTimeMillis()));

        info.setEndTime(TimeOfDayHelper.setMillis(getEndTimeMillis()));

        info.setDescr(new RichTextHelper().toRichTextInfo(this.getDescrPlain(), this.getDescrFormatted()));

        info.setMeta(super.toDTO());
        info.setAttributes(TransformUtility.toAttributeInfoList(this));

        return info;
    }

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

    public Set<TimeSlotAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<TimeSlotAttributeEntity> attributes) {
        this.attributes = attributes;
    }
}

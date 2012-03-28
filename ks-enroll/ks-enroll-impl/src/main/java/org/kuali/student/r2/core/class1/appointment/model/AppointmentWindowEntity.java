/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 2/29/12
 */
package org.kuali.student.r2.core.class1.appointment.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.infc.AppointmentSlotRule;
import org.kuali.student.r2.core.appointment.infc.AppointmentWindow;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_APPT_WINDOW")
public class AppointmentWindowEntity extends MetaEntity implements AttributeOwner<AppointmentWindowAttributeEntity> {

    @Column(name = "START_DT")
    private Date startDate;  // When registration starts (for individual) month/day/year 

    @Column(name = "END_DT")
    private Date endDate;    // When registration ends (for individual) month/day/year 

    @Column(name = "PRD_MSTONE_ID")
    private String periodMilestoneId;

    @Column(name = "ASSIGNED_POPULATION_ID")
    private String assignedPopulationId;

    @Column(name = "ASSIGNED_ORDER_TYPE")
    private String assignedOrderType;

    @Column(name = "MAX_APPT_PER_SLOT")
    private Integer maxAppointmentsPerSlot;
    // ---------------------------------------------------------------------
    // Fields for AppointmentSlotRule (flattened out)
    @Column(name = "SR_WEEKDAYS")
    private String weekdays; // Comma delimited "days of week" when appointments can occur

    @Column(name = "SR_START_TIME_MS")
    private Long startTime; // milliseconds since start of day

    @Column(name = "SR_END_TIME_MS")
    private Long endTime; // milliseconds since end of day
    
    // Next two fields correspond to "slot start interval" which is a TimeAmount (has two fields)
    @Column(name = "SR_START_INTVL_DUR_TYPE")
    private String startIntervalDurationType;

    @Column(name = "SR_START_INTVL_TIME_QTY")
    private Integer startIntervalTimeQuantity;

    // Next two fields correspond to "slot duration" which is a TimeAmount (has two fields)
    @Column(name = "SR_DUR_TYPE")
    private String durationType;

    @Column(name = "SR_DUR_TIME_QTY")
    private Integer durationTimeQuantity;

    // =====================================================================
    // The fields below are inherited from IdEntity (and everything IdEntity inherits from)
    // IdEntity is what AppointmentWindow extends (Meta fields are included by inheritance from MetaIdentity)
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "APPT_WINDOW_TYPE")
    private String apptWindowType;

    @Column(name = "APPT_WINDOW_STATE")
    private String apptWindowState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<AppointmentWindowAttributeEntity> attributes = new ArrayList<AppointmentWindowAttributeEntity>();

    public AppointmentWindowEntity() {
    }

    public AppointmentWindowEntity(AppointmentWindow apptWin) {
        super(apptWin);
        this.setId(apptWin.getId());
        // AppointmentWindow specific initialization
        this.setStartDate(apptWin.getStartDate()); // startDate not null
        if (apptWin.getEndDate() != null) { // endDate could be null
            this.setEndDate(apptWin.getEndDate());
        }
        this.setPeriodMilestoneId(apptWin.getPeriodMilestoneId());
        this.setAssignedPopulationId(apptWin.getAssignedPopulationId());
        this.setAssignedOrderType(apptWin.getAssignedOrderTypeKey());
        this.setMaxAppointmentsPerSlot(apptWin.getMaxAppointmentsPerSlot());

        // Generate comma delimited days of week to save (max length is 13 characters)
        List<Integer> weekdays = apptWin.getSlotRule().getWeekdays(); // not null
        StringBuilder weekdaysStr = new StringBuilder();
        for (Integer day: weekdays) {
            if (weekdaysStr.length() > 0) {
                weekdaysStr.append(",");
            }
            weekdaysStr.append(day);
        }
        this.setWeekdays(weekdaysStr.toString());
        AppointmentSlotRule slotRule = apptWin.getSlotRule();
        // start time not null, end time not null
        this.setStartTime(slotRule.getStartTimeOfDay().getMilliSeconds());
        this.setEndTime(slotRule.getEndTimeOfDay().getMilliSeconds());
        // start interval could be null, duration
        if (slotRule.getSlotStartInterval() != null) {
            this.setStartIntervalDurationType(slotRule.getSlotStartInterval().getAtpDurationTypeKey());
            this.setStartIntervalTimeQuantity(Integer.parseInt(slotRule.getSlotStartInterval().getTimeQuantity()));
        }
        // slot duration could be null
        if (slotRule.getSlotDuration() != null) {
            this.setDurationType(slotRule.getSlotDuration().getAtpDurationTypeKey());
            this.setDurationTimeQuantity(Integer.parseInt(slotRule.getSlotDuration().getTimeQuantity()));
        }
        // --- These getters/setters are for inherited fields
        this.setName(apptWin.getName());
        if (apptWin.getDescr() != null) {
            this.setDescrPlain(apptWin.getDescr().getPlain());
            this.setDescrFormatted(apptWin.getDescr().getFormatted());
        }
        // The state/type keys are in every entity, but are not explicitly inherited
        this.setApptWindowState(apptWin.getStateKey());
        this.setApptWindowType(apptWin.getTypeKey());
        // Add attributes individually
        this.setAttributes(new ArrayList<AppointmentWindowAttributeEntity>());
        if (null != apptWin.getAttributes()) {
            for (Attribute att : apptWin.getAttributes()) {
                this.getAttributes().add(new AppointmentWindowAttributeEntity(att, this));
            }
        }
    }

    public String getDescrFormatted() {
        return formatted;
    }

    public void setDescrFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getDescrPlain() {
        return plain;
    }

    public void setDescrPlain(String plain) {
        this.plain = plain;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPeriodMilestoneId() {
        return periodMilestoneId;
    }

    public void setPeriodMilestoneId(String periodMilestoneId) {
        this.periodMilestoneId = periodMilestoneId;
    }

    public String getAssignedPopulationId() {
        return assignedPopulationId;
    }

    public void setAssignedPopulationId(String assignedPopulationId) {
        this.assignedPopulationId = assignedPopulationId;
    }

    public String getAssignedOrderType() {
        return assignedOrderType;
    }

    public void setAssignedOrderType(String assignedOrderType) {
        this.assignedOrderType = assignedOrderType;
    }

    public Integer getMaxAppointmentsPerSlot() {
        return maxAppointmentsPerSlot;
    }

    public void setMaxAppointmentsPerSlot(Integer maxAppointmentsPerSlot) {
        this.maxAppointmentsPerSlot = maxAppointmentsPerSlot;
    }

    // Getters/setters for AppointmentSlotRule fields
    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getStartIntervalDurationType() {
        return startIntervalDurationType;
    }

    public void setStartIntervalDurationType(String startIntervalDurationType) {
        this.startIntervalDurationType = startIntervalDurationType;
    }

    public Integer getStartIntervalTimeQuantity() {
        return startIntervalTimeQuantity;
    }

    public void setStartIntervalTimeQuantity(Integer startIntervalTimeQuantity) {
        this.startIntervalTimeQuantity = startIntervalTimeQuantity;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public Integer getDurationTimeQuantity() {
        return durationTimeQuantity;
    }

    public void setDurationTimeQuantity(Integer durationTimeQuantity) {
        this.durationTimeQuantity = durationTimeQuantity;
    }

    // Getters/setters below from fields derived from IdEntity which are common for all entities
    // Notew: setDescr()/getDescr() is specialized to this type
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApptWindowType() {
        return apptWindowType;
    }

    public void setApptWindowType(String apptWinType) {
        this.apptWindowType = apptWinType;
    }

    public String getApptWindowState() {
        return apptWindowState;
    }

    public void setApptWindowState(String apptWinState) {
        this.apptWindowState = apptWinState;
    }

    @Override
    public void setAttributes(List<AppointmentWindowAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<AppointmentWindowAttributeEntity> getAttributes() {
        return attributes;
    }

    private TimeOfDayInfo convertToTimeOfDayInfo(Long time) {
        TimeOfDayInfo info = new TimeOfDayInfo();
        info.setMilliSeconds(time);
        return info;
    }
    
    private TimeAmountInfo convertToTimeAmountInfo(String typeKey, String quantity) {
        TimeAmountInfo info = new TimeAmountInfo();
        info.setAtpDurationTypeKey(typeKey);
        info.setTimeQuantity(quantity);
        return info;
    }

    public AppointmentWindowInfo toDto() {
        AppointmentWindowInfo info = new AppointmentWindowInfo();
        // AppointmentWindow-specific updates
        AppointmentSlotRuleInfo rule = new AppointmentSlotRuleInfo();
        info.setSlotRule(rule);
        // Set weekdays which takes comma delimited string of numbers and creates List<Integer>
        String[] numArr = getWeekdays().split(",");
        List<Integer> weekdays = new ArrayList<Integer>();
        for (String s: numArr) {
            weekdays.add(Integer.parseInt(s));
        }
        rule.setWeekdays(weekdays);
        // Start time (could be null)
        Long startTime = getStartTime();
        if (startTime != null) {
            rule.setStartTimeOfDay(convertToTimeOfDayInfo(startTime));
        }
        // End time (could be null)
        Long endTime = getEndTime();
        if (endTime != null) {
            rule.setEndTimeOfDay(convertToTimeOfDayInfo(endTime));
        }
        // start interval duration (could be null)
        String durType = getStartIntervalDurationType();
        if (durType != null) {
            // Assume duration also not null
            Integer quantity = getStartIntervalTimeQuantity();
            rule.setSlotStartInterval(convertToTimeAmountInfo(durType, "" + quantity));
        }
        // slot interval duration (could be null)
        durType = getDurationType();
        if (durType != null) {
            // Assume duration also not null
            Integer quantity = getDurationTimeQuantity();
            rule.setSlotStartInterval(convertToTimeAmountInfo(durType, "" + quantity));
        }
        info.setPeriodMilestoneId(getPeriodMilestoneId());
        info.setAssignedPopulationId(getAssignedPopulationId());
        info.setAssignedOrderTypeKey(getAssignedOrderType());
        info.setMaxAppointmentsPerSlot(getMaxAppointmentsPerSlot()); // could be null
        // -------------------------------------------------
        // Stuff that is updated for nearly all entities
        info.setId(getId());
        if (apptWindowType != null) {
            info.setTypeKey(apptWindowType);
        }
        if (apptWindowState != null) {
            info.setStateKey(apptWindowState);
        }
        info.setMeta(super.toDTO());
        if (getDescrPlain() != null) { // assume if this is not null, formatted also not null
            RichTextInfo textInfo = new RichTextInfo();
            textInfo.setFormatted(formatted);
            textInfo.setPlain(plain);
            info.setDescr(textInfo);
        }

        List<AttributeInfo> attrs = new ArrayList<AttributeInfo>();
        for (AppointmentWindowAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            attrs.add(attInfo);
        }
        info.setAttributes(attrs);
        return info;
    }
}

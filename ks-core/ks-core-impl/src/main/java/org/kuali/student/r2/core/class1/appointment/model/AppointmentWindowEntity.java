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

import org.kuali.student.r1.common.entity.KSEntityConstants;
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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_APPT_WINDOW")
public class AppointmentWindowEntity extends MetaEntity implements AttributeOwner<AppointmentWindowAttributeEntity> {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DT")
    private Date startDate;  // When registration starts (for individual) month/day/year 

    @Temporal(TemporalType.TIMESTAMP)
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
    private Set<AppointmentWindowAttributeEntity> attributes = new HashSet<AppointmentWindowAttributeEntity>();

    public AppointmentWindowEntity() {
    }

    public AppointmentWindowEntity(String appointmentWindowTypeKey, AppointmentWindow apptWin) {
        super(apptWin);
        this.setId(apptWin.getId());
        this.setApptWindowType(appointmentWindowTypeKey);
        this.fromDto(apptWin);
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

    public void setAttributes(Set<AppointmentWindowAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public Set<AppointmentWindowAttributeEntity> getAttributes() {
        return attributes;
    }

    private TimeOfDayInfo convertToTimeOfDayInfo(Long time) {
        if(time == null){
            return null;
        }
        TimeOfDayInfo info = new TimeOfDayInfo();
        info.setMilliSeconds(time);
        return info;
    }

    private TimeAmountInfo convertToTimeAmountInfo(String typeKey, Integer quantity) {
        if ((typeKey == null) && (quantity == null)) {
            return null;
        }
        TimeAmountInfo info = new TimeAmountInfo();
        info.setAtpDurationTypeKey(typeKey);
        if (quantity != null) {
            info.setTimeQuantity(quantity);
        }
        return info;
    }

    public void fromDto(AppointmentWindow apptWin) {

        // AppointmentWindow specific initialization; readOnly fields go only in the ctor
        this.setPeriodMilestoneId(apptWin.getPeriodMilestoneId());
        this.setAssignedPopulationId(apptWin.getAssignedPopulationId());
        this.setAssignedOrderType(apptWin.getAssignedOrderTypeKey());
        this.setStartDate(apptWin.getStartDate()); // startDate not null
        this.setEndDate(apptWin.getEndDate());
        this.setMaxAppointmentsPerSlot(apptWin.getMaxAppointmentsPerSlot());

        AppointmentSlotRule slotRule = apptWin.getSlotRule();

        if(slotRule != null){
            if(slotRule.getWeekdays() != null){
                // Generate comma delimited days of week to save (max length is 13 characters)
                List<Integer> weekdays = slotRule.getWeekdays(); // not null
                StringBuilder weekdaysStr = new StringBuilder();
                for (Integer day : weekdays) {
                    if (weekdaysStr.length() > 0) {
                        weekdaysStr.append(",");
                    }
                    weekdaysStr.append(day);
                }
                this.setWeekdays(weekdaysStr.toString());
            }else{
                this.setWeekdays(null);
            }

            this.setStartTime(slotRule.getStartTimeOfDay()==null?null:slotRule.getStartTimeOfDay().getMilliSeconds());
            this.setEndTime(slotRule.getEndTimeOfDay()==null?null:slotRule.getEndTimeOfDay().getMilliSeconds());

            // start interval could be null, duration
            if (slotRule.getSlotStartInterval() != null) {
                this.setStartIntervalDurationType(slotRule.getSlotStartInterval().getAtpDurationTypeKey());
                this.setStartIntervalTimeQuantity(slotRule.getSlotStartInterval().getTimeQuantity());
            }
            // slot duration could be null
            if (slotRule.getSlotDuration() != null) {
                this.setDurationType(slotRule.getSlotDuration().getAtpDurationTypeKey());
                this.setDurationTimeQuantity(slotRule.getSlotDuration().getTimeQuantity());
            }
        }else{
            //Default the slot rule info to null
            this.setWeekdays(null);
            this.setStartTime(null);
            this.setEndTime(null);
            this.setDurationType(null);
            this.setDurationTimeQuantity(null);
        }
        // --- These getters/setters are for inherited fields
        this.setName(apptWin.getName());
        if (apptWin.getDescr() != null) {
            this.setDescrPlain(apptWin.getDescr().getPlain());
            this.setDescrFormatted(apptWin.getDescr().getFormatted());
        }
        // The state keys are in every entity, but are not explicitly inherited
        this.setApptWindowState(apptWin.getStateKey());
        // Add attributes individually
        this.setAttributes(new HashSet<AppointmentWindowAttributeEntity>());
        if (null != apptWin.getAttributes()) {
            for (Attribute att : apptWin.getAttributes()) {
                this.getAttributes().add(new AppointmentWindowAttributeEntity(att, this));
            }
        }
    }

    public AppointmentWindowInfo toDto() {

        AppointmentWindowInfo info = new AppointmentWindowInfo();
        info.setStartDate(getStartDate());
        info.setEndDate(getEndDate());
        // AppointmentWindow-specific updates
        AppointmentSlotRuleInfo appointmentSlotRuleInfo = new AppointmentSlotRuleInfo();
        info.setSlotRule(appointmentSlotRuleInfo);
        // Set weekdays which takes comma delimited string of numbers and creates List<Integer>
        if(getWeekdays()!=null && !getWeekdays().isEmpty()){
            String[] numArr = getWeekdays().split(",");
            List<Integer> weekdays = new ArrayList<Integer>();
            for (String s : numArr) {
                weekdays.add(Integer.parseInt(s));
            }
            appointmentSlotRuleInfo.setWeekdays(weekdays);
        }else{
            appointmentSlotRuleInfo.setWeekdays(null);
        }
        appointmentSlotRuleInfo.setStartTimeOfDay(convertToTimeOfDayInfo(getStartTime()));
        appointmentSlotRuleInfo.setEndTimeOfDay(convertToTimeOfDayInfo(getEndTime()));
        appointmentSlotRuleInfo.setSlotStartInterval(convertToTimeAmountInfo(getStartIntervalDurationType(), getStartIntervalTimeQuantity()));
        appointmentSlotRuleInfo.setSlotDuration(convertToTimeAmountInfo(getDurationType(), getDurationTimeQuantity()));
        info.setPeriodMilestoneId(getPeriodMilestoneId());
        info.setAssignedPopulationId(getAssignedPopulationId());
        info.setAssignedOrderTypeKey(getAssignedOrderType());
        info.setMaxAppointmentsPerSlot(getMaxAppointmentsPerSlot()); // could be null
        info.setName(getName());
        // -------------------------------------------------
        // Stuff that is updated for nearly all entities
        info.setId(getId());
        info.setTypeKey(apptWindowType);
        info.setStateKey(apptWindowState);
        info.setMeta(super.toDTO());
        if (getDescrPlain() != null) { // assume if this is not null, formatted also not null
            RichTextInfo textInfo = new RichTextInfo();
            textInfo.setFormatted(getDescrFormatted());
            textInfo.setPlain(getDescrPlain());
            info.setDescr(textInfo);
        }

        for (AppointmentWindowAttributeEntity att : getAttributes()) {
            info.getAttributes().add(att.toDto());
        }
        return info;
    }
}

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
 */

package org.kuali.student.r2.core.class1.appointment.model;

import org.kuali.student.r2.core.appointment.infc.Appointment;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_APPT")
public class AppointmentEntity extends MetaEntity {

    @Column(name = "APPT_TYPE")
    String apptType;
    @Column(name = "APPT_STATE")
    String apptState;


    @Column(name = "PERS_ID")
    String personId;

    @ManyToOne
    @JoinColumn(name = "SLOT_ID")
    AppointmentSlotEntity slotEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    Date expirationDate;


    public AppointmentEntity() {

    }

    public AppointmentEntity(Appointment appointment) {
        super(appointment);
        // TODO: This seems like a lot to set...services team should look
        this.setApptState(appointment.getStateKey());
        this.setApptType(appointment.getTypeKey());
        this.setPersonId(appointment.getPersonId());
        this.setEffectiveDate(appointment.getEffectiveDate());
        this.setExpirationDate(appointment.getExpirationDate());

        this.fromDto(appointment);
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public String getApptState() {
        return apptState;
    }

    public void setApptState(String apptState) {
        this.apptState = apptState;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public AppointmentSlotEntity getSlotEntity() {
        return slotEntity;
    }

    public void setSlotEntity(AppointmentSlotEntity slotEntity) {
        this.slotEntity = slotEntity;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public AppointmentInfo toDto() {
        AppointmentInfo appointmentInfo = new AppointmentInfo();
        appointmentInfo.setPersonId(this.getPersonId());
        appointmentInfo.setSlotId(this.getSlotEntity().getId());
        appointmentInfo.setEffectiveDate(this.getEffectiveDate());
        appointmentInfo.setExpirationDate(this.getExpirationDate());
        // -------------------------------------------------
        // Stuff that is updated for nearly all entities
        appointmentInfo.setId(getId()); // id is assumed not null
        appointmentInfo.setTypeKey(getApptType()); // type is assumed not null
        appointmentInfo.setStateKey(getApptState()); // state is assumed not null
        appointmentInfo.setMeta(super.toDTO());
        // TODO: Attributes have not been implemented for Appointments since not needed right now.
        return appointmentInfo;
    }

    public void fromDto(Appointment appt) {
        this.setApptState(appt.getStateKey());
        this.setApptType(appt.getTypeKey());
        this.setPersonId(appt.getPersonId());
        //
        // Note: apptSlotEntity can't be set from appt which only contains the
        // id (which is a string) for AppointmentSlot.  When constructing an AppointmentEntity
        // in AppointmentServiceImpl, one needs to use the appointmentSlotDao to "find" (call the find
        // method) to get the AppointmentSlotEntity and call setSlotEntity to set the value
        // separately
    }

}
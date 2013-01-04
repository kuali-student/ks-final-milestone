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

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.infc.Appointment;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * JPA Entity for the appointment table
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_APPT")
public class AppointmentEntity extends MetaEntity implements AttributeOwner<AppointmentAttributeEntity> {

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<AppointmentAttributeEntity> attributes = new HashSet<AppointmentAttributeEntity>();

    public AppointmentEntity() {

    }

    public AppointmentEntity(Appointment appointment) {
        super(appointment);
        this.setId(appointment.getId());
        this.setApptType(appointment.getTypeKey());
        this.setPersonId(appointment.getPersonId());

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

    public Set<AppointmentAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AppointmentAttributeEntity> attributes) {
        this.attributes = attributes;
    }
    
    public AppointmentInfo toDto() {
        AppointmentInfo info = new AppointmentInfo();
        // -------------------------------------------------
        // Stuff that is updated for nearly all entities
        info.setId(getId()); // id is assumed not null
        info.setTypeKey(getApptType()); // type is assumed not null
        info.setStateKey(getApptState()); // state is assumed not null
        info.setPersonId(this.getPersonId());
        info.setSlotId(this.getSlotEntity().getId());
        info.setEffectiveDate(this.getEffectiveDate());
        info.setExpirationDate(this.getExpirationDate());
        info.setMeta(super.toDTO());
        if (getAttributes() != null) {
            for (AppointmentAttributeEntity att : getAttributes()) {
                info.getAttributes().add(att.toDto());
            }
        }
        return info;
    }

    public void fromDto(Appointment appt) {
        this.setApptState(appt.getStateKey());
        this.setEffectiveDate(appt.getEffectiveDate());
        this.setExpirationDate(appt.getExpirationDate());
        //
        // Note: apptSlotEntity can't be set from appt which only contains the
        // id (which is a string) for AppointmentSlot.  When constructing an AppointmentEntity
        // in AppointmentServiceImpl, one needs to use the appointmentSlotDao to "find" (call the find
        // method) to get the AppointmentSlotEntity and call setSlotEntity to set the value
        // separately
        // Add attributes individually
        this.setAttributes(new HashSet<AppointmentAttributeEntity>());
        for (Attribute att : appt.getAttributes()) {
            this.getAttributes().add(new AppointmentAttributeEntity(att, this));
        }
    }

}
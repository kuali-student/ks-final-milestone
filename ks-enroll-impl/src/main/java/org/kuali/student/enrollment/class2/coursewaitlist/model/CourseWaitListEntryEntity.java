/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.coursewaitlist.model;

import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.infc.CourseWaitList;
import org.kuali.student.enrollment.coursewaitlist.infc.CourseWaitListEntry;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for the CourseWaitListEntry table
 * User: jonrcook
 * Date: 9/3/13
 * Time: 11:54 AM
 */
public class CourseWaitListEntryEntity extends MetaEntity implements AttributeOwner<CourseWaitListEntryAttributeEntity> {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")

    private Date expirationDate;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "REG_GROUP_ID")
    private String regGroupId;

    @Column(name = "POSITION")
    private Integer position;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_CHECK_IN")
    private Date lastCheckIn;

    // =====================================================================
    // The fields below are inherited from MetaEntity (and everything MetaEntity inherits from)
    // MetaEntity is what CourseWaitListEntry extends (Meta fields are included by in heritance from MetaIdentity)

    @Column(name = "WAIT_LIST_ENTRY_TYPE")
    private String type;

    @Column(name = "WAIT_LIST_ENTRY_STATE")
    private String state;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<CourseWaitListEntryAttributeEntity> attributes = new HashSet<CourseWaitListEntryAttributeEntity>();

    public Set<CourseWaitListEntryAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CourseWaitListEntryAttributeEntity> attributes) {
        this.attributes = attributes;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(Date lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
    public void fromDto(CourseWaitListEntry entry) {
        this.setEffectiveDate(entry.getEffectiveDate());
        this.setExpirationDate(entry.getExpirationDate());
        this.setAttributes(new HashSet<CourseWaitListEntryAttributeEntity>());
        for (Attribute att : entry.getAttributes()) {
            CourseWaitListEntryAttributeEntity attEntity = new CourseWaitListEntryAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
        this.setLastCheckIn(entry.getLastCheckIn());
        this.setPosition(entry.getPosition());
        this.setRegGroupId(entry.getRegistrationGroupId());
        this.setStudentId(entry.getStudentId());
    }

    public CourseWaitListEntryEntity(CourseWaitListEntry entry) {
        super(entry);
        this.setId(entry.getId());
        this.setType(entry.getTypeKey());
        this.fromDto(entry);
    }

    public CourseWaitListEntryInfo toDto() {
        CourseWaitListEntryInfo entryInfo = new CourseWaitListEntryInfo();
        entryInfo.setId(getId());
        entryInfo.setStateKey(getState());
        entryInfo.setTypeKey(getType());
        entryInfo.setStudentId(getStudentId());
        entryInfo.setEffectiveDate(getEffectiveDate());
        entryInfo.setExpirationDate(getExpirationDate());
        entryInfo.setPosition(getPosition());
        entryInfo.setLastCheckIn(getLastCheckIn());
        entryInfo.setRegistrationGroupId(getRegGroupId());
        entryInfo.setMeta(super.toDTO());
        for(CourseWaitListEntryAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            entryInfo.getAttributes().add(attInfo);
        }
        return entryInfo;
    }
}

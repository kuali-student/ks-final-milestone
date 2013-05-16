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

package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestSet;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_SCHED_RQST_SET")
@NamedQueries({
        @NamedQuery(name="ScheduleRequestSet.getScheduleRequestSetByType", query="SELECT srs FROM ScheduleRequestSetEntity srs WHERE srs.schedReqSetType = :schedReqSetType"),
        @NamedQuery(name="ScheduleRequestSet.getScheduleRequestSetByRefObjTypeAndRefObjId",
                query="SELECT srs FROM ScheduleRequestSetEntity srs " +
                        "WHERE :refObjectTypeKey = srs.refObjectTypeKey and :refObjectId in elements(srs.refObjectIds)"),
        @NamedQuery(name="ScheduleRequestSet.getScheduleRequestSetIdsByRefObjType",
                query="SELECT srs.id FROM ScheduleRequestSetEntity srs WHERE :refObjectTypeKey = srs.refObjectTypeKey")
    })
public class ScheduleRequestSetEntity extends MetaEntity implements AttributeOwner<ScheduleRequestSetAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "SCHED_RQST_SET_TYPE")
    private String schedReqSetType;

    @Column(name = "SCHED_RQST_SET_STATE")
    private String schedReqSetState;

    @Column(name="MAX_ENRL_SHARED_IND")
    private Boolean isMaxEnrollmentShared;

    @Column(name="MAX_ENRL")
    private Integer maximumEnrollment;

    @Column(name="REF_OBJECT_TYPE")
    private String refObjectTypeKey;

    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_REF_OBJECT", joinColumns = @JoinColumn(name = "REF_OBJECT_ID"))
    @Column(name="SCHED_RQST_SET_ID")
    private List<String> refObjectIds;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval=true)
    private Set<ScheduleRequestSetAttributeEntity> attributes = new HashSet<ScheduleRequestSetAttributeEntity>();


    public ScheduleRequestSetEntity() {
    }

    public ScheduleRequestSetEntity(ScheduleRequestSet scheduleRequestSet) {
        super(scheduleRequestSet);
        setId(scheduleRequestSet.getId());
        setSchedReqSetType(scheduleRequestSet.getTypeKey());
        setRefObjectTypeKey(scheduleRequestSet.getRefObjectTypeKey());
        fromDto(scheduleRequestSet);
    }


    public void fromDto(ScheduleRequestSet scheduleRequestSet) {
        setSchedReqSetState(scheduleRequestSet.getStateKey());
        setName(scheduleRequestSet.getName());

        if (scheduleRequestSet.getDescr() != null) {
            setFormatted(scheduleRequestSet.getDescr().getFormatted());
            setPlain(scheduleRequestSet.getDescr().getPlain());
        } else {
            this.setFormatted(null);
            this.setPlain(null);
        }

        setMaxEnrollmentShared(scheduleRequestSet.getIsMaxEnrollmentShared());
        setMaximumEnrollment(scheduleRequestSet.getMaximumEnrollment());

        if(refObjectIds == null) {
            setRefObjectIds(new ArrayList<String>());
        } else {
            getRefObjectIds().clear();
        }
        if(scheduleRequestSet.getRefObjectIds() != null && !scheduleRequestSet.getRefObjectIds().isEmpty()) {
            getRefObjectIds().addAll(scheduleRequestSet.getRefObjectIds());
        }

        if(this.getAttributes() == null) {
            setAttributes(new HashSet<ScheduleRequestSetAttributeEntity>());
        }
        else {
            getAttributes().clear();
        }

        if(scheduleRequestSet.getAttributes() != null && !scheduleRequestSet.getAttributes().isEmpty()) {
            for (Attribute att : scheduleRequestSet.getAttributes()) {
                getAttributes().add(new ScheduleRequestSetAttributeEntity(att, this));
            }
        }


    }

    public ScheduleRequestSetInfo toDto() {
        ScheduleRequestSetInfo scheduleRequestSet = new ScheduleRequestSetInfo();
        scheduleRequestSet.setDescr(new RichTextHelper().toRichTextInfo(this.getPlain(), this.getFormatted()));
        scheduleRequestSet.setName(this.getName());
        scheduleRequestSet.setId(this.getId());
        scheduleRequestSet.setTypeKey(this.getSchedReqSetType());
        scheduleRequestSet.setStateKey(this.getSchedReqSetState());
        scheduleRequestSet.setMeta(super.toDTO());
        scheduleRequestSet.setMaxEnrollmentShared(getMaxEnrollmentShared());
        scheduleRequestSet.setMaximumEnrollment(getMaximumEnrollment());

        // Attributes
        scheduleRequestSet.setAttributes(TransformUtility.toAttributeInfoList(this));

        scheduleRequestSet.setRefObjectTypeKey(getRefObjectTypeKey());
        if(refObjectIds != null && !refObjectIds.isEmpty()) {
            scheduleRequestSet.setRefObjectIds(new ArrayList<String>(getRefObjectIds()));
        }

        return scheduleRequestSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSchedReqSetType() {
        return schedReqSetType;
    }

    public void setSchedReqSetType(String schedReqSetType) {
        this.schedReqSetType = schedReqSetType;
    }

    public String getSchedReqSetState() {
        return schedReqSetState;
    }

    public void setSchedReqSetState(String schedReqSetState) {
        this.schedReqSetState = schedReqSetState;
    }

    public Boolean getMaxEnrollmentShared() {
        return isMaxEnrollmentShared;
    }

    public void setMaxEnrollmentShared(Boolean maxEnrollmentShared) {
        isMaxEnrollmentShared = maxEnrollmentShared;
    }

    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    public List<String> getRefObjectIds() {
        return refObjectIds;
    }

    public void setRefObjectIds(List<String> refObjectIds) {
        this.refObjectIds = refObjectIds;
    }

    public Set<ScheduleRequestSetAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ScheduleRequestSetAttributeEntity> attributes) {
        this.attributes = attributes;
    }
}

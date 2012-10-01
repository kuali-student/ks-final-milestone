package org.kuali.student.r2.core.class1.scheduling.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.class1.lui.model.LuCodeEntity;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequest;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;
import org.kuali.student.r2.lum.clu.infc.LuCode;

import javax.persistence.*;
import java.util.*;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@Entity
@Table(name = "KSEN_SCHED_RQST")
public class ScheduleRequestEntity extends MetaEntity implements AttributeOwner<ScheduleRequestAttributeEntity> {

    @Column(name = "REF_OBJECT_ID")
    private String refObjectId;

    @Column(name = "REF_OBJECT_TYPE")
    private String refObjectTypeKey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleRequest", orphanRemoval=true)
    private List<ScheduleRequestComponentEntity> scheduleRequestComponentEntities;

    // IdEntity fields follow
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "SCHED_RQST_TYPE")
    private String schedReqType;

    @Column(name = "SCHED_RQST_STATE")
    private String schedReqState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval=true)
    private Set<ScheduleRequestAttributeEntity> attributes = new HashSet<ScheduleRequestAttributeEntity>();

    public ScheduleRequestEntity() {
    }

    public ScheduleRequestEntity(ScheduleRequest scheduleRequest) {
        super(scheduleRequest);
        this.setId(scheduleRequest.getId());
        this.setRefObjectId(scheduleRequest.getRefObjectId());
        this.setRefObjectTypeKey(scheduleRequest.getRefObjectTypeKey());
        this.setSchedReqType(scheduleRequest.getTypeKey());

        this.fromDto(scheduleRequest);
    }

    public void fromDto(ScheduleRequest scheduleRequest) {
        this.setSchedReqState(scheduleRequest.getStateKey());
        this.setName(scheduleRequest.getName());
        if (scheduleRequest.getDescr() != null) {
            this.setFormatted(scheduleRequest.getDescr().getFormatted());
            this.setPlain(scheduleRequest.getDescr().getPlain());
        } else {
            this.setFormatted(null);
            this.setPlain(null);
        }

        this.setAttributes(new HashSet<ScheduleRequestAttributeEntity>());
        if (null != scheduleRequest.getAttributes()) {
            for (Attribute att : scheduleRequest.getAttributes()) {
                this.getAttributes().add(new ScheduleRequestAttributeEntity(att, this));
            }
        }

        this.setScheduleRequestComponentEntities(new ArrayList<ScheduleRequestComponentEntity>());
        if(scheduleRequest.getScheduleRequestComponents() != null) {
            for(ScheduleRequestComponent sqComp : scheduleRequest.getScheduleRequestComponents()){
                this.getScheduleRequestComponentEntities().add(new ScheduleRequestComponentEntity(sqComp));
            }
        }
    }

    public ScheduleRequestInfo toDto() {
        ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
        scheduleRequestInfo.setRefObjectId(this.getRefObjectId());
        scheduleRequestInfo.setRefObjectTypeKey(this.getRefObjectTypeKey());

        // -------------------------------------------------
        // Stuff that is updated for nearly all entities
        scheduleRequestInfo.setId(this.getId()); // id is assumed not null
        scheduleRequestInfo.setTypeKey(this.getSchedReqType()); // type is assumed not null
        scheduleRequestInfo.setStateKey(this.getSchedReqState()); // state is assumed not null
        scheduleRequestInfo.setMeta(super.toDTO());
        if (getAttributes() != null) {
            for (ScheduleRequestAttributeEntity att : getAttributes()) {
                scheduleRequestInfo.getAttributes().add(att.toDto());
            }
        }

        if (getScheduleRequestComponentEntities() != null) {
            for (ScheduleRequestComponentEntity sqComp : getScheduleRequestComponentEntities()) {
                scheduleRequestInfo.getScheduleRequestComponents().add(sqComp.toDto());
            }
        }

        return scheduleRequestInfo;
    }

    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    public List<ScheduleRequestComponentEntity> getScheduleRequestComponentEntities() {
        return scheduleRequestComponentEntities;
    }

    public void setScheduleRequestComponentEntities(List<ScheduleRequestComponentEntity> scheduleRequestComponentEntities) {
        this.scheduleRequestComponentEntities = scheduleRequestComponentEntities;
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

    public String getSchedReqType() {
        return schedReqType;
    }

    public void setSchedReqType(String schedReqType) {
        this.schedReqType = schedReqType;
    }

    public String getSchedReqState() {
        return schedReqState;
    }

    public void setSchedReqState(String schedReqState) {
        this.schedReqState = schedReqState;
    }

    public Set<ScheduleRequestAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ScheduleRequestAttributeEntity> attributes) {
        this.attributes = attributes;
    }
}

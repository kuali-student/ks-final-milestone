package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.infc.Schedule;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponent;

import javax.persistence.*;
import java.util.*;

/**
 * Persistence entity class for an instance of an Actual Schedule
 *
 * @author andrewlubbers
 */
@Entity
@Table(name = "KSEN_SCHED")
@NamedQueries({
        @NamedQuery(name = "Schedule.getSchedulesByType", query = "Select schedule from ScheduleEntity schedule where schedule.scheduleType =:typeKey")
})
public class ScheduleEntity extends MetaEntity implements AttributeOwner<ScheduleAttributeEntity> {

    @Column(name = "ATP_ID")
    private String atpId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule", orphanRemoval=true)
    private List<ScheduleComponentEntity> scheduleComponents;

    // IdEntity fields follow
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "SCHED_TYPE")
    private String scheduleType;

    @Column(name = "SCHED_STATE")
    private String scheduleState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval=true)
    private Set<ScheduleAttributeEntity> attributes = new HashSet<ScheduleAttributeEntity>();

    public ScheduleEntity() {
        super();
    }

    public ScheduleEntity(Schedule schedule) {
        super(schedule);

        fromDto(schedule);
    }

    /**
     * Populates the entity with data from the instance of Schedule passed in.
     *
     * @param schedule
     * @return a non-null collection of objects that were orphaned from this entity
     */
    public void fromDto(Schedule schedule) {
        this.setId(schedule.getId());
        this.setScheduleState(schedule.getStateKey());
        this.setScheduleType(schedule.getTypeKey());
        this.setAtpId(schedule.getAtpId());
        this.setName(schedule.getName());
        if (schedule.getDescr() != null) {
            this.setPlain(schedule.getDescr().getPlain());
            this.setFormatted(schedule.getDescr().getFormatted());
        }
        else {
            this.setPlain(null);
            this.setFormatted(null);
        }

        if (scheduleComponents != null) {
            scheduleComponents.clear();
        } else {
            scheduleComponents = new ArrayList<ScheduleComponentEntity>();
        }

        if (!schedule.getScheduleComponents().isEmpty()) {
            for (ScheduleComponent component : schedule.getScheduleComponents()) {
                ScheduleComponentEntity componentEntity = new ScheduleComponentEntity(component);
                componentEntity.setSchedule(this);
                scheduleComponents.add(componentEntity);
            }
        }

        TransformUtility.mergeToEntityAttributes(ScheduleAttributeEntity.class, schedule, this);

    }

    public ScheduleInfo toDto() {
        ScheduleInfo info = new ScheduleInfo();
        info.setId(getId());
        info.setAtpId(getAtpId());
        info.setMeta(toDTO());
        info.setName(getName());
        info.setDescr(RichTextHelper.buildRichTextInfo(getPlain(), getFormatted()));
        info.setStateKey(getScheduleState());
        info.setTypeKey(getScheduleType());
        info.setAttributes(TransformUtility.toAttributeInfoList(this));

        List<ScheduleComponentInfo> componentInfos;
        if(this.getScheduleComponents() != null) {
            componentInfos = new ArrayList<ScheduleComponentInfo>(getScheduleComponents().size());
            for(ScheduleComponentEntity sce : getScheduleComponents()) {
                componentInfos.add(sce.toDto());
            }
        }
        else {
            componentInfos = new ArrayList<ScheduleComponentInfo>(0);
        }

        info.setScheduleComponents(componentInfos);

        return info;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
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

    public String getScheduleState() {
        return scheduleState;
    }

    public void setScheduleState(String scheduleState) {
        this.scheduleState = scheduleState;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Set<ScheduleAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ScheduleAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<ScheduleComponentEntity> getScheduleComponents() {
        return scheduleComponents;
    }

    public void setScheduleComponents(List<ScheduleComponentEntity> scheduleComponents) {
        this.scheduleComponents = scheduleComponents;
    }
}

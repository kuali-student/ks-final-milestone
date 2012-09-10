package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Schedule Component Entity
 */
@Entity
@Table(name = "KSEN_SCHED_CMP")
public class ScheduleComponentEntity extends MetaEntity {

    //  TODO: Uncomment this when the entity becomes available.
    //@ManyToOne
    //@JoinColumn(name = "SCHED_ID")
    //private ScheduleEntity schedule;

    @Column (name = "ROOM_ID")
    private String roomId;

    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_CMP_TMSLOT", joinColumns = @JoinColumn(name = "SCHED_CMP_ID"))
    @Column(name="TM_SLOT_ID")
    private List<String> timeSlotIds;

    @Column(name = "TBA_IND")
    private Boolean isTBA;

    public ScheduleComponentEntity() {}

    public ScheduleComponentEntity(ScheduleComponent scheduleComponent) {
        super();
        this.setId(scheduleComponent.getId());
        this.setRoomId(scheduleComponent.getRoomId());
        this.setIsTBA(scheduleComponent.getIsTBA());
        fromDto(scheduleComponent);
    }

    public List<Object> fromDto(ScheduleComponent scheduleComponent) {
        if (scheduleComponent.getTimeSlotIds() != null) {
            timeSlotIds = new ArrayList<String>(scheduleComponent.getTimeSlotIds());
        } else {
            timeSlotIds = null;
        }
        return new ArrayList<Object>();
    }

    public ScheduleComponentInfo toDto() {
        ScheduleComponentInfo scheduleComponentInfo = new ScheduleComponentInfo();
        scheduleComponentInfo.setId(getId());
        scheduleComponentInfo.setRoomId(getRoomId());
        scheduleComponentInfo.setIsTBA(getIsTBA());
        if (timeSlotIds != null) {
            List<String> ids = new ArrayList<String>();
            ids.addAll(timeSlotIds);
            scheduleComponentInfo.setTimeSlotIds(ids);
        }
        return scheduleComponentInfo;
    }

    public List<String> getTimeSlotIds() {
        return timeSlotIds;
    }

    public void setTimeSlotIds(List<String> timeSlotIds) {
        this.timeSlotIds = timeSlotIds;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Boolean getIsTBA() {
        return isTBA;
    }

    public void setIsTBA(Boolean TBA) {
        isTBA = TBA;
    }
}

package org.kuali.student.r2.core.class1.scheduling.model;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@Entity
@Table(name = "KSEN_SCHED_RQST_CMP")
public class ScheduleRequestComponentEntity extends MetaEntity {

    // TODO: Left as @Column per Sambit, TBD: Change the following to join relations
    @Column
    private List<String> buildingIds;

    @Column
    private List<String> campusIds;

    @Column
    private List<String> orgIds;

    @Column
    private List<String> resourceTypeKeys;

    @Column
    private List<String> roomIds;

    @Column
    private List<String> timeSlotIds;

    @ManyToOne
    @JoinColumn(name = "SCHED_RQST_ID")
    private ScheduleRequestEntity scheduleRequest;

    public ScheduleRequestComponentEntity() {
    }

    public ScheduleRequestComponentEntity(ScheduleRequestComponent scheduleRequestComponent) {
        super();
        this.setId(scheduleRequestComponent.getId());
    }

    public ScheduleRequestComponentInfo toDto() {
        ScheduleRequestComponentInfo scheduleRequestComponentInfo = new ScheduleRequestComponentInfo();
        scheduleRequestComponentInfo.setId(this.getId());
        return scheduleRequestComponentInfo;
    }

    public List<String> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }

    public List<String> getCampusIds() {
        return campusIds;
    }

    public void setCampusIds(List<String> campusIds) {
        this.campusIds = campusIds;
    }

    public List<String> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public List<String> getResourceTypeKeys() {
        return resourceTypeKeys;
    }

    public void setResourceTypeKeys(List<String> resourceTypeKeys) {
        this.resourceTypeKeys = resourceTypeKeys;
    }

    public List<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    public List<String> getTimeSlotIds() {
        return timeSlotIds;
    }

    public void setTimeSlotIds(List<String> timeSlotIds) {
        this.timeSlotIds = timeSlotIds;
    }

    public ScheduleRequestEntity getScheduleRequest() {
        return scheduleRequest;
    }

    public void setScheduleRequest(ScheduleRequestEntity scheduleRequest) {
        this.scheduleRequest = scheduleRequest;
    }
}

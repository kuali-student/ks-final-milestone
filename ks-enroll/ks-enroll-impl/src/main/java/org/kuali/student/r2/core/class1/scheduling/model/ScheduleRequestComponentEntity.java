package org.kuali.student.r2.core.class1.scheduling.model;

import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@Entity
@Table(name = "KSEN_SCHED_RQST_CMP")
public class ScheduleRequestComponentEntity {

    @Column(name = "SCHED_RQST_ID")
    private String id;

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

    public ScheduleRequestComponentEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r2.common.entity.BaseEntity;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@Entity
@Table(name = "KSEN_SCHED_RQST_CMP")
public class ScheduleRequestComponentEntity extends BaseEntity {
    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_RQST_CMP_BLDG",joinColumns = @JoinColumn(name = "CMP_ID"))
    @Column(name="BUILDING_ID")
    private List<String> buildingIds;

    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_RQST_CMP_CAMPUS",joinColumns = @JoinColumn(name = "CMP_ID"))
    @Column(name="CAMPUS_ID")
    private List<String> campusIds;

    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_RQST_CMP_ORG",joinColumns = @JoinColumn(name = "CMP_ID"))
    @Column(name="ORG_ID")
    private List<String> orgIds;

    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_RQST_CMP_RT",joinColumns = @JoinColumn(name = "CMP_ID"))
    @Column(name="RSRC_TYPE_KEY")
    private List<String> resourceTypeKeys;

    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_RQST_CMP_ROOM",joinColumns = @JoinColumn(name = "CMP_ID"))
    @Column(name="ROOM_ID")
    private List<String> roomIds;

    @ElementCollection
    @CollectionTable(name ="KSEN_SCHED_RQST_CMP_TMSLOT",joinColumns = @JoinColumn(name = "CMP_ID"))
    @Column(name="TM_SLOT_ID")
    private List<String> timeSlotIds;

    @ManyToOne
    @JoinColumn(name = "SCHED_RQST_ID")
    private ScheduleRequestEntity scheduleRequest;

    @Column(name = "TBA_IND")
    private Boolean isTBA;

    public ScheduleRequestComponentEntity() {
    }

    public ScheduleRequestComponentEntity(ScheduleRequestComponent scheduleRequestComponent) {
        super();

        setId(scheduleRequestComponent.getId());
        setTBA(scheduleRequestComponent.getIsTBA());

        fromDto(scheduleRequestComponent);
    }

    public List<Object> fromDto(ScheduleRequestComponent scheduleRequestComponent) {
        List<Object> orphansToDelete = new ArrayList<Object>();

        if(scheduleRequestComponent.getBuildingIds() != null) {
            buildingIds = new ArrayList<String>(scheduleRequestComponent.getBuildingIds());
        }else{
            buildingIds = null;
        }
        if(scheduleRequestComponent.getCampusIds() != null) {
            campusIds = new ArrayList<String>(scheduleRequestComponent.getCampusIds());
        }else{
            campusIds = null;
        }
        if(scheduleRequestComponent.getOrgIds() != null) {
            orgIds = new ArrayList<String>(scheduleRequestComponent.getOrgIds());
        }else{
            orgIds = null;
        }
        if(scheduleRequestComponent.getResourceTypeKeys() != null) {
            resourceTypeKeys = new ArrayList<String>(scheduleRequestComponent.getResourceTypeKeys());
        }else{
            resourceTypeKeys = null;
        }
        if(scheduleRequestComponent.getRoomIds() != null) {
            roomIds = new ArrayList<String>(scheduleRequestComponent.getRoomIds());
        }else{
            roomIds = null;
        }
        if(scheduleRequestComponent.getTimeSlotIds() != null) {
            timeSlotIds = new ArrayList<String>(scheduleRequestComponent.getTimeSlotIds());
        }else{
            timeSlotIds = null;
        }

        return orphansToDelete;
    }

    public ScheduleRequestComponentInfo toDto() {
        ScheduleRequestComponentInfo scheduleRequestComponentInfo = new ScheduleRequestComponentInfo();
        scheduleRequestComponentInfo.setId(this.getId());
        scheduleRequestComponentInfo.setIsTBA(getTBA());

        if(buildingIds != null){
            scheduleRequestComponentInfo.setBuildingIds(addIds(buildingIds));
        }

        if(campusIds != null){
            scheduleRequestComponentInfo.setCampusIds(addIds(campusIds));
        }

        if(orgIds != null) {
            scheduleRequestComponentInfo.setOrgIds(addIds(orgIds));
        }

        if(resourceTypeKeys != null) {
            scheduleRequestComponentInfo.setResourceTypeKeys(addIds(resourceTypeKeys));
        }

        if(roomIds != null){
            scheduleRequestComponentInfo.setRoomIds(addIds(roomIds));
        }

        if(timeSlotIds != null) {
            scheduleRequestComponentInfo.setTimeSlotIds(addIds(timeSlotIds));
        }

        return scheduleRequestComponentInfo;
    }

    private List<String> addIds(List<String> aList){
        List<String> objs = new ArrayList<String>();
        objs.addAll(aList);

        return objs;
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

    public Boolean getTBA() {
        return isTBA;
    }

    public void setTBA(Boolean TBA) {
        isTBA = TBA;
    }
}

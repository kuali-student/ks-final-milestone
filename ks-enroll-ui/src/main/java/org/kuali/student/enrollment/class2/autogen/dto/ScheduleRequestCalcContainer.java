package org.kuali.student.enrollment.class2.autogen.dto;

import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 4/13/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleRequestCalcContainer {


    private String aoId;
    private String schdCmpId;
    private List<TimeSlotInfo> timeSlots;
    private List<RoomInfo> rooms;
    private List<BuildingInfo> bldgs;
    private String scheduleType; // requested or actual
    private Boolean tbaInd;

    public ScheduleRequestCalcContainer(String aoId, String schdCmpId, String scheduleType, List<TimeSlotInfo> timeSlots,List<RoomInfo> rooms, List<BuildingInfo> bldgs , Boolean tbaInd) {
        this.aoId = aoId;
        this.scheduleType = scheduleType;
        this.tbaInd = tbaInd;

        this.schdCmpId = schdCmpId;
        this.timeSlots = timeSlots;
        this.rooms = rooms;
        this.bldgs = bldgs;
    }

    public String getAoId() {
        return aoId;
    }

    public void setAoId(String aoId) {
        this.aoId = aoId;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Boolean getTbaInd() {
        return tbaInd;
    }

    public void setTbaInd(Boolean tbaInd) {
        this.tbaInd = tbaInd;
    }

    public List<BuildingInfo> getBldgs() {
        return bldgs;
    }

    public void setBldgs(List<BuildingInfo> bldgs) {
        this.bldgs = bldgs;
    }

    public List<RoomInfo> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomInfo> rooms) {
        this.rooms = rooms;
    }

    public String getSchdCmpId() {
        return schdCmpId;
    }

    public void setSchdCmpId(String schdCmpId) {
        this.schdCmpId = schdCmpId;
    }

    public List<TimeSlotInfo> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotInfo> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
